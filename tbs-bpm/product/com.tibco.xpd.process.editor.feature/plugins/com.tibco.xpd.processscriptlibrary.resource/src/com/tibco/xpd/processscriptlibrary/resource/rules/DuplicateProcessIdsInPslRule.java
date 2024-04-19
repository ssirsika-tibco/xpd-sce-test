/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.processscriptlibrary.resource.rules;

import com.tibco.xpd.processscriptlibrary.resource.config.ProcessScriptLibraryConstants;
import com.tibco.xpd.validation.xpdl2.rules.file.DuplicateProcessIdsValidationRule;

/**
 * Validation rule that validates duplicate processIds in a PSL Project.
 *
 * @author nkelkar
 * @since Mar 28, 2024
 */
public class DuplicateProcessIdsInPslRule extends DuplicateProcessIdsValidationRule
{
	/**
	 * 
	 * @see com.tibco.xpd.validation.xpdl2.rules.file.DuplicateProcessIdsValidationRule#getSpecialFolderKind()
	 *
	 * @return
	 */
	@Override
	protected String getSpecialFolderKind()
	{
		return ProcessScriptLibraryConstants.PSL_SPECIAL_FOLDER_KIND;
	}

	/**
	 * 
	 * @see com.tibco.xpd.validation.xpdl2.rules.file.DuplicateProcessIdsValidationRule#getFileExtension()
	 *
	 * @return
	 */
	@Override
	protected String getFileExtension()
	{
		return ProcessScriptLibraryConstants.PSL_FILE_EXTENSION;
	}

	/**
	 * 
	 * @see com.tibco.xpd.validation.xpdl2.rules.file.DuplicateProcessIdsValidationRule#getDuplicateIdIssueId()
	 *
	 * @return
	 */
	@Override
	protected String getDuplicateIdIssueId()
	{
		return "bpmn.psl.duplicateIDs"; //$NON-NLS-1$
	}

	/**
	 * 
	 * @see com.tibco.xpd.validation.xpdl2.rules.file.DuplicateProcessIdsValidationRule#getDuplicateIdsInFileIssueId()
	 *
	 * @return
	 */
	@Override
	protected String getDuplicateIdsInFileIssueId()
	{
		return "bpmn.psl.duplicateIDsInTheFile"; //$NON-NLS-1$
	}
}
