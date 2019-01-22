package com.tibco.bds.designtime.generator.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;

import org.eclipse.core.runtime.CoreException;

import com.tibco.bds.designtime.generator.AbstractBDSGenerator;
import com.tibco.bds.designtime.generator.ModelGenerator;
import com.tibco.bds.designtime.generator.test.util.diff.DifferException;
import com.tibco.xpd.bom.gen.biz.BOMIllegalStateException;
import com.tibco.xpd.bom.gen.biz.GenerationException;

public class ModelGenerationTest extends RuntimeGenerationTest {

    /**
     * @see com.tibco.bds.designtime.generator.test.BaseTest#setUp()
     * 
     * @throws CoreException
     */
    public void setUp() throws CoreException {
        super.setUp();
        s_ignoreNames = new ArrayList<String>(s_ignoreNames);
        // No need to compare BOM. It's just a copy of what's imported.
        s_ignoreNames.add(".*\\.bom"); //$NON-NLS-1$
    }

    @Override
    protected AbstractBDSGenerator createGenerator() {
        return new ModelGenerator();
    }

    @Override
    protected String getExtensionId() {
        return "com.tibco.bds.designtime.generator"; //$NON-NLS-1$
    }

    public void testBOMWithForeignReference2() throws CoreException,
            IOException, GenerationException, DifferException,
            BOMIllegalStateException {

        LinkedHashMap<String, String> outputDeatils =
                new LinkedHashMap<String, String>();
        outputDeatils.put(".org.testing.ptlibrary.bds", //$NON-NLS-1$
                "PTLibrary-known-good-output.zip"); //$NON-NLS-1$
        outputDeatils.put(".com.example.foreignmodel3.bds", //$NON-NLS-1$
                "ForeignModel3-known-good-output.zip"); //$NON-NLS-1$
        outputDeatils.put(".foreignmodel2.bds", //$NON-NLS-1$
                "ForeignModel2-known-good-output.zip"); //$NON-NLS-1$
        outputDeatils.put(".com.example.reftest2.bds", //$NON-NLS-1$
                "RefTest2-known-good-output.zip"); //$NON-NLS-1$

        performTest(Arrays.asList(new String[] { "PTLibrary.bom", //$NON-NLS-1$
                "ForeignModel3.bom", "ForeignModel2.bom", "RefTest2.bom" }), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                outputDeatils,
                null);

        // TODO Add a test that tests cross-PROJECT references between BOMs
    }

    public void testBOMWithForeignReference() throws Exception {
        LinkedHashMap<String, String> outputDeatils =
                new LinkedHashMap<String, String>();
        outputDeatils.put(".foreignmodel.bds", //$NON-NLS-1$
                "ForeignModel-known-good-output.zip"); //$NON-NLS-1$
        outputDeatils.put(".reftest.bds", "RefTest-known-good-output.zip"); //$NON-NLS-1$ //$NON-NLS-2$

        performTest(Arrays.asList(new String[] { "ForeignModel.bom", //$NON-NLS-1$
                "RefTest.bom" }), //$NON-NLS-1$
                outputDeatils,
                null);
    }

    public void testComprehensive() throws CoreException, IOException,
            GenerationException, DifferException, BOMIllegalStateException {
        performTest(Collections.singletonList("Comprehensive.bom"), //$NON-NLS-1$
                "Comprehensive-known-good-output.zip", //$NON-NLS-1$
                ".comprehensive.bds", //$NON-NLS-1$
                null);
    }

    public void testBasicScenario() throws Exception {
        performTest(Collections.singletonList("UnitTestBOM1.bom"), //$NON-NLS-1$
                "UnitTestBOM1-known-good-output.zip", //$NON-NLS-1$
                ".unittestbom1.bds", //$NON-NLS-1$
                null);
    }

    public void testSimpleTypes() throws Exception {
        performTest(Collections.singletonList("Simples.bom"), //$NON-NLS-1$
                "Simples-known-good-output.zip", //$NON-NLS-1$
                ".com.example.simples.bds", //$NON-NLS-1$
                null);
    }

    public void testGlobalDataReferenceBOM() throws CoreException, IOException,
            GenerationException, BOMIllegalStateException, DifferException {
        // This is a global data sanity test, based on the 'reference
        // BOM' - a simple example of various global data concepts that
        // has been used throughout the global data design/discussion docs.
        // A set of finer-grained tests will be added later.
        performTest(Collections.singletonList("com.example.ordermodel.bom"), //$NON-NLS-1$
                "com.example.ordermodel.bds-known-good-output.zip", //$NON-NLS-1$
                ".com.example.ordermodel.bds", //$NON-NLS-1$
                "1.0.0.1"); //$NON-NLS-1$
    }

    public void testGDComp() throws CoreException, IOException,
            GenerationException, BOMIllegalStateException, DifferException {
        performTest(Collections.singletonList("com.example.gdcomp.bom"), //$NON-NLS-1$
                "com.example.gdcomp.bds-known-good-output.zip", //$NON-NLS-1$
                ".com.example.gdcomp.bds", //$NON-NLS-1$
                "1.0.0.1"); //$NON-NLS-1$
    }

    public void testWithFetchingStereotype() throws CoreException, IOException,
            GenerationException, BOMIllegalStateException, DifferException {
        performTest(Collections.singletonList("com.example.fetching.bom"), //$NON-NLS-1$
                "com.example.fetching.bds-known-good-output.zip", //$NON-NLS-1$
                ".com.example.fetching.bds", //$NON-NLS-1$
                "1.0.0.1"); //$NON-NLS-1$
    }

}
