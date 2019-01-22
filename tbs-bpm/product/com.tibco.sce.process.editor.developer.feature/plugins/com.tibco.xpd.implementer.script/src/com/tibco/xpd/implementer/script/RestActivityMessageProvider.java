/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.implementer.script;

import com.tibco.xpd.xpdl2.Activity;

/**
 * Interface for a massage adapter for a REST Service activity.
 * 
 * Use {@link RestActivityAdapterFactory} to create activity adapters.
 *
 * @author jarciuch
 * @since 7 Apr 2015
 */
public interface RestActivityMessageProvider extends
        BaseActivityMessageProvider {

    /**
     * Checks if this message provider is is supported for this activity.
     * 
     * @param activity
     *            activity to check.
     * @return 'true' if the this message provider is supported for this
     *         activity.
     */
    public boolean isSupported(Activity activity);
}
