/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.inlineSubProcess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Loop;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ResultType;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Utility class that allows analysis of the ability to inline a sub-process
 * call (and the sub-process itself).
 * <p>
 * This can be performed from different stand-points...
 * <li>Given a package, analyse all independent sub-process task calls to
 * processes flagged as "Inline".</li>
 * <li>Given an independent sub-process task, analyse the ability to inline the
 * sub-process content into the calling process.</li>
 * <li>Given a process, analyse the ability to inline it.
 * </p>
 * <p>
 * In both cases a list of potential problems (errors or things that can be
 * resolved by user) is returned.
 * </p>
 * 
 * @author aallway
 * 
 */
public class AnalyseInlineSubProcesses {

    public static enum InlineSubProcessProblemId {
        //
        // Problems related to a Sub-process to be inlined.
        // There are problems that exist REGARDLESS of the conditions of
        // individual Sub-Process Task calls.
        //

        /** Process is not invoked by any other process. */
        SUBPROC_NOT_INVOKED,

        /** Can't inline sub-proces that implements interface */
        SUBPROC_IMPLEMENTS_INTERFACE,

        /** Cannot access referenced sub-process. */
        SUBPROC_CANT_ACCESS,

        /** Sub-Process must have only one pool. */
        SUBPROC_MULTI_POOLS,

        /**
         * Sub-Process must have only one start event <li>
         * extraInfo=List&lt;Activity&gt; startActivities
         */
        SUBPROC_MULTI_STARTS,

        /**
         * If Sub-process starts with event it must be of type None. <li>
         * extraInfo=Start Event activity.
         */
        SUBPROC_STARTEVENT_NOT_NONE,

        /**
         * Sub-Process start event must have one outgoing flow. <li>
         * extraInfo=Start Event activity.
         */
        SUBPROC_START_MULTI_FLOW,

        /** Sub-Process has NO start activities (no activities or enclosed loop) */
        SUBPROC_NO_STARTS,

        /** Sub-Process has NO end activities (no activities or enclosed loop) */
        SUBPROC_NO_ENDS,

        /** Sub-process has no activities except start / end event. */
        SUBPROC_NO_ACTIVITIES,

        /**
         * If sub-process ends with end event it must be of type Non. <li>
         * extraInfo=End Event activity.
         */
        SUBPROC_ENDEVENT_NOT_NONE,

        /**
         * Sub-Process must have a single end activity. <li>
         * extraInfo=List&lt;Activity&gt; endActivities
         */
        SUBPROC_MULTI_ENDS,

        /**
         * Sub-Process end event must have single incoming flow <li>
         * extraInfo=End Event activity.
         */
        SUBPROC_END_MULTI_FLOW,

        //
        // Problems related to independent Sub-Process Task calls to a
        // sub-process.
        //

        /**
         * Sub-Process task cannot have attached events. <li>
         * extraInfo=List&lt;Activity&gt; attachedEventActivities
         */
        TASK_ATTACHED_EVENTS,

        /**
         * Sub-process task incoming flow must be properly merged. <li>
         * extraInfo=List&lt;Transition&gt; incomingSeqFlow
         */
        TASK_SEQFLOW_IN_MULTI,

        /**
         * Sub-process task outgoing flow must be properly split. <li>
         * extraInfo=List&lt;Transition&gt; outgoingSeqFlow
         */
        TASK_SEQFLOW_OUT_MULTI,

        /** Param mapping to sub-process must not use Script params */
        TASK_SCRIPT_PARAMS,

        /**
         * Sub-process call task has mapped different fields to the same
         * sub-process formal parameter. <li>extraInfo=String formalParamName
         */
        TASK_DIFFERENT_INOUT_PARAM_MAPPING,

        /**
         * Inline Sub-proc call task cannot be flagged as multiple instance.
         */
        TASK_MULTI_INSTANCE,

        //
        // Problems related to the package as a whole.
        //

        /**
         * Cannot inline sub-process from other package if it calls other inline
         * sub-processes (during actual inlining we can't change the external
         * package so can't change the process there).
         */
        EXTPACKAGE_INLINE_NESTING,

        /**
         * Cyclic (unbroken) inline sub-process hierarchy.
         */
        PACKAGE_CYCLIC_INLINE,

        //
        // EXtra couple to cover exceptions.
        //
        EXCEPTION_DURING_INLINE;
    }

    public static class InlineSubProcessProblem {
        /**
         * The problem id.
         */
        public final InlineSubProcessProblemId problemId;

        /**
         * If <b>manual</b> in-lining into embedded sub-process in calling
         * process, is this still a problem?
         */
        public final boolean isProblemForEmbedded;

        /**
         * The sub-process to be called.
         */
        public final Process subProcess;

        /**
         * The calling task (may be null if this is a problem that is internal
         * to the sub-process).
         */
        public final Activity subProcessTask;

        /**
         * The description of the problem from the point of view of
         * automatically inlining a sub-process.
         */
        public final String autoInlineProblemDescription;

        /**
         * The description of the problem from the point of view of manually
         * inlining a sub-process.
         */
        public final String manualInlineProblemDescription;

        /**
         * The description of the resolution (and default resolution if there is
         * one). This is never assigned by the analysis. It is the task of the
         * calling code to decide whether there is a sensible resolution for the
         * problem (this is just a handy place holder for such).
         */
        public String resolutionDescription = ""; //$NON-NLS-1$

        /**
         * Extra information regarding problem, see InlineSubprocessProblemId
         * items for possible values.
         */
        public Object extraInfo = null;

        /**
         * Construct an inline sub-process problem.
         * 
         * @param problemId
         *            The problem id.
         * @param subProcess
         *            The sub-process to be called.
         * @param subProcessTask
         *            The calling task (may be null if this is a problem that is
         *            internal to the sub-process).
         * @param autoInlineProblemDescription
         *            The description of the problem.
         * @param isProblemForEmbedded
         *            If in-lining into embedded sub-process in calling process,
         *            is this still a problem?
         */
        public InlineSubProcessProblem(InlineSubProcessProblemId problemId,
                Process subProcess, Activity subProcessTask,
                String autoInlineProblemDescription,
                String manualInlineProblemDescription,
                boolean isProblemForEmbedded, Object extraInfo) {
            super();
            this.problemId = problemId;
            this.subProcess = subProcess;
            this.subProcessTask = subProcessTask;
            this.autoInlineProblemDescription = autoInlineProblemDescription;
            this.manualInlineProblemDescription =
                    manualInlineProblemDescription;
            this.isProblemForEmbedded = isProblemForEmbedded;
            this.extraInfo = extraInfo;
        }

    }

    /**
     * Small data class that contains useful information regarding the analysis
     * of a particular sub-process.
     * <p>
     * This can be accessed after an analysis is performed via the
     * getInlineSubProcessInfo() method.
     * 
     * @author aallway
     * 
     */
    public class InlineSubProcessInfo {

        public Process subProcess;

        public List<Activity> startActs = Collections.EMPTY_LIST;

        public List<Activity> endActs = Collections.EMPTY_LIST;

        public InlineSubProcessInfo(Process subProcess) {
            super();
            this.subProcess = subProcess;
        }
    }

    /**
     * Small data class to store the process call hierarchy in a package.
     * 
     * During the method analysePackage() a process-call hierarchy is built.
     * <p>
     * This is effectively a list of all processes each with their call
     * hierarchy.
     * <p>
     * <b>Note: No attempt is made to rationalise this.</b> Therefore if we have
     * a situation where there are 3 processes in package (ProcessA calls
     * ProcessB which calls ProcessC) then the process hierarchy looks like (in
     * no particular order)...
     * 
     * <pre>
     * 
     * ProcessC ProcessA ProcessB ProcessC ProcessB ProcessC
     * 
     * </pre>
     * 
     * @author aallway
     * 
     */
    public class ProcessCallHierarchy {
        public Process process;

        public boolean isInline = false;

        public List<ProcessCallHierarchy> calledProcesses =
                Collections.EMPTY_LIST;

        public ProcessCallHierarchy parent = null;
    }

    private List<ProcessCallHierarchy> lastAnalysedProcessHierarchy =
            Collections.EMPTY_LIST;

    private Map<Process, InlineSubProcessInfo> inlineSubProcInfoMap =
            new HashMap<Process, InlineSubProcessInfo>();;

    private Xpdl2WorkingCopyImpl workingCopy = null;

    private boolean analyseAllProcesses = false;

    private boolean wantProcessNotInvokedProblems = false;

    private Package mainPackage;

    private long callsToRecursiveGetProcessHierarchy = 0;

    private long callsToGetSubProcessFromTask = 0;

    /**
     * This class uses certain features of working copy that will NOT work if
     * you're working on a copy of a package. If you are working on a copy of a
     * package then pass in the working copy of the original.
     * 
     * @param workingCopy
     */
    public AnalyseInlineSubProcesses(Xpdl2WorkingCopyImpl workingCopy,
            Package mainPackage) {
        super();
        this.workingCopy = workingCopy;
        this.mainPackage = mainPackage;
    }

    /**
     * Analyse an entire package (takes into account sub-processes flagged as
     * inline and calls to them only OR all processes if
     * setAnalyseAllProcesses(true) has been done).
     * <p>
     * Note that after calling this method you can use getLastprocessHierarchy()
     * to retrieve the process call hierarchy.
     * 
     * @param pkg
     * 
     * @return
     */
    public List<InlineSubProcessProblem> analysePackage(Package pkg) {
        List<InlineSubProcessProblem> problems =
                new ArrayList<InlineSubProcessProblem>();

        Set<Process> alreadyAnalysedProcesses = new HashSet<Process>();

        // Create and Check the process call hierarchy
        checkCallHierarchy(problems, pkg);

        // Analyse all processes in the hierarchy.
        for (ProcessCallHierarchy pchEntry : lastAnalysedProcessHierarchy) {
            recursiveAnalyseProcessAndTasks(pchEntry,
                    problems,
                    alreadyAnalysedProcesses);

            if (wantProcessNotInvokedProblems) {
                // Check whether this process is call by anything else in this
                // package and if so raise an error (becuase after inlining,
                // inline
                // sub-processes are removed).
                if (!processIsInvokedLocally(pchEntry.process,
                        lastAnalysedProcessHierarchy)) {
                    problems.add(new InlineSubProcessProblem(
                            InlineSubProcessProblemId.SUBPROC_NOT_INVOKED,
                            pchEntry.process,
                            null,
                            Messages.AnalyseInlineSubProcesses_ProcessIsNotInvokedProblem_longdesc,
                            Messages.AnalyseInlineSubProcesses_ProcessIsNotInvokedProblem_longdesc,
                            true, null));

                }
            }
        }

        // Analyse the sub-processes themselves.
        return problems;
    }

    /**
     * Check if the given process is invoked from a process in the current
     * package.
     * 
     * @param process
     * @param processCallHierarchy
     * @return
     */
    private boolean processIsInvokedLocally(Process process,
            List<ProcessCallHierarchy> processCallHierarchy) {
        for (ProcessCallHierarchy ph : processCallHierarchy) {
            if (ph.calledProcesses != null) {
                if (recursiveFindProcess(process, ph.calledProcesses)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean recursiveFindProcess(Process process,
            List<ProcessCallHierarchy> calledProcesses) {

        for (ProcessCallHierarchy ph : calledProcesses) {
            if (ph.process == process) {
                // This process is called from another process.
                return true;
            } else {
                if (recursiveFindProcess(process, ph.calledProcesses)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void recursiveAnalyseProcessAndTasks(ProcessCallHierarchy pchEntry,
            List<InlineSubProcessProblem> problems,
            Set<Process> alreadyAnalysedProcesses) {

        // If this process is inline then check it.
        if (!alreadyAnalysedProcesses.contains(pchEntry.process)) {
            alreadyAnalysedProcesses.add(pchEntry.process);

            if (internalIsInlineSubProcess(pchEntry.process)) {
                List<InlineSubProcessProblem> prbs =
                        analyseSubProcess(pchEntry.process);

                if (prbs != null && prbs.size() > 0) {
                    problems.addAll(prbs);
                }
            }

            // Analyse the conditions of all tasks that call sub-processes.
            Collection<Activity> activities =
                    Xpdl2ModelUtil.getAllActivitiesInProc(pchEntry.process);

            for (Iterator iterAct = activities.iterator(); iterAct.hasNext();) {
                Activity act = (Activity) iterAct.next();

                EObject procOrIfc = getSubProcessFromTask(act);
                if (procOrIfc instanceof Process) {
                    Process subProcess = (Process) procOrIfc;

                    if (internalIsInlineSubProcess(subProcess)) {
                        checkSubProcessTaskProblems(problems, act, subProcess);
                    }
                }
            }

            // Now recurs and analyse the conditions for all sub-processes we
            // call.
            for (ProcessCallHierarchy subPch : pchEntry.calledProcesses) {
                recursiveAnalyseProcessAndTasks(subPch,
                        problems,
                        alreadyAnalysedProcesses);
            }
        }

        return;
    }

    /**
     * @param act
     * @return
     */
    private EObject getSubProcessFromTask(Activity act) {
        callsToGetSubProcessFromTask++;
        if (TaskType.SUBPROCESS_LITERAL.equals(TaskObjectUtil
                .getTaskTypeStrict(act))) {
            // If the activity is in the original package we are analysing then
            // use
            // the working copy we were passed.
            Package pkg = Xpdl2ModelUtil.getPackage(act);
            if (pkg == mainPackage) {
                // If the object is in the main package we're dealing with then
                // use
                // the working copy we were passed (as the mainbPackage may be a
                // copy of original and hence won't have a working copy).
                return TaskObjectUtil
                        .getSubProcessOrInterface(act, workingCopy);
            }

            // Otherwise if its an ext package ref then it will have a working
            // copy.
            // So can use default method.
            return TaskObjectUtil.getSubProcessOrInterface(act);
        }
        return null;
    }

    /**
     * Create a process call hierarchy and check for cyclic inline processes
     * (the cycle can be broken by non-inline and thats ok).
     * 
     * @param problems
     * @param pkg
     */
    private void checkCallHierarchy(List<InlineSubProcessProblem> problems,
            Package pkg) {
        lastAnalysedProcessHierarchy = new ArrayList<ProcessCallHierarchy>();
        HashMap<Process, Set<Process>> processSubProcessCallMap =
                new HashMap<Process, Set<Process>>();

        callsToRecursiveGetProcessHierarchy = 0;
        callsToGetSubProcessFromTask = 0;

        for (Iterator iter = pkg.getProcesses().iterator(); iter.hasNext();) {
            Process process = (Process) iter.next();

            ProcessCallHierarchy ph =
                    recursiveGetProcessHierarchy(null,
                            process,
                            problems,
                            pkg,
                            processSubProcessCallMap);
            if (ph != null) {
                lastAnalysedProcessHierarchy.add(ph);
            }
        }

        //        System.out.println("PROCESS HIEARARCHY..."); //$NON-NLS-1$
        // // for (ProcessCallHierarchy ph : lastAnalysedProcessHierarchy) {
        // // listHierarchy(ph, 0);
        // // }
        // System.out
        //                .println("   callsToRecursiveGetProcessHierarchy: " + callsToRecursiveGetProcessHierarchy); //$NON-NLS-1$
        // System.out
        //                .println("   callsToRecursiveGetSubProcessFromTask: " + callsToGetSubProcessFromTask); //$NON-NLS-1$
        //
        //        System.out.println("=============================\n\n"); //$NON-NLS-1$
    }

    private void listHierarchy(ProcessCallHierarchy ph, int level) {
        String indent = ""; //$NON-NLS-1$

        for (int i = 0; i < level; i++) {
            indent += " "; //$NON-NLS-1$
        }

        System.out.println(indent + ph.process.getName());

        for (ProcessCallHierarchy subph : ph.calledProcesses) {
            listHierarchy(subph, level + 1);
        }
    }

    /**
     * @param process
     * @param processesInStrand
     * @param problems
     * @param originalPkg
     * @param processSubProcessCallMap
     */
    private ProcessCallHierarchy recursiveGetProcessHierarchy(
            ProcessCallHierarchy parent, Process process,
            List<InlineSubProcessProblem> problems, Package originalPkg,
            HashMap<Process, Set<Process>> processSubProcessCallMap) {

        callsToRecursiveGetProcessHierarchy++;

        ProcessCallHierarchy ph = new ProcessCallHierarchy();
        ph.process = process;
        ph.isInline = internalIsInlineSubProcess(process);
        ph.parent = parent;

        // First make sure that this process does not appear in the hierarchy
        // already.
        ProcessCallHierarchy caller = parent;
        boolean allInline = ph.isInline;

        while (caller != null) {
            if (!caller.isInline) {
                allInline = false;
            }

            if (caller.process == process) {

                // Secondly, if this is an inline sub-process, we must complain
                // if there are not any non-inline processes in between.
                if (allInline) {
                    problems.add(new InlineSubProcessProblem(
                            InlineSubProcessProblemId.PACKAGE_CYCLIC_INLINE,
                            process,
                            null,
                            Messages.AnalyseInlineSubProcesses_CyclicInlineSubProcs_longdesc,
                            Messages.AnalyseInlineSubProcesses_CyclicInlineSubProcs_longdesc,
                            true, null));
                }

                // Found cycle - get out.
                return null;
            }

            caller = caller.parent;
        }

        //
        // Find all the processes called from this process.
        // In large packages with 10's or m nore of processes things can get
        // very complex with lots of repeated hierarchy's so cache last results.
        Set<Process> calledProcesses = processSubProcessCallMap.get(process);
        if (calledProcesses == null) {
            calledProcesses = new HashSet<Process>();

            Collection<Activity> activities =
                    Xpdl2ModelUtil.getAllActivitiesInProc(process);

            for (Iterator iterAct = activities.iterator(); iterAct.hasNext();) {
                Activity act = (Activity) iterAct.next();

                EObject procOrIfc = getSubProcessFromTask(act);
                if (procOrIfc instanceof Process) {
                    Process subProcess = (Process) procOrIfc;
                    calledProcesses.add(subProcess);
                }
            }

            processSubProcessCallMap.put(process, calledProcesses);
        }

        if (calledProcesses.size() > 0) {
            ph.calledProcesses = new ArrayList<ProcessCallHierarchy>();

            for (Process subProcess : calledProcesses) {
                // Ensure that inline processes in other packages are not nested
                // beneath others
                // (when time comes to auto-inline we can't change a process in
                // a different package)
                if (ph.process.getPackage() != originalPkg) {
                    // We're processing an process that's already in
                    // an external package to the original package.
                    // WE DON'T USE internalIsInlineSubProcess() HERE
                    // because that will return true if caller has set
                    // analyseAllProcesses to true. BUT for external pkg
                    // sub-processes there is no reason to do so for this
                    // particular rule (as user won't be able to change flag
                    // in external pkg proc during proc optimisation of this
                    // original pkg).
                    if (isInlineSubProcess(subProcess)) {
                        problems.add(new InlineSubProcessProblem(
                                InlineSubProcessProblemId.EXTPACKAGE_INLINE_NESTING,
                                process,
                                null,
                                Messages.AnalyseInlineSubProcesses_ExtPkgInlineSubProcNesting_longdesc,
                                Messages.AnalyseInlineSubProcesses_ExtPkgInlineSubProcNesting_longdesc,
                                true, null));
                    }
                }

                // Only recurs into called process when called process is in
                // original package OR its in external package and its flagged
                // as inlined (so we can check for nesting of ext pkg inlines.
                // process is not in original package.
                if (ph.process.getPackage() == originalPkg
                        || isInlineSubProcess(subProcess)) {
                    ProcessCallHierarchy subPh =
                            recursiveGetProcessHierarchy(ph,
                                    subProcess,
                                    problems,
                                    originalPkg,
                                    processSubProcessCallMap);

                    if (subPh != null) {
                        ph.calledProcesses.add(subPh);
                    }
                }
            }
        }

        return ph;
    }

    /**
     * Analyse an individual Independent Sub-Process Task to check for potential
     * problems with in-lining the sub-process it calls.
     * 
     * @param subProcessTask
     * @return A list of potential problems (empty if no problems OR no
     *         sub-process called).
     */
    public List<InlineSubProcessProblem> analyseSubProcessTask(
            Activity subProcessTask) {
        List<InlineSubProcessProblem> problems =
                new ArrayList<InlineSubProcessProblem>();

        EObject eObj = getSubProcessFromTask(subProcessTask);
        if (!(eObj instanceof Process)) {
            problems.add(new InlineSubProcessProblem(
                    InlineSubProcessProblemId.SUBPROC_CANT_ACCESS, null,
                    subProcessTask,
                    Messages.AnalyseInlineSubProcesses_CantRefSubproc_longdesc,
                    Messages.AnalyseInlineSubProcesses_CantRefSubproc_longdesc,
                    true, null));

        } else {
            Process subProcess = (Process) eObj;

            //
            // Check for problems related to the call task.
            checkSubProcessTaskProblems(problems, subProcessTask, subProcess);
        }

        return problems;
    }

    /**
     * Analyse the ability to inline the given sub-process.
     * <p>
     * The problems returned are those that will be problems regardless of trhe
     * setup of individual independent sub-process tasks that reference the
     * sub-process.
     * 
     * @param subProcess
     * @return A list of potential problems - empty if no problems
     */
    public List<InlineSubProcessProblem> analyseSubProcess(Process subProcess) {
        List<InlineSubProcessProblem> problems =
                new ArrayList<InlineSubProcessProblem>();

        //
        // Check for problems that are internal to the sub-process (i.e.
        // things that will be problems regardless of the calling task).
        //
        checkSubProcessProblems(problems, subProcess);

        return problems;
    }

    /**
     * Check the given sub-process call task for problems.
     * <p>
     * i.e. just things related to the particular invocation of sub-process in
     * the calling process.
     * </p>
     * 
     * @param problems
     *            list of problems to add to.
     * @param subProcessTask
     * @param subProcess
     */
    private void checkSubProcessTaskProblems(
            List<InlineSubProcessProblem> problems, Activity subProcessTask,
            Process subProcess) {

        if (ProcessInterfaceUtil.getImplementedProcessInterfaceId(subProcess) != null) {
            problems.add(new InlineSubProcessProblem(
                    InlineSubProcessProblemId.SUBPROC_IMPLEMENTS_INTERFACE,
                    subProcess,
                    subProcessTask,
                    Messages.AnalyseInlineSubProcesses_CantInlineImplementingProcesses_longdesc,
                    Messages.AnalyseInlineSubProcesses_CantInlineImplementingProcesses_longdesc,
                    true, null));

        }

        // Call Task cannot be multiple instance
        Loop loop = subProcessTask.getLoop();
        if (loop != null) {
            if (loop.getLoopMultiInstance() != null) {
                problems.add(new InlineSubProcessProblem(
                        InlineSubProcessProblemId.TASK_MULTI_INSTANCE,
                        subProcess,
                        subProcessTask,
                        Messages.AnalyseInlineSubProcesses_MultiInstanceSubProcTask_longdesc2,
                        Messages.AnalyseInlineSubProcesses_Refactor_MultiInst_longdesc2,
                        false, null));
            }
        }

        // Task should have no attached events
        Collection<Activity> attachedEvents =
                TaskObjectUtil.getAttachedEvents(subProcessTask);
        if (attachedEvents != null && attachedEvents.size() > 0) {
            problems.add(new InlineSubProcessProblem(
                    InlineSubProcessProblemId.TASK_ATTACHED_EVENTS,
                    subProcess,
                    subProcessTask,
                    Messages.AnalyseInlineSubProcesses_AttachedEvents_longdesc2,
                    Messages.AnalyseInlineSubProcesses_Refactor_AttachedEvents_longdesc2,
                    false, attachedEvents));
        }

        // Task should have single incoming flow.
        List<Transition> inTrns = getIncomingFlow(subProcessTask);
        if (inTrns.size() > 1) {
            problems.add(new InlineSubProcessProblem(
                    InlineSubProcessProblemId.TASK_SEQFLOW_IN_MULTI,
                    subProcess,
                    subProcessTask,
                    Messages.AnalyseInlineSubProcesses_TaskMultiIncomingFlow_longdesc2,
                    Messages.AnalyseInlineSubProcesses_Refactor_MultiInFlow_longdesc2,
                    false, inTrns));

        }

        // Task should have single outgoing flow.
        List<Transition> outTrns = getOutgoingFlow(subProcessTask);
        if (outTrns.size() > 1) {
            problems.add(new InlineSubProcessProblem(
                    InlineSubProcessProblemId.TASK_SEQFLOW_OUT_MULTI,
                    subProcess,
                    subProcessTask,
                    Messages.AnalyseInlineSubProcesses_TaskMultiOutgoingFlow_longdesc2,
                    Messages.AnalyseInlineSubProcesses_Refactor_MultiOutFlow_longdesc2,
                    false, outTrns));

        }

        // Check for Data Field names are unique.
        checkParameterMappings(problems, subProcess, subProcessTask);
    }

    /**
     * Check subprocess task mappings into an inline sub-process
     * 
     * @param problems
     * @param subProcess
     * @param subProcessTask
     */
    private void checkParameterMappings(List<InlineSubProcessProblem> problems,
            Process subProcess, Activity subProcessTask) {

        SubFlow subFlow = (SubFlow) subProcessTask.getImplementation();
        EList dataMappings = subFlow.getDataMappings();

        // Create mappings of input and output mappings param to calling field
        Map<String, String> paramToFieldMappings =
                new HashMap<String, String>();

        for (Iterator iter = dataMappings.iterator(); iter.hasNext();) {
            DataMapping dataMap = (DataMapping) iter.next();

            //
            // Script parameters are not allowed.
            //
            ScriptInformation scriptParam =
                    (ScriptInformation) Xpdl2ModelUtil.getOtherElement(dataMap,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_Script());
            if (scriptParam != null) {
                problems.add(new InlineSubProcessProblem(
                        InlineSubProcessProblemId.TASK_SCRIPT_PARAMS,
                        subProcess,
                        subProcessTask,
                        Messages.AnalyseInlineSubProcesses_TaskWithScriptMappings_longdesc,
                        Messages.AnalyseInlineSubProcesses_Refactor_ScriptParams_longdesc2,
                        true, null));
            } else {
                // If a formal parameter is mapped IN and mapped OUT then it
                // MUST be mapped to same calling field.
                String callingField = dataMap.getActual().getText();
                String subProcParam = dataMap.getFormal();

                // If there is already a mapping for same sub-proc formal
                // parameter to a different calling proc field then complain.
                // (Otherwise, we can swap all occurrences of formal param with
                // same data field when inlining).
                if (paramToFieldMappings.containsKey(subProcParam)) {
                    if (!paramToFieldMappings.get(subProcParam)
                            .equals(callingField)) {
                        problems.add(new InlineSubProcessProblem(
                                InlineSubProcessProblemId.TASK_DIFFERENT_INOUT_PARAM_MAPPING,
                                subProcess,
                                subProcessTask,
                                String.format(Messages.AnalyseInlineSubProcesses_DifferentMappingsForSameParam_longdesc,
                                        subProcParam),
                                String.format(Messages.AnalyseInlineSubProcesses_Refactor_DiffInOutMapping_longdesc2,
                                        subProcParam), true,

                                null));
                        continue;
                    }
                }

                paramToFieldMappings.put(subProcParam, callingField);
            }
        }

        return;
    }

    /**
     * Check the given sub-process for problems.
     * 
     * <p>
     * i.e. things that will be problems regardless of the calling task.
     * </p>
     * 
     * @param problems
     * @param subProcess
     */
    private void checkSubProcessProblems(
            List<InlineSubProcessProblem> problems, Process subProcess) {

        // Create and Add the inline sub-process info for this process...
        InlineSubProcessInfo inlineSubProcInfo =
                new InlineSubProcessInfo(subProcess);
        inlineSubProcInfoMap.put(subProcess, inlineSubProcInfo);

        //
        // Check it has single pool.
        //
        Collection<Pool> pools = Xpdl2ModelUtil.getProcessPools(subProcess);
        if (pools == null || pools.size() != 1) {
            problems.add(new InlineSubProcessProblem(
                    InlineSubProcessProblemId.SUBPROC_MULTI_POOLS,
                    subProcess,
                    null,
                    Messages.AnalyseInlineSubProcesses_MultiPools_longdesc,
                    Messages.AnalyseInlineSubProcesses_Refactor_MultiPools_longdesc,
                    true, null));

            // this problem is quite fatal so no point checking the rest.
            return;
        }

        // Check the start activity is valid.
        Activity startAct = checkSubProcessStartActivity(problems, subProcess);

        // Check the end activitiy is valid.
        Activity endAct = checkSubProcessEndActivity(problems, subProcess);

        // Check that there are some other activities besides start and end.
        Collection<Activity> activities =
                Xpdl2ModelUtil.getAllActivitiesInProc(subProcess);

        if (startAct != null && startAct.getEvent() instanceof StartEvent) {
            activities.remove(startAct);
        }

        if (endAct != null && endAct.getEvent() instanceof EndEvent) {
            activities.remove(endAct);
        }

        if (activities.size() < 1) {
            problems.add(new InlineSubProcessProblem(
                    InlineSubProcessProblemId.SUBPROC_NO_ACTIVITIES,
                    subProcess,
                    null,
                    Messages.AnalyseInlineSubProcesses_NoActivitiesInInlineSubProc_Longdesc,
                    Messages.AnalyseInlineSubProcesses_Refactor_NoActivities_longdesc,
                    false, null));

        } else {
            if (startAct == null) {
                problems.add(new InlineSubProcessProblem(
                        InlineSubProcessProblemId.SUBPROC_NO_STARTS,
                        subProcess,
                        null,
                        Messages.AnalyseInlineSubProcesses_NoStartActivity_longdesc,
                        Messages.AnalyseInlineSubProcesses_Refactor_NoStartActivity_longdesc,
                        false, null));
            }

            if (endAct == null) {
                problems.add(new InlineSubProcessProblem(
                        InlineSubProcessProblemId.SUBPROC_NO_ENDS,
                        subProcess,
                        null,
                        Messages.AnalyseInlineSubProcesses_NoEndActivity_longdesc,
                        Messages.AnalyseInlineSubProcesses_Refactor_NoEndActivity_longdesc,
                        false, null));
            }
        }

    }

    /**
     * Add problems related to sub-process end activities.
     * 
     * @param problems
     * @param subProcess
     * @return end activity if found.
     */
    private Activity checkSubProcessEndActivity(
            List<InlineSubProcessProblem> problems, Process subProcess) {
        Activity endAct = null;

        EList activities = subProcess.getActivities();

        InlineSubProcessInfo inlineSubProcInfo =
                inlineSubProcInfoMap.get(subProcess);

        inlineSubProcInfo.endActs = new ArrayList<Activity>();

        // Check for activities with no outgoing transitions.
        for (Iterator iter = activities.iterator(); iter.hasNext();) {
            Activity act = (Activity) iter.next();
            String id = act.getId();

            // Note that we ALWAYS consider attached events without outgoing
            // flow as possible end events because an task-attached event
            // cancels the task (and its flow) and if in-lined directly it would
            // cancel the calling process's flow also - which would not happen
            // if the sub-process was called as a sub-process rather than
            // in-lined.

            boolean hasOutgoing = false;
            for (Iterator trnIter = subProcess.getTransitions().iterator(); trnIter
                    .hasNext();) {
                Transition trn = (Transition) trnIter.next();
                if (id.equals(trn.getFrom())) {
                    hasOutgoing = true;
                    break;
                }
            }

            if (!hasOutgoing) {
                inlineSubProcInfo.endActs.add(act);
            }
        }

        if (inlineSubProcInfo.endActs.size() == 0) {
            // Leave complaint about this uup to caller.

        } else if (inlineSubProcInfo.endActs.size() > 1) {
            problems.add(new InlineSubProcessProblem(
                    InlineSubProcessProblemId.SUBPROC_MULTI_ENDS,
                    subProcess,
                    null,
                    Messages.AnalyseInlineSubProcesses_MultiEndPoints_longdesc,
                    Messages.AnalyseInlineSubProcesses_Refactor_MultiEnds_longdesc,
                    false, inlineSubProcInfo.endActs));

        } else {
            endAct = inlineSubProcInfo.endActs.get(0);

            // If end activity is end event, must be of type none
            if (endAct.getEvent() instanceof EndEvent) {

                if (!ResultType.NONE_LITERAL.equals(((EndEvent) endAct
                        .getEvent()).getResult())) {
                    problems.add(new InlineSubProcessProblem(
                            InlineSubProcessProblemId.SUBPROC_ENDEVENT_NOT_NONE,
                            subProcess,
                            null,
                            Messages.AnalyseInlineSubProcesses_EndeEventNotNone_longdesc,
                            Messages.AnalyseInlineSubProcesses_EndeEventNotNone_longdesc,
                            false, endAct));

                }

                // And have one incoming flow.
                String id = endAct.getId();
                int inCount = 0;
                for (Iterator trnIter = subProcess.getTransitions().iterator(); trnIter
                        .hasNext();) {
                    Transition trn = (Transition) trnIter.next();
                    if (id.equals(trn.getTo())) {
                        inCount++;
                        if (inCount > 1) {
                            break;
                        }
                    }
                }

                if (inCount > 1) {
                    problems.add(new InlineSubProcessProblem(
                            InlineSubProcessProblemId.SUBPROC_END_MULTI_FLOW,
                            subProcess,
                            null,
                            Messages.AnalyseInlineSubProcesses_EndEventMultiIncomingFlow_longdesc,
                            Messages.AnalyseInlineSubProcesses_Refactor_EndActMultiInFlow_longdesc,
                            false, endAct));
                }

            }
        }
        return endAct;
    }

    /**
     * Add problems related to sub-process start activities.
     * 
     * @param problems
     * @param subProcess
     * @return startActivity if found.
     */
    private Activity checkSubProcessStartActivity(
            List<InlineSubProcessProblem> problems, Process subProcess) {
        Activity startAct = null;

        EList activities = subProcess.getActivities();

        InlineSubProcessInfo inlineSubProcInfo =
                inlineSubProcInfoMap.get(subProcess);

        inlineSubProcInfo.startActs = new ArrayList<Activity>();

        // Check for activities with no incoming transitions.
        for (Iterator iter = activities.iterator(); iter.hasNext();) {
            Activity act = (Activity) iter.next();
            String id = act.getId();

            // Don't count events attached to task boundary as start activities.
            boolean boundaryEvent = false;
            if (act.getEvent() instanceof IntermediateEvent) {
                String taskId =
                        ((IntermediateEvent) act.getEvent()).getTarget();

                if (taskId != null && taskId.length() > 0) {
                    boundaryEvent = true;
                }
            }

            if (!boundaryEvent) {
                boolean hasIncoming = false;
                for (Iterator trnIter = subProcess.getTransitions().iterator(); trnIter
                        .hasNext();) {
                    Transition trn = (Transition) trnIter.next();
                    if (id.equals(trn.getTo())) {
                        hasIncoming = true;
                        break;
                    }
                }

                if (!hasIncoming) {
                    inlineSubProcInfo.startActs.add(act);
                }
            }
        }

        if (inlineSubProcInfo.startActs.size() == 0) {
            // Leave complaint up to caller.

        } else if (inlineSubProcInfo.startActs.size() > 1) {
            problems.add(new InlineSubProcessProblem(
                    InlineSubProcessProblemId.SUBPROC_MULTI_STARTS,
                    subProcess,
                    null,
                    Messages.AnalyseInlineSubProcesses_MultiStartActivity_longdesc,
                    Messages.AnalyseInlineSubProcesses_Refactor_MultiStarts_longdesc,
                    false, inlineSubProcInfo.startActs));
        } else {
            startAct = inlineSubProcInfo.startActs.get(0);

            // if start activity is a start event then it should be of type
            // none.
            if (startAct.getEvent() instanceof StartEvent) {
                TriggerType trigger =
                        ((StartEvent) startAct.getEvent()).getTrigger();
                if (!TriggerType.NONE_LITERAL.equals(trigger)) {
                    problems.add(new InlineSubProcessProblem(
                            InlineSubProcessProblemId.SUBPROC_STARTEVENT_NOT_NONE,
                            subProcess,
                            null,
                            Messages.AnalyseInlineSubProcesses_StartNotNone_longdesc,
                            Messages.AnalyseInlineSubProcesses_StartNotNone_longdesc,
                            false, startAct));
                }

                // And should only have one outgoing flow.
                String id = startAct.getId();
                int outCount = 0;
                for (Iterator trnIter = subProcess.getTransitions().iterator(); trnIter
                        .hasNext();) {
                    Transition trn = (Transition) trnIter.next();
                    if (id.equals(trn.getFrom())) {
                        outCount++;
                        if (outCount > 1) {
                            break;
                        }
                    }
                }

                if (outCount > 1) {
                    problems.add(new InlineSubProcessProblem(
                            InlineSubProcessProblemId.SUBPROC_START_MULTI_FLOW,
                            subProcess,
                            null,
                            Messages.AnalyseInlineSubProcesses_StartEventMultiOutgoingFlow_longdesc,
                            Messages.AnalyseInlineSubProcesses_Refactor_StartActMultiOutFlow_longdesc,
                            false, startAct));
                }

            }

        }

        return startAct;
    }

    private List<Transition> getIncomingFlow(Activity act) {
        List<Transition> trans = new ArrayList<Transition>();

        FlowContainer container = act.getFlowContainer();
        String id = act.getId();

        for (Iterator trnIter = container.getTransitions().iterator(); trnIter
                .hasNext();) {
            Transition trn = (Transition) trnIter.next();
            if (id.equals(trn.getTo())) {
                trans.add(trn);
            }
        }
        return trans;
    }

    private List<Transition> getOutgoingFlow(Activity act) {
        List<Transition> trans = new ArrayList<Transition>();

        FlowContainer container = act.getFlowContainer();
        String id = act.getId();

        for (Iterator trnIter = container.getTransitions().iterator(); trnIter
                .hasNext();) {
            Transition trn = (Transition) trnIter.next();
            if (id.equals(trn.getFrom())) {
                trans.add(trn);
            }
        }
        return trans;
    }

    /**
     * Check if given process is flagged to be inlined.
     * <p>
     * This method takes the setting of analyseAllProcesses into account. i.e.
     * if that's true then it always returns true, else it works on the finline
     * flag in actual process model.
     * 
     * @param process
     *            process to check
     * @return true if is inlined.
     */
    private boolean internalIsInlineSubProcess(Process process) {
        if (analyseAllProcesses) {
            return true;
        }

        return isInlineSubProcess(process);
    }

    /**
     * Check if given process is flagged to be inlined.
     * 
     * @param process
     *            process to check
     * @return true if is inlined.
     */
    public static boolean isInlineSubProcess(Process process) {
        Object value =
                Xpdl2ModelUtil.getOtherAttribute(process,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_InlineSubProcess());
        if (value instanceof Boolean && ((Boolean) value).booleanValue()) {
            return true;
        }

        return false;
    }

    /**
     * During the method analysePackage() a process-call hierarchy is built.
     * <p>
     * This is effectively a list of all processes each with their call
     * hierarchy.
     * <p>
     * <b>Note: No attempt is made to rationalise this.</b> Therefore if we have
     * a situation where there are 3 processes in package (ProcessA calls
     * ProcessB which calls ProcessC) then the process hierarchy looks like (in
     * no particular order)...
     * 
     * <pre>
     * 
     * ProcessC ProcessA ProcessB ProcessC ProcessB ProcessC
     * 
     * </pre>
     * 
     * @return The process hierarchy.
     */
    public List<ProcessCallHierarchy> getLastAnalysedProcessHierarchy() {
        return lastAnalysedProcessHierarchy;
    }

    /**
     * @return the analyseAllProcesses
     */
    public boolean isAnalyseAllProcesses() {
        return analyseAllProcesses;
    }

    /**
     * @param analyseAllProcesses
     *            the analyseAllProcesses to set
     */
    public void setAnalyseAllProcesses(boolean analyseAllProcesses) {
        this.analyseAllProcesses = analyseAllProcesses;
    }

    /**
     * Access various information stored about sub-process during analysis.
     * 
     * @param subProcess
     * @return
     */
    public InlineSubProcessInfo getInlineSubProcessInfo(Process subProcess) {
        return inlineSubProcInfoMap.get(subProcess);
    }

    /**
     * @return the wantProcessNotInvokedProblems
     */
    public boolean isWantProcessNotInvokedProblems() {
        return wantProcessNotInvokedProblems;
    }

    /**
     * @param wantProcessNotInvokedProblems
     *            the wantProcessNotInvokedProblems to set
     */
    public void setWantProcessNotInvokedProblems(
            boolean wantProcessNotInvokedProblems) {
        this.wantProcessNotInvokedProblems = wantProcessNotInvokedProblems;
    }
}
