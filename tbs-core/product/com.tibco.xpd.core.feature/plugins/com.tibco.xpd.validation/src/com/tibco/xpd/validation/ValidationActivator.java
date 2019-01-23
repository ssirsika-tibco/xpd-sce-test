/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;
import com.tibco.xpd.resources.util.XpdConsts;
import com.tibco.xpd.validation.destinations.Destination;
import com.tibco.xpd.validation.internal.EmfValidationDisablerStartup;
import com.tibco.xpd.validation.internal.Messages;
import com.tibco.xpd.validation.internal.ValidationManager;
import com.tibco.xpd.validation.provider.IssueInfo;

/**
 * The activator class controls the plug-in life cycle.
 */
public class ValidationActivator extends AbstractUIPlugin implements IStartup {

    public enum ValidatorStatus {
        // ready - nothing to do, waiting for the change
        READY,
        // scheduled - there are validations item scheduled
        SCHEDULED,
        // working - processing validation items
        WORKING
    }

    /** The plug-in ID. */
    public static final String PLUGIN_ID = "com.tibco.xpd.validation"; //$NON-NLS-1$

    /**
     * Marker id for project specific problems - this will include cyclic
     * dependency or referenced project missing or closed problems.
     */
    public static final String PROJECT_MARKER_ID =
            "com.tibco.xpd.validation.projectMarker"; //$NON-NLS-1$

    /**
     * @deprecated Use PROJECT_MARKER_ID instead
     */
    @Deprecated
    public static final String CYCLED_PROJECTS_MARKER_ID = PROJECT_MARKER_ID;

    public static final String WORKSPACE_RESOURCE_MARKER_ID =
            "com.tibco.xpd.validation.workspaceResourceValidationMarker"; //$NON-NLS-1$

    /**
     * Key used in the marker's additional info to indicate link to a references
     * resource - this will be used during the build to revalidate resources
     * that reference a changed resource
     */
    public static final String LINKED_RESOURCE = "LinkedResource"; //$NON-NLS-1$

    /** The shared instance. */
    private static ValidationActivator plugin;

    /** status of the validation, for testing purpose */
    private ValidatorStatus validationStatus;

    /** The error log. */
    private Logger log;

    private final ValidationManager validationManager;

    /**
     * To be used for validation performance tests to collect validation timings
     * for the rules being run during the validation(set to true, collect stats
     * and then set to false)
     */
    private static boolean collectValidationStats = false;

    /**
     * The constructor.
     */
    public ValidationActivator() {
        plugin = this;
        log = LoggerFactory.createLogger(PLUGIN_ID);
        validationManager = ValidationManager.getInstance();
    }

    /**
     * @param context
     *            The bundle context.
     * @throws Exception
     *             if there was a problem starting the bundle.
     */
    @Override
    public void start(BundleContext context) throws Exception {
        plugin = this;
        super.start(context);

        // Disable some of EMF constraints.
        (new EmfValidationDisablerStartup()).earlyStartup();

        ValidationActivator.getDefault().getLogger()
                .debug("Validation Activator: Start() Start."); //$NON-NLS-1$
        long startTime = System.currentTimeMillis();

        /*
         * There are occasions in the RCP application when this plug-in is
         * loaded before the workbench is ready. If this happens then it will
         * fail to load this plug-in due to an SWT error in
         * AbstractUIPlugin.createImageRegistry().
         */
        if (!PlatformUI.isWorkbenchRunning()
                && Platform.getProduct() != null
                && Platform.getProduct().getId()
                        .equals("com.tibco.xpd.rcp.product")) { //$NON-NLS-1$
            long start = System.currentTimeMillis();
            // Only wait for a minute
            while (!PlatformUI.isWorkbenchRunning()
                    && System.currentTimeMillis() - start < 60000) {
                Thread.sleep(200);
            }
        }

        validationManager.initialize();
        validationManager.getValidationEngine().start();

        long timeTaken = System.currentTimeMillis() - startTime;

        ValidationActivator
                .getDefault()
                .getLogger()
                .debug(String.format("Validation Activator: Start() End (Time taken: %d ms)", //$NON-NLS-1$
                        timeTaken));
    }

    /**
     * @param context
     *            The bundle context.
     * @throws Exception
     *             if there was a problem stopping the bundle.
     */
    @Override
    public void stop(BundleContext context) throws Exception {
        validationManager.getValidationEngine().stop();
        plugin = null;
        super.stop(context);
    }

    /**
     * Returns the shared instance.
     * 
     * @return the shared instance.
     */
    public static ValidationActivator getDefault() {
        return plugin;
    }

    /**
     * @return The error log.
     */
    public Logger getLogger() {
        return log;
    }

    /**
     * Get the issue info with the given id.
     * 
     * @param issueId
     *            issue id
     * @return <code>IssueInfo</code>
     * @since 3.1
     */
    public IssueInfo getIssueInfo(String issueId) {
        return validationManager.getValidationEngine().getIssueInfo(issueId);
    }

    public Collection<Destination> getDestinations() {
        return validationManager.getDestinations();
    }

    /**
     * @param id
     *            The destination environment ID.
     * @return The associated Destination object.
     */
    public Destination getDestination(String id) {
        return validationManager.getDestination(id);
    }

    /**
     * @param listener
     *            The listener to add.
     */
    public void addValidationListener(final IValidationListener listener) {
        if (listener != null) {
            new Job(
                    Messages.ValidationActivator_AddValidationListener_shortdesc) {

                @Override
                protected IStatus run(IProgressMonitor monitor) {
                    if (validationManager != null
                            && validationManager.getValidationEngine() != null) {
                        validationManager.getValidationEngine()
                                .addValidationListener(listener);
                    }
                    return Status.OK_STATUS;
                }

            }.schedule();
        }
    }

    /**
     * @param listener
     *            The listener to remove.
     */
    public void removeValidationListener(final IValidationListener listener) {
        if (listener != null) {
            new Job(
                    Messages.ValidationActivator_RemoveValidationListener_shortdesc) {

                @Override
                protected IStatus run(IProgressMonitor monitor) {
                    if (validationManager != null
                            && validationManager.getValidationEngine() != null) {
                        validationManager.getValidationEngine()
                                .removeValidationListener(listener);
                    }
                    return Status.OK_STATUS;
                }

            }.schedule();
        }
    }

    /**
     * Construct and return validation marker additional information map.
     * 
     * @param marker
     *            The marker to get additional information for.
     * @return additional information, or empty map if not found.
     */
    @SuppressWarnings("unchecked")
    public Map<String, String> getAdditionalInfoMap(IMarker marker) {
        if (marker.exists()) {
            try {
                String additionalInfo =
                        String.valueOf(marker
                                .getAttribute(XpdConsts.VALIDATION_MARKER_ADDITIONAL_INFO));
                if (additionalInfo != null && additionalInfo.length() > 0) {
                    ByteArrayInputStream stream =
                            new ByteArrayInputStream(additionalInfo.getBytes());
                    try {
                        Properties props = new Properties();
                        props.load(stream);
                        Map<String, String> map = new HashMap<String, String>();
                        map.putAll((Map) props);
                        return map;
                    } catch (IOException e) {

                    }
                }
            } catch (CoreException e) {
                getLogger().error(e);
            }
        }
        return Collections.emptyMap();
    }

    public void earlyStartup() {
        /*
         * Engine start has been disabled here as it is also called from the
         * start method - this can cause two validation jobs running. It also
         * seems that start is always called before this method so no point in
         * the engine being started here.
         */
        // engine.start();
    }

    /**
     * Return status of the validation.
     */
    public ValidatorStatus getStatus() {
        return validationStatus;
    }

    /**
     * @param validationStatus
     */
    public void setStatus(ValidatorStatus validationStatus) {
        this.validationStatus = validationStatus;

    }

    /**
     * @return the collectValidationStats
     */
    public boolean isCollectValidationStats() {
        return collectValidationStats;
    }

    /**
     * 
     * This can be used for validation performance tests to collect validation
     * timings for the rules being run during the validation (set to true,
     * collect stats and then set to false)
     * 
     * @param collectValidationStats
     *            the collectValidationStats to set
     * 
     * 
     */
    public void setCollectValidationStats(boolean collectValidationStats) {
        ValidationActivator.collectValidationStats = collectValidationStats;
    }

}
