/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.n2.test.validations;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.n2.test.core.validation.AbstractN2BaseValidationTest;

/**
 * Junit to check if the issue added under XPD-7231 gets raised appropriately.
 * i.e. Checks if the Global Signal qualified name is not < 255 then raise a
 * validation.
 * 
 * 
 * @author kthombar
 * @since Mar 23, 2015
 */
public class N2_45_GlobalSignalNameLessThan255CharsTest extends
        AbstractN2BaseValidationTest {

    public N2_45_GlobalSignalNameLessThan255CharsTest() {
        super(true);
    }

    /**
     * N2_45_GlobalSignalNameLessThan255CharsTest
     * 
     * @throws Exception
     */
    public void testN2_45_GlobalSignalNameLessThan255CharsTest()
            throws Exception {
        doTestValidations();
        return;
    }

    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos =
                new ValidationsTestProblemMarkerInfo[] {

                        new ValidationsTestProblemMarkerInfo(
                                "/N2_45_GlobalSignalNameLessThan255Chars/Folder2/1AFolderWithBigNameSoThatWeCanTestThatTheGlobalSignalNameWithCharactersThan255WorksFine/AFolderWithBigNameSoThatWeCanTestThatTheGlobalSignalNameWithCharactersThan255WorksFine/AFolderWithBigNameSoThatWeCanTestThatTheGlobalSignalNameWithCharactersThan255WorksFine/N2_45_GlobalSignalNameLessThan255Chars.gsd", //$NON-NLS-1$ 
                                "gsd.globalSignalNameLengthMustBeLessThan255", //$NON-NLS-1$ 
                                "//@globalSignals.0", //$NON-NLS-1$ 
                                "Global Signal  : The qualified Global Signal name '1AFolderWithBigNameSoThatWeCanTestThatTheGlobalSignalNameWithCharactersThan255WorksFine/AFolderWithBigNameSoThatWeCanTestThatTheGlobalSignalNameWithCharactersThan255WorksFine/AFolderWithBigNameSoThatWeCanTestThatTheGlobalSignalNameWithCharactersThan255WorksFine/N2_45_GlobalSignalNameLessThan255Chars.gsd#GlobalSignal' must be less than 255 characters. (1AFolderWithBigNameSoThatWeCanTestThatTheGlobalSignalNameWithCharactersThan255WorksFine/AFolderWithBigNameSoThatWeCanTestThatTheGlobalSignalNameWithCharactersThan255WorksFine/AFolderWithBigNameSoThatWeCanTestThatTheGlobalSignalNameWithCharactersThan255WorksFine/N2_45_GlobalSignalNameLessThan255Chars.gsd#GlobalSignal)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/N2_45_GlobalSignalNameLessThan255Chars/Global Signal Definitions/AFolderWithBigNameSoThatWeCanTestThatTheGlobalSignalNameWithCharactersThan255WorksFine/AFolderWithBigNameSoThatWeCanTestThatTheGlobalSignalNameWithCharactersThan255WorksFine/AFolderWithBigNameSoThatWeCanTestThatTheGlobalSignalNameWithCharactersThan255WorksFine/N2_45_GlobalSignalNameLessThan255Chars.gsd", //$NON-NLS-1$ 
                                "gsd.globalSignalNameLengthMustBeLessThan255", //$NON-NLS-1$ 
                                "//@globalSignals.0", //$NON-NLS-1$ 
                                "Global Signal  : The qualified Global Signal name 'AFolderWithBigNameSoThatWeCanTestThatTheGlobalSignalNameWithCharactersThan255WorksFine/AFolderWithBigNameSoThatWeCanTestThatTheGlobalSignalNameWithCharactersThan255WorksFine/AFolderWithBigNameSoThatWeCanTestThatTheGlobalSignalNameWithCharactersThan255WorksFine/N2_45_GlobalSignalNameLessThan255Chars.gsd#GlobalSignal' must be less than 255 characters. (AFolderWithBigNameSoThatWeCanTestThatTheGlobalSignalNameWithCharactersThan255WorksFine/AFolderWithBigNameSoThatWeCanTestThatTheGlobalSignalNameWithCharactersThan255WorksFine/AFolderWithBigNameSoThatWeCanTestThatTheGlobalSignalNameWithCharactersThan255WorksFine/N2_45_GlobalSignalNameLessThan255Chars.gsd#GlobalSignal)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "N2_45_GlobalSignalNameLessThan255CharsTest"; //$NON-NLS-1$
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.n2.test"; //$NON-NLS-1$
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] {
                        new TestResourceInfo(
                                "resources/N245GlobalSignalNameLessThan255Chars", "N2_45_GlobalSignalNameLessThan255Chars/Folder2{gsd}/1AFolderWithBigNameSoThatWeCanTestThatTheGlobalSignalNameWithCharactersThan255WorksFine/AFolderWithBigNameSoThatWeCanTestThatTheGlobalSignalNameWithCharactersThan255WorksFine/AFolderWithBigNameSoThatWeCanTestThatTheGlobalSignalNameWithCharactersThan255WorksFine/N2_45_GlobalSignalNameLessThan255Chars.gsd"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/N245GlobalSignalNameLessThan255Chars", "N2_45_GlobalSignalNameLessThan255Chars/Global Signal Definitions{gsd}/AFolderWithBigNameSoThatWeCanTestThatTheGlobalSignalNameWithCharactersThan255WorksFine/AFolderWithBigNameSoThatWeCanTestThatTheGlobalSignalNameWithCharactersThan255WorksFine/AFolderWithBigNameSoThatWeCanTestThatTheGlobalSignalNameWithCharactersThan255WorksFine/N2_45_GlobalSignalNameLessThan255Chars.gsd"), //$NON-NLS-1$ //$NON-NLS-2$
                };

        return testResources;
    }

}
