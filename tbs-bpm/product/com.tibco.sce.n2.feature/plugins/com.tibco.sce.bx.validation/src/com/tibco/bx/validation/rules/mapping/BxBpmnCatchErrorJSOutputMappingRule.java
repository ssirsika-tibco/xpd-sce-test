/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.bx.validation.rules.mapping;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.validation.rules.scripts.BxCatchSubProcOrAllErrorMappedScriptRule;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorUtil;
import com.tibco.xpd.mapper.MapperContentProvider;
import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.process.js.parser.util.ScriptMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.StandardMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.script.ScriptInformationParsed;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.validation.bpmn.rules.baserules.MappingIssue;
import com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProvider;
import com.tibco.xpd.validation.bpmn.rules.baserules.MappingTypeCompatibility;
import com.tibco.xpd.validation.bpmn.rules.baserules.ProcessDataMappingRuleInfoProvider;
import com.tibco.xpd.xpdExtension.ErrorThrowerInfo;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Loop;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * AMX BPM Catch BPMN error event - error-data to Main Process JavaScript output
 * mapping rule.
 * <p>
 * This handles Specific sub-process error catch, AND CATCH_ALL / CATCH_NAME for
 * any error thrower.
 * 
 * @author aallway
 * @since 21 Dec 2010
 */
public class BxBpmnCatchErrorJSOutputMappingRule extends
        AbstractBxBpmnCatchErrorJSMappingRule {

    private static final String ISSUE_SINGLE_TO_MULTI_NONLOOP =
            "bx.subProcessNonArrayArrayMappingDisallowed"; //$NON-NLS-1$

    private static final String ISSUE_CATCH_SPECIFIC_ERROR_ERRODETAIL_NOTSUPPORTED =
            "bx.catchSpecificErrorErrorDetailNotSupported"; //$NON-NLS-1$

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
        return isScriptMappingSupportedForBpmnCatchError(getCurrentActivity());
    }

    /**
     * This is statically defined so that
     * {@link BxCatchSubProcOrAllErrorMappedScriptRule} can base whether it validates on
     * the same criteria as applied to the "script mapping not supported rule."
     * 
     * @return <code>true</code> if script mapping is supported for given
     *         activity.
     */
    public static boolean isScriptMappingSupportedForBpmnCatchError(
            Activity activity) {
        /*
         * XPD-3793 Allow Script mappings for all Catch Error Events
         * irrespective of Catch Type and Attached-to Task type
         */
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
        return isScriptMappingSupportedForBpmnCatchError(getCurrentActivity());
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
        /*
         * Single-instance to multi instance is supported only for output on
         * multi-instance task.
         */
        Activity taskAttachedTo =
                EventObjectUtil.getTaskAttachedTo(getCurrentActivity());
        if (taskAttachedTo != null) {
            Loop loop = taskAttachedTo.getLoop();
            if (loop != null && loop.getLoopType() != null) {
                return true;
            }
        }

        return false;
    }

    /**
     * 
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#createIssue(com.tibco.xpd.validation.bpmn.rules.baserules.MappingIssue,
     *      org.eclipse.emf.ecore.EObject, java.util.List, java.util.Map,
     *      java.lang.Object, java.lang.Object, java.lang.Object)
     * 
     * @param issue
     * @param issueTarget
     * @param messages
     * @param additionalInfo
     * @param source
     * @param target
     * @param mapping
     */
    @Override
    protected void createIssue(MappingIssue issue, EObject issueTarget,
            List<String> messages, Map<String, String> additionalInfo,
            Object source, Object target, Object mapping) {
        /*
         * Change validation message for
         * "Multiple to single is not supported for 'x' to 'x'" to
         * "Single to multi instance data mapping is only supported for multi-instance/looping tasks ('%1$s' to %2$s)"
         */
        if (MappingIssue.SINGLE_TO_MULTI_UNSUPPORTED.equals(issue)) {
            addIssue(ISSUE_SINGLE_TO_MULTI_NONLOOP,
                    issueTarget,
                    messages,
                    additionalInfo);
        } else {
            /* Use default message. */
            super.createIssue(issue,
                    issueTarget,
                    messages,
                    additionalInfo,
                    source,
                    target,
                    mapping);
        }
    }

    /**
     * 
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractProcess2ProcessJSOutputMappingRule#performAdditionalMappingValidation(java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     * 
     * @param mapping
     * @param sourceObjectInTree
     * @param targetObjectInTree
     */
    @Override
    protected void performAdditionalMappingValidation(Object mapping,
            Object sourceObjectInTree, Object targetObjectInTree) {
        super.performAdditionalMappingValidation(mapping,
                sourceObjectInTree,
                targetObjectInTree);

        /*
         * Sid XPD-2045. Nominally, for MI sub-process output mapping from
         * single instance param to multi-instance calling process data is ok.
         * Each sub-process instance returns data into a different element of
         * the target array.
         * 
         * However because of WRM-1762, this is not possible to a child sequence
         * inside a complex BOM object - so we will add a temporary rule against
         * that.
         */
        Activity taskAttachedTo =
                EventObjectUtil.getTaskAttachedTo(getCurrentActivity());
        if (taskAttachedTo != null) {
            Loop loop = taskAttachedTo.getLoop();
            if (loop != null && loop.getLoopType() != null) {
                /*
                 * It's a loop - if the source is single instance and the target
                 * is multi-instance...
                 */
                if (!((ProcessDataMappingRuleInfoProvider) sourceInfoProvider)
                        .isMultiInstancePublic(sourceObjectInTree)
                        && ((ProcessDataMappingRuleInfoProvider) targetInfoProvider)
                                .isMultiInstancePublic(targetObjectInTree)) {
                    /*
                     * Then make sure it is not child sequence of complex BOM
                     * object (effectively this means
                     * "if it has a parent element" because a multi-instance
                     * object with a parrent can ONLY be a seuqnce element
                     * inside a BOM.
                     */
                    if (((ProcessDataMappingRuleInfoProvider) targetInfoProvider)
                            .getContentProvider().getParent(targetObjectInTree) != null) {
                        addIssue(SubProcessJSOutputMappingRule.ISSUE_SINGLE_TO_MULTICHILD_NOT_SUPPORTED,
                                getModelObjectForMapping(mapping),
                                createMessageList(getMappingTypeDescription(getCurrentActivity()),
                                        getSourcePathDescription(sourceInfoProvider,
                                                mapping),
                                        getTargetPathDescription(targetInfoProvider,
                                                mapping)),
                                createAdditionalInfo(MapperContentProvider.DATAMAPPING_SOURCE_URI_ISSUEINFO,
                                        getSourcePath(sourceInfoProvider,
                                                mapping),
                                        MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
                                        getTargetPath(targetInfoProvider,
                                                mapping)));
                    }
                }
            }

            /*
             * XPD-2350 - Disallow mapping from $ERRORDETAIL for non-catch-all
             * events.
             */
            if (sourceObjectInTree instanceof ConceptPath) {
                if (StandardMappingUtil.CATCH_ERRORDETAIL_FORMALPARAMETER
                        .equals(((ConceptPath) sourceObjectInTree).getItem())) {

                    /*
                     * If there is a specific error thrower then there will be
                     * extendedErrorThrowerInfo otherwise for catch-all and
                     * catch-by-name this will be null
                     */
                    ErrorThrowerInfo extendedErrorThrowerInfo =
                            BpmnCatchableErrorUtil
                                    .getExtendedErrorThrowerInfo(getCurrentActivity());
                    if (extendedErrorThrowerInfo != null) {
                        addIssue(ISSUE_CATCH_SPECIFIC_ERROR_ERRODETAIL_NOTSUPPORTED,
                                getModelObjectForMapping(mapping),
                                createMessageList(getMappingTypeDescription(getCurrentActivity()),
                                        getSourcePathDescription(sourceInfoProvider,
                                                mapping),
                                        getTargetPathDescription(targetInfoProvider,
                                                mapping)),
                                createAdditionalInfo(MapperContentProvider.DATAMAPPING_SOURCE_URI_ISSUEINFO,
                                        getSourcePath(sourceInfoProvider,
                                                mapping)));
                    }
                }
            }
        }

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

        /*
         * We need to keep this for some jiggery pokery later - see
         * performAdditionalMappingValidation()
         */
        sourceInfoProvider = super.createSourceInfoProvider(objectToValidate);

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
        /*
         * We need to keep this for some jiggery pokery later - see
         * performAdditionalMappingValidation()
         */
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
