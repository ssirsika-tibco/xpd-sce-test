/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.core.test.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.xml.transform.Source;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.ide.IDEInternalPreferences;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.osgi.framework.Bundle;
import org.xml.sax.SAXException;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.migrateproject.MigrateProject;
import com.tibco.xpd.resources.projectconfig.Destination;
import com.tibco.xpd.resources.projectconfig.Destinations;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.ProjectConfigFactory;
import com.tibco.xpd.resources.projectconfig.ProjectDetails;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.ui.util.PostImportUtil;
import com.tibco.xpd.resources.util.ProjectImporter;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.wc.AbstractWorkingCopy;
import com.tibco.xpd.ui.wizards.newproject.ProjectSelectionPage;
import com.tibco.xpd.ui.wizards.newproject.XpdProjectWizard;
import com.tibco.xpd.ui.wizards.newproject.XpdProjectWizard.CreateXpdProjectOperation;
import com.tibco.xpd.ui.wizards.newproject.XpdProjectWizardFactory;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.ValidationActivator.ValidatorStatus;
import com.tibco.xpd.validation.provider.IIssue;
//import com.tibco.xpd.xpdl2.Package;
//import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
//import com.tibco.xpd.xpdl2.resources.XpdlMigrate;
//import com.tibco.xpd.xpdl2.util.XpdlSearchUtil;

import junit.framework.TestCase;

/**
 * Common utility methods for test cases.
 * 
 * @author wzurek
 */
public class TestUtil {

    private static final String NEW_PROJECTWIZARD_PROJECT_NAME_LABEL =
            "&Project name:"; //$NON-NLS-1$

    private static final String WSDL_ASSET_ID = "com.tibco.xpd.asset.wsdl"; //$NON-NLS-1$

    private static final String BOM_ASSET_ID = "com.tibco.xpd.asset.bom"; //$NON-NLS-1$

    private static final String PROCESS_ASSET_ID =
            "com.tibco.xpd.asset.businessProcess"; //$NON-NLS-1$

    private static final String FORMS_ASSET_ID = "com.tibco.xpd.asset.form"; //$NON-NLS-1$

    /**
     * Process UI input but do not return for the specified time interval.
     * 
     * @param waitTimeMillis
     *            the number of milliseconds
     */
    public static void delay(long waitTimeMillis) {
        Display display = Display.getCurrent();

        // If this is the UI thread,
        // then process input.
        if (display != null) {
            long endTimeMillis = System.currentTimeMillis() + waitTimeMillis;
            while (System.currentTimeMillis() < endTimeMillis) {
                try {
                    if (!display.readAndDispatch()) {
                        display.sleep();
                    }
                } catch (Exception e) {
                    // ignore
                    e.printStackTrace();
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
     * Get the project config of the project with the given name
     * 
     * @param projectName
     *            project to get config of
     * @return <code>ProjectConfig</code>, <code>null</code> if failed to access
     *         it
     */
    public static ProjectConfig getProjectConfig(String projectName) {
        TestCase.assertNotNull("Project name is null.", projectName); //$NON-NLS-1$
        IProject project =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getProject(projectName);

        return getProjectConfig(project);
    }

    /**
     * Get the project config of the given project
     * 
     * @param projectName
     *            project to get config of
     * @return <code>ProjectConfig</code>, <code>null</code> if failed to access
     *         it
     */
    @SuppressWarnings("nls")
    public static ProjectConfig getProjectConfig(IProject project) {
        TestCase.assertNotNull("Project is null.", project); //$NON-NLS-1$
        ProjectConfig pc =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);
        TestCase.assertNotNull("Project Config is not avaiable", pc); //$NON-NLS-1$
        return pc;
    }

    /**
     * Can be used on the 'tearDown()' method.
     */
    public static void removeProject(final String projectName) {
        closeEditors();

        final IWorkspace workspace = ResourcesPlugin.getWorkspace();
        try {
            workspace.run(new IWorkspaceRunnable() {
                @Override
                public void run(IProgressMonitor monitor) throws CoreException {
                    workspace
                            .getRoot()
                            .getProject(projectName)
                            .refreshLocal(IResource.DEPTH_INFINITE,
                                    new NullProgressMonitor());
                    workspace.getRoot().getProject(projectName)
                            .delete(true, true, new NullProgressMonitor());
                }

            },
                    null);
        } catch (CoreException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Can be used on the 'setUp()' method.
     * 
     * @param projectName
     *            name of the project to create
     * @deprecated This method does not add project-asset configuration to
     *             projects which normally happens when user creates project.
     *             Use {@link #createProjectFromWizard(String)} instead
     */
    @Deprecated
    static public IProject createProject(String projectName) {

        closeEditors();

        // setup the project
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IProject project = workspace.getRoot().getProject(projectName);

        final IProjectDescription description =
                workspace.newProjectDescription(project.getName());
        // description.setLocation(new Path(PROJECT_NAME));

        CreateXpdProjectOperation op =
                new XpdProjectWizard.CreateXpdProjectOperation(project,
                        description, null);

        try {
            op.run(new NullProgressMonitor());
            return project;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Close all editors
     */
    @SuppressWarnings("nls")
    public static void closeEditors() {
        boolean closeAllEditors =
                PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                        .getActivePage().closeAllEditors(false);
        TestCase.assertTrue("All editors should be closed", closeAllEditors); //$NON-NLS-1$
    }

    /**
     * Create special folder of given kind and name in the project.
     * 
     * @param projectName
     *            where to create a folder
     * @param folderName
     *            special folder name
     * @param folderKind
     *            special folder kind
     * @return special folder
     */
    @SuppressWarnings({ "unchecked", "nls" })
    public static SpecialFolder createSpecialFolder(IProject project,
            String folderName, String folderKind) {
        ProjectConfig pc = getProjectConfig(project);
        SpecialFolder sf;

        int count = pc.getSpecialFolders().getFoldersOfKind(folderKind).size();

        // create folder and mark it as a special folder
        final IFolder folder = project.getFolder(folderName);
        try {
            ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {
                @Override
                public void run(IProgressMonitor monitor) throws CoreException {
                    folder.create(true, true, monitor);
                }
            }, null);
        } catch (CoreException e) {
            throw new RuntimeException(e);
        }
        waitForJobs(1000);
        sf = pc.getSpecialFolders().addFolder(folder, folderKind);
        TestCase.assertNotNull("Failed to create special folder", sf); //$NON-NLS-1$

        // access this folder through project config
        List<SpecialFolder> sFolders =
                pc.getSpecialFolders().getFoldersOfKind(folderKind);

        TestCase.assertEquals("Number of special folders of kind " + folderKind, //$NON-NLS-1$
                count + 1,
                sFolders.size());

        return sf;
    }

    /**
     * Create special folder of given kind and name in the project.
     * 
     * @param projectName
     *            where to create a folder
     * @param folderName
     *            special folder name
     * @param folderKind
     *            special folder kind
     * @return special folder
     */
    @SuppressWarnings({ "unchecked", "nls" })
    public static SpecialFolder createSpecialFolder(String projectName,
            String folderName, String folderKind) {
        ProjectConfig pc = getProjectConfig(projectName);

        // create folder and mark is as a special folder
        IFolder folder = pc.getProject().getFolder(folderName);
        try {
            folder.create(true, true, null);
        } catch (CoreException e) {
            throw new RuntimeException(e);
        }
        pc.getSpecialFolders().addFolder(folder, folderKind);

        // access this folder through project config
        List<SpecialFolder> conceptsFolders =
                pc.getSpecialFolders().getFoldersOfKind(folderKind);
        TestCase.assertEquals("There should be one special folder of kind '" //$NON-NLS-1$
                + folderKind + "'", 1, conceptsFolders.size()); //$NON-NLS-1$
        SpecialFolder conceptsFolder = conceptsFolders.get(0);
        return conceptsFolder;
    }

    /**
     * Waits for all the jobs to complete until it times out. There is an
     * initial delay provided as initialWaitTime (ms) to allow some time for any
     * jobs to kick off.
     * 
     * @param initialWaitTime
     *            (ms)
     * 
     */
    public static void waitForJobs(int initialWaitTime) {
        waitForJobs(null, initialWaitTime);
    }

    /**
     * Wait until all jobs with the given family are completed.
     */
    public static void waitForJobs() {
        waitForJobs(null, 10);
    }

    /**
     * Waits for all the jobs of given family to complete until it times out.
     * There is an initial delay provided as initialWaitTime (ms) to allow some
     * time for any jobs to kick off.
     * 
     * @param initialWaitTime
     *            (ms)
     * 
     */
    @SuppressWarnings("nls")
    public static void waitForJobs(Object family, int initialWaitTime) {
        System.out.print("    ==> waitForJobs(family=" + family + ")..."); //$NON-NLS-1$ //$NON-NLS-2$

        /*
         * Reduce initial wait time (this is onyl waiting for a job to kick off
         * which should be relatively fast.
         */
        long start = System.currentTimeMillis();

        TestUtil.delay(initialWaitTime);

        boolean waitForJob = true;

        while (waitForJob) {
            Job[] checkJobs = Job.getJobManager().find(family);
            waitForJob = false;
            for (Job job2 : checkJobs) {
                if (job2.isUser()) {
                    if (job2.getState() != Job.NONE) {
                        waitForJob = true;
                        break;
                    }
                }
            }

            if (waitForJob) {
                TestUtil.delay(100);
            }

            if (((System.currentTimeMillis() - start) / 1000) > 300) {
                TestCase.fail(
                        "Test waited 5 minutes for completion of all jobs. But there are still some running, waiting or sleeping Jobs"); //$NON-NLS-1$
            }
        }

        long duration = System.currentTimeMillis() - start;
        System.out.println(String.format("  waited %dms <==", duration)); //$NON-NLS-1$
    }

    /**
     * As per waitForJobs(Object family) EXCEPT that it works for waiting for
     * auto build family too (waitForJobs() ignore non-user jobs and hence won't
     * wait for auto builds :o(
     */
    public static void waitForJobsEx(Object family) {
        waitForJobsEx(family, 300);
    }

    /**
     * Wait for BUild to complete (timing out after given timeout.
     * 
     * @param totalWaitTimeSeconds
     */
    public static void waitForBuilds(int totalWaitTimeSeconds) {
        waitForJobsEx(ResourcesPlugin.FAMILY_AUTO_BUILD, totalWaitTimeSeconds);
    }

    /**
     * Wait for Builds to complete (timing out after given
     * totalWaitTimeSeconds). The initialWaitTime (in ms) lets any queued jobs
     * to start before waiting for these to complete.
     * 
     * @param totalWaitTimeSeconds
     * @param initialWaitTime
     */
    public static void waitForBuilds(int totalWaitTimeSeconds,
            long initialWaitTime) {
        waitForJobsEx(ResourcesPlugin.FAMILY_AUTO_BUILD,
                totalWaitTimeSeconds,
                initialWaitTime);
    }

    /**
     * As per waitForJobs(Object family) EXCEPT that it works for waiting for
     * auto build family too (waitForJobs() ignore non-user jobs and hence won't
     * wait for auto builds :o(
     */
    public static void waitForJobsEx(Object family, int totalWaitTimeSeconds) {
        waitForJobsEx(family, totalWaitTimeSeconds, 10);
    }

    /**
     * As per waitForJobs(Object family) EXCEPT that it works for waiting for
     * auto build family too (waitForJobs() ignore non-user jobs and hence won't
     * wait for auto builds :o( The initialWaitTime (ms) allows some time for
     * any jobs to kick off.
     * 
     */
    public static void waitForJobsEx(Object family, int totalWaitTimeSeconds,
            long initialWaitTime) {
        System.out.print(String
                .format("    ==> waitForJobsEx(family=%s, timeoutSecs=%d)...", //$NON-NLS-1$
                        family,
                        totalWaitTimeSeconds));

        /*
         * Reduce initial wait time (this is onyl waiting for a job to kick off
         * which should be relatively fast.
         */
        long start = System.currentTimeMillis();

        TestUtil.delay(initialWaitTime);

        boolean waitForJob = true;

        while (waitForJob) {
            Job[] checkJobs = Job.getJobManager().find(family);
            waitForJob = false;
            for (Job job2 : checkJobs) {
                if (job2.isUser()
                        || family == ResourcesPlugin.FAMILY_AUTO_BUILD) {
                    if (job2.getState() != Job.NONE) {
                        waitForJob = true;
                        break;
                    }
                }
            }

            if (waitForJob) {
                TestUtil.delay(100);
            }

            if (((System.currentTimeMillis() - start) / 1000) > totalWaitTimeSeconds) {
                TestCase.fail("Test waited 5 minutes for completion of all jobs. But there are still some running, waiting or sleeping Jobs"); //$NON-NLS-1$
            }

        }

        long duration = System.currentTimeMillis() - start;
        System.out.println(String.format("  waited %dms <==", duration)); //$NON-NLS-1$

    }

    /**
     * Wait until all validation related jobs will finish.
     */
    public static void waitForValidatior() {
        int seconds = 0;
        while (ValidationActivator.getDefault().getStatus() != ValidatorStatus.READY) {
            System.out.println("Waiting for VALIDATOR (" + (seconds++) //$NON-NLS-1$
                    + "sec.)"); //$NON-NLS-1$
            delay(300);

            if (seconds >= 100) {
                // Give up after a 30 secs
                TestCase.fail("Gave up on validator after 30 secs."); //$NON-NLS-1$
                break;
            }
        }
    }

    /**
     * Validate xml document against XMLSchema. If document is invalid
     * SAXException is thrown.<br/>
     * 
     * <b>Example usage to validate IFile against schemas from test plug-in:</b>
     * <code>
     * <pre>
     * private static String SCHEMA_BASE_URI = &quot;platform:/plugin/com.tibco.xpd.om.test/resources/schema&quot;;
     * 
     * ...
     * 
     * String deMetaFileName = &quot;directory-metamodel-1.0.xsd&quot;;
     * InputStream deMetaXSDInputStream =
     *         new ResourceSetImpl().getURIConverter().createInputStream(URI
     *                 .createURI(SCHEMA_BASE_URI + deMetaFileName));
     * Source deMetaXSD = new StreamSource(deMetaXSDInputStream, deMetaFileName);
     * 
     * String deFileName = &quot;directory-model-1.0.xsd&quot;;
     * InputStream deXSDInputStream =
     *         new ResourceSetImpl().getURIConverter().createInputStream(URI
     *                 .createURI(SCHEMA_BASE_URI + deFileName));
     * Source deXSD = new StreamSource(deXSDInputStream, deFileName);
     * 
     * //deMetaXSD is imported by deXSD so it must be before deXSD.
     * Source[] schemas = new Source[] { deMetaXSD, deXSD };
     * Source document = new StreamSource(xmlIFile.getContents(), xmlIFile.getName());
     * TestUtil.validateAgainstXMLSchema(schemas, document);
     * </pre>
     * </code>
     * 
     * @param schemas
     *            the array of sources containing schema definitions. The order
     *            of schemas is important. All imported schemas must be
     *            specified first. It is also important to provide the valid
     *            systemIds for the schema sources so the references can be
     *            resolved.
     * @param document
     *            xml document source to validate.
     * 
     * @throws SAXException
     *             If there is problem with compiling schema (for example: the
     *             provided schema definitions order is incorrect, or part of
     *             definition is missed).
     * @throws IOException
     *             if problem with reading documents.
     * 
     * @throws NullPointerException
     *             If the schemas parameter itself is null or any item in the
     *             array is null.
     * @throws llegalArgumentException
     *             If any item in the array is not recognized by this method.
     */
    public static void validateAgainstXMLSchema(Source[] schemas,
            Source document) throws SAXException, IOException {
        // Lookup a factory for the W3C XML Schema language
        SchemaFactory factory =
                SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema"); //$NON-NLS-1$

        // Compile the schema.
        Schema schema = factory.newSchema(schemas);

        // Get a validator from the schema.
        Validator validator = schema.newValidator();

        // Check the document
        String systemId = document.getSystemId();
        try {
            validator.validate(document);
            System.out.println(String.format("%1$s is valid.", systemId)); //$NON-NLS-1$
        } catch (SAXException e) {
            System.out.println(String
                    .format("%1$s is invalid. %2$s", systemId, e //$NON-NLS-1$
                            .getMessage()));
            throw e;
        }
    }

    /**
     * Migrates the working copy model
     * 
     * @param wc
     */
    /** This method depends on xpdl packages which are not available in studio-core
	* TODO JA: Revisit it later	
    public static void migratePackage(WorkingCopy wc) {
        Package model = (Package) wc.getRootElement();
        if (model == null) {
            // may be XPDL file needs migration, this will avoid the pain to
            // migrate XPDL files manually (no need to check-in the migrated
            // files to SVN as there might be another change)
            if (wc instanceof Xpdl2WorkingCopyImpl) {
                try {
                    ((Xpdl2WorkingCopyImpl) wc).migrate();
                } catch (CoreException e1) {
                    System.out.println("Migration exception: " //$NON-NLS-1$
                            + e1.getLocalizedMessage());
                    e1.printStackTrace();
                }
                // Save the working copy after the migration
                try {
                    wc.save();
                } catch (IOException e) {
                    System.out.println("Migration IOException Exception: " //$NON-NLS-1$
                            + e.getMessage());
                    e.printStackTrace();
                }
            }
            model = (Package) wc.getRootElement();
        } else {
            // format version of XPDL file might not be correct, so
            // trying to migrate it
            String current =
                    XpdlSearchUtil.findExtendedAttributeValue(model,
                            XpdlMigrate.FORMAT_VERSION_ATT_NAME);
            if (!XpdlMigrate.FORMAT_VERSION_ATT_VALUE.equals(current)) {
                if (wc instanceof Xpdl2WorkingCopyImpl) {
                    try {
                        ((Xpdl2WorkingCopyImpl) wc).migrate();
                    } catch (CoreException e) {
                        System.out.println("Migration exception: " //$NON-NLS-1$
                                + e.getLocalizedMessage());
                        e.printStackTrace();
                    }
                }
            }
        }
    }
	*/
	/** TODO: Temporary implementation. */	
    @Deprecated
    public static void migratePackage(WorkingCopy wc) {
        try {
            if (wc instanceof AbstractWorkingCopy) {
                ((AbstractWorkingCopy) wc).migrate();
                // Save the working copy after the migration
                wc.save();
            }
        } catch (CoreException e) {
            System.out.println("Migration IOException Exception: " //$NON-NLS-1$
                    + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Migration IOException Exception: " //$NON-NLS-1$
                    + e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * Create an IFile by copying the content of an existing file under current
     * project's resources/ directory.
     * 
     * @param pluginID
     *            The plug-in name of the test program where srcFolder exists
     * @param srcFolder
     *            The source folder where resource file exists
     * @param destFolder
     *            The special folder where new file will be created in
     * @param fileName
     *            Name of the new file that will be created
     * @return A IFile resource representing the file
     */
    public static IFile createFileFromResource(String pluginID,
            String srcFolder, SpecialFolder destFolder, String fileName) {
        return createFileFromResource(pluginID, destFolder, srcFolder
                + IPath.SEPARATOR + fileName);
    }

    public static IFile createFileFromResource(String pluginID,
            SpecialFolder destFolder, String fileRelativePath) {
        IFile newFile = null;
        InputStream fileInputStream = null;
        try {
            // Copy existing XPDL model to package special folder
            Bundle bundle = Platform.getBundle(pluginID);
            Path filePath = new Path(fileRelativePath);
            fileInputStream = FileLocator.openStream(bundle, filePath, false);
            IPath path = new Path(fileRelativePath);
            newFile = destFolder.getFolder().getFile(path.lastSegment());
            newFile.create(fileInputStream, true, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return newFile;
    }

    /**
     * 
     * @return
     */
    public static List<String> getAllStudioProjectNamesInWorkSpace() {
        List<String> studioProjectNames = new ArrayList<String>();
        List<IProject> allStudioProjectsInWorkSpace =
                getAllStudioProjectsInWorkSpace();
        for (IProject project : allStudioProjectsInWorkSpace) {
            studioProjectNames.add(project.getName());
        }
        return studioProjectNames;
    }

    /**
     * 
     * @return
     */
    public static List<IProject> getAllStudioProjectsInWorkSpace() {
        List<IProject> studioProjects = new ArrayList<IProject>();
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        IProject[] projects = root.getProjects();
        for (IProject project : projects) {
            boolean studioProject = TestUtil.isBPMStudioProject(project);
            if (studioProject) {
                studioProjects.add(project);
            }
        }
        return studioProjects;
    }

    public static void buildAndWait() {

        try {
            // If auto-build is on no need to rebuild.
            if (!ResourcesPlugin.getWorkspace().isAutoBuilding()) {
                ResourcesPlugin.getWorkspace()
                        .build(IncrementalProjectBuilder.FULL_BUILD,
                                new NullProgressMonitor());
            }
            TestUtil.waitForJobsEx(ResourcesPlugin.FAMILY_AUTO_BUILD);

        } catch (CoreException e) {
            e.printStackTrace();
        }
        return;
    }

	/**
	 * Use this build and wait if you get intermittent build issues.
	 */
	public static void fullBuildAndWait()
	{

		try
		{
			ResourcesPlugin.getWorkspace().build(IncrementalProjectBuilder.FULL_BUILD, new NullProgressMonitor());
			
			TestUtil.waitForJobsEx(ResourcesPlugin.FAMILY_MANUAL_BUILD);
			TestUtil.waitForJobsEx(ResourcesPlugin.FAMILY_AUTO_BUILD);

		}
		catch (CoreException e)
		{
			e.printStackTrace();
		}
		return;
	}

    public static void buildAndWait(IProject project) {
        try {
            // If auto-build is on no need to rebuild.
            if (!ResourcesPlugin.getWorkspace().isAutoBuilding()) {
                project.build(IncrementalProjectBuilder.FULL_BUILD,
                        new NullProgressMonitor());
                // ResourcesPlugin.getWorkspace().getRoot().getProject(project
                // .getName()).build(IncrementalProjectBuilder.FULL_BUILD,
                // new NullProgressMonitor());
            }
            TestUtil.waitForJobsEx(ResourcesPlugin.FAMILY_AUTO_BUILD);

        } catch (CoreException e) {
            e.printStackTrace();
        }
        return;
    }

    /**
     * 
     * @throws CoreException
     * @Deprecated Use
     *             {@link MigrateProject#migrate(IProject, IProgressMonitor)}
     *             instead.
     */
    public static void migrateProjectXpdlFiles(IProject project)
            throws CoreException {
        List<SpecialFolder> allSpecialFoldersOfKind =
                SpecialFolderUtil.getAllSpecialFoldersOfKind(project,
                        "processes"); //$NON-NLS-1$
        if (allSpecialFoldersOfKind == null) {
            return;
        }
        for (SpecialFolder specialFolder : allSpecialFoldersOfKind) {
            IFolder folder = specialFolder.getFolder();
            folder.accept(new IResourceVisitor() {
                @Override
                public boolean visit(IResource resource) throws CoreException {
                    if (resource instanceof IFile) {
                        if ("xpdl".equals(resource.getFileExtension())) { //$NON-NLS-1$
                            WorkingCopy wc =
                                    XpdResourcesPlugin.getDefault()
                                            .getWorkingCopy(resource);
                            TestUtil.migratePackage(wc);
                        }
                        return false;
                    }
                    return true;
                }
            });

        }
    }

    public static void deleteAllWorkpsaceProjects() {
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        IProject[] projects = root.getProjects();
        for (IProject project : projects) {
            try {
                project.delete(Boolean.TRUE, new NullProgressMonitor());
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
    }

    public static void addGlobalDestinationToProject(String testPluginId,
            String globalDestinationName, IProject project) {
        ProjectConfig projectConfig =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);

        if (projectConfig != null) {
            ProjectDetails projDetails =
                    ProjectConfigFactory.eINSTANCE.createProjectDetails();
            projDetails.setId(testPluginId);
            projectConfig.setProjectDetails(projDetails);
            Destinations createDestinations =
                    ProjectConfigFactory.eINSTANCE.createDestinations();
            projDetails.setGlobalDestinations(createDestinations);
            Destination destination =
                    ProjectConfigFactory.eINSTANCE.createDestination();
            destination.setType(globalDestinationName);
            createDestinations.getDestination().add(destination);
        }
    }

    /**
     * @param markers
     * @return newline terminated list of marker descriptions.
     */
    public static String markersToString(List<IMarker> markers) {
        String text = ""; //$NON-NLS-1$
        if (markers != null) {
            for (IMarker marker : markers) {
                String id = marker.getAttribute(IIssue.ID, "id.?????"); //$NON-NLS-1$
                String desc =
                        marker.getAttribute(IMarker.MESSAGE, "message.?????"); //$NON-NLS-1$

                text += id + " : " + desc + "\n"; //$NON-NLS-1$ //$NON-NLS-2$
            }
        }

        return text;
    }

    /**
     * Uses the UI way to create a BPM project. Should add all the natures and
     * relevant builders
     * 
     * @param projectName
     * @return a brand new BPM project
     */
    public static IProject createBPMProjectFromWizard(String testProjectName) {
        return createBPMProjectFromWizard(testProjectName, null);
    }

    /**
     * Uses the UI way to create a project with a specific XPD wizard. Adds all
     * the natures and relevant builders and set the BPM destination. The wizard
     * ID must match the ID specified in the wizard extension point.
     * 
     * @param testProjectName
     *            The name of the project to create.
     * @param wizardId
     *            The ID of the wizard to use, defaults to BPM Developer
     *            Project.
     * @return The created project.
     */
    public static IProject createBPMProjectFromWizard(String testProjectName,
            String wizardId) {
        IProject project =
                TestUtil.createProjectFromWizard(testProjectName, wizardId);

        ProjectConfig projectConfig =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);
        if (projectConfig != null) {
            ProjectDetails projDetails =
                    ProjectConfigFactory.eINSTANCE.createProjectDetails();
            projectConfig.setProjectDetails(projDetails);
            Destinations createDestinations =
                    ProjectConfigFactory.eINSTANCE.createDestinations();
            projDetails.setGlobalDestinations(createDestinations);
            Destination destination =
                    ProjectConfigFactory.eINSTANCE.createDestination();
            destination.setType("CE"); //$NON-NLS-1$
            createDestinations.getDestination().add(destination);
        }

        return project;
    }

    /**
     * Uses the UI way to create projects. Hopefully this should add all the
     * natures and relevant builders.
     * <p>
     * **NOTE**: Now the Project creation wizard by default does not include the
     * BOM and Service Descriptior(Wsdl) assets, as most of the tests as based
     * on these assets we explicitly add them to the test framework through this
     * method.
     * 
     * @param projectName
     *            The name of the project to create.
     * @return The created project.
     */
    public static IProject createProjectFromWizard(String projectName) {
        return createProjectFromWizard(projectName, null);
    }

    /**
     * Creates a new project using the XpdProjectWizard for a specific XPD
     * wizard type. The wizard ID must match the value in the wizard extension
     * point, or can be null to default to a standard BPM Developer Project.
     * 
     * @param projectName
     *            The name of the project to create.
     * @param wizardId
     *            The ID of the XPD wizard to use - or null to create BPM
     *            developer project with BOM and WSDL assets also added (for
     *            backwards compatibility for existing test cases)
     * @return The created project.
     */
    public static IProject createProjectFromWizard(String projectName,
            String wizardId) {
        Map<String, String> parameters = new HashMap<String, String>();

        return createProjectFromWizard(projectName, wizardId, parameters);
    }

    /**
     * Creates a new project using the XpdProjectWizard for a specific XPD
     * wizard type. The wizard ID must match the value in the wizard extension
     * point, or can be null to default to a standard BPM Developer Project.
     * 
     * @param projectName
     *            The name of the project to create.
     * @param wizardId
     *            The ID of the XPD wizard to use - or null to create BPM
     *            developer project with BOM and WSDL assets also added (for
     *            backwards compatibility for existing test cases)
     * @param parameters
     *            As per {@link XpdProjectWizardFactory} parameter definitions
     *            (such as "hideDestinationEnv", "defaultProjectVersion" etc)
     * 
     * @return The created project.
     */
    public static IProject createProjectFromWizard(String projectName,
            String wizardId, Map<String, String> parameters) {

        try {
            IPreferenceStore store =
                    IDEWorkbenchPlugin.getDefault().getPreferenceStore();

            store.setValue(IDEInternalPreferences.PROJECT_SWITCH_PERSP_MODE,
                    IDEInternalPreferences.PSPM_ALWAYS);

            /*
             * Sid ACE-411 - use the factory to create the wizard AS that takes
             * into account the wizard parameters.
             */
            XpdProjectWizardFactory factory = new XpdProjectWizardFactory();

            TempConfigurationElement config =
                    new TempConfigurationElement(wizardId);

            factory.setInitializationData(config, "class", parameters); //$NON-NLS-1$

            XpdProjectWizard projWizard = (XpdProjectWizard) factory.create();

            /*
             * SCF-411: Kapil- Now the Project creation wizard by default does
             * not include the BOM and Service Descriptior(Wsdl) assets, as most
             * of the tests as based on these assets we explicitly add them to
             * the test framework.
             */
            /*
             * ACE-467 Sid - Don't create WSDL assets by default any more, not
             * supported in SCE!
             */
            if (wizardId == null) {
                String[] assetIdsToEnable =
                        new String[] { BOM_ASSET_ID };

                /* Add the BOM and Wsdl assets to the project. */
                projWizard.setAssetIdsToEnable(assetIdsToEnable);
            }

            projWizard.init(PlatformUI.getWorkbench(), null);
            WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench()
                    .getActiveWorkbenchWindow().getShell(), projWizard);

            dialog.create();
            IWizardPage page = dialog.getCurrentPage();
            if (page instanceof ProjectSelectionPage) {
                Control control = page.getControl();
                if (control instanceof Composite) {
                    Composite composite = (Composite) control;
                    Text findText = findText(composite);
                    if (findText != null) {
                        findText.setText(projectName);
                    }
                }
            }

            projWizard.performFinish();
            TestUtil.waitForJobs(1000);

            IProject project = ResourcesPlugin.getWorkspace().getRoot()
                    .getProject(projectName);
            if (project.exists()) {
                return project;
            }

        } catch (CoreException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Create a project in as near as it can be to that created via the XPD
     * wizard extension point contributions.
     * 
     * @param wizardId
     * @param parameters
     *            As per {@link XpdProjectWizardFactory} parameter definitions
     *            (such as "hideDestinationEnv", "defaultProjectVersion" etc)
     * @return The project
     */
    public IProject createProjectFromWizardFactory(String wizardId,
            Map<String, String> parameters) {
        IProject project = null;

        try {
            XpdProjectWizardFactory factory = new XpdProjectWizardFactory();

            TempConfigurationElement config =
                    new TempConfigurationElement(wizardId);

            factory.setInitializationData(config, "class", parameters); //$NON-NLS-1$

            XpdProjectWizard wizard = (XpdProjectWizard) factory.create();

        } catch (CoreException e) {
            e.printStackTrace();
        }

        return project;
    }

    private static Text findText(Composite composite) {
        Control[] children = composite.getChildren();
        Text txt = null;
        int counter = 0;
        while (counter < children.length) {

            if (children[counter] instanceof Label) {
                Label label = (Label) children[counter];
                if (label.getText()
                        .equals(NEW_PROJECTWIZARD_PROJECT_NAME_LABEL)) {
                    if (children[counter + 1] instanceof Text) {
                        return (Text) children[counter + 1];
                    }
                }
            }
            if (children[counter] instanceof Composite) {
                txt = findText((Composite) children[counter]);
                if (txt != null) {
                    break;
                }
            }
        }
        return txt;
    }

    /**
     * Get a List of IMarkers that indicate an ERROR type problem.
     * 
     * @param resource
     *            An XPDL file or project that contains the markers
     * @return A List of IMarker that have severity ERROR
     */
    public static List<IMarker> getErrorMarkers(IResource resource) {
        List<IMarker> markerList = new ArrayList<IMarker>();
        try {
            IMarker[] findMarkers =
                    resource.findMarkers(IMarker.PROBLEM,
                            true,
                            IResource.DEPTH_ZERO);
            // Enumerate the markers and find the markers that indicate Error
            for (IMarker marker : findMarkers) {
                // System.out.println(marker.getAttribute("destinationId"));
                Object severity = marker.getAttribute(IMarker.SEVERITY);
                if (severity != null && severity instanceof Number) {
                    int value = ((Number) severity).intValue();
                    if (value == IMarker.SEVERITY_ERROR) {
                        markerList.add(marker);
                    }
                }
            }// end for
        } catch (CoreException e) {
            e.printStackTrace();
        }
        return markerList;
    }

    /**
     * 
     * @param resource
     * @param depthInfinite
     *            <code>true</code> to check this resource and all it's descendants <code>false</code> to check only
     *            this resource.
     * @param testName
     *            Name of test to output in System.err (so that if an error was found it would be output).
     * 
     * @return <code>true</code> if given resource has given problem marker raised on it.
     */
    public static boolean hasErrorProblemMarker(IResource resource, boolean depthInfinite, String testName) {
        return hasErrorProblemMarker(resource, depthInfinite, Collections.emptySet(), testName);
    }

    /**
     * Checks if the given resources (or descendants if depthInfinite=true) has any error level problem marker.
     * 
     * If any found the error marker text will be output to System.err so that the console output will log the errors
     * found.
     * 
     * @param resource
     * @param depthInfinite
     *            <code>true</code> to check this resource and all it's descendants <code>false</code> to check only
     *            this resource.
     * @param exceptIdsOrMessageText
     *            Marker id's OR partial message text of markers to ignore.
     * @param testName
     *            Name of test to output in System.err (so that if an error was found it would be output).
     * 
     * @return <code>true</code> if given resource has given problem marker raised on it.
     */
    @SuppressWarnings("nls")
    public static boolean hasErrorProblemMarker(IResource resource, boolean depthInfinite,
            Collection<String> exceptIdsOrMessageText, String testName) {
        boolean found = false;

        try {
            String heading = String.format(
                    "TestUtil.hasErrorLevelProblemMarker() : Error markers found for test '%s' on resource '%s'...\n=================================================================\n",
                    testName,
                    resource != null ? resource.getFullPath().toString() : "<bad resource>");

            IMarker[] markers = resource.findMarkers(IMarker.PROBLEM,
                    true,
                    depthInfinite ? IResource.DEPTH_INFINITE : IResource.DEPTH_ZERO);

            if (markers != null) {
                for (IMarker marker : markers) {
                    if (marker.getAttribute(IMarker.SEVERITY, -1) == IMarker.SEVERITY_ERROR
                            && !exceptIdsOrMessageText.contains(marker.getAttribute("issueId", ""))
                            && !containsMessageFragment(marker, exceptIdsOrMessageText)) {
                        System.err.println(heading + "- " + marker.getAttribute(IMarker.MESSAGE, "") + " ("
                                + marker.getAttribute("issueId", "") + ")");
                        heading = "";
                        found = true;
                    }
                }

            }

        } catch (CoreException e) {
            e.printStackTrace();
        }

        if (found) {
            // output footer to error messages
            System.err.println("=================================================================");
        }
        return found;
    }

    /**
     *
     * @param resource
     * @param depthInfinite
     *            <code>true</code> to check this resource and all it's descendants <code>false</code> to check only
     *            this resource.
     * 
     * @return String list of error level problem markers on resource (and optionally, its descendants).
     */
    @SuppressWarnings("nls")
    public static String getErrorProblemMarkerList(IResource resource, boolean depthInfinite) {
        String errorList = "";

        try {
            IMarker[] markers = resource.findMarkers(IMarker.PROBLEM,
                    true,
                    depthInfinite ? IResource.DEPTH_INFINITE : IResource.DEPTH_ZERO);

            if (markers != null) {
                for (IMarker marker : markers) {
                    if (marker.getAttribute(IMarker.SEVERITY, -1) == IMarker.SEVERITY_ERROR) {
                        errorList += "- " + marker.getAttribute(IMarker.MESSAGE, "") + "\n";
                    }
                }
            }

        } catch (CoreException e) {
            e.printStackTrace();
        }

        return errorList;
    }

    /**
     * @param marker
     * @param exceptIdsOrMessageText
     * @return <code>true</code> if the message text for the problem marker contains any of the given strings.
     */
    private static boolean containsMessageFragment(IMarker marker, Collection<String> exceptIdsOrMessageText) {
        String message = marker.getAttribute(IMarker.MESSAGE, ""); //$NON-NLS-1$

        for (String exceptionText : exceptIdsOrMessageText) {
            if (message.contains(exceptionText)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns a collection of all the markers of severity "ERROR".
     * 
     * @param resource
     *            the resource to be searched.
     * @param depthInfinite
     *            <code>false</code> if only top-level markers are to be returned.
     * @param aExceptIds
     *            the collection of IDs to be ignored in the result.
     * @return the collection of markers found.
     * @throws CoreException
     */
    @SuppressWarnings("nls")
    public static Collection<IMarker> getErrorMarkers(IResource resource, boolean depthInfinite, String... aExceptIds)
            throws CoreException {
        IMarker[] markers = resource
                .findMarkers(IMarker.PROBLEM, true, depthInfinite ? IResource.DEPTH_INFINITE : IResource.DEPTH_ZERO);

        if (markers == null) {
            return Collections.emptyList();
        }

        HashSet<String> exceptions = new HashSet<>(Arrays.asList(aExceptIds));

        Collection<IMarker> result = new ArrayList<>();
        for (IMarker marker : markers) {
            if ((marker.getAttribute(IMarker.SEVERITY, -1) == IMarker.SEVERITY_ERROR)
                    && (!exceptions.contains(marker.getAttribute("issueId", "")))) {
                result.add(marker);
            }
        }
        return result;
    }

    @SuppressWarnings("nls")
    public static void outputErrorMarkers(IResource resource, boolean depthInfinite) {
        try {
            IMarker[] markers = resource.findMarkers(IMarker.PROBLEM,
                    true,
                    depthInfinite ? IResource.DEPTH_INFINITE : IResource.DEPTH_ZERO);

            if (markers != null) {
                String header = "Error Markers for resource: " + resource.getName()
                        + "\n=============================================================\n";

                for (IMarker marker : markers) {
                    if (marker.getAttribute(IMarker.SEVERITY, -1) == IMarker.SEVERITY_ERROR) {
                        System.err.println(header + String.format("%1$s - \"%2$s\"",
                                marker.getAttribute("issueId"),
                                marker.getAttribute(IMarker.MESSAGE)));

                        header = "";
                    }
                }

                if (header.length() == 0) {
                    System.err.println("\n=============================================================\n");
                }

            }

        } catch (CoreException e) {
            e.printStackTrace();
        }
    }

    private static final String COMPOSITE_SF_KIND = "composite"; //$NON-NLS-1$

    /**
     * Returns true if it is a Studio project & does not have composite special
     * folder. TODO need to find better logic than that.
     * 
     * @param studioProject
     * @return
     */
    public static boolean isBPMStudioProject(IProject studioProject) {
        boolean isStudioProject = ProjectUtil.isStudioProject(studioProject);
        if (isStudioProject) {
            SpecialFolder specialFolderOfKind =
                    SpecialFolderUtil.getSpecialFolderOfKind(studioProject,
                            TestUtil.COMPOSITE_SF_KIND);
            if (specialFolderOfKind == null) {
                // looks like it is a BPM project
                return true;
            }
        }
        return false;
    }

    /**
     * Import the projects from a given resource folder (or single project in
     * the given zip) in the test plugin-relative resource path and execute the
     * standard post import tasks on them.
     * 
     * Note that the {@link ProjectImporter} looks like it deletes existing
     * projects before doing the import.
     * 
     * @param testRsourcePluginId
     *            The id of the plugin where the test resource zip'd projects
     *            can be found.
     * @param resourcePath
     *            the list of strings representing bundle relative project URIs.
     *            Two forms are allowed:
     *            <li>plug-in relative path to folder containing project
     *            (resource/myProject/)</li>
     *            <li>plug-in relative path to zip file containing project
     *            content (resource/myProject.zip)</li>. If the project is the
     *            folder the relative URI should have trailing path separator
     *            '/'.
     * @param expectedProjectNames
     *            The expected projects in the resourcePath
     * 
     * @return The {@link ProjectImporter} used to import th projects or
     *         <code>null</code> on failure
     */
    public static ProjectImporter importProjectsFromZip(
            String testRsourcePluginId,
            String[] resourcePaths, String[] expectedProjectNames) {

        ProjectImporter importer = ProjectImporter.createPluginProjectImporter(
                testRsourcePluginId,
                Arrays.asList(resourcePaths));

        if (!importer.performImport()) {
            XpdResourcesPlugin.getDefault().getLogger()
                    .error("ProjectImporter.performImport() failed."); //$NON-NLS-1$
            return null;
        }

        for (String projectName : expectedProjectNames) {
            IProject project = ResourcesPlugin.getWorkspace().getRoot()
                    .getProject(projectName);

            if (!project.isAccessible()) {
                XpdResourcesPlugin.getDefault().getLogger()
                        .error("Expected project '" + projectName //$NON-NLS-1$
                                + "' isn't accessible after import (wrong name provided or not in given import resources?)."); //$NON-NLS-1$
                return null;
            }

            IStatus performPostImportTasks = PostImportUtil.getInstance()
                    .performPostImportTasks(Collections.singletonList(project),
                            new NullProgressMonitor());

            if (!performPostImportTasks.isOK()) {
                XpdResourcesPlugin.getDefault().getLogger()
                        .error("Post import tasks failed for project '" //$NON-NLS-1$
                                + projectName
                                + "' isn't accessible after import (" //$NON-NLS-1$
                                + performPostImportTasks.getMessage() + ")"); //$NON-NLS-1$
                return null;
            }
        }

        return importer;
    }
}
