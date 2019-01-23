package com.tibco.bds.designtime.validator.rules;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Model;

import com.tibco.bds.designtime.generator.BDSUtils;
import com.tibco.bds.designtime.validator.CDSIssueIds;
import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.validation.engine.WorkspaceResourceValidator;
import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * Checks the BOM to ensure it is valid for the Global data deployment
 * 
 */
public class UnsupportedBOMContent implements WorkspaceResourceValidator {

    public UnsupportedBOMContent() {
    }

    @Override
    public void validate(IValidationScope scope, IResource resource) {

        if (resource instanceof IFile
                && null != resource.getFileExtension()
                && resource.getFileExtension()
                        .endsWith(BOMResourcesPlugin.BOM_FILE_EXTENSION)
                && !BOMValidationUtil.isGeneratedBom(resource)) {

            if (BOMGlobalDataUtils.hasGlobalDataProfile(resource)) {

                boolean resourceEmpty = isResourceEmpty(resource);

                if (!resourceEmpty) {

                    ArrayList<IFile> bomResources = new ArrayList<IFile>();
                    bomResources.add((IFile) resource);

                    // Only allow BOMs in the Global Data project if we are
                    // going to use then to generate an actual BDS Bundle
                    if (!BDSUtils.shouldGenerateBDSPlugin(bomResources)) {
                        scope.createIssue(CDSIssueIds.UNSUPPORTED_SERVICE_ONLY_BOM,
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

    /**
     * @param resource
     */
    private boolean isResourceEmpty(IResource resource) {

        WorkingCopy wc =
                XpdResourcesPlugin.getDefault().getWorkingCopy(resource);
        EObject rootElement = wc.getRootElement();
        if (rootElement instanceof Model) {

            Model model = (Model) rootElement;
            if (model.getPackagedElements().size() == 0) {

                return true;
            }
        }
        return false;
    }

    @Override
    public void setProject(IProject project) {
    }
}
