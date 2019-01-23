/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.resolutions;

import org.eclipse.core.resources.IMarker;

/**
 * Simplified version of IMarkerResolution to allow resolution of validation.
 * The resolution can be defined through the
 * <code>com.tibco.xpd.validation.resolution</code> extension point. The
 * resolution label, description and image will be taken from the extension.
 * 
 * @author nwilson
 */
public interface IResolution {
    /**
     * Runs this resolution.
     * 
     * @param marker
     *            the marker to resolve.
     * @throws ResolutionException
     *             if a problem occured during the resolution.
     */
    void run(IMarker marker) throws ResolutionException;
}
