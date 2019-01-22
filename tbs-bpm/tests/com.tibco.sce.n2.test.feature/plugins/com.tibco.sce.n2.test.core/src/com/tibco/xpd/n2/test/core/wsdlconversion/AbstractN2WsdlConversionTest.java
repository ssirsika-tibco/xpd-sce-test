/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.n2.test.core.wsdlconversion;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

import com.tibco.xpd.bom.wsdltransform.builder.WsdlToBomBuilder;
import com.tibco.xpd.n2.test.core.AbstractN2BaseResourceTest;
import com.tibco.xpd.resources.util.SpecialFolderUtil;

/**
 * Test that inclusion of "#wsdlrelativepath#" in a BPM project generates the
 * appropriate BOM files with the correct content (and that they have no problem
 * markers).
 * <p>
 * Then checks that the correct XSDs are derived by the Bom2XsdBuilder (in the
 * .bom2xsd" folder) with the correct content and that they have no problem
 * markers.
 * <p>
 * Then checks that the correct BDS projects are created and contains the
 * correct XSDs dervied for them to build the BDS ecore - and that the BDS
 * project has no problem markers.
 * 
 * @author aallway
 * @since 18 Apr 2011
 */
public abstract class AbstractN2WsdlConversionTest extends
        AbstractN2BaseResourceTest {

    public static final String GOLD_GENERATED_BOM_FOLDER = "GoldGeneratedBoms"; //$NON-NLS-1$

    public static final String GOLD_GENERATED_BOM2XSDS_FOLDER = "GoldBom2Xsds"; //$NON-NLS-1$

    public static final String GOLD_GENERATED_XSDS4BDS_FOLDER = "GoldXsds4Bds"; //$NON-NLS-1$

    private String wsdlOrXsdProjectName;

    private String srcWsdlXsdProjectRelativePath;

    /**
     * @param wsdlOrXsdProjectName
     * @param srcWsdlXsdProjectRelativePath
     */
    public AbstractN2WsdlConversionTest(String wsdlOrXsdProjectName,
            String srcWsdlXsdProjectRelativePath) {
        super();
        this.wsdlOrXsdProjectName = wsdlOrXsdProjectName;
        this.srcWsdlXsdProjectRelativePath = srcWsdlXsdProjectRelativePath;
    }

    /**
     * Test that inclusion of "#wsdlrelativepath#" in a BPM project generates
     * the appropriate BOM files with the correct content (and that they have no
     * problem markers).
     * 
     * @param bomFolderRelativeBoms
     * @throws CoreException
     */
    protected void checkGeneratedBoms(String[] bomFolderRelativeBoms)
            throws CoreException {
        /*
         * First check that all of the generated boms exist
         */
        IFile sourceWsdlOrXsd = getSourceWsdlOrXsd();
        Set<IFile> bomsGeneratedFromWsdl =
                WsdlToBomBuilder.getBomsGeneratedFromWsdl(sourceWsdlOrXsd);

        IFolder genBomFolder =
                WsdlToBomBuilder
                        .getGeneratedBOMFolder(getSourceWsdlOrXsdProject(),
                                false);
        /*
         * Check that the expected BOMs were generated and appear in list from
         * builder.
         */
        for (String expectedGenBom : bomFolderRelativeBoms) {
            IFile expectedBomFile = genBomFolder.getFile(expectedGenBom);

            assertTrue(String.format("The expected BOM '%s' was not generated.", //$NON-NLS-1$
                    expectedBomFile.getFullPath().toString()),
                    expectedBomFile.exists());

            assertTrue(String.format("The generated BOM '%s' exists but was not found in list of WsdlToBomBuilder.getBomsGeneratedFromWsdl('%s')", //$NON-NLS-1$
                    expectedBomFile.getFullPath().toString(),
                    sourceWsdlOrXsd.getFullPath().toString()),
                    bomsGeneratedFromWsdl.contains(expectedBomFile));
        }

        /*
         * Check all in the list of Boms actually generated are expected by this
         * test.
         */
        for (IFile generatedBom : bomsGeneratedFromWsdl) {
            IPath bomFolderRelativePath =
                    SpecialFolderUtil
                            .getSpecialFolderRelativePath(generatedBom);

            boolean found = false;
            for (String expectedGenBomPath : bomFolderRelativeBoms) {
                if (expectedGenBomPath.equals(bomFolderRelativePath.toString())) {
                    found = true;
                    break;
                }
            }

            assertTrue(String.format("The generated BOM '%s' was returned in list from WsdlToBomBuilder.getBomsGeneratedFromWsdl('%s') but was not expected by test.", //$NON-NLS-1$
                    generatedBom.getFullPath().toString(),
                    sourceWsdlOrXsd.getFullPath().toString()),
                    found);
        }

        // Diff the boms

        return; // SUCCESS!
    }

    /**
     * Test that the correct XSDs are derived by the Bom2XsdBuilder (in the
     * .bom2xsd" folder) with the correct content and that they have no problem
     * markers.
     * 
     * @param bom2XsdFolderRelativeXsds
     */
    protected void checkDerivedBom2Xsds(String[] bom2XsdFolderRelativeXsds) {

        return; // SUCCESS!
    }

    /**
     * Test that the correct BDS projects are created and contains the correct
     * XSDs derived for them to build the BDS ecore - and that the BDS project
     * has no problem markers.
     * 
     * @param xsd4BdsInfos
     */
    protected void checkDerivedXsd4Bds(Xsd4BdsInfo[] xsd4BdsInfos) {
        // TODO Auto-generated method stub
        return; // SUCCESS!
    }

    /**
     * 
     * @return The source WSDL or XSD being tested.
     */
    private IFile getSourceWsdlOrXsd() {
        IProject project = getSourceWsdlOrXsdProject();

        return project.getFile(srcWsdlXsdProjectRelativePath);
    }

    /**
     * @return the project for the wsl or xsd being tested.
     */
    protected IProject getSourceWsdlOrXsdProject() {
        IProject project =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getProject(wsdlOrXsdProjectName);
        return project;
    }
}
