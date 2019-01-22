/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.preCommit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDestinationUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.preferences.BpmBindingPreferenceUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.util.UserInfoUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.ParticipantSharedResource;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.WsInbound;
import com.tibco.xpd.xpdExtension.WsResource;
import com.tibco.xpd.xpdExtension.WsSoapHttpInboundBinding;
import com.tibco.xpd.xpdExtension.WsSoapJmsInboundBinding;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.EndPoint;
import com.tibco.xpd.xpdl2.EndPointTypeType;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantType;
import com.tibco.xpd.xpdl2.ParticipantTypeElem;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Service;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskReceive;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Command to add the port type for a given activity. Port types are added to
 * Web Service type implementations of activities.
 * 
 * @author rsomayaj
 * 
 */
public class AddPortTypeCommand extends CompoundCommand {

    /** SERVICE VIRTUALISATION URI. */
    public static final String SERVICE_VIRTUALIZATION_URL =
            "http://www.tibco.com/service_virtualisation"; //$NON-NLS-1$

    private final Process process;

    private final Activity activity;

    private final EditingDomain editingDomain;

    public static final Map<String, String> activityNameIdMap =
            new HashMap<String, String>();

    /**
     * 
     */
    public AddPortTypeCommand(EditingDomain ed, Process process,
            Activity activity) {
        this.editingDomain = ed;
        this.process = process;
        this.activity = activity;
    }

    /**
     * @param cmd
     * @param editingDomain
     * @param notifier
     * @param newValue
     */
    @Override
    public void execute() {

        // This command will only execute for WSDLs that are Studio generated
        // and for processes which are configured to generate WSDLs
        if (ProcessDestinationUtil
                .shouldGenerateWSDLForProcessDestinations(process)) {

            TaskType taskType = TaskObjectUtil.getTaskType(activity);
            if (TaskType.RECEIVE_LITERAL.equals(taskType)) {
                Task task = (Task) activity.getImplementation();
                TaskReceive taskReceive = task.getTaskReceive();
                if (taskReceive != null) {
                    /*
                     * Ignore activities that have ungenerated wsdl operations
                     * (i.e. user custom wsdl).
                     */
                    if (!Xpdl2ModelUtil.isGeneratedRequestActivity(activity)) {
                        if (taskReceive.getWebServiceOperation() != null) {
                            return;
                        }
                    }

                    /*
                     * Create brand new port type and web service operation
                     * elements
                     */
                    PortTypeOperation portTypeOp =
                            createPortTypeOperation(process, activity);

                    WebServiceOperation webServiceOperation =
                            createWebServiceOperation(process,
                                    taskReceive.getWebServiceOperation());

                    this.appendAndExecute(Xpdl2ModelUtil
                            .getSetOtherAttributeCommand(editingDomain,
                                    taskReceive,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_ImplementationType(),
                                    TaskImplementationTypeDefinitions.WEB_SERVICE));
                    this.appendAndExecute(Xpdl2ModelUtil
                            .getSetOtherElementCommand(editingDomain,
                                    taskReceive,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_PortTypeOperation(),
                                    portTypeOp));

                    this.appendAndExecute(SetCommand.create(editingDomain,
                            taskReceive,
                            Xpdl2Package.eINSTANCE
                                    .getTaskReceive_WebServiceOperation(),
                            webServiceOperation));

                    /*
                     * Add the system participant (if it does not already
                     * exist).
                     */
                    if (needsEndPointParticipant(process,
                            activity,
                            webServiceOperation)) {
                        this.appendAndExecute(getAssignDefaultApiParticipantCommand(editingDomain,
                                process,
                                activity,
                                webServiceOperation));
                    }

                    if (!(Xpdl2ModelUtil.isGeneratedRequestActivity(activity))) {
                        this.appendAndExecute(Xpdl2ModelUtil
                                .getSetOtherAttributeCommand(editingDomain,
                                        taskReceive,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_Generated(),
                                        Boolean.TRUE));
                    }
                }
                /* End of Task Receive processing. */

            } else if (activity.getEvent() != null) {

                /* It's an event . */
                Event event = activity.getEvent();

                TriggerResultMessage trm = null;
                if (event instanceof StartEvent) {
                    trm = ((StartEvent) event).getTriggerResultMessage();
                } else if (event instanceof IntermediateEvent
                        && EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL
                                .equals(EventObjectUtil
                                        .getEventTriggerType(activity))) {
                    trm = ((IntermediateEvent) event).getTriggerResultMessage();
                }

                if (trm != null) {
                    if (!Xpdl2ModelUtil.isGeneratedRequestActivity(activity)) {
                        if (trm.getWebServiceOperation() != null) {
                            return;
                        }
                    }

                    if (ProcessInterfaceUtil.isEventImplemented(activity)) {
                        /*
                         * We need to just add the api endpoint participant to
                         * this.
                         */
                        /*
                         * Assign the endpoint to the defailt api activity
                         * endpoint participant for process.
                         */
                        if (trm.getWebServiceOperation() != null) {
                            if (needsEndPointParticipant(process,
                                    activity,
                                    trm.getWebServiceOperation())) {
                                this.appendAndExecute(getAssignDefaultApiParticipantCommand(editingDomain,
                                        process,
                                        activity,
                                        trm.getWebServiceOperation()));
                            }
                        }

                    } else {

                        /*
                         * It's an request event that is not implementing
                         * interface event or doesn't have a web svc operation
                         * set yet.
                         */

                        /*
                         * Create brand new port type and web service operation
                         * elements
                         */
                        PortTypeOperation portTypeOp =
                                createPortTypeOperation(process, activity);

                        WebServiceOperation webServiceOperation =
                                createWebServiceOperation(process,
                                        trm.getWebServiceOperation());

                        this.appendAndExecute(SetCommand
                                .create(editingDomain,
                                        trm,
                                        Xpdl2Package.eINSTANCE
                                                .getTriggerResultMessage_WebServiceOperation(),
                                        webServiceOperation));
                        this.appendAndExecute(Xpdl2ModelUtil
                                .getSetOtherElementCommand(editingDomain,
                                        trm,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_PortTypeOperation(),
                                        portTypeOp));
                        if (event instanceof StartEvent) {
                            this.appendAndExecute(SetCommand
                                    .create(editingDomain,
                                            event,
                                            Xpdl2Package.eINSTANCE
                                                    .getStartEvent_Implementation(),
                                            ImplementationType.WEB_SERVICE_LITERAL));
                        } else if (event instanceof IntermediateEvent) {
                            this.appendAndExecute(SetCommand
                                    .create(editingDomain,
                                            event,
                                            Xpdl2Package.eINSTANCE
                                                    .getIntermediateEvent_Implementation(),
                                            ImplementationType.WEB_SERVICE_LITERAL));
                        }

                        this.appendAndExecute(Xpdl2ModelUtil
                                .getSetOtherAttributeCommand(editingDomain,
                                        event,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_ImplementationType(),
                                        TaskImplementationTypeDefinitions.WEB_SERVICE));

                        /*
                         * Assign the endpoint to the default api activity
                         * endpoint participant for process.
                         */
                        if (needsEndPointParticipant(process,
                                activity,
                                webServiceOperation)) {
                            this.appendAndExecute(getAssignDefaultApiParticipantCommand(editingDomain,
                                    process,
                                    activity,
                                    webServiceOperation));
                        }

                        if (!(Xpdl2ModelUtil
                                .isGeneratedRequestActivity(activity))) {
                            this.appendAndExecute(Xpdl2ModelUtil
                                    .getSetOtherAttributeCommand(editingDomain,
                                            trm,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_Generated(),
                                            Boolean.TRUE));

                        }
                    }
                }
            }
        }

        return;
    }

    /**
     * @param process
     * @param activity
     * @param webServiceOperation
     * 
     * @return The command to create (if necessary) a system participant for the
     *         given auto-generating process api activity and assign that
     *         participant (if necessary) to the activity. or <b>null</b> if
     *         nothing to do.
     */
    public static boolean needsEndPointParticipant(Process process,
            Activity activity, WebServiceOperation webServiceOperation) {

        /*
         * Check if activity needs a participant assigning.
         */
        Participant endPointPartic = null;
        String endPointParticId =
                (String) Xpdl2ModelUtil.getOtherAttribute(webServiceOperation,
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_Alias());
        if (endPointParticId != null && endPointParticId.length() > 0) {
            endPointPartic = process.getParticipant(endPointParticId);
            if (endPointPartic == null) {
                endPointPartic =
                        process.getPackage().getParticipant(endPointParticId);
            }
        }

        return (endPointPartic == null);
    }

    /**
     * @param editingDomain
     * @param process
     * @param activity
     * @param webServiceOperation
     * 
     * @return Command to assign the default api activity endpoint participant
     *         for the process to the given process api activity (and it's web
     *         service operation).
     */
    public static Command getAssignDefaultApiParticipantCommand(
            EditingDomain editingDomain, Process process, Activity activity,
            WebServiceOperation webServiceOperation) {
        /*
         * None assigned, assign the default process api activity endpoint
         * activity to it.
         */
        CompoundCommand cmd = new CompoundCommand();

        /*
         * Check whether there is a participant already - create if not.
         */
        Participant endPointPartic =
                Xpdl2ModelUtil.getProcessApiActivityParticipant(process);
        if (endPointPartic == null) {
            endPointPartic =
                    assignNewApiEndpointParticpant(editingDomain, process, cmd);
        }

        /*
         * Set participant to activity performer.
         */
        List<EObject> perfs = new ArrayList<EObject>();
        perfs.add(endPointPartic);
        Command perfCommand =
                TaskObjectUtil.getSetPerformersCommand(editingDomain,
                        activity,
                        perfs.toArray(new EObject[0]));
        if (perfCommand != null) {
            cmd.append(perfCommand);
        }

        /*
         * Set Participant in endpoint alias
         */
        cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(editingDomain,
                webServiceOperation,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_Alias(),
                endPointPartic.getId()));

        return cmd;
    }

    /**
     * @param editingDomain
     * @param process
     * @param cmd
     * @return
     */
    public static Participant assignNewApiEndpointParticpant(
            EditingDomain editingDomain, Process process, CompoundCommand cmd) {

        Participant endPointPartic = createNewApiEndpointParticipant(process);

        cmd.append(AddCommand.create(editingDomain,
                process.getPackage(),
                Xpdl2Package.eINSTANCE.getParticipantsContainer_Participants(),
                endPointPartic));

        /*
         * Set the process to reference the new default api activity endpoint
         * participant.
         */
        cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(editingDomain,
                process,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_ApiEndPointParticipant(),
                endPointPartic.getId()));
        return endPointPartic;
    }

    /**
     * @param process
     * @return
     */
    public static Participant createNewApiEndpointParticipant(Process process) {
        /* We need to create one. */
        Participant endPointPartic = Xpdl2Factory.eINSTANCE.createParticipant();

        /*
         * XPD-5911: if a process name has leading digit(s) then prefix with
         * underscore
         */
        String processName = process.getName();
        if (processName != null && Character.isDigit(processName.charAt(0))) {

            processName = "_" + processName; //$NON-NLS-1$
        }
        String particName = getDefaultParticipantNameForProcessApi(processName);
        endPointPartic.setName(particName);

        String particLabel =
                getDefaultParticipantLabelForProcessApi(Xpdl2ModelUtil.getDisplayNameOrName(process),
                        Xpdl2ModelUtil.getDisplayNameOrName(process
                                .getPackage()));

        Xpdl2ModelUtil.setOtherAttribute(endPointPartic,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                particLabel);

        ParticipantTypeElem typeElem =
                Xpdl2Factory.eINSTANCE.createParticipantTypeElem();
        typeElem.setType(ParticipantType.SYSTEM_LITERAL);
        endPointPartic.setParticipantType(typeElem);

        String sharedResourceURI =
                getDefaultSharedResourceURIForProcessApi(process,
                        processName,
                        process.getPackage().getName());

        /* Add the sharedResource extension */
        ParticipantSharedResource sharedRes =
                createWsParticipantSharedResource(sharedResourceURI, particName);

        Xpdl2ModelUtil.setOtherElement(endPointPartic,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_ParticipantSharedResource(),
                sharedRes);
        return endPointPartic;
    }

    /**
     * Creates the model for participant shared resource that is added to the
     * Participant Xpd Extension model.
     * 
     * @param sharedResUriPath
     * @param particName
     * @return
     */
    private static ParticipantSharedResource createWsParticipantSharedResource(
            String sharedResUriPath, String particName) {
        /* Add the sharedResource extension */
        ParticipantSharedResource sharedRes =
                XpdExtensionFactory.eINSTANCE.createParticipantSharedResource();

        WsResource wsResource =
                XpdExtensionFactory.eINSTANCE.createWsResource();
        WsInbound wsInBound =
                XpdExtensionFactory.eINSTANCE.createWsInboundDefault();
        wsInBound.setVirtualBinding(XpdExtensionFactory.eINSTANCE
                .createWsVirtualBindingDefault());

        /*
         * check if the preference page has http or jms default binding
         * selection
         */

        String preferedSoapVersion =
                BpmBindingPreferenceUtil.getSoapVersionPreference().getName();
        if (BpmBindingPreferenceUtil.isHttpBinding()) {

            WsSoapHttpInboundBinding wsSoapHttpInboundBindingDefault = null;
            if (wsInBound.getSoapHttpBinding().size() > 0) {
                wsSoapHttpInboundBindingDefault =
                        wsInBound.getSoapHttpBinding().get(0);

            } else {
                wsSoapHttpInboundBindingDefault =
                        XpdExtensionFactory.eINSTANCE
                                .createWsSoapHttpInboundBindingDefault();
                wsSoapHttpInboundBindingDefault
                        .setSoapVersion(preferedSoapVersion);
                wsInBound.getSoapHttpBinding()
                        .add(wsSoapHttpInboundBindingDefault);
            }
            wsSoapHttpInboundBindingDefault
                    .setEndpointUrlPath(sharedResUriPath);

        } else if (BpmBindingPreferenceUtil.isJmsBinding()) {

            WsSoapJmsInboundBinding wsSoapJmsInboundBindingDefault = null;

            if (wsInBound.getSoapJmsBinding().size() > 0) {
                wsSoapJmsInboundBindingDefault =
                        wsInBound.getSoapJmsBinding().get(0);
            } else {
                wsSoapJmsInboundBindingDefault =
                        XpdExtensionFactory.eINSTANCE
                                .createWsSoapJmsInboundBindingDefault();
                wsSoapJmsInboundBindingDefault
                        .setSoapVersion(preferedSoapVersion);
                wsInBound.getSoapJmsBinding()
                        .add(wsSoapJmsInboundBindingDefault);
            }

            wsSoapJmsInboundBindingDefault
                    .setInboundConnectionFactoryConfiguration(wsSoapJmsInboundBindingDefault
                            .getInboundConnectionFactoryConfiguration());
            wsSoapJmsInboundBindingDefault
                    .setInboundDestination(wsSoapJmsInboundBindingDefault
                            .getInboundDestination());
            wsSoapJmsInboundBindingDefault
                    .setOutboundConnectionFactory(wsSoapJmsInboundBindingDefault
                            .getOutboundConnectionFactory());

        }

        wsResource.setInbound(wsInBound);

        sharedRes.setSharedResource(wsResource);
        return sharedRes;
    }

    /**
     * Creates the PortTypeOperation element for the given activity configured
     * to generate its own web-service operation
     * 
     * @param process
     * @param activity
     * 
     * @return portTypeOp
     */
    public static PortTypeOperation createPortTypeOperation(
            com.tibco.xpd.xpdl2.Process process, Activity activity) {

        PortTypeOperation portTypeOp =
                XpdExtensionFactory.eINSTANCE.createPortTypeOperation();
        /*
         * XPD-5911: if a process name has leading digit(s) then prefix with
         * underscore
         */
        String processName = process.getName();
        if (processName != null && Character.isDigit(processName.charAt(0))) {

            processName = "_" + processName; //$NON-NLS-1$
        }
        portTypeOp.setPortTypeName(processName);

        String activityName = activity.getName();
        /*
         * XPD-5911: if a activity name has leading digit(s) then prefix with
         * underscore
         */
        if (activityName != null && Character.isDigit(activityName.charAt(0))) {

            activityName = "_" + activityName; //$NON-NLS-1$
        }
        if (activityName != null && activityName.trim().length() > 0) {
            portTypeOp.setOperationName(activityName);
        } else {
            activityName = OperationNameUtil.getSubstitutedName(activity);
            portTypeOp.setOperationName(activityName);
        }
        // if activity name is null or empty
        // then call it "Unnamed[ActivityType]Operation"+index
        // store it the map with id against name

        ExternalReference ref =
                Xpdl2Factory.eINSTANCE.createExternalReference();
        ref.setLocation(Xpdl2ModelUtil.getWsdlFileName(WorkingCopyUtil
                .getFile(process)));
        portTypeOp.setExternalReference(ref);
        return portTypeOp;
    }

    /**
     * Creates the WebServiceOperation element for the given business process
     * 
     * @param process
     * 
     * @return webServiceOp
     */
    public static WebServiceOperation createWebServiceOperation(
            com.tibco.xpd.xpdl2.Process process,
            WebServiceOperation oldWebServiceOperation) {
        WebServiceOperation webServiceOp =
                Xpdl2Factory.eINSTANCE.createWebServiceOperation();
        webServiceOp
                .getOtherAttributes()
                .set(XpdExtensionPackage.eINSTANCE.getDocumentRoot_SecurityProfile(),
                        ""); //$NON-NLS-1$
        webServiceOp.getOtherAttributes()
                .set(XpdExtensionPackage.eINSTANCE.getDocumentRoot_Transport(),
                        SERVICE_VIRTUALIZATION_URL);

        webServiceOp.getOtherAttributes()
                .set(XpdExtensionPackage.eINSTANCE.getDocumentRoot_Alias(), ""); //$NON-NLS-1$

        Service service = Xpdl2Factory.eINSTANCE.createService();
        Xpdl2ModelUtil.setOtherAttribute(service,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_IsRemote(),
                true);
        EndPoint endPoint = Xpdl2Factory.eINSTANCE.createEndPoint();
        endPoint.setEndPointType(EndPointTypeType.WSDL_LITERAL);
        ExternalReference wsdlRef =
                Xpdl2Factory.eINSTANCE.createExternalReference();
        wsdlRef.setLocation(Xpdl2ModelUtil.getWsdlFileName(WorkingCopyUtil
                .getFile(process)));
        endPoint.setExternalReference(wsdlRef);
        service.setEndPoint(endPoint);
        webServiceOp.setService(service);

        /* Preserve the current selected participant to new web service op. */
        if (oldWebServiceOperation != null) {
            String endPointParticId =
                    (String) Xpdl2ModelUtil
                            .getOtherAttribute(oldWebServiceOperation,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_Alias());

            if (endPointParticId != null) {
                Xpdl2ModelUtil.setOtherAttribute(webServiceOp,
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_Alias(),
                        endPointParticId);

            }
        }

        return webServiceOp;
    }

    @Override
    public boolean canExecute() {
        return true;
    }

    /**
     * @param process
     * 
     * @return The default shared resource URI for auto-generated process api
     *         activity participant.
     */
    public static String getDefaultSharedResourceNameForProcessApi(
            String processName, String packageName) {
        return packageName + "_" + processName; //$NON-NLS-1$
    }

    /**
     * @param process
     * 
     * @return The default shared resource URI for auto-generated process api
     *         activity participant.
     */
    public static String getDefaultSharedResourceURIForProcessApi(
            Process process, String processName, String packageName) {
        IProject project = WorkingCopyUtil.getProjectFor(process);
        String userPrefURI =
                UserInfoUtil.getProjectPreferences(project).getEndpointURI();
        if (null != userPrefURI && userPrefURI.trim().length() > 0) {
            /**
             * default endpoint uri is '/'. if the user has modified endpoint
             * uri in the preference page then we would consider it for shared
             * resource uri; else we already prefix the shared res uri with '/'
             */
            if (!userPrefURI.equalsIgnoreCase(UserInfoUtil.getDefaultValue()
                    .getEndpointURI())) {
                return userPrefURI + "/" + packageName + "/" + processName; //$NON-NLS-1$ //$NON-NLS-2$ 
            }
        }
        return "/" + packageName + "/" + processName; //$NON-NLS-1$ //$NON-NLS-2$ 
    }

    /**
     * @param process
     * 
     * @return legacy format for the participant name.
     */
    public static String getDefaultParticipantNameForProcessApi(
            String processName, String packageName) {
        return packageName + "_" + processName; //$NON-NLS-1$
    }

    /**
     * @param process
     * 
     * @return current format for the participant name.
     */
    public static String getDefaultParticipantNameForProcessApi(
            String processName) {
        return processName;
    }

    /**
     * @param process
     * 
     * @return The default shared resource URI for auto-generated process api
     *         activity participant.
     */
    public static String getDefaultParticipantLabelForProcessApi(
            String processLabel, String packageLabel) {
        return processLabel + " " + //$NON-NLS-1$
                Messages.AddPortTypeCommand_APIActivityEndPointSuffix_label;
    }

}
