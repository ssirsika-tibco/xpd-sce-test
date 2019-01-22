/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.common.util;

import java.util.ArrayList;

import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Process;

/**
 * @author nwilson
 */
public class ParameterSource {
    /** The parameter ID. */
    private String parameterId;

    private FormalParameter parameter;

    /** A list of destinations for this source. */
    private ArrayList<ParameterDestination> destinations;

    /** The total weighting factor. */
    private double totalWeighting;

    /** The process that this parameter belongs to. */
    private Process process;

    /**
     * @param process
     *            The process that this parameter belongs to.
     * @param parameterId
     *            The parameter ID.
     */
    public ParameterSource(Process process, String parameterId,
            FormalParameter parameter) {
        this.process = process;
        this.parameterId = parameterId;
        this.parameter = parameter;
        destinations = new ArrayList<ParameterDestination>();
        totalWeighting = 0;
    }

    /**
     * @param destination
     *            The destination to add.
     */
    public void addDestination(ParameterDestination destination) {
        if (!destinations.contains(destination)) {
            destinations.add(destination);
            totalWeighting += destination.getWeightingFactor();
        }
    }

    /**
     * @return The parameter ID.
     */
    public String getParameterId() {
        return parameterId;
    }

    /**
     * @return An array of destinations.
     */
    public ParameterDestination[] getDestinations() {
        ParameterDestination[] destinationArray = new ParameterDestination[destinations
                .size()];
        destinations.toArray(destinationArray);
        return destinationArray;
    }

    /**
     * @return The total weighting factor.
     */
    public double getTotalWeighting() {
        return totalWeighting;
    }

    /**
     * @param obj
     *            The object to test.
     * @return true if equal.
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
        if (obj instanceof ParameterSource) {
            return parameterId.equals(((ParameterSource) obj).getParameterId());
        }
        return super.equals(obj);
    }

    /**
     * @return The process that this parameter belongs to.
     */
    public Process getProcess() {
        return process;
    }

    /**
     * @return The parameter ID hashcode.
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return parameterId.hashCode();
    }

    /**
     * Returns label for the parameter. It will be name of the parameter or id
     * of the parameter in case the reference to formal parameter is null.
     * 
     * @return label for the parameter.
     */
    public String getText() {
        return (parameter == null) ? parameterId : WorkingCopyUtil
                .getText(parameter);
    }
}
