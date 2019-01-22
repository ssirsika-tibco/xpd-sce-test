/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bizassets.resources.quality;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

import com.tibco.xpd.bizassets.resources.BusinessassetsPlugin;

/**
 * @author nwilson
 */
public class ArchiveFileValidator implements ISelectionStatusValidator {

    /**
     * @param selection
     * @return
     * @see org.eclipse.ui.dialogs.ISelectionStatusValidator#validate(java.lang.Object[])
     */
    public IStatus validate(Object[] selection) {
        IStatus status = new Status(IStatus.ERROR, BusinessassetsPlugin.ID,
                "Select a zip file"); //$NON-NLS-1$
        if (selection.length == 1) {
            if (selection[0] instanceof IFile) {
                IFile file = (IFile) selection[0];
                if ("zip".equals(file.getFileExtension())) { //$NON-NLS-1$
                    status = Status.OK_STATUS;
                }
            }
        }
        return status;
    }

}
