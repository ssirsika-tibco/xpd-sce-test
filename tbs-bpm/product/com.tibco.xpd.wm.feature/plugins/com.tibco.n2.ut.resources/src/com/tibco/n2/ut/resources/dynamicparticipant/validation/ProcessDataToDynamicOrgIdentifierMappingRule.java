/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.n2.ut.resources.dynamicparticipant.validation;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

import com.tibco.n2.ut.resources.dynamicparticipant.mapper.DynamicOrgIdentiferPath;
import com.tibco.n2.ut.resources.dynamicparticipant.mapper.DynamicOrgIdentifierContentProvider;
import com.tibco.n2.ut.resources.dynamicparticipant.mapper.ProcessDataToDynamicOrgIdentifierMappingContentProvider;
import com.tibco.n2.ut.resources.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ProcessScopeDataConceptPathProvider;
import com.tibco.xpd.processeditor.xpdl2.script.ScriptInformationParsed;
import com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase;
import com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProviderBase;
import com.tibco.xpd.validation.bpmn.rules.baserules.ProcessDataMappingRuleInfoProvider;
import com.tibco.xpd.xpdExtension.DynamicOrgIdentifierRef;
import com.tibco.xpd.xpdExtension.DynamicOrganizationMapping;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * Mapping rule for mapping process data to dynamic org identifiers.
 * 
 * @author kthombar
 * @since 09-Oct-2013
 */
public class ProcessDataToDynamicOrgIdentifierMappingRule extends
        AbstractMappingRuleBase {

    /**
     * 
     */
    public ProcessDataToDynamicOrgIdentifierMappingRule() {

    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#getObjectsToValidate(com.tibco.xpd.xpdl2.Process)
     * 
     * @param process
     * @return
     */
    @Override
    protected Collection<? extends EObject> getObjectsToValidate(Process process) {
        /*
         * Returning the process as the process level mapping is under
         * consideration
         */
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
        return Messages.ProcessDataToDynamicOrgIdentifierMappingRule_MappingTypeDescription_message;
    }

    /**
     * Info provider for source data, i.e. Concept path
     * 
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#createSourceInfoProvider(org.eclipse.emf.ecore.EObject)
     * 
     * @param objectToValidate
     * @return
     */
    @Override
    protected MappingRuleContentInfoProviderBase createSourceInfoProvider(
            EObject objectToValidate) {

        ITreeContentProvider contentProvider =
                new ProcessScopeDataConceptPathProvider();

        return new ProcessDataMappingRuleInfoProvider(contentProvider);
    }

    /**
     * Info provider for target that, i.e. dynamic org identifier which is
     * wrapped in DynamicOrgIdentifierPath.
     * 
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#createTargetInfoProvider(org.eclipse.emf.ecore.EObject)
     * 
     * @param objectToValidate
     * @return
     */
    @Override
    protected MappingRuleContentInfoProviderBase createTargetInfoProvider(
            EObject objectToValidate) {
        ITreeContentProvider contentProvider =
                new DynamicOrgIdentifierContentProvider();

        return new DynamicOrgIdentifierMappingRuleInfoProvider(contentProvider);
    }

    /**
     * Mapping content provider, provides mapping info of the mapping from
     * process data to dynamic org idnetifiers.
     * 
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#createMappingsContentProvider(org.eclipse.emf.ecore.EObject)
     * 
     * @param objectToValidate
     * @return
     */
    @Override
    protected IStructuredContentProvider createMappingsContentProvider(
            EObject objectToValidate) {
        IStructuredContentProvider contentProvider =
                new ProcessDataToDynamicOrgIdentifierMappingContentProvider();

        return contentProvider;
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

            if (cpath.getParent() == null) {
                return true;
            }

        }
        return false;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#isMappingToTargetLevelSupported(java.lang.Object)
     * 
     * @param targetObjectInTree
     * @return
     */
    @Override
    protected boolean isMappingToTargetLevelSupported(Object targetObjectInTree) {
        if (targetObjectInTree instanceof DynamicOrgIdentiferPath) {
            return true;
        }
        return false;
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
                && targetObjectInTree instanceof DynamicOrgIdentiferPath) {
            ConceptPath cpath = (ConceptPath) sourceObjectInTree;
            Object item = cpath.getItem();
            ProcessRelevantData pRD = (ProcessRelevantData) item;
            BasicType basicType =
                    BasicTypeConverterFactory.INSTANCE.getBasicType(pRD);

            if (basicType != null
                    && BasicTypeType.STRING_LITERAL.equals(basicType.getType())) {
                return true;
            }

        }
        return false;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#getSourcePathDescription(com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProviderBase,
     *      java.lang.Object)
     * 
     * @param sourceInfoProvider
     * @param mapping
     * @return
     */
    @Override
    protected String getSourcePathDescription(
            MappingRuleContentInfoProviderBase sourceInfoProvider,
            Object mapping) {

        String path = null;

        Object sourceObjectFromMapping = getSourceObject(mapping);
        if (sourceObjectFromMapping != null) {
            path =
                    sourceInfoProvider
                            .getObjectPathDescription(sourceObjectFromMapping);
        }

        if (path == null || path.length() == 0) {
            /*
             * If the infoProvider didn't find a description then it maybe
             * because it couldn't find the source content for the given object
             * - so we'll try and pull out the raw data from
             * DynamicOrganizationMapping which might provide a hint at least.
             */
            EObject eo = getModelObjectForMapping(mapping);
            if (eo instanceof DynamicOrganizationMapping) {
                DynamicOrganizationMapping dataMapping =
                        (DynamicOrganizationMapping) eo;
                path = dataMapping.getSourcePath();
            }
        }

        return path != null ? path : ""; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#getTargetPathDescription(com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProviderBase,
     *      java.lang.Object)
     * 
     * @param targetInfoProvider
     * @param mapping
     * @return
     */
    @Override
    protected String getTargetPathDescription(
            MappingRuleContentInfoProviderBase targetInfoProvider,
            Object mapping) {
        String path = null;

        Object targetObjectFromMapping = getTargetObject(mapping);
        if (targetObjectFromMapping != null) {
            path =
                    targetInfoProvider
                            .getObjectPathDescription(targetObjectFromMapping);
        }

        if (path == null || path.length() == 0) {
            /*
             * If the infoProvider didn't find a description then it maybe
             * because it couldn't find the source content for the given object
             * - so we'll try and pull out the raw data from
             * DynamicOrganizationMapping which might provide a hint at least.
             */
            EObject eo = getModelObjectForMapping(mapping);
            if (eo instanceof DynamicOrganizationMapping) {
                DynamicOrganizationMapping dataMapping =
                        (DynamicOrganizationMapping) eo;
                DynamicOrgIdentifierRef dynamicOrgIdentifierRef =
                        dataMapping.getDynamicOrgIdentifierRef();
                if (dynamicOrgIdentifierRef != null) {
                    path = dynamicOrgIdentifierRef.getIdentifierName();
                }
            }
        }

        return path != null ? path : ""; //$NON-NLS-1$
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
        /*
         * concatenated mapping is not supported
         */
        return false;
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
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#isScriptMapping(java.lang.Object)
     * 
     * @param mapping
     * @return
     */
    @Override
    protected boolean isScriptMapping(Object mapping) {

        return false;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#isScriptMappingTypeResolved(java.lang.Object,
     *      org.eclipse.emf.ecore.EObject)
     * 
     * @param mapping
     * @param objectToValidate
     * @return
     */
    @Override
    protected boolean isScriptMappingTypeResolved(Object mapping,
            EObject objectToValidate) {

        return false;
    }
}
