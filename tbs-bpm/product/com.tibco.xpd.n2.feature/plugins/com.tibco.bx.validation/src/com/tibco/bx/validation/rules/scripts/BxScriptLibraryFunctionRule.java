/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.bx.validation.rules.scripts;

import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.validation.internal.Messages;
import com.tibco.xpd.js.validation.tools.ScriptTool;
import com.tibco.xpd.js.validation.tools.TaskScriptTool;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.process.js.parser.util.ScriptParserUtil;
import com.tibco.xpd.processscriptlibrary.resource.editor.util.PslEditorUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.script.parser.validator.ErrorMessage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.UniqueIdElement;

/**
 * Process Script Library - task script validation rule.
 *
 * @author aallway
 * @since 8 Mar 2024
 */
public class BxScriptLibraryFunctionRule extends BxScriptTaskRule
{
	ScriptTool scriptTool = null;
	/**
	 * @see com.tibco.xpd.js.validation.rules.AbstractTaskScriptRule#getScriptContext()
	 *
	 * @return
	 */
	@Override
	protected String getScriptContext()
	{
		return ProcessJsConsts.SCRIPT_LIBRARY_FUNCTION;
	}

	/**
	 * 
	 * @see com.tibco.xpd.js.validation.rules.AbstractTaskScriptRule#createScriptToolIfInterested(com.tibco.xpd.xpdl2.Expression)
	 *
	 * @param expression
	 * @return
	 */
	@Override
	protected ScriptTool createScriptToolIfInterested(Expression expression)
	{
		scriptTool = super.createScriptToolIfInterested(expression);
		/*
		 * Should be TaskScriptTool, but we need to override the script type to be script context for
		 * ScriptLibaryFunction.
		 */
		((TaskScriptTool) scriptTool).overrideScriptType(ProcessJsConsts.SCRIPT_LIBRARY_FUNCTION);
		return scriptTool;
	}

	/**
	 * 
	 * @see com.tibco.xpd.validation.xpdl2.rules.Xpdl2ValidationRule#getLocationPath(java.lang.String,
	 *      org.eclipse.emf.ecore.EObject)
	 *
	 * @param path
	 * @param o
	 * @return
	 */
	@Override
	protected String getLocationPath(String path, EObject o)
	{
		/*
		 * For ScriptLibaryFunction show the project name as a prefix in validation message
		 */
		if (o instanceof Activity)
		{
			IFile pslFile = WorkingCopyUtil.getFile(o);
			return pslFile.getName() + ":"; //$NON-NLS-1$
		}

		return path;
	}

	/**
	 * 
	 * @see com.tibco.xpd.js.validation.rules.AbstractExpressionRule#performAdditionalValidation(com.tibco.xpd.xpdl2.Expression,
	 *      com.tibco.xpd.xpdl2.UniqueIdElement)
	 *
	 * @param expression
	 * @param expressionIssueHost
	 */
	@Override
	protected void performAdditionalValidation(Expression expression, UniqueIdElement expressionIssueHost)
	{
		if (scriptTool != null)
		{
			EObject input = scriptTool.getEObject();

			boolean isPSLfunctionWithNonVoidReturn = isPSLFunction(input) && isReturnTypeParamaterOfTypeNonVoid(input);

			String scriptGrammar = expression.getScriptGrammar();
			String text = expression.getText();

			// ACE-8286 PSL Functions with no content (except comments)should have a return statement validation
			if (ScriptParserUtil.isEmptyScript(text, scriptGrammar) && isPSLfunctionWithNonVoidReturn)
			{
				ErrorMessage errorMessage = new ErrorMessage(0, 0,
						Messages.N2FunctionStatementValidator_PSFunctionWithNonVoidReturnTypeParamter_LastStatementWithReturn);

				reportError(input, Collections.singletonList(errorMessage));
			}
		}
	}

	/**
	 * Returns true if the $RETURN type parameter in process script library function is of type non-void return; false
	 * otherwise
	 * 
	 * @param input
	 * @return
	 */
	protected boolean isReturnTypeParamaterOfTypeNonVoid(EObject input)
	{
		boolean isReturnTypeParameterOfTypeNonVoid = false;
		if (input instanceof Activity)
		{
			EList<DataField> activityDataList = ((Activity) input).getDataFields();
			for (DataField dataField : activityDataList)
			{
				if (PslEditorUtil.RETURN_PARAMETER_NAME.equals(dataField.getName()))
				{
					DataType dataType = dataField.getDataType();
					isReturnTypeParameterOfTypeNonVoid = (dataType != null);
				}
			}
		}
		return isReturnTypeParameterOfTypeNonVoid;
	}

	/**
	 * Returns true if the the current input in selection is a script library function; false otherwise
	 * 
	 * @param input
	 * @return
	 */
	private boolean isPSLFunction(EObject input)
	{
		return PslEditorUtil.isScriptLibraryFunction(input);
	}

}
