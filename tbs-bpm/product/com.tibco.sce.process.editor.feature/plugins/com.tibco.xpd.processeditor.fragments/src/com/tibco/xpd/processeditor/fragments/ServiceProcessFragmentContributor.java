/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.processeditor.fragments;

import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.fragments.IFragmentCategory;
import com.tibco.xpd.fragments.IFragmentElement;

/**
 * Contributes the process fragments to the {@link FragmentViewPart}. Migrates
 * fragments from the older versions of Studio if the user opens the latest
 * version using an older workspace.
 * 
 * @author bharge
 * @since 21 Jan 2015
 */
public class ServiceProcessFragmentContributor extends
        PageflowProcessFragmentContributor {

    private static final String SYSTEM_FRAGMENTS_LOCATION =
            "Service Process Fragments"; //$NON-NLS-1$

    private static final String SERVICE_PROCESS_FRAGMENTS_CONTRIBUTOR_ID =
            "com.tibco.xpd.processeditor.fragments"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.processeditor.fragments.PageflowProcessFragmentContributor#getTemplatesLocation()
     * 
     * @return
     */
    @Override
    protected String getTemplatesLocation() {

        return SYSTEM_FRAGMENTS_LOCATION;
    }

    /**
     * @see com.tibco.xpd.processeditor.fragments.PageflowProcessFragmentContributor#getSystemFragmentsLocation()
     * 
     * @return
     */
    @Override
    protected String getSystemFragmentsLocation() {

        return SYSTEM_FRAGMENTS_LOCATION;
    }

    /**
     * @see com.tibco.xpd.processeditor.fragments.PageflowProcessFragmentContributor#getContributorId()
     * 
     * @return
     */
    @Override
    protected String getContributorId() {

        return SERVICE_PROCESS_FRAGMENTS_CONTRIBUTOR_ID;
    }

    /**
     * @see com.tibco.xpd.processeditor.fragments.PageflowProcessFragmentContributor#getIcon(com.tibco.xpd.fragments.IFragmentElement)
     * 
     * @param fragment
     * @return
     */
    @Override
    public Image getIcon(IFragmentElement fragment) {

        if (!(fragment instanceof IFragmentCategory)) {

            return Xpdl2ResourcesPlugin.getDefault().getImageRegistry()
                    .get(Xpdl2ResourcesConsts.ICON_SERVICE_PROCESS);
        }
        return null;

    }
}
