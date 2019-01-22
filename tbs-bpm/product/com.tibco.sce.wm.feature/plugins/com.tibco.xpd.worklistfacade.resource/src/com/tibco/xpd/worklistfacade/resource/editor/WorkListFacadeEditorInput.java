/**
 *  Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.worklistfacade.resource.editor;

import org.eclipse.core.resources.IFile;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.wc.InvalidFileException;
import com.tibco.xpd.worklistfacade.model.DocumentRoot;
import com.tibco.xpd.worklistfacade.model.WorkListFacade;
import com.tibco.xpd.worklistfacade.resource.editor.util.WorkListFacadeEditorUtil;
import com.tibco.xpd.worklistfacade.resource.util.Messages;
import com.tibco.xpd.worklistfacade.resource.workingcopy.WorkListFacadeWorkingCopy;

/**
 * The Editor Input for WorkListFacadeEditor. It extends FileEditorInput and
 * adds methods like getfileName, getModelRoot.
 * 
 * @author aprasad
 * 
 */
public class WorkListFacadeEditorInput extends FileEditorInput {

    /**
     * Working Copy associated with this EditorInput.
     */
    private WorkListFacadeWorkingCopy workingCopy;

    /**
     * @return the WorkListFacadeWorkinCopy associated with this EditorInput.
     */
    public WorkListFacadeWorkingCopy getWorkingCopy() {
        return workingCopy;
    }

    /**
     * Constructor for {@link WorkListFacadeEditorInput}, throws
     * InvalidFileException if the given file is not a valid WorkListFacadeFile.
     * Retrieves the Working Copy for this file.
     * 
     * @param file
     * @throws InvalidFileException
     */
    public WorkListFacadeEditorInput(IFile file) throws InvalidFileException {

        super(file);
        if (WorkListFacadeEditorUtil.isWorkListFacadeFile(file)) {
            // if a WorkListFacade file
            WorkingCopy tempWorkingCopy = WorkingCopyUtil.getWorkingCopy(file);

            // Contains valid WorkListFacade contents
            if (tempWorkingCopy instanceof WorkListFacadeWorkingCopy) {
                // get the WorkingCopy
                workingCopy =
                        (WorkListFacadeWorkingCopy) WorkingCopyUtil
                                .getWorkingCopy(file);
            }
        } else {
            throw new InvalidFileException(
                    Messages.WorkListFacadeEditor_Invalid_File);
        }
    }

    /**
     * Returns name of the Source file in this EditorInput.
     * 
     * @return name of the source file of the Editor Input.
     */
    public String getfileName() {
        return getFile().getName();
    }

    /**
     * Returns the {@link WorkListFacade} root element of this Editor Input.
     * Returns null if root element does not exist.
     * 
     * @return , {@link WorkListFacade}, root element of the WorkListFacade
     *         file.
     */
    public WorkListFacade getModelRoot() {
        WorkListFacadeWorkingCopy workingcopy = getWorkingCopy();

        if (workingcopy != null && workingcopy.getRootElement() != null) {
            return ((DocumentRoot) workingcopy.getRootElement())
                    .getWorkListFacade();
        }
        return null;
    }
}
