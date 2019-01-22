/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.xsdbom_tle;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.test.transform.util.TransformUtil;
import com.tibco.xpd.bom.types.PrimitivesUtil;

/**
 * Test extension of a complex type.
 * <p>
 * Tests 3.13 as described in 'BDS Support for Handling - Studio BOM Import'.
 * </p>
 * 
 * @author njpatel
 * 
 */
public class XSDBOM_TLE12A_ComplexTypeExtension extends AbstractTLETest {

    private static final String CHILDTYPE_CLASS = "ChildType";

    private static final String PERSON_CLASS = "Person";

    public XSDBOM_TLE12A_ComplexTypeExtension() {
        super("XSDBOM_TLE12A_ComplexTypeExtension.xsd");
    }

    @Override
    protected void checkBOMElements(Model model) throws Exception {
        EList<PackageableElement> packagedElements =
                model.getPackagedElements();
        assertEquals("Number of packageable elements", 2, packagedElements
                .size());

        // All elements should be classes
        for (PackageableElement element : packagedElements) {
            if (!(element instanceof Class)) {
                fail("Unexpected packageable element: "
                        + element.eClass().getName());
            }
        }

        checkClasses(packagedElements);
    }

    /**
     * Check the classes
     * 
     * @param packagedElements
     * @throws Exception
     */
    private void checkClasses(EList<PackageableElement> packagedElements)
            throws Exception {
        Class childType =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        CHILDTYPE_CLASS);
        Class person =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        PERSON_CLASS);

        // ChildType should be subclass of Person
        assertEquals("Generalizations of class '" + CHILDTYPE_CLASS + "'",
                1,
                childType.getGeneralizations().size());
        Generalization generalization = childType.getGeneralizations().get(0);
        assertTrue("Expected '" + PERSON_CLASS + "' to be superclass of '"
                + CHILDTYPE_CLASS + "'", generalization.getGeneral() == person);

        // Check attributes of each class
        assertEquals("Number of properties of class '" + CHILDTYPE_CLASS + "'",
                1,
                childType.getOwnedAttributes().size());
        assertEquals("Number of properties of class '" + PERSON_CLASS + "'",
                1,
                person.getOwnedAttributes().size());

        Property prop =
                TransformUtil.assertAttributeInClass(childType
                        .getOwnedAttributes(),
                        "school",
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        checkMultiplicity(prop, 1, 1);

        prop =
                TransformUtil.assertAttributeInClass(person
                        .getOwnedAttributes(),
                        "surname",
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        checkMultiplicity(prop, 1, 1);
    }

}
