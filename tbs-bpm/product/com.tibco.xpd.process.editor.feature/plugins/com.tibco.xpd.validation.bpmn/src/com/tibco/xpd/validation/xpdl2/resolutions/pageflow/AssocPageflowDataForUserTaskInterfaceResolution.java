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
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Resolution that will add an existing pageflow parameter to the start event
 * interface so that it matches the data associated with a referncing user task
 * 
 * @author aallway
 * @since 3.2
 */
public class AssocPageflowDataForUserTaskInterfaceResolution extends
        AbstractPageflowParamDataResolution {

    @Override
    protected Command createResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        if (target instanceof Activity) {
            Activity userTaskActivity = (Activity) target;

            Process pageflowProcess = getPageflowProcessFromMarker(marker);
            String paramName = getPageflowParamNameFromMarker(marker);
            FormalParameter pageflowParam =
                    getPageflowParameter(pageflowProcess, paramName);

            if (pageflowParam != null && pageflowProcess != null) {

                Activity pageflowStartEvent =
                        SubProcUtil.getSubProcessStartEvent(pageflowProcess);
                if (pageflowStartEvent != null) {
                    // Now we need to get the user task associated parameter and
                    // data so that we can associate the pageflowParam
                    // correctly.
                    AssociatedParameter userTaskAssocParam =
                            getAssociatedParameter(userTaskActivity, paramName);

                    Map<String, ProcessRelevantData> allInScopeUserTaskData =
                            getInScopeActivityData(userTaskActivity);

                    ProcessRelevantData userTaskData =
                            allInScopeUserTaskData.get(paramName);
                    if (userTaskData != null) {
                        AssociatedParameter pageflowAssocParam =
                                PageflowUtil
                                        .createMatchingPageflowAssocParam(userTaskAssocParam,
                                                userTaskData,
                                                paramName);

                        CompoundCommand cmd =
                                new CompoundCommand(
                                        Messages.AssocPageflowDataForUserTaskInterfaceResolution_AddDataToPageflowStartInteface_menu);

                        AssociatedParameters pageflowAssocs =
                                getOrCreateActivityAssociatedParameters(pageflowStartEvent,
                                        editingDomain,
                                        cmd);
                        cmd.append(AddCommand
                                .create(editingDomain,
                                        pageflowAssocs,
                                        XpdExtensionPackage.eINSTANCE
                                                .getAssociatedParameters_AssociatedParameter(),
                                        pageflowAssocParam));

                        /*
                         * Sid XPD-2087. And unset the disable Implicit
                         * associaiton flag.
                         */
                        if (pageflowAssocs.isDisableImplicitAssociation()) {
                            cmd.append(SetCommand
                                    .create(editingDomain,
                                            pageflowAssocs,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getAssociatedParameters_DisableImplicitAssociation(),
                                            Boolean.FALSE));
                        }

                        return cmd;
                    }
                }
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
