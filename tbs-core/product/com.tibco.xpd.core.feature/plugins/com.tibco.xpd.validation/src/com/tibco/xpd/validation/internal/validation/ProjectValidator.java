/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.validation.internal.validation;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.SpecialFolders;
import com.tibco.xpd.resources.util.CyclicDependencyException;
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
