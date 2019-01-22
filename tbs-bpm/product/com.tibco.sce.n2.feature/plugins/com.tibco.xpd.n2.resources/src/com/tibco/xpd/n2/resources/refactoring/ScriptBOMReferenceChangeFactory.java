/*
 * Copyright (c) TIBCO Software Inc. 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.n2.resources.refactoring;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.ltk.core.refactoring.participants.RenameArguments;

import com.tibco.xpd.js.validation.tools.PackageScopeEnumCache;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Transition;

/**
 * 
 * @author mtorres
 * 
 */
public class ScriptBOMReferenceChangeFactory {

    private EObject scriptContainer;

    private List<ScriptBOMReferenceChange> changes;

    private RenameArguments args = null;

    private EObject element = null;

    /**
     * This custom property map is be used hold custom properties to support
     * flexible functionality enhancement.At this stage is used for rename
     * Arguments for qualified name of Enumeration and Enumeration cache for
     * process package scope.
     */
    private PackageScopeEnumCache packageScopeEnumCache;

    public ScriptBOMReferenceChangeFactory(EObject scriptContainer,
            RenameArguments args, EObject element) {
        this.scriptContainer = scriptContainer;
        this.args = args;
        this.element = element;
    }

    public List<ScriptBOMReferenceChange> getChanges() {
        if (changes == null) {
            TransactionalEditingDomain editingDomain =
                    XpdResourcesPlugin.getDefault().getEditingDomain();
            changes = new ArrayList<ScriptBOMReferenceChange>();
            String scriptGrammar = JsConsts.JAVASCRIPT_GRAMMAR;
            if (scriptContainer instanceof Activity) {
                Activity activity = (Activity) scriptContainer;
                if (ProcessScriptUtil.isTimerEventWithScriptType(activity,
                        scriptGrammar)) {
                    String timerEventScript =
                            ProcessScriptUtil.getTimerEventScript(activity);
                    changes.add(new TimerEventScriptBOMReferenceChange(
                            timerEventScript, args, element, activity,
                            editingDomain, packageScopeEnumCache));

                }

                if (ProcessScriptUtil
                        .isRescheduleTimerEventWithScriptType(activity,
                                scriptGrammar)) {
                    String timerEventScript =
                            ProcessScriptUtil
                                    .getRescheduleTimerEventScript(activity);
                    changes.add(new RescheduleTimerEventScriptBOMReferenceChange(
                            timerEventScript, args, element, activity,
                            editingDomain, packageScopeEnumCache));

                }

                if (ProcessScriptUtil.isScriptTaskWithScriptType(activity,
                        scriptGrammar)) {
                    String scriptTaskScript =
                            ProcessScriptUtil.getScriptTaskScript(activity);
                    changes.add(new ScriptTaskScriptBOMReferenceChange(
                            scriptTaskScript, args, element, activity,
                            editingDomain, packageScopeEnumCache));

                }

                /*
                 * If the Adhoc Task Precondition Script have the JavaScript Grammar then add them to the change to execute.
                 */
                if (ProcessScriptUtil
                        .isAdhocPreconditionTaskWithScriptType(activity,
                                scriptGrammar)) {
                    String adhocTaskScript =
                            ProcessScriptUtil
                                    .getAdhocTaskPreconditionScript(activity);
                    changes.add(new AdhocPreconditionScriptBOMReferenceChange(
                            adhocTaskScript, args, element, activity,
                            editingDomain, packageScopeEnumCache));
                }

                if (ProcessScriptUtil.isServiceTaskWithScriptType(activity,
                        scriptGrammar)
                        || ProcessScriptUtil
                                .isBwServiceTaskWithScriptType(activity,
                                        scriptGrammar)
                        || ProcessScriptUtil
                                .isJavaServiceTaskWithScriptType(activity,
                                        scriptGrammar)
                        || ProcessScriptUtil.isSendTaskWithScriptType(activity,
                                scriptGrammar)
                        || ProcessScriptUtil
                                .isReceiveTaskWithScriptType(activity,
                                        scriptGrammar)) {
                    List<ScriptInformation> allServiceScriptInformations =
                            ProcessScriptUtil
                                    .getAllServiceScriptInformations(activity,
                                            scriptGrammar);
                    if (allServiceScriptInformations != null) {
                        for (ScriptInformation scriptInformation : allServiceScriptInformations) {
                            String scriptTaskScript =
                                    ProcessScriptUtil
                                            .getTaskScript(scriptInformation);
                            changes.add(new ServiceTaskScriptBOMReferenceChange(
                                    scriptTaskScript, args, element, activity,
                                    scriptInformation, editingDomain,
                                    packageScopeEnumCache));
                        }
                    }
                }
                if (ProcessScriptUtil.isUserTaskWithOpenScriptType(activity,
                        scriptGrammar)) {
                    String scriptTaskScript =
                            ProcessScriptUtil.getUserTaskOpenScript(activity);
                    changes.add(new UserTaskOpenScriptBOMReferenceChange(
                            scriptTaskScript, args, element, activity,
                            editingDomain, packageScopeEnumCache));
                }
                if (ProcessScriptUtil.isUserTaskWithCloseScriptType(activity,
                        scriptGrammar)) {
                    String scriptTaskScript =
                            ProcessScriptUtil.getUserTaskCloseScript(activity);
                    changes.add(new UserTaskCloseScriptBOMReferenceChange(
                            scriptTaskScript, args, element, activity,
                            editingDomain, packageScopeEnumCache));

                }
                if (ProcessScriptUtil.isUserTaskWithSubmitScriptType(activity,
                        scriptGrammar)) {
                    String scriptTaskScript =
                            ProcessScriptUtil.getUserTaskSubmitScript(activity);
                    changes.add(new UserTaskSubmitScriptBOMReferenceChange(
                            scriptTaskScript, args, element, activity,
                            editingDomain, packageScopeEnumCache));
                }
                if (ProcessScriptUtil
                        .isUserTaskWithScheduleScriptType(activity,
                                scriptGrammar)) {
                    String scriptTaskScript =
                            ProcessScriptUtil
                                    .getUserTaskScheduleScript(activity);
                    changes.add(new UserTaskScheduleScriptBOMReferenceChange(
                            scriptTaskScript, args, element, activity,
                            editingDomain, packageScopeEnumCache));

                }
                if (ProcessScriptUtil
                        .isUserTaskWithRescheduleScriptType(activity,
                                scriptGrammar)) {
                    String scriptTaskScript =
                            ProcessScriptUtil
                                    .getUserTaskRescheduleScript(activity);
                    changes.add(new UserTaskRescheduleScriptBOMReferenceChange(
                            scriptTaskScript, args, element, activity,
                            editingDomain, packageScopeEnumCache));
                }
                if (ProcessScriptUtil
                        .isActivityWithInitiatedScriptType(activity,
                                scriptGrammar)) {
                    String scriptTaskScript =
                            ProcessScriptUtil.getInitiatedScript(activity);
                    changes.add(new ActivityInitiatedScriptBOMReferenceChange(
                            scriptTaskScript, args, element, activity,
                            editingDomain, packageScopeEnumCache));

                }
                if (ProcessScriptUtil
                        .isActivityWithCompletedScriptType(activity,
                                scriptGrammar)) {
                    String scriptTaskScript =
                            ProcessScriptUtil.getCompletedScript(activity);
                    changes.add(new ActivityCompletedScriptBOMReferenceChange(
                            scriptTaskScript, args, element, activity,
                            editingDomain, packageScopeEnumCache));
                }
                if (ProcessScriptUtil
                        .isActivityWithDeadlineExpiredScriptType(activity,
                                scriptGrammar)) {
                    String scriptTaskScript =
                            ProcessScriptUtil
                                    .getDeadlineExpiredScript(activity);
                    changes.add(new ActivityDeadlineExpiredScriptBOMReferenceChange(
                            scriptTaskScript, args, element, activity,
                            editingDomain, packageScopeEnumCache));

                }
                if (ProcessScriptUtil
                        .isActivityWithCancelledScriptType(activity,
                                scriptGrammar)) {
                    String scriptTaskScript =
                            ProcessScriptUtil.getCancelledScript(activity);
                    changes.add(new ActivityCancelledScriptBOMReferenceChange(
                            scriptTaskScript, args, element, activity,
                            editingDomain, packageScopeEnumCache));

                }
                if (ProcessScriptUtil.isActivityStdLoopExprHasGrammar(activity,
                        scriptGrammar)) {
                    String scriptTaskScript =
                            ProcessScriptUtil
                                    .getStdLoopExpressionScript(activity);
                    changes.add(new ActivityStdLoopScriptBOMReferenceChange(
                            scriptTaskScript, args, element, activity,
                            editingDomain, packageScopeEnumCache));

                }
                if (ProcessScriptUtil.isActivityMILoopExprHasGrammar(activity,
                        scriptGrammar)) {
                    String scriptTaskScript =
                            ProcessScriptUtil
                                    .getMILoopExpressionScript(activity);
                    changes.add(new ActivityMILoopScriptBOMReferenceChange(
                            scriptTaskScript, args, element, activity,
                            editingDomain, packageScopeEnumCache));

                }
                if (ProcessScriptUtil
                        .isActivityMIComplexExitExprHasGrammar(activity,
                                scriptGrammar)) {
                    String scriptTaskScript =
                            ProcessScriptUtil
                                    .getMIComplexExitExpressionScript(activity);
                    changes.add(new ActivityMIComplexExitScriptBOMReferenceChange(
                            scriptTaskScript, args, element, activity,
                            editingDomain, packageScopeEnumCache));
                }
                if (ProcessScriptUtil
                        .isActivityMIAdditionalInstancesExprHasGrammar(activity,
                                scriptGrammar)) {
                    String scriptTaskScript =
                            ProcessScriptUtil
                                    .getMIAdditionalLoopExpressionScript(activity);
                    changes.add(new ActivityMIAdditionalLoopScriptBOMReferenceChange(
                            scriptTaskScript, args, element, activity,
                            editingDomain, packageScopeEnumCache));

                }
            } else if (scriptContainer instanceof Transition) {
                Transition transition = (Transition) scriptContainer;
                if (ProcessScriptUtil.isSeqFlowWithScriptType(transition,
                        scriptGrammar)) {
                    String scriptTaskScript =
                            ProcessScriptUtil.getSeqFlowScript(transition);
                    changes.add(new SeqFlowScriptBOMReferenceChange(
                            scriptTaskScript, args, element, transition,
                            editingDomain, packageScopeEnumCache));

                }
            }
        }
        return changes;
    }

    /**
     * Returns the cache Map of enumerations in process package scope.
     * 
     * @return the packageScopeEnumCache
     */
    public PackageScopeEnumCache getPackageScopeEnumCache() {
        return packageScopeEnumCache;
    }

    /**
     * @param packageScopeEnumCache
     *            the packageScopeEnumCache to set
     */
    public void setPackageScopeEnumCache(
            PackageScopeEnumCache packageScopeEnumCache) {
        this.packageScopeEnumCache = packageScopeEnumCache;
    }

}
