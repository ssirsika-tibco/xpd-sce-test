/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.simulation.test;

import java.util.Arrays;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;

import com.tibco.xpd.resources.util.ProjectImporter;

/**
 * Test simulation of a simple XPDL process.
 * 
 * @author Jan Arciuchiewicz
 */
public class SimpleSimulationTest extends BaseSimulationTest {

    private ProjectImporter projectImporter;

    @Override
    protected void setUp() throws Exception {
        projectImporter =
                ProjectImporter
                        .createPluginProjectImporter(SimulationTestActivator.PLUGIN_ID,
                                Arrays
                                        .asList("resources/SimpleSimulationTest.zip")); //$NON-NLS-1$
        assertTrue(projectImporter.performImport());
    }

    @Override
    protected void tearDown() throws Exception {
        assertTrue(projectImporter.performDelete());
    }

    public void testSimpleSimulation() throws Exception {
        long timeout = 30L * 60L * 1000L; // 30 min.
        simulateProcess("SimpleSimulationTest/Process Packages/ProcessPackage.xpdl", //$NON-NLS-1$
                "ProcessPackageProcess", //$NON-NLS-1$
                timeout);

        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        // make sure that simulation folder is in sync with workspace.
        root.getProject("SimpleSimulationTest") //$NON-NLS-1$
                .refreshLocal(IResource.DEPTH_INFINITE,
                        new NullProgressMonitor());

        IFolder folder =
                root
                        .getFolder(new Path(
                                "SimpleSimulationTest/Simulation/ProcessPackage/ProcessPackageProcess")); //$NON-NLS-1$
        IFile resultFile = getNewestFile(folder);

        assertNotNull(resultFile);
        IFile goldResult =
                root.getFile(new Path("SimpleSimulationTest/GoldResult.sim")); //$NON-NLS-1$

        assertTrue(goldResult.exists());

        assertTrue(equals(goldResult, resultFile));

    }
}
