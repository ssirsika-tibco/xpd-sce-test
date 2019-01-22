/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.n2.test.validations;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.n2.test.core.validation.AbstractN2BaseValidationTest;

/**
 * JUnit test to ensure that the validations on GSD project are raised
 * appropriately. This test checks for the following validations:
 * <p>
 * 1. Global Signal should have a Timeout value when 'Timeout Signal After'
 * option is selected.
 * <p>
 * 2. Signals should be unique in a Global Signal Definition file.
 * <p>
 * 3. Global Signal must have at least one Correlation Field.
 * <p>
 * 4. The qualified Global Signal name '%1$s' should be less than 255
 * characters.
 * <p>
 * 
 * @author sajain
 * @since Mar 31, 2015
 */
public class N2_47_GSDValidationTest extends AbstractN2BaseValidationTest {

    public N2_47_GSDValidationTest() {
        super(true);
    }

    /**
     * N247_GSDValidationTest
     * 
     * @throws Exception
     */
    public void testN247_GSDValidationTest() throws Exception {
        doTestValidations();
        return;
    }

    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos =
                new ValidationsTestProblemMarkerInfo[] {

                        new ValidationsTestProblemMarkerInfo(
                                "/TestGSDValidationProject/Global Signal Definitions/TestGSDValidationProject.gsd", //$NON-NLS-1$ 
                                "gsd.globalSignalsMustBeUnique", //$NON-NLS-1$ 
                                "//@globalSignals.0", //$NON-NLS-1$ 
                                "Global Signal  : Signals should be unique in a Global Signal Definition file.", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestGSDValidationProject/Global Signal Definitions/TestGSDValidationProject.gsd", //$NON-NLS-1$ 
                                "gsd.globalSignalMustHaveCorrelationField", //$NON-NLS-1$ 
                                "//@globalSignals.1", //$NON-NLS-1$ 
                                "Global Signal  : Global Signal must have at least one Correlation Field.", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestGSDValidationProject/Global Signal Definitions/TestGSDValidationProject.gsd", //$NON-NLS-1$ 
                                "gsd.globalSignalMustHaveTimeOutValueWhenTSA", //$NON-NLS-1$ 
                                "//@globalSignals.1", //$NON-NLS-1$ 
                                "Global Signal  : Global Signal should have a Timeout value when 'Timeout Signal After' option is selected.", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestGSDValidationProject/Global Signal Definitions/TestGSDValidationProject.gsd", //$NON-NLS-1$ 
                                "gsd.globalSignalsMustBeUnique", //$NON-NLS-1$ 
                                "//@globalSignals.2", //$NON-NLS-1$ 
                                "Global Signal  : Signals should be unique in a Global Signal Definition file.", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestGSDValidationProject/Global Signal Definitions/TestGSDValidationProject.gsd", //$NON-NLS-1$ 
                                "gsd.correlationDataMustBeNonArrayBasicType", //$NON-NLS-1$ 
                                "_7FcYMNdvEeSVdLYQ5RUqsQ", //$NON-NLS-1$ 
                                "Global Signal  : Correlation Data must be a non-array basic type.", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestGSDValidationProject/Global Signal Definitions/TestGSDValidationProject.gsd", //$NON-NLS-1$ 
                                "gsd.correlationDataMustBeNonArrayBasicType", //$NON-NLS-1$ 
                                "_KA7gENdwEeSVdLYQ5RUqsQ", //$NON-NLS-1$ 
                                "Global Signal  : Correlation Data must be a non-array basic type.", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "N247_GSDValidationTest"; //$NON-NLS-1$
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.n2.test"; //$NON-NLS-1$
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] { new TestResourceInfo(
                        "resources/N247GSDValidationTest", "TestGSDValidationProject/Global Signal Definitions{gsd}/TestGSDValidationProject.gsd"), //$NON-NLS-1$ //$NON-NLS-2$
                };

        return testResources;
    }

}
