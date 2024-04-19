/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.processscriptlibrary.resource.rules;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.bpmn.rules.UnresolvedDataExternalReferenceRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;

/**
 * Rule to check any un-set or unresolved process data type external references in a PSL Function Param
 *
 * @author nkelkar
 * @since Apr 9, 2024
 */
public class UnresolvedDataExternalRefRule extends UnresolvedDataExternalReferenceRule
{
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
