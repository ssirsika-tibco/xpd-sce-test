/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections.internal.controls.table.columns;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.ActivityManagerEvent;
import org.eclipse.ui.activities.IActivityManager;
import org.eclipse.ui.activities.IActivityManagerListener;

import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.resources.ui.components.AbstractColumn;
import com.tibco.xpd.resources.ui.components.AbstractTableControl;
import com.tibco.xpd.ui.util.CapabilityUtil;

/**
 * Name column for a {@link NamedElement} in a group
 * {@link AbstractTableControl table} .
 * 
 * @author njpatel
 * 
 */
public class NameColumn extends AbstractColumn {

    private TextCellEditor editor;

    private final boolean isReadOnly;

    private TableActivityListener tableManagerListener = null;

    /**
     * Name column
     * 
     * @param editingDomain
     * @param viewer
     *            table viewer
     */
    public NameColumn(EditingDomain editingDomain, ColumnViewer viewer) {
        this(editingDomain, viewer, false);
    }

    /**
     * Name column
     * 
     * @param editingDomain
     * @param viewer
     *            table viewer
     * @param isReadOnly
     *            <code>true</code> if this is a read-only column,
     *            <code>false</code> if editing is allowed.
     */
    public NameColumn(EditingDomain editingDomain, ColumnViewer viewer,
            boolean isReadOnly) {
        super(editingDomain, viewer, Messages.NameColumn_title, 200);
        this.isReadOnly = isReadOnly;

        if (!isReadOnly) {
            editor = new TextCellEditor((Composite) viewer.getControl());
        }
        setShowImage(true);

        IActivityManager activityManager =
                PlatformUI.getWorkbench().getActivitySupport()
                        .getActivityManager();
        if (activityManager != null) {
            tableManagerListener = new TableActivityListener(this);
            activityManager.addActivityManagerListener(tableManagerListener);
        }
    }

    @Override
    public void dispose() {
        IActivityManager activityManager =
                PlatformUI.getWorkbench().getActivitySupport()
                        .getActivityManager();
        if (activityManager != null) {
            activityManager.removeActivityManagerListener(tableManagerListener);
        }
        super.dispose();
    }

    @Override
    protected String getText(Object element) {
        if (element instanceof NamedElement) {
            return ((NamedElement) element).getName();
        }
        return ""; //$NON-NLS-1$
    }

    @Override
    protected CellEditor getCellEditor(Object element) {
        if (!isReadOnly && element instanceof NamedElement) {
            return editor;
        }
        return null;
    }

    @Override
    protected Command getSetValueCommand(Object element, Object value) {
        // Cannot set an empty or null name
        if (element instanceof NamedElement && value instanceof String
                && ((String) value).trim().length() > 0) {
            return SetCommand.create(getEditingDomain(),
                    element,
                    OMPackage.eINSTANCE.getNamedElement_Name(),
                    value != null ? value : SetCommand.UNSET_VALUE);
        }
        return null;
    }

    @Override
    protected Object getValueForEditor(Object element) {
        return getText(element);
    }

    class TableActivityListener implements IActivityManagerListener {
        private NameColumn nameColumn;

        /**
         * @param nameColumn
         */
        public TableActivityListener(NameColumn nameColumn) {
            this.nameColumn = nameColumn;
        }

        public void activityManagerChanged(
                ActivityManagerEvent activityManagerEvent) {
            // if developer activity is enabled show name column
            // else hide name column
            if (CapabilityUtil.isDeveloperActivityEnabled()) {
                setVisible(nameColumn, true);
            } else {
                setVisible(nameColumn, false);
            }
        }

        private void setVisible(AbstractColumn column, boolean visible) {
            if (column != null
                    && column.getColumn() instanceof TableViewerColumn) {
                TableColumn theColumn =
                        ((TableViewerColumn) column.getColumn()).getColumn();
                if (theColumn != null) {
                    if (visible) {
                        theColumn.setWidth(column.getInitialWidth());
                        theColumn.setResizable(true);
                    } else {
                        theColumn.setWidth(0);
                        theColumn.setResizable(false);
                    }
                }
            }
        }
    }
}
