/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.resources.refactoring;

import org.eclipse.core.resources.IProject;
import org.eclipse.ltk.core.refactoring.participants.RefactoringArguments;

import com.tibco.xpd.resources.projectconfig.ProjectStatus;

/**
 * Project lifecycle refactoring argument that is passed to each participant.
 * This will provide the details of the project being refactored and it's new
 * details.
 * 
 * @author njpatel
 * 
 */
public final class ProjectVersionRefactoringArguments extends
        RefactoringArguments {
    private final IProject project;

    private final String id;

    private final String version;

    private final ProjectStatus status;

    private String[] destinations;

    private static final String[] EMPTY_DESTINATIONS = new String[0];

    /**
     * Project version refactoring argument for the participants.
     * 
     * @param project
     *            project being refactored
     * @param id
     *            project id
     * @param version
     *            project version
     * @param status
     *            project status
     * @param destinations
     *            global destinations
     */
    public ProjectVersionRefactoringArguments(IProject project, String id,
            String version, ProjectStatus status, String[] destinations) {
        this.project = project;
        this.id = id;
        this.version = version;
        this.status = status;
        this.destinations = destinations;
    }

    /**
     * Get the project being refactored.
     * 
     * @return
     */
    public IProject getProject() {
        return project;
    }

    /**
     * Get the project id.
     * 
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * Get the project version.
     * 
     * @return
     */
    public String getVersion() {
        return version;
    }

    /**
     * Get the project status.
     * 
     * @return
     */
    public ProjectStatus getStatus() {
        return status;
    }

    /**
     * Get the project's global destinations.
     * 
     * @return array of destination ids or empty array if none set.
     */
    public String[] getDestinations() {
        return destinations != null ? destinations : EMPTY_DESTINATIONS;
    }
}