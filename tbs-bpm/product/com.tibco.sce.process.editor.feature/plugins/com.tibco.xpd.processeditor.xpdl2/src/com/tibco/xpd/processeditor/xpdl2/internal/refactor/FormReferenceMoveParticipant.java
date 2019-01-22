/**
 * Copyright (c) TIBCO Software Inc 2004-2012. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.internal.refactor;

import org.eclipse.core.resources.IFile;

import com.tibco.xpd.processeditor.xpdl2.internal.refactor.change.UpdateFormReferenceChange;
import com.tibco.xpd.processeditor.xpdl2.internal.refactor.change.UpdateReferenceChange;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;

/**
 * 
 * 
 * @author njpatel
 * @since 3.5.10
 */
public class FormReferenceMoveParticipant extends XpdlReferenceMoveParticipant {

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.internal.refactor.XpdlReferenceMoveParticipant#createChange(com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl,
     *      org.eclipse.core.resources.IFile[],
     *      org.eclipse.core.resources.IFile[])
     * 
     * @param wc
     * @param currentFiles
     * @param refactoredFiles
     * @return
     */
    @Override
    public UpdateReferenceChange createChange(Xpdl2WorkingCopyImpl wc,
            IFile[] currentFiles, IFile[] refactoredFiles) {
        return new UpdateFormReferenceChange(wc, currentFiles, refactoredFiles);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.internal.refactor.XpdlReferenceRenameParticipant#createHelper()
     * 
     * @param changeProvider
     * @return
     */
    @Override
    protected XpdlReferenceRefactorHelper createHelper() {
        return new FormReferenceRefactorHelper();
    }

}
