package com.tibco.xpd.analyst.qa.test;

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
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Test for creating mappings from Process to a WebService soap request. This
 * test creates a copy of an XPDL that already contains the ServiceTask and WSDL
 * containing WebService component.
 * 
 * @author sghani
 * 
 */
public class WebServiceMappingTest extends TestCase {

    /** There will be no cleaning after test if this is set to false */
    private static boolean SHOULD_CLEAN = true;

    /** The test plugin ID */
    private static final String PLUGIN_ID = "com.tibco.xpd.analyst.qa.test"; //$NON-NLS-1$

    /** Name of the test project. */
    protected static String TEST_PROJECT_NAME = "WSTestProject"; //$NON-NLS-1$

    /** Sample model file name */
    protected static String XPDL_MODEL_FILE = "TestProcess3.xpdl"; //$NON-NLS-1$

    /** Special folder name */
    private static final String FOLDER_NAME = "TestProcessPackage"; //$NON-NLS-1$

    /** Name of Process used for Testing */
    private static final String PROCESS_NAME = "TestProcess"; //$NON-NLS-1$

    /** Name of Service Descriptor folder */
    private static final String SERVICE_FOLDER_NAME = "TestService"; //$NON-NLS-1$

    /** Service Descriptor folder kind */
    private static final String SERVICE_FOLDER_KIND = "wsdl"; //$NON-NLS-1$

    /** Name of WSDL file */
    private static final String WSDL_FILE = "Cargo_FlightPlan.wsdl"; //$NON-NLS-1$

    /** Name of the ScriptTask activity */
    private static final String TASK_NAME = "ServiceTaskTest"; //$NON-NLS-1$

    /** Name of the DataFields */
    private static final String FIELD_NAME_1 = "StringField"; //$NON-NLS-1$

    private static final String FIELD_NAME_2 = "IntegerField"; //$NON-NLS-1$

    private static final String FIELD_NAME_3 = "DateField"; //$NON-NLS-1$

    private IProject project;

    private IFile xpdlFile;

    private IFile wsdlFile;

    /**
     * This test copies an XPDL containing a ServiceTask and WSDL that has
     * already been imported. However, the ServiceTask has no mappings to/from
     * WebService components. In the test, we add two input and one output
     * simple mapping to/from Process-WebService. Once the mappings are added,
     * the error markers disappear.
     * 
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void testAddWebServiceMapping() throws Exception {

        this.setName("WebServiceMappingTest.testAddWebServiceMapping()"); //$NON-NLS-1$

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

        // Verify the DataFields of the process exist as needed in mapping
        DataField strField =
                AnalystTestUtil.getDataField(process, FIELD_NAME_1);
        assertNotNull("Unable to retrieve parameter " + FIELD_NAME_1, strField); //$NON-NLS-1$
        DataField numField =
                AnalystTestUtil.getDataField(process, FIELD_NAME_2);
        assertNotNull("Unable to retrieve parameter " + FIELD_NAME_2, numField); //$NON-NLS-1$
        DataField dateField =
                AnalystTestUtil.getDataField(process, FIELD_NAME_3);
        assertNotNull("Unable to retrieve parameter " + FIELD_NAME_3, dateField); //$NON-NLS-1$

        // Get a count of current IN and OUT mappings, both should be zero
        List<DataMapping> mappings =
                Xpdl2ModelUtil.getDataMappings(act, DirectionType.IN_LITERAL);
        int inMappings = mappings.size();
        mappings =
                Xpdl2ModelUtil.getDataMappings(act, DirectionType.OUT_LITERAL);
        int outMappings = mappings.size();

        /*
         * Add a mapping from process to WS element Sample DataMapping from XPDL
         * file: <xpdl2:DataMappings> <xpdl2:DataMapping Direction="IN"
         * Formal="wso:processRequest/part:CorticonRequest/@decisionServiceName[0]"
         * > <xpdl2:Actual ScriptGrammar="XPath">StringField</xpdl2:Actual>
         * </xpdl2:DataMapping> NOTE: The 'Formal' attribute refers to the
         * target The 'Actual' element refers to the source
         */
        String targetPath =
                "wso:processRequest/part:CorticonRequest/@decisionServiceName[0]"; //$NON-NLS-1$
        boolean added =
                AnalystTestUtil.addSimpleMapping(wc,
                        act,
                        FIELD_NAME_1,
                        targetPath,
                        MappingDirection.IN);
        assertTrue("Unable to execute invalid Add Mapping command", added); //$NON-NLS-1$
        // Add another input mapping that is required by the WebService
        targetPath =
                "wso:processRequest/part:CorticonRequest/group:sequence[3]/tns:WorkDocuments[0]/group:choice[1]{0}/tns:Aircraft[0]/group:sequence[2]/tns:maxCargoVolume[1]"; //$NON-NLS-1$
        added =
                AnalystTestUtil.addSimpleMapping(wc,
                        act,
                        FIELD_NAME_2,
                        targetPath,
                        MappingDirection.IN);
        assertTrue("Unable to execute invalid Add Mapping command", added); //$NON-NLS-1$
        // Add a output mapping from WS response to process parameter
        String sourcePath =
                "wso:processRequest/part:CorticonResponse/@decisionServiceEffectiveTimestamp[2]"; //$NON-NLS-1$
        added =
                AnalystTestUtil.addSimpleMapping(wc,
                        act,
                        sourcePath,
                        FIELD_NAME_3,
                        MappingDirection.OUT);

        // Save the working copy of XPDL model
        if (wc.isWorkingCopyDirty()) {
            System.out.println("Saving the XPDL model's working copy."); //$NON-NLS-1$
            wc.save();
        }

        TestUtil.waitForJobsEx(ResourcesPlugin.FAMILY_AUTO_BUILD);

        // Now check the INPUT DataMapping count
        mappings =
                Xpdl2ModelUtil.getDataMappings(act, DirectionType.IN_LITERAL);
        // Check there are two additional input mappings and
        // one additional output mapping than before
        assertEquals((inMappings + 2), mappings.size());
        System.out.println("IN Mappings Count: " + mappings.size()); //$NON-NLS-1$
        mappings =
                Xpdl2ModelUtil.getDataMappings(act, DirectionType.OUT_LITERAL);
        assertEquals((outMappings + 1), mappings.size());
        System.out.println("OUT Mappings Count: " + mappings.size()); //$NON-NLS-1$

        // Verify no error markers raised since all needed mappings are added
        List<IMarker> errors = AnalystTestUtil.getErrorMarkers(xpdlFile);
        assertEquals("Expected no errors in XPDL. Got...\n" //$NON-NLS-1$
                + TestUtil.markersToString(errors), 0, errors.size());
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
        SpecialFolder packageFolder =
                TestUtil.createSpecialFolder(project,
                        FOLDER_NAME,
                        Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND);
        SpecialFolder serviceFolder =
                TestUtil.createSpecialFolder(project,
                        SERVICE_FOLDER_NAME,
                        SERVICE_FOLDER_KIND);

        // Copy existing XPDL model to package special folder
        xpdlFile =
                AnalystTestUtil.createFileFromResource(PLUGIN_ID,
                        packageFolder,
                        XPDL_MODEL_FILE);
        assertNotNull("Unable to copy XPDL from resource", xpdlFile); //$NON-NLS-1$
        // Copy existing WSDL file to service special folder
        wsdlFile =
                AnalystTestUtil.createFileFromResource(PLUGIN_ID,
                        serviceFolder,
                        WSDL_FILE);
        assertNotNull("Unable to copy WSDL from resource", wsdlFile); //$NON-NLS-1$
        TestUtil.waitForJobsEx(ResourcesPlugin.FAMILY_AUTO_BUILD);

        // Check the model is added to project
        IResource resource =
                project.findMember(FOLDER_NAME + "/" + XPDL_MODEL_FILE); //$NON-NLS-1$
        assertNotNull(resource);

        // Rebuild the project because WSDL is included
        try {
            project.build(IncrementalProjectBuilder.FULL_BUILD, null);
        } catch (CoreException e) {
            fail(e.getMessage());
        }
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
