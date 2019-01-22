/*
 * Copyright (c) TIBCO Software Inc. 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.resources.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.command.AbstractCommand;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.internal.Messages;

/**
 * Command to set a project reference. This command will also handle undo which
 * will unset the reference.
 * 
 * @author njpatel
 */
public class SetProjectReferenceCommand extends AbstractCommand {

    private final IProject project;

    private final IProject refProject;

    private boolean referenceSet;

    public SetProjectReferenceCommand(IProject project, IProject refProject) {
        this.project = project;
        this.refProject = refProject;
        this.referenceSet = false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.common.command.Command#execute()
     */
    public void execute() {
        try {
            IProjectDescription description = project.getDescription();
            IProject[] referencedProjects = description.getReferencedProjects();
            // Check if the reference is already set - shouldn't be
            for (IProject ref : referencedProjects) {
                if (referenceSet = ref.equals(refProject)) {
                    break;
                }
            }

            // Set the reference
            if (!referenceSet) {
                IProject[] newList =
                        new IProject[referencedProjects.length + 1];
                System.arraycopy(referencedProjects,
                        0,
                        newList,
                        0,
                        referencedProjects.length);
                newList[referencedProjects.length] = refProject;
                description.setReferencedProjects(newList);
                project.setDescription(description, new NullProgressMonitor());
            }
        } catch (CoreException e) {
            XpdResourcesPlugin
                    .getDefault()
                    .getLogger()
                    .error(e,
                            String
                                    .format(Messages.SetProjectReferenceCommand_cannotAccessProjectDescription_error_message,
                                            project.getName()));
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.common.command.Command#redo()
     */
    public void redo() {
        execute();
    }

    @Override
    protected boolean prepare() {
        if (project != null && project.isAccessible() && refProject != null
                && refProject.isAccessible()) {
            // Make sure the refProject is not already referenced
            boolean isReferenced = false;
            try {
                for (IProject ref : project.getReferencedProjects()) {
                    if (isReferenced = (ref == refProject)) {
                        break;
                    }
                }
            } catch (CoreException e) {
                // Problem accessing the referenced projects so command cannot
                // run
                return false;
            }
            return !isReferenced;
        }

        return false;
    }

    @Override
    public void undo() {
        if (referenceSet) {
            // Unreference the project
            try {
                IProjectDescription description = project.getDescription();
                if (description != null) {
                    IProject[] referencedProjects =
                            description.getReferencedProjects();
                    List<IProject> newList = new ArrayList<IProject>();
                    for (IProject ref : referencedProjects) {
                        if (!ref.equals(refProject)) {
                            newList.add(ref);
                        }
                    }
                    description.setReferencedProjects(newList
                            .toArray(new IProject[newList.size()]));
                    project.setDescription(description,
                            new NullProgressMonitor());
                }
            } catch (CoreException e) {
                XpdResourcesPlugin
                        .getDefault()
                        .getLogger()
                        .error(e,
                                String
                                        .format(Messages.SetProjectReferenceCommand_cannotAccessProjectDescription_error_message,
                                                project.getName()));
            }
            referenceSet = false;
        }
    }
}