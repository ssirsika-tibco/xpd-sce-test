/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.xsdbom_tle;

import org.eclipse.uml2.uml.Model;

/**
 * Tests to ensure that all properties are set inside all complex types upon
 * export of a previously imported BOM with identical complex type names across
 * schemas.
 * <p>
 * Tests 3.11 as described in 'BDS Support for Handling - Studio BOM Import'.
 * </p>
 * 
 * @author glewis
 * 
 */
public class XSDBOM_TLE20_SameNameComplexTypesWithAttributes extends
        AbstractTLETest {

    public XSDBOM_TLE20_SameNameComplexTypesWithAttributes() {
        super(
                new String[] {
                        "XSDBOM_TLE20_SameNameComplexTypesWithAttributes.xsd",
                        "XSDBOM_TLE20_SameNameComplexTypesWithAttributes2.xsd",
                        "XSDBOM_TLE20_SameNameComplexTypesWithAttributes3.xsd" },
                new String[] {
                        "XSDBOM_TLE20_SameNameComplexTypesWithAttributes.xsd",
                        "XSDBOM_TLE20_SameNameComplexTypesWithAttributes2.xsd",
                        "XSDBOM_TLE20_SameNameComplexTypesWithAttributes3.xsd" }, null); //$NON-NLS-1$
    }

    @Override
    protected void checkBOMElements(Model model) throws Exception {
    }
}
