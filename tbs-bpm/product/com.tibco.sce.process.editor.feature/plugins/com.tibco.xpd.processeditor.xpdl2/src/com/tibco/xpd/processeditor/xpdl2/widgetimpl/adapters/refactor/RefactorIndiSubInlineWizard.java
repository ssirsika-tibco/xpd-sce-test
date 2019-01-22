/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.inlineSubProcess.AnalyseInlineSubProcesses;
import com.tibco.xpd.processeditor.xpdl2.inlineSubProcess.AnalyseInlineSubProcesses.InlineSubProcessProblem;
import com.tibco.xpd.processeditor.xpdl2.inlineSubProcess.AnalyseInlineSubProcesses.InlineSubProcessProblemId;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processwidget.adapters.DiagramModelImageProvider;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author aallway
 * 
 */
public class RefactorIndiSubInlineWizard extends ProcessRefactorWizard {
    private RefactorIndiSubInlineInfo refactorInfo;

    private Xpdl2WorkingCopyImpl workingCopy;

    private boolean haveProblemUnlessEmbeddedItems;

    private boolean haveUnresolvableProblems;

    /**
     * This config item is the folder containing problems that can be resolved
     * by in-lining into an embedded sub-process. It also acts as the config
     * 'switch' that allows the user to select inline into embedded sub-process
     * even if there are no problems.
     */
    private ProcessRefactorConfigurationItem inlineAsEmbeddedConfigItem;

    /**
     * This config item is the folder containing unresolvable problems (and
     * hence is always disabled because there are no 'fixes' for the problems.
     */
    private ProcessRefactorConfigurationItem unresolvableProblemsConfigItem;

    /**
     * This config item is the folder containing problems that require a gateway
     * to be inserted to join paths on the way into the sub-process content.
     * (Because indi subproc task has multiple in flow or the sub-process start
     * event has multiple outgoing flow or the subproc has multiple start
     * activities. If the user selects inline as embedded, this option is
     * disabled and ignored.
     */
    private ProcessRefactorConfigurationItem joinInPathsConfigItem;

    /**
     * This config item is the folder containing problems that require a gateway
     * to be inserted to join paths on the way out of the sub-process content.
     * (Because indi subproc task has multiple out flow or the sub-process end
     * event has multiple incoming flow or the subproc has multiple end
     * activities. If the user selects inline as embedded, this option is
     * disabled and ignored.
     */
    private ProcessRefactorConfigurationItem joinOutPathsConfigItem;

    private String errorUnlessEmbeddedMsg =
            Messages.RefactorIndiSubInlineWizard_MustInlineToEmbedded_longdesc2;

    public RefactorIndiSubInlineWizard(RefactorIndiSubInlineInfo refactorInfo) {
        super(refactorInfo);

        setWindowTitle(Messages.RefactorIndiSubInlineWizard_InlineSubProc_title);
    }

    @Override
    public void init(Object inputObject) {
        super.init(inputObject);

        refactorInfo = (RefactorIndiSubInlineInfo) getInputObject();

        workingCopy =
                (Xpdl2WorkingCopyImpl) WorkingCopyUtil
                        .getWorkingCopyFor(refactorInfo.subProcessTask
                                .getProcess().getPackage());

        haveProblemUnlessEmbeddedItems = false;
        haveUnresolvableProblems = false;

    }

    @Override
    protected void postPageCreation() {
        super.postPageCreation();

        ProcessRefactorConfigurationPage configPage = getConfigPage();

        if (haveUnresolvableProblems) {
            configPage
                    .setErrorMessage(Messages.RefactorIndiSubInlineWizard_IrresolvableProblems_longdesc);

        } else if (haveProblemUnlessEmbeddedItems) {
            configPage.setErrorMessage(errorUnlessEmbeddedMsg);
        }

        configPage
                .setTitle(Messages.RefactorIndiSubInlineWizard_SelectOptViewProb_label);

        configPage.setAlwaysRefreshOnCheckStateChange(true);
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

        //
        // First of all, run an analysis of the sub-process that is called and
        // the sub-process task that is calling it.
        refactorInfo.inlineSubProcAnalysis =
                new AnalyseInlineSubProcesses(workingCopy, Xpdl2ModelUtil
                        .getPackage(refactorInfo.subProcessTask));

        List<InlineSubProcessProblem> problems =
                new ArrayList<InlineSubProcessProblem>();

        List<InlineSubProcessProblem> subProcProblems =
                refactorInfo.inlineSubProcAnalysis
                        .analyseSubProcess(refactorInfo.subProcess);
        problems.addAll(subProcProblems);

        List<InlineSubProcessProblem> subProcTaskProblems =
                refactorInfo.inlineSubProcAnalysis
                        .analyseSubProcessTask(refactorInfo.subProcessTask);
        problems.addAll(subProcTaskProblems);

        //
        // As far as manual refactor is concerned, there are 2 categories of
        // problems...
        // - Problems that can be resolved by in-lining into an embedded
        // sub-process task.
        // - Problems that simply cannot be resolved.

        // We also always want to give the user the option to say inline as
        // embedded sub-proc even if there are no problems.

        // So first of all, sort the problems into the 2 categories.
        List<InlineSubProcessProblem> embSubProcResolvedProblems =
                new ArrayList<InlineSubProcessProblem>();

        List<InlineSubProcessProblem> unresolvableProblems =
                new ArrayList<InlineSubProcessProblem>();

        for (InlineSubProcessProblem problem : problems) {
            if (!isIgnoredProblem(problem)) {
                if (problem.isProblemForEmbedded) {
                    // This problem is fatal as can't even be resolved by
                    // inlining
                    // into embedded sub-proc.
                    unresolvableProblems.add(problem);

                    haveUnresolvableProblems = true;

                } else {
                    // This problem can be resolved by inlining into embedded
                    // sub-proc.
                    embSubProcResolvedProblems.add(problem);

                    haveProblemUnlessEmbeddedItems = true;
                }
            }
        }

        List<ProcessRefactorConfigurationItem> configItems =
                new ArrayList<ProcessRefactorConfigurationItem>();

        // 
        // If we have any unresolvable problems, create a disabled config
        // category and add the problems to it.
        if (haveUnresolvableProblems) {
            unresolvableProblemsConfigItem =
                    new ProcessRefactorConfigurationItem(
                            this,
                            Messages.RefactorIndiSubInlineWizard_IrresolvableProblemsFolder_label,
                            Messages.RefactorIndiSubInlineWizard_IrresolvableProblems_label,
                            false,
                            true,
                            Xpdl2ProcessEditorPlugin
                                    .getDefault()
                                    .getImageRegistry()
                                    .get(ProcessEditorConstants.IMG_ERROR_DECORATOR));

            unresolvableProblemsConfigItem.setCheckIsEnabled(false);

            for (InlineSubProcessProblem problem : unresolvableProblems) {

                // Use a Sub-Proc task icon if the problem is related to call
                // task,
                // or process icon if related to the sub-process itself.
                Image icon;
                if (subProcTaskProblems.contains(problem)) {
                    icon =
                            Xpdl2ProcessEditorPlugin
                                    .getDefault()
                                    .getImageRegistry()
                                    .get(ProcessEditorConstants.IMG_SUBPROCTASK);
                } else {
                    icon =
                            Xpdl2ProcessEditorPlugin
                                    .getDefault()
                                    .getImageRegistry()
                                    .get(ProcessEditorConstants.IMG_BLUE_PROCESS);
                }

                ConfigItemWithProcImage configItem =
                        new ConfigItemWithProcImage(
                                problem,
                                problem.manualInlineProblemDescription,
                                Messages.RefactorIndiSubInlineWizard_IrresolvableProblem_longdesc,
                                false, true, icon);

                configItem.setCheckIsEnabled(false);

                unresolvableProblemsConfigItem.addChildItem(configItem);

                // Create an image enabled config item subclass to show
                // problem and use it if there is a way of displaying it.
                // else consider using text detail to describe the problem.
                setupProblemImager(configItem, problem);

            }

            configItems.add(unresolvableProblemsConfigItem);

        } else {
            // 
            // If no unresolvable problems then add option to inline as embedded
            // sub-proc (with child items that would be a problem if not
            // embedded)
            inlineAsEmbeddedConfigItem =
                    new ProcessRefactorConfigurationItem(
                            this,
                            Messages.RefactorIndiSubInlineWizard_InlineIntoEmbedded_label,
                            Messages.RefactorIndiSubInlineWizard_InlineIntoEmbedded_longdesc,
                            false, haveProblemUnlessEmbeddedItems,
                            Xpdl2ProcessEditorPlugin.getDefault()
                                    .getImageRegistry()
                                    .get(ProcessEditorConstants.IMG_EMBSUBPROC)) {
                        @Override
                        public void setChecked(boolean isChecked) {
                            super.setChecked(isChecked);

                            ProcessRefactorConfigurationPage configPage =
                                    getConfigPage();

                            if (!isChecked() && haveProblemUnlessEmbeddedItems) {

                                configPage
                                        .setErrorMessage(errorUnlessEmbeddedMsg);
                            } else {
                                configPage.setErrorMessage(null);
                            }

                            refactorInfo.inlineAsEmbeddedSubProc = isChecked();
                        }
                    };

            // Then add any items that are problems if user does not set select
            // embedded sub-proc.
            // We will always set these to disabled - thus indicating that any
            // one
            // requires all to be selected (i.e. via parent).
            for (InlineSubProcessProblem problem : embSubProcResolvedProblems) {

                // Use a Sub-Proc task icon if the problem is related to call
                // task,or process icon if related to the sub-process itself.
                Image icon;
                if (subProcTaskProblems.contains(problem)) {
                    icon =
                            Xpdl2ProcessEditorPlugin
                                    .getDefault()
                                    .getImageRegistry()
                                    .get(ProcessEditorConstants.IMG_SUBPROCTASK);
                } else {
                    icon =
                            Xpdl2ProcessEditorPlugin
                                    .getDefault()
                                    .getImageRegistry()
                                    .get(ProcessEditorConstants.IMG_BLUE_PROCESS);
                }

                ConfigItemWithProcImage configItem =
                        new ConfigItemWithProcImage(
                                problem,
                                problem.manualInlineProblemDescription,
                                Messages.RefactorIndiSubInlineWizard_OkIfEmbeddedProblem_longdesc,
                                false, false, icon);

                // configItem.setCheckIsEnabled(false);

                inlineAsEmbeddedConfigItem.addChildItem(configItem);

                // Create an image enabled config item subclass to show
                // problem and use it if there is a way of displaying it.
                // else consider using text detail to describe the problem.
                setupProblemImager(configItem, problem);
            }

            configItems.add(inlineAsEmbeddedConfigItem);
        }

        return configItems;
    }

    /**
     * Some inline sub-proc problems have associated images. This method
     * assesses the various problems and setups up an image provider for them to
     * highlight the objects causing the problem.
     * 
     * @param configItem
     * @param problem
     */
    private void setupProblemImager(ConfigItemWithProcImage configItem,
            InlineSubProcessProblem problem) {

        DiagramModelImageProvider imageProvider = null;
        Set<Object> imageAreaObjects = new HashSet<Object>();
        Set<Object> highlightObjects = new HashSet<Object>();
        Set<Object> selectedObjects = new HashSet<Object>();

        if (InlineSubProcessProblemId.SUBPROC_MULTI_POOLS
                .equals(problem.problemId)) {
            imageProvider = refactorInfo.subProcessImageProvider;

            Collection<Pool> processPools =
                    Xpdl2ModelUtil.getProcessPools(refactorInfo.subProcess);
            imageAreaObjects.addAll(processPools);
            highlightObjects.addAll(processPools);

        } else if (InlineSubProcessProblemId.SUBPROC_MULTI_STARTS
                .equals(problem.problemId)) {
            if (problem.extraInfo instanceof Collection) {
                // For this problem, extra info should be list of start places.
                Collection<Activity> starts =
                        (Collection<Activity>) problem.extraInfo;

                if (!starts.isEmpty()) {
                    imageProvider = refactorInfo.subProcessImageProvider;

                    imageAreaObjects.addAll(starts);
                    imageAreaObjects.addAll(getConnectedActivities(starts));
                    highlightObjects.addAll(starts);
                }
            }
        } else if (InlineSubProcessProblemId.SUBPROC_MULTI_ENDS
                .equals(problem.problemId)) {
            if (problem.extraInfo instanceof Collection) {
                // For this problem, extra info should be list of end places.
                Collection<Activity> ends =
                        (Collection<Activity>) problem.extraInfo;

                if (!ends.isEmpty()) {
                    imageProvider = refactorInfo.subProcessImageProvider;

                    imageAreaObjects.addAll(ends);
                    imageAreaObjects.addAll(getConnectedActivities(ends));
                    highlightObjects.addAll(ends);
                }
            }

        } else if (InlineSubProcessProblemId.SUBPROC_START_MULTI_FLOW
                .equals(problem.problemId)) {
            if (problem.extraInfo instanceof Activity) {
                Activity startAct = (Activity) problem.extraInfo;

                imageProvider = refactorInfo.subProcessImageProvider;

                imageAreaObjects.add(startAct);
                List<Activity> startActList =
                        Collections.singletonList(startAct);

                imageAreaObjects.addAll(getConnectedActivities(startActList));

                selectedObjects.add(startAct);

                highlightObjects.addAll(getActivityConnections(startActList));
            }

        } else if (InlineSubProcessProblemId.SUBPROC_END_MULTI_FLOW
                .equals(problem.problemId)) {
            if (problem.extraInfo instanceof Activity) {
                Activity endAct = (Activity) problem.extraInfo;

                imageProvider = refactorInfo.subProcessImageProvider;

                imageAreaObjects.add(endAct);
                List<Activity> startActList = Collections.singletonList(endAct);

                imageAreaObjects.addAll(getConnectedActivities(startActList));

                selectedObjects.add(endAct);

                highlightObjects.addAll(getActivityConnections(startActList));
            }
        } else if (InlineSubProcessProblemId.TASK_ATTACHED_EVENTS
                .equals(problem.problemId)) {
            if (problem.extraInfo instanceof Collection) {
                Collection<Activity> events =
                        (Collection<Activity>) problem.extraInfo;

                if (events.size() > 0) {
                    imageProvider = refactorInfo.callingProcessImageProvider;

                    imageAreaObjects.add(problem.subProcessTask);
                    imageAreaObjects.addAll(getConnectedActivities(Collections
                            .singletonList(problem.subProcessTask)));
                    imageAreaObjects.addAll(getConnectedActivities(events));

                    highlightObjects.addAll(events);

                    selectedObjects.add(problem.subProcessTask);

                }
            }
        } else if (InlineSubProcessProblemId.TASK_SEQFLOW_IN_MULTI
                .equals(problem.problemId)
                || InlineSubProcessProblemId.TASK_SEQFLOW_OUT_MULTI
                        .equals(problem.problemId)) {
            if (problem.extraInfo instanceof Collection) {
                Collection<Transition> flows =
                        (Collection<Transition>) problem.extraInfo;

                if (flows.size() > 0) {
                    imageProvider = refactorInfo.callingProcessImageProvider;

                    imageAreaObjects.add(problem.subProcessTask);
                    imageAreaObjects.addAll(getConnectedActivities(Collections
                            .singletonList(problem.subProcessTask)));

                    highlightObjects.addAll(flows);

                    selectedObjects.add(problem.subProcessTask);

                }
            }
        }

        if (imageProvider != null) {
            configItem.setImageProvider(imageProvider);
            configItem.setImageAreaObjects(imageAreaObjects);
            configItem.setHighlightObjects(highlightObjects);
            configItem.setSelectedObjects(selectedObjects);
        }

        return;
    }

    /**
     * Get list of sequence flow connections from the given set of activities.
     * 
     * @param connections
     * @return
     */
    private Collection<Transition> getActivityConnections(
            List<Activity> activities) {
        Set<Transition> connections = new HashSet<Transition>();

        for (Activity act : activities) {
            FlowContainer flowContainer = act.getFlowContainer();

            for (Iterator iterator = flowContainer.getTransitions().iterator(); iterator
                    .hasNext();) {
                Transition trans = (Transition) iterator.next();

                if (trans.getFrom().equals(act.getId())
                        || trans.getTo().equals(act.getId())) {
                    connections.add(trans);
                }
            }
        }

        return connections;
    }

    /**
     * Get a list of the activities connected to the given activities
     * 
     * @param starts
     * @return
     */
    private Collection<Activity> getConnectedActivities(
            Collection<Activity> activities) {
        Set<Activity> connectedActs = new HashSet<Activity>();

        for (Activity act : activities) {
            FlowContainer flowContainer = act.getFlowContainer();

            for (Iterator iterator = flowContainer.getTransitions().iterator(); iterator
                    .hasNext();) {
                Transition trans = (Transition) iterator.next();

                if (trans.getFrom().equals(act.getId())) {
                    Activity connAct = flowContainer.getActivity(trans.getTo());
                    if (connAct != null) {
                        connectedActs.add(connAct);
                    }
                } else if (trans.getTo().equals(act.getId())) {
                    Activity connAct =
                            flowContainer.getActivity(trans.getFrom());
                    if (connAct != null) {
                        connectedActs.add(connAct);
                    }
                }
            }
        }

        return connectedActs;
    }

    /**
     * There are certain problems that we don't care about for manual
     * refactoring because we do not need to be quite as tight about maintaining
     * the original flow-semantics.
     * 
     * @param problem
     * 
     * @return
     */
    private boolean isIgnoredProblem(InlineSubProcessProblem problem) {
        if (InlineSubProcessProblemId.SUBPROC_ENDEVENT_NOT_NONE
                .equals(problem.problemId)) {
            return true;
        } else if (InlineSubProcessProblemId.SUBPROC_STARTEVENT_NOT_NONE
                .equals(problem.problemId)) {
            return true;
        }
        return false;
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
        if (haveUnresolvableProblems) {
            // If there are any unresolvable items then we cannot continue.
            return false;
        }

        if (haveProblemUnlessEmbeddedItems
                && !inlineAsEmbeddedConfigItem.isChecked()) {
            // If there are problems that can only be resolved if selected to
            // inline as embedded AND embedded is not selected then we cannot
            // continue.
            return false;
        }
        return true;
    }

    @Override
    public boolean performFinish() {
        boolean ret = super.performFinish();

        refactorInfo.inlineAsEmbeddedSubProc =
                inlineAsEmbeddedConfigItem.isChecked();

        return ret;
    }

    /**
     * Config item Class with optional process image.
     * 
     * @author aallway
     * 
     */
    private class ConfigItemWithProcImage extends
            ProcessRefactorConfigurationItem {

        private DiagramModelImageProvider imageProvider;

        private Collection<Object> imageAreaObjects = Collections.EMPTY_LIST;

        private Collection<Object> highlightObjects = Collections.EMPTY_LIST;

        private Collection<Object> selectedObjects = Collections.EMPTY_LIST;

        public ConfigItemWithProcImage(Object inputObject, String itemText,
                String itemDesc, boolean isChecked,
                boolean isProblemIfUnchecked, Image decorator) {
            super(inputObject, itemText, itemDesc, isChecked,
                    isProblemIfUnchecked, decorator);
        }

        @Override
        public Image getConfigItemPreviewImage(Dimension sizeHint) {
            if (imageProvider != null && !imageAreaObjects.isEmpty()) {
                return imageProvider.getDiagramImageFromModel(imageAreaObjects,
                        selectedObjects,
                        highlightObjects,
                        sizeHint,
                        30,
                        DiagramModelImageProvider.PAINT_UNSELECTED_OBJECTS
                                | DiagramModelImageProvider.MAX_SCALE_1_TO_1);
            }
            return null;
        }

        /**
         * @param imageProvider
         *            the imageProvider to set
         */
        public void setImageProvider(DiagramModelImageProvider imageProvider) {
            this.imageProvider = imageProvider;
        }

        /**
         * @param highlightObjects
         *            the highlightObjects to set
         */
        public void setHighlightObjects(Collection<Object> highlightObjects) {
            this.highlightObjects = highlightObjects;
        }

        /**
         * @param imageAreaObjects
         *            the imageAreaObjects to set
         */
        public void setImageAreaObjects(Collection<Object> imageAreaObjects) {
            this.imageAreaObjects = imageAreaObjects;
        }

        /**
         * @param selectedObjects
         *            the selectedObjects to set
         */
        public void setSelectedObjects(Collection<Object> selectedObjects) {
            this.selectedObjects = selectedObjects;
        }

    }
}
