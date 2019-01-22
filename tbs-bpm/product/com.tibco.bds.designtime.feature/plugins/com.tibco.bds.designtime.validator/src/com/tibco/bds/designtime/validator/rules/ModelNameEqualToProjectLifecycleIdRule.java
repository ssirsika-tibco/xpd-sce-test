package com.tibco.bds.designtime.validator.rules;

import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.uml2.uml.Model;

import com.tibco.xpd.bom.gen.extensions.BOMGenerator2Extension;
import com.tibco.xpd.bom.gen.extensions.BOMGenerator2ExtensionHelper;
import com.tibco.xpd.bom.resources.ui.util.BomUIUtil;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Rule to check that the BOM root model name is not same as project life cycle
 * id with any BOMGenerator2Extension suffix appended.
 * 
 * @author glewis
 * 
 */
public class ModelNameEqualToProjectLifecycleIdRule implements IValidationRule {

    private static final String ISSUE_ID =
            "bom.cds.duplicateModelAndProjectLifecycle.issue"; //$NON-NLS-1$

    public ModelNameEqualToProjectLifecycleIdRule() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.validation.rules.IValidationRule#getTargetClass()
     */
    public Class<?> getTargetClass() {
        return Model.class;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.rules.IValidationRule#validate(com.tibco.xpd
     * .validation.provider.IValidationScope, java.lang.Object)
     */
    public void validate(IValidationScope scope, Object obj) {
        if (obj instanceof Model) {
            Model model = (Model) obj;
            String name = BOMWorkingCopy.getQualifiedClassName(model);
            String uri = BomUIUtil.getURI(model);

            IFile file = WorkspaceSynchronizer.getFile(model.eResource());
            String projectLifecycleId =
                    ProjectUtil.getProjectId(file.getProject());

            BOMGenerator2Extension[] extensions =
                    BOMGenerator2ExtensionHelper.getInstance().getExtensions();
            for (BOMGenerator2Extension extension : extensions) {
                String suffix = extension.getSuffix();
                String tmpName = name + "." + suffix; //$NON-NLS-1$
                if (tmpName.equals(projectLifecycleId)) {
                    scope.createIssue(ISSUE_ID,
                            BOMValidationUtil.getLocation(model),
                            URI.createURI(uri).fragment(),
                            Collections.singleton(name));
                }
            }
        }
    }
}
