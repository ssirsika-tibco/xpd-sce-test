/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.model.extension;

import java.util.Collections;
import java.util.List;

/**
 * Simple status implementation.
 * <p>
 * <i>Created: 17 Apr 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class DeploymentSimpleStatus implements DeploymentStatus {

    private Severity severity;

    private String message;

    private Throwable exception;

    private boolean showReason;

    /**
     * The default non-parameter constructor.
     */
    public DeploymentSimpleStatus() {
        this.severity = Severity.OK;
        this.message = null;
        this.exception = null;
        this.showReason = false;
    }

    /**
     * The constructor.
     * 
     * @param severity
     *            the severity of status (DeploymentStatus.Severity).
     * @param message
     *            textual message describing status.
     * @param exception
     *            exception or mull if there is no exception associated.
     */
    public DeploymentSimpleStatus(Severity severity, String message,
            Throwable exception) {
        if (severity == null) {
            throw new NullPointerException("Severity cannot be null."); //$NON-NLS-1$
        }
        this.severity = severity;
        this.message = message;
        this.exception = exception;
        this.showReason = false;
    }

    /**
     * The constructor.
     * 
     * @param severity
     *            the severity of status (DeploymentStatus.Severity).
     * @param message
     *            textual message describing status.
     * @param exception
     *            exception or mull if there is no exception associated.
     */
    public DeploymentSimpleStatus(Severity severity, String message,
            Throwable exception, boolean showReason) {
        this.showReason = showReason;
        if (severity == null) {
            throw new NullPointerException("Severity cannot be null."); //$NON-NLS-1$
        }
        this.severity = severity;
        this.message = message;
        this.exception = exception;
        this.showReason = showReason;
    }

    /**
     * @see com.tibco.xpd.deploy.model.extension.DeploymentStatus#getChildren()
     */
    public List<DeploymentStatus> getChildren() {
        return Collections.emptyList();
    }

    /**
     * @see com.tibco.xpd.deploy.model.extension.DeploymentStatus#getException()
     */
    public Throwable getException() {
        return exception;
    }

    /**
     * @see com.tibco.xpd.deploy.model.extension.DeploymentStatus#getMessage()
     */
    public String getMessage() {
        if (showReason && getReason() != null) {
            return message += getReason();
        }
        return message;
    }

    /**
     * @see com.tibco.xpd.deploy.model.extension.DeploymentStatus#getSeverity()
     */
    public Severity getSeverity() {
        return severity;
    }

    /**
     * Always return false in case of basic implementation.
     * 
     * @see com.tibco.xpd.deploy.model.extension.DeploymentStatus#isMultiStatus()
     */
    public boolean isMultiStatus() {
        return false;
    }

    /**
     * Returns true if severity is OK.
     * 
     * @return true if severity is OK.
     * @see com.tibco.xpd.deploy.model.extension.DeploymentStatus#isOK()
     */
    public boolean isOK() {
        return Severity.OK == severity;
    }

    /**
     * @param message
     *            the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @param severity
     *            the severity to set
     */
    public void setSeverity(Severity severity) {
        if (severity == null) {
            throw new NullPointerException("Severity cannot be null."); //$NON-NLS-1$
        }
        this.severity = severity;
    }

    /**
     * @param exception
     *            the exception to set
     */
    public void setException(Throwable exception) {
        this.exception = exception;
    }

    public String getReason() {
        if (exception != null) {
            String reason = "\n "; //$NON-NLS-1$
            if (exception.getLocalizedMessage() != null
                    && exception.getLocalizedMessage().trim().length() > 0) {
                return reason + exception.getLocalizedMessage();
            } else if (exception.getMessage() != null
                    && exception.getMessage().trim().length() > 0) {
                return reason + exception.getMessage();
            }
        }
        return null;
    }

    /**
     * Returns true if error message (if exists) will be automatically appended
     * to message.
     * 
     * @return the showReason
     */
    public boolean isShowReason() {
        return showReason;
    }

    /**
     * Sets if error message (if exists) should be automatically appended to
     * message.
     * 
     * @param showReason
     *            the showReason to set
     */
    public void setShowReason(boolean showReason) {
        this.showReason = showReason;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.deploy.model.extension.DeploymentStatus#getErrorsCount()
     */
    public int getErrorsCount() {
        if (getSeverity() == Severity.ERROR || getSeverity() == Severity.CANCEL) {
            return 1;
        }
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.deploy.model.extension.DeploymentStatus#getWarningsCount()
     */
    public int getWarningsCount() {
        if (getSeverity() == Severity.WARNNG) {
            return 1;
        }
        return 0;
    }

}
