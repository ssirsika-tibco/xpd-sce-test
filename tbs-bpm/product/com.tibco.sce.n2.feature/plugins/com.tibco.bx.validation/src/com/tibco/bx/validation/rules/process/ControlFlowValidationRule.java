package com.tibco.bx.validation.rules.process;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.util.EList;

import com.tibco.bx.validation.BxValidationPlugin;
import com.tibco.bx.xpdl2bpel.analyzer.Analyzer;
import com.tibco.bx.xpdl2bpel.analyzer.AnalyzerIssue;
import com.tibco.xpd.n2.resources.ui.FlowAnalyzerPreferenceContributor;
import com.tibco.xpd.processeditor.xpdl2.util.ValidationControlUtil;
import com.tibco.xpd.validation.preferences.util.ValidationPreferenceUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.ValidationIssueOverrideType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

public class ControlFlowValidationRule extends ProcessValidationRule {

    private static final String ISSUE_FLOWCONTROL_ANALYZER_FAILED =
            "bx.flowCOntrolAnalyzerFailed"; //$NON-NLS-1$

    /**
     * Issue for "Process has no migration point activities."
     */
    public static final String ISSUE_NO_MIGRATION_POINTS =
            "bx.processHasNoMigrationPoints"; //$NON-NLS-1$

    /**
     * Not a real issue Id but can be used with ValidationControlUtil to
     * suppress all flow analysis.
     */
    public static final String BX_FLOW_ANALYSER_SUPPRESSOR_ID =
            "suppress.bx.flowAnalyzer"; //$NON-NLS-1$

    /**
     * Issues for
     * "Process flow analysis has been disabled, you must re-enable it and fix flow related issues before deploying."
     */
    public static final String ISSUE_FLOW_ANALYZER_DISABLED =
            "bx.flowAnalyzerDisabled"; //$NON-NLS-1$

    /**
     * Issues for
     * "Process flow analysis has been disabled on package, you must re-enable it and fix flow related issues before deploying."
     */
    public static final String ISSUE_FLOW_ANALYZER_DISABLED_ON_PKG =
            "bx.flowAnalyzerDisabledPkg"; //$NON-NLS-1$

    @Override
    public void validate(Process process) {
        /*
         * Sid XPD-4902. Check if user has overridden ALL flow analysis.
         * 
         * This allows user to prevent execution of the flow analyzer in cases
         * where the flow is so unstructured and large tht it takes a
         * prohibitive amount of time to analyze the flow.
         */
        boolean validationSuppressed = false;

        ValidationIssueOverrideType flowAnalyzerOverride =
                ValidationControlUtil.getValidationIssueOverrideType(process,
                        BX_FLOW_ANALYSER_SUPPRESSOR_ID);

        if (flowAnalyzerOverride != null) {
            /*
             * Cannot allow actual deployment without ever having performed flow
             * analysis, so raise a problem marker to tell the user they have
             * disabled flow analysis.
             */
            validationSuppressed = true;
            addIssue(ISSUE_FLOW_ANALYZER_DISABLED, process);
        }

        /*
         * Check for package level suppression..
         * 
         * This issue is separate to the process issue above so that it can have
         * a separate resolution to re-enable all flow analysing on package
         */
        if (process.getPackage() != null) {
            ValidationIssueOverrideType pkgFlowAnalyzerOverride =
                    ValidationControlUtil
                            .getValidationIssueOverrideType(process
                                    .getPackage(),
                                    BX_FLOW_ANALYSER_SUPPRESSOR_ID);

            if (pkgFlowAnalyzerOverride != null) {
                /*
                 * Cannot allow actual deployment without ever having performed
                 * flow analysis, so raise a problem marker to tell the user
                 * they have disabled flow analysis.
                 */
                validationSuppressed = true;
                addIssue(ISSUE_FLOW_ANALYZER_DISABLED_ON_PKG, process);
            }
        }

        if (validationSuppressed) {
            return;
        }

        try {
            boolean hasErrors = false;
            boolean hasMigrationPoints = false;

            /*
             * Sid XPD-6407 - Pass timeout to analyzer.
             */

            Analyzer analyzer =
                    new Analyzer(process,
                            FlowAnalyzerPreferenceContributor
                                    .getFlowAnalyzerTimeout());

            for (AnalyzerIssue issue : analyzer.getIssues()) {

                String issueId = issue.getIssueId();

                int preferenceValue =
                        ValidationPreferenceUtil.getPreferenceValue(issueId);

                if (preferenceValue == IMarker.SEVERITY_ERROR) {
                    hasErrors = true;
                }

                if (Analyzer.MIGRATION_POINT.equals(issueId)) {
                    hasMigrationPoints = true;
                }

                /*
                 * For now, do not add INFO markers at all (otherwise we may end
                 * up with 1000's of "This is a migration point" info's
                 */
                if (preferenceValue != IMarker.SEVERITY_INFO) {
                    List<String> msgData = issue.getMsgData();
                    addIssue(issue);
                }

            }

            /*
             * Sid XPD-3148. Complain if there are no migration points at all
             * 
             * Unless there are other problems OR the validation has been
             * overridden by user in process
             * ValidationControl/ValidationIssueOverrides properties.
             */
            if (!hasErrors) {
                if (!hasMigrationPoints
                        && Xpdl2ModelUtil.isBusinessProcess(process)) {
                    /*
                     * Check if user has overridden the
                     * "Process has no migration point activities" issue - and
                     * only raise issue if not overridden.
                     */
                    ValidationIssueOverrideType overrideType =
                            ValidationControlUtil
                                    .getValidationIssueOverrideType(process,
                                            ISSUE_NO_MIGRATION_POINTS);

                    if (overrideType == null) {
                        addIssue(ISSUE_NO_MIGRATION_POINTS, process);
                    }
                }
            }

        } catch (Exception e) {
            /*
             * XPD-2319 If analyser throws exception then catch it and issue a
             * problem - else processes can slip thru to deploy time and then
             * fail on xpdl2bpel conversion
             */
            BxValidationPlugin.getDefault().getLogger()
                    .error("Flow Analyzer Exception: " + e.getMessage()); //$NON-NLS-1$

            addIssue(ISSUE_FLOWCONTROL_ANALYZER_FAILED, process);

        }
    }

    /**
     * Create a valdaiiton issue from the analyzer issue.
     * 
     * @param issue
     */
    public void addIssue(AnalyzerIssue issue) {
        if (issue.getMsgData() != null) {
            this.addIssue(issue.getIssueId(),
                    issue.getObj(),
                    issue.getMsgData());

        } else if (issue.getObj() instanceof NamedElement) {
            String displayNameOrName =
                    Xpdl2ModelUtil.getDisplayNameOrName((NamedElement) issue
                            .getObj());
            if (displayNameOrName == null || displayNameOrName.length() == 0) {
                displayNameOrName = ((NamedElement) issue.getObj()).getId();
            }

            this.addIssue(issue.getIssueId(),
                    issue.getObj(),
                    Collections.singletonList(displayNameOrName));

        } else {
            this.addIssue(issue.getIssueId(), issue.getObj());
        }
    }

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
    }

}