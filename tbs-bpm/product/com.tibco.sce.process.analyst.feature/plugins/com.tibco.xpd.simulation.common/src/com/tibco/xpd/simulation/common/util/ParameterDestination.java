/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.common.util;

import java.util.ArrayList;
import java.util.Iterator;

import com.tibco.xpd.simulation.EnumerationValueType;

/**
 * @author nwilson
 */
public class ParameterDestination {
    /** The source associated with this destination. */
    private ParameterSource source;

    /** The value for this destination. */
    private String value;

    /** The weighting factor for this destination. */
    private double weightingFactor;

    /** The model object. */
    private EnumerationValueType enumeration;

    /** Transitions using this destination. */
    private ArrayList<String> transitions;

    /**
     * @param source The parameter source.
     * @param value The value for this destination.
     * @param weightingFactor The weighting factory for this destination.
     * @param enumeration The model object.
     */
    public ParameterDestination(ParameterSource source, String value,
            double weightingFactor, EnumerationValueType enumeration) {
        this.source = source;
        this.value = value;
        this.weightingFactor = weightingFactor;
        this.enumeration = enumeration;
        transitions = new ArrayList<String>();
    }

    /**
     * @return The transition source.
     */
    public ParameterSource getSource() {
        return source;
    }

    /**
     * @return The parameter value.
     */
    public String getValue() {
        return value;
    }

    /**
     * @return The parameter weighting factor.
     */
    public double getWeightingFactor() {
        return weightingFactor;
    }

    /**
     * @return The model object.
     */
    public EnumerationValueType getEnumeration() {
        return enumeration;
    }

    /**
     * Adds a transition using this destination.
     * @param name The transition to add.
     */
    public void addTransition(String name) {
        transitions.add(name);
    }

    /**
     * @return The list of transitions using this destination.
     */
    public String getTransitionList() {
        StringBuffer list = new StringBuffer();
        for (Iterator i = transitions.iterator(); i.hasNext();) {
            list.append(i.next());
            if (i.hasNext()) {
                list.append(", "); //$NON-NLS-1$
            }
        }
        return list.toString();
    }

    /**
     * @param obj The object to test.
     * @return true if equal.
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
        if (obj instanceof ParameterDestination) {
            return getValue().equals(((ParameterDestination) obj).getValue());
        }
        return super.equals(obj);
    }

    /**
     * @return The transition list hashcode.
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return getTransitionList().hashCode();
    }
}
