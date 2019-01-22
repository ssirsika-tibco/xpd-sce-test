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
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.resources.utils.UML2ModelUtil;
import com.tibco.xpd.bom.test.transform.util.TransformUtil;
import com.tibco.xpd.bom.types.PrimitivesUtil;

/**
 * Test nested anonymous types.
 * <p>
 * Tests 3.10 as described in 'BDS Support for Handling - Studio BOM Import'.
 * </p>
 * 
 * @author njpatel
 * 
 */
public class XSDBOM_TLE09_NestedAnonymousTypes extends AbstractTLETest {

    private static final String ANONYMOUSNESTEDTYPES_CLASS =
            "AnonymousNestedType";

    private static final String ANONYMOUSNESTEDTYPES_CLASS2 =
            "AnonymousNested2Type";

    private static final String ADDRESSTYPE_CLASS = "addressType";

    private static final String ADDRESSTYPE1_CLASS = "addressType1";

    private static final String TOPELEMENTTYPE_CLASS = "topElementType";

    public XSDBOM_TLE09_NestedAnonymousTypes() {
        super("XSDBOM_TLE09_NestedAnonymousTypes.xsd");
    }

    @Override
    protected void checkBOMElements(Model model) throws Exception {
        EList<PackageableElement> packagedElements =
                model.getPackagedElements();

        /*
         * Expecting 3 packageable elements - 5 classes, 2 association.
         */
        assertEquals("Number of packageable elements", 7, packagedElements
                .size());
        EList<Class> classes = new BasicEList<Class>();
        EList<Association> associations = new BasicEList<Association>();

        for (PackageableElement element : packagedElements) {
            if (element instanceof Class) {
                classes.add((Class) element);
            } else if (element instanceof Association) {
                associations.add((Association) element);
            } else {
                fail("Unexpected packageable element: "
                        + element.eClass().getName());
            }
        }

        // Should be only one association
        assertEquals("Number of associations", 2, associations.size());
        checkClasses(classes);

    }

    private void checkClasses(EList<Class> classes) throws Exception {
        // Should be 2 classes
        assertEquals("Number of classes", 5, classes.size());

        /*
         * Check AnonymousNestedType that has 3 properties (one being an
         * association)
         */
        Class anonymousNestedType =
                TransformUtil.assertPackagedElementClass(classes,
                        ANONYMOUSNESTEDTYPES_CLASS);
        EList<Property> properties = anonymousNestedType.getOwnedAttributes();
        // Should have 3 properties
        assertEquals("Number of properties in Class "
                + ANONYMOUSNESTEDTYPES_CLASS, 3, properties.size());

        Property prop =
                TransformUtil.assertAttributeInClass(properties,
                        "city",
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        checkMultiplicity(prop, 1, 1);

        prop =
                TransformUtil.assertAttributeInClass(properties,
                        "country",
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        checkMultiplicity(prop, 1, 1);

        Property assocProp =
                TransformUtil.assertAttributeInClass(properties,
                        "address",
                        null);
        checkMultiplicity(assocProp, 1, 1);

        assertNotNull("Expected address property in "
                + ANONYMOUSNESTEDTYPES_CLASS + " to be an Association",
                assocProp.getAssociation());

        /*
         * Check AnonymousNested2Type that has 3 properties (one being an
         * association)
         */
        Class anonymousNested2Type =
                TransformUtil.assertPackagedElementClass(classes,
                        ANONYMOUSNESTEDTYPES_CLASS2);
        properties = anonymousNested2Type.getOwnedAttributes();
        // Should have 3 properties
        assertEquals("Number of properties in Class "
                + ANONYMOUSNESTEDTYPES_CLASS2, 3, properties.size());

        prop =
                TransformUtil.assertAttributeInClass(properties,
                        "city",
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        checkMultiplicity(prop, 1, 1);

        prop =
                TransformUtil.assertAttributeInClass(properties,
                        "country",
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        checkMultiplicity(prop, 1, 1);

        Property assocProp2 =
                TransformUtil.assertAttributeInClass(properties,
                        "address",
                        null);
        checkMultiplicity(assocProp2, 1, 1);

        assertNotNull("Expected address property in "
                + ANONYMOUSNESTEDTYPES_CLASS2 + " to be an Association",
                assocProp2.getAssociation());

        /*
         * Check AddressType class - should have 1 property
         */
        Class addressType =
                TransformUtil.assertPackagedElementClass(classes,
                        ADDRESSTYPE_CLASS);
        properties = addressType.getOwnedAttributes();
        // Should have 1 property
        assertEquals("Number of properties in Class "
                + ANONYMOUSNESTEDTYPES_CLASS, 1, properties.size());
        prop =
                TransformUtil.assertAttributeInClass(properties,
                        "firstline",
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        checkMultiplicity(prop, 1, 1);

        /*
         * Check AddressType2 class - should have 1 property
         */
        Class addressType2 =
                TransformUtil.assertPackagedElementClass(classes,
                        ADDRESSTYPE1_CLASS);
        properties = addressType2.getOwnedAttributes();
        // Should have 1 property
        assertEquals("Number of properties in Class "
                + ANONYMOUSNESTEDTYPES_CLASS2, 1, properties.size());
        prop =
                TransformUtil.assertAttributeInClass(properties,
                        "firstline",
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        checkMultiplicity(prop, 1, 1);

        /*
         * Check TopElementType class - should have 1 property
         */
        Class topLevelElement =
                TransformUtil.assertPackagedElementClass(classes,
                        TOPELEMENTTYPE_CLASS);
        properties = topLevelElement.getOwnedAttributes();
        // Should have 1 property
        assertEquals("Number of properties in Class "
                + ANONYMOUSNESTEDTYPES_CLASS2, 1, properties.size());
        prop =
                TransformUtil.assertAttributeInClass(properties,
                        "name",
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        checkMultiplicity(prop, 1, 1);

        /* Validate association */
        checkAssociation(assocProp, anonymousNestedType, addressType);

        /* Validate association */
        checkAssociation(assocProp2, anonymousNested2Type, addressType2);

    }

    /**
     * Check the Association Property.
     * 
     * @param assocProperty
     * @param src
     * @param tgt
     */
    private void checkAssociation(Property assocProperty, Class src, Class tgt) {
        Association assoc = assocProperty.getAssociation();

        assertEquals("Number of member ends", 2, assoc.getMemberEnds().size());
        assertEquals("Number of owned ends", 1, assoc.getOwnedEnds().size());
        Property property = assoc.getOwnedEnds().get(0);

        assertTrue("Expected the type of the '" + assocProperty.getName()
                + "' property to be '" + ADDRESSTYPE_CLASS + "'", assocProperty
                .getType() == tgt);
        assertTrue("Expected the type of the association owned end property to be '"
                + ANONYMOUSNESTEDTYPES_CLASS + "'",
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
