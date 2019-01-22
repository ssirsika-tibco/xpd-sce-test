/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.bom.resources.internal.businessdata;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.resources.util.ProjectUtil;

/**
 * Business Data project nature.
 * 
 * @author njpatel
 */
public class BusinessDataProjectNature implements IProjectNature {

    public static final String ID = BOMResourcesPlugin.PLUGIN_ID
            + ".businessDataProjectNature"; //$NON-NLS-1$

    private static final String VALIDATION_BUILDER_ID =
            "com.tibco.xpd.validation.validationBuilder"; //$NON-NLS-1$

    private IProject project;

    @Override
    public void configure() throws CoreException {
        if (project != null) {
            addBuilderToProject(project);
        }
    }

    /**
     * @param project2
     * @param id2
     * @throws CoreException
     */
    private void addBuilderToProject(IProject project) throws CoreException {
        // Add the business data builder on the top of the builder stack
        IProjectDescription description = project.getDescription();
        if (description != null) {
            ICommand[] buildSpec = description.getBuildSpec();
            List<ICommand> newBuildSpec =
                    new ArrayList<ICommand>(buildSpec.length + 1);

            for (ICommand spec : buildSpec) {
                newBuildSpec.add(spec);

                if (VALIDATION_BUILDER_ID.equals(spec.getBuilderName())) {
                    /*
                     * Add the Business Data Version builder after the
                     * validation builder and continue adding the rest of the
                     * configured builders to the new list.
                     */
                    ICommand cmd = description.newCommand();
                    cmd.setBuilderName(BusinessDataVersionBuilder.ID);

                    newBuildSpec.add(cmd);
                }
            }

            description.setBuildSpec(newBuildSpec
                    .toArray(new ICommand[newBuildSpec.size()]));

            project.setDescription(description, null);
        }
    }

    @Override
    public void deconfigure() throws CoreException {
        if (project != null) {
            ProjectUtil.removeBuilderFromProject(project,
                    BusinessDataVersionBuilder.ID);
        }
    }

    @Override
    public IProject getProject() {
        return project;
    }

    @Override
    public void setProject(IProject project) {
        this.project = project;
    }

}
