/*
 * Copyright (c) TIBCO Software Inc. 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.deploy.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.tibco.xpd.deploy.model.extension.DeploymentMultiStatus;
import com.tibco.xpd.deploy.model.extension.DeploymentStatus;
import com.tibco.xpd.deploy.model.extension.DeploymentStatus.Severity;

/**
 * Utility class to convert a <code>DeploymentStatus</code> to an
 * <code>IStatus</code>.
 * 
 * @author njpatel
 * 
 */
public class DeploymentStatusUtil {

    /**
     * Adapt the <code>DeploymentStatus</code> to an <code>IStatus</code>.
     * 
     * @param deployStatus
     *            status object to adapt to <code>IStatus</code>
     * @param pluginId
     *            the unique identifier of the relevant plug-in
     * @param pluginCode
     *            the plug-in-specific status code, or
     *            <code>{@link IStatus#OK}</code>
     * @return <code>IStatus</code>
     */
    public static IStatus adaptToIStatus(DeploymentStatus deployStatus,
            String pluginId, int pluginCode) {
        IStatus status = null;

        if (deployStatus != null) {

            if (deployStatus instanceof DeploymentMultiStatus) {
                DeploymentMultiStatus multiStatus = (DeploymentMultiStatus) deployStatus;
                // Multi status
                List<DeploymentStatus> deployChildren = ((DeploymentMultiStatus) deployStatus)
                        .getChildren();
                List<IStatus> children = new ArrayList<IStatus>();

                if (deployChildren != null) {
                    for (DeploymentStatus child : deployChildren) {
                        children
                                .add(adaptToIStatus(child, pluginId, pluginCode));
                    }
                }

                status = new MultiStatus(
                        getSeverity(multiStatus.getSeverity()), pluginId,
                        pluginCode, multiStatus.getMessage(), multiStatus
                                .getException(), children
                                .toArray(new IStatus[children.size()]));

            } else {
                // Single status
                status = new Status(getSeverity(deployStatus.getSeverity()),
                        pluginId, pluginCode, deployStatus.getMessage(),
                        deployStatus.getException());
            }
        }

        return status;
    }

    /**
     * Convert the <code>DeploymentStatus</code> Severity value to an
     * <code>IStatus</code> severity value.
     * 
     * @param deploySeverity
     * @return <code>IStatus</code> severity value.
     */
    public static int getSeverity(Severity deploySeverity) {
        int severity = IStatus.OK;

        if (deploySeverity == Severity.INFO) {
            severity = IStatus.INFO;
        } else if (deploySeverity == Severity.WARNNG) {
            severity = IStatus.WARNING;
        } else if (deploySeverity == Severity.ERROR) {
            severity = IStatus.ERROR;
        } else if (deploySeverity == Severity.CANCEL) {
            severity = IStatus.CANCEL;
        }

        return severity;
    }

    /**
     * Extends <code>Status</code> to include Multi status functionality.
     * 
     * @author njpatel
     * 
     */
    private static class MultiStatus extends Status {

        private final IStatus[] children;

        /**
         * Constructor.
         * 
         * @param severity
         * @param pluginId
         * @param code
         * @param message
         * @param exception
         * @param children
         *            Array of status children
         */
        public MultiStatus(int severity, String pluginId, int code,
                String message, Throwable exception, IStatus[] children) {
            super(severity, pluginId, code, message, exception);

            this.children = children;
        }

        @Override
        public IStatus[] getChildren() {
            return children != null ? children : new IStatus[0];
        }

        @Override
        public boolean isMultiStatus() {
            return getChildren().length > 0;
        }

    }
}
