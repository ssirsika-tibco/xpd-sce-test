/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.annotation;

/**
 * An Annotation Data Provider provides all of the values necessary to draw
 * the annotation graphs.
 *
 * @author nwilson
 */
public interface IAnnotationDataProvider {
    /**
     * @param slaMin The SLA minimum level.
     */
    void setSlaMin(Double slaMin);
    /**
     * @param slaMax The SLA maximum level.
     */
    void setSlaMax(Double slaMax);
    /**
     * Call to update the data values held.
     */
    void update();
    /**
     * @return The current value.
     */
    double getValue();
    /**
     * @return The maximum value reached.
     */
    double getMax();
    /**
     * @param maximum The maximum value reached.
     */
    void setMax(double maximum);
    /**
     * @return The SLA minimum level.
     */
    Double getSlaMin();
    /**
     * @return The SLA maximum level.
     */
    Double getSlaMax();
    /**
     * @return The label for this data item.
     */
    Object getLabel();
}
