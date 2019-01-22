/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.compare;

/**
 * @author nwilson
 */
public class ReportType {
    /** Identifier for HTML output. */
    public static final int OUTPUT_HTML = 1;
    /** Identifier for PDF output. */
    public static final int OUTPUT_PDF = 2;
    /** Identifier for a single input resource. */
    public static final int INPUT_SINGLE = 1;
    /** Identifier for multiple input resources. */
    public static final int INPUT_MULTIPLE = 2;

    /** The unique id for this report type. */
    private String id;
    /** The title of this report. */
    private String title;
    /** The description of this report. */
    private String description;
    /** The factory used to create the report engine. */
    private ComparisonReportFactory factory;
	/**
	 * @return The description of this report. 
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description The description of this report. 
	 */
	public void setDescription(final String description) {
		this.description = description;
	}
	/**
	 * @return The unique id for this report type.
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id The unique id for this report type.
	 */
	public void setId(final String id) {
		this.id = id;
	}
	/**
	 * @return The title of this report.
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title The title of this report.
	 */
	public void setTitle(final String title) {
		this.title = title;
	}
	/**
	 * @return The factory used to create the report engine.
	 */
	public ComparisonReportFactory getFactory() {
		return factory;
	}
	/**
	 * @param factory The factory used to create the report engine.
	 */
	public void setFactory(final ComparisonReportFactory factory) {
		this.factory = factory;
	}
}
