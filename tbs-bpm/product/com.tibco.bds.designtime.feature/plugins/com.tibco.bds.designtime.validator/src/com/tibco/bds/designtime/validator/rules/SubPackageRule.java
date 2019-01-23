package com.tibco.bds.designtime.validator.rules;

import com.tibco.bds.designtime.validator.CDSIssueIds;
import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;

/**
 * Check that Sub-Packages are not used in BOMs that contain Global Data
 *
 */
public class SubPackageRule implements IValidationRule {

    /**
     * @see com.tibco.xpd.validation.rules.IValidationRule#getTargetClass()
     *
     * @return
     */
    @Override
    public Class<?> getTargetClass() {
        return Package.class;
    }

    /**
     * @see com.tibco.xpd.validation.rules.IValidationRule#validate(com.tibco.xpd.validation.provider.IValidationScope, java.lang.Object)
     *
     * @param scope
     * @param obj
     */
    @Override
    public void validate(IValidationScope scope, Object obj) {

        // Check for the cases of a sub package
        // If this is the root Model (which extends Package) then we do
        // not want to raise a validation error
        if ((obj instanceof Package) && (!(obj instanceof Model))) {
            Package pkg = (Package) obj;

            // Need to check to see if this BOM contains Global Data
            if (BOMGlobalDataUtils.hasGlobalDataElements(pkg.getModel())) {
                scope.createIssue(CDSIssueIds.GLOBAL_SUBPACKAGE,
                        pkg.getQualifiedName(),
                        pkg.eResource().getURIFragment(pkg));
            }
        }
    }
}
