/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.bx.validation.rules.mapping;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorUtil;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorUtil.ErrorCatchType;
import com.tibco.xpd.implementer.nativeservices.globaldataservice.GlobalDataErrorSourceContentProvider;
import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.process.js.parser.util.ScriptMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.script.ScriptInformationParsed;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProvider;
import com.tibco.xpd.validation.bpmn.rules.baserules.MappingTypeCompatibility;
import com.tibco.xpd.validation.bpmn.rules.baserules.ProcessDataMappingRuleInfoProvider;
import com.tibco.xpd.xpdExtension.GlobalDataOperation;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * AMX BPM Catch BPMN error event - error-data to Main Process JavaScript output
 * mapping rule for GlobalData Task.
 * 
 * 
 * @author aprasad
 * @since 19 Feb 2014
 */
public class GlobalDataCatchErrorJSOutputMappingRule extends
        AbstractBxBpmnCatchErrorJSMappingRule {

    private static final String ISSUE_SINGLE_TO_MULTI_NONLOOP =
            "bx.subProcessNonArrayArrayMappingDisallowed"; //$NON-NLS-1$

    private MappingRuleContentInfoProvider sourceInfoProvider;

    private MappingRuleContentInfoProvider targetInfoProvider;

    /**
     * 
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#isConcatenationMappingSupported()
     * 
     * @return
     */
    @Override
    protected boolean isConcatenationMappingSupported() {
        return false;
    }

    /**
     * 
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#isConcatenationMappingSupportedForTarget(java.lang.Object)
     * 
     * @param targetObject
     * @return
     */
    @Override
    protected boolean isConcatenationMappingSupportedForTarget(
            Object targetObject) {
        return false;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#isMappingFromSourceLevelSupported(java.lang.Object)
     * 
     * @param sourceObjectInTree
     * @return
     */
    @Override
    protected boolean isMappingFromSourceLevelSupported(
            Object sourceObjectInTree) {
        return true;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#isMappingToTargetLevelSupported(java.lang.Object)
     * 
     * @param targetObjectInTree
     * @return
     */
    @Override
    protected boolean isMappingToTargetLevelSupported(Object targetObjectInTree) {
        if (targetObjectInTree instanceof ConceptPath) {
            /* Don't allow mapping to individual elements in a sequence */
            if (((ConceptPath) targetObjectInTree).getIndex() >= 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#isMultiToMultiSupported(java.lang.Object,
     *      java.lang.Object)
     * 
     * @param multiInstanceSource
     * @param singleInstanceTarget
     * @return
     */
    @Override
    protected boolean isMultiToMultiSupported(Object multiInstanceSource,
            Object singleInstanceTarget) {
        return true;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#isMultiToSingleSupported(java.lang.Object,
     *      java.lang.Object)
     * 
     * @param multiInstanceSource
     * @param singleInstanceTarget
     * @return
     */
    @Override
    protected boolean isMultiToSingleSupported(Object multiInstanceSource,
            Object singleInstanceTarget) {
        return false;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#isScriptMappingSupported()
     * 
     * @return
     */
    @Override
    protected boolean isScriptMappingSupported() {
        return true;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#isScriptMappingSupportedForTarget(java.lang.Object)
     * 
     * @param targetObject
     * @return
     */
    @Override
    protected boolean isScriptMappingSupportedForTarget(Object targetObject) {
        return true;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#isSingleToMultiSupported(java.lang.Object,
     *      java.lang.Object)
     * 
     * @param singleInstanceSource
     * @param multiInstanceTarget
     * @return
     */
    @Override
    protected boolean isSingleToMultiSupported(Object singleInstanceSource,
            Object multiInstanceTarget) {

        return false;
    }

    /**
     * 
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractProcessData2ProcessDataJSMappingRule#createSourceInfoProvider(org.eclipse.emf.ecore.EObject)
     * 
     * @param objectToValidate
     * @return
     */
    @Override
    protected MappingRuleContentInfoProvider createSourceInfoProvider(
            EObject objectToValidate) {

        GlobalDataErrorSourceContentProvider globalDataErrorSourceContentProvider =
                new GlobalDataErrorSourceContentProvider();

        globalDataErrorSourceContentProvider.inputChanged(null,
                null,
                objectToValidate);

        sourceInfoProvider =
                new ProcessDataMappingRuleInfoProvider(
                        globalDataErrorSourceContentProvider);

        return sourceInfoProvider;
    }

    /**
     * 
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractProcessData2ProcessDataJSMappingRule#createTargetInfoProvider(org.eclipse.emf.ecore.EObject)
     * 
     * @param objectToValidate
     * @return
     */
    @Override
    protected MappingRuleContentInfoProvider createTargetInfoProvider(
            EObject objectToValidate) {

        targetInfoProvider = super.createTargetInfoProvider(objectToValidate);

        return targetInfoProvider;
    }

    @Override
    protected MappingTypeCompatibility isScriptTypeToProcessDataCompatible(
            IScriptRelevantData scriptType, ConceptPath target) {

        return N2ProcessDataMappingCompatibilityUtil
                .checkTypeCompatibility(scriptType, target);

    }

    @Override
    protected ScriptInformationParsed parseScript(Object mapping) {
        if (mapping instanceof Mapping
                && ((Mapping) mapping).getMappingModel() instanceof DataMapping) {
            DataMapping dataMapping =
                    (DataMapping) ((Mapping) mapping).getMappingModel();
            String strScript =
                    ProcessScriptUtil.getDataMappingScript(dataMapping);

            Activity activity = Xpdl2ModelUtil.getParentActivity(dataMapping);

            ScriptInformation scriptInformation =
                    ProcessScriptUtil
                            .getScriptInformationFromDataMapping(dataMapping);

            if (strScript == null || strScript.trim().length() < 1) {
                return new ScriptInformationParsed(scriptInformation, null);
            }

            Process process = Xpdl2ModelUtil.getProcess(activity);

            if (activity != null && scriptInformation != null
                    && process != null) {
                return ScriptMappingUtil
                        .parseScript(mapping,
                                getValidationStrategyList(process, activity),
                                getProcessDestinationList(process),
                                getScriptRelevantDataTypeMap(process,
                                        scriptInformation),
                                getScriptType());
            }
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractBpmnCatchErrorJSMappingRule#doActivityIsApplicable(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    protected boolean doActivityIsApplicable(Activity activity) {
        return isCatchGlobalDataErrorEvent(activity);

    }

    /**
     * Checks and returns true, if the given activity is a Catch Error Event
     * attached to a Global Data Task, returns false otherwise.
     * 
     * @param activity
     * @return <code>true</code> if the given activity is catch error event
     *         attached to GlobalData task .
     */
    private static boolean isCatchGlobalDataErrorEvent(Activity activity) {
        if (activity.getEvent() instanceof IntermediateEvent) {

            IntermediateEvent event = (IntermediateEvent) activity.getEvent();

            if (TriggerType.ERROR_LITERAL.equals(event.getTrigger())) {

                ErrorCatchType catchType =
                        BpmnCatchableErrorUtil.getCatchTypeStrict(activity);

                /*
                 * Check that this is a specific error for a Global Data task.
                 */
                if (catchType == ErrorCatchType.CATCH_SPECIFIC) {
                    Object thrower =
                            BpmnCatchableErrorUtil.getErrorThrower(activity);

                    if (thrower instanceof Activity) {

                        Implementation implementation =
                                ((Activity) thrower).getImplementation();

                        if (implementation instanceof Task) {

                            TaskService taskService =
                                    ((Task) implementation).getTaskService();

                            if (taskService != null) {

                                GlobalDataOperation globalDataOp =
                                        (GlobalDataOperation) Xpdl2ModelUtil
                                                .getOtherElement(taskService,
                                                        XpdExtensionPackage.eINSTANCE
                                                                .getDocumentRoot_GlobalDataOperation());

                                return globalDataOp != null;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractActivityMappingJavaScriptRule#isUntypedScriptListToArrayMappingAllowed(java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     * 
     * @param sourceObjectInTree
     * @param targetObjectInTree
     * @param mapping
     * @return
     */
    @Override
    public boolean isUntypedScriptListToArrayMappingAllowed(
            Object sourceObjectInTree, Object targetObjectInTree, Object mapping) {

        return true;
    }
}
