/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.analyst.resources.xpdl2.pickers.LocalTaskReferenceActivityPicker;
import com.tibco.xpd.processeditor.xpdl2.util.ReferenceTaskUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Reference;

/**
 * Resolution to allow user to select a local process task reference for a
 * reference task.
 * 
 * @author aallway
 * @since 3.2
 */
public class SelectLocalTaskReferenceResolution extends
        AbstractWorkingCopyResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {

        if (target instanceof Activity
                && ((Activity) target).getImplementation() instanceof Reference) {

            Activity activity = (Activity) target;

            LocalTaskReferenceActivityPicker picker =
                    new LocalTaskReferenceActivityPicker(Display.getDefault()
                            .getActiveShell(), activity.getProcess());

            if (picker.open() == picker.OK) {
                Object[] result = picker.getResult();

                if (result.length == 1 && result[0] instanceof Activity) {
                    Activity taskAct = (Activity) result[0];

                    return ReferenceTaskUtil
                            .getSetReferencedLocalTaskCommand(editingDomain,
                                    activity,
                                    taskAct.getId());
                }
            }
        }
        return null;
    }

}
