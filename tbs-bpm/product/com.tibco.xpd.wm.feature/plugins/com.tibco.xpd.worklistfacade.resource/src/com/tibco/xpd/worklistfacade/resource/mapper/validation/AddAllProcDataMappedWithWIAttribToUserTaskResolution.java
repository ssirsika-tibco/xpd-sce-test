/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.worklistfacade.resource.mapper.validation;

import java.util.Collection;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.worklistfacade.resource.mapper.WorkListFacadeMapperUtil;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Resolution to Associate/add all Process Data which are mapped to Physical
 * Work Item Attribute with User Task.
 * 
 * @author aprasad
 * @since 06-Jan-2014
 */
public class AddAllProcDataMappedWithWIAttribToUserTaskResolution extends
        AbstractWorkingCopyResolution {

    /**
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     * 
     * @param editingDomain
     * @param target
     * @param marker
     * @return {@link Command} to associate/add all Process Data which is mapped
     *         with Physical Work item Attribute, with the User Task.
     * @throws ResolutionException
     */
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {

        if (target instanceof Activity) {
            CompoundCommand cmd = new CompoundCommand();

            Activity activity = (Activity) target;
            TaskType taskType = TaskObjectUtil.getTaskTypeStrict(activity);

            // ONLY interested in User Tasks
            if (TaskType.USER_LITERAL.equals(taskType)) {

                // All Process Relevant Data which is mapped to a Physical Work
                // Item Attribute
                Collection<ProcessRelevantData> allProcessDataMappedToWIAttrib =
                        WorkListFacadeMapperUtil
                                .getAllProcessDataMappedToPhysicalAttribute(Xpdl2ModelUtil
                                        .getProcess(activity));
                // All Process Data mapped to Physical Work Item Attribute and
                // not associated with the User task.
                Collection<ProcessRelevantData> allUnAssociatedProcessDataForActivity =
                        WorkListFacadeMapperUtil
                                .getAllUnAssociatedProcessDataForActivity(activity,
                                        allProcessDataMappedToWIAttrib);
                // Associated Parameters container
                EObject otherElement =
                        activity.getOtherElement(XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_AssociatedParameters()
                                .getName());

                AssociatedParameters assoParams = null;

                if (otherElement instanceof AssociatedParameters) {
                    assoParams = (AssociatedParameters) otherElement;
                }
                // Append Create Command for Container if it does not already
                // exist
                if (otherElement == null) {
                    assoParams =
                            XpdExtensionFactory.eINSTANCE
                                    .createAssociatedParameters();
                    cmd.append(AddCommand.create(editingDomain,
                            activity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_AssociatedParameters(),
                            assoParams));
                }

                // collect commands to associate the required process data with
                // the User Task.
                for (ProcessRelevantData processRelevantData : allUnAssociatedProcessDataForActivity) {

                    AssociatedParameter newAssoc =
                            XpdExtensionFactory.eINSTANCE
                                    .createAssociatedParameter();
                    newAssoc.setFormalParam(processRelevantData.getName());

                    cmd.append(AddCommand
                            .create(editingDomain,
                                    assoParams,
                                    XpdExtensionPackage.eINSTANCE
                                            .getAssociatedParameters_AssociatedParameter()
                                            .getName(),
                                    newAssoc));

                }
                return cmd;

            }

        }

        return null;
    }
}
