/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.CopyCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.utils.ActionUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.Xpdl2ProcessDiagramUtils;
import com.tibco.xpd.processwidget.adapters.ProcessPasteCommand;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.util.EqualityHelperXpd;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.InitialValues;
import com.tibco.xpd.xpdExtension.TaskLibraryReference;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Reference;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.resolvers.Xpdl2FieldOrParamResolver;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Utilities for helping with reference tasks.
 * 
 * @author aallway
 * @since 3.2
 */
public class ReferenceTaskUtil {

    /**
     * Return true if the given reference task activity is configured to
     * reference a task in a task library.
     * <p>
     * <b>NOTE:</b> This will be true EVEN if the reference is not completely
     * configured. (i.e. user may have chosen that a library task should be
     * referenced WITHOUT YET selecting a task.
     * 
     * @param activity
     * @return true if the given reference task activity is configured to
     *         reference a task in a task library.
     */
    public static boolean isTaskLibraryTaskReference(Activity activity) {
        //
        // Make sure this is
        TaskLibraryReference extActRef = getTaskLibraryReference(activity);
        if (extActRef != null) {
            return true;
        }
        return false;
    }

    /**
     * @param act
     * @return Get the referenced task id.
     */
    public static String getReferencedTaskId(Activity act) {
        if (TaskType.REFERENCE_LITERAL.equals(TaskObjectUtil.getTaskType(act))) {
            Reference ref = (Reference) act.getImplementation();

            return ref.getActivityId();
        }

        return null;
    }

    /**
     * @param activity
     * @return The xpdExt:ExternalActivityRef if the given activity is a
     *         reference task that references an activity defined in a task
     *         library otherwise null.
     */
    public static TaskLibraryReference getTaskLibraryReference(Activity activity) {
        TaskLibraryReference extActRef = null;

        if (activity.getImplementation() instanceof Reference) {
            Reference taskRef = (Reference) activity.getImplementation();

            extActRef =
                    (TaskLibraryReference) Xpdl2ModelUtil
                            .getOtherElement(taskRef,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_TaskLibraryReference());
        }
        return extActRef;
    }

    /**
     * @param activity
     * @return The local activity referenced by the given reference task (or
     *         null if no reference set or task library reference is set).
     */
    public static Activity getReferencedLocalActivity(Activity activity) {
        if (!isTaskLibraryTaskReference(activity)) {
            String taskId = getReferencedTaskId(activity);

            return getActivityFromProcess(taskId, activity.getProcess());
        }

        return null;
    }

    /**
     * Get the referenced activity regardless of whether it's a library or local
     * task.
     * 
     * @param activity
     * @return The activity referenced by the given reference activity - this
     *         might be either a local process task or an activity in a task
     *         library or null if could not be located.
     */
    public static Activity getReferencedTask(Activity activity) {
        if (isTaskLibraryTaskReference(activity)) {
            return getReferencedLibraryTask(activity);

        } else {
            return getReferencedLocalActivity(activity);
        }
    }

    /**
     * Use this method ONLY if you absolutely need the actual EObject -
     * otherwise use getReferencedLibraryTaskIndexerItem(). This will save
     * unnecessary loading of the referenced model into working copy.
     * 
     * @param activity
     * @return The task library activity referenced in the external activity
     *         reference (or null if no reference set or local task reference is
     *         set).
     */
    public static Activity getReferencedLibraryTask(Activity activity) {
        if (activity.getImplementation() instanceof Reference) {
            Reference taskRef = (Reference) activity.getImplementation();

            if (isTaskLibraryTaskReference(activity)) {

                String taskId = taskRef.getActivityId();
                if (taskId != null) {
                    return Xpdl2WorkingCopyImpl.locateTaskLibraryTask(taskId);
                }
            }
        }

        return null;
    }

    /**
     * Use this method rather than getReferenceTaskLibrary() unless you
     * ABSOLUTELY require the actual Process EObject - this method will not
     * cause a load of the task library resource so is more efficient.
     * 
     * @param activity
     * @return The indexer item task library in which the referenced library
     *         task is located or null if not a library task reference or no
     *         task reference set.
     */
    public static IndexerItem getReferencedLibraryTaskIndexerItem(
            Activity activity) {
        if (isTaskLibraryTaskReference(activity)) {
            String taskId = getReferencedTaskId(activity);
            if (taskId != null && taskId.length() > 0) {
                return ProcessUIUtil.getTaskLibraryTaskIndexItem(taskId);
            }
        }
        return null;
    }

    /**
     * Use this method ONLY if you absolutely need the actual EObject -
     * otherwise use getReferencedTaskLibraryIndexerItem(). This will save
     * unnecessary loading of the referenced model into working copy.
     * 
     * @param activity
     * @return The task library in which the referenced library task is located
     *         or null if not a library task reference or no task reference set.
     */
    public static Process getReferencedTaskLibrary(Activity activity) {
        TaskLibraryReference taskLibRef = getTaskLibraryReference(activity);

        if (taskLibRef != null) {
            String taskLibId = taskLibRef.getTaskLibraryId();
            if (taskLibId != null && taskLibId.length() > 0) {
                return Xpdl2WorkingCopyImpl.locateTaskLibrary(taskLibId);
            }
        }
        return null;
    }

    /**
     * Use this method rather than getReferenceTaskLibrary() unless you
     * ABSOLUTELY require the actual Process EObject - this method will not
     * cause a load of the task library resource so is more efficient.
     * 
     * @param activity
     * @return The indexer item task library in which the referenced library
     *         task is located or null if not a library task reference or no
     *         task reference set.
     */
    public static IndexerItem getReferencedTaskLibraryIndexerItem(
            Activity activity) {
        TaskLibraryReference taskLibRef = getTaskLibraryReference(activity);

        if (taskLibRef != null) {
            String taskLibId = taskLibRef.getTaskLibraryId();
            if (taskLibId != null && taskLibId.length() > 0) {
                return ProcessUIUtil.getTaskLibraryIndexItem(taskLibId);
            }
        }

        return null;
    }

    /**
     * @param ed
     * @param activity
     * @param refTaskId
     *            Referenced Task or null to configure as local task reference
     *            with no ref set.
     * @return Command to set local task referenced
     */
    public static Command getSetReferencedLocalTaskCommand(EditingDomain ed,
            Activity activity, String refTaskId) {

        Activity referencedTask = null;

        Reference ref = Xpdl2Factory.eINSTANCE.createReference();

        if (refTaskId == null || refTaskId.length() == 0) {
            ref.setActivityId(TaskObjectUtil.UNKNOWN_REF_ID);
        } else {
            ref.setActivityId(refTaskId);

            referencedTask =
                    getActivityFromProcess(refTaskId, activity.getProcess());
        }

        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.TaskObjectUtil_SetToReferenceLocalTask_menu);

        cmd.append(SetCommand.create(ed,
                activity,
                Xpdl2Package.eINSTANCE.getActivity_Implementation(),
                ref));

        //
        // If current reference task's name is default for ref task then
        // change to "Ref: <refdTask>"
        if (referencedTask != null) {
            addSetReferenceTaskNameCommand(ed, cmd, activity, referencedTask);
        }

        return cmd;
    }

    /**
     * 
     * @param ed
     * @param referenceTaskActivity
     * @param libraryTaskId
     * @param taskLibrary
     * 
     * @return Command to set the reference to the given library task.
     */
    public static Command getSetReferencedLibraryTaskCommand(EditingDomain ed,
            Activity referenceTaskActivity, Process referenceTaskProcess,
            String libraryTaskId, Process taskLibrary) {

        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.TaskObjectUtil_SetToReferenceLocalTask_menu);

        //
        // Get the task library package reference (if there is one).
        String externalPackageHref = null;
        if (taskLibrary != null) {
            WorkingCopy externalWc =
                    WorkingCopyUtil.getWorkingCopyFor(taskLibrary);
            Xpdl2WorkingCopyImpl wc =
                    (Xpdl2WorkingCopyImpl) WorkingCopyUtil
                            .getWorkingCopyFor(referenceTaskProcess);
            if (wc != null && wc != externalWc) {
                externalPackageHref =
                        wc.appendCreateReferenceCommand(externalWc, cmd);
            }
        }

        //
        // Create a new xpdl Reference Element
        Reference ref =
                createLibraryTaskReference(libraryTaskId,
                        taskLibrary,
                        externalPackageHref);

        // Set it in the activity
        cmd.append(SetCommand.create(ed,
                referenceTaskActivity,
                Xpdl2Package.eINSTANCE.getActivity_Implementation(),
                ref));

        //
        // Setup the extra bits and pieces.
        if (taskLibrary != null) {
            Activity libraryTask =
                    getActivityFromProcess(libraryTaskId, taskLibrary);
            if (libraryTask == null) {
                throw new IllegalStateException(
                        "Bad library task reference id: " + libraryTaskId); //$NON-NLS-1$
            }

            //
            // If current reference task's name is default for ref task then
            // change to "Ref: <refdTask>"
            addSetReferenceTaskNameCommand(ed,
                    cmd,
                    referenceTaskActivity,
                    libraryTask);

            //
            // Add any data fields that are referenced by the reference task to
            // the local process.
            // Effectively this can be done simply same as a 'paste' command in
            // that we ONLY want to create field if one with the same name does
            // not already exist in the process.
            List<ProcessRelevantData> referencedLibData =
                    getLibraryTaskReferencedData(libraryTask);

            Command addDataCmd =
                    getAddTaskLibraryDataToProcessCommand(ed,
                            referenceTaskProcess,
                            referencedLibData);

            if (addDataCmd != null) {
                cmd.append(addDataCmd);
            }
        }

        return cmd;
    }

    /**
     * Create a new xpdl Reference element for the given library task.
     * 
     * @param libraryTaskId
     *            Library task id or <code>null</code> for empty task reference.
     * @param taskLibrary
     *            Task Library or <code>null</code> for empty task reference.
     * @param externalPackageHref
     *            Library task href (as previously returned by
     *            {@link Xpdl2WorkingCopyImpl#appendAddReferenceCommand(EditingDomain, CompoundCommand, com.tibco.xpd.xpdl2.ExternalPackage, com.tibco.xpd.xpdl2.Package)}
     *            for the task library's package) or <code>null</code> for empty
     *            reference.
     * 
     * @return New xpdl:Reference element.
     */
    public static Reference createLibraryTaskReference(String libraryTaskId,
            Process taskLibrary, String externalPackageHref) {

        Reference ref = Xpdl2Factory.eINSTANCE.createReference();

        if (libraryTaskId == null || libraryTaskId.length() == 0) {
            ref.setActivityId(TaskObjectUtil.UNKNOWN_REF_ID);
        } else {
            ref.setActivityId(libraryTaskId);
        }

        TaskLibraryReference extActRef =
                XpdExtensionFactory.eINSTANCE.createTaskLibraryReference();
        Xpdl2ModelUtil.addOtherElement(ref, XpdExtensionPackage.eINSTANCE
                .getDocumentRoot_TaskLibraryReference(), extActRef);

        if (externalPackageHref != null && externalPackageHref.length() > 0) {
            extActRef.setPackageRef(externalPackageHref);
        }

        if (taskLibrary != null) {
            extActRef.setTaskLibraryId(taskLibrary.getId());
        } else {
            extActRef.setTaskLibraryId(""); //$NON-NLS-1$
        }

        return ref;
    }

    /**
     * Get the default name for the task referencing the given task <b>or
     * <code>null</code> if it is
     * 
     * @param taskName
     * 
     * @return <code>true</code> if the given name looks like default for a
     *         reference task.
     */
    public static boolean isDefaultTaskName(String taskName) {
        String refTaskLeader =
                Messages.ReferenceTaskUtil_RefTaskLabelLeader_label;

        if (taskName == null || taskName.length() == 0
                || taskName.startsWith(refTaskLeader)
                || taskName.startsWith(TaskType.REFERENCE_LITERAL.toString())) {
            return true;
        }
        return false;
    }

    /**
     * @param referencingTask
     * @param referencedTask
     * 
     * @return The default referencing task name for the given referenced task.
     */
    public static String getDefaultReferenceTaskName(Activity referencedTask) {
        String refTaskLeader =
                Messages.ReferenceTaskUtil_RefTaskLabelLeader_label;

        String libTaskName =
                Xpdl2ModelUtil.getDisplayNameOrName(referencedTask);

        return refTaskLeader + " " + libTaskName; //$NON-NLS-1$
    }

    /**
     * Get the command that will add the given task library data fields to the
     * process for the given referenceTask actvitity IF it is necessary.
     * <p>
     * If fields reference other things (type declarations) then these will be
     * copied also.
     * <p>
     * If same name data already exists in destination process then do not add
     * it.
     * 
     * @param ed
     * @param referenceTask
     * @param taskLibraryData
     * @return Command to add any of the given data that does not exist yet in
     *         the process or null if nothing to do.
     */
    public static Command getAddTaskLibraryDataToProcessCommand(
            EditingDomain ed, Process process,
            Collection<ProcessRelevantData> taskLibraryData) {
        if (taskLibraryData != null && !taskLibraryData.isEmpty()) {
            // Copy and grab any extra things referenced by the library data
            // that is referenced (type declarations).
            Collection<EObject> copyList =
                    ActionUtil.getCopyElements(ed, taskLibraryData, false);
            if (copyList != null && copyList.size() > 0) {
                // re-paste into the local, process - this should ignore
                // same-named items.
                ProcessPasteCommand addDataCmd =
                        Xpdl2ProcessDiagramUtils
                                .getAddDiagramObjectsCommand(ed,
                                        process,
                                        process,
                                        null,
                                        copyList,
                                        null,
                                        false,
                                        true);
                if (addDataCmd != null && addDataCmd.canExecute()) {
                    CompoundCommand cmd =
                            new CompoundCommand(
                                    Messages.ReferenceTaskUtil_CopyRefTaskData_menu);
                    cmd.append(addDataCmd);
                    return cmd;
                }
            }
        }
        return null;
    }

    /**
     * Get Command to change the equivalent (by name) data in the process so
     * that it matches the type specification of the given list of library data.
     * <p>
     * The things that this method fixes should be kept in synch with the
     * comparison method ReferenceTaskRule.compareProcessRelevantData()
     * 
     * @param editingDomain
     * @param localProcess
     * @param singletonList
     * 
     * @return COmmand that synchronises the significant parts of data
     *         specification with the given library fields.
     */
    public static Command getFixTaskLibraryDataInProcessCommand(
            EditingDomain editingDomain, Process localProcess,
            List<ProcessRelevantData> taskLibraryData) {
        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.ReferenceTaskUtil_FixTaskLibraryData_menu);

        Map<String, ProcessRelevantData> processDataMap =
                ProcessRelevantDataUtil.getProcessDataNameMap(localProcess);

        for (ProcessRelevantData libData : taskLibraryData) {
            //
            // Copy the things that must be the same for the copy of task
            // library data in the process.
            ProcessRelevantData procData =
                    processDataMap.get(libData.getName());
            if (procData != null) {
                cmd.append(getSynchDataCommand(editingDomain, libData, procData));
            }

        }

        return cmd;
    }

    /**
     * Get the list of Task Library data fields that are is referenced by the
     * given library task.
     * <p>
     * 
     * @param libraryTask
     * @return List of Data Fields
     */
    public static List<ProcessRelevantData> getLibraryTaskReferencedData(
            Activity libraryTask) {

        //
        // For User Tasks / Manual Tasks - If there are no explicit data
        // associations with task then it is implicitly associated with all and
        // therefore we should say it references all data in the library.
        //
        // This is true ONLY for user/manual becuase the data associations have
        // a DIRECT IMPACT on the functioning of the task itself. Whereas other
        // task types use the associated data only to filter the available data
        // for design time things like filtering data available for mapping.
        //
        // So for the other task types we only need specify the library data
        // that is actually referenced.
        TaskType taskType = TaskObjectUtil.getTaskType(libraryTask);
        if (TaskType.USER_LITERAL.equals(taskType)
                || TaskType.MANUAL_LITERAL.equals(taskType)) {
            List<AssociatedParameter> assocParams =
                    ProcessInterfaceUtil
                            .getActivityAssociatedParameters(libraryTask);
            if (assocParams == null || assocParams.isEmpty()) {
                /*
                 * Sid XPD-2087: Only use all data implicitly if implicit
                 * association has not been disabled.
                 */
                if (!ProcessInterfaceUtil
                        .isImplicitAssociationDisabled(libraryTask)) {
                    return ProcessInterfaceUtil
                            .getAllAvailableRelevantDataForActivity(libraryTask);
                } else {
                    return Collections.emptyList();
                }
            }
        }

        //
        // Get the fields explicitly referenced by the library task.
        Xpdl2FieldOrParamResolver refResolver =
                new Xpdl2FieldOrParamResolver(libraryTask);

        Set<ProcessRelevantData> dataRefs =
                refResolver.getDataReferences(libraryTask);
        if (dataRefs != null && !dataRefs.isEmpty()) {
            return new ArrayList<ProcessRelevantData>(dataRefs);
        }

        return Collections.emptyList();
    }

    /**
     * Create and return an EMF equality helper to help us compare task library
     * data fields with process field/param with same name.
     * <p>
     * For use with -
     * 
     * @return Equality helper for process relevant data items of same name.
     */
    public static EqualityHelperXpd createDataEqualityHelper() {
        EqualityHelperXpd eq = new EqualityHelperXpd();

        // For debugging - Log mismatches...
        // eq.setConsoleLoggingOn(true);

        return eq;
    }

    /**
     * Check for differences in the given fields/params. <b>Remember</b> that
     * once copied from a task library, a data field can be refactored to
     * correlation data / parameter; so we must only compare the things that
     * matter (and are available in all types).
     * <p>
     * Changes to this method will need to be synchronised with changes to
     * getFixTaskLibraryDataInProcessCommand()
     * 
     * @param eqHelper
     *            As returned by createDataEqualityHelper
     * @param taskLibraryData
     * @param processData
     * @return null if the fields are equivalent or area (translatable msg) in
     *         which they are not equivalent.
     */
    public static String compareProcessRelevantData(EqualityHelperXpd eqHelper,
            ProcessRelevantData taskLibraryData, ProcessRelevantData processData) {

        if (taskLibraryData.isReadOnly() != processData.isReadOnly()) {
            return Messages.ReferenceTaskRule_ReadOnlyProblemArea_label;
        }

        if (taskLibraryData.isIsArray() != processData.isIsArray()) {
            return Messages.ReferenceTaskRule_IsArrayProblemArea_label;
        }

        //
        // Compare the data types.
        // (To be fair we are only interested in the type that the library
        // and process data fields will actually finally be (i.e. a process
        // field of type Text;10 is considered equivalent of a library field
        // of delcared type when ref'd TypeDelaration is of type Text:10
        // too.)
        DataType libDataType =
                ProcessRelevantDataUtil.getFinalDataType(taskLibraryData);
        DataType procDataType =
                ProcessRelevantDataUtil.getFinalDataType(processData);

        if (!eqHelper.equals(libDataType, procDataType)) {
            return Messages.ReferenceTaskRule_DataTypeProblemArray_label;
        }

        if (taskLibraryData instanceof DataField) {
            List<String> taskLibInitVals =
                    new ArrayList<String>(
                            ProcessDataUtil
                                    .getDataInitialValues(taskLibraryData,
                                            false));

            List<String> procInitVals =
                    new ArrayList<String>(
                            ProcessDataUtil.getDataInitialValues(processData,
                                    false));

            Comparator<String> comp = new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o1.compareTo(o2);
                }
            };

            Collections.sort(taskLibInitVals, comp);
            Collections.sort(procInitVals, comp);

            if (!taskLibInitVals.equals(procInitVals)) {
                return Messages.ReferenceTaskRule_InitValProblemArea_label;
            }
        }

        //
        // As far as we're concerned they are the same.
        return null;
    }

    /**
     * Get command to synchronise an individual process data with the library
     * data field.
     * <p>
     * The things that this method fixes should be kept in synch with the
     * comparison method ReferenceTaskRule.compareProcessRelevantData()
     * 
     * @param editingDomain
     * @param libData
     * @param procData
     * @return Command to synchronise an individual process data with the
     *         library data field
     */
    private static Command getSynchDataCommand(EditingDomain editingDomain,
            ProcessRelevantData libData, ProcessRelevantData procData) {
        CompoundCommand cmd = new CompoundCommand();

        //
        // Read Only Flag
        Object readOnly =
                libData.isSetReadOnly() ? libData.isReadOnly()
                        : SetCommand.UNSET_VALUE;

        cmd.append(SetCommand.create(editingDomain,
                procData,
                Xpdl2Package.eINSTANCE.getProcessRelevantData_ReadOnly(),
                readOnly));

        //
        // Is Array Flag
        Object isArray =
                libData.isSetIsArray() ? libData.isIsArray()
                        : SetCommand.UNSET_VALUE;

        cmd.append(SetCommand.create(editingDomain,
                procData,
                Xpdl2Package.eINSTANCE.getProcessRelevantData_IsArray(),
                isArray));

        //
        // Data Type -
        // Rather than going thru all the potentially nasty (and not
        // possible) combinations that might occur with declared type data types
        // we will just set the data field to be the same non-declared final
        // data type of the library data.
        DataType libDataType =
                ProcessRelevantDataUtil.getFinalDataType(libData);
        if (libDataType != null) {
            Command copy =
                    CopyCommand.create(editingDomain,
                            Collections.singletonList(libDataType));
            copy.execute();

            DataType procDataType =
                    (DataType) copy.getResult().iterator().next();

            cmd.append(SetCommand.create(editingDomain,
                    procData,
                    Xpdl2Package.eINSTANCE.getProcessRelevantData_DataType(),
                    procDataType));
        }

        //
        // Initial value.
        if (libData instanceof DataField) {
            InitialValues libInitVals =
                    (InitialValues) Xpdl2ModelUtil.getOtherElement(libData,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_InitialValues());
            if (libInitVals != null) {
                Command copy =
                        CopyCommand.create(editingDomain,
                                Collections.singletonList(libInitVals));
                copy.execute();

                libInitVals =
                        (InitialValues) copy.getResult().iterator().next();
            }

            cmd.append(Xpdl2ModelUtil.getSetOtherElementCommand(editingDomain,
                    procData,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_InitialValues(),
                    libInitVals));
        }

        return cmd;
    }

    /**
     * @param actId
     * @param process
     * @return Activity within given id from given process (regardless of
     *         whether it is in process or child activity set.
     */
    public static Activity getActivityFromProcess(String actId, Process process) {
        if (actId != null && actId.length() > 0 && process != null) {
            Collection<Activity> acts =
                    Xpdl2ModelUtil.getAllActivitiesInProc(process);
            for (Activity a : acts) {
                if (actId.equals(a.getId())) {
                    return a;
                }
            }
        }
        return null;
    }

    /**
     * If current reference task's name is default for ref task then change to
     * "Ref: <refdTask>"
     * 
     * @param ed
     * @param cmd
     * @param referencingTask
     * @param referencedTask
     */
    private static void addSetReferenceTaskNameCommand(EditingDomain ed,
            CompoundCommand cmd, Activity referencingTask,
            Activity referencedTask) {

        String curName = Xpdl2ModelUtil.getDisplayName(referencingTask);
        if (isDefaultTaskName(curName)) {
            cmd.append(Xpdl2ModelUtil
                    .getSetOtherAttributeCommand(ed,
                            referencingTask,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName(),
                            getDefaultReferenceTaskName(referencedTask)));
        }

        return;
    }
}
