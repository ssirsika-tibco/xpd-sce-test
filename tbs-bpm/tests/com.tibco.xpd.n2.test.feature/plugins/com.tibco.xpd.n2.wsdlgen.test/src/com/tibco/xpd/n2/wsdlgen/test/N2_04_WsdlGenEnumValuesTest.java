/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.n2.wsdlgen.test;

import java.util.Collections;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.core.test.util.WSDLFileComparator;
import com.tibco.xpd.resources.projectconfig.projectassets.util.ProjectAssetMigrationManager;
import com.tibco.xpd.resources.util.ProjectImporter;

/**
 * 
 * 
 * @author rsomayaj
 * @since 3.3 (17 Feb 2010)
 */
public class N2_04_WsdlGenEnumValuesTest extends TestCase {

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
                                        .singletonList("resources/EnumValuesTest/")); //$NON-NLS-1$

        boolean projectImported = projectImporter.performImport();
        assertTrue("Project Imported:", projectImported); //$NON-NLS-1$

        project =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getProject("EnumValuesTest"); //$NON-NLS-1$
        ProjectAssetMigrationManager.getInstance().migrate(project,
                new NullProgressMonitor());
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
            TestUtil.waitForJobs(200);
        }
    }

    public void testWSDLsMatch() throws Exception {
        IFile generatedFile =
                project.getFile(new Path(
                        "Generated Services/EnumValuesTest.wsdl")); //$NON-NLS-1$

        assertNotNull("GeneratedFile is not null::", generatedFile); //$NON-NLS-1$
        assertTrue("WSDL has been generated", generatedFile.exists()); //$NON-NLS-1$

        IFile goldFile =
                project.getFile(new Path(
                        "Service Descriptors/EnumValuesTestGOLD.wsdl")); //$NON-NLS-1$
        assertNotNull("Gold File is not null", goldFile); //$NON-NLS-1$
        assertTrue("Gold File exists::", goldFile.exists()); //$NON-NLS-1$
        WSDLFileComparator fileComparator = new WSDLFileComparator();
        IStatus status =
                fileComparator.compareContents(goldFile.getContents(),
                        generatedFile.getContents(),
                        null);
        if (status != null) {
            System.out.println(status.getMessage());
        }
        assertNull("Status null implies that there is no difference::", status); //$NON-NLS-1$
    }
}
