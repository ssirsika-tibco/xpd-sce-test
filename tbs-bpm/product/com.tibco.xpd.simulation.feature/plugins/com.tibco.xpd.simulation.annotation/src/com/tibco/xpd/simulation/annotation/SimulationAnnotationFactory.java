/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.annotation;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.tibco.simulation.report.SimRepActivities;
import com.tibco.simulation.report.SimRepActivity;
import com.tibco.simulation.report.SimRepExperiment;
import com.tibco.simulation.report.SimRepExperimentState;
import com.tibco.simulation.report.SimRepParticipant;
import com.tibco.simulation.report.SimRepParticipants;
import com.tibco.simulation.report.provider.SimRepItemProviderAdapterFactory;
import com.tibco.xpd.processwidget.annotations.AnnotationFactory;
import com.tibco.xpd.processwidget.annotations.AnnotationListener;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.simulation.ActivitySimulationDataType;
import com.tibco.xpd.simulation.ParticipantSimulationDataType;
import com.tibco.xpd.simulation.common.util.SimulationXpdlUtils;
import com.tibco.xpd.simulation.launch.LaunchPlugin;
import com.tibco.xpd.simulation.launch.ReportManager;
import com.tibco.xpd.simulation.launch.SimulationControler;
import com.tibco.xpd.simulation.launch.SimulationEventKeys;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Process;

/**
 * Implementaion AnnotationFactory that facilitate two way communication between
 * a background processing and visualization of it on the process widgets.
 * 
 * @author wzurek
 */
public class SimulationAnnotationFactory implements AnnotationFactory,
        PropertyChangeListener, IPerspectiveListener, CommandStackListener {

    /** The item provider adapter factory. */
    private SimRepItemProviderAdapterFactory factory =
            new SimRepItemProviderAdapterFactory();

    /** True if annotaions are visible. */
    private boolean isVisible = true;

    /** Annotation listener list. */
    private Map<AnnotationListener, List<IFigure>> listeners =
            new HashMap<AnnotationListener, List<IFigure>>();

    /** The simulation controller. */
    private SimulationControler simulationControler;

    /** The report manager. */
    private ReportManager reportManager;

    /** Activity annotators. */
    private Map<SimRepActivity, SimRepActivityAnnotator> activityAnnotators =
            new HashMap<SimRepActivity, SimRepActivityAnnotator>();

    /** The editing domain to monitor for changes. */
    private EditingDomain domain;

    /**
     * Constructor.
     */
    public SimulationAnnotationFactory() {
        simulationControler = LaunchPlugin.getSimulationControler();
        simulationControler.addListener(this);
        PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                .addPerspectiveListener(this);
    }

    /**
     * The widget is ready to show annotation.
     * 
     * @param listener
     *            The listener to register.
     */
    @Override
    public void registerListener(AnnotationListener listener) {
        listeners.put(listener, new ArrayList<IFigure>());
    }

    /**
     * The widget dont wont to show annotation anymore.
     * 
     * @param listener
     *            The listener to unregister.
     */
    @Override
    public void unregisterListener(AnnotationListener listener) {
        listener.removeAnnotations();
        listeners.remove(listener);
    }

    /**
     * The widget ask for the annotation for particular model object.
     * 
     * @param listener
     *            The listener to add.
     * @param model
     *            The model object.
     * @param parent
     *            The parent figure.
     * @return The figure.
     */
    @Override
    public IFigure createFigure(AnnotationListener listener, Object model,
            IFigure parent) {
        IFigure fig = null;
        if (isVisible) {
            if (model instanceof EObject) {
                EditingDomain ed =
                        WorkingCopyUtil.getEditingDomain((EObject) model);
                if (domain != ed && ed != null) {
                    if (domain != null) {
                        domain.getCommandStack()
                                .removeCommandStackListener(this);
                    }
                    domain = ed;
                    domain.getCommandStack().addCommandStackListener(this);
                }
            }
            if (model instanceof Activity) {
                Activity activity = (Activity) model;
                SimRepExperiment experiment = getExperiment(activity);
                if (experiment != null) {
                    SimRepActivity simActivity =
                            getSimRepActivity(experiment, activity);
                    SimRepParticipant simParticipant =
                            getSimRepParticipant(experiment, activity);
                    ActivitySimulationDataType activitySimulation =
                            SimulationXpdlUtils
                                    .getActivitySimulationData(activity);
                    Process process = (Process) activity.eContainer();

                    Performer performer = null;
                    EList performers = activity.getPerformerList();

                    if (performers != null && performers.size() > 0) {
                        performer = (Performer) performers.get(0);
                    }

                    ParticipantSimulationDataType participantSimualtion = null;
                    if (performer != null) {
                        Participant participant;
                        participant =
                                SimulationXpdlUtils.findParticipant(process,
                                        performer.getValue());
                        participantSimualtion =
                                SimulationXpdlUtils
                                        .getParticipantSimulationData(participant);
                    }
                    if (simActivity != null && simParticipant != null) {
                        SimRepActivityAnnotator annotator;
                        if (activityAnnotators.containsKey(simActivity)) {
                            annotator = activityAnnotators.get(simActivity);
                        } else {
                            int caseCount = reportManager.getTotalCaseCount();
                            annotator =
                                    new SimRepActivityAnnotator(simActivity,
                                            simParticipant, activitySimulation,
                                            participantSimualtion, factory,
                                            caseCount, parent);
                            activityAnnotators.put(simActivity, annotator);
                        }
                        fig = annotator.getFigure();
                        List<IFigure> figures = listeners.get(listener);
                        figures.add(fig);

                    }
                }
            }

        } else {
            throw new IllegalStateException();
        }
        return fig;
    }

    /**
     * @param activity
     *            The activity.
     * @return The associated experiment.
     */
    private SimRepExperiment getExperiment(Activity activity) {
        SimRepExperiment confirmedExperiment = null;
        if (reportManager != null) {
            SimRepExperiment experiment = reportManager.getSimRepExperiment();
            if (experiment != null) {
                String simProcessId = experiment.getProcessId();
                Process process = (Process) activity.eContainer();
                if (process.getId().equals(simProcessId)) {
                    confirmedExperiment = experiment;
                }
            }
        }
        return confirmedExperiment;
    }

    /**
     * @param experiment
     *            The experiment.
     * @param activity
     *            The activity.
     * @return The simulation report participant for the activity.
     */
    private SimRepParticipant getSimRepParticipant(SimRepExperiment experiment,
            Activity activity) {
        SimRepParticipant simRepParticipant = null;
        SimRepParticipants participants = experiment.getParticipants();

        Performer performer = null;
        EList performers = activity.getPerformerList();

        if (performers != null && performers.size() > 0) {
            performer = (Performer) performers.get(0);
        }

        if (performer != null) {
            String participantId = performer.getValue();
            if (participants != null && participantId != null) {
                EList participantList = participants.getParticipant();
                for (Iterator i = participantList.iterator(); i.hasNext();) {
                    SimRepParticipant simParticipant =
                            (SimRepParticipant) i.next();
                    if (participantId.equals(simParticipant.getId())) {
                        simRepParticipant = simParticipant;
                    }
                }
            }
        }
        return simRepParticipant;
    }

    /**
     * @param experiment
     *            The experiment.
     * @param activity
     *            The activity.
     * @return The simulation report activity.
     */
    private SimRepActivity getSimRepActivity(SimRepExperiment experiment,
            Activity activity) {
        SimRepActivity simRepActivity = null;
        SimRepActivities activities = experiment.getActivities();
        if (activities != null) {
            EList activityList = activities.getActivity();
            for (Iterator i = activityList.iterator(); i.hasNext();) {
                SimRepActivity simActivity = (SimRepActivity) i.next();
                if (simActivity.getId().equals(activity.getId())) {
                    simRepActivity = simActivity;
                }
            }
        }
        return simRepActivity;
    }

    /**
     * Repaints figures.
     */
    private void updateFigures() {
        for (List<IFigure> figures : listeners.values()) {
            for (IFigure figure : figures) {
                if (figure instanceof TaskAnnotationFigure) {
                    TaskAnnotationFigure taskFig =
                            (TaskAnnotationFigure) figure;
                    taskFig.repaint();
                }
            }
        }
    }

    /**
     * Called when the simulation starts.
     */
    private void started() {
        reportManager = simulationControler.getReportManager();
        Display display = PlatformUI.getWorkbench().getDisplay();
        if (!display.isDisposed()) {
            display.syncExec(new Runnable() {
                @Override
                public void run() {
                    isVisible = true;
                    for (Iterator iter = listeners.keySet().iterator(); iter
                            .hasNext();) {
                        AnnotationListener lst =
                                (AnnotationListener) iter.next();
                        lst.createAnnotations();
                    }
                    updateFigures();
                }
            });
        }
    }

    /**
     * Called when the simulation stops.
     */
    private void finished() {
        if (domain != null) {
            // The command stack listener needs to be removed during an
            // asynchronous on the Display thread.[Was being done on the command
            // stack notify change]
            Display display = PlatformUI.getWorkbench().getDisplay();
            if (!display.isDisposed()) {
                final EditingDomain tmpDomain = domain;
                display.asyncExec(new Runnable() {
                    @Override
                    public void run() {
                        tmpDomain
                                .getCommandStack()
                                .removeCommandStackListener(SimulationAnnotationFactory.this);
                    }
                });
            }
            domain = null;
        }
        Display display = PlatformUI.getWorkbench().getDisplay();
        if (!display.isDisposed()) {
            display.syncExec(new Runnable() {
                @Override
                public void run() {
                    isVisible = false;
                    for (Iterator i = activityAnnotators.values().iterator(); i
                            .hasNext();) {
                        SimRepActivityAnnotator annotator =
                                (SimRepActivityAnnotator) i.next();
                        annotator.dispose();
                        i.remove();
                    }
                    for (Iterator iter = listeners.keySet().iterator(); iter
                            .hasNext();) {
                        AnnotationListener lst =
                                (AnnotationListener) iter.next();
                        lst.removeAnnotations();
                        ((List) listeners.get(lst)).clear();
                    }
                }
            });
        }
        reportManager = null;
    }

    /**
     * @param evt
     *            The change event.
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        if (propertyName.equalsIgnoreCase(SimulationEventKeys.EXPERIMENT_STATE)) {
            SimRepExperimentState newState =
                    (SimRepExperimentState) evt.getNewValue();
            String newStateName = newState.getName();
            if (newStateName
                    .equalsIgnoreCase(SimulationEventKeys.SIMULATION_STATE_RUNNING)) {
                finished();
                started();

            }
        }
    }

    /**
     * @param page
     *            The workbench page.
     * @param perspective
     *            The perspective.
     * @see org.eclipse.ui.IPerspectiveListener#perspectiveActivated(org.eclipse.ui.IWorkbenchPage,
     *      org.eclipse.ui.IPerspectiveDescriptor)
     */
    @Override
    public void perspectiveActivated(IWorkbenchPage page,
            IPerspectiveDescriptor perspective) {
        finished();
    }

    /**
     * @param page
     *            The workbench page.
     * @param perspective
     *            The perspective.
     * @param changeId
     *            The change id.
     * @see org.eclipse.ui.IPerspectiveListener#perspectiveChanged(org.eclipse.ui.IWorkbenchPage,
     *      org.eclipse.ui.IPerspectiveDescriptor, java.lang.String)
     */
    @Override
    public void perspectiveChanged(IWorkbenchPage page,
            IPerspectiveDescriptor perspective, String changeId) {
    }

    /**
     * @param event
     *            The change event.
     * @see org.eclipse.emf.common.command.CommandStackListener#
     *      commandStackChanged(java.util.EventObject)
     */
    @Override
    public void commandStackChanged(EventObject event) {
        finished();
    }

    @Override
    public void disposeFactory() {
        finished();
    }

}
