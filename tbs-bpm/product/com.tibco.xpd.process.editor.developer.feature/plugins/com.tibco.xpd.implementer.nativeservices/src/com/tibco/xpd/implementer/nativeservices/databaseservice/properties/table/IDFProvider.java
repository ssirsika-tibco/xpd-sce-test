/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.databaseservice.properties.table;

import com.tibco.xpd.xpdl2.Activity;

/**
 * Interface to be implemented by the class that will provide the
 * <code>Activity</code> for the data field cell picker.
 * 
 * @author njpatel
 */
public interface IDFProvider {
    /**
     * Get the <code>Process</code> that the Database step belongs to
     * 
     * @return
     */
    public Activity getActivity();
}