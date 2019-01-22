/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.dataReferences;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.resources.ui.components.AbstractColumn;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.resolvers.ProcessDataReferenceAndContexts;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Column for process data name
 * 
 * @author aallway
 * @since 25 Jun 2012
 */
class DataReferencesByDataNameColumn extends AbstractColumn {

    public DataReferencesByDataNameColumn(EditingDomain editingDomain,
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
        if (element instanceof ProcessDataReferenceAndContexts) {
            return Xpdl2ModelUtil
                    .getDisplayNameOrName(((ProcessDataReferenceAndContexts) element)
                            .getReferencedData());
        }
        return "??"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getToolTipText(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    protected String getToolTipText(Object element) {
        if (element instanceof ProcessDataReferenceAndContexts) {
            String label =
                    Xpdl2ModelUtil
                            .getDisplayNameOrName(((ProcessDataReferenceAndContexts) element)
                                    .getReferencedData());
            String name =
                    ((ProcessDataReferenceAndContexts) element)
                            .getReferencedData().getName();

            if (label.equals(name)) {
                return label;
            } else {
                return String.format("%s (%s)", label, name); //$NON-NLS-1$
            }
        }

        return super.getToolTipText(element);
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getImage(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    protected Image getImage(Object element) {
        if (element instanceof ProcessDataReferenceAndContexts) {
            return WorkingCopyUtil
                    .getImage(((ProcessDataReferenceAndContexts) element)
                            .getReferencedData());
        }

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