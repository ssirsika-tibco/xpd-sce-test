/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections.internal.controls.table.columns;

import java.util.Collections;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.om.core.om.Attribute;
import com.tibco.xpd.om.core.om.AttributeType;
import com.tibco.xpd.om.core.om.OMFactory;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgElementType;
import com.tibco.xpd.om.core.om.OrgTypedElement;
import com.tibco.xpd.om.core.om.QualifiedOrgElement;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.om.resources.ui.properties.sections.general.NamedElementSection;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.general.AttributeSection;
import com.tibco.xpd.resources.ui.components.AbstractColumn;
import com.tibco.xpd.resources.ui.components.AbstractTableControl;
import com.tibco.xpd.resources.ui.components.DialogCellWithClearEditor;
import com.tibco.xpd.resources.ui.components.XpdToolkit;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.properties.XpdWizardToolkit;

/**
 * {@link OrgTypedElement#getType() Type} attribute column of the
 * <code>OrgTypedElement</code> for use in the groups
 * {@link AbstractTableControl table}.
 * 
 * @author njpatel
 * 
 */
public class QualifierColumn extends AbstractColumn {

    private QualifierEditor editor;

    private final boolean isReadOnly;

    /**
     * Type column.
     * 
     * @param editingDomain
     * @param viewer
     *            table viewer
     */
    public QualifierColumn(EditingDomain editingDomain, ColumnViewer viewer) {
        this(editingDomain, viewer, false);
    }

    /**
     * Type column.
     * 
     * @param editingDomain
     * @param viewer
     *            table viewer
     * @param isReadOnly
     *            <code>true</code> if this is a read-only column,
     *            <code>false</code> if editing is allowed.
     */
    public QualifierColumn(EditingDomain editingDomain, ColumnViewer viewer,
            boolean isReadOnly) {
        super(editingDomain, viewer,
                Messages.QualifierColumn_qualifierColumn_title, 150);
        this.isReadOnly = isReadOnly;

        if (!isReadOnly) {
            editor = new QualifierEditor((Composite) viewer.getControl());
        }
        setShowImage(true);
    }

    @Override
    protected CellEditor getCellEditor(Object element) {
        if (!isReadOnly && element instanceof QualifiedOrgElement) {
            editor.setInput((QualifiedOrgElement) element);
            return editor;
        }
        return null;
    }

    @Override
    protected Command getSetValueCommand(Object element, Object value) {
        if (element instanceof QualifiedOrgElement
                && (value == null || value instanceof Attribute)) {
            return SetCommand.create(getEditingDomain(),
                    element,
                    OMPackage.eINSTANCE
                            .getQualifiedOrgElement_QualifierAttribute(),
                    value != null ? value : SetCommand.UNSET_VALUE);
        }
        return null;
    }

    @Override
    protected String getText(Object element) {
        if (element instanceof QualifiedOrgElement) {
            Attribute attr =
                    ((QualifiedOrgElement) element).getQualifierAttribute();
            if (attr != null) {
                if (attr.getType() != null) {
                    return String.format("%s (%s)", attr.getDisplayName(), attr //$NON-NLS-1$
                            .getType().getName());
                }
                return attr.getDisplayName();
            }
        }
        return null;
    }

    @Override
    protected Image getImage(Object element) {
        return null;
    }

    @Override
    protected Object getValueForEditor(Object element) {
        return getText(element);
    }

    /**
     * Create a qualifier
     * 
     * @param input
     * @return
     */
    private Attribute createQualifier(QualifiedOrgElement input) {
        Attribute attr = OMFactory.eINSTANCE.createAttribute();
        attr.setName(Messages.QualifiedOrgElementSection_qualifier_name);
        attr.setDisplayName(attr.getName());
        attr.setType(AttributeType.TEXT);

        // Add qualifier to the element
        getEditingDomain().getCommandStack()
                .execute(SetCommand.create(getEditingDomain(),
                        input,
                        OMPackage.eINSTANCE
                                .getQualifiedOrgElement_QualifierAttribute(),
                        attr));
        return attr;
    }

    /**
     * Editor to show the {@link OrgElementType} picker.
     * 
     * @author njpatel
     * 
     */
    private class QualifierEditor extends DialogCellWithClearEditor {

        private QualifiedOrgElement input;

        private final Composite parent;

        protected QualifierEditor(Composite parent) {
            super(parent);
            this.parent = parent;
        }

        @Override
        protected Object openDialogBox(Control cellEditorWindow) {
            if (input instanceof QualifiedOrgElement) {
                Attribute qualifier = input.getQualifierAttribute();
                if (qualifier == null) {
                    qualifier = createQualifier(input);
                }
                final QualifierDialog dlg =
                        new QualifierDialog(parent.getShell());
                dlg.setInput(qualifier);

                EContentAdapter adapter = new EContentAdapter() {
                    @Override
                    public void notifyChanged(Notification notification) {
                        // Refresh the contents of the dialog
                        if (!notification.isTouch()) {
                            dlg.refresh();
                        }
                    }
                };
                // Add a listener to the qualifier so that we can update the
                // contents of the dialog when some value changes
                qualifier.eAdapters().add(adapter);
                try {
                    dlg.open();
                } finally {
                    qualifier.eAdapters().remove(adapter);
                }

            }
            return null;
        }

        protected void setInput(QualifiedOrgElement input) {
            this.input = input;
        }
    }

    /**
     * Dialog to set the qualifier attribute's properties
     * 
     * @author rgreen
     * 
     */
    private class QualifierDialog extends Dialog {

        private final NamedElementSection nameSection;

        private final AttributeSection attrSection;

        protected QualifierDialog(Shell parentShell) {
            super(parentShell);
            /*
             * This section will be made up of the named element and the
             * attribute section as this, in effect, is an attribute.
             */
            nameSection = new NamedElementSection();
            attrSection = new AttributeSection();
            setShellStyle(SWT.ON_TOP | SWT.APPLICATION_MODAL | SWT.CLOSE
                    | SWT.RESIZE);
        }

        public void refresh() {
            if (nameSection != null && attrSection != null) {
                nameSection.refresh();
                attrSection.refresh();
            }
        }

        @Override
        protected void configureShell(Shell newShell) {
            super.configureShell(newShell);
            newShell.setText(Messages.QualifierColumn_setQualifierPropertiesDlg_title);
        }

        @Override
        protected Control createDialogArea(Composite parent) {
            XpdToolkit toolkit = new XpdWizardToolkit(parent);
            Composite root = toolkit.createComposite(parent);
            FillLayout layout = new FillLayout();
            layout.marginHeight = 5;
            layout.marginWidth = 5;
            root.setLayout(layout);

            Group grp =
                    toolkit.createGroup(root,
                            Messages.QualifierColumn_setQualifierPropertiesGrp_title);
            grp.setLayout(new GridLayout());
            Control content =
                    nameSection.createControls(grp, (XpdFormToolkit) toolkit);
            if (content != null) {
                content.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
                        true));
            }

            content = attrSection.createControls(grp, (XpdFormToolkit) toolkit);
            if (content != null) {
                GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
                data.widthHint = 500;
                data.heightHint = 250;
                content.setLayoutData(data);
            }
            refresh();

            return root;
        }

        public void setInput(Object input) {
            Set<Object> inputCollection = Collections.singleton(input);
            if (nameSection != null) {
                nameSection.setInput(inputCollection);
            }
            if (attrSection != null) {
                attrSection.setInput(inputCollection);
            }
        }

        @Override
        protected void createButtonsForButtonBar(Composite parent) {
            // Only want the OK button
            createButton(parent,
                    IDialogConstants.OK_ID,
                    IDialogConstants.OK_LABEL,
                    true);
        }
    }

}
