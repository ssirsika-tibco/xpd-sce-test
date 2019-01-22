/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
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
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Loop;
import com.tibco.xpd.xpdl2.LoopMultiInstance;
import com.tibco.xpd.xpdl2.LoopType;
import com.tibco.xpd.xpdl2.MIOrderingType;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * SetLoopMultiInstanceSequentialResolution
 * 
 * 
 * @author aallway
 * @since 3.3 (11 Nov 2009)
 */
public class SetLoopMultiInstanceSequentialResolution extends
        AbstractWorkingCopyResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {

        if (target instanceof Activity) {
            Activity activity = (Activity) target;

            Loop loop = activity.getLoop();
            if (loop != null) {
                if (LoopType.MULTI_INSTANCE_LITERAL.equals(loop.getLoopType())) {
                    LoopMultiInstance loopMultiInstance =
                            loop.getLoopMultiInstance();
                    if (loopMultiInstance != null) {
                        CompoundCommand cmd =
                                new CompoundCommand(Messages.SetLoopMultiInstanceSequentialResolution_SetMultiInstSequential_menu);
                        cmd.append(SetCommand.create(editingDomain,
                                loopMultiInstance,
                                Xpdl2Package.eINSTANCE
                                        .getLoopMultiInstance_MIOrdering(),
                                MIOrderingType.SEQUENTIAL_LITERAL));
                        return cmd;
                    }
                }
            }
        }

        return null;
    }

}
