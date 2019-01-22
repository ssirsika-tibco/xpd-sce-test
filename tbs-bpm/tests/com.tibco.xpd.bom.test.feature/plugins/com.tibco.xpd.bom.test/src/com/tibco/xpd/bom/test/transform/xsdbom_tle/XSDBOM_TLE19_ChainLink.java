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
public class XSDBOM_TLE19_ChainLink extends AbstractTLETest {

    public XSDBOM_TLE19_ChainLink() {
        super(new String[] { "XSDBOM_TLE19_ChainLink1.xsd",
                "XSDBOM_TLE19_ChainLink2.xsd", "XSDBOM_TLE19_ChainLink3.xsd",
                "XSDBOM_TLE19_ChainLink4.xsd" },
                new String[] { "XSDBOM_TLE19_ChainLink1.xsd",
                        "XSDBOM_TLE19_ChainLink2.xsd",
                        "XSDBOM_TLE19_ChainLink3.xsd",
                        "XSDBOM_TLE19_ChainLink4.xsd" }, null); //$NON-NLS-1$
    }

    @Override
    protected void checkBOMElements(Model model) throws Exception {
    }
}
