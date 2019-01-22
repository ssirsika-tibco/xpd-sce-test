/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.compare;

import org.eclipse.jface.resource.ImageDescriptor;

/**
 * @author nwilson
 */
public abstract class ComparisonReportFactory {
    /** The image to use for this report type. */
    private ImageDescriptor imageDescriptor;

    /** The description of the report type. */
    private String description;

    /**
     * @return A new report engine.
     */
    public abstract ComparisonReportEngine createEngine();

    /**
     * Checks to see if the the given report output type is supported. Valid
     * values are specified in the ReportType class as "ReportType.OUTPUT_xxxx".
     * 
     * @param outputType The report type to check.
     * @return true if the output type is supported, otherwise false.
     */
    public abstract boolean supportsOutputType(int outputType);

    /**
     * Checks to see if the the given report input type is supported. Valid
     * values are specified in the ReportType class as "ReportType.INPUT_xxxx".
     * 
     * @param inputType The input type to check.
     * @return true if the input type is supported, otherwise false.
     */
    public abstract boolean supportsInputType(int inputType);

    /**
     * @param imageDescriptor The image to use for the associated report engine.
     */
    public void setImageDescriptor(final ImageDescriptor imageDescriptor) {
        this.imageDescriptor = imageDescriptor;
    }

    /**
     * @return The image to use for the associated report engine.
     */
    public ImageDescriptor getImageDescriptor() {
        return imageDescriptor;
    }

    /**
     * @param description The description for the associated report engine.
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * @return The description for the associated report engine.
     */
    public String getDescription() {
        return description;
    }
}
