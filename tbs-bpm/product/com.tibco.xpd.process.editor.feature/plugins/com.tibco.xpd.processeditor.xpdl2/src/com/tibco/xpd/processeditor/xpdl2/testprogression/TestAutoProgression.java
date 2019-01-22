/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.testprogression;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.xpd.processwidget.ProcessWidget;
import com.tibco.xpd.processwidget.parts.WidgetRootEditPart;
import com.tibco.xpd.processwidget.process.progression.ProcessProgression;
import com.tibco.xpd.processwidget.progression.AbstractDiagramProgression;
import com.tibco.xpd.processwidget.progression.model.ProgressionModel;
import com.tibco.xpd.processwidget.progression.model.ProgressionState;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.JoinSplitType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * TestAutoProgression
 * 
 * 
 * @author aallway
 * @since 3.3 (30 Oct 2009)
 */
public class TestAutoProgression {

    private AbstractDiagramProgression processProgression;

    private Set<Object> inProgressOrProcessedObjects =
            new LinkedHashSet<Object>();

    private boolean cancelled = false;

    private boolean progressionRunning = false;

    private Object timedUpdateLock = new Object();

    private WorkingCopy processWorkingCopy;

    private PropertyChangeListener workingCopyChangeListener;

    private WidgetRootEditPart rootEditPart;

    private AdvanceProgression advanceProgressionRunnable;

    private IWorkbenchPart diagramWorkbenchPart;

    public TestAutoProgression(ProcessWidget processWidgetImpl) {
        rootEditPart =
                (WidgetRootEditPart) processWidgetImpl.getGraphicalViewer()
                        .getRootEditPart();

        workingCopyChangeListener = new ProcessChangeListener();

        processWorkingCopy =
                WorkingCopyUtil.getWorkingCopyFor((Process) rootEditPart
                        .getProcessEditPart().getModel());

        diagramWorkbenchPart =
                processWidgetImpl.getSite().getPage().getActivePart();

        processWidgetImpl.getSite().getPage()
                .addPartListener(new IPartListener() {

                    public void partActivated(IWorkbenchPart part) {
                        if (part.equals(diagramWorkbenchPart)) {
                            if (processProgression != null
                                    && advanceProgressionRunnable != null) {
                                if (!processProgression.isActivitated()) {
                                    cancelled = false;
                                    processProgression.activateProgression();
                                    Display
                                            .getDefault()
                                            .asyncExec(advanceProgressionRunnable);
                                }
                            }
                        }
                    }

                    public void partBroughtToTop(IWorkbenchPart part) {
                    }

                    public void partClosed(IWorkbenchPart part) {
                        stopProgression();
                    }

                    public void partDeactivated(IWorkbenchPart part) {
                        if (part.equals(diagramWorkbenchPart)) {
                            if (processProgression != null) {
                                if (processProgression.isActivitated()) {
                                    cancelled = true;
                                    processProgression.deactivateProgression();
                                }
                            }
                        }
                    }

                    public void partOpened(IWorkbenchPart part) {
                    }
                });

    }

    /**
     * @return the progressionRunning
     */
    public boolean isProgressionRunning() {
        return progressionRunning;
    }

    /**
     * @param cancelled
     *            the cancelled to set
     */
    public void stopProgression() {
        synchronized (timedUpdateLock) {
            /* Make sure we don't try to use progression layer from timer. */
            this.cancelled = true;
            processWorkingCopy.removeListener(workingCopyChangeListener);

            if (processProgression != null) {
                processProgression.deactivateProgression();
                processProgression.dispose();
                processProgression = null;
            }

            progressionRunning = false;
        }

        return;
    }

    /**
     * Start the automated progression.
     */
    public void startProgression() {
        if (progressionRunning) {
            stopProgression();
        }

        initialiseProcessProgression();

        progressionRunning = true;
        cancelled = false;
        processProgression.activateProgression();

        processWorkingCopy.addListener(workingCopyChangeListener);

        advanceProgressionRunnable = new AdvanceProgression();

        Display.getCurrent().asyncExec(advanceProgressionRunnable);

        return;
    }

    /**
     * @param rootEditPart
     */
    private void initialiseProcessProgression() {
        processProgression = new ProcessProgression(rootEditPart);

        processProgression.initialiseProgressionModel();

        /*
         * Start the whole thing by finding the start events, and adding setting
         * them up as in progress.
         */
        Process process =
                (Process) rootEditPart.getProcessEditPart().getModel();

        for (Activity activity : process.getActivities()) {
            if (activity.getIncomingTransitions().isEmpty()) {

                inProgressOrProcessedObjects.add(activity);

                ProgressionModel progressionModel =
                        processProgression
                                .getProgressionModelForDiagramObject(activity);

                if (progressionModel != null) {
                    progressionModel
                            .setProgressionState(ProgressionState.IN_PROGRESS);
                }
            }
        }
        return;
    }

    /**
     * 
     * 
     * @author aallway
     * @since 3.3 (26 Jan 2010)
     */
    private final class AdvanceProgression implements Runnable {
        public void run() {
            synchronized (timedUpdateLock) {
                if (!cancelled) {

                    /*
                     * Advance the progression by finding all in progress and
                     * setting them to complete before adding all the outgoing
                     * activities.
                     */
                    List<ProgressionModel> toComplete =
                            new ArrayList<ProgressionModel>();
                    for (Object modelObject : inProgressOrProcessedObjects) {

                        ProgressionModel progressionModel =
                                processProgression
                                        .getProgressionModelForDiagramObject(modelObject);
                        if (progressionModel != null) {
                            if (ProgressionState.IN_PROGRESS
                                    .equals(progressionModel
                                            .getProgressionState())) {
                                toComplete.add(progressionModel);
                            }
                        }
                    }

                    boolean moreToDo = false;

                    for (ProgressionModel progressionModel : toComplete) {
                        if (progressionModel.getDiagramModelObject() instanceof Transition) {
                            if (completeTransition(progressionModel)) {
                                moreToDo = true;
                            }

                        } else if (progressionModel.getDiagramModelObject() instanceof Activity) {
                            if (completeActivity(progressionModel)) {
                                moreToDo = true;
                            }
                        }
                    }

                    if (moreToDo) {
                        Display.getCurrent().timerExec(2000, this);
                        return;
                    }
                }
            }

            return;
        }

        private boolean completeActivity(ProgressionModel progressionModel) {
            boolean moreToDo = false;

            Activity activity =
                    (Activity) progressionModel.getDiagramModelObject();
            inProgressOrProcessedObjects.remove(activity);

            progressionModel.setProgressionState(ProgressionState.COMPLETE);

            List<Transition> outgoingTransitions =
                    activity.getOutgoingTransitions();
            if (!outgoingTransitions.isEmpty()) {
                if (activity.getRoute() != null
                        && outgoingTransitions.size() > 1
                        && !JoinSplitType.PARALLEL_LITERAL.equals(activity
                                .getRoute().getGatewayType())) {
                    /* For non parallel splits pick one at random! */
                    int idx =
                            (int) Math.round(Math.random()
                                    * (outgoingTransitions.size() - 1));
                    outgoingTransitions =
                            Collections.singletonList(outgoingTransitions
                                    .get(idx));
                }

                for (Transition out : outgoingTransitions) {
                    if (!inProgressOrProcessedObjects.contains(out)) {
                        boolean doInProgressTransitions = false;

                        if (doInProgressTransitions) {
                            inProgressOrProcessedObjects.add(out);

                            ProgressionModel outProgressionModel =
                                    processProgression
                                            .getProgressionModelForDiagramObject(out);
                            if (outProgressionModel != null) {
                                outProgressionModel
                                        .setProgressionState(ProgressionState.IN_PROGRESS);
                                moreToDo = true;
                            }
                        } else {
                            ProgressionModel outProgressionModel =
                                    processProgression
                                            .getProgressionModelForDiagramObject(out);
                            if (outProgressionModel != null) {
                                outProgressionModel
                                        .setProgressionState(ProgressionState.COMPLETE);
                                moreToDo =
                                        completeTransition(outProgressionModel);
                            }
                        }
                    }
                }
            } else {
                /* Check for end of embedded sub-process. */
                if (activity.eContainer() instanceof ActivitySet) {
                    /*
                     * Check if this activity is the last to complete in emb
                     * subproc.
                     */
                    boolean lastInActSet = true;

                    ActivitySet actSet = (ActivitySet) activity.eContainer();

                    for (Activity sibling : actSet.getActivities()) {
                        if (sibling != activity) {
                            ProgressionModel siblingProgModel =
                                    processProgression
                                            .getProgressionModelForDiagramObject(sibling);
                            if (ProgressionState.IN_PROGRESS
                                    .equals(siblingProgModel
                                            .getProgressionState())) {
                                lastInActSet = false;
                                break;
                            }
                        }
                    }

                    if (lastInActSet) {
                        /* Also check in progress transitions. */
                        for (Transition transition : actSet.getTransitions()) {
                            ProgressionModel transitionProgModel =
                                    processProgression
                                            .getProgressionModelForDiagramObject(transition);
                            if (ProgressionState.IN_PROGRESS
                                    .equals(transitionProgModel
                                            .getProgressionState())) {
                                lastInActSet = false;
                                break;
                            }
                        }
                    }

                    if (lastInActSet) {
                        /*
                         * Add the parent emb subproc activity to list of things
                         * to process next time round.
                         */
                        Activity embSubProcActivity =
                                Xpdl2ModelUtil
                                        .getEmbSubProcActivityForActSet(activity
                                                .getProcess(),
                                                actSet.getId());

                        inProgressOrProcessedObjects.add(embSubProcActivity);

                        moreToDo = true;
                    }
                }
            }

            return moreToDo;
        }

        private boolean completeTransition(ProgressionModel progressionModel) {
            boolean moreToDo = false;

            Transition transition =
                    (Transition) progressionModel.getDiagramModelObject();

            inProgressOrProcessedObjects.remove(transition);

            progressionModel.setProgressionState(ProgressionState.COMPLETE);

            Activity targetActivity =
                    transition.getFlowContainer().getActivity(transition
                            .getTo());
            if (targetActivity != null) {
                if (!inProgressOrProcessedObjects.contains(targetActivity)) {

                    ProgressionModel tgtProgressionModel =
                            processProgression
                                    .getProgressionModelForDiagramObject(targetActivity);
                    if (tgtProgressionModel != null) {
                        tgtProgressionModel
                                .setProgressionState(ProgressionState.IN_PROGRESS);

                        if (targetActivity.getBlockActivity() != null) {
                            /*
                             * Deal with drill down into embedded sub-process.
                             */
                            ActivitySet set =
                                    targetActivity.getProcess()
                                            .getActivitySet(targetActivity
                                                    .getBlockActivity()
                                                    .getActivitySetId());

                            boolean childStartAdded = false;
                            for (Activity activity : set.getActivities()) {
                                if (activity.getIncomingTransitions().isEmpty()) {
                                    ProgressionModel childProgModel =
                                            processProgression
                                                    .getProgressionModelForDiagramObject(activity);
                                    if (childProgModel != null) {
                                        childStartAdded = true;
                                        childProgModel
                                                .setProgressionState(ProgressionState.IN_PROGRESS);
                                        inProgressOrProcessedObjects
                                                .add(activity);
                                    }
                                }
                            }

                            if (!childStartAdded) {
                                inProgressOrProcessedObjects
                                        .add(targetActivity);
                            }

                        } else {
                            inProgressOrProcessedObjects.add(targetActivity);
                        }

                        moreToDo = true;
                    }
                }
            }

            return moreToDo;
        }
    }

    /**
     * ProcessChangeListener
     * <p>
     * Listen for working copy change then popup a message explaining that the
     * progression must be stopped because model has changed.
     * 
     * @author aallway
     * @since 3.3 (2 Nov 2009)
     */
    private class ProcessChangeListener implements PropertyChangeListener {

        private boolean alreadyHere = false;

        public void propertyChange(PropertyChangeEvent evt) {
            if (alreadyHere) {
                return;
            }

            alreadyHere = true;

            Display.getDefault().asyncExec(new Runnable() {

                public void run() {

                    if (progressionRunning && !cancelled) {
                        stopProgression();
                        MessageDialog
                                .openWarning(Display.getDefault()
                                        .getActiveShell(),
                                        "Process Resource Changed", "The monitiored process resource has changed, progression terminated."); //$NON-NLS-1$//$NON-NLS-2$
                    }

                    alreadyHere = false;
                    return;
                }
            });
        }

    }

}
