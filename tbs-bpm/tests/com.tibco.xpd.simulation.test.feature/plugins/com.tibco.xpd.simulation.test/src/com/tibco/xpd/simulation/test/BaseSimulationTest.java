/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.simulation.test;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunchConfiguration;

import com.tibco.simulation.report.SimRepExperimentState;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.simulation.launch.LaunchPlugin;
import com.tibco.xpd.simulation.launch.SimulationControler;
import com.tibco.xpd.simulation.launch.SimulationEventKeys;
import com.tibco.xpd.simulation.launch.SimulationLaunchConfigurationDelegate;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;

/**
 * 
 * @author Jan Arciuchiewicz
 */
public abstract class BaseSimulationTest extends TestCase {

    private volatile boolean started = false;

    private volatile boolean finished = false;

    /**
     * No-argument constructor to enable serialization. This method is not
     * intended to be used by mere mortals without calling setName().
     */
    public BaseSimulationTest() {
        super();
    }

    /**
     * Constructs a test case with the given name.
     */
    public BaseSimulationTest(String name) {
        super(name);
    }

    protected PropertyChangeListener simListener =
            new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent evt) {
                    String propertyName = evt.getPropertyName();
                    if (propertyName
                            .equalsIgnoreCase(SimulationEventKeys.EXPERIMENT_STATE)) {
                        // simulation state changed
                        SimRepExperimentState newStateName =
                                (SimRepExperimentState) evt.getNewValue();
                        if (newStateName
                                .getName()
                                .equalsIgnoreCase(SimulationEventKeys.SIMULATION_STATE_ABORTED)) {
                            finish();
                        } else if (newStateName
                                .getName()
                                .equalsIgnoreCase(SimulationEventKeys.SIMULATION_STATE_FINISHED)) {
                            finish();
                        } else if (newStateName
                                .getName()
                                .equalsIgnoreCase(SimulationEventKeys.SIMULATION_STATE_NOT_STARTED)) {
                        } else if (newStateName
                                .getName()
                                .equalsIgnoreCase(SimulationEventKeys.SIMULATION_STATE_PAUSED)) {
                        } else if (newStateName
                                .getName()
                                .equalsIgnoreCase(SimulationEventKeys.SIMULATION_STATE_RUNNING)) {
                            start();
                        }
                    }
                }

            };

    /**
     * Starts and runs simulation of the process. The method will wait until
     * simulation finishes. Clients also provide timeout and if simulation takes
     * more then timeout the {@link SimulationTimeoutException} will be thrown.
     * 
     * @param packageFullPath
     *            the workspace path to the xpdl file.
     * @param processName
     *            the name of the process.
     * @param simulationTimeout
     *            the max time in miliseconds that simulation can take.
     * @throws SimulationTimeoutException
     *             if the simulation is taken more time than indicated in the
     *             simulationTimeout parameter.
     */
    protected void simulateProcess(final String packageFullPath,
            final String processName, final long simulationTimeout)
            throws Exception {
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        final IFile file =
                workspace.getRoot().getFile(new Path(packageFullPath));
        assertTrue(file.exists());
        final Process[] process = new Process[1];
        workspace.run(new IWorkspaceRunnable() {
            public void run(IProgressMonitor monitor) throws CoreException {
                WorkingCopy wc =
                        XpdResourcesPlugin.getDefault().getWorkingCopy(file);
                assertNotNull(wc);
                TestUtil.migratePackage(wc);
                com.tibco.xpd.xpdl2.Package xpdlPackage =
                        (Package) wc.getRootElement();
                assertNotNull(xpdlPackage);

                BaseSimulationTest.getProcessByName(xpdlPackage, processName);
                // get first (and only process)
                process[0] = xpdlPackage.getProcesses().get(0);
            }
        }, new NullProgressMonitor());

        SimulationControler simulationControler =
                LaunchPlugin.getSimulationControler();
        simulationControler.setDelay(5);
        simulationControler.setReferenceTime(new Date(1252666856122L));
        simulationControler.addListener(simListener);

        new SimulationLaunchConfigurationDelegate() {
            @Override
            public void runSimulation(IFile xpdlFile, Process xpdlProcess,
                    ILaunchConfiguration config) throws CoreException {
                super.runSimulation(xpdlFile, xpdlProcess, config);
            }
        }.runSimulation(file, process[0], null);
        waitForSimulationStartAndEnd(1000, 600000, simulationTimeout);
    }

    /**
     * Gets process by its name.
     * 
     * @param xpdlPackage
     *            xpdl package containing processes.
     * @param processName
     *            the name of the process to obtain.
     * @return the process with the provided name or <code>null</code> if not
     *         found.
     */
    protected static Process getProcessByName(Package xpdlPackage,
            String processName) {
        for (Process p : xpdlPackage.getProcesses()) {
            if (p.equals(processName))
                return p;
        }
        return null;

    }

    private void finish() {
        finished = true;
    }

    private void start() {
        started = true;
    }

    /**
     * Wait for simulation to start and finish.
     */
    @SuppressWarnings("nls")
    protected void waitForSimulationStartAndEnd(long interval,
            long prepareDelayTimeout, long runDelayTimeout) {
        long prepareEndTimeout =
                System.currentTimeMillis() + prepareDelayTimeout;
        while (System.currentTimeMillis() < prepareEndTimeout) {
            TestUtil.delay(interval);
            if (started)
                break;
        }
        if (System.currentTimeMillis() >= prepareEndTimeout) {
            throw new SimulationTimeoutException(String
                    .format("Simulation prepare timeout (%d s) exceeded.",
                            prepareDelayTimeout / 1000));
        }

        long runEndTimeout = System.currentTimeMillis() + runDelayTimeout;
        while (System.currentTimeMillis() < runEndTimeout) {
            TestUtil.delay(interval);
            if (started && finished)
                break;
        }
        if (System.currentTimeMillis() >= runEndTimeout) {
            throw new SimulationTimeoutException(String
                    .format("Simulation run timeout (%d s) exceeded.",
                            runDelayTimeout / 1000));
        }

    }

    /**
     * Does line by line comparison.
     * 
     * @param goldResult
     *            gold file.
     * @param resultFile
     *            result file.
     * @return 'true' if files are equal.
     */
    protected boolean equals(IFile goldResult, IFile resultFile)
            throws CoreException, IOException {
        String charsetName = "ASCII"; //$NON-NLS-1$
        BufferedReader goldIr = null, resultIr = null;
        try {
            goldIr =
                    new BufferedReader(new InputStreamReader(goldResult
                            .getContents(), charsetName));
            resultIr =
                    new BufferedReader(new InputStreamReader(resultFile
                            .getContents(), charsetName));
            String goldLine = null;
            while ((goldLine = goldIr.readLine()) != null) {
                String resultLine = resultIr.readLine();
                if (!goldLine.equals(resultLine)) {
                    return false;
                }
            }
            return resultIr.readLine() == null;
        } finally {
            if (goldIr != null) {
                goldIr.close();
            }
            if (resultIr != null) {
                resultIr.close();
            }
        }
    }

    /**
     * Obtains the most recently created file in the folder.
     * 
     * @param folder
     *            the folder to look into.
     * @return the most recently created file in the folder or null if there is
     *         no file.
     * @throws CoreException
     */
    protected IFile getNewestFile(IFolder folder) throws CoreException {
        IFile newest = null;
        for (IResource r : folder.members()) {
            if (r instanceof IFile) {
                IFile f = ((IFile) r);
                if (newest == null
                        || f.getLocalTimeStamp() > newest.getLocalTimeStamp()) {
                    newest = f;
                }
            }
        }
        return newest;
    }

}