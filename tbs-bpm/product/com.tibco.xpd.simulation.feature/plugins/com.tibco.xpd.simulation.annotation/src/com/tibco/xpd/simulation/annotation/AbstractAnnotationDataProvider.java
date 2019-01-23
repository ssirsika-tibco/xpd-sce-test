/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.annotation;

import com.tibco.simulation.report.SimRepActivity;
import com.tibco.simulation.report.SimRepParticipant;

/**
 * @author nwilson
 */
public abstract class AbstractAnnotationDataProvider implements
        IAnnotationDataProvider {
    /** The simulation report activity. */
    private SimRepActivity simActivity;

    /** The simulation report participant. */
    private SimRepParticipant simParticipant;

    /** The minimum SLA. */
    private Double slaMin;

    /** The maximum SLA. */
    private Double slaMax;

    /** The current value. */
    private double value;

    /** The highest value reached. */
    private double max;

    /**
     * @param simActivity The simulation report activity.
     * @param simParticipant The simulation report participant.
     */
    public AbstractAnnotationDataProvider(SimRepActivity simActivity,
            SimRepParticipant simParticipant) {
        this.simActivity = simActivity;
        this.simParticipant = simParticipant;
    }

    /**
     * @param slaMin The minimum SLA.
     * @see com.tibco.xpd.simulation.annotation.IAnnotationDataProvider#
     *      setSlaMin(java.lang.Double)
     */
    public void setSlaMin(Double slaMin) {
        this.slaMin = slaMin;
    }

    /**
     * @param slaMax The maximum SLA.
     * @see com.tibco.xpd.simulation.annotation.IAnnotationDataProvider#
     *      setSlaMax(java.lang.Double)
     */
    public void setSlaMax(Double slaMax) {
        this.slaMax = slaMax;
    }

    /**
     * @param max The highest value reached.
     * @see com.tibco.xpd.simulation.annotation.IAnnotationDataProvider#
     *      setMax(double)
     */
    public void setMax(double max) {
        this.max = max;
    }

    /**
     * @return The current value.
     * @see com.tibco.xpd.simulation.annotation.IAnnotationDataProvider#
     *      getValue()
     */
    public double getValue() {
        return value;
    }

    /**
     * @return The highest value reached.
     * @see com.tibco.xpd.simulation.annotation.IAnnotationDataProvider#getMax()
     */
    public double getMax() {
        return max;
    }

    /**
     * @return The minimum SLA.
     * @see com.tibco.xpd.simulation.annotation.IAnnotationDataProvider#
     *      getSlaMin()
     */
    public Double getSlaMin() {
        return slaMin;
    }

    /**
     * @return The maximum SLA.
     * @see com.tibco.xpd.simulation.annotation.IAnnotationDataProvider#
     *      getSlaMax()
     */
    public Double getSlaMax() {
        return slaMax;
    }

    /**
     * @return The simulation report activity.
     */
    protected SimRepActivity getSimActivity() {
        return simActivity;
    }

    /**
     * @return The simulation report participant.
     */
    protected SimRepParticipant getSimParticipant() {
        return simParticipant;
    }

    /**
     * @param value The current value.
     */
    protected void setValue(double value) {
        this.value = value;
    }
}
