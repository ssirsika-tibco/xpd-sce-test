/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.model.extension;

import java.util.List;

/**
 * 
 * <p>
 * <i>Created: 17 Apr 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public interface DeploymentStatus {

    public static final DeploymentStatus OK_STATUS =
            new DeploymentSimpleStatus();

    public enum Severity {
        /**
         * Status severity indicating this status represents the nominal case.
         */
        OK,

        /**
         * Status severity indicating this status represents the informational
         * case.
         */
        INFO,

        /**
         * Status severity indicating this status represents the warning case.
         */
        WARNNG,

        /**
         * Status severity indicating this status represents the error occurred.
         */
        ERROR,

        /**
         * Status severity indicating this status represents the error occurred.
         */
        CANCEL
    }

    /**
     * Returns the severity. The severities are as follows (in descending
     * order):
     * <ul>
     * <li><code>CANCEL</code> - cancellation occurred</li>
     * <li><code>ERROR</code> - a serious error (most severe)</li>
     * <li><code>WARNING</code> - a warning (less severe)</li>
     * <li><code>INFO</code> - an informational message</li>
     * <li><code>OK</code> - everything is just fine</li>
     * </ul>
     * <p>
     * The severity of a multi-status is defined to be the maximum severity of
     * any of its children, or <code>OK</code> if it has no children.
     * </p>
     * 
     * @return the severity: one of <code>OK</code>, <code>ERROR</code>,
     *         <code>INFO</code>, <code>WARNING</code>, or <code>CANCEL</code>
     */
    public Severity getSeverity();

    /**
     * Returns whether this status is a multi-status. A multi-status describes
     * the outcome of an operation involving multiple operands.
     * <p>
     * The severity of a multi-status is derived from the severities of its
     * children; a multi-status with no children is <code>OK</code> by
     * definition. A multi-status carries a plug-in identifier, a status code, a
     * message, and an optional exception. Clients may treat multi-status
     * objects in a multi-status unaware way.
     * </p>
     * 
     * @return <code>true</code> for a multi-status, <code>false</code>
     *         otherwise
     * @see #getChildren()
     */
    public boolean isMultiStatus();

    /**
     * Returns a list of status object immediately contained in this
     * multi-status, or an empty list if this is not a multi-status.
     * 
     * @return an list of status objects
     * @see #isMultiStatus()
     */
    public List<DeploymentStatus> getChildren();

    /**
     * Returns whether this status indicates everything is okay (neither info,
     * warning, nor error).
     * 
     * @return <code>true</code> if this status has severity <code>OK</code>,
     *         and <code>false</code> otherwise
     */
    public boolean isOK();

    /**
     * Returns the relevant low-level exception, or <code>null</code> if none.
     * For example, when an operation fails because of a network communications
     * failure, this might return the <code>java.io.IOException</code>
     * describing the exact nature of that failure.
     * 
     * @return the relevant low-level exception, or <code>null</code> if none
     */
    public Throwable getException();

    /**
     * Returns the message describing the outcome. The message is localized to
     * the current locale.
     * 
     * @return a localized message
     */
    public String getMessage();

    /**
     * Returns number of errors associated with the status.
     * 
     * @return number of errors associated with the status.
     */
    public int getErrorsCount();

    /**
     * Returns number of warnings associated with the status.
     * 
     * @return number of warnings associated with the status.
     */
    public int getWarningsCount();
}
