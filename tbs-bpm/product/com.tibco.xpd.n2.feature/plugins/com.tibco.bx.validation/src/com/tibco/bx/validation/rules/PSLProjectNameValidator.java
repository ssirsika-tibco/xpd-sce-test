/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.bx.validation.rules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

import com.tibco.xpd.processscriptlibrary.resource.config.ProcessScriptLibraryConstants;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.validation.engine.WorkspaceResourceValidator;
import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * Process Script Library (PSL) project specific validator which covers scenarios not covered by
 * {@link ProjectNameValidator}. So basically this validator prevents project name starting with numeric characters.
 * 
 * @author ssirsika
 * @since 20-Mar-2024
 */
public class PSLProjectNameValidator implements WorkspaceResourceValidator
{
	private static final String NAME_INVALID_ISSUE_ID = "bx.invalidPSLProjectName"; //$NON-NLS-1$

	// Regular expression pattern to match names that do not start with a numeric character and also should not contain dot.
	private static final Pattern	PATTERN					= Pattern.compile("^[^0-9.]\\w*$");	//$NON-NLS-1$

	/**
	 * @see com.tibco.xpd.validation.engine.WorkspaceResourceValidator#validate(com.tibco.xpd.validation.provider.IValidationScope, org.eclipse.core.resources.IResource)
	 *
	 * @param aScope
	 * @param aResource
	 */
	@Override
	public void validate(IValidationScope aScope, IResource aResource)
	{
		if (aResource instanceof IProject && aResource.isAccessible())
		{
			IProject project = (IProject) aResource;

			// Is PSL project
			if (SpecialFolderUtil.containsSpecialFolderOfKind(project,
					ProcessScriptLibraryConstants.PSL_SPECIAL_FOLDER_KIND))
			{
				String name = project.getName();
				Matcher matcher = PATTERN.matcher(name);
				if (!matcher.matches())
				{
					aScope.createIssue(NAME_INVALID_ISSUE_ID, project.getName(),
							project.getProjectRelativePath().toString());
				}
			}
		}

	}

	/**
	 * @see com.tibco.xpd.validation.engine.WorkspaceResourceValidator#setProject(org.eclipse.core.resources.IProject)
	 *
	 * @param project
	 */
	@Override
	public void setProject(IProject project)
	{
		// Do nothing
	}

}
