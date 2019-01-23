/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.xsdbom_tle;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.PrimitiveType;

import com.tibco.xpd.bom.test.transform.util.PrimitiveTypeDecimalValidator;
import com.tibco.xpd.bom.test.transform.util.PrimitiveTypeIntegerValidator;
import com.tibco.xpd.bom.test.transform.util.PrimitiveTypeTextValidator;
import com.tibco.xpd.bom.test.transform.util.TransformUtil;
import com.tibco.xpd.bom.types.PrimitivesUtil;

/**
 * Test anonymous simple types.
 * <p>
 * Tests 3.9 as described in 'BDS Support for Handling - Studio BOM Import'.
 * </p>
 * 
 * @author njpatel
 * 
 */
public class XSDBOM_TLE08_AnonymousSimpleTypes extends AbstractTLETest {

    public XSDBOM_TLE08_AnonymousSimpleTypes() {
        super("XSDBOM_TLE08_AnonymousSimpleTypes.xsd");
    }

    @Override
    protected void checkBOMElements(Model model) throws Exception {
        EList<PackageableElement> packagedElements =
                model.getPackagedElements();
        /*
         * Expected 6 primitive types
         */
        assertEquals("Number of packageable elements", 6, packagedElements
                .size());

        for (PackageableElement element : packagedElements) {
            if (!(element instanceof PrimitiveType)) {
                fail("Expected primitive type, got "
                        + element.eClass().getName());
            }
        }

        checkPrimitiveTypes(packagedElements);
    }

    /**
     * Check primtive types.
     * 
     * @param packagedElements
     * @throws Exception
     */
    private void checkPrimitiveTypes(EList<PackageableElement> packagedElements)
            throws Exception {
        // String
        PrimitiveType pType =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "MyStrElementType");
        new PrimitiveTypeTextValidator(pType).isValid("MyStrElementType",
                null,
                200,
                ".*");

        // Name
        pType =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "MyNameElementType");
        new PrimitiveTypeTextValidator(pType).isValid("MyNameElementType",
                null,
                150,
                null);

        // Entity
        pType =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "MyEntityElementType");
        new PrimitiveTypeTextValidator(pType).isValid("MyEntityElementType",
                null,
                null,
                null);

        // Long
        pType =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "MyLongElementType");
        new PrimitiveTypeIntegerValidator(pType).isValid("MyLongElementType",
                null,
                null,
                null,
                null,
                PrimitivesUtil.INTEGER_SUBTYPE_FIXEDLENGTH);

        // Double
        pType =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "MyDoubleElementType");
        new PrimitiveTypeDecimalValidator(pType).isValid("MyDoubleElementType",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                PrimitivesUtil.DECIMAL_SUBTYPE_FLOATINGPOINT);

        // Any URI
        pType =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "MyAnyURIElementType");
        assertTrue("Base Primitive Type:", PrimitivesUtil
                .getBasePrimitiveType(pType).getName()
                .equals(PrimitivesUtil.BOM_PRIMITIVE_URI_NAME));

    }

}
