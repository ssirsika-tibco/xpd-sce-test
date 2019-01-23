/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.n2.wp.resolution;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.xpd.n2.wp.utils.PresentationChannelUtils;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * Workspace Email GWT is an INVALID channel type , which was introduced in
 * Studio in error/confusion, this resolution will be used for existing projects
 * to switch from this invalid channel to Openspace Email channel which is the
 * ONLY email channel supported. XPD-4003 & XPD-3854
 * 
 * @author aprasad
 * 
 */
public class RemoveInvalidChannelResolution implements IResolution {

    /**
     * @see com.tibco.xpd.validation.resolutions.IResolution#run(org.eclipse.core.resources.IMarker)
     * 
     * @param marker
     * @throws ResolutionException
     */
    public void run(IMarker marker) throws ResolutionException {

        IResource resource = marker.getResource();

        TransactionalEditingDomain ed =
                XpdResourcesPlugin.getDefault().getEditingDomain();
        if (resource instanceof IProject) {

            IProject project = (IProject) resource;
            PresentationChannelUtils
                    .removeInvalidChannelType(ed, project, null);
        }

    }

}
