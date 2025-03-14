/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.script.parser.internal.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.script.parser.Activator;
import com.tibco.xpd.script.parser.internal.util.ValidationConstants;
import com.tibco.xpd.script.parser.validator.ErrorMessage;
import com.tibco.xpd.script.parser.validator.ErrorType;

import antlr.Token;
import antlr.collections.AST;

public abstract class AbstractValidator implements IValidator {
    
    public static final Logger LOG = Activator.getDefault().getLogger();
	
    protected List<ErrorMessage> errorList = new ArrayList<ErrorMessage>();
	
	protected List<ErrorMessage> warningList = new ArrayList<ErrorMessage>();	
	
	public static final String		PROJECTNAME_REFERENCE_ADDITIONAL_INFO	= "project";							//$NON-NLS-1$

	@Override
	public List<ErrorMessage> getErrorMessageList() {
		return this.errorList;
	}
	
	@Override
	public List<ErrorMessage> getWarningMessageList() {
		return this.warningList;
	}

    protected void addErrorMessage(Token token, String errorMessage) {
		addErrorMessage(token, errorMessage, null, null, null, null);
	}
	
    protected void addErrorMessage(Token token, String errorMessage,
            List<String> additionalAttributes) {
		addErrorMessage(token, errorMessage, null, additionalAttributes, null, null);
    }

	protected void addErrorMessage(Token token, String errorMessage, List<String> additionalAttributes,
			String alterativeIssueId, Map<String, String> additionalInfoMap)
	{
		addErrorMessage(token, errorMessage, null, additionalAttributes, alterativeIssueId, additionalInfoMap);
	}
	
    protected void addErrorMessage(Token token, String errorMessage,
            ErrorType errorType) {
		addErrorMessage(token, errorMessage, errorType, null, null, null);
    }
    
	protected void addErrorMessage(Token token, String errorMessage,
			ErrorType errorType, List<String> additionalAttributes, String alterativeIssueId,
			Map<String, String> additionalInfoMap)
	{
        if (errorType == null) {
            errorType =
                    new ErrorType(
                            ValidationConstants.ERRORTYPE_KEY_WRONGFORMAT, null);
        }
        ErrorMessage msg =
                new ErrorMessage(token.getLine(), token.getColumn(),
						errorMessage, errorType, additionalAttributes, alterativeIssueId, additionalInfoMap);

        boolean b = errorList.contains(msg);
        if (!b) {
            errorList.add(msg);
        }
    }
	
    protected void addWarningMessage(Token token, String warningMessage) {
        addWarningMessage(token, warningMessage, null, null);
    }

    protected void addWarningMessage(Token token, String warningMessage,
            ErrorType errorType) {
        addWarningMessage(token, warningMessage, errorType, null);
    }

    protected void addWarningMessage(Token token, String warningMessage,
            List<String> additionalAttributes) {
        addWarningMessage(token, warningMessage, null, additionalAttributes);
    }
    
    protected void addWarningMessage(Token token, String warningMessage,
            ErrorType errorType, List<String> additionalAttributes) {
        if (errorType == null) {
            errorType =
                    new ErrorType(
                            ValidationConstants.ERRORTYPE_KEY_WRONGFORMAT, null);
        }
        ErrorMessage msg = new ErrorMessage(token.getLine(), token.getColumn(),
                warningMessage, errorType, additionalAttributes);
        boolean b = warningList.contains(msg);
        if (!b) {
            warningList.add(msg);
        }
    }

	protected boolean isSupportedAST(AST ast, int astType) {
		if (ast == null || ast.getType() != astType) {
			return false;
		}
		return true;
	}

	@Override
	public void validate(AST expression, Token token) {
	    // Do nothing, this method is deprecated, use evaluate(IExpr expr) instead
	}

}
