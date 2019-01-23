/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.validator.rules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.om.validator.GenericIssueIds;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Validation rule to check for invalid name.
 * 
 * @author njpatel
 * 
 */
public class InvalidNameRule implements IValidationRule {

    private Pattern namePattern = Pattern.compile("[a-zA-Z0-9_]*"); //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.validation.rules.IValidationRule#getTargetClass()
     */
    public Class<?> getTargetClass() {
        return NamedElement.class;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.rules.IValidationRule#validate(com.tibco.xpd
     * .validation.provider.IValidationScope, java.lang.Object)
     */
    public void validate(IValidationScope scope, Object target) {
        if (target instanceof NamedElement) {
            NamedElement namedElem = (NamedElement) target;
            String name = namedElem.getName();

            Matcher matcher = namePattern.matcher(name);

            if (!matcher.matches()) {
                scope.createIssue(GenericIssueIds.INVALID_NAME, namedElem
                        .eClass().getName(), namedElem.eResource()
                        .getURIFragment(namedElem));
            }
        }
    }

}
