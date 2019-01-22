/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.dnd.SimpleObjectTransfer;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeItem;

import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;

/**
 * Dialog that allows user to set the order of a given list of arbitrary
 * objects.
 * <p>
 * If Dialog.open() returns SWT.OK then you can use getOrderObjects() to return
 * a copy of the original orderObjects list that is ordered according to the
 * user selection.
 * 
 * @author aallway
 * 
 */
public class SelectObjectOrderDialog extends TitleAreaDialog implements
        ISelectionChangedListener, SelectionListener, ITreeContentProvider {

    private Button moveUp = null;

    private Button moveDown = null;

    private TreeViewer treeViewer = null;

    private List<Object> orderObjects;

    private ILabelProvider labelProvider;

    private Image icon = null;
    
    private String title = Messages.SelectObjectOrderDialog_SetOrderDefault_title;

    public SelectObjectOrderDialog(Shell parentShell, String title,
            List<Object> orderObjects, ILabelProvider labelProvider) {
        super(parentShell);

        this.orderObjects = new ArrayList<Object>();
        for (Object o : orderObjects) {
            this.orderObjects.add(o);
        }

        this.labelProvider = labelProvider;

        this.setShellStyle(this.getShellStyle() | SWT.RESIZE);
        
        this.title = title;
        this.icon = Xpdl2ProcessEditorPlugin.getDefault().getImageRegistry().get(ProcessEditorConstants.IMG_SELORDER_DLGTITLE);
    }

    @Override
    protected Control createContents(Composite parent) {
        
        Control ctrl = super.createContents(parent);
        
        setTitle(title);
        if (icon != null) {
            this.setTitleImage(icon);
        }
        this.setMessage(Messages.SelectObjectOrderDialog_DragDrop_message);
        
        return ctrl;
    }
    
    @Override
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);

        newShell.setText(Messages.SelectObjectOrderDialog_SetOrderDefault_title);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
     */
    protected void createButtonsForButtonBar(Composite parent) {
        // create OK and Cancel buttons by default
        createButton(parent,
                        IDialogConstants.OK_ID,
                        IDialogConstants.OK_LABEL,
                        true);

        createButton(parent,
                        IDialogConstants.CANCEL_ID,
                        IDialogConstants.CANCEL_LABEL,
                        false);
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        Composite root = (Composite) super.createDialogArea(parent);

        Composite composite = new Composite(root, SWT.NONE);
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));
        composite.setLayout(new GridLayout(2, false));

        // Create a list
        treeViewer =
                new TreeViewer(composite, SWT.SINGLE | SWT.BORDER
                        | SWT.H_SCROLL | SWT.V_SCROLL);
        treeViewer.setLabelProvider(labelProvider);
        treeViewer.setContentProvider(this);
        treeViewer.setInput(this);
        GridData listGD = new GridData(GridData.FILL_BOTH);
        listGD.widthHint = 300;
        listGD.heightHint = 200;

        treeViewer.getControl().setLayoutData(listGD);
        treeViewer.setSelection(new StructuredSelection(orderObjects.get(0)));
        treeViewer.addSelectionChangedListener(this);

        treeViewer.addDragSupport(DND.DROP_MOVE,
                new Transfer[] { SelectOrderDragDropTransfer.getInstance() },
                new SelectOrderDragSourceListener());

        treeViewer.addDropSupport(DND.DROP_MOVE,
                new Transfer[] { SelectOrderDragDropTransfer.getInstance() },
                new SelectOrderDropTargetListener());

        // Create 2 buttons to the right of list for move up and move down.
        Composite btnCont = new Composite(composite, SWT.NONE);
        btnCont.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
        GridLayout gridLayout = new GridLayout(1, false);
        gridLayout.marginHeight = 0;
        btnCont.setLayout(gridLayout);

        moveUp = new Button(btnCont, SWT.PUSH);
        moveUp.setText(Messages.SelectObjectOrderDialog_MoveUp_button);
        moveUp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        moveUp.addSelectionListener(this);
        moveUp.setEnabled(false);

        moveDown = new Button(btnCont, SWT.PUSH);
        moveDown.setText(Messages.SelectObjectOrderDialog_MoveDown_button);
        moveDown.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        moveDown.addSelectionListener(this);

        return root;
    }

    public void widgetDefaultSelected(SelectionEvent e) {
    }

    public void widgetSelected(SelectionEvent e) {
        int idx = getSelectionIndex();

        treeViewer.getTree().setRedraw(false);

        if (e.widget == moveUp) {
            if (idx >= 0) {

                Object selObj = orderObjects.get(idx);

                orderObjects.remove(idx);
                orderObjects.add(idx - 1, selObj);

                treeViewer.refresh();
                treeViewer.setSelection(new StructuredSelection(selObj));

            }
        } else if (e.widget == moveDown) {
            if (idx >= 0) {
                treeViewer.remove(orderObjects.toArray());

                Object selObj = orderObjects.get(idx);

                orderObjects.remove(idx);
                orderObjects.add(idx + 1, selObj);

                treeViewer.refresh();
                treeViewer.setSelection(new StructuredSelection(selObj));
            }

        }

        setButtonsFromSelection();

        treeViewer.getTree().setRedraw(true);

    }

    /**
     * Move the given object from one place in list to above the targetObject in
     * list.
     * 
     * @param objectToMove
     * @param targetObject
     */
    private void moveListObject(Object objectToMove, Object targetObject) {
        orderObjects.remove(objectToMove);

        int idx = -1;

        if (targetObject != null) {
            idx = orderObjects.indexOf(targetObject);
        }

        if (idx == -1) {
            // Append to end of list if no target object.
            orderObjects.add(objectToMove);
        } else {
            orderObjects.add(idx, objectToMove);
        }

        treeViewer.getTree().setRedraw(false);
        treeViewer.refresh();
        treeViewer.setSelection(new StructuredSelection(objectToMove));
        treeViewer.getTree().setRedraw(true);
        return;
    }

    private int getSelectionIndex() {
        StructuredSelection sel =
                (StructuredSelection) treeViewer.getSelection();
        Object selObj = sel.getFirstElement();

        int idx = orderObjects.indexOf(selObj);
        return idx;
    }

    private void setButtonsFromSelection() {
        int idx = getSelectionIndex();

        if (idx > 0) {
            moveUp.setEnabled(true);
        } else {
            moveUp.setEnabled(false);
        }

        if (idx < (orderObjects.size() - 1)) {
            moveDown.setEnabled(true);
        } else {
            moveDown.setEnabled(false);
        }
    }

    /**
     * @return the orderObjects
     */
    public List<Object> getOrderObjects() {
        return orderObjects;
    }

    public void selectionChanged(SelectionChangedEvent event) {
        setButtonsFromSelection();

    }

    public Object[] getChildren(Object parentElement) {
        return new Object[0];
    }

    public Object getParent(Object element) {
        return null;
    }

    public boolean hasChildren(Object element) {
        return false;
    }

    public Object[] getElements(Object inputElement) {
        return orderObjects.toArray();
    }

    public void dispose() {
    }

    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }

    /**
     * Drag-Drop transfer object to allow drag-drop re-ordering of objects.
     * 
     * @author aallway
     * 
     */
    private static class SelectOrderDragDropTransfer extends
            SimpleObjectTransfer {
        private static SelectOrderDragDropTransfer INSTANCE =
                new SelectOrderDragDropTransfer();

        private static final String TYPE_NAME = "SelectObjectOrderTransfer"//$NON-NLS-1$
                + System.currentTimeMillis() + ":" + INSTANCE.hashCode();//$NON-NLS-1$

        private static final int TYPEID = registerType(TYPE_NAME);

        public static SelectOrderDragDropTransfer getInstance() {
            return INSTANCE;
        }

        @Override
        protected int[] getTypeIds() {
            return new int[] { TYPEID };
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.swt.dnd.Transfer#getTypeNames()
         */
        @Override
        protected String[] getTypeNames() {
            return new String[] { TYPE_NAME };
        }
    }

    /**
     * Simple drop target listener to allow change order of selected item.
     * 
     * @author aallway
     * 
     */
    private class SelectOrderDragSourceListener implements DragSourceListener {

        public void dragFinished(DragSourceEvent event) {
        }

        public void dragSetData(DragSourceEvent event) {
            event.data = SelectOrderDragDropTransfer.getInstance().getObject();
        }

        public void dragStart(DragSourceEvent event) {
            StructuredSelection sel =
                    (StructuredSelection) treeViewer.getSelection();
            if (sel != null && sel.getFirstElement() != null) {
                SelectOrderDragDropTransfer.getInstance().setObject(sel
                        .getFirstElement());
                event.doit = true;
            } else {
                event.doit = false;
            }
        }
    }

    /**
     * Simple drop target listener to allow change order of selected item.
     * 
     * @author aallway
     * 
     */
    private class SelectOrderDropTargetListener implements DropTargetListener {

        public void dragEnter(DropTargetEvent event) {
        }

        public void dragLeave(DropTargetEvent event) {
        }

        public void dragOperationChanged(DropTargetEvent event) {
            setDropType(event);
        }

        public void dragOver(DropTargetEvent event) {
            setDropType(event);
        }

        public void drop(DropTargetEvent event) {
            Object dropTargetObject = getDropTargetObject(event);

            moveListObject(SelectOrderDragDropTransfer.getInstance()
                    .getObject(), dropTargetObject);
            event.detail = DND.DROP_MOVE;
        }

        public void dropAccept(DropTargetEvent event) {
        }

        private void setDropType(DropTargetEvent event) {
            Object o = getDropTargetObject(event);
            if (o == SelectOrderDragDropTransfer.getInstance().getObject()) {
                event.detail = DND.DROP_NONE;
            } else {
                event.detail = DND.DROP_MOVE;

                if (o != null) {
                    event.feedback = DND.FEEDBACK_INSERT_BEFORE;
                } else {
                    event.feedback = DND.FEEDBACK_INSERT_AFTER;
                }
            }
        }

        private Object getDropTargetObject(DropTargetEvent event) {
            if (event.item instanceof TreeItem) {
                TreeItem item = (TreeItem) event.item;

                return item.getData();
            }
            return null;
        }

    }

}
