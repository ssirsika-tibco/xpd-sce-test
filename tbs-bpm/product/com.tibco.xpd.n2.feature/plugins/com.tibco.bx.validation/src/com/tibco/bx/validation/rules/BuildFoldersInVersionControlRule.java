/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.bx.validation.rules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.team.core.RepositoryProvider;
import org.eclipse.team.core.TeamException;
import org.eclipse.team.core.subscribers.Subscriber;

import com.tibco.bx.validation.BxValidationPlugin;
import com.tibco.xpd.validation.engine.WorkspaceResourceValidator;
import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * Validate against the use of version control on the build folders...
 * <p>
 * <li>.bpm</li>
 * <li>.processOut</li>
 * <li>.forms</li>
 * <li>.bom2xsd</li>
 * <li>.tmp</li>
 * </p>
 * 
 * @author aallway
 * @since 20 Jan 2012
 */
public class BuildFoldersInVersionControlRule implements
        WorkspaceResourceValidator {

    public static final String ISSUE = "bx.buildFoldersInSVNValidator"; //$NON-NLS-1$ 

    private IProject project;

    public BuildFoldersInVersionControlRule() {
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
        if (resource instanceof IProject) {
            IProject project = (IProject) resource;

            RepositoryProvider repositoryProvider =
                    RepositoryProvider.getProvider(project);

            if (repositoryProvider != null) {
                Subscriber repoSubscriber = repositoryProvider.getSubscriber();

                if (repoSubscriber != null) {
                    List<IFolder> buildFolders = getBuildFolders(project);

                    for (IFolder buildFolder : buildFolders) {
                        try {
                            if (buildFolder.exists() && repoSubscriber.isSupervised(buildFolder)) {
                                scope.createIssue(ISSUE,
                                        project.getName(),
                                        project.getName(),
                                        Collections.singleton(project.getName()));

                                return;
                            }
                        } catch (TeamException e) {
                            BxValidationPlugin.getDefault().getLogger()
                                    .error(e);
                        }
                    }
                }

            }
        }
    }

    /**
     * @param project
     * @return The list of build folders that should not e under version
     *         control.
     */
    public static List<IFolder> getBuildFolders(IProject project) {
        ArrayList<IFolder> folders = new ArrayList<IFolder>();

        folders.add(project.getFolder(".bpm")); //$NON-NLS-1$
        folders.add(project.getFolder(".processOut")); //$NON-NLS-1$
        folders.add(project.getFolder(".forms")); //$NON-NLS-1$
        folders.add(project.getFolder(".tmp")); //$NON-NLS-1$

        return folders;

    }

    /**
     * @see com.tibco.xpd.validation.engine.WorkspaceResourceValidator#setProject(org.eclipse.core.resources.IProject)
     * 
     * @param project
     */
    @Override
    public void setProject(IProject project) {
        this.project = project;
    }

}
