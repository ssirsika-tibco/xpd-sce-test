/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.compare;

import java.util.HashMap;

/**
 * @author nwilson
 */
public class ReportTypeManager {
	/** A map of report id to ReportType. */
	private HashMap<String, ReportType> types;
	/** A map of report title to report id. */
	private HashMap<String, String> titleToId;
	
	/**
	 * Constructor.
	 */
	public ReportTypeManager() {
		types = new HashMap<String, ReportType>();
		titleToId = new HashMap<String, String>();
	}
	
	/**
     * Adds a new report type to the manager.
	 * @param reportType The report type to add.
	 */
	public void addReportType(final ReportType reportType) {
		types.put(reportType.getId(), reportType);
		titleToId.put(reportType.getTitle(), reportType.getId());
	}
	
	/**
	 * @param reportId The unique id of the report type.
	 * @return The title of the report type.
	 */
	public String getTitle(final String reportId) {
		String title = ""; //$NON-NLS-1$
		ReportType reportType = (ReportType) types.get(reportId);
		if (reportType != null) {
			title = reportType.getTitle();
		}
		return title;
	}

	/**
	 * @param reportId The unique id of the report type.
	 * @return The description of the report type.
	 */
	public String getDescription(final String reportId) {
		String description = ""; //$NON-NLS-1$
		ReportType reportType = (ReportType) types.get(reportId);
		if (reportType != null) {
			description = reportType.getDescription();
		}
		return description;
	}
	/**
	 * @return A list of all report types.
	 */
	public ReportType[] getReportTypes() {
		ReportType[] reportTypes = new ReportType[types.size()];
		types.values().toArray(reportTypes);
		return reportTypes;
	}

	/**
	 * @param title The title of the report type.
	 * @return The report type matching the title.
	 */
	public String getReportTypeIdForTitle(final String title) {
		return (String) titleToId.get(title);
	}

	/**
	 * @param reportId The unique id of the report type.
	 * @return The report type associatedwith the id.
	 */
	public ReportType getReportType(final String reportId) {
		return (ReportType) types.get(reportId);
	}

}
