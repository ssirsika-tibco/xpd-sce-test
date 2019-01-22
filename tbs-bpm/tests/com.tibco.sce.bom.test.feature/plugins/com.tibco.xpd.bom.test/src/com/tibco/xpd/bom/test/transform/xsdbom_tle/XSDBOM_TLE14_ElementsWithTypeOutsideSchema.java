/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.xsdbom_tle;

import org.eclipse.uml2.uml.Model;

/**
 * SID: "XSDBOM_TLE14_ElementsWithTypeOutsideSchema has been disabled on purpose
 * because it involves a Cyclic-Dependency (via TLE's) and we now strictly
 * validate against this in BOMs, so there is no point in this
 * test.XSDBOM_TLE14_ElementsWithTypeOutsideSchema Test top level elements of
 * types in other schemas - circular dependencies.
 * <p>
 * Tests 3.13 as described in 'BDS Support for Handling - Studio BOM Import'.
 * </p>
 * 
 * @author glewis
 * 
 */
public class XSDBOM_TLE14_ElementsWithTypeOutsideSchema extends AbstractTLETest {

    public XSDBOM_TLE14_ElementsWithTypeOutsideSchema() {
        super(
                new String[] {
                        "XSDBOM_TLE14_ElementsWithTypeOutsideSchema.xsd", "XSDBOM_TLE14_ElementsWithTypeOutsideSchema2.xsd", "XSDBOM_TLE14_ElementsWithTypeOutsideSchema3.xsd" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

        throw new RuntimeException(
                "XSDBOM_TLE14_ElementsWithTypeOutsideSchema has been disabled on purpose because it involves a Cyclic-Dependency (via TLE's) and we now strictly validate against this in BOMs, so there is no point in this test.XSDBOM_TLE14_ElementsWithTypeOutsideSchema");

    }

    @Override
    protected void checkBOMElements(Model model) throws Exception {
    }
}
