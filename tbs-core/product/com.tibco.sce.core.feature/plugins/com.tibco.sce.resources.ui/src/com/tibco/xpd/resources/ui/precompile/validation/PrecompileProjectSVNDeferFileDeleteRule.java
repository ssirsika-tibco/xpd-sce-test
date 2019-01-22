/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.resources.ui.precompile.validation;

import java.util.Arrays;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.team.core.RepositoryProvider;
import org.eclipse.team.core.TeamException;
import org.eclipse.team.core.subscribers.Subscriber;
import org.tigris.subversion.subclipse.core.ISVNLocalResource;
import org.tigris.subversion.subclipse.core.SVNException;
import org.tigris.subversion.subclipse.core.resources.SVNWorkspaceRoot;
import org.tigris.subversion.svnclientadapter.ISVNProperty;

import com.tibco.xpd.resources.precompile.PreCompileContributorManager;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.engine.WorkspaceResourceValidator;
import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * Validates a Pre-compiled project to check if the property "DeferFileDelete"
 * has been set to "true"
 * 
 * @author bharge
 * @since 29 Apr 2015
 */
public class PrecompileProjectSVNDeferFileDeleteRule implements
        WorkspaceResourceValidator {

    public static final String ISSUE =
            "precompile.svnDeferFileDeleteOnPrecompileProjectFolder"; //$NON-NLS-1$ 

    IProject project;

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

            IFolder precompileFolder =
                    project.getFolder(PreCompileContributorManager
                            .getInstance().PRECOMPILE_OUTPUTFOLDER_NAME);

            if (precompileFolder.exists()) {

                RepositoryProvider repositoryProvider =
                        RepositoryProvider.getProvider(project);
                if (null != repositoryProvider) {

                    Subscriber repoSubscriber =
                            repositoryProvider.getSubscriber();
                    if (null != repoSubscriber) {

                        try {
                            if (repoSubscriber.isSupervised(precompileFolder)) {

                                if (isSvnFolderWithoutDeferFileDeleteProperty(precompileFolder)) {

                                    scope.createIssue(ISSUE,
                                            precompileFolder.getFullPath()
                                                    .toPortableString(),
                                            project.getName(),
                                            Arrays.asList(precompileFolder
                                                    .getName(), project
                                                    .getName()));
                                }
                            }
                        } catch (TeamException e) {

                            ValidationActivator.getDefault().getLogger()
                                    .error(e);
                        }
                    }
                }
            }
        }
    }

    /**
     * Checks if folder is managed by SVN and has not set "DeferFileDelete" SVN
     * property set with value of 'true'.
     */
    private boolean isSvnFolderWithoutDeferFileDeleteProperty(IFolder folder) {

        ISVNLocalResource svnFolder =
                SVNWorkspaceRoot.getSVNResourceFor(folder);
        if (null != svnFolder) {

            try {

                if (svnFolder.isManaged()) {

                    ISVNProperty deferFileDelete =
                            svnFolder.getSvnProperty("DeferFileDelete"); //$NON-NLS-1$
                    if (deferFileDelete == null
                            || !"true".equalsIgnoreCase(deferFileDelete.getValue())) { //$NON-NLS-1$

                        return true;
                    }
                }
            } catch (SVNException e) {

                ValidationActivator.getDefault().getLogger().error(e);
            }
        }
        return false;
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
