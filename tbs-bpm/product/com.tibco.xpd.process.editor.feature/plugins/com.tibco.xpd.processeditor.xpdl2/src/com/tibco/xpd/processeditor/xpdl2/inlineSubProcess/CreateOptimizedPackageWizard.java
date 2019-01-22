/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.inlineSubProcess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.inlineSubProcess.AnalyseInlineSubProcesses.InlineSubProcessProblem;
import com.tibco.xpd.processeditor.xpdl2.inlineSubProcess.AnalyseInlineSubProcesses.ProcessCallHierarchy;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.ProcessRefactorConfigurationItem;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.ProcessRefactorConfigurationPage;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.ProcessRefactorWizard;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Create optimised package wizard.
 * <p>
 * Perform validation and user configuration parts of a 'Create Optimised
 * Package'.
 * <p>
 * The creator can use getSubProcConfigItems() to check user selection and
 * perform necessary processing.
 * 
 * @author aallway
 * 
 */
public class CreateOptimizedPackageWizard extends ProcessRefactorWizard {

    /** The source process package that we are optimising. */
    private Package srcPackage;

    private Xpdl2WorkingCopyImpl srcWorkingCopy;

    private List<InlineSubProcConfigItem> subProcConfigItems;

    /**
     * Configuration list item that represents a folder of an individual
     * category of processes to inline (strong, potential, cant, external)
     * 
     * @author aallway
     */
    private class ConfigItemFolderWithProblemMarker extends
            ProcessRefactorConfigurationItem {

        private Image problemImage = null;

        public ConfigItemFolderWithProblemMarker(Object inputObject,
                String itemText, String itemDesc, boolean isChecked,
                boolean isProblemIfUnchecked, Image decorator,
                Image problemImage) {
            super(inputObject, itemText, itemDesc, isChecked,
                    isProblemIfUnchecked, decorator);
            this.problemImage = problemImage;
        }

        @Override
        public Image getDecorator() {
            List<ProcessRefactorConfigurationItem> children = getChildItems();
            if (children != null) {
                for (ProcessRefactorConfigurationItem configItem : children) {
                    if (configItem instanceof InlineSubProcConfigItem) {
                        InlineSubProcConfigItem item =
                                (InlineSubProcConfigItem) configItem;
                        if (item.isChecked() && item.problems.size() > 0) {
                            return problemImage;
                        }
                    }
                }
            }
            return super.getDecorator();
        }

    }

    /**
     * Configuration list item data for a process.
     * 
     * @author aallway
     * 
     */
    public class InlineSubProcConfigItem extends
            ProcessRefactorConfigurationItem {

        private List<InlineSubProcessProblem> problems = Collections.EMPTY_LIST;

        private String extraDetailText = ""; //$NON-NLS-1$

        private boolean processIsInvokedLocally = false;

        private Process process;

        public InlineSubProcConfigItem(Process process, boolean isChecked) {
            super(process, WorkingCopyUtil.getText(process),
                    "", isChecked, false, Xpdl2ProcessEditorPlugin //$NON-NLS-1$
                            .getDefault().getImageRegistry()
                            .get(ProcessEditorConstants.IMG_BLUE_PROCESS));
            this.process = process;
        }

        /**
         * @return the problems
         */
        public List<InlineSubProcessProblem> getProblems() {
            return problems;
        }

        /**
         * @param problems
         *            the problems to set
         */
        public void setProblems(List<InlineSubProcessProblem> problems) {
            this.problems = problems;
        }

        /**
         * @param extraDetailText
         *            the extraDetailText to set
         */
        public void setExtraDetailText(String extraDetailText) {
            this.extraDetailText = extraDetailText;
        }

        @Override
        public Image getDecorator() {
            if (isChecked() && problems.size() > 0) {
                return Xpdl2ProcessEditorPlugin.getDefault().getImageRegistry()
                        .get(ProcessEditorConstants.IMG_PROCESSPROBLEM);
            } else if (isChecked() && !processIsInvokedLocally) {
                return Xpdl2ProcessEditorPlugin.getDefault().getImageRegistry()
                        .get(ProcessEditorConstants.IMG_PROCESSWARNING);
            }
            return Xpdl2ProcessEditorPlugin.getDefault().getImageRegistry()
                    .get(ProcessEditorConstants.IMG_BLUE_PROCESS);
        }

        /**
         * @param processIsInvokedLocally
         *            the processIsInvokedLocally to set
         */
        public void setProcessIsInvokedLocally(boolean processIsInvokedLocally) {
            this.processIsInvokedLocally = processIsInvokedLocally;
        }

        /**
         * @return the processIsInvokedLocally
         */
        public boolean isProcessIsInvokedLocally() {
            return processIsInvokedLocally;
        }

        @Override
        public String getConfigItemTextDetails() {
            String detailsString = ""; //$NON-NLS-1$

            if (extraDetailText != null && extraDetailText.length() > 0) {
                detailsString += extraDetailText;
                detailsString += "<p></p>"; //$NON-NLS-1$

            }

            if (problems.size() > 0) {
                detailsString += "<p><b>"; //$NON-NLS-1$
                detailsString +=
                        Messages.CreateOptimizedPackageWizard_CantInlineProcBecause_longdesc;
                detailsString += "</b></p>"; //$NON-NLS-1$

                for (InlineSubProcessProblem problem : problems) {
                    detailsString += "<li bindent=\"8\">"; //$NON-NLS-1$

                    detailsString += problem.autoInlineProblemDescription;

                    if (problem.subProcessTask != null) {
                        Process callProc = problem.subProcessTask.getProcess();

                        detailsString += " (" //$NON-NLS-1$ 
                                + callProc.getName() + " : " //$NON-NLS-1$
                                + problem.subProcessTask.getName() + ")"; //$NON-NLS-1$
                    }

                    detailsString += "</li>"; //$NON-NLS-1$
                }

            } else if (!processIsInvokedLocally) {
                detailsString +=
                        "<p><b>" + //$NON-NLS-1$
                                Messages.CreateOptimizedPackageWizard_UninvokedInlineSubProc2_longdesc
                                + "</b></p>" + //$NON-NLS-1$
                                "<li bindent=\"8\"><span color=\"" //$NON-NLS-1$
                                + ProcessRefactorConfigurationPage.DETAIL_TEXT_GREEN_KEY
                                + "\">" + //$NON-NLS-1$ 
                                Messages.CreateOptimizedPackageWizard_UnInvokedInlineSubProc3_longdesc
                                + "</span></li><li bindent=\"8\"><span color=\"" + ProcessRefactorConfigurationPage.DETAIL_TEXT_GREEN_KEY + "\">" + //$NON-NLS-1$ //$NON-NLS-2$
                                Messages.CreateOptimizedPackageWizard_UnInvokedInlineSubProc4_longdesc
                                + "</span></li>"; //$NON-NLS-1$                    
            }

            return detailsString;
        }

        /**
         * @return the process
         */
        public Process getProcess() {
            return process;
        }
    }

    /**
     * The create optimized package wizard.
     * <p>
     * This analyses the given package and offers up suggestion for optimising a
     * process package.
     * <p>
     * Currently this just means options for performing Inlining of
     * Sub-Processes.
     * 
     * @param srcPackage
     */
    public CreateOptimizedPackageWizard(Package srcPackage) {
        super(srcPackage);

        setWindowTitle(Messages.CreateOptimizedPackageWizard_CreateOptimizedPkg_title);

    }

    @Override
    public void init(Object inputObject) {
        super.init(inputObject);

        this.srcPackage = (Package) getInputObject();

        srcWorkingCopy =
                (Xpdl2WorkingCopyImpl) WorkingCopyUtil
                        .getWorkingCopyFor(srcPackage);

        subProcConfigItems = Collections.EMPTY_LIST;
    }

    @Override
    protected void postPageCreation() {
        super.postPageCreation();

        ProcessRefactorConfigurationPage configPage = getConfigPage();
        if (configPage != null) {
            configPage
                    .setTitle(Messages.CreateOptimizedPackageWizard_SelectInlineSubProcs_title);
            configPage
                    .setDescription(Messages.CreateOptimizedPackageWizard_SelectInlineSubProcs_longdesc2);
            configPage.setAlwaysRefreshOnCheckStateChange(true);
        }
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.ProcessRefactorWizard#getConfigurationItems(java.lang.Object)
     * 
     * @param inputObject
     * @return
     */
    @Override
    protected List<ProcessRefactorConfigurationItem> getConfigurationItems(
            Object inputObject) {

        // Perform an analysis of the source process package treating all
        // processes as if they had been set to inlined (so that we can find out
        // whether they are viable for in-lining).
        AnalyseInlineSubProcesses inlineSubProcAnalysis =
                new AnalyseInlineSubProcesses(srcWorkingCopy, srcPackage);
        inlineSubProcAnalysis.setAnalyseAllProcesses(true);
        inlineSubProcAnalysis.setWantProcessNotInvokedProblems(true);

        List<InlineSubProcessProblem> problems =
                inlineSubProcAnalysis.analysePackage(srcPackage);

        // Create a map of processes each linked with the problems that apply to
        // them.
        Map<Process, List<InlineSubProcessProblem>> processProblemMap =
                new HashMap<Process, List<InlineSubProcessProblem>>();

        for (InlineSubProcessProblem problem : problems) {
            if (problem.subProcess != null) {
                List<InlineSubProcessProblem> processProblems =
                        processProblemMap.get(problem.subProcess);

                if (processProblems != null) {
                    processProblems.add(problem);
                } else {
                    processProblems = new ArrayList<InlineSubProcessProblem>();
                    processProblems.add(problem);

                    processProblemMap.put(problem.subProcess, processProblems);
                }
            }
        }

        //
        // Build 4 categories of process types...
        // - Strong Candidates For Inline Sub-Process
        // - Potential Candidates For Inline Sub-Process
        // - Processes That Cannot Currently Be In-lined
        // - External Package Inline Sub-Processes
        //

        // First get the complete set of processes that are called.
        // The process hierarchy will always contain ALL processes from source
        // package PLUS any processes from other packages that are invoked by
        // source package process independent sub-proc tasks.
        Set<Process> completeProcessSet = new HashSet<Process>();

        List<ProcessCallHierarchy> processCallHierarchy =
                inlineSubProcAnalysis.getLastAnalysedProcessHierarchy();
        getCompleteProcessSet(processCallHierarchy, completeProcessSet);

        List<InlineSubProcConfigItem> cantInlineList =
                new ArrayList<InlineSubProcConfigItem>();
        List<InlineSubProcConfigItem> extPackageList =
                new ArrayList<InlineSubProcConfigItem>();
        List<InlineSubProcConfigItem> potentialCandidateList =
                new ArrayList<InlineSubProcConfigItem>();
        List<InlineSubProcConfigItem> strongCandidateList =
                new ArrayList<InlineSubProcConfigItem>();

        for (Process process : completeProcessSet) {
            // Create a configuration item for the process.
            InlineSubProcConfigItem configItem =
                    new InlineSubProcConfigItem(process,
                            AnalyseInlineSubProcesses
                                    .isInlineSubProcess(process));

            List<InlineSubProcessProblem> processProblems =
                    processProblemMap.get(process);
            if (processProblems != null) {
                configItem.setProblems(processProblems);
            }

            boolean processIsInvokedLocally =
                    processIsInvokedLocally(process, processCallHierarchy);
            configItem.setProcessIsInvokedLocally(processIsInvokedLocally);

            if (process.getPackage() != srcPackage) {
                // External pkg processes always go in ext pkg category
                // regardless of other status.
                configItem.setItemText(process.getPackage().getName()
                        + " : " + WorkingCopyUtil.getText(process)); //$NON-NLS-1$

                configItem
                        .setItemDesc(Messages.CreateOptimizedPackageWizard_ExtPkgSubProc_longdesc);

                extPackageList.add(configItem);

            } else if (processProblems != null && processProblems.size() > 0) {
                // Anything else with problems goes in the can't inline process
                // category.
                configItem
                        .setItemDesc(Messages.CreateOptimizedPackageWizard_CannotInlineSubProc_longdesc);

                cantInlineList.add(configItem);

            } else {
                // Now we can see if this is a strong candidate which must...
                // - These are processes that are defined within the constraints
                // for inline sub-processes.
                // - The process is invoked from an independent sub-process task
                // in another process in the package.
                // - The process will contain no tasks / events that will cause
                // a pause in the process-flow (such as user/manual/receive
                // task, intermediate event type message or timer).
                boolean isStrongCandidate =
                        isStrongCandidate(process, processCallHierarchy);

                if (isStrongCandidate) {

                    configItem
                            .setItemDesc(Messages.CreateOptimizedPackageWizard_InlineSubprocStrongCandidate_longdesc);
                    configItem
                            .setExtraDetailText("<p><b>" + //$NON-NLS-1$ 
                                    Messages.CreateOptimizedPackageWizard_InlineSubprocStrongCandidate2_longdesc
                                    + "</b></p><li bindent=\"8\">" + //$NON-NLS-1$
                                    Messages.CreateOptimizedPackageWizard_InlineWithinConstraints_longdesc
                                    + "</li><li bindent=\"8\">" + //$NON-NLS-1$
                                    Messages.CreateOptimizedPackageWizard_InlineSubProcIsInvoked_longdesc
                                    + "</li><li bindent=\"8\">" + //$NON-NLS-1$
                                    Messages.CreateOptimizedPackageWizard_InlineSubProcContainsNonDelayTasks_longdesc
                                    + "</li><li bindent=\"24\"><span color=\"" + ProcessRefactorConfigurationPage.DETAIL_TEXT_GREEN_KEY + "\">" + //$NON-NLS-1$ //$NON-NLS-2$
                                    Messages.CreateOptimizedPackageWizard_InlineSubProcContainsNonDelayTasks2_longdesc
                                    + "</span></li>"); //$NON-NLS-1$

                    strongCandidateList.add(configItem);

                } else {
                    // Last resort is that it is potential candidate.
                    configItem
                            .setItemDesc(Messages.CreateOptimizedPackageWizard_InlineSubProcPotentialCandidate_longdesc);
                    configItem
                            .setExtraDetailText("<p><b>" + //$NON-NLS-1$ 
                                    Messages.CreateOptimizedPackageWizard_InlineSubProcPotentialCandidate2_longdesc
                                    + "</b></p><li bindent=\"8\">" + //$NON-NLS-1$
                                    Messages.CreateOptimizedPackageWizard_InlineSubProcContainsDelayTasks_longdesc
                                    + "</li><li bindent=\"24\"><span color=\"" + ProcessRefactorConfigurationPage.DETAIL_TEXT_GREEN_KEY + "\">" + //$NON-NLS-1$ //$NON-NLS-2$
                                    Messages.CreateOptimizedPackageWizard_InlineSubProcContainsDelayTasks2_longdesc
                                    + "</span></li>"); //$NON-NLS-1$

                    potentialCandidateList.add(configItem);

                }
            }
        }

        // Finally, create our categories.
        List<ProcessRefactorConfigurationItem> categories =
                new ArrayList<ProcessRefactorConfigurationItem>();

        if (strongCandidateList.size() > 0) {
            ProcessRefactorConfigurationItem cat =
                    new ConfigItemFolderWithProblemMarker(
                            this,
                            Messages.CreateOptimizedPackageWizard_InlineSubProcStrongCandidates_label,
                            Messages.CreateOptimizedPackageWizard_InlineSubProcStrongCandidates_longdesc,
                            false,
                            false,
                            Xpdl2ProcessEditorPlugin
                                    .getDefault()
                                    .getImageRegistry()
                                    .get(ProcessEditorConstants.IMG_PROCESSSTRONG),
                            Xpdl2ProcessEditorPlugin
                                    .getDefault()
                                    .getImageRegistry()
                                    .get(ProcessEditorConstants.IMG_PROCESSSTRONG_PROBLEM));

            for (InlineSubProcConfigItem item : strongCandidateList) {
                cat.addChildItem(item);
            }

            categories.add(cat);
        }

        if (potentialCandidateList.size() > 0) {
            ProcessRefactorConfigurationItem cat =
                    new ConfigItemFolderWithProblemMarker(
                            this,
                            Messages.CreateOptimizedPackageWizard_InlineSubProcPotentialCandidates_label,
                            Messages.CreateOptimizedPackageWizard_InlineSubProcPotentialCandidates_longdesc,
                            false,
                            false,
                            Xpdl2ProcessEditorPlugin
                                    .getDefault()
                                    .getImageRegistry()
                                    .get(ProcessEditorConstants.IMG_PROCESSNORMAL),
                            Xpdl2ProcessEditorPlugin
                                    .getDefault()
                                    .getImageRegistry()
                                    .get(ProcessEditorConstants.IMG_PROCESSNORMAL_PROBLEM));

            for (InlineSubProcConfigItem item : potentialCandidateList) {
                cat.addChildItem(item);
            }

            categories.add(cat);
        }

        if (cantInlineList.size() > 0) {
            ProcessRefactorConfigurationItem cat =
                    new ConfigItemFolderWithProblemMarker(
                            this,
                            Messages.CreateOptimizedPackageWizard_InlineSubProcCant_label,
                            Messages.CreateOptimizedPackageWizard_InlineSubProcCant_longdesc,
                            false,
                            false,
                            Xpdl2ProcessEditorPlugin
                                    .getDefault()
                                    .getImageRegistry()
                                    .get(ProcessEditorConstants.IMG_PROCESSCANT),
                            Xpdl2ProcessEditorPlugin
                                    .getDefault()
                                    .getImageRegistry()
                                    .get(ProcessEditorConstants.IMG_PROCESSCANT_PROBLEM));

            for (InlineSubProcConfigItem item : cantInlineList) {
                cat.addChildItem(item);
            }

            categories.add(cat);
        }

        if (extPackageList.size() > 0) {
            ProcessRefactorConfigurationItem cat =
                    new ConfigItemFolderWithProblemMarker(
                            this,
                            Messages.CreateOptimizedPackageWizard_InlineSubProcExternal_label,
                            Messages.CreateOptimizedPackageWizard_InlineSubProcExternal_longdesc,
                            false,
                            false,
                            Xpdl2ProcessEditorPlugin
                                    .getDefault()
                                    .getImageRegistry()
                                    .get(ProcessEditorConstants.IMG_PACKAGEFILE),
                            Xpdl2ProcessEditorPlugin
                                    .getDefault()
                                    .getImageRegistry()
                                    .get(ProcessEditorConstants.IMG_PACKAGEPROBLEM));
            cat.setCheckIsEnabled(false);

            for (InlineSubProcConfigItem item : extPackageList) {
                item.setCheckIsEnabled(false);
                cat.addChildItem(item);
            }

            categories.add(cat);
        }

        //
        // Finally store a separate non-hierarchical list of actual process
        // config items (for use by creator on on return from wizard).
        subProcConfigItems = new ArrayList<InlineSubProcConfigItem>();
        subProcConfigItems.addAll(cantInlineList);
        subProcConfigItems.addAll(strongCandidateList);
        subProcConfigItems.addAll(potentialCandidateList);
        subProcConfigItems.addAll(extPackageList);

        return categories;
    }

    /**
     * Check if this process is a strong candidate for inlining.
     * 
     * @param process
     * @param processCallHierarchy
     * @return
     */
    private boolean isStrongCandidate(Process process,
            List<ProcessCallHierarchy> processCallHierarchy) {
        Collection<Activity> activities =
                Xpdl2ModelUtil.getAllActivitiesInProc(process);

        for (Activity act : activities) {
            boolean isStrong = false;

            if (act.getImplementation() instanceof Task) {
                Task task = (Task) act.getImplementation();

                if (task.getTaskSend() != null || task.getTaskService() != null
                        || task.getTaskScript() != null) {
                    isStrong = true;
                }

            } else if (act.getImplementation() instanceof SubFlow) {
                isStrong = true;

            } else if (act.getBlockActivity() != null) {
                isStrong = true;

            } else if (act.getEvent() instanceof IntermediateEvent) {
                IntermediateEvent event = (IntermediateEvent) act.getEvent();

                if (event.getTarget() != null && event.getTarget().length() > 0) {
                    // Events attached to task boundary are ok.
                    isStrong = true;
                }
                TriggerType trigger = event.getTrigger();
                if (TriggerType.ERROR_LITERAL.equals(trigger)
                        || TriggerType.LINK_LITERAL.equals(trigger)
                        || TriggerType.CANCEL_LITERAL.equals(trigger)) {
                    isStrong = true;
                }

            } else if (act.getEvent() instanceof StartEvent
                    || act.getEvent() instanceof EndEvent) {
                isStrong = true;
            }

            // If this activity is not a strong candidate then whole process
            // isn't.
            if (!isStrong) {
                return false;
            }

        }

        return true;
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

    /**
     * Recursively create set of all processes in local package (or processes in
     * external packages invoked from this package).
     * 
     * @param lastAnalysedProcessHierarchy
     * @param completeProcessSet
     */
    private void getCompleteProcessSet(
            List<ProcessCallHierarchy> processHierarchyLevel,
            Set<Process> completeProcessSet) {
        for (ProcessCallHierarchy ph : processHierarchyLevel) {
            completeProcessSet.add(ph.process);

            if (ph.calledProcesses != null) {
                getCompleteProcessSet(ph.calledProcesses, completeProcessSet);
            }
        }
        return;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.ProcessRefactorWizard#getPostConfigPages(java.lang.Object)
     * 
     * @param inputObject
     * @return
     */
    @Override
    protected List<WizardPage> getPostConfigPages(Object inputObject) {
        return Collections.EMPTY_LIST;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.ProcessRefactorWizard#isConfigurationComplete(java.lang.Object,
     *      java.util.List)
     * 
     * @param inputObject
     * @param configItems
     * @return
     */
    @Override
    protected boolean isConfigurationComplete(Object inputObject,
            List<ProcessRefactorConfigurationItem> configItems) {
        return true;
    }

    /**
     * If dialog returns OK then caller can use this to get a list of proess
     * config items (to check the check state)
     * 
     * @return the subProcConfigItems
     */
    public List<InlineSubProcConfigItem> getSubProcConfigItems() {
        return subProcConfigItems;
    }

}
