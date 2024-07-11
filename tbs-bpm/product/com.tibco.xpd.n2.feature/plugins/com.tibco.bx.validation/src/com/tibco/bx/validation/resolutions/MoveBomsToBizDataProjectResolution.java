/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.bx.validation.resolutions;

import java.util.Collection;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

import com.tibco.bx.validation.internal.Messages;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.ui.Activator;
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
public class MoveBomsToBizDataProjectResolution
        extends AbstractMoveAssetsToOwnProjectResolution {


    public MoveBomsToBizDataProjectResolution() {
        super(Messages.MoveBomsToBizDataProjectResolution_SelectBizDataProject_title,
                Messages.MoveBomsToBizDataProjectResolution_NoBizDataProjects_message,
                Messages.MoveBomsToBizDataProjectResolution_Success_message,
				Messages.MoveBomsToBizDataProjectResolution_ErrorMovingBOMS_message,
				AbstractMoveAssetsToOwnProjectResolution.MoveTargetType.PROJECT);
    }

    /**
     * @return The special folder kind we're interested in.
     */
    @Override
    protected String getSpecialFolderKind() {
        return BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND;
    }

    /**
     * @return The special folder kind we're interested in.
     */
    @Override
    protected String getAssetType() {
        return Activator.BOM_ASSET_ID;
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
            if (hasAssetType(projectConfig,
                    "com.tibco.xpd.asset.businessdata.bom")) { //$NON-NLS-1$
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
                .getFolder(), BOMResourcesPlugin.BOM_FILE_EXTENSION, false);
    }

}
