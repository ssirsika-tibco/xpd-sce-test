/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.bx.validation.rules.mapping;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredContentProvider;

import com.tibco.bx.validation.internal.Messages;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.AbstractInvokedProcessParameterItemProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.SubProcMappingItemProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.SubProcParameterItemProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.validation.bpmn.rules.baserules.MappingIssue;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Loop;

/**
 * AMX BPM Sub-process input mapping rule for AMX BPM process manager.
 * 
 * @author aallway
 * @since 17 Dec 2010
 */
public class SubProcessJSInputMappingRule extends
        AbstractN2Process2SubProcessJSInputMappingRule {

    private static final String ISSUE_MULTI_TO_SINGLE_NONLOOP =
            "bx.subProcessArrayNonArrayMappingDisallowed"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractProcess2ProcessJSInputMappingRule#doActivityIsApplicable(com.tibco.xpd.xpdl2.Activity)
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
                            DirectionType.IN_LITERAL);

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
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractProcess2ProcessJSInputMappingRule#doActivityValidationDone(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     */
    @Override
    protected void doActivityValidationDone(Activity activity) {
    }

    @Override
    protected AbstractInvokedProcessParameterItemProvider doCreateMappingTargetItemProvider() {
        return new SubProcParameterItemProvider(MappingDirection.IN);
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
        return new SubProcMappingItemProvider(MappingDirection.IN);
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
        /*
         * Multi-instance to single instance is supported only for
         * multi-instance task.
         */
        Loop loop = getCurrentActivity().getLoop();
        if (loop != null && loop.getLoopType() != null) {
            return true;
        }

        return false;
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
        return Messages.SubProcessJSInputMappingRule_SubProcessInputMappingRule_label;
    }

    /**
     * 
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#createIssue(com.tibco.xpd.validation.bpmn.rules.baserules.MappingIssue,
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
         * "Multiple to single instance data mapping is only supported for multi-instance/looping tasks ('%1$s' to %2$s)"
         */
        if (MappingIssue.MULTI_TO_SINGLE_UNSUPPORTED.equals(issue)) {
            addIssue(ISSUE_MULTI_TO_SINGLE_NONLOOP,
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
