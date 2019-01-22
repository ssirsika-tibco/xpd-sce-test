/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.preCommit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.CopyCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDestinationUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.ErrorMethod;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.ResultType;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskReceive;
import com.tibco.xpd.xpdl2.TaskSend;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.extension.EMFSearchUtil;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.ThrowErrorEventUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Command to add mappings to process API activities or process interface
 * events.
 * 
 * For convenience, this command also accepts Process or process interface as
 * parameters, and adds mappings to the activities and interface events.
 * 
 * @author rsomayaj
 * 
 */
public abstract class UpdateMappingsCommand extends CompoundCommand {

    private EditingDomain editingDomain;

    private EObject eObject;

    public UpdateMappingsCommand(EditingDomain editingDomain, EObject eObject) {
        this.editingDomain = editingDomain;
        this.eObject = eObject;
    }

    /**
     * @see org.eclipse.emf.common.command.CompoundCommand#execute()
     * 
     */
    @Override
    @SuppressWarnings("unchecked")
    public void execute() {

        List<EObject> objsReqMappings = new ArrayList<EObject>();
        if (eObject instanceof ProcessInterface) {
            ProcessInterface procIfc = (ProcessInterface) eObject;
            if (!ProcessDestinationUtil
                    .shouldGenerateWSDLForProcessInterfaceDestinations(procIfc)) {
                return;
            }
            objsReqMappings
                    .addAll(EMFSearchUtil.findManyInList(procIfc
                            .getStartMethods(),
                            XpdExtensionPackage.eINSTANCE
                                    .getInterfaceMethod_Trigger(),
                            TriggerType.MESSAGE_LITERAL));
            objsReqMappings
                    .addAll(EMFSearchUtil.findManyInList(procIfc
                            .getIntermediateMethods(),
                            XpdExtensionPackage.eINSTANCE
                                    .getInterfaceMethod_Trigger(),
                            TriggerType.MESSAGE_LITERAL));
        } else if (eObject instanceof Process) {
            Process process = (Process) eObject;
            if (!ProcessDestinationUtil
                    .shouldGenerateWSDLForProcessDestinations(process)) {
                return;
            }
            Collection<Activity> activitiesInProc =
                    Xpdl2ModelUtil.getAllActivitiesInProc(process);
            for (Activity activity : activitiesInProc) {
                if (Xpdl2ModelUtil.isGeneratedRequestActivity(activity)
                        && activity.getName() != null) {
                    objsReqMappings.add(activity);

                } else if (ReplyActivityUtil.isReplyActivity(activity)) {
                    Activity requestActivityForReplyActivity =
                            ReplyActivityUtil
                                    .getRequestActivityForReplyActivity(activity);

                    if (requestActivityForReplyActivity != null
                            && requestActivityForReplyActivity.getName() != null
                            && Xpdl2ModelUtil
                                    .isGeneratedRequestActivity(requestActivityForReplyActivity)) {
                        objsReqMappings.add(activity);
                    }

                } else if (ThrowErrorEventUtil
                        .shouldGenerateMappingsForErrorEvent(activity)) {
                    Activity reqestActvity =
                            ThrowErrorEventUtil.getRequestActivity(activity);
                    if (reqestActvity != null
                            && reqestActvity.getName() != null) {
                        objsReqMappings.add(activity);
                    }
                }
            }

        } else if (eObject instanceof Activity) {
            Activity activity = (Activity) eObject;
            Process process = Xpdl2ModelUtil.getProcess(activity);
            if (!ProcessDestinationUtil
                    .shouldGenerateWSDLForProcessDestinations(process)) {
                return;
            }

            /*
             * Should not run for request activities (or their replies) that are
             * not generated
             */
            if (ReplyActivityUtil.isReplyActivity(activity)) {
                Activity requestActivity =
                        ReplyActivityUtil
                                .getRequestActivityForReplyActivity(activity);
                if (!Xpdl2ModelUtil.isGeneratedRequestActivity(requestActivity)) {
                    return;
                }
            } else if (ThrowErrorEventUtil
                    .shouldGenerateMappingsForErrorEvent(activity)) {
                Activity reqestActvity =
                        ThrowErrorEventUtil.getRequestActivity(activity);
                if (reqestActvity != null && reqestActvity.getName() != null) {
                    objsReqMappings.add(activity);
                }
            } else {
                if (!Xpdl2ModelUtil.isGeneratedRequestActivity(activity)) {
                    return;
                }
            }

            objsReqMappings.add(activity);

        } else if (eObject instanceof InterfaceMethod) {
            InterfaceMethod interfaceMethod = (InterfaceMethod) eObject;
            ProcessInterface processInterface =
                    ProcessInterfaceUtil.getProcessInterface(interfaceMethod);
            if (!ProcessDestinationUtil
                    .shouldGenerateWSDLForProcessInterfaceDestinations(processInterface)) {
                return;
            }
            objsReqMappings.add(interfaceMethod);
        }

        /*
         * Having ascertained the objects that require mapping updates, do so.
         */
        for (EObject dataMappingContainer : objsReqMappings) {
            if (dataMappingContainer instanceof InterfaceMethod) {
                InterfaceMethod interfaceMethod =
                        (InterfaceMethod) dataMappingContainer;
                updateInterfaceMethodMappings(interfaceMethod);

            } else if (dataMappingContainer instanceof Activity) {
                Activity activity = (Activity) dataMappingContainer;

                appendAndExecuteRemoveScriptInformationCommand(activity);

                /*
                 * Update the mappings for the given activity type.
                 */
                TaskType taskType = TaskObjectUtil.getTaskType(activity);
                if (TaskType.RECEIVE_LITERAL.equals(taskType)) {
                    TaskReceive taskReceive =
                            ((Task) activity.getImplementation())
                                    .getTaskReceive();

                    updateReceiveTaskMappings(activity, taskReceive);

                } else if (TaskType.SEND_LITERAL.equals(taskType)) {
                    TaskSend taskSend =
                            ((Task) activity.getImplementation()).getTaskSend();
                    updateSendTaskMappings(activity, taskSend);

                } else {
                    EventFlowType flowType =
                            EventObjectUtil.getFlowType(activity);
                    if (EventFlowType.FLOW_START_LITERAL.equals(flowType)) {
                        StartEvent startEvent =
                                (StartEvent) activity.getEvent();
                        updateStartEventMappings(activity, startEvent);

                    } else if (EventFlowType.FLOW_INTERMEDIATE_LITERAL
                            .equals(flowType)) {
                        IntermediateEvent intermediateEvent =
                                (IntermediateEvent) activity.getEvent();
                        updateIntermediateEventMappings(activity,
                                intermediateEvent);

                    } else if (EventFlowType.FLOW_END_LITERAL.equals(flowType)) {
                        EndEvent endEvent = (EndEvent) activity.getEvent();
                        updateEndEventMappings(activity, endEvent);
                    }
                }
            }
        }

        return;
    }

    /**
     * @param activity
     */
    protected void appendAndExecuteRemoveScriptInformationCommand(
            Activity activity) {
        /*
         * When activity was previously not having mappings generated there may
         * be a xpdExt:ScriptInformation with wrong script grammar in (if user
         * had changed grammar before setting activity to auto-gen)
         * 
         * This will override the actual grammar in the mappings we create with
         * the correct grammar and mess up the mapper sections. So remove it.
         */
        Object scriptInformation =
                Xpdl2ModelUtil.getOtherElement(activity,
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_Script());
        if (scriptInformation instanceof ScriptInformation) {
            this.appendAndExecute(Xpdl2ModelUtil
                    .getRemoveOtherElementCommand(editingDomain,
                            activity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_Script(),
                            scriptInformation));
        }
    }

    /**
     * @param activity
     * @param endEvent
     */
    private void updateEndEventMappings(Activity activity, EndEvent endEvent) {
        if (ResultType.MESSAGE_LITERAL.equals(endEvent.getResult())) {
            if (endEvent.getTriggerResultMessage() != null
                    && endEvent.getTriggerResultMessage().getMessage() != null) {
                Message clearedMessage =
                        getClearedMessage(endEvent.getTriggerResultMessage()
                                .getMessage());

                this.appendAndExecute(SetCommand.create(editingDomain, endEvent
                        .getTriggerResultMessage(), Xpdl2Package.eINSTANCE
                        .getTriggerResultMessage_Message(), clearedMessage));

                List<DataMapping> dataMappings =
                        createMappingsForActivity(activity);

                this.appendAndExecute(AddCommand.create(editingDomain,
                        clearedMessage,
                        Xpdl2Package.eINSTANCE.getMessage_DataMappings(),
                        dataMappings));
            }

        } else if (ResultType.ERROR_LITERAL.equals(endEvent.getResult())) {
            Message faultMessage =
                    ThrowErrorEventUtil.getThrowErrorFaultMessage(activity);
            if (faultMessage == null) {
                faultMessage =
                        ThrowErrorEventUtil
                                .createBaseFaultMessage(ThrowErrorEventUtil
                                        .getThrowErrorCode(activity));
            }

            if (faultMessage != null) {
                Message newMessage = getClearedMessage(faultMessage);

                List<DataMapping> dataMappings =
                        createMappingsForActivity(activity);
                if (!dataMappings.isEmpty()) {
                    newMessage.getDataMappings().addAll(dataMappings);
                }

                this.appendAndExecute(ThrowErrorEventUtil
                        .getResetFaultMessageCommand(editingDomain,
                                activity,
                                newMessage));

            }
        }

        return;
    }

    /**
     * @param activity
     * @param intermediateEvent
     */
    private void updateIntermediateEventMappings(Activity activity,
            IntermediateEvent intermediateEvent) {
        if (TriggerType.MESSAGE_LITERAL.equals(intermediateEvent.getTrigger())) {
            if (intermediateEvent.getTriggerResultMessage() != null
                    && intermediateEvent.getTriggerResultMessage().getMessage() != null) {
                Message clearedMessage =
                        getClearedMessage(intermediateEvent
                                .getTriggerResultMessage().getMessage());
                this.appendAndExecute(SetCommand.create(editingDomain,
                        intermediateEvent.getTriggerResultMessage(),
                        Xpdl2Package.eINSTANCE
                                .getTriggerResultMessage_Message(),
                        clearedMessage));
                List<DataMapping> dataMappings =
                        createMappingsForActivity(activity);
                this.appendAndExecute(AddCommand.create(editingDomain,
                        clearedMessage,
                        Xpdl2Package.eINSTANCE.getMessage_DataMappings(),
                        dataMappings));
            }
        }
    }

    /**
     * @param activity
     * @param startEvent
     */
    private void updateStartEventMappings(Activity activity,
            StartEvent startEvent) {
        if (TriggerType.MESSAGE_LITERAL.equals(startEvent.getTrigger())) {
            if (startEvent.getTriggerResultMessage() != null
                    && startEvent.getTriggerResultMessage().getMessage() != null) {
                Message clearedMessage =
                        getClearedMessage(startEvent.getTriggerResultMessage()
                                .getMessage());
                this.appendAndExecute(SetCommand.create(editingDomain,
                        startEvent.getTriggerResultMessage(),
                        Xpdl2Package.eINSTANCE
                                .getTriggerResultMessage_Message(),
                        clearedMessage));
                List<DataMapping> dataMappings =
                        createMappingsForActivity(activity);
                this.appendAndExecute(AddCommand.create(editingDomain,
                        clearedMessage,
                        Xpdl2Package.eINSTANCE.getMessage_DataMappings(),
                        dataMappings));
            }

        }
    }

    /**
     * @param activity
     * @param taskSend
     */
    private void updateSendTaskMappings(Activity activity, TaskSend taskSend) {
        if (taskSend.getMessage() != null) {
            Message clearedMessage = getClearedMessage(taskSend.getMessage());
            this.appendAndExecute(SetCommand.create(editingDomain,
                    taskSend,
                    Xpdl2Package.eINSTANCE.getTaskSend_Message(),
                    clearedMessage));
            List<DataMapping> mappingsForActivity =
                    createMappingsForActivity(activity);
            this.appendAndExecute(AddCommand.create(editingDomain,
                    clearedMessage,
                    Xpdl2Package.eINSTANCE.getMessage_DataMappings(),
                    mappingsForActivity));
        }
    }

    /**
     * @param activity
     * @param taskReceive
     */
    private void updateReceiveTaskMappings(Activity activity,
            TaskReceive taskReceive) {
        if (taskReceive.getMessage() != null) {
            Message clearedMessage =
                    getClearedMessage(taskReceive.getMessage());
            this.appendAndExecute(SetCommand.create(editingDomain,
                    taskReceive,
                    Xpdl2Package.eINSTANCE.getTaskReceive_Message(),
                    clearedMessage));

            List<DataMapping> mappingsForActivity =
                    createMappingsForActivity(activity);
            this.appendAndExecute(AddCommand.create(editingDomain,
                    clearedMessage,
                    Xpdl2Package.eINSTANCE.getMessage_DataMappings(),
                    mappingsForActivity));
        }
    }

    /**
     * @param interfaceMethod
     */
    private void updateInterfaceMethodMappings(InterfaceMethod interfaceMethod) {
        if (TriggerType.MESSAGE_LITERAL.equals(interfaceMethod.getTrigger())) {
            if (interfaceMethod.getTriggerResultMessage() != null
                    && interfaceMethod.getTriggerResultMessage().getMessage() != null) {
                Message clearedMessage =
                        getClearedMessage(interfaceMethod
                                .getTriggerResultMessage().getMessage());
                this.appendAndExecute(SetCommand.create(editingDomain,
                        interfaceMethod.getTriggerResultMessage(),
                        Xpdl2Package.eINSTANCE
                                .getTriggerResultMessage_Message(),
                        clearedMessage));
                List<DataMapping> dataMappings = Collections.EMPTY_LIST;
                if (!(interfaceMethod.getAssociatedParameters().isEmpty())) {
                    List<AssociatedParameter> associatedParameters =
                            interfaceMethod.getAssociatedParameters();

                    dataMappings =
                            createMappingsForAssocParams(interfaceMethod,
                                    associatedParameters);
                } else {
                    /*
                     * Sid XPD-2087: Only use all data implicitly if implicit
                     * association has not been disabled.
                     */
                    if (!ProcessInterfaceUtil
                            .isImplicitAssociationDisabled(interfaceMethod)) {
                        List<FormalParameter> formalParams =
                                ((ProcessInterface) interfaceMethod
                                        .eContainer()).getFormalParameters();

                        dataMappings =
                                createMappingsForFormalParams(interfaceMethod,
                                        formalParams);
                    }

                }
                this.appendAndExecute(AddCommand.create(editingDomain,
                        clearedMessage,
                        Xpdl2Package.eINSTANCE.getMessage_DataMappings(),
                        dataMappings));

            }
        }
    }

    /**
     * @param activity
     * @param mapList
     */
    protected List<DataMapping> createMappingsForActivity(Activity activity) {
        List<DataMapping> dataMappings = Collections.EMPTY_LIST;

        Activity assocParamActivity = null;

        if (Xpdl2ModelUtil.isGeneratedRequestActivity(activity)) {
            assocParamActivity = activity;
        } else if (ReplyActivityUtil.isReplyActivity(activity)) {
            Activity requestActivityForReplyActivity =
                    ReplyActivityUtil
                            .getRequestActivityForReplyActivity(activity);
            if (requestActivityForReplyActivity != null) {
                assocParamActivity = requestActivityForReplyActivity;
            }
        } else if (ThrowErrorEventUtil
                .shouldGenerateMappingsForErrorEvent(activity)) {
            assocParamActivity = activity;
        } else if (TaskType.SERVICE_LITERAL.equals(TaskObjectUtil
                .getTaskType(activity))) {
            assocParamActivity = activity;
        }

        if (assocParamActivity != null) {
            /*
             * No explicitly associated data, hanle implicit association...
             */
            List<AssociatedParameter> associatedParameters =
                    ProcessInterfaceUtil
                            .getActivityAssociatedParameters(assocParamActivity);
            if (!(associatedParameters.isEmpty())) {
                dataMappings =
                        createMappingsForAssocParams(activity,
                                associatedParameters);
            } else {
                List<FormalParameter> formalParams = Collections.EMPTY_LIST;

                if (ProcessInterfaceUtil.isEventImplemented(activity)) {
                    InterfaceMethod implementedMethod =
                            ProcessInterfaceUtil.getImplementedMethod(activity);
                    if (implementedMethod != null) {
                        ProcessInterface processInterface =
                                ProcessInterfaceUtil
                                        .getProcessInterface(implementedMethod);
                        if (processInterface != null) {
                            /*
                             * Sid XPD-2087: Only return all data implicitly if
                             * implicit association has not been disabled.
                             */
                            if (!ProcessInterfaceUtil
                                    .isImplicitAssociationDisabled(implementedMethod)) {
                                formalParams =
                                        processInterface.getFormalParameters();
                            }
                        }

                    } else {
                        ErrorMethod implementedErrorMethod =
                                ProcessInterfaceUtil
                                        .getImplementedErrorMethod(activity);
                        if (implementedErrorMethod != null) {
                            ProcessInterface processInterface =
                                    ProcessInterfaceUtil
                                            .getProcessInterface(implementedErrorMethod);
                            if (processInterface != null) {
                                /*
                                 * Sid XPD-2087: Only return all data implicitly
                                 * if implicit association has not been
                                 * disabled.
                                 */
                                if (!ProcessInterfaceUtil
                                        .isImplicitAssociationDisabled(implementedErrorMethod)) {
                                    formalParams =
                                            processInterface
                                                    .getFormalParameters();
                                }
                            }
                        }
                    }

                } else {
                    /*
                     * Sid XPD-2087: Only return all data implicitly if implicit
                     * association has not been disabled.
                     */
                    if (!ProcessInterfaceUtil
                            .isImplicitAssociationDisabled(assocParamActivity)) {
                        formalParams =
                                ProcessInterfaceUtil
                                        .getAllFormalParameters(activity
                                                .getProcess());
                    }
                }

                dataMappings =
                        createMappingsForFormalParams(activity, formalParams);
            }
        }
        return dataMappings;
    }

    /**
     * @param interfaceMethod
     * @param mapList
     * @param formalParams
     */
    private List<DataMapping> createMappingsForFormalParams(
            NamedElement namedElement, List<FormalParameter> formalParams) {

        List<DataMapping> dataMappings = new ArrayList<DataMapping>();
        for (FormalParameter param : formalParams) {
            if (isParamUnresolvedExternalReference(param)) {
                continue;
            }
            dataMappings.addAll(createDataMappings(namedElement,
                    param.getName(),
                    param.getMode()));
        }

        return dataMappings;
    }

    /**
     * @param param
     * @return
     */
    private boolean isParamUnresolvedExternalReference(FormalParameter param) {
        DataType dataType = param.getDataType();
        if (dataType instanceof ExternalReference) {
            ExternalReference externalReference = (ExternalReference) dataType;
            return ProcessUIUtil
                    .isUnresolvedExternalReference(externalReference);
        }
        return false;
    }

    /**
     * @param interfaceMethod
     * @param mapList
     * @param associatedParameters
     */
    private List<DataMapping> createMappingsForAssocParams(
            NamedElement namedElement,
            List<AssociatedParameter> associatedParameters) {

        if (associatedParameters.isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        List<DataMapping> dataMappings = new ArrayList<DataMapping>();
        for (AssociatedParameter associatedParameter : associatedParameters) {
            ModeType modeType =
                    ProcessInterfaceUtil
                            .getAssocParamModeType(associatedParameter);
            ProcessRelevantData processRelevantData =
                    ProcessInterfaceUtil
                            .getProcessRelevantDataFromAssociatedParam(associatedParameter);

            if (modeType != null
                    && processRelevantData instanceof FormalParameter) {
                FormalParameter formalParameter =
                        (FormalParameter) processRelevantData;
                if (isParamUnresolvedExternalReference(formalParameter)) {
                    continue;
                }
                dataMappings.addAll(createDataMappings(namedElement,
                        associatedParameter.getFormalParam(),
                        modeType));
            }
        }
        return dataMappings;
    }

    /**
     * @param msg
     * @return
     */
    protected Message getClearedMessage(Message msg) {
        Command cpyCmd = CopyCommand.create(editingDomain, msg);
        cpyCmd.execute();
        Message newMessage = (Message) cpyCmd.getResult().iterator().next();

        // Clear out the old mappings from the copy
        if (!(newMessage.getDataMappings().isEmpty())) {
            newMessage.getDataMappings().clear();
        }
        return newMessage;
    }

    /**
     * @param eObj
     * @param formalParam
     * @param modeType
     * @return
     */
    protected List<DataMapping> createDataMappings(NamedElement namedElement,
            String dataMappingName, ModeType modeType) {
        List<DataMapping> mapList = new ArrayList<DataMapping>();
        if (namedElement instanceof InterfaceMethod) {
            DataMapping inMapping =
                    createInMapping(namedElement.getName(),
                            dataMappingName,
                            modeType,
                            DirectionType.OUT_LITERAL);
            if (inMapping != null) {
                mapList.add(inMapping);
            }
            DataMapping outMapping =
                    createOutMapping(namedElement.getName(),
                            dataMappingName,
                            modeType,
                            DirectionType.IN_LITERAL);
            if (outMapping != null) {
                mapList.add(outMapping);
            }

        } else {
            if (namedElement instanceof Activity) {
                Activity activity = (Activity) namedElement;
                if (Xpdl2ModelUtil.isGeneratedRequestActivity(activity)) {
                    DataMapping inMapping =
                            createInMapping(activity.getName(),
                                    dataMappingName,
                                    modeType,
                                    DirectionType.OUT_LITERAL);
                    if (inMapping != null) {
                        mapList.add(inMapping);
                    }
                } else if (ReplyActivityUtil.isReplyActivity(activity)) {
                    Activity requestActivityForReplyActivity =
                            ReplyActivityUtil
                                    .getRequestActivityForReplyActivity(activity);
                    if (requestActivityForReplyActivity != null
                            && Xpdl2ModelUtil
                                    .isGeneratedRequestActivity(requestActivityForReplyActivity)) {
                        DataMapping outMapping =
                                createOutMapping(requestActivityForReplyActivity
                                        .getName(),
                                        dataMappingName,
                                        modeType,
                                        DirectionType.IN_LITERAL);
                        if (outMapping != null) {
                            mapList.add(outMapping);
                        }
                    }

                } else if (ThrowErrorEventUtil
                        .shouldGenerateMappingsForErrorEvent(activity)) {
                    Activity requestActivity =
                            ThrowErrorEventUtil.getRequestActivity(activity);
                    if (requestActivity != null) {
                        DataMapping outMapping =
                                createOutMapping(requestActivity.getName(),
                                        dataMappingName,
                                        modeType,
                                        DirectionType.IN_LITERAL);
                        if (outMapping != null) {
                            mapList.add(outMapping);
                        }
                    }

                }
            }
        }

        return mapList;
    }

    /**
     * @param eObj
     * @param dataMappingName
     * @param modeType
     * @param mapList
     */
    protected DataMapping createOutMapping(String activityName,
            String dataMappingName, ModeType modeType,
            DirectionType directionType) {
        if (modeType == ModeType.OUT_LITERAL
                || modeType == ModeType.INOUT_LITERAL) {
            DataMapping dataMapping =
                    Xpdl2Factory.eINSTANCE.createDataMapping();

            String formalString =
                    getFormalString(activityName, dataMappingName);
            dataMapping.setFormal(formalString);
            Expression expression =
                    Xpdl2ModelUtil.createExpression(dataMappingName);
            expression.setScriptGrammar(getScriptGrammar());
            dataMapping.setActual(expression);
            dataMapping.setDirection(directionType);
            return dataMapping;
        }
        return null;
    }

    /**
     * @param eObj
     * @param dataMappingName
     * @param modeType
     * @param mapList
     */
    protected DataMapping createInMapping(String name, String dataMappingName,
            ModeType modeType, DirectionType directionType) {
        if (ModeType.IN_LITERAL.equals(modeType)
                || ModeType.INOUT_LITERAL.equals(modeType)) {
            DataMapping dataMapping =
                    Xpdl2Factory.eINSTANCE.createDataMapping();

            String formalString = getFormalString(name, dataMappingName);
            dataMapping.setFormal(formalString);
            Expression expression =
                    Xpdl2ModelUtil.createExpression(dataMappingName);
            expression.setScriptGrammar(getScriptGrammar());
            dataMapping.setActual(expression);
            dataMapping.setDirection(directionType);
            return dataMapping;
        }
        return null;
    }

    /**
     * @param operationName
     * @param dataMappingName
     * @return
     */
    protected abstract String getFormalString(String operationName,
            String dataMappingName);

    @Override
    public boolean canExecute() {
        return true;
    }

    protected abstract String getScriptGrammar();

    /**
     * @return the editingDomain
     */
    public EditingDomain getEditingDomain() {
        return editingDomain;
    }

    /**
     * @return the mappingObject
     */
    public EObject getMappingObject() {
        return eObject;
    }
}
