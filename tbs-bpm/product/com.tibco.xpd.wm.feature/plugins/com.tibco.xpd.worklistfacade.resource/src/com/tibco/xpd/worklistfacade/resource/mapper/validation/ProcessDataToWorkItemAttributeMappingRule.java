/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.worklistfacade.resource.mapper.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.mapper.MapperContentProvider;
import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ProcessScopeDataConceptPathProvider;
import com.tibco.xpd.processeditor.xpdl2.script.ScriptInformationParsed;
import com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase;
import com.tibco.xpd.validation.bpmn.rules.baserules.BasicTypeHandler;
import com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProviderBase;
import com.tibco.xpd.validation.bpmn.rules.baserules.MappingTypeCompatibility;
import com.tibco.xpd.validation.bpmn.rules.baserules.ProcessDataMappingRuleInfoProvider;
import com.tibco.xpd.worklistfacade.resource.mapper.ProcessDataToWIAttributeMappingContentProvider;
import com.tibco.xpd.worklistfacade.resource.mapper.WorkItemAttributeConceptPath;
import com.tibco.xpd.worklistfacade.resource.mapper.WorkListFacadeMapperContentProvider;
import com.tibco.xpd.worklistfacade.resource.util.Messages;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.Process;

/**
 * Rule to validate mapping between Process Data and Physical Work Item
 * Attribute.
 * 
 * @author aprasad
 * @since 14-Nov-2013
 */
public class ProcessDataToWorkItemAttributeMappingRule
        extends AbstractMappingRuleBase {

    private static final String ISSUE_WRONGSIZE =
            "bpmn.subProcessWrongSizeParameter"; //$NON-NLS-1$

    private static final String ISSUE_NUMBER_TO_INTEGER =
            "wlf.mapping.numberToInteger"; //$NON-NLS-1$

    /**
     * Result of invocation of
     * {@link #checkTypeCompatibility(Object, Object, Object)} for current
     * mapping (so that we can add additional length incompatibility warning if
     * types are compatible but not same length) during the subsequent call to
     * {@link #performAdditionalMappingValidation(Object, Object, Object)}
     */
    private MappingTypeCompatibility currentMappingTypeCompatibilityResult =
            null;

    private Process currentProcess;

    private WorkItemAttributeMappingRuleInfoProvider targetInfoProvider;

    private ProcessDataMappingRuleInfoProvider sourceInfoProvider;

    /**
     * @param o
     *            The object to validate.
     * @see com.tibco.xpd.validation.xpdl2.rules.Xpdl2ValidationRule#validate(java.lang.Object)
     */
    @Override
    public void validate(Object o) {
        if (o instanceof Process) {
            currentProcess = (Process) o;
            validate(currentProcess);
            // Interested in validating only Process
        }
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#getObjectsToValidate(com.tibco.xpd.xpdl2.Process)
     * 
     * @param process
     * @return
     */
    @Override
    protected Collection<? extends EObject> getObjectsToValidate(
            Process process) {
        // Validate Mappings for Process
        return Collections.singletonList(process);
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#getMappingTypeDescription(org.eclipse.emf.ecore.EObject)
     * 
     * @param objectToValidate
     * @return
     */
    @Override
    protected String getMappingTypeDescription(EObject objectToValidate) {
        return Messages.UserTaskDataAssociationValidation_ProcessDataWIAttributeMappingTypeDescription;

    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#createSourceInfoProvider(org.eclipse.emf.ecore.EObject)
     * 
     * @param objectToValidate
     * @return
     */
    @Override
    protected MappingRuleContentInfoProviderBase createSourceInfoProvider(
            EObject objectToValidate) {
        // Process Data Info provider
        ITreeContentProvider contentProvider =
                new ProcessScopeDataConceptPathProvider();

        sourceInfoProvider =
                new ProcessDataMappingRuleInfoProvider(contentProvider);

        return sourceInfoProvider;

    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#createTargetInfoProvider(org.eclipse.emf.ecore.EObject)
     * 
     * @param objectToValidate
     * @return
     */
    @Override
    protected MappingRuleContentInfoProviderBase createTargetInfoProvider(
            EObject objectToValidate) {
        // Work List Facade Info Provider
        ITreeContentProvider contentProvider =
                new WorkListFacadeMapperContentProvider();

        targetInfoProvider =
                new WorkItemAttributeMappingRuleInfoProvider(contentProvider);

        return targetInfoProvider;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#createMappingsContentProvider(org.eclipse.emf.ecore.EObject)
     * 
     * @param objectToValidate
     * @return
     */
    @Override
    protected IStructuredContentProvider createMappingsContentProvider(
            EObject objectToValidate) {
        // Process Data to Work Item Attrib Alias Mappings content provider
        IStructuredContentProvider contentProvider =
                new ProcessDataToWIAttributeMappingContentProvider();
        return contentProvider;

    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#getMappingDirection()
     * 
     * @return
     */
    @Override
    protected MappingDirection getMappingDirection() {
        return null;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#isConcatenationMappingSupported()
     * 
     * @return
     */
    @Override
    protected boolean isConcatenationMappingSupported() {

        return false;
    }

    /**
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
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#isScriptMappingSupported()
     * 
     * @return
     */
    @Override
    protected boolean isScriptMappingSupported() {

        return false;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#isScriptMappingSupportedForTarget(java.lang.Object)
     * 
     * @param targetObject
     * @return
     */
    @Override
    protected boolean isScriptMappingSupportedForTarget(Object targetObject) {

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

        if (sourceObjectInTree instanceof ConceptPath) {
            ConceptPath cpath = (ConceptPath) sourceObjectInTree;

            while (cpath != null) {
                /* Don't allow mapping from individual elements in a sequence */
                if (cpath.getIndex() >= 0) {
                    return false;
                }
                cpath = cpath.getParent();
            }

        }
        return true;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#isMappingToTargetLevelSupported(java.lang.Object)
     * 
     * @param targetObjectInTree
     * @return
     */
    @Override
    protected boolean isMappingToTargetLevelSupported(
            Object targetObjectInTree) {
        // ONLY to Work Item Attribute
        return (targetObjectInTree instanceof WorkItemAttributeConceptPath);
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
    protected boolean checkTypeCompatibility(Object sourceObjectInTree,
            Object targetObjectInTree, Object mapping) {

        if (sourceObjectInTree instanceof ConceptPath
                && targetObjectInTree instanceof WorkItemAttributeConceptPath) {
            ConceptPath srcdata = (ConceptPath) sourceObjectInTree;

            Object sourceType = BasicTypeConverterFactory.INSTANCE
                    .getBaseType(srcdata.getItem(), true);

            WorkItemAttributeConceptPath targetPath =
                    (WorkItemAttributeConceptPath) targetObjectInTree;

            Property attribute = targetPath.getPhysicalAttribute();

            Object attributeType =
                    getBasicTypeForWorkItemAttributeAliasType(attribute);

            if (sourceType instanceof BasicType
                    && attributeType instanceof BasicType) {
                // XPD-5969: Work Item attribute mapping should show warning for
                // mapping when the length of source process data is exceeding
                // length of target WI attribute.
                BasicType targetType = (BasicType) attributeType;

                BasicType sourceBasicType = (BasicType) sourceType;

                String issueId = null;

                // XPD-7677: Allow any basic types to be mapped to String
                // targets.
                if (BasicTypeType.STRING_LITERAL.equals(targetType.getType())
                        || ((BasicType) sourceType).getType()
                                .equals(targetType.getType())) {

                    BasicType sourceT = (BasicType) sourceType;
                    BasicType targetT = targetType;

                    currentMappingTypeCompatibilityResult =
                            BasicTypeHandler.handleMatchingTypes(sourceT,
                                    targetT,
                                    null,
                                    MappingTypeCompatibility.OK);

                    if (MappingTypeCompatibility.WRONGSIZE
                            .equals(currentMappingTypeCompatibilityResult)) {
                        // add issue
                        addIssue(ISSUE_WRONGSIZE, mapping, srcdata, targetPath);
                    }
                    return true;

                } else if (BasicTypeType.INTEGER_LITERAL
                        .equals(targetType.getType())
                        && BasicTypeType.FLOAT_LITERAL
                                .equals(sourceBasicType.getType())) {
                    /*
                     * Sid ACE-1755
                     * 
                     * Support Mapping of fixed point zero places to integer.
                     */
                    boolean isFixedPointZeroDecimals = false;

                    if (sourceBasicType.getScale() != null
                            && sourceBasicType.getScale().getValue() == 0) {
                        isFixedPointZeroDecimals = true;
                    }

                    if (!isFixedPointZeroDecimals) {
                        addIssue(ISSUE_NUMBER_TO_INTEGER,
                                mapping,
                                srcdata,
                                targetPath);
                    }

                    /*
                     * Either way, we've either already raised a more specific
                     * specific issue than returning false would cause OR there
                     * is no issue. To return true to prevent default
                     * incompatible issue from being raised.
                     */
                    return true;
                }

            }
            /*
             * XPD-7014: Allow BOM.Enumeration Attribute <-> Work Item Text
             * Attribute mapping
             */
            else if ((sourceType instanceof BasicType
                    && BasicTypeType.STRING_LITERAL
                            .equals(((BasicType) sourceType).getType())
                    && attributeType instanceof Enumeration)
                    || (attributeType instanceof BasicType
                            && BasicTypeType.STRING_LITERAL.equals(
                                    ((BasicType) attributeType).getType())
                            && sourceType instanceof Enumeration)) {

                return true;

            }
        }
        return false;

    }

    /**
     * Add a validation issue for the given mapping.
     * 
     * @param issueId
     * @param mapping
     * @param srcPath
     * @param targetPath
     */
    protected void addIssue(String issueId, Object mapping, ConceptPath srcPath,
            WorkItemAttributeConceptPath targetPath) {
        ArrayList<String> messages = new ArrayList<String>();

        messages.add(getTargetPathDescription(targetInfoProvider, mapping));
        messages.add(getSourcePathDescription(sourceInfoProvider, mapping));

        Map<String, String> additionalInfo = new HashMap<String, String>();

        additionalInfo.put(
                MapperContentProvider.DATAMAPPING_SOURCE_URI_ISSUEINFO,
                srcPath.getPath());

        additionalInfo.put(
                MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
                targetPath.getPath());

        Object issueTarget = null;
        if (mapping instanceof Mapping) {
            issueTarget = ((Mapping) mapping).getMappingModel();
        }

        if (!(issueTarget instanceof EObject)) {
            issueTarget = currentProcess;
        }

        addIssue(issueId, (EObject) issueTarget, messages, additionalInfo);
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
        // Work List Facade does not support multi
        return false;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#isPartialMappingCompletionSupported(java.lang.Object)
     * 
     * @param targetObjectInTree
     * @return
     */
    @Override
    protected boolean isPartialMappingCompletionSupported(
            Object targetObjectInTree) {
        return false;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#performAdditionalMappingValidation(java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     * 
     * @param mapping
     * @param sourceObjectInTree
     * @param targetObjectInTree
     */
    @Override
    protected void performAdditionalMappingValidation(Object mapping,
            Object sourceObjectInTree, Object targetObjectInTree) {
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#performAdditionalMappingsValidation(java.util.Collection)
     * 
     * @param mappings
     */
    @Override
    protected void performAdditionalMappingsValidation(Object objectToValidate,
            Collection<Object> mappings) {

    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#parseScript(java.lang.Object)
     * 
     * @param mapping
     * @return
     */
    @Override
    protected ScriptInformationParsed parseScript(Object mapping) {

        return null;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#getScriptType()
     * 
     * @return
     */
    @Override
    protected String getScriptType() {

        return null;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#getScriptGrammar(org.eclipse.emf.ecore.EObject)
     * 
     * @param objectToValidate
     * @return
     */
    @Override
    protected String getScriptGrammar(EObject objectToValidate) {

        return null;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#getDefaultScriptDestination()
     * 
     * @return
     */
    @Override
    protected String getDefaultScriptDestination() {

        return null;
    }

    /**
     * Maps Work Item Attribute Data Type {@link Type} to Process Data type
     * {@link BasicTypeType}.
     * 
     * @param workItemAttributeAliasType
     * @return
     */
    private BasicType getBasicTypeForWorkItemAttributeAliasType(
            Property attribute) {

        if (attribute != null) {

            Object targetType = BasicTypeConverterFactory.INSTANCE
                    .getBaseType(attribute, true);

            if (targetType instanceof BasicType) {
                return (BasicType) targetType;
            }
        }

        return null;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#isScriptMapping(java.lang.Object)
     * 
     * @param mapping
     * @return
     */
    @Override
    protected boolean isScriptMapping(Object mapping) {

        return false;
    }
}
