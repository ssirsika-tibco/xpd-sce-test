/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.cdm.test.transform;

import java.util.function.Function;

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
     * @see com.tibco.xpd.n2.cdm.test.transform.AbstractSingleBomCdmTransformTest#getBomFileName()
     */
    @Override
    protected String getBomFileName() {
        return "Simple.bom";
    }

    /**
     * @see com.tibco.xpd.n2.cdm.test.transform.AbstractSingleBomCdmTransformTest#assertBomCdmTransformation(com.tibco.bpm.da.dm.api.DataModel,
     *      org.eclipse.uml2.uml.Model)
     */
    @Override
    protected void assertBomCdmTransformation(DataModel cdmModel,
            Model bomModel) {
        boolean mandatory = true;
        boolean array = true;

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
        Attribute textAttr =
                assertAttribute(simpleClassType,
                        "textAttr",
                        "base:Text",
                        mandatory,
                        !array);
        // Check label and description are correctly set.
        Assert.assertEquals("text Attr", textAttr.getLabel());
        Assert.assertEquals("testAttr desc", textAttr.getDescription());
        // Assert Types
        assertAttribute(simpleClassType,
                "integerAttr",
                "base:Number",
                !mandatory,
                !array);
        assertAttribute(simpleClassType,
                "decimalAttr",
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
        // TODO Uncomment when the other simple types are supported.
        // assertAttribute(simpleClassType, "booleanAttr", "base:Boolean",
        // !mandatory, !array);
        // assertAttribute(simpleClassType, "dateTimeTzAttr",
        // "base:DateTimeTZ", !mandatory, !array);
        // assertAttribute(simpleClassType, "idAttr", "base:Time", !mandatory,
        // !array);
        
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
                "integerAttrArray",
                "base:Number",
                !mandatory,
                array);
        assertAttribute(simpleClassArrayType,
                "decimalAttrArray",
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
        // TODO Uncomment when the other simple types are supported.
        // assertAttribute(simpleClassType, "booleanAttrArray", "base:Boolean",
        // !mandatory, array);
        // assertAttribute(simpleClassType, "dateTimeTzAttrArray",
        // "base:DateTimeTZ", !mandatory,array);
        // assertAttribute(simpleClassType, "idAttrArray", "base:Time");
    }

    /**
     * Asserts name and type of a simple type property.
     * 
     * @param type
     *            the class containing attribute.
     * @param name
     *            name of the attribute.
     * @param expectedType
     *            expected type name.
     * @return the attribute with the name or 'null'.
     */
    private Attribute assertAttribute(StructuredType type, String name,
            String expectedType, boolean expectedIsMandatory,
            boolean expectedIsArray) {
        Function<String, String> m =
                facet -> String.format("Type '%s' attr: '%s' facet: %s",
                        type.getName(),
                        name,
                        facet);

        Attribute attr = type.getAttributeByName(name);
        Assert.assertNotNull(
                String.format("Type %s is missing attr '%s'",
                        type.getName(),
                        name),
                attr);
        Assert.assertEquals(m.apply("name"), name, attr.getName());
        Assert.assertEquals(m.apply("type"), expectedType, attr.getType());
        Assert.assertEquals(m.apply("isMandatory"),
                expectedIsMandatory,
                attr.getIsMandatory());
        Assert.assertEquals(m.apply("isArray"),
                expectedIsArray,
                attr.getIsArray());
        return attr;
    }

}
