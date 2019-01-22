/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections.internal.attributes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.om.resources.ui.OMResourcesUIActivator;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.resources.ui.components.BaseTableControl;
import com.tibco.xpd.resources.ui.components.XpdToolkit;
import com.tibco.xpd.resources.ui.components.actions.ViewerDeleteAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerMoveDownAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerMoveUpAction;
import com.tibco.xpd.ui.properties.XpdWizardToolkit;

/**
 * Attribute value SET values dialog.
 * 
 * 
 * @author njpatel
 */
/* package */class SetValuesDialog extends Dialog {

    private final String title;

    private final String msg;

    private static final String PREF_ID = "set_value_dialog_size"; //$NON-NLS-1$

    private Text valueTxt;

    private CLabel errorLbl;

    private Button addBtn;

    private SetValuesTable table;

    private final List<ListItem> values;

    /**
     * @param parentShell
     */
    protected SetValuesDialog(Shell parentShell, String title, String msg) {
        super(parentShell);
        this.title = title;
        this.msg = msg;
        values = new ArrayList<ListItem>();
        setShellStyle(SWT.RESIZE | SWT.MAX);
    }

    /**
     * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
     * 
     * @param newShell
     */
    @Override
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        if (title != null) {
            newShell.setText(title);
        }
    }

    /**
     * @see org.eclipse.jface.dialogs.Dialog#getDialogBoundsSettings()
     * 
     * @return
     */
    @Override
    protected IDialogSettings getDialogBoundsSettings() {
        IDialogSettings settings =
                OMResourcesUIActivator.getDefault().getDialogSettings()
                        .getSection(PREF_ID);

        if (settings == null) {
            settings =
                    OMResourcesUIActivator.getDefault().getDialogSettings()
                            .addNewSection(PREF_ID);
        }

        return settings;
    }

    /**
     * @param attrValue
     *            the attrValue to set
     */
    public void setValues(Collection<String> values) {
        this.values.clear();
        if (values != null && !values.isEmpty()) {
            for (String value : values) {
                this.values.add(new ListItem(value));
            }
        }
    }

    /**
     * Get the values set in the dialog.
     * 
     * @return
     */
    public List<String> getValues() {
        List<String> strs = new ArrayList<String>(values.size());
        for (ListItem value : values) {
            strs.add(value.str);
        }
        return strs;
    }

    /**
     * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
     * 
     * @param parent
     */
    @Override
    protected void createButtonsForButtonBar(Composite parent) {
        // create OK and CANCEL button - overriding as we don't want the OK
        // button to be the default button.
        createButton(parent,
                IDialogConstants.OK_ID,
                Messages.SetValuesDialog_ok_button,
                false);
        createButton(parent,
                IDialogConstants.CANCEL_ID,
                Messages.SetValuesDialog_cancel_button,
                false);
    }

    /**
     * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
     * 
     * @param parent
     * @return
     */
    @Override
    protected Control createDialogArea(Composite parent) {
        XpdWizardToolkit toolkit = new XpdWizardToolkit(parent);
        Composite root = (Composite) super.createDialogArea(parent);
        root.setLayout(new GridLayout(2, false));

        Label lbl = toolkit.createLabel(root, msg, SWT.WRAP);
        GridData data = new GridData(SWT.FILL, SWT.FILL, true, false);
        data.widthHint = 300;
        data.horizontalSpan = 2;
        lbl.setLayoutData(data);

        valueTxt = toolkit.createText(root, ""); //$NON-NLS-1$
        valueTxt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

        addBtn =
                toolkit.createButton(root,
                        Messages.SetValuesDialog_add_button,
                        SWT.NONE);

        table = new SetValuesTable(root, toolkit);
        if (values != null) {
            table.setInitialValues(values);
        }
        data = new GridData(SWT.FILL, SWT.FILL, true, true);
        data.widthHint = 300;
        data.heightHint = 150;
        data.horizontalSpan = 2;
        table.setLayoutData(data);

        errorLbl = toolkit.createCLabel(root, ""); //$NON-NLS-1$
        data = new GridData(SWT.FILL, SWT.FILL, true, false);
        data.horizontalSpan = 2;
        errorLbl.setLayoutData(data);

        addBtn.addSelectionListener(new SelectionAdapter() {
            /**
             * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
             * 
             * @param e
             */
            @Override
            public void widgetSelected(SelectionEvent e) {
                addValue(valueTxt.getText().trim());
            }
        });

        valueTxt.addKeyListener(new KeyAdapter() {
            /**
             * @see org.eclipse.swt.events.KeyAdapter#keyPressed(org.eclipse.swt.events.KeyEvent)
             * 
             * @param e
             */
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.keyCode == 13/* enter */) {
                    addValue(valueTxt.getText().trim());
                }
            }
        });

        return root;
    }

    /**
     * Add a value to the table.
     * 
     * @param text
     */
    private void addValue(String text) {
        if (!text.isEmpty()) {
            table.addValue(text);
            valueTxt.setText(""); //$NON-NLS-1$
            valueTxt.setFocus();
        }
    }

    /**
     * Set an error message in this dialog.
     * 
     * @param errorMsg
     */
    private void setError(String errorMsg) {
        if (errorMsg != null && !errorMsg.isEmpty()) {
            errorLbl.setImage(PlatformUI.getWorkbench().getSharedImages()
                    .getImage(ISharedImages.IMG_OBJS_ERROR_TSK));
            errorLbl.setText(errorMsg);
            getButton(IDialogConstants.OK_ID).setEnabled(false);
        } else if (!errorLbl.getText().isEmpty()) {
            errorLbl.setImage(null);
            errorLbl.setText(""); //$NON-NLS-1$
            getButton(IDialogConstants.OK_ID).setEnabled(true);
        }
    }

    /**
     * Set values table.
     * 
     */
    private class SetValuesTable extends BaseTableControl {

        private IContentProvider contentProvider;

        private List<ListItem> values;

        /**
         * @param parent
         * @param toolkit
         */
        public SetValuesTable(Composite parent, XpdToolkit toolkit) {
            super(parent, toolkit);
        }

        /**
         * Set the initial values.
         * 
         * @param values
         */
        public void setInitialValues(List<ListItem> values) {
            if (values != null) {
                this.values = values;
            }

            getTableViewer().setInput(this.values);
        }

        /**
         * Add value to the table.
         * 
         * @param value
         */
        public void addValue(String value) {
            if (value != null && !value.isEmpty()) {
                ListItem item = new ListItem(value);
                values.add(item);
                getViewer().refresh();
                getViewer().setSelection(new StructuredSelection(item));
                validate();
            }
        }

        /**
         * Validate the values in the table.
         */
        private void validate() {
            // If there are duplicate values then show error
            boolean containsDuplicate = false;
            for (ListItem value : values) {
                if (getCaseInsensitive(values, value) > 1) {
                    containsDuplicate = true;
                    break;
                }
            }

            if (containsDuplicate) {
                setError(Messages.SetValuesDialog_listContainsDuplicates_error_shortdesc);
            } else {
                setError(null);
            }
        }

        /**
         * Case insensitive lookup of a list.
         * 
         * @param values
         * @param value
         * @return
         */
        private int getCaseInsensitive(List<ListItem> values, ListItem value) {
            int count = 0;
            for (ListItem item : values) {
                if (item.str.equalsIgnoreCase(value.str)) {
                    ++count;
                }
            }
            return count;
        }

        /**
         * Get the set values.
         * 
         * @return the values
         */
        public List<ListItem> getValues() {
            return values;
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#getViewerContentProvider()
         * 
         * @return
         */
        @Override
        protected IContentProvider getViewerContentProvider() {
            if (contentProvider == null) {
                contentProvider = new ArrayContentProvider();
            }
            return contentProvider;
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#addColumns(org.eclipse.jface.viewers.ColumnViewer)
         * 
         * @param viewer
         */
        @Override
        protected void addColumns(final ColumnViewer viewer) {
            final TextCellEditor ed =
                    new TextCellEditor((Composite) viewer.getControl());

            /*
             * Add value column with editing support.
             */
            TableViewerColumn col =
                    new TableViewerColumn((TableViewer) viewer, SWT.NONE);
            col.getColumn()
                    .setText(Messages.SetValuesDialog_value_column_label);
            col.setLabelProvider(new ColumnLabelProvider());

            col.setEditingSupport(new EditingSupport(viewer) {

                @Override
                protected void setValue(Object element, Object value) {
                    int idx =
                            ((TableViewer) viewer).getTable()
                                    .getSelectionIndex();
                    if (idx >= 0) {
                        ListItem item = values.get(idx);
                        if (!item.str.equals(value)) {
                            item.str = (String) value;
                            viewer.refresh();
                            validate();
                        }
                    }
                }

                @Override
                protected Object getValue(Object element) {
                    if (element instanceof ListItem) {
                        return ((ListItem) element).str;
                    }
                    return null;
                }

                @Override
                protected CellEditor getCellEditor(Object element) {
                    return ed;
                }

                @Override
                protected boolean canEdit(Object element) {
                    return true;
                }
            });

            setColumnProportions(new float[] { 1.0f });
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#createDeleteAction(org.eclipse.jface.viewers.ColumnViewer)
         * 
         * @param viewer
         * @return
         */
        @Override
        protected ViewerDeleteAction createDeleteAction(
                final ColumnViewer viewer) {
            return new ViewerDeleteAction(viewer) {
                /**
                 * @see org.eclipse.jface.action.Action#run()
                 * 
                 */
                @Override
                public void run() {

                    if (selection != null && !selection.isEmpty()) {
                        int selectionIdx = -1;
                        for (Iterator<?> iter = selection.iterator(); iter
                                .hasNext();) {
                            Object next = iter.next();
                            if (selectionIdx < 0) {
                                selectionIdx = values.indexOf(next);
                            }
                            values.remove(next);
                        }

                        viewer.refresh();

                        if (!values.isEmpty()) {
                            if (selectionIdx >= values.size()) {
                                selectionIdx = values.size() - 1;
                            }
                            if (selectionIdx < 0) {
                                selectionIdx = 0;
                            }

                            viewer.setSelection(new StructuredSelection(values
                                    .get(selectionIdx)));
                            validate();
                        }
                    }
                }
            };
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#createMoveUpAction(org.eclipse.jface.viewers.ColumnViewer)
         * 
         * @param viewer
         * @return
         */
        @Override
        protected ViewerMoveUpAction createMoveUpAction(
                final ColumnViewer viewer) {
            return new ViewerMoveUpAction(viewer) {

                /**
                 * @see com.tibco.xpd.resources.ui.components.actions.ViewerMoveUpAction#canMoveUp(org.eclipse.jface.viewers.IStructuredSelection,
                 *      org.eclipse.jface.viewers.StructuredViewer)
                 * 
                 * @param paramIStructuredSelection
                 * @param paramStructuredViewer
                 * @return
                 */
                @Override
                protected boolean canMoveUp(IStructuredSelection selection,
                        StructuredViewer viewer) {
                    if (values != null && selection != null
                            && selection.size() == 1) {
                        int idx = values.indexOf(selection.getFirstElement());
                        return idx > 0;
                    }
                    return false;
                }

                /**
                 * @see org.eclipse.jface.action.Action#run()
                 * 
                 */
                @Override
                public void run() {
                    ListItem value = (ListItem) selection.getFirstElement();
                    int idx = values.indexOf(value);
                    Collections.swap(values, idx - 1, idx);
                    viewer.refresh();
                    viewer.setSelection(selection);
                }
            };
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#createMoveDownAction(org.eclipse.jface.viewers.ColumnViewer)
         * 
         * @param viewer
         * @return
         */
        @Override
        protected ViewerMoveDownAction createMoveDownAction(
                final ColumnViewer viewer) {
            return new ViewerMoveDownAction(viewer) {
                /**
                 * @see com.tibco.xpd.resources.ui.components.actions.ViewerMoveDownAction#canMoveDown(org.eclipse.jface.viewers.IStructuredSelection,
                 *      org.eclipse.jface.viewers.StructuredViewer)
                 * 
                 * @param selection
                 * @param viewer
                 * @return
                 */
                @Override
                protected boolean canMoveDown(IStructuredSelection selection,
                        StructuredViewer viewer) {
                    if (values != null && selection != null
                            && selection.size() == 1) {
                        int idx = values.indexOf(selection.getFirstElement());
                        return idx < values.size() - 1;
                    }
                    return false;
                }

                /**
                 * @see org.eclipse.jface.action.Action#run()
                 * 
                 */
                @Override
                public void run() {
                    ListItem value = (ListItem) selection.getFirstElement();
                    int idx = values.indexOf(value);
                    Collections.swap(values, idx + 1, idx);
                    viewer.refresh();
                    viewer.setSelection(selection);
                }
            };
        }
    }

    /**
     * Represents a row in the Set values table. As the table is for adding
     * arbitrary strings duplicate strings cannot be distinguished so added this
     * wrapper class.
     * 
     */
    private static class ListItem {
        private UUID id;

        private String str;

        public ListItem(String value) {
            this.str = value;
            this.id = UUID.randomUUID();
        }

        /**
         * @see java.lang.Object#equals(java.lang.Object)
         * 
         * @param paramObject
         * @return
         */
        @Override
        public boolean equals(Object paramObject) {
            if (paramObject == this) {
                return true;
            } else if (paramObject instanceof ListItem) {
                ListItem other = (ListItem) paramObject;
                return str.equals(other.str) && id.equals(other.id);
            }
            return super.equals(paramObject);
        }

        /**
         * @see java.lang.Object#toString()
         * 
         * @return
         */
        @Override
        public String toString() {
            return str;
        }
    }

}