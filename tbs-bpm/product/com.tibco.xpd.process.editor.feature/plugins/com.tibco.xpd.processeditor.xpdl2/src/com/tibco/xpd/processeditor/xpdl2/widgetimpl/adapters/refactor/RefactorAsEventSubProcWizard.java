/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor;

import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;

/**
 * Wizard to refactor an event handler flow to an event sub-process.
 * 
 * @author sajain
 * @since Oct 7, 2014
 */
public class RefactorAsEventSubProcWizard extends BaseRefactorAsSubProcWizard {

    /**
     * Wizard to refactor an event handler flow to an event sub-process.
     * 
     * @param refactorInfo
     * @param editingDomain
     */
    public RefactorAsEventSubProcWizard(
            RefactorAsSubProcWizardInfo refactorInfo,
            EditingDomain editingDomain) {

        super(
                refactorInfo,
                editingDomain,
                Messages.RefactorAsEventSubProcCommand_RefactorAsEventSubproc_title,
                Messages.RefactorAsEventSubProcWizard_RefactorAsEventSubproc_longdesc);
    }

}
