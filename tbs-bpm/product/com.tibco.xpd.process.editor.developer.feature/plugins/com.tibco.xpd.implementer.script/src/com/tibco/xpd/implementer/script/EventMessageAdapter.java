/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.script;

import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.wsdl.WsdlServiceKey;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.CatchThrow;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ResultType;
import com.tibco.xpd.xpdl2.Service;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Adapt any event with a message trigger or result to the MessageProvider
 * interface. Handles start, intermediate and end events.
 * 
 * @author scrossle
 * 
 */
public class EventMessageAdapter extends AbstractActivityMessageAdapter {

    /**
     * 
     */
    private static final String BLANK = ""; //$NON-NLS-1$

    /**
     * Structure for holding an event and it's description (whether Start,
     * Intermediate, End).
     */
    private class EventDescriptor {
        /** The event. */
        Event event;

        /** The class that implements the event. */
        Class<?> eventClass;

        /** Feature for trigger result message. */
        EStructuralFeature trmFeature;

        /** Feature for implementation. */
        EStructuralFeature implementationFeature;

        /** Implementation type */
        ImplementationType implementationType;

        /** Trigger or Result message */
        TriggerResultMessage trm;

        /** TriggerType of a start or intermediate event */
        TriggerType triggerType;

        EStructuralFeature trigTypeFeature;

        /** ResultType of an end evern */
        ResultType resultType;
    };

    @Override
    public void assignWebService(List<Object> objectsToCreate, Process process,
            Activity act, String endpoint, boolean isRemote, WsdlServiceKey key) {
        EventDescriptor eventDesc = getEvent(act);

        TriggerResultMessage trm = getTriggerResultMessage(act);
        if (trm == null) {
            trm = Xpdl2Factory.eINSTANCE.createTriggerResultMessage();

            setEventTriggerType(act, trm, eventDesc);

            Event event = act.getEvent();
            if (event instanceof EndEvent) {
                trm.setCatchThrow(CatchThrow.THROW);
            } else {
                trm.setCatchThrow(CatchThrow.CATCH);
            }
        }

        WebServiceOperation wso = trm.getWebServiceOperation();
        Service ws = (wso == null) ? null : wso.getService();

        if (wso == null) {
            wso = Xpdl2Factory.eINSTANCE.createWebServiceOperation();
            if (trm != null) {
                // Set the implementation type
                eventDesc.event.eSet(eventDesc.implementationFeature,
                        ImplementationType.WEB_SERVICE_LITERAL);

                Xpdl2ModelUtil.setOtherAttribute(eventDesc.event,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ImplementationType(),
                        TaskImplementationTypeDefinitions.WEB_SERVICE);

                trm.setWebServiceOperation(wso);
            }
        }
        ws = Xpdl2Factory.eINSTANCE.createService();

        // clears alias
        Xpdl2ModelUtil.setOtherAttribute(wso,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_Alias(),
                BLANK);

        // clears security profile
        Xpdl2ModelUtil
                .setOtherAttribute(wso, XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_SecurityProfile(), BLANK);

        // sets the transport type name
        if (key.getTransportURI() != null
                && key.getTransportURI().trim().length() > 0) {

            Xpdl2ModelUtil.setOtherAttribute(wso,
                    XpdExtensionPackage.eINSTANCE.getDocumentRoot_Transport(),
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

        Xpdl2ModelUtil.setOtherAttribute(ws,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_IsRemote(),
                new Boolean(isRemote));

        assignWebServiceEndpoint(ws, endpoint);
        ws.setServiceName(key.getService());
        ws.setPortName(key.getPort());
        wso.setService(ws);
        wso.setOperationName(key.getOperation());

        String portTypeName = BLANK;
        String portOperationName = BLANK;
        if (key.getPortType() != null) {
            portTypeName = key.getPortType();
            portOperationName = key.getPortTypeOperation();
        }

        // Add the abstract wsdl information with its external reference also
        PortTypeOperation portTypeOperation = getPortTypeOperation(act);
        if (portTypeOperation == null) {
            portTypeOperation =
                    XpdExtensionFactory.eINSTANCE.createPortTypeOperation();
        }
        portTypeOperation.setPortTypeName(portTypeName);
        portTypeOperation.setOperationName(portOperationName);
        ExternalReference ref =
                Xpdl2Factory.eINSTANCE.createExternalReference();
        ref.setLocation(endpoint);
        portTypeOperation.setExternalReference(ref);
        Xpdl2ModelUtil.setOtherElement(trm, XpdExtensionPackage.eINSTANCE
                .getDocumentRoot_PortTypeOperation(), portTypeOperation);

        Message mess = trm.getMessage();
        if (mess == null) {
            mess = Xpdl2Factory.eINSTANCE.createMessage();
            trm.setMessage(mess);
        }
        EList<DataMapping> mappings = mess.getDataMappings();
        mappings.clear();

        //
        // Create and assign an endpoint participant if possible.
        autoAssignEndpointParticipant(objectsToCreate, process, act, key);

        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.implementer.resources.xpdl2.adapters.ActivityMessageProvider
     * #getAssignWebServiceCommand(org.eclipse.emf.edit.domain.EditingDomain,
     * com.tibco.xpd.xpdl2.Activity, java.lang.String,
     * com.tibco.xpd.wsdl.WsdlServiceKey)
     */
    @Override
    public Command getAssignWebServiceCommand(EditingDomain ed,
            Process process, Activity act, String endpoint, boolean isRemote,
            WsdlServiceKey key) {
        CompoundCommand command =
                new CompoundCommand(
                        Messages.EventMessageAdapter_AssignWebServiceCommand);

        EventDescriptor eventDesc = getEvent(act);

        TriggerResultMessage trm = getTriggerResultMessage(act);
        if (trm == null) {
            trm = Xpdl2Factory.eINSTANCE.createTriggerResultMessage();

            getSetEventTriggerTypeCommand(ed, act, trm, command, eventDesc);
        }

        WebServiceOperation wso = trm.getWebServiceOperation();
        Service ws = (wso == null) ? null : wso.getService();

        if (wso == null) {
            wso = Xpdl2Factory.eINSTANCE.createWebServiceOperation();
            if (trm != null) {
                // Set the implementation type
                command.append(SetCommand.create(ed,
                        eventDesc.event,
                        eventDesc.implementationFeature,
                        ImplementationType.WEB_SERVICE_LITERAL));
                command.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                        eventDesc.event,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ImplementationType(),
                        TaskImplementationTypeDefinitions.WEB_SERVICE));

                command.append(SetCommand.create(ed,
                        trm,
                        Xpdl2Package.eINSTANCE
                                .getTriggerResultMessage_WebServiceOperation(),
                        wso));
            }
        }
        ws = Xpdl2Factory.eINSTANCE.createService();

        // clears alias
        command.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                wso,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_Alias(),
                BLANK));

        // clears security profile
        command.append(Xpdl2ModelUtil
                .getSetOtherAttributeCommand(ed,
                        wso,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_SecurityProfile(),
                        BLANK));

        // sets the transport type name
        if (key.getTransportURI() != null
                && key.getTransportURI().trim().length() > 0) {

            command.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                    wso,
                    XpdExtensionPackage.eINSTANCE.getDocumentRoot_Transport(),
                    key.getTransportURI()));
        } else {
            /*
             * If there's no transport then it means it's abstract wsdl in which
             * case if it's a service invoke or a api activity we want to set it
             * to service virtualisation transport.
             */
            command.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                    wso,
                    XpdExtensionPackage.eINSTANCE.getDocumentRoot_Transport(),
                    Xpdl2WsdlUtil.SERVICE_VIRTUALIZATION_URL));
        }

        command.append(getAssignWebServiceIsRemoteCommand(ed, ws, isRemote));
        command.append(getAssignWebServiceEndpointCommand(ed, ws, endpoint));
        command.append(SetCommand.create(ed,
                ws,
                Xpdl2Package.eINSTANCE.getService_ServiceName(),
                key.getService()));
        command.append(SetCommand.create(ed,
                ws,
                Xpdl2Package.eINSTANCE.getService_PortName(),
                key.getPort()));

        command.append(SetCommand.create(ed,
                wso,
                Xpdl2Package.eINSTANCE.getWebServiceOperation_Service(),
                ws));

        command.append(SetCommand.create(ed,
                wso,
                Xpdl2Package.eINSTANCE.getWebServiceOperation_OperationName(),
                key.getOperation()));

        String portTypeName = BLANK;
        String portOperationName = BLANK;
        if (key.getPortType() != null) {
            portTypeName = key.getPortType();
            portOperationName = key.getPortTypeOperation();
        }

        // Add the abstract wsdl information with its external reference also
        PortTypeOperation portTypeOperation = getPortTypeOperation(act);
        if (portTypeOperation == null) {
            portTypeOperation =
                    XpdExtensionFactory.eINSTANCE.createPortTypeOperation();
            portTypeOperation.setPortTypeName(portTypeName);
            portTypeOperation.setOperationName(portOperationName);
            ExternalReference ref =
                    Xpdl2Factory.eINSTANCE.createExternalReference();
            ref.setLocation(endpoint);
            portTypeOperation.setExternalReference(ref);
        } else {
            command.append(SetCommand.create(ed,
                    portTypeOperation,
                    XpdExtensionPackage.eINSTANCE
                            .getPortTypeOperation_PortTypeName(),
                    portTypeName));
            command.append(SetCommand.create(ed,
                    portTypeOperation,
                    XpdExtensionPackage.eINSTANCE
                            .getPortTypeOperation_OperationName(),
                    portOperationName));
            ExternalReference ref =
                    Xpdl2Factory.eINSTANCE.createExternalReference();
            ref.setLocation(endpoint);
            command.append(SetCommand.create(ed,
                    portTypeOperation,
                    XpdExtensionPackage.eINSTANCE
                            .getPortTypeOperation_ExternalReference(),
                    ref));
        }
        command.append(Xpdl2ModelUtil.getSetOtherElementCommand(ed,
                trm,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_PortTypeOperation(),
                portTypeOperation));

        Message mess = trm.getMessage();
        if (mess == null) {
            mess = Xpdl2Factory.eINSTANCE.createMessage();
            command.append(SetCommand.create(ed,
                    trm,
                    Xpdl2Package.eINSTANCE.getTriggerResultMessage_Message(),
                    mess));
        }
        EList<DataMapping> mappings = mess.getDataMappings();
        // XPD-3451 : changes for Events to handle mappings for web service,
        // when there is no change in PortType and PortType Operation.
        if (!noChangeInPortTypeOperation(act, key)) {
            command.append(RemoveCommand.create(ed,
                    mess,
                    Xpdl2Package.eINSTANCE.getMessage_DataMappings(),
                    mappings));
        }
        command.append(EventObjectUtil.getSetNotGeneratedCommand(ed, act));

        //
        // Create and assign an endpoint participant if possible.
        autoAssignEndpointParticipantCommand(ed, process, act, key, command);

        return command;
    }

    private void getSetEventTriggerTypeCommand(EditingDomain ed, Activity act,
            TriggerResultMessage trm, CompoundCommand cmd,
            EventDescriptor eventDesc) {

        EObject removeNode = eventDesc.event.getEventTriggerTypeNode();

        if (removeNode != null) {
            cmd.append(RemoveCommand.create(ed, removeNode));
        }

        cmd.append(SetCommand.create(ed,
                eventDesc.event,
                eventDesc.trmFeature,
                trm));

        if (eventDesc.event instanceof EndEvent) {
            cmd.append(SetCommand.create(ed,
                    eventDesc.event,
                    eventDesc.trigTypeFeature,
                    ResultType.MESSAGE_LITERAL));
        } else {
            cmd.append(SetCommand.create(ed,
                    eventDesc.event,
                    eventDesc.trigTypeFeature,
                    TriggerType.MESSAGE_LITERAL));
        }
        /*
         * create CatchThrow for TriggerResultMessage when drag and drop of wsdl
         * operation is performed
         */
        if (eventDesc.event instanceof StartEvent) {
            cmd.append(SetCommand.create(ed,
                    trm,
                    Xpdl2Package.eINSTANCE.getTriggerResultMessage_CatchThrow(),
                    CatchThrow.CATCH));
        } else {
            cmd.append(SetCommand.create(ed,
                    trm,
                    Xpdl2Package.eINSTANCE.getTriggerResultMessage_CatchThrow(),
                    CatchThrow.THROW));
        }

        return;
    }

    private void setEventTriggerType(Activity act, TriggerResultMessage trm,
            EventDescriptor eventDesc) {

        EObject removeNode = eventDesc.event.getEventTriggerTypeNode();

        if (removeNode != null) {
            removeNode.eContainer().eContents().remove(removeNode);
        }

        eventDesc.event.eSet(eventDesc.trmFeature, trm);

        if (eventDesc.event instanceof EndEvent) {
            eventDesc.event.eSet(eventDesc.trigTypeFeature,
                    ResultType.MESSAGE_LITERAL);
        } else {
            eventDesc.event.eSet(eventDesc.trigTypeFeature,
                    TriggerType.MESSAGE_LITERAL);
        }

        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.implementer.resources.xpdl2.adapters.ActivityMessageProvider
     * #getImplementation(com.tibco.xpd.xpdl2.Activity)
     */
    @Override
    public ImplementationType getImplementation(Activity act) {
        EventDescriptor ed = getEvent(act);
        return ed.implementationType;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.implementer.resources.xpdl2.adapters.ActivityMessageProvider
     * #getMessageIn(com.tibco.xpd.xpdl2.Activity)
     */
    @Override
    public Message getMessageIn(Activity act) {
        Message msg = null;
        EventDescriptor eventDescriptor = getEvent(act);
        if (EndEvent.class == eventDescriptor.eventClass
                || IntermediateEvent.class == eventDescriptor.eventClass) {
            TriggerResultMessage trm = getTriggerResultMessage(act);
            if (trm != null && CatchThrow.THROW.equals(trm.getCatchThrow())) {
                msg = trm.getMessage();
            }
        }
        return msg;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.implementer.resources.xpdl2.adapters.ActivityMessageProvider
     * #getMessageOut(com.tibco.xpd.xpdl2.Activity)
     */
    @Override
    public Message getMessageOut(Activity act) {
        Message msg = null;
        EventDescriptor eventDescriptor = getEvent(act);
        if (StartEvent.class == eventDescriptor.eventClass
                || IntermediateEvent.class == eventDescriptor.eventClass) {
            TriggerResultMessage trm = getTriggerResultMessage(act);
            if (trm != null && CatchThrow.CATCH.equals(trm.getCatchThrow())) {
                msg = trm.getMessage();
            }
        }
        return msg;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.implementer.resources.xpdl2.adapters.ActivityMessageProvider
     * #getSetImplementationCommand(org.eclipse.emf.edit.domain.EditingDomain,
     * com.tibco.xpd.xpdl2.Activity, com.tibco.xpd.xpdl2.ImplementationType)
     */
    @Override
    public Command getSetImplementationCommand(EditingDomain ed, Activity act,
            ImplementationType newImplType) {
        EventDescriptor eventDesc = getEvent(act);
        if (!newImplType.equals(eventDesc.implementationType)) {
            return SetCommand.create(ed,
                    eventDesc.event,
                    eventDesc.implementationFeature,
                    newImplType);
        } else {
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.implementer.resources.xpdl2.adapters.ActivityMessageProvider
     * #getSetMessageInCommand(org.eclipse.emf.edit.domain.EditingDomain,
     * com.tibco.xpd.xpdl2.Activity, com.tibco.xpd.xpdl2.Message)
     */
    public Command getSetMessageInCommand(EditingDomain ed, Activity act,
            Message newMessageIn) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.implementer.resources.xpdl2.adapters.ActivityMessageProvider
     * #getSetMessageOutCommand(org.eclipse.emf.edit.domain.EditingDomain,
     * com.tibco.xpd.xpdl2.Activity, com.tibco.xpd.xpdl2.Message)
     */
    public Command getSetMessageOutCommand(EditingDomain ed, Activity act,
            Message newMessageOut) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.implementer.resources.xpdl2.adapters.ActivityMessageProvider
     * #getWebServiceOperation(com.tibco.xpd.xpdl2.Activity)
     */
    @Override
    public WebServiceOperation getWebServiceOperation(Activity act) {
        TriggerResultMessage target = getTriggerResultMessage(act);
        return target == null ? null : target.getWebServiceOperation();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.implementer.resources.xpdl2.adapters.ActivityMessageProvider
     * #getPortTypeOperation(com.tibco.xpd.xpdl2.Activity)
     */
    @Override
    public PortTypeOperation getPortTypeOperation(Activity act) {
        TriggerResultMessage trm = null;
        if (!(ProcessInterfaceUtil.isEventImplemented(act))) {
            trm = getTriggerResultMessage(act);

        } else {
            InterfaceMethod method =
                    ProcessInterfaceUtil.getImplementedMethod(act);
            if (method != null) {
                trm = method.getTriggerResultMessage();
            }
        }

        if (trm != null) {
            PortTypeOperation portTypeOperation =
                    (PortTypeOperation) Xpdl2ModelUtil.getOtherElement(trm,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_PortTypeOperation());
            return portTypeOperation;
        }

        return null;
    }

    /**
     * Pull the Message Trigger ot Result out of the input activity.
     * 
     * @param act
     * @return
     */
    private TriggerResultMessage getTriggerResultMessage(Activity act) {
        EventDescriptor ed = getEvent(act);
        return ed.trm;
    }

    private EventDescriptor getEvent(Activity act) {
        Event ev = act.getEvent();
        EventDescriptor desc = new EventDescriptor();
        desc.event = ev;
        if (ev instanceof StartEvent) {
            desc.eventClass = StartEvent.class;
            desc.trmFeature =
                    Xpdl2Package.eINSTANCE.getStartEvent_TriggerResultMessage();
            desc.implementationFeature =
                    Xpdl2Package.eINSTANCE.getStartEvent_Implementation();
            StartEvent se = (StartEvent) ev;
            desc.trm = se.getTriggerResultMessage();
            desc.implementationType = se.getImplementation();
            desc.triggerType = se.getTrigger();
            desc.trigTypeFeature =
                    Xpdl2Package.eINSTANCE.getStartEvent_Trigger();

        } else if (ev instanceof IntermediateEvent) {
            desc.eventClass = IntermediateEvent.class;
            desc.trmFeature =
                    Xpdl2Package.eINSTANCE
                            .getIntermediateEvent_TriggerResultMessage();
            desc.implementationFeature =
                    Xpdl2Package.eINSTANCE
                            .getIntermediateEvent_Implementation();
            IntermediateEvent ie = (IntermediateEvent) ev;
            desc.trm = ie.getTriggerResultMessage();
            desc.implementationType = ie.getImplementation();
            desc.triggerType = ie.getTrigger();

            desc.trigTypeFeature =
                    Xpdl2Package.eINSTANCE.getIntermediateEvent_Trigger();
        } else {
            desc.eventClass = EndEvent.class;
            desc.trmFeature =
                    Xpdl2Package.eINSTANCE.getEndEvent_TriggerResultMessage();
            desc.implementationFeature =
                    Xpdl2Package.eINSTANCE.getEndEvent_Implementation();
            EndEvent ee = (EndEvent) ev;
            desc.trm = ee.getTriggerResultMessage();
            desc.implementationType = ee.getImplementation();
            desc.resultType = ee.getResult();
            desc.trigTypeFeature = Xpdl2Package.eINSTANCE.getEndEvent_Result();
        }
        return desc;

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.implementer.resources.xpdl2.adapters.ActivityMessageProvider
     * #getMessageContainer(com.tibco.xpd.xpdl2.Activity)
     */
    @Override
    public EObject getMessageContainer(Activity act) {
        return getTriggerResultMessage(act);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.implementer.resources.xpdl2.adapters.ActivityMessageProvider
     * #hasMappingIn()
     */
    @Override
    public boolean hasMappingIn(Activity act) {
        // only web service end events have inbound
        // other types may be added later
        EventDescriptor ed = getEvent(act);
        if (ed.eventClass.equals(EndEvent.class)) {
            if (ed.resultType.equals(ResultType.MESSAGE_LITERAL)
                    && ImplementationType.WEB_SERVICE_LITERAL
                            .equals(ed.implementationType)) {
                return true;
            }
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.implementer.resources.xpdl2.adapters.ActivityMessageProvider
     * #hasMappingOut()
     */
    @Override
    public boolean hasMappingOut(Activity act) {
        // only web service start and intermediate events have inbound
        // other types may be added later
        EventDescriptor ed = getEvent(act);
        if (ed.eventClass.equals(StartEvent.class)
                || ed.eventClass.equals(IntermediateEvent.class)) {
            if (ed.triggerType.equals(TriggerType.MESSAGE_LITERAL)
                    && ImplementationType.WEB_SERVICE_LITERAL
                            .equals(ed.implementationType)) {
                return true;
            }
        }
        return false;
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
                EventObjectUtil.getSetNotGeneratedCommand(ed, act);
        if (setNotGeneratedCommand != null
                && setNotGeneratedCommand.canExecute()) {
            cmd.append(setNotGeneratedCommand);
        }
        return cmd;
    }
}
