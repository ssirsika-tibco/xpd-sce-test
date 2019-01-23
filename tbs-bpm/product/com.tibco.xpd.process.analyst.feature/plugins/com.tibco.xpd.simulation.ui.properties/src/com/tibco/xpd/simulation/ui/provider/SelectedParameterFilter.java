/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.ui.provider;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.tibco.xpd.simulation.common.util.ParameterDestination;
import com.tibco.xpd.simulation.common.util.ParameterSource;

/**
 * Parameter filter.
 */
public class SelectedParameterFilter extends ViewerFilter {

    /** Parameter ID. */
    private String selectedParameterId;

    /**
     * @param viewer The viewer.
     * @param parentElement The parent element.
     * @param element The element.
     * @return true if the element matches the selected parameter.
     * @see org.eclipse.jface.viewers.ViewerFilter#select(
     *      org.eclipse.jface.viewers.Viewer, java.lang.Object,
     *      java.lang.Object)
     */
    public boolean select(Viewer viewer, Object parentElement, Object element) {
        if (selectedParameterId == null) {
            return true;
        }
        if (element instanceof ParameterSource) {
            ParameterSource parameterNode = (ParameterSource) element;
            return selectedParameterId.equals(parameterNode.getParameterId());
        }
        if (element instanceof ParameterDestination
                && parentElement instanceof ParameterSource) {
            ParameterSource parameterNode = (ParameterSource) parentElement;
            if (parameterNode.getParameterId() != null
                    && parameterNode.getParameterId().equals(
                            selectedParameterId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return The selected parameter ID.
     */
    public String getSelectedParameterId() {
        return selectedParameterId;
    }

    /**
     * @param selectedParameterId The selected parameter ID.
     */
    public void setSelectedParameterId(String selectedParameterId) {
        this.selectedParameterId = selectedParameterId;
    }
}
