/**
 * Copyright (c) TIBCO Software Inc 2004-2012. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.internal.refactor;

import org.eclipse.core.resources.IFile;

import com.tibco.xpd.processeditor.xpdl2.internal.refactor.change.UpdateFormReferenceChange;
import com.tibco.xpd.processeditor.xpdl2.internal.refactor.change.UpdateReferenceChange;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;

/**
 * Refactoring participant to update all references in an XPDL model when a
 * referenced Form file is renamed.
 * 
 * @author njpatel
 * @since 3.5.10
 */
public class FormReferenceRenameParticipant extends
        XpdlReferenceRenameParticipant {

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.internal.refactor.XpdlReferenceRenameParticipant#createHelper()
     * 
     * @return
     */
    @Override
    protected XpdlReferenceRefactorHelper createHelper() {
        return new FormReferenceRefactorHelper();
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.internal.refactor.XpdlReferenceRenameParticipant#createChange(com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl,
     *      org.eclipse.core.resources.IFile[],
     *      org.eclipse.core.resources.IFile[])
     * 
     * @param wc
     * @param oldFiles
     * @param newFiles
     * @return
     */
    @Override
    public UpdateReferenceChange createChange(Xpdl2WorkingCopyImpl wc,
            IFile[] oldFiles, IFile[] newFiles) {

        return new UpdateFormReferenceChange(wc, oldFiles, newFiles);
    }

}
