/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.preCommit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskSend;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * The one way Send task mappings generation isn't one that is updated on each
 * command. Also, the mappings needs to be on different occasions than those of
 * the rest of the other activities.
 * 
 * This applies to Throw Intermediate Message Events, and End Message events
 * which are set as One-Way send tasks.
 * 
 * @author rsomayaj
 * 
 */
public class UpdateOneWaySendTaskMappings extends
        UpdateJavaScriptMappingsCommand {

    private final Activity oneWaySendActivity;

    /**
     * Expected a service task only
     * 
     * @param editingDomain
     * @param obj
     */
    public UpdateOneWaySendTaskMappings(EditingDomain editingDomain,
            Activity oneWaySendTaskActivity) {
        super(editingDomain, oneWaySendTaskActivity);
        this.oneWaySendActivity = oneWaySendTaskActivity;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.preCommit.UpdateMappingsCommand#execute()
     * 
     */
    @Override
    public void execute() {
        appendAndExecuteRemoveScriptInformationCommand(oneWaySendActivity);

        /*
         * Update the mappings for the given activity type.
         */
        if (oneWaySendActivity.getEvent() != null) {
            EventTriggerType eventTriggerType =
                    EventObjectUtil.getEventTriggerType(oneWaySendActivity);
            if (EventTriggerType.EVENT_MESSAGE_THROW_LITERAL
                    .equals(eventTriggerType)) {
                TriggerResultMessage triggerResultMessage =
                        EventObjectUtil
                                .getTriggerResultMessage(oneWaySendActivity);
                Message msg = triggerResultMessage.getMessage();
                updateTaskServiceMappings(oneWaySendActivity,
                        triggerResultMessage,
                        msg,
                        Xpdl2Package.eINSTANCE
                                .getTriggerResultMessage_Message());
            }
        } else {
            TaskType taskType = TaskObjectUtil.getTaskType(oneWaySendActivity);
            if (TaskType.SEND_LITERAL.equals(taskType)) {
                TaskSend taskSend =
                        ((Task) oneWaySendActivity.getImplementation())
                                .getTaskSend();
                updateTaskServiceMappings(oneWaySendActivity,
                        taskSend,
                        taskSend.getMessage(),
                        Xpdl2Package.eINSTANCE.getTaskSend_Message());
            }
        }
    }

    /**
     * @param activity
     * @param taskService
     */
    private void updateTaskServiceMappings(Activity activity,
            EObject messageContainer, Message msg,
            EStructuralFeature messageReferenceFeature) {
        Message clearedMessageIn = null;
        // resetting Message In and Message Out
        clearedMessageIn = getClearedMessage(msg);
        this.appendAndExecute(SetCommand.create(getEditingDomain(),
                messageContainer,
                messageReferenceFeature,
                clearedMessageIn));

        List<DataMapping> mappingsForActivity =
                createMappingsForActivity(activity);
        // List<DataMapping> inMappings =
        // Collections.EMPTY_LIST;
        // List<DataMapping> outMappings =
        // Collections.EMPTY_LIST;
        List<DataMapping> inMappings = new ArrayList<DataMapping>();

        for (DataMapping dataMapping : mappingsForActivity) {
            if (DirectionType.IN_LITERAL.equals(dataMapping.getDirection())) {
                inMappings.add(dataMapping);
            }
        }

        if (!inMappings.isEmpty()) {
            this.appendAndExecute(AddCommand.create(getEditingDomain(),
                    clearedMessageIn,
                    Xpdl2Package.eINSTANCE.getMessage_DataMappings(),
                    inMappings));
        }
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.preCommit.UpdateMappingsCommand#createMappingsForActivity(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    protected List<DataMapping> createMappingsForActivity(Activity activity) {
        List<AssociatedParameter> activityAssociatedParameters =
                ProcessInterfaceUtil.getActivityAssociatedParameters(activity);
        List<DataMapping> dataMappings = new ArrayList<DataMapping>();
        if (activityAssociatedParameters.isEmpty()) {
            /*
             * Sid XPD-2087: Only use all data implicitly if implicit
             * association has not been disabled.
             */
            if (!ProcessInterfaceUtil.isImplicitAssociationDisabled(activity)) {
                // create mappings for all formal parameters and process level
                // data
                // fields
                Process process = activity.getProcess();
                ProcessInterface implementedProcessInterface =
                        ProcessInterfaceUtil
                                .getImplementedProcessInterface(process);

                // Process formal parameters
                Collection<FormalParameter> formalParameters =
                        process.getFormalParameters();
                createMappingsForFormalParams(dataMappings,
                        activity,
                        formalParameters);

                if (null != implementedProcessInterface) {
                    formalParameters =
                            implementedProcessInterface.getFormalParameters();
                    createMappingsForFormalParams(dataMappings,
                            activity,
                            formalParameters);
                }

                Collection<DataField> dataFields = process.getDataFields();
                for (DataField df : dataFields) {
                    DataMapping inMapping =
                            createInMapping(activity.getName(),
                                    df.getName(),
                                    ModeType.IN_LITERAL,
                                    DirectionType.IN_LITERAL);
                    if (null != inMapping) {
                        dataMappings.add(inMapping);
                    }
                }
            }
        } else {
            /*
             * Deal with explicit data associations
             */
            for (AssociatedParameter param : activityAssociatedParameters) {
                DataMapping inMapping =
                        createInMapping(activity.getName(),
                                param.getFormalParam(),
                                param.getMode(),
                                DirectionType.IN_LITERAL);
                if (null != inMapping) {
                    dataMappings.add(inMapping);
                }
            }
        }
        return dataMappings;
    }

    /**
     * @param dataMappings
     * @param activity
     * @param formalParameters
     */
    private void createMappingsForFormalParams(List<DataMapping> dataMappings,
            Activity activity, Collection<FormalParameter> formalParameters) {
        for (FormalParameter fp : formalParameters) {
            DataMapping inMapping =
                    createInMapping(activity.getName(),
                            fp.getName(),
                            fp.getMode(),
                            DirectionType.IN_LITERAL);
            if (null != inMapping) {
                dataMappings.add(inMapping);
            }

        }
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.preCommit.UpdateMappingsCommand#createDataMappings(com.tibco.xpd.xpdl2.NamedElement,
     *      java.lang.String, com.tibco.xpd.xpdl2.ModeType)
     * 
     * @param namedElement
     * @param dataMappingName
     * @param modeType
     * @return
     */
    @Override
    protected List<DataMapping> createDataMappings(NamedElement namedElement,
            String dataMappingName, ModeType modeType) {

        List<DataMapping> mapList = new ArrayList<DataMapping>();
        DataMapping inMapping =
                createInMapping(oneWaySendActivity.getName(),
                        dataMappingName,
                        modeType,
                        DirectionType.IN_LITERAL);
        if (inMapping != null) {
            mapList.add(inMapping);
        }

        DataMapping outMapping =
                createOutMapping(oneWaySendActivity.getName(),
                        dataMappingName,
                        modeType,
                        DirectionType.OUT_LITERAL);
        if (outMapping != null) {
            mapList.add(outMapping);
        }
        return mapList;
    }

}