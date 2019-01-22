/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.wsdlbom_tle;

import org.eclipse.uml2.uml.Model;

/**
 * This highlights a few edge cases - 1. A local element referring to a top
 * level element outside its own schema 2. The local element referring is itself
 * contained in a top level element that has a duplicate name as another top
 * level element in another schema that it is importing. 3. We have int1 and
 * str1 as top level elements in both schemas and also they themselves are being
 * referenced by local elements in each schemas. 4. Most importantly if you
 * notice in this case the wsdl definitions ns0 is referring to Schema.xsd
 * however the Schema.xsd has its own ns0 definition referring to
 * SimpleMappings.xsd and this was making the transformation bring back the
 * wrong schema for prefix.
 * <p>
 * Tests 3.13 as described in 'BDS Support for Handling - Studio BOM Import'.
 * </p>
 * 
 * @author glewis
 * 
 */
public class XSDBOM_TLE1_ClashingNamespaceDefinitions extends AbstractTLETest {

    public XSDBOM_TLE1_ClashingNamespaceDefinitions() {
        super(
                new String[] { "XSDBOM_TLE1_ClashingNamespaceDefinitions.wsdl" }, new String[] { "XSDBOM_TLE1_ClashingNamespaceDefinitions2.xsd", "XSDBOM_TLE1_ClashingNamespaceDefinitions.xsd" }, "com.tibco.schemas.CreateExamples.Schema.bom", true); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    }

    @Override
    protected void checkBOMElements(Model model) throws Exception {
    }
}
