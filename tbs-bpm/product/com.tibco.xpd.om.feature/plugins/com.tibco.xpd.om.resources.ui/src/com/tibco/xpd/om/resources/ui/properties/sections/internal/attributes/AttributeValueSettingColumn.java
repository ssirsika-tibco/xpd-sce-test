/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections.internal.attributes;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.ui.celleditor.ExtendedComboBoxCellEditor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.om.core.om.Attribute;
import com.tibco.xpd.om.core.om.AttributeType;
import com.tibco.xpd.om.core.om.AttributeValue;
import com.tibco.xpd.om.core.om.EnumValue;
import com.tibco.xpd.om.resources.ui.internal.Messages;

/**
 * Column for {@link Attribute} value setting. This column should be used in an
 * attribute table where the values of the attributes are set.
 * 
 * @author njpatel
 * 
 */
public abstract class AttributeValueSettingColumn extends AttributeValueColumn {

    private static final Object EMPTY_OBJ = new Object();

    private ExtendedComboBoxCellEditor enumEditor;

    private final Composite root;

    private EnumValueEditor valEditor;

    private SetValuesDialogEditor setEditor;

    /**
     * Column for setting <code>Attribute</code> values.
     * 
     * @param editingDomain
     * @param viewer
     *            column viewer
     * @param label
     *            column label
     * @param width
     *            initial column width
     */
    public AttributeValueSettingColumn(EditingDomain editingDomain,
            ColumnViewer viewer, String label, int width) {
        super(editingDomain, viewer, label, width);
        root = (Composite) viewer.getControl();
        valEditor = new EnumValueEditor(root);
        setEditor = new SetValuesDialogEditor(root);
    }

    /**
     * Get the {@link Command} to remove the given {@link AttributeValue} - in
     * essence setting this attribute value to its default.
     * 
     * @param ed
     *            editing domain
     * @param element
     *            element being edited
     * @param value
     *            <code>AttributeValue</code> to remove
     * @return <code>Command</code> or <code>null</code> if the value cannot be
     *         removed.
     */
    protected abstract Command getRemoveAttributeValueCommand(EditingDomain ed,
            Object element, AttributeValue value);

    /**
     * Get the {@link AttributeValue} from the given input element.
     * 
     * @param element
     *            table row data
     * @return <code>AttributeValue</code> if set, <code>null</code> otherwise.
     */
    protected abstract AttributeValue getAttributeValue(Object element);

    @Override
    protected CellEditor getCellEditor(Object element) {
        Attribute attr = getAttribute(element);
        if (attr != null) {
            if (attr.getType() != null) {
                if (attr.getType() == AttributeType.ENUM) {
                    List<Object> values = new ArrayList<Object>();
                    values.add(EMPTY_OBJ);
                    values.addAll(attr.getValues());
                    enumEditor =
                            new ExtendedComboBoxCellEditor(root, values,
                                    new EnumValueLabelProvider(), SWT.READ_ONLY) {
                                @Override
                                public boolean isDirty() {
                                    // Return true always otherwise the value,
                                    // when
                                    // changed, will not be saved
                                    return true;
                                }
                            };
                    return enumEditor;
                } else if (attr.getType() == AttributeType.ENUM_SET) {
                    valEditor.setElement(element);
                    return valEditor;
                } else if (attr.getType() == AttributeType.SET) {
                    setEditor.setElement(element);
                    return setEditor;
                }
            }
        }
        return super.getCellEditor(element);
    }

    @Override
    protected Object getValueForEditor(Object element) {
        Attribute attr = getAttribute(element);
        if (attr != null) {
            if (attr.getType() == AttributeType.ENUM
                    || attr.getType() == AttributeType.ENUM_SET) {
                AttributeValue value = getAttributeValue(element);
                EList<EnumValue> enumSetValues = null;
                if (value != null) {
                    enumSetValues = value.getEnumSetValues();
                } else {
                    enumSetValues = attr.getDefaultEnumSetValues();
                }

                if (attr.getType() == AttributeType.ENUM) {
                    return enumSetValues != null && !enumSetValues.isEmpty() ? enumSetValues
                            .get(0) : EMPTY_OBJ;
                } else {
                    return enumSetValues;
                }
            } else if (attr.getType() == AttributeType.SET) {
                AttributeValue value = getAttributeValue(element);
                if (value != null) {
                    return value.getSetValues();
                }
            }
        }
        return super.getValueForEditor(element);
    }

    /**
     * Label provider for the ENUM attribute combo.
     * 
     * @author njpatel
     * 
     */
    private class EnumValueLabelProvider extends ColumnLabelProvider {
        @Override
        public String getText(Object element) {
            if (element == EMPTY_OBJ) {
                return ""; //$NON-NLS-1$
            }
            return getModelLabelProvider().getText(element);
        }

        @Override
        public Image getImage(Object element) {
            if (element == EMPTY_OBJ) {
                return null;
            }
            return getModelLabelProvider().getImage(element);
        }
    }

    /**
     * Editor for the ENUMSET attributes - this pops up a enum value selection
     * dialog.
     * 
     * @author njpatel
     * 
     */
    private class EnumValueEditor extends DialogCellEditor {

        private Object element;

        public EnumValueEditor(Composite parent) {
            super(parent);
        }

        /**
         * Set the element being edited.
         * 
         * @param element
         */
        public void setElement(Object element) {
            this.element = element;
        }

        @Override
        protected Object openDialogBox(Control cellEditorWindow) {
            EditingDomain ed = getEditingDomain();
            Object ret = null;
            if (ed != null) {
                Attribute attribute = getAttribute(element);

                if (attribute != null) {
                    EnumValuePopup popup =
                            new EnumValuePopup(cellEditorWindow.getShell(),
                                    attribute);
                    popup.setSelection(getSelection());
                    int dlgRet = popup.open();
                    Command cmd = null;
                    if (dlgRet == EnumValuePopup.OK) {
                        cmd = getSetCommand(element, popup.getSelection());
                        ret = popup.getSelection();
                    } else if (dlgRet == EnumValuePopup.USE_DEFAULT) {
                        AttributeValue value = getAttributeValue(element);
                        if (value != null) {
                            cmd =
                                    getRemoveAttributeValueCommand(ed,
                                            element,
                                            value);
                        }
                    }
                    if (cmd != null) {
                        ed.getCommandStack().execute(cmd);
                        getViewer().refresh();
                    }
                }
            }
            return ret;
        }

        @Override
        protected void updateContents(Object value) {
            super.updateContents(getText(element));
        }

        /**
         * Get the list of values selected in the dialog.
         * 
         * @return
         */
        private List<EnumValue> getSelection() {
            List<EnumValue> selection = null;
            if (element != null) {
                AttributeValue value = getAttributeValue(element);
                if (value != null) {
                    selection = value.getEnumSetValues();
                } else {
                    Attribute attribute = getAttribute(element);
                    if (attribute != null) {
                        // Get the default value
                        selection = attribute.getDefaultEnumSetValues();
                    }
                }
            }
            return selection;
        }

    }

    /**
     * Tree viewer that displays all ENUM values for the given ENUMSET
     * attribute.
     * 
     * @author njpatel
     * 
     */
    private class EnumValuePopup extends Dialog implements ICheckStateListener {

        public static final int USE_DEFAULT = IDialogConstants.CLIENT_ID + 1;

        // private EnumValueTable table;
        private final Attribute attribute;

        private CheckboxTreeViewer viewer;

        private final List<EnumValue> selection;

        protected EnumValuePopup(Shell parentShell, Attribute attribute) {
            super(parentShell);
            this.attribute = attribute;
            setShellStyle(SWT.APPLICATION_MODAL | SWT.TOOL | SWT.CLOSE);
            setBlockOnOpen(true);
            selection = new ArrayList<EnumValue>();
        }

        /**
         * Set the initial selection.
         * 
         * @param selection
         */
        public void setSelection(List<EnumValue> selection) {
            this.selection.clear();
            if (selection != null) {
                this.selection.addAll(selection);
            }
        }

        /**
         * Get the selection from this dialog.
         * 
         * @return
         */
        public List<EnumValue> getSelection() {
            return selection;
        }

        @Override
        protected void configureShell(Shell newShell) {
            super.configureShell(newShell);
            newShell.setText(Messages.AttributeValueSettingColumn_selectValues_dialog_title);
        }

        @Override
        protected Control createDialogArea(Composite parent) {
            Composite root = new Composite(parent, SWT.NONE);
            GridLayout layout = new GridLayout();
            root.setLayout(layout);
            CLabel lbl = new CLabel(root, SWT.NONE);
            GridData data = new GridData(SWT.FILL, SWT.CENTER, false, false);
            data.widthHint = 300;
            lbl.setLayoutData(data);
            String msg =
                    attribute != null ? String
                            .format(Messages.AttributeValueSettingColumn_setValues_dialog_message,
                                    attribute.getDisplayName())
                            : Messages.AttributeValueSettingColumn_setValues_label;
            lbl.setText(shortenText(msg, lbl));
            viewer = new CheckboxTreeViewer(root, SWT.BORDER);
            viewer.setContentProvider(new EnumValueContentProvider());
            viewer.setLabelProvider(getModelLabelProvider());
            viewer.setInput(attribute.getValues());
            viewer.setCheckedElements(selection.toArray());
            data = new GridData(SWT.FILL, SWT.FILL, true, true);
            data.widthHint = 150;
            data.heightHint = 100;
            viewer.getControl().setLayoutData(data);
            viewer.addCheckStateListener(this);
            return root;
        }

        @Override
        protected Control createButtonBar(Composite parent) {
            Composite root = new Composite(parent, SWT.NONE);
            root.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
            GridLayout layout = new GridLayout(2, false);
            root.setLayout(layout);

            Button btn = new Button(root, SWT.NONE);
            btn.setText(Messages.AttributeValueSettingColumn_useDefault_button);
            btn.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false));
            btn.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent e) {
                    setReturnCode(USE_DEFAULT);
                    close();
                }
            });
            btn = new Button(root, SWT.NONE);
            btn.setText(Messages.AttributeValueSettingColumn_ok_button);
            btn.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent e) {
                    setReturnCode(OK);
                    close();
                }
            });

            return root;
        }

        @Override
        protected org.eclipse.swt.graphics.Point getInitialLocation(
                org.eclipse.swt.graphics.Point initialSize) {
            return getShell().getDisplay().getCursorLocation();
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.jface.viewers.ICheckStateListener#checkStateChanged(org
         * .eclipse.jface.viewers.CheckStateChangedEvent)
         */
        @Override
        public void checkStateChanged(CheckStateChangedEvent event) {
            Object element = event.getElement();
            if (element instanceof EnumValue) {
                if (event.getChecked()) {
                    selection.add((EnumValue) element);
                } else {
                    selection.remove(element);
                }
            }
        }
    }

    /**
     * Content provider for the enum set values' dialog.
     * 
     * @author njpatel
     * 
     */
    private class EnumValueContentProvider implements ITreeContentProvider {

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang
         * .Object)
         */
        @Override
        public Object[] getChildren(Object parentElement) {
            return new Object[0];
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang
         * .Object)
         */
        @Override
        public Object getParent(Object element) {
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang
         * .Object)
         */
        @Override
        public boolean hasChildren(Object element) {
            return false;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.jface.viewers.IStructuredContentProvider#getElements(
         * java.lang.Object)
         */
        @Override
        public Object[] getElements(Object inputElement) {
            Object[] elements = null;
            if (inputElement instanceof List<?>) {
                elements = ((List<?>) inputElement).toArray();
            }
            return elements != null ? elements : new Object[0];
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.jface.viewers.IContentProvider#dispose()
         */
        @Override
        public void dispose() {
            // Nothing to do
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse
         * .jface.viewers.Viewer, java.lang.Object, java.lang.Object)
         */
        @Override
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
            // Nothing to do
        }
    }

    private class SetValuesDialogEditor extends DialogCellEditor {

        private Object element;

        /**
         * @param parent
         */
        public SetValuesDialogEditor(Composite parent) {
            super(parent);
        }

        /**
         * @param element
         */
        public void setElement(Object element) {
            this.element = element;
        }

        /**
         * @see org.eclipse.jface.viewers.DialogCellEditor#openDialogBox(org.eclipse.swt.widgets.Control)
         * 
         * @param parent
         * @return
         */
        @SuppressWarnings("unchecked")
        @Override
        protected Object openDialogBox(Control parent) {
            SetValuesDialog dlg =
                    new SetValuesDialog(parent.getShell(),
                            Messages.AttributeValueSettingColumn_setValues_dialog_title,
                            Messages.AttributeValueSettingColumn_setValues_dialog_shortdesc);
            AttributeValue value = getAttributeValue(element);
            if (value != null) {
                dlg.setValues(value.getSetValues());
            }
            if (dlg.open() == Dialog.OK) {
                return dlg.getValues();
            }

            return null;
        }

        @Override
        protected void updateContents(Object value) {
            super.updateContents(getText(element));
        }
    }
}
