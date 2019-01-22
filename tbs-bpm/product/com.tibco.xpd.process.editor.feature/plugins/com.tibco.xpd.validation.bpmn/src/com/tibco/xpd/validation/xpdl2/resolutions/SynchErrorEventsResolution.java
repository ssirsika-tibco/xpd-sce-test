/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.ErrorMethod;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.util.ThrowErrorEventUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Resolution to set the fault message of an error event if it implements an
 * error method belonging to a request activity.
 * 
 * @author rsomayaj
 * 
 */
public class SynchErrorEventsResolution extends AbstractWorkingCopyResolution {

    /**
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     * 
     * @param editingDomain
     * @param target
     * @param marker
     * @return
     * @throws ResolutionException
     */
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        if (target instanceof Activity) {
            Activity activity = (Activity) target;
            if (Xpdl2ModelUtil.isEventImplemented(activity)) {
                ErrorMethod implementedErrorMethod =
                        ProcessInterfaceUtil
                                .getImplementedErrorMethod(activity);
                if (implementedErrorMethod != null) {
                    if (implementedErrorMethod.eContainer() instanceof InterfaceMethod) {
                        InterfaceMethod interfaceMethod =
                                (InterfaceMethod) implementedErrorMethod
                                        .eContainer();
                        boolean isMessageEvent =
                                TriggerType.MESSAGE_LITERAL
                                        .equals(interfaceMethod.getTrigger());
                        if (isMessageEvent) {
                            Activity implementingRequestActivity =
                                    findCorrespondingRequestActivity(activity
                                            .getProcess(), interfaceMethod);
                            if (implementingRequestActivity != null) {
                                return ThrowErrorEventUtil
                                        .getConfigureAsFaultMessageErrorCommand(editingDomain,
                                                activity,
                                                implementingRequestActivity
                                                        .getId(),
                                                implementedErrorMethod
                                                        .getErrorCode());
                            }
                        } else {
                            return ThrowErrorEventUtil
                                    .getConfigureAsProcessErrorCommand(editingDomain,
                                            activity,
                                            implementedErrorMethod
                                                    .getErrorCode());
                        }

                    }
                }

            }

        }
        return null;
    }

    /**
     * @param process
     * @param interfaceMethod
     */
    private Activity findCorrespondingRequestActivity(Process process,
            InterfaceMethod interfaceMethod) {
        Activity correspondingRequestActivity =
                ProcessInterfaceUtil.findCorrespondingStartEvent(process,
                        interfaceMethod);
        if (correspondingRequestActivity == null) {
            correspondingRequestActivity =
                    ProcessInterfaceUtil
                            .findCorrespondingIntermediateEvent(process,
                                    interfaceMethod);
        }
        return correspondingRequestActivity;
    }

}
