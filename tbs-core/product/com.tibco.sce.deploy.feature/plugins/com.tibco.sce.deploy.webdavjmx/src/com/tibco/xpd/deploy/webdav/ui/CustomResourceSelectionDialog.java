/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.deploy.webdav.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.tibco.xpd.deploy.webdav.Utils;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.internal.ide.IIDEHelpContextIds;
import org.eclipse.ui.internal.ide.misc.CheckboxTreeAndListGroup;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

/**
 * CustomResourceSelectionDialog is a customised version of the ResourceSelectionDialog. This allows to filter the Forms specific
 * Folders within the projects in the workspace. The current implementation of the ResourceSelectionDialog does not allow us to add
 * the custom ITreeContentProvider in the getResourceProvider method. There is no way to extend this class as is. NOTE: This is a
 * stop-gap implementation due to want of time. We need to address this wizard similar to what we have in the IPE server wizard (in
 * next release.)
 */
@SuppressWarnings("restriction")
public class CustomResourceSelectionDialog extends SelectionDialog {

    /** A reusable empty object array. */
    private static final Object[] EMPTY_OBJECTS = new Object[0];
    /** Widget Height. */
    private static final int SIZING_SELECTION_WIDGET_HEIGHT = 300;
    /** Widget Width. */
    private static final int SIZING_SELECTION_WIDGET_WIDTH = 400;

    /** The set of valid file extensions. */
    private static final String[] VALID_EXTENSIONS = { "form", "properties", "xml", "js" };
    /** the root element to populate the viewer with. */
    private final IAdaptable root;
    /** the visual selection widget group. */
    private CheckboxTreeAndListGroup selectionGroup;

    /**
     * Constructs a new CustomResourceSelectionDialog.
     * @param parentShell
     * @param rootElement
     * @param message
     */
    public CustomResourceSelectionDialog(Shell parentShell, IAdaptable rootElement, String message) {
        super(parentShell);
        setTitle(IDEWorkbenchMessages.ResourceSelectionDialog_title);
        root = rootElement;
        if (message != null) {
            setMessage(message);
        } else {
            setMessage(IDEWorkbenchMessages.ResourceSelectionDialog_message);
        }
        setShellStyle(getShellStyle() | SWT.RESIZE);
    }

    /**
     * Visually checks the previously-specified elements in the container (left) portion of this dialog's resource selection viewer.
     */
    @SuppressWarnings("unchecked")
    private void checkInitialSelections() {
        Iterator<IResource> itemsToCheck = getInitialElementSelections().iterator();
        while (itemsToCheck.hasNext()) {
            IResource currentElement = itemsToCheck.next();
            if (currentElement.getType() == IResource.FILE) {
                selectionGroup.initialCheckListItem(currentElement);
            } else {
                selectionGroup.initialCheckTreeItem(currentElement);
            }
        }
    }

    /**
     * @param event the event
     */
    public void checkStateChanged(CheckStateChangedEvent event) {
        getOkButton().setEnabled(selectionGroup.getCheckedElementCount() > 0);
    }

    /*
     * (non-Javadoc) Method declared in Window.
     */
    @Override
    protected void configureShell(Shell shell) {
        super.configureShell(shell);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(shell, IIDEHelpContextIds.RESOURCE_SELECTION_DIALOG);
    }

    @Override
    public void create() {
        super.create();
        initializeDialog();
    }

    /*
     * (non-Javadoc) Method declared on Dialog.
     */
    @Override
    protected Control createDialogArea(Composite parent) {
        // page group
        Composite composite = (Composite)super.createDialogArea(parent);

        // create the input element, which has the root resource
        // as its only child
        List<IAdaptable> input = new ArrayList<IAdaptable>();
        input.add(root);

        createMessageArea(composite);
        selectionGroup = new CheckboxTreeAndListGroup(composite, input, getResourceProvider(IResource.FOLDER | IResource.PROJECT
            | IResource.ROOT), WorkbenchLabelProvider.getDecoratingWorkbenchLabelProvider(), getResourceProvider(IResource.FILE),
            WorkbenchLabelProvider.getDecoratingWorkbenchLabelProvider(), SWT.NONE,
            // since this page has no other significantly-sized
            // widgets we need to hardcode the combined widget's
            // size, otherwise it will open too small
            SIZING_SELECTION_WIDGET_WIDTH, SIZING_SELECTION_WIDGET_HEIGHT);

        composite.addControlListener(new ControlListener() {
            public void controlMoved(ControlEvent e) {
            }

            public void controlResized(ControlEvent e) {
                // Also try and reset the size of the columns as appropriate
                TableColumn[] columns = selectionGroup.getListTable().getColumns();
                for (TableColumn element : columns) {
                    element.pack();
                }
            }
        });

        return composite;
    }

    /**
     * Returns a content provider for <code>IResource</code>s that returns only children of the given resource type.
     */
    private ITreeContentProvider getResourceProvider(final int resourceType) {
        return new WorkbenchContentProvider() {
            @Override
            public Object[] getChildren(Object o) {
                if (o instanceof IContainer) {
                    IResource[] members = null;
                    try {
                        members = ((IContainer)o).members();
                    } catch (CoreException e) {
                        // just return an empty set of children
                        return EMPTY_OBJECTS;
                    }

                    // filter out the desired resource types
                    List<IResource> results = new ArrayList<IResource>();
                    for (IResource element : members) {
                        // And the test bits with the resource types to see if they are what we want
                        if ((element.getType() & resourceType) > 0) {
                            if (element.getType() == IResource.PROJECT && Utils.isValidProjectWithFormsFolder(element.getName())) {
                                results.add(element);
                            } else if (element.getType() == IResource.FOLDER && Utils.isContainsFormsFolder((IFolder)element)) {
                                results.add(element);
                            } else if (o instanceof IFolder && element.getType() == IResource.FILE) {
                                if (isValidFile((IFile)element)) {
                                    results.add(element);
                                }
                            }
                        }
                    }
                    return results.toArray();
                }
                // input element case
                if (o instanceof Collection) {
                    return ((Collection<?>)o).toArray();
                }
                return EMPTY_OBJECTS;
            }
        };
    }

    /**
     * Initializes this dialog's controls.
     */
    private void initializeDialog() {
        selectionGroup.addCheckStateListener(new ICheckStateListener() {
            public void checkStateChanged(CheckStateChangedEvent event) {
                getOkButton().setEnabled(selectionGroup.getCheckedElementCount() > 0);
            }
        });

        if (getInitialElementSelections().isEmpty()) {
            getOkButton().setEnabled(false);
        } else {
            checkInitialSelections();
        }
    }

    private boolean isValidFile(IFile file) {
        String fileExt = file.getFileExtension();
        if (fileExt == null) {
            return false;
        }
        for (String element : VALID_EXTENSIONS) {
            if (element.equals(fileExt)) {
                return true;
            }
        }
        return false;
    }

    /**
     * The <code>ResourceSelectionDialog</code> implementation of this <code>Dialog</code> method builds a list of the selected
     * resources for later retrieval by the client and closes this dialog.
     */
    @Override
    protected void okPressed() {
        @SuppressWarnings("unchecked") Iterator<Object> resultEnum = selectionGroup.getAllCheckedListItems();
        List<Object> list = new ArrayList<Object>();
        while (resultEnum.hasNext()) {
            list.add(resultEnum.next());
        }
        setResult(list);
        super.okPressed();
    }
}
