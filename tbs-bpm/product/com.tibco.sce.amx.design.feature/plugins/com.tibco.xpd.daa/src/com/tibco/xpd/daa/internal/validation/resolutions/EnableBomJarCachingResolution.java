/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.daa.internal.validation.resolutions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.daa.DaaActivator;
import com.tibco.xpd.daa.internal.preferences.DAAGenPreferences;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * Resolution to enable the BOM jar caching in preferences followed by
 * rebuilding of all affected BOM resources.
 * 
 * @author kthombar
 * @since 19-Feb-2014
 */
public class EnableBomJarCachingResolution implements IResolution {

    /**
     * @see com.tibco.xpd.validation.resolutions.IResolution#run(org.eclipse.core.resources.IMarker)
     * 
     * @param marker
     * @throws ResolutionException
     */
    @Override
    public void run(IMarker marker) throws ResolutionException {

        if (!DAAGenPreferences.cacheBomJars()) {
            /* Enable the bom jar caching */
            DAAGenPreferences.setCacheBomJars(true);
        }

        Set<IResource> resourcesToTouch = new HashSet<IResource>();
        resourcesToTouch.add(marker.getResource());

        try {

            List<IProject> allStudioProjects =
                    Arrays.asList(ProjectUtil.getAllStudioProjects());

            for (IProject eachProject : allStudioProjects) {
                if (!BOMUtils.isBusinessDataProject(eachProject)) {
                    /*
                     * It is obvious that these markers wont be present on
                     * Business Data projects so scan other projects to see if
                     * they have similar markers on them.
                     */
                    IMarker[] markers =
                            eachProject.findMarkers(marker.getType(),
                                    false,
                                    IResource.DEPTH_INFINITE);

                    List<IMarker> allMarkers = Arrays.asList(markers);

                    for (IMarker eachMarker : allMarkers) {
                        IResource resource = eachMarker.getResource();
                        if (resource != null) {
                            resourcesToTouch.add(resource);
                        }
                    }
                }
            }

            for (IResource eachResource : resourcesToTouch) {
                /*
                 * Touch all the resources which have this marker on them so
                 * that the are re-built and re-validated.
                 */
                eachResource.touch(new NullProgressMonitor());
            }

        } catch (CoreException e1) {
            DaaActivator.getDefault().getLogger().error(e1);
        }
    }
}
