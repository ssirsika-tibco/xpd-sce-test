/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.processscriptlibrary.resource.rules;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.bpmn.rules.BasicTypeLengthRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * BasicTypeLengthRule - validates the length (for integer, and decimal data types) to be greater than zero
 * 
 * Validates the scale (for decimal data types) to be non-negative number validates if the scale (for decimal data
 * types) is greater than the length specified
 *
 * @author nkelkar
 * @since Apr 2, 2024
 */
public class BasicTypeLengthScaleRule extends BasicTypeLengthRule
{

	/**
	 * 
	 * @see com.tibco.xpd.validation.bpmn.rules.BasicTypeLengthRule#getDataFields(com.tibco.xpd.xpdl2.Package)
	 *
	 * @param pckg
	 * @return
	 */
	@Override
	protected List<ProcessRelevantData> getDataFields(Package pckg)
	{
		List<ProcessRelevantData> prdFields = new ArrayList<>();

		List<Process> processes = pckg.getProcesses();
		for (Process process : processes)
		{
			for (Activity activity : Xpdl2ModelUtil.getAllActivitiesInProc(process))
			{
				List<DataField> dataFields = activity.getDataFields();
				prdFields.addAll(dataFields);
			}
		}
		return prdFields;

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
