/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.bx.validation.resolutions;

import java.util.Collection;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

import com.tibco.bx.validation.internal.Messages;
import com.tibco.xpd.om.core.om.util.OMUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.SpecialFolderUtil;

/**
 * Quick fix to move BOMs into a an existing Business Data project.
 *
 * @author aallway
 * @since 16 May 2019
 */
public class MoveOrgsToOrgProjectResolution
        extends AbstractMoveAssetsToOwnProjectResolution {

    public MoveOrgsToOrgProjectResolution() {
        super(Messages.MoveOrgsToOrgProjectResolution2_SelectOrgProject_title,
                Messages.MoveOrgsToOrgProjectResolution2_NoOrgProjects_message,
                Messages.MoveOrgsToOrgProjectResolution2_Success_message,
                Messages.MoveOrgsToOrgProjectResolution2_ErrorMovingOrgs_message);
    }

    /**
     * @return The special folder kind we're interested in.
     */
    @Override
    protected String getSpecialFolderKind() {
        return OMUtil.OM_SPECIAL_FOLDER_KIND;
    }

    /**
     * @return The special folder kind we're interested in.
     */
    @Override
    protected String getAssetType() {
        return "com.tibco.xpd.asset.om"; //$NON-NLS-1$
    }

    /**
     * Check if the given project is a suitable target for the asset types in
     * question.
     * 
     * @param project
     * @return <code>true</code> if the given project is a suitable target for
     *         the asset types in question.
     */
    @Override
    protected boolean isAppropriateTargetProject(IProject project) {
        ProjectConfig projectConfig =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);

        if (projectConfig != null) {
            if (hasAssetType(projectConfig, getAssetType())) {
                return true;
            }
        }

        return false;
    }

    /**
     * 
     * @param srcSpecialFolder
     * @return All of the source assets to move from the given source special
     *         folder.
     */
    @Override
    protected Collection<IResource> getSourceAssetsToMove(
            SpecialFolder srcSpecialFolder) {
        return SpecialFolderUtil.getAllDeepResourcesInContainer(srcSpecialFolder
                .getFolder(), OMUtil.OM_FILE_EXTENSION, false);
    }

}
