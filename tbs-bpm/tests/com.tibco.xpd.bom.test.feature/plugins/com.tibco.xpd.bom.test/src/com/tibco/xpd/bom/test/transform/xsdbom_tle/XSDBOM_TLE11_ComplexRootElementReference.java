/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.xsdbom_tle;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.resources.utils.UML2ModelUtil;
import com.tibco.xpd.bom.test.transform.util.TransformUtil;
import com.tibco.xpd.bom.types.PrimitivesUtil;

/**
 * Test the referencing of complex-type root elements.
 * <p>
 * Tests 3.12 as described in 'BDS Support for Handling - Studio BOM Import'.
 * </p>
 * 
 * @author njpatel
 * 
 */
public class XSDBOM_TLE11_ComplexRootElementReference extends AbstractTLETest {
    private static final String BUSDRIVERTYPE_CLASS = "BusDriverType";

    private static final String PERSON_CLASS = "Person";

    private static final String CHILD_CLASS = "Child";

    private static final String SURNAME_PRIMITIVE = "surnameType";

    public XSDBOM_TLE11_ComplexRootElementReference() {
        super("XSDBOM_TLE11_ComplexRootElementReference.xsd");
    }

    @Override
    protected void checkBOMElements(Model model) throws Exception {
        // Expect 3 elements - 3 classes, 1 primitive
        EList<PackageableElement> packagedElements =
                model.getPackagedElements();
        assertEquals("Number of packageable elements", 4, packagedElements
                .size());

        EList<Class> classes = new BasicEList<Class>();

        for (PackageableElement element : packagedElements) {
            if (element instanceof Class) {
                classes.add((Class) element);
            } else if (element instanceof PrimitiveType) {
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                SURNAME_PRIMITIVE);
            } else {
                fail("Unexpected packageable element: "
                        + element.eClass().getName());
            }
        }

        assertEquals("Number of classes", 3, classes.size());

        checkClasses(classes);
    }

    /**
     * Validate the classes
     * 
     * @param classes
     * @throws Exception
     */
    private void checkClasses(EList<Class> classes) throws Exception {
        Class busDriverType =
                TransformUtil.assertPackagedElementClass(classes,
                        BUSDRIVERTYPE_CLASS);
        Class person =
                TransformUtil.assertPackagedElementClass(classes, PERSON_CLASS);

        Class child =
                TransformUtil.assertPackagedElementClass(classes, CHILD_CLASS);

        // Person should have one property
        Property prop =
                TransformUtil.assertAttributeInClass(person
                        .getOwnedAttributes(),
                        "surname",
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        checkMultiplicity(prop, 1, 1);

        // Bus driver should have one property - association property
        Property assocProp =
                TransformUtil.assertAttributeInClass(busDriverType
                        .getOwnedAttributes(), "personElem", PERSON_CLASS);
        checkMultiplicity(assocProp, 1, 1);

        // Child should have one property
        prop =
                TransformUtil
                        .assertAttributeInClass(child.getOwnedAttributes(),
                                "surname",
                                SURNAME_PRIMITIVE);
        checkMultiplicity(prop, 1, 1);
    }

    /**
     * Validate the association.
     * 
     * @param assocProp
     * @param src
     * @param tgt
     */
    private void checkAssociation(Property assocProp, Class src, Class tgt) {
        assertNotNull("Expected a property to be an association property",
                assocProp.getAssociation());
        Association assoc = assocProp.getAssociation();

        assertEquals("Number of member ends", 2, assoc.getMemberEnds().size());
        assertEquals("Number of owned ends", 1, assoc.getOwnedEnds().size());
        Property property = assoc.getOwnedEnds().get(0);

        assertTrue("Expected the type of the '" + assocProp.getName()
                + "' property to be '" + PERSON_CLASS + "'", assocProp
                .getType() == tgt);
        assertTrue("Expected the type of the association owned end property to be '"
                + BUSDRIVERTYPE_CLASS + "'",
                property.getType() == src);

        // Should be a composition
        AggregationKind type = UML2ModelUtil.getAggregationType(assoc);
        assertEquals("Aggregation kind",
                AggregationKind.COMPOSITE_LITERAL,
                type);

        // Check multiplicity
        checkMultiplicity(property, 1, 1);
    }

}
