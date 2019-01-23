package com.tibco.xpd.om.resources.ui.properties.sections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.PageBook;

import com.tibco.xpd.om.core.om.Attribute;
import com.tibco.xpd.om.core.om.AttributeType;
import com.tibco.xpd.om.core.om.AttributeValue;
import com.tibco.xpd.om.core.om.EnumValue;
import com.tibco.xpd.om.core.om.OMFactory;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgElementType;
import com.tibco.xpd.om.core.om.OrgTypedElement;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.attributes.AttributeValueSettingColumn;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.attributes.AttributesTableSection;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.controls.table.columns.LabelColumn;
import com.tibco.xpd.resources.ui.components.ViewerAction;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Section for {@link OrgTypedElement}s to set the values of {@link Attribute}s
 * of it's corresponding type.
 * 
 * @author njpatel
 * 
 */
public class AttributeValuesSection extends AttributesTableSection implements
        IFilter {

    private Font tableHeaderFont;

    private PageBook book;

    private Composite noAttributePage;

    private Control attributeTablePage;

    private Control currentPage;

    private Label noAttributeLabel;

    @Override
    protected Collection<ViewerAction> getActions(TableViewer viewer) {
        ViewerAction action = new ResetToDefaultAction(viewer);
        return Collections.singleton(action);
    }

    @Override
    protected void addColumns(TableViewer viewer) {
        new LabelColumn(getEditingDomain(), viewer, true);
        new AttributeValueColumnImpl(getEditingDomain(), viewer);
    }

    @Override
    public void dispose() {
        if (tableHeaderFont != null) {
            tableHeaderFont.dispose();
        }
        super.dispose();
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        /*
         * Register 2 pages in a page book - one to show a message when no
         * attributes are defined in the selection type and the other to show a
         * table of attributes.
         */
        book = new PageBook(parent, SWT.NONE);
        book.setBackground(ColorConstants.red);
        toolkit.adapt(book);

        noAttributePage = toolkit.createComposite(book);
        noAttributePage.setLayout(new GridLayout());
        noAttributeLabel =
                toolkit.createLabel(noAttributePage,
                        Messages.AttributesSettingSection_selectedTypeHasNoAttributes_label);
        GridData data = new GridData(SWT.FILL, SWT.TOP, true, false);
        data.horizontalIndent = 5;
        noAttributeLabel.setLayoutData(data);
        noAttributeLabel.setForeground(ColorConstants.darkGray);
        tableHeaderFont = new Font(noAttributeLabel.getDisplay(), "Arial", 10, //$NON-NLS-1$
                SWT.BOLD);
        noAttributeLabel.setFont(tableHeaderFont);

        attributeTablePage = super.doCreateControls(book, toolkit);
        currentPage = noAttributePage;
        book.showPage(currentPage);
        return book;
    }

    /**
     * Update the no attributes message.
     * 
     * @param msg
     */
    private void updateNoAttributeMessage(String msg) {
        if (noAttributeLabel != null && !noAttributeLabel.isDisposed()) {
            noAttributeLabel.setText(msg);
        }
    }

    @Override
    protected void doRefresh() {
        EObject input = getInput();

        if (input instanceof OrgTypedElement) {
            OrgTypedElement elem = (OrgTypedElement) input;

            if (elem.getType() != null) {
                EList<Attribute> attributes = elem.getType().getAttributes();

                setElements(attributes);

                if (book != null && !book.isDisposed()) {
                    if (attributes != null && !attributes.isEmpty()) {
                        if (currentPage != attributeTablePage) {
                            currentPage = attributeTablePage;
                            book.showPage(currentPage);
                        }
                    } else {
                        if (currentPage != noAttributePage) {
                            updateNoAttributeMessage(String
                                    .format(Messages.AttributesSettingSection_typeHasNoAttributes_message,
                                            elem.getType().getDisplayName()));
                            currentPage = noAttributePage;
                            book.showPage(currentPage);
                        }
                    }
                }
            }
        }

    }

    /**
     * Get the value of the given attribute for this {@link OrgTypedElement}.
     * 
     * @param attr
     * @return value or <code>null</code> if not set.
     */
    private AttributeValue getAttributeValue(Attribute attr) {
        EObject input = getInput();
        if (input instanceof OrgTypedElement && attr != null) {
            EList<AttributeValue> values =
                    ((OrgTypedElement) input).getAttributeValues();

            if (values != null) {
                for (AttributeValue value : values) {
                    if (attr.equals(value.getAttribute())) {
                        return value;
                    }
                }
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     */
    @Override
    public boolean select(Object toTest) {
        EObject input = resollveInput(toTest);
        // Only show this section if type is defined and the type has attributes
        if (input instanceof OrgTypedElement) {
            OrgElementType type = ((OrgTypedElement) input).getType();

            return type != null;
        }
        return false;
    }

    /**
     * Get the Command to remove the given attribute values.
     * 
     * @param ed
     * @param values
     * @return
     */
    private Command getRemoveAttributeValuesCommand(EditingDomain ed,
            List<AttributeValue> values) {
        EObject input = getInput();

        if (ed != null && values != null && !values.isEmpty()
                && input instanceof OrgTypedElement) {
            return RemoveCommand.create(ed,
                    input,
                    OMPackage.eINSTANCE.getOrgTypedElement_AttributeValues(),
                    values);
        }
        return null;
    }

    /**
     * Column to set the attribute values.
     * 
     * @author njpatel
     * 
     */
    private class AttributeValueColumnImpl extends AttributeValueSettingColumn {

        public AttributeValueColumnImpl(EditingDomain editingDomain,
                TableViewer viewer) {
            super(editingDomain, viewer,
                    Messages.AttributesSettingSection_valueColumn_title, 200);
        }

        @Override
        protected AttributeValue getAttributeValue(Object element) {
            if (element instanceof Attribute) {
                return AttributeValuesSection.this
                        .getAttributeValue((Attribute) element);
            }
            return null;
        }

        @Override
        protected Command getRemoveAttributeValueCommand(EditingDomain ed,
                Object element, AttributeValue value) {
            return AttributeValuesSection.this
                    .getRemoveAttributeValuesCommand(ed,
                            Collections.singletonList(value));
        }

        @Override
        protected Command getSetCommand(Object element, Object value) {
            Command cmd = null;
            EObject input = getInput();
            EditingDomain ed = getEditingDomain();

            /*
             * If value is null then set to empty string - this will indicate
             * that the user wishes to unset the value and not use the default
             * value
             */
            if (value == null) {
                value = ""; //$NON-NLS-1$
            }

            if (input instanceof OrgTypedElement
                    && element instanceof Attribute
                    && ((Attribute) element).getType() != null) {
                Attribute attr = (Attribute) element;
                AttributeValue attrValue = getAttributeValue(attr);
                if (attrValue != null) {
                    if (attr.getType() == AttributeType.ENUM
                            || attr.getType() == AttributeType.ENUM_SET
                            || attr.getType() == AttributeType.SET) {
                        Collection<?> enumValues = null;

                        if (value instanceof EnumValue) {
                            enumValues = Collections.singletonList(value);
                        } else if (value instanceof Collection<?>) {
                            enumValues = (Collection<?>) value;
                        } else {
                            enumValues = Collections.EMPTY_LIST;
                        }

                        CompoundCommand ccmd = new CompoundCommand();
                        ccmd.append(SetCommand.create(ed,
                                attrValue,
                                OMPackage.eINSTANCE.getAttributeValue_Value(),
                                SetCommand.UNSET_VALUE));
                        ccmd.append(SetCommand.create(ed,
                                attrValue,
                                attr.getType() == AttributeType.SET ? OMPackage.eINSTANCE
                                        .getAttributeValue_SetValues()
                                        : OMPackage.eINSTANCE
                                                .getAttributeValue_EnumSetValues(),
                                enumValues));
                        cmd = ccmd;
                    } else if (attr.getType() == AttributeType.SET) {
                        Collection<?> setValues = null;
                        if (value instanceof String) {
                            setValues = Collections.singleton(value);
                        }
                        if (value instanceof Collection<?>) {
                            setValues = (Collection<?>) value;
                        } else {
                            setValues = Collections.EMPTY_LIST;
                        }

                        CompoundCommand ccmd = new CompoundCommand();
                        ccmd.append(SetCommand.create(ed,
                                attrValue,
                                OMPackage.eINSTANCE.getAttributeValue_Value(),
                                SetCommand.UNSET_VALUE));
                        ccmd.append(SetCommand.create(ed,
                                attrValue,
                                OMPackage.eINSTANCE
                                        .getAttributeValue_SetValues(),
                                setValues));
                        cmd = ccmd;

                    } else {
                        cmd =
                                SetCommand.create(ed,
                                        attrValue,
                                        OMPackage.eINSTANCE
                                                .getAttributeValue_Value(),
                                        value);
                    }
                } else {
                    attrValue = OMFactory.eINSTANCE.createAttributeValue();
                    attrValue.setAttribute(attr);
                    if (attr.getType() == AttributeType.ENUM
                            || attr.getType() == AttributeType.ENUM_SET
                            || attr.getType() == AttributeType.SET) {
                        Collection<?> enumValues = null;

                        if (value instanceof EnumValue) {
                            enumValues = Collections.singletonList(value);
                        } else if (value instanceof Collection<?>) {
                            enumValues = (Collection<?>) value;
                        } else {
                            enumValues = Collections.EMPTY_LIST;
                        }
                        attrValue
                                .eSet(attr.getType() == AttributeType.SET ? OMPackage.eINSTANCE
                                        .getAttributeValue_SetValues()
                                        : OMPackage.eINSTANCE
                                                .getAttributeValue_EnumSetValues(),
                                        enumValues);
                    } else if (value instanceof String) {
                        attrValue.setValue((String) value);
                    }
                    cmd =
                            AddCommand
                                    .create(ed,
                                            input,
                                            OMPackage.eINSTANCE
                                                    .getOrgTypedElement_AttributeValues(),
                                            attrValue);
                }
            }

            return cmd;
        }

        @Override
        protected Object getValue(Object element) {
            Attribute attr = getAttribute(element);
            if (attr != null && attr.getType() != null) {
                AttributeValue value = getAttributeValue(attr);
                if (value != null) {
                    switch (attr.getType()) {
                    case ENUM:
                    case ENUM_SET:
                        return value.getEnumSetValues();
                    case SET:
                        return value.getSetValues();
                    default:
                        return value.getValue();
                    }
                }
            }
            return null;
        }
    }

    /**
     * Action to reset the selected attribute values to their default values.
     * 
     * @author njpatel
     * 
     */
    private class ResetToDefaultAction extends ViewerAction {

        public ResetToDefaultAction(StructuredViewer viewer) {
            super(viewer, Messages.AttributesSettingSection_setDefaults_action,
                    PlatformUI.getWorkbench().getSharedImages()
                            .getImageDescriptor(ISharedImages.IMG_TOOL_UNDO));
            setEnabled(false);
            setToolTipText(Messages.AttributesSettingSection_setDefaults_action_tooltip);
            setAccelerator(SWT.CTRL | 'r');
        }

        @Override
        public void selectionChanged(IStructuredSelection selection) {
            super.selectionChanged(selection);
            setEnabled(selection != null && !selection.isEmpty());
        }

        @Override
        public void run() {
            IStructuredSelection selection =
                    (IStructuredSelection) getViewer().getSelection();
            EditingDomain ed = getEditingDomain();
            if (ed != null && selection != null && !selection.isEmpty()) {
                // Cancel editing of a cell before resetting to default
                if (getViewer() instanceof ColumnViewer) {
                    ((ColumnViewer) getViewer()).cancelEditing();
                }

                List<AttributeValue> valuesToDelete =
                        new ArrayList<AttributeValue>();

                for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
                    Object next = iter.next();

                    if (next instanceof Attribute) {
                        AttributeValue attrValue =
                                getAttributeValue((Attribute) next);

                        if (attrValue != null) {
                            valuesToDelete.add(attrValue);
                        }
                    }
                }

                if (!valuesToDelete.isEmpty()) {
                    Command cmd =
                            getRemoveAttributeValuesCommand(ed, valuesToDelete);
                    if (cmd != null) {
                        ed.getCommandStack().execute(cmd);
                    }
                }
            }
        }
    }
}
