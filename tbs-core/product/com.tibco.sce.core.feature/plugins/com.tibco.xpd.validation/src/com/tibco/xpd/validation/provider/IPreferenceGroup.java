/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.provider;

/**
 * Interface that defines the <b>preferenceGroup</b> element of the validation
 * <b>provider</b> extension point.
 * 
 * @author njpatel
 * 
 */
public interface IPreferenceGroup {

    /**
     * Get the id of this preference group.
     * 
     * @return id
     */
    public String getId();

    /**
     * Get the short description of the group.
     * 
     * @return name
     */
    public String getName();

    /**
     * Get the preference id assigned to this group.
     * 
     * @return preference id or <code>null</code> if one is not defined.
     */
    public String getPreferenceId();

    /**
     * Get issues assigned to this group.
     * 
     * @return Array of <code>IssueInfo</code> objects for each issue assigned
     *         to this group. An empty array will be returned if no issues are
     *         assigned.
     */
    public IssueInfo[] getIssues();
}
