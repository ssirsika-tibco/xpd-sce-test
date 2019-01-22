/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.compare.report;

import com.tibco.xpd.simulation.compare.ComparisonReportEngine;
import com.tibco.xpd.simulation.compare.ComparisonReportFactory;

/**
 * @author nwilson
 */
public class BirtReportFactory extends ComparisonReportFactory {

    /**
     * @return A new BIRT report engine.
     * @see com.tibco.xpd.simulation.compare.ComparisonReportFactory#createEngine()
     */
    public ComparisonReportEngine createEngine() {
        return new BirtReportEngine();
    }

    /**
     * @param outputType The output type to check.
     * @return true, all output types are supported.
     * @see com.tibco.xpd.simulation.compare.ComparisonReportFactory#
     *      supportsOutputType(int)
     */
    public boolean supportsOutputType(final int outputType) {
        return true;
    }

    /**
     * @param inputType The input type to check.
     * @return true, all input types are supported.
     * @see com.tibco.xpd.simulation.compare.ComparisonReportFactory#
     *      supportsInputType(int)
     */
    public boolean supportsInputType(final int inputType) {
        return true;
    }

}
