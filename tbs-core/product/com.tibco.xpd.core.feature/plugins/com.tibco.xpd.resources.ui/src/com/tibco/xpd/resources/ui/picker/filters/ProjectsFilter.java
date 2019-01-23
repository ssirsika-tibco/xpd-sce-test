/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.resources.ui.picker.filters;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IFilter;

import com.tibco.xpd.resources.ui.picker.PickerItem;

/**
 * Limits the selected items to only these coming from resources in the provided
 * projects.
 * 
 * @since 3.3.1
 * @author Jan Arciuchiewicz
 */
public class ProjectsFilter implements IFilter {

    private final Set<String> projectNames = new HashSet<String>();

    /**
     * Indexer item attribute name that stores the project name
     */
    private static final String ATT_PROJECT_NAME = "internal_project"; //$NON-NLS-1$

    /**
     * Creates new projects filter.
     * 
     * @param queryProjects
     *            collection of projects. Only items from these resources will
     *            be selected.
     */
    public ProjectsFilter(Collection<IProject> queryProjects) {
        for (IProject project : queryProjects) {
            projectNames.add(project.getName());
        }
    }

    /**
     * Creates new projects filter.
     * 
     * @param queryProjects
     *            collection of projects. Only items from these resources will
     *            be selected.
     */
    public ProjectsFilter(IProject... queryProjects) {
        this(Arrays.asList(queryProjects));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean select(Object toTest) {
        if (toTest instanceof PickerItem) {
            String projectName = ((PickerItem) toTest).get(ATT_PROJECT_NAME);
            // If project name is null then it's an element from a "built-in"
            // resource, eg base primitive type
            return projectName == null
                    || (projectName != null && projectNames
                            .contains(projectName));
        }
        return true;
    }

}
