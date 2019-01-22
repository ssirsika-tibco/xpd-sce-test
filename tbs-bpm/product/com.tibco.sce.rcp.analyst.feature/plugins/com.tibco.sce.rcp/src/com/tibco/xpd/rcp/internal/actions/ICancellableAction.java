/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.rcp.internal.actions;

/**
 * To be implemented by any Action that can be cancelled by the user.
 * 
 * @author njpatel
 */
public interface ICancellableAction {
    /**
     * Returns <code>true</code> if this action has been cancelled by the user.
     * 
     * @return
     */
    boolean isCancelled();
}
