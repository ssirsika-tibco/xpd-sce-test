/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.n2.wsdlgen.test;

import java.util.Collections;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.core.test.util.WSDLFileComparator;
import com.tibco.xpd.resources.projectconfig.projectassets.util.ProjectAssetMigrationManager;
import com.tibco.xpd.resources.util.ProjectImporter;

/**
 * This is a comprehensive test for
 * 
 * @author rsomayaj
 * @since 3.3 (15 Feb 2010)
 */
public class N2_15_ProcIfNestedBOMTestCase extends TestCase {

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
                                        .singletonList("resources/ProcIfcNestedBOM/")); //$NON-NLS-1$

        boolean projectImported = projectImporter.performImport();
        assertTrue("Project Imported:", projectImported); //$NON-NLS-1$
        project =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getProject("ProcIfcNestedBOM"); //$NON-NLS-1$
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
            project.delete(true, new NullProgressMonitor());
        }
    }

    /**
     * @throws CoreException
     */
    public void testProcIfcWSDLGenerated() throws CoreException {
        ResourcesPlugin.getWorkspace()
                .build(IncrementalProjectBuilder.CLEAN_BUILD,
                        new NullProgressMonitor());

        TestUtil.buildAndWait();

        IFile generatedWSDLFile =
                project.getFile(new Path(
                        "Generated Services/ProcIfcNestedBOMs.wsdl")); //$NON-NLS-1$
        assertNotNull("Generated WSDL not null", generatedWSDLFile); //$NON-NLS-1$
        assertTrue(generatedWSDLFile.exists());

        IFile goldWSDLFile =
                project.getFile(new Path("GOLDS/ProcIfcNestedBOMsGOLD.wsdl")); //$NON-NLS-1$
        assertNotNull("GOLDWSDL not null", goldWSDLFile); //$NON-NLS-1$
        assertTrue(goldWSDLFile.exists());

        WSDLFileComparator comparator = new WSDLFileComparator();

        IStatus status =
                comparator.compareContents(goldWSDLFile.getContents(),
                        generatedWSDLFile.getContents(),
                        null);
        String statusMsg = null;
        if (status != null) {
            statusMsg = status.getMessage();
        }
        assertNull(statusMsg, statusMsg);

    }
}
