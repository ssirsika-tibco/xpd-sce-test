/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.dataReferences;

import java.util.Map.Entry;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.resources.ui.components.AbstractColumn;
import com.tibco.xpd.xpdl2.resolvers.DataReferenceContext;

/**
 * Column for data reference context label
 * 
 * @author aallway
 * @since 25 Jun 2012
 */
class DataReferencesByContextNameColumn extends AbstractColumn {

    public DataReferencesByContextNameColumn(EditingDomain editingDomain,
            ColumnViewer viewer, String header, int width) {
        super(editingDomain, viewer, header, width);
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
        if (element instanceof Entry) {
            return ((DataReferenceContext) ((Entry) element).getKey())
                    .getLabel();
        }
        return "??"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getImage(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    protected Image getImage(Object element) {
        return super.getImage(element);
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getCellEditor(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    protected CellEditor getCellEditor(Object element) {
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
        return null;
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
        return null;
    }

}