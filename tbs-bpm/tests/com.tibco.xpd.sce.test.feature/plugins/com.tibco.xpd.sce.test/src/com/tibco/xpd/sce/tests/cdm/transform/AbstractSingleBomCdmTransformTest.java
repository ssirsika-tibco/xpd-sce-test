/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.tests.cdm.transform;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.uml2.uml.Model;
import org.junit.Assert;

import com.tibco.bpm.da.dm.api.AllowedValue;
import com.tibco.bpm.da.dm.api.Attribute;
import com.tibco.bpm.da.dm.api.DataModel;
import com.tibco.bpm.da.dm.api.StructuredType;
import com.tibco.bpm.da.dm.api.ValidationResult;
import com.tibco.xpd.n2.cdm.transform.BomConstraintTransformer.NameValuePair;

/**
 * Test single bom file transformation.
 *
 * @author jarciuch
 * @since 12 Mar 2019
 */
@SuppressWarnings("nls")
public abstract class AbstractSingleBomCdmTransformTest
        extends AbstractCdmTransformTest {

    // Used for a better label of true.
    protected final static boolean mandatory = true;

    // Used for a better label of true.
    protected final static boolean array = true;

    /**
     * @return <code>true</<code> if you would like to see output model.
     */
    protected boolean printCdmModel() {
        return false;
    }

    /**
     * Gets a .bom file name to be tested. The file will be imported to the
     * project from this test plug-in:
     * <code>resources/BomCdmTransformTest/Business Objects</code>.
     * <p>
     * For example: "Simple.bom"
     * </p>
     * 
     * @return bom filename to be tested.
     */
    protected abstract String getBomFileName();

    /**
     * Asserts transformation of the bom model (taken from
     * {@link #getBomFileName()} file).
     * 
     * @param cdmModel
     *            result CDM model.
     * @param bomModel
     *            source BOM model.
     */
    protected abstract void assertBomCdmTransformation(DataModel cdmModel,
            Model bomModel);

    /**
     * @see com.tibco.xpd.sce.tests.cdm.transform.AbstractCdmTransformTest#getBomFileName()
     */
    @Override
    protected List<String> getBomFileNames() {
        return Arrays.asList(getBomFileName());
    }

    protected void validateCdmModel(DataModel cdmModel) {
        ValidationResult result = cdmModel.validate();
        if (result.containsIssues() || result.containsErrors()) {
            String msg = String.format("Issues:\n%s", result.toReportMessage());
            System.out.printf(msg);
            fail(msg);
        } else {
            if (printCdmModel()) {
                System.out.printf("CDM model validation: OK");
            }
        }
    }

    /**
     * Execute transformation and calls
     * {@link #assertBomCdmTransformation(DataModel, DataModel)} to assert the
     * result.
     * 
     * @throws Exception
     */
    public void testBomCdmTransformation() throws Exception {
        BomCdmTransfomation transform =
                getBomCdmTransformation(getBomFolderPath(), getBomFileName());
        DataModel cdmModel = transform.getCdmModel();
        if (printCdmModel()) {
            System.out.printf("========== CDM model for %s =============\n",
                    getBomFileName());
            System.out.println(cdmModel.serialize());
            System.out.printf("---------- END model for %s -------------\n",
                    getBomFileName());
        }
        // Validate the translated model.
        validateCdmModel(cdmModel);

        // Assert the translated model.
        assertBomCdmTransformation(transform.getCdmModel(),
                transform.getBomModel());
    }

    // Utility methods.

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
    protected Attribute assertAttribute(StructuredType type, String name,
            String expectedType, boolean expectedIsMandatory,
            boolean expectedIsArray) {
        Function<String, String> m =
                facet -> String.format("Type '%s' attr: '%s' facet: %s",
                        type.getName(),
                        name,
                        facet);

        Attribute attr = type.getAttributeByName(name);
        Assert.assertNotNull(String
                .format("Type %s is missing attr '%s'", type.getName(), name),
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

    /**
     * Asserts attribute has provided collection of constraints (with expected
     * values).
     * 
     * @param attribute
     *            attribute to assert.
     * @param expectedConstaints
     *            collection of expected constraints.
     */
    protected void assertConstraints(Attribute attribute,
            Collection<NameValuePair> expectedConstaints) {
        Function<String, String> msg = constraintName -> String.format(
                "Type '%s' attr: '%s' constraint: %s",
                attribute.getParent().getName(),
                attribute.getName(),
                constraintName);
        for (NameValuePair c : expectedConstaints) {
            String message = msg.apply(c.getName());
            assertNotNull(message, attribute.getConstraint(c.getName()));
            assertEquals(message,
                    c.getValue(),
                    attribute.getConstraint(c.getName()).getValue());

        }
    }

    /**
     * Asserts attribute has provided collection of constraints (with expected
     * values) and also doesn't have not expected constraints.
     * 
     * @param attribute
     *            attribute to assert.
     * @param expectedConstaints
     *            collection of expected constraints.
     * @param notExpectedConstraintNames
     *            collection of not expected constraint names.
     */
    protected void assertConstraints(Attribute attribute,
            Collection<NameValuePair> expectedConstaints,
            Collection<String> notExpectedConstraintNames) {
        assertConstraints(attribute, expectedConstaints);
        Function<String, String> msg = constraintName -> String.format(
                "Type '%s' attr: '%s' constraint: %s was NOT expected.",
                attribute.getParent().getName(),
                attribute.getName(),
                constraintName);
        for (String constraintName : notExpectedConstraintNames) {
            String message = msg.apply(constraintName);
            assertNull(message, attribute.getConstraint(constraintName));
        }
    }

    /**
     * Asserts attribute has provided collection of allowedValues.
     * 
     * @param attribute
     *            attribute to assert.
     * @param expectedAllowedValues
     *            collection of expected constraints.
     */
    protected void assertAllowedValues(Attribute attribute,
            Collection<NameValuePair> expectedAllowedValues) {
        Function<String, String> msg = allowedValueLabel -> String.format(
                "Type '%s' attr: '%s' allowedValue: %s",
                attribute.getParent().getName(),
                attribute.getName(),
                allowedValueLabel);
        List<AllowedValue> attrAllowedValues = attribute.getAllowedValues();
        Function<String, Optional<String>> getValueOfAllowedValue =
                allowedValueLabel -> {
                    return attrAllowedValues.stream().filter(
                            av -> allowedValueLabel.equals(av.getLabel()))
                            .map(av -> av.getValue()).findFirst();
                };
        for (NameValuePair expectedAV : expectedAllowedValues) {
            Optional<String> attrAllowedValueValue =
                    getValueOfAllowedValue.apply(expectedAV.getName());
            String message = msg.apply(expectedAV.getName());
            assertTrue(message, attrAllowedValueValue.isPresent());
            assertEquals(message,
                    expectedAV.getValue(),
                    attrAllowedValueValue.get());

        }
    }

    /**
     * Asserts attribute has provided expected default value.
     * 
     * @param attribute
     *            attribute to assert.
     * @param expectedDefaultValue
     *            expected default value.
     */
    protected void assertDefaultValue(Attribute attribute,
            String expectedDefaultValue) {
        String msg = String.format("Type '%s' attr: '%s' :defaultValue",
                attribute.getParent().getName(),
                attribute.getName());
        assertEquals(msg, expectedDefaultValue, attribute.getDefaultValue());
    }

}