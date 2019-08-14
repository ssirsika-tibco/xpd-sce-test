/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.bx.validation.rules.mapping;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ITreeContentProvider;

import com.tibco.bx.validation.internal.Messages;
import com.tibco.bx.validation.rules.datamapper.AbstractN2DataMapperMappingRule;
import com.tibco.xpd.datamapper.infoProviders.ContributableDataMapperInfoProvider;
import com.tibco.xpd.datamapper.scripts.IScriptDataMapperProvider;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.util.SubProcUtil;
import com.tibco.xpd.subprocess.datamapper.SubProcessDataMapperConstants;
import com.tibco.xpd.subprocess.datamapper.SubProcessScriptDataMapperProvider;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Loop;

/**
 * Mapping rules for Process-To-SubProcess context of Data Mapper for Call
 * Sub-Process use case.
 * 
 * @author sajain
 * @since Nov 4, 2015
 */
public class ProcessToSubProcessMappingRule extends
        AbstractN2DataMapperMappingRule {

    /**
     * Class level field to track the activity being validated.
     */
    Activity act;

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
        return MappingDirection.IN;
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
        return SubProcessDataMapperConstants.PROCESS_TO_SUBPROCESS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getMappingTypeDescription(EObject objectToValidate) {
        return Messages.ProcessDataToSubProcessJsRule_Mapping_desc;
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
        /* Mandatory target child content mapping is required. */
        return false;
    }

    /**
     * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule#isMultiToSingleSupported(java.lang.Object,
     *      java.lang.Object)
     * 
     * @param multiInstanceSource
     * @param singleInstanceTarget
     * @return
     */
    @Override
    protected boolean isMultiToSingleSupported(Object multiInstanceSource,
            Object singleInstanceTarget) {

        if (isMultiInstTask()) {

            /* Sid ACE-2088 Multi-single is only supported if target single is NOT in an array ancestor. */
            ITreeContentProvider contentProvider = targetInfoProvider.getContentProvider();

            while (singleInstanceTarget != null) {
                if (targetInfoProvider.isMultiInstance(singleInstanceTarget)) {
                    return false;
                }

                singleInstanceTarget = contentProvider.getParent(singleInstanceTarget);
            }

            return true;
        }

        return super.isMultiToSingleSupported(multiInstanceSource,
                singleInstanceTarget);
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
