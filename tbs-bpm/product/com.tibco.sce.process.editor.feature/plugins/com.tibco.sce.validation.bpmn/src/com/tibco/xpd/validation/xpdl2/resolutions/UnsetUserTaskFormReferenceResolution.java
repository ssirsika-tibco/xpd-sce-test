/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Quick fix to configure user task as not referncing a form or pageflow
 * 
 * @author aallway
 * @since 3.2
 */
public class UnsetUserTaskFormReferenceResolution extends
        AbstractWorkingCopyResolution {
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        if (target instanceof Activity) {
            return TaskObjectUtil
                    .getUserTaskSetFormImplementationCommand(editingDomain,
                            (Activity) target,
                            null);
        }
        return null;
    }

}
