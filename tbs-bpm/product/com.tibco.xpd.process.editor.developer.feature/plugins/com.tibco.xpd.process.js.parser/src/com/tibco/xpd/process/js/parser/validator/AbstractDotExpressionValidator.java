/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.process.js.parser.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.analyst.resources.xpdl2.ReservedWords;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.parser.Messages;
import com.tibco.xpd.script.parser.internal.expr.IExpr;
import com.tibco.xpd.script.parser.internal.validator.IValidateResult;
import com.tibco.xpd.script.parser.validator.jscript.JScriptDotExpressionValidator;
import com.tibco.xpd.validation.xpdl2.resolutions.SetReferencedProjectResolution;

import antlr.Token;
import antlr.collections.AST;

/**
 *
 * AbstractDotExpressionValidator validates for PSL Project References when script contains
 * "bpmScripts.<ValidPSLProjectReference>".
 * 
 * Since validations for PSL Project References occur at Script Editior Level so it makes much more sense to have
 * "AbstractDotExpressionValidator" as part of Process Editior Developer Feature.
 * 
 *
 * @author cbabar
 * @since Mar 12, 2024
 */
public class AbstractDotExpressionValidator extends JScriptDotExpressionValidator
{

	/** The issue id. */
	private static final String REFERENCE_ERROR_ID = "bx.validateScriptTaskForPSLReference"; //$NON-NLS-1$

	/**
	 * @see com.tibco.xpd.script.parser.validator.jscript.JScriptDotExpressionValidator#processExpressionAfterDot(com.tibco.xpd.script.parser.internal.expr.IExpr,
	 *      com.tibco.xpd.script.model.client.IScriptRelevantData, antlr.collections.AST, antlr.collections.AST,
	 *      antlr.Token, boolean)
	 *
	 * @param expression
	 * @param type
	 * @param astExpression
	 * @param firstChild
	 * @param token
	 * @param isStaticReference
	 * @return
	 */
	@Override
	protected IValidateResult processExpressionAfterDot(IExpr expression, IScriptRelevantData type, AST astExpression,
			AST firstChild, Token token, boolean isStaticReference)
	{
		
		if (firstChild != null && isBpmScriptsFirstChildFromStatement(firstChild))
		{
			// Check expression after the dot
            AST nextSibling = firstChild.getNextSibling();
			if (nextSibling != null)
			{
				String projectName = nextSibling.getText();

				if (getInput(getInfoObject()) != null)
				{
					// Get the project from the Workspace.
					IProject refProject = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);

					// If the Project is found from Workspace.
					if (refProject.exists())
					{
						IProject contextProject = WorkingCopyUtil.getProjectFor(getInput(getInfoObject()));

						try
						{
							/**
							 * If the referenced project and context project are not same then only raise Validation for
							 * adding the project reference if not present already.
							 */
							if (!contextProject.equals(refProject)
									&& !ProjectUtil.isDirectProjectReference(contextProject, refProject))
							{
								/**
								 * ACE-7371 : Raise the validation todo, if the <project_name> suffixing the
								 * "bpmScripits.<project_name>" is not added as reference in the current project in
								 * context.
								 */
								String message = Messages.ExpressionValidator_ProjectRefereceFor_bpmScripts_Type_NotAdded;
								List<String> additionalAttributes = new ArrayList<String>();
								additionalAttributes.add(projectName);
								additionalAttributes.add(projectName);

								Map<String, String> additionalInfoMap = new HashMap<>();
								additionalInfoMap.put(SetReferencedProjectResolution.PROJECTNAME_ADDITIONAL_INFO,
										projectName);

								addErrorMessage(token, message, additionalAttributes, REFERENCE_ERROR_ID,
										additionalInfoMap);
							}
						}
						catch (CoreException e)
						{
							e.printStackTrace();
						}

					}
				}
			}
		}
		return super.processExpressionAfterDot(expression, type, astExpression, firstChild, token, isStaticReference);
	}

	/**
	 * Function to determine weather give AST expression contains "bpmScripts" string as it's first child.
	 * 
	 * 
	 * @param firstChild
	 * @return {boolean} True if "bpmScripts" is the first child, otherwise false
	 */
	private boolean isBpmScriptsFirstChildFromStatement(AST firstChild)
	{
		return firstChild != null && ReservedWords.PROCESS_SCRIPT_LIBRARY_WRAPPER_NAME.equals(firstChild.getText())
				&& firstChild.getFirstChild() == null;
	}
}
