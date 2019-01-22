/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.om.resources.ui.properties.sections.organization;

import java.util.Set;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import com.tibco.xpd.om.core.om.DynamicOrgIdentifier;
import com.tibco.xpd.om.core.om.OMFactory;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.om.resources.ui.properties.sections.general.NamedElementSection;
import com.tibco.xpd.resources.ui.components.AbstractColumn;
import com.tibco.xpd.resources.ui.components.BaseTableControl;
import com.tibco.xpd.resources.ui.components.XpdToolkit;
import com.tibco.xpd.resources.ui.components.actions.TableAddAction;
import com.tibco.xpd.resources.ui.components.actions.TableDeleteAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerAddAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerDeleteAction;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.properties.AbstractTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Properties section for the Dynamic Organization Identifiers tab.
 * 
 * @author njpatel
 * @since 25 Sep 2013
 */
public class DynamicOrgIdentifiersSection extends AbstractTransactionalSection
        implements IFilter {

    private static final String HEADING =
            Messages.DynamicOrgIdentifiersSection_heading;

    private static final String IDENTIFIER_NAME_PATTERN =
            Messages.DynamicOrgIdentifiersSection_defaultIdentNamePattern_label;

    private Font tableHeaderFont;

    private DynamicOrgIdentifiersTable identTable;

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {
        if (identTable != null
                && !identTable.getTableViewer().getControl().isDisposed()) {
            EObject input = getInput();
            Object tableInput = null;
            if (input instanceof Organization) {
                Organization org = (Organization) input;

                if (org.isDynamic()) {
                    tableInput = org;
                }
            }

            identTable.getTableViewer().cancelEditing();
            identTable.getTableViewer().setInput(tableInput);
        }
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doCreateControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param parent
     * @param toolkit
     * @return
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = toolkit.createComposite(parent);
        root.setLayout(new GridLayout());
        Label label = toolkit.createLabel(root, HEADING);
        GridData data = new GridData(SWT.FILL, SWT.TOP, true, false);
        data.horizontalIndent = 5;
        label.setLayoutData(data);
        label.setForeground(ColorConstants.darkGray);
        tableHeaderFont = new Font(root.getDisplay(), "Arial", 10, //$NON-NLS-1$
                SWT.BOLD);
        label.setFont(tableHeaderFont);
        identTable = new DynamicOrgIdentifiersTable(root, toolkit);
        data = new GridData(SWT.FILL, SWT.FILL, true, true);
        data.widthHint = 200;
        data.heightHint = 150;
        identTable.setLayoutData(data);

        return root;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    protected Command doGetCommand(Object obj) {
        // Managed by the table.
        return null;
    }

    /**
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     * 
     * @param toTest
     * @return
     */
    @Override
    public boolean select(Object toTest) {
        /*
         * Only show for Dynamic Organization.
         */
        EObject input = resollveInput(toTest);
        return input instanceof Organization
                && ((Organization) input).isDynamic();
    }

    @Override
    protected EObject resollveInput(Object object) {
        if (object instanceof EditPart) {
            return (EObject) ((EditPart) object).getAdapter(EObject.class);
        }

        return super.resollveInput(object);
    }

    /**
     * Dynamic Organization Identifiers table.
     * 
     */
    private class DynamicOrgIdentifiersTable extends BaseTableControl {

        /**
         * @param parent
         * @param toolkit
         */
        public DynamicOrgIdentifiersTable(Composite parent, XpdToolkit toolkit) {
            super(parent, toolkit);
            setColumnProportions(new float[] { 0.5f, 0.5f });
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#addColumns(org.eclipse.jface.viewers.ColumnViewer)
         * 
         * @param viewer
         */
        @Override
        protected void addColumns(ColumnViewer viewer) {
            IdentColumn labelCol =
                    new IdentColumn(
                            getEditingDomain(),
                            viewer,
                            SWT.NONE,
                            Messages.DynamicOrgIdentifiersSection_labelColumn_label,
                            250, OMPackage.eINSTANCE
                                    .getNamedElement_DisplayName()) {

                        @Override
                        protected Command getSetValueCommand(Object element,
                                Object value) {
                            return DynamicOrgIdentifiersTable.this
                                    .getSetValueCommand((DynamicOrgIdentifier) element,
                                            value,
                                            getFeature());
                        }

                    };
            labelCol.setShowImage(true);

            new IdentColumn(getEditingDomain(), viewer, SWT.NONE,
                    Messages.DynamicOrgIdentifiersSection_nameColumn_label,
                    250, OMPackage.eINSTANCE.getNamedElement_Name()) {

                @Override
                protected Command getSetValueCommand(Object element,
                        Object value) {
                    return DynamicOrgIdentifiersTable.this
                            .getSetValueCommand((DynamicOrgIdentifier) element,
                                    value,
                                    getFeature());
                }

            };
        }

        /**
         * Set the value for the given feature.
         * 
         * @param ident
         * @param value
         * @param feature
         * @return
         */
        private Command getSetValueCommand(DynamicOrgIdentifier ident,
                Object value, EStructuralFeature feature) {
            if (value instanceof String && !((String) value).trim().isEmpty()) {

                // If the value being set hasn't actually changed then do
                // nothing
                if (value.equals(ident.eGet(feature))) {
                    return null;
                }

                /*
                 * When updating the label then ensure that the name is in sync
                 * if not set by the user.
                 */
                CompoundCommand cmd = new CompoundCommand();
                String newValue = ((String) value).trim();

                // Update the feature being changed
                cmd.append(SetCommand.create(getEditingDomain(),
                        ident,
                        feature,
                        newValue));

                if (feature == OMPackage.eINSTANCE
                        .getNamedElement_DisplayName()) {
                    // Updating the label
                    boolean setName = true;

                    /*
                     * If the name is different to what would have been the
                     * generated name then do not reset the name
                     */
                    if (ident.getLabel() != null && ident.getName() != null) {
                        setName =
                                ident.getName()
                                        .equals(getNameFromLabel(ident.getLabel()));
                    }

                    if (setName) {
                        cmd.append(SetCommand.create(getEditingDomain(),
                                ident,
                                OMPackage.eINSTANCE.getNamedElement_Name(),
                                getNameFromLabel(newValue)));
                    }
                } else if (feature == OMPackage.eINSTANCE
                        .getNamedElement_Name()) {
                    // If user changed name then update label if not set
                    if (ident.getLabel() == null || ident.getLabel().isEmpty()) {
                        cmd.append(SetCommand.create(getEditingDomain(),
                                ident,
                                OMPackage.eINSTANCE.getNamedElement_Name(),
                                newValue));
                    }
                }
                return cmd.getCommandList().size() > 1 ? cmd : cmd
                        .getCommandList().get(0);
            }
            return null;
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#getViewerContentProvider()
         * 
         * @return
         */
        @Override
        protected IContentProvider getViewerContentProvider() {
            return new IStructuredContentProvider() {

                @Override
                public void inputChanged(Viewer viewer, Object oldInput,
                        Object newInput) {

                }

                @Override
                public void dispose() {

                }

                @Override
                public Object[] getElements(Object inputElement) {
                    if (inputElement instanceof Organization) {
                        Organization org = (Organization) inputElement;
                        if (org.isDynamic()) {
                            return org.getDynamicOrgIdentifiers().toArray();
                        }
                    }
                    return new Object[0];
                }
            };
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#createAddAction(org.eclipse.jface.viewers.ColumnViewer)
         * 
         * @param viewer
         * @return
         */
        @Override
        protected ViewerAddAction createAddAction(ColumnViewer viewer) {
            return new AddIdentifierAction(viewer);
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#createDeleteAction(org.eclipse.jface.viewers.ColumnViewer)
         * 
         * @param viewer
         * @return
         */
        @Override
        protected ViewerDeleteAction createDeleteAction(ColumnViewer viewer) {
            return new TableDeleteAction(viewer) {

                @Override
                protected void deleteRows(IStructuredSelection selection) {
                    if (!selection.isEmpty()) {
                        EObject eo = getInput();
                        if (eo instanceof Organization) {
                            getEditingDomain()
                                    .getCommandStack()
                                    .execute(RemoveCommand.create(getEditingDomain(),
                                            eo,
                                            OMPackage.eINSTANCE
                                                    .getOrganization_DynamicOrgIdentifiers(),
                                            selection.toList()));
                        }
                    }
                }
            };
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#getMovableFeatures()
         * 
         * @return
         */
        @Override
        protected Set<EStructuralFeature> getMovableFeatures() {
            Set<EStructuralFeature> features = super.getMovableFeatures();
            features.add(OMPackage.eINSTANCE
                    .getOrganization_DynamicOrgIdentifiers());
            return features;
        }
    }

    /**
     * Column of the Dynamic Org Identifier table.
     * 
     */
    private abstract class IdentColumn extends AbstractColumn {

        private final EStructuralFeature feature;

        private TextCellEditor cellEditor;

        /**
         * @param editingDomain
         * @param viewer
         * @param style
         * @param heading
         * @param width
         */
        public IdentColumn(EditingDomain editingDomain, ColumnViewer viewer,
                int style, String heading, int width, EStructuralFeature feature) {
            super(editingDomain, viewer, style, heading, width);
            this.feature = feature;

            cellEditor = new TextCellEditor((Composite) viewer.getControl());
        }

        /**
         * @return the feature
         */
        protected EStructuralFeature getFeature() {
            return feature;
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getText(java.lang.Object)
         * 
         * @param element
         * @return
         */
        @Override
        protected String getText(Object element) {
            if (element instanceof DynamicOrgIdentifier) {
                return (String) ((DynamicOrgIdentifier) element)
                        .eGet(getFeature());
            }
            return ""; //$NON-NLS-1$
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getImage(java.lang.Object)
         * 
         * @param element
         * @return
         */
        @Override
        protected Image getImage(Object element) {
            return WorkingCopyUtil.getImage((EObject) element);
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getCellEditor(java.lang.Object)
         * 
         * @param element
         * @return
         */
        @Override
        protected CellEditor getCellEditor(Object element) {
            return cellEditor;
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getValueForEditor(java.lang.Object)
         * 
         * @param element
         * @return
         */
        @Override
        protected Object getValueForEditor(Object element) {
            return getText(element);
        }

    }

    /**
     * Get the internal name generated from the given label.
     * 
     * @param label
     * @return
     */
    private String getNameFromLabel(String label) {
        return NamedElementSection.getNameFromLabel(label);
    }

    /**
     * Action to add a new Dynamic Organization Identifier.
     * 
     */
    private class AddIdentifierAction extends TableAddAction {

        /**
         * @param viewer
         */
        public AddIdentifierAction(StructuredViewer viewer) {
            super(
                    viewer,
                    Messages.DynamicOrgIdentifiersSection_addIdentifierAction_label,
                    Messages.DynamicOrgIdentifiersSection_addIdentifierAction_tooltip);
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.actions.TableAddAction#addRow(org.eclipse.jface.viewers.StructuredViewer)
         * 
         * @param viewer
         * @return
         */
        @Override
        protected Object addRow(StructuredViewer viewer) {
            EObject input = getInput();
            if (input instanceof Organization) {
                DynamicOrgIdentifier identifier =
                        OMFactory.eINSTANCE.createDynamicOrgIdentifier();
                String nextAvailableLabel = getNextAvailableLabel();
                identifier.setDisplayName(nextAvailableLabel);
                identifier.setName(getNameFromLabel(nextAvailableLabel));

                getEditingDomain()
                        .getCommandStack()
                        .execute(AddCommand.create(getEditingDomain(),
                                input,
                                OMPackage.eINSTANCE
                                        .getOrganization_DynamicOrgIdentifiers(),
                                identifier));

                return identifier;
            }
            return null;
        }

        /**
         * Find the next available identifier label.
         * 
         * @return
         */
        private String getNextAvailableLabel() {
            String name = null;
            EObject input = getInput();
            if (input instanceof Organization) {
                EList<DynamicOrgIdentifier> identifiers =
                        ((Organization) input).getDynamicOrgIdentifiers();

                int idx = 1;
                while (name == null) {
                    name = String.format(IDENTIFIER_NAME_PATTERN, idx++);

                    for (DynamicOrgIdentifier ident : identifiers) {
                        if (ident.getDisplayName().equals(name)
                                || ident.getName().equals(name)) {
                            // Continue searching
                            name = null;
                            break;
                        }
                    }
                }

            }

            return name;
        }

    }

}
