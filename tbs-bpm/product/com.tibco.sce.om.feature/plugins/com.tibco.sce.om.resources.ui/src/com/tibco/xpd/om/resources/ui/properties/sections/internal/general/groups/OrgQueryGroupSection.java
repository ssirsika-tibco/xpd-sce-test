/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections.internal.general.groups;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgQuery;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.controls.table.columns.LabelColumn;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.controls.table.columns.NameColumn;
import com.tibco.xpd.resources.ui.components.AbstractColumn;
import com.tibco.xpd.ui.properties.table.MultilineTextCellEditor;
import com.tibco.xpd.ui.util.CapabilityUtil;

/**
 * Properties section for the Query group.
 * 
 * @author njpatel
 * 
 */
public class OrgQueryGroupSection extends AbstractGroupSection {

    @Override
    protected void addColumns(TableViewer viewer) {
        new LabelColumn(getEditingDomain(), viewer);
        if (CapabilityUtil.isDeveloperActivityEnabled()) {
            new NameColumn(getEditingDomain(), viewer);
        }
        new QueryColumn(getEditingDomain(), viewer);
    }

    @Override
    protected boolean isRequiredChildDescriptor(CommandParameter descriptor) {
        return descriptor.getEReference() == OMPackage.eINSTANCE
                .getOrgModel_Queries();
    }

    /**
     * Query column that displays a multi-line text control to edit the query.
     * 
     * @author njpatel
     * 
     */
    private class QueryColumn extends AbstractColumn {

        private final MultilineTextCellEditor cellEditor;

        /**
         * Query column.
         * 
         * @param editingDomain
         * @param viewer
         */
        public QueryColumn(EditingDomain editingDomain, ColumnViewer viewer) {
            super(editingDomain, viewer, SWT.LEFT,
                    Messages.OrgQueryGroupSection_queryColumn_label, 400);

            cellEditor =
                    new MultilineTextCellEditor((Composite) viewer.getControl()) {

                        @Override
                        public boolean isUndoEnabled() {
                            return false;
                        }
                    };
        }

        @Override
        protected CellEditor getCellEditor(Object element) {
            if (element instanceof OrgQuery) {
                return cellEditor;
            }
            return null;
        }

        @Override
        protected Command getSetValueCommand(Object element, Object value) {
            if (element instanceof OrgQuery) {
                String str = value != null ? value.toString() : ""; //$NON-NLS-1$
                return SetCommand.create(getEditingDomain(),
                        element,
                        OMPackage.eINSTANCE.getOrgQuery_Query(),
                        str.length() > 0 ? str : SetCommand.UNSET_VALUE);
            }
            return null;
        }

        @Override
        protected String getText(Object element) {
            if (element instanceof OrgQuery) {
                String query = ((OrgQuery) element).getQuery();
                if (query != null) {
                    String firstLine = null;

                    if (query.contains(Text.DELIMITER)) {
                        firstLine =
                                query.substring(0,
                                        query.indexOf(Text.DELIMITER)).trim();
                        firstLine += "..."; //$NON-NLS-1$ 
                    } else {
                        firstLine = query;
                    }
                    return firstLine;
                }
            }
            return ""; //$NON-NLS-1$
        }

        @Override
        protected Object getValueForEditor(Object element) {
            if (element instanceof OrgQuery) {
                String query = ((OrgQuery) element).getQuery();
                if (query != null) {
                    return query;
                }
            }
            return ""; //$NON-NLS-1$
        }
    }

}
