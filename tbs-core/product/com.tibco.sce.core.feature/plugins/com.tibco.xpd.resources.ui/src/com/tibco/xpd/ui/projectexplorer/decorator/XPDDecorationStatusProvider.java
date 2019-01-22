/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.ui.projectexplorer.decorator;

import org.eclipse.core.resources.IMarker;

/**
 * @author wzurek
 */
public interface XPDDecorationStatusProvider {

    /**
     * Return severity of the problem of given element. Used as adapter.
     * 
     * @see IMarker#SEVERITY_ERROR
     * @see IMarker#SEVERITY_WARNING
     * @see IMarker#SEVERITY_INFO
     * 
     * @return severity or '0' if none.
     */
    int getDecorationMarker();

}
