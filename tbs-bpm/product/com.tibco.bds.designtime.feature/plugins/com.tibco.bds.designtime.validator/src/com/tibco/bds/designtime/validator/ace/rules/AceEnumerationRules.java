package com.tibco.bds.designtime.validator.ace.rules;

import org.eclipse.uml2.uml.Enumeration;

import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Validate ACE specific enumeration rules...
 *
 * <li>Enumerations with super-class are not supported</li>
 *
 * @author aallway
 * @since 16 Apr 2019
 */
public class AceEnumerationRules implements IValidationRule {

    private static final String ISSUE_ACE_ENUMERATION_SUPERCLASS =
            "ace.bom.enumeration.superclass"; //$NON-NLS-1$

    @Override
    public Class<?> getTargetClass() {
        return Enumeration.class;
    }


    /**
     * Validate the given enumeration
     * 
     * @param scope
     * @param obj
     */
    private void validateEnumeration(IValidationScope scope,
            Enumeration enumeration) {

        if (!enumeration.getGeneralizations().isEmpty()) {
            scope.createIssue(ISSUE_ACE_ENUMERATION_SUPERCLASS,
                    BOMValidationUtil.getLocation(enumeration),
                    enumeration.eResource().getURIFragment(enumeration));
        }
    }

    @Override
    public void validate(IValidationScope scope, Object obj) {
        if (obj instanceof Enumeration) {
            validateEnumeration(scope, (Enumeration) obj);
        }
    }

}
