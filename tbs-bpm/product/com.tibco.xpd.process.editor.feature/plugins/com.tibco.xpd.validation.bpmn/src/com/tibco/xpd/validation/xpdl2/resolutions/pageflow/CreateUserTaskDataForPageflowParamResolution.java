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
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.commands.LateExecuteCompoundCommand;

/**
 * Resolution to create a data field to match the pageflow parameter.
 * 
 * @author aallway
 * @since 3.2
 */
public class CreateUserTaskDataForPageflowParamResolution extends
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

                CompoundCommand cmd = new LateExecuteCompoundCommand();
                cmd.setLabel(Messages.CreateUserTaskDataForPageflowParamResolution_CreateDataToMatchPageflowParam_menu);

                // Create the data field to match pageflow param.
                DataField newData =
                        PageflowUtil
                                .createDataForPageflowAssociatedData(pageflowParam);

                // Add it to user task's process.
                cmd.append(AddCommand.create(editingDomain, userTaskActivity
                        .getProcess(), Xpdl2Package.eINSTANCE
                        .getDataFieldsContainer_DataFields(), newData));

                //
                // Add an association for it.
                // BEfore doing so check whether associations already exist - if
                // not then we will have to create them BUT WE MUST then also
                // add all the other (currently) implicit associations too.
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
                } else {
                    /*
                     * Sid XPD-2087. If the associatedParameters element already
                     * exists then unset the disable implicit
                     */
                    if (userTaskAssociations.isDisableImplicitAssociation()) {
                        cmd.append(SetCommand
                                .create(editingDomain,
                                        userTaskAssociations,
                                        XpdExtensionPackage.eINSTANCE
                                                .getAssociatedParameters_DisableImplicitAssociation(),
                                        Boolean.FALSE));
                    }

                    if (userTaskAssociations.getAssociatedParameter().isEmpty()) {
                        creatingTaskAssociations = true;
                    }
                }

                if (creatingTaskAssociations) {
                    // Get the other parameters (for which there are user task
                    // data items for and create their associated params too.
                    List<FormalParameter> pageflowStartParams =
                            SubProcUtil.getProcessParameters(pageflowProcess,
                                    null);

                    Map<String, ProcessRelevantData> allInScopeUserTaskData =
                            getInScopeActivityData(userTaskActivity);

                    for (FormalParameter p : pageflowStartParams) {
                        // Ignore the original one we were dealing with.
                        if (!paramName.equals(p.getName())) {
                            if (allInScopeUserTaskData.containsKey(p.getName())) {
                                // Add to list of params to create assocs for.
                                paramsToAssociate.add(p);
                            }
                        }
                    }
                }

                //
                // Now add the association for the param (this has to be done
                // because for instance data field doesn't have a madnatory flag
                // - so that has to be defined in associated param.
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

                    cmd.append(AddCommand
                            .create(editingDomain,
                                    userTaskAssociations,
                                    XpdExtensionPackage.eINSTANCE
                                            .getAssociatedParameters_AssociatedParameter(),
                                    userTaskAssocParam));
                }

                return cmd;
            }
        }
        return null;
    }

}
