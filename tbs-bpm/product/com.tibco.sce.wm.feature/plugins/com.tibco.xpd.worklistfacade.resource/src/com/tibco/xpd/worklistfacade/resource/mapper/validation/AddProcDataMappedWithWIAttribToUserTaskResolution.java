/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.worklistfacade.resource.mapper.validation;

import java.util.Collection;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.worklistfacade.resource.WorkListFacadeResourcePlugin;
import com.tibco.xpd.worklistfacade.resource.mapper.WorkListFacadeMapperUtil;
import com.tibco.xpd.worklistfacade.resource.util.Messages;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Resolution to Associate/add ONLY specific Process Data which is mapped to
 * Physical Work Item Attribute with User Task.
 * 
 * @author aprasad
 * @since 06-Jan-2014
 */
public class AddProcDataMappedWithWIAttribToUserTaskResolution extends
        AbstractWorkingCopyResolution {

    /**
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     * 
     * @param editingDomain
     * @param target
     * @param marker
     * @return {@link Command} to associate/add Process Data in the Marker
     *         ,which is mapped with Physical Work item Attribute, with the User
     *         Task.
     * @throws ResolutionException
     * @throws CoreException
     */
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) {

        if (target instanceof Activity) {
            CompoundCommand cmd = new CompoundCommand();
            String procData = null;

            // get the Process Data name from the Marker
            try {
                String message = (String) marker.getAttribute("message"); //$NON-NLS-1$
                int startIndexOfData = message.indexOf('\'');

                procData =
                        message.substring(startIndexOfData + 1,
                                message.indexOf('\'', startIndexOfData + 1));

            } catch (CoreException e) {
                // Log appropriate message
                WorkListFacadeResourcePlugin
                        .getDefault()
                        .logError(Messages.AddProcDataMappedWithWIAttribToUserTaskResolution_Error_Extracting_ProcessDataFrom_Marker,
                                e);
            }
            // Handle Process Data when found
            if (procData != null) {
                Activity activity = (Activity) target;
                TaskType taskType = TaskObjectUtil.getTaskTypeStrict(activity);

                // ONLY interested in User Tasks
                if (TaskType.USER_LITERAL.equals(taskType)) {

                    // All Process Relevant Data which is mapped to a Physical
                    // Work Item Attribute
                    Collection<ProcessRelevantData> allProcessDataMappedToWIAttrib =
                            WorkListFacadeMapperUtil
                                    .getAllProcessDataMappedToPhysicalAttribute(Xpdl2ModelUtil
                                            .getProcess(activity));
                    // find Process Data for which marker is exists
                    ProcessRelevantData procRelData =
                            getProcessData(procData,
                                    allProcessDataMappedToWIAttrib);

                    // Process Data found
                    if (procRelData != null) {

                        // Associated Parameters container
                        EObject otherElement =
                                activity.getOtherElement(XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_AssociatedParameters()
                                        .getName());

                        AssociatedParameters assoParams = null;

                        if (otherElement instanceof AssociatedParameters) {
                            assoParams = (AssociatedParameters) otherElement;
                        }
                        // append create for Container if does not exist
                        if (otherElement == null) {
                            assoParams =
                                    XpdExtensionFactory.eINSTANCE
                                            .createAssociatedParameters();
                            cmd.append(AddCommand
                                    .create(editingDomain,
                                            activity,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_AssociatedParameters(),
                                            assoParams));
                        }

                        // return commands to associate the required process
                        // data with the User Task.

                        AssociatedParameter newAssoc =
                                XpdExtensionFactory.eINSTANCE
                                        .createAssociatedParameter();
                        newAssoc.setFormalParam(procRelData.getName());
                        newAssoc.setDescription(procRelData.getDescription());

                        // Set values for FormalParameter
                        if (procRelData instanceof FormalParameter) {
                            // Set MODE
                            newAssoc.setMode((((FormalParameter) procRelData)
                                    .getMode()));
                            // Set mandatory
                            newAssoc.setMandatory(((FormalParameter) procRelData)
                                    .isRequired());
                        }

                        cmd.append(AddCommand
                                .create(editingDomain,
                                        assoParams,
                                        XpdExtensionPackage.eINSTANCE
                                                .getAssociatedParameters_AssociatedParameter()
                                                .getName(),
                                        newAssoc));
                        return cmd;
                    }
                }

            }
        }

        return null;
    }

    /**
     * Searches for the {@link ProcessRelevantData} with given name in the
     * collection of {@link ProcessRelevantData} and returns if found, null
     * otherwise.
     * 
     * @param procData
     * @return {@link ProcessRelevantData}, with the given name , if found null
     *         otherwise
     */
    private ProcessRelevantData getProcessData(String procData,
            Collection<ProcessRelevantData> allProcessData) {

        for (ProcessRelevantData prd : allProcessData) {
            // XPD-5761: Quick fix and markings for facade warnings seem to
            // behave a little strangely. Return correct Process relevant data.
            if (procData.equals(prd.getName())) {
                return prd;
            }

        }
        return null;
    }
}
