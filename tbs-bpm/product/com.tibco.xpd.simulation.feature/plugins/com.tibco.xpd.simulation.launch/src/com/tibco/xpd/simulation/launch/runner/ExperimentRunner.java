/* 
 ** 
 **  MODULE:             $RCSfile: ExperimentRunner.java $ 
 **                      $Revision: 1.0 $ 
 **                      $Date: 2005-10-04 $ 
 ** 
 **  DESCRIPTION:           
 **                                              
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc, All Rights Reserved.
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */
package com.tibco.xpd.simulation.launch.runner;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ILock;
import org.eclipse.core.runtime.jobs.Job;

import com.tibco.xpd.simulation.launch.ExperimentParamConstants;
import com.tibco.xpd.simulation.launch.LaunchPlugin;
import com.tibco.xpd.simulation.launch.Messages;
import com.tibco.xpd.simulation.model.WorkflowModel;

import desmoj.core.report.Reporter;
import desmoj.core.simulator.Condition;
import desmoj.core.simulator.Entity;
import desmoj.core.simulator.Experiment;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.Reportable;
import desmoj.core.simulator.SimTime;
import desmoj.core.util.ExperimentListener;
import desmoj.core.util.ExperimentParameter;
import desmoj.core.util.SimClockListener;
import desmoj.core.util.SimRunEvent;
import desmoj.core.util.SimRunListener;

/**
 * This class define an eclipse Job a DesmoJ-Experiment can run in. The
 * experiment runner can notifiy registered ExperimentListeners when the
 * assigned experiment is (re)started, stopped or temporarily paused and
 * registered SimClockListeners when the simulation clock is set forth.
 * 
 * This class was based on DESMO-J example ExperimentRunner by Nicolas Knaak
 * 
 */
public class ExperimentRunner implements Observer {

    private static final String NULL_OUTPUT_TYPE = "desmoj.core.report.NullOutput"; //$NON-NLS-1$
    
    /** Status: model not connected to experiment */
    public static final int CREATED = 0;

    /** Status: Model created and connected but experiment not started yet */
    public static final int INITIALIZED = 1;

    /** Status: Experiment currently running */
    public static final int RUNNING = 2;

    /** Status: Experiment temporarily paused */
    public static final int PAUSED = 3;

    /** Status: Experiment finally stopped */
    public static final int STOPPED = 4;

    /** The start time for the experiment running in this thread */
    private SimTime startTime;

    /** The stop time specified in the experiment options */
    private SimTime stopTime;

    /** The real start time as specified on the controller. */
    private Date realStartTime;

    /** The experiment to be run */
    private Experiment experiment;

    /** Vector of listeners to the current experiment's running status changes */
    private Vector experimentListeners = null;

    /** Vector of listeners to the contained experiment's SimClock */
    private Vector simClockListeners = null;

    /** The contained experiment's status */
    private volatile int status;

    /** Flag indicating if a report is to be drawn after the experiment stopped */
    private boolean reportIsOn = true;

    /**
     * Millisecond part of delay between steps of the scheduler. Necessary for
     * online observation of experiments .
     */
    private long delayMillis = 0;

    /**
     * Nanosecond part of delay between steps of the scheduler. Necessary for
     * online observation of experiments.
     */
    private int delayNanos = 0;

    /**
     * This flag is set to true if the delay time between 2 steps of the
     * scheduler is not 0.
     */
    private boolean hasDelay = false;

    /** A semaphore used to interactively suspend and resume this experiment */
    ILock sem = Platform.getJobManager().newLock();

    /** The eclipse job the experiment should run in */
    private Job simJob;

    /** Model parameters */
    private Map modelParams;

    /** Experiment parameter names and values */
    private Map expSettings;

    /** SimRunEvent sent to all listeners of current experiment */
    private SimRunEvent simRunEvent;

    private class SimulationJob extends Job {
        private int startedCases = 0;

        private int finishedCases = 0;

        public SimulationJob() {
            super(Messages.ExperimentRunner_Simulation_label);
        }

        protected IStatus run(final IProgressMonitor monitor) {
            try {
                final WorkflowModel workflowModel = (WorkflowModel) experiment
                        .getModel();
                workflowModel.setRealStartTime(realStartTime);
                workflowModel.setTimeUnit(experiment.getReferenceUnit());
                workflowModel
                .addPropertyChangeListener(new PropertyChangeListener() {
                    public void propertyChange(PropertyChangeEvent event) {
                        String propertyName = event.getPropertyName();
                        if (WorkflowModel.STARTED_CASES_PROPERTY
                                .equals(propertyName)) {
                            startedCases = ((Integer) event
                                    .getNewValue()).intValue();
                            monitor.subTask(Messages.ExperimentRunner_Started1
                                    + startedCases
                                    + Messages.ExperimentRunner_Finished1
                                    + finishedCases);
                        }
                        if (WorkflowModel.FINISHED_CASES_PROPERTY
                                .equals(propertyName)) {
                            finishedCases = ((Integer) event
                                    .getNewValue()).intValue();
                            monitor.subTask(Messages.ExperimentRunner_Started2
                                    + startedCases
                                    + Messages.ExperimentRunner_Finished2
                                    + finishedCases);
                            monitor.worked(1);
                        }
                    }
                });
                
                //  adding stop confition.
                experiment.stop(new Condition(experiment.getModel(),
                        Messages.ExperimentRunner_Stop, false) {
                    public boolean check() {
                        return (workflowModel.getFinishedCaseCount() >= workflowModel
                                .getNoOfCases())
                                || (!workflowModel.hasActivities());
                    }

                    public boolean check(Entity e) {
                        return check();
                    }

                });

                if (startTime != null)
                    experiment.start(startTime);
                else
                    experiment.start();
                
                int noOfCases = workflowModel.getNoOfCases();
                monitor.beginTask(Messages.ExperimentRunner_Simulation_label,
                        noOfCases);                
                
                while (status != STOPPED) {
                    sem.acquire();// sem.P();
                    sem.release();// sem.V();
                    if (status == STOPPED)
                        continue;
                    SimTime currentTime = experiment.getModel().currentTime();
                    if (experiment.isAborted()
                            || monitor.isCanceled()
                            || workflowModel.getFinishedCaseCount() >= noOfCases) {
                        setStatus(STOPPED);
                    } else
                        experiment.proceed();
                }
                finishExperiment();

            } catch (RuntimeException e) {
                e.printStackTrace();
                return new Status(IStatus.CANCEL, LaunchPlugin.ID,
                        IStatus.CANCEL, e.getMessage() == null ? "null" : e //$NON-NLS-1$
                                .getMessage(), e);
            } catch (Exception e) {
                return new Status(IStatus.ERROR, LaunchPlugin.ID, IStatus.OK, e
                        .getMessage() == null ? "null" : e.getMessage(), e); //$NON-NLS-1$
            } finally {
                monitor.done();
            }
            return Status.OK_STATUS;
        }

    }

    public int getStatus() {
        return status;
    }

    /**
     * An inner class providing the experiment runner's report. The report
     * contains all model parameter and experiment settings of the current
     * experiment
     */
    public static class ReportProvider extends Reportable {

        private ExperimentRunner runner;

        public ReportProvider(ExperimentRunner runner) {
            super(runner.model, "ExperimentRunner", true, false); //$NON-NLS-1$
            this.runner = runner;
        }

        public Reporter createReporter() {
            return null; // new ExperimentParameterReporter(this);
        }

        public ExperimentRunner getExperimentRunner() {
            return runner;
        }
    }

    /** An object providing the experiment runner's report. */
    private Reportable reportProvider;

    /** The model currently active */
    private Model model;

    /** Creates a new experiment runner for the given model */
    public ExperimentRunner(Model model) {
        this.model = model;
        this.simJob = new SimulationJob();
        this.startTime = null;
        this.stopTime = null;
        this.status = CREATED;

        if (model != null) {
            // modelParams = model.createParameters();
            modelParams = new HashMap();
            expSettings = createExperimentParameters();
        }
    }

    /**
     * Creates a new experiment runner that is not connected to a model yet. The
     * model must be set consequently using the <code>setModel()</code>
     * method.
     */
    public ExperimentRunner() {
        this(null);
    }

    /**
     * Sets the current model
     * 
     * @param model
     *            a desmoj.Model
     */
    public void setModel(Model model) {
        this.model = model;
        // modelParams = model.createParameters();
        expSettings = createExperimentParameters();
    }

    /** @return model running in this experiment runner */
    public Model getModel() {
        return model;
    }

    /** Initializes the experiment before it is run in the thread */
    public void init() {
        try {
            reportProvider = new ReportProvider(this);
            experiment = createExperiment();
            experiment.getSimClock().addObserver(this);
            registerMessageReceivers();
            simRunEvent = new SimRunEvent(experiment);
            this.status = INITIALIZED;
        } catch (Exception e) {
            System.err.println("Exception was thrown during experiment runner init! " + e); //$NON-NLS-1$
            e.printStackTrace();
            this.status = STOPPED;
        }
    }

    /** @return the experiment running in this experiment runner */
    public Experiment getExperiment() {
        return experiment;
    }

    /** @return the SimTime the current experiment starts at */
    public SimTime getStartTime() {
        return startTime;
    }

    /** @return the time the current experiment will finally stop */
    public SimTime getStopTime() {
        return stopTime;
    }

    /** Starts this thread and the contained experiment with start time 0.0 */
    public void start() {
        if (status <= INITIALIZED) {
            sem.acquire(); // sem.P();
            if (status == CREATED) {
                init();
            }

            setStatus(RUNNING);
            simJob.schedule();
            sem.release(); // sem.V();
        }
    }

    /** Stops the experiment running in this experiment runner (forever). */
    public void stopExperiment() {
        if (status == RUNNING || status == PAUSED) {
            experiment.stop();
            if (status == PAUSED) {
                setStatus(STOPPED);
                sem.release();
            } else {
                setStatus(STOPPED);
            }

        }
    }

    /**
     * Lets the experiment report (if desired) and closes all output streams by
     * calling <code>Experiment.finish()</code>
     */
    public void finishExperiment() {
        if (reportIsOn)
            experiment.report();
        experiment.finish();
    }

    /**
     * This method is implemented for interface observer. It is called by the
     * assigned experiment's SimClock every time it's value is increased. The
     * methods tests and waits for the runners semaphore. If a delay time is
     * set, the runner sleeps for the given delay time afterwards.
     * 
     * @param o
     *            an observable object
     * @param value
     *            the updated value
     */
    public void update(Observable o, Object value) {
        if (simClockListeners != null) {
            for (Iterator i = simClockListeners.iterator(); i.hasNext();) {
                SimClockListener l = (SimClockListener) i.next();
                l.clockAdvanced(simRunEvent);
            }
        }
        if (hasDelay) {
            try {
                Thread.sleep(delayMillis, delayNanos);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * (Un)pauses running the assigned experiment by requiring and releasing the
     * semaphore tested in the experiments proceed loop.
     * 
     * @param pause
     *            boolean value indicating pause (<code>true</code>) or
     *            restart (<code>false</code>).
     */
    public void setPaused(boolean pause) {
        if (status == RUNNING && pause) {
            sem.acquire();// sem.P();
            experiment.stop();
            setStatus(PAUSED);
        } else if (status == PAUSED && !pause) {
            setStatus(RUNNING);
            sem.release(); // sem.V();
        }
    }

    /**
     * Returns the millisecond part of the delay between each step of the
     * scheduler
     * 
     * @return A long value representing the delay time in milliseconds
     */
    public long getDelayMillis() {
        return delayMillis;
    }

    /**
     * Returns the nanosecond part of the delay between each step of the
     * scheduler
     * 
     * @return A long value representing the delay time in milliseconds
     * 
     */
    public int getDelayNanos() {
        return delayNanos;
    }

    /**
     * Sets the delay between each step of the scheduler.
     * 
     * @param delay:
     *            Delay time in milliseconds as a long value
     */
    public void setDelay(long millis, int nanos) {
        delayMillis = millis;
        delayNanos = nanos;
        if (millis + nanos != 0)
            hasDelay = true;
        else
            hasDelay = false;
    }

    /** Adds a listener to the contained experiment's running status */
    public void addExperimentListener(ExperimentListener l) {
        if (experimentListeners == null)
            experimentListeners = new Vector();
        if (!experimentListeners.contains(l))
            experimentListeners.add(l);
    }

    /** Removes an experiment listener */
    public void removeExperimentListener(ExperimentListener l) {
        if (experimentListeners != null)
            experimentListeners.remove(l);
    }

    /** Adds a listener to the contained experiment's sim clock */
    public void addSimClockListener(SimClockListener l) {
        if (simClockListeners == null)
            simClockListeners = new Vector();
        if (!simClockListeners.contains(l))
            simClockListeners.add(l);
    }

    /** Removes a SimClock listener */
    public void removeSimClockListener(SimClockListener l) {
        if (simClockListeners != null)
            simClockListeners.remove(l);
    }

    /**
     * adds a new SimRunListener. Reacts only on SimClockListener and
     * ExperimentListener objects. This method is called from
     * ExperimentRunner.setListeners().
     * 
     * @author Ruth Meyer
     */
    public void addSimRunListener(SimRunListener l) {
        // can only handle SimClock- and ExperimentListener
        if (l instanceof SimClockListener) {
            addSimClockListener((SimClockListener) l);
        }
        if (l instanceof ExperimentListener) {
            addExperimentListener((ExperimentListener) l);
        }
    }

    /** Sets the experiment's status and notifies registered ExperimentListeners */
    protected void setStatus(int status) {
        this.status = status;
        if (experimentListeners != null) {
            SimRunEvent e = new SimRunEvent(this.getExperiment());
            for (Iterator i = experimentListeners.iterator(); i.hasNext();) {
                ExperimentListener l = (ExperimentListener) i.next();
                switch (this.status) {
                case RUNNING:
                    l.experimentRunning(e);
                    break;
                case STOPPED:
                    l.experimentStopped(e);
                    break;
                case PAUSED:
                    l.experimentPaused(e);
                    break;
                }
            }
        }
    }

    /**
     * Returns an array of model parameters for this experiment run The first
     * row contains names, the second row contains values.
     */
    // public Object[][] getModelParameterArray() {
    // Object[][] mp = new Object[2][modelParams.size()];
    // if (modelParams != null) {
    // mp[0] = modelParams.getProbeNames();
    // mp[1] = modelParams.getProbeValues();
    // }
    // return mp;
    // }
    /**
     * Returns an array of experiment settings for this experiment run The first
     * row contains names, the second row contains values.
     */
    // public Object[][] getExperimentSettingsArray() {
    // Object[][] ep = new Object[2][expSettings.size()];
    // if (expSettings != null) {
    // ep[0] = expSettings.getProbeNames();
    // ep[1] = expSettings.getProbeValues();
    // }
    // return ep;
    // }
    public Map getModelParameters() {
        return this.modelParams;
    }

    public Map getExperimentSettings() {
        return this.expSettings;
    }

    public Job getSimulationJob() {
        return simJob;
    }

    /**
     * creates and initializes an experiment with the parameters in expParams.
     * Connects the experiment to the model.
     */
    protected Experiment createExperiment() {
        Experiment e = null;
        String name = (String) (((ExperimentParameter) expSettings.get("name")) //$NON-NLS-1$
                .getValue());
        String outputPath = (String) (((ExperimentParameter) expSettings
                .get("outputPath")).getValue()); //$NON-NLS-1$
        String reportOutputType = (String) (((ExperimentParameter) expSettings
                .get(ExperimentParamConstants.EXP_R_OUTTYPE)).getValue());
        
        String traceOutputType = (String) (((ExperimentParameter) expSettings
                .get(ExperimentParamConstants.EXP_T_OUTTYPE)).getValue());
        String errorOutputType = (String) (((ExperimentParameter) expSettings
                .get(ExperimentParamConstants.EXP_E_OUTTYPE)).getValue());
        String debugOutputType = (String) (((ExperimentParameter) expSettings
                .get(ExperimentParamConstants.EXP_D_OUTTYPE)).getValue());
        boolean isTimed = ((Boolean)(((ExperimentParameter) expSettings
                .get("isTimed")).getValue())).booleanValue(); //$NON-NLS-1$
        if (isTimed) {
            String referenceTime = (String) (((ExperimentParameter) expSettings
                    .get("referenceTime")).getValue()); //$NON-NLS-1$
                    
            int referenceUnit = ((Integer) (((ExperimentParameter) expSettings
                    .get("referenceUnit")).getValue())).intValue(); //$NON-NLS-1$
                    
            e = new Experiment(name, outputPath, referenceTime, referenceUnit, reportOutputType,
                    traceOutputType, errorOutputType, debugOutputType);
        } else {
            e = new Experiment(name, outputPath, reportOutputType,
                    traceOutputType, errorOutputType, debugOutputType);
        }
        model.connectToExperiment(e);
        boolean randomizeConcurentEvents = ((Boolean)(((ExperimentParameter) expSettings
                .get("randomizeConcurrentEvents")).getValue())).booleanValue(); //$NON-NLS-1$
        if (randomizeConcurentEvents) {
            e.randomizeConcurrentEvents(true);
        }
        boolean showProgressBar = ((Boolean)(((ExperimentParameter) expSettings
                .get("showProgressBar")).getValue())).booleanValue(); //$NON-NLS-1$
        e.setShowProgressBar(showProgressBar);
       
        SimTime traceStartTime = (SimTime)(((ExperimentParameter) expSettings
                .get("traceStartTime")).getValue());  //$NON-NLS-1$
        SimTime traceStopTime = (SimTime)(((ExperimentParameter) expSettings
                .get("traceStopTime")).getValue()); //$NON-NLS-1$
        e.traceOn(traceStartTime);
        e.traceOff(traceStopTime);

        SimTime t = (SimTime)(((ExperimentParameter) expSettings
                .get("stopTime")).getValue());  //$NON-NLS-1$
        stopTime = t;
        e.stop(t);
        startTime = (SimTime)(((ExperimentParameter) expSettings
                .get("startTime")).getValue());  //$NON-NLS-1$
        return e;
    }

    /** @return a probe map of experiment settings */
    protected Map createExperimentParameters() {
        Map xp = new HashMap();
        xp.put(ExperimentParamConstants.EXP_NAME, new ExperimentParameter(
                ExperimentParamConstants.EXP_NAME, model.getName()
                        + Messages.ExperimentRunner_ProcessLastName_label));
        xp.put(ExperimentParamConstants.EXP_OUTPUT_PATH,
                new ExperimentParameter(
                        ExperimentParamConstants.EXP_OUTPUT_PATH, "./")); //$NON-NLS-1$
        xp.put(ExperimentParamConstants.EXP_IS_TIMED, new ExperimentParameter(
                ExperimentParamConstants.EXP_IS_TIMED, new Boolean(false)));
        xp.put(ExperimentParamConstants.EXP_REF_TIME, new ExperimentParameter(
                ExperimentParamConstants.EXP_REF_TIME, "1.1.1970 00:00:00")); //$NON-NLS-1$
        xp.put(ExperimentParamConstants.EXP_REF_UNIT, new ExperimentParameter(
                ExperimentParamConstants.EXP_REF_UNIT, new Integer(
                        desmoj.core.simulator.Units.MIN)));
        xp.put(ExperimentParamConstants.EXP_START_TIME,
                new ExperimentParameter(
                        ExperimentParamConstants.EXP_START_TIME, new SimTime(
                                0.0)));
        xp.put(ExperimentParamConstants.EXP_STOP_TIME, new ExperimentParameter(
                ExperimentParamConstants.EXP_STOP_TIME, new SimTime(0.0)));
        xp.put(ExperimentParamConstants.EXP_SHOW_PROG_BAR,
                new ExperimentParameter(
                        ExperimentParamConstants.EXP_SHOW_PROG_BAR,
                        new Boolean(false)));
        xp.put(ExperimentParamConstants.EXP_TRACE_START,
                new ExperimentParameter(
                        ExperimentParamConstants.EXP_TRACE_START, new SimTime(
                                0.0)));
        xp.put(ExperimentParamConstants.EXP_TRACE_STOP,
                new ExperimentParameter(
                        ExperimentParamConstants.EXP_TRACE_STOP, new SimTime(
                                0.0)));
        xp.put(ExperimentParamConstants.EXP_RAND_EVENTS,
                new ExperimentParameter(
                        ExperimentParamConstants.EXP_RAND_EVENTS, new Boolean(
                                false)));
        String nullOutpurType = NULL_OUTPUT_TYPE;
        xp.put(ExperimentParamConstants.EXP_R_OUTTYPE, new ExperimentParameter(
                ExperimentParamConstants.EXP_R_OUTTYPE, nullOutpurType));
        xp.put(ExperimentParamConstants.EXP_T_OUTTYPE, new ExperimentParameter(
                ExperimentParamConstants.EXP_T_OUTTYPE, nullOutpurType));
        xp.put(ExperimentParamConstants.EXP_E_OUTTYPE, new ExperimentParameter(
                ExperimentParamConstants.EXP_E_OUTTYPE, nullOutpurType));
        xp.put(ExperimentParamConstants.EXP_D_OUTTYPE, new ExperimentParameter(
                ExperimentParamConstants.EXP_D_OUTTYPE, nullOutpurType));
        return xp;
    }

    /** Registers new message receivers as output channels of the experiment */
    public void registerMessageReceivers() {
    }


    /**
     * Experiment is active when it was created, initialised, running, or pused.
     * It's not active when it was stopped.
     * 
     * @return true if active
     */
    public boolean isActive() {
        return (status == CREATED || status == INITIALIZED || status == RUNNING || status == PAUSED);

    }

    public Date getRealStartTime() {
        return realStartTime;
    }

    public void setRealStartTime(Date realStartTime) {
        this.realStartTime = realStartTime;
    }
}
