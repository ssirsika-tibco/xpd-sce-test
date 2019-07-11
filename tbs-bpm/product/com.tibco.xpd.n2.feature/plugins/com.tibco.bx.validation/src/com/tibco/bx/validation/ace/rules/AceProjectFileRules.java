/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.bx.validation.ace.rules;

import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

import com.tibco.xpd.validation.engine.WorkspaceResourceValidator;
import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * ACE specific rules related to project files.
 *
 * <li>Project-relative file-path must not exceed 100 characters.</li>
 * 
 * 
 * @author aallway
 * @since 10 Jul 2019
 */
public class AceProjectFileRules implements WorkspaceResourceValidator {

    private static final String ACE_ISSUE_FILEPATH_TOO_LONG = "ace.filepath.too.long"; //$NON-NLS-1$

    private static final String ACE_ISSUE_FILENAME_TOO_LONG = "ace.filename.too.long"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.engine.WorkspaceResourceValidator#validate(com.tibco.xpd.validation.provider.IValidationScope,
     *      org.eclipse.core.resources.IResource)
     *
     * @param scope
     * @param resource
     */
    @Override
    public void validate(IValidationScope scope, IResource resource) {
        if (resource instanceof IFile && resource.isAccessible()) {
            /*
             * Sid ACE-1586 Validate project-relative filepath/name length.
             */
            if (resource.getName() != null && resource.getName().length() > 100) {
                scope.createIssue(ACE_ISSUE_FILENAME_TOO_LONG,
                        resource.getName(),
                        resource.getProjectRelativePath().toString(),
                        Collections.singletonList("100")); //$NON-NLS-1$
            }

            if (resource.getProjectRelativePath() != null
                    && resource.getProjectRelativePath().toString().length() > 250) {
                scope.createIssue(ACE_ISSUE_FILEPATH_TOO_LONG,
                        resource.getName(),
                        resource.getProjectRelativePath().toString(),
                        Collections.singletonList("250")); //$NON-NLS-1$
            }
        }
    }

    /**
     * @see com.tibco.xpd.validation.engine.WorkspaceResourceValidator#setProject(org.eclipse.core.resources.IProject)
     *
     * @param project
     */
    @Override
    public void setProject(IProject project) {
        // do nothing
    }

}
