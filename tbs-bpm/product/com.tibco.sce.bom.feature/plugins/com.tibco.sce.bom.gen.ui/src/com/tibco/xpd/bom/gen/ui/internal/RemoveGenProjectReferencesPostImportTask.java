/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.bom.gen.ui.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;

import com.tibco.xpd.resources.ui.imports.IPostImportProjectTask;

/**
 * Post import task to remove the .bds / .js generated project dependencies
 * 
 * @author aallway
 * @since 11 Jan 2012
 */
public class RemoveGenProjectReferencesPostImportTask implements
        IPostImportProjectTask {

    /**
     * @see com.tibco.xpd.resources.ui.imports.IPostImportProjectTask#runPostImportTask(org.eclipse.core.resources.IProject,
     *      org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param project
     * @param monitor
     * @throws CoreException
     */
    @Override
    public void runPostImportTask(IProject project, IProgressMonitor monitor)
            throws CoreException {
        try {
            IProjectDescription description = project.getDescription();

            if (description != null) {
                IProject[] referencedProjects =
                        description.getReferencedProjects();

                if (referencedProjects != null && referencedProjects.length > 0) {

                    List<IProject> keptReferences = new ArrayList<IProject>();

                    for (IProject refProject : referencedProjects) {

                        String refName = refProject.getName();

                        boolean isGeneratedProject = false;

                        if (refName.startsWith(".")) { //$NON-NLS-1$
                            if (refName.endsWith(".bds") || refName.endsWith(".js")) { //$NON-NLS-1$ //$NON-NLS-2$
                                isGeneratedProject = true;
                            }
                        }

                        if (!isGeneratedProject) {
                            keptReferences.add(refProject);
                        }
                    }

                    if (keptReferences.size() != referencedProjects.length) {
                        monitor.beginTask(Messages.RemoveGenProjectReferencesPostImportTask_RemovingGeneratedProjectReferences_message,
                                1);

                        description.setReferencedProjects(keptReferences
                                .toArray(new IProject[0]));

                        project.setDescription(description,
                                new SubProgressMonitor(monitor, 1));

                        monitor.worked(1);
                    }
                }

            }
        } finally {
            monitor.done();
        }
    }

}
