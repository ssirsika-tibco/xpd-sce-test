/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions.pageflow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
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
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.commands.LateExecuteCompoundCommand;

/**
 * Quick fix to make user task data match the pageflow start interface data
 * named in makrer by {@link PageflowUserTaskRule}
 * 
 * @author aallway
 * @since 3.2
 */
public class MatchUserTaskDataToPageflowParamResolution extends
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
                Map<String, ProcessRelevantData> allInScopeUserTaskData =
                        getInScopeActivityData(userTaskActivity);
                ProcessRelevantData userTaskData =
                        allInScopeUserTaskData.get(paramName);

                if (userTaskData != null) {
                    // Easiest thing to do is just create a new data field and
                    // replace the old one with it.
                    ProcessRelevantData newUserTaskData =
                            PageflowUtil
                                    .createDataForPageflowAssociatedData(pageflowParam);

                    newUserTaskData.eSet(Xpdl2Package.eINSTANCE
                            .getUniqueIdElement_Id(), userTaskData.getId());

                    CompoundCommand cmd = new LateExecuteCompoundCommand();
                    cmd
                            .setLabel(Messages.MatchUserTaskDataToPageflowParamResolution_MatchUSerTaskDataToPageflowParam_menu);

                    EObject dataContainer = userTaskData.eContainer();
                    EStructuralFeature containingFeature;

                    if (userTaskData instanceof FormalParameter) {
                        // For formal parameters we have match up the parts
                        // individually.
                        AssociatedParameter pageflowAssoc = null;
                        Activity pageflowStartEvent =
                                SubProcUtil
                                        .getSubProcessStartEvent(pageflowProcess);
                        if (pageflowStartEvent != null) {
                            pageflowAssoc =
                                    getAssociatedParameter(pageflowStartEvent,
                                            paramName);
                        }

                        FormalParameter newFormalParam =
                                (FormalParameter) EcoreUtil.copy(userTaskData);

                        newFormalParam.setIsArray(pageflowParam.isIsArray());
                        newFormalParam.setReadOnly(pageflowParam.isReadOnly());
                        newFormalParam.setDataType((DataType) EcoreUtil
                                .copy(pageflowParam.getDataType()));

                        newFormalParam.setMode(PageflowUtil
                                .getExplicitOrImplicitParamMode(pageflowAssoc,
                                        pageflowParam));
                        newFormalParam
                                .setRequired(PageflowUtil
                                        .getExplicitOrImplicitParamMandatory(pageflowAssoc,
                                                pageflowParam));
                        newUserTaskData = newFormalParam;

                        containingFeature =
                                Xpdl2Package.eINSTANCE
                                        .getFormalParametersContainer_FormalParameters();
                    } else {
                        containingFeature =
                                Xpdl2Package.eINSTANCE
                                        .getDataFieldsContainer_DataFields();

                    }

                    cmd.append(RemoveCommand
                            .create(editingDomain, userTaskData));

                    cmd.append(AddCommand.create(editingDomain,
                            dataContainer,
                            containingFeature,
                            newUserTaskData));

                    // 
                    // Add an association for it (it is the only way to match
                    // 'mode and mandatory ' for data fields!
                    // BEfore doing so check whether associations already exist
                    // - if not then we will have to create them BUT WE MUST
                    // then also add all the other (currently) implicit
                    // associations too.
                    //
                    // THIS MAY HAVE THE SIDE EFFECT OF FIXING OTHER PROBLEMS
                    // BUT WILL PREVENT A LOAD OF OTHER PROBLEMS WITH SUDDENLY
                    // GETTING EXPLICITLY UNASSOCIATED EVERY OTHER DATA FIELD.
                    List<FormalParameter> paramsToAssociate =
                            new ArrayList<FormalParameter>();
                    paramsToAssociate.add(pageflowParam);

                    AssociatedParameters userTaskAssociations =
                            getActivityAssociatedParameters(userTaskActivity);

                    boolean creatingTaskAssociations = false;

                    if (userTaskAssociations == null) {
                        creatingTaskAssociations = true;
                        userTaskAssociations =
                                getOrCreateActivityAssociatedParameters(userTaskActivity,
                                        editingDomain,
                                        cmd);
                    } else if (userTaskAssociations.getAssociatedParameter()
                            .isEmpty()) {
                        creatingTaskAssociations = true;

                    } else {
                        AssociatedParameter currentAssoc =
                                getAssociatedParameter(userTaskActivity,
                                        paramName);
                        if (currentAssoc != null) {
                            cmd.append(RemoveCommand.create(editingDomain,
                                    currentAssoc));
                        }

                    }

                    if (creatingTaskAssociations) {
                        // Get the other parameters (for which there are user
                        // task data items for and create their associated
                        // params too.
                        List<FormalParameter> pageflowStartParams =
                                SubProcUtil
                                        .getProcessParameters(pageflowProcess,
                                                null);

                        for (FormalParameter p : pageflowStartParams) {
                            // Ignore the original one we were dealing with.
                            if (!paramName.equals(p.getName())) {
                                if (allInScopeUserTaskData.containsKey(p
                                        .getName())) {
                                    // Add to list of params to create assocs
                                    // for.
                                    paramsToAssociate.add(p);
                                }
                            }
                        }
                    }

                    //
                    // Now add the association for the param (this has to be
                    // done because for instance data field doesn't have a
                    // madnatory flag - so that has to be defined in associated
                    // param.
                    for (FormalParameter paramToAssociate : paramsToAssociate) {
                        // Get pageflow start event assoc param if there.
                        AssociatedParameter pageflowAssocParam =
                                getPageflowAssociatedParam(pageflowProcess,
                                        paramToAssociate.getName());

                        AssociatedParameter userTaskAssocParam =
                                PageflowUtil
                                        .createMatchingUserTaskAssocParam(pageflowAssocParam,
                                                paramToAssociate,
                                                paramToAssociate.getName());

                        cmd
                                .append(AddCommand
                                        .create(editingDomain,
                                                userTaskAssociations,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getAssociatedParameters_AssociatedParameter(),
                                                userTaskAssocParam));
                    }
                    return cmd;
                }
            }
        }

        return null;
    }

}
