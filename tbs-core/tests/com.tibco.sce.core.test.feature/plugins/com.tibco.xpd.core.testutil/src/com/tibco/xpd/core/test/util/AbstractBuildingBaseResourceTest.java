/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.core.test.util;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;

/**
 * 
 * 
 * @author aallway
 * @since 17 Jun 2011
 */
public abstract class AbstractBuildingBaseResourceTest extends
        AbstractBaseResourceTest {

    public static boolean enableAutoBuild(boolean state) {
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IWorkspaceDescription desc = workspace.getDescription();
        boolean isAutoBuilding = desc.isAutoBuilding();
        if (isAutoBuilding != state) {
            desc.setAutoBuilding(state);
            try {
                workspace.setDescription(desc);
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
        return isAutoBuilding;
    }

    @Override
    protected void setUp() throws Exception {
        // System.out.println("setup files");
        enableAutoBuild(false);

        super.setUp();

        setUpBeforeBuild();

        // System.out.println("build and wait");
        buildAndWait();

        // System.out.println("done");
        return;
    }

    protected void setUpBeforeBuild() {
        // Do nothing by default
    }

    /**
     * Force a build of the whole workspace - and wait for it to finish.
     */
    protected void buildAndWait() {
        try {
            /*
             * FULL Build does not clean! So need to clean then turn auto build
             * back on then do a full build.
             */
            enableAutoBuild(true);
            ResourcesPlugin.getWorkspace()
                    .build(IncrementalProjectBuilder.CLEAN_BUILD,
                            new NullProgressMonitor());

            ResourcesPlugin.getWorkspace()
                    .build(IncrementalProjectBuilder.FULL_BUILD,
                            new NullProgressMonitor());

            TestUtil.waitForJobsEx(ResourcesPlugin.FAMILY_AUTO_BUILD, 300, 300);

        } catch (CoreException e) {
            e.printStackTrace();
            fail("Workspace Build Failed: " + e.getMessage()); //$NON-NLS-1$
        }

        return;
    }

    /**
     * 
     */
    public AbstractBuildingBaseResourceTest() {
        super();
    }

}