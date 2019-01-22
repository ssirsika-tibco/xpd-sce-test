package com.tibco.bx.validation.rules;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.validation.engine.WorkspaceResourceValidator;
import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * Project validation to check if a global data bom and process package exist
 * together.
 * 
 * 
 * @author bharge
 * @since 9 Oct 2013
 */
public class XPDLAndGDBOMInSameProjectValidator implements
        WorkspaceResourceValidator {

    private static final String INVALID_ISSUE_ID =
            "bx.xpdlAndGDBOMInSameProjectRestricted"; //$NON-NLS-1$

    public void validate(IValidationScope scope, IResource resource) {

        if (resource instanceof IProject && resource.isAccessible()) {

            IProject project = (IProject) resource;
            if (BOMGlobalDataUtils.isGlobalDataBOMProject(project)) {

                List<SpecialFolder> procPkgSplFolderList =
                        SpecialFolderUtil
                                .getAllSpecialFoldersOfKind(project,
                                        Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND);

                if (null != procPkgSplFolderList
                        && !procPkgSplFolderList.isEmpty()) {

                    scope.createIssue(INVALID_ISSUE_ID,
                            project.getName(),
                            project.getProjectRelativePath().toString());
                }

            }

        }
    }

    public void setProject(IProject project) {
        // do nothing
    }

}
