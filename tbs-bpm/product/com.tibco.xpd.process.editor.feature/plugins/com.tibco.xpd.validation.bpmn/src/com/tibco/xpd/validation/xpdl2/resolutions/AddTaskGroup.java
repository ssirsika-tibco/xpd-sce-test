/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import java.util.Properties;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.StructuredSelection;

import com.tibco.xpd.processeditor.xpdl2.util.RetainFamiliarCommandActions;
import com.tibco.xpd.processeditor.xpdl2.util.SeparationOfDutiesCommandActions;
import com.tibco.xpd.validation.bpmn.rules.RetainFamiliarRule;
import com.tibco.xpd.validation.bpmn.rules.SeparationOfDutiesRule;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validation.rules.ValidationUtil;
import com.tibco.xpd.xpdExtension.RetainFamiliarActivities;
import com.tibco.xpd.xpdExtension.SeparationOfDutiesActivities;

/**
 * @author bharge
 * 
 */
public class AddTaskGroup extends AbstractWorkingCopyResolution {
    /** Add Task Group command name. */
    private static final String COMMAND = "addTaskGroupCommand"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     * 
     * @param editingDomain
     * @param target
     * @param marker
     * @return
     * @throws ResolutionException
     */
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        CompoundCommand cCmd =
                new CompoundCommand(ResolutionMessages.getText(COMMAND));
        /**
         * reading the additional info added in the rule class
         */
        Properties props = ValidationUtil.getAdditionalInfo(marker);

        if (target instanceof SeparationOfDutiesActivities) {
            SeparationOfDutiesActivities sepOfDutiesActivities =
                    (SeparationOfDutiesActivities) target;

            if (props != null) {
                /**
                 * getting the value of the sep of duties activities group id
                 */
                String groupId =
                        props.getProperty(SeparationOfDutiesRule.GROUP_ID);
                if (null != groupId) {
                    /**
                     * if the group id added in the additional info matches,
                     * then add tasks to that group
                     */
                    if (sepOfDutiesActivities.getId().equals(groupId)) {
                        StructuredSelection selection =
                                new StructuredSelection(sepOfDutiesActivities);
                        SeparationOfDutiesCommandActions commandActions =
                                new SeparationOfDutiesCommandActions();
                        commandActions.addTasksToGroup(selection, cCmd);
                    }
                }
            }
        } else if (target instanceof RetainFamiliarActivities) {
            RetainFamiliarActivities retainFamiliarActivities =
                    (RetainFamiliarActivities) target;
            if (props != null) {
                // getting the value of the sep of duties activities group id
                String groupId = props.getProperty(RetainFamiliarRule.GROUP_ID);
                if (null != groupId) {
                    /**
                     * if the group id added in the additional info matches,
                     * then add tasks to that group
                     */
                    if (retainFamiliarActivities.getId().equals(groupId)) {
                        StructuredSelection selection =
                                new StructuredSelection(
                                        retainFamiliarActivities);
                        RetainFamiliarCommandActions commandActions =
                                new RetainFamiliarCommandActions();
                        commandActions.addTasksToGroup(selection, cCmd);
                    }
                }
            }
        }
        return cCmd;
    }
}
