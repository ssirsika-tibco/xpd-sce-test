/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.tests.cdm.transform;

import org.eclipse.uml2.uml.Model;
import org.junit.Assert;

import com.tibco.bpm.da.dm.api.Attribute;
import com.tibco.bpm.da.dm.api.DataModel;
import com.tibco.bpm.da.dm.api.StructuredType;

/**
 * Simple bom transformation test.
 * 
 * Package with one class containing attributes of all simple types and another
 * class containing attributes of arrays of all simple types.
 * <p>
 * Testing:
 * <li>DataModel (namepsace),</li>
 * <li>StructuredType (name, label, desctiption),</li>
 * <li>Attribute (name, label, description,base types, isArray,
 * isMandatory)</li>
 * </p>
 *
 * @author jarciuch
 * @since 7 Mar 2019
 */
@SuppressWarnings("nls")
public class SimpleCdmTransformTest extends AbstractSingleBomCdmTransformTest {

    /**
     * @see com.tibco.xpd.sce.tests.cdm.transform.AbstractSingleBomCdmTransformTest#getBomFileName()
     */
    @Override
    protected String getBomFileName() {
        return "Simple.bom";
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

        Assert.assertEquals("com.example.simple", cdmModel.getNamespace());

        // Class with primitive types attributes.
        StructuredType simpleClassType =
                cdmModel.getStructuredTypeByName("SimpleClass");
        Assert.assertNotNull(simpleClassType);
        Assert.assertEquals("SimpleClass", simpleClassType.getName());
        Assert.assertEquals("Simple Class", simpleClassType.getLabel());
        Assert.assertEquals("SimpleClass desc",
                simpleClassType.getDescription());
        // textAttribute is mandatory
        Attribute textAttr = assertAttribute(simpleClassType,
                "textAttr",
                "base:Text",
                mandatory,
                !array);
        // Check label and description are correctly set.
        Assert.assertEquals("text Attr", textAttr.getLabel());
        Assert.assertEquals("testAttr desc", textAttr.getDescription());
        // Assert Types
        assertAttribute(simpleClassType,
                "fixedPointNumberAttr",
                "base:FixedPointNumber",
                !mandatory,
                !array);
        assertAttribute(simpleClassType,
                "numberAttr",
                "base:Number",
                !mandatory,
                !array);
        assertAttribute(simpleClassType,
                "dateAttr",
                "base:Date",
                !mandatory,
                !array);
        assertAttribute(simpleClassType,
                "timeAttr",
                "base:Time",
                !mandatory,
                !array);
        assertAttribute(simpleClassType,
                "booleanAttr",
                "base:Boolean",
                !mandatory,
                !array);
        assertAttribute(simpleClassType,
                "dateTimeTzAttr",
                "base:DateTimeTZ",
                !mandatory,
                !array);
        assertAttribute(simpleClassType,
                "uriAttr",
                "base:URI",
                !mandatory,
                !array);

        // Class with primitive type arrays attributes.
        StructuredType simpleClassArrayType =
                cdmModel.getStructuredTypeByName("SimpleClassArray");
        Assert.assertNotNull(simpleClassArrayType);
        // Check that if description is not set for the class then transformed
        // description is null.
        Assert.assertNull(simpleClassArrayType.getDescription());
        // Assert Types
        // Checks that textAttrArray is also mandatory.
        assertAttribute(simpleClassArrayType,
                "textAttrArray",
                "base:Text",
                mandatory,
                array);
        assertAttribute(simpleClassArrayType,
                "fixedPointNumberAttrArray",
                "base:FixedPointNumber",
                !mandatory,
                array);
        assertAttribute(simpleClassArrayType,
                "numberAttrArray",
                "base:Number",
                !mandatory,
                array);
        assertAttribute(simpleClassArrayType,
                "dateAttrArray",
                "base:Date",
                !mandatory,
                array);
        assertAttribute(simpleClassArrayType,
                "timeAttrArray",
                "base:Time",
                !mandatory,
                array);
        assertAttribute(simpleClassArrayType,
                "booleanAttrArray",
                "base:Boolean",
                !mandatory,
                array);
        assertAttribute(simpleClassArrayType,
                "dateTimeTzAttrArray",
                "base:DateTimeTZ",
                !mandatory,
                array);
        assertAttribute(simpleClassArrayType,
                "uriAttrArray",
                "base:URI",
                !mandatory,
                array);
    }

}
