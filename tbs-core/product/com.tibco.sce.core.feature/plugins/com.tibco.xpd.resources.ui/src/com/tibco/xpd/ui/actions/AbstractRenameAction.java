/*
 * Copyright (c) TIBCO Software Inc. 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.ui.actions;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.TextActionHandler;
import org.eclipse.ui.actions.WorkspaceAction;

import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.internal.Messages;

/**
 * Generic abstract rename action that displays a text editor control on a view
 * site's tree view and makes that text control the cut/copy/paste aciton
 * handler for the view site (whilst the text control is active).
 * 
 * @author aallway
 * @since 3.3 (21 Jan 2010)
 */
public abstract class AbstractRenameAction extends WorkspaceAction {

    /**
     * The id of this action.
     */
    public static final String ID = XpdResourcesUIActivator.ID
            + ".RenameAction";//$NON-NLS-1$

    /*
     * The tree editing widgets. If treeEditor is null then edit using the
     * dialog. We keep the editorText around so that we can close it if a new
     * selection is made.
     */
    private TreeEditor treeEditor = null;

    private TextActionHandler textActionHandler = null;

    /*
     * OPTIONAL! action bar parent of action - allows accelerator handling of
     * copy/paste in rename text cell editor.
     */
    private IActionBars actionBars = null;

    private Text textEditor = null;

    private Composite textEditorParent = null;

    private IStructuredSelection selection = null;

    private Tree viewerTreeControl;

    private boolean saving = false;

    private String originalName;

    /**
     * Rename action
     * 
     * @param shell
     * @param tree
     */
    public AbstractRenameAction(Shell shell, Tree tree) {
        this(shell, tree, null);
    }

    /**
     * Special constructor of rename action that allows ctrl+c accelerator
     * copy/paste/cut etc.
     * <p>
     * For this the action bar that the action belongs to is required so it can
     * set/unset itself as the default action handler.
     * 
     * @param shell
     * @param tree
     * @param actionBars
     */
    public AbstractRenameAction(Shell shell, Tree tree, IActionBars actionBars) {
        super(shell, Messages.AbstractRenameAction_Rename_menu);
        this.viewerTreeControl = tree;
        this.treeEditor = new TreeEditor(viewerTreeControl);
        setId(ID);
        this.actionBars = actionBars;
    }

    /**
     * @param selectedItem
     * 
     * @return true if the action should be enabled for given object.
     */
    protected abstract boolean canRename(Object selectedItem);

    /**
     * @param selectedItem
     * 
     * @return Get the current name of the object.
     */
    protected abstract String getName(Object selectedItem);

    /**
     * Perform a rename on the given selected item.
     * 
     * @param selectedItem
     *            item to rename
     * @param newName
     *            new name
     */
    protected abstract void doRename(Object selectedItem, String newName);

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.actions.WorkspaceAction#getOperationMessage()
     */
    @Override
    protected String getOperationMessage() {
        return Messages.AbstractRenameAction_Rename_menu;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.actions.WorkspaceAction#invokeOperation(org.eclipse.core
     * .resources.IResource, org.eclipse.core.runtime.IProgressMonitor)
     */
    @Override
    protected void invokeOperation(IResource resource, IProgressMonitor monitor)
            throws CoreException {
        // Do nothing
    }

    /**
     * @return the selection
     */
    protected IStructuredSelection getSelection() {
        return selection;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.actions.WorkspaceAction#updateSelection(org.eclipse.jface
     * .viewers.IStructuredSelection)
     */
    @Override
    protected boolean updateSelection(IStructuredSelection selection) {
        boolean bRet = false;

        disposeTextWidget();

        if (selection != null) {
            // Should only be one item selected
            if (selection.size() == 1) {
                if (canRename(selection.getFirstElement())) {
                    this.selection = selection;
                    bRet = true;
                }
            }
        }

        return bRet;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.actions.WorkspaceAction#run()
     */
    @Override
    public void run() {
        // Only deal with single selection
        if (selection != null && selection.size() == 1) {
            Object selectedItem = selection.getFirstElement();

            // Make sure text editor is created only once. Simply reset text
            // editor when action is executed more than once. Fixes bug 22269.
            if (textEditorParent == null) {
                createTextEditor(selectedItem);
            }

            originalName = getName(selectedItem);
            if (originalName == null) {
                originalName = "";
            }

            textEditor.setText(originalName);

            /* Add Ctrl+c etc handling for text editor */
            addTextActionHandler();

            try {
                // Open text editor with initial size
                textEditorParent.setVisible(true);
                Point textSize =
                        textEditor.computeSize(SWT.DEFAULT, SWT.DEFAULT);
                textSize.x += textSize.y; // Add extra space for new characters.
                Point parentSize = textEditorParent.getSize();
                int inset = getCellEditorInset(textEditorParent);
                textEditor.setBounds(2,
                        inset,
                        Math.min(textSize.x, parentSize.x - 4),
                        parentSize.y - 2 * inset);
                textEditorParent.redraw();
                textEditor.selectAll();
                textEditor.setFocus();

            } catch (Exception e) {
                /*
                 * Ensure we remove text action handler if we fail to set up
                 * control after adding it
                 */
                removeTextActionHandler();
                e.printStackTrace();
            }
        }

        return;
    }

    /**
     * Close the text widget and reset the editorText field.
     */
    protected void disposeTextWidget() {
        if (textEditorParent != null) {
            textEditorParent.dispose();
            textEditorParent = null;
            textEditor = null;
            treeEditor.setEditor(null, null);
        }
    }

    /**
     * Create the text editor widget.
     * 
     * @param resource
     *            the resource to rename
     */
    private void createTextEditor(final Object resource) {
        // Create text editor parent. This draws a nice bounding rect.
        textEditorParent = createParent();
        textEditorParent.setVisible(false);
        final int inset = getCellEditorInset(textEditorParent);
        if (inset > 0) // only register for paint events if we have a border
            textEditorParent.addListener(SWT.Paint, new Listener() {
                @Override
                public void handleEvent(Event e) {
                    Point textSize = textEditor.getSize();
                    Point parentSize = textEditorParent.getSize();
                    e.gc.drawRectangle(0,
                            0,
                            Math.min(textSize.x + 4, parentSize.x - 1),
                            parentSize.y - 1);
                }
            });
        // Create inner text editor.
        textEditor = new Text(textEditorParent, SWT.NONE);
        textEditor.setFont(viewerTreeControl.getFont());
        textEditorParent.setBackground(textEditor.getBackground());
        textEditor.addListener(SWT.Modify, new Listener() {
            @Override
            public void handleEvent(Event e) {
                Point textSize =
                        textEditor.computeSize(SWT.DEFAULT, SWT.DEFAULT);
                textSize.x += textSize.y; // Add extra space for new
                // characters.
                Point parentSize = textEditorParent.getSize();
                textEditor.setBounds(2,
                        inset,
                        Math.min(textSize.x, parentSize.x - 4),
                        parentSize.y - 2 * inset);
                textEditorParent.redraw();
            }
        });
        textEditor.addListener(SWT.Traverse, new Listener() {
            @Override
            public void handleEvent(Event event) {

                // Workaround for Bug 20214 due to extra
                // traverse events
                switch (event.detail) {
                case SWT.TRAVERSE_ESCAPE:
                    // Do nothing in this case
                    disposeTextWidget();
                    event.doit = true;
                    event.detail = SWT.TRAVERSE_NONE;
                    break;
                case SWT.TRAVERSE_RETURN:
                    if (resource instanceof EObject) {
                        saveChangesAndDisposeEObject((EObject) resource);
                    }
                    event.doit = true;
                    event.detail = SWT.TRAVERSE_NONE;
                    break;
                }
            }
        });
        textEditor.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent fe) {
                if (resource instanceof EObject) {
                    saveChangesAndDisposeEObject((EObject) resource);
                }
            }
        });

        textEditor.addListener(SWT.Deactivate, new Listener() {
            @Override
            public void handleEvent(Event event) {
                switch (event.type) {
                case SWT.Deactivate:
                    removeTextActionHandler();
                    break;
                default:
                    break;
                }
            }
        });
        return;
    }

    /**
     * If available add the text control as the ctrl+c/v/x copy/paste action
     * handler for the parent view (action bar)
     */
    private void addTextActionHandler() {
        /*
         * set the text control as the Text copy/paste action handler for this
         * parent view's bar
         */
        if (actionBars != null) {
            textActionHandler = new TextActionHandler(actionBars);
            textActionHandler.addText(textEditor);
        }

        return;
    }

    /**
     * If available remove the text control ctrl+c/v/x copy/paste action handler
     * from the parent view (action bar)
     */
    private void removeTextActionHandler() {
        if (textActionHandler != null) {
            textActionHandler.removeText(textEditor);
            textActionHandler.dispose();
            textActionHandler = null;
        }

        return;
    }

    private Composite createParent() {
        Composite result = new Composite(viewerTreeControl, SWT.NONE);
        TreeItem[] selectedItems = viewerTreeControl.getSelection();
        treeEditor.horizontalAlignment = SWT.LEFT;
        treeEditor.grabHorizontal = true;
        treeEditor.setEditor(result, selectedItems[0]);
        return result;
    }

    /**
     * On Mac the text widget already provides a border when it has focus, so
     * there is no need to draw another one. The value of returned by this
     * method is usd to control the inset we apply to the text field bound's in
     * order to get space for drawing a border. A value of 1 means a one-pixel
     * wide border around the text field. A negative value supresses the border.
     * However, in M9 the system property
     * "org.eclipse.swt.internal.carbon.noFocusRing" has been introduced as a
     * temporary workaround for bug #28842. The existence of the property turns
     * the native focus ring off if the widget is contained in a main window
     * (not dialog). The check for the property should be removed after a final
     * fix for #28842 has been provided.
     */
    private static int getCellEditorInset(Control c) {
        // special case for MacOS X
        if ("carbon".equals(SWT.getPlatform())) { //$NON-NLS-1$
            if (System
                    .getProperty("org.eclipse.swt.internal.carbon.noFocusRing") == null //$NON-NLS-1$
                    || c.getShell().getParent() != null)
                return -2; // native border
        }
        return 1; // one pixel wide black border
    }

    /**
     * Save the changes and dispose of the text widget.
     * 
     * @param resource
     *            - the resource to move.
     */
    private void saveChangesAndDisposeEObject(EObject eObject) {
        if (saving == true)
            return;

        saving = true;
        // Cache the resource to avoid selection loss since a selection of
        // another item can trigger this method
        final EObject theObject = eObject;

        final String newName = textEditor.getText();

        // Run this in an async to make sure that the operation that triggered
        // this action is completed. Otherwise this leads to problems when the
        // icon of the item being renamed is clicked (i.e., which causes the
        // rename
        // text widget to lose focus and trigger this method).
        Runnable query = new Runnable() {
            @Override
            public void run() {
                if (!newName.equals(originalName)) {
                    doRename(theObject, newName);
                }

                // Dispose the text widget regardless
                disposeTextWidget();

                // Ensure the Navigator tree has focus, which it may not if
                // thetext widget previously had focus.
                if (viewerTreeControl != null
                        && !viewerTreeControl.isDisposed()) {
                    viewerTreeControl.setFocus();
                }

                saving = false;
            }

        };
        viewerTreeControl.getShell().getDisplay().asyncExec(query);
    }

}
