/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.test.supporting;

/**
 * WorkItem test class.
 * 
 * @author nwilson
 * @since 8 Jun 2015
 */
public class WorkItem {
    private Boolean cancel;

    private String description;

    private Integer priority;

    private WorkItemAttributes workItemAttributes;

    /**
     * @return the cancel
     */
    public Boolean getCancel() {
        return cancel;
    }

    /**
     * @param cancel
     *            the cancel to set
     */
    public void setCancel(Boolean cancel) {
        this.cancel = cancel;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the priority
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * @param priority
     *            the priority to set
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    /**
     * @return the workItemAttributes
     */
    public WorkItemAttributes getWorkItemAttributes() {
        return workItemAttributes;
    }

    /**
     * @param workItemAttributes
     *            the workItemAttributes to set
     */
    public void setWorkItemAttributes(WorkItemAttributes workItemAttributes) {
        this.workItemAttributes = workItemAttributes;
    }

}
