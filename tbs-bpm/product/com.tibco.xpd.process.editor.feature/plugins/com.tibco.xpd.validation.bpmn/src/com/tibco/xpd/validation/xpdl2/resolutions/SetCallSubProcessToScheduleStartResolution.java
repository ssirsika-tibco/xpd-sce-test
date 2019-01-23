/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validations.bpmn.internal.Messages;
import com.tibco.xpd.xpdExtension.SubProcessStartStrategy;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Resolution to set Call Sub-process activity's start strategy to 'Schedule
 * Start'.
 * 
 * @author sajain
 * @since Jan 15, 2015
 */
public class SetCallSubProcessToScheduleStartResolution extends
        AbstractWorkingCopyResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {

        if (target instanceof Activity) {

            Activity activity = (Activity) target;

            /*
             * Filter out for Call sub-process activities.
             */
            if (activity.getImplementation() instanceof SubFlow) {

                /*
                 * Get sub-flow.
                 */
                SubFlow subFlow = (SubFlow) activity.getImplementation();

                /*
                 * Get start strategy object.
                 */
                Object startStrategyObject =
                        Xpdl2ModelUtil.getOtherAttribute(subFlow,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_StartStrategy());

                /*
                 * Start strategy must be there.
                 */
                if (startStrategyObject instanceof SubProcessStartStrategy) {

                    SubProcessStartStrategy startStrategy =
                            (SubProcessStartStrategy) startStrategyObject;

                    /*
                     * Check if start strategy is set to SCHEDULE_START.
                     */
                    if (!SubProcessStartStrategy.SCHEDULE_START
                            .equals(startStrategy)) {

                        /*
                         * If it isn't, then return command to set it to
                         * SCHEDULE_START.
                         */

                        CompoundCommand cmd =
                                new CompoundCommand(
                                        Messages.SetCallSubProcessStartStrategyResolution_Command_label);

                        cmd.append(Xpdl2ModelUtil
                                .getSetOtherAttributeCommand(editingDomain,
                                        subFlow,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_StartStrategy(),
                                        SubProcessStartStrategy.SCHEDULE_START));

                        return cmd;
                    }

                }
            }
        }

        return null;
    }
}
