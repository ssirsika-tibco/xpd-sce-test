/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.process.js.parser.validator;

import java.util.ArrayList;
import java.util.List;

import com.tibco.xpd.analyst.resources.xpdl2.ReservedWords;
import com.tibco.xpd.process.js.parser.Messages;
import com.tibco.xpd.script.parser.internal.validator.IValidateResult;
import com.tibco.xpd.script.parser.internal.validator.jscript.JScriptAssignmentExpressionValidator;

import antlr.Token;
import antlr.collections.AST;

/**
 * AbstractJScriptAssignmentExpressionValidator validates for Variable Assignment.
 * 
 * Since validations for Variable Assignment occur at Process Script Editior Level so it makes much more sense to have
 * "AbstractJScriptAssignmentExpressionValidator" as part of Process Editior Developer Feature.
 *
 * @author cbabar
 * @since Apr 15, 2024
 */
public class AbstractJScriptAssignmentExpressionValidator extends JScriptAssignmentExpressionValidator
{

	/**
	 * @see com.tibco.xpd.script.parser.internal.validator.jscript.JScriptAssignmentExpressionValidator#performExtraAssignmentValidation(com.tibco.xpd.script.parser.internal.validator.IValidateResult,
	 *      com.tibco.xpd.script.parser.internal.validator.IValidateResult, antlr.Token)
	 *
	 * @param lhsValidateResult
	 * @param rhsValidateResult
	 * @param token
	 */
	@Override
	protected void performExtraAssignmentValidation(IValidateResult lhsValidateResult,
			IValidateResult rhsValidateResult, Token token)
	{
		super.performExtraAssignmentValidation(lhsValidateResult, rhsValidateResult, token);
	}

	/**
	 * @see com.tibco.xpd.script.parser.internal.validator.jscript.JScriptAssignmentExpressionValidator#validateVariableAssignment(antlr.collections.AST,
	 *      antlr.Token)
	 *
	 * @param rhsExpression
	 * @param token
	 */
	@Override
	protected void validateVariableAssignment(AST rhsExpression, Token token)
	{
		if (rhsExpression != null)
		{

			/**
			 * Raise a to-do for below script statement.
			 * 
			 * [variable_name] = 'bpmScripts';
			 */
			if (ReservedWords.PROCESS_SCRIPT_LIBRARY_WRAPPER_NAME.equals(rhsExpression.getText()))
			{
				List<String> additionalAttributes = new ArrayList<String>();
				additionalAttributes.add(ReservedWords.PROCESS_SCRIPT_LIBRARY_WRAPPER_NAME);
				String errorMessage = Messages.JScriptAssignmentExpressionValidator_AssignmentOfBPMScriptReference;
				addErrorMessage(token, errorMessage, additionalAttributes);
			}

			/**
			 * Raise a to-do for below script statement.
			 * 
			 * [variable_name] = 'bpmScripts.[ProjectName];
			 */
			if (rhsExpression.getFirstChild() != null)
			{
				AST firstChild = rhsExpression.getFirstChild();
				if (ReservedWords.PROCESS_SCRIPT_LIBRARY_WRAPPER_NAME.equals(firstChild.getText()))
				{
					List<String> additionalAttributes = new ArrayList<String>();
					additionalAttributes.add(ReservedWords.PROCESS_SCRIPT_LIBRARY_WRAPPER_NAME);
					String errorMessage = Messages.JScriptAssignmentExpressionValidator_AssignmentOf_ProjectReference_UsingBPMScript;
					addErrorMessage(token, errorMessage, additionalAttributes);
				}
			}

		}
	}

}
