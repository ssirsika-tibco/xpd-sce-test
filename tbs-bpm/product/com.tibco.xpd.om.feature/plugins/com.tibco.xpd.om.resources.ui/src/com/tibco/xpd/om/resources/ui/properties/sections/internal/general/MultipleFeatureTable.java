/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections.internal.general;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.xpd.om.core.om.MultipleFeature;
import com.tibco.xpd.om.core.om.OrgElementType;
import com.tibco.xpd.om.resources.ui.commonpicker.OMTypeQuery;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.controls.table.columns.LabelColumn;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.controls.table.columns.MultiplicityColumn;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.controls.table.columns.NameColumn;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.components.AbstractColumn;
import com.tibco.xpd.resources.ui.components.BaseTableControl;
import com.tibco.xpd.resources.ui.components.DialogCellWithClearEditor;
import com.tibco.xpd.resources.ui.components.XpdToolkit;
import com.tibco.xpd.resources.ui.picker.PickerService;
import com.tibco.xpd.resources.ui.picker.PickerTypeQuery;
import com.tibco.xpd.resources.ui.picker.filters.SameResourceFilter;
import com.tibco.xpd.resources.ui.types.TypeInfo;
import com.tibco.xpd.ui.util.CapabilityUtil;

/**
 * Abstract table used by the {@link MultipleFeature} objects.
 * 
 * @author njpatel
 * 
 */
/* public */abstract class MultipleFeatureTable extends BaseTableControl {

    /**
     * Table containing label, type and multiplicity columns for element
     * additions.
     * 
     * @param parent
     * @param toolkit
     * @param pickerInfo
     */
    public MultipleFeatureTable(Composite parent, XpdToolkit toolkit) {
        super(parent, toolkit);
    }

    /**
     * Get the element type of the given feature.
     * 
     * @param element
     * @return
     */
    protected abstract OrgElementType getElementType(MultipleFeature element);

    protected abstract String getPickerType();

    /**
     * Get the {@link Command} to set the type of the given feature.
     * 
     * @param editingDomain
     * @param feature
     * @param type
     * @return
     */
    protected abstract Command getSetTypeCommand(EditingDomain editingDomain,
            MultipleFeature feature, OrgElementType type);

    @Override
    protected void addColumns(ColumnViewer viewer) {
        EditingDomain domain =
                XpdResourcesPlugin.getDefault().getEditingDomain();
        new LabelColumn(domain, viewer);
        if (CapabilityUtil.isDeveloperActivityEnabled()) {
            new NameColumn(domain, viewer);
        }
        new FeatureTypeColumn(domain, viewer);
        new MultiplicityColumn(domain, viewer);
    }

    /**
     * Feature type column that will show the type picker.
     * 
     * @author njpatel
     * 
     */
    public class FeatureTypeColumn extends AbstractColumn {

        private final TypeEditor editor;

        /**
         * Type column.
         * 
         * @param editingDomain
         * @param viewer
         *            table viewer
         * @param type
         *            {@link TypeInfo} type that the picker of the value in this
         *            column will show.
         */
        public FeatureTypeColumn(EditingDomain editingDomain,
                ColumnViewer viewer) {
            super(editingDomain, viewer, Messages.TypeColumn_typeColumn_title,
                    250);
            editor = new TypeEditor((Composite) viewer.getControl());
            setShowImage(true);
        }

        @Override
        protected CellEditor getCellEditor(Object element) {
            if (element instanceof MultipleFeature) {
                editor.setInput((MultipleFeature) element);
                return editor;
            }
            return null;
        }

        @Override
        protected Command getSetValueCommand(Object element, Object value) {
            if (element instanceof MultipleFeature
                    && value instanceof OrgElementType) {
                return getSetTypeCommand(getEditingDomain(),
                        (MultipleFeature) element,
                        (OrgElementType) value);
            }
            return null;
        }

        @Override
        protected String getText(Object element) {
            if (element instanceof MultipleFeature) {
                return getTypeText(getElementType((MultipleFeature) element));
            }
            return null;
        }

        @Override
        protected Image getImage(Object element) {
            // Get the type image
            if (element instanceof MultipleFeature) {
                element = getElementType((MultipleFeature) element);
            } else {
                element = null;
            }
            return super.getImage(element);
        }

        /**
         * Get the display name of the element type.
         * 
         * @param type
         * @return
         */
        private String getTypeText(OrgElementType type) {
            if (type != null) {
                // return getModelLabelProvider().getText(type);
                return type.getDisplayName();
            }
            return null;
        }

        @Override
        protected Object getValueForEditor(Object element) {
            if (element instanceof MultipleFeature) {
                return getElementType((MultipleFeature) element);
            }
            return null;
        }

        /**
         * Editor to show the {@link OrgElementType} picker.
         * 
         * @author njpatel
         * 
         */
        private class TypeEditor extends DialogCellWithClearEditor {

            private MultipleFeature input;

            private OrgElementType currentType;

            protected TypeEditor(Composite parent) {
                super(parent);
            }

            @Override
            protected Object openDialogBox(Control cellEditorWindow) {
                String type = getPickerType();
                if (input != null && type != null) {
                    return PickerService
                            .getInstance()
                            .openSinglePickerDialog(cellEditorWindow.getShell(),
                                    new PickerTypeQuery[] { new OMTypeQuery(
                                            type) },
                                    null,
                                    null,
                                    null,
                                    new IFilter[] { new SameResourceFilter(
                                            input) });
                }
                return null;
            }

            protected void setInput(MultipleFeature input) {
                this.input = input;
            }

            @Override
            protected void doSetValue(Object value) {
                if (value instanceof OrgElementType) {
                    currentType = (OrgElementType) value;
                    value = getTypeText(currentType);
                } else {
                    currentType = null;
                }
                super.doSetValue(value);
            }

            @Override
            protected Object doGetValue() {
                return currentType;
            }
        }
    }
}
