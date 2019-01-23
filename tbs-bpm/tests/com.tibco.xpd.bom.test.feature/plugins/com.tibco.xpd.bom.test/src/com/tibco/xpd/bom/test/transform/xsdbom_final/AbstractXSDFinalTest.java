/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.xsdbom_final;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Property;
import org.eclipse.xsd.XSDSchema;
import org.openarchitectureware.xsd.builder.OawXSDResource;

import com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip;
import com.tibco.xpd.bom.test.transform.util.BOMCompareUtil;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Abstract class to be implemented by all XSD Choice junit test classes.
 * 
 * @author glewis
 * 
 */
public abstract class AbstractXSDFinalTest extends TransformationTestRoundtrip {

    protected String TEST_FILE_NAME = "myTest.bom";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/xsd-final";

    protected String PLATFORM_WIZARD_GOLD_FILES_BASE =
            PLATFORM_EXAMPLE_FILES_BASE + "/gold/wizard";

    protected String PLATFORM_BUILDER_GOLD_FILES_BASE =
            PLATFORM_EXAMPLE_FILES_BASE + "/gold/builder";

    protected String PLATFORM_BOM_GOLD_FILES_BASE = PLATFORM_EXAMPLE_FILES_BASE
            + "/gold/bom";

    private final String modelFile;

    private String[] otherModelFileNames;

    public AbstractXSDFinalTest(String modelFile) {
        this.modelFile = modelFile;
    }

    protected void checkBOMElements(Model model) throws Exception {
        Model goldModel = loadGoldModel(new Path(modelFile));

        IStatus status = new BOMCompareUtil().compare(goldModel, model);
        assertTrue(String.format("BOM Compare Failed: %s", status.getMessage()),
                status.isOK());
    }

    private Model loadGoldModel(IPath xsdFile) {
        Model model = null;
        IPath bomGoldPath =
                new Path(PLATFORM_BOM_GOLD_FILES_BASE)
                        .append(xsdFile.removeFileExtension())
                        .addFileExtension("bom");
        Resource resource =
                ed.loadResource(URI.createURI(bomGoldPath.toString(), true)
                        .toString());
        assertNotNull(String.format("Cannot load bom gold file for: %s",
                xsdFile.toString()), resource);

        for (EObject eo : resource.getContents()) {
            if (eo instanceof Model) {
                model = (Model) eo;
                break;
            }
        }

        assertNotNull(String.format("Cannot load Model from resource: %s",
                bomGoldPath.toString()), model);

        return model;
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

        goldFileNames.add(modelFile);

        if (otherModelFileNames != null && otherModelFileNames.length > 1) {
            for (int i = 1; i < otherModelFileNames.length; i++) {
                modelFileNames.add(otherModelFileNames[i]);
                goldFileNames.add(otherModelFileNames[i]);
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

        IPath outputBOMPath = getBomIPath();
        List<IStatus> result =
                importXSDtoBOM(new File(modelFiles.get(0).getLocationURI()),
                        outputSpecialFolder.getFolder().getFullPath()
                                .append(outputBOMPath.lastSegment()));

        List<IStatus> errors = getErrors(result);
        if (!errors.isEmpty()) {
            throw new Exception(errors.get(0).getMessage());
        }

        // check resulting bom file is correct
        bomFile =
                ResourcesPlugin
                        .getWorkspace()
                        .getRoot()
                        .getFile(outputSpecialFolder.getFolder().getFullPath()
                                .append(outputBOMPath.lastSegment()));
        assertTrue("Cannot find newly exported BOM file", bomFile.exists()); //$NON-NLS-1$

        WorkingCopy wc =
                XpdResourcesPlugin.getDefault().getWorkingCopy(bomFile);
        assertNotNull("Cannot create WorkingCopy for newly exported BOM file", wc); //$NON-NLS-1$

        assertTrue("Root element is null or not of type Model", wc.getRootElement() instanceof Model); //$NON-NLS-1$

        checkBOMElements((Model) wc.getRootElement());

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
