/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.tests;

import com.tibco.xpd.sce.tests.cdm.transform.AllBomCdmTransformTests;
import com.tibco.xpd.sce.tests.javascript.AceDataWrapperScriptObjectTest;
import com.tibco.xpd.sce.tests.javascript.AceProcessAndWMScriptTest;
import com.tibco.xpd.sce.tests.javascript.CaseDataLiteralParamTest;
import com.tibco.xpd.sce.tests.javascript.CaseDataScriptGenerationTest;
import com.tibco.xpd.sce.tests.javascript.CaseDataTaskInvalidTest;
import com.tibco.xpd.sce.tests.javascript.CaseDataTaskTest;
import com.tibco.xpd.sce.tests.javascript.GetAuthenticatedUserValidationTest;
import com.tibco.xpd.sce.tests.javascript.JavascriptArrayInvalidTest;
import com.tibco.xpd.sce.tests.javascript.JavascriptArrayValidTest;
import com.tibco.xpd.sce.tests.javascript.JavascriptDateValidTest;
import com.tibco.xpd.sce.tests.javascript.JavascriptGenericArrayInvalidTest;
import com.tibco.xpd.sce.tests.javascript.JavascriptNumberValidTest;
import com.tibco.xpd.sce.tests.javascript.JavascriptScriptUtilTest;
import com.tibco.xpd.sce.tests.javascript.JavascriptStringTest;
import com.tibco.xpd.sce.tests.javascript.JavascriptTempArrayTest;
import com.tibco.xpd.sce.tests.processscriptlibrary.ProcessScriptLibraryReferenceProviderTest;
import com.tibco.xpd.sce.tests.rasc.contributors.BrmModelRascContributorTest;
import com.tibco.xpd.sce.tests.rasc.contributors.CdmRascContributorTest;
import com.tibco.xpd.sce.tests.rasc.contributors.GlobalSignalRascContributorTest;
import com.tibco.xpd.sce.tests.rasc.contributors.OrgModelRascContributorTest;
import com.tibco.xpd.sce.tests.rasc.contributors.PERascContributorTest;
import com.tibco.xpd.sce.tests.rasc.contributors.PeSharedResourceContributorTest;
import com.tibco.xpd.sce.tests.rasc.contributors.PfeRascContributorTest;
import com.tibco.xpd.sce.tests.rasc.contributors.WlfModelRascContributorTest;
import com.tibco.xpd.sce.tests.rasc.contributors.WpModelRascContributorTest;
import com.tibco.xpd.sce.tests.validation.JavascriptDateTimeUtilTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * All SCE unit tests.
 *
 * @author pwatson
 * @since 21 Mar 2019
 */
@SuppressWarnings("nls")
public class AllUnitTests {
    public static Test suite() {
        TestSuite suite = new TestSuite("Test for com.tibco.xpd.sce.tests");

        /*
         * RASC contributors
         */
        suite.addTestSuite(OrgModelRascContributorTest.class);
        suite.addTestSuite(PERascContributorTest.class);
        suite.addTestSuite(GlobalSignalRascContributorTest.class);
        suite.addTestSuite(CdmRascContributorTest.class);
        suite.addTestSuite(BrmModelRascContributorTest.class);
        suite.addTestSuite(WpModelRascContributorTest.class);
        suite.addTestSuite(WlfModelRascContributorTest.class);
        suite.addTestSuite(PeSharedResourceContributorTest.class);
        suite.addTestSuite(PfeRascContributorTest.class);

        /*
         * BOM->CDM transformation
         */
        suite.addTest(AllBomCdmTransformTests.suite());


        /*
         * Javascript
         */
        suite.addTestSuite(JavascriptArrayValidTest.class);
        suite.addTestSuite(JavascriptArrayInvalidTest.class);
        suite.addTestSuite(JavascriptGenericArrayInvalidTest.class);
        suite.addTestSuite(JavascriptTempArrayTest.class);
        suite.addTestSuite(JavascriptDateValidTest.class);
        suite.addTestSuite(JavascriptNumberValidTest.class);
        suite.addTestSuite(JavascriptScriptUtilTest.class);
		suite.addTestSuite(JavascriptStringTest.class);
        suite.addTestSuite(CaseDataTaskTest.class);
        suite.addTestSuite(CaseDataTaskInvalidTest.class);
        suite.addTestSuite(CaseDataScriptGenerationTest.class);
        suite.addTestSuite(CaseDataLiteralParamTest.class);
        suite.addTestSuite(AceDataWrapperScriptObjectTest.class);
        suite.addTestSuite(AceProcessAndWMScriptTest.class);
        suite.addTestSuite(GetAuthenticatedUserValidationTest.class);
		suite.addTestSuite(JavascriptDateTimeUtilTest.class);

		// Process Script Library tests
		suite.addTestSuite(ProcessScriptLibraryReferenceProviderTest.class);
        return suite;
    }
}
