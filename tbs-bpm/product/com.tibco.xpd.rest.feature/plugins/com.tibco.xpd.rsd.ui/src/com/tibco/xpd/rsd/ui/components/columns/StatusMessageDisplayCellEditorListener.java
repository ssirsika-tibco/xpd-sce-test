/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */
package com.tibco.xpd.rsd.ui.components.columns;

import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.viewers.ICellEditorListener;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.resources.ui.components.AbstractColumn;

/**
 * A cell editor listener displaying validation messages (from column's cell
 * editor) in the workbench's status line.
 * <p>
 * {@link #getStatusLineManager()} method should be called when cell editor is
 * requested (for example: {@link AbstractColumn#getCellEditor(Object)}) to set
 * the correct status line manager.
 * </p>
 * 
 * @author jarciuch
 * @since 10 Aug 2015
 */
public class StatusMessageDisplayCellEditorListener implements
        ICellEditorListener {
    private final TextCellEditor editor;

    private IStatusLineManager statusLineManager;

    public StatusMessageDisplayCellEditorListener(TextCellEditor editor) {
        this.editor = editor;
    }

    /**
     * This method needs to be called in getCellEditor to initialize correct
     * status line manager.
     */
    public void setCurrentStatusLineManager() {
        statusLineManager = getStatusLineManager();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ICellEditorListener#applyEditorValue()
     */
    @Override
    public void applyEditorValue() {
        if (statusLineManager != null) {
            statusLineManager.setErrorMessage(null);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ICellEditorListener#cancelEditor()
     */
    @Override
    public void cancelEditor() {
        if (statusLineManager != null) {
            statusLineManager.setErrorMessage(null);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ICellEditorListener#editorValueChanged(
     * boolean, boolean)
     */
    @Override
    public void editorValueChanged(boolean oldValidState, boolean newValidState) {
        if (statusLineManager != null) {
            if (!newValidState) {
                statusLineManager.setErrorMessage(editor.getErrorMessage());
            } else {
                statusLineManager.setErrorMessage(null);
            }
        }
    }

    /**
     * @return IStatusLineManager to display validation messages or 'null' if it
     *         can't be obtained..
     */
    @SuppressWarnings("restriction")
    private static IStatusLineManager getStatusLineManager() {
        IWorkbench wb = PlatformUI.getWorkbench();
        if (wb != null
                && wb.getActiveWorkbenchWindow() != null
                && wb.getActiveWorkbenchWindow().getActivePage() != null
                && wb.getActiveWorkbenchWindow().getActivePage()
                        .getActivePart() != null) {
            IWorkbenchPart part =
                    wb.getActiveWorkbenchWindow().getActivePage()
                            .getActivePart();
            if (part != null
                    && part.getSite() instanceof org.eclipse.ui.internal.PartSite) {
                return ((org.eclipse.ui.internal.PartSite) part.getSite())
                        .getActionBars().getStatusLineManager();
            }
        }
        return null;
    }

}