/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.core.validate.system.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;

import com.tibco.xpd.core.validate.system.Activator;
import com.tibco.xpd.core.validate.system.ValidationConsts;
import com.tibco.xpd.resources.XpdConfigOption;

/**
 * The system checker that will run all checks to validate the Studio
 * installation.
 * 
 * @author njpatel
 * 
 */
public final class SystemCheckRules {
    private static final String PROP_JAVA_SPECIFIFICATION_VERSION =
            "java.specification.version"; //$NON-NLS-1$

    private static final String PROP_JAVA_RUNTIME_VERSION =
            "java.runtime.version"; //$NON-NLS-1$

    private final IConfigurationLogger logger;

    private final IWorkbenchWindow window;

    /**
     * System check to validate the Studio installation.
     * 
     * @param window
     * @param logger
     */
    public SystemCheckRules(IWorkbenchWindow window,
            IConfigurationLogger logger) {
        this.window = window;
        this.logger = logger;
    }

    /**
     * Run the validation checks.
     * 
     * @param monitor
     *            progress monitor.
     */
    public void runChecks(IProgressMonitor monitor) {
        List<IStatus> results = new ArrayList<IStatus>();
        /*
         * Check execution environment. If the right Java version is not running
         * then the user will be warned and an advice will be printed for
         * corrective measure.
         */
        logger.heading(Messages.SystemCheckRules_executionEnv_heading_title);
        IStatus status = checkExecutionEnvironment(monitor);
        if (status != null) {
            results.add(status);
            reportResult(status);
            if (status.getSeverity() == IStatus.ERROR) {
                // Add advice
                logger.advice(String.format(Messages.AdviseExecutionFailed,
                        ValidationConsts.REQUIRED_JAVA_RUNTIME_VERSION));
            }

            // Log the status
            if (!status.isOK()) {
                Activator.getDefault().getLogger().log(status);
            }
        }

        if (monitor.isCanceled()) {
            throw new OperationCanceledException();
        }

        /* ACE-841: Target platform validation was removed in Studio ACE. */
        
        // status = TargetPlatformValidationUtils.validateTargetPlatform();
        // if (status != null
        // && SystemValidationPreferences
        // .validateDefaultTargetPlatformPath()) {
        // reportResult(status);
        // // Log the status
        // if (!status.isOK()) {
        // Activator.getDefault().getLogger().log(status);
        // }
        // }
        //
        // if (monitor.isCanceled()) {
        // throw new OperationCanceledException();
        // }

        /*
         * Check perspective. If a Studio perspective is not active an hyperlink
         * will be added that will allow the user to activate the correct
         * perspective.
         */
        logger.heading(Messages.SystemCheckRules_Perspective_heading_title);
        status = checkPerspective(monitor);
        if (status != null) {
            results.add(status);
            reportResult(status);

            if (status.getSeverity() == IStatus.WARNING
                    || status.getSeverity() == IStatus.ERROR) {
                // Wrong perspective set so provide link to set Modeling
                // perspective
                final String modelingPerspectiveId =
                        ValidationConsts.DEFAULT_PERSPECTIVE;

                if (modelingPerspectiveId != null) {
                    logger.hyperlink(
                            Messages.SystemCheckRules_switchToModelingPerpsective_hyperlink_shortdesc,
                            "perspective", //$NON-NLS-1$
                            new HyperlinkAdapter() {
                                @Override
                                public void linkActivated(HyperlinkEvent e) {
                                    if ("perspective".equals(e.getHref())) { //$NON-NLS-1$
                                        setPerspective(modelingPerspectiveId);
                                    }
                                }

                            },
                            true);
                }
            }
        }
        logger.heading(Messages.SystemCheckRules_configurationOptions_heading_title);
        for (XpdConfigOption option : XpdConfigOption.values()) {
            logger.message(option.getPreferenceName() + "=" + option.isOn(), //$NON-NLS-1$
                    IStatus.OK);
        }

        /*
         * Add final result
         */
        MultiStatus finalResult = new MultiStatus(Activator.PLUGIN_ID, 0,
                results.toArray(new IStatus[results.size()]), "", null); //$NON-NLS-1$
        switch (finalResult.getSeverity()) {
        case IStatus.ERROR:
            logger.result(
                    createStatus(IStatus.ERROR, Messages.CheckNoSuccess_title));
            break;
        case IStatus.WARNING:
            logger.result(createStatus(IStatus.WARNING,
                    Messages.SystemCheckRules_potentialProblems_shortdesc));
            break;
        default:
            logger.result(
                    createStatus(IStatus.OK, Messages.CheckSuccess_title));

        }
    }

    /**
     * Report the final result based on the status provided.
     * 
     * @param status
     *            result of the checks. This will probably be a
     *            {@link MultiStatus}.
     */
    private void reportResult(IStatus status) {
        if (status != null) {
            if (status.isMultiStatus()) {
                IStatus[] results = status.getChildren();
                for (IStatus st : results) {
                    reportResult(st);
                }
            } else {
                if (status instanceof ConfigStatus) {
                    logger.message(status.getMessage(),
                            ((ConfigStatus) status).getAdditionalBullets(),
                            status.getSeverity());
                } else {
                    logger.message(status.getMessage(), status.getSeverity());
                }
            }
        }
    }

    /**
     * Check the execution environment. This will verify the version of Java
     * running.
     * 
     * @param monitor
     * @return result
     */
    private IStatus checkExecutionEnvironment(IProgressMonitor monitor) {
        Properties props = System.getProperties();
        IStatus status = null;

        String javaRuntimeVersion =
                props.getProperty(PROP_JAVA_SPECIFIFICATION_VERSION);

        Double versionNumber = 0.0;

        try {
            versionNumber = new Double(javaRuntimeVersion);
        } catch (NumberFormatException ex) {

            // This should never happen
            javaRuntimeVersion = props.getProperty(PROP_JAVA_RUNTIME_VERSION);
            if (!javaRuntimeVersion.startsWith(
                    ValidationConsts.REQUIRED_JAVA_RUNTIME_VERSION)) {
                status = createStatus(IStatus.ERROR,
                        String.format(
                                Messages.SystemCheckRules_javaIsBelowRequiredVersion_warning_shortdesc,
                                ValidationConsts.REQUIRED_JAVA_RUNTIME_VERSION));
            }
        }

        if (status == null) {
            Double requiredRuntime;

            requiredRuntime =
                    new Double(ValidationConsts.REQUIRED_JAVA_RUNTIME_VERSION);

            if (versionNumber.doubleValue() < requiredRuntime.doubleValue()) {
                status = createStatus(IStatus.ERROR,
                        String.format(
                                Messages.SystemCheckRules_javaIsBelowRequiredVersion_warning_shortdesc,
                                ValidationConsts.REQUIRED_JAVA_RUNTIME_VERSION,
                                versionNumber.toString()));
            }
        }

        if (status == null) {
            status = createStatus(IStatus.OK,
                    Messages.SystemCheckRules_executionEnvironmentIsValid_shortdesc);
        }
        return status;
    }

    /**
     * Check the current perspective. If a Studio perspective is not active then
     * a warning will be shown.
     * 
     * @param monitor
     * @return result
     */
    private IStatus checkPerspective(IProgressMonitor monitor) {
        IPerspectiveDescriptor perspective = getCurrentPerspective();
        IStatus status = null;
        if (perspective != null) {
            if (perspective.getId()
                    .startsWith(ValidationConsts.TIBCO_PERSPECTIVE_PREFIX)) {
                // Modeling perspective active
                status = createStatus(IStatus.INFO,
                        String.format(
                                Messages.SystemCheckRules_activePerspective_shortdesc,
                                perspective.getLabel()));
            } else {
                // Not modeling perspective
                status = createStatus(IStatus.WARNING,
                        Messages.SystemCheckRules_perspectiveIsNotModeling_shortdesc);
            }
        } else {
            status = createStatus(IStatus.WARNING,
                    Messages.SystemCheckRules_cannotDeterminePerspective_shortdesc);
        }
        return status;
    }

    /**
     * Set the given perspective as active.
     * 
     * @param perspectiveId
     *            id of perspective to activate
     */
    private void setPerspective(final String perspectiveId) {
        IPerspectiveDescriptor[] perspectives = window.getWorkbench()
                .getPerspectiveRegistry().getPerspectives();
        for (int i = 0; i < perspectives.length; i++) {
            IPerspectiveDescriptor desc = perspectives[i];
            if (desc.getId().equals(perspectiveId)) {
                window.getActivePage().setPerspective(desc);
                return;
            }
        }
    }

    /**
     * Get the current perspective.
     * 
     * @return perspective description.
     */
    private IPerspectiveDescriptor getCurrentPerspective() {
        return window.getActivePage().getPerspective();
    }

    private IStatus createStatus(int severity, String message) {
        return new Status(severity, Activator.PLUGIN_ID, message, null);
    }

    /**
     * Extension of {@link Status} to include bullet-points that need to be
     * printed in the dialog under the main message.
     * 
     * @author njpatel
     * 
     */
    private class ConfigStatus extends Status {

        private final String[] additionalBullets;

        public ConfigStatus(int severity, String pluginId, String message,
                String[] additionalBullets) {
            super(severity, pluginId, message);
            this.additionalBullets = additionalBullets;
        }

        /**
         * Additional bullet points to go under the main message in the dialog.
         * 
         * @return
         */
        public String[] getAdditionalBullets() {
            return additionalBullets;
        }

    }
}
