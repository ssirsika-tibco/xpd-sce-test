/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.bx.validation.rules.datamapper;

import java.util.Collection;

import org.eclipse.uml2.uml.PrimitiveType;

import com.tibco.bx.validation.rules.mapping.N2ProcessDataMappingCompatibilityUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.datamapper.api.DataMapperUtils;
import com.tibco.xpd.datamapper.infoProviders.WrappedContributedContent;
import com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule;
import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.globaldata.CaseUMLScriptRelevantData;
import com.tibco.xpd.validation.bpmn.rules.baserules.JavaScriptTypeCompatibilityResult;
import com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProviderBase;
import com.tibco.xpd.validation.bpmn.rules.baserules.MappingTypeCompatibility;
import com.tibco.xpd.validation.bpmn.rules.baserules.ProcessDataMappingCompatibilityUtil;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Abstract data mapper rule class to provide common rules applicable to all
 * data mappers.
 * 
 * @author nwilson
 * @since 13 Apr 2015
 */
public abstract class AbstractN2DataMapperMappingRule
        extends AbstractDataMapperMappingRule {

    private static final int MAPPING_SIZE_LIMIT = 100;

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractActivityMappingJavaScriptRule#checkJavaScriptTypeCompatibility(java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     * 
     * @param sourceObjectInTree
     * @param targetObjectInTree
     * @param mapping
     * @return
     */
    @Override
    protected JavaScriptTypeCompatibilityResult checkJavaScriptTypeCompatibility(
            Object sourceObjectInTree, Object targetObjectInTree,
            Object mapping) {

        boolean isLikeMapping = isLikeMapping(mapping);

        if (isLikeMapping) {
            return JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_SUCCEEDED;
        }

        Object unwrappedSource = sourceObjectInTree;
        Object unwrappedTarget = targetObjectInTree;
        if (sourceObjectInTree instanceof WrappedContributedContent) {
            unwrappedSource = getConceptPath(
                    (WrappedContributedContent) sourceObjectInTree);
        }
        if (targetObjectInTree instanceof WrappedContributedContent) {
            unwrappedTarget = getConceptPath(
                    (WrappedContributedContent) targetObjectInTree);
        }

        JavaScriptTypeCompatibilityResult compatible =
                super.checkJavaScriptTypeCompatibility(unwrappedSource,
                        unwrappedTarget,
                        mapping);

        if (JavaScriptTypeCompatibilityResult.UNHANDLED_SCENARIO
                .equals(compatible)) {

            if (unwrappedTarget instanceof ConceptPath) {
                ConceptPath target = (ConceptPath) unwrappedTarget;

                if (unwrappedSource instanceof ConceptPath) {
                    ConceptPath source = (ConceptPath) unwrappedSource;

                    /*
                     * Allow an entry point to give us or subclass chance to
                     * override the default compatibility checking (we use
                     * ProcessDataMappingCompatibilityUtil which contains
                     * (possibly necessary) restrictions that do not apply to
                     * data mapper scenarios because the implied JavaScritp
                     * assignment in the generated scritp will be compatible.
                     */
                    compatible = overrideTypeCompatibilityCheck(source, target);

                    if (JavaScriptTypeCompatibilityResult.UNHANDLED_SCENARIO
                            .equals(compatible)) {

                        compatible = EnumerationTypeCompatibilityUtil
                                .checkTypeCompatibility(source, target);

                        if (JavaScriptTypeCompatibilityResult.UNHANDLED_SCENARIO
                                .equals(compatible)) {

                            MappingTypeCompatibility result =
                                    ProcessDataMappingCompatibilityUtil
                                            .checkTypeCompatibility(source,
                                                    target,
                                                    DirectionType.IN_LITERAL);

                            if (MappingTypeCompatibility.WRONGTYPE
                                    .equals(result)) {

                                compatible =
                                        JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_FAILED;
                            } else {
                                compatible =
                                        JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_SUCCEEDED;
                            }
                        }
                    }

                } else if (unwrappedSource instanceof ScriptInformation) {
                    IScriptRelevantData returnType =
                            getCachedScriptReturnType(mapping);
                    if (returnType != null && returnType.getType() != null
                            && !returnType.getType()
                                    .equals(JsConsts.UNDEFINED_DATA_TYPE)) {
                        MappingTypeCompatibility result =
                                getTypeCompatabilityResult(mapping,
                                        target,
                                        returnType);
                        if (MappingTypeCompatibility.WRONGTYPE.equals(result)) {
                            compatible =
                                    JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_FAILED;
                        } else {
                            compatible =
                                    JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_SUCCEEDED;
                        }
                    }
                }
            }
        }
        return compatible;
    }

    /**
     * @param mapping
     * @return <code>true</code> if mapping is a 'like mapping'
     */
    protected boolean isLikeMapping(Object mapping) {
        // If it's a 'like' mapping skip type compatibility check.
        boolean isLikeMapping = false;
        if (mapping instanceof Mapping) {
            Mapping m = (Mapping) mapping;
            if (m.isEditable()
                    && DataMapperUtils.isLikeMapping((Mapping) mapping)) {
                isLikeMapping = true;
            }
        }
        return isLikeMapping;
    }

    /**
     * Get the result as to whether the mapping source and target types are
     * compatible or not.
     * 
     * @param mapping
     * @param target
     * @param returnType
     * @param result
     * 
     * @return the result as to whether the mapping source and target types are
     *         compatible or not.
     */
    private MappingTypeCompatibility getTypeCompatabilityResult(Object mapping,
            ConceptPath target, IScriptRelevantData returnType) {

        MappingTypeCompatibility result = MappingTypeCompatibility.OK;

        /*
         * First of all, make sure that we aren't mapping a case ref to/from a
         * non-case-ref data (e.g. BOM).
         */
        if (isCaseRefToFromNonCaseRef(returnType, target)) {

            /*
             * If that's the case, then move on to check their compatibility.
             */
            result = MappingTypeCompatibility.WRONGTYPE;

            return result;
        }

        result = overrideScriptMappingTypeCompatibilityCheck(target,
                returnType);

        return result;
    }

    /**
     * Return <code>true</code> if the mapping is a case-ref type data to/from a
     * non-case-ref type data, <code>false</code> otherwise
     * 
     * @param returnType
     * @param target
     * 
     * @return <code>true</code> if the mapping is a case-ref type data to/from
     *         a non-case-ref type data, <code>false</code> otherwise
     */
    private boolean isCaseRefToFromNonCaseRef(IScriptRelevantData returnType,
            ConceptPath target) {

        boolean isCaseRefToFromNonCaseRef = false;

        if (returnType instanceof CaseUMLScriptRelevantData) {

            /*
             * Source is a Case Reference FIeld/property, check that target is.
             */

            Object targetItem = target.getItem();
            if (targetItem instanceof ProcessRelevantData) {

                ProcessRelevantData targetPrd =
                        (ProcessRelevantData) targetItem;

                isCaseRefToFromNonCaseRef =
                        !(targetPrd.getDataType() instanceof RecordType);

            } else {
                /*
                 * We aren't mapping to a process relevant data, so the target
                 * couldn't be a case-ref.
                 */
                isCaseRefToFromNonCaseRef = true;
            }

        } else {
            /* Source is not case ref, make sure target is not. */

            Object targetItem = target.getItem();
            if (targetItem instanceof ProcessRelevantData) {

                ProcessRelevantData targetPrd =
                        (ProcessRelevantData) targetItem;

                if (targetPrd.getDataType() instanceof RecordType) {
                    isCaseRefToFromNonCaseRef = true;

                } else {
                    isCaseRefToFromNonCaseRef = false;
                }
            }

        }

        return isCaseRefToFromNonCaseRef;

    }

    /**
     * Allows the script mapping type check to be overridden by subclasses.
     * 
     * @param target
     *            The target concept path.
     * @param returnType
     *            The source script return type.
     * @return the type check result.
     */
    protected MappingTypeCompatibility overrideScriptMappingTypeCompatibilityCheck(
            ConceptPath target, IScriptRelevantData returnType) {
        /**
         * Sid XPD-7925 Should be able to map from user defined scripts that
         * return "null;"
         */
        if (JsConsts.NULL.equals(returnType.getType())) {
            return MappingTypeCompatibility.OK;
        }

        MappingTypeCompatibility result = N2ProcessDataMappingCompatibilityUtil
                .checkTypeCompatibility(returnType, target);
        return result;
    }

    /**
     * This method is called as a final recourse to allow for additional type
     * compatibility that might not be allowed by the underyling historic
     * ProcessDataMappingCompatibilityUtil checks.
     * <p>
     * It is undesirable to change the underlying checks as there are a number
     * of runtime scenarios that are protected by those specific more stringent
     * rules.
     * <p>
     * As data mapper is a new feature with restricted runtime scope we can
     * afford to be more liberal because we can allow any type compatibility
     * that will be allowed in the later generated JAvaScript.
     * <p>
     * This method is called only after the main comaptibility criteria is
     * checked by
     * 
     * @param source
     * @param target
     * @return {@link JavaScriptTypeCompatibilityResult#HANDLED_SCENARIO_CHECK_SUCCEEDED}
     *         if types are considered compatible for data mapper scenarios
     *         over-and-above
     *         {@link ProcessDataMappingCompatibilityUtil#checkSubTypeCompatibility(ConceptPath, ConceptPath, DirectionType)}
     *         . {@link JavaScriptTypeCompatibilityResult#UNHANDLED_SCENARIO} if
     *         not a compatibility check we care about.
     */
    protected JavaScriptTypeCompatibilityResult overrideTypeCompatibilityCheck(
            ConceptPath source, ConceptPath target) {

        if (source.getItem() != null && target.getItem() != null) {
            if (source.getType() instanceof PrimitiveType) {
                /*
                 * All primitive types can be assigned to anything equivalent to
                 * basic type text.
                 */
                BasicType targetType = BasicTypeConverterFactory.INSTANCE
                        .getBasicType(target.getItem());

                if (targetType != null && BasicTypeType.STRING_LITERAL
                        .equals(targetType.getType())) {
                    return JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_SUCCEEDED;
                }

            } else if (BasicTypeConverterFactory.INSTANCE
                    .getBasicType(source.getItem()) != null) {
                /*
                 * Any source item that can be boiled down to a basic type can
                 * be assigned to anything equivalent to basic type text
                 */
                BasicType targetType = BasicTypeConverterFactory.INSTANCE
                        .getBasicType(target.getItem());

                if (targetType != null && BasicTypeType.STRING_LITERAL
                        .equals(targetType.getType())) {
                    return JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_SUCCEEDED;
                }
            }
        }

        return JavaScriptTypeCompatibilityResult.UNHANDLED_SCENARIO;
    }

    /**
     * @param wcc
     *            The wrapped content.
     * @return The conecpt path representing the wrapped object.
     */
    protected ConceptPath getConceptPath(WrappedContributedContent wcc) {
        ConceptPath cp = null;
        Object contributed = wcc.getContributedObject();
        if (contributed instanceof ConceptPath) {
            cp = (ConceptPath) contributed;
        }
        return cp;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#allowDescendantMappings(com.tibco.xpd.mapper.Mapping,
     *      com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProviderBase,
     *      com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProviderBase)
     * 
     * @param mapping
     * @param sourceInfoProvider
     * @param targetInfoProvider
     * @return
     */
    @Override
    protected boolean allowDescendantMappings(Mapping mapping,
            MappingRuleContentInfoProviderBase sourceInfoProvider,
            MappingRuleContentInfoProviderBase targetInfoProvider) {
        return DataMapperUtils.isLikeMapping(mapping);
    }

    /**
     * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule#performAdditionalMappingsValidation(java.lang.Object,
     *      java.util.Collection)
     *
     * @param objectToValidate
     * @param mappings
     */
    @Override
    protected void performAdditionalMappingsValidation(Object objectToValidate,
            Collection<Object> mappings) {

        super.performAdditionalMappingsValidation(objectToValidate, mappings);

        if (mappings.size() > MAPPING_SIZE_LIMIT) {

            if (objectToValidate instanceof Activity) {

                Activity activity = (Activity) objectToValidate;

                ScriptDataMapper sdm = getScriptDataMapperProvider()
                        .getScriptDataMapper(activity);

                MappingDirection mappingDirection = getMappingDirection();

                if (MappingDirection.IN.equals(mappingDirection)) {

                    boolean suppressMaxInputMappingsError =
                            Xpdl2ModelUtil.getOtherAttributeAsBoolean(sdm,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_SuppressMaxMappingsError());
                    if (suppressMaxInputMappingsError) {
                        /* raise warning */
                        addIssue("bx.inputMappingCountExceedsLimitWarning", //$NON-NLS-1$
                                sdm,
                                createMessageList(
                                        getMappingTypeDescription(activity)));
                    } else {
                        /* raise error */
                        addIssue("bx.inputMappingCountExceedsLimitError", //$NON-NLS-1$
                                sdm,
                                createMessageList(
                                        getMappingTypeDescription(activity)));
                    }

                } else if (MappingDirection.OUT.equals(mappingDirection)) {
                    boolean suppressMaxOutputMappingsError =
                            Xpdl2ModelUtil.getOtherAttributeAsBoolean(sdm,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_SuppressMaxMappingsError());
                    if (suppressMaxOutputMappingsError) {
                        /* raise warning */
                        addIssue("bx.outputMappingCountExceedsLimitWarning", //$NON-NLS-1$
                                sdm,
                                createMessageList(
                                        getMappingTypeDescription(activity)));
                    } else {
                        /* raise error */
                        addIssue("bx.outputMappingCountExceedsLimitError", //$NON-NLS-1$
                                sdm,
                                createMessageList(
                                        getMappingTypeDescription(activity)));
                    }
                }
            }
        }
    }
}
