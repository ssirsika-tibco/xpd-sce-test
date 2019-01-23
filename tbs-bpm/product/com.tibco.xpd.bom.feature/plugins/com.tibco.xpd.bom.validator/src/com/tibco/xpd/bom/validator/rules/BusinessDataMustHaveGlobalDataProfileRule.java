package com.tibco.xpd.bom.validator.rules;

import java.util.Arrays;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.validation.engine.WorkspaceResourceValidator;
import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * Restriction to notify that a business data project must have to have global
 * data profile on all the boms it contains
 * 
 * 
 * @author bharge
 * @since 20 Nov 2013
 */
public class BusinessDataMustHaveGlobalDataProfileRule implements
        WorkspaceResourceValidator {

    private IProject project;

    private static final String ISSUE_MUST_HAVE_GLOBAL_DATA_PROFILE =
            "businessdata.businessDataMustHaveGlobalDataProfile"; //$NON-NLS-1$

    public BusinessDataMustHaveGlobalDataProfileRule() {
    }

    @Override
    public void validate(IValidationScope scope, IResource resource) {

        // Check to see if we are running in Studio Analyst mode, if we are then
        // we do not want to show the validation about the project type. This
        // can occur if the user exported out of BPM Developer Studio as an MAA
        // and then imports into Analyst Studio. The user of Analyst Studio does
        // not know about Global Data Projects, so it makes no sense to show
        // them the error
        if (XpdResourcesPlugin.isRCP()) {
            return;
        }

        if (resource instanceof IFile
                && null != resource.getFileExtension()
                && resource.getFileExtension()
                        .endsWith(BOMResourcesPlugin.BOM_FILE_EXTENSION)
                && !BOMValidationUtil.isGeneratedBom(resource)) {

            if (BOMUtils.isBusinessDataProject(project)) {

                if (!BOMGlobalDataUtils.hasGlobalDataProfile(resource)) {

                    scope.createIssue(ISSUE_MUST_HAVE_GLOBAL_DATA_PROFILE,
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

    @Override
    public void setProject(IProject project) {

        this.project = project;
    }

}
