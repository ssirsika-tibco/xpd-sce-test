/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.bds.designtime.generator.test;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;

import com.tibco.bds.designtime.generator.ModelGenerator;
import com.tibco.bds.designtime.generator.test.util.diff.Differ;
import com.tibco.bds.designtime.generator.test.util.diff.Difference;
import com.tibco.xpd.bom.wsdltransform.api.WSDLTransformUtil;

/**
 * Tests the ability to import WSDLs in order to create a BOM
 * 
 */
public class WsdlImportGenerationTest extends BaseTest {

    /**
     * @see com.tibco.bds.designtime.generator.test.BaseTest#setUp()
     * 
     * @throws CoreException
     */
    public void setUp() throws CoreException {
        super.setUp();
        s_ignoreNames = new ArrayList<String>(s_ignoreNames);
        s_ignoreNames.add(".*\\.bom"); //$NON-NLS-1$
    }

    // Set of tests for checking the import of WSDLs
    // Note - referenced schemas get included in the required EMF generation
    // with these test - they do not in the real system
    // The Coverage WSDLs are taken from:
    // http://wiki.tibco.com/BIWiki/WSDL_Coverage_Across_Active_Matrix
    // Studio Support:
    // http://wiki.tibco.com/StudioWiki/WsdlXsdBomTests

    public void testWsdlCoverageNo1() throws Exception {
        doWsdlImport("CoverageNo01/Atomic_XSD_Define_Types.wsdl", //$NON-NLS-1$
                new String[] { "CoverageNo01/Atomic_XSD_Define_Types.xsd" }, //$NON-NLS-1$
                "com.amsbqa.linda01.XSDDefineTypes.types.bom", //$NON-NLS-1$
                null);
    }

    public void testWsdlCoverageNo2() throws Exception {
        doWsdlImport("CoverageNo02/Book_XSD1IncludeXSD2.wsdl", //$NON-NLS-1$
                new String[] { "CoverageNo02/Book_importedSchema.xsd", //$NON-NLS-1$
                        "CoverageNo02/BookXSD1.xsd" }, //$NON-NLS-1$
                "com.amsbqa.Linda02.XSD1IncludeXSD2.BothDefineTypes.wsdl.bom", //$NON-NLS-1$
                new String[] { "com.amsbqa.Linda02.XSD1importXSD2.XSD2.bom", //$NON-NLS-1$
                        "com.amsbqa.linda02.XSD1ImportsXSD2_Both_Define_types02.XSD1.bom" }); //$NON-NLS-1$
    }

    public void testWsdlCoverageNo3() throws Exception {
        doWsdlImport("CoverageNo03/Foo_XSD1IncludesXSD2_BothDefineTypes.wsdl", //$NON-NLS-1$
                new String[] { "CoverageNo03/Foo_XSD1includingXSD2.xsd", //$NON-NLS-1$
                        "CoverageNo03/IncludedXSD2.xsd" }, //$NON-NLS-1$
                "com.amsbqa.linda03.XSD1IncludesXSD2.BothDefineTypes.wsdl.bom", //$NON-NLS-1$
                new String[] { "com.amsbqa.linda03.XSD1IncludesXSD2_Both_Define_Types03.bom" }); //$NON-NLS-1$
    }

    public void testWsdlCoverageNo4() throws Exception {
        doWsdlImport("CoverageNo04/OnLineShopping_XSD1importXSD2_XSD1IncludeXSD3.wsdl", //$NON-NLS-1$
                new String[] { "CoverageNo04/ImportedXSD2.xsd", //$NON-NLS-1$
                        "CoverageNo04/XSD1.xsd", //$NON-NLS-1$
                        "CoverageNo04/IncludedXSD3.xsd" }, //$NON-NLS-1$
                "amsbqa.Linda04.XSD1importXSD2.XSD1IncludeXSD3.allDefineTypes.wsdl.bom", //$NON-NLS-1$
                new String[] {
                        "com.amsbqa.Linda04.XSD1importedXSD2.XSD1IncludesXSD3.allDefinestype.bom", //$NON-NLS-1$
                        "com.amsbqa.Linda04.XSD1importedXSD2.XSD1IncludesXSD3.allDefinestypes.bom" }); //$NON-NLS-1$
    }

    // Note: Coverage Test 5 will not produce a BDS bundle

    public void testWsdlCoverageNo6() throws Exception {
        doWsdlImport("CoverageNo06/Amazon_WSDLEmbedsSchema_SchemaDefineTypes.wsdl", //$NON-NLS-1$
                new String[] {},
                "com.amsbqa.linda06.WSDLEmbedsSchema.SchemaDefineTypes.wsdl.bom", //$NON-NLS-1$
                null);
    }

    public void testWsdlCoverageNo7() throws Exception {
        doWsdlImport("CoverageNo07/Query_wsdlEmbedSchema1_Schema1ImportXSD2.wsdl", //$NON-NLS-1$
                new String[] { "CoverageNo07/schemas/airline/Airline.xsd", //$NON-NLS-1$
                        "CoverageNo07/schemas/car/Car.xsd", //$NON-NLS-1$
                        "CoverageNo07/schemas/hotel/Hotel.xsd" }, //$NON-NLS-1$
                "com.amsbqa.Linda07.WSDLEmbedXSD1.XSD1importXSD2.Query.wsdl.bom", //$NON-NLS-1$
                new String[] { "xyzcorp.procureservice.Airline.bom", //$NON-NLS-1$
                        "xyzcorp.procureservice.Car.bom", //$NON-NLS-1$
                        "xyzcorp.procureservice.Hotel.bom" }); //$NON-NLS-1$
    }

    public void testWsdlCoverageNo8() throws Exception {
        doWsdlImport("CoverageNo08/Profile_WSDLEmbedsSchema1_Schema1IncludeSchema2L.wsdl", //$NON-NLS-1$
                new String[] { "CoverageNo08/AirlineUserProfileL.xsd" }, //$NON-NLS-1$
                "com.amsbqa.linda08.WSDLEmbedSchema1.Schema1IncludeSchema2.bom", //$NON-NLS-1$
                null);
    }

    public void testWsdlCoverageNo9() throws Exception {
        doWsdlImport("CoverageNo09/Profile_WSLEmbedSchma12_Schema1ImprotSchema2.wsdl", //$NON-NLS-1$
                new String[] {},
                "com.amsbqa.Linda09.Schema1importSchema2.WSDL.bom", //$NON-NLS-1$
                new String[] {
                        "com.amsbqa.Linda09.Schema1importSchema2.Schema2.bom", //$NON-NLS-1$
                        "com.amsbqa.Linda09.Schema1importSchema2.Schema1.bom" }); //$NON-NLS-1$
    }

    public void testWsdlCoverageNo10() throws Exception {
        doWsdlImport("CoverageNo10/Profile_WSDLEmbedSchema12_Schema1includeSchema2.wsdl", //$NON-NLS-1$
                new String[] { "CoverageNo10/profile/CarUserProfile.xsd", //$NON-NLS-1$
                        "CoverageNo10/ProfileSchema.xsd" }, //$NON-NLS-1$
                "com.amsbqa.Linda10.Schema1importSchema2.Schema2.bom", //$NON-NLS-1$
                null);
    }

    public void testWsdlCoverageNo11() throws Exception {
        doWsdlImport("CoverageNo11/Scenario11.wsdl", //$NON-NLS-1$
                new String[] { "CoverageNo11/Scenario11_ImportSchema2.xsd" }, //$NON-NLS-1$
                "com.tibco.matrix.qa.xsd.primitives.bom", //$NON-NLS-1$
                null);
    }

    public void testWsdlCoverageNo12() throws Exception {
        doWsdlImport("CoverageNo12/Book_XSD1IncludeXSD2.wsdl", //$NON-NLS-1$
                new String[] { "CoverageNo12/Book_importedSchema.xsd", //$NON-NLS-1$
                        "CoverageNo12/BookXSD1.xsd" }, //$NON-NLS-1$
                "com.amsbqa.Linda02.XSD1IncludeXSD2.BothDefineTypes.wsdl", //$NON-NLS-1$
                new String[] { "com.amsbqa.Linda02.XSD1importXSD2.XSD2.bom", //$NON-NLS-1$
                        "com.amsbqa.linda02.XSD1ImportsXSD2_Both_Define_types02.XSD1.bom" }); //$NON-NLS-1$
    }

    public void testWsdlCoverageNo13() throws Exception {
        doWsdlImport("CoverageNo13/TopONE.wsdl", //$NON-NLS-1$
                new String[] { "CoverageNo13/LibraryBook.xsd", //$NON-NLS-1$
                        "CoverageNo13/Library.xsd" }, //$NON-NLS-1$
                "com.amsbqa.linda13.XSD2Define_Types13.bom", //$NON-NLS-1$
                null);
    }

    public void testWsdlCoverageNo14() throws Exception {
        doWsdlImport("CoverageNo14/ctms_WSDLimportsSchema1Schema2.wsdl", //$NON-NLS-1$
                new String[] { "CoverageNo14/Schema/ctM002.xsd", //$NON-NLS-1$
                        "CoverageNo14/Schema/groupH019.xsd" }, //$NON-NLS-1$
                "com.amsbqa.linda14.WSDLimportsSchema1schema2.WSDL.bom", //$NON-NLS-1$
                new String[] {
                        "com.amsbqa.linda14.WSDLimportsSchema1schema2.schema1.bom", //$NON-NLS-1$
                        "com.amsbqa.linda14.WSDLimportsSchema1schema2.schema2.bom" }); //$NON-NLS-1$
    }

    public void testWsdlCoverageNo15() throws Exception {
        doWsdlImport("CoverageNo15/AtomicRPX.wsdl", //$NON-NLS-1$
                new String[] { "CoverageNo15/AtomicRPX/AtomicRPXTypes.xsd", //$NON-NLS-1$
                        "CoverageNo15/AtomicRPX/AtomicRPXQueryCoupons.xsd", //$NON-NLS-1$
                        "CoverageNo15/AtomicRPX/AtomicRPXChangeCouponStatus.xsd" }, //$NON-NLS-1$
                "com.mobile.t.atomic.rpx.services.bom", //$NON-NLS-1$
                new String[] { "com.mobile.t.esp.atomic.rpx.types.bom", //$NON-NLS-1$
                        "com.mobile.t.esp.atomic.rpx.QueryCoupons.bom", //$NON-NLS-1$
                        "com.mobile.t.esp.atomic.rpx.ChangeCouponStatus.bom" }); //$NON-NLS-1$
    }

    public void testWsdlCoverageNo19() throws Exception {

        // This yields two BOMs. One for the WSDL (which is a 'service' BOM
        // and, therefore, doesn't produce a BDS bundle - thus no known-good
        // ZIP required) and one for the Contract.xsd that we do check
        // against a ZIP.
        doWsdlImport("CoverageNo19/WSDL2embedsSchema1.wsdl", //$NON-NLS-1$
                new String[] { "CoverageNo19/Contract.xsd" }, //$NON-NLS-1$
                "com.amsbqa.linda19.wsdl2embedsSchema1Schema1includesSchema2.bom", //$NON-NLS-1$
                new String[] { "com.amsbqa.linda19.XSD2Define_Types19.bom" }); //$NON-NLS-1$

        // This just produces a 'service' BOM, so no BDS output. We invoke
        // the generation merely to make sure it doesn't fail, rather than
        // actually checking any output.
        doWsdlImport("CoverageNo19/WSDL1importsWSDL2.wsdl", //$NON-NLS-1$
                new String[] { "CoverageNo19/WSDL2embedsSchema1.wsdl", //$NON-NLS-1$
                        "CoverageNo19/Contract.xsd" }, //$NON-NLS-1$
                "com.example.xmlns._1236046527725.bom", //$NON-NLS-1$
                null);
    }

    public void testWsdlCoverageNo20() throws Exception {

        // The WSDL yields three BOMs: A 'service' BOM and two for the
        // embedded schemas within the WSDL. BDS model bundles are generated for
        // the last
        // two and there are ZIPs corresponding to the two 'extra' BOMs here:
        doWsdlImport("CoverageNo20/WSDL2embedsSchema1andSchema2.wsdl", //$NON-NLS-1$
                new String[] {},
                "com.amsbqa.linda20.WSDL2embedsSchema1andSchema2.bom", //$NON-NLS-1$
                new String[] { "com.amsbqa.linda20.Schema2.bom", //$NON-NLS-1$
                        "org.example.Schema1importsSchema2.bom", }); //$NON-NLS-1$

        // This just produces a 'service' BOM, so no BDS output. We invoke
        // the generation merely to make sure it doesn't fail, rather than
        // actually checking any output.
        doWsdlImport("CoverageNo20/WSDL1importsWSDL2.wsdl", //$NON-NLS-1$
                new String[] { "CoverageNo20/WSDL2embedsSchema1andSchema2.wsdl" }, //$NON-NLS-1$
                "com.example.xmlns._1236058394286.bom", //$NON-NLS-1$
                null);

    }

    public void testWsdlCoverageNo26() throws Exception {

        // NOTE: The test data here are a modified version.
        // Further details at:
        // http://jira.tibco.com/browse/XPD-2272?focusedCommentId=1164685&page=com.atlassian.jira.plugin.system.issuetabpanels:comment-tabpanel#comment-1164685

        // This produces three BOMs: A 'service' BOM for the WSDL
        // (thus no BDS output to check) and one for each of the
        // included schemas (absorbing their respective included
        // counterparts); We check the BDS output for these against ZIPs.
        doWsdlImport("CoverageNo26/MessageWSDL.wsdl", //$NON-NLS-1$
                new String[] { "CoverageNo26/SecondImportSchema.xsd", //$NON-NLS-1$
                        "CoverageNo26/FirstImportSchema.xsd", //$NON-NLS-1$
                        "CoverageNo26/SecondIncludeSchema.xsd", //$NON-NLS-1$
                        "CoverageNo26/FirstIncludeSchema.xsd" }, //$NON-NLS-1$
                "com.tibco.amx.integration.test.wsdl.messages.bom", //$NON-NLS-1$
                new String[] {
                        "com.tibco.amx.integration.test.xsd.secondimportschema.bom", //$NON-NLS-1$
                        "com.tibco.amx.integration.test.wsdl.firstimportschema.bom" }); //$NON-NLS-1$

        // This just produces a 'service' BOM, so no BDS output. We invoke
        // the import merely to make sure it doesn't fail, rather than
        // actually checking any output.
        doWsdlImport("CoverageNo26/PortTypeWSDL.wsdl", //$NON-NLS-1$
                new String[] { "CoverageNo26/SecondImportSchema.xsd", //$NON-NLS-1$
                        "CoverageNo26/FirstImportSchema.xsd", //$NON-NLS-1$
                        "CoverageNo26/SecondIncludeSchema.xsd", //$NON-NLS-1$
                        "CoverageNo26/FirstIncludeSchema.xsd", //$NON-NLS-1$
                        "CoverageNo26/MessageWSDL.wsdl" }, //$NON-NLS-1$
                "com.tibco.amx.integration.test.wsdl.porttype.bom", //$NON-NLS-1$
                null);

    }

    /**
     * This method tests that a known good WSDL can be imported into studio to
     * generate a BOM, that BOM can then be used to generate the BDS classes and
     * that the BDS classes a correct when validated against a know good
     * version.
     * 
     * @param wsdlFileNameString
     *            Name of the wsdl file
     * @param supportingXSDs
     *            And required supporting XSDs
     * @param mainBomFileName
     *            The name of the BOM file that will be created
     * @param extraBomFileNames
     *            Name of any additional BOM files that should be created
     * @throws Exception
     */
    public void doWsdlImport(final String wsdlFileNameString,
            final String[] supportingXSDs, final String mainBomFileName,
            final String[] extraBomFileNames) throws Exception {
        // Create project with a BOM folder
        IProject project = createBOMProject("BOMProj", null); //$NON-NLS-1$
        configureN2Destination(project, null);
        IFolder folder = createBusinessObjectsFolderInProject(project);

        // Create some files and copy the test WSDL and XSD to them
        File wsdlFile =
                folder.getParent().getLocation().append(wsdlFileNameString)
                        .toFile();
        wsdlFile.getParentFile().mkdirs();

        writeResourceToFile(RESOURCE_ROOT + "/wsdlimport/import/" //$NON-NLS-1$
                + wsdlFileNameString, wsdlFile);

        // Now create all of the supporting XSDs
        for (String xsdFileString : supportingXSDs) {
            File xsdFile =
                    folder.getParent().getLocation().append(xsdFileString)
                            .toFile();
            xsdFile.getParentFile().mkdirs();
            writeResourceToFile(RESOURCE_ROOT + "/wsdlimport/import/" //$NON-NLS-1$
                    + xsdFileString, xsdFile);
        }

        project.refreshLocal(IResource.DEPTH_INFINITE, null);

        List<IStatus> statusArr = null;
        try {
            // Transform the WSDL to a BOM. This will also produce a BOM for
            // any XSD on which the WSDL depends, but NOT for WSDLs on which
            // it depends.
            statusArr =
                    WSDLTransformUtil.transformWSDLtoBOM(wsdlFile, folder
                            .getFullPath().append("/" + mainBomFileName), null); //$NON-NLS-1$

            // Remove the Files copied from resources into the workspace, the
            // BOM has
            // been generates so the source files are no longer required
            wsdlFile.delete();
            for (String xsdFileString : supportingXSDs) {
                File xsdFile =
                        folder.getParent().getLocation().append(xsdFileString)
                                .toFile();
                xsdFile.delete();
            }
            project.refreshLocal(IResource.DEPTH_INFINITE, null);

            // Check for any errors or complete failure, warnings are OK
            for (IStatus status : statusArr) {
                if (status.getSeverity() == IStatus.ERROR
                        || status.getSeverity() == IStatus.CANCEL) {
                    fail(String.format("Failed to import WSDL '%s': %s", //$NON-NLS-1$
                            wsdlFile,
                            status));
                }
            }

            List<IFile> bomFiles = new ArrayList<IFile>();

            // Get the extra BOM files that we need and generate the BDS classes
            // from
            if (extraBomFileNames != null) {
                List<IFile> extraBomList = new ArrayList<IFile>();
                for (String bomFileName : extraBomFileNames) {
                    IFile extraBom = (IFile) folder.findMember(bomFileName);
                    if (extraBom == null) {
                        fail(String
                                .format("Could not find extra BOM in folder '%s' with name '%s'", //$NON-NLS-1$
                                        folder,
                                        bomFileName));
                    }
                    // First generate the extra BOM
                    extraBomList.add(extraBom);
                    doGeneration(project,
                            extraBomList,
                            new ModelGenerator(),
                            "com.tibco.bds.designtime.generator"); //$NON-NLS-1$
                    project.refreshLocal(IResource.DEPTH_INFINITE, null);

                    // Now add it to the list, as it should be in the dependent
                    // list
                    bomFiles.add(extraBom);
                }
            }

            // Add the main BOM to the end of the list, dependent BOMs get
            // listed first
            bomFiles.add((IFile) folder.findMember(mainBomFileName));

            // Only do generation if there is something in the wsdl to check
            if (getClass().getResourceAsStream(RESOURCE_ROOT
                    + "/wsdlimport/import/" //$NON-NLS-1$
                    + wsdlFileNameString.replace(".wsdl", //$NON-NLS-1$
                            "-known-good-output.zip")) != null) { //$NON-NLS-1$
                doGeneration(project,
                        bomFiles,
                        new ModelGenerator(),
                        "com.tibco.bds.designtime.generator"); //$NON-NLS-1$
            }
        } catch (Exception e) {
            throw e;
        }

        // Create a map of things that should be checked
        // key: BDS package name, value: known good output
        LinkedHashMap<String, String> checkMap =
                new LinkedHashMap<String, String>();

        // Add any extra boms that will be generated
        if (extraBomFileNames != null) {
            for (int i = 0; i < extraBomFileNames.length; i++) {
                // It is actually the xsd that we use are the name for the good
                // data
                String goodOutput = null;
                if (supportingXSDs.length > i) {
                    // Get the name of the good output
                    String[] split = supportingXSDs[i].split("/"); //$NON-NLS-1$
                    goodOutput =
                            "/wsdlimport/import/" //$NON-NLS-1$
                                    + split[0]
                                    + "/" //$NON-NLS-1$
                                    + split[split.length - 1].replace(".xsd", //$NON-NLS-1$
                                            "-known-good-output.zip"); //$NON-NLS-1$
                } else {
                    String[] split = extraBomFileNames[i].split("/"); //$NON-NLS-1$
                    String[] dirSplit = wsdlFileNameString.split("/"); //$NON-NLS-1$
                    goodOutput =
                            "/wsdlimport/import/" //$NON-NLS-1$
                                    + dirSplit[0]
                                    + "/" //$NON-NLS-1$
                                    + split[split.length - 1].replace(".bom", //$NON-NLS-1$
                                            "-known-good-output.zip"); //$NON-NLS-1$
                }
                checkMap.put("." + extraBomFileNames[i].replace(".bom", ".bds"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        goodOutput);
            }
        }

        // Now add the main WSDL
        if (getClass()
                .getResourceAsStream(RESOURCE_ROOT
                        + "/wsdlimport/import/" //$NON-NLS-1$
                        + wsdlFileNameString.replace(".wsdl", //$NON-NLS-1$
                                "-known-good-output.zip")) != null) { //$NON-NLS-1$
            checkMap.put("." + mainBomFileName.replace(".bom", ".bds"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                    "/wsdlimport/import/" //$NON-NLS-1$
                            + wsdlFileNameString.replace(".wsdl", //$NON-NLS-1$
                                    "-known-good-output.zip")); //$NON-NLS-1$
        }

        for (String bds_package : checkMap.keySet()) {
            // Extract our comparison data
            File zipExportRoot = File.createTempFile("temp", "zip"); //$NON-NLS-1$ //$NON-NLS-2$
            zipExportRoot.deleteOnExit();
            unzipFromResource(RESOURCE_ROOT + checkMap.get(bds_package),
                    zipExportRoot);
            IProject outputProject =
                    ResourcesPlugin.getWorkspace().getRoot()
                            .getProject(bds_package);
            assertTrue("Output project doesn't exist", outputProject.exists()); //$NON-NLS-1$

            List<Difference> diffs =
                    Differ.diff(zipExportRoot, outputProject.getLocation()
                            .toFile(), s_ignoreNames, s_ignoreContents);

            assertTrue("Unexpected diffs " + diffs, diffs.isEmpty()); //$NON-NLS-1$

            // Do a test build of the new project
            buildProject(outputProject);
        }

        // Now all have been checked and built, remove them
        for (String bds_package : checkMap.keySet()) {
            IProject outputProject =
                    ResourcesPlugin.getWorkspace().getRoot()
                            .getProject(bds_package);
            cleanUp(outputProject);
        }
    }
}
