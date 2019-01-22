/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.compare.editor;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.xpd.simulation.compare.ComparePlugin;

/**
 * @author nwilson
 */
public class SimulationReportsContentProvider implements
        IStructuredContentProvider {
    /**
     * Constructor.
     */
    public SimulationReportsContentProvider() {
    }

    /**
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     */
    public void dispose() {
    }

    /**
     * @param viewer The viewer for this content.
     * @param oldInput The old input.
     * @param newInput The new input.
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(
     *      org.eclipse.jface.viewers.Viewer, java.lang.Object,
     *      java.lang.Object)
     */
    public void inputChanged(final Viewer viewer, final Object oldInput,
            final Object newInput) {
    }

    /**
     * @param inputElement The input element for this content.
     * @return A list of contained elements.
     * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(
     *      java.lang.Object)
     */
    public Object[] getElements(final Object inputElement) {
        return ComparePlugin.getDefault().getReports();
    }

}
