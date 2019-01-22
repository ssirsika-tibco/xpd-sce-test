/**
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.internal.refactor.change;

import org.eclipse.core.resources.IFile;

import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;

/**
 * Interface to be implemented by the refactor participant that will provide the
 * Change object to the refactor helper.
 * 
 * @author njpatel
 * @since 3.5.10
 */
public interface IRefactorReferenceChangeProvider {

    /**
     * Create the {@link UpdateReferenceChange} to perform the refactor.
     * 
     * @param wc
     *            working copy of the xpdl being refactored
     * @param currentFiles
     *            the files being renamed/moved
     * @param refactoredFiles
     *            the files after they will be renamed/moved
     * @return
     */
    UpdateReferenceChange createChange(Xpdl2WorkingCopyImpl wc,
            IFile[] currentFiles, IFile[] refactoredFiles);

}
