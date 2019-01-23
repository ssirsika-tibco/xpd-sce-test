/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.bx.validation.rules.mapping;

import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.process.js.parser.util.ScriptMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.script.ScriptInformationParsed;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.validation.bpmn.rules.baserules.AbstractSubProcessCallOutputMappingRule;
import com.tibco.xpd.validation.bpmn.rules.baserules.MappingTypeCompatibility;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * This rule abstracts the similarities of requriements for validation of
 * mappings in activities that effectively map process data to process data.
 * <p>
 * Currently this includes...
 * <li>Reusable Sub-Process Task.</li>
 * <li>Decision Service Task.</li>
 * <p>
 * The rules and restrictions applicable to these situations within AMX BPM are
 * likely to remain identical in most aspects.
 * 
 * @author aallway
 * @since 1 Aug 2011
 */
public abstract class AbstractN2Process2ProcessJSOutputMappingRule extends
        AbstractSubProcessCallOutputMappingRule {

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
     * 
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
     * 
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
     * 
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
     * 
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
     * 
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
         * Currently only reusable-sub-process task supports Single->Multi and
         * then only for multi-instance tasks (to pass data back parameter
         * values into from array element corresponding to task loop index into
         * each task instance). This is dealt with in the specific sub-proess
         * task type sub-class of this rule.
         * 
         * Later this strategy _should_ be expanded to include other tasks types
         * by the process engine and therefore could be pulled up from the
         * sub-proces implemetation of this rule to be more generic.
         */
        return false;
    }

    /**
     * 
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#isScriptMappingSupported()
     * 
     * @return
     */
    @Override
    protected boolean isScriptMappingSupported() {
        /* Script mappings are not supported on output from sub-process */
        return false;
    }

    /**
     * 
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#isScriptMappingSupportedForTarget(java.lang.Object)
     * 
     * @param targetObject
     * @return
     */
    @Override
    protected boolean isScriptMappingSupportedForTarget(Object targetObject) {
        /* Script mappings are not supported on output from sub-process */
        return false;
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
         * Not checking integer type length compatibility anymore due to
         * XPD-6064
         */
    }

}
