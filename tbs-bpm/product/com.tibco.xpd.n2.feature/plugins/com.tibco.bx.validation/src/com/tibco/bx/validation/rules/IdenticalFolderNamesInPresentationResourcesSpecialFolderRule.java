/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.bx.validation.rules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.validation.engine.WorkspaceResourceValidator;
import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * Validation rule that forces case of identically named folders in
 * "Presentation Resources" folder to be the same.
 * 
 * @author sajain
 * @since Jan 31, 2014
 */
public class IdenticalFolderNamesInPresentationResourcesSpecialFolderRule
        implements WorkspaceResourceValidator {

    /** Presentation special folder kind. */
    public static final String PRESENTATION_SPECIAL_FOLDER_KIND =
            "presentation"; //$NON-NLS-1$

    private static final String INVALID_ISSUE_ID =
            "bx.invalidFolderNameInPresentationResources"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.engine.WorkspaceResourceValidator#validate(com.tibco.xpd.validation.provider.IValidationScope,
     *      org.eclipse.core.resources.IResource)
     * 
     * @param scope
     * @param resource
     */
    public void validate(IValidationScope scope, IResource resource) {
        // TODO Auto-generated method stub
        if (resource instanceof IProject && resource.isAccessible()) {

            /*
             * Get the current project.
             */
            IProject currentProject = (IProject) resource;

            /*
             * Fetch all the resources present in "Presentation Resources"
             * special folder(s) of all the projects.
             */
            List<IResource> relatedPRSpecialFolderResources =
                    getrelatedPRSpecialFolderResources(currentProject);

            /*
             * Fetch the resources present in "Presentation Resources" special
             * folder(s) of current project.
             */
            List<IResource> currentProjectResources =
                    getPresentationResourcesSpecificResources(currentProject);

            /*
             * Check if there are any resources with identical names, but
             * different cases. If there are, then raise a validation marker.
             */
            for (IResource eachCurrentProjectResource : currentProjectResources) {
                String eachCurrentProjectResourceSFRelativePath =
                        SpecialFolderUtil
                                .getSpecialFolderRelativePath(eachCurrentProjectResource,
                                        PRESENTATION_SPECIAL_FOLDER_KIND,
                                        false).toString();

                for (IResource eachAllProjectResource : relatedPRSpecialFolderResources) {
                    if (eachCurrentProjectResourceSFRelativePath
                            .equalsIgnoreCase(SpecialFolderUtil
                                    .getSpecialFolderRelativePath(eachAllProjectResource,
                                            PRESENTATION_SPECIAL_FOLDER_KIND,
                                            false).toString())
                            && !eachCurrentProjectResourceSFRelativePath
                                    .equals(SpecialFolderUtil
                                            .getSpecialFolderRelativePath(eachAllProjectResource,
                                                    PRESENTATION_SPECIAL_FOLDER_KIND,
                                                    false).toString())) {
                        Collection<String> additionalArgumentsCollection =
                                new ArrayList<String>();
                        additionalArgumentsCollection
                                .add(eachCurrentProjectResourceSFRelativePath);
                        scope.createIssue(INVALID_ISSUE_ID,
                                eachCurrentProjectResourceSFRelativePath,
                                eachCurrentProjectResource
                                        .getProjectRelativePath().toString(),
                                additionalArgumentsCollection);
                    }
                }
            }
        }
    }

    /**
     * Returns the list of all the resources which are there in the
     * "Presentation Resources" special folder(s) of all the projects in the
     * workspace.
     * 
     * @return List of resources specific to "Presentation Resources" special
     *         folder(s) of all the projects.
     */
    private List<IResource> getrelatedPRSpecialFolderResources(
            IProject currentProject) {
        /*
         * Get all referenced projects in the workspace.
         */
        Set<IProject> referencedProjects = new HashSet<IProject>();
        referencedProjects =
                ProjectUtil.getReferencedProjectsHierarchy(currentProject,
                        new HashSet<IProject>(),
                        true);

        /*
         * Fetch all the resources present in "Presentation Resources" special
         * folder(s) of all the referenced projects.
         */
        List<IResource> allPRSpecialFolderResources =
                new ArrayList<IResource>();

        for (IProject eachProject : referencedProjects) {
            if (allPRSpecialFolderResources == null) {
                allPRSpecialFolderResources =
                        SpecialFolderUtil
                                .getAllDeepResourcesInSpecialFolderOfKind(eachProject,
                                        PRESENTATION_SPECIAL_FOLDER_KIND,
                                        null,
                                        true);
            } else {
                allPRSpecialFolderResources.addAll(SpecialFolderUtil
                        .getAllDeepResourcesInSpecialFolderOfKind(eachProject,
                                PRESENTATION_SPECIAL_FOLDER_KIND,
                                null,
                                true));
            }
        }

        return allPRSpecialFolderResources;
    }

    /**
     * Returns the list of all the resources which are there in the
     * "Presentation Resources" special folder(s) of the specified project.
     * 
     * @param project
     * @return List of resources specific to "Presentation Resources" special
     *         folder(s) of the specified project.
     */
    private List<IResource> getPresentationResourcesSpecificResources(
            IProject project) {
        List<IResource> currentProjectResources =
                SpecialFolderUtil
                        .getAllDeepResourcesInSpecialFolderOfKind(project,
                                PRESENTATION_SPECIAL_FOLDER_KIND,
                                null,
                                true);
        return currentProjectResources;
    }

    /**
     * @see com.tibco.xpd.validation.engine.WorkspaceResourceValidator#setProject(org.eclipse.core.resources.IProject)
     * 
     * @param project
     */
    public void setProject(IProject project) {
        // TODO Auto-generated method stub

    }

}
