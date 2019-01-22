/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions.pageflow;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.util.PageflowUtil;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validations.bpmn.internal.Messages;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Resolution to add user task interface associated parameter to match a given
 * pageflow parameter.
 * 
 * @author aallway
 * @since 3.2
 */
public class AssocUserTaskDataForPageflowParamResolution extends
        AbstractUserTaskPageflowParamSynchResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        if (target instanceof Activity) {
            Activity userTaskActivity = (Activity) target;

            Process pageflowProcess = getPageflowProcessFromMarker(marker);
            String paramName = getPageflowParamNameFromMarker(marker);
            FormalParameter pageflowParam =
                    getPageflowParameter(pageflowProcess, paramName);

            if (pageflowParam != null && pageflowProcess != null) {
                // Get pageflow start event assoc param if there.
                AssociatedParameter pageflowAssocParam =
                        getPageflowAssociatedParam(pageflowProcess, paramName);

                // Create user task assoc param to match.
                AssociatedParameter userTaskAssocParam =
                        PageflowUtil
                                .createMatchingUserTaskAssocParam(pageflowAssocParam,
                                        pageflowParam,
                                        paramName);

                // Create command to add it to user task.
                CompoundCommand cmd =
                        new CompoundCommand(
                                Messages.AssocUserTaskDataForPageflowParamResolution_AddDataToUserTaskInteface_menu);

                AssociatedParameters userTaskAssociations =
                        getOrCreateActivityAssociatedParameters(userTaskActivity,
                                editingDomain,
                                cmd);

                cmd.append(AddCommand.create(editingDomain,
                        userTaskAssociations,
                        XpdExtensionPackage.eINSTANCE
                                .getAssociatedParameters_AssociatedParameter(),
                        userTaskAssocParam));

                /*
                 * Sid XPD-2087. And unset the disable Implicit associaiton
                 * flag.
                 */
                if (userTaskAssociations.isDisableImplicitAssociation()) {
                    cmd.append(SetCommand
                            .create(editingDomain,
                                    userTaskAssociations,
                                    XpdExtensionPackage.eINSTANCE
                                            .getAssociatedParameters_DisableImplicitAssociation(),
                                    Boolean.FALSE));
                }
                return cmd;
            }

        }
        return null;
    }

    @Override
    protected String getResolutionLabel(String propertiesLabel, IMarker marker) {
        String dataName = "???"; //$NON-NLS-1$

        FormalParameter param = getPageflowParameterFromMarker(marker);
        if (param != null) {
            dataName = Xpdl2ModelUtil.getDisplayNameOrName(param);
        }

        return String.format(propertiesLabel, dataName);
    }

    @Override
    protected String getResolutionDescription(String propertiesDescription,
            IMarker marker) {
        String dataName = "???"; //$NON-NLS-1$

        FormalParameter param = getPageflowParameterFromMarker(marker);
        if (param != null) {
            dataName = Xpdl2ModelUtil.getDisplayNameOrName(param);
        }

        return String.format(propertiesDescription, dataName);
    }

}
