/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.resources.refactoring;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.RenameParticipant;

import com.tibco.xpd.resources.internal.Messages;
import com.tibco.xpd.resources.util.ProjectUtil;

/**
 * class to disallow certain special characters from being entered during
 * project/resource rename
 * 
 * @author bharge
 * 
 */
public class ResourceNameRefactorParticipant extends RenameParticipant {

    /**
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#checkConditions(org.eclipse.core.runtime.IProgressMonitor,
     *      org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext)
     * 
     * @param pm
     * @param context
     * @return
     * @throws OperationCanceledException
     */
    @Override
    public RefactoringStatus checkConditions(IProgressMonitor pm,
            CheckConditionsContext context) throws OperationCanceledException {
        RefactoringStatus status = new RefactoringStatus();
        /**
         * check for invalid # character while renaming project/any resource
         * under project
         */

        String refactorResourceName = getArguments().getNewName();
        String msgLst =
                ProjectUtil
                        .getDisplayableLstOfInvalidCharacters(ProjectUtil.URI_DISRUPTIVE_CHARACTERS_PATTERN,
                                refactorResourceName);
        if ((msgLst != null) && (!msgLst.isEmpty())) {
            status.addFatalError(Messages.ResourceNameRefactorParticipant_nameHasUnsupportedCharacters_shortdesc
                    + " '" + refactorResourceName + "' " + msgLst); //$NON-NLS-1$ //$NON-NLS-2$
        }

        return status;
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#createChange(org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param pm
     * @return
     * @throws CoreException
     * @throws OperationCanceledException
     */
    @Override
    public Change createChange(IProgressMonitor pm) throws CoreException,
            OperationCanceledException {
        return null;
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#getName()
     * 
     * @return
     */
    @Override
    public String getName() {
        return Messages.RenameActionContributorParticipant_ProjectRenameParticipant_Label;
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#initialize(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    protected boolean initialize(Object element) {
        return true;
    }

}
