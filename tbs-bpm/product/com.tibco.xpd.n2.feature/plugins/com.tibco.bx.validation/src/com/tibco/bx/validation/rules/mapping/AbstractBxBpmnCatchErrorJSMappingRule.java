/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.bx.validation.rules.mapping;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

import com.tibco.bx.validation.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorUtil;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorUtil.ErrorCatchType;
import com.tibco.xpd.processeditor.xpdl2.properties.event.error.CatchErrorMapperMappingContentProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.event.error.CatchErrorMapperSourceContentProvider;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.validation.bpmn.rules.baserules.AbstractProcess2ProcessJSOutputMappingRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.IntermediateEvent;

/**
 * Catch Bx BPMN error event - error-data to Main Process JavaScript output
 * mapping rule.
 * <p>
 * This handles Specific sub-process error catch, AND CATCH_ALL / CATCH_NAME for
 * any error thrower.
 * 
 * 
 * @author aallway
 * @since 21 Dec 2010
 */
public abstract class AbstractBxBpmnCatchErrorJSMappingRule extends
        AbstractProcess2ProcessJSOutputMappingRule {

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractProcess2ProcessJSOutputMappingRule#doActivityIsApplicable(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    protected boolean doActivityIsApplicable(Activity activity) {
        if (activity.getEvent() instanceof IntermediateEvent) {
            if (EventTriggerType.EVENT_ERROR_LITERAL.equals(EventObjectUtil
                    .getEventTriggerType(activity))) {

                if (EventObjectUtil.isAttachedToTask(activity)) {
                    ErrorCatchType catchType =
                            BpmnCatchableErrorUtil.getCatchType(activity);

                    /*
                     * This method used to include CATCH_ALL/CATCH_BY_NAME
                     * almost by accident of being attached to sub-process.
                     * 
                     * Also, used to include web-service fault catches when set
                     * to catch fault in web-service under sub-process it was
                     * actually attached to.
                     * 
                     * So check specifically for catching all/by-name and
                     * sub-process error throws.
                     */
                    if (ErrorCatchType.CATCH_ALL.equals(catchType)
                            || ErrorCatchType.CATCH_BY_NAME.equals(catchType)) {
                        return true;

                    } else if (BpmnCatchableErrorUtil
                            .isCatchSubProcessErrorEvent(activity)) {
                        return true;
                    }
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
        return new CatchErrorMapperMappingContentProvider();
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractProcess2ProcessJSOutputMappingRule#doCreateMappingSourceItemContentProvider()
     * 
     * @return
     */
    @Override
    protected ITreeContentProvider doCreateMappingSourceItemContentProvider(
            Activity activity) {
        CatchErrorMapperSourceContentProvider catchErrorMapperSourceContentProvider =
                new CatchErrorMapperSourceContentProvider();
        catchErrorMapperSourceContentProvider
                .inputChanged(null, null, activity);
        return catchErrorMapperSourceContentProvider;
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
        return Messages.MapFromErrorPrefix_label;
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
        return true;
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
         * Not checking integer type length compatibility anymore due to
         * XPD-6064
         */

    }

}
