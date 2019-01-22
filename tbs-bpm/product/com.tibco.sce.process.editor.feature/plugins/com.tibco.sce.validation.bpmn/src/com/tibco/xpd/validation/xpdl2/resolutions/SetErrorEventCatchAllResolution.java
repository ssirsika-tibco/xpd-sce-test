/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.ResultError;

/**
 *
 * @author aallway
 * @since 3.2
 */
public class SetErrorEventCatchAllResolution extends
        AbstractWorkingCopyResolution {

    /* (non-Javadoc)
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     */
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        //
        // Check that target is a catch error event, attached to task boundary.
        //
        if (target instanceof Activity) {
            Activity catchErrorEvent = (Activity) target;

            if (catchErrorEvent.getEvent() instanceof IntermediateEvent
                    && catchErrorEvent.getEvent().getEventTriggerTypeNode() instanceof ResultError) {
                if (BpmnCatchableErrorUtil.getAttachedToTask(catchErrorEvent) != null) {
                    return BpmnCatchableErrorUtil.createSetCatchAllCommand(editingDomain, catchErrorEvent);
                }
            }
        }
        return null;
    }

}
