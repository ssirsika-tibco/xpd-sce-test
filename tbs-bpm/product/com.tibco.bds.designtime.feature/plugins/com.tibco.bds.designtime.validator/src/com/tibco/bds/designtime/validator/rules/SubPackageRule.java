package com.tibco.bds.designtime.validator.rules;

import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;

import com.tibco.bds.designtime.validator.CDSIssueIds;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Check that Sub-Packages are not used in BOMs
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

            /*
             * Sid ACE-470 - sub-packages are never supported in ace regardless
             * of the presence of case/global classes or not.
             */
            scope.createIssue(CDSIssueIds.GLOBAL_SUBPACKAGE,
                    BOMValidationUtil.getLocation(pkg),
                    pkg.eResource().getURIFragment(pkg));
        }
    }
}
