package com.tibco.xpd.bom.validator.rules;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.AssetType;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.validation.engine.WorkspaceResourceValidator;
import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * A Business Data Project cannot have any other asset types other than BOM
 * asset and WSDL Service Descriptors asset
 * 
 * 
 * @author bharge
 * @since 20 Nov 2013
 */
public class BusinessDataAssetsRule implements WorkspaceResourceValidator {

    private static final String ISSUE_CANT_HAVE_OTHER_ASSETS =
            "businessdata.businessDataCantHaveOtherAssets"; //$NON-NLS-1$

    public BusinessDataAssetsRule() {
    }

    @Override
    public void validate(IValidationScope scope, IResource resource) {

        if (resource instanceof IProject) {

            IProject project = (IProject) resource;
            if (BOMUtils.isBusinessDataProject(project)) {

                String[] allowedAssetIds =
                        { "com.tibco.xpd.asset.bom",
                                "com.tibco.xpd.asset.businessdata.bom",
                                "com.tibco.xpd.asset.wsdl" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                Set<String> allowedAssets =
                        new HashSet<String>(Arrays.asList(allowedAssetIds));
                ProjectConfig config =
                        XpdResourcesPlugin.getDefault()
                                .getProjectConfig(project);
                for (AssetType assetType : config.getAssetTypes()) {

                    String assetId = assetType.getId();
                    if (!allowedAssets.contains(assetId)) {

                        scope.createIssue(ISSUE_CANT_HAVE_OTHER_ASSETS,
                                project.getName(),
                                project.getProjectRelativePath().toString());
                    }
                }
            }
        }
    }

    @Override
    public void setProject(IProject project) {
    }

}
