package com.tibco.bx.validation.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.bx.validation.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.implementer.script.AbstractActivityMessageAdapter;
import com.tibco.xpd.implementer.script.EventMessageAdapter;
import com.tibco.xpd.implementer.script.TaskReceiveMessageAdapter;
import com.tibco.xpd.implementer.script.TaskSendMessageAdapter;
import com.tibco.xpd.implementer.script.TaskServiceMessageAdapter;
import com.tibco.xpd.implementer.script.Xpdl2WsdlUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.util.WsdlIndexerUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.wsdl.WsdlServiceKey;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;

public class GenerateWebServiceParticipantResolution extends
        AbstractWorkingCopyResolution implements IResolution {

    public GenerateWebServiceParticipantResolution() {
        // TODO Auto-generated constructor stub
    }

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

        Activity activity =
                (target instanceof Activity) ? (Activity) target : null;
        WsdlServiceKey wsdlServiceKey =
                ProcessUIUtil.getWsdlServiceKey(activity);

        IProject project = WorkingCopyUtil.getProjectFor(activity);
        String transportUri =
                WsdlIndexerUtil.getTransportUri(project,
                        wsdlServiceKey,
                        true,
                        true);
        if (null == transportUri) {
            transportUri = Xpdl2WsdlUtil.SERVICE_VIRTUALIZATION_URL;
        }
        wsdlServiceKey.setTransportURI(transportUri);

        Process process = activity.getProcess();
        CompoundCommand compoundCmd =
                new CompoundCommand(Messages.GenerateAndAssignWSParticipant);

        if (isWebServiceCapableActivity(activity)) {

            // Create and assign an endpoint participant if possible.
            getAdapterInstance(activity)
                    .autoAssignEndpointParticipantCommand(editingDomain,
                            process,
                            activity,
                            wsdlServiceKey,
                            compoundCmd);
        }

        return compoundCmd;
    }

    /**
     * @param activity
     * @return
     */
    private AbstractActivityMessageAdapter getAdapterInstance(Activity activity) {

        AbstractActivityMessageAdapter adapter = null;

        Task task =
                (activity.getImplementation() instanceof Task) ? (Task) activity
                        .getImplementation() : null;
        if (task != null) {
            if (task.getTaskReceive() != null)
                adapter = new TaskReceiveMessageAdapter();
            else if (task.getTaskSend() != null)
                adapter = new TaskSendMessageAdapter();
            else if (task.getTaskService() != null)
                adapter = new TaskServiceMessageAdapter();
        } else if (activity.getEvent() != null)
            adapter = new EventMessageAdapter();

        return adapter;
    }

    /**
     * Is the activity capable of hosting a web service operation?
     * 
     * @param activity
     */
    private boolean isWebServiceCapableActivity(Activity activity) {

        if (activity.getEvent() != null) {
            return true;
        } else if (activity.getRoute() == null
                && activity.getBlockActivity() == null) {
            TaskType taskType = TaskObjectUtil.getTaskType(activity);

            if (!TaskType.EMBEDDED_SUBPROCESS_LITERAL.equals(taskType)) {
                return true;
            }
        }
        return false;
    }

}
