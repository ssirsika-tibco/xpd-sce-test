/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.v2.xpdl1.validation.utils;

import java.net.URL;

/**
 * Helper class to get hold of the URL of the XPDL2 to XPDL1.1 downgrade XSLT.
 * <p>
 * Use <code>{@link #INSTANCE}</code> to get the singleton instance of this
 * class through which the method <code>{@link #getDowngradeXSLT()}</code> can
 * be called to get the <code>URL</code> to the XSLT.
 * </p>
 * 
 * @author njpatel
 */
public final class Xpdl1Util {
    /**
     * Downgrade XSLT location.
     */
    private static final String XSLT = "/xslts/Studio_v3_1_to_v1_1.xslt"; //$NON-NLS-1$

    /**
     * Get singleton instance of this class.
     */
    public static final Xpdl1Util INSTANCE = new Xpdl1Util();

    /**
     * Private constructor, Use INSTANCE to get the singleton instance of this
     * class.
     */
    private Xpdl1Util() {
    }

    /**
     * Get the XPDL2 to XPDL1.1 downgrade XSLT's <code>URL</code>.
     * 
     * @return The URL of the XSLT.
     */
    public URL getDowngradeXSLT() {
        return getClass().getResource(XSLT);
    }
}
