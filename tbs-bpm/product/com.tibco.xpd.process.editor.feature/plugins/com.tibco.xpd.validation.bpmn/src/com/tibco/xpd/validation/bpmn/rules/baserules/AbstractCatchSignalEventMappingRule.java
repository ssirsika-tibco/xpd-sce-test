/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules.baserules;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

import com.tibco.xpd.processeditor.xpdl2.properties.event.signal.CatchSignalMapperSourceContentProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.event.signal.CatchSignalMapperTargetContentProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.event.signal.CatchSignalMappingContentProvider;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.validations.bpmn.internal.Messages;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Validation rule for Catch Signal Event (signal payload to attached-to-task
 * work item data) mappings.
 * 
 * @author aallway
 * @since 8 May 2012
 */
public abstract class AbstractCatchSignalEventMappingRule extends
        AbstractProcessData2ProcessDataJSMappingRule {

    private CatchSignalMapperSourceContentProvider sourceContentProvider;

    private CatchSignalMapperTargetContentProvider targetContentProvider;

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractProcess2ProcessJSOutputMappingRule#doActivityIsApplicable(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    protected boolean doActivityIsApplicable(Activity activity) {
        if (EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL.equals(EventObjectUtil
                .getEventTriggerType(activity))) {
            /*
             * XPD-7075: support this mapping rule only for Local catch signal
             * events.
             */
            return EventObjectUtil.isLocalSignalEvent(activity) ? true : false;
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
        sourceContentProvider =
                new CatchSignalMapperSourceContentProvider(true);

        return new ProcessDataMappingRuleInfoProvider(sourceContentProvider);
    }

    /*
     * XPD-3835 3. Missing Right hand content Causing broken mapping is not
     * validated
     */
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
        targetContentProvider = new CatchSignalMapperTargetContentProvider();

        return new ProcessDataMappingRuleInfoProvider(targetContentProvider);
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractProcess2ProcessJSOutputMappingRule#doCreateMappingSourceItemContentProvider(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    protected ITreeContentProvider doCreateMappingSourceItemContentProvider(
            Activity activity) {
        throw new RuntimeException(
                "Sould not get here because of overirde createSourceInfoProvider()"); //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#getMappingTypeDescription(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param objectToValidate
     *            One of the objects returned by
     *            {@link #getObjectsToValidate(Process)}
     * @return
     */
    @Override
    protected String getMappingTypeDescription(EObject objectToValidate) {
        return Messages.AbstractCatchSignalEventMappingRule_MapFromSignal_label;
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
        if (sourceContentProvider == null) {
            throw new RuntimeException(
                    "Source content provider and Mapping content provider creation request in unexpected order."); //$NON-NLS-1$
        }

        return new CatchSignalMappingContentProvider(sourceContentProvider);
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
        return true;
    }

}
