/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.script;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.ElementsFactory;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.wsdl.WsdlServiceKey;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.OtherAttributesContainer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskReceive;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Service message adapter for receive task.
 * 
 * @author aallway
 * 
 */
public class TaskReceiveMessageAdapter extends
        AbstractTaskActivityMessageAdapter {

    @Override
    protected OtherAttributesContainer getExtImplementationTypeContainer(
            Activity act) {
        TaskReceive taskReceive = getTaskReceive(act);
        return taskReceive;
    }

    @Override
    protected EAttribute getImplementationTypeFeature() {
        return Xpdl2Package.eINSTANCE.getTaskReceive_Implementation();
    }

    @Override
    protected EReference getMessageInFeature() {
        return null;
    }

    @Override
    protected EReference getMessageOutFeature() {
        return Xpdl2Package.eINSTANCE.getTaskReceive_Message();
    }

    @Override
    protected EReference getWebServiceOperationFeature() {
        return Xpdl2Package.eINSTANCE.getTaskReceive_WebServiceOperation();
    }

    /**
     * @see com.tibco.xpd.implementer.script.AbstractActivityMessageAdapter#getMessageIn(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param act
     * @return
     */
    @Override
    public Message getMessageIn(Activity act) {
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
        return getTaskReceive(act).getMessage();
    }

    /**
     * @see com.tibco.xpd.implementer.script.AbstractActivityMessageAdapter#getWebServiceOperation(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param act
     * @return
     */
    @Override
    public WebServiceOperation getWebServiceOperation(Activity act) {
        TaskReceive taskReceive = getTaskReceive(act);
        if (taskReceive != null) {
            return taskReceive.getWebServiceOperation();
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
        TaskReceive taskReceive = getTaskReceive(act);
        if (taskReceive != null) {
            PortTypeOperation portTypeOperation =
                    (PortTypeOperation) Xpdl2ModelUtil
                            .getOtherElement(taskReceive,
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
        TaskReceive taskReceive = getTaskReceive(act);
        if (taskReceive != null) {
            return taskReceive.getImplementation();
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
        return getTaskReceive(act);
    }

    /**
     * @param act
     * @return
     */
    private TaskReceive getTaskReceive(Activity act) {
        if (act.getImplementation() instanceof Task) {
            TaskReceive taskReceive =
                    ((Task) act.getImplementation()).getTaskReceive();
            return taskReceive;
        }
        return null;
    }

    @Override
    protected EObject createTaskTypeNode(Activity act) {
        Task task =
                ElementsFactory
                        .createActivityTaskElement(TaskType.RECEIVE_LITERAL);
        act.setImplementation(task);
        return task.getTaskReceive();
    }

    @Override
    protected EObject createTaskTypeNode(Activity act, EditingDomain ed,
            CompoundCommand cmd, EObject in, EObject out) {
        Task task =
                ElementsFactory
                        .createActivityTaskElement(TaskType.RECEIVE_LITERAL);

        cmd.append(SetCommand.create(ed,
                act,
                Xpdl2Package.eINSTANCE.getActivity_Implementation(),
                task));

        // Changes for XPD-3451, persist in/out mapping when there is no port
        // type and operation change
        TaskReceive taskTypeNode = task.getTaskReceive();
        if (out != null) {
            cmd.append(SetCommand.create(ed,
                    taskTypeNode,
                    Xpdl2Package.eINSTANCE.getTaskReceive_Message(),
                    out));
        }

        return taskTypeNode;
    }

    @Override
    protected String getXpdExtImplementationType() {
        return TaskServiceMessageAdapter.XPDEXT_WEBSVC_LITERAL;
    }

    /**
     * @see com.tibco.xpd.implementer.script.AbstractActivityMessageAdapter#getClearWebServiceCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      com.tibco.xpd.xpdl2.Activity)
     * 
     * @param ed
     * @param act
     * @return
     */
    @Override
    public Command getClearWebServiceCommand(EditingDomain ed, Activity act) {
        CompoundCommand cmd = new CompoundCommand();
        cmd.append(super.getClearWebServiceCommand(ed, act));
        Command setNotGeneratedCommand =
                TaskObjectUtil.getSetNotGeneratedCommand(ed, act);
        if (setNotGeneratedCommand != null
                && setNotGeneratedCommand.canExecute()) {
            cmd.append(setNotGeneratedCommand);
        }
        return cmd;
    }

    /**
     * @see com.tibco.xpd.implementer.script.AbstractTaskActivityMessageAdapter#getAssignWebServiceCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      com.tibco.xpd.xpdl2.Process, com.tibco.xpd.xpdl2.Activity,
     *      java.lang.String, boolean, com.tibco.xpd.wsdl.WsdlServiceKey)
     * 
     * @param ed
     * @param process
     * @param act
     * @param wsdlURL
     * @param isRemote
     * @param key
     * @return
     */
    @Override
    public Command getAssignWebServiceCommand(EditingDomain ed,
            Process process, Activity act, String wsdlURL, boolean isRemote,
            WsdlServiceKey key) {
        CompoundCommand cmd = new CompoundCommand();
        Command assignWebServiceCommand =
                super.getAssignWebServiceCommand(ed,
                        process,
                        act,
                        wsdlURL,
                        isRemote,
                        key);

        cmd.append(assignWebServiceCommand);
        cmd.append(TaskObjectUtil.getSetNotGeneratedCommand(ed, act));
        return cmd;
    }

}
