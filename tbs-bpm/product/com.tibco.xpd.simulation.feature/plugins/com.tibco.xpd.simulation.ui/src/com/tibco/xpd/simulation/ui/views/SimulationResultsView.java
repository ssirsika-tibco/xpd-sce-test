package com.tibco.xpd.simulation.ui.views;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import com.tibco.simulation.report.SimRepExperimentState;
import com.tibco.simulation.report.SimRepPackage;
import com.tibco.simulation.report.provider.SimRepItemProviderAdapterFactory;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.EObjectInclusionFilter;
import com.tibco.xpd.simulation.launch.LaunchPlugin;
import com.tibco.xpd.simulation.launch.ReportManager;
import com.tibco.xpd.simulation.launch.SimulationControler;
import com.tibco.xpd.simulation.launch.SimulationEventKeys;

/**
 * Display view for the results of a simulation. The view is split into a tree
 * showing the available data types and a table showing the data for the type
 * currently selected in the tree.
 * 
 * @author NWilson
 */
public class SimulationResultsView extends ViewPart implements
        PropertyChangeListener {
    private EObjectTreeViewer viewer;
    private SimulationControler simulationControler;

    public void createPartControl(Composite parent) {
        SimRepItemProviderAdapterFactory factory = new SimRepItemProviderAdapterFactory();

        HashSet inclusions = new HashSet();
        inclusions.add(SimRepPackage.eINSTANCE.getSimRepActivities());
        inclusions.add(SimRepPackage.eINSTANCE.getSimRepCases());
        inclusions.add(SimRepPackage.eINSTANCE.getSimRepParticipants());
        inclusions.add(SimRepPackage.eINSTANCE.getSimRepCost());

        EObjectInclusionFilter filter = new EObjectInclusionFilter(inclusions);
        viewer = new EObjectTreeViewer(parent, factory);
        viewer.addTreeFilter(filter);

        simulationControler = LaunchPlugin.getSimulationControler();
        ReportManager reportManager = simulationControler.getReportManager();
        if (reportManager != null) {
            viewer.setInput(reportManager.getSimRepExperiment());
        }
        simulationControler.addListener(this);
    }

    public void dispose() {
        simulationControler.removeListener(this);
        super.dispose();
    }

    public void setFocus() {
    }

    /**
     * TODO comment me!
     * 
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
     */
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        if (propertyName.equalsIgnoreCase(SimulationEventKeys.EXPERIMENT_STATE)) {
            SimRepExperimentState newState = (SimRepExperimentState) evt
                    .getNewValue();
            String newStateName = newState.getName();
            if (newStateName
                    .equalsIgnoreCase(SimulationEventKeys.SIMULATION_STATE_RUNNING)) {
                ReportManager reportManager = simulationControler
                        .getReportManager();
                if (reportManager != null) {
                    viewer.setInput(reportManager.getSimRepExperiment());
                }
            }
        }
    }
}
