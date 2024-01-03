/*
 * Copyright (c) TIBCO Software Inc. 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.resources;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IStatus;

import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Status holder. For OK status provides no message when ERROR, INFO and WARNING
 * provides also acompaning message.
 * 
 * @author jarciuch
 */
public class StatusInfo implements IStatus {

    public static final IStatus OK_STATUS = new StatusInfo();

    private String statusMessage;

    private int severity;

    /**
     * Creates OK status with no message.
     */
    public StatusInfo() {
        this(OK, null);
    }

    /**
     * Creates a status with given servity and message.
     * 
     * @param severity
     *            The status severity: ERROR, WARNING, INFO and OK. If ok then
     *            message parameter is ignored
     * @param message
     *            The message of the status. Applies only for ERROR, WARNING and
     *            INFO.
     */
    public StatusInfo(int severity, String message) {
        checkSeverity(severity);
        if (severity != OK) {
            this.statusMessage = message;
        }
        this.severity = severity;
    }

    /**
     * Checks if severity is legal.
     * 
     * @param sev
     */
    private void checkSeverity(int sev) {
        Assert.isLegal(sev == OK || sev == ERROR
                || sev == WARNING || sev == INFO,
                "Not supported severity!"); //$NON-NLS-1$
    }

    /**
     * Returns if the status severity is OK.
     */
    @Override
    public boolean isOK() {
        return severity == IStatus.OK;
    }

    /**
     * Returns if the status' severity is WARNING.
     */
    public boolean isWarning() {
        return severity == IStatus.WARNING;
    }

    /**
     * Returns if the status' severity is INFO.
     */
    public boolean isInfo() {
        return severity == IStatus.INFO;
    }

    /**
     * Returns if the status' severity is ERROR.
     */
    public boolean isError() {
        return severity == IStatus.ERROR;
    }

    /**
     * @see IStatus#getMessage
     */
    @Override
    public String getMessage() {
        return statusMessage;
    }

    /**
     * Sets the status to ERROR.
     * 
     * @param errorMessage
     *            The error message (can be empty, but not null)
     */
    public void setError(String errorMessage) {
        Assert.isNotNull(errorMessage);
        statusMessage = errorMessage;
        severity = IStatus.ERROR;
    }

    /**
     * Sets the status to WARNING.
     * 
     * @param warningMessage
     *            The warning message (can be empty, but not null)
     */
    public void setWarning(String warningMessage) {
        Assert.isNotNull(warningMessage);
        statusMessage = warningMessage;
        severity = IStatus.WARNING;
    }

    /**
     * Sets the status to INFO.
     * 
     * @param infoMessage
     *            The info message (can be empty, but not null)
     */
    public void setInfo(String infoMessage) {
        Assert.isNotNull(infoMessage);
        statusMessage = infoMessage;
        severity = IStatus.INFO;
    }

    /**
     * Sets the status to OK.
     */
    public void setOK() {
        statusMessage = null;
        severity = IStatus.OK;
    }

    /*
     * @see IStatus#matches(int)
     */
    @Override
    public boolean matches(int severityMask) {
        return (severity & severityMask) != 0;
    }

    /**
     * Returns always <code>false</code>.
     * 
     * @see IStatus#isMultiStatus()
     */
    @Override
    public boolean isMultiStatus() {
        return false;
    }

    /*
     * @see IStatus#getSeverity()
     */
    @Override
    public int getSeverity() {
        return severity;
    }

    /*
     * @see IStatus#getPlugin()
     */
    @Override
    public String getPlugin() {
        return XpdResourcesPlugin.ID_PLUGIN;
    }

    /**
     * Returns always <code>null</code>.
     * 
     * @see IStatus#getException()
     */
    @Override
    public Throwable getException() {
        return null;
    }

    /**
     * Returns always the error severity.
     * 
     * @see IStatus#getCode()
     */
    @Override
    public int getCode() {
        return severity;
    }

    /**
     * Returns always <code>null</code>.
     * 
     * @see IStatus#getChildren()
     */
    @Override
    public IStatus[] getChildren() {
        return new IStatus[0];
    }

}