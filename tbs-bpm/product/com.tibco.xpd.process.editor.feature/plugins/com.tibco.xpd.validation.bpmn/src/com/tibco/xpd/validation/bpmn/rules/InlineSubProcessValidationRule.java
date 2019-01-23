/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.processeditor.xpdl2.inlineSubProcess.AnalyseInlineSubProcesses;
import com.tibco.xpd.processeditor.xpdl2.inlineSubProcess.AnalyseInlineSubProcesses.InlineSubProcessProblem;
import com.tibco.xpd.processeditor.xpdl2.inlineSubProcess.AnalyseInlineSubProcesses.ProcessCallHierarchy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.rules.IValidationRule;
import com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Check all rules applying to processes flagged as "Inline Sub-process" and
 * calls to them.
 * <p>
 * We use the AnalyseInlineSubProc class to check the rules and use the same
 * issue ID for all (tagging on the specific error from the Analysis).
 * </p>
 * <p>
 * This is done in order to keep the validation rules inline with both the
 * automatic inline on deploy facility and the manual refactor as inline
 * facility.
 * </p>
 * 
 * @author aallway
 * 
 */
public class InlineSubProcessValidationRule extends PackageValidationRule {

    /** General all-purpose inline sub-process issue. */
    private static final String ISSUE_ID = "bpmn.inlineSubProcess"; //$NON-NLS-1$

    /**
     * Additional info map key - data = The InlineSubProcessId for the problem
     * as a string.
     */
    public static final String ADDINFO_INLINE_PROBLEM_TYPE =
            "bpmn.inline.problem.type"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule#validate(com.tibco.xpd.xpdl2.Package)
     * 
     * @param pckg
     *            process package to check.
     */
    @Override
    public void validate(Package pckg) {
        Xpdl2WorkingCopyImpl wc =
                (Xpdl2WorkingCopyImpl) WorkingCopyUtil.getWorkingCopyFor(pckg);

        AnalyseInlineSubProcesses analyse =
                new AnalyseInlineSubProcesses(wc, pckg);

        List<InlineSubProcessProblem> problems = analyse.analysePackage(pckg);

        if (problems != null && problems.size() > 0) {
            for (InlineSubProcessProblem problem : problems) {
                addInlineIssue(problem);
            }
        }

        return;
    }

    /**
     * Add a validation issue for the given inloine sub-process problem.
     * 
     * @param process
     * @param problem
     */
    private void addInlineIssue(InlineSubProcessProblem problem) {
        List<String> messages = new ArrayList<String>();
        messages.add(problem.autoInlineProblemDescription);

        Map<String, String> additionalInfo = new HashMap<String, String>();
        additionalInfo.put(ADDINFO_INLINE_PROBLEM_TYPE, problem.problemId
                .toString());

        EObject problemObject = problem.subProcess;
        if (problem.subProcessTask != null) {
            problemObject = problem.subProcessTask;
        } else if (problem.extraInfo instanceof Activity) {
            problemObject = (Activity) problem.extraInfo;
        }

        addIssue(ISSUE_ID, problemObject, messages, additionalInfo);
    }

}
