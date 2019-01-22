/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.rules;

import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * This is the base interface that all rules must implement.
 * 
 * @author nwilson
 */
public interface IValidationRule {

    /**
     * @return The target class that this rule will validate.
     */
    Class<?> getTargetClass();
    
    /**
     * @param scope The validation scope.
     * @param o The object to validate.
     */
    void validate(IValidationScope scope, Object o);

}
