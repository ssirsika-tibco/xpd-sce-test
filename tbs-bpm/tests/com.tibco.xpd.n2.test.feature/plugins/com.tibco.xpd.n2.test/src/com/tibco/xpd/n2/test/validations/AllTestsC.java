/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.n2.test.validations;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * 3rd suite of validation tests.
 * 
 * @author sajain
 * @since Mar 4, 2016
 */
public class AllTestsC {

    public static Test suite() {
        TestSuite suite =
                new TestSuite("Test for com.tibco.xpd.n2.test.validations"); //$NON-NLS-1$
        // $JUnit-BEGIN$
        suite.addTestSuite(N2_37_MultiInstaceAndTopLevelAdHocValidationTest.class);
        suite.addTestSuite(N2_38_AllocateToOfferSetMemberValidationTest.class);
        suite.addTestSuite(N2_39_CorrelateImmediatelyFeatureValidationTest.class);
        suite.addTestSuite(N2_40_CascadingSubProcessCancellationValidationTest.class);
        suite.addTestSuite(N2_41_EnumerationToTextMappingTest.class);
        suite.addTestSuite(N2_42_UntypedListToArrayMappingTest.class);
        suite.addTestSuite(N2_43_ServiceProcessValidationTest.class);
        suite.addTestSuite(N2_44_GlobalSignalReferencingTest.class);
        suite.addTestSuite(N2_45_GlobalSignalNameLessThan255CharsTest.class);
        suite.addTestSuite(N2_46_CaseDataSignalValidationTest.class);
        suite.addTestSuite(N2_47_GSDValidationTest.class);
        suite.addTestSuite(N2_48_CaseDataSignalScriptTest.class);
        suite.addTestSuite(N2_49_DataMapperValidationMarkerTest.class);
        suite.addTestSuite(N2_49bXPD_7735MappingParentAndChild1Test.class);
        suite.addTestSuite(N2_49c_XPD_7735_MappingParentAndChildTest2Test.class);
        suite.addTestSuite(N2_50_CaseSignalFollowedByGlobalDataTaskWithSameCaseRefFieldTest.class);
        suite.addTestSuite(N2_51_RestRefClearedProblemsTest.class);
        suite.addTestSuite(N2_52_ThrowErrorEventsComplexAssociatedParamsTest.class);
        suite.addTestSuite(N2_53_CSPUserDefinedScriptValidationsTest.class);
        suite.addTestSuite(N2_56_DataMapperMappingsValidationsTest.class);

        suite.addTestSuite(N2_59_CatchErrorDMAndJSMappingValidationsTest.class);
        // $JUnit-END$
        return suite;
    }
}
