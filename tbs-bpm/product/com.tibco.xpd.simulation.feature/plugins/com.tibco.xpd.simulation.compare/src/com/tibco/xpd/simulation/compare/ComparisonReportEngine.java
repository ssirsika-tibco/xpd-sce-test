/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.compare;

import org.eclipse.core.resources.IResource;

import com.tibco.xpd.simulation.compare.editor.SimulationReport;

/**
 * @author nwilson
 */
public interface ComparisonReportEngine {
	/**
	 * Sets up the report engine. This should be called once before calling generate.
     * @param report The simulation report to use for this engine.
	 */
	void initialise(SimulationReport report);
	/**
	 * Sets up the report engine specifying the output file name. This should be called
	 * once before calling generate.
     * @param report  The simulation report to use for this engine.
     * @param outputFile The file to which to write the report.
	 */
	void initialise(SimulationReport report, String outputFile);

	/**
	 * Generates a report as a file and returns the URL at which the file can be found.
	 * @param inputs The input files to use.
	 * @param outputType One of the output type constants defined in this interface.
	 * @return The URL of the report file.
	 */
	String generate(IResource[] inputs, int outputType);

	/**
	 * Disposes of the report engine and any associated resources.
	 */
	void dispose();
}
