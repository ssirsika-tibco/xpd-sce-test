/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.global_data;

import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.projectassets.IAssetConfigurator;
import com.tibco.xpd.resources.projectconfig.projectassets.util.ProjectAssetElement;
import com.tibco.xpd.resources.projectconfig.projectassets.util.ProjectAssetManager;

/**
 * Abstract class to be implemented by all XSD Choice junit test classes.
 * 
 * @author njpatel
 * 
 */
public abstract class AbstractGlobalDataTest extends
        TransformationTestRoundtrip {

    protected String TEST_FILE_NAME = "myTest.bom";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/bom-global-data";

    protected String PLATFORM_WIZARD_GOLD_FILES_BASE =
            PLATFORM_EXAMPLE_FILES_BASE + "/gold/wizard";

    protected String PLATFORM_BUILDER_GOLD_FILES_BASE =
            PLATFORM_EXAMPLE_FILES_BASE + "/gold/builder";

    private final String modelFile;

    private String[] otherModelFileNames;

    public AbstractGlobalDataTest(String modelFile) {
        this.modelFile = modelFile;
    }

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        modelFileNames = new ArrayList<String>();
        modelFiles = new ArrayList<IFile>();

        platformExampleFilesBase = PLATFORM_EXAMPLE_FILES_BASE;
        assertNotNull("Model file not set", modelFile);
        modelFileNames.add(modelFile);

        platformWizardGoldFilesBase = PLATFORM_WIZARD_GOLD_FILES_BASE;
        platformBuilderGoldFilesBase = PLATFORM_BUILDER_GOLD_FILES_BASE;

        goldFileNames.add(modelFile
                .replace("." + BOMResourcesPlugin.BOM_FILE_EXTENSION, ".xsd")); //$NON-NLS-1$ //$NON-NLS-2$

        if (otherModelFileNames != null && otherModelFileNames.length > 1) {
            for (int i = 1; i < otherModelFileNames.length; i++) {
                modelFileNames.add(otherModelFileNames[i]);
                goldFileNames.add(otherModelFileNames[i]);
            }
        }

        super.setUp();

        /*
         * the project with global data bom is a bpm project, so converting it
         * to business data project
         */
        convertProjectToBusinessDataProject(project);
    }

    /*
     * I should have referred this from RefactorToBusinessDataProjectAction. But
     * it is defined as private, and after code freeze with ccrb in place I cant
     * make it public or move it some util.
     */
    private static final String BUSINESS_DATA_ASSET_ID =
            "com.tibco.xpd.asset.businessdata.bom"; //$NON-NLS-1$

    /**
     * Convert the project to a Business Data project.
     * 
     * @param project
     * @return
     * @throws CoreException
     */
    private boolean convertProjectToBusinessDataProject(IProject project)
            throws CoreException {
        // Add the Business Data asset to this project
        ProjectConfig config =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);
        if (config != null) {
            config.addAssetTask(BUSINESS_DATA_ASSET_ID);
            ProjectAssetElement asset =
                    ProjectAssetManager.getProjectAssetMenager()
                            .getAssetById(BUSINESS_DATA_ASSET_ID);
            if (asset != null) {
                IAssetConfigurator configurator = asset.getConfigurator();
                if (configurator != null) {
                    configurator.configure(project, asset.getConfiguration());
                }
            }

            return true;
        }

        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip#
     * testTransformation()
     */
    @Override
    public void testTransformation() throws Exception {
        // check resulting bom file is correct
        bomFile = modelFiles.get(0);

        exportTest();
    }

    private IPath getBomIPath() {
        IPath outputBOMPath =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getProject(testProjectName).getFullPath()
                        .append(TEST_FILE_NAME);
        return outputBOMPath;
    }

    /**
     * Check multiplicity of the given Property.
     * 
     * @param property
     * @param lower
     * @param upper
     */
    protected void checkMultiplicity(Property property, int lower, int upper) {
        assertEquals("Property '" + property.getName()
                + "' lower multiplicity value", lower, property.getLower());
        assertEquals("Property '" + property.getName()
                + "' upper multiplicity value", upper, property.getUpper());
    }

}
