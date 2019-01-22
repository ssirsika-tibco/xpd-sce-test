/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.worklistfacade.resource.validation;

import java.util.Arrays;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.AssetType;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.XpdConsts;
import com.tibco.xpd.validation.engine.WorkspaceResourceValidator;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.worklistfacade.resource.WorkListFacadeResourcePlugin;

/**
 * Validation to raise error if a Given WLF Project contains other visible
 * asset.
 * 
 * @author aprasad
 * @since 09-Jan-2014
 */
public class WorkListFacadeProjectValidation implements
        WorkspaceResourceValidator {

    /**
     * id of the WLF asset.
     */
    private static final String WORK_LIST_FACADE_ASSET_ID =
            "com.tibco.xpd.asset.workListFacade"; //$NON-NLS-1$

    private static final String ISSUE_CANT_CONTAIN_OTHER_VISIBLE_ASSETS =
            "workListFacadeProject.other.assets.not.allowed.issue"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.engine.WorkspaceResourceValidator#validate(com.tibco.xpd.validation.provider.IValidationScope,
     *      org.eclipse.core.resources.IResource)
     * 
     * @param scope
     * @param resource
     */
    @Override
    public void validate(IValidationScope scope, IResource resource) {

        if (resource instanceof IProject && resource.isAccessible()
                && ProjectUtil.isStudioProject((IProject) resource)) {
            IProject project = (IProject) resource;

            ProjectConfig config =
                    XpdResourcesPlugin.getDefault().getProjectConfig(project);

            if (isWorkListFacadeProject(config)) {

                EList<AssetType> assetTypes = config.getAssetTypes();
                // Should only contain WLF Asset
                if (assetTypes.size() > 1) {

                    scope.createIssue(ISSUE_CANT_CONTAIN_OTHER_VISIBLE_ASSETS,
                            resource.getName(),
                            resource.getProjectRelativePath().toString(),
                            Arrays.asList(new String[] {
                                    resource.getName(),
                                    resource.getParent().getFullPath()
                                            .toString() }));
                }
            }
        }
    }

    /**
     * Checks if the project contains Work List Facade Asset and only XPD
     * nature.
     * 
     * @param project2
     * @return true, if given resource is a Work List Facade Project.
     */
    private boolean isWorkListFacadeProject(ProjectConfig config) {
        boolean wlfProject = false;
        if (config != null) {
            // Contains Work List Facade Asset.
            wlfProject = config.hasAssetType(WORK_LIST_FACADE_ASSET_ID);
            try {
                // Contains XPD nature
                wlfProject =
                        wlfProject
                                && (config.getProject()
                                        .getNature(XpdConsts.PROJECT_NATURE_ID) != null);
                // XPD is the only Nature
                wlfProject =
                        wlfProject
                                && (config.getProject().getDescription()
                                        .getNatureIds() != null && config
                                        .getProject().getDescription()
                                        .getNatureIds().length == 1);
            } catch (CoreException e) {
                WorkListFacadeResourcePlugin.getDefault().logError(e);
            }
        }

        return wlfProject;
    }

    /**
     * @see com.tibco.xpd.validation.engine.WorkspaceResourceValidator#setProject(org.eclipse.core.resources.IProject)
     * 
     * @param project
     */
    @Override
    public void setProject(IProject project) {

    }

}
