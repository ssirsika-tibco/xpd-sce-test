/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.simulation.validation;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.processeditor.xpdl2.properties.script.JavaScriptUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;

/**
 * @author nwilson
 */
public class FormalParameterNameRule extends ProcessValidationRule {

    /** Formal parameters must be valid javascript identifiers. */
    public static final String JAVASCRIPT = "sim.invalidJavscriptIdentifier"; //$NON-NLS-1$

    /**
     * @param process
     *            The process.
     * @param activities
     *            The activities.
     * @param transitions
     *            The transitions.
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#
     *      validateFlowContainer(com.tibco.xpd.xpdl2.Process,
     *      org.eclipse.emf.common.util.EList,
     *      org.eclipse.emf.common.util.EList)
     */
    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        for (Object next : process.getFormalParameters()) {
            if (next instanceof FormalParameter) {
                FormalParameter formal = (FormalParameter) next;
                String name = formal.getName();
                if (!JavaScriptUtil.isValidJavascriptIdentifier(name)) {
                    List<String> messages = new ArrayList<String>();
                    messages.add(name);
                    addIssue(JAVASCRIPT, formal, messages);
                }
            }
        }
    }

}
