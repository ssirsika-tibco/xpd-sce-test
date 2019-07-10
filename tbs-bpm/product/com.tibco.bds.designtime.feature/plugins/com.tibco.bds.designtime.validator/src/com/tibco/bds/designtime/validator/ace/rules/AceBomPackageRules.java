package com.tibco.bds.designtime.validator.ace.rules;

import org.eclipse.uml2.uml.Package;

import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Validate ACE specific general BOM package rules...
 *
 * <li>Business data package names must not exceed 100 characters.</li>
 *
 * @author aallway
 * @since 16 Apr 2019
 */
public class AceBomPackageRules implements IValidationRule {

    private static final String ACE_ISSUE_PKG_ID_TOO_LONG = "ace.bom.package.id.too.long"; //$NON-NLS-1$


    @Override
    public Class<?> getTargetClass() {
        return Package.class;
    }

    @Override
    public void validate(IValidationScope scope, Object obj) {
        if (obj instanceof org.eclipse.uml2.uml.Package) {
            Package pkg = (org.eclipse.uml2.uml.Package) obj;

            if (pkg.getName().length() > 100) {
                scope.createIssue(ACE_ISSUE_PKG_ID_TOO_LONG,
                        BOMValidationUtil.getLocation(pkg),
                        pkg.eResource().getURIFragment(pkg));

            }
        }
    }

}
