/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.tests.validation;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.n2.test.core.validation.AbstractN2BaseValidationTest;

/**
 * JUnit suite to test the validations applied on upper/lower limits of BOM
 * properties.
 *
 * @author sajain
 * @since Aug 1, 2019
 */
@SuppressWarnings("nls")
public class AcePropertyLimitValidationTest extends AbstractN2BaseValidationTest {

    public AcePropertyLimitValidationTest() {
        super(true);
    }

    /**
     * AcePropertyLimitValidationTest
     * 
     * @throws Exception
     */
    public void testAcePropertyLimitValidationTest() throws Exception {
        doTestValidations();
        return;
    }

    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos = new ValidationsTestProblemMarkerInfo[] {

                new ValidationsTestProblemMarkerInfo("/SimpleBizDataProj01/Business Objects/SimpleBizDataProj01.bom",
                        "ace.bom.number.lower.limit",
                        "_DMzn4LRBEemvFouo4BWFWA",
                        "BPM  : Lower limit must be in the range -999999999999999 to 999999999999999. (attribute1 (com.example.simplebizdataproj01))",
                        ""),
                new ValidationsTestProblemMarkerInfo("/SimpleBizDataProj01/Business Objects/SimpleBizDataProj01.bom",
                        "ace.bom.number.upper.limit", "_DMzn4LRBEemvFouo4BWFWA",
                        "BPM  : Upper limit must be in the range -999999999999999 to 999999999999999. (attribute1 (com.example.simplebizdataproj01))",
                        "") };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "AcePropertyLimitValidationTest";
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.sce.test";
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources = new TestResourceInfo[] {
                new TestResourceInfo("resources/AcePropertyLimitValidationTest",
                        "SimpleBizDataProj01/Business Objects{bom}/SimpleBizDataProj01.bom") };

        return testResources;
    }
}