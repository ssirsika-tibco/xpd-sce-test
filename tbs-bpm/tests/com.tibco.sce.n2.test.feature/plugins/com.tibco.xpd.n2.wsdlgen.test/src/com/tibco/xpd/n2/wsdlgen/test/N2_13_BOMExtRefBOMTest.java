/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.n2.wsdlgen.test;

import java.io.IOException;
import java.util.Collections;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.core.test.util.WSDLFileComparator;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.projectconfig.projectassets.util.ProjectAssetMigrationManager;
import com.tibco.xpd.resources.util.ProjectImporter;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.util.XpdConsts;

/**
 * This test is to see that the WSDL is updated when a BOM referred to by
 * another BOM which is referred to as a type in the process is changed.
 * 
 * For eg:
 * 
 * TestCCC.bom refers to TestBBB.BOM which refers to TestAAA.bom
 * 
 * Now MyXPDL.xpdl has a parameter of type CCls which is contained in
 * TestCCC.bom
 * 
 * and a wsdl MyXPDL.wsdl is generated.
 * 
 * When TestAAA.bom is modified, need to ensure that MyXPDL.wsdl is updated.
 * 
 * @author rsomayaj
 * @since 3.3 (15 Feb 2010)
 */
public class N2_13_BOMExtRefBOMTest extends TestCase {

    private IProject project;

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
                                        .singletonList("resources/XPD23Wsdlgen/")); //$NON-NLS-1$

        boolean projectImported = projectImporter.performImport();
        assertTrue("Project Imported:", projectImported); //$NON-NLS-1$
        project =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getProject("XPD23Wsdlgen"); //$NON-NLS-1$

        assertTrue("Project Doesn't exist", project.exists()); //$NON-NLS-1$
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

    /**
     * @see junit.framework.TestCase#tearDown()
     * 
     * @throws Exception
     */
    @Override
    protected void tearDown() throws Exception {
        if (null != project) {
            project.delete(true, new NullProgressMonitor());
            TestUtil.waitForJobs(300);
        }
    }

    /**
     * @throws CoreException
     * @throws IOException
     */
    public void testProcessWSDLGenerated() throws CoreException, IOException {

        WSDLFileComparator comparator = new WSDLFileComparator();
        comparator.setTestFileContent(true);
        IFile generatedWSDLFile =
                project.getFile(new Path(
                        "Generated Services/WsdlGenTestForRefBoms.wsdl")); //$NON-NLS-1$

        assertTrue("WSDL is not generated", generatedWSDLFile.exists()); //$NON-NLS-1$

        // compare the WSDLs
        IFile goldFile1 =
                project.getFile(new Path(
                        "Service Descriptors/WsdlGenTestForRefBomsGOLD1.wsdl")); //$NON-NLS-1$
        IStatus compareContents =
                comparator.compareContents(goldFile1.getContents(),
                        generatedWSDLFile.getContents(),
                        null);
        if (compareContents != null) {
            fail("Contents match 1 -> The files are different, maybe because of namepsace prefix differences:" //$NON-NLS-1$
                    + compareContents.getMessage());
        }
        // Change the BOMs

        IFile basebaseBom =
                project.getFile(new Path("Business Objects/TestAA.bom")); //$NON-NLS-1$
        if (basebaseBom != null && basebaseBom.exists()) {

            WorkingCopy bomWC = WorkingCopyUtil.getWorkingCopy(basebaseBom);

            assertTrue(bomWC != null);
            assertTrue(bomWC.getRootElement() instanceof Model);
            Model model = (Model) bomWC.getRootElement();

            Class class1 = UMLFactory.eINSTANCE.createClass();
            class1.setName("ABC"); //$NON-NLS-1$

            Command addCmd =
                    AddCommand.create(bomWC.getEditingDomain(),
                            model,
                            UMLPackage.eINSTANCE.getPackage_PackagedElement(),
                            class1);

            if (addCmd.canExecute()) {
                bomWC.getEditingDomain().getCommandStack().execute(addCmd);
            }
            bomWC.save();
            TestUtil.delay(10000);
            TestUtil.buildAndWait();
            // Compare the WSDL's again
            IFile goldFile2 =
                    project.getFile(new Path(
                            "Service Descriptors/WsdlGenTestForRefBomsGOLD2.wsdl")); //$NON-NLS-1$
            IStatus compareContents2 =
                    comparator.compareContents(goldFile2.getContents(),
                            generatedWSDLFile.getContents(),
                            null);

            if (compareContents2 != null) {
                fail("Contents match 2 -> The files are different, maybe because of namepsace prefix differences:" //$NON-NLS-1$
                        + compareContents2.getMessage());
            }
        }

    }
}
