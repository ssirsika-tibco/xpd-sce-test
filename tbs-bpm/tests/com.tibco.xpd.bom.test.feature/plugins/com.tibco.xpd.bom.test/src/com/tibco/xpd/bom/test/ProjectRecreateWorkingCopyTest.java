/**
 * 
 */
package com.tibco.xpd.bom.test;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.ui.wizards.newproject.XpdProjectWizard;
import com.tibco.xpd.ui.wizards.newproject.XpdProjectWizard.CreateXpdProjectOperation;

import junit.framework.TestCase;

/**
 * Tests problem with deleting and recreating project with the same name and
 * with the same special folder.
 * 
 * @author Jan Arciuchiewicz.
 * 
 */
public class ProjectRecreateWorkingCopyTest extends TestCase {

    private static final String PROJECT_NAME = "com.tibco.xpd.bom.resources.test.wc.ProjectRecreateWorkingCopyTest";

    @Override
    protected void setUp() throws Exception {
        System.out.println("*** setUp start");
        super.setUp();
        System.out.println("*** setUp end");
    }

    @Override
    protected void tearDown() throws Exception {
        System.out.println("*** tearDown start");
        super.tearDown();
        System.out.println("*** tearDown end");
    }

    private ProjectConfig getProjectConfig() {
        XpdResourcesPlugin rp = XpdResourcesPlugin.getDefault();
        IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(
                PROJECT_NAME);
        ProjectConfig pc = rp.getProjectConfig(project);
        assertNotNull("Project Config is not avaiable", pc);
        return pc;
    }

    @SuppressWarnings("unchecked")
    public void testProjectWithSpecialFolderRecteate() throws Exception {
        System.out.println("START testProjectWithSpecialFolderRecteate");
        // setup the project
        {
            IWorkspace workspace = ResourcesPlugin.getWorkspace();
            IProject project = workspace.getRoot().getProject(PROJECT_NAME);

            final IProjectDescription description = workspace
                    .newProjectDescription(project.getName());

            CreateXpdProjectOperation op = new XpdProjectWizard.CreateXpdProjectOperation(
                    project, description, null);
            System.out.println(String.format("-- Creating project: %s ...",
                    PROJECT_NAME));
            op.run(new NullProgressMonitor());
            System.out.println(String.format("-- Project: %s created.",
                    PROJECT_NAME));
        }
        // create concept folder
        {
            System.out.println("-- Creating special folder ...");
            ProjectConfig pc = getProjectConfig();
            // create folder and mark is as a special folder
            IFolder conceptsIFolder = pc.getProject().getFolder("Concepts");
            conceptsIFolder.create(true, true, null);
            pc.getSpecialFolders().addFolder(conceptsIFolder, BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND);
            System.out.println("-- Special folder created.");
        }
        // delete project
        {
            System.out.println("*** before project delete");
            final IWorkspace workspace = ResourcesPlugin.getWorkspace();
            workspace.run(new IWorkspaceRunnable() {
               @Override
               public void run(IProgressMonitor monitor) throws CoreException {
                    workspace.getRoot().getProject(PROJECT_NAME).delete(true,
                            true, new NullProgressMonitor());
                }

            }, null);
            System.out.println("*** after project delete");
        }

        delay(1000); // One second delay fix the problem.

        // setup the project again
        {
            IWorkspace workspace = ResourcesPlugin.getWorkspace();
            IProject project = workspace.getRoot().getProject(PROJECT_NAME);

            final IProjectDescription description = workspace
                    .newProjectDescription(project.getName());

            CreateXpdProjectOperation op = new XpdProjectWizard.CreateXpdProjectOperation(
                    project, description, null);
            System.out.println(String.format("-- Creating project: %s ...",
                    PROJECT_NAME));
            op.run(new NullProgressMonitor());
            System.out.println(String.format("-- Project: %s created.",
                    PROJECT_NAME));
        }
        // create concept folder
        {
            System.out.println("-- Creating special folder ...");
            ProjectConfig pc = getProjectConfig();
            // create folder and mark is as a special folder
            IFolder conceptsIFolder = pc.getProject().getFolder("Concepts");
            conceptsIFolder.create(true, true, null);
            pc.getSpecialFolders().addFolder(conceptsIFolder, BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND);
            System.out.println("-- Special folder created.");
        }
        // delete project
        {
            System.out.println("*** before project delete");
            final IWorkspace workspace = ResourcesPlugin.getWorkspace();
            workspace.run(new IWorkspaceRunnable() {
                @Override
                public void run(IProgressMonitor monitor) throws CoreException {
                    workspace.getRoot().getProject(PROJECT_NAME).delete(true,
                            true, new NullProgressMonitor());
                }

            }, null);
            System.out.println("*** after project delete");
        }
        System.out.println("END testProjectWithSpecialFolderRecteate");

    }

    /**
     * Process UI input but do not return for the specified time interval.
     * 
     * @param waitTimeMillis
     *            the number of milliseconds
     */
    private void delay(long waitTimeMillis) {
        Display display = Display.getCurrent();

        // If this is the UI thread,
        // then process input.
        if (display != null) {
            long endTimeMillis = System.currentTimeMillis() + waitTimeMillis;
            while (System.currentTimeMillis() < endTimeMillis) {
                if (!display.readAndDispatch()) {
                    display.sleep();
                }
            }
            display.update();
        }
        // Otherwise, perform a simple sleep.
        else {
            try {
                Thread.sleep(waitTimeMillis);
            } catch (InterruptedException e) {
                // Ignored.
            }
        }
    }

    /**
     * Wait until all background tasks are complete.
     */
    protected void waitForJobs() {
        while (Job.getJobManager().currentJob() != null) {
            delay(1000);
        }
    }
}
