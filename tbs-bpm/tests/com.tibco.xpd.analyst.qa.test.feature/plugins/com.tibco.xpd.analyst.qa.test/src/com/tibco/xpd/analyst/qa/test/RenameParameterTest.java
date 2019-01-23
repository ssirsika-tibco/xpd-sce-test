package com.tibco.xpd.analyst.qa.test;

import java.util.List;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.commands.RenameFieldOrParamCommand;

/**
 * Test for renaming a Parameter that is referenced by process's ScriptTask's
 * JavaScript code. Upon renaming the parameter, the JavaScript code should
 * automatically be updated to reflect the new name of the parameter.
 * 
 * @author sghani
 * 
 */
public class RenameParameterTest extends TestCase {

    /** There will be no cleaning after test if this is set to false */
    private static boolean SHOULD_CLEAN = true;

    /** The test plugin ID */
    private static final String PLUGIN_ID = "com.tibco.xpd.analyst.qa.test"; //$NON-NLS-1$

    /** Name of the test project. */
    protected static String TEST_PROJECT_NAME = "JavaScriptTestProject"; //$NON-NLS-1$

    /** Sample model file name */
    protected static String XPDL_MODEL_FILE = "TestProcess2.xpdl"; //$NON-NLS-1$

    /** Special folder name */
    private static final String FOLDER_NAME = "TestProcessPackage"; //$NON-NLS-1$

    /** Name of Process used for Testing */
    private static final String PROCESS_NAME = "TestProcess"; //$NON-NLS-1$

    /** Name of the ScriptTask activity */
    private static final String TASK_NAME = "ScriptTaskTest"; //$NON-NLS-1$

    /** Name of the Formal Parameter */
    private static final String PARAM_NAME = "StringParameter"; //$NON-NLS-1$

    private IProject project;

    private IFile xpdlFile;

    /**
     * This test will get an XPDL model containing a ScriptTask, rename a
     * Parameter that is being referenced in the JavaScript code of the
     * ScriptTask, and verify the Parameter's new name is reflected in the
     * JavaScript code automatically.
     * 
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void testRenameParameter() throws Exception {

        this.setName("RenameParameterTest.testRenameParameter()"); //$NON-NLS-1$

        // Get a handle to the EMF model for the Package
        WorkingCopy wc =
                XpdResourcesPlugin.getDefault().getWorkingCopy(xpdlFile);

        AnalystTestUtil.migratePackage(wc);
        Package model = (Package) wc.getRootElement();
        assertNotNull("XPDL package null after migration", model); //$NON-NLS-1$

        // Get the process we are testing by process name
        Process process = AnalystTestUtil.getProcess(model, PROCESS_NAME);
        assertNotNull("Unable to get process " + PROCESS_NAME, process); //$NON-NLS-1$
        assertEquals(PROCESS_NAME, process.getName());

        // Get the ScriptTask activity
        Activity act = AnalystTestUtil.getActivity(process, TASK_NAME);
        assertNotNull("Unable to get Activity " + TASK_NAME, act); //$NON-NLS-1$
        assertEquals(TASK_NAME, act.getName());

        // Get the FormalParameter of the ScriptTask
        FormalParameter param =
                AnalystTestUtil.getParameter(process, PARAM_NAME);
        assertNotNull("Unable to retrieve parameter " + PARAM_NAME, param); //$NON-NLS-1$
        assertEquals(PARAM_NAME, param.getName());

        // Get the Script from the ScriptTask (which is an Activity)
        // and verify that the script contains a reference to the parameter name
        String script = ProcessScriptUtil.getScriptTaskScript(act);
        assertTrue(script.contains(PARAM_NAME));

        // Rename the name of the FormalParameter using following command
        EditingDomain editDomain = wc.getEditingDomain();
        CommandStack stack = editDomain.getCommandStack();
        String newName = "ModifiedName"; //$NON-NLS-1$
        Command cmd =
                RenameFieldOrParamCommand.create(editDomain,
                        param,
                        process,
                        PARAM_NAME,
                        newName,
                        false);
        assertTrue(cmd.canExecute());
        stack.execute(cmd);

        // Save the working copy of XPDL model
        if (wc.isWorkingCopyDirty()) {
            System.out.println("Saving the XPDL model's working copy."); //$NON-NLS-1$
            wc.save();
        }

        TestUtil.waitForJobsEx(ResourcesPlugin.FAMILY_AUTO_BUILD);

        // Verify the parameter name was modified by retrieving it from process
        param = AnalystTestUtil.getParameter(process, newName);
        assertNotNull(param);

        // Verify the ScriptTask's script that was referencing the parameter
        // has been updated to reflect the new parameter name
        String upScript = ProcessScriptUtil.getScriptTaskScript(act);
        System.out.println("Updated Script: " + upScript); //$NON-NLS-1$
        // Make sure the script also reflects the modified parameter name
        assertTrue(upScript.contains(newName));

        // Also make sure no problem markers (error) were raised
        // Assuming there were no errors before the update was done
        List<IMarker> markers = AnalystTestUtil.getErrorMarkers(xpdlFile);
        assertEquals("Expected no errors. Got...\n" //$NON-NLS-1$
                + TestUtil.markersToString(markers),
                0,
                markers.size());

    }

    /**
     * Create a new project, a process special folder and create a copy of an
     * existing XPDL file.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // Create project and ProcessPackage special folder,
        // Then copy resource XPDL file to the special folder
        project = TestUtil.createProject(TEST_PROJECT_NAME);
        assertTrue(project.isAccessible());
        SpecialFolder specialFolder =
                TestUtil.createSpecialFolder(project,
                        FOLDER_NAME,
                        Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND);

        // Copy existing XPDL model to special folder.
        xpdlFile =
                AnalystTestUtil.createFileFromResource(PLUGIN_ID,
                        specialFolder,
                        XPDL_MODEL_FILE);
        assertNotNull("Unable to copy XPDL from resource", xpdlFile); //$NON-NLS-1$
        // Check the model is added to project
        IResource resource =
                project.findMember(FOLDER_NAME + "/" + XPDL_MODEL_FILE); //$NON-NLS-1$
        assertNotNull(resource);
        TestUtil.waitForJobsEx(ResourcesPlugin.FAMILY_AUTO_BUILD);
    }

    /**
     * Remove project created for test if we should cleanup.
     */
    @Override
    protected void tearDown() throws Exception {
        if (SHOULD_CLEAN) {
            AnalystTestUtil.cleanProject(TEST_PROJECT_NAME);
        }
        SHOULD_CLEAN = true;
        super.tearDown();
        TestUtil.waitForJobsEx(ResourcesPlugin.FAMILY_AUTO_BUILD);
    }
}
