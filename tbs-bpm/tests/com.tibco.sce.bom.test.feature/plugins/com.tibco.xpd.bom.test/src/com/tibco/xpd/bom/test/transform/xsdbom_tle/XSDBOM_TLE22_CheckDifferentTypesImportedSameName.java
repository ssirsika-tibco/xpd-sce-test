/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.xsdbom_tle;

import org.eclipse.uml2.uml.Model;

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
 * @author glewis
 */
public class XSDBOM_TLE22_CheckDifferentTypesImportedSameName extends
        AbstractTLETest {

    public XSDBOM_TLE22_CheckDifferentTypesImportedSameName() {
        super("XSDBOM_TLE22_CheckDifferentTypesImportedSameName.xsd");
    }

    protected void checkBOMElements(Model model) throws Exception {
    }
}
