/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.bds.designtime.generator.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
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
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;

/**
 * Tests the ability to import schemas in order to create a BOM
 * 
 */
public class XsdImportGenerationTest extends BaseTest {

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

    // Perform all of the XSD import tests for the supported constructs
    // For each of the different files that we wish to import, call the
    // method that performs the operation. There are a few naming rules
    // that need to follow:
    // - The XSD's namespace should be the same as the filename
    // - The comparison zip must have the same filename with
    // "-known-good-output.zip" added
    public void testXsdImportXsdAllTypes() throws Exception {
        doXsdImport("xsdAllTypes"); //$NON-NLS-1$
    }

    public void testXsdImportSimpleEnumeration() throws Exception {
        doXsdImport("simpleEnumeration"); //$NON-NLS-1$
    }

    public void testXsdImportComplexTypeWithSimpleContent() throws Exception {
        doXsdImport("complexTypeWithSimpleContent"); //$NON-NLS-1$
    }

    public void testXsdImportComplexTypeWithSimpleRestriction()
            throws Exception {
        doXsdImport("complexTypeWithSimpleRestriction"); //$NON-NLS-1$
    }

    public void testXsdImportComplexTypeUsingAttributes() throws Exception {
        doXsdImport("complexTypeUsingAttributes"); //$NON-NLS-1$
    }

    public void testXsdImportComplexTypeWithElements() throws Exception {
        doXsdImport("complexTypeWithElements"); //$NON-NLS-1$
    }

    public void testXsdImportComplexTypeWithExtension() throws Exception {
        doXsdImport("complexTypeWithExtension"); //$NON-NLS-1$
    }

    public void testXsdImportComplexTypeWithNillable() throws Exception {
        doXsdImport("complexTypeWithNillable"); //$NON-NLS-1$
    }

    public void testXsdImportAnonymousType() throws Exception {
        doXsdImport("anonymousType"); //$NON-NLS-1$
    }

    public void testXsdImportAnonymousEnumeration() throws Exception {
        doXsdImport("anonymousEnumeration"); //$NON-NLS-1$
    }

    public void testXsdImportNameClash() throws Exception {
        doXsdImport("nameclash"); //$NON-NLS-1$
    }

    public void testXsdImportNameClash2() throws Exception {
        doXsdImport("nameclash2"); //$NON-NLS-1$
    }

    public void testXsdImportNameClash3() throws Exception {
        doXsdImport("nameclash3"); //$NON-NLS-1$
    }

    public void testXsdImportAnyComprehensive() throws Exception {
        doXsdImport("AnyComprehensive"); //$NON-NLS-1$
    }

    public void testXsdImportAnyTypeSimpleAttrib() throws Exception {
        doXsdImport("AnyTypeSimpleAttrib"); //$NON-NLS-1$
    }

    public void testXsdImportTopLevelElements() throws Exception {
        doXsdImport("TopLevelElements"); //$NON-NLS-1$
    }

    public void testXsdImportUnderscores() throws Exception {
        // Tests logic that makes name-clash detection 'underscore-insensitive'
        doXsdImport("underscores"); //$NON-NLS-1$
    }

    public void testXsdImportAbstractSupport() throws Exception {
        doXsdImport("AbstractSupport"); //$NON-NLS-1$
    }

    public void testXsdImportSubstitutionGroup() throws Exception {
        doXsdImport("SubstitutionGroup"); //$NON-NLS-1$
    }

    public void testXsdImportChoice() throws Exception {
        doXsdImport("ChoiceSupport"); //$NON-NLS-1$
    }

    public void testXsdImportMultipleOccurs() throws Exception {
        doXsdImport("MultipleOccurs"); //$NON-NLS-1$
    }

    // TODO Enable once ecore:name propagation is enabled (XPD-2569)
    // TODO Remember to add a 'known good' ZIP.
    public void XtestXsdImportGetterNouns() throws Exception {
        doXsdImport("getternouns"); //$NON-NLS-1$
    }

    public void testXsdImportComplexRestriction() throws Exception {
        doXsdImport("ComplexRestriction"); //$NON-NLS-1$
    }

    public void testXsdImportInheritedAttrClash() throws Exception {
        doXsdImport("inheritedAttrClash"); //$NON-NLS-1$
    }

    public void testXsdImportWindowsDeviceNames() throws Exception {
        // This tests that Windows device names in the XSD are cleansed to avoid problems.
        // Part of this involves having Windows device names in the namespace URI
        // (http://devices.con.prn.aux.nul.com1.com2.com8.com9.lpt1.lpt2.lpt8.lpt9), meaning
        // we unavoidably end up with an ugly filename for the BOM.
        doXsdImport("lpt9_.lpt8_.lpt2_.lpt1_.com9_.com8_.com2_.com1_.nul_.aux_.prn_.con_.devices"); //$NON-NLS-1$
    }

    /**
     * This method tests that a known good XSD can be imported into studio to
     * generate a BOM, that BOM can then be used to generate the BDS classes and
     * that the BDS classes a correct when validated against a know good
     * version.
     * 
     * @param coreFileNameString
     *            Name of the file (with no extension)
     * @throws Exception
     */
    public void doXsdImport(final String coreFileNameString) throws Exception {
        // Create project with a BOM folder
        IProject project = createBOMProject("BOMProj", null); //$NON-NLS-1$
        configureN2Destination(project, null);
        IFolder folder = createBusinessObjectsFolderInProject(project);

        final String xsdFileString = coreFileNameString + ".xsd"; //$NON-NLS-1$
        final String bomFileName = coreFileNameString + ".bom"; //$NON-NLS-1$
        final String knownGoodOutput =
                coreFileNameString + "-known-good-output.zip"; //$NON-NLS-1$
        String bdsProjectName = "." + coreFileNameString + ".bds"; //$NON-NLS-1$ //$NON-NLS-2$

        // Create a file and copy the XSD to it
        File xsdFile =
                folder.getParent().getLocation().append(xsdFileString).toFile();

        writeResourceToFile(RESOURCE_ROOT + "/xsdimport/" + xsdFileString, //$NON-NLS-1$
                xsdFile);

        project.refreshLocal(IResource.DEPTH_INFINITE, null);

        List<IStatus> statusArr = null;

        // Run the import command to import the WSDL (The XSD should be
        // automatically picked up)
        // This will generate the two required BOM files
        statusArr =
                XSDUtil.transformXSDToBOM(xsdFile,
                        folder.getFullPath().append("/" + bomFileName), //$NON-NLS-1$
                        null);

        // Remove the Files copied from resources into the workspace, the
        // BOM has
        // been generates so the source files are no longer required
        xsdFile.delete();
        project.refreshLocal(IResource.DEPTH_INFINITE, null);

        if (!statusArr.isEmpty()) {
            fail("Failed to import XSD: " + statusArr); //$NON-NLS-1$
        }

        // Get the BOM files that we need and generate the BDS classes from
        doGeneration(project,
                Collections.singletonList((IFile) folder
                        .findMember(bomFileName)),
                new ModelGenerator(),
                "com.tibco.bds.designtime.generator"); //$NON-NLS-1$

        // Extract our comparison data
        File zipExportRoot = File.createTempFile("temp", "zip"); //$NON-NLS-1$ //$NON-NLS-2$
        zipExportRoot.deleteOnExit();
        unzipFromResource(RESOURCE_ROOT + "/xsdimport/" + knownGoodOutput, //$NON-NLS-1$
                zipExportRoot);

        IProject outputProject =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getProject(bdsProjectName);
        assertTrue("Output " + bdsProjectName + " project doesn't exist", //$NON-NLS-1$ //$NON-NLS-2$
                outputProject.exists());

        List<Difference> diffs =
                Differ.diff(zipExportRoot,
                        outputProject.getLocation().toFile(),
                        s_ignoreNames,
                        s_ignoreContents);
        if (!diffs.isEmpty()) {
            System.out.println("Diffs with " + getName()); //$NON-NLS-1$
        }
        assertTrue("Unexpected diffs " + diffs, diffs.isEmpty()); //$NON-NLS-1$
        
        // Do a test build of the new project
        buildProject(outputProject);

        cleanUp(outputProject);
    }
}
