/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions.pageflow;

import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.util.PageflowUtil;
import com.tibco.xpd.processeditor.xpdl2.util.SubProcUtil;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validations.bpmn.internal.Messages;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * Resolution to create a pageflow parameter to match the user task interface
 * data.
 * 
 * @author aallway
 * @since 3.2
 */
public class CreatePageflowParamFromUserTaskDataResolution extends
        AbstractPageflowParamDataResolution {

    @Override
    protected Command createResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        if (target instanceof Activity) {
            Activity userTaskActivity = (Activity) target;

            Process pageflowProcess = getPageflowProcessFromMarker(marker);
            String paramName = getPageflowParamNameFromMarker(marker);

            if (pageflowProcess != null) {
                // Get the user task associated param for the missin pageflow
                // data (if there is any)
                AssociatedParameter userTaskAssocParam =
                        getAssociatedParameter(userTaskActivity, paramName);

                Map<String, ProcessRelevantData> allInScopeUserTaskData =
                        getInScopeActivityData(userTaskActivity);

                // And the original data
                ProcessRelevantData userTaskData =
                        allInScopeUserTaskData.get(paramName);

                if (userTaskData != null) {
                    FormalParameter newPageflowParam =
                            PageflowUtil
                                    .createPageflowParamForAssociatedData(userTaskAssocParam,
                                            userTaskData);

                    CompoundCommand cmd =
                            new CompoundCommand(
                                    Messages.CreatePageflowParamFromUSerTaskDataResolution_CreatePageflowParam_menu);
                    cmd.append(AddCommand
                            .create(editingDomain,
                                    pageflowProcess,
                                    Xpdl2Package.eINSTANCE
                                            .getFormalParametersContainer_FormalParameters(),
                                    newPageflowParam));

                    //
                    // If the pageflow has a start event with explicit
                    // associations then add the new param to them (else the
                    // interface still won't match).
                    Activity pageflowStartEvent =
                            SubProcUtil
                                    .getSubProcessStartEvent(pageflowProcess);
                    if (pageflowStartEvent != null) {
                        AssociatedParameters pageflowStartAssociations =
                                getActivityAssociatedParameters(pageflowStartEvent);
                        if (pageflowStartAssociations != null) {
                            /*
                             * Sid XPD-2087. And unset the disable Implicit
                             * associaiton flag.
                             */
                            if (pageflowStartAssociations
                                    .isDisableImplicitAssociation()) {
                                cmd.append(SetCommand
                                        .create(editingDomain,
                                                pageflowStartAssociations,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getAssociatedParameters_DisableImplicitAssociation(),
                                                Boolean.FALSE));
                            }

                            if (!pageflowStartAssociations
                                    .getAssociatedParameter().isEmpty()) {
                                AssociatedParameter newAssoc =
                                        ProcessInterfaceUtil
                                                .createAssociatedParam(newPageflowParam);

                                cmd.append(AddCommand
                                        .create(editingDomain,
                                                pageflowStartAssociations,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getAssociatedParameters_AssociatedParameter(),
                                                newAssoc));

                            }
                        }
                    }

                    return cmd;
                }

            }
        }

        return null;
    }
}
