/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.xsdbom_tle;

import org.eclipse.uml2.uml.Model;

/**
 * Test top level elements of types in other schemas - non circular
 * dependencies.
 * <p>
 * Tests 3.13 as described in 'BDS Support for Handling - Studio BOM Import'.
 * </p>
 * 
 * @author glewis
 * 
 */
public class XSDBOM_TLE15_ElementsWithTypeOutsideSchemaNonCircular extends
        AbstractTLETest {

    public XSDBOM_TLE15_ElementsWithTypeOutsideSchemaNonCircular() {
        super(
                new String[] {
                        "XSDBOM_TLE15_ElementsWithTypeOutsideSchemaNonCircular.xsd", "XSDBOM_TLE15_ElementsWithTypeOutsideSchemaNonCircular2.xsd", "XSDBOM_TLE15_ElementsWithTypeOutsideSchemaNonCircular3.xsd" }, new String[] { "XSDBOM_TLE15_ElementsWithTypeOutsideSchemaNonCircular2.xsd", "XSDBOM_TLE15_ElementsWithTypeOutsideSchemaNonCircular3.xsd", "XSDBOM_TLE15_ElementsWithTypeOutsideSchemaNonCircular.xsd" }, "org.example.root.bom"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    @Override
    protected void checkBOMElements(Model model) throws Exception {
    }
}
