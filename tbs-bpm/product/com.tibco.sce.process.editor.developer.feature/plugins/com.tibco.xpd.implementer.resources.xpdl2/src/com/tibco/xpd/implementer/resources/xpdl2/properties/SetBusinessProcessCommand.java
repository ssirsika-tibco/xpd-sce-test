package com.tibco.xpd.implementer.resources.xpdl2.properties;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.implementer.script.ActivityMessageProvider;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.wsdl.WsdlServiceKey;
import com.tibco.xpd.xpdExtension.BusinessProcess;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.commands.LateExecuteCompoundCommand;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * There was a problem with appending command to the compound command. This
 * approach of appendAndExecute seems to add both the extended
 * attributes(PorttypeOperation and BusinessProcess) to TaskSend
 * 
 * 
 * 
 * @author bharge
 * @since 3.3 (12 Mar 2010)
 */
public class SetBusinessProcessCommand extends CompoundCommand {

    private ActivityMessageProvider provider;

    private Process proc;

    private Activity act;

    private Activity requestActivity;

    private String wsdlUrl;

    private boolean isRemote;

    private WsdlServiceKey key;

    private EditingDomain ed;

    public SetBusinessProcessCommand(EditingDomain ed,
            ActivityMessageProvider provider, Activity requestActivity,
            Activity act, String wsdlUrl, boolean isRemote, WsdlServiceKey key) {
        this.ed = ed;
        this.provider = provider;
        this.proc = requestActivity.getProcess();
        this.requestActivity = requestActivity;
        this.act = act;
        this.wsdlUrl = wsdlUrl;
        this.isRemote = isRemote;
        this.key = key;

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.common.command.AbstractCommand#canExecute()
     */
    @Override
    public boolean canExecute() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.common.command.CompoundCommand#execute()
     */
    @Override
    public void execute() {
        this.appendAndExecute(provider.getAssignWebServiceCommand(ed,
                proc,
                act,
                wsdlUrl,
                isRemote,
                key));

        this.appendAndExecute(getSetBusinessProcessCommand(ed,
                act,
                requestActivity));
    }

    private Command getSetBusinessProcessCommand(EditingDomain ed,
            Activity activity, EObject requestActivity) {
        LateExecuteCompoundCommand cmd = new LateExecuteCompoundCommand();
        cmd.setLabel(Messages.Xpdl2TaskAdapter_SetMainflow);
        if (TaskType.SEND_LITERAL.equals(TaskObjectUtil
                .getTaskTypeStrict(activity))) {

            Task task = (Task) activity.getImplementation();
            BusinessProcess sendTaskBusinessProc =
                    XpdExtensionFactory.eINSTANCE.createBusinessProcess();

            if (requestActivity != null && requestActivity instanceof Activity) {

                Activity reqAct = (Activity) requestActivity;

                sendTaskBusinessProc.setActivityId(reqAct.getId());

                String procId = reqAct.getProcess().getId();
                if (procId == null || reqAct.getId() == null) {
                    return null;
                }

                Object otherElement =
                        Xpdl2ModelUtil.getOtherElement(task.getTaskSend(),
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_BusinessProcess());
                if (otherElement instanceof BusinessProcess) {
                    sendTaskBusinessProc = (BusinessProcess) otherElement;
                    cmd.append(SetCommand.create(ed,
                            sendTaskBusinessProc,
                            XpdExtensionPackage.eINSTANCE
                                    .getBusinessProcess_ProcessId(),
                            reqAct.getProcess().getId()));

                    cmd.append(SetCommand.create(ed,
                            sendTaskBusinessProc,
                            XpdExtensionPackage.eINSTANCE
                                    .getBusinessProcess_ActivityId(),
                            reqAct.getId()));

                    return cmd;
                }

                WorkingCopy externalWc =
                        WorkingCopyUtil.getWorkingCopyFor(reqAct.getProcess());
                Xpdl2WorkingCopyImpl wc =
                        (Xpdl2WorkingCopyImpl) WorkingCopyUtil
                                .getWorkingCopyFor(activity);
                if (wc == externalWc) {
                    sendTaskBusinessProc.setProcessId(procId);
                } else {
                    String refId =
                            wc.appendCreateReferenceCommand(externalWc, cmd);
                    sendTaskBusinessProc.setProcessId(procId);
                    sendTaskBusinessProc.setPackageRefId(refId);
                }
            } else {
                sendTaskBusinessProc.setProcessId("none"); //$NON-NLS-1$
            }
            cmd.append(Xpdl2ModelUtil.getAddOtherElementCommand(ed, task
                    .getTaskSend(), XpdExtensionPackage.eINSTANCE
                    .getDocumentRoot_BusinessProcess(), sendTaskBusinessProc));

        }
        return cmd;
    }
}
