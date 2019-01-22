/* 
 ** 
 **  MODULE:             $RCSfile: SimCaseGenerator.java $ 
 **                      $Revision: 1.0 $ 
 **                      $Date: 2005-09-16 $ 
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
package com.tibco.xpd.simulation.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Date;
import java.util.Iterator;

import com.tibco.xpd.simulation.preprocess.Case;
import com.tibco.xpd.simulation.preprocess.CaseGenerator;
import com.tibco.xpd.xpdl2.Process;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.SimTime;

public class SimRealDataCaseGenerator extends SimProcess {

    // TODO Temporary file
    private static final String TEST_REALDATA_FILE = "P1/Simulation/test.realdata"; //$NON-NLS-1$

    /**
     * There will be maximaly generated MAX_NO_OF_GEN_CASES if no of cases is
     * bigger this value then cases will be generated with this period.
     */
    private static final int MAX_NO_OF_GEN_CASES = 100000;

    private final int noOfCases;

    private final boolean showInTrace;

    private final CaseGenerator caseGenerator;

    private final String packageName;

    private final String processName;

    private int generatedCases = 0;

    private PropertyChangeSupport propChangeSupport = new PropertyChangeSupport(
            this);

    private final String eventId;

    /**
     * The constructor.
     * 
     * @param owner
     * @param name
     * @param showInTrace
     * @param noOfCases
     *            number of cases 0 means endless loop
     * @param process
     *            XPDL process.
     * @param eventId
     *            id of event (activity id)
     */
    public SimRealDataCaseGenerator(Model owner, String name,
            boolean showInTrace, int noOfCases, Process process,
            String packageName, String processName, String eventId) {
        super(owner, name, showInTrace);
        WorkflowModel model = (WorkflowModel) owner;
        this.eventId = eventId;
        if (noOfCases < 0) {
            throw new IllegalArgumentException("Number of cases must be >= 0"); //$NON-NLS-1$
        }
        this.showInTrace = showInTrace;

        this.caseGenerator = model.getStartEventCaseGenerator(this.eventId);

        this.noOfCases = model.getNoOfCasesForStartEvent(this.eventId);
            
        this.packageName = packageName;
        this.processName = processName;
    }

    public void lifeCycle() {

        // get a reference to the model
        WorkflowModel model = (WorkflowModel) getModel();
        Iterator iter = caseGenerator.iterator();
        Date currentDate = null;
        Date caseDate = null;

        if (iter.hasNext()) {

            Case caseWithParams = (Case) iter.next();
            currentDate = caseWithParams.getCaseStartDate();

            caseWithParams.setPackageName(packageName);
            caseWithParams.setProcessName(processName);
            SimCase simCase = new SimCase(model, "SimCase", showInTrace, //$NON-NLS-1$
                    caseWithParams);

            // now let the newly created SimCase start
            // which means we will activate it after this SimCase generator
            simCase.activateAfter(this);
            incrementGeneratedCases();
        }
        while (generatedCases < noOfCases && iter.hasNext()) {
            // create a new case

            Case caseWithParams = (Case) iter.next();
            caseDate = caseWithParams.getCaseStartDate();
            caseWithParams.setPackageName(packageName);
            caseWithParams.setProcessName(processName);
            SimCase simCase = new SimCase(model, "SimCase", showInTrace, //$NON-NLS-1$
                    caseWithParams);

            double timeInterval = getTimeIntervalInUnits(currentDate, caseDate);
            // wait until next generation
            hold(new SimTime(timeInterval));

            // now let the newly created SimCase start
            // which means we will activate it after this SimCase generator
            simCase.activateAfter(this);
            incrementGeneratedCases();

        }
        
    }

    public int getGeneratedCases() {
        return generatedCases;
    }

    private void incrementGeneratedCases() {
        generatedCases++;
        propChangeSupport.firePropertyChange("generatedCases", new Integer( //$NON-NLS-1$
                generatedCases - 1), new Integer(generatedCases));
    }

    public synchronized void addPropertyChangeListener(
            PropertyChangeListener listener) {
        propChangeSupport.addPropertyChangeListener(listener);
    }

    public synchronized void addPropertyChangeListener(String propertyName,
            PropertyChangeListener listener) {
        propChangeSupport.addPropertyChangeListener(propertyName, listener);
    }

    public synchronized void removePropertyChangeListener(
            PropertyChangeListener listener) {
        propChangeSupport.removePropertyChangeListener(listener);
    }

    public synchronized void removePropertyChangeListener(String propertyName,
            PropertyChangeListener listener) {
        propChangeSupport.removePropertyChangeListener(propertyName, listener);
    }

    private double getTimeIntervalInUnits(Date startDate, Date endDate) {
        long timeIntervalMilis = endDate.getTime() - startDate.getTime();
        return ((double) timeIntervalMilis) / 1000 / 60;
    }
}
