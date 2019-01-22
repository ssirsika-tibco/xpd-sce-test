/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.bx.validation.rules;

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

import com.tibco.bx.validation.BxValidationPlugin;
import com.tibco.xpd.bom.wsdltransform.builder.WsdlToBomBuilder;
import com.tibco.xpd.validation.engine.WorkspaceResourceValidator;
import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * Validates if the property "DeferFileDelete" has been set to "true" on
 * generated BOM folder.
 * 
 * @author jarciuch
 * @since 17 Apr 2014
 */
public class SvnDeferFileDeleteOnBomGenFolderRule implements
        WorkspaceResourceValidator {

    public static final String ISSUE = "bx.svnDeferFileDeleteOnBomGenFolder"; //$NON-NLS-1$ 

    private IProject project;

    public SvnDeferFileDeleteOnBomGenFolderRule() {
    }

    /**
     * @see com.tibco.xpd.validation.engine.WorkspaceResourceValidator#validate(com.tibco.xpd.validation.provider.IValidationScope,
     *      org.eclipse.core.resources.IResource)
     * 
     * @param scope
     * @param resource
     */
    public void validate(IValidationScope scope, IResource resource) {
        if (resource instanceof IFolder) {
            IProject project = resource.getProject();
            IFolder bomGenSf =
                    WsdlToBomBuilder.getGeneratedBOMFolder(project, false);
            if (resource.equals(bomGenSf)) {
                RepositoryProvider repositoryProvider =
                        RepositoryProvider.getProvider(project);
                if (repositoryProvider != null) {
                    Subscriber repoSubscriber =
                            repositoryProvider.getSubscriber();
                    if (repoSubscriber != null) {

                        try {
                            if (bomGenSf != null
                                    && repoSubscriber.isSupervised(bomGenSf)) {
                                if (isSvnFolderWithoutDeferFileDeleteProperty(bomGenSf)) {
                                    scope.createIssue(ISSUE,
                                            bomGenSf.getFullPath()
                                                    .toPortableString(),
                                            project.getName(),
                                            Arrays.asList(bomGenSf.getName(),
                                                    project.getName()));
                                }
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
     * Checks if folder is managed by SVN and has not set "DeferFileDelete" SVN
     * property set with value of 'true'.
     */
    private boolean isSvnFolderWithoutDeferFileDeleteProperty(IFolder folder) {
        ISVNLocalResource svnFolder =
                SVNWorkspaceRoot.getSVNResourceFor(folder);
        if (svnFolder != null) {
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
                BxValidationPlugin.getDefault().getLogger().error(e);
            }
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.validation.engine.WorkspaceResourceValidator#setProject(org.eclipse.core.resources.IProject)
     * 
     * @param project
     */
    public void setProject(IProject project) {
        this.project = project;
    }

}
