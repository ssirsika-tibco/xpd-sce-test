/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.bx.validation.rules.mapping;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.validation.internal.Messages;
import com.tibco.bx.validation.rules.datamapper.AbstractN2DataMapperMappingRule;
import com.tibco.xpd.datamapper.infoProviders.ContributableDataMapperInfoProvider;
import com.tibco.xpd.datamapper.infoProviders.WrappedContributedContent;
import com.tibco.xpd.datamapper.scripts.IScriptDataMapperProvider;
import com.tibco.xpd.mapper.MapperContentProvider;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.util.SubProcUtil;
import com.tibco.xpd.subprocess.datamapper.SubProcessDataMapperConstants;
import com.tibco.xpd.subprocess.datamapper.SubProcessScriptDataMapperProvider;
import com.tibco.xpd.xpdExtension.DataMapperArrayInflation;
import com.tibco.xpd.xpdExtension.DataMapperArrayInflationType;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Loop;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Rules applicable to Sub-Process to Process mappings.
 * 
 * @author sajain
 * @since Dec 10, 2015
 */
public class SubProcessToProcessMappingRule extends
        AbstractN2DataMapperMappingRule {

    /*
     * Sub-process instances can complete in any order so output must be appended to target list. For single to
     * multi-instance output mappings, target list must be configured 'Append to target list'.
     */
    public static final String ACE_ISSUE_SINGLE2MULTI_MUST_BE_APPEND = "ace.single2multi.mapping.target.must.be.append"; //$NON-NLS-1$

    /**
     * Class level field to track the activity being validated.
     */
    Activity act;

    private ContributableDataMapperInfoProvider sourceInfoProvider;

    private ContributableDataMapperInfoProvider targetInfoProvider;

    /**
     * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule#objectIsApplicable(org.eclipse.emf.ecore.EObject)
     * 
     * @param eo
     * @return
     */
    @Override
    protected boolean objectIsApplicable(EObject eo) {
        act = null;

        boolean ret = super.objectIsApplicable(eo);

        if (ret) {
            if (eo instanceof Activity) {
                act = (Activity) eo;

                /*
                 * Sid XPD-8180: XPD-8168 was implemented with the assumption
                 * that the base super.objectIsApplicable() would be returning
                 * false for non-sub-process-tasks. THAT IS NOT THE CASE.
                 * 
                 * Because of the
                 * SubProcessScriptDataMapperProvider.usesDataMapperGrammar()
                 * returns true if the default or selected grmmar for the
                 * context activity is data-mapper REGARDLESS of whether it is
                 * acutally a sub-process THEN THIS RULE enables for all
                 * activities that do or can use datamapper mappings.
                 * 
                 * The check made here is a temporary measure for late in CCRB.
                 * The real fix is wider ranging to ensure that the
                 * usesDatamapperGrammar() methods for ALL new mapper scenarios
                 * returns true only if the activity is reelevant to the rule.
                 */
                if (!SubProcUtil.isSubProcessImplementation(act)) {
                    ret = false;
                }

                /*
                 * Sid ACE-2088 Support datammapper for multi-instance sub-process.
                 */

            }
        }

        return ret;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getScriptType() {
        return ProcessScriptContextConstants.DATA_MAPPER_PE_MAPPING_SCRIPTS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected MappingDirection getMappingDirection() {
        return MappingDirection.OUT;
    }

    /**
     * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule#getScriptDataMapperProvider()
     * 
     * @return
     */
    @Override
    protected IScriptDataMapperProvider getScriptDataMapperProvider() {
        return new SubProcessScriptDataMapperProvider(getMappingDirection());
    }

    /**
     * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule#getDataMapperContext()
     * 
     * @return
     */
    @Override
    protected String getDataMapperContext() {
        return SubProcessDataMapperConstants.SUBPROCESS_TO_PROCESS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getMappingTypeDescription(EObject objectToValidate) {
        return Messages.SubProcessToProcessJsRule_Mapping_desc;
    }

    /**
     * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule#isPartialMappingCompletionSupported(java.lang.Object)
     * 
     * @param targetObjectInTree
     * @return
     */
    @Override
    protected boolean isPartialMappingCompletionSupported(
            Object targetObjectInTree) {
        /*
         * Mandatory target child content mapping is optional. On output from
         * Sub-Process we may be merging into existing process data content
         */
        return true;
    }

    /**
     * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule#isSingleToMultiSupported(java.lang.Object,
     *      java.lang.Object)
     * 
     * @param singleInstanceSource
     * @param multiInstanceTarget
     * @return
     */
    @Override
    protected boolean isSingleToMultiSupported(Object singleInstanceSource,
            Object multiInstanceTarget) {

        if (isMultiInstTask()) {
            return true;
        }

        return super.isSingleToMultiSupported(singleInstanceSource,
                multiInstanceTarget);
    }

    /**
     * XPD-8168:
     * 
     * @return <code>true</code>if the task being validated is a multi-instance
     *         one.
     */
    private boolean isMultiInstTask() {
        if (act != null) {
            Loop loop = act.getLoop();

            if (loop != null && loop.getLoopType() != null) {
                return true;

            }
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule#performAdditionalMappingValidation(java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     *
     * @param mapping
     * @param sourceObjectInTree
     * @param targetObjectInTree
     */
    @Override
    protected void performAdditionalMappingValidation(Object mapping, Object sourceObjectInTree,
            Object targetObjectInTree) {
        super.performAdditionalMappingValidation(mapping, sourceObjectInTree, targetObjectInTree);

        /*
         * Sid ACE-2088 - In AMX BPM the reverse is true and you can map from a single instance parameter into an array
         * field in the calling process. The array element assigned in the target field corresponds to the task instance
         * index. IF sub-process instances complete in non-sequential order then the target field is filled with arrays
         * to ensure that the matching index is assigned.
         * 
         * In ACE this is no longer true because ACE data system does not allow arrays with null elements. You can still
         * map from a single instance parameter into an array field in the calling process. However, in ACE the semantic
         * used for the target array is simply to append the source value onto the end of the target array. A validation
         * will be introduced to enforce the use of 'append to target list' mapping strategy on single->multi mappings
         * (and also make clear that sub-process may complete in any order).
         * 
         * So if mapping single->multi in a multi-instance task then ensure target root array is set to 'append'
         * strategy.
         */
        ScriptDataMapper sdm = getScriptDataMapper(mapping);

        if (sdm != null && isMultiInstTask() && targetObjectInTree instanceof WrappedContributedContent) {
            
            if (!sourceInfoProvider.isMultiInstance(sourceObjectInTree)
                    && targetInfoProvider.isMultiInstance(targetObjectInTree)) {
                boolean isConfiguredAsAppend = false;

                /* It's a single->multi mapping, ensure that the target array is config'd as append */
                String arraypath = getTargetPath(targetInfoProvider, mapping);

                EList<DataMapperArrayInflation> arrayInflationType = sdm.getArrayInflationType();

                for (DataMapperArrayInflation dataMapperArrayInflation : arrayInflationType) {
                    if (arraypath.equals(dataMapperArrayInflation.getPath())) {
                        /* This is the config for the target of this mapping - check the type */
                        if (DataMapperArrayInflationType.APPEND_LIST
                                .equals(dataMapperArrayInflation.getMappingType())) {
                            isConfiguredAsAppend = true;
                        }
                    }
                }

                if (!isConfiguredAsAppend) {
                    addIssue(ACE_ISSUE_SINGLE2MULTI_MUST_BE_APPEND,
                            getModelObjectForMapping(mapping),
                            createMessageList(getMappingTypeDescription(act),
                                    getSourcePathDescription(sourceInfoProvider, mapping),
                                    getTargetPathDescription(targetInfoProvider, mapping)),
                            createAdditionalInfo(MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
                                    arraypath));
                }
            }
        }
    }

    /**
     * @param mapping
     * @return The ScriptDataMapper model ancestor of the given mapping.
     */
    private ScriptDataMapper getScriptDataMapper(Object mapping) {
        EObject modelObjectForMapping = getModelObjectForMapping(mapping);
        if (modelObjectForMapping != null) {
            return (ScriptDataMapper) Xpdl2ModelUtil.getAncestor(modelObjectForMapping, ScriptDataMapper.class);
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule#createSourceInfoProvider(org.eclipse.emf.ecore.EObject)
     * 
     * @param objectToValidate
     * @return
     */
    @Override
    protected ContributableDataMapperInfoProvider createSourceInfoProvider(EObject objectToValidate) {
        sourceInfoProvider = super.createSourceInfoProvider(objectToValidate);

        return sourceInfoProvider;
    }

    /**
     * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule#createTargetInfoProvider(org.eclipse.emf.ecore.EObject)
     * 
     * @param objectToValidate
     * @return
     */
    @Override
    protected ContributableDataMapperInfoProvider createTargetInfoProvider(EObject objectToValidate) {
        targetInfoProvider = super.createTargetInfoProvider(objectToValidate);

        return targetInfoProvider;
    }
}
