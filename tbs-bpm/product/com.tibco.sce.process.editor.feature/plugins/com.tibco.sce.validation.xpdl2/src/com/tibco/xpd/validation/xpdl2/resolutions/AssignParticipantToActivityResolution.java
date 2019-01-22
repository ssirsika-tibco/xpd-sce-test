/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.xpdl2.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.analyst.resources.xpdl2.pickers.DataFilterPicker;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.ParticipantsPicker;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.DataFilterPicker.DataPickerType;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskReceive;
import com.tibco.xpd.xpdl2.TaskSend;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author nwilson
 */
public class AssignParticipantToActivityResolution extends
        AbstractWorkingCopyResolution {

    /**
     * @param ed
     *            The editing domain.
     * @param target
     *            The target object.
     * @param marker
     *            The problem marker.
     * @return The command to make the change.
     * @throws ResolutionException
     *             If there was a problem creating the command.
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution
     *      #getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     */
    @Override
    protected Command getResolutionCommand(EditingDomain ed, EObject target,
            IMarker marker) throws ResolutionException {
        // Command command = null;
        if (target instanceof Activity) {
            DataFilterPicker picker =
                    new DataFilterPicker(Display.getCurrent().getActiveShell(),
                            DataPickerType.PARTICIPANTS, false);
            Activity activity = (Activity) target;
            picker.setScope(activity);

            WebServiceOperation wso = null;
            if (activity.getImplementation() instanceof Task) {
                Task task = (Task) activity.getImplementation();
                if (task.getTaskService() != null) {
                    TaskService taskService = task.getTaskService();
                    wso = taskService.getWebServiceOperation();
                } else if (task.getTaskSend() != null) {
                    TaskSend taskSend = task.getTaskSend();
                    wso = taskSend.getWebServiceOperation();
                } else if (task.getTaskReceive() != null) {
                    TaskReceive taskRecieve = task.getTaskReceive();
                    wso = taskRecieve.getWebServiceOperation();
                }
            }
            if (picker.open() == ParticipantsPicker.OK) {
                Object result = picker.getFirstResult();
                if (result instanceof Participant
                        || result instanceof ProcessRelevantData) {
                    // UniqueIdElement element = (UniqueIdElement) result;
                    // Performer performer =
                    // Xpdl2Factory.eINSTANCE.createPerformer();
                    // performer.setValue(element.getId());
                    // command =
                    // SetCommand.create(ed,
                    // activity,
                    // Xpdl2Package.eINSTANCE
                    // .getActivity_Performer(),
                    // performer);

                    NamedElement particOrPerf = (NamedElement) result;

                    CompoundCommand cmd = new CompoundCommand();
                    cmd.append(TaskObjectUtil.getSetPerformersCommand(ed,
                            activity,
                            new NamedElement[] { particOrPerf }));

                    // if this process is a webservice type we need to set the
                    // alias as this participant
                    if (wso != null) {
                        Xpdl2ModelUtil
                                .setEndpointFromDataPickerSelection(result, wso);
                    }
                    return cmd;
                }
            }
        }
        return null;
    }

}
