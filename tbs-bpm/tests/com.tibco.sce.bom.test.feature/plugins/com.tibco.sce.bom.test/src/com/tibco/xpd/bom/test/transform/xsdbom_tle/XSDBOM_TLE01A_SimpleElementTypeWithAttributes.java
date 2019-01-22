/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.xsdbom_tle;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.PrimitiveType;

import com.tibco.xpd.bom.test.transform.util.TopLevelElementValidator;
import com.tibco.xpd.bom.test.transform.util.TransformUtil;

/**
 * 
 * Tests the simplest form of top level element of type xsd base type.
 * <p>
 * &lt;xsd:element name="testStringData" type="xsd:string"&gt;
 * </p>
 * The schema used in this test contains a top level element for each supported
 * xsd base types <br>
 * </br>
 * <p>
 * <i> Tests 3.1 and 3.2 as described in 'BDS Support for Handling - Studio BOM
 * Import'. </i>
 * </p>
 * 
 * @author rgreen
 */
public class XSDBOM_TLE01A_SimpleElementTypeWithAttributes extends
        AbstractTLETest {

    public XSDBOM_TLE01A_SimpleElementTypeWithAttributes() {
        super("XSDBOM_TLE01A_SimpleElementTypeWithAttributes.xsd");
    }

    @Override
    protected void checkBOMElements(Model model) throws Exception {

        EList<PackageableElement> packagedElements =
                model.getPackagedElements();

        checkPrimitiveTypes(packagedElements);

    }

    private void checkPrimitiveTypes(EList<PackageableElement> packagedElements)
            throws Exception {

        assertEquals("Number of Primitive types expected",
                3,
                packagedElements.size());

        checkText(packagedElements);
    }

    /**
     * Check all XSD data types that end up as BOM Text
     * 
     * @param packagedElements
     * @throws Exception
     */
    private void checkText(EList<PackageableElement> packagedElements)
            throws Exception {
        PrimitiveType pt = null;
        TopLevelElementValidator validator =
                new TopLevelElementValidator(
                        packagedElements.get(0).getModel(), 3);

        // TLE 1
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "tle1Type");

        validator.isValid("tle1",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);

        // TLE 2
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "tle1attrsType");

        validator.isValid("tle1attrs",
                "myID1",
                "default1",
                null,
                Boolean.TRUE,
                null,
                "#all",
                "#all",
                null);

        // TLE 3
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "tle2attrsType");

        validator.isValid("tle2attrs",
                "myID2",
                null,
                "fixed1",
                Boolean.TRUE,
                null,
                "#all",
                "#all",
                null);

    }

}
