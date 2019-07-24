/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.validation;

import java.util.Arrays;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.n2.globalsignal.resource.GsdResourcePlugin;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.AssetType;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.XpdConsts;
import com.tibco.xpd.validation.engine.WorkspaceResourceValidator;
import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * Global Signal Definition project validator.
 * 
 * @author sajain
 * @since Feb 25, 2015
 */
public class GsdProjectValidator implements WorkspaceResourceValidator {

    /**
     * ID of GSD asset.
     */
    public static final String GLOBAL_SIGNAL_DEFINITION_ASSET_ID =
            "com.tibco.xpd.asset.globalSignalDefinition"; //$NON-NLS-1$

    /**
     * ID of GSD nature.
     */
    public static final String GLOBAL_SIGNAL_DEFINITION_NATURE_ID =
            "com.tibco.xpd.n2.globalsignal.resource.gsdProjectNature"; //$NON-NLS-1$

    /**
     * ID of hidden GSD asset.
     */
    public static final String GLOBAL_SIGNAL_DEFINITION_HIDDEN_ASSET_ID =
            "com.tibco.xpd.project.asset.globalSignalDefinition"; //$NON-NLS-1$

    /**
     * Global Signal Definition projects must not contain other asset types.
     */
    private static final String ISSUE_GSDPROJECT_CANT_CONTAIN_OTHER_NATURES_OR_ASSETS =
            "gsdProject.other.assets.not.allowed.issue"; //$NON-NLS-1$

    /**
     * Global Signal Definition assets can only be used in Global Signal
     * Definition projects.
     */
    private static final String ISSUE_NONGSDPROJECTS_CANT_CONTAIN_GSD_NATURE_OR_ASSET =
            "gsdAssetNotAllowedInNonGsdProject.issue"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.engine.WorkspaceResourceValidator#validate(com.tibco.xpd.validation.provider.IValidationScope,
     *      org.eclipse.core.resources.IResource)
     * 
     * @param scope
     * @param resource
     */
    @Override
    public void validate(IValidationScope scope, IResource resource) {

        /*
         * Check if project is accessible.
         */
        if (resource instanceof IProject && resource.isAccessible()
                && ProjectUtil.isStudioProject((IProject) resource)) {

            IProject project = (IProject) resource;

            /*
             * Get the project config.
             */
            ProjectConfig config =
                    XpdResourcesPlugin.getDefault().getProjectConfig(project);

            /*
             * Check if the specified project config is of a GSD project.
             */
            if (isGSDProject(config)) {

                EList<AssetType> assetTypes = config.getAssetTypes();

                /*
                 * If we've a non-GSD asset or non GSD/XPD nature, then create
                 * an issue.
                 */
                if (hasOtherAssetOrNature(project, assetTypes)) {

                    /*
                     * Global Signal Definition projects must not contain other
                     * natures/asset types.
                     */
                    scope.createIssue(ISSUE_GSDPROJECT_CANT_CONTAIN_OTHER_NATURES_OR_ASSETS,
                            resource.getName(),
                            resource.getProjectRelativePath().toString(),
                            Arrays.asList(new String[] {
                                    resource.getName(),
                                    resource.getParent().getFullPath()
                                            .toString() }));
                }

            } else {

                /*
                 * The project is not a GSD project. So check if it has a GSD
                 * asset or nature and if that's the case, then raise an error.
                 */

                /*
                 * If we have a GSD asset/nature in a non-GSD project, then
                 * raise an error.
                 */
                if (hasGSDAsset(project, config)) {

                    /**
                     * Global Signal Definition assets can only be used in
                     * Global Signal Definition projects.
                     */
                    scope.createIssue(ISSUE_NONGSDPROJECTS_CANT_CONTAIN_GSD_NATURE_OR_ASSET,
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
     * Return <code>true</code> if we have a GSD asset in the specified project,
     * <code>false</code> otherwise.
     * 
     * @param project
     * @param config
     * @return <code>true</code> if we have either a GSD asset in the specified
     *         project, <code>false</code> otherwise.
     */
    private boolean hasGSDAsset(IProject project, ProjectConfig config) {
        /*
         * Flag to indicate if we've got a GSD asset in the project config.
         */
        boolean hasGsdAsset = false;

        /*
         * See if the config has any of the GSD assets.
         */
        if (config.hasAssetType(GLOBAL_SIGNAL_DEFINITION_HIDDEN_ASSET_ID)
                || config.hasAssetType(GLOBAL_SIGNAL_DEFINITION_ASSET_ID)) {

            /*
             * Found a GSD asset, so set the boolean flag to true.
             */
            hasGsdAsset = true;
        }

        return hasGsdAsset;
    }

    /**
     * Return <code>true</code> if we have either a Non-GSD asset or a
     * Non-GSD/XPD nature in the specified project, <code>false</code>
     * otherwise.
     * 
     * @param project
     * @param assetTypes
     * @return <code>true</code> if we have either a Non-GSD asset or a
     *         Non-GSD/XPD nature in the specified project, <code>false</code>
     *         otherwise.
     */
    private boolean hasOtherAssetOrNature(IProject project,
            EList<AssetType> assetTypes) {

        /*
         * Traverse through all the asset types of the project config to see if
         * we have an asset other than the GSD assets in it.
         */
        for (AssetType eachAssetType : assetTypes) {

            if (!(GLOBAL_SIGNAL_DEFINITION_HIDDEN_ASSET_ID.equals(eachAssetType
                    .getId()) || GLOBAL_SIGNAL_DEFINITION_ASSET_ID
                            .equals(eachAssetType.getId()))) {

                /*
                 * Found a non-GSD asset, so return true.
                 */
                return true;
            }
        }

        /*
         * Get project description.
         */
        try {

            IProjectDescription projectDescription = project.getDescription();

            String[] allNatures = projectDescription.getNatureIds();

            /*
             * Traverse through all the nature IDs of the project config to see
             * if we have a nature other than the XPD/GSD/LockedForProduction
             * nature in it.
             */
            for (String eachNature : allNatures) {

                if (!(XpdConsts.PROJECT_NATURE_ID.equals(eachNature) || GLOBAL_SIGNAL_DEFINITION_NATURE_ID
                        .equals(eachNature) || XpdConsts.LOCKED_FOR_PRODUCTION_NATURE
                        .equals(eachNature))) {

                    /*
                     * Found a non GSD/XPD nature, so return true.
                     */
                    return true;
                }
            }

        } catch (CoreException e) {

            GsdResourcePlugin.getDefault().getLogger().error(e);
        }

        return false;
    }

    /**
     * Checks if the project contains Global Signal Definition Nature and XPD
     * nature.
     * 
     * @param project2
     * @return true, if given resource is a Global Signal Definition Project.
     */
    private boolean isGSDProject(ProjectConfig config) {

        boolean isGSDProject = false;

        if (config != null) {

            try {

                /*
                 * Contains XPD nature
                 */
                isGSDProject =
                        config.getProject()
                                .getNature(XpdConsts.PROJECT_NATURE_ID) != null;

                /*
                 * Contains GSD nature
                 */
                isGSDProject =
                        isGSDProject
                                && (config
                                        .getProject()
                                        .getNature(GLOBAL_SIGNAL_DEFINITION_NATURE_ID) != null);

                /*
                 * XPD-7308: Saket: We should not expect to have only XPD nature
                 * in the config while figuring out whether it's a GSD project
                 * or not. Because then if we have multiple assets, then we
                 * won't have the invalid asset combination problem marker
                 * raised as this method itself would retun false at the first
                 * place. So just check if we have XPD nature and GSD asset in
                 * the config and move on.
                 */

            } catch (CoreException e) {
                GsdResourcePlugin.getDefault().logError(e);
            }
        }

        return isGSDProject;
    }

    /**
     * @see com.tibco.xpd.validation.engine.WorkspaceResourceValidator#setProject(org.eclipse.core.resources.IProject)
     * 
     * @param project
     */
    @Override
    public void setProject(IProject project) {

        // Nothing to do here.
    }

}
