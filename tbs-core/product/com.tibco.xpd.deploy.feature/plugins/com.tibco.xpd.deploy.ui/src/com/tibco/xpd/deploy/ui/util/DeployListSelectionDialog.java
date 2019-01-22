/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.util;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ICheckable;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.internal.IWorkbenchHelpContextIds;
import org.eclipse.ui.internal.WorkbenchMessages;


/**
 * A standard dialog which solicits a list of selections from the user.
 * This class is configured with an arbitrary data model represented by content
 * and label provider objects. The <code>getResult</code> method returns all the
 * elements.
 * <p>
 * This class may be instantiated; it is not intended to be subclassed.
 * </p>
 * <p>
 * Example:
 * <pre>
 * DeployListSelectionDialog dlg =
 *   new DeployListSelectionDialog(
 *       getShell(),
 *       input,
 *       new BaseWorkbenchContentProvider(),
 *       new WorkbenchLabelProvider(),
 *       "Select the resources to save:");
 *  dlg.setInitialSelections(dirtyEditors);
 *  dlg.setTitle("Save Resources");
 *  dlg.open();
 * </pre>
 * </p>
 * @noextend This class is not intended to be subclassed by clients.
 */
public class DeployListSelectionDialog extends SelectionDialog{

    // the root element to populate the viewer with
    private Object inputElement;

    // providers for populating this dialog
    private ILabelProvider labelProvider;

    private IStructuredContentProvider contentProvider;

    // the visual selection widget group
    CheckboxTableViewer listViewer;

    // sizing constants
    private final static int SIZING_SELECTION_WIDGET_HEIGHT = 250;

    private final static int SIZING_SELECTION_WIDGET_WIDTH = 300;   


    static String SELECT_ALL_TITLE = WorkbenchMessages.SelectionDialog_selectLabel;

    static String DESELECT_ALL_TITLE = WorkbenchMessages.SelectionDialog_deselectLabel;

    /**
     * Creates a list selection dialog.
     *
     * @param parentShell the parent shell
     * @param input the root element to populate this dialog with
     * @param contentProvider the content provider for navigating the model
     * @param labelProvider the label provider for displaying model elements
     * @param message the message to be displayed at the top of this dialog, or
     *    <code>null</code> to display a default message
     */
    public DeployListSelectionDialog(Shell parentShell, Object input,
            IStructuredContentProvider contentProvider,
            ILabelProvider labelProvider, String message) {
        super(parentShell);
        setTitle(WorkbenchMessages.ListSelection_title);
        inputElement = input;
        this.contentProvider = contentProvider;
        this.labelProvider = labelProvider;
        if (message != null) {
            setMessage(message);
        } else {
            setMessage(WorkbenchMessages.ListSelection_message);
        } 
    }

    /**
     * Visually checks the previously-specified elements in this dialog's list 
     * viewer.
     */
    private void checkInitialSelections() {
        Iterator itemsToCheck = getInitialElementSelections().iterator();

        while (itemsToCheck.hasNext()) {
            listViewer.setChecked(itemsToCheck.next(), true);
        }
        listViewer.setAllGrayed(true);
    }

    /*
     *  (non-Javadoc)
     * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
     */
    protected void configureShell(Shell shell) {
        super.configureShell(shell);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(shell,
                IWorkbenchHelpContextIds.LIST_SELECTION_DIALOG);
    }

    /* (non-Javadoc)
     * Method declared on Dialog.
     */
    protected Control createDialogArea(Composite parent) {
        // page group
        Composite composite = (Composite) super.createDialogArea(parent);
        
        initializeDialogUnits(composite);
        
        createMessageArea(composite);

        listViewer = CheckboxTableViewer.newCheckList(composite, SWT.BORDER);
        GridData data = new GridData(GridData.FILL_BOTH);
        data.heightHint = SIZING_SELECTION_WIDGET_HEIGHT;
        data.widthHint = SIZING_SELECTION_WIDGET_WIDTH;
        listViewer.getTable().setLayoutData(data);

        listViewer.setLabelProvider(labelProvider);
        listViewer.setContentProvider(contentProvider);
        listViewer.addCheckStateListener(new ICheckStateListener() {

            @Override
            public void checkStateChanged(CheckStateChangedEvent event) {
                Object element = event.getElement();
                Object source = event.getSource();
                if (source instanceof CheckboxTableViewer) {
                    CheckboxTableViewer ctv = (CheckboxTableViewer) source;
                    TableItem[] items = ctv.getTable().getItems();
                    if (items != null) {
                        for (TableItem tableItem : items) {
                            tableItem.setChecked(true);
                        }
                    }
                }
            }

        });
        initializeViewer();

        // initialize page
        if (!getInitialElementSelections().isEmpty()) {
            checkInitialSelections();
        }

        Dialog.applyDialogFont(composite);
        
        return composite;
    }

    /**
     * Returns the viewer used to show the list.
     * 
     * @return the viewer, or <code>null</code> if not yet created
     */
    protected CheckboxTableViewer getViewer() {
        return listViewer;
    }

    /**
     * Initializes this dialog's viewer after it has been laid out.
     */
    private void initializeViewer() {
        listViewer.setInput(inputElement);
    }

    /**
     * The <code>ListSelectionDialog</code> implementation of this 
     * <code>Dialog</code> method builds a list of the selected elements for later
     * retrieval by the client and closes this dialog.
     */
    protected void okPressed() {

        // Get the input children.
        Object[] children = contentProvider.getElements(inputElement);

        // Build a list of selected children.
        if (children != null) {
            ArrayList list = new ArrayList();
            for (int i = 0; i < children.length; ++i) {
                Object element = children[i];
                if (listViewer.getChecked(element)) {
                    list.add(element);
                }
            }
            setResult(list);
        }

        super.okPressed();
    }
   
}
