/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.dialogs;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.ui.internal.Messages;

/**
 * An UI group that contains a text control and a common navigator view that
 * allows selection of a container. This has been created to replace the default
 * tree in a wizard that displays the default view of the workspace and not the
 * common navigator showing the special folders. This group will be typically
 * used in a new file creation wizard page.
 * 
 * @see WizardNewFileInSpecialFolderCreationPage
 * 
 * @author njpatel
 * 
 */
public class SpecialFolderContainerGroup extends FileSelectionBrowserControl {
    private final boolean allowCreationOfContainer;

    private Text containerText;

    private IPath containerPath;

    private final DelegateListener delegateListener;

    /**
     * Constructor. Special folder container selection group. Use
     * {@link #setInput(Object) setInput} to set the input of the tree (the
     * workspace root will be set as input by default).
     * 
     * @param parent
     *            parent composite that uses <code>GridLayout</code>.
     * @param client
     *            listener that will be informed of events in the controls
     *            contained in this group
     * @param allowCreationOfContainer
     *            set to <code>true</code> to allow creation of new containers
     *            if not present (this will present the user with a text control
     *            above the tree view that allows a path entry.
     */
    public SpecialFolderContainerGroup(Composite parent, Listener client,
            boolean allowCreationOfContainer) {
        this(parent, client, SWT.DEFAULT, SWT.DEFAULT, allowCreationOfContainer);
    }

    /**
     * Constructor. Special folder container selection group. Use
     * {@link #setInput(Object) setInput} to set the input of the tree (the
     * workspace root will be set as input by default).
     * 
     * @param parent
     *            parent composite that uses <code>GridLayout</code>.
     * @param client
     *            listener that will be informed of events in the controls
     *            contained in this group
     * @param widthHint
     *            width hint for the tree view
     * @param heightHint
     *            height hint for the tree view
     * @param allowCreationOfContainer
     *            set to <code>true</code> to allow creation of new containers
     *            if not present (this will present the user with a text control
     *            above the tree view that allows a path entry.
     */
    public SpecialFolderContainerGroup(Composite parent, Listener client,
            int widthHint, int heightHint, boolean allowCreationOfContainer) {
        super(widthHint, heightHint);
        this.delegateListener = new DelegateListener(client);
        setListener(delegateListener);
        this.allowCreationOfContainer = allowCreationOfContainer;

        /*
         * Add filters
         */
        // Only show folders
        addFilter(new ViewerFilter() {

            @Override
            public boolean select(Viewer viewer, Object parentElement,
                    Object element) {
                return element instanceof IContainer
                        || element instanceof SpecialFolder;
            }

        });

        _createControl(parent);
    }

    /**
     * Get the selected container path.
     * 
     * @return Selected path. This can be <code>null</code>.
     */
    public IPath getContainerPath() {
        return containerPath;
    }

    /**
     * Get the current selection. If creation of containers is allowed and a
     * path to a non-existing folder has been input then an <code>IFolder</code>
     * handle will be returned as selection.
     * 
     * @return current selection which would typically be an
     *         <code>IFolder</code> or <code>SpecialFolder</code>. This can be
     *         <code>null</code>.
     */
    public Object getSelection() {
        Object treeSelection = super.getSelection();
        Object selection = null;

        if (treeSelection instanceof SpecialFolder) {
            IFolder folder = ((SpecialFolder) treeSelection).getFolder();

            if (folder != null
                    && folder.getFullPath().equals(getContainerPath())) {
                // Return the special folder
                selection = treeSelection;
            }
        } else if (treeSelection instanceof IContainer
                && ((IContainer) treeSelection).getFullPath()
                        .equals(getContainerPath())) {
            // Return the container
            selection = treeSelection;
        }

        // If selection not defined and there is a container path then assume
        // that this folder does not exist yet
        if (selection == null && getContainerPath() != null) {
            selection =
                    ResourcesPlugin.getWorkspace().getRoot()
                            .getFolder(getContainerPath());
        }

        return selection;
    }

    /**
     * Set the initial selection for the tree. The first item in the selection
     * will be selected.
     * 
     * @param selection
     *            initial selection.
     */
    public void setInitialSelection(IStructuredSelection selection) {
        Object container = null;
        SpecialFolder specialFolder;
        if (selection != null) {
            Object element = selection.getFirstElement();
            IFile file = null;

            if (element instanceof IFile) {
                file = (IFile) element;
            } else if (element instanceof IAdaptable) {
                file = (IFile) ((IAdaptable) element).getAdapter(IFile.class);
            }

            if (file != null) {
                // Get actual parent from the content provider
                IContainer parentIContainer = file.getParent();
                container = parentIContainer;
                if (parentIContainer instanceof IFolder) {
                    ProjectConfig config =
                            XpdResourcesPlugin.getDefault()
                                    .getProjectConfig(parentIContainer
                                            .getProject());
                    specialFolder =
                            config.getSpecialFolders()
                                    .getFolder((IFolder) parentIContainer);
                    if (specialFolder != null) {
                        container = specialFolder;
                    }
                }
            } else {
                container = element;
            }
        }

        // Container can be an IResource or a SpecialFolder
        if (container != null) {
            IContainer folder = null;

            // Get the IContainer of the initial selection
            if (container instanceof IContainer) {
                folder = (IContainer) container;
            } else if (container instanceof IAdaptable) {
                folder =
                        (IContainer) ((IAdaptable) container)
                                .getAdapter(IContainer.class);
            }

            if (folder != null) {
                // Display path in text control
                setContainerPath(folder.getFullPath());
                setContainerText(getContainerPath());
            }

            // Select item in the tree
            super.setInitialSelection(new StructuredSelection(container));
        }
    }

    /**
     * Create the special folder selection control - the text control (if
     * requested) and the tree view. This will assume that the parent composite
     * uses the <code>GridLayout</code> manager.
     * 
     * @param parent
     *            parent <code>Composite</code>.
     */
    protected void _createControl(Composite parent) {
        Composite root = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        root.setLayout(layout);
        root.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        Label lbl = new Label(root, SWT.NONE);

        if (allowCreationOfContainer) {
            lbl
                    .setText(Messages.SpecialFolderContainerGroup_enterOrSelectParentFolder_label);
            containerText = new Text(root, SWT.BORDER);
            containerText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
                    true, false));
        } else {
            lbl
                    .setText(Messages.SpecialFolderContainerGroup_selectParentFolder_label);
        }

        // Create the file browser
        Composite treeControl = super.createControl(root);
        treeControl.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        // Start listening to events
        if (containerText != null) {
            containerText.addListener(SWT.Modify, delegateListener);
        }
    }

    /**
     * Update the container path
     * 
     * @param path
     */
    private void setContainerPath(IPath path) {
        containerPath = path != null ? path.makeAbsolute() : null;
    }

    /**
     * Set the text of the container text control.
     * 
     * @param path
     */
    private void setContainerText(IPath path) {
        if (containerText != null) {
            containerText.removeListener(SWT.Modify, delegateListener);

            containerText.setText(path != null ? path.makeRelative().toString()
                    : ""); //$NON-NLS-1$

            containerText.addListener(SWT.Modify, delegateListener);
        }
    }

    /**
     * Delegate listener that will delegate events to the client listener, and
     * also update the container path when the text control is modified
     * directly.
     * 
     * @author njpatel
     * 
     */
    private class DelegateListener implements Listener {

        private final Listener client;

        public DelegateListener(Listener client) {
            this.client = client;
        }

        public void handleEvent(Event event) {
            if (event.widget == containerText) {
                String text = containerText.getText().trim();
                setContainerPath(text.length() > 0 ? new Path(text) : null);
            } else if (event.widget instanceof Tree) {
                // Update the container text with the selection from the file
                // browser
                TreeItem[] selection = ((Tree) event.widget).getSelection();
                if (selection != null && selection.length > 0) {
                    Object item = selection[0].getData();
                    IContainer container = null;

                    if (item instanceof IContainer) {
                        container = (IContainer) item;
                    } else if (item instanceof IAdaptable) {
                        container =
                                (IContainer) ((IAdaptable) item)
                                        .getAdapter(IContainer.class);
                    }

                    if (container != null) {
                        setContainerPath(container.getFullPath());
                        setContainerText(getContainerPath());
                    }
                }
            }

            if (client != null) {
                client.handleEvent(event);
            }
        }
    }
}
