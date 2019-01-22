/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.n2.wsdlgen.test;

import java.io.IOException;
import java.util.Collections;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
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
import com.tibco.xpd.xpdl2.extension.EMFSearchUtil;

/**
 * This is a comprehensive test for
 * 
 * @author rsomayaj
 * @since 3.3 (15 Feb 2010)
 */
public class N2_01_ProcessNestedBOMTestCase extends TestCase {

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
                                        .singletonList("resources/BPMNestedBOM/")); //$NON-NLS-1$

        boolean projectImported = projectImporter.performImport();
        assertTrue("Project Imported:", projectImported); //$NON-NLS-1$
        project =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getProject("BPMNestedBOM"); //$NON-NLS-1$

        ProjectAssetMigrationManager.getInstance().migrate(project,
                new NullProgressMonitor());
        assertTrue("Project Doesn't exist", project.exists()); //$NON-NLS-1$
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
            project.delete(true, null);
        }
    }

    /**
     * @throws CoreException
     * @throws IOException
     */
    public void testProcessWSDLGenerated() throws CoreException, IOException {

        WSDLFileComparator comparator = new WSDLFileComparator();
        IFile generatedWSDLFile =
                project.getFile(new Path("Generated Services/NestedBOMs.wsdl")); //$NON-NLS-1$

        assertTrue("WSDL is generated", generatedWSDLFile.exists()); //$NON-NLS-1$

        // compare the WSDLs
        IFile goldFile1 =
                project.getFile(new Path(
                        "Service Descriptors/NestedBOMsGOLD1.wsdl")); //$NON-NLS-1$
        IStatus compareContents =
                comparator.compareContents(goldFile1.getContents(),
                        generatedWSDLFile.getContents(),
                        null);
        if (compareContents != null) {
            fail("Contents match 1 -> The files are different, maybe because of namepsace prefix differences:" //$NON-NLS-1$
                    + compareContents.getMessage());
        }
        // Change the BOMs

        IFile bomWithInternalPackages =
                project.getFile(new Path(
                        "Business Objects/BOMWithInternalPacakges.bom")); //$NON-NLS-1$
        if (bomWithInternalPackages != null && bomWithInternalPackages.exists()) {

            WorkingCopy bomWC =
                    WorkingCopyUtil.getWorkingCopy(bomWithInternalPackages);

            assertTrue(bomWC != null);
            assertTrue(bomWC.getRootElement() instanceof Model);
            Model model = (Model) bomWC.getRootElement();

            Class class1 = UMLFactory.eINSTANCE.createClass();
            class1.setName("NewClassAdded"); //$NON-NLS-1$

            Command addCmd =
                    AddCommand.create(bomWC.getEditingDomain(),
                            model,
                            UMLPackage.eINSTANCE.getPackage_PackagedElement(),
                            class1);

            if (addCmd.canExecute()) {
                bomWC.getEditingDomain().getCommandStack().execute(addCmd);
            }
            bomWC.save();
            TestUtil.delay(6000);
            // TestUtil.buildAndWait();
            // Compare the WSDL's again
            IFile goldFile2 =
                    project.getFile(new Path(
                            "Service Descriptors/NestedBOMsGOLD2.wsdl")); //$NON-NLS-1$
            IStatus compareContents2 =
                    comparator.compareContents(goldFile2.getContents(),
                            generatedWSDLFile.getContents(),
                            null);

            if (compareContents2 != null) {
                fail("Contents match 2 -> The files are different, maybe because of namepsace prefix differences:" //$NON-NLS-1$
                        + compareContents2.getMessage());
            }
            // assertTrue("Contents match 2" + compareContents2.getMessage(),
            // IStatus.OK == compareContents2.getSeverity());

            Class class2 = UMLFactory.eINSTANCE.createClass();
            class2.setName("InternalClassAdded"); //$NON-NLS-1$

            EObject object =
                    EMFSearchUtil.findInList(model.getPackagedElements(),
                            UMLPackage.eINSTANCE.getNamedElement_Name(),
                            "internalpkg2"); //$NON-NLS-1$
            if (object instanceof com.tibco.xpd.xpdl2.Package) {
                com.tibco.xpd.xpdl2.Package internalPckg =
                        (com.tibco.xpd.xpdl2.Package) object;
                addCmd =
                        AddCommand.create(bomWC.getEditingDomain(),
                                internalPckg,
                                UMLPackage.eINSTANCE
                                        .getPackage_PackagedElement(),
                                class2);
                if (addCmd.canExecute()) {
                    bomWC.getEditingDomain().getCommandStack().execute(addCmd);
                }
                bomWC.save();
                TestUtil.delay(6000);
                // TestUtil.buildAndWait();
                // Compare the WSDL's again
                IFile goldFile3 =
                        project.getFile(new Path(
                                "Service Descriptors/NestedBOMsGOLD3.wsdl")); //$NON-NLS-1$
                IStatus compareContents3 =
                        comparator.compareContents(goldFile3.getContents(),
                                generatedWSDLFile.getContents(),
                                null);

                if (compareContents3 != null) {
                    fail("Contents match 3 -> The files are different, maybe because of namepsace prefix differences:" //$NON-NLS-1$
                            + compareContents3.getMessage());
                }

            }
        }

    }

}
