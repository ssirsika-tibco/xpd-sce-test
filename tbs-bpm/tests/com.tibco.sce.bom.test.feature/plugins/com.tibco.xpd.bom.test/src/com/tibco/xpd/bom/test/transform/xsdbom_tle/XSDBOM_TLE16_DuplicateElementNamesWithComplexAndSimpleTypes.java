/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.xsdbom_tle;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.test.transform.util.TransformUtil;
import com.tibco.xpd.bom.types.PrimitivesUtil;

/**
 * Test the referencing of simple-type root elements.
 * <p>
 * Tests 3.11 as described in 'BDS Support for Handling - Studio BOM Import'.
 * </p>
 * 
 * @author glewis
 * 
 */
public class XSDBOM_TLE16_DuplicateElementNamesWithComplexAndSimpleTypes extends
        AbstractTLETest {

    private static final String ELEM1_CLASS = "Elem1";

    private static final String ELEM1_TYPE_CLASS = "topElem1Type";

    private static final String ELEM4_CLASS = "Elem4";

    private static final String ELEM4_TYPE_CLASS = "topElem4Type";

    private static final String ELEM2_PRIMITIVE = "Elem2";

    private static final String ELEM3_ENUMERATION = "Elem3";

    public XSDBOM_TLE16_DuplicateElementNamesWithComplexAndSimpleTypes() {
        super(
                "XSDBOM_TLE16_DuplicateElementNamesWithComplexAndSimpleTypes.xsd");
    }

    @Override
    protected void checkBOMElements(Model model) throws Exception {
        EList<PackageableElement> packagedElements =
                model.getPackagedElements();
        assertEquals("Number of packageable elements",
                6,
                packagedElements.size());

        TransformUtil.assertPackagedElementPrimitiveType(packagedElements,
                ELEM2_PRIMITIVE);

        TransformUtil.assertPackagedElementEnumeration(packagedElements,
                ELEM3_ENUMERATION);

        TransformUtil.assertPackagedElementClass(packagedElements, ELEM1_CLASS);

        TransformUtil.assertPackagedElementClass(packagedElements,
                ELEM1_TYPE_CLASS);

        TransformUtil.assertPackagedElementClass(packagedElements, ELEM4_CLASS);

        TransformUtil.assertPackagedElementClass(packagedElements,
                ELEM4_TYPE_CLASS);

        EList<Class> classes = new BasicEList<Class>();

        for (PackageableElement element : packagedElements) {
            if (element instanceof Class) {
                classes.add((Class) element);
            }
        }

        checkClasses(classes);
    }

    /**
     * Check the classes.
     * 
     * @param cl
     * @throws Exception
     */
    private void checkClasses(EList<Class> classes) throws Exception {
        // Should be 4 classes
        assertEquals("Number of classes", 4, classes.size());

        /*
         * Check Elem1Type that has 4 properties
         */
        Class elem1TypeClass =
                TransformUtil.assertPackagedElementClass(classes,
                        ELEM1_TYPE_CLASS);
        EList<Property> properties = elem1TypeClass.getOwnedAttributes();
        // Should have 4 properties
        assertEquals("Number of properties in Class " + ELEM1_TYPE_CLASS,
                4,
                properties.size());

        TransformUtil.assertAttributeInClass(properties,
                "attr1",
                PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME);

        TransformUtil.assertAttributeInClass(properties,
                "attr2",
                PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME);

        TransformUtil.assertAttributeInClass(properties,
                "elem1",
                PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);

        TransformUtil.assertAttributeInClass(properties,
                "elem2",
                PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
    }

}
