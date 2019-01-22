/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.compare.editor;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.tibco.xpd.simulation.compare.ComparePlugin;
import com.tibco.xpd.simulation.compare.ReportType;
import com.tibco.xpd.simulation.compare.ReportTypeManager;

/**
 * A descriptor for a simulation report.
 * @author nwilson
 */
public class SimulationReport implements Cloneable {
	/** The report type manager. */
	private ReportTypeManager reportTypeManager;
	/** The storage object for this report descriptor. */
	private SimulationReportStorage storage;
	
	/**
	 * Creates a new report descriptor.
	 */
	public SimulationReport() {
		reportTypeManager = ComparePlugin.getDefault().getReportTypeManager();
		storage = new SimulationReportStorage();
	}
	
	/**
     * Creates a report descriptor from a report storage object.
	 * @param storage A report storage object.
	 */
	public SimulationReport(final SimulationReportStorage storage) {
		this.storage = storage;
		reportTypeManager = ComparePlugin.getDefault().getReportTypeManager();
	}
	
	/**
	 * @return The unique id for this report type.
	 */
	public String getReportTypeId() {
		return storage.getReportTypeId();
	}

    /**
	 * @param reportId The unique id for this report type.
	 */
	public void setReportTypeId(final String reportId) {
		if (reportId != storage.getReportTypeId()) {
			ReportType reportType = reportTypeManager.getReportType(reportId);
			if (reportType != null) {
				storage.setReportTypeId(reportId);
			}
		}
	}

    /**
	 * @return The name of the report type.
	 */
	public String getName() {
		return storage.getName();
	}

    /**
	 * @param name The name of the report type.
	 */
	public void setName(final String name) {
		storage.setName(name);
	}

	/**
	 * @return A clone of the report descriptor.
	 * @see java.lang.Object#clone()
	 */
	public Object clone()  {
		SimulationReport report;
		try {
			report = (SimulationReport) super.clone();
		} catch (CloneNotSupportedException e) {
			report = null;
		}
		return report;
	}

    /**
	 * @return The storage object for this descriptor.
	 */
	public SimulationReportStorage getStorage() {
		return storage;
	}

	/**
	 * @return The path to the report file.
	 */
	public IPath getReportFile() {
		String reportFile = storage.getReportFile();
		IPath path = null;
		if (reportFile != null) {
			path = new Path(reportFile);
		}
		return path;
	}
	
	/**
	 * @param reportFile The path to the report file.
	 */
	public void setReportFile(final IPath reportFile) {
		if (reportFile != null) {
			storage.setReportFile(reportFile.toString());
		}
	}

    /**
     * Support files are used to store additional report files, such as style sheets
     * and images for HTML reports. Normally these would be placed in a zip or jar
     * file and referenced by this support file path.
     * @return The path to a support file for the report.
     */
    public IPath getSupportFile() {
        String supportFile = storage.getSupportFile();
        IPath path = null;
        if (supportFile != null) {
            path = new Path(supportFile);
        }
        return path;
    }

    /**
     * @param supportFile The path to a support file for the report.
     */
    public void setSupportFile(final IPath supportFile) {
        if (supportFile != null) {
            storage.setSupportFile(supportFile.toString());
        }
    }
}
