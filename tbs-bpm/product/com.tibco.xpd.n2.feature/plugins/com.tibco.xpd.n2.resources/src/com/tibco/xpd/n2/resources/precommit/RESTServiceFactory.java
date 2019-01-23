/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.n2.resources.precommit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.wsdl.Operation;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.wst.wsdl.Part;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceData;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceDataUtil;
import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.implementer.resources.xpdl2.properties.JavaScriptConceptUtil;
import com.tibco.xpd.implementer.script.ActivityMessageProvider;
import com.tibco.xpd.processeditor.xpdl2.preCommit.AddPortTypeCommand;
import com.tibco.xpd.processeditor.xpdl2.properties.StandardMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.ElementsFactory;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.util.XpdEcoreUtil;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.AsyncExecutionMode;
import com.tibco.xpd.xpdExtension.ParticipantSharedResource;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.SubProcessStartStrategy;
import com.tibco.xpd.xpdExtension.WsOutbound;
import com.tibco.xpd.xpdExtension.WsResource;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdExtension.XpdModelType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.ExecutionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.Length;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantType;
import com.tibco.xpd.xpdl2.ParticipantTypeElem;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskSend;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.RestServiceUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Provides createRestService(activity) method which creates a REST service for
 * the given activity
 * 
 * @author agondal
 * @since 4 Dec 2012
 */
public class RESTServiceFactory {

    public static RESTServiceFactory INSTANCE = new RESTServiceFactory();

    Xpdl2Factory factory = Xpdl2Factory.eINSTANCE;

    private static String InLiteral = "In"; //$NON-NLS-1$

    private static String OutLiteral = "Out"; //$NON-NLS-1$

    /**
     * Creates a REST service element for the given activity
     * 
     * @param activity
     * 
     * @return restService or <code>null</code> if the REST service couldn't be
     *         generated.
     */
    public Process createRestService(Activity activity) {

        String restServiceName = RestServiceUtil.getRestServiceName(activity);
        Process restService = factory.createProcess();

        Xpdl2ModelUtil.setOtherAttribute(restService,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                restServiceName);

        restService.setName(NameUtil.getInternalName(restServiceName, false));

        Xpdl2ModelUtil.setOtherAttribute(restService,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_RestActivityId(),
                activity.getId());

        Xpdl2ModelUtil.setOtherAttribute(restService,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_XpdModelType(),
                XpdModelType.PAGE_FLOW);

        /*
         * create formal parameters -- If it has a user-defined wsdl, create
         * parameters from wsdl part to be used for mappings in the service task
         * otherwise create from activity's associated params
         */

        boolean isGeneratedRequestActivity =
                Xpdl2ModelUtil.isGeneratedRequestActivity(activity);

        /*
         * XPD-7263: We now support publish as rest on start none events as
         * well.
         */
        boolean isStartTypeNoneEvent =
                EventFlowType.FLOW_START_LITERAL.equals(EventObjectUtil
                        .getFlowType(activity))
                        && EventTriggerType.EVENT_NONE_LITERAL
                                .equals(EventObjectUtil
                                        .getEventTriggerType(activity));

        boolean hasOutData = false;

        RESTServiceParamsData paramsData = null;

        String dummyProcessIdParamName = null;

        if (isGeneratedRequestActivity || isStartTypeNoneEvent) {
            /*
             * XPD-7263: If its a generated request activity or if its a start
             * type none event then get all the interface formal params.
             */
            paramsData = new RESTServiceParamsData();

            Collection<ActivityInterfaceData> activityInterfaceData =
                    ActivityInterfaceDataUtil
                            .getActivityInterfaceData(activity);

            for (ActivityInterfaceData interfaceData : activityInterfaceData) {
                /**
                 * XPD-4470: activity may be in bad state where a data field is
                 * explicitly associated. So should not assume is formal
                 * parameter.
                 */
                if (!(interfaceData.getData() instanceof FormalParameter)) {
                    return null;
                }
                if (isStartTypeNoneEvent
                        && ModeType.OUT_LITERAL.equals(interfaceData.getMode())) {
                    /*
                     * XPD-7263: Rest service generated from start none events
                     * should copy the out mode parameters
                     */
                    continue;
                }

                FormalParameter formalParameter =
                        (FormalParameter) EcoreUtil.copy(interfaceData
                                .getData());
                formalParameter.eSet(Xpdl2Package.eINSTANCE
                        .getUniqueIdElement_Id(), XpdEcoreUtil.generateUUID());
                paramsData.paramsList.add(formalParameter);
                if (ModeType.INOUT_LITERAL.equals(interfaceData.getMode())
                        || ModeType.OUT_LITERAL.equals(interfaceData.getMode())) {
                    hasOutData = true;
                }
            }

            if (isStartTypeNoneEvent) {

                /*
                 * XPD-7263: Rest service generated from start none events will
                 * have an additional 'OutputProcessId' OUT mode parameter.
                 */
                dummyProcessIdParamName =
                        addDummyOutputProcessIdFormalParam(paramsData);

            }

        } else {
            paramsData = getFormalParamForRESTfulActivity(activity);
            if (paramsData == null) {
                /*
                 * REST Generation failed due to missing BOM for formal
                 * parameters, so return null
                 */
                return null;
            }
        }

        // add start event for REST service
        Activity startEvent = factory.createActivity();
        startEvent.setName("Start"); //$NON-NLS-1$
        StartEvent event = factory.createStartEvent();
        event.setTrigger(TriggerType.NONE_LITERAL);
        startEvent.setEvent(event);
        restService.getActivities().add(startEvent);

        /*
         * If it's a request-reply activity or has out data then create a
         * service-task otherwise create a send-task for request only activity
         */
        Activity task = null;
        if (isStartTypeNoneEvent) {
            /*
             * XPD-7263: For start type none events create sub process in the
             * REST service.
             */
            task =
                    createSubProcess(activity,
                            paramsData,
                            dummyProcessIdParamName);
        } else {
            // add participant
            Participant participant = createParticipant();
            restService.getParticipants().add(participant);

            if (hasOutData
                    || !ReplyActivityUtil.getReplyActivities(activity)
                            .isEmpty()
                    || ReplyActivityUtil
                            .isStartMessageWithReplyImmediate(activity)) {

                task =
                        createServiceTask(activity,
                                paramsData,
                                participant.getId(),
                                isGeneratedRequestActivity);

            } else {

                task =
                        createSendTask(activity,
                                paramsData,
                                participant.getId());
            }
        }

        if (task == null) {
            // REST Generation Failed
            return null;
        }

        /*
         * Doing this after the task has been created as in case of reply
         * immediate an output param for process id is added to the paramsData
         * list in createServiceTask()
         */

        if (!paramsData.paramsList.isEmpty()) {
            restService.getFormalParameters().addAll(paramsData.paramsList);
        }

        restService.getActivities().add(task);

        // add transition between the start event and send/service task
        Transition transition = factory.createTransition();
        transition.setFrom(startEvent.getId());
        if (task != null) {
            transition.setTo(task.getId());
        }

        // add destinations
        ExtendedAttribute ea;
        for (ExtendedAttribute e : activity.getProcess()
                .getExtendedAttributes()) {
            if (e.getName().equals(DestinationUtil.DESTINATION_ATTRIBUTE)) {
                ea = EcoreUtil.copy(e);
                restService.getExtendedAttributes().add(ea);
            }
        }
        return restService;
    }

    /**
     * Adds a dummy 'OutputProcessId' OUT mode parameter to the
     * {@link RESTServiceParamsData} and returns the '__PROCESS_ID__' which will
     * be mapped to this dummy param.
     * 
     * @param paramsData
     *            the {@link RESTServiceParamsData} to which the dummy output
     *            param should be added
     * @return the '__PROCESS_ID__' which will be mapped to the
     *         'OutputProcessId' OUT mode parameter.
     */
    private String addDummyOutputProcessIdFormalParam(
            RESTServiceParamsData paramsData) {

        /*
         * XPD-7263: Rest service generated from start none events will have an
         * additional 'OutputProcessId' OUT mode parameter. Note the we create a
         * copy of the param because the param created by 'StandardMappingUtil'
         * is static and hence we cannot modify its name because doing that
         * would break everything ;)
         */
        FormalParameter outputProcessIdFormalParam =
                EcoreUtil
                        .copy(StandardMappingUtil.REPLY_IMMEDIATE_PROCESS_ID_FORMALPARAMETER);
        /*
         * extracting the dummy name from StandardMappingUtil parameter so that
         * its consistent incase in future we decide to change it then it will
         * be reflected at all places.
         */
        String dummyProcessIdParamName = outputProcessIdFormalParam.getName();
        /*
         * the name of the Output parameter that will be created
         * 'OutputProcessId'.
         */
        String outputProcessIdParamName = "OutputProcessId"; //$NON-NLS-1$
        /*
         * There might be a chance that the process already has a parameter with
         * the name 'OutputProcessId'; hence get the unique name.
         */
        outputProcessIdParamName =
                Xpdl2ModelUtil.getUniqueNameInSet(outputProcessIdParamName,
                        paramsData.paramsList,
                        false);
        /*
         * set the param name to 'OutputProcessId'
         */
        outputProcessIdFormalParam.setName(outputProcessIdParamName);
        Xpdl2ModelUtil.setOtherAttribute(outputProcessIdFormalParam,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                outputProcessIdParamName);

        /* set the string length to 50 */
        Length len = Xpdl2Factory.eINSTANCE.createLength();
        len.setValue("50"); //$NON-NLS-1$
        ((BasicType) outputProcessIdFormalParam.getDataType()).setLength(len);

        paramsData.paramsList.add(outputProcessIdFormalParam);

        return dummyProcessIdParamName;

    }

    /**
     * Create a Re-usable Sub proc activity for the REST Service
     * 
     * @param activity
     * @param paramsData
     * @param dummyProcessIdParamName
     * @return the created subProcActivity or <code>null</code> if the activity
     *         could not be created.
     */
    private Activity createSubProcess(Activity activity,
            RESTServiceParamsData paramsData, String dummyProcessIdParamName) {

        Process calledProcess = activity.getProcess();

        Activity subProcActivity = factory.createActivity();

        if (subProcActivity == null) {
            /*
             * return null if for some reason the sub proc activity could not be
             * created.
             */
            return null;
        }
        subProcActivity.setName("Call" + calledProcess.getName()); //$NON-NLS-1$

        SubFlow createSubFlow = factory.createSubFlow();

        if (createSubFlow == null) {
            /*
             * return null if for some reason the subFlow could not be created.
             */
            return null;
        }
        createSubFlow.setExecution(ExecutionType.ASYNCHR_LITERAL);
        /* set Id to the called process id */
        createSubFlow.setProcessId(calledProcess.getId());

        /*
         * the sub process should be async detached
         */
        Xpdl2ModelUtil.setOtherAttribute(createSubFlow,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_AsyncExecutionMode(),
                AsyncExecutionMode.DETACHED);

        Xpdl2ModelUtil.setOtherAttribute(createSubFlow,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_StartStrategy(),
                SubProcessStartStrategy.SCHEDULE_START);

        List<DataMapping> dataMappings = new ArrayList<DataMapping>();

        for (FormalParameter formalParameter : paramsData.paramsList) {
            /*
             * Create Data mappings for all the In/In-Out parameters to map
             */
            DataMapping createDataMapping = null;

            if (ModeType.OUT_LITERAL.getLiteral().contains(formalParameter
                    .getMode().getLiteral())) {

                /*
                 * XPD-7339: No need to check
                 * "OUTPUT_PROC_ID_PARAM_NAME.equals(formalParameter.getName())"
                 * because For REST service generated from start none events
                 * there will be ONLY one Single output parameter, i.e.
                 * 'OutputProcessId'
                 */

                /*
                 * Create out mapping from the __Process_Id__ param to
                 * 'OutputProcessId' param
                 */
                createDataMapping =
                        createDataMapping(formalParameter,
                                dummyProcessIdParamName,
                                DirectionType.OUT_LITERAL,
                                false);

            } else {
                /*
                 * For In as well as In-Out mode params the data mapping mode
                 * will be IN only.
                 */
                createDataMapping =
                        createDataMapping(formalParameter,
                                formalParameter.getName(),
                                DirectionType.IN_LITERAL,
                                false);
            }

            if (createDataMapping != null) {

                dataMappings.add(createDataMapping);
            }
        }

        if (!dataMappings.isEmpty()) {
            /*
             * add mappings to the subflow
             */
            createSubFlow.getDataMappings().addAll(dataMappings);
        }

        subProcActivity.setImplementation(createSubFlow);

        return subProcActivity;
    }

    /**
     * Create formal parameters from WSDL parts for a user-defined WSDL
     * 
     * @param activity
     * 
     * @return list of parameters or <code>null</code> if parameters couldn't be
     *         created
     */
    private RESTServiceParamsData getFormalParamForRESTfulActivity(
            Activity activity) {

        boolean paramCreationFailed = false;

        ActivityMessageProvider messageProvider =
                JavaScriptConceptUtil.INSTANCE.getMessageProvider(activity);
        if (messageProvider != null) {
            WebServiceOperation wso =
                    messageProvider.getWebServiceOperation(activity);

            Operation op = JavaScriptConceptUtil.INSTANCE.getWSDLOperation(wso);

            if (wso == null || op == null) {
                return null;
            }

            RESTServiceParamsData paramsData = new RESTServiceParamsData();

            paramsData.inputParts =
                    JavaScriptConceptUtil.INSTANCE.getInputParts(op);
            paramsData.outputParts =
                    JavaScriptConceptUtil.INSTANCE.getOutputParts(op);

            FormalParameter param;
            for (Object p : paramsData.inputParts) {
                if (p instanceof Part) {
                    Part part = (Part) p;
                    param =
                            JavaScriptConceptUtil.INSTANCE
                                    .createFormalParamForPart(part,
                                            ModeType.IN_LITERAL,
                                            false);
                    if (param == null
                            || param.getId()
                                    .equals(JavaScriptConceptUtil.INVALID_PART_PARAMETER_ID)) {
                        /*
                         * Parameter with Dummy ID suggest referenced BOM
                         * element couldn't be found, hence can't create the
                         * parameter
                         */
                        paramCreationFailed = true;
                        break;
                    } else {
                        param.setName(InLiteral + param.getName());
                        paramsData.paramsList.add(param);
                        paramsData.partParameterMap.put(part, param);
                    }
                }
            }

            if (!paramCreationFailed) {
                for (Object p : paramsData.outputParts) {
                    if (p instanceof Part) {
                        Part part = (Part) p;
                        param =
                                JavaScriptConceptUtil.INSTANCE
                                        .createFormalParamForPart(part,
                                                ModeType.OUT_LITERAL,
                                                false);

                        if (param != null) {
                            if (param
                                    .getId()
                                    .equals(JavaScriptConceptUtil.INSTANCE.INVALID_PART_PARAMETER_ID)) {
                                /*
                                 * Parameter with Dummy ID suggest referenced
                                 * BOM element couldn't be found, hence can't
                                 * create the parameter
                                 */
                                paramCreationFailed = true;
                                break;
                            } else {
                                param.setName(OutLiteral + param.getName());
                                paramsData.paramsList.add(param);
                                paramsData.partParameterMap.put(part, param);
                            }
                        }
                    }
                }
            }
            /**
             * only return params if all created successfully
             */
            if (!paramCreationFailed && !paramsData.paramsList.isEmpty()) {
                return paramsData;
            }
        }
        return null;
    }

    /**
     * Create service task activity for the REST service
     * 
     * @param activity
     * @param paramsData
     * @param participantId
     * @param isGeneratedRequestActivity
     * 
     * @return serviceTaskActivity or <code>null</code> if the activity couldn't
     *         be generated
     */
    private Activity createServiceTask(Activity activity,
            RESTServiceParamsData paramsData, String participantId,
            boolean isGeneratedRequestActivity) {

        Activity serviceTaskActivity = factory.createActivity();
        serviceTaskActivity.setName("Call" + activity.getName()); //$NON-NLS-1$

        TaskService taskService = factory.createTaskService();
        taskService.setImplementation(ImplementationType.WEB_SERVICE_LITERAL);

        // create message content (mappings) for service task
        taskService.setMessageIn(createMessageIn(activity, paramsData));

        if (ReplyActivityUtil.isStartMessageWithReplyImmediate(activity)
                && isGeneratedRequestActivity) {
            /*
             * For generating start activity with reply immediate set on we need
             * to create parameter for the return Process Id (there isn't one in
             * the actual business process. As it's visible to the user we'll
             * call it after the generated output Part name ("ProcessId") but
             * make sure it is unique in the real parameter set just in case
             * user has created a parameter "ProcessId" for themselves.
             */
            String processIdParamName =
                    Xpdl2ModelUtil
                            .getUniqueNameInSet(StandardMappingUtil.REPLY_IMMEDIATE_PROCESS_PART_NAME,
                                    paramsData.paramsList,
                                    false);

            /*
             * Take a copy because we don't want to change name of the static
             * process id param!!
             */
            FormalParameter replyImmediateParam =
                    EcoreUtil
                            .copy(StandardMappingUtil.REPLY_IMMEDIATE_PROCESS_ID_FORMALPARAMETER);

            replyImmediateParam.setName(processIdParamName);

            paramsData.paramsList.add(replyImmediateParam);

            Message replyImmediateMsg = factory.createMessage();

            replyImmediateMsg.getDataMappings()

            .add(createDataMapping(replyImmediateParam,
                    StandardMappingUtil.REPLY_IMMEDIATE_PROCESS_PART_NAME,
                    DirectionType.OUT_LITERAL,
                    true));

            taskService.setMessageOut(replyImmediateMsg);

        } else {
            /*
             * If it's NOT a reply immediate (generated) then create the message
             * from either the user defined wsdl output parts OR the associated
             * formal parameters (if its a generating activity).
             */
            taskService.setMessageOut(createMessageOut(activity,
                    paramsData,
                    isGeneratedRequestActivity));
        }

        // add web service operation and port type operation

        WebServiceOperation activityWso =
                Xpdl2ModelUtil.getWebServiceOperation(activity);

        PortTypeOperation activityPo =
                Xpdl2ModelUtil.getPortTypeOperation(activity);

        if (activityWso == null || activityPo == null) {
            return null;
        }

        /*
         * In case of generated request activity, its web service operation and
         * port type operation may be updated after this pre-commit listener, so
         * we create both of these from scratch rather than copying from the
         * request activity
         */

        WebServiceOperation wso;
        PortTypeOperation po;

        if (isGeneratedRequestActivity) {

            wso =
                    AddPortTypeCommand.createWebServiceOperation(activity
                            .getProcess(), null);

            po =
                    AddPortTypeCommand.createPortTypeOperation(activity
                            .getProcess(), activity);

        } else {

            wso = EcoreUtil.copy(activityWso);

            po = EcoreUtil.copy(activityPo);
        }

        Xpdl2ModelUtil.setOtherAttribute(wso,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_Alias(),
                participantId);

        taskService.setWebServiceOperation(wso);

        Xpdl2ModelUtil.addOtherElement(taskService,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_PortTypeOperation(),
                po);

        Task task =
                ElementsFactory
                        .createActivityTaskElement(TaskType.SERVICE_LITERAL);
        task.setTaskService(taskService);

        serviceTaskActivity.setImplementation(task);

        Performer performer = factory.createPerformer();
        performer.setValue(participantId);

        serviceTaskActivity.setPerformer(performer);

        return serviceTaskActivity;
    }

    /**
     * @param activity
     * @param paramsData
     * 
     * @return msg
     */
    private Message createMessageIn(Activity activity,
            RESTServiceParamsData paramsData) {

        Message msg = factory.createMessage();

        if (Xpdl2ModelUtil.isGeneratedRequestActivity(activity)) {
            for (FormalParameter formalParameter : paramsData.paramsList) {

                if (formalParameter.getMode().equals(ModeType.IN_LITERAL)
                        || formalParameter.getMode()
                                .equals(ModeType.INOUT_LITERAL)) {
                    msg.getDataMappings()
                            .add(createDataMapping(formalParameter,
                                    formalParameter.getName(),
                                    DirectionType.IN_LITERAL,
                                    true));
                }
            }
        } else {

            for (Part inputPart : paramsData.inputParts) {
                FormalParameter formalParameter =
                        paramsData.partParameterMap.get(inputPart);

                msg.getDataMappings().add(createDataMapping(formalParameter,
                        inputPart.getName(),
                        DirectionType.IN_LITERAL,
                        true));
            }

        }

        return msg;
    }

    /**
     * @param activity
     * @param paramsData
     * @param isGeneratedRequestActivity
     * 
     * @return msg
     */
    private Message createMessageOut(Activity activity,
            RESTServiceParamsData paramsData, boolean isGeneratedRequestActivity) {

        Message msg = factory.createMessage();

        if (isGeneratedRequestActivity) {
            for (FormalParameter formalParameter : paramsData.paramsList) {

                if (formalParameter.getMode().equals(ModeType.OUT_LITERAL)
                        || formalParameter.getMode()
                                .equals(ModeType.INOUT_LITERAL)) {
                    msg.getDataMappings()
                            .add(createDataMapping(formalParameter,
                                    formalParameter.getName(),
                                    DirectionType.OUT_LITERAL,
                                    true));
                }
            }
        } else {

            for (Part outputPart : paramsData.outputParts) {
                FormalParameter formalParameter =
                        paramsData.partParameterMap.get(outputPart);
                msg.getDataMappings().add(createDataMapping(formalParameter,
                        outputPart.getName(),
                        DirectionType.OUT_LITERAL,
                        true));
            }
        }
        return msg;
    }

    /**
     * Create data mappings using the given parameter and part
     * 
     * @param formalParameter
     *            the formal param for which the mapping should be created.
     * @param mappingFormalParam
     *            the data mapping formal parameter
     * @param directionLiteral
     *            the direction of the mapping
     * @param shouldAddPrimitivePropertyInfo
     *            pass true if we wish to add the primitive property info to the
     *            data mapping.
     * 
     * @return dataMapping
     */
    private DataMapping createDataMapping(FormalParameter formalParameter,
            String mappingFormalParam, DirectionType directionLiteral,
            boolean shouldAddPrimitivePropertyInfo) {

        DataMapping dataMapping = factory.createDataMapping();

        dataMapping.setDirection(directionLiteral);
        dataMapping.setFormal(mappingFormalParam);

        if (shouldAddPrimitivePropertyInfo) {
            /*
             * XPD-7263: Add Primitive type property info only if necessary.
             */
            boolean primitiveProperty = false;
            if (formalParameter.getDataType() instanceof ExternalReference) {
                primitiveProperty = false;

            } else {
                primitiveProperty = true;

            }

            if (directionLiteral.equals(DirectionType.OUT_LITERAL)) {

                Xpdl2ModelUtil.setOtherAttribute(dataMapping,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_TargetPrimitiveProperty(),
                        primitiveProperty);

            } else {

                Xpdl2ModelUtil.setOtherAttribute(dataMapping,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_SourcePrimitiveProperty(),
                        primitiveProperty);
            }
        }

        Expression expression =
                Xpdl2Factory.eINSTANCE.createExpression(formalParameter
                        .getName());
        expression.setScriptGrammar(ScriptGrammarFactory.JAVASCRIPT);
        dataMapping.setActual(expression);

        return dataMapping;
    }

    /**
     * Create a Send Task activity for the REST Service
     * 
     * @param activity
     * @param paramsData
     * @param participantId
     * 
     * @return sendTaskActivity or <code>null</code> if the activity couldn't be
     *         generated
     * 
     */
    private Activity createSendTask(Activity activity,
            RESTServiceParamsData paramsData, String participantId) {

        Activity sendTaskActivity = factory.createActivity();
        sendTaskActivity.setName("Call" + activity.getName()); //$NON-NLS-1$

        TaskSend taskSend = factory.createTaskSend();
        taskSend.setImplementation(ImplementationType.WEB_SERVICE_LITERAL);

        taskSend.setMessage(createMessageIn(activity, paramsData));

        // add web service operation and portType operation

        WebServiceOperation activityWso =
                Xpdl2ModelUtil.getWebServiceOperation(activity);
        PortTypeOperation activityPo =
                Xpdl2ModelUtil.getPortTypeOperation(activity);
        if (activityWso == null || activityPo == null) {

            return null;
        }

        /*
         * In case of generated request activity, its web service operation and
         * port type operation may be updated after this pre-commit listener, so
         * we create both of these from scratch rather than copying from the
         * request activity
         */

        WebServiceOperation wso;
        PortTypeOperation po;

        if (Xpdl2ModelUtil.isGeneratedRequestActivity(activity)) {

            wso =
                    AddPortTypeCommand.createWebServiceOperation(activity
                            .getProcess(), null);

            po =
                    AddPortTypeCommand.createPortTypeOperation(activity
                            .getProcess(), activity);

        } else {

            wso = EcoreUtil.copy(activityWso);

            po = EcoreUtil.copy(activityPo);
        }

        Xpdl2ModelUtil.setOtherAttribute(wso,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_Alias(),
                participantId);

        taskSend.setWebServiceOperation(wso);

        Xpdl2ModelUtil.addOtherElement(taskSend, XpdExtensionPackage.eINSTANCE
                .getDocumentRoot_PortTypeOperation(), po);

        Task task =
                ElementsFactory
                        .createActivityTaskElement(TaskType.SEND_LITERAL);
        task.setTaskSend(taskSend);

        sendTaskActivity.setImplementation(task);

        Performer performer = factory.createPerformer();
        performer.setValue(participantId);

        sendTaskActivity.setPerformer(performer);

        return sendTaskActivity;
    }

    /**
     * Create a system participant
     * 
     * @param restServiceName
     * @return participant
     */
    private Participant createParticipant() {

        XpdExtensionFactory xFact = XpdExtensionFactory.eINSTANCE;

        Participant participant = factory.createParticipant();

        String participantName = "Partic" + participant.getId(); //$NON-NLS-1$
        participantName = NameUtil.getInternalName(participantName, false);
        participant.setName(participantName);

        ParticipantTypeElem pe = factory.createParticipantTypeElem();
        pe.setType(ParticipantType.SYSTEM_LITERAL);
        participant.setParticipantType(pe);

        ParticipantSharedResource sharedRes =
                xFact.createParticipantSharedResource();
        WsResource wsResource = xFact.createWsResource();
        WsOutbound wsOutBound = xFact.createWsOutboundDefault();
        wsOutBound.setVirtualBinding(xFact.createWsVirtualBindingDefault());

        wsResource.setOutbound(wsOutBound);
        sharedRes.setSharedResource(wsResource);
        Xpdl2ModelUtil.addOtherElement(participant,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_ParticipantSharedResource(),
                sharedRes);

        return participant;
    }

    /**
     * Just a small data class for transporting info about parameters and
     * message parts.
     * 
     * 
     * @author agondal
     * @since 4 Jan 2013
     */
    private static class RESTServiceParamsData {
        Map<Part, FormalParameter> partParameterMap =
                new HashMap<Part, FormalParameter>();

        Collection<Part> inputParts = Collections.emptyList();

        Collection<Part> outputParts = Collections.emptyList();

        Collection<FormalParameter> paramsList =
                new ArrayList<FormalParameter>();
    }
}
