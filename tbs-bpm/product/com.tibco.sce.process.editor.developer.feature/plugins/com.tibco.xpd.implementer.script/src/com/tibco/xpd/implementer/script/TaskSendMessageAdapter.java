/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.script;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.ElementsFactory;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.OtherAttributesContainer;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskSend;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Service message adapter for send task.
 * 
 * @author aallway
 * 
 */
public class TaskSendMessageAdapter extends AbstractTaskActivityMessageAdapter {

    @Override
    protected OtherAttributesContainer getExtImplementationTypeContainer(
            Activity act) {
        TaskSend taskSend = getTaskSend(act);
        return taskSend;
    }

    @Override
    protected EAttribute getImplementationTypeFeature() {
        return Xpdl2Package.eINSTANCE.getTaskSend_Implementation();
    }

    @Override
    protected EReference getMessageInFeature() {
        return Xpdl2Package.eINSTANCE.getTaskSend_Message();
    }

    @Override
    protected EReference getMessageOutFeature() {
        return null;
    }

    @Override
    protected EReference getWebServiceOperationFeature() {
        return Xpdl2Package.eINSTANCE.getTaskSend_WebServiceOperation();
    }

    /**
     * @see com.tibco.xpd.implementer.script.AbstractActivityMessageAdapter#getMessageIn(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param act
     * @return
     */
    @Override
    public Message getMessageIn(Activity act) {
        TaskSend taskSend = getTaskSend(act);
        if (taskSend != null) {
            return taskSend.getMessage();
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.implementer.script.AbstractActivityMessageAdapter#getMessageOut(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param act
     * @return
     */
    @Override
    public Message getMessageOut(Activity act) {
        return null;
    }

    /**
     * @see com.tibco.xpd.implementer.script.AbstractActivityMessageAdapter#getWebServiceOperation(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param act
     * @return
     */
    @Override
    public WebServiceOperation getWebServiceOperation(Activity act) {
        TaskSend taskSend = getTaskSend(act);
        if (taskSend != null) {
            return taskSend.getWebServiceOperation();
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.implementer.script.AbstractActivityMessageAdapter#getPortTypeOperation(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param act
     * @return
     */
    @Override
    public PortTypeOperation getPortTypeOperation(Activity act) {
        TaskSend taskSend = getTaskSend(act);
        if (taskSend != null) {
            PortTypeOperation portTypeOperation =
                    (PortTypeOperation) Xpdl2ModelUtil
                            .getOtherElement(taskSend,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_PortTypeOperation());
            return portTypeOperation;
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.implementer.script.ActivityMessageProvider#getImplementation(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param act
     * @return
     */
    public ImplementationType getImplementation(Activity act) {
        TaskSend taskSend = getTaskSend(act);
        if (taskSend != null) {
            return taskSend.getImplementation();
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.implementer.script.ActivityMessageProvider#getMessageContainer(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param act
     * @return
     */
    public EObject getMessageContainer(Activity act) {
        return getTaskSend(act);
    }

    /**
     * @param act
     * @return
     */
    private TaskSend getTaskSend(Activity act) {
        if (act.getImplementation() instanceof Task) {
            TaskSend taskSend = ((Task) act.getImplementation()).getTaskSend();
            return taskSend;
        }
        return null;
    }

    @Override
    protected EObject createTaskTypeNode(Activity act) {
        Task task =
                ElementsFactory
                        .createActivityTaskElement(TaskType.SEND_LITERAL);

        act.setImplementation(task);

        return task.getTaskSend();
    }

    @Override
    protected EObject createTaskTypeNode(Activity act, EditingDomain ed,
            CompoundCommand cmd, EObject in, EObject out) {
        Task task =
                ElementsFactory
                        .createActivityTaskElement(TaskType.SEND_LITERAL);

        cmd.append(SetCommand.create(ed,
                act,
                Xpdl2Package.eINSTANCE.getActivity_Implementation(),
                task));
        // Changes for XPD-3451, persist in/out mapping when there is no port
        // type and operation change
        TaskSend taskTypeNode = task.getTaskSend();
        if (in != null) {
            cmd.append(SetCommand.create(ed,
                    taskTypeNode,
                    Xpdl2Package.eINSTANCE.getTaskSend_Message(),
                    in));
        }
        return taskTypeNode;
    }

    @Override
    protected String getXpdExtImplementationType() {
        return TaskServiceMessageAdapter.XPDEXT_WEBSVC_LITERAL;
    }

}
