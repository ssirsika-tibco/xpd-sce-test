/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.bx.validation.resolutions;

import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.bx.validation.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Add formal parameter associations to the target activity and make all input
 * parameters mandatory.
 * 
 * @author aallway
 * @since 3.3 (10 May 2010)
 */
public class AddMandatoryInParamAssociationsResolution extends
        AbstractWorkingCopyResolution {

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#
     * getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     * org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     */
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        if (target instanceof Activity) {
            Activity activity = (Activity) target;

            Process process = activity.getProcess();

            List<FormalParameter> allFormalParameters =
                    ProcessInterfaceUtil.getAllFormalParameters(process);

            AssociatedParameters associatedParameters =
                    XpdExtensionFactory.eINSTANCE.createAssociatedParameters();

            for (FormalParameter formalParameter : allFormalParameters) {
                ModeType mode = formalParameter.getMode();

                AssociatedParameter associatedParameter =
                        ProcessInterfaceUtil
                                .createAssociatedParam(formalParameter);
                if (ModeType.IN_LITERAL.equals(mode)
                        || ModeType.INOUT_LITERAL.equals(mode)) {
                    /* Force input params to mandatory . */
                    associatedParameter.setMandatory(true);
                }

                associatedParameters.getAssociatedParameter()
                        .add(associatedParameter);
            }

            CompoundCommand cmd =
                    new CompoundCommand(
                            Messages.AddMandatoryInParamAssociationsResolution_menu);

            cmd.append(Xpdl2ModelUtil.getSetOtherElementCommand(editingDomain,
                    activity,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_AssociatedParameters(),
                    associatedParameters));

            return cmd;
        }

        return null;
    }

}
