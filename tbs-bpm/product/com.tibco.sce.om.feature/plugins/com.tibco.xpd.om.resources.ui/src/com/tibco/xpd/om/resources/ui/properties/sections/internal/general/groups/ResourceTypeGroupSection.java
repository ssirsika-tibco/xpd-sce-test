/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections.internal.general.groups;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.ResourceType;
import com.tibco.xpd.om.core.om.impl.ResourceTypeImpl;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.resources.ui.components.AbstractColumn;
import com.tibco.xpd.resources.ui.components.AbstractTableControl;
import com.tibco.xpd.ui.properties.table.CheckboxCellEditor;

/**
 * General properties section for the {@link ResourceType} group.
 * 
 * @author njpatel
 * 
 */
public class ResourceTypeGroupSection extends OrgElementTypesGroupSection {

    public static Collection<EReference> SPECIAL_RESOURCE_TYPE_FEATURES =
            Arrays.asList(OMPackage.Literals.ORG_MODEL__HUMAN_RESOURCE_TYPE,
                    OMPackage.Literals.ORG_MODEL__CONSUMABLE_RESOURCE_TYPE,
                    OMPackage.Literals.ORG_MODEL__DURABLE_RESOURCE_TYPE);

    @Override
    protected void addColumns(TableViewer viewer) {
        super.addColumns(viewer);

        // Add special resource type columns
        new SpecialTypeColumn(getEditingDomain(), viewer,
                Messages.ResourceTypeGroupSection_humanResourceColumn_label,
                OMPackage.Literals.ORG_MODEL__HUMAN_RESOURCE_TYPE);
        new SpecialTypeColumn(getEditingDomain(), viewer,
                Messages.ResourceTypeGroupSection__durableResourceColumn_label,
                OMPackage.Literals.ORG_MODEL__DURABLE_RESOURCE_TYPE);
        new SpecialTypeColumn(
                getEditingDomain(),
                viewer,
                Messages.ResourceTypeGroupSection__consumableResourceColumn_label,
                OMPackage.Literals.ORG_MODEL__CONSUMABLE_RESOURCE_TYPE);
    }

    @Override
    protected boolean canDelete(IStructuredSelection selection) {
        // The human resource types cannot be deleted
        for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
            Object next = iter.next();
            if (next instanceof ResourceType
                    && ((ResourceType) next).isHumanResourceType()) {
                return false;
            }
        }
        return super.canDelete(selection);
    }

    @Override
    protected boolean shouldRefresh(List<Notification> notifications) {
        /*
         * Should refresh this section if the human resource type has changed in
         * the OrgModel.
         */
        if (notifications != null) {
            for (Notification notification : notifications) {
                if (notification.getNotifier() instanceof OrgModel
                        && SPECIAL_RESOURCE_TYPE_FEATURES.contains(notification
                                .getFeature())) {
                    return true;
                }
            }
        }

        return super.shouldRefresh(notifications);
    }

    @Override
    protected void doRefresh() {
        super.doRefresh();
        /*
         * Reselect the selected item to force the update of the actions. This
         * is required as if a resource type is marked as human then it should
         * not be deletable.
         */
        AbstractTableControl table = getTable();
        if (table != null && table.getTableViewer() != null
                && !table.isDisposed()) {
            ISelection sel = table.getTableViewer().getSelection();
            table.getTableViewer().setSelection(sel);
        }
    }

    /**
     * Special resource type check box column
     * 
     * @author Jan Arciuchiewicz
     * 
     */
    private class SpecialTypeColumn extends AbstractColumn {

        private final CheckboxCellEditor editor;

        private final EReference specialResourceTypeOrgModelFeature;

        public SpecialTypeColumn(EditingDomain editingDomain,
                ColumnViewer viewer, String label,
                EReference specialResourceTypeOrgModelFeature) {
            super(editingDomain, viewer, SWT.LEFT, label, 150);
            this.specialResourceTypeOrgModelFeature =
                    specialResourceTypeOrgModelFeature;
            editor =
                    new CheckboxCellEditor((Composite) viewer.getControl(),
                            SWT.NONE);
            setShowImage(true);
        }

        @Override
        protected CellEditor getCellEditor(Object element) {
            return editor;
        }

        @Override
        protected Command getSetValueCommand(Object element, Object value) {
            if (element instanceof ResourceType && value instanceof Boolean
                    && ((Boolean) value)) {
                EObject container = (EObject) element;

                while (container != null && !(container instanceof OrgModel)) {
                    container = container.eContainer();
                }

                if (container instanceof OrgModel) {
                    return SetCommand.create(getEditingDomain(),
                            container,
                            specialResourceTypeOrgModelFeature,
                            element);
                }
            }
            return null;
        }

        @Override
        protected String getText(Object element) {
            return null;
        }

        @Override
        protected Image getImage(Object element) {
            return isSpecialResourceType(element) ? CheckboxCellEditor
                    .getImgChecked() : CheckboxCellEditor.getImgUnchecked();
        }

        @Override
        protected Object getValueForEditor(Object element) {
            return isSpecialResourceType(element);
        }

        private boolean isSpecialResourceType(Object element) {
            return element instanceof ResourceTypeImpl
                    && ((ResourceTypeImpl) element)
                            .isSpecialResourceType(specialResourceTypeOrgModelFeature
                                    .getFeatureID());
        }

    }
}
