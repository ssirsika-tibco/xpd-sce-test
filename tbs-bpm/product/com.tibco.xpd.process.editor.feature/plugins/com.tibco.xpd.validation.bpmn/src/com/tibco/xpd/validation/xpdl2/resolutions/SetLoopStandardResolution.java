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

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validations.bpmn.internal.Messages;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Loop;
import com.tibco.xpd.xpdl2.LoopStandard;
import com.tibco.xpd.xpdl2.LoopType;
import com.tibco.xpd.xpdl2.TestTimeType;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * Add Standard Loop properties to target task activity
 * 
 * 
 * @author aallway
 * @since 3.3 (11 Nov 2009)
 */
public class SetLoopStandardResolution extends AbstractWorkingCopyResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {

        if (target instanceof Activity) {
            Activity activity = (Activity) target;

            if (TaskObjectUtil.getTaskTypeStrict(activity) != null) {

                Loop loop = Xpdl2Factory.eINSTANCE.createLoop();
                loop.setLoopType(LoopType.STANDARD_LITERAL);

                LoopStandard loopStandard =
                        Xpdl2Factory.eINSTANCE.createLoopStandard();
                loopStandard.setTestTime(TestTimeType.BEFORE_LITERAL);
                loop.setLoopStandard(loopStandard);

                CompoundCommand cmd =
                        new CompoundCommand(
                                Messages.SetLoopStandardResolution_SetTaskLoopStandard_menu);

                cmd.append(SetCommand.create(editingDomain,
                        activity,
                        Xpdl2Package.eINSTANCE.getActivity_Loop(),
                        loop));

                return cmd;
            }

        }

        return null;
    }

}
