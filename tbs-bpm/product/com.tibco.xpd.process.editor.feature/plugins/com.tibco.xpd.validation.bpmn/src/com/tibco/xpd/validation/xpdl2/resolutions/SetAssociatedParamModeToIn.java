/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import java.util.List;
import java.util.Properties;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.validation.bpmn.rules.AbstractUserManualTaskAssociationRule;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validation.rules.ValidationUtil;
import com.tibco.xpd.validations.bpmn.internal.Messages;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * Resolution to set the mode of the associated param to IN.
 * 
 * @author aallway
 * @since 3.2
 */
public class SetAssociatedParamModeToIn extends AbstractWorkingCopyResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        if (target instanceof Activity) {
            Activity activity = (Activity) target;

            String paramName = getAssociatedParamName(marker);
            if (paramName != null && paramName.length() > 0) {
                AssociatedParameter assocParam =
                        locateAssocParam(activity, paramName);

                if (assocParam != null) {
                    CompoundCommand cmd =
                            new CompoundCommand(
                                    Messages.SetAssociatedParamModeToIn_SetAssocParamMode_menu);

                    cmd.append(SetCommand.create(editingDomain,
                            assocParam,
                            XpdExtensionPackage.eINSTANCE
                                    .getAssociatedParameter_Mode(),
                            ModeType.IN_LITERAL));
                    return cmd;
                }

            }
        } else if (target instanceof FormalParameter) {
            FormalParameter parameter = (FormalParameter) target;
            CompoundCommand cmd =
                    new CompoundCommand(
                            Messages.SetParamModeToIn_SetParamMode_menu);
            cmd.append(SetCommand.create(editingDomain,
                    parameter,
                    Xpdl2Package.eINSTANCE.getFormalParameter_Mode(),
                    ModeType.IN_LITERAL));
            return cmd;
        }
        return null;
    }

    private AssociatedParameter locateAssocParam(Activity activity,
            String paramName) {
        List<AssociatedParameter> assocParams =
                ProcessInterfaceUtil.getActivityAssociatedParameters(activity);
        for (AssociatedParameter assocParam : assocParams) {
            if (paramName.equals(assocParam.getFormalParam())) {
                return assocParam;
            }
        }

        return null;
    }

    private String getAssociatedParamName(IMarker marker) {
        Properties addInfo = ValidationUtil.getAdditionalInfo(marker);
        if (addInfo != null) {
            return addInfo
                    .getProperty(AbstractUserManualTaskAssociationRule.EXTRA_INFO_PARAMNAME);
        }

        return null;
    }

}
