/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.ui.provider;

import java.text.DecimalFormat;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.simulation.common.util.DisplayTimeUnitConverter;
import com.tibco.xpd.simulation.common.util.ParameterDestination;
import com.tibco.xpd.simulation.common.util.ParameterSource;

/**
 * Simulation parameters data label provider.
 */
public class SimulationParametersDataLabelProvider implements
        ITableLabelProvider {

    /** Transitions column index. */
    private static final int TRANSITIONS_COLUMN = 3;

    /** Percentage display format. */
    private static final DecimalFormat PERCENTAGE_FORMAT = new DecimalFormat(
            DisplayTimeUnitConverter.DEFAULT_PERCENTAGE_FORMAT);

    /**
     * @param element
     *            The element.
     * @param columnIndex
     *            The column index.
     * @return null.
     * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(
     *      java.lang.Object, int)
     */
    public Image getColumnImage(Object element, int columnIndex) {
        return null;
    }

    /**
     * @param element
     *            The element.
     * @param columnIndex
     *            The column index.
     * @return The text for the cell.
     * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(
     *      java.lang.Object, int)
     */
    public String getColumnText(Object element, int columnIndex) {
        if (element instanceof ParameterSource) {
            if (columnIndex == 0) {
                ParameterSource sourceNode = (ParameterSource) element;
                return sourceNode.getText();
            }
        } else if (element instanceof ParameterDestination) {
            ParameterDestination destinationNode = (ParameterDestination) element;
            if (columnIndex == 0) {
                return destinationNode.getValue();
            } else if (columnIndex == 1) {
                return String.valueOf(destinationNode.getWeightingFactor());
            } else if (columnIndex == 2) {
                double weight = destinationNode.getWeightingFactor();
                double value = weight
                        / destinationNode.getSource().getTotalWeighting();
                return PERCENTAGE_FORMAT.format(value);
            } else if (columnIndex == TRANSITIONS_COLUMN) {
                return destinationNode.getTransitionList();
            }
        }
        return null;
    }

    /**
     * @param listener
     *            The label provider listener.
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(
     *      org.eclipse.jface.viewers.ILabelProviderListener)
     */
    public void addListener(ILabelProviderListener listener) {
    }

    /**
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
     */
    public void dispose() {
    }

    /**
     * @param element
     *            The element.
     * @param property
     *            The property.
     * @return false.
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(
     *      java.lang.Object, java.lang.String)
     */
    public boolean isLabelProperty(Object element, String property) {
        return false;
    }

    /**
     * @param listener
     *            The label provider listener.
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(
     *      org.eclipse.jface.viewers.ILabelProviderListener)
     */
    public void removeListener(ILabelProviderListener listener) {
    }
}
