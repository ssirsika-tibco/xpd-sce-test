/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules.baserules;

import java.util.Collections;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.mapper.MapperContentProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.DefaultJsAttribute;
import com.tibco.xpd.script.model.client.DefaultScriptRelevantData;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.IUMLScriptRelevantData;
import com.tibco.xpd.xpdExtension.ScriptInformation;

/**
 * Abstract Mapping rule class for Java Script Grammar. Validate the mappings
 * for process Data to Web Service content.
 * <p>
 * i.e. This validate service invocation input mappings (ServiceTask &
 * Send-one-way SendTask/EndEvent) AND reply activities/throw end error for
 * non-generated request activities.
 * <p>
 * This can handle much of the generic aspects of validation (because the
 * mapping content provider used already intelligently switches between grammars
 * according to the activity in question)
 * <p>
 * The overriding sub-class need only do the grammar specific things.
 * 
 * @author aprasad
 * @since 17-Dec-2014
 */
abstract public class AbstractActivityMappingJavaScriptRule extends
        AbstractActivityMappingRule {

    private EObject objectToValidate = null;

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#validateObject(org.eclipse.emf.ecore.EObject)
     * 
     * @param objectToValidate
     */
    @Override
    protected void validateObject(EObject objectToValidate) {
        this.objectToValidate = objectToValidate;
        super.validateObject(objectToValidate);

    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#checkTypeCompatibility(java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     * 
     * @param sourceObjectInTree
     * @param targetObjectInTree
     * @param mapping
     * @return
     */
    @Override
    protected final boolean checkTypeCompatibility(Object sourceObjectInTree,
            Object targetObjectInTree, Object mapping) {

        JavaScriptTypeCompatibilityResult compatibilityResult =
                checkJavaScriptTypeCompatibility(sourceObjectInTree,
                        targetObjectInTree,
                        mapping);

        if (JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_FAILED
                .equals(compatibilityResult)) {
            return false;
        }
        return true;
    }

    /**
     * Checks Java Script Type Compatibility , this method replaces use of
     * checkTypeCompatibility() which is changed to final here and delegates
     * type compatibility check to this new method. This method at present does
     * following basic checks
     * <p>
     * <ul>
     * <li>Is Untyped List to Array mapping allowed: This class detects mappings
     * from scripts returning untyped lists and sub-class decides whether this
     * is permitted in its isUntypedScriptListToArrayMappingAllowed()
     * implementation.</li>
     * <li>Is Abstract type mapping allowed: This class detects mappings of XSD
     * Abstract Complex Type derived BOM types scripts and by default permits
     * this. If required the sub-class can override
     * isAbstractTypeMappingAllowed()to disallow abstract mappings.</li>
     * </ul>
     * </p>
     * 
     * The subclass should override this method and should call
     * super.checkJavaScriptTypeCompatibility() and check the return
     * {@link JavaScriptTypeCompatibilityResult} from super to decide to proceed
     * further with the validation check based on the checks run by the super.
     * In other words, if this method returns UNHANDLED_SCENARIO then the
     * sub-class should go ahead and check its own rules. If this method returns
     * HANDLED_SCENARIO_CHECK_FAILED then the sub-class should nominally not
     * continue with any other checks because the mapping scenario is handled
     * and found to be invalid here. If this class returns
     * HANDLED_SCENARIO_CHECK_SUCCEEDED then the sub-class may either quit or
     * continue depending on whether it may have further additional rules to
     * apply even in the mapping scenarios handled here.
     * 
     * @param sourceObjectInTree
     * @param targetObjectInTree
     * @param mapping
     * @return Compatibility check result defined as no scenarios were run in
     *         super 'UNHALDLED_SCENARIO', checks run and failed
     *         'HANDLED_SCENARIO_CHECK_FAILED' and checks run and passed
     *         'HANDLED_SCENARIO_CHECK_SUCCEEDED'.
     */
    protected JavaScriptTypeCompatibilityResult checkJavaScriptTypeCompatibility(
            Object sourceObjectInTree, Object targetObjectInTree, Object mapping) {

        JavaScriptTypeCompatibilityResult result =
                JavaScriptTypeCompatibilityResult.UNHANDLED_SCENARIO;
        // RETURN TRUE FOR Untyped list mapping and caller says ok

        if (isUntypedScriptListToArrayMapping(sourceObjectInTree,
                targetObjectInTree,
                mapping)) {

            if (!isUntypedScriptListToArrayMappingAllowed(sourceObjectInTree,
                    targetObjectInTree,
                    mapping)) {

                return JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_FAILED;
            }
            // Add warning

            MappingRuleContentInfoProviderBase targetInfoProvider =
                    createTargetInfoProvider(objectToValidate);
            targetInfoProvider.setContextObject(objectToValidate);

            createIssue(MappingIssue.UNABLE_TO_CHECK_MAPPING_TYPES,
                    getModelObjectForMapping(mapping),
                    Collections.EMPTY_LIST,
                    createAdditionalInfo(MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
                            getTargetPath(targetInfoProvider, mapping)),
                    mapping);

            result =
                    JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_SUCCEEDED;
        }

        /*
         * Create issue if and only if abstract mappings are not allowed and the
         * mappings we are checking are abstract. (Note: this had to be in a
         * single condition so that it could check for type compatibility in the
         * next 'else if' when abstract mappings aren't allowed but the mappings
         * are actually not abstract)
         */
        if (isAbstractTypeMapping(sourceObjectInTree,
                targetObjectInTree,
                mapping)) {
            if (!isAbstractTypeMappingAllowed(sourceObjectInTree,
                    targetObjectInTree,
                    mapping)) {

                MappingRuleContentInfoProviderBase sourceInfoProvider =
                        createSourceInfoProvider(objectToValidate);
                sourceInfoProvider.setContextObject(objectToValidate);

                MappingRuleContentInfoProviderBase targetInfoProvider =
                        createTargetInfoProvider(objectToValidate);
                targetInfoProvider.setContextObject(objectToValidate);
                /*
                 * XPD-1738: Restrict usage of Abstract type as Source of
                 * mapping
                 */
                createIssue(MappingIssue.ABSTRACT_TYPE_USED_IN_MAPPING,
                        getModelObjectForMapping(mapping),
                        createMessageList(getMappingTypeDescription(objectToValidate),
                                getSourcePathDescription(sourceInfoProvider,
                                        mapping),
                                getTargetPathDescription(targetInfoProvider,
                                        mapping)),
                        createAdditionalInfo(MapperContentProvider.DATAMAPPING_SOURCE_URI_ISSUEINFO,
                                getSourcePath(sourceInfoProvider, mapping),
                                MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
                                getTargetPath(targetInfoProvider, mapping)),
                        sourceObjectInTree,
                        targetObjectInTree,
                        mapping);
                result =
                        JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_FAILED;

            }

            result =
                    JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_SUCCEEDED;
        }

        return result;
    }

    /**
     * This method is used by checkJavaScriptTypeCompatibility() in conjunction
     * with isUntypedScriptListToArrayMapping() to check if Untyped List to
     * Array Mapping is allowed.Subclasses should return true to allow and false
     * otherwise.
     * 
     * @param sourceObjectInTree
     * @param targetObjectInTree
     * @return true to allow Untyped List to Array Mapping and false otherwise.
     */
    protected abstract boolean isUntypedScriptListToArrayMappingAllowed(
            Object sourceObjectInTree, Object targetObjectInTree, Object mapping);

    /**
     * This method is used by checkJavaScriptTypeCompatibility() in conjunction
     * with isUntypedScriptListToArrayMappingAllowed() to check if Untyped List
     * to Array Mapping is allowed.Subclasses should return true if this is an
     * Untyped List to Array mapping and false otherwise.
     * 
     * @param sourceObjectInTree
     * @param targetObjectInTree
     * @param mapping
     * @return true for Untyped List to Array mapping and false otherwise.
     */
    protected boolean isUntypedScriptListToArrayMapping(
            Object sourceObjectInTree, Object targetObjectInTree, Object mapping) {

        if (sourceObjectInTree instanceof ScriptInformation
                && targetObjectInTree instanceof ConceptPath) {

            ConceptPath conceptPath = (ConceptPath) targetObjectInTree;
            IScriptRelevantData returnType = getCachedScriptReturnType(mapping);

            if (returnType != null
                    && returnType.getType() != null
                    && !returnType.getType()
                            .equals(JsConsts.UNDEFINED_DATA_TYPE)) {

                if (returnType.isArray()
                        && PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME
                                .equalsIgnoreCase(returnType.getName())
                        && conceptPath.isArray()) {

                    return true;

                }
            }
        }
        return false;

    }

    /**
     * 
     * If checkJavaScriptTypeCompatibility detects a mapping involving BOM types
     * derived from XSD Abstract Complex types
     * 
     * it then determines whether this is permitted for a particular mapping
     * scenario by calling this method.
     * 
     * 
     * 
     * @return Return true if XSD Abstract type mapping is allowed for the
     *         sub-class’ mapping scenario, false if not. The default
     *         implementation returns true (XSD abstract type mapping is
     *         permitted).
     */
    protected boolean isAbstractTypeMappingAllowed(Object sourceObjectInTree,
            Object targetObjectInTree, Object mapping) {
        // Default Implementation does not implement restriction
        return true;
    }

    /**
     * Checks for usage of Abstract types in mapping. Returns true if atleast
     * one of source/target is of Abstract type. Restricts mapping to and from
     * an Abstract type and it's sub elements.Sub classes Should override for a
     * different behaviour.
     * 
     * @param sourceObjectInTree
     * @param targetObjectInTree
     * @param mapping
     * @return true, if atleast one of source or target is of abstract type.
     */

    protected boolean isAbstractTypeMapping(Object sourceObjectInTree,
            Object targetObjectInTree, Object mapping) {

        Class targetClass = null;
        Class sourceClass = null;
        // Extract information of class for target
        if (targetObjectInTree instanceof ConceptPath) {
            // get the root element, if available
            ConceptPath targetPath =
                    getRootElement((ConceptPath) targetObjectInTree);

            Object target =
                    BasicTypeConverterFactory.INSTANCE.getBaseType(targetPath
                            .getItem(), true);

            if (target == null) {
                target = targetPath.getType();
            }

            if (target instanceof Class) {
                targetClass = (Class) target;
            }

        }
        // Extract information of the Class for source
        if (sourceObjectInTree instanceof ConceptPath) {
            // get the root element if available
            ConceptPath sourcePath =
                    getRootElement((ConceptPath) sourceObjectInTree);

            Object sourceType =
                    BasicTypeConverterFactory.INSTANCE.getBaseType(sourcePath
                            .getItem(), true);

            if (sourceType == null) {
                sourceType = sourcePath.getType();
            }

            if (sourceType instanceof Class) {

                sourceClass = (Class) sourceType;
            }
        } else if (sourceObjectInTree instanceof ScriptInformation) {

            IScriptRelevantData returnType = getCachedScriptReturnType(mapping);

            if (returnType != null
                    && returnType.getType() != null
                    && !returnType.getType()
                            .equals(JsConsts.UNDEFINED_DATA_TYPE)) {
                if (returnType instanceof IUMLScriptRelevantData) {

                    // Restrict mapping from an Abstract Type.
                    IUMLScriptRelevantData scriptRelevantData =
                            (IUMLScriptRelevantData) returnType;
                    scriptRelevantData.getAdditionalInfo();
                    sourceClass = scriptRelevantData.getJsClass().getUmlClass();

                } else if (returnType instanceof DefaultScriptRelevantData) {
                    // restrict mapping from sub elements/properties of an
                    // Abstract type
                    DefaultScriptRelevantData data =
                            (DefaultScriptRelevantData) returnType;

                    Object extendedInfo = data.getExtendedInfo();

                    // check if this is an attribute of a Class.
                    if (extendedInfo != null
                            && extendedInfo instanceof DefaultJsAttribute) {

                        Element element =
                                ((DefaultJsAttribute) extendedInfo)
                                        .getElement();

                        if (element instanceof Property) {

                            Element owner = ((Property) element).getOwner();
                            // get owner class
                            sourceClass =
                                    (owner instanceof Class) ? (Class) owner
                                            : null;
                        }
                    }
                }
            }
        }

        // source OR target is of abstract type or sub element of an abstract
        // type.
        if (isAbstractType(sourceClass) || isAbstractType(targetClass)) {
            return true;
        }

        return false;
    }

    /**
     * Searches to retrieve the top most parent in the {@link ConceptPath}
     * hierarchy.
     * 
     * @param conceptPath
     * @return {@link ConceptPath}, top most parent in the {@link ConceptPath}
     *         hierarchy.
     */
    private ConceptPath getRootElement(ConceptPath conceptPath) {
        ConceptPath parent = conceptPath;
        do {
            parent = conceptPath.getParent();
            if (parent != null) {
                conceptPath = parent;
            }
        } while (parent != null);
        return conceptPath;
    }

    /**
     * For the given class looks for applied XSD stereotype for abstract,
     * returns true ONLY if the stereotype is applied and set to true, false
     * otherwise.
     * 
     * @param sourceClass
     * @return true, for an abstract class.
     */
    private boolean isAbstractType(Class bomClass) {

        if (bomClass == null) {
            return false;
        }

        /*
         * Sid ACE-194 - we don't support XSD based BOMs in ACE
         */
        return false;

    }
}
