/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.tests.validation;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.util.ProjectImporter;

import junit.framework.TestCase;

/**
 * Sid ACE-5185 Test New nature for switching off validations against mixed asset projects - should be no validations
 * against mixing BOM / OM assets with other asset types for project with the
 * com.tibco.bx.validation.mixedAssetProjectNature set.
 * 
 * @author aallway
 * @since 07 May 2021
 */
public class AceMultiAssetProjectValidationTest extends TestCase {

    // @Test
    public void testLocalBOMDataProjectMigration() {
        ProjectImporter projectImporter = importMainTestProjects();
        try {
            assertFalse(hasProblemMarker(
                            ResourcesPlugin.getWorkspace().getRoot().getProject("MultiAsset"))); //$NON-NLS-1$
        } finally {
            if (projectImporter != null) {
                projectImporter.performDelete();
            }
        }
    }

    /**
     * Import all projects from test plugin resources for the main test
     * 
     * @return the project importer
     */
    private ProjectImporter importMainTestProjects() {
        /*
         * Import and migrate the project
         */
        ProjectImporter projectImporter = TestUtil.importProjectsFromZip("com.tibco.xpd.sce.test", //$NON-NLS-1$
                new String[] { "resources/AceMultiAssetProjectValidationTest/MultiAsset.zip" }, //$NON-NLS-1$
                new String[] { "MultiAsset" }); //$NON-NLS-1$

        assertTrue("Failed to load project from resources/AceMultiAssetProjectValidationTest/MultiAsset.zip", //$NON-NLS-1$
                projectImporter != null);

        TestUtil.buildAndWait();

        return projectImporter;
    }

    /**
     * 
     * @param resource
     * @param markerId
     * @return <code>true</code> if given resource has given problem marker raised on it.
     */
    private boolean hasProblemMarker(IResource resource) {
        try {
            IMarker[] markers = resource.findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);

            if (markers != null && markers.length > 0) {
                return true;
            }

        } catch (CoreException e) {
            e.printStackTrace();
        }

        return false;
    }

}
