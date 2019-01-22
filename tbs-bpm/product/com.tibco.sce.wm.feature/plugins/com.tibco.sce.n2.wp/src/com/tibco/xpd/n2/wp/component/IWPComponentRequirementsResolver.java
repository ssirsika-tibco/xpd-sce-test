/*
 * Copyright (c) TIBCO Software Inc 2004 - 2015. All rights reserved.
 */

package com.tibco.xpd.n2.wp.component;

import com.tibco.amf.sca.model.componenttype.Requirements;
import com.tibco.n2.model.common.WPImplementation;

/**
 * Interface class to support contribution to the
 * <code>com.tibco.xpd.n2.wp.WPComponentRequirementsResolver</code> extension
 * <p>
 * This allows the contributor to add additional requirements etc for the Work
 * Presentation component.
 * 
 * @author aallway
 * @since 31 Mar 2015
 */
public interface IWPComponentRequirementsResolver {

    /**
     * Called when Work Presentation component is configured in the SCA
     * composite file during deployment. This allows
     * 
     * @param wpComponentRequirements
     *            The SCA requirements element for the WP Component
     * @param wpComponentImplementation
     *            The WP component Implementation element
     */
    public void addWPComponentRequirements(
            Requirements wpComponentRequirements,
            WPImplementation wpComponentImplementation);
}
