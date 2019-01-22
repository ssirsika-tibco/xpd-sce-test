/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules.baserules;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * This rule abstracts the requirements for validation of mappings in activities
 * that map sub-process output data to invoking process data for a <li>Reusable
 * Sub-Process Task.</li>
 * 
 * @author sajain
 * @since Jan 13, 2015
 */
public abstract class AbstractSubProcessCallOutputMappingRule extends
        AbstractProcess2ProcessJSOutputMappingRule {

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractProcessData2ProcessDataJSMappingRule#createSourceInfoProvider(org.eclipse.emf.ecore.EObject)
     * 
     * @param objectToValidate
     * @return
     */
    @Override
    protected MappingRuleContentInfoProvider createSourceInfoProvider(
            EObject objectToValidate) {

        /*
         * Check if it's an async sub-process call.
         */
        if (isAsyncCall(objectToValidate)) {
            /*
             * If this is asynch call then we do not want to allow scripts in
             * source content, super.crateSourceInfoProvider() adds Scripts and
             * content from doCreateMappingSourceItemContentProvider() together.
             * For asynch we just add the latter.
             */
            return new ProcessDataMappingRuleInfoProvider(
                    doCreateMappingSourceItemContentProvider((Activity) objectToValidate));

        }

        /*
         * Not asynch sub-process call so use super to create Script+Params
         * content provider.
         */
        return super.createSourceInfoProvider(objectToValidate);
    }

    /**
     * Return <code>true</code> if it's an asynchronous call, <code>false</code>
     * otherwise.
     * 
     * @param objectToValidate
     *            Object to validate.
     * @return <code>true</code> if it's an asynchronous call,
     *         <code>false</code> otherwise.
     */
    private boolean isAsyncCall(EObject objectToValidate) {

        /*
         * Check if input element is an activity.
         */
        if (objectToValidate instanceof Activity) {

            Activity act = (Activity) objectToValidate;

            /*
             * Filter out for Call sub-process activities.
             */
            if (act.getImplementation() instanceof SubFlow) {

                /*
                 * Get sub-flow.
                 */
                SubFlow subFlow = (SubFlow) act.getImplementation();

                /*
                 * Get execution mode object.
                 */
                Object execModeObject =
                        Xpdl2ModelUtil.getOtherAttribute(subFlow,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_AsyncExecutionMode());

                /*
                 * We need to show PROCESS_ID parameter only for asynchronous
                 * sub-process calls.
                 */
                if (execModeObject != null) {
                    return true;
                }
            }
        }

        return false;
    }
}
