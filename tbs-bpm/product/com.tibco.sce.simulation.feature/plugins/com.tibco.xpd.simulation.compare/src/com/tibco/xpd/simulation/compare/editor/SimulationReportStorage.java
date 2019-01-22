/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.compare.editor;

import java.io.Serializable;

/**
 * @author nwilson
 */
public class SimulationReportStorage implements Serializable {
    /** The serial UID. */
    private static final long serialVersionUID = -733463452061417584L;
    /** The unique id for this report type. */
    private String reportTypeId;
	/** The name of this report type. */
	private String name;
	/** The path to the report file. */
	private String reportFile;
    /** The path to the support file. */
    private String supportFile;

    /**
     * @return The name of this report type.
     */
    public String getName() {
		return name;
	}
	/**
	 * @param name  The name of this report type.
	 */
	public void setName(final String name) {
		this.name = name;
	}
	/**
	 * @return The unique id for this report type.
	 */
	public String getReportTypeId() {
		return reportTypeId;
	}
	/**
	 * @param reportTypeId The unique id for this report type.
	 */
	public void setReportTypeId(final String reportTypeId) {
		this.reportTypeId = reportTypeId;
	}
	/**
	 * @return The path to the report file.
	 */
	public String getReportFile() {
		return reportFile;
	}
	/**
	 * @param reportFile The path to the report file.
	 */
	public void setReportFile(final String reportFile) {
		this.reportFile = reportFile;
	}
    /**
     * @param supportFile The path to the support file.
     */
    public void setSupportFile(final String supportFile) {
        this.supportFile = supportFile;
    }
    /**
     * @return The path to the support file.
     */
    public String getSupportFile() {
        return supportFile;
    }
}
