/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.compare.report;

import com.tibco.xpd.simulation.compare.ComparisonReportEngine;
import com.tibco.xpd.simulation.compare.ComparisonReportFactory;
import com.tibco.xpd.simulation.compare.ReportType;

/**
 * @author nwilson
 */
public class XsltReportFactory extends ComparisonReportFactory {

    /**
     * @return A new XSLT report engine.
     * @see com.tibco.xpd.simulation.compare.ComparisonReportFactory#createEngine()
     */
    public ComparisonReportEngine createEngine() {
        return new XsltReportEngine();
    }

    /**
     * @param outputType The output type to check.
     * @return true for ReportType.OUTPUT_HTML, otherwise false.
     * @see com.tibco.xpd.simulation.compare.ComparisonReportFactory#
     *      supportsOutputType(int)
     */
    public boolean supportsOutputType(final int outputType) {
        return outputType == ReportType.OUTPUT_HTML;
    }

    /**
     * @param inputType The input type to check.
     * @return true for ReportType.INPUT_SINGLE, otherwise false.
     * @see com.tibco.xpd.simulation.compare.ComparisonReportFactory#
     *      supportsInputType(int)
     */
    public boolean supportsInputType(final int inputType) {
        return inputType == ReportType.INPUT_SINGLE;
    }

}
