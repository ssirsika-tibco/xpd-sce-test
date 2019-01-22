/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.wm.test;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;

import com.tibco.xpd.core.test.util.AbstractBaseResourceTest;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskScript;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * AbstractBaseContentAssistTest
 * 
 * 
 * @author mtorres
 */
public abstract class AbstractBaseContentAssistTest extends
        AbstractBaseResourceTest {


    /**
     * 
     */
    public AbstractBaseContentAssistTest() {
        
    }

    protected void doTestValidations() throws Exception {
        // Check all files created correctly.
        checkTestFilesCreated();
        TestResourceInfo[] testResources = getTestResources();
        if (testResources != null) {
        for (TestResourceInfo testResourceInfo : testResources) {
            String testFileName = testResourceInfo.getTestFileName();
            if(testFileName != null && testFileName.endsWith(".xpdl")){ //$NON-NLS-1$
                // Get the test plugin source resource file.
                Path path =
                        new Path(TestResourceInfo.PATH_SEPARATOR
                                + testResourceInfo.getProjectName()
                                + TestResourceInfo.PATH_SEPARATOR
                                + testResourceInfo.getTestFilePath());
                String resourceURI = testResourceInfo.getTestFilePath();

                IResource resource =
                        ResourcesPlugin.getWorkspace().getRoot()
                                .findMember(path);
                if (resource == null || !resource.exists()) {
                    fail("Unable to find resource '" + resourceURI //$NON-NLS-1$
                            + "' for  test problem markers"); //$NON-NLS-1$
                }
                TaskScript taskScriptToProcess = null;
                    WorkingCopy wc =
                            (WorkingCopy) XpdResourcesPlugin.getDefault()
                                    .getWorkingCopy(resource);
                    if (wc.getRootElement() instanceof Package) {
                        Package model = (Package) wc.getRootElement();
                        assertNotNull("Root element of the model is null even after migration", //$NON-NLS-1$
                                model);
                        if (model != null) {
                            List<Process> processes = model.getProcesses();
                            if (processes != null) {
                                for (Process process : processes) {
                                    Collection<Activity> activities =
                                            Xpdl2ModelUtil
                                                    .getAllActivitiesInProc(process);
                                    if (activities != null) {
                                        for (Activity activity : activities) {
                                            Implementation implementation =
                                                    activity.getImplementation();
                                            if (implementation instanceof Task) {
                                                Task tsk = (Task) implementation;
                                                TaskScript taskScript =
                                                        tsk.getTaskScript();
                                                if (taskScript != null) {

                                                    taskScriptToProcess = taskScript;
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                }
                if(taskScriptToProcess != null){
                    System.out.println();
                }
            }
        }
        }

        /*
         * TA DA!!!
         */
        return;
    }

    
    @Override
    protected void setUp() throws Exception {
        // System.out.println("setup files");
        enableAutoBuild(false);

        super.setUp();

        setUpBeforeBuild();

        enableAutoBuild(true);

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
    private void buildAndWait() {
        try {
            ResourcesPlugin.getWorkspace()
                    .build(IncrementalProjectBuilder.FULL_BUILD,
                            new NullProgressMonitor());

            TestUtil.waitForJobsEx(ResourcesPlugin.FAMILY_AUTO_BUILD);

        } catch (CoreException e) {
            e.printStackTrace();
            fail("Workspace Build Failed: " + e.getMessage()); //$NON-NLS-1$
        }

        return;
    }


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

}
