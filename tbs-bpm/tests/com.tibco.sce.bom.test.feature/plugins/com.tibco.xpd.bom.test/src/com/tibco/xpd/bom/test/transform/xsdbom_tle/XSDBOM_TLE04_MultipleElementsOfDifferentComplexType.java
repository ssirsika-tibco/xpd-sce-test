/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.xsdbom_tle;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.bom.test.transform.util.PrimitiveTypeIntegerValidator;
import com.tibco.xpd.bom.test.transform.util.PrimitiveTypeTextValidator;
import com.tibco.xpd.bom.test.transform.util.TopLevelElementValidator;
import com.tibco.xpd.bom.test.transform.util.TransformUtil;

/**
 * This tests multiple top-level elements that are of different complex types.
 * <p>
 * Tests 3.5 as described in 'BDS Support for Handling - Studio BOM Import'.
 * </p>
 * 
 * @author njpatel
 * 
 */
public class XSDBOM_TLE04_MultipleElementsOfDifferentComplexType extends
        AbstractTLETest {

    private static final String PERSON_CLASS = "Person";

    private static final String CUSTOMER_CLASS = "Customer";

    private static final String NAME_ATTR = "name";

    private static final String CUSTOMERID_ATTR = "customerId";

    public XSDBOM_TLE04_MultipleElementsOfDifferentComplexType() {
        super("XSDBOM_TLE04_MultipleElementsOfDifferentComplexType.xsd");
    }

    @Override
    protected void checkBOMElements(Model model) throws Exception {
        EList<PackageableElement> packagedElements =
                model.getPackagedElements();
        assertEquals("Number of packageable elements", 2, packagedElements
                .size());

        for (PackageableElement element : packagedElements) {
            if (!(element instanceof Class)) {
                fail("Unexpected packageable element: "
                        + element.eClass().getName());
            }
        }

        checkClasses(packagedElements);
    }

    /**
     * Check the classes.
     * 
     * @param packagedElements
     * @throws Exception
     */
    private void checkClasses(EList<PackageableElement> packagedElements)
            throws Exception {
        Class person =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        PERSON_CLASS);
        /*
         * Validate person class - expect one attribute 'name'
         */
        assertEquals("Class '" + PERSON_CLASS + "' attributes", 1, person
                .getAttributes().size());
        assertEquals("Class '" + PERSON_CLASS + "' operations", 0, person
                .getOperations().size());
        Property property = person.getAttributes().get(0);

        assertEquals("Attribute name", NAME_ATTR, property.getName());

        checkMultiplicity(property, 1, 1);

        Type type = property.getType();
        assertTrue("Expected the property '" + NAME_ATTR
                + "' to be of a PrimitiveType", type instanceof PrimitiveType);

        // Validate the type of the property and its constraints
        new PrimitiveTypeTextValidator((PrimitiveType) type).isValid(null,
                null,
                50,
                null);
        
        TopLevelElementValidator validator = new TopLevelElementValidator(person.getModel(),3);
       
        validator.isValid("PersonElement",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        validator.isValid("PersonElement2",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        validator.isValid("CustomerElement",
                null,
                null,
                null,
                true,
                null,
                null,
                null,
                null);

        Class customer =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        CUSTOMER_CLASS);

        /*
         * Validate customer class - expected one attribute and generalization
         * to Person class
         */
        assertEquals("Class '" + CUSTOMER_CLASS + "' attributes", 1, customer
                .getAttributes().size());
        property = customer.getAttributes().get(0);
        assertEquals("Class '" + CUSTOMER_CLASS + "' operations", 0, customer
                .getOperations().size());

        assertEquals("Number of Generalizations from the '" + CUSTOMER_CLASS
                + "' class.", 1, customer.getGeneralizations().size());
        Generalization gen = customer.getGeneralizations().get(0);
        assertTrue("Expected '" + CUSTOMER_CLASS + "' to be a subclass of '"
                + PERSON_CLASS + "'.", gen.getGeneral() == person);
        assertEquals("Attribute name", CUSTOMERID_ATTR, property.getName());
        checkMultiplicity(property, 1, 1);

        type = property.getType();
        assertTrue("Expected the property '" + NAME_ATTR
                + "' to be of a PrimitiveType", type instanceof PrimitiveType);

        new PrimitiveTypeIntegerValidator((PrimitiveType) type).isValid(null,
                null,
                null,
                null,
                null,
                null);

        validator.isValid("CustomerElement",
                null,
                null,
                null,
                true,
                false,
                null,
                null,
                null);
    }
}
