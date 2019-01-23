/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.n2.wsdlgen.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.core.test.util.WSDLFileComparator;
import com.tibco.xpd.resources.projectconfig.projectassets.util.ProjectAssetMigrationManager;
import com.tibco.xpd.resources.util.ProjectImporter;
import com.tibco.xpd.resources.util.XpdConsts;

/**
 * XPD-1024 highlighted a problem that WSDL generated wasn't done correctly, for
 * a Process that had a formal parameter in a BOM that was in a referenced
 * project.
 * 
 * @author rsomayaj
 * @since 3.3 (24 Aug 2010)
 */
public class N2_11_XPD1024ExtPrjTest extends TestCase {

    /**
     * @see junit.framework.TestCase#setUp()
     * 
     * @throws Exception
     */
    @Override
    protected void setUp() throws Exception {

        List<String> urls = new ArrayList<String>();
        urls.add("resources/XPD1024/A/"); //$NON-NLS-1$
        urls.add("resources/XPD1024/B/"); //$NON-NLS-1$
        urls.add("resources/XPD1024/CCCC/"); //$NON-NLS-1$
        urls.add("resources/XPD1024/GoldWsdl/"); //$NON-NLS-1$

        ProjectImporter prjImporter =
                ProjectImporter
                        .createPluginProjectImporter(WSDLGenTestPluginActivator.PLUGIN_ID,
                                urls);

        assertTrue("Imported Projects", prjImporter.performImport()); //$NON-NLS-1$
        IProject project =
                ResourcesPlugin.getWorkspace().getRoot().getProject("A"); //$NON-NLS-1$

        if (project.findMarkers(XpdConsts.PROJECT_MIGRATION_MARKER_TYPE,
                false,
                IResource.DEPTH_ZERO) != null) {
            ProjectAssetMigrationManager.getInstance().migrate(project, null);
        }

        project = ResourcesPlugin.getWorkspace().getRoot().getProject("B"); //$NON-NLS-1$
        if (project.findMarkers(XpdConsts.PROJECT_MIGRATION_MARKER_TYPE,
                false,
                IResource.DEPTH_ZERO) != null) {
            ProjectAssetMigrationManager.getInstance().migrate(project, null);
        }

        project = ResourcesPlugin.getWorkspace().getRoot().getProject("CCCC"); //$NON-NLS-1$
        if (project.findMarkers(XpdConsts.PROJECT_MIGRATION_MARKER_TYPE,
                false,
                IResource.DEPTH_ZERO) != null) {
            ProjectAssetMigrationManager.getInstance().migrate(project, null);
        }

        project =
                ResourcesPlugin.getWorkspace().getRoot().getProject("GoldWsdl"); //$NON-NLS-1$
        if (project.findMarkers(XpdConsts.PROJECT_MIGRATION_MARKER_TYPE,
                false,
                IResource.DEPTH_ZERO) != null) {
            ProjectAssetMigrationManager.getInstance().migrate(project, null);
        }

        TestUtil.buildAndWait();

        TestUtil.delay(10000);
    }

    public void testWSDLGenCompareWithGold() throws Exception {
        IFile genWSDLFile =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getFile(new Path("A/Generated Services/P1.wsdl")); //$NON-NLS-1$
        assertNotNull("WSDL generated", genWSDLFile); //$NON-NLS-1$

        assertTrue("WSDL Accessible", genWSDLFile.isAccessible()); //$NON-NLS-1$

        IFile goldFile =
                ResourcesPlugin
                        .getWorkspace()
                        .getRoot()
                        .getFile(new Path(
                                "GoldWsdl/Service Descriptors/P1.wsdl")); //$NON-NLS-1$
        assertNotNull("GOLD file", goldFile); //$NON-NLS-1$

        assertTrue("GOLD file accessible", goldFile.isAccessible()); //$NON-NLS-1$

        WSDLFileComparator wsdlFileComparator = new WSDLFileComparator();

        IStatus status =
                wsdlFileComparator.compareContents(goldFile.getContents(),
                        genWSDLFile.getContents(),
                        null);

        if (status != null) {
            fail("Generate File:" + genWSDLFile.getName() + "  Difference ->  " //$NON-NLS-1$ //$NON-NLS-2$
                    + status.getMessage());
        }

    }

    /**
     * @see junit.framework.TestCase#tearDown()
     * 
     * @throws Exception
     */
    @Override
    protected void tearDown() throws Exception {
        IProject[] projects =
                ResourcesPlugin.getWorkspace().getRoot().getProjects();

        for (IProject project : projects) {
            project.delete(true, null);
        }
    }
}
