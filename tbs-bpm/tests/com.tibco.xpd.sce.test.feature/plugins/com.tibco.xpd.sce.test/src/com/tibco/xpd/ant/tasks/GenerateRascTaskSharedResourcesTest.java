/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.ant.tasks;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.util.ProjectImporter;

import junit.framework.TestCase;

/**
 * Test generation of deployment info file in generate-rascs command.
 *
 * @author aallway
 * @since 14 Oct 2021
 */
@SuppressWarnings("nls")
public class GenerateRascTaskSharedResourcesTest extends TestCase {

    /*
     * This needs to be a copy of GenerateRasctTask.DEFAULT_DEST_DIR as: From
     * Eclipse Help: "The task or type can reference any class available for the
     * plug-in but plug-in classes MUST NOT access the tasks or types."
     */
    private static final String DEFAULT_DEST_DIR =
            "Deployment Artifacts";

    public void testExecuteTask() throws Exception {
        ProjectImporter projectImporter = importProjects(
                new String[] { "resources/GenerateRascsSharedResoucesTest/AntProject/",
                        "resources/GenerateRascsSharedResoucesTest/Processes/",
                        "resources/GenerateRascsSharedResoucesTest/Processes2/" },
                new String[] { "Processes", "Processes2" });

        try {
            IWorkspaceRoot workspaceRoot =
                    ResourcesPlugin.getWorkspace().getRoot();
            assertNotNull(workspaceRoot.getProject("Processes"));
            assertNotNull(workspaceRoot.getProject("Processes2"));

            // test that the rasc files does not yet exist
            assertFalse(workspaceRoot.getProject("AntProject").getFolder("project").getFolder("RascOut")
                    .getFile("Processes.rasc").exists());
            assertFalse(workspaceRoot.getProject("AntProject")
                    .getFolder("project").getFolder("RascOut")
                    .getFile("Processes2.rasc").exists());

            runLaunch("AntProject", "project/build.xml");

            IFolder folder = workspaceRoot.getProject("AntProject")
                    .getFolder("project").getFolder("RascOut");
            folder.refreshLocal(2, null);

            IFile rascFile = folder.getFile("Processes.rasc");
            assertTrue(rascFile.exists());

            IFile rascFile2 = folder.getFile("Processes2.rasc");
            assertTrue(rascFile2.exists());

            IFile manifestFile = folder.getFile("deploy.manifest");
            assertTrue(manifestFile.exists());

            IFile infoFile = folder.getFile("deploy.info");
            assertTrue(infoFile.exists());

            /*
             * Define the expected shared resource definitions
             */
            ExpectedSharedResource[] expectedSharedResources = {
                    // P:Processes/Processes.xpdl...
                    new ExpectedSharedResource("My Email Resource", "EMAIL", null),
                    new ExpectedSharedResource("EMAIL_PJ1_PR0", "EMAIL", null),
                    new ExpectedSharedResource("My REST Resource", "REST", "A REST service resource"),
                    new ExpectedSharedResource("REST_PJ1_PR0", "REST", "REST service REST_PJ1_PR0"),

                    // P:Processes/Processes1.xpdl...
                    new ExpectedSharedResource("EMAIL_PJ1_PR1", "EMAIL", null),
                    new ExpectedSharedResource("REST_PJ1_PR1", "REST", "REST service REST_PJ1_PR1"),

                    // P:Processes2/Processes2.xpdl
                    new ExpectedSharedResource("EMAIL_PJ2_PR0", "EMAIL", null),
                    new ExpectedSharedResource("REST_PJ2_PR0_nodesc", "REST", null)
            };

            /*
             * Check content of the sharedResources property are correct.
             * 
             * The test projects have several XPDL's with system participants, some shared and some not.
             */
            Type type = new TypeToken<Map<String, Object>>() {
            }.getType();
            Gson gson = new Gson();

            FileReader fileReader = new FileReader(infoFile.getLocation().toFile());
            JsonReader jsonReader = gson.newJsonReader(fileReader);
            Map<String, Object> myMap =
                    gson.fromJson(jsonReader, type);
            
            Object sharedResources = myMap.get("sharedResources");

            assertTrue(sharedResources instanceof List);
            
            List lSharedResources = (List)sharedResources;
            
            for (Object sharedResource : lSharedResources) {
                assertTrue(sharedResource instanceof Map);
                
                String rname = (String) ((Map) sharedResource).get("name");
                String rtype = (String) ((Map) sharedResource).get("type");
                String rdescription = (String) ((Map) sharedResource).get("description");

                for (ExpectedSharedResource expectedSharedResource : expectedSharedResources) {
                    if (expectedSharedResource.equals(new ExpectedSharedResource(rname, rtype, rdescription))) {
                        if (expectedSharedResource.found) {
                            fail("Shared Resosurce type:name - '"+rtype+":"+rname+"' - is listed more than once in deploy.info");
                        } else {
                            expectedSharedResource.found = true;
                            assertTrue((rdescription != null ? rdescription.equals(expectedSharedResource.description)
                                    : expectedSharedResource.description == null));

                            break;
                        }
                        
                    }
                }
            }
            
            for (ExpectedSharedResource expectedSharedResource : expectedSharedResources) {
                if (!expectedSharedResource.found) {
                    fail("Shared Resosurce type:name - '" + expectedSharedResource.type + ":" + expectedSharedResource
                            + "' - is not listed in deploy.info");
                }
            }

            jsonReader.close();
            fileReader.close();
            
        } finally {

            assertTrue("Failed deleting resources at end of test", projectImporter.performDelete());
        }
    }

    /**
     * For tracking of expected sharedResources entries in deploy.info file.
     *
     *
     * @author aallway
     * @since 14 Oct 2021
     */
    private static class ExpectedSharedResource {
        String name;

        String type;

        String description;

        boolean found = false;

        /**
         * @param name
         * @param type
         * @param description
         */
        public ExpectedSharedResource(String name, String type, String description) {
            super();
            this.name = name;
            this.type = type;
            this.description = description;
        }

        /**
         * @see java.lang.Object#equals(java.lang.Object)
         *
         * @param obj
         * @return
         */
        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof ExpectedSharedResource)) {
                return false;
            }

            ExpectedSharedResource esr = (ExpectedSharedResource) obj;

            return name.equals(esr.name) && type.equals(esr.type);
        }

    }


    /**
     * Test the given project.
     * 
     * @param aProjectName
     */
    private ProjectImporter importProjects(String[] aResourcePaths,
            String[] aExpectedProjectNames) {
        /*
         * Import and mgirate the project
         */
        ProjectImporter projectImporter =
                TestUtil.importProjectsFromZip("com.tibco.xpd.sce.test",
                        aResourcePaths,
                        aExpectedProjectNames);

        assertTrue("Failed to load projects", projectImporter != null);

        for (String aProjectName : aExpectedProjectNames) {
            IProject project = ResourcesPlugin.getWorkspace().getRoot()
                    .getProject(aProjectName);
            assertTrue(aProjectName + " project does not exist",
                    project.isAccessible());
        }

        TestUtil.buildAndWait();

        return projectImporter;
    }

    private void runLaunch(String aProject, String aBuildXml)
            throws CoreException {
    
        ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
    
        ILaunchConfigurationType type = manager.getLaunchConfigurationType(
                "org.eclipse.ant.AntLaunchConfigurationType");
    
        ILaunchConfigurationWorkingCopy workingCopy =
                type.newInstance(null, "Launch ant");
    
        workingCopy.setAttribute("bad_container_name", "launcher");
        workingCopy.setAttribute("org.eclipse.ant.ui.DEFAULT_VM_INSTALL",
                false);
        workingCopy.setAttribute("org.eclipse.debug.core.ATTR_REFRESH_SCOPE",
                "${workspace}");
    
        workingCopy.setAttribute("org.eclipse.jdt.launching.CLASSPATH_PROVIDER",
                "org.eclipse.ant.ui.AntClasspathProvider");
        workingCopy.setAttribute("org.eclipse.jdt.launching.PROJECT_ATTR",
                aProject);
        workingCopy.setAttribute(
                "org.eclipse.jdt.launching.SOURCE_PATH_PROVIDER",
                "org.eclipse.ant.ui.AntClasspathProvider");
        workingCopy.setAttribute("org.eclipse.ui.externaltools.ATTR_LOCATION",
                String.format("${workspace_loc:/%1$s/%2$s}",
                        aProject,
                        aBuildXml));
        workingCopy.setAttribute("process_factory_id",
                "org.eclipse.ant.ui.remoteAntProcessFactory");
        workingCopy.setAttribute("org.eclipse.debug.core.MAPPED_RESOURCE_PATHS",
                Arrays.asList(
                        String.format("/%1$s/%2$s", aProject, aBuildXml)));
        workingCopy.setAttribute("org.eclipse.debug.core.MAPPED_RESOURCE_TYPES",
                Arrays.asList("1"));
    
        ILaunch launch = workingCopy.launch(ILaunchManager.RUN_MODE,
                new NullProgressMonitor());
    
        // wait for launch to complete - within 5 minutes
        int count = 0;
        while ((++count < 1000) && (!launch.isTerminated())) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
            }
        }
    
        // fail if it failed to complete
        assertTrue("Launch failed to complete.", launch.isTerminated());
    }
}
