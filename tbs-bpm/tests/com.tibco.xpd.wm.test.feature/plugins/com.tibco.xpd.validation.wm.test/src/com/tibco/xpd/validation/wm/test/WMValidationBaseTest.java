package com.tibco.xpd.validation.wm.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.core.test.util.ValidationTestUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.xpdl2.Package;

/**
 * This is a base class for testing WM validations given XPDL that contain error
 * markers.
 * 
 * @author mtorres
 * 
 */
public abstract class WMValidationBaseTest extends TestCase {

    /** There will be no cleaning after test if this is set to false */
    // TODO: Temporarily disabling project removal because an exception
    // is caused when deleting Services folder that contains *.wsdl
    protected static boolean SHOULD_CLEAN = false;

    /** Special folder name */
    protected static final String FOLDER_NAME = "Process Packages"; //$NON-NLS-1$

    // Following are test variables and different for each test run
    /** Name of the test project. */
    protected static String testProjectName = "ValidationProject"; //$NON-NLS-1$

    /** XPDL model file name */
    protected String xpdlModelFileName = "WMValidations.xpdl"; //$NON-NLS-1$

    /** In this test, we are expected the following errors */
    protected String[] expectedErrors = new String[] {};

    /** Id of the objects whose error validations markers are being retrieved */
    protected List<String> affectedIds = new ArrayList<String>();

    protected IProject project;

    protected IFile xpdlFile;

    /**
     * This method checks if the expected errMsg matches an error is the list of
     * problem markers that were returned by builder/validation engine.
     * 
     * @param expectedError
     *            The error message expected in this test
     * @return True if errMsg contains/matches an error retrieved in the test.
     *         False otherwise.
     */
    protected boolean findError(String expectedError, List<IMarker> markers)
            throws Exception {
        // Traverse the array of errors and check
        // if errMsg contains any of the expected errors
        for (int i = 0; i < markers.size(); i++) {
            IMarker marker = markers.get(i);
            String msg = (String) marker.getAttribute(IMarker.MESSAGE);
            if (msg.contains(expectedError)) {
                return true;
            }
        }// end for
        return false;
    }

    /**
     * This method checks if the specified errMsg matches any error in the array
     * expectedErrors.
     * 
     * @param errMsg
     *            The error message being checked
     * @return True if errMsg contains/matches an expected error. False
     *         otherwise.
     */
    protected boolean findError(String errMsg) {
        // Traverse the array of errors and check
        // if errMsg contains any of the expected errors
        for (String error : expectedErrors) {
            // We don't do exact string comparison. Instead
            // we make sure the error retrieved in test contains
            // expected error string
            if (errMsg.contains(error)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method should be overridden to load the ids of the objects in the
     * model that we want to test
     */
    protected void loadAffectedIds() {
        // Do nothing
    }

    /**
     * TODO
     * 
     * @throws Exception
     */
    public void runFilteredValidationTest() throws Exception {
        // Load a list of object Ids whose error markers we want to retrieve
        loadAffectedIds();
        // Get a handle to the EMF model for the Package
        WorkingCopy wc =
                XpdResourcesPlugin.getDefault().getWorkingCopy(xpdlFile);

        TestUtil.migratePackage(wc);
        TestUtil.buildAndWait();

        Package model = (Package) wc.getRootElement();
        assertNotNull("Expected non-null XPDL package", model); //$NON-NLS-1$

        // Check for existing problems since the XPDL has incorrect behavior
        List<IMarker> allMarkers =
                ValidationTestUtil.getProblemMarkers(xpdlFile);
        List<IMarker> interestingMarkers = new ArrayList<IMarker>();
        if (affectedIds.isEmpty()) {
            interestingMarkers = allMarkers;
        } else {
            // Load only interesting markers
            if (allMarkers != null) {
                for (IMarker marker : allMarkers) {
                    String location = (String) marker.getAttribute("location");//$NON-NLS-1$
                    if (affectedIds.contains(location)) {
                        interestingMarkers.add(marker);
                    }
                }
            }
        }
        WMValidationTestActivator.logInfo("Found problems: " //$NON-NLS-1$
                + interestingMarkers.size());
        printErrorMarkers(interestingMarkers);

        // We are expecting the same number of errors as specified
        // in expectedErrors array
        assertEquals("Number of errors doesn't match expected number", //$NON-NLS-1$
                expectedErrors.length,
                interestingMarkers.size());

        // Go through the list of errors and make sure all expected
        // errors are found
        for (int i = 0; i < interestingMarkers.size(); i++) {
            IMarker marker = interestingMarkers.get(i);
            String msg = (String) marker.getAttribute(IMarker.MESSAGE);
            // Check the error message matches one of the expected
            // validation error
            if (!findError(msg)) {
                // If any error did not match an expected error, then
                // fail the test
                fail("Unexpected error found! Error: " + msg); //$NON-NLS-1$
            }
        }// end for

        TestUtil.waitForJobs();
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
        project = TestUtil.createProject(testProjectName);
        assertTrue(project.isAccessible());
        SpecialFolder packageFolder =
                TestUtil.createSpecialFolder(project,
                        FOLDER_NAME,
                        Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND);

        // Copy existing XPDL model to package special folder
        xpdlFile =
                TestUtil.createFileFromResource(WMValidationTestActivator.PLUGIN_ID,
                        "resources/XPDL", //$NON-NLS-1$
                        packageFolder,
                        xpdlModelFileName);
        assertNotNull("Unable to copy XPDL from resource", xpdlFile); //$NON-NLS-1$

        // Check the model is added to project
        IResource resource =
                project.findMember(FOLDER_NAME + "/" + xpdlModelFileName); //$NON-NLS-1$
        assertNotNull(resource);

        // Rebuild the project
        try {
            project.build(IncrementalProjectBuilder.FULL_BUILD, null);
        } catch (CoreException e) {
            fail(e.getMessage());
        }

        TestUtil.waitForBuilds(300);
    }

    /**
     * Remove project created for test if we should cleanup.
     */
    @Override
    protected void tearDown() throws Exception {
        project.close(null);
        if (SHOULD_CLEAN) {
            IProject proj =
                    ResourcesPlugin.getWorkspace().getRoot()
                            .getProject(testProjectName);
            if (proj.exists()) {
                TestUtil.removeProject(proj.getName());
            }
        }
        super.tearDown();
    }

    /**
     * Set the name of the project created for the test. Children classes MUST
     * set the project name.
     * 
     * @param name
     */
    protected void setProjectName(String name) {
        testProjectName = name;
    }

    /**
     * Set the name of the XPDL file. Children classes MUST set this file name
     * according their test.
     * 
     * @param name
     */
    protected void setXPDLModelFileName(String name) {
        xpdlModelFileName = name;
    }

    protected void setExpectedErrors(String[] err) {
        expectedErrors = err;
    }

    protected void printErrorMarkers(List<IMarker> markers) throws Exception {
        for (int i = 0; i < markers.size(); i++) {
            IMarker marker = markers.get(i);
            String msg = (String) marker.getAttribute(IMarker.MESSAGE);
            // Print for debugging purposes
            System.out.println("******** " + i + "*********"); //$NON-NLS-1$ //$NON-NLS-2$
            System.out.println(msg);
        }// end for
    }
}
