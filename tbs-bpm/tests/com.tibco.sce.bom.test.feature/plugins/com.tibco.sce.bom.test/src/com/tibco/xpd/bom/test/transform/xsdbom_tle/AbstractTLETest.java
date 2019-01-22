/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.xsdbom_tle;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Property;
import org.eclipse.xsd.XSDSchema;
import org.openarchitectureware.xsd.builder.OawXSDResource;

import com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Abstract class to be implemented by all top-level element junit test classes.
 * 
 * @author njpatel
 * 
 */
public abstract class AbstractTLETest extends TransformationTestRoundtrip {

    protected String TEST_FILE_NAME = "myTest.bom";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/tle/xsd";

    protected String PLATFORM_WIZARD_GOLD_FILES_BASE =
            PLATFORM_EXAMPLE_FILES_BASE + "/gold/wizard";

    protected String PLATFORM_BUILDER_GOLD_FILES_BASE =
            PLATFORM_EXAMPLE_FILES_BASE + "/gold/builder";

    private final String modelFile;

    private String[] otherModelFileNames;

    private String[] otherGoldFileNames;

    private String rootXSDBOMFileName;

    public AbstractTLETest(String modelFile) {
        this.modelFile = modelFile;
        goldFileNames.add(modelFile);
        modelFileNames = new ArrayList<String>();
    }

    public AbstractTLETest(String[] modelFiles) {
        this.modelFile = modelFiles[0];
        this.otherModelFileNames = modelFiles;
        this.otherGoldFileNames = this.otherModelFileNames;
        modelFileNames = new ArrayList<String>();
    }

    public AbstractTLETest(String[] modelFiles, String[] goldFiles,
            String rootXSDBOMFileName) {
        this.modelFile = modelFiles[0];
        this.otherModelFileNames = modelFiles;
        this.otherGoldFileNames = goldFiles;
        this.rootXSDBOMFileName = rootXSDBOMFileName;
        modelFileNames = new ArrayList<String>();
    }

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {

        modelFiles = new ArrayList<IFile>();

        platformExampleFilesBase = PLATFORM_EXAMPLE_FILES_BASE;
        assertNotNull("Model file not set", modelFile);
        modelFileNames.add(modelFile);

        platformWizardGoldFilesBase = PLATFORM_WIZARD_GOLD_FILES_BASE;
        platformBuilderGoldFilesBase = PLATFORM_BUILDER_GOLD_FILES_BASE;

        if (otherModelFileNames != null && otherModelFileNames.length > 1) {
            for (int i = 1; i < otherModelFileNames.length; i++) {
                modelFileNames.add(otherModelFileNames[i]);
            }
        }

        if (otherGoldFileNames != null && otherGoldFileNames.length > 0) {
            for (int i = 0; i < otherGoldFileNames.length; i++) {
                goldFileNames.add(otherGoldFileNames[i]);
            }
        }

        super.setUp();
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip#
     * testTransformation()
     */
    @Override
    public void testTransformation() throws Exception {
        IFile modelFile =
                outputSpecialFolder.getFolder().getFile(this.modelFile);
        OawXSDResource resource =
                new OawXSDResource(URI.createURI(modelFile.getFullPath()
                        .toPortableString()));
        resource.load(Collections.EMPTY_MAP);
        XSDSchema schema = resource.getSchema();
        TEST_FILE_NAME =
                XSDUtil.getJavaPackageNameFromNamespaceURI(modelFile
                        .getProject(), schema.getTargetNamespace())
                        + ".bom"; //$NON-NLS-1$

        final IPath outputBOMPath = getBomIPath();

        ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {

            @Override
            public void run(IProgressMonitor monitor) throws CoreException {
                List<IStatus> result =
                        importXSDtoBOM(new File(modelFiles.get(0)
                                .getLocationURI()),
                                outputSpecialFolder.getFolder().getFullPath()
                                        .append(outputBOMPath.lastSegment()));

                List<IStatus> errors = getErrors(result);
                if (!errors.isEmpty()) {
                    fail(errors.get(0).getMessage());
                }
            }
        },
                null);

        ResourcesPlugin.getWorkspace()
                .build(IncrementalProjectBuilder.CLEAN_BUILD,
                        new NullProgressMonitor());
        TestUtil.waitForJobs();

        ResourcesPlugin.getWorkspace()
                .build(IncrementalProjectBuilder.FULL_BUILD,
                        new NullProgressMonitor());
        TestUtil.waitForJobs();

        if (rootXSDBOMFileName == null) {
            rootXSDBOMFileName = outputBOMPath.lastSegment();
        }
        // check resulting bom file is correct
        bomFile =
                ResourcesPlugin
                        .getWorkspace()
                        .getRoot()
                        .getFile(outputSpecialFolder.getFolder().getFullPath()
                                .append(rootXSDBOMFileName));
        assertTrue("Cannot find newly exported BOM file", bomFile.exists()); //$NON-NLS-1$

        WorkingCopy wc =
                XpdResourcesPlugin.getDefault().getWorkingCopy(bomFile);
        assertNotNull("Cannot create WorkingCopy for newly exported BOM file", wc); //$NON-NLS-1$

        assertTrue("Root element is null or not of type Model", wc.getRootElement() instanceof Model); //$NON-NLS-1$

        checkBOMElements((Model) wc.getRootElement());

        /* Sid: ALWAYS do export test, this must give us better test coverage. */
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

    /**
     * Validate the generated model.
     * 
     * @param model
     * @throws Exception
     */
    protected abstract void checkBOMElements(Model model) throws Exception;
}
