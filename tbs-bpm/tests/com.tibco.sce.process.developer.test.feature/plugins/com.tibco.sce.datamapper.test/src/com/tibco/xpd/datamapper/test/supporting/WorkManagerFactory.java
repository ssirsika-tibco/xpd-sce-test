/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.test.supporting;

/**
 * WorkManagerFactory test class.
 * 
 * @author nwilson
 * @since 8 Jun 2015
 */
public class WorkManagerFactory {
    private WorkItem workItem;

    /**
     * @return the workItem
     */
    public WorkItem getWorkItem() {
        return workItem;
    }

    /**
     * @param workItem
     *            the workItem to set
     */
    public void setWorkItem(WorkItem workItem) {
        this.workItem = workItem;
    }
}
