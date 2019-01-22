/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.developer.rules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.mapper.MapperContentProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.CatchThrow;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.ResultType;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskReceive;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author Miguel Torres
 */
public class MessageEventParametersRule extends ProcessValidationRule {

    /** Wrong mode issue id. */
    private static final String WRONG_MODE = "bpmn.dev.mappingModeError"; //$NON-NLS-1$

    /** Data Field Mapping disallowed */
    private static final String DATAFIELD_MAPPING_DISALLOWED =
            "bpmn.dev.datafieldMappingError"; //$NON-NLS-1$

    private static final String PARAM_MAP_MODE_UNCONFIRM =
            "bpmn.dev.mapModeUndecidedWarning"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validate(com.tibco.xpd.xpdl2.Process)
     * 
     * @param process
     */
    @Override
    public void validate(Process process) {
        Collection<Activity> allActivitiesInProc =
                Xpdl2ModelUtil.getAllActivitiesInProc(process);
        for (Activity activity : allActivitiesInProc) {
            if (activity != null) {

                Event event = activity.getEvent();

                /*
                 * XPD-7539 Message Events can be REST sdervce as well as
                 * WebService; only want to deal with WebService
                 */
                if (event != null) {
                    if (EventObjectUtil.isWebServiceRelatedEvent(activity)) {

                        TriggerType trigger = null;
                        if (event instanceof StartEvent) {
                            StartEvent startEvent = (StartEvent) event;
                            trigger = startEvent.getTrigger();
                            TriggerResultMessage triggerResultMessage =
                                    startEvent.getTriggerResultMessage();
                            if (triggerResultMessage != null) {
                                Message message =
                                        triggerResultMessage.getMessage();
                                if (message != null) {
                                    List<DataMapping> dataMappings =
                                            message.getDataMappings();
                                    if (trigger != null
                                            && trigger.getName() != null) {
                                        if (trigger
                                                .getName()
                                                .equals(TriggerType.MESSAGE_LITERAL
                                                        .getName())) {
                                            checkCatchMessageEventMappedParameters(activity,
                                                    dataMappings);
                                        }
                                    }
                                }
                            }
                        } else if (event instanceof IntermediateEvent) {
                            IntermediateEvent intermediateEvent =
                                    (IntermediateEvent) event;
                            trigger = intermediateEvent.getTrigger();
                            TriggerResultMessage triggerResultMessage =
                                    intermediateEvent.getTriggerResultMessage();
                            if (triggerResultMessage != null) {
                                Message message =
                                        triggerResultMessage.getMessage();
                                if (message != null) {
                                    List<DataMapping> dataMappings =
                                            message.getDataMappings();
                                    if (trigger != null
                                            && trigger.getName() != null) {
                                        if (TriggerType.MESSAGE_LITERAL == trigger) {
                                            if (CatchThrow.THROW
                                                    .equals(triggerResultMessage
                                                            .getCatchThrow())) {
                                                checkThrowMessageEventMappedParameters(activity,
                                                        dataMappings);
                                            } else {
                                                checkCatchMessageEventMappedParameters(activity,
                                                        dataMappings);
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (event instanceof EndEvent) {
                            EndEvent endEvent = (EndEvent) event;
                            ResultType result = endEvent.getResult();
                            TriggerResultMessage triggerResultMessage =
                                    endEvent.getTriggerResultMessage();
                            if (triggerResultMessage != null) {
                                Message message =
                                        triggerResultMessage.getMessage();
                                if (message != null) {
                                    List<DataMapping> dataMappings =
                                            message.getDataMappings();
                                    if (result != null
                                            && result.getName() != null) {
                                        if (result
                                                .getName()
                                                .equals(ResultType.MESSAGE_LITERAL
                                                        .getName())) {

                                            checkThrowMessageEventMappedParameters(activity,
                                                    dataMappings);
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else if (TaskObjectUtil.getTaskType(activity) == TaskType.RECEIVE_LITERAL) {
                    Implementation impl = activity.getImplementation();
                    if (impl instanceof Task) {
                        TaskReceive taskReceive =
                                ((Task) impl).getTaskReceive();
                        Message message = taskReceive.getMessage();
                        if (message != null) {
                            checkCatchMessageEventMappedParameters(activity,
                                    message.getDataMappings());
                        }
                    }
                } else if (TaskType.SERVICE_LITERAL.equals(TaskObjectUtil
                        .getTaskType(activity))) {
                    checkCatchMessageEventMappedParameters(activity,
                            Xpdl2ModelUtil.getDataMappings(activity,
                                    DirectionType.OUT_LITERAL));
                }
            }
        }
    }

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validateFlowContainer(com.tibco.xpd.xpdl2.Process,
     *      org.eclipse.emf.common.util.EList,
     *      org.eclipse.emf.common.util.EList)
     * 
     * @param process
     * @param activities
     * @param transitions
     */
    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
    }

    /**
     * Validate the message mapping for a throw message event (Intermediate
     * Throw message or end message.
     * 
     * @param activity
     * @param dataMappings
     */
    private void checkThrowMessageEventMappedParameters(Activity activity,
            List<DataMapping> dataMappings) {

        for (DataMapping dataMapping : dataMappings) {
            validateThrowMessageMappingDirection(activity, dataMapping);
        }
    }

    /**
     * Validate the mode of the parameter for the given mapping in a catch
     * message (start event or intermediate catch message).
     * 
     * @param activity
     * @param dataMappings
     */
    private void checkCatchMessageEventMappedParameters(Activity activity,
            List<DataMapping> dataMappings) {
        for (DataMapping dataMapping : dataMappings) {
            if (dataMappings != null) {
                validateMessageMappingDirection(activity, dataMapping);
            }
        }
        return;
    }

    /**
     * Validate the message mapping for a throw message event (Intermediate
     * Throw message or end message.
     * 
     * @param activity
     *            The activity..
     * @param mapping
     *            The mapping.
     */
    private void validateThrowMessageMappingDirection(Activity activity,
            DataMapping mapping) {
        if (mapping != null && mapping.getActual() != null
                && !DataMappingUtil.isScripted(mapping)) {
            String script = DataMappingUtil.getScript(mapping);
            if (script != null) {
                boolean dontWarn = false;

                /*
                 * When checking mode of parameter need to check the root of the
                 * path (otherwise we will always complain when child of complex
                 * is mapped).
                 */
                String rootName = script;
                ConceptPath conceptPath =
                        ConceptUtil.resolveConceptPath(activity, script);
                if (conceptPath != null) {
                    ConceptPath rootPath = conceptPath.getRoot();
                    if (rootPath != null) {
                        rootName = rootPath.getPath();
                    }
                }

                List<AssociatedParameter> associatedParameters =
                        ProcessInterfaceUtil
                                .getActivityAssociatedParameters(activity);
                if (associatedParameters != null
                        && !associatedParameters.isEmpty()) {
                    for (AssociatedParameter associatedParameter : associatedParameters) {
                        // If parameter is associated with Formal Parameter and
                        // mode type is IN then raise an issue
                        if (associatedParameter.getFormalParam()
                                .equals(rootName)) {
                            dontWarn = true;
                            /*
                             * SID XPD-225: don't check for read only mappings
                             * now that this is done elsewhere in other mapping
                             * rules.
                             * 
                             * validateIsReadOnlyAllowed(activity,
                             * associatedParameter, mapping, script);
                             */
                            if (ModeType.IN_LITERAL == ProcessInterfaceUtil
                                    .getAssocParamModeType(associatedParameter)) {
                                List<String> messages = new ArrayList<String>();
                                messages.add(ModeType.IN_LITERAL.getName());
                                messages.add(script);
                                String key =
                                        MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO;
                                addIssue(WRONG_MODE,
                                        mapping,
                                        messages,
                                        Collections.singletonMap(key,
                                                DataMappingUtil
                                                        .getTarget(mapping)));
                                return;
                            } else {
                                if (Xpdl2ModelUtil
                                        .isProcessAPIActivity(activity)
                                        && ProcessInterfaceUtil
                                                .getProcessRelevantDataFromAssociatedParam(associatedParameter) instanceof DataField) {
                                    String key =
                                            MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO;
                                    List<String> messages =
                                            new ArrayList<String>();
                                    messages.add(associatedParameter
                                            .getFormalParam());
                                    addIssue(DATAFIELD_MAPPING_DISALLOWED,
                                            mapping,
                                            messages,
                                            Collections
                                                    .singletonMap(key,
                                                            DataMappingUtil
                                                                    .getTarget(mapping)));
                                    return;
                                }
                            }
                        }
                    }

                    if (!dontWarn) {
                        List<String> messages = new ArrayList<String>();
                        messages.add(activity.getName());
                        String key =
                                MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO;
                        addIssue(PARAM_MAP_MODE_UNCONFIRM,
                                mapping,
                                messages,
                                Collections.singletonMap(key,
                                        DataMappingUtil.getTarget(mapping)));
                    }
                } else {
                    List<ProcessRelevantData> associatedPRD =
                            ProcessInterfaceUtil
                                    .getAssociatedProcessRelevantDataForActivity(activity);
                    for (ProcessRelevantData processRelevantData : associatedPRD) {
                        if (processRelevantData.getName().equals(rootName)) {
                            dontWarn = true;
                            /*
                             * SID XPD-225: don't check for read only mappings
                             * now that this is done elsewhere in other mapping
                             * rules.
                             * 
                             * validateIsReadOnlyAllowed(activity,
                             * processRelevantData, mapping, script);
                             */
                            if (processRelevantData instanceof FormalParameter) {
                                FormalParameter formalParameter =
                                        (FormalParameter) processRelevantData;
                                if (formalParameter.getMode().getName()
                                        .equals(ModeType.IN_LITERAL.getName())) {
                                    List<String> messages =
                                            new ArrayList<String>();
                                    messages.add(ModeType.IN_LITERAL.getName());
                                    messages.add(script);
                                    String key =
                                            MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO;
                                    addIssue(WRONG_MODE,
                                            mapping,
                                            messages,
                                            Collections
                                                    .singletonMap(key,
                                                            DataMappingUtil
                                                                    .getTarget(mapping)));
                                    return;
                                }
                            } else {
                                if (Xpdl2ModelUtil
                                        .isProcessAPIActivity(activity)) {
                                    String key =
                                            MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO;
                                    List<String> messages =
                                            new ArrayList<String>();
                                    messages.add(processRelevantData.getName());
                                    addIssue(DATAFIELD_MAPPING_DISALLOWED,
                                            mapping,
                                            messages,
                                            Collections
                                                    .singletonMap(key,
                                                            DataMappingUtil
                                                                    .getTarget(mapping)));
                                    return;
                                }
                            }
                        }
                    }
                    if (!dontWarn) {
                        List<String> messages = new ArrayList<String>();
                        messages.add(activity.getName());
                        String key =
                                MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO;
                        addIssue(PARAM_MAP_MODE_UNCONFIRM,
                                mapping,
                                messages,
                                Collections.singletonMap(key,
                                        DataMappingUtil.getTarget(mapping)));
                    }
                }
            }
        }
        return;
    }

    /**
     * Validate the mode of the parameter for the given mapping in a catch
     * message (start event or intermediate catch message).
     * 
     * This unfortunately checks mappings for Service tasks as well - need to
     * refactor this out of this part
     * 
     * @param activity
     *            The activity..
     * @param mapping
     *            The mapping.
     */
    private void validateMessageMappingDirection(Activity activity,
            DataMapping mapping) {
        boolean dontWarn = false;
        if (mapping != null && mapping.getActual() != null) {

            // For mappings to BOM class children, the mode has to be compared
            // to the parent Formal Parameter that it belongs to
            String targetConceptPathName = DataMappingUtil.getTarget(mapping);

            ConceptPath targetCP =
                    ConceptUtil.resolveConceptPath(activity,
                            targetConceptPathName);

            ProcessRelevantData resolvedParameter = null;
            if (targetCP != null) {
                resolvedParameter =
                        ConceptUtil.resolveParameter(activity, targetCP);
            }
            // Target name is set from the resolved parameter - resolved
            // parameter is the formal parameter of which the attribute is part
            // of
            String target = null;
            if (null != resolvedParameter) {
                target = resolvedParameter.getName();
            } else {
                target = targetConceptPathName;
            }
            if (target != null) {
                List<AssociatedParameter> associatedParameters =
                        ProcessInterfaceUtil
                                .getActivityAssociatedParameters(activity);
                if (associatedParameters != null
                        && !associatedParameters.isEmpty()) {
                    for (AssociatedParameter associatedParameter : associatedParameters) {
                        if (associatedParameter.getFormalParam().equals(target)) {
                            dontWarn = true;

                            /*
                             * SID XPD-225: don't check for read only mappings
                             * now that this is done elsewhere in other mapping
                             * rules.
                             * 
                             * validateIsReadOnlyAllowed(activity,
                             * associatedParameter, mapping, target);
                             */

                            if (Xpdl2ModelUtil.isProcessAPIActivity(activity)
                                    && ProcessInterfaceUtil
                                            .getProcessRelevantDataFromAssociatedParam(associatedParameter) instanceof DataField) {
                                // Dissallow datamapping to datafield for
                                // Process API activity
                                String key =
                                        MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO;
                                List<String> messages = new ArrayList<String>();
                                messages.add(associatedParameter
                                        .getFormalParam());
                                addIssue(DATAFIELD_MAPPING_DISALLOWED,
                                        mapping,
                                        messages,
                                        Collections.singletonMap(key, target));

                                return;
                            } else {
                                if (!TaskType.SERVICE_LITERAL
                                        .equals(TaskObjectUtil
                                                .getTaskType(activity))) {
                                    // This check shouldn't apply for Service
                                    // tasks
                                    if (ModeType.OUT_LITERAL == ProcessInterfaceUtil
                                            .getAssocParamModeType(associatedParameter)) {
                                        List<String> messages =
                                                new ArrayList<String>();
                                        messages.add(ModeType.OUT_LITERAL
                                                .getName());
                                        messages.add(target);
                                        String key =
                                                MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO;
                                        addIssue(WRONG_MODE,
                                                mapping,
                                                messages,
                                                Collections.singletonMap(key,
                                                        target));
                                        return;
                                    }
                                }
                            }
                        }
                    }
                    if (!dontWarn) {
                        List<String> messages = new ArrayList<String>();
                        messages.add(activity.getName());
                        String key =
                                MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO;
                        addIssue(PARAM_MAP_MODE_UNCONFIRM,
                                mapping,
                                messages,
                                Collections.singletonMap(key, target));
                    }
                } else {
                    List<ProcessRelevantData> associatedPRD =
                            ProcessInterfaceUtil
                                    .getAssociatedProcessRelevantDataForActivity(activity);
                    for (ProcessRelevantData processRelevantData : associatedPRD) {
                        if (processRelevantData.getName().equals(target)) {
                            dontWarn = true;

                            /*
                             * SID XPD-225: don't check for read only mappings
                             * now that this is done elsewhere in other mapping
                             * rules.
                             * 
                             * validateIsReadOnlyAllowed(activity,
                             * processRelevantData, mapping, target);
                             */
                            // The mode check isn't applicable for Service tasks
                            if (!TaskType.SERVICE_LITERAL.equals(TaskObjectUtil
                                    .getTaskType(activity))) {
                                if (processRelevantData instanceof FormalParameter) {
                                    FormalParameter formalParameter =
                                            (FormalParameter) processRelevantData;
                                    if (formalParameter
                                            .getMode()
                                            .getName()
                                            .equals(ModeType.OUT_LITERAL.getName())) {
                                        List<String> messages =
                                                new ArrayList<String>();
                                        messages.add(ModeType.OUT_LITERAL
                                                .getName());
                                        messages.add(target);
                                        String key =
                                                MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO;
                                        addIssue(WRONG_MODE,
                                                mapping,
                                                messages,
                                                Collections.singletonMap(key,
                                                        target));
                                        return;
                                    }
                                }
                            } else {
                                if (Xpdl2ModelUtil
                                        .isProcessAPIActivity(activity)) {
                                    String key =
                                            MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO;
                                    List<String> messages =
                                            new ArrayList<String>();
                                    messages.add(processRelevantData.getName());
                                    addIssue(DATAFIELD_MAPPING_DISALLOWED,
                                            mapping,
                                            messages,
                                            Collections.singletonMap(key,
                                                    target));
                                    return;
                                }
                            }
                        }
                    }
                    if (!dontWarn) {
                        List<String> messages = new ArrayList<String>();
                        messages.add(activity.getName());
                        String key =
                                MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO;
                        addIssue(PARAM_MAP_MODE_UNCONFIRM,
                                mapping,
                                messages,
                                Collections.singletonMap(key, target));
                    }
                }
            }
        }
    }

}
