/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.destinations.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.destinations.DestinationsActivator;
import com.tibco.xpd.destinations.GlobalDestinationUtil;
import com.tibco.xpd.destinations.preferences.DestinationPreferences;
import com.tibco.xpd.resources.util.CyclicDependencyException;
import com.tibco.xpd.resources.util.ProjectUtil2;
import com.tibco.xpd.resources.util.XpdConsts;
import com.tibco.xpd.validation.engine.WorkspaceResourceValidator;
import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * Validates projects in a workspace to check if the expected/required
 * destinations are available on a project or its referenced project(s).
 * 
 * @author bharge
 * @since 22 Sep 2011
 */
public abstract class AbstractRequiredDestinationsValidator implements
        WorkspaceResourceValidator {

    /*
     * Issue additional infokey for String destination Id of destination in
     * question.
     */
    public static final String PROJECT_DEST_ADDINFOKEY = "project.destination"; //$NON-NLS-1$ 

    /**
     * @see com.tibco.xpd.validation.engine.WorkspaceResourceValidator#setProject(org.eclipse.core.resources.IProject)
     * 
     * @param project
     */
    @Override
    public void setProject(IProject project) {
    }

    protected abstract String getRequiredDestinationId();

    protected abstract String getIssueId();

    /**
     * This is introduced to check the BPM destination is available for a BDP
     * project referenced from a Decisions project
     * 
     * @return Return empty string as the default behaviour
     */
    protected String getRequiredDestNameForRefProj(IProject project) {

        return ""; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.validation.engine.WorkspaceResourceValidator#validate(com.tibco.xpd.validation.provider.IValidationScope,
     *      org.eclipse.core.resources.IResource)
     * 
     * @param scope
     * @param resource
     */
    @Override
    public void validate(IValidationScope scope, IResource resource) {
        if (resource instanceof IProject && resource.isAccessible()) {

            IProject validateProject = (IProject) resource;

            checkForReferencedProjects(scope, validateProject);

        }
    }

    /**
     * 
     * @param scope
     * @param validateProject
     */
    protected void checkForReferencedProjects(IValidationScope scope,
            IProject validateProject) {

        DestinationPreferences preferences =
                DestinationsActivator.getDefault().getDestinationPreferences();

        try {

            Set<String> enabledRequiredDestinations = new HashSet<String>();

            Set<String> validateEnabledDestNames =
                    GlobalDestinationUtil
                            .getEnabledGlobalDestinations(validateProject, true);

            for (String destName : validateEnabledDestNames) {
                if (getRequiredDestinationId()
                        .equals(preferences.getGlobalDestinationId(destName))) {
                    enabledRequiredDestinations.add(destName);
                }
            }

            Set<IProject> referencedProjects =
                    ProjectUtil2
                            .getReferencedProjectsHierarchy(validateProject,
                                    true);

            for (IProject project : referencedProjects) {
                try {
                    if (project.hasNature(XpdConsts.PROJECT_NATURE_ID)) {
                        Set<String> enabledDestNames =
                                GlobalDestinationUtil
                                        .getEnabledGlobalDestinations(project,
                                                true);

                        for (String requiredDestName : enabledRequiredDestinations) {

                            createIssue(scope,
                                    validateProject,
                                    project,
                                    enabledDestNames,
                                    requiredDestName);
                        }
                        String reqDestNameForRefProj =
                                getRequiredDestNameForRefProj(project);
                        if (reqDestNameForRefProj.length() > 0) {

                            String reqDestName =
                                    GlobalDestinationUtil
                                            .getGlobalDestinationName(reqDestNameForRefProj);
                            createIssue(scope,
                                    validateProject,
                                    project,
                                    enabledDestNames,
                                    reqDestName);
                        }
                    }
                } catch (CoreException e) {
                }
            }

        } catch (CyclicDependencyException e1) {
        }

    }

    /**
     * @param scope
     * @param validateProject
     * @param project
     * @param enabledDestNames
     * @param requiredDestName
     */
    private void createIssue(IValidationScope scope, IProject validateProject,
            IProject project, Set<String> enabledDestNames,
            String requiredDestName) {

        if (!enabledDestNames.contains(requiredDestName)) {

            List<String> messages = new ArrayList<String>();
            messages.add(project.getName());
            messages.add(validateProject.getName());
            messages.add(requiredDestName);

            Map<String, String> addInfo = new HashMap<String, String>();
            addInfo.put(PROJECT_DEST_ADDINFOKEY, requiredDestName);

            scope.createIssue(getIssueId(),
                    validateProject.getName(),
                    validateProject.getFullPath().toString(),
                    messages,
                    addInfo);
        }
    }

}
