/* 
 ** 
 **  MODULE:             $RCSfile: SimulationControler.java $ 
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

import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;

import com.tibco.simulation.report.SimRepCases;
import com.tibco.simulation.report.SimRepExperiment;
import com.tibco.simulation.report.SimRepExperimentData;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.simulation.SimulationConstants;
import com.tibco.xpd.simulation.launch.ExperimentParamConstants;
import com.tibco.xpd.simulation.launch.ReportManager;
import com.tibco.xpd.simulation.launch.SimulationControler;
import com.tibco.xpd.simulation.model.WorkflowModel;

import desmoj.core.simulator.Experiment;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimTime;
import desmoj.core.util.ExperimentParameter;

public class SimulationControlerImpl implements SimulationControler {

    ExperimentRunner expRunner;

    private SimulationEventNotificator eventNotificator;

    private long experimentalRunerDelay;

    SimTime currentSimulationTime;

    private Date referenceTime;

    private ReportManager reportManager;

    private IContainer simulationFolder;

    private int totalNoOfCases;

    /**
     * The constructor.
     */
    public SimulationControlerImpl() {
        eventNotificator = new SimulationEventNotificator();
        currentSimulationTime = new SimTime(0.0);        
    }

    public void runSimulation(final WorkingCopy wc, Model model) {

        // model.connectToExperiment(exp);
        if (isActiveExperiment()) {
            return;
        }
        expRunner = new ExperimentRunner();
        expRunner.setModel(model);
        expRunner.setDelay(experimentalRunerDelay, expRunner.getDelayNanos());
        Map expSettings = expRunner.getExperimentSettings();
        // expSettings.put(new ExperimentParameter("stopTime", new
        // SimTime(100000.0)));

        if (referenceTime == null) {
            referenceTime = Calendar.getInstance().getTime();
        }
        String currentTimeString = ExperimentParamConstants.simpleDateFormat.format(referenceTime);
        expSettings.put(ExperimentParamConstants.EXP_REF_TIME, new ExperimentParameter(
                ExperimentParamConstants.EXP_REF_TIME, currentTimeString));
        
        expSettings.put(ExperimentParamConstants.EXP_IS_TIMED, new ExperimentParameter(
                ExperimentParamConstants.EXP_IS_TIMED, new Boolean(true)));

        IContainer outputFolder = getReportOutputFolder((IFile) wc
                .getEclipseResources().get(0));
        simulationFolder = outputFolder;

        expSettings.put(ExperimentParamConstants.EXP_OUTPUT_PATH, new ExperimentParameter(
                ExperimentParamConstants.EXP_OUTPUT_PATH, outputFolder
                        .getLocation().toString()));

        expRunner.init();
        expRunner.setRealStartTime(referenceTime);

        reportManager = new ReportManagerImpl(expRunner);
        expRunner.addExperimentListener(reportManager);
        expRunner.addSimClockListener(reportManager);
        // exp.setShowProgressBar(false); // display a progress bar (or not)
        // exp.stop(new SimTime(1500)); // set end of simulation at 1500 time

        // units
        // exp.tracePeriod(new SimTime(0.0), new SimTime(100));
        // set the period of the
        // trace
        // exp.debugPeriod(new SimTime(0.0), new SimTime(50)); // and debug
        // output

        // exp.setDelayInMillis(10);
        // start the experiment at simulation time 0.0
        // exp.start();
        // it seems the only way to get know when simulation is finished is to
        // listen for event

        WorkflowModel workflowModel = (WorkflowModel) expRunner.getModel();
        this.totalNoOfCases = workflowModel.getNoOfCases();
        SimulationEventAdapter eventAdapter = new SimulationEventAdapter(this,
                eventNotificator);

        SimRepExperiment simRepExperiment = reportManager.getSimRepExperiment();
        SimRepExperimentData experimentData = simRepExperiment
                .getExperimentData();
        EList list = experimentData.eAdapters();
        list.add(eventAdapter);
        simRepExperiment.eAdapters().add(eventAdapter);
        SimRepCases cases = simRepExperiment.getCases();
        EList list2 = cases.eAdapters();
        list2.add(eventAdapter);

        expRunner.start();

        // --> now the simulation is running until it reaches its end criterion
        // ...
        // ...
        // <-- afterwards, the main thread returns here

        // generate the report (and other output files)
        // exp.report();

        // stop all threads still alive and close all output files
        // exp.finish();

        try {
            outputFolder.refreshLocal(IResource.DEPTH_INFINITE, null);
        } catch (CoreException e) {
            e.printStackTrace();
        }
    }

    public IContainer getSimulationFolder() {
        return simulationFolder;
    }

    private IContainer getReportOutputFolder(IFile xpdlFile) {
        IProject currentProject = xpdlFile.getProject();
        IFolder simFolder = currentProject
                .getFolder(SimulationConstants.SIMULATION_FOLDER);
        if (simFolder.exists()) {
            return simFolder;
        } else {
            try {
                simFolder.create(true, true, null);
                return simFolder;
            } catch (CoreException e) {
                return currentProject;
            }
        }
    }

    public void stopSimulation() {
        if (expRunner != null) {
            expRunner.stopExperiment();
        }
    }

    public void pauseSimulation() {
        if (expRunner != null) {
            expRunner.setPaused(true);
        }

    }

    public void resumeSimulation() {
        if (expRunner != null) {
            expRunner.setPaused(false);
        }

    }

    /**
     * Experiment is active when it was created, initialised, running, or pused.
     * And not active when it was stopped.
     * 
     * @return true if active
     */
    public boolean isActiveExperiment() {
        if (expRunner != null && expRunner.isActive()) {
            return true;
        }
        return false;
    }

    /**
     * TODO comment me!
     * 
     * @see com.tibco.xpd.simulation.launch.SimulationControler#setDelay(long,
     *      int)
     */
    public void setDelay(long millis) {
        experimentalRunerDelay = millis;
        if (expRunner != null) {
            // instead of setting constant value for nanos
            // respect already set
            int delayNanos = expRunner.getDelayNanos();
            expRunner.setDelay(experimentalRunerDelay, delayNanos);
        }
    }

    /**
     * TODO comment me!
     * 
     * @see com.tibco.xpd.simulation.launch.SimulationControler#getDelay()
     */
    public long getDelay() {
        long result;
        result = experimentalRunerDelay;
        return result;
    }

    /**
     * TODO comment me!
     * 
     * @see com.tibco.xpd.simulation.launch.SimulationControler#addListener(java.util.EventListener)
     */
    public synchronized void addListener(PropertyChangeListener listener) {
        if (listener == null) {
            throw new NullPointerException("You cannot add null listener."); //$NON-NLS-1$
        }
        eventNotificator.addListener(listener);
    }

    /**
     * TODO comment me!
     * 
     * @see com.tibco.xpd.simulation.launch.SimulationControler#removeListener(java.util.EventListener)
     */
    public synchronized void removeListener(PropertyChangeListener listener) {
        if (listener == null) {
            throw new NullPointerException("You cannot remove null listener."); //$NON-NLS-1$
        }
        eventNotificator.removeListener(listener);
    }

    /**
     * TODO comment me!
     * 
     * @see com.tibco.xpd.simulation.launch.SimulationControler#getRealTime()
     */
    public Date getRealTime() {
        Date result;
        Experiment experiment = expRunner.getExperiment();
        String trueTime = experiment.toTrueTime(currentSimulationTime);
       
        try {
            result = ExperimentParamConstants.simpleDateFormat.parse(trueTime);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * TODO comment me!
     * 
     * @see com.tibco.xpd.simulation.launch.SimulationControler#setReferenceTime(java.util.Date)
     */
    public void setReferenceTime(Date referenceTime) {
        this.referenceTime = referenceTime;
    }

    public SimRepExperiment getSimRepExperiment() {
        if (reportManager != null) {
            return reportManager.getSimRepExperiment();
        }
        return null;
    }

    public ReportManager getReportManager() {
        if (reportManager != null) {
            return reportManager;
        }
        return null;
    }

    public int getTotalNumberOfCases() {
        return this.totalNoOfCases;
    }
}
