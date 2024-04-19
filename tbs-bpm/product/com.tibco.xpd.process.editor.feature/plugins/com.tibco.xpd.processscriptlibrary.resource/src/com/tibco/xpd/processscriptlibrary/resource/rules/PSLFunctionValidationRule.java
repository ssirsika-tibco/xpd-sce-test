/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.processscriptlibrary.resource.rules;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Verifies that the name text for a Process Script Library Function follows all naming conventions. Also verifies the
 * length of name and description of a PSL function.
 *
 * @author nkelkar
 * @since Mar 14, 2024
 */
public class PSLFunctionValidationRule extends ProcessActivitiesValidationRule
{
	private static final String	NAME_MAX_LENGTH_ISSUE_ID		= "bpmn.functionName.length";				//$NON-NLS-1$

	private static final String	DESCRIPTION_MAX_LENGTH_ISSUE_ID	= "bpmn.functionDescription.length";		//$NON-NLS-1$

	private static final String	INVALID_NAME_ISSUE_ID			= "bpmn.functionName.validJSIdentifier";	//$NON-NLS-1$

	private static final String	DUPLICATE_NAME_ISSUE_ID			= "bpmn.functionName.duplicate";			//$NON-NLS-1$

	/**
	 * @see com.tibco.xpd.validation.xpdl2.rules.ActivityValidationRule#validate(com.tibco.xpd.xpdl2.Activity)
	 *
	 * @param container
	 */
	@Override
	public void validate(Activity activity)
	{
		if (activity.getName() != null)
		{
			if (!NameUtil.isValidName(activity.getName(), false))
			{
				addIssue(INVALID_NAME_ISSUE_ID, activity);
			}
			if (activity.getName().length() > 100)
			{
				addIssue(NAME_MAX_LENGTH_ISSUE_ID, activity);
			}

		}

		if (activity.getDescription() != null)
		{
			if (activity.getDescription().getValue().length() > 1024)
			{
				addIssue(DESCRIPTION_MAX_LENGTH_ISSUE_ID, activity);
			}

		}

	}

	/**
	 * @see com.tibco.xpd.validation.xpdl2.rules.Xpdl2ValidationRule#validate(java.lang.Object)
	 * 
	 * @param o
	 */
	@Override
	protected void validate(Object o)
	{
		if (o instanceof Process)
		{
			Collection<Activity> allActivitiesInProc = Xpdl2ModelUtil.getAllActivitiesInProc((Process) o);
			Map<String, Integer> duplicateNamesMap = new HashMap<>();

			for (Activity activity : allActivitiesInProc)
			{
				validate(activity);
				duplicateNamesMap.put(activity.getName(), 
						duplicateNamesMap.getOrDefault(activity.getName(), 0) + 1);
			}

			for (Activity activity : allActivitiesInProc)
			{
				if (duplicateNamesMap.get(activity.getName()) > 1)
				{
					// Add issue for duplicate names
					addIssue(DUPLICATE_NAME_ISSUE_ID, activity);
				}
			}
		}

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
		 * For a PSL Function (Activity) show the project name as a prefix in validation message
		 */
		if (o instanceof Activity)
		{
			IProject pslProject = WorkingCopyUtil.getProjectFor(o);
			return pslProject.getName() + ":"; //$NON-NLS-1$
		}

		return path;
	}

}
