/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.bx.validation.rules.mapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;
import com.tibco.xpd.mapper.MapperContentProvider;
import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.validation.bpmn.developer.baserules.AbstractProcessDataToWebSvcRuleForJS;
import com.tibco.xpd.validation.bpmn.rules.baserules.JavaScriptTypeCompatibilityResult;
import com.tibco.xpd.validation.bpmn.rules.baserules.ProcessDataMappingCompatibilityUtil;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.FormalParameter;

/**
 * Validate the mappings for process Data to Web Service java script content.
 * <p>
 * i.e. This validate service invocation input mappings (ServiceTask &
 * Send-one-way SendTask/EndEvent) AND reply activities/throw end error for
 * non-generated request activities.
 * 
 * @author aallway
 * @since 3.3 (23 Jun 2010)
 */
public class ProcessDataToWebSvcJavaScriptRule extends
        AbstractProcessDataToWebSvcRuleForJS {

    private Activity currentActivity = null;

    /*
     * List of Warning's of all issues returned for each mappings for current
     * activity.
     */
    private WebServiceJavaScriptMappingIssue[] lastDataMappingJSCompatibilityIssues =
            null;

    private boolean currentMappingIsFromSuperToSubType = false;

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.developer.baserules.
     * AbstractProcessDataToWebSvcJavaScriptRule
     * #activityIsApplicable(com.tibco.xpd.xpdl2.Activity)
     */
    @Override
    public boolean objectIsApplicable(EObject objectToValidate) {
        /* Cache activity currently being validated. */
        /**
         * It's ok to cast to activity as that is the only thing we return from
         * #getObjectsToValidates()
         * 
         * Sid - moved outside of objectIsApplicable() so that we can be used as
         * delegate.
         */
        currentActivity = (Activity) objectToValidate;
        if (super.objectIsApplicable(objectToValidate)) {

            return true;
        }

        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.developer.baserules.
     * AbstractProcessDataToWebSvcJavaScriptRule
     * #activityValidateDone(com.tibco.xpd.xpdl2.Activity)
     */
    @Override
    public void objectValidateDone(EObject objectToValidate) {
        /* Throw away cached current activity being validated. */
        currentActivity = null;
        super.objectValidateDone(objectToValidate);
        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRule#
     * checkTypeCompatibility(java.lang.Object, java.lang.Object)
     */
    @Override
    public JavaScriptTypeCompatibilityResult checkJavaScriptTypeCompatibility(
            Object sourceObjectInTree, Object targetObjectInTree, Object mapping) {

        JavaScriptTypeCompatibilityResult ret =
                super.checkJavaScriptTypeCompatibility(sourceObjectInTree,
                        targetObjectInTree,
                        mapping);
        /*
         * XPD-6978: Validate further only when Super has not validated any
         * scenario
         */
        if (JavaScriptTypeCompatibilityResult.UNHANDLED_SCENARIO.equals(ret)) {

            ret =
                    JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_FAILED;
            lastDataMappingJSCompatibilityIssues = null;

            /*
             * XPD-6031: Removed validation changes from XPD-5765 to prevent
             * case reference fields being mapped directly to Strings.
             */

            if (sourceObjectInTree instanceof ConceptPath
                    && targetObjectInTree instanceof ConceptPath
                    && mapping instanceof Mapping) {
                /*
                 * Use the type comparison checker from the original rule.
                 * 
                 * Currently this is designed specifically for the way the old
                 * JSWSMappingRule worked, but is also used by script
                 * validation.
                 */
                ret =
                        JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_SUCCEEDED;

                ConceptPath sourcePath = (ConceptPath) sourceObjectInTree;
                ConceptPath targetPath = (ConceptPath) targetObjectInTree;

                lastDataMappingJSCompatibilityIssues =
                        JSTypeComparisonHelper
                                .getTypeCompatabilityIssues(currentActivity,
                                        sourcePath,
                                        targetPath);

                /*
                 * A number of different errors / warning are returned, we are
                 * only interested in 'types don't match' error at the mo but we
                 * should save the others for later when we're asked for any
                 * other things.
                 */
                if (lastDataMappingJSCompatibilityIssues != null) {

                    for (WebServiceJavaScriptMappingIssue issue : lastDataMappingJSCompatibilityIssues) {

                        if (WebServiceJavaScriptMappingIssue.TYPES_DONT_MATCH
                                .equals(issue)) {

                            /* XPD-2181: Check super type to sub type mapping */
                            if (ProcessDataMappingCompatibilityUtil
                                    .checkSubTypeCompatibility((ConceptPath) sourceObjectInTree,
                                            (ConceptPath) targetObjectInTree,
                                            DirectionType.IN_LITERAL)) {

                                currentMappingIsFromSuperToSubType = true;

                                if (currentMappingIsFromSuperToSubType) {

                                    return JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_SUCCEEDED;
                                }
                            }
                            /*
                             * ok, this is the main result we were looking for.
                             */
                            ret =
                                    JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_FAILED;
                            break;
                        }
                    }
                }

            } else if (sourceObjectInTree instanceof ScriptInformation
                    && targetObjectInTree instanceof ConceptPath
                    && mapping instanceof Mapping) {

                ConceptPath targetPath = (ConceptPath) targetObjectInTree;

                IScriptRelevantData returnType =
                        getCachedScriptReturnType(mapping);

                if (returnType != null
                        && returnType.getType() != null
                        && !returnType.getType()
                                .equals(JsConsts.UNDEFINED_DATA_TYPE)) {
                    /*
                     * Use the type comparison checker from the original rule.
                     * 
                     * Currently this is designed specifically for the way the
                     * old JSWSMappingRule worked, but is also used by script
                     * validation.
                     */
                    ret =
                            JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_SUCCEEDED;

                    lastDataMappingJSCompatibilityIssues =
                            JSTypeComparisonHelper
                                    .getTypeCompatabilityIssues(currentActivity,
                                            returnType,
                                            targetPath);

                    /*
                     * A number of different errors / warning are returned, we
                     * are only interested in 'types don't match' error at the
                     * mo but we should save the others for later when we're
                     * asked for any other things.
                     */
                    if (lastDataMappingJSCompatibilityIssues != null) {

                        for (WebServiceJavaScriptMappingIssue issue : lastDataMappingJSCompatibilityIssues) {

                            if (WebServiceJavaScriptMappingIssue.TYPES_DONT_MATCH
                                    .equals(issue)) {
                                /*
                                 * XPD-2181: Check super type to sub type
                                 * mapping
                                 */
                                if (ProcessDataMappingCompatibilityUtil
                                        .checkSubTypeCompatibility(returnType,
                                                (ConceptPath) targetObjectInTree)) {

                                    currentMappingIsFromSuperToSubType = true;

                                    if (currentMappingIsFromSuperToSubType) {
                                        return JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_SUCCEEDED;
                                    }
                                }
                                /*
                                 * ok, this is the main result we were looking
                                 * for.
                                 */
                                ret =
                                        JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_FAILED;
                                break;
                            }
                        }
                    }
                } else {
                    ret =
                            JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_SUCCEEDED;
                }
            }
        }
        return ret;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.developer.baserules.
     * AbstractProcessDataToWebSvcJavaScriptRule
     * #performAdditionalMappingValidation(java.lang.Object, java.lang.Object,
     * java.lang.Object)
     */
    @Override
    protected void performAdditionalMappingValidation(Object mapping,
            Object sourceObjectInTree, Object targetObjectInTree) {

        super.performAdditionalMappingValidation(mapping,
                sourceObjectInTree,
                targetObjectInTree);

        performAdditionalTypeCompatibilityRules(sourceObjectInTree,
                targetObjectInTree,
                mapping);

        return;
    }

    /**
     * During {@link #checkJavaScriptTypeCompatibility(Object, Object, Object)}
     * some data is loaded ready for processing until the
     * {@link #performAdditionalMappingValidation(Object, Object, Object)}
     * phase.
     * <p>
     * This method deals specifically with the type compatibility issues raised
     * as part of this phase.
     * 
     * @param sourceObjectInTree
     * @param targetObjectInTree
     * @param mapping
     */
    public void performAdditionalTypeCompatibilityRules(
            Object sourceObjectInTree, Object targetObjectInTree, Object mapping) {
        /*
         * Raise any issues found during type compatibility check earlier for
         * this data mapping.
         */
        if (lastDataMappingJSCompatibilityIssues != null
                && lastDataMappingJSCompatibilityIssues.length > 0) {

            if (sourceObjectInTree instanceof ConceptPath
                    && targetObjectInTree instanceof ConceptPath
                    && mapping instanceof Mapping) {
                ConceptPath sourcePath = (ConceptPath) sourceObjectInTree;
                ConceptPath targetPath = (ConceptPath) targetObjectInTree;

                Object dataMapping = ((Mapping) mapping).getMappingModel();
                if (dataMapping instanceof DataMapping) {
                    /*
                     * XPD-2181: add warning message if it is super type to sub
                     * type mapping
                     */
                    if (currentMappingIsFromSuperToSubType) {
                        addIssue(WebServiceJavaScriptMappingIssue.SUPER_TYPE_SUB_TYPE_MAPPING
                                .getValue(),
                                (DataMapping) dataMapping,
                                Collections.EMPTY_LIST,
                                createAdditionalInfo(MapperContentProvider.DATAMAPPING_SOURCE_URI_ISSUEINFO,
                                        sourcePath.getPath(),
                                        MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
                                        targetPath.getPath()));
                    } else {
                        for (WebServiceJavaScriptMappingIssue issue : lastDataMappingJSCompatibilityIssues) {
                            /*
                             * Add any other warnings that the type checker
                             * found.
                             */
                            if (isInterestingExtraJSCompatibilityIssue(issue)) {
                                addIssue(issue.getValue(),
                                        (DataMapping) dataMapping,
                                        Collections.EMPTY_LIST,
                                        createAdditionalInfo(MapperContentProvider.DATAMAPPING_SOURCE_URI_ISSUEINFO,
                                                sourcePath.getPath(),
                                                MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
                                                targetPath.getPath()));
                            }
                        }

                    }
                }
            } else if (sourceObjectInTree instanceof ScriptInformation
                    && targetObjectInTree instanceof ConceptPath
                    && mapping instanceof Mapping) {
                /*
                 * XPD-2181: add warning message if it is super type to sub type
                 * mapping
                 */
                if (currentMappingIsFromSuperToSubType) {
                    Object dataMapping = ((Mapping) mapping).getMappingModel();
                    ConceptPath targetPath = (ConceptPath) targetObjectInTree;

                    ArrayList<String> messages = new ArrayList<String>();

                    messages.add(targetPath.getPath());

                    Map<String, String> additionalInfo =
                            Collections
                                    .singletonMap(MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
                                            targetPath.getPath());
                    addIssue(WebServiceJavaScriptMappingIssue.SUPER_TYPE_SUB_TYPE_MAPPING
                            .getValue(),
                            (DataMapping) dataMapping,
                            messages,
                            additionalInfo);
                }
            }
        }

        if (sourceObjectInTree instanceof ConceptPath
                && targetObjectInTree instanceof ConceptPath) {
            ConceptPath src = (ConceptPath) sourceObjectInTree;
            ConceptPath trg = (ConceptPath) targetObjectInTree;
            Object itemSrc = src.getItem();
            Object itemTrg = trg.getItem();

            // If we are mapping arrays and the source array represents a
            // Property within a XSD sequence with multiplicity, we want to
            // display a warning that any ordering information is effectively
            // lost.
            if (itemSrc instanceof Property && itemTrg instanceof Property) {
                Property srcProp = (Property) itemSrc;
                Property trgProp = (Property) itemTrg;

                if ((srcProp.getUpper() > 1 || srcProp.getUpper() == -1)
                        && (trgProp.getUpper() > 1 || trgProp.getUpper() == -1)) {
                    if (XSDUtil.isContainedInSequenceWithMultiplicity(srcProp)) {
                        Object dataMapping =
                                ((Mapping) mapping).getMappingModel();

                        ArrayList<String> messages = new ArrayList<String>();

                        messages.add(trg.getPath());

                        Map<String, String> additionalInfo =
                                Collections
                                        .singletonMap(MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
                                                trg.getPath());

                        addIssue(WebServiceJavaScriptMappingIssue.XSD_SEQUENCE_MULTIPLICITY_ORDER
                                .getValue(),
                                (DataMapping) dataMapping,
                                messages,
                                additionalInfo);
                    }
                }
            }
        }

        lastDataMappingJSCompatibilityIssues = null;

        /* XPD-4291: Mapping to 'xsd any' typed message part is not supported */
        ConceptPath target = (ConceptPath) targetObjectInTree;
        if (target.getItem() instanceof FormalParameter
                && PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME.equals(target
                        .getType().getName())) {

            Map<String, String> additionalInfo =
                    Collections
                            .singletonMap(MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
                                    target.getPath());

            addIssue(WebServiceJavaScriptMappingIssue.XSD_ANY_MAPPING_TO_INVALID
                    .getValue(),
                    getModelObjectForMapping(mapping),
                    Collections.singletonList(target.getName()),
                    additionalInfo);
        }
    }

    /**
     * @param issue
     * @return <code>true</code> if the given extra validation issue one we want
     *         to raise some are duplicates of the issues already raised by
     *         AbstractMappingRule).
     */
    private boolean isInterestingExtraJSCompatibilityIssue(
            WebServiceJavaScriptMappingIssue issue) {
        if (
        // Invalid type = AbstractMappingRule source or target missing.
        !WebServiceJavaScriptMappingIssue.INVALID_TYPE.equals(issue)
                // Various rules that all amount to AbstractMappingRule
                // mandatory target not mapped.
                && !WebServiceJavaScriptMappingIssue.ATTRIBUTE_REQUIRES_MAPPING
                        .equals(issue)
                && !WebServiceJavaScriptMappingIssue.ELEMENT_REQUIRES_MAPPING
                        .equals(issue)
                && !WebServiceJavaScriptMappingIssue.OBJECT_REQUIRES_MAPPING
                        .equals(issue)
                && !WebServiceJavaScriptMappingIssue.PARAMETER_REQUIRES_MAPPING
                        .equals(issue)
                && !WebServiceJavaScriptMappingIssue.SIMPLE_CONTENT_VALUE_NEEDS_MAPPING
                        .equals(issue)
                // Duplicates of AbstractMappingRule single/multi
                // instance mapping issues.
                && !WebServiceJavaScriptMappingIssue.ARRAY_ARRAY_CHILDREN_MAPPING
                        .equals(issue)
                // Duplicate of ABstractMappingRule can't map complex parent and
                // descendants.
                && !WebServiceJavaScriptMappingIssue.CHILD_SHOULDNT_BE_MAPPED
                        .equals(issue)
                // Duplicate of AbstractMappingRule concatenation not supported.
                && !WebServiceJavaScriptMappingIssue.CONCATENATION_NOT_SUPPORTED
                        .equals(issue)
                // Duplicates the AbtractMappingRule checkTypeCompatibility
                // above.
                && !WebServiceJavaScriptMappingIssue.TYPES_DONT_MATCH
                        .equals(issue)) {
            return true;
        }

        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.developer.baserules.
     * AbstractWebSvcJavaScriptToProcessDataRule
     * #isMappingFromSourceLevelSupported(java.lang.Object)
     */
    @Override
    protected boolean isMappingFromSourceLevelSupported(
            Object sourceObjectInTree) {
        /*
         * N2 currently does not support mapping to / from individual elements
         * in a sequence.
         */
        if (sourceObjectInTree instanceof ConceptPath) {
            ConceptPath sourcePath = (ConceptPath) sourceObjectInTree;

            if (sourcePath.isArrayItem()) {
                return false;
            }
        }

        return super.isMappingFromSourceLevelSupported(sourceObjectInTree);
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.developer.baserules.
     * AbstractWebSvcJavaScriptToProcessDataRule
     * #isMappingToTargetLevelSupported(java.lang.Object)
     */
    @Override
    protected boolean isMappingToTargetLevelSupported(Object targetObjectInTree) {
        /*
         * N2 currently does not support mapping to / from individual elements
         * in a sequence.
         */
        if (targetObjectInTree instanceof ConceptPath) {
            ConceptPath targetPath = (ConceptPath) targetObjectInTree;

            if (targetPath.isArrayItem()) {
                return false;
            }
        }
        return super.isMappingToTargetLevelSupported(targetObjectInTree);
    }

    @Override
    protected boolean isConcatenationMappingSupported() {
        return false;
    }

    @Override
    protected boolean isConcatenationMappingSupportedForTarget(
            Object targetObject) {
        return false;
    }

    @Override
    protected boolean isMultiToMultiSupported(Object multiInstanceSource,
            Object singleInstanceTarget) {
        return true;
    }

    @Override
    protected boolean isMultiToSingleSupported(Object multiInstanceSource,
            Object singleInstanceTarget) {
        return false;
    }

    @Override
    protected boolean isScriptMappingSupported() {
        return true;
    }

    @Override
    protected boolean isScriptMappingSupportedForTarget(Object targetObject) {
        return true;
    }

    @Override
    protected boolean isSingleToMultiSupported(Object singleInstanceSource,
            Object multiInstanceTarget) {
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
