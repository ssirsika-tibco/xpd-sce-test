/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.rules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessRelevantDataUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ReferenceTaskUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.resources.util.EqualityHelperXpd;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.validations.bpmn.internal.Messages;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Reference;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Rule to check all aspects of reference tasks
 * 
 * @author aallway
 */
public class ReferenceTaskRule extends ProcessValidationRule {

    private static final String REFERENCE_TO_REFERENCE =
            "bpmn.referenceToReferenceTask"; //$NON-NLS-1$

    private static final String TASK_UNSET = "bpmn.referenceToUnknownTask"; //$NON-NLS-1$

    private static final String INVALID_TASK = "bpmn.referenceToInvalidTask"; //$NON-NLS-1$

    private static final String INVALID_LIBRARY =
            "bpmn.referenceToInvalidTaskLibrary"; //$NON-NLS-1$

    private static final String REFTASK_MISSING_LOCAL_DATA =
            "bpmn.referenceTaskMissingData"; //$NON-NLS-1$

    private static final String REFTASK_LOCAL_DATA_MISMATCH =
            "bpmn.referenceTaskDataMismatch"; //$NON-NLS-1$

    public static final String ADDINFO_LIBRARYTASK_ID =
            "bpmn.AdditionalInfoLibraryTaskId"; //$NON-NLS-1$

    public static final String ADDINFO_LIBRARYDATA_NAME =
            "bpmn.AdditionalInfoLibraryDataName"; //$NON-NLS-1$

    public static final String ADDINFO_REFERINGPROCESS_ID =
            "bpmn.AdditionalInfoReferringProcessId"; //$NON-NLS-1$

    public static final String ADDINFO_REFERINGTASK_ID =
            "bpmn.AdditionalInfoReferringTaskId"; //$NON-NLS-1$

    public static final String PROCESS_OVERRIDING_PARTICIPANT_ID =
            "bpmn.ProcessCannotSetParticipant"; //$NON-NLS-1$

    /**
     * @param container
     *            The container to check.
     * @see com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule#
     *      validate(com.tibco.xpd.xpdl2.FlowContainer)
     */
    @Override
    public void validate(Process process) {

        // Map of task library to set of objects referenced by library tasks
        // referenced from this process.
        Map<Process, Set<LibraryDataAndTask>> referencedDataCache =
                new HashMap<Process, Set<LibraryDataAndTask>>();

        Collection<Activity> allActs =
                Xpdl2ModelUtil.getAllActivitiesInProc(process);

        for (Object next : allActs) {
            Activity activity = (Activity) next;
            Implementation implementation = activity.getImplementation();
            if (implementation instanceof Reference) {
                String referencedTaskId =
                        ReferenceTaskUtil.getReferencedTaskId(activity);

                if (referencedTaskId == null
                        || referencedTaskId.length() == 0
                        || TaskObjectUtil.UNKNOWN_REF_ID
                                .equals(referencedTaskId)) {
                    // 
                    // No task selected yet.
                    //
                    addIssue(TASK_UNSET, activity);

                } else if (ReferenceTaskUtil
                        .isTaskLibraryTaskReference(activity)) {
                    // 
                    // Check library task.
                    //
                    Activity libraryTask =
                            ReferenceTaskUtil
                                    .getReferencedLibraryTask(activity);

                    if (libraryTask == null) {
                        if (ReferenceTaskUtil
                                .getReferencedTaskLibrary(activity) == null) {
                            addIssue(INVALID_LIBRARY, activity);
                        } else {
                            addIssue(INVALID_TASK, activity);
                        }

                    } else {
                        // 
                        // We can access the task library task.
                        // Check data referenced by library task is present in
                        // referencing task scope.
                        addReferenceDataToCheck(activity,
                                libraryTask,
                                referencedDataCache);

                        // Check that the referencing task does not override the
                        // participant illegally.
                        checkParticipantSetting(activity, libraryTask);
                    }

                } else {
                    // 
                    // Check local task reference.
                    //
                    Activity referencedActivity =
                            ReferenceTaskUtil
                                    .getReferencedLocalActivity(activity);

                    if (referencedActivity != null) {
                        checkReferencedActivityType(activity,
                                referencedActivity);

                    } else {
                        addIssue(INVALID_TASK, activity);
                    }
                }
            }
        }

        //
        // Check that all task library data reference
        checkReferenceTaskLibraryData(process, referencedDataCache);

        return;
    }

    /**
     * Check that the referencing task does not override the // participant
     * illegally.
     * 
     * @param activity
     * @param libraryTask
     */
    private void checkParticipantSetting(Activity activity, Activity libraryTask) {
        EObject[] performers =
                TaskObjectUtil.getActivityPerformers(activity, activity
                        .getProcess());

        if (performers != null && performers.length > 0) {
            boolean configAsSetInProcess =
                    Boolean.TRUE
                            .equals(Xpdl2ModelUtil
                                    .getOtherAttribute(libraryTask,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_SetPerformerInProcess()));
            
            if (!configAsSetInProcess) {
                addIssue(PROCESS_OVERRIDING_PARTICIPANT_ID, activity);
            }
        }
        return;
    }

    /**
     * Validate that the task library data referenced by library tasks that are
     * referenced by local process tasks are all present and correct in local
     * process.
     * 
     * @param process
     * @param referencedDataCache
     */
    private void checkReferenceTaskLibraryData(Process localProcess,
            Map<Process, Set<LibraryDataAndTask>> referencedDataCache) {
        //
        // Create a EMF object equality tester for comparing library and process
        // data.
        EqualityHelperXpd processRelevantDataEqualityTester =
                ReferenceTaskUtil.createDataEqualityHelper();

        // 
        // Create a map of local process data names for quick lookup.
        Map<String, ProcessRelevantData> localProcessNameToDataMap =
                ProcessRelevantDataUtil.getProcessDataNameMap(localProcess);

        //
        // Go thru the referenced library's data checking for consistency with
        // local process data.
        for (Entry<Process, Set<LibraryDataAndTask>> entry : referencedDataCache
                .entrySet()) {
            Process taskLibrary = entry.getKey();

            Set<LibraryDataAndTask> refdLibraryDataSet = entry.getValue();

            //
            // Go thru the data from this task library that is referenced by
            // tasks the process references
            for (LibraryDataAndTask refdLibraryData : refdLibraryDataSet) {
                ProcessRelevantData localData =
                        localProcessNameToDataMap
                                .get(refdLibraryData.libraryData.getName());

                if (localData == null) {
                    // 
                    // There is no local data with the same name.
                    addIssue(REFTASK_MISSING_LOCAL_DATA,
                            refdLibraryData.referenceTask,
                            getDataAndLibraryNameList(refdLibraryData),
                            getLibraryTaskAdditionalInfo(refdLibraryData));
                } else {
                    //
                    // Compare the library data field and the local process
                    // data field / formal parameter.
                    String diffArea =
                            ReferenceTaskUtil
                                    .compareProcessRelevantData(processRelevantDataEqualityTester,
                                            refdLibraryData.libraryData,
                                            localData);
                    if (diffArea != null) {
                        addIssue(REFTASK_LOCAL_DATA_MISMATCH,
                                localData,
                                getDataAndLibraryNameList(refdLibraryData,
                                        "'" + diffArea + "'"), //$NON-NLS-1$//$NON-NLS-2$
                                getLibraryTaskAdditionalInfo(refdLibraryData));
                    }
                }
            }
        }

        return;
    }

    /**
     * @param libData
     * @param taskLibrary
     * @param supplementary
     * @return list containing the name strings for library data field, library
     *         and optional supplementary data
     */
    private List<String> getDataAndLibraryNameList(LibraryDataAndTask refLibData) {
        return getDataAndLibraryNameList(refLibData, null);
    }

    /**
     * @param libData
     * @param taskLibrary
     * @param supplementary
     * @return list containing the name strings for library data field, library
     *         and operional
     */
    private List<String> getDataAndLibraryNameList(
            LibraryDataAndTask refLibData, String supplementary) {
        List<String> msgs = new ArrayList<String>();
        msgs.add(Xpdl2ModelUtil.getDisplayNameOrName(refLibData.libraryData));

        Process taskLib = refLibData.libraryTask.getProcess();

        msgs.add(Xpdl2ModelUtil.getDisplayNameOrName(taskLib));
        msgs.add(Xpdl2ModelUtil.getDisplayNameOrName(refLibData.libraryTask));
        msgs.add(supplementary == null ? "" : supplementary); //$NON-NLS-1$

        return msgs;
    }

    /**
     * @param refdLibraryData
     * @return
     */
    private Map<String, String> getLibraryTaskAdditionalInfo(
            LibraryDataAndTask refdLibraryData) {
        Map<String, String> libraryTaskAdditionalInfo =
                new HashMap<String, String>();
        libraryTaskAdditionalInfo.put(ADDINFO_LIBRARYTASK_ID,
                refdLibraryData.libraryTask.getId());
        libraryTaskAdditionalInfo.put(ADDINFO_LIBRARYDATA_NAME,
                refdLibraryData.libraryData.getName());
        libraryTaskAdditionalInfo.put(ADDINFO_REFERINGPROCESS_ID,
                refdLibraryData.referenceTask.getProcess().getId());
        libraryTaskAdditionalInfo.put(ADDINFO_REFERINGTASK_ID,
                refdLibraryData.referenceTask.getId());
        return libraryTaskAdditionalInfo;
    }

    /**
     * Add any Task Library data (field/param) that is referenced by the given
     * library task and add it to the set already cached for the parent library
     * (creating cache if first one).
     * <p>
     * This is to save doing unnecessary multiple checking for the same library
     * data when multiple reference tasks reference the same library.
     * <p>
     * checkReferencedTaskLibraryData() will do the actual checking later.
     * 
     * @param libraryTask
     * @param referencedDataCache
     */
    private void addReferenceDataToCheck(Activity referenceTask,
            Activity libraryTask,
            Map<Process, Set<LibraryDataAndTask>> referencedDataCache) {

        Process taskLibrary = libraryTask.getProcess();
        if (taskLibrary != null) {
            //
            // Get the current list of task library data referenced by
            // preceding referenced library tasks from the same library.
            Set<LibraryDataAndTask> refdLibraryObjects =
                    referencedDataCache.get(taskLibrary);
            if (refdLibraryObjects == null) {
                //
                // Or create one first time in.
                refdLibraryObjects = new HashSet<LibraryDataAndTask>();
                referencedDataCache.put(taskLibrary, refdLibraryObjects);
            }

            // Add the set of data fields referenced by this particular task.

            List<ProcessRelevantData> libraryTaskReferencedData =
                    ReferenceTaskUtil.getLibraryTaskReferencedData(libraryTask);
            for (ProcessRelevantData libData : libraryTaskReferencedData) {
                refdLibraryObjects.add(new LibraryDataAndTask(libData,
                        libraryTask, referenceTask));
            }

        }

        return;
    }

    /**
     * @param activity
     *            The activity being validated.
     * @param referencedActivity
     *            The referenced activity.
     */
    private void checkReferencedActivityType(Activity activity,
            Activity referencedActivity) {
        Implementation implementation = referencedActivity.getImplementation();
        if (implementation instanceof Reference) {
            Reference reference = (Reference) implementation;
            if (reference != null) {
                addIssue(REFERENCE_TO_REFERENCE, activity);
            }
        }
        return;
    }

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        // We do everything at process level - so don't need to do any for
        // individual activity containers separately
    }

    /**
     * Simple data class to store a task library field along with the first
     * referenced library task it was used in.
     * <p>
     * Used only in the caching of referenced task library data so equality is
     * based only on the library data.
     * 
     */
    private static class LibraryDataAndTask {
        Activity libraryTask;

        Activity referenceTask;

        ProcessRelevantData libraryData;

        public LibraryDataAndTask(ProcessRelevantData libraryData,
                Activity libraryTask, Activity referenceTask) {
            this.libraryData = libraryData;
            this.libraryTask = libraryTask;
            this.referenceTask = referenceTask;
        }

        @Override
        public int hashCode() {
            return libraryData.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof LibraryDataAndTask) {
                return libraryData
                        .equals(((LibraryDataAndTask) obj).libraryData);
            } else if (obj instanceof ProcessRelevantData) {
                return libraryData.equals(obj);
            }
            return false;
        }
    }
}
