/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.validation.internal;

import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Wrapper class to store whether this validation rule should be run on save
 * only or during the live validation
 * 
 * @author agondal
 * @since 23 Sep 2013
 */
public class ValidationRule {

    private IValidationRule validationRule;

    private boolean validateOnSaveOnly;

    public ValidationRule(IValidationRule validationRule,
            boolean validateOnSaveOnly) {

        this.validationRule = validationRule;
        this.validateOnSaveOnly = validateOnSaveOnly;
    }

    /**
     * @return the validationRule
     */
    public IValidationRule getValidationRule() {
        return validationRule;
    }

    /**
     * @return the validateOnSaveOnly
     */
    public boolean isValidateOnSaveOnly() {
        return validateOnSaveOnly;
    }
}
