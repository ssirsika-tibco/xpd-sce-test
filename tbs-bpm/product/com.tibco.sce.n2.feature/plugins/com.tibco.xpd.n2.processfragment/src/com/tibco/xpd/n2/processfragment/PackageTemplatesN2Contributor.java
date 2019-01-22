/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.n2.processfragment;

import org.osgi.framework.Bundle;

import com.tibco.xpd.packagetemplates.PackageTemplatesContributor;

/**
 * Package Templates displayed in the Project and Process Package creation
 * wizard specific for N2
 * 
 * 
 * @author bharge
 * @since 3.3 (4 May 2010)
 */
public class PackageTemplatesN2Contributor extends PackageTemplatesContributor {
    /**
     * 
     */
    private static final String PKG_TEMPLATES_CONTRIBUTOR_ID =
            "com.tibco.xpd.n2.proc.pckg.templates"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String N2_PACKAGE_TEMPLATE_SYS_LOCATION =
            "N2 Package Template";

    /**
     * 
     */
    public PackageTemplatesN2Contributor() {
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.packagetemplates.PackageTemplatesContributor#
     * getSystemFragmentsLocation()
     */
    @Override
    protected String getSystemFragmentsLocation() {
        return N2_PACKAGE_TEMPLATE_SYS_LOCATION;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.packagetemplates.PackageTemplatesContributor#
     * getTemplatesLocation()
     */
    @Override
    protected String getTemplatesLocation() {
        return N2_PACKAGE_TEMPLATE_SYS_LOCATION;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.packagetemplates.PackageTemplatesContributor#getContributorId
     * ()
     */
    @Override
    protected String getContributorId() {
        return PKG_TEMPLATES_CONTRIBUTOR_ID;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processeditor.fragments.BaseXpdlTemplatesContributor#
     * getPluginBundle()
     */
    @Override
    protected Bundle getPluginBundle() {
        return N2Fragments.getDefault().getBundle();
    }
}
