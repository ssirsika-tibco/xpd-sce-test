/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.n2.wsdlgen.test;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;
import org.eclipse.wst.validation.ValidationFramework;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.core.test.util.WSDLFileComparator;
import com.tibco.xpd.core.test.validations.AbstractBaseValidationTest;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.processeditor.xpdl2.util.DeleteActivityCommand;
import com.tibco.xpd.processeditor.xpdl2.wizards.NewBusinessProcessWizard;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.projectassets.util.ProjectAssetMigrationManager;
import com.tibco.xpd.resources.util.ProjectImporter;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.extension.EMFSearchUtil;

/**
 * 
 * The objective of this test is to ensure the WSDL generated for the XPDL is
 * correct. However, the test also ensures that the process has the required
 * validation rules.
 * 
 * Testing project which has BPM destination switched on.
 * 
 * 
 * 
 * @author rsomayaj
 * @since 3.3 (15 Feb 2010)
 */
public class N2_03_ProcIfcBPMDestTest extends AbstractBaseValidationTest {

    private IProject project;

    private com.tibco.xpd.xpdl2.Process proc;

    private boolean seq1 = false;

    private boolean seq2 = false;

    public void testProcessNoParamsWSDLValidation() throws Exception {
        TestUtil.buildAndWait();
        TransactionalEditingDomain editingDomain =
                XpdResourcesPlugin.getDefault().getEditingDomain();
        // Get the WSDL file
        IFile generatedWSDL =
                project.getFile(new Path(
                        "Generated Services/ProcIfcNoParams.wsdl")); //$NON-NLS-1$
        // Assert that it does not validate properly

        assertTrue("WSDL File is created", generatedWSDL.exists()); //$NON-NLS-1$
        ValidationFramework.getDefault().validate(generatedWSDL,
                new NullProgressMonitor());

        List<IMarker> genWSDLErrorMarkers =
                TestUtil.getErrorMarkers(generatedWSDL);

        TestUtil.markersToString(genWSDLErrorMarkers);
        assertTrue("There should be errors", !(genWSDLErrorMarkers.isEmpty())); //$NON-NLS-1$

        // TODO - doTestValidation to see that the error -
        // "Events with faults need to be request response"
        seq1 = true;

        // Implement the processinterface using the process wizard
        IFile procIfcXpdl =
                project.getFile(new Path(
                        "Process Packages/ProcIfcNoParams.xpdl")); //$NON-NLS-1$

        WorkingCopy xpdlWorkingCopy =
                WorkingCopyUtil.getWorkingCopy(procIfcXpdl);

        TestUtil.delay(10000);
        TestUtil.getErrorMarkers(procIfcXpdl);
        System.out.println("After 1st validation test"); //$NON-NLS-1$
        doTestValidations();

        if (null != xpdlWorkingCopy) {
            EObject rootElement = xpdlWorkingCopy.getRootElement();
            assertTrue("Root element is Package", //$NON-NLS-1$
                    rootElement instanceof com.tibco.xpd.xpdl2.Package);
            com.tibco.xpd.xpdl2.Package xpdlPackage =
                    (com.tibco.xpd.xpdl2.Package) rootElement;
            ProcessInterface procIfc =
                    getProcIfcFromPackage(xpdlPackage,
                            "aInterfaceWithoutParams"); //$NON-NLS-1$
            assertTrue("Process Interface found", procIfc != null); //$NON-NLS-1$

            NewBusinessProcessWizard newBusinessProcessWizard =
                    new NewBusinessProcessWizard();
            newBusinessProcessWizard.init(PlatformUI.getWorkbench(),
                    new StructuredSelection(procIfc));
            WizardDialog dialog =
                    new WizardDialog(PlatformUI.getWorkbench()
                            .getActiveWorkbenchWindow().getShell(),
                            newBusinessProcessWizard);
            dialog.create();
            assertTrue(newBusinessProcessWizard.performFinish());

            TestUtil.buildAndWait();

            proc = getProcess(xpdlPackage, "ProcIfcNoParamsProcess"); //$NON-NLS-1$
            assertNotNull(proc);

            Activity activity = getActivity(proc, "cStartMsgEventWithoutError"); //$NON-NLS-1$
            assertNotNull(activity);

            DeleteActivityCommand deleteActivityCommand =
                    new DeleteActivityCommand(editingDomain, activity);

            if (deleteActivityCommand.canExecute()) {
                editingDomain.getCommandStack().execute(deleteActivityCommand);
            }

            TestUtil.buildAndWait();
            xpdlWorkingCopy.save();

            TestUtil.buildAndWait();
            TestUtil.delay(7000);
            seq2 = true;
            seq1 = false;

            doTestValidations();

            // compare the WSDLs
            IFile goldFile =
                    project.getFile(new Path(
                            "Service Descriptors/ProcIfcNoParamsGOLD.wsdl")); //$NON-NLS-1$

            assertNotNull(goldFile);
            assertTrue(goldFile.exists());
            WSDLFileComparator wsdlFileComparator = new WSDLFileComparator();

            IStatus status =
                    wsdlFileComparator.compareContents(goldFile.getContents(),
                            generatedWSDL.getContents(),
                            null);

            // If status is null , then files are similar

            TestUtil.delay(1000);
            // WSDL File are still showing up different.
            if (status != null) {
                fail("Maybe because of namespace prefix differences:" //$NON-NLS-1$
                        + status.getMessage());
            }
            assertNull("Status null is no difference", status); //$NON-NLS-1$

        }

    }

    /**
     * @throws Exception
     */
    public void testSimpleTypeWSDLGen() throws Exception {
        TestUtil.buildAndWait();
        IFile generatedWSDL =
                project.getFile(new Path(
                        "Generated Services/SimpleTypesProcIfcTest.wsdl")); //$NON-NLS-1$

        assertNotNull("WSDL File is generated", generatedWSDL); //$NON-NLS-1$
        assertTrue("WSDL File exists", generatedWSDL.exists()); //$NON-NLS-1$

        IFile goldWSDL =
                project.getFile(new Path(
                        "Service Descriptors/SimpleTypesProcIfcTestGOLD.wsdl")); //$NON-NLS-1$

        assertNotNull("GOLD WSDL File is not null", goldWSDL); //$NON-NLS-1$
        assertTrue("GOLD WSDL File exists", goldWSDL.exists()); //$NON-NLS-1$

        WSDLFileComparator wsdlComparator = new WSDLFileComparator();
        IStatus status =
                wsdlComparator.compareContents(goldWSDL.getContents(),
                        generatedWSDL.getContents(),
                        null);

        if (status != null) {
            fail("Maybe because of differences in namespace prefixes::" //$NON-NLS-1$
                    + status.getMessage());
        } else {
            assertNull("Status null is no difference", status); //$NON-NLS-1$
        }

    }

    /**
     * @param proc
     * @param activityName
     * @return
     */
    private Activity getActivity(com.tibco.xpd.xpdl2.Process proc,
            String activityName) {
        EObject object =
                EMFSearchUtil.findInList(proc.getActivities(),
                        Xpdl2Package.eINSTANCE.getNamedElement_Name(),
                        activityName);
        if (object instanceof Activity) {
            Activity act = (Activity) object;
            return act;
        }
        return null;
    }

    /**
     * @param xpdlPackage
     * @param string
     */
    private com.tibco.xpd.xpdl2.Process getProcess(Package xpdlPackage,
            String procName) {
        EObject object =
                EMFSearchUtil.findInList(xpdlPackage.getProcesses(),
                        Xpdl2Package.eINSTANCE.getNamedElement_Name(),
                        procName);
        if (object instanceof com.tibco.xpd.xpdl2.Process) {
            return (com.tibco.xpd.xpdl2.Process) object;
        }
        return null;
    }

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
                        .createPluginProjectImporter(WSDLGenTestPluginActivator.PLUGIN_ID,
                                Collections
                                        .singletonList("resources/BPMSimpleTypesProcIfc/")); //$NON-NLS-1$

        boolean projectImported = projectImporter.performImport();
        assertTrue("Project Imported:", projectImported); //$NON-NLS-1$
        project =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getProject("BPMSimpleTypesProcIfc"); //$NON-NLS-1$
        ProjectAssetMigrationManager.getInstance().migrate(project,
                new NullProgressMonitor());
        super.assertTrue("Project Doesn't exist", project.exists()); //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.core.test.validations.AbstractBaseValidationTest#getValidationProblemMarkerInfos()
     * 
     * @return
     */
    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {

        // need switches to return the right validationtestproblemmarkerinfos

        if (seq1) {
            ValidationsTestProblemMarkerInfo[] markerInfos =
                    new ValidationsTestProblemMarkerInfo[] {

                            new ValidationsTestProblemMarkerInfo(
                                    "/BPMSimpleTypesProcIfc/Process Packages/ProcIfcNoParams.xpdl", //$NON-NLS-1$ 
                                    "bpmn.dev.ifcErrResponseRequired", //$NON-NLS-1$ 
                                    "_BtvHEhobEd-KR4rH3IKjZA", //$NON-NLS-1$ 
                                    "BPMN : Event needs to be request response for it to have errors.(Adding parameters to the process interface would resolve the problem) (dIntermediateMsgEventWithErr)", //$NON-NLS-1$
                                    ""), //$NON-NLS-1$ 

                            new ValidationsTestProblemMarkerInfo(
                                    "/BPMSimpleTypesProcIfc/Process Packages/ProcIfcNoParams.xpdl", //$NON-NLS-1$ 
                                    "bpmn.dev.ifcErrResponseRequired", //$NON-NLS-1$ 
                                    "_BtvHChobEd-KR4rH3IKjZA", //$NON-NLS-1$ 
                                    "BPMN : Event needs to be request response for it to have errors.(Adding parameters to the process interface would resolve the problem) (dStartMsgEventWithError)", //$NON-NLS-1$ 
                                    ""), //$NON-NLS-1$ 

                            new ValidationsTestProblemMarkerInfo(
                                    "/BPMSimpleTypesProcIfc/Process Packages/ProcIfcNoParams.xpdl", //$NON-NLS-1$ 
                                    "bpmn.dev.ifcErrResponseRequired", //$NON-NLS-1$ 
                                    "_BtvHCBobEd-KR4rH3IKjZA", //$NON-NLS-1$ 
                                    "BPMN : Event needs to be request response for it to have errors.(Adding parameters to the process interface would resolve the problem) (bStartNoneEventWithError)", //$NON-NLS-1$ 
                                    ""), //$NON-NLS-1$ 

                            new ValidationsTestProblemMarkerInfo(
                                    "/BPMSimpleTypesProcIfc/Process Packages/ProcIfcNoParams.xpdl", //$NON-NLS-1$ 
                                    "bpmn.processinterface.messageEventParam", //$NON-NLS-1$ 
                                    "_BtvHBBobEd-KR4rH3IKjZA", //$NON-NLS-1$ 
                                    "BPMN : Process Interface containing Events of trigger type Message should probably have at least one Formal Parameter. (aInterfaceWithoutParams)", //$NON-NLS-1$ 
                                    ""), //$NON-NLS-1$ 

                    };
            return markerInfos;
        } else if (seq2) {
            ValidationsTestProblemMarkerInfo[] markerInfos =
                    new ValidationsTestProblemMarkerInfo[] {

                    new ValidationsTestProblemMarkerInfo(
                            "/BPMSimpleTypesProcIfc/Process Packages/ProcIfcNoParams.xpdl", //$NON-NLS-1$ 
                            "bpmn.processOutOfSyncProcIfc", //$NON-NLS-1$ 
                            "_kKKqkBr6Ed-hCtUFoT1B4A", //$NON-NLS-1$ 
                            "BPMN : Process is out of sync with the implementing process interface. (ProcIfcNoParamsProcess)", //$NON-NLS-1$ 
                            "Synchronize process with process interface."), //$NON-NLS-1$ ), //$NON-NLS-1$ 

                    };
            return markerInfos;
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#getTestName()
     * 
     * @return
     */
    @Override
    protected String getTestName() {
        return "BPMProjProcIfcNoParamsTest"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#getTestPlugInId()
     * 
     * @return
     */
    @Override
    protected String getTestPlugInId() {
        return WSDLGenTestPluginActivator.PLUGIN_ID;
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#getTestResources()
     * 
     * @return
     */
    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] { new TestResourceInfo(
                        "resources/BPMSimpleTypesProcIfc", "BPMSimpleTypesProcIfc/Process Packages{processes}/ProcIfcNoParams.xpdl") //$NON-NLS-1$ //$NON-NLS-2$
                };

        return testResources;
    }

    @Override
    protected void tearDown() throws Exception {
        project.close(null);
        TestUtil.removeProject(project.getName());
        return;
    }
}
