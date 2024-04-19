/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.process.js.parser.validator;

import java.util.ArrayList;
import java.util.List;

import com.tibco.xpd.analyst.resources.xpdl2.ReservedWords;
import com.tibco.xpd.process.js.parser.Messages;
import com.tibco.xpd.script.parser.internal.validator.jscript.JScriptVariableDeclarationValidator;

import antlr.Token;
import antlr.collections.AST;

/**
 * ProcessJScriptVariableDeclarationValidator validates for Variable Declaration Assignment.
 * 
 * Since validations for Variable Declaration Assignment occur at Process Script Editior Level so it makes much more
 * sense to have "ProcessJScriptVariableDeclarationValidator" as part of Process Editior Developer Feature.
 *
 * @author cbabar
 * @since Apr 15, 2024
 */
public class ProcessJScriptVariableDeclarationValidator extends JScriptVariableDeclarationValidator
{


	/**
	 * @see com.tibco.xpd.script.parser.internal.validator.jscript.JScriptVariableDeclarationValidator#validateVarDeclarationAssignment(antlr.collections.AST,
	 *      antlr.Token)
	 * 
	 *
	 * @param rhsExpression
	 * @param token
	 */
	@Override
	protected void validateVarDeclarationAssignment(AST rhsExpression, Token token)
	{
		if (rhsExpression != null)
		{


			/**
			 * Raise a to-do for below script statement
			 * 
			 * var [variable_name] = 'bpmScripts';
			 */
			if (ReservedWords.PROCESS_SCRIPT_LIBRARY_WRAPPER_NAME.equals(rhsExpression.getText()))
			{
				List<String> additionalAttributes = new ArrayList<String>();
				additionalAttributes.add(ReservedWords.PROCESS_SCRIPT_LIBRARY_WRAPPER_NAME.toString());
				String errorMessage = Messages.JScriptAssignmentExpressionValidator_AssignmentOfBPMScriptReference;
				addErrorMessage(token, errorMessage, additionalAttributes);
			}

			/**
			 * Raise a to-do for below script statement
			 * 
			 * var [variable_name] = 'bpmScripts.[ProjectName];
			 */
			if (rhsExpression.getFirstChild() != null)
			{
				AST firstChild = rhsExpression.getFirstChild();
				if (ReservedWords.PROCESS_SCRIPT_LIBRARY_WRAPPER_NAME.equals(firstChild.getText()))
				{
					List<String> additionalAttributes = new ArrayList<String>();
					additionalAttributes.add(ReservedWords.PROCESS_SCRIPT_LIBRARY_WRAPPER_NAME.toString());
					String errorMessage = Messages.JScriptAssignmentExpressionValidator_AssignmentOf_ProjectReference_UsingBPMScript;
					addErrorMessage(token, errorMessage, additionalAttributes);
				}
			}

		}
	}

}
