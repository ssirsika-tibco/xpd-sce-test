/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.validation.resolutions;

import org.eclipse.core.resources.IMarker;

/**
 * 
 * 
 * @author bharge
 * @since 23 Sep 2011
 */
public interface IConfigurableResolutionLabel {

    /**
     * This method (when overridden) gives the individual resolution the
     * opportunity to modify the resolution's quick fix label.
     * 
     * @param propertiesLabel
     *            The original label specified by the resolution contribution.
     * @param marker
     *            The marker for which the resolution has been raised.
     * 
     * @return Resolution quick fix label text
     */
    public abstract String getConfigurableResolutionLabel(
            String propertiesLabel, IMarker marker);

    /**
     * This method (when overridden) gives the individual resolution the
     * opportunity to modify the resolution's quick fix description.
     * 
     * @param propertiesDescription
     *            The original description specified by the resolution
     *            contribution.
     * @param marker
     *            The marker for which the resolution has been raised.
     * 
     * @return Resolution quick fix description text
     */
    public abstract String getConfigurableResolutionDescription(
            String propertiesDescription, IMarker marker);

}