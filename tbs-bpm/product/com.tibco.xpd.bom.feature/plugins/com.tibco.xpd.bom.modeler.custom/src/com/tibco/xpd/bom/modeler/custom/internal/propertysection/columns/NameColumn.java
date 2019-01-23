/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.internal.propertysection.columns;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.ActivityManagerEvent;
import org.eclipse.ui.activities.IActivityManager;
import org.eclipse.ui.activities.IActivityManagerListener;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.resources.ui.components.AbstractColumn;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.util.CapabilityUtil;

/**
 * A name column for the BOM tables.
 * 
 * @author njpatel
 * 
 */
public class NameColumn extends AbstractColumn {

    private boolean showQualified = false;

    private TextCellEditor editor;

    private TableActivityListener tableManagerListener = null;

    /**
     * Name column.
     * 
     * @param editingDomain
     * @param viewer
     *            table viewer
     * @param editable
     *            <code>true</code> if this column should be editable.
     */
    public NameColumn(EditingDomain editingDomain, ColumnViewer viewer,
            boolean editable) {
        this(editingDomain, viewer, Messages.NameColumn_column_title, editable);
    }

    public NameColumn(EditingDomain editingDomain, ColumnViewer viewer,
            boolean editable, int width) {
        this(editingDomain, viewer, Messages.NameColumn_column_title, editable,
                width);
    }

    public NameColumn(EditingDomain editingDomain, ColumnViewer viewer,
            String title, boolean editable) {
        super(editingDomain, viewer, SWT.NONE, title, 150);
    }

    /**
     * Name column.
     * 
     * @param editingDomain
     * @param viewer
     *            table viewer
     * @param title
     *            column title
     * @param editable
     *            <code>true</code> if this column should be editable
     */
    public NameColumn(EditingDomain editingDomain, ColumnViewer viewer,
            String title, boolean editable, int width) {
        super(editingDomain, viewer, SWT.NONE, title, width);
        setShowImage(true);

        if (editable) {
            editor = new TextCellEditor((Composite) viewer.getControl());
        }

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
        if( (activityManager != null) && (tableManagerListener != null)) {
            activityManager.removeActivityManagerListener(tableManagerListener);
        }
        super.dispose();
    }

    /**
     * Set whether a qualified name should be returned in this column, default
     * is <code>false</code>.
     * 
     * @param showQualified
     */
    public void setShowQualifiedName(boolean showQualified) {
        this.showQualified = showQualified;
    }

    @Override
    protected CellEditor getCellEditor(Object element) {
        return editor;
    }

    @Override
    protected Command getSetValueCommand(Object element, Object value) {
        Command cmd = null;
        if (editor != null && element instanceof NamedElement && value != null
                && ((String) value).length() > 0) {
            // Can edit this column
            cmd =
                    SetCommand.create(getEditingDomain(),
                            element,
                            UMLPackage.eINSTANCE.getNamedElement_Name(),
                            value);
        }
        return cmd;
    }

    @Override
    protected String getText(Object element) {
        String txt = null;
        if (element instanceof NamedElement) {
            if (showQualified) {
                txt = ((NamedElement) element).getQualifiedName();
            }
            if (txt == null) {
                txt = ((NamedElement) element).getName();
            }
        }
        return txt != null ? txt : ""; //$NON-NLS-1$
    }

    @Override
    protected Image getImage(Object element) {
        Image img = null;
        if (element instanceof NamedElement && !((EObject) element).eIsProxy()) {
            img = WorkingCopyUtil.getImage((EObject) element);
        }
        return img != null ? img : null;
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