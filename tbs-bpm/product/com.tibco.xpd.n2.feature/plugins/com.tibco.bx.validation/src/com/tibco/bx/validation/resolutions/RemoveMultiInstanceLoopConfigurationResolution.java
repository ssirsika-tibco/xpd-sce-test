/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.bx.validation.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.bx.validation.internal.Messages;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Loop;
import com.tibco.xpd.xpdl2.LoopType;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * Resolution to remove multi instance loop configuration.
 * 
 * @author sajain
 * @since Oct 7, 2014
 */
public class RemoveMultiInstanceLoopConfigurationResolution extends
        AbstractWorkingCopyResolution {

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

            /*
             * Get activity loop.
             */
            Loop loop = activity.getLoop();

            if (loop != null) {

                /*
                 * Check if loop type is multi-instance.
                 */
                if (LoopType.MULTI_INSTANCE_LITERAL.equals(loop.getLoopType())) {

                    CompoundCommand cmd =
                            new CompoundCommand(
                                    Messages.RemoveMultiInstanceLoopConfigurationResolution_Command_Label1);

                    /*
                     * Append command to remove loop config if it is of
                     * multi-instance type.
                     */
                    cmd.append(SetCommand.create(editingDomain,
                            activity,
                            Xpdl2Package.eINSTANCE.getActivity_Loop(),
                            SetCommand.UNSET_VALUE));

                    return cmd;

                }
            }

        }

        return null;
    }
}
