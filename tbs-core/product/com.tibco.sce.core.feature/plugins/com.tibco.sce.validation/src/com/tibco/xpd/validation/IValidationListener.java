/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation;

/**
 * The IValidationListener interface provides a mechanism for listining to
 * ValidationEvent notifications. These occur at the end of each validation
 * run.
 * 
 * @author nwilson
 */
public interface IValidationListener {

    /**
     * @param event The validation event that occurred.
     */
    void validationEvent(ValidationEvent event);

}
