/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.ui.perspective;

import org.eclipse.ui.IStartup;

/**
 * Starts perspective lifecycle manager to registers perspective listeners (see
 * also: "com.tibco.xpd.resources.ui.perspectiveLifecycleListener" extension).
 *
 * @author jarciuch
 * @since 19 May 2015
 */
public class PerspactiveLifcycleStartup implements IStartup {
    /**
     * Starts perspective lifecycle manager.
     * 
     * @see org.eclipse.ui.IStartup#earlyStartup()
     */
    @Override
    public void earlyStartup() {
        PerspectiveLifecycleManager.getInstance();
    }
}