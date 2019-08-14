/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.bx.validation.rules.datamapper;

import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.validation.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorUtil;
import com.tibco.xpd.catcherror.datamapper.CatchErrorDataMapperConstants;
import com.tibco.xpd.catcherror.datamapper.CatchErrorScriptDataMapperProvider;
import com.tibco.xpd.datamapper.infoProviders.ContributableDataMapperInfoProvider;
import com.tibco.xpd.datamapper.infoProviders.WrappedContributedContent;
import com.tibco.xpd.datamapper.scripts.IScriptDataMapperProvider;
import com.tibco.xpd.mapper.MapperContentProvider;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.StandardMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.SubProcUtil;
import com.tibco.xpd.xpdExtension.ErrorThrowerInfo;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Loop;

/**
 * Rules for Subprocess Catch specific error rules for 'Map from error' section.
 * 
 * @author ssirsika
 * @since 07-Mar-2016
 */
public class SubProcessCatchErrorEventMapFromErrorDataMapperMappingRule extends
        AbstractN2DataMapperMappingRule {

    private Activity activity;

    private static final String ISSUE_CATCH_SPECIFIC_ERROR_ERRODETAIL_NOTSUPPORTED =
            "bx.catchSpecificErrorErrorDetailNotSupported"; //$NON-NLS-1$

    private ContributableDataMapperInfoProvider sourceInfoProvider;

    private ContributableDataMapperInfoProvider targetInfoProvider;

    /**
     * @see com.tibco.bx.validation.rules.datamapper.AbstractWebSvcToProcessDataMapperRule#objectIsApplicable(org.eclipse.emf.ecore.EObject)
     * 
     * @param eo
     * @return
     */
    @Override
    protected boolean objectIsApplicable(EObject eo) {
        boolean ret = false;

        activity = null;
        if (eo instanceof Activity) {
            Activity attachedToTask =
                    BpmnCatchableErrorUtil.getAttachedToTask((Activity) eo);

            if (attachedToTask != null
                    && SubProcUtil.isSubProcessImplementation(attachedToTask)) {
                activity = (Activity) eo;
                if (ScriptGrammarFactory.DATAMAPPER.equals(ScriptGrammarFactory
                        .getGrammarToUse(activity, DirectionType.OUT_LITERAL))) {

                    /*
                     * XPD-8168 This rule should only apply to error events that
                     * specifically catch Sub-Process Throw_Error-Event-Errors.
                     * 
                     * For Catch error on sub-process it is also possible to
                     * catch Web Service Fault from service task in (or in
                     * sub-process below) the invoked sub-process. That scenario
                     * should be covered by the 'catch ws fault rule'
                     */
                    ret =
                            (BpmnCatchableErrorUtil
                                    .isCatchSubProcessErrorEvent(activity) && super
                                    .objectIsApplicable(eo));

                    /*
                     * Sid ACE-2088 Support datam mapper for multi-instance
                     * sub-process.
                     */

                    return ret;
                }
            }
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule#getMappingTypeDescription(org.eclipse.emf.ecore.EObject)
     * 
     * @param objectToValidate
     * @return
     */
    @Override
    protected String getMappingTypeDescription(EObject objectToValidate) {
        return Messages.MapFromErrorPrefix_label;
    }

    /**
     * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule#createSourceInfoProvider(org.eclipse.emf.ecore.EObject)
     * 
     * @param objectToValidate
     * @return
     */
    @Override
    protected ContributableDataMapperInfoProvider createSourceInfoProvider(
            EObject objectToValidate) {

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
    protected ContributableDataMapperInfoProvider createTargetInfoProvider(
            EObject objectToValidate) {
        targetInfoProvider = super.createTargetInfoProvider(objectToValidate);

        return targetInfoProvider;
    }

    /**
     * @see com.tibco.bx.validation.rules.datamapper.AbstractWebSvcToProcessDataMapperRule#performAdditionalMappingValidation(java.lang.Object,
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

        Object unwrappedSource = sourceObjectInTree;
        Object unwrappedTarget = targetObjectInTree;
        if (sourceObjectInTree instanceof WrappedContributedContent) {
            unwrappedSource =
                    getConceptPath((WrappedContributedContent) sourceObjectInTree);
        }
        if (targetObjectInTree instanceof WrappedContributedContent) {
            unwrappedTarget =
                    getConceptPath((WrappedContributedContent) targetObjectInTree);
        }
        /*
         * XPD-2350 - Disallow mapping from $ERRORDETAIL for non-catch-all
         * events.
         */
        if (activity != null && unwrappedSource instanceof ConceptPath) {
            if (StandardMappingUtil.CATCH_ERRORDETAIL_FORMALPARAMETER
                    .equals(((ConceptPath) unwrappedSource).getItem())) {

                /*
                 * If there is a specific error thrower then there will be
                 * extendedErrorThrowerInfo otherwise for catch-all and
                 * catch-by-name this will be null
                 */
                ErrorThrowerInfo extendedErrorThrowerInfo =
                        BpmnCatchableErrorUtil
                                .getExtendedErrorThrowerInfo(activity);
                if (extendedErrorThrowerInfo != null) {
                    addIssue(ISSUE_CATCH_SPECIFIC_ERROR_ERRODETAIL_NOTSUPPORTED,
                            getModelObjectForMapping(mapping),
                            createMessageList(getMappingTypeDescription(activity),
                                    getSourcePathDescription(sourceInfoProvider,
                                            mapping),
                                    getTargetPathDescription(targetInfoProvider,
                                            mapping)),
                            createAdditionalInfo(MapperContentProvider.DATAMAPPING_SOURCE_URI_ISSUEINFO,
                                    getSourcePath(sourceInfoProvider, mapping)));
                }
            }
        }
    }

    /**
     * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule#getScriptDataMapperProvider()
     * 
     * @return
     */
    @Override
    protected IScriptDataMapperProvider getScriptDataMapperProvider() {
        return new CatchErrorScriptDataMapperProvider(getDataMapperContext());
    }

    /**
     * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule#getDataMapperContext()
     * 
     * @return
     */
    @Override
    protected String getDataMapperContext() {
        return CatchErrorDataMapperConstants.CATCH_SUBPROCESS_ERROR;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#getMappingDirection()
     * 
     * @return
     */
    @Override
    protected MappingDirection getMappingDirection() {
        return MappingDirection.OUT;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#getScriptType()
     * 
     * @return
     */
    @Override
    protected String getScriptType() {
        return ProcessScriptContextConstants.DATA_MAPPER_PE_MAPPING_SCRIPTS;
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

        /*
         * Sid XPD-8168: Even though we decided we would not support multi-inst
         * subprocs for datamapper, I noticed that this check (to allow single
         * to multi on error output was mnissing) So put it in for now in case
         * we move the restriction on datamapepr for multi-inst sub-process.
         */
        if (isAttachedToMultiInstTask()) {
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
    private boolean isAttachedToMultiInstTask() {
        if (activity != null) {
            Activity attachedTask = EventObjectUtil.getTaskAttachedTo(activity);

            if (attachedTask != null) {
                Loop loop = attachedTask.getLoop();

                if (loop != null && loop.getLoopType() != null) {
                    return true;

                }
            }
        }
        return false;
    }
}
