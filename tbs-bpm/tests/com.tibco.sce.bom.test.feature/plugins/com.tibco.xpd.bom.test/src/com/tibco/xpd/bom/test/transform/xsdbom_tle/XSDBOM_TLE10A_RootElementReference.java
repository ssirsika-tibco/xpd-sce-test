/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.xsdbom_tle;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.test.transform.util.PrimitiveTypeTextValidator;
import com.tibco.xpd.bom.test.transform.util.TransformUtil;

/**
 * Test the referencing of simple-type root elements.
 * <p>
 * Tests 3.11 as described in 'BDS Support for Handling - Studio BOM Import'.
 * </p>
 * 
 * @author glewis
 * 
 */
public class XSDBOM_TLE10A_RootElementReference extends AbstractTLETest {

    private static final String PERSON_CLASS = "Person";

    private static final String SURNAME_PRIMITIVE = "surnameType";

    public XSDBOM_TLE10A_RootElementReference() {
        super("XSDBOM_TLE10A_RootElementReference.xsd");
    }

    @Override
    protected void checkBOMElements(Model model) throws Exception {
        EList<PackageableElement> packagedElements =
                model.getPackagedElements();
        assertEquals("Number of packageable elements", 2, packagedElements
                .size());
        assertTrue("Expected a Class in this model",
                packagedElements.get(0) instanceof Class);
        checkClass((Class) packagedElements.get(0));
    }

    /**
     * Check the class.
     * 
     * @param cl
     * @throws Exception
     */
    private void checkClass(Class cl) throws Exception {
        assertEquals("Class name", PERSON_CLASS, cl.getName());
        EList<Property> properties = cl.getOwnedAttributes();
        assertEquals("Number of properties of class '" + PERSON_CLASS + "'",
                1,
                properties.size());

        // Check attributes
        Property property =
                TransformUtil.assertAttributeInClass(properties,
                        "surname",
                        SURNAME_PRIMITIVE);
        checkMultiplicity(property, 1, 1);

        new PrimitiveTypeTextValidator((PrimitiveType) property.getType())
                .isValid(SURNAME_PRIMITIVE, null, null, null);
    }

}
