/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.tests.cdm.transform;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.uml2.uml.Model;
import org.junit.Assert;

import com.tibco.bpm.da.dm.api.DataModel;
import com.tibco.bpm.da.dm.api.StructuredType;

/**
 * StructuredTypes bom transformation test.
 * 
 * Package with classes containing containment references within the same
 * package and other packages.
 * <p>
 * Testing:
 * <li>1 -> 1 containment reference - ClassA.classB.</li>
 * <li>1 -> * containment reference - ClassB.classC.</li>
 * <li>1 -> 1..* containment reference - ClassA.classD.</li>
 * <li>1 -> 1 attr. with type in external package - ClassA.extRef.</li>
 * <li>1 -> * attr. with type in external package - ClassA.extRefArray.</li>
 * </p>
 *
 * @author jarciuch
 * @since 7 Mar 2019
 */
@SuppressWarnings("nls")
public class StructuredTypesCdmTransformTest
        extends AbstractSingleBomCdmTransformTest {

    @Override
    protected void validateCdmModel(DataModel cdmModel) {
        // Model validation reports external references as errors. So it is
        // switched for this test.

        // ValidationResult result = cdmModel.validate();
        // if (result.containsIssues() || result.containsErrors()) {
        // String msg = String.format("Issues:\n%s", result.toReportMessage());
        // //$NON-NLS-1$
        // System.out.printf(msg);
        // fail(msg);
        // }
    }

    /**
     * @see com.tibco.xpd.sce.tests.cdm.transform.AbstractSingleBomCdmTransformTest#getBomFileName()
     */
    @Override
    protected String getBomFileName() {
        return "StructuredTypes.bom";
    }

    /**
     * @see com.tibco.xpd.sce.tests.cdm.transform.AbstractSingleBomCdmTransformTest#getBomFileNames()
     *
     * @return
     */
    @Override
    protected List<String> getBomFileNames() {
        // Adding Simple.bom as it is referenced form StructuredTypes.bom and is
        // required in the project.
        ArrayList<String> fileNames =
                new ArrayList<String>(super.getBomFileNames());
        fileNames.add("Simple.bom");
        return fileNames;
    }

    /**
     * @see com.tibco.xpd.sce.tests.cdm.transform.AbstractSingleBomCdmTransformTest#printCdmModel()
     */
    @Override
    protected boolean printCdmModel() {
        return super.printCdmModel();
    }

    /**
     * @see com.tibco.xpd.sce.tests.cdm.transform.AbstractSingleBomCdmTransformTest#assertBomCdmTransformation(com.tibco.bpm.da.dm.api.DataModel,
     *      org.eclipse.uml2.uml.Model)
     */
    @Override
    protected void assertBomCdmTransformation(DataModel cdmModel,
            Model bomModel) {
        boolean mandatory = true;
        boolean array = true;

        Assert.assertEquals("com.example.structuredtypes",
                cdmModel.getNamespace());

        // Class with primitive types attributes.
        StructuredType classA = cdmModel.getStructuredTypeByName("ClassA");
        StructuredType classB = cdmModel.getStructuredTypeByName("ClassB");
        Assert.assertNotNull(classA);

        // Assert Types
        assertAttribute(classA, "a", "base:Text", !mandatory, !array);
        assertAttribute(classA, "classB", "ClassB", mandatory, !array);
        assertAttribute(classA, "classD", "ClassD", mandatory, array);
        assertAttribute(classA,
                "extRef",
                "com.example.simple.SimpleClass",
                !mandatory,
                !array);
        assertAttribute(classA,
                "extRefArray",
                "com.example.simple.SimpleClass",
                !mandatory,
                array);

        assertAttribute(classB, "classC", "ClassC", !mandatory, array);

    }

}
