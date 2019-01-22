/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.implementer.script;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.wsdl.WsdlServiceKey;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Assignment;
import com.tibco.xpd.xpdl2.EndPoint;
import com.tibco.xpd.xpdl2.EndPointTypeType;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.OtherAttributesContainer;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Service;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskReceive;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author aallway
 * 
 */
public abstract class AbstractTaskActivityMessageAdapter extends
        AbstractActivityMessageAdapter {

    /**
     * Get the EMF feature reference for the WebServiceOperation element under
     * the activity message container.
     * 
     * @return web service operation feature.
     */
    protected abstract EReference getWebServiceOperationFeature();

    /**
     * Get the EMF feature reference for the Xpdl2 Implementation Type.
     * 
     * @return implementation type attribute feature.
     */
    protected abstract EAttribute getImplementationTypeFeature();

    /**
     * Get the EMF feature reference for the message input parameters feature.
     * 
     * @return null if message in feature not supported for the task type
     */
    protected abstract EReference getMessageInFeature();

    /**
     * Get the EMF feature reference for the message input parameters feature.
     * 
     * @return null if message out feature not supported for the task type
     */
    protected abstract EReference getMessageOutFeature();

    /**
     * Get the container element for the ImplementationType as an
     * OtherAttributesContainer.
     * 
     * @param act
     *            The activity.
     * @return Other attrib container for extended implamentation type
     *         attribute.
     */
    protected abstract OtherAttributesContainer getExtImplementationTypeContainer(
            Activity act);

    /**
     * Get the value of the required xpdExt:ImplementationType attribute.
     * 
     * @return The implementation type.
     */
    protected abstract String getXpdExtImplementationType();

    /**
     * Create the task type node (TaskService etc) in the event that task is not
     * already correct type.
     * 
     * @param act
     *            The activity.
     * @return new task type node.
     */
    protected abstract EObject createTaskTypeNode(Activity act);

    /**
     * Create the task type node (TaskService etc) in the event that task is not
     * already correct type. And append command the add task type to activity in
     * 
     * @param act
     *            The activity.
     * @param ed
     *            The editing domain.
     * @param cmd
     *            The command to add to.
     * @param out
     * @param in
     * @return new task type node.
     */
    protected abstract EObject createTaskTypeNode(Activity act,
            EditingDomain ed, CompoundCommand cmd, EObject in, EObject out);

    /**
     * @param process
     *            The process.
     * @param act
     *            The activity.
     * @param wsdlURL
     *            The URL of the WSDL.
     * @param isRemote
     *            True if remote.
     * @param key
     *            The WSDL service key.
     * @return The command to assign the web service to the activity.
     * @see com.tibco.xpd.implementer.script.ActivityMessageProvider#
     *      getAssignWebServiceCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      com.tibco.xpd.xpdl2.Activity, java.lang.String,
     *      com.tibco.xpd.wsdl.WsdlServiceKey)
     */
    @Override
    public void assignWebService(List<Object> objectsToCreate, Process process,
            Activity act, String wsdlURL, boolean isRemote, WsdlServiceKey key) {
        // Safest to always re-create the task implementation detail from
        // scratch - delete existing implementaiton
        act.setBlockActivity(null);
        act.setImplementation(null);
        act.setEvent(null);

        // Create new task implementation.
        EObject taskTypeNode = createTaskTypeNode(act);

        // Add the xpd extensions implementation type.
        Xpdl2ModelUtil
                .setOtherAttribute((OtherAttributesContainer) taskTypeNode,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ImplementationType(),
                        getXpdExtImplementationType());

        // Create the web service operation.
        WebServiceOperation wso =
                Xpdl2Factory.eINSTANCE.createWebServiceOperation();

        Service ws = Xpdl2Factory.eINSTANCE.createService();
        Xpdl2ModelUtil.setOtherAttribute(ws,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_IsRemote(),
                new Boolean(isRemote));

        wso.setService(ws);

        EndPoint endPoint = Xpdl2Factory.eINSTANCE.createEndPoint();
        endPoint.setEndPointType(EndPointTypeType.WSDL_LITERAL);

        ExternalReference ref =
                Xpdl2Factory.eINSTANCE.createExternalReference();
        ref.setLocation(wsdlURL);
        endPoint.setExternalReference(ref);
        ws.setEndPoint(endPoint);

        ws.setServiceName(key.getService());
        ws.setPortName(key.getPort());

        wso.setOperationName(key.getOperation());

        String portTypeName = ""; //$NON-NLS-1$
        String portOperationName = ""; //$NON-NLS-1$
        if (key.getPortType() != null) {
            portTypeName = key.getPortType();
            portOperationName = key.getPortTypeOperation();
        }

        // Add the abstract wsdl information
        PortTypeOperation portTypeOperation =
                (PortTypeOperation) Xpdl2ModelUtil
                        .getOtherElement((OtherElementsContainer) taskTypeNode,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_PortTypeOperation());
        if (portTypeOperation == null) {
            portTypeOperation =
                    XpdExtensionFactory.eINSTANCE.createPortTypeOperation();
        }
        portTypeOperation.setPortTypeName(portTypeName);
        portTypeOperation.setOperationName(portOperationName);
        ExternalReference ref2 =
                Xpdl2Factory.eINSTANCE.createExternalReference();
        ref2.setLocation(wsdlURL);
        portTypeOperation.setExternalReference(ref2);
        taskTypeNode.eSet(XpdExtensionPackage.eINSTANCE
                .getDocumentRoot_PortTypeOperation(), Collections
                .singleton(portTypeOperation));

        // clears security profile
        wso.getOtherAttributes()
                .set(XpdExtensionPackage.eINSTANCE.getDocumentRoot_SecurityProfile(),
                        ""); //$NON-NLS-1$

        // sets the transport type name
        if (key.getTransportURI() != null
                && key.getTransportURI().trim().length() > 0) {
            wso.getOtherAttributes()
                    .set(XpdExtensionPackage.eINSTANCE.getDocumentRoot_Transport(),
                            key.getTransportURI());
        } else {
            /*
             * If there's no transport then it means it's abstract wsdl in which
             * case if it's a service invoke or a api activity we want to set it
             * to service virtualisation transport.
             */
            wso.getOtherAttributes()
                    .set(XpdExtensionPackage.eINSTANCE.getDocumentRoot_Transport(),
                            Xpdl2WsdlUtil.SERVICE_VIRTUALIZATION_URL);
        }

        // set alias value with first participant or relevant data it finds for
        // this activity
        wso.getOtherAttributes()
                .set(XpdExtensionPackage.eINSTANCE.getDocumentRoot_Alias(), ""); //$NON-NLS-1$
        Iterator<Performer> iter = act.getPerformerList().iterator();
        boolean isFound = false;
        while (iter.hasNext() && !isFound) {
            Performer perf = iter.next();
            Object tempObj =
                    Xpdl2ModelUtil
                            .getParticipantOrProcessData(act.getProcess(), perf);
            if (tempObj != null && tempObj instanceof Participant) {
                wso.getOtherAttributes()
                        .set(XpdExtensionPackage.eINSTANCE.getDocumentRoot_Alias(),
                                ((Participant) tempObj).getId());
                isFound = true;
            } else if (tempObj != null
                    && tempObj instanceof ProcessRelevantData) {
                wso.getOtherAttributes()
                        .set(XpdExtensionPackage.eINSTANCE.getDocumentRoot_Alias(),
                                ((ProcessRelevantData) tempObj).getId());
                isFound = true;
            }
        }

        taskTypeNode.eSet(getWebServiceOperationFeature(), wso);

        taskTypeNode.eSet(getImplementationTypeFeature(),
                ImplementationType.WEB_SERVICE_LITERAL);

        //
        // Add the message in / out.
        EReference msgInFeature = getMessageInFeature();
        if (msgInFeature != null) {
            Message in = Xpdl2Factory.eINSTANCE.createMessage();
            taskTypeNode.eSet(msgInFeature, in);
        }

        EReference msgOutFeature = getMessageOutFeature();
        if (msgOutFeature != null) {
            Message out = Xpdl2Factory.eINSTANCE.createMessage();
            taskTypeNode.eSet(msgOutFeature, out);
        }

        //
        // Remove any existing assignments.
        EList<Assignment> assignments = act.getAssignments();
        assignments.clear();

        //
        // Create and assign an endpoint participant if possible.
        autoAssignEndpointParticipant(objectsToCreate, process, act, key);

        return;
    }

    /**
     * @param ed
     *            The editing domain.
     * @param process
     *            The process.
     * @param act
     *            The activity.
     * @param wsdlURL
     *            The URL of the WSDL.
     * @param isRemote
     *            True if remote.
     * @param key
     *            The WSDL service key.
     * @return The command to assign the web service to the activity.
     * @see com.tibco.xpd.implementer.script.ActivityMessageProvider#
     *      getAssignWebServiceCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      com.tibco.xpd.xpdl2.Activity, java.lang.String,
     *      com.tibco.xpd.wsdl.WsdlServiceKey)
     */
    @Override
    public Command getAssignWebServiceCommand(EditingDomain ed,
            Process process, Activity act, String wsdlURL, boolean isRemote,
            WsdlServiceKey key) {
        CompoundCommand command =
                new CompoundCommand(
                        Messages.TaskServiceMessageAdapter_AssignWebServiceCommand);

        /*
         * XPD-6972: Kapil - As we are creating task implementation details
         * alltogether we need to preserve the "Correlate Immediately"
         * Configuration.
         */
        boolean oldCorrelateImmediatelyValue = false;

        boolean hasCorrelateImmediateAttribute = false;

        if (Xpdl2ModelUtil.isCorrelatingActivity(act)) {
            /*
             * Applicable only if the Task is Correlating.
             */
            Implementation impl = act.getImplementation();

            if (impl instanceof Task) {

                Task task = (Task) impl;

                TaskReceive taskReceive = task.getTaskReceive();

                if (taskReceive != null) {
                    /*
                     * check if the "Correlate Immediately" attribute is
                     * present.
                     */
                    hasCorrelateImmediateAttribute =
                            taskReceive.eIsSet(XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_CorrelateImmediately());
                    /*
                     * Get the old value of "Correlate Immediately"
                     */
                    if (hasCorrelateImmediateAttribute) {
                        oldCorrelateImmediatelyValue =
                                Xpdl2ModelUtil
                                        .getOtherAttributeAsBoolean(taskReceive,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_CorrelateImmediately());
                    }
                }
            }
        }

        // Safest to always re-create the task implementation detail from
        // scratch - delete existing implementaiton
        command.append(SetCommand.create(ed,
                act,
                Xpdl2Package.eINSTANCE.getActivity_BlockActivity(),
                null));
        command.append(SetCommand.create(ed,
                act,
                Xpdl2Package.eINSTANCE.getActivity_Implementation(),
                null));
        command.append(SetCommand.create(ed,
                act,
                Xpdl2Package.eINSTANCE.getActivity_Event(),
                null));
        // Changes for XPD-3451, persist in/out mapping when there is no port
        // type and operation change
        EObject oldIn = null, OldOut = null;
        if (noChangeInPortTypeOperation(act, key)) {
            oldIn = getMessageIn(act);
            OldOut = getMessageOut(act);

            if (oldIn != null) {
                oldIn = EcoreUtil.copy(oldIn);
            }
            if (OldOut != null) {
                OldOut = EcoreUtil.copy(OldOut);
            }
        }
        // Create new task implementation.
        EObject taskTypeNode =
                createTaskTypeNode(act, ed, command, oldIn, OldOut);

        // Add the xpd extensions implementation type.
        Xpdl2ModelUtil
                .setOtherAttribute((OtherAttributesContainer) taskTypeNode,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ImplementationType(),
                        getXpdExtImplementationType());

        if (hasCorrelateImmediateAttribute) {
            /*
             * If the activity is an correlating activity then set the old value
             * of "Correlate Immediately" config.
             */
            command.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                    (OtherAttributesContainer) taskTypeNode,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_CorrelateImmediately(),
                    oldCorrelateImmediatelyValue));

        }

        // Create the web service operation.
        WebServiceOperation wso =
                Xpdl2Factory.eINSTANCE.createWebServiceOperation();

        Service ws = Xpdl2Factory.eINSTANCE.createService();
        Xpdl2ModelUtil.setOtherAttribute(ws,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_IsRemote(),
                new Boolean(isRemote));

        wso.setService(ws);

        EndPoint endPoint = Xpdl2Factory.eINSTANCE.createEndPoint();
        endPoint.setEndPointType(EndPointTypeType.WSDL_LITERAL);

        ExternalReference ref =
                Xpdl2Factory.eINSTANCE.createExternalReference();
        ref.setLocation(wsdlURL);
        endPoint.setExternalReference(ref);
        ws.setEndPoint(endPoint);

        ws.setServiceName(key.getService());
        ws.setPortName(key.getPort());

        wso.setOperationName(key.getOperation());

        String portTypeName = ""; //$NON-NLS-1$
        String portOperationName = ""; //$NON-NLS-1$
        if (key.getPortType() != null) {
            portTypeName = key.getPortType();
            portOperationName = key.getPortTypeOperation();
        }

        // Add the abstract wsdl information
        PortTypeOperation portTypeOperation =
                (PortTypeOperation) Xpdl2ModelUtil
                        .getOtherElement((OtherElementsContainer) taskTypeNode,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_PortTypeOperation());
        if (portTypeOperation == null) {
            portTypeOperation =
                    XpdExtensionFactory.eINSTANCE.createPortTypeOperation();
        }
        portTypeOperation.setPortTypeName(portTypeName);
        portTypeOperation.setOperationName(portOperationName);
        ExternalReference ref2 =
                Xpdl2Factory.eINSTANCE.createExternalReference();
        ref2.setLocation(wsdlURL);
        portTypeOperation.setExternalReference(ref2);
        taskTypeNode.eSet(XpdExtensionPackage.eINSTANCE
                .getDocumentRoot_PortTypeOperation(), Collections
                .singleton(portTypeOperation));

        // clears security profile
        wso.getOtherAttributes()
                .set(XpdExtensionPackage.eINSTANCE.getDocumentRoot_SecurityProfile(),
                        ""); //$NON-NLS-1$

        // sets the transport type name
        if (key.getTransportURI() != null
                && key.getTransportURI().trim().length() > 0) {
            wso.getOtherAttributes()
                    .set(XpdExtensionPackage.eINSTANCE.getDocumentRoot_Transport(),
                            key.getTransportURI());
        } else {
            /*
             * If there's no transport then it means it's abstract wsdl in which
             * case if it's a service invoke or a api activity we want to set it
             * to service virtualisation transport.
             */
            wso.getOtherAttributes()
                    .set(XpdExtensionPackage.eINSTANCE.getDocumentRoot_Transport(),
                            Xpdl2WsdlUtil.SERVICE_VIRTUALIZATION_URL);
        }

        // set alias value with first participant or relevant data it finds for
        // this activity
        wso.getOtherAttributes()
                .set(XpdExtensionPackage.eINSTANCE.getDocumentRoot_Alias(), ""); //$NON-NLS-1$
        Iterator<Performer> iter = act.getPerformerList().iterator();
        boolean isFound = false;
        while (iter.hasNext() && !isFound) {
            Performer perf = iter.next();
            Object tempObj =
                    Xpdl2ModelUtil
                            .getParticipantOrProcessData(act.getProcess(), perf);
            if (tempObj != null && tempObj instanceof Participant) {
                wso.getOtherAttributes()
                        .set(XpdExtensionPackage.eINSTANCE.getDocumentRoot_Alias(),
                                ((Participant) tempObj).getId());
                isFound = true;
            } else if (tempObj != null
                    && tempObj instanceof ProcessRelevantData) {
                wso.getOtherAttributes()
                        .set(XpdExtensionPackage.eINSTANCE.getDocumentRoot_Alias(),
                                ((ProcessRelevantData) tempObj).getId());
                isFound = true;
            }
        }

        taskTypeNode.eSet(getWebServiceOperationFeature(), wso);

        taskTypeNode.eSet(getImplementationTypeFeature(),
                ImplementationType.WEB_SERVICE_LITERAL);

        //
        // Add the message in / out.
        EReference msgInFeature = getMessageInFeature();
        if (msgInFeature != null) {
            Message in = Xpdl2Factory.eINSTANCE.createMessage();
            taskTypeNode.eSet(msgInFeature, in);
        }

        EReference msgOutFeature = getMessageOutFeature();
        if (msgOutFeature != null) {
            Message out = Xpdl2Factory.eINSTANCE.createMessage();
            taskTypeNode.eSet(msgOutFeature, out);
        }

        //
        // Remove any existing assignments.
        EList assignments = act.getAssignments();
        if (assignments.size() > 0) {
            command.append(RemoveCommand.create(ed,
                    act,
                    Xpdl2Package.eINSTANCE.getActivity_Assignments(),
                    assignments));
        }

        //
        // Create and assign an endpoint participant if possible.
        autoAssignEndpointParticipantCommand(ed, process, act, key, command);

        return command;
    }

    /**
     * @param ed
     *            The editing domain.
     * @param act
     *            The activity.
     * @param newImplType
     *            The new implementation type.
     * @return The command to change the implementation type.
     * @see com.tibco.xpd.implementer.script.ActivityMessageProvider#
     *      getSetImplementationCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      com.tibco.xpd.xpdl2.Activity,
     *      com.tibco.xpd.xpdl2.ImplementationType)
     */
    @Override
    public Command getSetImplementationCommand(EditingDomain ed, Activity act,
            ImplementationType newImplType) {

        EObject taskTypeNode = getMessageContainer(act);

        return SetCommand.create(ed,
                taskTypeNode,
                getImplementationTypeFeature(),
                newImplType);
    }

    /**
     * @param act
     *            The activity.
     * @return true if it has input mappings.
     * @see com.tibco.xpd.implementer.script.AbstractActivityMessageAdapter#
     *      hasMappingIn(com.tibco.xpd.xpdl2.Activity)
     */
    @Override
    public boolean hasMappingIn(Activity act) {
        if (ImplementationType.WEB_SERVICE_LITERAL
                .equals(getImplementation(act))
                && getMessageInFeature() != null) {
            return true;
        }
        return false;
    }

    /**
     * @param act
     *            The activity.
     * @return true if it has output mappings.
     * @see com.tibco.xpd.implementer.script.AbstractActivityMessageAdapter#
     *      hasMappingOut(com.tibco.xpd.xpdl2.Activity)
     */
    @Override
    public boolean hasMappingOut(Activity act) {
        if (ImplementationType.WEB_SERVICE_LITERAL
                .equals(getImplementation(act))
                && getMessageOutFeature() != null) {
            return true;
        }
        return false;
    }

}
