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
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.util.PageflowUtil;
import com.tibco.xpd.processeditor.xpdl2.util.SubProcUtil;
import com.tibco.xpd.validation.bpmn.rules.PageflowUserTaskRule;
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
import com.tibco.xpd.xpdl2.commands.LateExecuteCompoundCommand;

/**
 * Quick fix to make pageflow parameter match the user task interface data named
 * in makrer by {@link PageflowUserTaskRule}
 * 
 * @author aallway
 * @since 3.2
 */
public class MatchPageflowParamToUserTaskDataResolution extends
        AbstractPageflowParamDataResolution {

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.xpdl2.resolutions.pageflow.
     * AbstractPageflowParamDataResolution
     * #createResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     * org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     */
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

                // Get the user task associated param for the missin pageflow
                // data (if there is any)
                AssociatedParameter userTaskAssocParam =
                        getAssociatedParameter(userTaskActivity, paramName);

                Map<String, ProcessRelevantData> allInScopeUserTaskData =
                        getInScopeActivityData(userTaskActivity);
                ProcessRelevantData userTaskData =
                        allInScopeUserTaskData.get(paramName);

                if (userTaskData != null) {
                    // Easiest thing to do is just create a new parameter and
                    // replace the old one with it.
                    FormalParameter newPageflowParam =
                            PageflowUtil
                                    .createPageflowParamForAssociatedData(userTaskAssocParam,
                                            userTaskData);
                    newPageflowParam.eSet(Xpdl2Package.eINSTANCE
                            .getUniqueIdElement_Id(), pageflowParam.getId());

                    CompoundCommand cmd = new LateExecuteCompoundCommand();
                    cmd
                            .setLabel(Messages.MatchPageflowParamToUserTaskDataResolution_MatchPageflowParamToUserTaskData_menu);

                    EObject dataContainer = pageflowParam.eContainer();

                    cmd.append(RemoveCommand.create(editingDomain,
                            pageflowParam));
                    cmd
                            .append(AddCommand
                                    .create(editingDomain,
                                            dataContainer,
                                            Xpdl2Package.eINSTANCE
                                                    .getFormalParametersContainer_FormalParameters(),
                                            newPageflowParam));

                    //
                    // If the pageflow start event has associations then
                    // recreate those too.
                    Activity pageflowStartEvent =
                            SubProcUtil
                                    .getSubProcessStartEvent(pageflowProcess);
                    if (pageflowStartEvent != null) {
                        AssociatedParameters pageflowStartAssociations =
                                getActivityAssociatedParameters(pageflowStartEvent);
                        if (pageflowStartAssociations != null
                                && !pageflowStartAssociations
                                        .getAssociatedParameter().isEmpty()) {
                            AssociatedParameter newAssoc =
                                    PageflowUtil
                                            .createMatchingPageflowAssocParam(userTaskAssocParam,
                                                    userTaskData,
                                                    paramName);

                            AssociatedParameter paramAssoc =
                                    getAssociatedParameter(pageflowStartEvent,
                                            paramName);
                            if (paramAssoc != null) {
                                cmd.append(RemoveCommand.create(editingDomain,
                                        paramAssoc));
                            }

                            cmd
                                    .append(AddCommand
                                            .create(editingDomain,
                                                    pageflowStartAssociations,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getAssociatedParameters_AssociatedParameter(),
                                                    newAssoc));
                        }
                    }

                    return cmd;
                }
            }
        }

        return null;
    }

}
