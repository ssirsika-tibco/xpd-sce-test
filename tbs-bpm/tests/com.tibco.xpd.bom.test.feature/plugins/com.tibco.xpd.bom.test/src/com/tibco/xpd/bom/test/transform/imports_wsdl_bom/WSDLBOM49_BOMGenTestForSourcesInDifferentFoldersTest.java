/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.imports_wsdl_bom;

import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.xsdtransform.XsdStereotypeUtils;
import com.tibco.xpd.core.test.util.AbstractBuildingBaseResourceTest;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

import junit.framework.Assert;

/**
 * WSDLBOM49_BOMGenTestForSourcesInDifferentFoldersTest
 * <p>
 * Tests:
 * <li>whether any of the test resources have any problem markers and if so,
 * fails the test.</li>
 * <li>expected number of BOMs are generated.</li>
 * 
 * <p>
 * Note: This test covers hierarchical folder structure in Services Descriptor
 * folder with schema imports using '../' (xpd-6062).
 * </p>
 * 
 * 
 * </p>
 * 
 * 
 * @author
 * @since
 */

public class WSDLBOM49_BOMGenTestForSourcesInDifferentFoldersTest
        extends AbstractBuildingBaseResourceTest {

    private static final String PROJECT_NAME = "Test_6062";

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBuildingBaseResourceTest#setUp()
     * 
     * @throws Exception
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        IProject testProject = getTestProject(PROJECT_NAME);
        assertNotNull(testProject);
        TestUtil.addGlobalDestinationToProject(getTestPlugInId(),
                "BPM", //$NON-NLS-1$
                testProject);
        buildAndWait();
    }

    /**
     * WSDLBOM49_BOMGenTestForSourcesInDifferentFoldersTest
     * 
     * @throws Exception
     */
    public void testWSDLBOM49_BOMGenTestForSourcesInDifferentFoldersTest()
            throws Exception {
        // Check all files created correctly.
        checkTestFilesCreated();

        TestResourceInfo[] testResources = getTestResources();

        /*
         * For each of the test resource, there should be no error markers, and
         * if there is any, fail the test
         */
        for (TestResourceInfo testResource : testResources) {

            IMarker[] markers = testResource.getTestFile()
                    .findMarkers(null, true, IResource.DEPTH_INFINITE);

            boolean hasErrorMarker = false;
            StringBuffer sb = new StringBuffer();

            for (IMarker marker : markers) {
                int markerSeverity = marker.getAttribute(IMarker.SEVERITY,
                        IMarker.SEVERITY_WARNING);
                if (markerSeverity == IMarker.SEVERITY_ERROR) {
                    hasErrorMarker = true;
                    sb.append(marker.getAttribute(IMarker.MESSAGE));
                    sb.append("\n");
                    break;
                }
            }

            if (hasErrorMarker) {
                cleanProjectAtEnd = false;
                fail(String.format(
                        "Found error marker '%1$s' on '%2$s'when none is expected. ",
                        sb.toString(),
                        testResource.getTestFileName()));
                return;
            }
        }

        /*
         * Also check the number of generated BOMs are as expected
         */

        int bomCount = 0;

        IProject testProject = getTestProject(PROJECT_NAME);
        assertNotNull(testProject);

        IFolder generatedBomFolder =
                testProject.getFolder("Generated Business Objects");
        assertNotNull(generatedBomFolder);

        IResource[] members = generatedBomFolder.members();
        for (IResource member : members) {
            if (member instanceof IFile && BOMResourcesPlugin.BOM_FILE_EXTENSION
                    .equals(member.getFileExtension())) {

                bomCount++;

                IFile generatedBOMFile = (IFile) member;

                // check resulting bom file is correct
                WorkingCopy wc = XpdResourcesPlugin.getDefault()
                        .getWorkingCopy(generatedBOMFile);
                assertNotNull(
                        "Cannot create WorkingCopy for newly exported BOM file", //$NON-NLS-1$
                        wc);
                assertTrue("Root element is null or not of type Model", //$NON-NLS-1$
                        wc.getRootElement() instanceof Model);

                Model model = (Model) wc.getRootElement();

                Package currentPackage = model.getNearestPackage();

                Iterator<Profile> profilesIter =
                        model.getAppliedProfiles().iterator();

                Stereotype stereotype = null;

                /*
                 * check that the generated BOMs have expected schemaLocation
                 * attribute values as we now keep the source schema location as
                 * service descriptor sf relative path instead of source file
                 * name.
                 */
                while (profilesIter.hasNext()) {
                    Profile profile = profilesIter.next();

                    if (profile.getName().equals(
                            XsdStereotypeUtils.XSD_NOTATION_PROFILE_NAME)) {
                        Iterator<Stereotype> ownedStereotypesIter =
                                profile.getOwnedStereotypes().iterator();
                        while (ownedStereotypesIter.hasNext()) {
                            stereotype = ownedStereotypesIter.next();
                            if (XsdStereotypeUtils.XSD_BASED_MODEL
                                    .equals(stereotype.getName())) {
                                Object value = currentPackage.getValue(
                                        stereotype,
                                        XsdStereotypeUtils.XSD_SCHEMA_LOCATION);
                                if (member.getName()
                                        .equals("org.example.Main.bom"))
                                    Assert.assertEquals("Main.wsdl",
                                            value.toString());
                                else if (member.getName()
                                        .equals("org.example.Sub1.bom"))
                                    Assert.assertEquals("Sub1/Sub.wsdl",
                                            value.toString());
                                else if (member.getName()
                                        .equals("org.example.AA.bom"))
                                    Assert.assertEquals("AA.xsd",
                                            value.toString());
                                else if (member.getName()
                                        .equals("org.example.Sub1Sub1Sub.bom"))
                                    Assert.assertEquals("Sub1/Sub1Sub/Sub.wsdl",
                                            value.toString());
                                else if (member.getName()
                                        .equals("org.example.Sub2.bom"))
                                    Assert.assertEquals("Sub2/Sub.wsdl",
                                            value.toString());
                                else if (member.getName()
                                        .equals("org.example.xsd1.bom")) {
                                    /*
                                     * Sid XPD-8351 Was too dependent on order
                                     * of XSD's in string. Fixed.
                                     */
                                    Assert.assertTrue(
                                            "Xsds list doesn't contain 'XSDs/XSDOne/xsd1.xsd'",
                                            value.toString().contains(
                                                    "XSDs/XSDOne/xsd1.xsd"));
                                    Assert.assertTrue(
                                            "Xsds list doesn't contain 'XSDs/XSDOne/XSD1_Merge1.xsd'",
                                            value.toString().contains(
                                                    "XSDs/XSDOne/XSD1_Merge1.xsd"));
                                    Assert.assertTrue(
                                            "Xsds list doesn't contain 'XSDs/XSD1_Merge2.xsd'",
                                            value.toString().contains(
                                                    "XSDs/XSD1_Merge2.xsd"));
                                } else if (member.getName()
                                        .equals("org.example.xsd3.bom"))
                                    Assert.assertEquals(
                                            "XSDs/XSDThree/xsd3.xsd",
                                            value.toString());
                                else if (member.getName()
                                        .equals("org.example.XsdMain.bom"))
                                    Assert.assertEquals("XsdMain.xsd",
                                            value.toString());
                                else if (member.getName()
                                        .equals("org.example.XsdSub.bom"))
                                    Assert.assertEquals("XSDs/XsdSub.xsd",
                                            value.toString());
                            }
                        }
                    }
                }
            }
        }

        /*
         * check number of generated BOMs.
         */
        String msg = String.format(
                "Expected number of generated BOMs: %1s, found: %2$s .",
                9,
                bomCount);

        assertEquals(msg, 9, bomCount);

        return;
    }

    @Override
    protected String getTestName() {
        return "WSDLBOM49_BOMGenTestForSourcesInDifferentFoldersTest"; //$NON-NLS-1$
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.bom.test"; //$NON-NLS-1$
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources = new TestResourceInfo[] {
                new TestResourceInfo(
                        "test-resources/bom-xsd/WSDLBOM49_BOMGenTestForSourcesInDifferentFolders", //$NON-NLS-1$
                        "Test_6062/Business Objects{bom}/Test_6062.bom"), //$NON-NLS-1$
                new TestResourceInfo(
                        "test-resources/bom-xsd/WSDLBOM49_BOMGenTestForSourcesInDifferentFolders", //$NON-NLS-1$
                        "Test_6062/Process Packages{processes}/Test_6062.xpdl"), //$NON-NLS-1$
                new TestResourceInfo(
                        "test-resources/bom-xsd/WSDLBOM49_BOMGenTestForSourcesInDifferentFolders", //$NON-NLS-1$
                        "Test_6062/Service Descriptors{wsdl}/AA.xsd"), //$NON-NLS-1$
                new TestResourceInfo(
                        "test-resources/bom-xsd/WSDLBOM49_BOMGenTestForSourcesInDifferentFolders", //$NON-NLS-1$
                        "Test_6062/Service Descriptors{wsdl}/Main.wsdl"), //$NON-NLS-1$
                new TestResourceInfo(
                        "test-resources/bom-xsd/WSDLBOM49_BOMGenTestForSourcesInDifferentFolders", //$NON-NLS-1$
                        "Test_6062/Service Descriptors{wsdl}/Sub1/Sub.wsdl"), //$NON-NLS-1$
                new TestResourceInfo(
                        "test-resources/bom-xsd/WSDLBOM49_BOMGenTestForSourcesInDifferentFolders", //$NON-NLS-1$
                        "Test_6062/Service Descriptors{wsdl}/Sub1/Sub1Sub/Sub.wsdl"), //$NON-NLS-1$
                new TestResourceInfo(
                        "test-resources/bom-xsd/WSDLBOM49_BOMGenTestForSourcesInDifferentFolders", //$NON-NLS-1$
                        "Test_6062/Service Descriptors{wsdl}/Sub2/Sub.wsdl"), //$NON-NLS-1$
                new TestResourceInfo(
                        "test-resources/bom-xsd/WSDLBOM49_BOMGenTestForSourcesInDifferentFolders", //$NON-NLS-1$
                        "Test_6062/Service Descriptors{wsdl}/XSDs/XSD1_Merge2.xsd"), //$NON-NLS-1$
                new TestResourceInfo(
                        "test-resources/bom-xsd/WSDLBOM49_BOMGenTestForSourcesInDifferentFolders", //$NON-NLS-1$
                        "Test_6062/Service Descriptors{wsdl}/XSDs/XSDOne/XSD1_Merge1.xsd"), //$NON-NLS-1$
                new TestResourceInfo(
                        "test-resources/bom-xsd/WSDLBOM49_BOMGenTestForSourcesInDifferentFolders", //$NON-NLS-1$
                        "Test_6062/Service Descriptors{wsdl}/XSDs/XSDOne/xsd1.xsd"), //$NON-NLS-1$
                new TestResourceInfo(
                        "test-resources/bom-xsd/WSDLBOM49_BOMGenTestForSourcesInDifferentFolders", //$NON-NLS-1$
                        "Test_6062/Service Descriptors{wsdl}/XSDs/XSDOne/xsd1b.xsd"), //$NON-NLS-1$
                new TestResourceInfo(
                        "test-resources/bom-xsd/WSDLBOM49_BOMGenTestForSourcesInDifferentFolders", //$NON-NLS-1$
                        "Test_6062/Service Descriptors{wsdl}/XSDs/XSDThree/xsd3.xsd"), //$NON-NLS-1$
                new TestResourceInfo(
                        "test-resources/bom-xsd/WSDLBOM49_BOMGenTestForSourcesInDifferentFolders", //$NON-NLS-1$
                        "Test_6062/Service Descriptors{wsdl}/XSDs/XSDTwo/xsd2.xsd"), //$NON-NLS-1$
                new TestResourceInfo(
                        "test-resources/bom-xsd/WSDLBOM49_BOMGenTestForSourcesInDifferentFolders", //$NON-NLS-1$
                        "Test_6062/Service Descriptors{wsdl}/XSDs/XsdSub.xsd"), //$NON-NLS-1$
                new TestResourceInfo(
                        "test-resources/bom-xsd/WSDLBOM49_BOMGenTestForSourcesInDifferentFolders", //$NON-NLS-1$
                        "Test_6062/Service Descriptors{wsdl}/XsdMain.xsd"), //$NON-NLS-1$
        };

        return testResources;
    }

}
