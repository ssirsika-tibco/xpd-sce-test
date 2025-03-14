package com.tibco.bx.validation.ace.rules;

import java.util.Collections;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import com.tibco.bx.validation.BxValidationPlugin;
import com.tibco.bx.validation.MixedAssetProjectNature;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.ui.Activator;
import com.tibco.xpd.om.core.om.util.OMUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.AssetType;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.validation.engine.WorkspaceResourceValidator;
import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * ACE-590 / ACE-283 - Refactored old XPDLAndGDBOMInSameProjectValidator rule
 * saying that process and Global BOM cannot be in same project in favour of ace
 * rules...
 * 
 * <li>Organisation assets must be in their own project (not mixed with other
 * asset types such as Process and Data etc).</li>
 * 
 * <li>Business Object assets must be alone in their own Business Data project
 * (not mixed with other asset types such as Process and Organisation etc).</li>
 * 
 * <li>Business Object assets must be in a Business Data project.</li>
 * 
 * <li>Project names must not exceed 100 characters.</li>
 * 
 * <li>Project ID must not exceed 100 characters.</li>
 * 
 * @author aallway
 * @since 25 April 2019
 */
public class AceProjectRules implements
        WorkspaceResourceValidator {

    private static final String ACE_ISSUE_ORG_ASSET_MUST_BE_ALONE =
            "ace.org.asset.must.be.alone"; //$NON-NLS-1$
    
    private static final String ACE_ISSUE_BOM_ASSET_MUST_BE_ALONE =
            "ace.bom.asset.must.be.alone"; //$NON-NLS-1$

    private static final String ACE_ISSUE_BOM_ASSET_MUST_BE_BIZDATA =
            "ace.bom.asset.must.be.in.biz.data"; //$NON-NLS-1$

    private static final String ACE_ISSUE_PROJECT_NAME_TOO_LONG = "ace.project.name.too.long"; //$NON-NLS-1$

    private static final String ACE_ISSUE_PROJECT_ID_TOO_LONG = "ace.project.id.too.long"; //$NON-NLS-1$

    @Override
    public void validate(IValidationScope scope, IResource resource) {

        if (resource instanceof IProject && resource.isAccessible()) {

            IProject project = (IProject) resource;

            ProjectConfig projectConfig = XpdResourcesPlugin.getDefault()
                    .getProjectConfig(project);

            /*
             * Sid ACE-5185 Nominally in SCE (BPME Studio) we validate against the use of certain assets in combination
             * with other assets. Adding this nature to a project switches off that validation so that projects with
             * multiple assets (such as process, data and org) can be used and have RASC generated as a single project
             */
            boolean hasMultiAssetNature = false;

            try {
                hasMultiAssetNature = project.getNature(MixedAssetProjectNature.NATURE_ID) != null;
            } catch (CoreException e) {
                BxValidationPlugin.getDefault().getLogger().error(e, "Error getting mixed asset nature from project"); //$NON-NLS-1$
            }

            /*
             * Organisation assets must be in their own project
             */
            if (!hasMultiAssetNature) {
                boolean hasOrgAsset = hasAssetTypeAndFiles(project,
                        projectConfig,
                        "com.tibco.xpd.asset.om", //$NON-NLS-1$
                        OMUtil.OM_SPECIAL_FOLDER_KIND,
                        OMUtil.OM_FILE_EXTENSION);

                if (hasOrgAsset) {
                    if (projectConfig.getAssetTypes().size() != 1) {
                        scope.createIssue(ACE_ISSUE_ORG_ASSET_MUST_BE_ALONE,
                                project.getName(),
                                project.getProjectRelativePath().toString());
                    }
                }
            }

            /*
             * Business Objects assets must be in their own project and it must
             * be a business data project
             */
            if (!hasMultiAssetNature) {

                boolean hasBomAsset = hasAssetTypeAndFiles(project,
                        projectConfig,
                        Activator.BOM_ASSET_ID,
                        BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND,
                        BOMResourcesPlugin.BOM_FILE_EXTENSION);

                if (hasBomAsset) {
                    /* Must be in a business data project. */
                    boolean hasBizDataProjectAsset =
                            hasAssetType(projectConfig, "com.tibco.xpd.asset.businessdata.bom"); //$NON-NLS-1$

                    if (!hasBizDataProjectAsset && projectConfig.getAssetTypes().size() == 1) {
                        scope.createIssue(ACE_ISSUE_BOM_ASSET_MUST_BE_BIZDATA,
                                project.getName(),
                                project.getProjectRelativePath().toString());

                    } else if (projectConfig.getAssetTypes().size() != 2) {
                        scope.createIssue(ACE_ISSUE_BOM_ASSET_MUST_BE_ALONE,
                                project.getName(),
                                project.getProjectRelativePath().toString());
                    }
                }
            }

            /*
             * Sid ACE-1586 Validate project name length.
             */
            if (project.getName() != null && project.getName().length() > 100) {
                scope.createIssue(ACE_ISSUE_PROJECT_NAME_TOO_LONG,
                        project.getName(),
                        project.getProjectRelativePath().toString(),
                        Collections.singletonList("100")); //$NON-NLS-1$
            }

            if (projectConfig.getProjectDetails() != null && projectConfig.getProjectDetails().getId() != null
                    && projectConfig.getProjectDetails().getId().length() > 100) {
                scope.createIssue(ACE_ISSUE_PROJECT_ID_TOO_LONG,
                        project.getName(),
                        project.getProjectRelativePath().toString(),
                        Collections.singletonList("100")); //$NON-NLS-1$
            }

        }
    }

    /**
     * 
     * @param projectConfig
     * @param assetId
     * 
     * @return <code>true</code> if project has given asset type
     */
    private boolean hasAssetType(ProjectConfig projectConfig, String assetId) {
        for (AssetType asset : projectConfig.getAssetTypes()) {
            if (assetId.equals(asset.getId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 
     * @param project
     * @param projectConfig
     * @param assetId
     * @param fileExtension
     * @param specialFolderKind
     * 
     * @return <code>true</code> if project has given asset type AND files of
     *         the given kind.
     */
    private boolean hasAssetTypeAndFiles(IProject project, ProjectConfig projectConfig,
            String assetId, String specialFolderKind, String fileExtension) {
        for (AssetType asset : projectConfig.getAssetTypes()) {
            if (assetId.equals(asset.getId())) {
                /*
                 * Sid ACE-999: When initially implemented, the problem markers
                 * would be raised if the project configuration indicated that
                 * the org / bom asset id config was present alongside others
                 * regardless of whether there were actually any Org / BOM files
                 * present.
                 * 
                 * This could cause usability problems as it is possible to have
                 * projects that have the asset configured but all of the
                 * related files deleted or moved but you would still see the
                 * issue.
                 * 
                 * As part of this enhancement therefore we will slacken the
                 * rule so that there also needs to be Org/BOM files actually
                 * present as well as the asset id in the project configuration
                 * alongside other assets.
                 */
                if (!SpecialFolderUtil
                        .getAllDeepResourcesInSpecialFolderOfKind(project,
                                specialFolderKind,
                                fileExtension,
                                false)
                        .isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void setProject(IProject project) {
        // do nothing
    }

}
