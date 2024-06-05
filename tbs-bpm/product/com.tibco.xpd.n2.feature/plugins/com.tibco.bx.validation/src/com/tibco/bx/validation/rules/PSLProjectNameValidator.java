/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.bx.validation.rules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

import com.tibco.xpd.processscriptlibrary.resource.config.ProcessScriptLibraryConstants;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.validation.engine.WorkspaceResourceValidator;
import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * Process Script Library (PSL) project and library specific validator which covers scenarios not covered by
 * {@link ProjectNameValidator}. So basically this validator prevents project name starting with numeric characters and
 * PSL library name starting with dot(.)
 * 
 * @author ssirsika
 * @since 20-Mar-2024
 */
public class PSLProjectNameValidator implements WorkspaceResourceValidator
{
	private static final String		NAME_INVALID_ISSUE_ID	= "bx.invalidPSLProjectName";					//$NON-NLS-1$

	private static final String		LIBRARY_NAME_ISSUE_ID	= "bx.pslFileNameWithDotRestriction";	//$NON-NLS-1$

	// Regular expression pattern to match names that do not start with a numeric character and also should not contain
	// dot.
	private static final Pattern	PATTERN					= Pattern.compile("^[^0-9.]\\w*$");				//$NON-NLS-1$

	/**
	 * @see com.tibco.xpd.validation.engine.WorkspaceResourceValidator#validate(com.tibco.xpd.validation.provider.IValidationScope,
	 *      org.eclipse.core.resources.IResource)
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

				validatePSLLibrariesInProject(project, aScope);
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

	/**
	 * Validates a PSL Library name to make sure it does not start with a dot "." as such PSL files will be filtered out
	 * before validation is run. The remaining validations related to PSL file name is done in class
	 * PSLFileNameRestrictionRule.
	 * 
	 * @param aProject
	 * @param aScope
	 */
	private void validatePSLLibrariesInProject(IProject aProject, IValidationScope aScope)
	{
		Collection<IResource> allPsls = SpecialFolderUtil.getResourcesInSpecialFolderOfKind(aProject,
				ProcessScriptLibraryConstants.PSL_SPECIAL_FOLDER_KIND,
				ProcessScriptLibraryConstants.PSL_FILE_EXTENSION);

		for (IResource aPsl : allPsls)
		{
			if (aPsl.getName().startsWith(".")) //$NON-NLS-1$
			{
				Collection<String> additionalArguments = new ArrayList<String>();
				additionalArguments.add(aPsl.getProjectRelativePath().toString());

				aScope.createIssue(LIBRARY_NAME_ISSUE_ID, aPsl.getName(), aPsl.getProjectRelativePath().toString(),
						additionalArguments);
			}
		}
	}

}
