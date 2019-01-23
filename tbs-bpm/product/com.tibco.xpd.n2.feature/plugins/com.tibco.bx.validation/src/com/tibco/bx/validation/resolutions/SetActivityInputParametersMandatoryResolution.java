/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.bx.validation.resolutions;

import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.bx.validation.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * Resolution to set the target activity's input parameters (implicitly or
 * explicitly associated) to mandatory.
 * 
 * @author aallway
 * @since 3.3 (10 May 2010)
 */
public class SetActivityInputParametersMandatoryResolution extends
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

            CompoundCommand cmd =
                    new CompoundCommand(
                            Messages.SetActivityInputParametersMandatoryResolution_menu);

            List<AssociatedParameter> associatedParameters =
                    ProcessInterfaceUtil
                            .getActivityAssociatedParameters(activity);
            if (associatedParameters != null && !associatedParameters.isEmpty()) {
                /*
                 * Explicitly associated parameters.
                 */
                for (AssociatedParameter associatedParameter : associatedParameters) {
                    ModeType mode = associatedParameter.getMode();

                    if (ModeType.IN_LITERAL.equals(mode)
                            || ModeType.INOUT_LITERAL.equals(mode)) {
                        /* Force input params to mandatory . */
                        cmd.append(SetCommand.create(editingDomain,
                                associatedParameter,
                                XpdExtensionPackage.eINSTANCE
                                        .getAssociatedParameter_Mandatory(),
                                Boolean.TRUE));
                    }
                }

            } else {
                /*
                 * Sid XPD-2087: Only return all data implicitly if implicit
                 * association has not been disabled.
                 */
                if (!ProcessInterfaceUtil
                        .isImplicitAssociationDisabled(activity)) {
                    /*
                     * Implicitly associated parameters.
                     */
                    List<FormalParameter> allFormalParameters =
                            ProcessInterfaceUtil
                                    .getAllFormalParameters(activity
                                            .getProcess());

                    for (FormalParameter formalParameter : allFormalParameters) {
                        ModeType mode = formalParameter.getMode();

                        if (ModeType.IN_LITERAL.equals(mode)
                                || ModeType.INOUT_LITERAL.equals(mode)) {
                            /* Force input params to mandatory . */
                            cmd.append(SetCommand.create(editingDomain,
                                    formalParameter,
                                    Xpdl2Package.eINSTANCE
                                            .getFormalParameter_Required(),
                                    Boolean.TRUE));
                        }
                    }
                }
            }

            return cmd;
        }

        return null;
    }

}
