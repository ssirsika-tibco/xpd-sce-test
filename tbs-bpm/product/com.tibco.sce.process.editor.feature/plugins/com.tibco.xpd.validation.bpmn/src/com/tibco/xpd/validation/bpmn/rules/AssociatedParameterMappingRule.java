/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.rules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.mapper.MapperContentProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.StandardMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositorFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.script.SingleMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.util.SubProcUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.WebServiceOperationUtil;
import com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author nwilson
 */
public class AssociatedParameterMappingRule extends FlowContainerValidationRule {

    /** local Data used input mapping is not in the associated parameter list. */
    private static final String MAPPINGSOURCEDATA_NOT_ASSOCIATED =
            "bpmn.mappingSourceDataNotAssociated"; //$NON-NLS-1$

    /**
     * local data used in output mapping is not in the associated parameter
     * list.
     */
    private static final String MAPPINGTARGET_DATA_NOT_ASSOCIATED =
            "bpmn.mappingTargetDataNotAssociated"; //$NON-NLS-1$

    /**
     * Input Mapping to sub-process data that does not exist or is no longer
     * associated with sub-process start event
     */
    private static final String SUBPROCINPUT_NOT_ASSOCIATED =
            "bpmn.subProcessInputNotAssociatedParameter"; //$NON-NLS-1$

    /**
     * Output Mapping from sub-process data that does not exist or is no longer
     * associated with sub-process start event
     */
    private static final String SUBPROCOUTPUT_DATA_NOT_ASSOCIATED =
            "bpmn.subProcessOutputNotAssociatedParameter"; //$NON-NLS-1$

    /**
     * Output Mapping from sub-process data that does not exist or is no longer
     * associated with sub-process start event
     */
    private static final String ENDERROROUTPUT_DATA_NOT_ASSOCIATED =
            "bpmn.endErrorOutputNotAssociatedParameter"; //$NON-NLS-1$

    /** Database service id. */
    private static final String DB_SERVICE_ID = "Database"; //$NON-NLS-1$

    /**
     * @param container
     *            The container to validate.
     * @see com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule#
     *      validate(com.tibco.xpd.xpdl2.FlowContainer)
     */
    @Override
    public void validate(FlowContainer container) {
        for (Object next : container.getActivities()) {
            Activity activity = (Activity) next;

            // Don't run rule for database tasks.
            if (DB_SERVICE_ID.equals(TaskObjectUtil
                    .getTaskImplementationExtensionId(activity))) {
                continue;
            }

            /*
             * SID XPD-914: Don't run rule for web-service activities or
             * JavaService anymore either else we're just duplicating the
             * source/target of mapping is missing from the AbstractMappigRule
             * that they use.
             * 
             * When all mapping rules migrated to AbstractMappingRule (means
             * migration of transform and sub-process I think) then we should be
             * able to get rid of this rule.
             */
            if (WebServiceOperationUtil
                    .isWebServiceImplementationType(activity)
                    || WebServiceOperationUtil
                            .isInvokeBusinessProcessImplementationType(activity)
                    || TaskImplementationTypeDefinitions.JAVA_SERVICE
                            .equals(TaskObjectUtil
                                    .getTaskImplementationExtensionId(activity))) {
                continue;
            }

            //
            // Check Input Mappings...
            //
            List<DataMapping> inMappings =
                    Xpdl2ModelUtil.getDataMappings(activity,
                            DirectionType.IN_LITERAL);

            //
            // Build a list of data names that are available to the local
            // activity (i.e. all local data or just the associated data if any
            // spec'd)
            //
            List<ProcessRelevantData> dataAssociatedWithLocalActivity =
                    ProcessInterfaceUtil
                            .getAssociatedProcessRelevantDataForActivity(activity);
            List<String> localActivityAssociatedDataNames =
                    new ArrayList<String>();
            for (ProcessRelevantData data : dataAssociatedWithLocalActivity) {
                if (data != null) {
                    localActivityAssociatedDataNames.add(data.getName());
                }
            }

            //
            // If this is an incoming request activity (i.e. one which
            // correlation can be used) then build a list of the associated
            // correlation data names.
            List<String> localActivityAssociatedCorrelationDataNames =
                    Collections.emptyList();
            if (Xpdl2ModelUtil.canHaveCorrelationAssociated(activity)) {
                List<DataField> correls =
                        ProcessInterfaceUtil
                                .getAssociatedCorrelationDataForActivity(activity);
                if (!correls.isEmpty()) {
                    localActivityAssociatedCorrelationDataNames =
                            new ArrayList<String>();

                    for (ProcessRelevantData data : correls) {
                        if (data != null) {
                            localActivityAssociatedCorrelationDataNames
                                    .add(data.getName());
                        }
                    }
                }
            }

            //
            // Check source data for the input mappings (the local data we are
            // mapping to the sub-process input params.
            // (Catch Error Event does not map input params).
            // Basically this means, checking for any 1->1 or composite field
            // mappings and checking that all data used is available in the
            // local process (and associated with the local activity).
            //
            validateSourceDataOfInMappings(activity,
                    inMappings,
                    localActivityAssociatedDataNames);

            //
            // If this is a subprocess task, check that the target
            // sub-process params for sub-process input are valid.
            //
            if (activity.getImplementation() instanceof SubFlow) {
                /*
                 * Only validate if the sub-process is accessible - otherwise
                 * the user won't be able to see the wood for the trees!
                 */
                if (TaskObjectUtil.getSubProcessOrInterface(activity) != null) {
                    validateTargetDataOfSubFlowInMappings(activity, inMappings);
                }
            }

            //
            // Check Out Mappings...
            //
            List<DataMapping> outMappings =
                    Xpdl2ModelUtil.getDataMappings(activity,
                            DirectionType.OUT_LITERAL);
            if (outMappings.size() > 0) {
                //
                // Checking the target of out mappings is easy (just has to be
                // the valid name of a local associated data item)
                //
                validateTargetDataOfOutMappings(activity,
                        localActivityAssociatedDataNames,
                        localActivityAssociatedCorrelationDataNames,
                        outMappings);

                //
                // Check the source data used in out mappings.
                //
                if (activity.getEvent() instanceof IntermediateEvent
                        && activity.getEvent().getEventTriggerTypeNode() instanceof ResultError) {
                    //
                    // If this is a catch error event that catches error thrown
                    // by end error event then check that the source of mappings
                    // still exists / associated with thrown end event.
                    //
                    validateSourceDataOfCatchErrorOutMappings(activity,
                            outMappings);

                } else if (activity.getImplementation() instanceof SubFlow) {
                    //
                    // If this is a subprocess task, check that the target
                    // sub-process params for sub-process input are valid.
                    //
                    /*
                     * Only validate if the sub-process is accessible -
                     * otherwise the user won't be able to see the wood for the
                     * trees!
                     */
                    if (TaskObjectUtil.getSubProcessOrInterface(activity) != null) {
                        validateSourceDataOfOutputMappings(activity,
                                outMappings);
                    }
                }
            }
        }

        return;
    }

    /**
     * Validate the source data of output mappings (for sub-process task and
     * catch error event (thrown by end error))
     * <p>
     * i.e. validate that the source local data is present and associated with
     * the local activity event).
     * 
     * @param activity
     * @param outMappings
     */
    private void validateSourceDataOfOutputMappings(Activity activity,
            List<DataMapping> outMappings) {
        List<FormalParameter> subParams =
                TaskObjectUtil.getSubProcessStartParams(activity,
                        ModeType.OUT_LITERAL);
        Set<String> subParamNames = new HashSet<String>();
        for (FormalParameter param : subParams) {
            subParamNames.add(param.getName());
        }

        validateSourceDataOfOutMappings(activity,
                outMappings,
                subParamNames,
                SUBPROCOUTPUT_DATA_NOT_ASSOCIATED);
    }

    /**
     * Validate the target data of output mappings (for sub-process task and
     * catch error event (thrown by end error))
     * <p>
     * i.e. validate that the target local data is present and associated with
     * the local activity event).
     * 
     * @param activity
     * @param localActivityAssociatedDataNames
     * @param localActivityAssociatedCorrelationDataNames
     * @param outMappings
     */
    private void validateTargetDataOfOutMappings(Activity activity,
            List<String> localActivityAssociatedDataNames,
            List<String> localActivityAssociatedCorrelationDataNames,
            List<DataMapping> outMappings) {

        boolean canHaveCorrelationAssociated =
                Xpdl2ModelUtil.canHaveCorrelationAssociated(activity);

        for (DataMapping mapping : outMappings) {
            String target = DataMappingUtil.getTarget(mapping);
            ConceptPath concept =
                    ConceptUtil.resolveConceptPath(activity, target);

            if (concept != null) {
                String rootName = concept.getRoot().getName();

                ProcessRelevantData resolveParameter =
                        ConceptUtil.resolveParameter(activity, rootName);

                // MR 38750 / 39307
                // If this is a correlation capable activity then we should
                // expect to find correlation fields in the associated
                // correlation data rather than the normal associated data.
                if (canHaveCorrelationAssociated
                        && isCorrelationData(resolveParameter)) {
                    if (!localActivityAssociatedCorrelationDataNames
                            .contains(rootName)) {
                        List<String> messages = new ArrayList<String>();
                        messages.add(target);
                        addIssue(MAPPINGTARGET_DATA_NOT_ASSOCIATED,
                                mapping,
                                messages,
                                Collections
                                        .singletonMap(MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
                                                target));
                    }
                } else {
                    if (!localActivityAssociatedDataNames.contains(rootName)) {
                        List<String> messages = new ArrayList<String>();
                        messages.add(target);
                        addIssue(MAPPINGTARGET_DATA_NOT_ASSOCIATED,
                                mapping,
                                messages,
                                Collections
                                        .singletonMap(MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
                                                target));
                    }
                }
            }
        }
        return;
    }

    /**
     * @param procRelevantData
     * @return
     */
    private boolean isCorrelationData(ProcessRelevantData procRelevantData) {
        if (procRelevantData instanceof DataField) {
            return ((DataField) procRelevantData).isCorrelation();
        }
        return false;
    }

    /**
     * Validate the target data in a sub-process task input mappings (i.e.
     * validate that the target sub-proc input param is present and associated
     * with sub-proc start event).
     * 
     * @param activity
     * @param inMappings
     */
    private void validateTargetDataOfSubFlowInMappings(Activity activity,
            List<DataMapping> inMappings) {
        List<FormalParameter> subParams =
                TaskObjectUtil.getSubProcessStartParams(activity,
                        ModeType.IN_LITERAL);
        List<String> subParamNames = new ArrayList<String>();
        for (FormalParameter param : subParams) {
            subParamNames.add(param.getName());
        }
        for (DataMapping mapping : inMappings) {
            String target = DataMappingUtil.getTarget(mapping);
            if (!subParamNames.contains(target)) {
                List<String> messages = new ArrayList<String>();
                messages.add(target);
                addIssue(SUBPROCINPUT_NOT_ASSOCIATED, mapping, messages);
            }
        }
    }

    /**
     * Check source data for the input mappings (the local data we are mapping
     * to the sub-process input params. (Catch Error Event does not map input
     * params). Basically this means, checking for any 1->1 or composite field
     * mappings and checking that all data used is available in the local
     * process (and associated with the local activity).
     * 
     * @param activity
     * @param inMappings
     * @param localActivityAssociatedDataNames
     */
    private void validateSourceDataOfInMappings(Activity activity,
            List<DataMapping> inMappings,
            List<String> localActivityAssociatedDataNames) {
        for (DataMapping mapping : inMappings) {
            if (!DataMappingUtil.isScripted(mapping)) {
                String target = DataMappingUtil.getTarget(mapping);
                String script = DataMappingUtil.getScript(mapping);
                String grammar = DataMappingUtil.getGrammar(mapping);
                boolean isInitialValueMapping =
                        DataMappingUtil.isInitialValueMapping(mapping);
                ScriptMappingCompositorFactory factory =
                        ScriptMappingCompositorFactory
                                .getCompositorFactory(grammar,
                                        DirectionType.IN_LITERAL,
                                        SubProcUtil.SCRIPT_CONTEXT);
                if (factory != null) {
                    ScriptMappingCompositor compositor =
                            factory.getCompositor(activity, target, script);
                    if (compositor instanceof SingleMappingCompositor) {
                        SingleMappingCompositor single =
                                (SingleMappingCompositor) compositor;
                        List<Object> sources =
                                new ArrayList<Object>(
                                        single.getSourceItems(false));
                        List<String> sourceNames =
                                new ArrayList<String>(
                                        single.getSourceItemNames());

                        if (sources.size() != sourceNames.size()) {
                            System.err
                                    .println(this.getClass().getName()
                                            + ": Unexpected condition, different number of mapping source items and names! ('" + script + "')"); //$NON-NLS-1$ //$NON-NLS-2$
                            continue;
                        }

                        int srcIdx = -1;
                        for (Object source : sources) {
                            srcIdx++;
                            if (source instanceof ConceptPath) {
                                source =
                                        ((ConceptPath) source).getRoot()
                                                .getItem();
                            }

                            if (source instanceof ProcessRelevantData) {
                                String name =
                                        ((ProcessRelevantData) source)
                                                .getName();

                                if (!localActivityAssociatedDataNames
                                        .contains(name)) {
                                    List<String> messages =
                                            new ArrayList<String>();
                                    messages.add(name);
                                    String key =
                                            MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO;
                                    addIssue(MAPPINGSOURCEDATA_NOT_ASSOCIATED,
                                            mapping,
                                            messages,
                                            Collections.singletonMap(key,
                                                    target));
                                }
                            } else if (isInitialValueMapping) {
                                // Intial Value Mapping rule will kick in and
                                // validate whether it is a correct value.
                            } else {
                                List<String> messages = new ArrayList<String>();
                                messages.add(sourceNames.get(srcIdx));
                                String key =
                                        MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO;
                                addIssue(MAPPINGSOURCEDATA_NOT_ASSOCIATED,
                                        mapping,
                                        messages,
                                        Collections.singletonMap(key, target));
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * If this is a catch error event that catches error thrown by end error
     * event then check that the source of mappings still exists / associated
     * with thrown end event.
     * 
     * @param activity
     * @param outMappings
     */
    private void validateSourceDataOfCatchErrorOutMappings(Activity activity,
            List<DataMapping> outMappings) {
        //
        // If this is a catch error event that catches error thrown by
        // end error event then check that the source of mappings still
        // exists / associated with thrown end event.
        //
        Object thrower = BpmnCatchableErrorUtil.getErrorThrower(activity);
        if (thrower instanceof Activity) {
            Activity throwActivity = (Activity) thrower;

            if (throwActivity.getEvent() instanceof EndEvent
                    && throwActivity.getEvent().getEventTriggerTypeNode() instanceof ResultError) {
                // It is a catch of and end error

                //
                // Build list of data names available from the throw
                // error event.
                Set<String> assocThrowParamNames = new HashSet<String>();

                assocThrowParamNames
                        .add(StandardMappingUtil.CATCH_ERRORCODE_FORMALPARAMETER
                                .getName());
                assocThrowParamNames
                        .add(StandardMappingUtil.CATCH_ERRORDETAIL_FORMALPARAMETER
                                .getName());

                List<FormalParameter> thrownParams =
                        ProcessInterfaceUtil
                                .getAssociatedFormalParameters(throwActivity);
                for (FormalParameter tp : thrownParams) {
                    assocThrowParamNames.add(tp.getName());
                }

                //
                // Validate that all the data mapped from thrown error
                // event is available to the thrower (exists and is
                // associated).
                validateSourceDataOfOutMappings(activity,
                        outMappings,
                        assocThrowParamNames,
                        ENDERROROUTPUT_DATA_NOT_ASSOCIATED);
            }
        }
    }

    /**
     * Validate that all of the source data utilised in the given mappings is
     * available to the thrower and add issue if not.
     * 
     * @param localActivity
     * @param outMappings
     * @param availableSourceDataNames
     */
    private void validateSourceDataOfOutMappings(Activity localActivity,
            List<DataMapping> outMappings,
            Set<String> availableSourceDataNames, String issueId) {
        //
        // Check the source (from thrown error) data used in
        // each of the mappings.
        for (DataMapping mapping : outMappings) {
            if (!DataMappingUtil.isScripted(mapping)) {
                //
                // For which we will need the target and source
                // (called script below) and a compositor that
                // allows us to break up any composite mappings
                // (many->1) into individual source data items.
                //
                String target = DataMappingUtil.getTarget(mapping);
                String script = DataMappingUtil.getScript(mapping);
                String grammar = DataMappingUtil.getGrammar(mapping);
                ScriptMappingCompositorFactory factory =
                        ScriptMappingCompositorFactory
                                .getCompositorFactory(grammar,
                                        DirectionType.OUT_LITERAL,
                                        SubProcUtil.SCRIPT_CONTEXT);
                if (factory != null) {
                    ScriptMappingCompositor compositor =
                            factory.getCompositor(localActivity, target, script);
                    if (compositor instanceof SingleMappingCompositor) {
                        SingleMappingCompositor single =
                                (SingleMappingCompositor) compositor;

                        List<Object> sources =
                                new ArrayList<Object>(
                                        single.getSourceItems(false));
                        List<String> sourceNames =
                                new ArrayList<String>(
                                        single.getSourceItemNames());

                        if (sources.size() != sourceNames.size()) {
                            System.err
                                    .println(this.getClass().getName()
                                            + ": Unexpected condition, different number of mapping source items and names! ('" + script + "')"); //$NON-NLS-1$ //$NON-NLS-2$
                            continue;
                        }

                        //
                        // Compare each of the source param items
                        // found in script with the data that is
                        // available from throw error event (if it's
                        // 1->1 mappingit's still a composite script
                        // with just
                        // one item in).
                        int srcIdx = -1;

                        for (Object source : sources) {
                            srcIdx++;

                            if (source instanceof ConceptPath) {
                                source =
                                        ((ConceptPath) source).getRoot()
                                                .getItem();
                            }

                            if (source instanceof ProcessRelevantData) {
                                String name =
                                        ((ProcessRelevantData) source)
                                                .getName();

                                /*
                                 * ABPM-897: Saket: If source is a dummy
                                 * parameter (Process ID parameter), then
                                 * ignore.
                                 */
                                if (!availableSourceDataNames.contains(name)
                                        && !StandardMappingUtil.REPLY_IMMEDIATE_PROCESS_ID_FORMALPARAMETER
                                                .equals(source)) {
                                    List<String> messages =
                                            new ArrayList<String>();
                                    messages.add(name);
                                    String key =
                                            MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO;
                                    addIssue(issueId,
                                            mapping,
                                            messages,
                                            Collections.singletonMap(key,
                                                    target));
                                }
                            } else {
                                // Source is completely missing
                                // (probably deleted)!
                                List<String> messages = new ArrayList<String>();
                                messages.add(sourceNames.get(srcIdx));
                                String key =
                                        MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO;
                                addIssue(issueId,
                                        mapping,
                                        messages,
                                        Collections.singletonMap(key, target));
                            }
                        }
                    }
                }
            }
        }
        return;
    }

}
