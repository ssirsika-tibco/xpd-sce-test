package com.tibco.xpd.n2.resources.refactoring;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.n2.resources.refactoring.messages"; //$NON-NLS-1$

    public static String ActivityCancelledScriptBOMReferenceChange_Cancelled;

    public static String ActivityCompletedScriptBOMReferenceChange_Completed;

    public static String ActivityDeadlineExpiredScriptBOMReferenceChange_DeadlineExpired;

    public static String ActivityInitiatedScriptBOMReferenceChange_Initiated;

    public static String ActivityMIAdditionalLoopScriptBOMReferenceChange_MIAdditionalloop;

    public static String ActivityMIComplexExitScriptBOMReferenceChange_MIComplecExit;

    public static String ActivityMILoopScriptBOMReferenceChange_MILoopScrit;

    public static String ActivityStdLoopScriptBOMReferenceChange_StdLoop;

    public static String BOMElementRefactorParticipant_ExaminingActivities;

    public static String ScriptBOMElementRefactorParticipant_NoScriptElementsAffected;

    public static String ScriptBOMElementRefactorParticipant_ProcessingScripts;

    public static String ScriptBOMElementRefactorParticipant_RenameScriptsForActivity;

    public static String ScriptBOMElementRefactorParticipant_RenameScriptsForSequenceFlow;

    public static String ScriptBOMElementRefactorParticipant_RenamingActivitiesScripts;

    public static String BOMReferenceChange_CannotUpdate;

    public static String BOMReferenceChange_CurrentValue;

    public static String BOMReferenceChange_NoCommandFound;

    public static String BOMReferenceChange_NoCommandSet;

    public static String BOMReferenceChange_ReadOnly;

    public static String BOMReferenceChange_RefactoredValue;

    public static String ScriptBOMReferenceChange_RenamingScript;

    public static String BOMReferenceChange_ResourceDoesnotExist;

    public static String ScriptBOMReferenceChange_UpdatingScript;

    public static String BOMReferenceChange_ValidatingChange;

    public static String ReferenceChangePreviewViewer_CurrentValue;

    public static String ReferenceChangePreviewViewer_NewValue;

    public static String RescheduleTimerEventScriptBOMReferenceChange_RescheduleTimerEvent_label;

    public static String ScriptTaskScriptBOMReferenceChange_ScriptTask;

    public static String SeqFlowScriptBOMReferenceChange_SequenceFlowScript;

    public static String TimerEventScriptBOMReferenceChange_TimerEvent;

    public static String UserTaskCloseScriptBOMReferenceChange_CloseScript;

    public static String UserTaskOpenScriptBOMReferenceChange_OpenScript;

    public static String UserTaskScheduleScriptBOMReferenceChange_ScheduleScript;

    public static String UserTaskRescheduleScriptBOMReferenceChange_RescheduleScript;

    public static String UserTaskSubmitScriptBOMReferenceChange_Submit;

    public static String WSMappingBOMElementRefactorParticipant_NoDataMappingsAffected;

    public static String WSMappingBOMElementRefactorParticipant_ProcessingDataMappings;

    public static String WSMappingBOMElementRefactorParticipant_RefactoredActivity;

    public static String WSMappingBOMElementRefactorParticipant_RenamingDataMappings;

    public static String WSMappingBOMReferenceChange_DataMapping;

    public static String WSMappingBOMReferenceChange_UpdatingDataMapping;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
