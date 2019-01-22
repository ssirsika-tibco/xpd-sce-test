/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.compare;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.simulation.compare.editor.SimulationReport;
import com.tibco.xpd.simulation.compare.editor.SimulationReportStorage;

/**
 * The main plug-in class to be used in the desktop.
 */
public class ComparePlugin extends AbstractUIPlugin {

    /** Identifier of the plug-in. */
    public static final String PLUGIN_ID = "com.tibco.xpd.simulation.compare"; //$NON-NLS-1$

    /** The extension point id. */
    private static final String EXTENSION =
            "com.tibco.xpd.simulation.compare.defaultComparisonReport"; //$NON-NLS-1$

    /** The shared instance. */
    private static ComparePlugin plugin;

    /** A list of SimulationReport objects. */
    private ArrayList<SimulationReport> reports;

    /** The folder holding the reports. */
    private File reportFolder;

    /** The manager for valid report types. */
    private ReportTypeManager reportTypeManager;

    /** Listeners registered to be notified of changes to the report list. */
    private HashSet<IReportListener> reportListeners;

    /**
     * The constructor.
     */
    public ComparePlugin() {
        plugin = this;
        reports = new ArrayList<SimulationReport>();
        reportListeners = new HashSet<IReportListener>();
    }

    /**
     * This method is called upon plug-in activation.
     * 
     * @param context
     *            The bundle context.
     * @throws Exception
     *             if the plug-in could not be started.
     */
    public void start(final BundleContext context) throws Exception {
        super.start(context);
        initialiseReportTypeManager();
        reportFolder = getStateLocation().append(".reports").toFile(); //$NON-NLS-1$
        if (!reportFolder.exists()) {
            addDefaultReports();
        }
        loadReports();
    }

    /**
     * Adds the default reports to the list of currently available reports.
     */
    public void addDefaultReports() {
        IExtensionPoint point =
                Platform.getExtensionRegistry().getExtensionPoint(EXTENSION);
        IExtension[] extensions = point.getExtensions();
        for (int i = 0; i < extensions.length; i++) {
            IConfigurationElement[] config =
                    extensions[i].getConfigurationElements();
            for (int j = 0; j < config.length; j++) {
                if ("report".equals(config[j].getName())) { //$NON-NLS-1$
                    IConfigurationElement reportConfig = config[j];
                    String name = reportConfig.getAttribute("name"); //$NON-NLS-1$
                    IConfigurationElement[] birt =
                            reportConfig.getChildren("birt"); //$NON-NLS-1$
                    if (birt.length == 1) {
                        String file = birt[0].getAttribute("file"); //$NON-NLS-1$
                        SimulationReport report = new SimulationReport();
                        report.setName(name);
                        report.setReportFile(new Path(file));
                        report.setReportTypeId("birtReport"); //$NON-NLS-1$
                        try {
                            storeReport(report);
                        } catch (FileNotFoundException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    IConfigurationElement[] xslt =
                            reportConfig.getChildren("xslt"); //$NON-NLS-1$
                    if (xslt.length == 1) {
                        String file = xslt[0].getAttribute("file"); //$NON-NLS-1$
                        String zip = xslt[0].getAttribute("zip"); //$NON-NLS-1$
                        SimulationReport report = new SimulationReport();
                        report.setName(name);
                        report.setReportFile(new Path(file));
                        report.setSupportFile(new Path(zip));
                        report.setReportTypeId("xsltReport"); //$NON-NLS-1$
                        try {
                            storeReport(report);
                        } catch (FileNotFoundException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /**
     * Initialises the report type manager by loading the valid report types
     * from plugin extensions.
     */
    private void initialiseReportTypeManager() {
        reportTypeManager = new ReportTypeManager();
        IExtensionPoint point =
                Platform
                        .getExtensionRegistry()
                        .getExtensionPoint(
                                "com.tibco.xpd.simulation.compare.comparisonReportType"); //$NON-NLS-1$
        IExtension[] extensions = point.getExtensions();
        for (int i = 0; i < extensions.length; i++) {
            IConfigurationElement[] config =
                    extensions[i].getConfigurationElements();
            for (int j = 0; j < config.length; j++) {
                if ("report".equals(config[j].getName())) { //$NON-NLS-1$
                    try {
                        Object factory =
                                config[j].createExecutableExtension("factory"); //$NON-NLS-1$
                        if (factory instanceof ComparisonReportFactory) {
                            ReportType reportType = new ReportType();
                            reportType.setId(config[j].getAttribute("id")); //$NON-NLS-1$
                            reportType
                                    .setTitle(config[j].getAttribute("title")); //$NON-NLS-1$
                            reportType
                                    .setFactory((ComparisonReportFactory) factory);
                            reportType.setDescription(config[j]
                                    .getAttribute("description")); //$NON-NLS-1$
                            reportTypeManager.addReportType(reportType);
                        }
                    } catch (CoreException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    /**
     * This method is called when the plug-in is stopped.
     * 
     * @param context
     *            The bundle context.
     * @throws Exception
     *             If the plugin could not be stopped.
     */
    public void stop(final BundleContext context) throws Exception {
        super.stop(context);
        plugin = null;
    }

    /**
     * Returns the shared instance.
     * 
     * @return The default instance of this plugin.
     */
    public static ComparePlugin getDefault() {
        return plugin;
    }

    /**
     * Returns an image descriptor for the image file at the given plug-in
     * relative path.
     * 
     * @param path
     *            the path
     * @return the image descriptor
     */
    public static ImageDescriptor getImageDescriptor(final String path) {
        return AbstractUIPlugin.imageDescriptorFromPlugin(
                "com.tibco.xpd.simulation.compare", path); //$NON-NLS-1$
    }

    /**
     * Loads the report descriptor files from the reportFolder.
     */
    public void loadReports() {
        reports = new ArrayList<SimulationReport>();
        File[] reportFiles = reportFolder.listFiles();
        if (reportFiles != null) {
            for (int i = 0; i < reportFiles.length; i++) {
                try {
                    FileInputStream input = new FileInputStream(reportFiles[i]);
                    XMLDecoder decoder =
                            new XMLDecoder(input, null, null, getClass()
                                    .getClassLoader());
                    Object object = decoder.readObject();
                    decoder.close();
                    if (object instanceof SimulationReportStorage) {
                        SimulationReportStorage storage =
                                (SimulationReportStorage) object;
                        reports.add(new SimulationReport(storage));
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        fireReportChange();
    }

    /**
     * Adds a new report, saving the descriptor file to the report folder.
     * 
     * @param report
     *            The report to add.
     */
    public void addReport(final SimulationReport report) {
        try {
            storeReport(report);
            reports.add(report);
            fireReportChange();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @param report
     *            Stores a report descriptor file to the report folder.
     * @throws FileNotFoundException
     *             If the file could not be created.
     */
    private void storeReport(final SimulationReport report)
            throws FileNotFoundException {
        File file = new File(reportFolder, report.getName() + ".xml"); //$NON-NLS-1$
        reportFolder.mkdirs();
        FileOutputStream output = new FileOutputStream(file);
        XMLEncoder encoder = new XMLEncoder(output);
        encoder.writeObject(report.getStorage());
        encoder.close();
    }

    /**
     * @return A list of report descriptors.
     */
    public SimulationReport[] getReports() {
        SimulationReport[] reportArray = new SimulationReport[0];
        if (reports != null) {
            reportArray = new SimulationReport[reports.size()];
            reports.toArray(reportArray);
        }
        return reportArray;
    }

    /**
     * Removes a report and deletes the descriptor file.
     * 
     * @param report
     *            The report to remove.
     */
    public void removeReport(final SimulationReport report) {
        File file = new File(reportFolder, report.getName() + ".xml"); //$NON-NLS-1$
        file.delete();
        reports.remove(report);
        fireReportChange();
    }

    /**
     * @return The report type manager.
     */
    public ReportTypeManager getReportTypeManager() {
        return reportTypeManager;
    }

    /**
     * Adds a listener for changes to the report list.
     * 
     * @param listener
     *            The report listener to add.
     */
    public void addReportListener(final IReportListener listener) {
        reportListeners.add(listener);
    }

    /**
     * Removes a report listener.
     * 
     * @param listener
     *            The listener to remove.
     */
    public void removeReportListener(final IReportListener listener) {
        reportListeners.remove(listener);
    }

    /**
     * Notifies report listeners of a change to the report list.
     */
    private void fireReportChange() {
        for (Iterator i = reportListeners.iterator(); i.hasNext();) {
            IReportListener listener = (IReportListener) i.next();
            listener.reportChange();
        }
    }
}
