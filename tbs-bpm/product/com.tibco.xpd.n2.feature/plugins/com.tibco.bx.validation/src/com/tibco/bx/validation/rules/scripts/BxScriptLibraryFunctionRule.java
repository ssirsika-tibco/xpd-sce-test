/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.bx.validation.rules.scripts;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.js.validation.tools.ScriptTool;
import com.tibco.xpd.js.validation.tools.TaskScriptTool;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Expression;

/**
 * Process Script Library - task script validation rule.
 *
 * @author aallway
 * @since 8 Mar 2024
 */
public class BxScriptLibraryFunctionRule extends BxScriptTaskRule
{
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
		ScriptTool scriptTool = super.createScriptToolIfInterested(expression);
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
			IProject pslProject = WorkingCopyUtil.getProjectFor(o);
			return pslProject.getName() + ":"; //$NON-NLS-1$
		}

		return path;
	}

}
