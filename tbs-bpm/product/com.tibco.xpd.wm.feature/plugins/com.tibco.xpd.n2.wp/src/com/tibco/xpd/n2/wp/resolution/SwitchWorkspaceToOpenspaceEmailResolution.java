/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.n2.wp.resolution;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.xpd.n2.wp.utils.PresentationChannelUtils;
import com.tibco.xpd.presentation.resources.ui.internal.util.PresentationManager;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * Resolution that switches 'Workspace GI Email' Channel Type selection to
 * 'Openspace Email' Channel Type.
 * 
 * @author aprasad
 * 
 */
public class SwitchWorkspaceToOpenspaceEmailResolution implements IResolution {

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

            PresentationChannelUtils.replaceChannelType(ed,
                    project,
                    PresentationManager.EMAIL_GI_PUSH,
                    PresentationManager.OPENSPACE_EMAIL_PUSH);

        }

    }

}
