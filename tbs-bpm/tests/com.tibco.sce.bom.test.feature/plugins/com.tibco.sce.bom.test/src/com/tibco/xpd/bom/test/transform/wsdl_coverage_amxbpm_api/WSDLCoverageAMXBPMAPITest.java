package com.tibco.xpd.bom.test.transform.wsdl_coverage_amxbpm_api;

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
import com.tibco.xpd.bom.wsdltransform.api.WSDLTransformUtil;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Tests WSDLs that are shipped with AMX-BPM inside public_api folder
 * 
 * @author glewis
 * 
 */
public class WSDLCoverageAMXBPMAPITest extends TestCase {

    private static final String PLUGIN_ID = "com.tibco.xpd.bom.test";

    private static final String RESOURCES_FOLDER_PATH =
            "test-resources/WSDL-AMXBPM-API";

    private static final String GOLD_FOLDER = "gold";

    private static final String SERVICES_FOLDER = "services";

    private Bundle bundle;

    private IProject project;

    private IFolder folder;

    public WSDLCoverageAMXBPMAPITest() {
        bundle = Platform.getBundle(PLUGIN_ID);
    }

    @Override
    protected void setUp() throws Exception {
        project = TestUtil.createProject("WsdlAPIToBomProject");
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
     * Test case 01
     * 
     * @throws Exception
     */
    public void testWSDL01() throws Exception {
        runTest("WSDL01",
                "busserv.wsdl",
                "channeltype.xsd",
                "comexception.xsd",
                "datamodel.xsd",
                "df-payload.xsd",
                "pfe-business-service.xsd",
                "pfe-common.xsd",
                "pfe-exception.xsd",
                "presentationmodel.xsd");
    }

    /**
     * Test case 02
     * 
     * @throws Exception
     */
    public void testWSDL02() throws Exception {
        runTest("WSDL02",
                "dac.wsdl",
                "comexception.xsd",
                "dac-calendar-service.xsd",
                "dac-common.xsd",
                "dac-deadline-service.xsd",
                "dac-exception.xsd");
    }

    /**
     * Test case 03
     * 
     * @throws Exception
     */
    public void testWSDL03() throws Exception {
        runTest("WSDL03",
                "de.wsdl",
                "channeltype.xsd",
                "comexception.xsd",
                "de-attribute-service.xsd",
                "de-browse-service.xsd",
                "de-container-service.xsd",
                "de-exporter-service.xsd",
                "de-ldap-service.xsd",
                "de-ldap.xsd",
                "de-mapping-service.xsd",
                "de-notification-service.xsd",
                "de-resolver-service.xsd",
                "de-resourcequery-service.xsd",
                "de-security-service.xsd",
                "de-usersettings-service.xsd",
                "de.xsd",
                "deexception.xsd",
                "organisation.xsd");
    }

    /**
     * Test case 04
     * 
     * @throws Exception
     */
    public void testWSDL04() throws Exception {
        runTest("WSDL04",
                "ec.wsdl",
                "comexception.xsd",
                "ec-basetypes.xsd",
                "ec.xsd",
                "ecexception.xsd");
    }

    /**
     * Test case 05
     * 
     * @throws Exception
     */
    public void testWSDL05() throws Exception {
        runTest("WSDL05",
                "pflow.wsdl",
                "channeltype.xsd",
                "comexception.xsd",
                "datamodel.xsd",
                "df-payload.xsd",
                "pfe-common.xsd",
                "pfe-exception.xsd",
                "pfe-pageflow-service.xsd",
                "presentationmodel.xsd");
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

        TestUtil.buildAndWait();
        TestUtil.waitForJobs();

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

                assertEquals(goldModel.getName(), generatedModel.getName());
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
