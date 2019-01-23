/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.compare.editor;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.simulation.compare.ComparePlugin;
import com.tibco.xpd.simulation.compare.ReportTypeManager;

/**
 * @author nwilson
 */
public class SimulationReportsLabelProvider implements ITableLabelProvider {
    /** The report type manager. */
    private ReportTypeManager reportTypeManager;

    /**
     * Constructor.
     */
    public SimulationReportsLabelProvider() {
        reportTypeManager = ComparePlugin.getDefault().getReportTypeManager();
    }

    /**
     * @param object The object representing this row.
     * @param columnIndex The index of the column.
     * @return The text for the given cell.
     * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(
     *      java.lang.Object, int)
     */
    public String getColumnText(final Object object, final int columnIndex) {
        String text = ""; //$NON-NLS-1$
        if (object instanceof SimulationReport) {
            SimulationReport report = (SimulationReport) object;
            if (columnIndex == 0) {
                text = report.getName();
            } else if (columnIndex == 1) {
                return reportTypeManager.getTitle(report.getReportTypeId());
            }
        }
        return text;
    }

    /**
     * @param object The object representing this row.
     * @param columnIndex The index of the column.
     * @return null.
     * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(
     *      java.lang.Object, int)
     */
    public Image getColumnImage(final Object object, final int columnIndex) {
        return null;
    }

    /**
     * Not supported.
     * 
     * @param listener n/a
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(
     *      org.eclipse.jface.viewers.ILabelProviderListener)
     */
    public void addListener(final ILabelProviderListener listener) {
    }

    /**
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
     */
    public void dispose() {
    }

    /**
     * @param element The element to check.
     * @param property The property identifier.
     * @return false.
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(
     *      java.lang.Object, java.lang.String)
     */
    public boolean isLabelProperty(final Object element, final String property) {
        return false;
    }

    /**
     * Not supported.
     * 
     * @param listener n/a
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(
     *      org.eclipse.jface.viewers.ILabelProviderListener)
     */
    public void removeListener(final ILabelProviderListener listener) {
    }
}
