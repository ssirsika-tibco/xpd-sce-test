/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.validation.internal.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.precompile.PreCompileUtil;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.SpecialFolders;
import com.tibco.xpd.resources.util.CyclicDependencyException;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.ProjectUtil2;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.internal.ValidationManager;
import com.tibco.xpd.validation.internal.engine.ValidationEngine;
import com.tibco.xpd.validation.provider.IIssue;
import com.tibco.xpd.validation.provider.IssueInfo;

/**
 * Validates cycles in projects and also project references (if a referenced
 * project is missing or closed then an error marker will be added to the
 * project).
 * 
 * @author njpatel
 * 
 */
public class ProjectValidator {

    /**
     * Key in the additional info of the marker to indicate which project
     * reference it relates to.
     */
    public static final String REF_PROJECT = "refProject"; //$NON-NLS-1$ 

    /**
     * Marker attribute to store the reference project
     */
    private static final String ATT_REFPROJ = "refProject"; //$NON-NLS-1$

    private static final String MARKER_ID =
            ValidationActivator.PROJECT_MARKER_ID;

    private static final String CYCLEDPROJECT_ISSUE_ID = "cycledProject.issue"; //$NON-NLS-1$

    private final IssueInfo cycledProject_issue;

    private static final String SPECIAL_FOLDER_MISSING_ISSUE_ID =
            "specialFolderMissing.issue"; //$NON-NLS-1$

    private final IssueInfo specialFolderMissing_issue;

    private static final String REFERENCED_PROJECT_NOTFOUND_ISSUE_ID =
            "referencedProject.notfound.issue";//$NON-NLS-1$

    private final IssueInfo referencedProject_notFound_issue;

    private static final String REFERENCED_PROJECT_CLOSED_ISSUE_ID =
            "referencedProject.closed.issue";//$NON-NLS-1$

    private final IssueInfo referencedProject_closed_issue;

    private static final String REFERRED_PROJECT_PRE_COMPILE_ISSUE_ID =
            "referredProject.precompile.notset.issue"; //$NON-NLS-1$

    private final IssueInfo referredProject_preCompile_notSet_issue;

    /**
     * Constructor.
     * 
     * @param project
     *            current project being built.
     */
    public ProjectValidator() {
        ValidationEngine engine =
                ValidationManager.getInstance().getValidationEngine();
        cycledProject_issue = engine.getIssueInfo(CYCLEDPROJECT_ISSUE_ID);
        Assert.isNotNull(cycledProject_issue,
                String.format("Issue with id '%s' not found.", CYCLEDPROJECT_ISSUE_ID)); //$NON-NLS-1$
        referencedProject_notFound_issue =
                engine.getIssueInfo(REFERENCED_PROJECT_NOTFOUND_ISSUE_ID);
        Assert.isNotNull(referencedProject_notFound_issue,
                String.format("Issue with id '%s' not found.", REFERENCED_PROJECT_NOTFOUND_ISSUE_ID)); //$NON-NLS-1$
        referencedProject_closed_issue =
                engine.getIssueInfo(REFERENCED_PROJECT_CLOSED_ISSUE_ID);
        Assert.isNotNull(referencedProject_closed_issue,
                String.format("Issue with id '%s' not found.", REFERENCED_PROJECT_CLOSED_ISSUE_ID)); //$NON-NLS-1$
        specialFolderMissing_issue =
                engine.getIssueInfo(SPECIAL_FOLDER_MISSING_ISSUE_ID);
        Assert.isNotNull(specialFolderMissing_issue,
                String.format("Issue with id '%s' not found.", SPECIAL_FOLDER_MISSING_ISSUE_ID)); //$NON-NLS-1$
        referredProject_preCompile_notSet_issue =
                engine.getIssueInfo(REFERRED_PROJECT_PRE_COMPILE_ISSUE_ID);
        Assert.isNotNull(referredProject_preCompile_notSet_issue,
                String.format("Issue with id '%s' not found.", //$NON-NLS-1$
                        REFERRED_PROJECT_PRE_COMPILE_ISSUE_ID));
    }

    /**
     * Validate the given project. It checks if the project has dependency
     * cycles.
     * 
     * @param res
     *            <code>IProject</code> to validate.
     */
    public void validate(IProject project) {
        if (project != null) {
            /*
             * Remove existing marker from the project
             */
            try {
                if (project.isAccessible()) {
                    project.deleteMarkers(MARKER_ID, true, IResource.DEPTH_ZERO);

                    /*
                     * Check for cyclic dependencies
                     */
                    try {
                        ProjectUtil2.getReferencedProjectsHierarchy(project,
                                false);
                    } catch (CyclicDependencyException e) {
                        // There is a cyclic reference
                        createMarker(project, cycledProject_issue, null);
                    }

                    /*
                     * Check for valid referenced projects
                     */
                    validateProjectReferences(project);

                    /*
                     * Validate if the pre-compile flag is set on all the
                     * referenced/referencing projects for this project
                     */
                    validatePreCompileOnProjectReferences(project);

                    /*
                     * Validate that all special folders are present
                     */
                    validateSpecialFolders(project);
                }
            } catch (CoreException e) {
                ValidationActivator.getDefault().getLogger().error(e);
            }
        }
    }

    /**
     * Validate the project references.
     * 
     * @param project
     * @throws CoreException
     */
    private void validateProjectReferences(IProject project)
            throws CoreException {
        for (IProject ref : project.getReferencedProjects()) {
            if (!ref.isAccessible()) {
                createMarker(project,
                        ref.exists() ? referencedProject_closed_issue
                                : referencedProject_notFound_issue,
                        ref,
                        ref.getName());
            }
        }
    }

    /**
     * Validate the referenced and referencing projects if the pre-compile flag
     * is set
     * 
     * @param project
     * @throws CoreException
     */
    private void validatePreCompileOnProjectReferences(IProject project)
            throws CoreException {

        boolean isPreCompiled = ProjectUtil.isPrecompiledProject(project);

        List<IResource> precompileResources = new ArrayList<IResource>();
        PreCompileUtil.getEnabledSourceArtifacts(project, precompileResources);

        if (!isPreCompiled && !precompileResources.isEmpty()) {

            /*
             * This project is not pre-compiled but is being referenced from a
             * pre-compiled project. So get all referencing projects and if the
             * referencing project is pre-compiled then raise a problem marker
             */
            Set<IProject> referencingProjects =
                    ProjectUtil.getReferencingProjectsHierarchy(project,
                            new HashSet<IProject>());
            Set<IProject> allProjects = new HashSet<IProject>();
            allProjects.addAll(referencingProjects);
            /* Go thru all the references and check if pre-compile flag is set. */
            for (IProject refdProject : allProjects) {

                boolean preCompileSetOnRefdProject =
                        ProjectUtil.isPrecompiledProject(refdProject);
                /*
                 * Referencing project IS pre-compile but this is NOT so raise a
                 * problem
                 */
                if (preCompileSetOnRefdProject) {

                    createMarkerForPrecompileIssue(project,
                            referredProject_preCompile_notSet_issue,
                            refdProject,
                            refdProject.getName());
                }
            }
        }
    }

    /**
     * Validate that all Special Folders have their underlying folders in the
     * workspace.
     * 
     * @param project
     * @throws CoreException
     * @since 3.5.3
     */
    private void validateSpecialFolders(IProject project) throws CoreException {
        ProjectConfig config =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);
        if (config != null) {
            SpecialFolders specialFolders = config.getSpecialFolders();

            if (specialFolders != null) {
                for (SpecialFolder sf : specialFolders.getFolders()) {
                    if (sf.getFolder() == null || !sf.getFolder().exists()) {
                        // Found a special folder without a folder in the
                        // workspace
                        createMarker(project,
                                specialFolderMissing_issue,
                                null,
                                project.getName());
                        break;
                    }
                }
            }
        }
    }

    /**
     * Create a marker with the given issue on the project. This is different
     * from createMarker() in that this method adds a key to the marker that
     * uniquely represents this marker that can be used in other places to
     * analyze or ignore this marker (for instance in EnablePrecompileWizard
     * class while validating in the wizard page)
     * 
     * @param project
     * @param issue
     * @param refProject
     *            name of the referenced project, can be <code>null</code>.
     * @param args
     *            additional arguments to add to the issue message
     * @return
     * @throws CoreException
     */
    private IMarker createMarkerForPrecompileIssue(IProject project,
            IssueInfo issue, IProject refProject, Object... args)
            throws CoreException {

        Map<String, Object> attributes = new HashMap<String, Object>();
        String msg;
        if (args.length > 0) {

            msg = String.format(issue.getMessage(), args);
        } else {

            msg = issue.getMessage();
        }
        attributes.put(IMarker.MESSAGE, msg);
        attributes.put(IMarker.SEVERITY, issue.getSeverity());
        attributes.put(IMarker.LOCATION, "/"); //$NON-NLS-1$
        attributes.put(IIssue.ID, issue.getId());
        /*
         * Additional key being added to the marker so that this key can be used
         * for analysis in other validations
         */
        attributes
                .put(ProjectUtil.REFERRED_PROJECT_IS_NOT_PRECOMPILED_ISSUE_KEY,
                        ProjectUtil.REFERRED_PROJECT_IS_NOT_PRECOMPILED_ISSUE_KEY);
        if (refProject != null) {

            attributes.put(ATT_REFPROJ, refProject.getName());
        }

        IMarker marker = project.createMarker(MARKER_ID);
        marker.setAttributes(attributes);
        return marker;
    }

    /**
     * Create a marker with the given issue on the project.
     * 
     * @param project
     * @param issue
     * @param refProject
     *            name of the referenced project, can be <code>null</code>.
     * @param args
     *            additional arguments to add to the issue message
     * @return
     * @throws CoreException
     */
    private IMarker createMarker(IProject project, IssueInfo issue,
            IProject refProject, Object... args) throws CoreException {

        Map<String, Object> attributes = new HashMap<String, Object>();
        String msg;
        if (args.length > 0) {
            msg = String.format(issue.getMessage(), args);
        } else {
            msg = issue.getMessage();
        }
        attributes.put(IMarker.MESSAGE, msg);
        attributes.put(IMarker.SEVERITY, issue.getSeverity());
        attributes.put(IMarker.LOCATION, "/"); //$NON-NLS-1$
        attributes.put(IIssue.ID, issue.getId());
        if (refProject != null) {
            attributes.put(ATT_REFPROJ, refProject.getName());
        }
        IMarker marker = project.createMarker(MARKER_ID);
        marker.setAttributes(attributes);
        return marker;
    }

    /**
     * Get the referenced project, if any, stored in the given marker.
     * 
     * @param marker
     * @return project if the reference attribute is stored in the marker,
     *         <code>null</code> otherwise.
     * @throws CoreException
     * @since 3.6
     */
    public static IProject getReferencedProjectStoredInMarker(IMarker marker)
            throws CoreException {
        if (marker != null && marker.exists()) {
            Object value = marker.getAttribute(ATT_REFPROJ);
            if (value instanceof String) {
                return ResourcesPlugin.getWorkspace().getRoot()
                        .getProject((String) value);
            }
        }

        return null;
    }

}
