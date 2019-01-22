/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.exports_bom_xsd_rt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import junit.framework.Assert;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;

import com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip;
import com.tibco.xpd.bom.test.transform.util.TransformUtil;
import com.tibco.xpd.bom.validator.BOMValidatorActivator;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;
import com.tibco.xpd.core.xmlunit.XmlDiff;

/**
 * Its a very base transformation test it only test for now if transformation
 * will run without exceptions. You can later test manually the result in the
 * junit-workspace.
 * <p>
 * <i>Created: 06 Feb 2009</i>
 * </p>
 * 
 * @author glewis
 */
@SuppressWarnings("nls")
public class XSDWithImportTransformationTest_rt extends
        TransformationTestRoundtrip {

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/bom-xsd/round-trip/bom";

    protected String PLATFORM_WIZARD_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/bom-xsd/round-trip/gold/wizard";

    protected String PLATFORM_BUILDER_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/bom-xsd/round-trip/gold/builder";

    protected String MODEL_FILE = "XSDWithImportTransformationTest_rt.bom";

    protected String MODEL_FILE2 = "businessobjectmodel.tns.bom";

    protected String GOLD_FILE = "XSDWithImportTransformationTest_rt.xsd";

    protected String GOLD_FILE2 = "businessobjectmodel.tns.xsd";

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
        platformWizardGoldFilesBase = PLATFORM_WIZARD_GOLD_FILES_BASE;
        platformBuilderGoldFilesBase = PLATFORM_BUILDER_GOLD_FILES_BASE;

        modelFileNames.add(MODEL_FILE);
        modelFileNames.add(MODEL_FILE2);

        goldFileNames.add(GOLD_FILE);
        goldFileNames.add(GOLD_FILE2);

        super.setUp();
    }

    @Override
    public void testTransformation() throws Exception {
        // check wizard transformation
        String[] destIds =
                new String[] { BOMValidatorActivator.VALIDATION_DEST_ID_XSD,
                        BOMValidatorActivator.VALIDATION_DEST_ID_BOMGENERIC };
        boolean hasErrorMarker =
                TransformUtil.isMarkerSeverityExist(destIds,
                        modelFiles.get(0),
                        IMarker.SEVERITY_ERROR);
        assertEquals("There are XSD or BOM GENERIC errors on the BOM",
                false,
                hasErrorMarker);

        List<IStatus> result =
                exportBOMtoXSD(modelFiles.get(0),
                        true,
                        ResourcesPlugin
                                .getWorkspace()
                                .getRoot()
                                .getProject(testProjectName)
                                .getFullPath()
                                .append(outputSpecialFolder.getFolder()
                                        .getName()),
                        true);
        assertEquals("There were errors on this transformation process (simulating an export from wizard).",
                0,
                getErrors(result).size());

        processTransformedFiles(modelFiles.get(0));

        for (int index = 0; index < transformedFiles.size(); index++) {
            XmlDiff xmlDiff =
                    initXMLDiff(transformedFiles.get(index),
                            wizardGoldFiles.get(index));
            Assert.assertTrue("Wizard file must be identical to the gold file",
                    xmlDiff.similar());
        }

        // check builder transformation
        destIds = new String[] { BOMValidatorActivator.VALIDATION_DEST_ID_CDS };
        hasErrorMarker =
                TransformUtil.isMarkerSeverityExist(destIds,
                        modelFiles.get(0),
                        IMarker.SEVERITY_ERROR);
        assertEquals("There are CDS errors on the BOM", false, hasErrorMarker);

        result =
                exportBOMtoXSD(modelFiles.get(0),
                        true,
                        ResourcesPlugin
                                .getWorkspace()
                                .getRoot()
                                .getProject(testProjectName)
                                .getFullPath()
                                .append(outputSpecialFolder.getFolder()
                                        .getName()),
                        true);
        assertEquals("There were errors on this transformation process (simulating an export using builder).",
                0,
                getErrors(result).size());

        processTransformedFiles(modelFiles.get(0));

        for (int index = 0; index < transformedFiles.size(); index++) {
            XmlDiff xmlDiff =
                    initXMLDiff(transformedFiles.get(index),
                            builderGoldFiles.get(index));
            Assert.assertTrue("Builder file must be identical to the gold file",
                    xmlDiff.similar());
        }
    }

    @Override
    protected void processTransformedFiles(IFile file) {
        transformedFiles.clear();
        Collection<String> transformedFileNames =
                XSDUtil.getOutputFileNamesForBOMFile(modelFiles.get(0));
        for (String fileName : transformedFileNames) {
            IFile tmpFile = outputSpecialFolder.getFolder().getFile(fileName);
            transformedFiles.add(tmpFile);
        }
    }
}
