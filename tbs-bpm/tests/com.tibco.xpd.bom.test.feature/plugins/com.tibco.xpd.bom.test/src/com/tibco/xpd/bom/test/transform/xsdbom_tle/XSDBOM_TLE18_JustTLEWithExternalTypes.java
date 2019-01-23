/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.xsdbom_tle;

import org.eclipse.uml2.uml.Model;

/**
 * Tests to ensure that target namespaces with invalid chars are stripped in
 * both model and top level element stereotype values so upon export the top
 * level elements can be properly put in their right schema.
 * <p>
 * Tests 3.11 as described in 'BDS Support for Handling - Studio BOM Import'.
 * </p>
 * 
 * @author glewis
 * 
 */
public class XSDBOM_TLE18_JustTLEWithExternalTypes extends AbstractTLETest {

    public XSDBOM_TLE18_JustTLEWithExternalTypes() {
        super(
                new String[] { "XSDBOM_TLE18_JustTLEWithExternalTypes.xsd",
                        "XSDBOM_TLE18_JustTLEWithExternalTypes2.xsd" },
                new String[] {
                        "XSDBOM_TLE18_JustTLEWithExternalTypes2.xsd", "XSDBOM_TLE18_JustTLEWithExternalTypes.xsd" }, null); //$NON-NLS-1$
    }

    @Override
    protected void checkBOMElements(Model model) throws Exception {
    }
}
