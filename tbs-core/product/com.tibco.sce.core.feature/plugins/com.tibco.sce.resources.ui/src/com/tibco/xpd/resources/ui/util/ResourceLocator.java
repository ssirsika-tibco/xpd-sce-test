/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.resources.ui.util;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.precompile.AbstractPreCompileContributor;
import com.tibco.xpd.resources.precompile.PreCompileContributorManager;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.internal.Messages;
import com.tibco.xpd.resources.util.ProjectUtil;

/**
 * Resource Utility for a given project.
 * 
 * @author bharge
 * @since 14 Nov 2014
 */
public final class ResourceLocator {

    private static Logger logger = XpdResourcesUIActivator.getDefault()
            .getLogger();

    /**
     * Use this method to get the dervied artefacts folder - .precompiled folder
     * location if the project is pre compiled, standard location otherwise.
     * 
     * If the given project is a pre compiled project then returns the target
     * resource from the pre compiled folder path, else return the given
     * standard source location.
     * 
     * @param project
     * @param sourceResource
     *            - actual/default location where the actual resource could be
     *            found if the project is not pre compiled
     * @return <code>IResource</code> target resource from pre compile folder
     *         path if the given project is pre compiled project, otherwise
     *         standard source location is returned
     * @throws CoreException
     */
    public static IResource getTargetResource(IProject project,
            IResource sourceResource) throws CoreException {

        IResource targetResource = null;

        boolean isPrecompiled = ProjectUtil.isPrecompiledProject(project);
        if (isPrecompiled) {

            /* Get all the enable pre compile contributions */
            List<AbstractPreCompileContributor> enablePreCompileContributors =
                    PreCompileContributorManager.getInstance()
                            .getPreCompileContributors();
            for (AbstractPreCompileContributor enablePreCompileContributor : enablePreCompileContributors) {

                IResource precompileSourceRes =
                        enablePreCompileContributor.getSourceResource(project);
                /*
                 * Match the source resource if it has been pre compiled!
                 */
                if (null != precompileSourceRes
                        && precompileSourceRes.equals(sourceResource)) {

                    /*
                     * Yes, we have found the contribution for the source we are
                     * interested in, Get the target resource from the
                     * contribution
                     */
                    IPath precompileTarget =
                            enablePreCompileContributor
                                    .getPrecompileTarget(project);
                    targetResource = project.getFolder(precompileTarget);
                    break;
                }
            }
            /*
             * If the target is not found or is not accessible then log the
             * error message
             */
            if (null == targetResource || !targetResource.isAccessible()) {

                logger.warn(String
                        .format(Messages.PreCompileProject_targetResourceNotFound_error_message,
                                targetResource.getFullPath()));
            }
            return targetResource;
        } else {

            return sourceResource;
        }
    }
}
