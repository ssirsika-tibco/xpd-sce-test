/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.ui.properties.table;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * <p>
 * InternalCustomTableViewer
 * <p>
 * Internal custom table viewer for use by TableViewerWithButtons.
 * <p>
 * This provides support for keyboard access for cell traversal which isn't
 * present in the std SWT table viewer.
 * <p>
 * It also provides 'atuo-new-row' behaviour.
 * 
 * @author Sid
 */
final class InternalCustomTableViewer extends TableViewer {

    private InternalTableActionsListener actionsExecutor;

    /**
     * Create the table viewer with traversal handling.
     * 
     * @param parent
     *            param
     * @param editingActionsListener
     *            param
     * @param style
     *            param
     */
    public InternalCustomTableViewer(XpdFormToolkit toolkit, Composite parent,
            InternalTableActionsListener editingActionsListener, int style) {
        super(toolkit.createTable(parent, style));

        if (editingActionsListener == null) {
            throw new NullPointerException("defaultsProvider cannot be null!"); //$NON-NLS-1$
        }

        this.actionsExecutor = editingActionsListener;

        Table table = this.getTable();

        // Add event consumer and keyboard listener for table.
        final EventConsumer evtConsumer =
                new EventConsumer(this, editingActionsListener);

        table.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                evtConsumer.consume(e);
            }
        });

    }

    /**
     * Set the cell modifier.
     * 
     * @see org.eclipse.jface.viewers.TableViewer#setCellModifier(org.eclipse.jface.viewers.ICellModifier)
     * @param modifier
     *            param
     */
    @Override
    public void setCellModifier(ICellModifier modifier) {
        CustomModifier customModifier =
                new CustomModifier(modifier, this.actionsExecutor, this);
        super.setCellModifier(customModifier);
    }

    @Override
    public void setLabelProvider(IBaseLabelProvider labelProvider) {
        if (!(labelProvider instanceof ITableLabelProvider)) {
            throw new IllegalArgumentException(
                    "LabelProvider have to be derived from ITableLabelProvider. Check javadoc."); //$NON-NLS-1$
        }
        CustomLabelProvider customLabelProvider =
                new CustomLabelProvider((ITableLabelProvider) labelProvider);
        super.setLabelProvider(customLabelProvider);
    }

    @Override
    public void setContentProvider(IContentProvider provider) {
        if (!(provider instanceof IStructuredContentProvider)) {
            throw new IllegalArgumentException(
                    "ContentProvider have to be derived " //$NON-NLS-1$
                            + "from IStructuredContentProvider. Check javadoc."); //$NON-NLS-1$
        }
        CustomTableContentProviderWrapper customContentProvider =
                new CustomTableContentProviderWrapper(
                        (IStructuredContentProvider) provider, actionsExecutor,
                        this);

        super.setContentProvider(customContentProvider);
    }

    @Override
    public void setCellEditors(CellEditor[] editors) {
        if (editors == null) {
            throw new NullPointerException("Cell editors array cannot be null."); //$NON-NLS-1$
        }
        if (editors.length < 1) {
            throw new IllegalArgumentException(
                    "Cell editors array cannot be empty."); //$NON-NLS-1$
        }

        //
        // We now allow no cell-editor on first column so that you can have add
        // btn facility but not a user type-able add row facility.
        // if (editors[0] == null) {
        // throw new IllegalArgumentException(
        // "Cell editors array have to satisfy specific requirements. Check
        // javadoc."); //$NON-NLS-1$
        // }
        // if (!(editors[0] instanceof TextCellEditor)) {
        // throw new IllegalArgumentException(
        // "Cell editors array have to satisfy specific requirements. Check
        // javadoc."); //$NON-NLS-1$
        // }

        if (editors != null && editors.length > 0) {
            for (int i = 0; i < editors.length; i++) {

                if (editors[i] instanceof DialogCellEditor) {
                    DialogCellEditor dce = (DialogCellEditor) editors[i];

                    if (dce.getControl() instanceof Composite) {
                        Control[] children =
                                ((Composite) dce.getControl()).getChildren();

                        if (children != null && children.length > 0) {
                            for (int j = 0; j < children.length; j++) {
                                if (children[j] instanceof Button) {
                                    children[j]
                                            .addTraverseListener(new CellEditorTraverseListener(
                                                    this, editors[i], i));
                                }
                            }
                        }
                    }

                } else {
                    CellEditor cellEditor = editors[i];
                    if (cellEditor != null) {
                        Control control = cellEditor.getControl();
                        if (control != null) {
                            control.addTraverseListener(new CellEditorTraverseListener(
                                    this, editors[i], i));
                        }
                    }
                }

            }
        }

        super.setCellEditors(editors);
    }

    /**
     * Delete the given selection.
     * <p>
     * Asks actionsExecutor to delete actual datya and then updates selection
     * refreshes table etc
     * 
     * @param selection
     */
    public void deleteSelection(IStructuredSelection selection) {

        if (!actionsExecutor.canDeleteRows()) {
            return;
        }

        List<Object> l = new ArrayList<Object>();
        for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
            Object next = iter.next();
            if (next != PotentialNewRowData.INSTANCE) {
                l.add(next);
            }
        }
        if (!l.isEmpty()) {
            int idx = getTable().getSelectionIndex();

            // Find next item to select after delete.
            Object nextSelData = null;

            TableItem[] items = getTable().getItems();
            TableItem selItem = getTable().getItem(idx);

            if (selItem != null) {
                // Check for first item after idx that is not in selection.
                for (int i = idx; i < items.length; i++) {
                    if (!l.contains(items[i].getData())) {
                        nextSelData = items[i].getData();
                        break;
                    }
                }

                if (nextSelData == null) {
                    // If no items after current sel, then look for first before
                    // sel.
                    for (int i = (idx - 1); i >= 0; i--) {
                        if (!l.contains(items[i].getData())) {
                            nextSelData = items[i].getData();
                            break;
                        }
                    }
                }
            }

            IStructuredSelection s = new StructuredSelection(l);
            actionsExecutor.deleteRows(s);
            refresh();

            if (nextSelData != null) {
                setSelection(new StructuredSelection(nextSelData));
            } else {
                setSelection(new StructuredSelection());
            }

        }
    }

    /**
     * Add a new row to the table.
     * <p>
     * The new row is requested to be created by the actions executor is then
     * selected.
     * 
     * @param cellVal
     */
    public Object addNewRow(String cellVal) {
        if (actionsExecutor.canCreateNewRows()) {
            cancelEditing();

            Object newRowData = actionsExecutor.createWithDefaults(cellVal);

            refresh();
            if (newRowData != null) {
                setSelection(new StructuredSelection(newRowData));
            }

            return newRowData;
        }
        return null;
    }

    /**
     * Move row in selection up.
     * 
     * @param selection
     */
    public void moveRowUp(IStructuredSelection selection) {
        if (actionsExecutor.canMoveRowUp()) {
            Object rowData = selection.getFirstElement();

            if (rowData != null) {
                if (indexForElement(rowData) > 0) {
                    actionsExecutor.moveRowUp(rowData);

                    setSelection(new StructuredSelection(rowData));
                }
            }
        }
    }

    public boolean canMoveSelectionUp(IStructuredSelection selection) {
        Object rowData = selection.getFirstElement();

        if (rowData != null && !(rowData instanceof PotentialNewRowData)) {
            TableItem[] items = getTable().getItems();

            int idx;
            for (idx = 0; idx < items.length; idx++) {
                if (items[idx].getData() == rowData) {
                    break;
                }
            }

            if (idx > 0) {
                return true;
            }
        }
        return false;
    }

    public boolean canMoveSelectionDown(IStructuredSelection selection) {
        Object rowData = selection.getFirstElement();

        if (rowData != null && !(rowData instanceof PotentialNewRowData)) {
            TableItem[] items = getTable().getItems();

            int numitems = items.length;

            // Don't allow move below last row if last row is potential new row
            if (numitems > 0
                    && (items[numitems - 1].getData() instanceof PotentialNewRowData)) {
                numitems--;
            }

            int idx;
            for (idx = 0; idx < items.length; idx++) {
                if (items[idx].getData() == rowData) {
                    break;
                }
            }

            if (idx < (numitems - 1)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Move row in selection down.
     * 
     * @param selection
     */
    public void moveRowDown(IStructuredSelection selection) {
        if (actionsExecutor.canMoveRowDown()) {
            if (canMoveSelectionDown(selection)) {
                Object rowData = selection.getFirstElement();

                if (rowData != null) {
                    actionsExecutor.moveRowDown(rowData);

                    setSelection(new StructuredSelection(rowData));
                }
            }
        }
    }

    /**
     * Handle traversal (left right, tab etc) for table cell editors.
     * 
     * @author aallway
     */
    private class CellEditorTraverseListener implements TraverseListener {

        private InternalCustomTableViewer tableViewer = null;

        private int columnIdx;

        public CellEditorTraverseListener(
                InternalCustomTableViewer tableViewer, CellEditor cellEditor,
                int columnIdx) {
            this.tableViewer = tableViewer;
            this.columnIdx = columnIdx;

        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.swt.events.TraverseListener#keyTraversed(org.eclipse.
         * swt.events.TraverseEvent)
         */
        public void keyTraversed(TraverseEvent e) {
            int numCols = tableViewer.getTable().getColumnCount();
            int numRows = tableViewer.getTable().getItemCount();

            TableItem[] items = tableViewer.getTable().getItems();
            int selRow = tableViewer.getTable().getSelectionIndex();

            int traverseType = e.detail;
            boolean atStartOfText = false;
            boolean atEndOfText = false;

            String cellVal = ""; //$NON-NLS-1$

            // If this is a text field, ignore traverse unless we are on the
            // last / first character
            if (e.widget instanceof Text) {
                Text txt = (Text) e.widget;
                cellVal = txt.getText();

                int loc = txt.getCaretPosition();

                if (loc >= txt.getText().length()) {
                    atEndOfText = true;
                }

                // right arrow only stesp to next cell when cursor at end.
                if (traverseType == SWT.TRAVERSE_ARROW_NEXT
                        && e.keyCode == SWT.ARROW_RIGHT && !atEndOfText) {
                    traverseType = SWT.TRAVERSE_NONE;
                }

                if (loc == 0) {
                    atStartOfText = true;
                }

                // left arrow only stesp to prev cell when cursor at start.
                if (traverseType == SWT.TRAVERSE_ARROW_PREVIOUS
                        && e.keyCode == SWT.ARROW_LEFT && !atStartOfText) {
                    traverseType = SWT.TRAVERSE_NONE;
                }

            } else if (e.widget instanceof CCombo) {
                CCombo cc = (CCombo) e.widget;

                int index = cc.getSelectionIndex();
                if (index >= 0) {
                    cellVal = cc.getItem(index);
                }

                if (traverseType == SWT.TRAVERSE_ARROW_PREVIOUS
                        || traverseType == SWT.TRAVERSE_ARROW_NEXT) {
                    traverseType = SWT.TRAVERSE_NONE;
                }
            }

            // Treat left and right arrow as SHIFT-TAB / TAB
            if (traverseType == SWT.TRAVERSE_ARROW_PREVIOUS
                    && e.keyCode == SWT.ARROW_LEFT) {
                traverseType = SWT.TRAVERSE_TAB_PREVIOUS;
            } else if (traverseType == SWT.TRAVERSE_ARROW_NEXT
                    && e.keyCode == SWT.ARROW_RIGHT) {
                traverseType = SWT.TRAVERSE_TAB_NEXT;
            }

            if (items.length >= 1 && selRow >= 0) {
                Object selObject = items[selRow].getData();

                //
                // TAB = SELECT NEXT CELL IN ROW (OR 1ST OF NEXT ROW)
                if (traverseType == SWT.TRAVERSE_TAB_NEXT) {

                    if (columnIdx < (numCols - 1)) {
                        // If not on last column then activate editor for next
                        // column.
                        if (actionsExecutor.canCreateNewRows()
                                && selObject == PotentialNewRowData.INSTANCE
                                && columnIdx == 0) {
                            final int colIdx = columnIdx;

                            // We are stepping off the first element in a new
                            // row. Therefore we have to ask the defaults
                            // provider to create a new row.

                            Object newRowData = addNewRow(cellVal);

                            if (newRowData != null) {
                                tableViewer.editElement(newRowData, colIdx + 1);
                            }

                        } else {
                            tableViewer.editElement(selObject, columnIdx + 1);
                        }

                    } else {
                        // If on last column select next row.
                        if (selRow < (numRows - 1)) {
                            tableViewer.getTable().setSelection(selRow + 1);

                            tableViewer
                                    .editElement(items[selRow + 1].getData(), 0);
                        }
                    }
                }
                //
                // SHIFT-TAB = SELECT PREVIOUS CELL IN CURRENT ROW OR LAST ON
                // PREVIOUS ROW.
                else if (traverseType == SWT.TRAVERSE_TAB_PREVIOUS) {
                    if (columnIdx > 0) {
                        tableViewer.editElement(selObject, columnIdx - 1);

                    } else {
                        if (selRow > 0) {
                            //
                            // If reversing off of the dummy new row at end then
                            // cancel editing it.
                            if (selObject == PotentialNewRowData.INSTANCE) {
                                tableViewer.cancelEditing();
                            }
                            tableViewer.getTable().setSelection(selRow - 1);

                            tableViewer
                                    .editElement(items[selRow - 1].getData(),
                                            numCols - 1);
                        }

                    }
                }
                //
                // TRAVERSE TO CELL BELOW CURRENT.
                else if (traverseType == SWT.TRAVERSE_ARROW_NEXT) {
                    if (selRow < (numRows - 1)) {
                        boolean canDo = false;

                        if (e.widget instanceof Text) {
                            Text txt = (Text) e.widget;

                            int curLine = txt.getCaretLineNumber();
                            if (curLine == (txt.getLineCount() - 1)) {
                                canDo = true;
                            }

                        } else {
                            canDo = true;
                        }

                        // Don't allow jump down onto special. creation row
                        // uynless we're on start column
                        if (canDo
                                && (columnIdx == 0 || items[selRow + 1]
                                        .getData() != PotentialNewRowData.INSTANCE)) {
                            tableViewer.getTable().setSelection(selRow + 1);

                            tableViewer
                                    .editElement(items[selRow + 1].getData(),
                                            columnIdx);

                        }
                    }
                }
                //
                // TRAVERSE TO CELL ABOVE CURRENT.
                else if (traverseType == SWT.TRAVERSE_ARROW_PREVIOUS) {
                    if (selRow > 0) {
                        boolean canDo = false;

                        if (e.widget instanceof Text) {
                            Text txt = (Text) e.widget;

                            int curLine = txt.getCaretLineNumber();
                            if (curLine == 0) {
                                canDo = true;
                            }
                        } else {
                            canDo = true;
                        }

                        if (canDo) {
                            // If reversing off of the dummy new row at end then
                            // cancel editing it.
                            if (selObject == PotentialNewRowData.INSTANCE) {
                                tableViewer.cancelEditing();
                            }

                            tableViewer.getTable().setSelection(selRow - 1);

                            tableViewer
                                    .editElement(items[selRow - 1].getData(),
                                            columnIdx);
                        }

                    }
                }

            }

        }

    }

    /**
     * Consumer for Delete / CR events.
     */
    class EventConsumer {

        /**
         * TODO comment me.
         */
        private final Table table;

        /**
         * TODO comment me.
         */
        private final TableViewer tableViewer;

        /**
         * TODO comment me.
         * 
         * @param tableViewer
         *            param
         * @param actionsExecutor
         *            param
         */
        public EventConsumer(TableViewer tableViewer,
                InternalTableActionsListener actionsExecutor) {
            this.tableViewer = tableViewer;
            this.table = tableViewer.getTable();
        }

        /**
         * TODO comment me.
         * 
         * @param e
         *            param
         */
        public void consume(KeyEvent e) {

            IStructuredSelection selection =
                    (IStructuredSelection) this.tableViewer.getSelection();
            if (e.keyCode == SWT.DEL) {

                deleteSelection(selection);

            } else if (e.keyCode == SWT.CR) {
                TableItem[] items = this.table.getSelection();
                if (items.length >= 1) {
                    this.tableViewer.editElement(items[0].getData(), 0);
                }
            } else if (e.stateMask == SWT.ALT) {
                if (e.keyCode == SWT.ARROW_UP) {
                    moveRowUp((StructuredSelection) getSelection());
                } else if (e.keyCode == SWT.ARROW_DOWN) {
                    moveRowDown((StructuredSelection) getSelection());
                }
            }

        }

    }

}