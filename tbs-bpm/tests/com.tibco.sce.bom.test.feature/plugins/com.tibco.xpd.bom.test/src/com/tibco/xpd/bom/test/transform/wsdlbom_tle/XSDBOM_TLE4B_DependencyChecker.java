/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.wsdlbom_tle;

import org.eclipse.uml2.uml.Model;

/**
 * Used to ensure that if we only have an import in a wsdl and this in turn
 * imports other XML Schemas that have top level elements - it is to ensure
 * these elements are persisted through to the BOM.
 * <p>
 * Tests 3.13 as described in 'BDS Support for Handling - Studio BOM Import'.
 * </p>
 * 
 * @author glewis
 * 
 */
public class XSDBOM_TLE4B_DependencyChecker extends
        AbstractTLETest {

    public XSDBOM_TLE4B_DependencyChecker() {
        super(
                new String[] {
                        "XSDBOM_TLE4B_DependencyChecker.wsdl"}, new String[]{"org.exampleb.NewWSDLFile.xsd", "org.exampleb.NewWSDLFile2.xsd", "org.exampleb.NewWSDLFile3.xsd" },"org.exampleb.NewWSDLFile.bom", true); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    }

    @Override
    protected void checkBOMElements(Model model) throws Exception {
    }
}
