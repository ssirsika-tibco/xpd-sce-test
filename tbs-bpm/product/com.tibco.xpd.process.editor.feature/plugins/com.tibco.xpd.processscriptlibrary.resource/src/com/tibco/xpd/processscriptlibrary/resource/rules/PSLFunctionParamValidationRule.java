/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.processscriptlibrary.resource.rules;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;

/**
 * Verifies that the name text for a Process Script Library Function Parameter follows all naming conventions. Also
 * verifies the length of name and description of a PSL function param.
 *
 * @author nkelkar
 * @since Apr 5, 2024
 */
public class PSLFunctionParamValidationRule extends ProcessActivitiesValidationRule
{
	private static final String	NAME_MAX_LENGTH_ISSUE_ID		= "bpmn.functionParam.name.length";			//$NON-NLS-1$

	private static final String	DESCRIPTION_MAX_LENGTH_ISSUE_ID	= "bpmn.functionParam.description.length";	//$NON-NLS-1$

	private static final String	INVALID_NAME_ISSUE_ID			= "bpmn.functionParam.invalidName";			//$NON-NLS-1$

	private static final String	DUPLICATE_PARAM_NAME_ISSUE_ID	= "bpmn.functionParam.duplicate";			//$NON-NLS-1$

	private static final String	RETURN_PARAMETER_NAME			= "$RETURN";								//$NON-NLS-1$

	/**
	 * @see com.tibco.xpd.validation.xpdl2.rules.ActivityValidationRule#validate(com.tibco.xpd.xpdl2.Activity)
	 *
	 * @param container
	 */
	@Override
	public void validate(Activity activity)
	{
		List<DataField> allDataFieldsInActivity = activity.getDataFields();
		Map<String, Integer> duplicateNamesMap = new HashMap<>();

		for (DataField dataField : allDataFieldsInActivity)
		{
			validate(dataField);
			duplicateNamesMap.put(dataField.getName(), duplicateNamesMap.getOrDefault(dataField.getName(), 0) + 1);
		}

		for (DataField dataField : allDataFieldsInActivity)
		{
			if (duplicateNamesMap.get(dataField.getName()) > 1)
			{
				// Add issue for duplicate names
				addIssue(DUPLICATE_PARAM_NAME_ISSUE_ID, dataField);

			}
		}
	}

	/**
	 * Validate the given Data Field
	 * 
	 * @param dataField
	 */
	public void validate(DataField dataField)
	{
		if (dataField.getName() != null && (!RETURN_PARAMETER_NAME.equals(dataField.getName())))
		{
			if (!NameUtil.isValidName(dataField.getName(), true))
			{
				addIssue(INVALID_NAME_ISSUE_ID, dataField);
			}
			if (dataField.getName().length() > 100)
			{
				addIssue(NAME_MAX_LENGTH_ISSUE_ID, dataField);
			}

		}

		if (dataField.getDescription() != null)
		{
			if (dataField.getDescription().getValue().length() > 1024)
			{
				addIssue(DESCRIPTION_MAX_LENGTH_ISSUE_ID, dataField);
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
		 * For a PSL Function Param show the project name and activity name as a prefix in validation messages
		 */
		if (o instanceof DataField)
		{
			EObject container = o.eContainer();
			if (container instanceof Activity)
			{
				IProject pslProject = WorkingCopyUtil.getProjectFor(o);
				path = pslProject.getName() + ":"; //$NON-NLS-1$
				path += ((Activity) container).getName() + ":"; //$NON-NLS-1$
			}
		}
		return path;
	}

}
