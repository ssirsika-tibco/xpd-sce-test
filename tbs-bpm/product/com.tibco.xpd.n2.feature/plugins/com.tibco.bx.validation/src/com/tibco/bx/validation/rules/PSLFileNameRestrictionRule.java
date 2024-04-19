/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */

package com.tibco.bx.validation.rules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

import com.tibco.xpd.processscriptlibrary.resource.config.ProcessScriptLibraryConstants;
import com.tibco.xpd.validation.engine.WorkspaceResourceValidator;
import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * Process Script Library (PSL) file specific validator which make sure that name is a valid Javascrit identifier.
 *
 * @author ssirsika
 * @since 21-Mar-2024
 */
public class PSLFileNameRestrictionRule implements WorkspaceResourceValidator
{
	private static final String		ISSUE_ID	= "bx.pslFileNameRestrictions";		//$NON-NLS-1$

	// Regular expression for a valid JavaScript identifier
	private static final Pattern	PATTERN		= Pattern.compile("^[a-zA-Z_][a-zA-Z\\d_]*$");	//$NON-NLS-1$

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
		if (aResource instanceof IFile)
		{
			String name = removePSLExtension(aResource.getName());
			Matcher matcher = PATTERN.matcher(name);
			if (!matcher.matches())
			{
				aScope.createIssue(ISSUE_ID, aResource.getName(), aResource.getProjectRelativePath().toString());
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
	 * Removes the psl extension from the file name.
	 *
	 * @param fileName
	 *            The name of the file.
	 * @return The file name without the extension.
	 */
	private String removePSLExtension(String fileName)
	{
		if (fileName == null)
		{
			return fileName;
		}
		String extension = ProcessScriptLibraryConstants.PSL_FILE_EXTENSION;

		// Check if the fileName ends with the extension
		if (fileName.toLowerCase().endsWith(extension.toLowerCase()))
		{
			// Remove the extension.
			return fileName.substring(0, fileName.length() - extension.length() - 1);
		}

		return fileName;
	}
}
