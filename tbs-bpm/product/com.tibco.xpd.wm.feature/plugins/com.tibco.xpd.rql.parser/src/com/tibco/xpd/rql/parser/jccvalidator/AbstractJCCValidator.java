/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.rql.parser.jccvalidator;

import java.util.ArrayList;
import java.util.List;

import com.tibco.xpd.script.parser.internal.util.ValidationConstants;
import com.tibco.xpd.script.parser.validator.ErrorMessage;
import com.tibco.xpd.script.parser.validator.ErrorType;

/**
 * 
 * @author mtorres
 *
 */
public abstract class AbstractJCCValidator implements IJCCExpressionValidator {

	protected List<ErrorMessage> errorList = new ArrayList<ErrorMessage>();
	
	protected List<ErrorMessage> warningList = new ArrayList<ErrorMessage>();	

	/**
	 * Returns the error message list
	 */
	public List<ErrorMessage> getErrorMessageList() {
		return this.errorList;
	}
	
	/**
	 * Returns the warning message list
	 */
	public List<ErrorMessage> getWarningMessageList() {
		return this.warningList;
	}

	/**
	 * Add the error message to the warning list
	 * 
	 * @param errorMessage
	 */
	protected void addErrorMessage(String errorMessage) {
	    addErrorMessage(errorMessage, null, null);
	}
	
	/**
	 * Add the error message to the warning list
	 * 
	 * @param errorMessage
	 * @param additionalAttributes
	 */
    public void addErrorMessage(String errorMessage,
            List<String> additionalAttributes) {
        addErrorMessage(errorMessage, null, additionalAttributes);
    }
	
    /**
     * Add the error message to the warning list
     * 
     * @param errorMessage
     * @param errorType
     */
    protected void addErrorMessage(String errorMessage,
            ErrorType errorType) {
        addErrorMessage(errorMessage, errorType, null);
    }
    
    /**
     * Add the error message to the warning list
     * 
     * @param errorMessage
     * @param errorType
     * @param additionalAttributes
     */
	protected void addErrorMessage(String errorMessage,
            ErrorType errorType, List<String> additionalAttributes) {
        if (errorType == null) {
            errorType =
                    new ErrorType(
                            ValidationConstants.ERRORTYPE_KEY_WRONGFORMAT, null);
        }
        ErrorMessage msg =
                new ErrorMessage(1, 1,
                        errorMessage, errorType, additionalAttributes);
        boolean b = errorList.contains(msg);
        if (!b) {
            errorList.add(msg);
        }
    }
	
	/**
	 * Add the warning message to the warning list
	 * 
	 * @param warningMessage
	 */
    protected void addWarningMessage(String warningMessage) {
        addWarningMessage(warningMessage, null, null);
    }

    /**
     * Add the warning message to the warning list
     * 
     * @param warningMessage
     * @param errorType
     */
    protected void addWarningMessage(String warningMessage,
            ErrorType errorType) {
        addWarningMessage(warningMessage, errorType, null);
    }

    /**
     * Add the warning message to the warning list 
     * @param warningMessage
     * @param additionalAttributes
     */
    protected void addWarningMessage(String warningMessage,
            List<String> additionalAttributes) {
        addWarningMessage(warningMessage, null, additionalAttributes);
    }
    
    /**
     * Add the warning message to the warning list
     * 
     * @param warningMessage
     * @param errorType
     * @param additionalAttributes
     */
    protected void addWarningMessage(String warningMessage,
            ErrorType errorType, List<String> additionalAttributes) {
        if (errorType == null) {
            errorType =
                    new ErrorType(
                            ValidationConstants.ERRORTYPE_KEY_WRONGFORMAT, null);
        }
        ErrorMessage msg = new ErrorMessage(1, 1,
                warningMessage, errorType, additionalAttributes);
        boolean b = warningList.contains(msg);
        if (!b) {
            warningList.add(msg);
        }
    }


}
