/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.baserules;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredContentProvider;
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
 * Validate Java Service Task input mappings.
 * <p>
 * Does all the generic stuff leaving only the process data type to java type
 * compatibility to deal with (which is very likely to be different for
 * different destinations).
 * 
 * @author aallway
 * @since 3.3 (16 Jun 2010)
 */
public abstract class AbstractJavaServiceInputMappingRule extends
        AbstractDeveloperActivityMappingJavaScriptRule {

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRules#
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
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractActivityMappingJavaScriptRule#doAditionalTypeCompabilityCheck(java.lang.Object,
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

            if (sourceObjectInTree instanceof ConceptPath
                    && targetObjectInTree instanceof JavaMethodParameter) {

                ConceptPath cp = (ConceptPath) sourceObjectInTree;

                JavaMethodParameter jp =
                        (JavaMethodParameter) targetObjectInTree;

                Object item = cp.getItem();
                if (item != null) {
                    compatible =
                            isProcessDataTypeAssignableToJavaType(item, jp) ? JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_SUCCEEDED
                                    : JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_FAILED;
                }

            } else if (sourceObjectInTree instanceof ScriptInformation
                    && targetObjectInTree instanceof JavaMethodParameter) {

                JavaMethodParameter jp =
                        (JavaMethodParameter) targetObjectInTree;

                IScriptRelevantData returnType =
                        getCachedScriptReturnType(mapping);

                if (returnType != null
                        && returnType.getType() != null
                        && !returnType.getType()
                                .equals(JsConsts.UNDEFINED_DATA_TYPE)) {

                    compatible =
                            isScriptDataTypeAssignableToJavaType(returnType, jp) ? JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_SUCCEEDED
                                    : JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_FAILED;

                } else {
                    compatible =
                            JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_SUCCEEDED;
                }
            }
        }
        return compatible;
    }

    /**
     * Compare the data type of the process data and the java method input
     * parameter.
     * 
     * @param processDataObject
     *            This will be the value of ConceptPath.getItem() - i.e. usually
     *            {@link ProcessRelevantData} or BOM {@link Property}.
     * @param javaMethodParameter
     * 
     * @return true if compatible else false.
     */
    protected abstract boolean isProcessDataTypeAssignableToJavaType(
            Object processDataObject, JavaMethodParameter javaMethodParameter);

    /**
     * Compare the data type of the script and the java method input parameter.
     * 
     * @param scriptType
     *            This will be the value of the return type of the script
     * @param javaMethodParameter
     * 
     * @return true if compatible else false.
     */
    protected abstract boolean isScriptDataTypeAssignableToJavaType(
            IScriptRelevantData scriptType,
            JavaMethodParameter javaMethodParameter);

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#getMappingDirection()
     * 
     * @return
     */
    @Override
    protected MappingDirection getMappingDirection() {
        return MappingDirection.IN;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRules#
     * createSourceInfoProvider(com.tibco.xpd.xpdl2.Activity)
     */
    @Override
    protected MappingRuleContentInfoProvider createSourceInfoProvider(
            EObject objectToValidate) {
        CompositeTreeContentProvider contentProvider =
                new CompositeTreeContentProvider(
                        new ActivityDataFieldItemProvider(MappingDirection.IN),
                        new ActivityScriptContentProvider(MappingDirection.IN));
        ProcessDataMappingRuleInfoProvider sourceInfoProvider =
                new ProcessDataMappingRuleInfoProvider(contentProvider);
        return sourceInfoProvider;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRules#
     * createTargetInfoProvider(com.tibco.xpd.xpdl2.Activity)
     */
    @Override
    protected MappingRuleContentInfoProvider createTargetInfoProvider(
            EObject objectToValidate) {
        PojoItemProvider contentProvider =
                new PojoItemProvider(MappingDirection.IN);

        JavaMethodMappingRuleInfoProvider targetInfoProvider =
                new JavaMethodMappingRuleInfoProvider(contentProvider);
        return targetInfoProvider;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRules#
     * getMappingTypeDescription(com.tibco.xpd.xpdl2.Activity)
     */
    @Override
    protected String getMappingTypeDescription(EObject objectToValidate) {
        return Messages.MappingRule_InputToService_label;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRules#
     * getMappingsContentProvider(com.tibco.xpd.xpdl2.Activity)
     */
    @Override
    protected IStructuredContentProvider createMappingsContentProvider(
            EObject objectToValidate) {
        PojoMappingItemProvider mappingContentProvider =
                new PojoMappingItemProvider(MappingDirection.IN);
        return mappingContentProvider;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRules#
     * isConcatenationMappingSupported()
     */
    @Override
    protected boolean isConcatenationMappingSupported() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRules#
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
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRules#
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
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRules#
     * isMappingFromTargetLevelSupported(java.lang.Object)
     */
    @Override
    protected boolean isMappingToTargetLevelSupported(Object targetObjectInTree) {
        if (targetObjectInTree instanceof JavaMethodParameter) {
            return true;
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRules#
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
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRules#
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
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRules#
     * isScriptMappingSupported()
     */
    @Override
    protected boolean isScriptMappingSupported() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRules#
     * isScriptMappingSupportedForTarget(java.lang.Object)
     */
    @Override
    protected boolean isScriptMappingSupportedForTarget(Object targetObject) {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRules#
     * isPartialMappingCompletionSupported(java.lang.Object)
     */
    @Override
    protected boolean isPartialMappingCompletionSupported(
            Object targetObjectInTree) {
        /* All mandatory content must be mapped. */
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRules#
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
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRules#
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
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRules#
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
        }
        return false;
    }

}
