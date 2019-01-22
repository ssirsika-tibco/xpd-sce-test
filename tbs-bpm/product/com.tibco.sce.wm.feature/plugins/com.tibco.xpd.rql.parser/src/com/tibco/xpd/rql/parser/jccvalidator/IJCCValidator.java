/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.rql.parser.jccvalidator;

import java.util.List;

import com.tibco.n2.de.rql.parser.SimpleNode;
import com.tibco.xpd.script.parser.validator.ErrorMessage;

/**
 * 
 * @author mtorres
 *
 */
public interface IJCCValidator {
   
    /**
     * Initialize the Validator
     */
    void initialize();
    
    /**
     * Validate the Node
     * 
     * @param node
     */
    void validate(SimpleNode node);

    /**
     * Return the error message list
     * 
     * @return
     */
    List<ErrorMessage> getErrorMessageList();

    /**
     * Return the warning message list
     * @return
     */
    List<ErrorMessage> getWarningMessageList();

}
