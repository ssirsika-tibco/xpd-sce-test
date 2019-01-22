/* 
 ** 
 **  MODULE:             $RCSfile: SimulationRunner.java $ 
 **                      $Revision: 1.0 $ 
 **                      $Date: 2005-09-28 $ 
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
package com.tibco.xpd.simulation.launch;

import java.beans.PropertyChangeListener;
import java.util.Date;

import org.eclipse.core.resources.IContainer;

import com.tibco.xpd.resources.WorkingCopy;

import desmoj.core.simulator.Model;

public interface SimulationControler {
    public void runSimulation(WorkingCopy xpdlFile, Model model);

    public void stopSimulation();

    public void pauseSimulation();

    public void resumeSimulation();

    public void setDelay(long millis);

    public long getDelay();

    public Date getRealTime();

    public void setReferenceTime(Date referenceTime);

    /**
     * Add simulation events listener
     * 
     * @param listener
     */
    public void addListener(PropertyChangeListener listener);

    /**
     * Remove simulation events listener.
     * 
     * @param listener
     */
    public void removeListener(PropertyChangeListener listener);

    /**
     * Experiment is active when it was created, initialised, running, or pused.
     * And not active when it was stopped.
     * 
     * @return true if active.
     */
    public boolean isActiveExperiment();

    public ReportManager getReportManager();

    public IContainer getSimulationFolder();

    public int getTotalNumberOfCases();
}
