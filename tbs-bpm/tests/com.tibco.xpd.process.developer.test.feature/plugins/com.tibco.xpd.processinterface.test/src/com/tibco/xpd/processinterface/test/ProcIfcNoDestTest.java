/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.processinterface.test;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.util.ProjectImporter;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.extension.EMFSearchUtil;

import junit.framework.TestCase;

/**
 * 
 * 
 * @author rsomayaj
 * @since 3.3 (12 Feb 2010)
 */
public class ProcIfcNoDestTest extends TestCase {

    private IProject project;

    /**
     * This test is to ensure that there are no validation problem markers on
     * project with no destinations and process interfaces with no destinations.
     */
    public void testNoErrors() {

        IFile simpleProcIfcsFile =
                project.getFile(new Path("Process Packages/SimpleProcIfcs.xpdl")); //$NON-NLS-1$
        assertTrue("XPDL Exists", simpleProcIfcsFile.exists()); //$NON-NLS-1$
        TestUtil.buildAndWait();
        List<IMarker> errorMarkers =
                TestUtil.getErrorMarkers(simpleProcIfcsFile);

        if (!errorMarkers.isEmpty()) {
            System.out.println("Error Markers::" //$NON-NLS-1$
                    + TestUtil.markersToString(errorMarkers));
        }
        TestUtil.buildAndWait();
        assertTrue("There shouldn't be errors", errorMarkers.isEmpty()); //$NON-NLS-1$
    }

    /**
	 * Sid SCF-565 diabled test function - it hangs and implementation was never completed originally...
	 * 
	 * This tests implements the proc ifc "a. InterfaceWithoutParams" using the wizard so that has the implementing
	 * activities.
	 */
	// public void testImplementProcessInterface() {
	// IFile simpleProcIfcs =
	// project.getFile(new Path("Process Packages/SimpleProcIfcs.xpdl")); //$NON-NLS-1$
	// WorkingCopy xpdlWorkingCopy =
	// WorkingCopyUtil.getWorkingCopy(simpleProcIfcs);
	// if (null != xpdlWorkingCopy) {
	//
	// /*
	// * XPD-5179: Saket- Check if the project requires migration. If yes,
	// * then migrate the project build it and then continue with the
	// * test.
	// */
	//
	// try {
	// if (project
	// .findMarkers(XpdConsts.PROJECT_MIGRATION_MARKER_TYPE,
	// false,
	// IResource.DEPTH_ZERO) != null) {
	// ProjectAssetMigrationManager.getInstance().migrate(project,
	// true,
	// new NullProgressMonitor());
	// }
	// } catch (CoreException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// }
	//
	// TestUtil.buildAndWait();
	//
	// EObject rootElement = xpdlWorkingCopy.getRootElement();
	// assertTrue("Root element is Package", //$NON-NLS-1$
	// rootElement instanceof com.tibco.xpd.xpdl2.Package);
	//
	// ProcessInterface procIfc =
	// getProcIfcFromPackage((com.tibco.xpd.xpdl2.Package) rootElement,
	// "aInterfaceWithoutParams"); //$NON-NLS-1$
	// assertTrue("Process Interface found", procIfc != null); //$NON-NLS-1$
	//
	// NewBusinessProcessWizard newBusinessProcessWizard =
	// new NewBusinessProcessWizard();
	// newBusinessProcessWizard.init(PlatformUI.getWorkbench(),
	// new StructuredSelection(procIfc));
	// WizardDialog dialog =
	// new WizardDialog(PlatformUI.getWorkbench()
	// .getActiveWorkbenchWindow().getShell(),
	// newBusinessProcessWizard);
	// dialog.create();
	// assertTrue(newBusinessProcessWizard.performFinish());
	// // TODO - Check whether the process contains activities that
	// // implement the
	// // process interface
	// }
	//
	// }

    /**
     * @param xpdlPackage
     * @param procIfcName
     * @return
     */
    private ProcessInterface getProcIfcFromPackage(Package xpdlPackage,
            String procIfcName) {
        ProcessInterfaces processInterfaces =
                ProcessInterfaceUtil.getProcessInterfaces(xpdlPackage);
        if (null != processInterfaces) {
            EObject procIfcObj =
                    EMFSearchUtil.findInList(processInterfaces
                            .getProcessInterface(), Xpdl2Package.eINSTANCE
                            .getNamedElement_Name(), procIfcName);
            if (null != procIfcObj) {
                return (ProcessInterface) procIfcObj;
            }
        }

        return null;
    }

    /**
     * @see junit.framework.TestCase#setUp()
     * 
     * @throws Exception
     */
    @Override
    protected void setUp() throws Exception {
        ProjectImporter projectImporter =
                ProjectImporter
                        .createPluginProjectImporter(ProcessInterfaceTestPlugin.PLUGIN_ID,
                                Collections
                                        .singletonList("resources/ProjectWithNoDest/")); //$NON-NLS-1$

        boolean projectImported = projectImporter.performImport();
        assertTrue("Project Imported:", projectImported); //$NON-NLS-1$
        project =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getProject("ProjectWithNoDest"); //$NON-NLS-1$
    }

    /**
     * @see junit.framework.TestCase#tearDown()
     * 
     * @throws Exception
     */
    @Override
    protected void tearDown() throws Exception {
        if (null != project) {
            project.delete(true, new NullProgressMonitor());
        }
    }
}
