/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.bx.validation.rules.mapping;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.uml2.uml.Property;

import com.tibco.bx.validation.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.mapper.MapperContentProvider;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.SubProcMappingItemProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.SubProcParameterItemProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.validation.bpmn.rules.baserules.JavaScriptTypeCompatibilityResult;
import com.tibco.xpd.validation.bpmn.rules.baserules.MappingIssue;
import com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProvider;
import com.tibco.xpd.validation.bpmn.rules.baserules.ProcessDataMappingRuleInfoProvider;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Loop;

/**
 * AMX BPM Sub process to Main Process JavaScript Input mapping rule.
 * 
 * @author aallway
 * @since 21 Dec 2010
 */
public class SubProcessJSOutputMappingRule extends
        AbstractN2Process2ProcessJSOutputMappingRule {

    private static final String ISSUE_SINGLE_TO_MULTI_NONLOOP =
            "bx.subProcessNonArrayArrayMappingDisallowed"; //$NON-NLS-1$

    public static final String ISSUE_SINGLE_TO_MULTICHILD_NOT_SUPPORTED =
            "bx.miSubProcessNonArrayArrayDisallowedForChildSeq"; //$NON-NLS-1$

    public static final String ISSUE_MULTI_INSTANCE_TO_MULTI_INSTANCE_WITH_PARENT_MAPPING_NOT_SUPPORTED =
            "bx.SubProcessMultiInstanceToMultiInstaceWithParentDisallowedMapping"; //$NON-NLS-1$

    private MappingRuleContentInfoProvider sourceInfoProvider;

    private MappingRuleContentInfoProvider targetInfoProvider;

    /**
     * Timezone in date-time value must be set when mapping to date-time-zone
     * types (may cause run-time failure).
     */
    private static final String DATETIME_DATEIMEZONE_MAPPING_WARNING =
            "bx.dateTimeDateTimeZoneMappingWarning"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractProcessData2ProcessDataJSMappingRule#checkTypeCompatibility(java.lang.Object,
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
        /*
         * If source and target are compatible types, then check for mapping
         * DateTime -> DateTimeZone XPD-3479
         */
        if (!JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_FAILED
                .equals(compatible)) {

            ConceptPath source = null, target = null;
            Object sourceObject = getSourceObject(mapping);
            Object targetObject = getTargetObject(mapping);

            // Check for mapping DateTime -> DateTimeZone XPD-3479
            if (sourceObject instanceof ConceptPath)
                source = (ConceptPath) sourceObject;

            if (targetObject instanceof ConceptPath)
                target = (ConceptPath) targetObject;

            if (source != null && target != null) {

                String targetTypeName = null;

                Object retBasicType =
                        BasicTypeConverterFactory.INSTANCE.getBaseType(source
                                .getItem(), false);

                Boolean isDateTimeSource =
                        (retBasicType instanceof BasicType)
                                && BasicTypeType.DATETIME_LITERAL
                                        .equals(((BasicType) retBasicType)
                                                .getType());

                if (target.getItem() instanceof Property) {

                    targetTypeName =
                            ((Property) target.getItem()).getType().getName();
                }

                if (isDateTimeSource
                        && PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME
                                .equalsIgnoreCase(targetTypeName)) {
                    // Create warning issue to inform user the
                    // possibility of mapping failure at runtime if
                    // dateTime does not contain Zone.
                    addIssue(DATETIME_DATEIMEZONE_MAPPING_WARNING,
                            getModelObjectForMapping(mapping),
                            createMessageList(getMappingTypeDescription(getCurrentActivity()),
                                    getSourcePathDescription(sourceInfoProvider,
                                            mapping),
                                    getTargetPathDescription(targetInfoProvider,
                                            mapping)),
                            createAdditionalInfo(MapperContentProvider.DATAMAPPING_SOURCE_URI_ISSUEINFO,
                                    getSourcePath(sourceInfoProvider, mapping),
                                    MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
                                    getTargetPath(targetInfoProvider, mapping)));
                }
            }
        }
        return compatible;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractProcess2ProcessJSOutputMappingRule#doActivityIsApplicable(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    protected boolean doActivityIsApplicable(Activity activity) {
        /* Only validate sub-processes */
        if (TaskType.SUBPROCESS_LITERAL.equals(TaskObjectUtil
                .getTaskTypeStrict(activity))) {
            /* Only validate JavaScript mappings */
            String grammar =
                    ScriptGrammarFactory.getGrammarToUse(activity,
                            DirectionType.OUT_LITERAL);

            if (ScriptGrammarFactory.JAVASCRIPT.equals(grammar)) {
                /* No point validating if sub-process is not accessible. */
                EObject currentActivitySubProcOrIfc =
                        TaskObjectUtil.getSubProcessOrInterface(activity);

                if (currentActivitySubProcOrIfc != null) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractProcess2ProcessJSOutputMappingRule#doActivityValidationDone(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     */
    @Override
    protected void doActivityValidationDone(Activity activity) {
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractProcess2ProcessJSOutputMappingRule#doCreateMappingSourceItemContentProvider()
     * 
     * @return
     */
    @Override
    protected ITreeContentProvider doCreateMappingSourceItemContentProvider(
            Activity activity) {
        return new SubProcParameterItemProvider(MappingDirection.OUT);
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#createMappingsContentProvider(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param objectToValidate
     *            One of the objects returned by
     *            {@link #getObjectsToValidate(Process)}
     * @return
     */
    @Override
    protected IStructuredContentProvider createMappingsContentProvider(
            EObject objectToValidate) {
        return new SubProcMappingItemProvider(MappingDirection.OUT);
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
        Loop loop = getCurrentActivity().getLoop();
        if (loop != null && loop.getLoopType() != null) {
            return true;
        }

        return false;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractProcess2ProcessJSOutputMappingRule#getMappingTypeDescription(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param objectToValidate
     *            One of the objects returned by
     *            {@link #getObjectsToValidate(Process)}
     * @return
     */
    @Override
    protected String getMappingTypeDescription(EObject objectToValidate) {
        return Messages.SubProcessJSOutputMappingRule_SubProcessInputMappingRule_label;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#createIssue(MappingIssue,
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
         * Kapil XPD-3797: Validation against Map-from-sub-process mapping FROM
         * an array parameter TO a child attribute with multiplicity > 1 of a
         * class datafield. Important- This code should be removed when the
         * Issue BX-2326 is Fixed.
         */
        if (targetObjectInTree instanceof ConceptPath) {
            if (((ConceptPath) targetObjectInTree).isArray()
                    && ((ConceptPath) targetObjectInTree).getParent() != null) {
                addIssue(ISSUE_MULTI_INSTANCE_TO_MULTI_INSTANCE_WITH_PARENT_MAPPING_NOT_SUPPORTED,
                        getModelObjectForMapping(mapping),
                        createMessageList(getMappingTypeDescription(getCurrentActivity()),
                                getSourcePathDescription(sourceInfoProvider,
                                        mapping),
                                getTargetPathDescription(targetInfoProvider,
                                        mapping)),
                        createAdditionalInfo(MapperContentProvider.DATAMAPPING_SOURCE_URI_ISSUEINFO,
                                getSourcePath(sourceInfoProvider, mapping),
                                MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
                                getTargetPath(targetInfoProvider, mapping)));

            }
        }

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
        Loop loop = getCurrentActivity().getLoop();
        if (loop != null && loop.getLoopType() != null) {
            /*
             * It's a loop - if the source is single instance and the target is
             * multi-instance...
             */
            if (!((ProcessDataMappingRuleInfoProvider) sourceInfoProvider)
                    .isMultiInstancePublic(sourceObjectInTree)
                    && ((ProcessDataMappingRuleInfoProvider) targetInfoProvider)
                            .isMultiInstancePublic(targetObjectInTree)) {
                /*
                 * Then make sure it is not child sequence of complex BOM object
                 * (effectively this means "if it has a parent element" because
                 * a multi-instance object with a parrent can ONLY be a seuqnce
                 * element inside a BOM.
                 */
                if (((ProcessDataMappingRuleInfoProvider) targetInfoProvider)
                        .getContentProvider().getParent(targetObjectInTree) != null) {
                    addIssue(ISSUE_SINGLE_TO_MULTICHILD_NOT_SUPPORTED,
                            getModelObjectForMapping(mapping),
                            createMessageList(getMappingTypeDescription(getCurrentActivity()),
                                    getSourcePathDescription(sourceInfoProvider,
                                            mapping),
                                    getTargetPathDescription(targetInfoProvider,
                                            mapping)),
                            createAdditionalInfo(MapperContentProvider.DATAMAPPING_SOURCE_URI_ISSUEINFO,
                                    getSourcePath(sourceInfoProvider, mapping),
                                    MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
                                    getTargetPath(targetInfoProvider, mapping)));
                }

            }
        }
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractProcess2ProcessJSOutputMappingRule#createSourceInfoProvider(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param objectToValidate
     *            One of the objects returned by
     *            {@link #getObjectsToValidate(Process)}
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
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractProcess2ProcessJSOutputMappingRule#createTargetInfoProvider(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param objectToValidate
     *            One of the objects returned by
     *            {@link #getObjectsToValidate(Process)}
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
    protected String getScriptType() {
        return JsConsts.SUBPROCESS_TASK;
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
