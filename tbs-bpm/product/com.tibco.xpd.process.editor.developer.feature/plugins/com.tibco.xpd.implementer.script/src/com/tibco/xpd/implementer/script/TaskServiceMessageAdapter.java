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
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Adapt a task service to the ActivityMessage interface so that it can be used
 * in standard message operations.
 * 
 * @author aallway
 * 
 */
public class TaskServiceMessageAdapter extends
        AbstractTaskActivityMessageAdapter {

    public static String XPDEXT_WEBSVC_LITERAL = "WebService"; //$NON-NLS-1$

    @Override
    protected OtherAttributesContainer getExtImplementationTypeContainer(
            Activity act) {
        TaskService taskService = getTaskService(act);
        return taskService;
    }

    @Override
    protected EAttribute getImplementationTypeFeature() {
        return Xpdl2Package.eINSTANCE.getTaskService_Implementation();
    }

    @Override
    protected EReference getMessageInFeature() {
        return Xpdl2Package.eINSTANCE.getTaskService_MessageIn();
    }

    @Override
    protected EReference getMessageOutFeature() {
        return Xpdl2Package.eINSTANCE.getTaskService_MessageOut();
    }

    @Override
    protected EReference getWebServiceOperationFeature() {
        return Xpdl2Package.eINSTANCE.getTaskService_WebServiceOperation();
    }

    /**
     * @see com.tibco.xpd.implementer.script.AbstractActivityMessageAdapter#getMessageIn(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param act
     * @return
     */
    @Override
    public Message getMessageIn(Activity act) {
        TaskService taskService = getTaskService(act);
        if (taskService != null) {
            return taskService.getMessageIn();
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
        TaskService taskService = getTaskService(act);
        if (taskService != null) {
            return taskService.getMessageOut();
        }
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
        TaskService taskService = getTaskService(act);
        if (taskService != null) {
            return taskService.getWebServiceOperation();
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
        TaskService taskService = getTaskService(act);
        if (taskService != null) {
            PortTypeOperation portTypeOperation =
                    (PortTypeOperation) Xpdl2ModelUtil
                            .getOtherElement(taskService,
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
        TaskService taskService = getTaskService(act);
        if (taskService != null) {
            return taskService.getImplementation();
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
        return getTaskService(act);
    }

    /**
     * @param act
     * @return
     */
    private TaskService getTaskService(Activity act) {
        if (act.getImplementation() instanceof Task) {
            TaskService taskService =
                    ((Task) act.getImplementation()).getTaskService();
            return taskService;
        }
        return null;
    }

    @Override
    protected EObject createTaskTypeNode(Activity act) {
        Task task =
                ElementsFactory
                        .createActivityTaskElement(TaskType.SERVICE_LITERAL);

        act.setImplementation(task);

        return task.getTaskService();
    }

    @Override
    protected EObject createTaskTypeNode(Activity act, EditingDomain ed,
            CompoundCommand cmd, EObject in, EObject out) {
        Task task =
                ElementsFactory
                        .createActivityTaskElement(TaskType.SERVICE_LITERAL);

        cmd.append(SetCommand.create(ed,
                act,
                Xpdl2Package.eINSTANCE.getActivity_Implementation(),
                task));
        // Changes for XPD-3451, persist in/out mapping when there is no port
        // type and operation change
        TaskService taskTypeNode = task.getTaskService();
        if (in != null) {
            cmd.append(SetCommand.create(ed,
                    taskTypeNode,
                    Xpdl2Package.eINSTANCE.getTaskService_MessageIn(),
                    in));

        }
        if (out != null) {
            cmd.append(SetCommand.create(ed,
                    taskTypeNode,
                    Xpdl2Package.eINSTANCE.getTaskService_MessageOut(),
                    out));
        }
        return taskTypeNode;
    }

    @Override
    protected String getXpdExtImplementationType() {
        return XPDEXT_WEBSVC_LITERAL;
    }

}
