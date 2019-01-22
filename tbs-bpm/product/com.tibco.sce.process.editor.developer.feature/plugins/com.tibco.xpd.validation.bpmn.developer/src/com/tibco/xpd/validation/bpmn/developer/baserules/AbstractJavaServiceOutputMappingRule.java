/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.baserules;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.JavaMethodParameter;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.PojoItemProvider;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.PojoMappingItemProvider;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.processeditor.xpdl2.properties.ActivityDataFieldItemProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ActivityScriptContentProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.script.CompositeTreeContentProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.validation.bpmn.developer.internal.Messages;
import com.tibco.xpd.validation.bpmn.developer.providers.JavaMethodMappingRuleInfoProvider;
import com.tibco.xpd.validation.bpmn.rules.baserules.JavaScriptTypeCompatibilityResult;
import com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProvider;
import com.tibco.xpd.validation.bpmn.rules.baserules.ProcessDataMappingRuleInfoProvider;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * Validate Java service task output mapping.
 * 
 * @author aallway
 * @since 3.3 (21 Jun 2010)
 */
public abstract class AbstractJavaServiceOutputMappingRule extends
        AbstractDeveloperActivityMappingJavaScriptRule {

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRule#
     * activityIsApplicable(com.tibco.xpd.xpdl2.Activity)
     */
    @Override
    protected boolean objectIsApplicable(EObject objectToValidate) {
        String taskImplementationExtensionId =
                TaskObjectUtil
                        .getTaskImplementationExtensionId((Activity) objectToValidate);
        if (TaskImplementationTypeDefinitions.JAVA_SERVICE
                .equals(taskImplementationExtensionId)) {
            return true;
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractActivityMappingJavaScriptRule#checkTypeCompatibility(java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     * 
     * @param sourceObjectInTree
     * @param targetObjectInTree
     * @param mapping
     * @return
     */
    @Override
    protected JavaScriptTypeCompatibilityResult checkJavaScriptTypeCompatibility(
            Object sourceObjectInTree, Object targetObjectInTree, Object mapping) {

        JavaScriptTypeCompatibilityResult compatible =
                super.checkJavaScriptTypeCompatibility(sourceObjectInTree,
                        targetObjectInTree,
                        mapping);
        /*
         * XPD-6978: Validate further only when Super has not validated any
         * scenario
         */
        if (JavaScriptTypeCompatibilityResult.UNHANDLED_SCENARIO
                .equals(compatible)) {

            compatible =
                    JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_FAILED;

            if (targetObjectInTree instanceof ConceptPath
                    && sourceObjectInTree instanceof JavaMethodParameter) {

                ConceptPath cp = (ConceptPath) targetObjectInTree;

                JavaMethodParameter javaMethodParameter =
                        (JavaMethodParameter) sourceObjectInTree;

                Object item = cp.getItem();

                if (item != null) {

                    compatible =
                            isJavaTypeAssignableToProcessDataType(javaMethodParameter,
                                    item) ? JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_SUCCEEDED
                                    : JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_FAILED;
                }

            } else if (sourceObjectInTree instanceof ScriptInformation
                    && targetObjectInTree instanceof ConceptPath) {

                ConceptPath cp = (ConceptPath) targetObjectInTree;

                IScriptRelevantData returnType =
                        getCachedScriptReturnType(mapping);

                if (returnType != null
                        && returnType.getType() != null
                        && !returnType.getType()
                                .equals(JsConsts.UNDEFINED_DATA_TYPE)) {

                    Object item = cp.getItem();

                    if (item != null) {

                        compatible =
                                isScriptTypeAssignableToProcessDataType(returnType,
                                        item) ? JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_SUCCEEDED
                                        : JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_FAILED;
                    }
                }
            }
        }
        return compatible;
    }

    /**
     * Compare the data type of the process data and the java method input
     * parameter.
     * 
     * @param javaMethodParameter
     * @param processDataObject
     *            This will be the value of ConceptPath.getItem() - i.e. usually
     *            {@link ProcessRelevantData} or BOM {@link Property}.
     * 
     * @return true if compatible else false.
     */
    protected abstract boolean isJavaTypeAssignableToProcessDataType(
            JavaMethodParameter javaMethodParameter, Object processDataObject);

    /**
     * Compare the data type of the process data and the java method input
     * parameter.
     * 
     * @param javaMethodParameter
     * @param processDataObject
     *            This will be the value of ConceptPath.getItem() - i.e. usually
     *            {@link ProcessRelevantData} or BOM {@link Property}.
     * 
     * @return true if compatible else false.
     */
    protected boolean isJavaTypeAssignableToProcessDataType(
            JavaMethodParameter javaMethodParameter, Object conceptPath,
            Object mapping) {

        if (conceptPath instanceof ConceptPath) {
            return isJavaTypeAssignableToProcessDataType(javaMethodParameter,
                    ((ConceptPath) conceptPath).getItem());
        }

        return true;
    }

    /**
     * Compare the data type of the script type and the java method input
     * parameter.
     * 
     * @param scriptType
     * @param processDataObject
     *            This will be the value of ConceptPath.getItem() - i.e. usually
     *            {@link ProcessRelevantData} or BOM {@link Property}.
     * 
     * @return true if compatible else false.
     */
    protected abstract boolean isScriptTypeAssignableToProcessDataType(
            IScriptRelevantData scriptType, Object processDataObject);

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRule#
     * createMappingsContentProvider(com.tibco.xpd.xpdl2.Activity)
     */
    @Override
    protected IStructuredContentProvider createMappingsContentProvider(
            EObject objectToValidate) {
        PojoMappingItemProvider mappingContentProvider =
                new PojoMappingItemProvider(MappingDirection.OUT);
        return mappingContentProvider;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#getMappingDirection()
     * 
     * @return
     */
    @Override
    protected MappingDirection getMappingDirection() {
        return MappingDirection.OUT;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRule#
     * createSourceInfoProvider(com.tibco.xpd.xpdl2.Activity)
     */
    @Override
    protected MappingRuleContentInfoProvider createSourceInfoProvider(
            EObject objectToValidate) {
        ITreeContentProvider contentProvider =
                new CompositeTreeContentProvider(new PojoItemProvider(
                        MappingDirection.OUT),
                        new ActivityScriptContentProvider(MappingDirection.OUT));
        JavaMethodMappingRuleInfoProvider sourceInfoProvider =
                new JavaMethodMappingRuleInfoProvider(contentProvider);
        return sourceInfoProvider;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRule#
     * createTargetInfoProvider(com.tibco.xpd.xpdl2.Activity)
     */
    @Override
    protected MappingRuleContentInfoProvider createTargetInfoProvider(
            EObject objectToValidate) {
        ActivityDataFieldItemProvider contentProvider =
                new ActivityDataFieldItemProvider(MappingDirection.OUT);
        ProcessDataMappingRuleInfoProvider targetInfoProvider =
                new ProcessDataMappingRuleInfoProvider(contentProvider);
        return targetInfoProvider;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRule#
     * getMappingTypeDescription(com.tibco.xpd.xpdl2.Activity)
     */
    @Override
    protected String getMappingTypeDescription(EObject objectToValidate) {
        return Messages.MappingRule_OuputFromService_label;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRule#
     * isConcatenationMappingSupported()
     */
    @Override
    protected boolean isConcatenationMappingSupported() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRule#
     * isConcatenationMappingSupportedForTarget(java.lang.Object)
     */
    @Override
    protected boolean isConcatenationMappingSupportedForTarget(
            Object targetObject) {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRule#
     * isMappingFromSourceLevelSupported(java.lang.Object)
     */
    @Override
    protected boolean isMappingFromSourceLevelSupported(
            Object sourceObjectInTree) {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRule#
     * isMappingToTargetLevelSupported(java.lang.Object)
     */
    @Override
    protected boolean isMappingToTargetLevelSupported(Object targetObjectInTree) {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRule#
     * isMultiToMultiSupported(java.lang.Object, java.lang.Object)
     */
    @Override
    protected boolean isMultiToMultiSupported(Object multiInstanceSource,
            Object singleInstanceTarget) {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRule#
     * isMultiToSingleSupported(java.lang.Object, java.lang.Object)
     */
    @Override
    protected boolean isMultiToSingleSupported(Object multiInstanceSource,
            Object singleInstanceTarget) {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRule#
     * isPartialMappingCompletionSupported(java.lang.Object)
     */
    @Override
    protected boolean isPartialMappingCompletionSupported(
            Object targetObjectInTree) {
        /*
         * Assume that target process data (or parent of target if complex data)
         * will have already been created so no need to map the entire required
         * content of the target object.
         */
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRule#
     * isScriptMappingSupported()
     */
    @Override
    protected boolean isScriptMappingSupported() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRule#
     * isScriptMappingSupportedForTarget(java.lang.Object)
     */
    @Override
    protected boolean isScriptMappingSupportedForTarget(Object targetObject) {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRule#
     * isSingleToMultiSupported(java.lang.Object, java.lang.Object)
     */
    @Override
    protected boolean isSingleToMultiSupported(Object singleInstanceSource,
            Object multiInstanceTarget) {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRule#
     * performAdditionalMappingValidation(java.lang.Object, java.lang.Object,
     * java.lang.Object)
     */
    @Override
    protected void performAdditionalMappingValidation(Object mapping,
            Object sourceObjectInTree, Object targetObjectInTree) {
        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRule#
     * performAdditionalMappingsValidation(java.util.Collection)
     */
    @Override
    protected void performAdditionalMappingsValidation(Object objectToValidate,
            Collection<Object> mappings) {
        return;
    }

    @Override
    protected String getDefaultScriptDestination() {
        return ProcessJsConsts.JSCRIPT_DESTINATION;
    }

    @Override
    protected String getScriptGrammar(EObject objectToValidate) {
        return ScriptGrammarFactory.JAVASCRIPT;
    }

    @Override
    protected String getScriptType() {
        return ProcessJsConsts.JAVASERVICE_TASK;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractActivityMappingJavaScriptRule#isUntypedScriptListToArrayMapping(java.lang.Object,
     *      java.lang.Object)
     * 
     * @param sourceObjectInTree
     * @param targetObjectInTree
     * @return
     */
    @Override
    protected boolean isUntypedScriptListToArrayMapping(
            Object sourceObjectInTree, Object targetObjectInTree, Object mapping) {
        if (sourceObjectInTree instanceof ScriptInformation
                && targetObjectInTree instanceof JavaMethodParameter) {

            JavaMethodParameter jp = (JavaMethodParameter) targetObjectInTree;
            IScriptRelevantData returnType = getCachedScriptReturnType(mapping);

            if (returnType != null
                    && returnType.getType() != null
                    && !returnType.getType()
                            .equals(JsConsts.UNDEFINED_DATA_TYPE)) {

                if (returnType.isArray()
                        && PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME
                                .equalsIgnoreCase(returnType.getName())
                        && jp.isArray()) {

                    return true;

                }
            }
        } else if (sourceObjectInTree instanceof ScriptInformation
                && targetObjectInTree instanceof ConceptPath) {

            // XPD-7190: Target is a ConceptPath, use that to check array flag

            ConceptPath cp = (ConceptPath) targetObjectInTree;

            IScriptRelevantData returnType = getCachedScriptReturnType(mapping);

            if (returnType != null
                    && returnType.getType() != null
                    && !returnType.getType()
                            .equals(JsConsts.UNDEFINED_DATA_TYPE)) {

                if (returnType.isArray()
                        && PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME
                                .equalsIgnoreCase(returnType.getName())
                        && cp.isArray()) {

                    return true;

                }
            }
        }
        return false;
    }

}
