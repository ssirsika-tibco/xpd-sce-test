/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validations.bpmn.internal.Messages;
import com.tibco.xpd.xpdExtension.AsyncExecutionMode;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Resolution to set Call Sub-process activity's invocation mode to
 * 'Synchronous'.
 * 
 * @author sajain
 * @since Jan 15, 2015
 */
public class SetCallSubProcessToSyncResolution extends
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
                 * Get execution mode object.
                 */
                Object execModeObject =
                        Xpdl2ModelUtil.getOtherAttribute(subFlow,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_AsyncExecutionMode());

                /*
                 * Execution mode must be there.
                 */
                if (execModeObject instanceof AsyncExecutionMode) {

                    AsyncExecutionMode executionMode =
                            (AsyncExecutionMode) execModeObject;

                    /*
                     * Sid ACE-6063 - This quick fix was coded incorrectly. The resolution was to switch sub-proc call
                     * to Synchronous, but wasn't doing so if mode was Asynch-Detached - it should do what it says on
                     * the tin!
                     */
                    CompoundCommand cmd =
                            new CompoundCommand(Messages.SetCallSubProcessInvocationModeResolution_Command_label);

                    cmd.append(SetCommand.create(editingDomain,
                            subFlow,
                            XpdExtensionPackage.eINSTANCE.getDocumentRoot_AsyncExecutionMode(),
                            SetCommand.UNSET_VALUE));

                    return cmd;
                }
            }
        }

        return null;
    }
}
