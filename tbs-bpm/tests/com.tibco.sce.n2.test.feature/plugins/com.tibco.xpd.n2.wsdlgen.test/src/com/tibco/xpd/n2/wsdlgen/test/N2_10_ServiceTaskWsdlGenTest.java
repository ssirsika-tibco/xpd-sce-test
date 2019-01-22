/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.n2.wsdlgen.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.processeditor.xpdl2.preCommit.UpdateServiceTaskMappings;
import com.tibco.xpd.processeditor.xpdl2.util.WebServiceOperationUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.projectconfig.projectassets.util.ProjectAssetMigrationManager;
import com.tibco.xpd.resources.util.ProjectImporter;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.util.XpdConsts;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.extension.EMFSearchUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * This tests verifies the WSDL generated for a Service task. The Service task
 * WSDL is generated using the Generate button on the properties section of the
 * Web Service task.
 * 
 * A system participant is assigned to the web service task, and the XPDL is
 * checked so that there are no error markers.
 * 
 * This assures us that the mappings have been generated correctly for the
 * Web-Service task.
 * 
 * @author rsomayaj
 * @since 3.3 (19 Jul 2010)
 */
public class N2_10_ServiceTaskWsdlGenTest extends AbstractWSDLGenForNonAPICompareTest {

    /**
     * @see junit.framework.TestCase#setUp()
     * 
     * @throws Exception
     */
    @Override
    protected void setUp() throws Exception {
        List<String> urls = new ArrayList<String>();
        urls.add("resources/XPD923/Prj1.zip"); //$NON-NLS-1$
        urls.add("resources/XPD923/Prj1CompareWsdl.zip"); //$NON-NLS-1$

        // Import project XPD-365 into the workspace.
        // Import project ComparingProject into workspace.
        ProjectImporter createPluginProjectImporter =
                ProjectImporter
                        .createPluginProjectImporter(WSDLGenTestPluginActivator.PLUGIN_ID,
                                urls);
        IProject project =
                ResourcesPlugin.getWorkspace().getRoot().getProject("Prj1"); //$NON-NLS-1$
        ProjectAssetMigrationManager.getInstance().migrate(project,
                new NullProgressMonitor());
        assertTrue(createPluginProjectImporter.performImport());
        TestUtil.buildAndWait();

        // Kapil: check if the project requires migration, if yes then migrate
        // the project build it and then continue with the test.
        IMarker[] markers =
                project.findMarkers(XpdConsts.PROJECT_MIGRATION_MARKER_TYPE,
                        false,
                        IResource.DEPTH_ZERO);

        if (markers != null) {
            ProjectAssetMigrationManager.getInstance().migrate(project,
                    new NullProgressMonitor());
        }
        TestUtil.buildAndWait();

    }

    public void testServiceTaskWsdlGeneration() throws Exception {
        IFile xpdlFile = getXpdlFileTested();

        assertNotNull("Xpdl file", xpdlFile); //$NON-NLS-1$
        assertTrue("Xpdl File Accessible", xpdlFile.isAccessible()); //$NON-NLS-1$

        WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(xpdlFile);

        assertNotNull(workingCopy);

        EObject rootElement = workingCopy.getRootElement();

        assertTrue("rootelement package", rootElement instanceof Package); //$NON-NLS-1$

        com.tibco.xpd.xpdl2.Package xpdlPackage =
                (com.tibco.xpd.xpdl2.Package) rootElement;
        Process process = xpdlPackage.getProcesses().get(0);

        assertNotNull("Process", process); //$NON-NLS-1$

        EObject objectInList =
                EMFSearchUtil.findInList(process.getActivities(),
                        Xpdl2Package.eINSTANCE.getNamedElement_Name(),
                        "ServiceTask"); //$NON-NLS-1$

        assertTrue("ServiceTask not found", objectInList instanceof Activity); //$NON-NLS-1$

        Activity serviceTaskActivity = (Activity) objectInList;
        runWizard(workingCopy, serviceTaskActivity);

        compareWsdls(process.getName() + "-" + serviceTaskActivity.getName()); //$NON-NLS-1$

        // AssignParticipant

        assignParticipant(workingCopy, xpdlPackage, serviceTaskActivity);

        TestUtil.buildAndWait();

        TestUtil.delay(3000);
        List<IMarker> errorMarkers = TestUtil.getErrorMarkers(xpdlFile);

        assertTrue("Errors exist" + TestUtil.markersToString(errorMarkers), //$NON-NLS-1$
                errorMarkers.isEmpty());

    }

    /**
     * @param workingCopy
     * @param xpdlPackage
     * @param serviceTaskActivity
     * @throws IOException
     */
    private void assignParticipant(WorkingCopy workingCopy,
            com.tibco.xpd.xpdl2.Package xpdlPackage,
            Activity serviceTaskActivity) throws IOException {
        CompoundCommand cmd = new CompoundCommand("Assign Participant"); //$NON-NLS-1$
        EObject participantEObject = getSystemParticipant(xpdlPackage);

        assertTrue("Participant valid", //$NON-NLS-1$
                participantEObject instanceof Participant);

        Participant participant = (Participant) participantEObject;

        WebServiceOperation webServiceOperation =
                WebServiceOperationUtil
                        .getWebServiceOperation(serviceTaskActivity);

        cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(workingCopy
                .getEditingDomain(),
                webServiceOperation,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_Alias(),
                participant.getId()));

        assertTrue("Setting participant", cmd.canExecute()); //$NON-NLS-1$

        workingCopy.getEditingDomain().getCommandStack().execute(cmd);

        workingCopy.save();
    }

    /**
     * @param xpdlPackage
     * @return
     */
    private EObject getSystemParticipant(Package xpdlPackage) {
        return EMFSearchUtil.findInList(xpdlPackage.getParticipants(),
                Xpdl2Package.eINSTANCE.getNamedElement_Name(),
                "httpParticipant"); //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.n2.wsdlgen.test.AbstractWSDLGenForNonAPICompareTest#getGeneratedFile(org.eclipse.core.runtime.IPath)
     * 
     * @param wsdlFilePath
     * @return
     */
    @Override
    protected IFile getGeneratedFile(IPath wsdlFilePath) {
        IPath path = new Path("Prj1/Service Descriptors").append(wsdlFilePath); //$NON-NLS-1$
        return ResourcesPlugin.getWorkspace().getRoot().getFile(path);
    }

    /**
     * @see com.tibco.xpd.n2.wsdlgen.test.AbstractWSDLGenForNonAPICompareTest#getGoldFile(org.eclipse.core.runtime.IPath)
     * 
     * @param wsdlFilePath
     * @return
     */
    @Override
    protected IFile getGoldFile(IPath wsdlFilePath) {
        IPath path =
                new Path("Prj1CompareWsdl/Service Descriptors") //$NON-NLS-1$
                        .append(wsdlFilePath);
        return ResourcesPlugin.getWorkspace().getRoot().getFile(path);
    }

    /**
     * @see com.tibco.xpd.n2.wsdlgen.test.AbstractWSDLGenForNonAPICompareTest#getXpdlFileTested()
     * 
     * @return
     */
    @Override
    protected IFile getXpdlFileTested() {
        return ResourcesPlugin.getWorkspace().getRoot()
                .getFile(new Path("/Prj1/Process Packages/P11.xpdl")); //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.n2.wsdlgen.test.AbstractWSDLGenForNonAPICompareTest#getUpdateMappingsCommand(com.tibco.xpd.resources.WorkingCopy,
     *      com.tibco.xpd.xpdl2.Activity)
     * 
     * @param workingCopy
     * @param activity
     * @return
     */
    @Override
    protected Command getUpdateMappingsCommand(WorkingCopy workingCopy,
            Activity activity) {
        return new UpdateServiceTaskMappings(workingCopy.getEditingDomain(),
                activity);
    }
}
