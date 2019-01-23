/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions.pageflow;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Quick fix resolution to allow user to select a form file and configure user
 * task activity to reference it.
 * 
 * @author aallway
 * @since 3.2
 */
public class SelectUserTaskPageflowResolution extends
        AbstractWorkingCopyResolution  {

   
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        if (target instanceof Activity) {
            return TaskObjectUtil
                    .getUserTaskSetPageflowProcessFromPickerCommand(Display
                            .getDefault().getActiveShell(),
                            editingDomain,
                            (Activity) target);
        }
        return null;
    }

}
