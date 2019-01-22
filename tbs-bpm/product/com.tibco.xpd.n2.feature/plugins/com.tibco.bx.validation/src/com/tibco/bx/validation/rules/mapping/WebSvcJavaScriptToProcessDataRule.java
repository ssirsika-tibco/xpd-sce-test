/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.bx.validation.rules.mapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.wsdl.Definition;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Property;

import com.tibco.bx.validation.rules.mapping.helpers.BaseScriptToConceptPathMappingsHelper;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.implementer.resources.xpdl2.utils.JavaScriptWsdlPathToXPathConverter;
import com.tibco.xpd.implementer.script.Xpdl2WsdlUtil;
import com.tibco.xpd.mapper.MapperContentProvider;
import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.WsdlPartConceptPath;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.validation.bpmn.developer.baserules.AbstractWebSvcToProcessDataRuleForJS;
import com.tibco.xpd.validation.bpmn.rules.baserules.JavaScriptTypeCompatibilityResult;
import com.tibco.xpd.validation.bpmn.rules.baserules.ProcessDataMappingCompatibilityUtil;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;

/**
 * Validate the mappings for Web Service java script to Process Data content.
 * <p>
 * i.e. This validates service invocation output mappings (ServiceTask output
 * from service) AND non-generated request activities input (input to process)
 * 
 * @author aallway
 * @since 3.3 (23 Jun 2010)
 */
public class WebSvcJavaScriptToProcessDataRule extends
        AbstractWebSvcToProcessDataRuleForJS {

    private static final String ISSUE_MISSING_NAMESPACE_REFERENCED_IN_CORRELATION_MAPPING =
            "bx.missingNamespaceReferencedInCorrelationMapping"; //$NON-NLS-1$

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

        JavaScriptTypeCompatibilityResult result =
                super.checkJavaScriptTypeCompatibility(sourceObjectInTree,
                        targetObjectInTree,
                        mapping);
        /*
         * XPD-6978: Validate further only when Super has not validated any
         * scenario
         */
        if (JavaScriptTypeCompatibilityResult.UNHANDLED_SCENARIO.equals(result)) {

            result =
                    JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_FAILED;

            lastDataMappingJSCompatibilityIssues = null;

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
                result =
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
                            result =
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
                    result =
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
                                result =
                                        JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_FAILED;
                                break;
                            }
                        }

                    } else {
                        /*
                         * XPD-2174: if a user defined script with type being
                         * one of the member types of the target union is mapped
                         * from wsdl to process we must complain such mapping.
                         * (process to wsdl member type to target union mapping
                         * is fine)
                         */
                        if (BaseScriptToConceptPathMappingsHelper
                                .isNonUnionToUnionMapping(currentActivity,
                                        returnType,
                                        targetPath)) {

                            return JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_FAILED;
                        }
                    }
                } else {
                    result =
                            JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_SUCCEEDED;
                }
            }
        }
        return result;
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

        performAdditionalCorrelationMappingRules(sourceObjectInTree,
                targetObjectInTree,
                mapping);
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

        lastDataMappingJSCompatibilityIssues = null;

        performAdditionalCorrelationMappingRules(sourceObjectInTree,
                targetObjectInTree,
                mapping);

        /* XPD-4291: Mapping from 'xsd any' typed message part is not supported */
        if (sourceObjectInTree instanceof ConceptPath) {
            ConceptPath source = (ConceptPath) sourceObjectInTree;
            if (source.getItem() instanceof FormalParameter
                    && (source.getType() != null && PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME
                            .equals(source.getType().getName()))) {

                Map<String, String> additionalInfo =
                        Collections
                                .singletonMap(MapperContentProvider.DATAMAPPING_SOURCE_URI_ISSUEINFO,
                                        source.getPath());

                addIssue(WebServiceJavaScriptMappingIssue.XSD_ANY_MAPPING_FROM_INVALID
                        .getValue(),
                        getModelObjectForMapping(mapping),
                        Collections.singletonList(source.getName()),
                        additionalInfo);
            }
        }
        return;
    }

    /**
     * Sid XPD-3821. For correlation mappings we must check for any reference to
     * namespaces that are not listed in the WSDL definition header element.
     * 
     * This is because XMA BPM Process-Engine only supports XPath correlation
     * mappings and therefore the Xpdl2Bpel converter must convert the
     * JavaScript mappings to XPath expressions and add them as PropertyAlias
     * elements to the copy of WSDL in the .processOut BPEL outputfolder.
     * 
     * When this conversion is done, in order to function correctly then all the
     * namespaces referenced in the source path for the mapping must be defined
     * in the WSDL definition header element.
     * 
     * @param sourceObjectInTree
     * @param targetObjectInTree
     * @param mapping
     */
    public void performAdditionalCorrelationMappingRules(
            Object sourceObjectInTree, Object targetObjectInTree, Object mapping) {
        if (isCorrelationMapping(targetObjectInTree)
                && sourceObjectInTree instanceof ConceptPath) {

            DataMapping dataMapping = getDataMapping(mapping);
            if (dataMapping != null) {

                ConceptPath sourcePath = (ConceptPath) sourceObjectInTree;

                checMissingNamespacesInCorrelationMapping(dataMapping,
                        sourcePath);
            }
        }
    }

    /**
     * * Sid XPD-3821. For correlation mappings we must check for any reference
     * to namespaces that are not listed in the WSDL definition header element.
     * <p>
     * This is because AMX BPM Process-Engine only supports XPath correlation
     * mappings and therefore the Xpdl2Bpel converter must convert the
     * JavaScript mappings to XPath expressions and add them as PropertyAlias
     * elements to the copy of WSDL in the .processOut BPEL output folder.
     * <p>
     * When this conversion is done, in order to function correctly then all the
     * namespaces referenced in the source path for the mapping must be defined
     * in the WSDL definition header element.
     * 
     * @param dataMapping
     * @param sourcePath
     */
    protected void checMissingNamespacesInCorrelationMapping(
            DataMapping dataMapping, ConceptPath sourcePath) {
        // ... may have to change this when we actually find out what namespaces
        // go with what Elements.

        Definition wsdlDefinition =
                Xpdl2WsdlUtil.getWSDLDefinition(currentActivity);

        if (wsdlDefinition != null) {
            /* Get the namespaces defined on the definition element. */
            Map namespaces = wsdlDefinition.getNamespaces();

            if (namespaces != null) {
                Collection wsdlNamespaces = namespaces.values();

                /*
                 * Traverse backwards up thru source concept path and create the
                 * set of namespaces that will be referenced by the correlation
                 * mapping.
                 * 
                 * Do this until we get back to the root element (which we
                 * ignore because the XPath expression for correlation
                 * propertyAlias is message-part-relative.
                 */
                ConceptPath currentElementPath = sourcePath;

                boolean debug = false;
                if (debug) {
                    outputXPathXpressionForMapping(sourcePath,
                            wsdlDefinition,
                            namespaces);
                }

                Set<String> missingNamespaces = new HashSet<String>();

                while (currentElementPath != null
                        && !(currentElementPath instanceof WsdlPartConceptPath)) {
                    /* Ignore artifical nodes in path (like Choice) */
                    if (!currentElementPath.isArtificialConceptPath()
                            && currentElementPath.getItem() instanceof Property
                            && currentElementPath.isQualifiedSchemaProperty()) {
                        String schemaNamespaceForProperty =
                                currentElementPath
                                        .getSchemaNamespaceForProperty();

                        if (schemaNamespaceForProperty != null
                                && schemaNamespaceForProperty.length() > 0) {
                            if (!wsdlNamespaces
                                    .contains(schemaNamespaceForProperty)) {
                                missingNamespaces
                                        .add(schemaNamespaceForProperty);
                            }
                        }
                    }

                    currentElementPath = currentElementPath.getParent();
                }

                /* Report any missing namespaces. */
                for (String missingNamespace : missingNamespaces) {
                    ArrayList<String> messages = new ArrayList<String>();

                    messages.add(missingNamespace);

                    Map<String, String> additionalInfo =
                            Collections
                                    .singletonMap(MapperContentProvider.DATAMAPPING_SOURCE_URI_ISSUEINFO,
                                            sourcePath.getPath());
                    addIssue(ISSUE_MISSING_NAMESPACE_REFERENCED_IN_CORRELATION_MAPPING,
                            dataMapping,
                            messages,
                            additionalInfo);
                }
            }
        }
    }

    /**
     * @param sourcePath
     * @param wsdlDefinition
     * @param namespaces
     */
    private void outputXPathXpressionForMapping(ConceptPath sourcePath,
            Definition wsdlDefinition, Map namespaces) {
        /*
         * ALWAYS PUT the default namespce in map with no prefix this will cause
         * the converter to outpout non-prefixed elements in path for default
         * namespace elements - this matches the original Xpdl2Bpel standard and
         * that means we're backward compatible.
         */
        Map<String, String> nsprefmap = new HashMap<String, String>();

        for (Object object : namespaces.entrySet()) {
            Entry<String, String> entry = (Entry<String, String>) object;

            String namespace = entry.getValue();
            String prefix = entry.getKey();
            nsprefmap.put(namespace, prefix);
        }
        System.out.println(sourcePath.getPath() + "::  " //$NON-NLS-1$
                + JavaScriptWsdlPathToXPathConverter
                        .javaScriptWsdlPathToXPath(currentActivity,
                                sourcePath.getPath(),
                                true,
                                nsprefmap));
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
     * @seecom.tibco.xpd.validation.bpmn.developer.baserules.
     * AbstractWebSvcToProcessDataRuleForJS
     * #isSingleToMultiSupported(java.lang.Object, java.lang.Object)
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
     * isScriptMappingSupported()
     */
    @Override
    protected boolean isScriptMappingSupported() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRule#
     * isScriptMappingSupportedForTarget(java.lang.Object)
     */
    @Override
    protected boolean isScriptMappingSupportedForTarget(Object targetObject) {
        /*
         * XPD-3791 - prevent script mappings to correlation data in API request
         * activities.
         */
        if (ReplyActivityUtil.isIncomingRequestActivity(currentActivity)) {
            if (targetObject instanceof ConceptPath) {
                targetObject = ((ConceptPath) targetObject).getRoot().getItem();
            }
            if (targetObject instanceof DataField) {
                if (((DataField) targetObject).isCorrelation()) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * @param targetObject
     * @return <code>true</code> If the mapping is to a c
     */
    protected boolean isCorrelationMapping(Object targetObject) {
        if (ReplyActivityUtil.isIncomingRequestActivity(currentActivity)) {
            if (targetObject instanceof ConceptPath) {
                targetObject = ((ConceptPath) targetObject).getRoot().getItem();
            }
            if (targetObject instanceof DataField) {
                if (((DataField) targetObject).isCorrelation()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param mapping
     * @return xpdl DataMapping for mapping or <code>null</code> if mapping
     *         model is not data mapping.
     */
    private DataMapping getDataMapping(Object mapping) {
        if (mapping instanceof Mapping) {
            Object mappingModel = ((Mapping) mapping).getMappingModel();
            if (mappingModel instanceof DataMapping) {
                return (DataMapping) mappingModel;
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
