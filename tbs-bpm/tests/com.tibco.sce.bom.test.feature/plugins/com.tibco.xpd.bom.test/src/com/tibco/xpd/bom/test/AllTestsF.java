/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.test;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.tibco.xpd.bom.test.copypaste.CopyPasteAllTests;
import com.tibco.xpd.bom.test.transform.exports_bom_xsd_rt.BOMXSD01_ReferencedPackageNameChangedTest_rt;
import com.tibco.xpd.bom.test.transform.exports_bom_xsd_rt.BOMXSD02_UserDefinedBOMReferencingElementFromImportedSchemaTest_rt;
import com.tibco.xpd.bom.test.transform.exports_bom_xsd_rt.BOMXSD03_UserDefBOMCompTypeAndCompositionAssocSameNameTest;
import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM49_BOMGenTestForSourcesInDifferentFoldersTest;

/**
 * @author wzurek
 * 
 */
public class AllTestsF {

    public static Test suite() {
        TestSuite suite =
                new TestSuite("Test for com.tibco.xpd.bom.test - set F"); //$NON-NLS-1$

        addBOMtoXSDRoundTripTests(suite);

        // Kapil: Adding copy paste tests to the suite
        suite.addTest(CopyPasteAllTests.suite());

        suite.addTestSuite(WSDLBOM49_BOMGenTestForSourcesInDifferentFoldersTest.class);

        return suite;
    }

    /**
     * Test suite for Round trip test from Bom to XSD.
     * 
     * @param suite
     * @return
     */
    private static Test addBOMtoXSDRoundTripTests(TestSuite suite) {
        suite.addTestSuite(BOMXSD01_ReferencedPackageNameChangedTest_rt.class);
        suite.addTestSuite(BOMXSD02_UserDefinedBOMReferencingElementFromImportedSchemaTest_rt.class);
        suite.addTestSuite(BOMXSD03_UserDefBOMCompTypeAndCompositionAssocSameNameTest.class);
        return suite;

    }

}
