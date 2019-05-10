/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.tests.cdm.transform;

import java.util.Arrays;

import org.eclipse.uml2.uml.Model;
import org.junit.Assert;

import com.tibco.bpm.da.dm.api.Attribute;
import com.tibco.bpm.da.dm.api.DataModel;
import com.tibco.bpm.da.dm.api.StructuredType;
import com.tibco.xpd.n2.cdm.transform.BomConstraintTransformer.NameValuePair;

/**
 * BOM properties constraints transformation test.
 * 
 * Package with class (ClassA) containing containing properties of various types
 * with set constraints and default values and allowed values.
 * <p>
 * Testing:
 * <li>constraints</li>
 * <li>default value</li>
 * <li>allowed values</li> for various types of properties.
 * </p>
 *
 * @author jarciuch
 * @since 1 Apr 2019
 */
@SuppressWarnings("nls")
public class ConstraintsCdmTransformTest
        extends AbstractSingleBomCdmTransformTest {

    /**
     * @see com.tibco.xpd.sce.tests.cdm.transform.AbstractSingleBomCdmTransformTest#getBomFileName()
     */
    @Override
    protected String getBomFileName() {
        return "Constraints.bom";
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

        Assert.assertEquals("com.example.constraints", cdmModel.getNamespace());

        // Class with primitive types attributes.
        StructuredType classA = cdmModel.getStructuredTypeByName("ClassA");
        Assert.assertNotNull(classA);

        // Assert Attributes
        Attribute textAttr = assertAttribute(classA,
                "textAttr",
                "base:Text",
                mandatory,
                !array);

        /*
         * TODO ACE-738: This causes failures in CDM validation
         * "Unknown constraint "pattern", therefore disabling for now until it
         * is supported.
         * 
         * So removed this until CDM supports pattern and
         * BomConstraintsTransformer has this re-enabled under the same TODO
         * 
         * new NameValuePair("pattern", "aPattern")
         */

        assertConstraints(textAttr,
                Arrays.asList(new NameValuePair("length", "30")));
        assertDefaultValue(textAttr, "textDefaut");
        //////////////////////
        Attribute text20Attr = assertAttribute(classA,
                "text20Attr",
                "base:Text",
                !mandatory,
                !array);
        assertConstraints(text20Attr,
                Arrays.asList(new NameValuePair("length", "20")));
        assertDefaultValue(text20Attr, null);
        //////////////////////
        Attribute integerAttr = assertAttribute(classA,
                "integerAttr",
                "base:FixedPointNumber",
                !mandatory,
                !array);
        assertConstraints(integerAttr,
                Arrays.asList(new NameValuePair("length", "10"),
                        new NameValuePair("decimalPlaces", "0"),
                        new NameValuePair("minValue", "23"),
                        new NameValuePair("minValueInclusive", "true"),
                        new NameValuePair("maxValue", "100"),
                        new NameValuePair("maxValueInclusive", "true")));
        assertDefaultValue(integerAttr, "30");
        //////////////////////
        Attribute integerAttrNoRestrictions = assertAttribute(classA,
                "integerAttrNoRestrictions",
                "base:FixedPointNumber",
                !mandatory,
                !array);
        assertConstraints(integerAttrNoRestrictions,
                Arrays.asList(new NameValuePair("length", "10"),
                        new NameValuePair("decimalPlaces", "0")));
        assertDefaultValue(integerAttrNoRestrictions, null);
        //////////////////////
        Attribute integer0to5Attr = assertAttribute(classA,
                "integer0to5Attr",
                "base:FixedPointNumber",
                !mandatory,
                !array);
        assertConstraints(integer0to5Attr,
                Arrays.asList(
                        new NameValuePair("length", "10"),
                        new NameValuePair("decimalPlaces", "0"),
                        new NameValuePair("minValue", "0"),
                        new NameValuePair("minValueInclusive", "true"),
                        new NameValuePair("maxValue", "5"),
                        new NameValuePair("maxValueInclusive", "true")));
        assertDefaultValue(integer0to5Attr, null);
        //////////////////////
        Attribute decimalAttr = assertAttribute(classA,
                "decimalAttr",
                "base:Number",
                !mandatory,
                !array);
        assertConstraints(decimalAttr,
                Arrays.asList(new NameValuePair("minValue", "-50.5"),
                        new NameValuePair("minValueInclusive", "false"),
                        new NameValuePair("maxValue", "500.23"),
                        new NameValuePair("maxValueInclusive", "true")));
        assertDefaultValue(decimalAttr, "8.2");
        //////////////////////
        Attribute decimalAttrNoRestrictions = assertAttribute(classA,
                "decimalAttrNoRestrictions",
                "base:Number",
                !mandatory,
                !array);
        assertDefaultValue(decimalAttrNoRestrictions, null);
        //////////////////////
        Attribute decimalPiTo200 = assertAttribute(classA,
                "decimalPiTo200",
                "base:Number",
                !mandatory,
                !array);

        assertConstraints(decimalPiTo200,
                Arrays.asList(new NameValuePair("minValue", "3.14"),
                        new NameValuePair("minValueInclusive", "false"),
                        new NameValuePair("maxValue", "200"),
                        new NameValuePair("maxValueInclusive", "true")));
        assertDefaultValue(decimalPiTo200, null);
        //////////////////////
        Attribute booleanAttr = assertAttribute(classA,
                "booleanAttr",
                "base:Boolean",
                !mandatory,
                !array);
        assertDefaultValue(booleanAttr, "true");
        //////////////////////
        Attribute dateAttr = assertAttribute(classA,
                "dateAttr",
                "base:Date",
                !mandatory,
                !array);
        assertDefaultValue(dateAttr, "2019-04-05");
        //////////////////////
        Attribute timeAttr = assertAttribute(classA,
                "timeAttr",
                "base:Time",
                !mandatory,
                !array);
        assertDefaultValue(timeAttr, "14:58");
        //////////////////////
        Attribute dateTimeTzAttr = assertAttribute(classA,
                "dateTimeTzAttr",
                "base:DateTimeTZ",
                !mandatory,
                !array);
        assertDefaultValue(dateTimeTzAttr, "2019-04-06T14:58:00.000+01:00");
        //////////////////////
        Attribute uriAttr = assertAttribute(classA,
                "uriAttr",
                "base:URI",
                !mandatory,
                !array);
        assertDefaultValue(uriAttr, "https;//tibco.com");
        //////////////////////
        Attribute favFruit = assertAttribute(classA,
                "favFruit",
                "base:Text",
                !mandatory,
                !array);
        assertDefaultValue(favFruit, null);
        assertAllowedValues(favFruit,
                Arrays.asList(new NameValuePair("Apple", "APPLE"),
                        new NameValuePair("Bannana", "BANNANA"),
                        new NameValuePair("Plum", "PLUM")));
        //////////////////////
    }

}
