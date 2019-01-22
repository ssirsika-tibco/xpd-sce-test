/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.validator.rules;

import com.tibco.xpd.om.core.om.OrgElement;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Validate the End Date of an {@link OrgElement}.
 */
public class EndDateValidationRule implements IValidationRule {

    private static final String ISSUE_ID = "om.invalidEndTime.issue"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.validation.rules.IValidationRule#getTargetClass()
     */
    public Class<?> getTargetClass() {
        return OrgElement.class;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.rules.IValidationRule#validate(com.tibco.xpd
     * .validation.provider.IValidationScope, java.lang.Object)
     */
    public void validate(IValidationScope scope, Object obj) {
        if (obj instanceof OrgElement) {
            OrgElement elem = (OrgElement) obj;

            if (elem.getStartDate() != null && elem.getEndDate() != null
                    && elem.getEndDate().compareTo(elem.getStartDate()) <= 0) {
                scope.createIssue(ISSUE_ID, elem.getDisplayName(), elem
                        .eResource().getURIFragment(elem));
            }
        }
    }

}
