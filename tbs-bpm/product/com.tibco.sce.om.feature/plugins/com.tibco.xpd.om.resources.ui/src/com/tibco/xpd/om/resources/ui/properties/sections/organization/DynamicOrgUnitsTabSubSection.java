/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.om.resources.ui.properties.sections.organization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.CreateChildCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.transaction.ui.provider.TransactionalAdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;

import com.tibco.xpd.om.core.om.DynamicOrgReference;
import com.tibco.xpd.om.core.om.DynamicOrgUnit;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.OrgUnitRelationship;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.resources.ui.commonpicker.OMTypeQuery;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.general.AbstractGeneralSection;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.components.AbstractColumn;
import com.tibco.xpd.resources.ui.components.BaseTableControl;
import com.tibco.xpd.resources.ui.components.XpdToolkit;
import com.tibco.xpd.resources.ui.components.actions.ViewerAddAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerDeleteAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerDeleteEMFAction;
import com.tibco.xpd.resources.ui.picker.PickerService;
import com.tibco.xpd.resources.ui.picker.PickerTypeQuery;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Dynamic Org units section in the "Units" tab.
 * 
 * @author njpatel
 */
public class DynamicOrgUnitsTabSubSection extends AbstractGeneralSection
        implements IFilter {

    private DynamicOrgUnitTable table;

    private TransactionalAdapterFactoryLabelProvider modelLabelProvider;

    /**
     * Dynamic Org units section in the "Units" tab.
     */
    public DynamicOrgUnitsTabSubSection() {
        modelLabelProvider =
                new TransactionalAdapterFactoryLabelProvider(XpdResourcesPlugin
                        .getDefault().getEditingDomain(), XpdResourcesPlugin
                        .getDefault().getAdapterFactory());
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractTransactionalSection#dispose()
     * 
     */
    @Override
    public void dispose() {
        modelLabelProvider.dispose();
        super.dispose();
    }

    /**
     * @see com.tibco.xpd.om.resources.ui.properties.sections.internal.general.AbstractGeneralSection#doCreateControls(org.eclipse.swt.widgets.Composite,
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

        Label desc =
                toolkit.createLabel(root,
                        Messages.DynamicOrgUnitsTabSubSection_dynamicOrgUnitLabelDerived_longdesc,
                        SWT.WRAP);
        setLayoutData(desc);

        table = new DynamicOrgUnitTable(root, toolkit);
        GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
        data.widthHint = 200;
        data.heightHint = 150;
        table.setLayoutData(data);

        return root;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {
        table.getViewer().setInput(getInput());
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractTransactionalSection#shouldRefresh(java.util.List)
     * 
     * @param notifications
     * @return
     */
    @Override
    protected boolean shouldRefresh(List<Notification> notifications) {
        boolean shouldRefresh = super.shouldRefresh(notifications);

        /*
         * If a sub-unit has been refreshed then this section needs to be
         * updated (sub-units are not contained in this OrgUnit)
         */
        if (!shouldRefresh && notifications != null) {
            // Also refresh if any of the sub-units have been updated
            for (Notification notification : notifications) {
                if (notification.getNotifier() instanceof OrgUnit) {
                    OrgUnitRelationship relationship =
                            ((OrgUnit) notification.getNotifier())
                                    .getIncomingHierachicalRelationship();
                    if (relationship != null && relationship.getFrom() != null) {
                        return (relationship.getFrom().equals(getInput()));
                    }
                }
            }
        }
        return shouldRefresh;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    protected Command doGetCommand(Object obj) {
        // Controlled by the table
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
        EObject eo = resollveInput(toTest);

        /*
         * XPD-5300: Don't show for a Dynamic OrgUnit
         */
        if (eo instanceof DynamicOrgUnit) {
            return false;
        }

        // Don't show for dynamic organization
        if (eo instanceof Organization) {
            return !((Organization) eo).isDynamic();
        } else if (eo instanceof OrgUnit) {
            return !((OrgUnit) eo).getOrganization().isDynamic();
        }

        return false;
    }

    /**
     * Table showing dynamic org units.
     * 
     */
    private class DynamicOrgUnitTable extends BaseTableControl {

        private IContentProvider contentProvider;

        /**
         * @param parent
         * @param toolkit
         */
        public DynamicOrgUnitTable(Composite parent, XpdToolkit toolkit) {
            super(parent, toolkit);
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#addColumns(org.eclipse.jface.viewers.ColumnViewer)
         * 
         * @param viewer
         */
        @Override
        protected void addColumns(ColumnViewer viewer) {
            new LabelColumn(getEditingDomain(), viewer);
            new DynamicOrgRefColumn(getEditingDomain(), viewer);
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#getViewerContentProvider()
         * 
         * @return
         */
        @Override
        protected IContentProvider getViewerContentProvider() {
            if (contentProvider == null) {
                contentProvider = new IStructuredContentProvider() {

                    @Override
                    public void inputChanged(Viewer viewer, Object oldInput,
                            Object newInput) {

                    }

                    @Override
                    public void dispose() {

                    }

                    @Override
                    public Object[] getElements(Object inputElement) {
                        List<DynamicOrgUnit> units =
                                new ArrayList<DynamicOrgUnit>();

                        EList<OrgUnit> orgUnits = null;
                        if (inputElement instanceof Organization) {
                            orgUnits =
                                    ((Organization) inputElement).getSubUnits();
                        } else if (inputElement instanceof OrgUnit) {
                            orgUnits = ((OrgUnit) inputElement).getSubUnits();
                        }

                        if (orgUnits != null) {
                            for (OrgUnit unit : orgUnits) {
                                if (unit instanceof DynamicOrgUnit) {
                                    units.add((DynamicOrgUnit) unit);
                                }
                            }
                        }

                        return units.toArray();
                    }
                };
            }
            return contentProvider;
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#createAddAction(org.eclipse.jface.viewers.ColumnViewer)
         * 
         * @param viewer
         * @return
         */
        @Override
        protected ViewerAddAction createAddAction(ColumnViewer viewer) {
            return new AddDynamicOrgUnitAction(viewer);
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
            return new ViewerDeleteEMFAction(viewer,
                    Collections.singleton(OMPackage.eINSTANCE
                            .getOrganization_Units())) {

                /**
                 * @see com.tibco.xpd.resources.ui.components.actions.ViewerDeleteEMFAction#run()
                 * 
                 */
                @Override
                public void run() {
                    int idx = -1;
                    if (selection != null && !selection.isEmpty()) {
                        TableViewer viewer = (TableViewer) getViewer();
                        Table table = viewer.getTable();
                        idx = table.getSelectionIndex();

                        super.run();

                        if (table.getSelectionIndex() < 0 && idx >= 0) {
                            // Select the item in the position where the item
                            // was deleted from
                            if (idx >= table.getItemCount()) {
                                idx = table.getItemCount() - 1;
                            }

                            if (idx >= 0) {
                                Object element = viewer.getElementAt(idx);
                                if (element != null) {
                                    viewer.setSelection(new StructuredSelection(
                                            element));
                                }
                            }
                        }
                    }
                }

            };
        }
    }

    /**
     * Table action to add a new Dynamic OrgUnit.
     * 
     */
    private class AddDynamicOrgUnitAction extends ViewerAddAction {

        /**
         * @param viewer
         */
        public AddDynamicOrgUnitAction(StructuredViewer viewer) {
            super(viewer);
        }

        /**
         * @see org.eclipse.jface.action.Action#run()
         * 
         */
        @Override
        public void run() {
            EditingDomain ed = getEditingDomain();
            EObject input = getInput();
            IEditingDomainItemProvider provider =
                    (IEditingDomainItemProvider) XpdResourcesPlugin
                            .getDefault().getAdapterFactory()
                            .adapt(input, IEditingDomainItemProvider.class);

            if (ed != null && input != null && provider != null) {
                Collection<?> descriptors =
                        provider.getNewChildDescriptors(input, ed, null);

                if (descriptors != null) {
                    for (Object descriptor : descriptors) {
                        if (descriptor instanceof CommandParameter
                                && ((CommandParameter) descriptor).getValue() instanceof DynamicOrgUnit) {
                            ed.getCommandStack()
                                    .execute(CreateChildCommand.create(ed,
                                            input,
                                            descriptor,
                                            Collections.singletonList(input)));

                            Object value =
                                    ((CommandParameter) descriptor).getValue();

                            ((ColumnViewer) getViewer()).editElement(value, 1);
                        }
                    }
                }
            }
        }
    }

    /**
     * Label column of the Dynamic OrgUnit table.
     * 
     */
    private class LabelColumn extends AbstractColumn {

        /**
         * @param editingDomain
         * @param viewer
         * @param style
         * @param heading
         * @param width
         */
        public LabelColumn(EditingDomain editingDomain, ColumnViewer viewer) {
            super(editingDomain, viewer, SWT.NONE, Messages.DynamicOrgUnitsTabSubSection_labelColumn_title, 300);
            setShowImage(true);
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getText(java.lang.Object)
         * 
         * @param element
         * @return
         */
        @Override
        protected String getText(Object element) {
            return modelLabelProvider.getText(element);
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getCellEditor(java.lang.Object)
         * 
         * @param element
         * @return
         */
        @Override
        protected CellEditor getCellEditor(Object element) {
            // Read-only column
            return null;
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

        /**
         * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getSetValueCommand(java.lang.Object,
         *      java.lang.Object)
         * 
         * @param element
         * @param value
         * @return
         */
        @Override
        protected Command getSetValueCommand(Object element, Object value) {
            // Read-only column
            return null;
        }
    }

    /**
     * Dynamic Org column of the Dynamic OrgUnit table.
     */
    private class DynamicOrgRefColumn extends AbstractColumn {

        private DynamicOrgPickerEditor editor;

        /**
         * @param editingDomain
         * @param viewer
         * @param style
         * @param heading
         * @param width
         */
        public DynamicOrgRefColumn(EditingDomain editingDomain,
                ColumnViewer viewer) {
            super(editingDomain, viewer, SWT.NONE, Messages.DynamicOrgUnitsTabSubSection_dynOrgColumn_title, 300);
            setShowImage(true);
            editor =
                    new DynamicOrgPickerEditor((Composite) viewer.getControl());
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getText(java.lang.Object)
         * 
         * @param element
         * @return
         */
        @Override
        protected String getText(Object element) {
            if (element instanceof DynamicOrgUnit) {
                DynamicOrgReference orgReference =
                        ((DynamicOrgUnit) element).getDynamicOrganization();
                if (orgReference != null && orgReference.getTo() != null) {
                    return modelLabelProvider.getText(orgReference.getTo());
                }
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
            // Show image of the referenced Dynamic Organization, if set
            if (element instanceof DynamicOrgUnit) {
                DynamicOrgReference ref =
                        ((DynamicOrgUnit) element).getDynamicOrganization();
                if (ref != null) {
                    return super.getImage(ref.getTo());
                }
            }
            return null;
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getCellEditor(java.lang.Object)
         * 
         * @param element
         * @return
         */
        @Override
        protected CellEditor getCellEditor(Object element) {
            editor.setInput((EObject) element);
            return editor;
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

        /**
         * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getSetValueCommand(java.lang.Object,
         *      java.lang.Object)
         * 
         * @param element
         * @param value
         * @return
         */
        @Override
        protected Command getSetValueCommand(Object element, Object value) {
            if (element instanceof DynamicOrgUnit
                    && value instanceof Organization) {
                DynamicOrgReference ref =
                        ((DynamicOrgUnit) element).getDynamicOrganization();

                if (ref == null || !value.equals(ref.getTo())) {
                    return SetCommand.create(getEditingDomain(),
                            element,
                            OMPackage.eINSTANCE
                                    .getDynamicOrgUnit_DynamicOrganization(),
                            value);
                }
            }
            return null;
        }

    }

    /**
     * Dynamic Organization column cell editor. This will show the Dynamic Org
     * picker.
     * 
     */
    private static class DynamicOrgPickerEditor extends DialogCellEditor {

        private EObject input;

        /**
         * 
         */
        public DynamicOrgPickerEditor(Composite parent) {
            super(parent);
        }

        public void setInput(EObject input) {
            this.input = input;

        }

        /**
         * @see org.eclipse.jface.viewers.DialogCellEditor#openDialogBox(org.eclipse.swt.widgets.Control)
         * 
         * @param cellEditorWindow
         * @return
         */
        @Override
        protected Object openDialogBox(Control cellEditorWindow) {
            IResource res = null;

            if (input != null) {
                res = WorkingCopyUtil.getFile(input);
            }

            Object selection =
                    PickerService
                            .getInstance()
                            .openSinglePickerDialog(cellEditorWindow.getShell(),
                                    new PickerTypeQuery[] { new OMTypeQuery(
                                            OMTypeQuery.TYPE_ID_DYNAMIC_ORGANIZATION) },
                                    null,
                                    res != null ? new IResource[] { res }
                                            : null,
                                    null,
                                    null,
                                    null);
            return (selection instanceof Organization ? selection : null);
        }

    }
}
