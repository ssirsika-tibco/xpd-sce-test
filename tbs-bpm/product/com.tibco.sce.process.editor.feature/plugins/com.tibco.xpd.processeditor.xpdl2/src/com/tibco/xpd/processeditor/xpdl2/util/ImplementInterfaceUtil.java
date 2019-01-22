/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.preCommit.AddPortTypeCommand;
import com.tibco.xpd.processeditor.xpdl2.preCommit.UpdateMappingsCommandFactory;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.ElementsFactory;
import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.WidgetRGB;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.GatewayType;
import com.tibco.xpd.processwidget.adapters.SequenceFlowType;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.AssociatedParametersContainer;
import com.tibco.xpd.xpdExtension.ErrorMethod;
import com.tibco.xpd.xpdExtension.ImplementedInterface;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.IntermediateMethod;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdExtension.Visibility;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.CatchThrow;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.EndPoint;
import com.tibco.xpd.xpdl2.EndPointTypeType;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.Service;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.commands.AbstractLateExecuteCommand;
import com.tibco.xpd.xpdl2.commands.LateExecuteCompoundCommand;
import com.tibco.xpd.xpdl2.extension.EMFSearchUtil;
import com.tibco.xpd.xpdl2.resolvers.Xpdl2FieldOrParamDeleter;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.ThrowErrorEventUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Utility to ease implementation of process interfaces.
 * 
 * @author rsomayaj
 * 
 * 
 */
public class ImplementInterfaceUtil {
    /**
     * 
     */
    private static final String EMPTY_STRING = ""; //$NON-NLS-1$

    /**
     * This method creates the start events, end response events and end error
     * events for corresponding process interface events. If it is invoked from
     * a quick fix, it doesn't create the transitions but if it is invoked from
     * the wizard it does
     * 
     * The port type operation, and associated parameters are copied across.
     * 
     * @param editingDomain
     * @param process
     * @param startMethod
     * @param point
     * @return
     */
    public static Command getAddImplementedStartMethodCommand(
            EditingDomain editingDomain, Process process,
            StartMethod startMethod, Point point, boolean transitionRequired) {
        LateExecuteCompoundCommand cmd = new LateExecuteCompoundCommand();
        WidgetRGB fillColor =
                ProcessWidgetColors.getInstance().getGraphicalNodeColor(null,
                        ProcessWidgetColors.INTERFACE_START_EVENT_FILL);
        WidgetRGB lineColor =
                ProcessWidgetColors.getInstance().getGraphicalNodeColor(null,
                        ProcessWidgetColors.START_EVENT_LINE);
        String laneId =
                getRelevantLane(getRelevantPool(process, cmd), cmd).getId();
        Activity startEventAct =
                ElementsFactory
                        .createEvent(point,
                                new org.eclipse.draw2d.geometry.Dimension(
                                        ProcessWidgetConstants.START_EVENT_SIZE,
                                        ProcessWidgetConstants.START_EVENT_SIZE),
                                laneId,
                                EventFlowType.FLOW_START_LITERAL,
                                TriggerType.NONE_LITERAL.equals(startMethod
                                        .getTrigger()) ? EventTriggerType.EVENT_NONE_LITERAL
                                        : EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL,
                                fillColor.toString(),
                                lineColor.toString(),
                                null);
        startEventAct.setName(startMethod.getName());
        Xpdl2ModelUtil.setOtherAttribute(startEventAct,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                Xpdl2ModelUtil.getDisplayName(startMethod));

        Xpdl2ModelUtil.setOtherAttribute(startEventAct,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_Implements(),
                startMethod.getId());

        Xpdl2ModelUtil.setOtherAttribute(startEventAct,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_Visibility(),
                Visibility.PUBLIC);

        // Copy port type operations
        copyPortTypeOperation(startEventAct, startMethod);

        // Copy the associatedParameters from the startMethod
        copyAssociatedParameters(startEventAct, startMethod);

        prepareWebServiceOperation(startEventAct);
        cmd.append(AddCommand.create(editingDomain,
                process,
                Xpdl2Package.eINSTANCE.getFlowContainer_Activities(),
                startEventAct));
        cmd.append(UpdateMappingsCommandFactory
                .getUpdateMappingsCommand(editingDomain, startEventAct));
        Activity connectAct =
                createErrorEvents(editingDomain,
                        process,
                        startMethod,
                        point,
                        transitionRequired,
                        cmd,
                        fillColor,
                        lineColor,
                        laneId,
                        startEventAct);
        if (!isNoneTypeInterfaceMethod(startMethod)
                && isRequestResponse(startMethod)) {
            appendEndMessageEvent(editingDomain,
                    process,
                    startMethod,
                    point,
                    transitionRequired,
                    cmd,
                    fillColor,
                    lineColor,
                    laneId,
                    startEventAct,
                    connectAct);
        }

        return cmd;
    }

    /**
     * This is a private method invoked from the createStartEvents - for
     * creating end message events for request response activities.
     * 
     * @param editingDomain
     * @param process
     * @param interfaceMethod
     * @param point
     * @param transitionRequired
     * @param cmd
     * @param fillColor
     * @param lineColor
     * @param laneId
     * @param eventAct
     */
    private static void appendEndMessageEvent(EditingDomain editingDomain,
            Process process, InterfaceMethod interfaceMethod, Point point,
            boolean transitionRequired, CompoundCommand cmd,
            WidgetRGB fillColor, WidgetRGB lineColor, String laneId,
            Activity eventAct, Activity connectAct) {

        String endEventName = null;
        String reqActName = ReplyActivityUtil.getRequestActivityLabel(eventAct);
        Activity endEventAct =
                ElementsFactory.createEvent(new Point(590, point.y),
                        new Dimension(ProcessWidgetConstants.END_EVENT_SIZE,
                                ProcessWidgetConstants.END_EVENT_SIZE),
                        laneId,
                        EventFlowType.FLOW_END_LITERAL,
                        EventTriggerType.EVENT_MESSAGE_THROW_LITERAL,
                        fillColor.toString(),
                        lineColor.toString(),
                        null);
        Xpdl2ModelUtil.setOtherAttribute(endEventAct,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_Implements(),
                interfaceMethod.getId());
        Xpdl2ModelUtil.setOtherAttribute(endEventAct,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_Visibility(),
                Visibility.PUBLIC);

        Xpdl2ModelUtil.setOtherAttribute(EventObjectUtil
                .getTriggerResultMessage(endEventAct),
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_ReplyToActivityId(),
                eventAct.getId());

        /*
         * XPD-4248: set apropriate name for reply activity based on requesting
         * activity
         */
        endEventName =
                ReplyActivityUtil.REPLY_ACTIVITY_UTIL_REPLY_TO_LABEL + ": " //$NON-NLS-1$
                        + reqActName;
        endEventAct.setName(NameUtil.getInternalName(endEventName, false));
        Xpdl2ModelUtil.setOtherAttribute(endEventAct,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                endEventName);

        cmd.append(AddCommand.create(editingDomain,
                process,
                Xpdl2Package.eINSTANCE.getFlowContainer_Activities(),
                endEventAct));
        cmd.append(UpdateMappingsCommandFactory
                .getUpdateMappingsCommand(editingDomain, endEventAct));

        if (transitionRequired) {
            WidgetRGB transitionColor =
                    ProcessWidgetColors
                            .getInstance()
                            .getGraphicalNodeColor(null,
                                    ProcessWidgetColors.UNCONTROLLED_SEQ_FLOW_LINE);
            Transition transition = null;
            SequenceFlowType seqFlowType = SequenceFlowType.DEFAULT_LITERAL;
            if (connectAct == eventAct) {
                seqFlowType = SequenceFlowType.UNCONTROLLED_LITERAL;
            }
            transition =
                    ElementsFactory.createTransition(connectAct,
                            endEventAct,
                            seqFlowType,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            transitionColor.toString());
            cmd.append(AddCommand.create(editingDomain,
                    process,
                    Xpdl2Package.eINSTANCE.getFlowContainer_Transitions(),
                    transition));
        }
    }

    /**
     * Private method invoked from createStartEvents to create End error for
     * start events which have error associated with them.
     * 
     * Creates gateways and transitions when created via the wizard and doesn't
     * do so, if invoked from the quick fix.
     * 
     * @param editingDomain
     * @param process
     * @param interfaceMethod
     * @param point
     * @param transitionRequired
     * @param cmd
     * @param fillColor
     * @param lineColor
     * @param laneId
     * @param requestEventActivity
     * @param connectAct
     * @return
     */
    private static Activity createErrorEvents(EditingDomain editingDomain,
            Process process, InterfaceMethod interfaceMethod, Point point,
            boolean transitionRequired, CompoundCommand cmd,
            WidgetRGB fillColor, WidgetRGB lineColor, String laneId,
            Activity requestEventActivity) {
        Activity connectAct = requestEventActivity;
        EList<ErrorMethod> errorMethods = interfaceMethod.getErrorMethods();
        if (!(errorMethods.isEmpty())) {

            int errMethodCount = 1;
            for (ErrorMethod errorMethod : errorMethods) {
                /*
                 * Check if already contains a error event for method. Ignore if
                 * does.
                 */
                if (findImplementingActivity(process, errorMethod.getId()) != null) {
                    continue;
                }

                // Generate an end Error event.
                Point errorEventPoint =
                        new Point(point.x + (100 * errMethodCount),
                                point.y + 100);
                Activity errorEventActivity =
                        ElementsFactory.createEvent(errorEventPoint,
                                new Dimension(
                                        ProcessWidgetConstants.END_EVENT_SIZE,
                                        ProcessWidgetConstants.END_EVENT_SIZE),
                                laneId,
                                EventFlowType.FLOW_END_LITERAL,
                                EventTriggerType.EVENT_ERROR_LITERAL,
                                fillColor.toString(),
                                lineColor.toString(),
                                process);

                Xpdl2ModelUtil.setOtherAttribute(errorEventActivity,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_Implements(),
                        errorMethod.getId());

                Xpdl2ModelUtil.setOtherAttribute(errorEventActivity,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_Visibility(),
                        Visibility.PUBLIC);

                copyAssociatedParameters(errorEventActivity, errorMethod);

                setActivityNameFromErrorCode(errorEventActivity,
                        errorMethod.getErrorCode());

                ((EndEvent) errorEventActivity.getEvent()).getResultError()
                        .setErrorCode(errorMethod.getErrorCode());

                cmd.append(AddCommand.create(editingDomain,
                        process,
                        Xpdl2Package.eINSTANCE.getFlowContainer_Activities(),
                        errorEventActivity));

                /*
                 * If necessary add the xpdl2:ResultError/xpdExt:FaultMessage
                 * and request activity id to the given throw error event
                 * activity.
                 * 
                 * Only errors for interface message type methods have fault
                 * message stuff
                 */
                if (TriggerType.MESSAGE_LITERAL.equals(interfaceMethod
                        .getTrigger())) {
                    cmd.append(ThrowErrorEventUtil
                            .getConfigureAsFaultMessageErrorCommand(editingDomain,
                                    errorEventActivity,
                                    requestEventActivity.getId(),
                                    errorMethod.getErrorCode()));

                    /* Add the data mappings */
                    cmd.append(UpdateMappingsCommandFactory
                            .getUpdateMappingsCommand(editingDomain,
                                    errorEventActivity));
                }

                if (transitionRequired) {
                    /*
                     * On initial creation generate an XOR gateway and flows
                     * from incomign request activity.
                     */
                    Dimension size =
                            new Dimension(
                                    ProcessWidgetConstants.GATEWAY_WIDTH_SIZE,
                                    ProcessWidgetConstants.GATEWAY_HEIGHT_SIZE);
                    WidgetRGB gatewayFill =
                            ProcessWidgetColors
                                    .getInstance()
                                    .getGraphicalNodeColor(null,
                                            ProcessWidgetColors.COMPLEX_GATEWAY_FILL);
                    WidgetRGB gatewayLine =
                            ProcessWidgetColors
                                    .getInstance()
                                    .getGraphicalNodeColor(null,
                                            ProcessWidgetColors.COMPLEX_GATEWAY_LINE);
                    Point gatewayPoint =
                            new Point(point.x + (100 * errMethodCount), point.y);
                    Activity gateway =
                            ElementsFactory.createGateway(gatewayPoint,
                                    size,
                                    laneId,
                                    GatewayType.EXCLUSIVE_DATA_LITERAL,
                                    gatewayFill.toString(),
                                    gatewayLine.toString());

                    cmd.append(AddCommand.create(editingDomain,
                            process,
                            Xpdl2Package.eINSTANCE
                                    .getFlowContainer_Activities(),
                            gateway));
                    WidgetRGB transitionColor =
                            ProcessWidgetColors
                                    .getInstance()
                                    .getGraphicalNodeColor(null,
                                            ProcessWidgetColors.UNCONTROLLED_SEQ_FLOW_LINE);

                    SequenceFlowType seqFlowType =
                            SequenceFlowType.DEFAULT_LITERAL;
                    if (connectAct == requestEventActivity) {
                        seqFlowType = SequenceFlowType.UNCONTROLLED_LITERAL;
                    }
                    Transition transition1 =
                            ElementsFactory.createTransition(connectAct,
                                    gateway,
                                    seqFlowType,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    transitionColor.toString());

                    connectAct = gateway;

                    Transition transition2 =
                            ElementsFactory.createTransition(connectAct,
                                    errorEventActivity,
                                    SequenceFlowType.CONDITIONAL_LITERAL,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    transitionColor.toString());
                    cmd.append(AddCommand.create(editingDomain,
                            process,
                            Xpdl2Package.eINSTANCE
                                    .getFlowContainer_Transitions(),
                            transition1));
                    cmd.append(AddCommand.create(editingDomain,
                            process,
                            Xpdl2Package.eINSTANCE
                                    .getFlowContainer_Transitions(),
                            transition2));
                }
                errMethodCount++;
            }
        }
        return connectAct;
    }

    /**
     * Set the implementing error event name from error method error code.
     * 
     * @param endError
     * @param errorCode
     */
    private static void setActivityNameFromErrorCode(Activity endError,
            String errorCode) {
        String name = getImplementingErrorEventName(errorCode);

        Xpdl2ModelUtil.setOtherAttribute(endError,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                name);

        endError.setName(NameUtil.getInternalName(name, false));
    }

    /**
     * @param errorCode
     * @return The Implementing error event activity display name from the
     */
    public static String getImplementingErrorEventName(String errorCode) {
        String name = ""; //$NON-NLS-1$

        if (errorCode != null && errorCode.length() > 0) {
            name =
                    String.format(Messages.ImplementInterfaceUtil_ThrowError_ErrorCode_label,
                            errorCode);
        }

        return name;
    }

    /**
     * Private method which prepares the Web Service Operation in for an API
     * implemented activity.
     * 
     * @param activity
     */
    private static void prepareWebServiceOperation(Activity activity) {
        TriggerResultMessage triggerResultMessage =
                EventObjectUtil.getTriggerResultMessage(activity);
        if (triggerResultMessage != null) {
            WebServiceOperation webServiceOperation =
                    triggerResultMessage.getWebServiceOperation();
            if (webServiceOperation == null) {
                webServiceOperation =
                        ElementsFactory.createWebServiceOperation();
                triggerResultMessage
                        .setWebServiceOperation(webServiceOperation);
            }
            if (webServiceOperation != null) {
                Xpdl2ModelUtil.setOtherAttribute(webServiceOperation,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_SecurityProfile(),
                        EMPTY_STRING);
                /*
                 * XPD-3669: While fixing XPD-3547 it has been decided that
                 * transport type for generated services (and abstract
                 * operations from user defined wsdls) must be service
                 * virtualisation.
                 */
                Xpdl2ModelUtil.setOtherAttribute(webServiceOperation,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_Transport(),
                        AddPortTypeCommand.SERVICE_VIRTUALIZATION_URL);

                Xpdl2ModelUtil.setOtherAttribute(webServiceOperation,
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_Alias(),
                        EMPTY_STRING);

                Service service = webServiceOperation.getService();

                ExternalReference externalReference = null;

                PortTypeOperation pTO =
                        getPortTypeOperation(triggerResultMessage);
                if (pTO != null) {
                    externalReference = pTO.getExternalReference();
                    if (externalReference != null) {
                        EObject copiedExternalReference =
                                EcoreUtil.copy(externalReference);
                        EndPoint endPoint = service.getEndPoint();
                        if (endPoint == null) {
                            endPoint = Xpdl2Factory.eINSTANCE.createEndPoint();
                            endPoint.setExternalReference((ExternalReference) copiedExternalReference);
                        }
                    }
                }

                if (service != null) {
                    if (service.getEndPoint() != null) {
                        EndPoint endPoint = service.getEndPoint();
                        endPoint.setEndPointType(EndPointTypeType.WSDL_LITERAL);

                        if (externalReference != null) {
                            endPoint.setExternalReference(EcoreUtil
                                    .copy(externalReference));
                        }
                    }

                    Xpdl2ModelUtil.setOtherAttribute(service,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_IsRemote(),
                            true);
                }
            }
        }

        return;
    }

    /**
     * 
     * This method returns a command that creates only the Response message
     * event to an API implemented request activity.
     * 
     * It finds the request activity, set itself as a reply, copies the web
     * service operations, and associated parameters across.
     * 
     * 
     * Command to create only a missing {@link EndEvent} that implements a
     * {@link StartMethod}.
     * 
     * @param editingDomain
     * @param process
     * @param interfaceMethod
     * @param point
     * @return
     */
    public static Command getAddImplementedEndResponseEventCommand(
            EditingDomain editingDomain, Process process,
            InterfaceMethod interfaceMethod, Point point) {
        String endEventName = null;
        CompoundCommand cmd = new CompoundCommand();
        WidgetRGB fillColor =
                ProcessWidgetColors.getInstance().getGraphicalNodeColor(null,
                        ProcessWidgetColors.INTERFACE_START_EVENT_FILL);
        WidgetRGB lineColor =
                ProcessWidgetColors.getInstance().getGraphicalNodeColor(null,
                        ProcessWidgetColors.END_EVENT_LINE);

        Activity endEventActivity =
                ElementsFactory
                        .createEvent(new Point(590, point.y),
                                new Dimension(
                                        ProcessWidgetConstants.END_EVENT_SIZE,
                                        ProcessWidgetConstants.END_EVENT_SIZE),
                                getRelevantLane(getRelevantPool(process, cmd),
                                        cmd).getId(),
                                EventFlowType.FLOW_END_LITERAL,
                                interfaceMethod.getTrigger() == TriggerType.NONE_LITERAL ? EventTriggerType.EVENT_NONE_LITERAL
                                        : EventTriggerType.EVENT_MESSAGE_THROW_LITERAL,
                                fillColor.toString(),
                                lineColor.toString(),
                                null);

        /*
         * XPD-4248: Set end event name appropriately according to requesting
         * event.
         */
        endEventName =
                ReplyActivityUtil.REPLY_ACTIVITY_UTIL_REPLY_TO_LABEL + ": " //$NON-NLS-1$
                        + Xpdl2ModelUtil.getDisplayName(interfaceMethod);
        endEventActivity.setName(NameUtil.getInternalName(endEventName, false));
        Xpdl2ModelUtil.setOtherAttribute(endEventActivity,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                endEventName);

        Xpdl2ModelUtil.setOtherAttribute(endEventActivity,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_Implements(),
                interfaceMethod.getId());
        Xpdl2ModelUtil.setOtherAttribute(endEventActivity,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_Visibility(),
                Visibility.PUBLIC);
        prepareWebServiceOperation(endEventActivity);
        cmd.append(AddCommand.create(editingDomain,
                process,
                Xpdl2Package.eINSTANCE.getFlowContainer_Activities(),
                endEventActivity));

        cmd.append(new SetupEndMessageReplyActIdCmd(editingDomain, process,
                endEventActivity, interfaceMethod.getId()));

        return cmd;
    }

    /**
     * Private method that finds the request activity for a given interface
     * method.
     * 
     * @param process
     * @param interfaceMethodId
     * @return activity that implements start/intermedaite/error method with
     *         given id.
     */
    public static Activity findImplementingActivity(Process process,
            String interfaceMethodId) {
        List activities = new ArrayList();
        activities.addAll(Xpdl2ModelUtil.getAllActivitiesInProc(process));
        EObject object =
                EMFSearchUtil.findInList(activities,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_Implements(),
                        interfaceMethodId);
        if (object instanceof Activity) {
            return (Activity) object;
        }
        return null;
    }

    /**
     * Add only {@link StartEvent} that implements a {@link StartMethod}. DOES
     * NOT create {@link EndEvent} if the {@link StartMethod} is a
     * Request-Response type.
     * 
     * @param editingDomain
     * @param process
     * @param startMethod
     * @param point
     * @return
     */
    public static Command getAddOnlyImplementedStartEventCommand(
            EditingDomain editingDomain, Process process,
            StartMethod startMethod, Point point) {
        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.ImplementInterfaceUtil_AddStartEventCmd_label);
        WidgetRGB fillColor =
                ProcessWidgetColors.getInstance().getGraphicalNodeColor(null,
                        ProcessWidgetColors.INTERFACE_START_EVENT_FILL);
        WidgetRGB lineColor =
                ProcessWidgetColors.getInstance().getGraphicalNodeColor(null,
                        ProcessWidgetColors.START_EVENT_LINE);
        String laneId =
                getRelevantLane(getRelevantPool(process, cmd), cmd).getId();
        Activity startEvent =
                ElementsFactory
                        .createEvent(point,
                                new org.eclipse.draw2d.geometry.Dimension(
                                        ProcessWidgetConstants.START_EVENT_SIZE,
                                        ProcessWidgetConstants.START_EVENT_SIZE),
                                laneId,
                                EventFlowType.FLOW_START_LITERAL,
                                startMethod.getTrigger() == TriggerType.NONE_LITERAL ? EventTriggerType.EVENT_NONE_LITERAL
                                        : EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL,
                                fillColor.toString(),
                                lineColor.toString(),
                                null);

        startEvent.setName(startMethod.getName());
        Xpdl2ModelUtil.setOtherAttribute(startEvent,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                Xpdl2ModelUtil.getDisplayName(startMethod));

        Xpdl2ModelUtil.setOtherAttribute(startEvent,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_Implements(),
                startMethod.getId());
        // copying visibilty from Method
        Xpdl2ModelUtil.setOtherAttribute(startEvent,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_Visibility(),
                Visibility.PUBLIC);

        // Copy port type operations
        copyPortTypeOperation(startEvent, startMethod);

        // Copy the associatedParameters from the startMethod
        copyAssociatedParameters(startEvent, startMethod);

        prepareWebServiceOperation(startEvent);
        cmd.append(AddCommand.create(editingDomain,
                process,
                Xpdl2Package.eINSTANCE.getFlowContainer_Activities(),
                startEvent));
        cmd.append(UpdateMappingsCommandFactory
                .getUpdateMappingsCommand(editingDomain, startEvent));

        return cmd;
    }

    /**
     * Add only IntermediateEvent that implements a {@link IntermediateMethod}.
     * DOES NOT create {@link EndEvent} if the {@link IntermediateMethod} is a
     * Request-Response type.
     * 
     * @param editingDomain
     * @param process
     * @param intermediateMethod
     * @param point
     * @return
     */
    public static Command getAddOnlyImplementedIntermediateEvents(
            EditingDomain editingDomain, Process process,
            IntermediateMethod intermediateMethod, Point point) {
        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.ImplementInterfaceUtil_AddIntermediateEventCmd_label);
        WidgetRGB fillColor =
                ProcessWidgetColors.getInstance().getGraphicalNodeColor(null,
                        ProcessWidgetColors.INTERFACE_INTERMEDIATE_EVENT_FILL);
        WidgetRGB lineColor =
                ProcessWidgetColors.getInstance().getGraphicalNodeColor(null,
                        ProcessWidgetColors.INTERMEDIATE_EVENT_LINE);
        String laneId =
                getRelevantLane(getRelevantPool(process, cmd), cmd).getId();
        Activity intermediateEvent =
                ElementsFactory
                        .createEvent(point,
                                new org.eclipse.draw2d.geometry.Dimension(
                                        ProcessWidgetConstants.INTERMEDIATE_EVENT_SIZE,
                                        ProcessWidgetConstants.INTERMEDIATE_EVENT_SIZE),
                                laneId,
                                EventFlowType.FLOW_INTERMEDIATE_LITERAL,
                                intermediateMethod.getTrigger() == TriggerType.NONE_LITERAL ? EventTriggerType.EVENT_NONE_LITERAL
                                        : EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL,
                                fillColor.toString(),
                                lineColor.toString(),
                                null);

        intermediateEvent.setName(intermediateMethod.getName());
        Xpdl2ModelUtil.setOtherAttribute(intermediateEvent,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                Xpdl2ModelUtil.getDisplayName(intermediateMethod));

        Xpdl2ModelUtil.setOtherAttribute(intermediateEvent,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_Implements(),
                intermediateMethod.getId());

        Xpdl2ModelUtil.setOtherAttribute(intermediateEvent,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_Visibility(),
                Visibility.PUBLIC);

        // Copy port type operations
        copyPortTypeOperation(intermediateEvent, intermediateMethod);

        // Copy the associatedParameters from the startMethod
        copyAssociatedParameters(intermediateEvent, intermediateMethod);

        prepareWebServiceOperation(intermediateEvent);
        cmd.append(AddCommand.create(editingDomain,
                process,
                Xpdl2Package.eINSTANCE.getFlowContainer_Activities(),
                intermediateEvent));
        cmd.append(UpdateMappingsCommandFactory
                .getUpdateMappingsCommand(editingDomain, intermediateEvent));

        return cmd;
    }

    /**
     * Command that adds the intermediate events corresponding to a process
     * interface intermediate event.
     * 
     * @param editingDomain
     * @param process
     * @param intermediateMethod
     * @param point
     * @return
     */
    public static Command getAddImplementedIntermediateMethodCommand(
            EditingDomain editingDomain, Process process,
            IntermediateMethod intermediateMethod, Point point,
            boolean transitionRequired) {
        CompoundCommand cmd = new LateExecuteCompoundCommand();
        cmd.setLabel(Messages.ImplementInterfaceUtil_AddIntermediateEventCmd_label);

        WidgetRGB fillColor =
                ProcessWidgetColors.getInstance().getGraphicalNodeColor(null,
                        ProcessWidgetColors.INTERFACE_INTERMEDIATE_EVENT_FILL);
        WidgetRGB lineColor =
                ProcessWidgetColors.getInstance().getGraphicalNodeColor(null,
                        ProcessWidgetColors.INTERMEDIATE_EVENT_LINE);

        EventTriggerType eventTriggerType = null;
        TriggerType triggerType = intermediateMethod.getTrigger();
        if (TriggerType.NONE_LITERAL.equals(triggerType)) {
            eventTriggerType = EventTriggerType.EVENT_NONE_LITERAL;
        } else if (null != intermediateMethod.getTriggerResultMessage()) {
            CatchThrow catchThrow =
                    intermediateMethod.getTriggerResultMessage()
                            .getCatchThrow();
            if (CatchThrow.CATCH == catchThrow) {
                eventTriggerType = EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL;
            } else if (CatchThrow.THROW == catchThrow) {
                eventTriggerType = EventTriggerType.EVENT_MESSAGE_THROW_LITERAL;
            }
        }

        String laneId =
                getRelevantLane(getRelevantPool(process, cmd), cmd).getId();
        if (null != eventTriggerType) {
            Activity intermediateEventAct =
                    ElementsFactory.createEvent(point,
                            new Dimension(
                                    ProcessWidgetConstants.START_EVENT_SIZE,
                                    ProcessWidgetConstants.START_EVENT_SIZE),
                            laneId,
                            EventFlowType.FLOW_INTERMEDIATE_LITERAL,
                            eventTriggerType,
                            fillColor.toString(),
                            lineColor.toString(),
                            null);

            intermediateEventAct.setName(intermediateMethod.getName());
            Xpdl2ModelUtil
                    .setOtherAttribute(intermediateEventAct,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName(),
                            Xpdl2ModelUtil.getDisplayName(intermediateMethod));

            Xpdl2ModelUtil.setOtherAttribute(intermediateEventAct,
                    XpdExtensionPackage.eINSTANCE.getDocumentRoot_Implements(),
                    intermediateMethod.getId());
            // copying visibilty from Method
            Xpdl2ModelUtil.setOtherAttribute(intermediateEventAct,
                    XpdExtensionPackage.eINSTANCE.getDocumentRoot_Visibility(),
                    Visibility.PUBLIC);
            // Copy port type operations
            copyPortTypeOperation(intermediateEventAct, intermediateMethod);

            // Copy the associatedParameters from the startMethod
            copyAssociatedParameters(intermediateEventAct, intermediateMethod);

            prepareWebServiceOperation(intermediateEventAct);

            cmd.append(AddCommand.create(editingDomain,
                    process,
                    Xpdl2Package.eINSTANCE.getFlowContainer_Activities(),
                    intermediateEventAct));
            cmd.append(UpdateMappingsCommandFactory
                    .getUpdateMappingsCommand(editingDomain,
                            intermediateEventAct));
            Activity connectAct =
                    createErrorEvents(editingDomain,
                            process,
                            intermediateMethod,
                            point,
                            transitionRequired,
                            cmd,
                            fillColor,
                            lineColor,
                            laneId,
                            intermediateEventAct);
            if (!isNoneTypeInterfaceMethod(intermediateMethod)
                    && isRequestResponse(intermediateMethod)) {
                appendEndMessageEvent(editingDomain,
                        process,
                        intermediateMethod,
                        point,
                        transitionRequired,
                        cmd,
                        fillColor,
                        lineColor,
                        laneId,
                        intermediateEventAct,
                        connectAct);
            }
        }
        return cmd;
    }

    /**
     * Checks if the given interface method is a none type or not.
     * 
     * @param intermediateMethod
     * @return
     */
    public static boolean isNoneTypeInterfaceMethod(
            InterfaceMethod interfaceMethod) {
        if (TriggerType.NONE_LITERAL.equals(interfaceMethod.getTrigger())) {
            return true;
        }
        return false;
    }

    /**
     * This is an Add-All Command provider, basically used for creating a
     * process out of a process interface. Goes through the process interface
     * being implemented and adds all the start activites, related end response
     * and error activities, and the intermediate activities.
     * 
     * @param editingDomain
     * @param process
     * @param processInterface
     * @return
     */
    public static Command getAddAllImplementedMethodsCommand(
            EditingDomain editingDomain, Process process,
            ProcessInterface processInterface) {
        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.ImplementInterfaceUtil_AddAllImplementedMethodCmd_label);
        List<StartMethod> startMethods = processInterface.getStartMethods();
        Point initialPoint = new Point(60, 60);
        for (StartMethod startMethod : startMethods) {
            cmd.append(getAddImplementedStartMethodCommand(editingDomain,
                    process,
                    startMethod,
                    initialPoint,
                    false));
            initialPoint.y = initialPoint.y + 120;
        }
        List<IntermediateMethod> intermediateMethods =
                processInterface.getIntermediateMethods();
        initialPoint = new Point(180, 120);
        for (IntermediateMethod intermediateMethod : intermediateMethods) {
            cmd.append(getAddImplementedIntermediateMethodCommand(editingDomain,
                    process,
                    intermediateMethod,
                    initialPoint,
                    false));
            initialPoint.x = initialPoint.x + 100;
        }
        return cmd;
    }

    /**
     * Returns the first pool of the process, if a process doesn't have one,
     * then appends an add command to add a pool to a given process if it
     * doesn't have one already
     * 
     * @param process
     * @param cmd
     * @return
     */
    public static Pool getRelevantPool(Process process, CompoundCommand cmd) {
        Package pkg = process.getPackage();
        if (pkg != null) {
            List<Pool> pools = pkg.getPools();
            for (Pool pool : pools) {
                if (pool.getProcessId() != null
                        && pool.getProcessId().equals(process.getId())) {
                    return pool;
                }
            }
        }

        Pool pool =
                ElementsFactory
                        .createPool(Messages.ProcessPropertySection_DefaultPool_value,
                                process.getId());
        Xpdl2ModelUtil.getOrCreateNodeGraphicsInfo(pool);
        if (pkg != null) {
            if (cmd == null) {
                pkg.getPools().add(pool);
            } else {
                cmd.append(AddCommand.create(WorkingCopyUtil
                        .getEditingDomain(process), pkg, Xpdl2Package.eINSTANCE
                        .getPackage_Pools(), pool));
            }
        }

        return pool;
    }

    /**
     * Returns the first lane of the pool, if it doesn't have any at all,
     * appends a command to add a lane to the pool.
     * 
     * @param pool
     * @param cmd
     * @return
     */
    public static Lane getRelevantLane(Pool pool, CompoundCommand cmd) {
        Lane lane;
        if (pool.getLanes().isEmpty()) {
            lane = Xpdl2Factory.eINSTANCE.createLane();
            lane.setName(NameUtil
                    .getInternalName(Messages.ProcessPropertySection_DefaultLane_label,
                            false));
            Xpdl2ModelUtil
                    .setOtherAttribute(lane,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName(),
                            Messages.ProcessPropertySection_DefaultLane_label);
            Xpdl2ModelUtil.getOrCreateNodeGraphicsInfo(lane);
            if (cmd == null || pool.eResource() == null) {
                pool.getLanes().add(lane);
            } else {
                cmd.append(AddCommand.create(WorkingCopyUtil
                        .getEditingDomain(pool), pool, Xpdl2Package.eINSTANCE
                        .getPool_Lanes(), lane));
            }
        } else {
            lane = pool.getLanes().get(0);
        }
        return lane;
    }

    /**
     * Utility that verifies whether a given {@link StartMethod} or
     * {@link IntermediateMethod} is a Request-Response operation.
     * 
     * @param interfaceMethod
     * @return
     */
    public static boolean isRequestResponse(InterfaceMethod interfaceMethod) {
        boolean resultIn = false, resultOut = false;
        List<AssociatedParameter> associatedParameters =
                interfaceMethod.getAssociatedParameters();
        for (AssociatedParameter param : associatedParameters) {
            ModeType paramMode =
                    ProcessInterfaceUtil.getAssocParamModeType(param);
            if (paramMode == ModeType.INOUT_LITERAL) {
                resultIn = true;
                resultOut = true;
                break;

            } else if (paramMode == ModeType.IN_LITERAL) {
                resultIn = true;
            } else if (paramMode == ModeType.OUT_LITERAL) {
                resultOut = true;
            }
            if (resultOut) {
                break;
            }
        }
        if (associatedParameters.isEmpty()) {
            /*
             * Sid XPD-2087: Only use all data implicitly if implicit
             * association has not been disabled.
             */
            if (!ProcessInterfaceUtil
                    .isImplicitAssociationDisabled(interfaceMethod)) {

                List<FormalParameter> formalParams =
                        ((ProcessInterface) interfaceMethod.eContainer())
                                .getFormalParameters();
                for (FormalParameter param : formalParams) {
                    if (param.getMode() == ModeType.INOUT_LITERAL) {
                        resultIn = true;
                        resultOut = true;
                        break;

                    } else if (param.getMode() == ModeType.IN_LITERAL) {
                        resultIn = true;
                    } else if (param.getMode() == ModeType.OUT_LITERAL) {
                        resultOut = true;
                    }
                    if (resultOut) {
                        break;
                    }
                }
            }
        }

        // return resultIn && resultOut;
        return resultOut;
    }

    /**
     * Utility that returns whether a given activity is a request-response
     * activity.
     * 
     * @param startMethod
     * @return true if the given incoming request activity represent a
     *         request-response message pattern.
     */

    public static boolean isRequestResponse(Activity activity) {
        boolean hasOutDataOrReplyActs = false;

        if (ReplyActivityUtil.isIncomingRequestActivity(activity)) {
            List<AssociatedParameter> associatedParameters =
                    ProcessInterfaceUtil
                            .getActivityAssociatedParameters(activity);
            if (!associatedParameters.isEmpty()) {
                for (AssociatedParameter param : associatedParameters) {
                    ModeType paramMode =
                            ProcessInterfaceUtil.getAssocParamModeType(param);
                    if (paramMode == ModeType.INOUT_LITERAL
                            || paramMode == ModeType.OUT_LITERAL) {
                        hasOutDataOrReplyActs = true;
                        break;
                    }
                }
            } else {
                /*
                 * Sid XPD-2087: Only use all data implicitly if implicit
                 * association has not been disabled.
                 */
                if (!ProcessInterfaceUtil
                        .isImplicitAssociationDisabled(activity)) {
                    Process proc = activity.getProcess();
                    List<FormalParameter> formalParams =
                            ProcessInterfaceUtil.getAllFormalParameters(proc);
                    for (FormalParameter param : formalParams) {
                        if (param.getMode() == ModeType.INOUT_LITERAL
                                || param.getMode() == ModeType.OUT_LITERAL) {
                            hasOutDataOrReplyActs = true;
                            break;
                        }
                    }
                }
            }

            if (!hasOutDataOrReplyActs) {
                /*
                 * If there is no out data it MIGHT still be request/response if
                 * it has any reply activities.
                 */
                if (!ReplyActivityUtil.getReplyActivities(activity).isEmpty()) {
                    hasOutDataOrReplyActs = true;
                }
            }
        }

        return hasOutDataOrReplyActs;
    }

    /**
     * 
     * @param ed
     * @param activity
     * @param interfaceMethod
     * @return
     */
    private static void copyPortTypeOperation(Activity activity,
            InterfaceMethod interfaceMethod) {
        // Copy the port type operation, associated parameters, data
        // mappings for the start activity
        TriggerResultMessage ifcMethodTrm =
                interfaceMethod.getTriggerResultMessage();
        if (ifcMethodTrm != null) {
            Event event = activity.getEvent();
            if (event instanceof StartEvent) {
                ((StartEvent) event)
                        .setImplementation(ImplementationType.WEB_SERVICE_LITERAL);

            } else if (event instanceof IntermediateEvent) {
                ((IntermediateEvent) event)
                        .setImplementation(ImplementationType.WEB_SERVICE_LITERAL);
            }
            Xpdl2ModelUtil.setOtherAttribute(event,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_ImplementationType(),
                    TaskImplementationTypeDefinitions.WEB_SERVICE);
            PortTypeOperation pTO = getPortTypeOperation(ifcMethodTrm);
            if (pTO != null) {

                EObject copiedPTO = EcoreUtil.copy(pTO);
                TriggerResultMessage actTrm =
                        EventObjectUtil.getTriggerResultMessage(activity);
                if (actTrm != null) {
                    Xpdl2ModelUtil.setOtherAttribute(actTrm,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_Generated(),
                            Boolean.TRUE);
                    Xpdl2ModelUtil.setOtherElement(actTrm,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_PortTypeOperation(),
                            copiedPTO);
                }
            }
        }
        return;
    }

    private static void copyAssociatedParameters(Activity activity,
            AssociatedParametersContainer assocParamsContainer) {
        List<AssociatedParameter> associatedParameters =
                assocParamsContainer.getAssociatedParameters();
        if (!associatedParameters.isEmpty()) {
            Object assocParamsObj =
                    Xpdl2ModelUtil.getOtherElement(activity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_AssociatedParameters());
            AssociatedParameters assocParams = null;
            if (assocParamsObj instanceof AssociatedParameters) {
                assocParams = (AssociatedParameters) assocParamsObj;
            }
            if (assocParams == null) {
                assocParams =
                        XpdExtensionFactory.eINSTANCE
                                .createAssociatedParameters();
                Xpdl2ModelUtil.setOtherElement(activity,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_AssociatedParameters(),
                        assocParams);
            }
            Collection<AssociatedParameter> copiedAssocParams =
                    EcoreUtil.copyAll(associatedParameters);
            assocParams.getAssociatedParameter().addAll(copiedAssocParams);
        }
        return;
    }

    /**
     * @param trm
     * @return
     */
    private static PortTypeOperation getPortTypeOperation(
            TriggerResultMessage trm) {
        Object otherElement =
                Xpdl2ModelUtil.getOtherElement(trm,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_PortTypeOperation());
        if (otherElement instanceof PortTypeOperation) {
            return (PortTypeOperation) otherElement;
        }
        return null;
    }

    /**
     * Command to add error events to the process for any given process
     * interface error event. Adds only the Error event and no transitions to or
     * from it.
     * 
     * @param editingDomain
     * @param process
     * @param interfaceMethod
     * @param initialPoint
     * @return
     */
    public static Command getAddImplementedEndErrorEvent(
            EditingDomain editingDomain, Process process,
            ErrorMethod errorMethod, Point initialPoint) {

        CompoundCommand cmd = new CompoundCommand();
        WidgetRGB fillColor =
                ProcessWidgetColors.getInstance().getGraphicalNodeColor(null,
                        ProcessWidgetColors.INTERFACE_START_EVENT_FILL);
        WidgetRGB lineColor =
                ProcessWidgetColors.getInstance().getGraphicalNodeColor(null,
                        ProcessWidgetColors.END_EVENT_LINE);

        Activity errorEventActivity =
                ElementsFactory.createEvent(new Point(initialPoint.x + 60,
                        initialPoint.y + 60),
                        new Dimension(ProcessWidgetConstants.END_EVENT_SIZE,
                                ProcessWidgetConstants.END_EVENT_SIZE),
                        getRelevantLane(getRelevantPool(process, cmd), cmd)
                                .getId(),
                        EventFlowType.FLOW_END_LITERAL,
                        EventTriggerType.EVENT_ERROR_LITERAL,
                        fillColor.toString(),
                        lineColor.toString(),
                        null);

        Xpdl2ModelUtil.setOtherAttribute(errorEventActivity,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_Implements(),
                errorMethod.getId());
        Xpdl2ModelUtil.setOtherAttribute(errorEventActivity,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_Visibility(),
                Visibility.PUBLIC);
        copyAssociatedParameters(errorEventActivity, errorMethod);

        ResultError resultError =
                ((EndEvent) errorEventActivity.getEvent()).getResultError();
        resultError.setErrorCode(errorMethod.getErrorCode());

        setActivityNameFromErrorCode(errorEventActivity,
                errorMethod.getErrorCode());

        cmd.append(AddCommand.create(editingDomain,
                process,
                Xpdl2Package.eINSTANCE.getFlowContainer_Activities(),
                errorEventActivity));

        /*
         * If necessary add the xpdl2:ResultError/xpdExt:FaultMessage and
         * request activity id to the given throw error event activity.
         * 
         * Only errors for interface message type methods have fault message
         * stuff
         */
        if (errorMethod.eContainer() instanceof InterfaceMethod) {
            InterfaceMethod interfaceMethod =
                    (InterfaceMethod) errorMethod.eContainer();

            if (TriggerType.MESSAGE_LITERAL
                    .equals(interfaceMethod.getTrigger())) {
                /*
                 * Find the activity that implements the parent interface
                 * method.
                 */
                cmd.append(new SetupEndErrorReplyActIdCmd(editingDomain,
                        process, errorEventActivity, interfaceMethod.getId(),
                        errorMethod));
            }

        }

        return cmd;

    }

    /**
     * Returns a command that adds the new selected Process Interface on to the
     * given process
     * 
     * @param ed
     * @param process
     * @param newInterface
     * @return {@link Command} that removes the old implemented process
     *         interface and adds the new process interface
     */
    public static Command addImplementedProcessInterfaceCommand(
            EditingDomain ed, Process process, ProcessInterface newInterface) {

        LateExecuteCompoundCommand cmd = new LateExecuteCompoundCommand();

        /*
         * Remove the old implemented interface (even if there wasn't one we'll
         * check that we have no implementing activities etc in case user
         * deleted the interface before un-implementing it.
         */

        appendRemoveImplementedInterfaceCommand(ed, process, cmd);

        /* Create and add the ImplementedInterface reference. */

        ImplementedInterface implementedInterface =
                XpdExtensionFactory.eINSTANCE.createImplementedInterface();

        implementedInterface.setProcessInterfaceId(newInterface.getId());

        /* Set external package reference */
        if (ProcessInterfaceUtil.getProcessInterfacePackage(newInterface) != process
                .getPackage()) {

            WorkingCopy externalWc =
                    WorkingCopyUtil.getWorkingCopyFor(newInterface);
            Xpdl2WorkingCopyImpl wc =
                    (Xpdl2WorkingCopyImpl) WorkingCopyUtil
                            .getWorkingCopyFor(process);

            String refId = wc.appendCreateReferenceCommand(externalWc, cmd);

            implementedInterface.setPackageRef(refId);
        }

        cmd.append(Xpdl2ModelUtil.getSetOtherElementCommand(ed,
                process,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_ImplementedInterface(),
                implementedInterface));

        cmd.append(ImplementInterfaceUtil
                .getAddAllImplementedMethodsCommand(ed, process, newInterface));

        /*
         * Add the inherited Service Process Configuration values from Service
         * Process Interface to the Service Process
         */
        if (Xpdl2ModelUtil.isServiceProcessInterface(newInterface)
                && Xpdl2ModelUtil.isServiceProcess(process)) {

            cmd.append(Xpdl2ModelUtil.getSetOtherElementCommand(ed,
                    process,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_ServiceProcessConfiguration(),
                    null));
        }

        /*
         * Enabling Destinations for a process that implements a process
         * interface, while having selected a process interface from a picker in
         * the process property page
         */
        Set<String> enabledDestinations =
                DestinationUtil.getEnabledGlobalDestinations(newInterface);
        for (String enabledDestination : enabledDestinations) {

            /*
             * Check if the enabled destination is already configured on the
             * process
             */
            ExtendedAttribute attribute = null;
            List<?> destinationList = process.getExtendedAttributes();
            for (Object next : destinationList) {

                if (next instanceof ExtendedAttribute) {

                    ExtendedAttribute ea = (ExtendedAttribute) next;
                    if (DestinationUtil.DESTINATION_ATTRIBUTE.equals(ea
                            .getName())) {

                        if (enabledDestination.equals(ea.getValue())) {

                            attribute = ea;
                        }
                    }
                }
            }
            /*
             * If the enabled destination is not already configured on the
             * process, then add it now
             */
            if (null == attribute) {

                cmd.append(DestinationUtil.getSetDestinationEnabledCommand(ed,
                        process,
                        enabledDestination,
                        true));
            }
        }

        return cmd;
    }

    /**
     * Append commands required for removing the currently implemented interface
     * from the given process.
     * <p>
     * This will remove activities that implement interface events and remove
     * references to interface formal parameters from activities/transitions.
     * 
     * @param ed
     * @param process
     * @param cmd
     * 
     * @return true if there was a currently implemented interface to remove.
     */
    public static boolean appendRemoveImplementedInterfaceCommand(
            EditingDomain ed, Process process, LateExecuteCompoundCommand cmd) {

        ImplementedInterface implementedInterface =
                (ImplementedInterface) (process)
                        .getOtherElement(XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ImplementedInterface()
                                .getName());
        if (implementedInterface != null) {

            cmd.setLabel(Messages.ProcessPropertySection_ClearImplementedIfc_menu);

            /*
             * XPD-5427: Use new Xpdl2FieldOrParamDeleter method that deals with
             * all the objects that may contain references internally.
             * 
             * Now done above deletion of implementingActivities so that doing
             * delete reference from one after it's removed won't cause issues.
             */
            ProcessInterface procIfc =
                    ProcessInterfaceUtil
                            .getImplementedProcessInterface(process);

            if (procIfc != null) {
                EList<FormalParameter> ifcParams =
                        procIfc.getFormalParameters();

                Xpdl2FieldOrParamDeleter deleter =
                        new Xpdl2FieldOrParamDeleter();

                for (FormalParameter param : ifcParams) {
                    Command c =
                            deleter.getDeleteDataReferencesCommand(ed, param);

                    if (c != null) {
                        cmd.append(c);
                    }
                }
            }

            /* Remove any activities implementing interface methods. */

            Collection<Activity> activities =
                    Xpdl2ModelUtil.getAllActivitiesInProc(process);

            for (Activity act : activities) {
                if (ProcessInterfaceUtil.isEventImplemented(act)) {
                    cmd.append(new DeleteActivityCommand(ed, act));
                }
            }

            /* Finally remove the implemented interface element itself. */

            cmd.append(Xpdl2ModelUtil.getRemoveOtherElementCommand(ed,
                    (process),
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_ImplementedInterface(),
                    implementedInterface));

            return true;
        }

        return false;
    }

    /**
     * Sid XPD-2087: If both the request event and reply was added at the same
     * time then the reply activity did not get the xpdExt:ReplyActivityId set
     * because tthe findImplementingActivity would not work until the start
     * event is added.
     * 
     * This command finds the implementing request activity and then sets the
     * given reply activity's xpdExt:ReplyActivityId to that.
     * 
     * @author aallway
     * @since 11 Jul 2011
     */
    private static class SetupEndMessageReplyActIdCmd extends
            AbstractLateExecuteCommand {

        private String implementedMethodId;

        private Activity replyEventActivity;

        private Process process;

        /**
         * @param editingDomain
         * @param contextObject
         */
        public SetupEndMessageReplyActIdCmd(EditingDomain editingDomain,
                Process process, Activity replyEventActivity,
                String implementedMethodId) {
            super(editingDomain, replyEventActivity);

            this.process = process;
            this.replyEventActivity = replyEventActivity;
            this.implementedMethodId = implementedMethodId;
        }

        /**
         * @see com.tibco.xpd.xpdl2.commands.AbstractLateExecuteCommand#createCommand(org.eclipse.emf.edit.domain.EditingDomain,
         *      java.lang.Object)
         * 
         * @param editingDomain
         * @param contextObject
         * @return
         */
        @Override
        protected Command createCommand(EditingDomain editingDomain,
                Object contextObject) {
            Activity requestActivity =
                    findImplementingActivity(process, implementedMethodId);

            if (requestActivity != null) {
                CompoundCommand cmd = new CompoundCommand();

                cmd.append(Xpdl2ModelUtil
                        .getSetOtherAttributeCommand(editingDomain,
                                EventObjectUtil
                                        .getTriggerResultMessage(replyEventActivity),
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_ReplyToActivityId(),
                                requestActivity.getId()));

                cmd.append(UpdateMappingsCommandFactory
                        .getUpdateMappingsCommand(editingDomain,
                                replyEventActivity));

                return cmd;

            }
            return null;
        }

    }

    /**
     * Sid XPD-2087: If both the request event and error event was added at the
     * same time then the reply activity did not get the xpdExt:ReplyActivityId
     * set because tthe findImplementingActivity would not work until the start
     * event is added.
     * 
     * This command finds the implementing request activity and then sets the
     * given reply activity's xpdExt:ReplyActivityId to that.
     * 
     * @author aallway
     * @since 11 Jul 2011
     */
    private static class SetupEndErrorReplyActIdCmd extends
            AbstractLateExecuteCommand {

        private String implementedMethodId;

        private Activity errorEventActivity;

        private Process process;

        private ErrorMethod errorMethod;

        /**
         * @param editingDomain
         * @param contextObject
         */
        public SetupEndErrorReplyActIdCmd(EditingDomain editingDomain,
                Process process, Activity errorEventActivity,
                String implementedMethodId, ErrorMethod errorMethod) {
            super(editingDomain, errorEventActivity);

            this.process = process;
            this.errorEventActivity = errorEventActivity;
            this.implementedMethodId = implementedMethodId;
            this.errorMethod = errorMethod;
        }

        /**
         * @see com.tibco.xpd.xpdl2.commands.AbstractLateExecuteCommand#createCommand(org.eclipse.emf.edit.domain.EditingDomain,
         *      java.lang.Object)
         * 
         * @param editingDomain
         * @param contextObject
         * @return
         */
        @Override
        protected Command createCommand(EditingDomain editingDomain,
                Object contextObject) {
            Activity requestActivity =
                    findImplementingActivity(process, implementedMethodId);

            if (requestActivity != null) {
                CompoundCommand cmd = new CompoundCommand();

                String requestActivityId = null;
                if (requestActivity != null) {
                    requestActivityId = requestActivity.getId();
                }

                if (requestActivityId != null) {
                    cmd.append(ThrowErrorEventUtil
                            .getConfigureAsFaultMessageErrorCommand(editingDomain,
                                    errorEventActivity,
                                    requestActivityId,
                                    errorMethod.getErrorCode()));

                    /* Add the data mappings */
                    cmd.append(UpdateMappingsCommandFactory
                            .getUpdateMappingsCommand(editingDomain,
                                    errorEventActivity));
                    return cmd;
                }

            }
            return null;
        }

    }

}
