/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.n2.live;

import java.util.Map;

/**
 * Interface to allow properties to be contributed to the Openspace URL.
 * 
 * @author nwilson
 * @since 1 Sep 2014
 */
public interface IPropertyProvider {
    /**
     * @return A map of properties.
     */
    Map<String, String> getProperties();
}
