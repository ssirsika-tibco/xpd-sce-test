/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventAdapter.TimerTriggerMode;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Event;

/**
 * SetEventTimerModeToDateTimeResolution - quick fix to set the date time
 * trigger mode for unsupported cyclic event
 * 
 * 
 * @author agondal
 * @since 3.6 (14 Nov 2012)
 */
public class SetEventTimerModeToDateTimeResolution extends
        AbstractWorkingCopyResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        if (target instanceof Activity) {
            Activity activity = (Activity) target;
            Event event = activity.getEvent();
            if (event != null) {
                Command cmd =
                        EventObjectUtil
                                .getSetTimerTriggerModeCommand(editingDomain,
                                        activity,
                                        TimerTriggerMode.DATETIME);
                if (cmd != null) {
                    return cmd;
                }
            }
        }
        return null;
    }

}
