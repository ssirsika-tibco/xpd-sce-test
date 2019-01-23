/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.actions;

import com.tibco.xpd.deploy.model.extension.DeploymentMultiStatus;
import com.tibco.xpd.deploy.model.extension.DeploymentStatus;
import com.tibco.xpd.deploy.ui.internal.Messages;

/**
 * Status describing the deployment action which is responsible for deploying a
 * list of modules.
 * <p>
 * <i>Created: 18 Apr 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class DeploymentActionStatus extends DeploymentMultiStatus {

    boolean refreshNeeded = true;
    private String details;

    /**
     * The constructor.
     */
    public DeploymentActionStatus() {
        super(Severity.OK, null, null);
    }

    /**
     * @see com.tibco.xpd.deploy.model.extension.DeploymentSimpleStatus#getMessage()
     */
    @Override
    public String getMessage() {
        processStatus();
        return super.getMessage();
    }

    /**
     * @return
     */
    public String getDetails() {
        processStatus();
        return details;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.deploy.model.extension.DeploymentMultiStatus#add(com.tibco.xpd.deploy.model.extension.DeploymentStatus)
     */
    @Override
    public void add(DeploymentStatus child) {
        super.add(child);
        refreshNeeded = true;
    }

    /**
     * 
     */
    private void processStatus() {
        if (refreshNeeded) {
            StringBuilder sb = new StringBuilder();
            String separator = "\n-------------\n"; //$NON-NLS-1$
            int errors = getErrorsCount();
            int warnings = getWarningsCount();
            int deployed = getChildren().size();
            int current = 0;
            for (DeploymentStatus childStatus : getChildren()) {
                sb.append(childStatus.getMessage());
                if (current < deployed - 1) {
                    sb.append(separator);
                }
                current++;
            }
            StringBuilder message = new StringBuilder(
                    Messages.DeploymentActionStatus_DeploymentFinished_message);
            message.append(' ');
            if (errors > 0 || warnings > 0) {
                message
                        .append(String
                                .format(
                                        Messages.DeploymentActionStatus_ErrorsWarningPart_message,
                                        errors, warnings));
            } else {
                message
                        .append(Messages.DeploymentActionStatus_NoErrorsReported_message);
            }
            setMessage(message.toString());

            details = sb.toString();
            refreshNeeded = false;
        }

    }
}
