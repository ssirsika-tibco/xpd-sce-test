/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.n2.resources.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.xpdl2bpel.analyzer.Analyzer;
import com.tibco.bx.xpdl2bpel.analyzer.AnalyzerIssue;
import com.tibco.xpd.n2.resources.ui.FlowAnalyzerPreferenceContributor;
import com.tibco.xpd.processeditor.xpdl2.util.ValidationControlUtil;
import com.tibco.xpd.xpdExtension.ValidationIssueOverrideType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Process migration point utilities.
 * 
 * @author aallway
 * @since 18 Jun 2012
 */
public class MigrationPointUtil {

    /**
     * Not a real issue Id but can be used with ValidationControlUtil to
     * suppress migration point decoration in UI..
     */
    public static final String BX_MIGRATION_POINT_DECORATION_SUPPRESSOR_ID =
            "suppress.bx.migrationPointDecoration"; //$NON-NLS-1$

    /**
     * Check for disable migration point decorations
     * <p>
     * This allows user to prevent execution of the flow analyzer in cases where
     * the flow is so unstructured and large that it takes a prohibitive amount
     * of time to analyze the flow.
     * 
     * @param process
     * 
     * @return <code>true</code> If user has selected the advanced property to
     *         hide migration point decorations on the process or its parent
     *         package.
     */
    public static boolean migrationPointDecorationDisabled(Process process) {
        ValidationIssueOverrideType flowAnalyzerOverride =
                ValidationControlUtil.getValidationIssueOverrideType(process,
                        BX_MIGRATION_POINT_DECORATION_SUPPRESSOR_ID);

        if (flowAnalyzerOverride != null) {
            return true;
        }

        /* Check for setting on whole package too. */
        if (process.getPackage() != null) {
            flowAnalyzerOverride =
                    ValidationControlUtil
                            .getValidationIssueOverrideType(process
                                    .getPackage(),
                                    BX_MIGRATION_POINT_DECORATION_SUPPRESSOR_ID);

            if (flowAnalyzerOverride != null) {
                return true;
            }
        }

        return false;
    }

    /**
     * @param hostProcess
     * @return The set of activities that AMX BPM process engine considers to be
     *         migration point.
     */
    public static Set<Activity> getMigrationPointActivities(Process hostProcess) {
        Set<Activity> migrationPoints = new HashSet<Activity>();

        /*
         * Sid XPD-4209 : Check for disable migration point decorations
         * 
         * This allows user to prevent execution of the flow analyzer in cases
         * where the flow is so unstructured and large that it takes a
         * prohibitive amount of time to analyze the flow.
         */
        if (!migrationPointDecorationDisabled(hostProcess)) {

            /*
             * Use the PE-Team supplied flow analyzer to check for migration
             * points.
             */
            try {
                Analyzer analyzer =
                        new Analyzer(hostProcess,
                                FlowAnalyzerPreferenceContributor
                                        .getFlowAnalyzerTimeout());
                List<AnalyzerIssue> analyzerIssues = analyzer.getIssues();

                for (AnalyzerIssue analyzerIssue : analyzerIssues) {
                    if (Analyzer.MIGRATION_POINT.equals(analyzerIssue
                            .getIssueId())) {
                        EObject issueObject = analyzerIssue.getObj();
                        if (issueObject instanceof Activity) {
                            migrationPoints.add((Activity) issueObject);
                        }
                    }
                }

            } catch (Exception e) {
                /*
                 * Ignore any exception (don't want to stop ANY normal editing
                 * of incomplete processes etc.
                 */
            }
        }

        return migrationPoints;
    }

    /**
     * @param process
     * 
     * @return <code>true</code> if the procss is the kind of process that
     *         migration point activities are relevant.
     */
    public static boolean doMigrationPointsApply(Process process) {
        return Xpdl2ModelUtil.isBusinessProcess(process)
                && N2Utils.isN2DestinationEnabled(process);
    }
}
