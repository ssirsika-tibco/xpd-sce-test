/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.xsdbom_tle;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.PrimitiveType;

import com.tibco.xpd.bom.test.transform.util.PrimitiveTypeTextValidator;
import com.tibco.xpd.bom.test.transform.util.TransformUtil;

/**
 * Tests top-level enumeration elements.
 * <p>
 * Tests 3.8 as described in 'BDS Support for Handling - Studio BOM Import'.
 * </p>
 * 
 * @author njpatel
 * 
 */
public class XSDBOM_TLE07_TopLevelEnumeration extends AbstractTLETest {

    public XSDBOM_TLE07_TopLevelEnumeration() {
        super("XSDBOM_TLE07_TopLevelEnumeration.xsd");
    }

    @Override
    protected void checkBOMElements(Model model) throws Exception {
        EList<PackageableElement> packagedElements =
                model.getPackagedElements();
        // Expecting 5 elements
        assertEquals("Number of packaged elements", 5, packagedElements.size());

        // Separate the enumerations and primitive types
        EList<PrimitiveType> pTypes = new BasicEList<PrimitiveType>();
        EList<Enumeration> enums = new BasicEList<Enumeration>();
        for (PackageableElement elem : packagedElements) {
            if (elem instanceof Enumeration) {
                enums.add((Enumeration) elem);
            } else if (elem instanceof PrimitiveType) {
                pTypes.add((PrimitiveType) elem);
            } else {
                // Not expected
                fail("Unexpected packageable element in the model: "
                        + elem.eClass().getName());
            }
        }

        checkEnumerations(enums);
        checkPrimitiveTypes(pTypes);
    }

    /**
     * Check the primitive types.
     * 
     * @param types
     * @throws Exception
     */
    private void checkPrimitiveTypes(EList<PrimitiveType> types)
            throws Exception {
        PrimitiveType pt = null;
        PrimitiveTypeTextValidator textValidator = null;

        pt =
                TransformUtil.assertPackagedElementPrimitiveType(types,
                        "attrString");

        textValidator = new PrimitiveTypeTextValidator(pt);
        textValidator.isValid("attrString", null, null, null);

        pt =
                TransformUtil.assertPackagedElementPrimitiveType(types,
                        "attrGDay");

        textValidator = new PrimitiveTypeTextValidator(pt);
        textValidator.isValid("attrGDay", null, null, null);

        pt =
                TransformUtil.assertPackagedElementPrimitiveType(types,
                        "attrGMonth");

        textValidator = new PrimitiveTypeTextValidator(pt);
        textValidator.isValid("attrGMonth", null, null, null);

        pt =
                TransformUtil.assertPackagedElementPrimitiveType(types,
                        "NoValues");

        textValidator = new PrimitiveTypeTextValidator(pt);
        textValidator.isValid("NoValues", null, null, null);

    }

    /**
     * Check the enumerations.
     * 
     * @param enums
     * @throws Exception
     */
    private void checkEnumerations(EList<Enumeration> enums) throws Exception {

        /*
         * Check SubAccountType (with 6 literals)
         */
        Enumeration enumeration =
                TransformUtil.assertPackagedElementEnumeration(enums,
                        "SubAccountType");
        EList<EnumerationLiteral> literals = enumeration.getOwnedLiterals();
        assertEquals("SubAccountType literals", 6, literals.size());

        // Check the literals
        TransformUtil.assertEnumLiteralInEnum(literals, "CASH");
        TransformUtil.assertEnumLiteralInEnum(literals, "MARGIN");
        TransformUtil.assertEnumLiteralInEnum(literals, "INCOME");
        TransformUtil.assertEnumLiteralInEnum(literals, "SHORT");
        TransformUtil.assertEnumLiteralInEnum(literals, "DVPRVP");
        TransformUtil.assertEnumLiteralInEnum(literals, "DIVIDEND");
    }

}
