package com.tibco.xpd.bom.validator.rules;

import java.util.Arrays;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.validation.engine.WorkspaceResourceValidator;
import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * Global Data BOM(s) - boms with case/global classes must be supported only in
 * business data project. Any other project having global data bom must
 * complain.
 * 
 * 
 * @author bharge
 * @since 20 Nov 2013
 */
public class GlobalBOMsInOtherProjectsRule implements
        WorkspaceResourceValidator {

    private IProject project;

    private static final String ISSUE_CANT_HAVE_GLOBAL_BOM =
            "businessdata.otherProjectCantHaveGlobalBOM"; //$NON-NLS-1$

    private static final String ISSUE_CANT_HAVE_GLOBALDATAPROFILE =
            "businessdata.otherProjectCantHaveGlobalDataProfile"; //$NON-NLS-1$

    public GlobalBOMsInOtherProjectsRule() {
    }

    @Override
    public void validate(IValidationScope scope, IResource resource) {

        if (!BOMUtils.isBusinessDataProject(project)) {

            if (resource instanceof IFile
                    && null != resource.getFileExtension()
                    && resource.getFileExtension()
                            .endsWith(BOMResourcesPlugin.BOM_FILE_EXTENSION)
                    && !BOMValidationUtil.isGeneratedBom(resource)) {

                if (BOMGlobalDataUtils.hasGlobalDataProfile(resource)) {
                    /*
                     * the bom does not have case/global classes but has global
                     * data profile applied to it, then show the validation
                     * error. this error has a quick fix to remove the profile
                     */
                    if (!BOMGlobalDataUtils.hasGlobalDataElements(resource)) {

                        scope.createIssue(ISSUE_CANT_HAVE_GLOBALDATAPROFILE,
                                resource.getName(),
                                resource.getProjectRelativePath().toString(),
                                Arrays.asList(new String[] {
                                        resource.getName(),
                                        resource.getParent().getFullPath()
                                                .toString() }));
                    } else if (BOMGlobalDataUtils
                            .hasGlobalDataElements(resource)) {
                        /*
                         * the bom has global data profile and has global data
                         * elements
                         */
                        scope.createIssue(ISSUE_CANT_HAVE_GLOBAL_BOM,
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
    }

    @Override
    public void setProject(IProject project) {

        this.project = project;
    }

}
