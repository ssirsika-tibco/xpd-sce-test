/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.provider;

import org.eclipse.core.runtime.AssertionFailedException;

/**
 * Instances of this interface can be obtained via the explicit Validator class,
 * but are otherwise used internally. It provides access to static information
 * about an issue as defined in the issue extension point.
 * 
 * @author nwilson
 */
public class IssueInfo implements Comparable {
    /** The issue id. */
    private String id;

    /** The issue message. */
    private String message;

    /** The issue severity. */
    private int severity;

    /** The preference group id */
    private String preferenceGroupId;

    /** The preference description */
    private String preferenceDescription;

    /**
     * @param id
     *            The issue id.
     * @param message
     *            The issue message.
     * @param severity
     *            The issue severity.
     * @param preferenceGroupId
     *            The preference id.
     * @param preferenceDesc
     *            The preference description.
     */
    public IssueInfo(String id, String message, int severity,
            String preferenceGroupId, String preferenceDesc) {
        this.id = id;
        this.message = message;
        this.severity = severity;
        this.preferenceGroupId = preferenceGroupId;
        this.preferenceDescription = preferenceDesc;
    }

    /**
     * @return The issue id.
     */
    public String getId() {
        return id;
    }

    /**
     * @return The issue message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return The severity.
     */
    public int getSeverity() {
        return severity;
    }

    /**
     * @return The preference group id. If no group id is set then
     *         <code>null</code> will be returned.
     */
    public String getPreferenceGroupId() {
        return preferenceGroupId;
    }

    /**
     * @return The preference description. If no description is set then
     *         <code>null</code> will be returned.
     */
    public String getPreferenceDescription() {
        return preferenceDescription;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Object o) {
        if (o instanceof IssueInfo) {
            String thisName = getPreferenceDescription() != null ? getPreferenceDescription()
                    : getMessage();

            String secondName = ((IssueInfo) o).getPreferenceDescription() != null ? ((IssueInfo) o)
                    .getPreferenceDescription()
                    : ((IssueInfo) o).getMessage();

            return thisName.compareTo(secondName);
        }

        throw new AssertionFailedException(
                "Issues can only be compared with each other."); //$NON-NLS-1$
    }

}
