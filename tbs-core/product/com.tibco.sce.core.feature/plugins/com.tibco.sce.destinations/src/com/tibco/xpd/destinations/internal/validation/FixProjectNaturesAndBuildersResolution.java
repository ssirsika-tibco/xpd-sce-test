/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.destinations.internal.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNatureDescriptor;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.XpdConsts;
import com.tibco.xpd.validation.provider.IIssue;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validation.rules.ValidationUtil;

/**
 * 
 * 
 * @author aallway
 * @since 3.3 (27 Apr 2010)
 */
public class FixProjectNaturesAndBuildersResolution implements IResolution {

    private static final Logger LOG = XpdResourcesPlugin.getDefault()
            .getLogger();

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.resolutions.IResolution#run(org.eclipse.core
     * .resources.IMarker)
     */
    @Override
    public void run(final IMarker marker) throws ResolutionException {

        if (marker.getResource() instanceof IProject) {
            final IProject project = (IProject) marker.getResource();
            try {

                ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {

                    @Override
                    public void run(IProgressMonitor monitor)
                            throws CoreException {

                        if (RequiredNaturesRule.ISSUE_MISSING_NATURES.equals(marker
                                .getAttribute(IIssue.ID))) {
                            // Fix the missing natures problem
                            fixMissingNatures(marker, project);
                        } else {
                            // Fix the nature/builder sequence
                            fixProjectNaturesAndBuilders(project);
                        }
                    }
                },
                        null);

            } catch (CoreException e) {
                LOG.error(e, "Unable to add nature to project"); //$NON-NLS-1$
            }
        }
        return;
    }

    /**
     * Add the missing natures to the project.
     * 
     * @param marker
     * @param project
     * @throws CoreException
     */
    private void fixMissingNatures(IMarker marker, IProject project)
            throws CoreException {
        Properties info = ValidationUtil.getAdditionalInfo(marker);
        if (info != null) {
            Object value = info.get(RequiredNaturesRule.ATT_NATURES);
            if (value instanceof String) {
                List<String> natureIds = deserealizeList((String) value);

                fixMissingNatures(project, natureIds);
            }
        }
    }

    /**
     * Add the missing natures to the project.
     * <p>
     * Note that this will also fix nature and builder order after adding
     * missing natures.
     * 
     * @param project
     * @param missingNatureIds
     * @throws CoreException
     */
    public static void fixMissingNatures(IProject project,
            Collection<String> missingNatureIds) throws CoreException {
        if (!missingNatureIds.isEmpty()) {
            // Add the natures to the project
            for (String id : missingNatureIds) {
                ProjectUtil.addNature(project, id);
            }

            // Fix the sequence of natures/builders
            fixProjectNaturesAndBuilders(project);
        }
    }

    /**
     * Deserealize the given comma-separated string into a list
     * 
     * @param listStr
     * @return
     */
    private List<String> deserealizeList(String listStr) {
        List<String> values = null;

        if (listStr != null && listStr.length() > 0) {
            values = Arrays.asList(listStr.split(",")); //$NON-NLS-1$
        }

        return values != null ? values : new ArrayList<String>(0);
    }

    /**
     * Put the project natures and their builds back in correct sequence.
     * 
     * @param project
     * @throws CoreException
     */
    public static void fixProjectNaturesAndBuilders(IProject project)
            throws CoreException {
        if (project.isAccessible()) {
            IProjectDescription projectDescription = project.getDescription();

            List<String> topLevelNatures = getTopLevelNatures(project);

            if (!topLevelNatures.isEmpty()) {
                /*
                 * Remove all existing natures - this should remove all of their
                 * builders so that we can re-add them in the correct order.
                 * 
                 * BUT do not remove natures that do not have a contribution in
                 * current environment because we won't be able to re-add them!
                 * 
                 * Sid SCF-110: Also do not remove non-tibco derived natures (as
                 * we have no control over them and they may do odd things.)
                 */
                List<String> naturesToKeep = new ArrayList<String>();

                for (String nature : projectDescription.getNatureIds()) {

                    IProjectNatureDescriptor natureDescriptor =
                            project.getWorkspace().getNatureDescriptor(nature);
                    if (natureDescriptor == null) {
                        /* Keep this nature because we can't reload it! */
                        naturesToKeep.add(nature);

                    } else if (!RequiredNaturesRule
                            .isTibcoProjectNature(nature)) {
                        /* Keep this nature because it's not ours to mess with. */
                        naturesToKeep.add(nature);
                    }
                }

                projectDescription.setNatureIds(naturesToKeep
                        .toArray(new String[0]));
                project.setDescription(projectDescription, null);

                /*
                 * Now we are free to re-add any top level natures (natures that
                 * no other nature is dependent upon, in any order because there
                 * cannot be any inter-dependecy betwee these hence the builders
                 * __should__ be able to be added in any order.
                 */
                for (String natureId : topLevelNatures) {
                    ProjectUtil.addNature(project, natureId);
                }

            }

        }

        return;
    }

    /**
     * Returns a list of top level natures (natures in the project which no
     * other nature in the project depends upon)
     * 
     * @param project
     * @param topLevelNatures
     * @throws CoreException
     */
    private static List<String> getTopLevelNatures(IProject project)
            throws CoreException {
        List<String> topLevelNatures = new ArrayList<String>();

        /*
         * ALWAYS add XPD nature to project first (because EVERYTHING assumes
         * that that will be the first nature
         */
        topLevelNatures.add(XpdConsts.PROJECT_NATURE_ID);

        IProjectDescription projectDescription = project.getDescription();

        String[] natureIds = projectDescription.getNatureIds();
        if (natureIds != null) {
            /*
             * Create a list of all natures that are marked as requiredNatures
             * by other natures.
             */
            Set<String> dependencyNatures = new HashSet<String>();
            for (String natureId : natureIds) {
                /* Recursively add any nature that this nature requires. */
                gatherRequiredNatures(project, natureId, dependencyNatures);
            }

            /*
             * Now go thru the project natures again, any that don't appear in
             * the natureDependencies list are not depended upon by any other
             * nature - which means they can be re-added in any order to ensure
             * the build order remains correct.
             */
            for (String natureId : natureIds) {
                if (!dependencyNatures.contains(natureId)) {
                    topLevelNatures.add(natureId);
                }
            }
        }

        return topLevelNatures;
    }

    /**
     * Add natures required by given nature to list.
     * 
     * @param project
     * @param natureId
     * @param requiredNatures
     */
    private static void gatherRequiredNatures(IProject project,
            String natureId, Set<String> requiredNatures) {
        IProjectNatureDescriptor natureDescriptor =
                project.getWorkspace().getNatureDescriptor(natureId);
        if (natureDescriptor != null) {

            String[] requiredNatureIds =
                    natureDescriptor.getRequiredNatureIds();
            if (requiredNatureIds != null) {
                /* Add all natures required at this level. */
                for (String reqdNature : requiredNatureIds) {
                    if (!requiredNatures.contains(reqdNature)) {
                        requiredNatures.add(reqdNature);
                        gatherRequiredNatures(project,
                                reqdNature,
                                requiredNatures);
                    }
                }

            }
        }
        return;
    }

}
