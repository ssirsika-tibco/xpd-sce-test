package com.tibco.xpd.bom.test.transform.wsdl_coverage_amx_bpm;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.XMLResource.URIHandler;
import org.eclipse.emf.ecore.xmi.impl.URIHandlerImpl;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.uml2.uml.Model;
import org.osgi.framework.Bundle;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.test.transform.util.BOMCompareUtil;
import com.tibco.xpd.bom.wsdltransform.api.WSDLTransformUtil;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Tests WSDLs defined in
 * http://wiki.tibco.com/BIWiki/WSDL_Coverage_Across_Active_Matrix.
 * 
 * @author njpatel
 * 
 */
public class WSDLCoverageAMXBPMTest extends TestCase {

    private static final String PLUGIN_ID = "com.tibco.xpd.bom.test";

    private static final String RESOURCES_FOLDER_PATH =
            "test-resources/WSDLCoverageAMXBPM";

    private static final String GOLD_FOLDER = "gold";

    private static final String SERVICES_FOLDER = "services";

    private Bundle bundle;

    private IProject project;

    private IFolder folder;

    public WSDLCoverageAMXBPMTest() {
        bundle = Platform.getBundle(PLUGIN_ID);
    }

    @Override
    protected void setUp() throws Exception {
        project = TestUtil.createProject("WsdlToBomProject");
        assertTrue("Failed to create project",
                project != null && project.isAccessible());

        SpecialFolder sf =
                TestUtil.createSpecialFolder(project,
                        "businessModels",
                        BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND);
        assertTrue("Cannot create BOM special folder",
                sf != null && sf.getFolder() != null && sf.getFolder().exists());
        folder = sf.getFolder();
    }

    @Override
    protected void tearDown() throws Exception {
        if (project != null) {
            project.delete(true, null);
        }
    }

    /**
     * Test case 01: Schema (XSD) defines the type.
     * 
     * @throws Exception
     */
    public void testWSDL01() throws Exception {
        runTest("WSDL01",
                "Atomic_XSD_Define_Types.wsdl",
                "Atomic_XSD_Define_Types.xsd");
    }

    /**
     * Test case 02: XSD1 imports XSD2, XSD1/XSD2 define the type.
     * 
     * @throws Exception
     */
    public void testWSDL02() throws Exception {
        runTest("WSDL02",
                "Book_XSD1IncludeXSD2.wsdl",
                "Book_importedSchema.xsd",
                "BookXSD1.xsd");
    }

    /**
     * Sid 8/July/13. I HAVE __NOT__ PERFORMED A ANALYSIS OF THE OUTPUT, JUST
     * CHECKED THAT THE RESULTING .bom2xsd's correctly. Therefore if you do find
     * a failure in the future it may well be that you have hit upon a
     * pre-existing problem.
     * 
     * Therefore, on failure, YOU NEED TO CHECK IF THE ORIGINAL GOLDFILE IS
     * ACTUALLY CORRECT!!
     */
    public void testWSDL03() throws Exception {
        runTest("WSDL03",
                "Foo_XSD1IncludesXSD2_BothDefineTypes.wsdl",
                "Foo_XSD1includingXSD2.xsd",
                "IncludedXSD2.xsd");
    }

    /**
     * Sid 8/July/13. I HAVE __NOT__ PERFORMED A ANALYSIS OF THE OUTPUT, JUST
     * CHECKED THAT THE RESULTING .bom2xsd's correctly. Therefore if you do find
     * a failure in the future it may well be that you have hit upon a
     * pre-existing problem.
     * 
     * Therefore, on failure, YOU NEED TO CHECK IF THE ORIGINAL GOLDFILE IS
     * ACTUALLY CORRECT!!
     */
    public void testWSDL04() throws Exception {
        runTest("WSDL04",
                "OnLineShopping_XSD1importXSD2_XSD1IncludeXSD3.wsdl",
                "ImportedXSD2.xsd",
                "IncludedXSD3.xsd",
                "XSD1.xsd");
    }

    /**
     * Test case 05: WSDL defines the type.
     * 
     * @throws Exception
     */
    public void testWSDL05() throws Exception {
        runTest("WSDL05", "Genric_WSDL_Define_Types.wsdl");
    }

    /**
     * Sid 8/July/13. I HAVE __NOT__ PERFORMED A ANALYSIS OF THE OUTPUT, JUST
     * CHECKED THAT THE RESULTING .bom2xsd's correctly. Therefore if you do find
     * a failure in the future it may well be that you have hit upon a
     * pre-existing problem.
     * 
     * Therefore, on failure, YOU NEED TO CHECK IF THE ORIGINAL GOLDFILE IS
     * ACTUALLY CORRECT!!
     */
    public void testWSDL06() throws Exception {
        runTest("WSDL06", "Amazon_WSDLEmbedsSchema_SchemaDefineTypes.wsdl");
    }

    /**
     * Sid 8/July/13. I HAVE __NOT__ PERFORMED A ANALYSIS OF THE OUTPUT, JUST
     * CHECKED THAT THE RESULTING .bom2xsd's correctly. Therefore if you do find
     * a failure in the future it may well be that you have hit upon a
     * pre-existing problem.
     * 
     * Therefore, on failure, YOU NEED TO CHECK IF THE ORIGINAL GOLDFILE IS
     * ACTUALLY CORRECT!!
     */
    public void testWSDL07() throws Exception {
        runTest("WSDL07",
                "Query_wsdlEmbedSchema1_Schema1ImportXSD2.wsdl",
                "schemas/airline/Airline.xsd",
                "schemas/car/Car.xsd",
                "schemas/hotel/Hotel.xsd");
    }

    /**
     * Test case 08: WSDL embeds Schema1, Schema1 includes Schema2,
     * Schema1/Schema2 define the type.
     * 
     * @throws Exception
     */
    public void testWSDL08() throws Exception {
        runTest("WSDL08",
                "Profile_WSDLEmbedsSchema1_Schema1IncludeSchema2L.wsdl",
                "profile/AirlineUserProfileL.xsd");
    }

    /**
     * Test case 09: WSDL embeds Schema1, WSDL embeds Schema2, Schema1 imports
     * Schema2, Schema1/Schema2 define the type.
     * 
     * @throws Exception
     */
    public void testWSDL09() throws Exception {
        runTest("WSDL09", "Profile_WSLEmbedSchma12_Schema1ImprotSchema2.wsdl");
    }

    /**
     * Test case 10: WSDL embeds Schema1, WSDL embeds Schema2, Schema1 includes
     * Schema2, Schema1/Schema2 define the type.
     * 
     * @throws Exception
     */
    public void testWSDL10() throws Exception {
        runTest("WSDL10",
                "Profile_WSDLEmbedSchema12_Schema1includeSchema2.wsdl",
                "ProfileSchema.xsd",
                "profile/CarUserProfile.xsd");
    }

    /**
     * Test case 11: WSDL imports Schema, Schema defines the type.
     * 
     * @throws Exception
     */
    public void testWSDL11() throws Exception {
        runTest("WSDL11", "Scenario11.wsdl", "Scenario11_ImportSchema2.xsd");
    }

    /**
     * Sid 8/July/13. I HAVE __NOT__ PERFORMED A ANALYSIS OF THE OUTPUT, JUST
     * CHECKED THAT THE RESULTING .bom2xsd's correctly. Therefore if you do find
     * a failure in the future it may well be that you have hit upon a
     * pre-existing problem.
     * 
     * Therefore, on failure, YOU NEED TO CHECK IF THE ORIGINAL GOLDFILE IS
     * ACTUALLY CORRECT!!
     */
    public void testWSDL12() throws Exception {
        runTest("WSDL12",
                "Book_XSD1IncludeXSD2.wsdl",
                "Book_importedSchema.xsd",
                "BookXSD1.xsd");
    }

    /**
     * Sid 8/July/13. I HAVE __NOT__ PERFORMED A ANALYSIS OF THE OUTPUT, JUST
     * CHECKED THAT THE RESULTING .bom2xsd's correctly. Therefore if you do find
     * a failure in the future it may well be that you have hit upon a
     * pre-existing problem.
     * 
     * Therefore, on failure, YOU NEED TO CHECK IF THE ORIGINAL GOLDFILE IS
     * ACTUALLY CORRECT!!
     */
    public void testWSDL13() throws Exception {
        runTest("WSDL13", "TopONE.wsdl", "Library.xsd", "LibraryBook.xsd");
    }

    /**
     * Sid 8/July/13. I HAVE __NOT__ PERFORMED A ANALYSIS OF THE OUTPUT, JUST
     * CHECKED THAT THE RESULTING .bom2xsd's correctly. Therefore if you do find
     * a failure in the future it may well be that you have hit upon a
     * pre-existing problem.
     * 
     * Therefore, on failure, YOU NEED TO CHECK IF THE ORIGINAL GOLDFILE IS
     * ACTUALLY CORRECT!!
     */
    public void testWSDL14() throws Exception {
        runTest("WSDL14",
                "ctms_WSDLimportsSchema1Schema2.wsdl",
                "Schema/ctM002.xsd",
                "Schema/groupH019.xsd");
    }

    /**
     * Test case 15: WSDL imports Schema1, WSDL imports Schema2, Schema1 imports
     * Schema2, Schema1/Schema2 defines the type.
     * 
     * @throws Exception
     */
    public void testWSDL15() throws Exception {
        /*
         * SID 8/Jul/13 - Note that I saw one instance of failure "couponPin"
         * already paired when running this test but it was not reproducible.
         */
        runTest("WSDL15",
                "AtomicRPX.wsdl",
                "AtomicRPX/AtomicRPXChangeCouponStatus.xsd",
                "AtomicRPX/AtomicRPXQueryCoupons.xsd",
                "AtomicRPX/AtomicRPXTypes.xsd");
    }

    /**
     * Sid 8/July/13. I HAVE __NOT__ PERFORMED A ANALYSIS OF THE OUTPUT, JUST
     * CHECKED THAT THE RESULTING .bom2xsd's correctly. Therefore if you do find
     * a failure in the future it may well be that you have hit upon a
     * pre-existing problem.
     * 
     * Therefore, on failure, YOU NEED TO CHECK IF THE ORIGINAL GOLDFILE IS
     * ACTUALLY CORRECT!!
     */
    public void testWSDL19() throws Exception {
        runTest("WSDL19",
                "WSDL1importsWSDL2.wsdl",
                "WSDL2embedsSchema1.wsdl",
                "Contract.xsd");
    }

    /**
     * Sid 8/July/13. I HAVE __NOT__ PERFORMED A ANALYSIS OF THE OUTPUT, JUST
     * CHECKED THAT THE RESULTING .bom2xsd's correctly. Therefore if you do find
     * a failure in the future it may well be that you have hit upon a
     * pre-existing problem.
     * 
     * Therefore, on failure, YOU NEED TO CHECK IF THE ORIGINAL GOLDFILE IS
     * ACTUALLY CORRECT!!
     */
    public void testWSDL20() throws Exception {
        runTest("WSDL20",
                "WSDL1importsWSDL2.wsdl",
                "WSDL2embedsSchema1andSchema2.wsdl");
    }

    /**
     * Sid 8/July/13. I HAVE __NOT__ PERFORMED A ANALYSIS OF THE OUTPUT, JUST
     * CHECKED THAT THE RESULTING .bom2xsd's correctly. Therefore if you do find
     * a failure in the future it may well be that you have hit upon a
     * pre-existing problem.
     * 
     * Therefore, on failure, YOU NEED TO CHECK IF THE ORIGINAL GOLDFILE IS
     * ACTUALLY CORRECT!!
     */
    public void testWSDL21() throws Exception {
        runTest("WSDL21",
                "Flex_wsdl2.wsdl",
                "fixml-datatypes-4-4.xsd",
                "SecondIncludeSchema.xsd");
    }

    /**
     * Sid 8/July/13. I HAVE __NOT__ PERFORMED A ANALYSIS OF THE OUTPUT, JUST
     * CHECKED THAT THE RESULTING .bom2xsd's correctly. Therefore if you do find
     * a failure in the future it may well be that you have hit upon a
     * pre-existing problem.
     * 
     * Therefore, on failure, YOU NEED TO CHECK IF THE ORIGINAL GOLDFILE IS
     * ACTUALLY CORRECT!!
     */
    public void testWSDL22() throws Exception {
        runTest("WSDL22",
                "WSDL1.wsdl",
                "EmbedWSDL.wsdl",
                "Schema/common-messages.xsd");
    }

    /**
     * Sid 8/July/13. I HAVE __NOT__ PERFORMED A ANALYSIS OF THE OUTPUT, JUST
     * CHECKED THAT THE RESULTING .bom2xsd's correctly. Therefore if you do find
     * a failure in the future it may well be that you have hit upon a
     * pre-existing problem.
     * 
     * Therefore, on failure, YOU NEED TO CHECK IF THE ORIGINAL GOLDFILE IS
     * ACTUALLY CORRECT!!
     */
    public void testWSDL23() throws Exception {
        runTest("WSDL23",
                "Unit.wsdl",
                "Schema/common-messages.xsd",
                "Schema/portfolio-stock-messages.xsd");
    }

    /**
     * Sid 8/July/13. I HAVE __NOT__ PERFORMED A ANALYSIS OF THE OUTPUT, JUST
     * CHECKED THAT THE RESULTING .bom2xsd's correctly. Therefore if you do find
     * a failure in the future it may well be that you have hit upon a
     * pre-existing problem.
     * 
     * Therefore, on failure, YOU NEED TO CHECK IF THE ORIGINAL GOLDFILE IS
     * ACTUALLY CORRECT!!
     */
    public void testWSDL24() throws Exception {
        runTest("WSDL24",
                "DescribtionBWWSDL1.wsdl",
                "SchoolWSDL2.wsdl",
                "profile/AirlineUserProfile.xsd",
                "profile/CarUserProfile.xsd",
                "profile/HotelUserProfile.xsd",
                "profile/UserProfile.xsd");
    }

    /**
     * Sid 8/July/13. I HAVE __NOT__ PERFORMED A ANALYSIS OF THE OUTPUT, JUST
     * CHECKED THAT THE RESULTING .bom2xsd's correctly. Therefore if you do find
     * a failure in the future it may well be that you have hit upon a
     * pre-existing problem.
     * 
     * Therefore, on failure, YOU NEED TO CHECK IF THE ORIGINAL GOLDFILE IS
     * ACTUALLY CORRECT!!
     */
    public void testWSDL25() throws Exception {
        runTest("WSDL25",
                "WSDL2.wsdl",
                "Schema/HotelUserProfile.xsd",
                "Schema/SchemaName.xsd");
    }

    /**
     * Run a test for the wsdl details given.
     * 
     * @param testName
     *            name of the test - this will be the main folder name under the
     *            "test-resources/wsdl"
     * @param wsdlFileName
     *            name of the wsdl file (that will be in the services folder in
     *            the test-resource's test folder).
     * @throws CoreException
     */
    private void runTest(String testName, String wsdlFileName,
            String... supportingFiles) throws CoreException {
        assertNotNull("No test name provided.", testName);
        assertNotNull("No WSDL file name provided.", wsdlFileName);

        IPath wsdlFilePath =
                new Path(testName).append(SERVICES_FOLDER).append(wsdlFileName);

        final File wsdlFile = createFile(bundle, wsdlFilePath.toString());

        assertTrue("Cannot find file: " + wsdlFilePath, wsdlFile != null
                && wsdlFile.exists());

        for (String supportingFile : supportingFiles) {
            IPath path =
                    new Path(testName).append(SERVICES_FOLDER)
                            .append(supportingFile);
            createFile(bundle, path.toString());
        }

        /*
         * Create a dummy file to pass to the transformation - it seems that the
         * file is only used to get the target folder. This filename will be
         * ignored. Instead a filename based on the namespace of the wsdl will
         * be created.
         */
        final IFile file =
                folder.getFile(testName + "."
                        + BOMResourcesPlugin.BOM_FILE_EXTENSION);

        final List<IStatus> result = new ArrayList<IStatus>();

        WorkspaceModifyOperation op = new WorkspaceModifyOperation() {

            @Override
            protected void execute(IProgressMonitor monitor)
                    throws CoreException, InvocationTargetException,
                    InterruptedException {
                result.addAll(WSDLTransformUtil.transformWSDLtoBOM(wsdlFile,
                        file.getFullPath(),
                        null));

            }
        };

        try {
            op.run(null);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (IStatus res : result) {
            if (res.getSeverity() == IStatus.ERROR) {
                fail("Transform failed: " + res.getMessage());
            }
        }

        validateGeneratedBOMs(project, testName);
    }

    /**
     * Compare the generated BOMs with the gold file.
     * 
     * @param project
     * @param testName
     * @throws CoreException
     */
    private void validateGeneratedBOMs(IProject project, String testName)
            throws CoreException {
        IResource[] members = folder.members();

        // Temp resource set to load the gold files into
        ResourceSet goldResourceSet = new ResourceSetImpl();
        URIHandler uriHandler = new URIHandlerImpl();
        uriHandler.setBaseURI(getGoldFolder(testName));
        goldResourceSet.getLoadOptions().put(XMIResource.OPTION_URI_HANDLER,
                uriHandler);

        BOMCompareUtil compareUtil = new BOMCompareUtil();
        for (IResource member : members) {
            if (member instanceof IFile
                    && BOMResourcesPlugin.BOM_FILE_EXTENSION.equals(member
                            .getFileExtension())) {
                IFile generatedFile = (IFile) member;
                URI goldFileURI = getGoldFile(testName, generatedFile);

                // Load the generated Model
                WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(generatedFile);
                assertNotNull("Cannot get working copy for: "
                        + generatedFile.getFullPath().toString(), wc);
                assertTrue("Model not found for: "
                        + generatedFile.getFullPath().toString(),
                        wc.getRootElement() instanceof Model);
                Model generatedModel = (Model) wc.getRootElement();

                // Load the gold Model
                Resource res = goldResourceSet.getResource(goldFileURI, true);
                assertNotNull("Cannot load GOLD file", res);

                // Reset the URI so that the resolver can find it as the
                // reference in the gold file will be SpecialFolder relative
                res.setURI(URI.createURI(generatedFile.getName()));
                EList<EObject> contents = res.getContents();
                assertFalse("No contents in GOLD file", contents.isEmpty());
                Model goldModel = null;
                for (EObject eo : contents) {
                    if (eo instanceof Model) {
                        goldModel = (Model) eo;
                        break;
                    }
                }
                assertNotNull("Cannot find Model in GOLD file.", goldModel);

                System.out.println("COMPARING: " + generatedFile.getName());

                IStatus status = compareUtil.compare(goldModel, generatedModel);
                if (!status.isOK()) {
                    fail(String
                            .format("Compare failed with the gold file[%s]: %s",
                                    generatedFile.getName(),
                                    status.getMessage()));
                }
            }
        }
    }

    /**
     * Get the gold file URI that corresponds to the generated BOM given.
     * 
     * @param testName
     * @param generatedFile
     * @return
     */
    private URI getGoldFile(String testName, IFile generatedFile) {
        return getGoldFolder(testName).appendSegment(generatedFile.getName());
    }

    /**
     * Get the URI to the folder containing the gold files for the given test.
     * 
     * @param testName
     * @return
     */
    private URI getGoldFolder(String testName) {
        IPath path =
                new Path(PLUGIN_ID).append(RESOURCES_FOLDER_PATH)
                        .append(testName).append(GOLD_FOLDER);
        return URI.createPlatformPluginURI(path.toString(), true);
    }

    /**
     * Get the file with the given relative path.
     * 
     * @param bundle
     * @param wsdlFilePath
     * @return
     */
    private File createFile(Bundle bundle, String wsdlFilePath) {

        TestResourceInfo tri =
                new TestResourceInfo(RESOURCES_FOLDER_PATH, wsdlFilePath);

        try {
            IFile iFile = tri.createFile(bundle.getSymbolicName());

            assertTrue("Failed to copy file from bundle to test project: "
                    + tri.getTestFilePath(), iFile.exists());

            File file = iFile.getLocation().toFile();

            assertTrue("Java I/O copy of test resource in test project was not created: "
                    + file.getAbsolutePath(),
                    file.exists());

            return file;

        } catch (Exception e) {
            fail("Exception copying test file '" + tri.getTestFilePath()
                    + "' to test project: " + e.getMessage());
        }

        return null;
    }
}
