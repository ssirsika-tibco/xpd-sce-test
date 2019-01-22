/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.xsdbom_tle;

import org.eclipse.emf.common.util.BasicEList;
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
public class XSDBOM_TLE10B_RootElementReference extends AbstractTLETest {

    private static final String PERSON_CLASS = "Person";

    private static final String MY_ELEMENT_TYPE_CLASS = "myElementType";

    private static final String MY_ELEMENT_TYPE2_CLASS = "nextElement2Type";

    private static final String SURNAME_PRIMITIVE = "surnameType";

    private static final String ID_PRIMITIVE = "idType";

    private static final String NEXT_ELEMENT_PRIMITIVE = "nextElementType";

    public XSDBOM_TLE10B_RootElementReference() {
        super("XSDBOM_TLE10B_RootElementReference.xsd");
    }

    @Override
    protected void checkBOMElements(Model model) throws Exception {
        EList<PackageableElement> packagedElements =
                model.getPackagedElements();
        assertEquals("Number of packageable elements", 6, packagedElements
                .size());

        TransformUtil.assertPackagedElementPrimitiveType(packagedElements,
                ID_PRIMITIVE);

        TransformUtil.assertPackagedElementPrimitiveType(packagedElements,
                SURNAME_PRIMITIVE);

        TransformUtil.assertPackagedElementPrimitiveType(packagedElements,
                NEXT_ELEMENT_PRIMITIVE);

        EList<Class> classes = new BasicEList<Class>();

        for (PackageableElement element : packagedElements) {
            if (element instanceof Class) {
                classes.add((Class) element);
            }
        }

        assertTrue("Expected a Class in this model",
                packagedElements.get(0) instanceof Class);

        checkClasses(classes);
    }

    /**
     * Check the classes.
     * 
     * @param cl
     * @throws Exception
     */
    private void checkClasses(EList<Class> classes) throws Exception {
        // Should be 2 classes
        assertEquals("Number of classes", 3, classes.size());

        /*
         * Check Person that has 2 properties
         */
        Class personClass =
                TransformUtil.assertPackagedElementClass(classes, PERSON_CLASS);
        EList<Property> properties = personClass.getOwnedAttributes();
        // Should have 2 properties
        assertEquals("Number of properties in Class " + PERSON_CLASS,
                2,
                properties.size());

        Property prop =
                TransformUtil.assertAttributeInClass(properties,
                        "surname",
                        SURNAME_PRIMITIVE);
        checkMultiplicity(prop, 1, 1);
        new PrimitiveTypeTextValidator((PrimitiveType) prop.getType())
                .isValid(SURNAME_PRIMITIVE, null, 200, null);

        prop =
                TransformUtil.assertAttributeInClass(properties,
                        "id",
                        ID_PRIMITIVE);
        checkMultiplicity(prop, 1, 1);

        /*
         * Check MyElementType that has 2 properties
         */
        Class myElemTypeClass =
                TransformUtil.assertPackagedElementClass(classes,
                        MY_ELEMENT_TYPE_CLASS);
        properties = myElemTypeClass.getOwnedAttributes();
        // Should have 2 properties
        assertEquals("Number of properties in Class " + MY_ELEMENT_TYPE_CLASS,
                2,
                properties.size());

        prop =
                TransformUtil.assertAttributeInClass(properties,
                        "nextElement",
                        NEXT_ELEMENT_PRIMITIVE);
        checkMultiplicity(prop, 0, 1);

        prop =
                TransformUtil.assertAttributeInClass(properties,
                        "nextElement2",
                        MY_ELEMENT_TYPE2_CLASS);
        checkMultiplicity(prop, 0, 1);

        /*
         * Check NextElement2Type1 that has 1 property
         */
        Class nextElement2Type1Class =
                TransformUtil.assertPackagedElementClass(classes,
                        MY_ELEMENT_TYPE2_CLASS);
        properties = nextElement2Type1Class.getOwnedAttributes();
        // Should have 1 property
        assertEquals("Number of properties in Class " + MY_ELEMENT_TYPE2_CLASS,
                1,
                properties.size());

        prop =
                TransformUtil.assertAttributeInClass(properties,
                        "myElement",
                        MY_ELEMENT_TYPE_CLASS);
        checkMultiplicity(prop, 1, 1);
    }

}
