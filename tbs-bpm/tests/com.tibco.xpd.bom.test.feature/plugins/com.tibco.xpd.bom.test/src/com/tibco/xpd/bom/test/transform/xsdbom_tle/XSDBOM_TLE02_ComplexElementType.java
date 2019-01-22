/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.xsdbom_tle;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.bom.test.transform.util.PrimitiveTypeTextValidator;
import com.tibco.xpd.bom.test.transform.util.TopLevelElementValidator;

/**
 * 
 * Tests the Element of complex type. The schema used for this test contains a
 * single top-level element that is of a complex type.
 * <p>
 * <i> Tests 3.3 as described in 'BDS Support for Handling - Studio BOM Import'.
 * </i>
 * </p>
 * 
 * @author njpatel
 */
public class XSDBOM_TLE02_ComplexElementType extends AbstractTLETest {

    private static final String CLASS_NAME = "Person";

    private static final String ATTRIBUTE_NAME = "name";

    public XSDBOM_TLE02_ComplexElementType() {
        this("XSDBOM_TLE02_ComplexElementType.xsd");
    }

    protected XSDBOM_TLE02_ComplexElementType(String modelName) {
        super(modelName);
    }

    @Override
    protected void checkBOMElements(Model model) throws Exception {
        EList<PackageableElement> elements = model.getPackagedElements();
        assertEquals("Number of classes", 1, elements.size());
        assertTrue("Expected a CLASS as the only element",
                elements.get(0) instanceof Class);
        Class cl = (Class) elements.get(0);
        checkClass(cl);
        validateAppliedStereotypes(cl);
    }

    /**
     * Validate the stereotypes applied on the "Person" class.
     * 
     * @param cl
     */
    protected void validateAppliedStereotypes(Class cl) {
        TopLevelElementValidator validator =
                new TopLevelElementValidator(cl.getModel(), 1);
        validator.isValid("PersonElement",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
    }

    /**
     * Validate the Person class.
     * 
     * @param cl
     */
    protected void checkClass(Class cl) {
        assertEquals("Class name", CLASS_NAME, cl.getName());
        assertEquals("Class '" + CLASS_NAME + "' attributes", 1, cl
                .getAttributes().size());
        assertEquals("Class '" + CLASS_NAME + "' operations", 0, cl
                .getOperations().size());
        Property property = cl.getAttributes().get(0);

        // Check attribute
        assertEquals("Attribute name", ATTRIBUTE_NAME, property.getName());

        checkMultiplicity(property, 1, 1);

        Type type = property.getType();
        assertTrue("Expected the property '" + ATTRIBUTE_NAME
                + "' to be of a PrimitiveType", type instanceof PrimitiveType);

        // Validate the type of the property and its constraints
        new PrimitiveTypeTextValidator((PrimitiveType) type).isValid(null,
                null,
                50,
                null);
    }
}
