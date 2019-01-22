/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.resolution;

import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.validation.bpmn.developer.internal.Messages;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * Resolution to change the mode of all target activity's associated parameters
 * to In.
 * 
 * @author aallway
 * @since 3.3 (15 Apr 2010)
 */
public class ChangeAssocParamsToInModeResolution extends
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
                            Messages.ChangeAssocParamsToInModeResolution_ChangePAramsToIn_menu);

            List<FormalParameter> formalParameters =
                    ProcessInterfaceUtil
                            .getAssociatedFormalParameters(activity);

            List<AssociatedParameter> activityAssociatedParameters =
                    ProcessInterfaceUtil
                            .getActivityAssociatedParameters(activity);
            if (activityAssociatedParameters != null
                    && !activityAssociatedParameters.isEmpty()) {
                /*
                 * Explicitly associated aprameters only.
                 */
                for (AssociatedParameter associatedParameter : activityAssociatedParameters) {
                    cmd.append(SetCommand.create(editingDomain,
                            associatedParameter,
                            XpdExtensionPackage.eINSTANCE
                                    .getAssociatedParameter_Mode(),
                            ModeType.IN_LITERAL));

                    FormalParameter formalParameter =
                            findParam(associatedParameter.getFormalParam(),
                                    formalParameters);
                    if (formalParameter != null) {
                        cmd.append(SetCommand.create(editingDomain,
                                formalParameter,
                                Xpdl2Package.eINSTANCE
                                        .getFormalParameter_Mode(),
                                ModeType.IN_LITERAL));
                    }
                }

            } else {
                /*
                 * Sid XPD-2087: Only use all data implicitly if implicit
                 * association has not been disabled.
                 */
                if (!ProcessInterfaceUtil
                        .isImplicitAssociationDisabled(activity)) {
                    /*
                     * All Implicitly assocaited parameters...
                     */
                    for (FormalParameter formalParameter : formalParameters) {
                        cmd.append(SetCommand.create(editingDomain,
                                formalParameter,
                                Xpdl2Package.eINSTANCE
                                        .getFormalParameter_Mode(),
                                ModeType.IN_LITERAL));
                    }
                }
            }

            if (!cmd.isEmpty()) {
                return cmd;
            }

        }
        return null;
    }

    /**
     * @param formalParamName
     * @param formalParameters
     */
    private FormalParameter findParam(String formalParamName,
            List<FormalParameter> formalParameters) {
        for (FormalParameter formalParameter : formalParameters) {
            if (formalParamName.equals(formalParameter.getName())) {
                return formalParameter;
            }
        }
        return null;
    }

}
