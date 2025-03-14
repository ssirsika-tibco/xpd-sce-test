/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.sce.tests.validation;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.n2.test.core.validation.AbstractN2BaseValidationTest;

/**
 * CaseAttributesValidationTest
 * <p>
 * CaseAttributesValidationTest - Test selected validations are correctly
 * raised.
 * </p>
 * <p>
 * Generated by: createBaseTest.javajet
 * </p>
 *
 * Test case attribute validations and quick fixes.
 *
 * @author
 * @since
 */
public class AceCaseAttributesValidationTest
        extends AbstractN2BaseValidationTest {

    public AceCaseAttributesValidationTest() {
        super(true);
    }

    /**
     * CaseAttributesValidationTest
     * 
     * @throws Exception
     */
    public void testCaseAttributesValidationTest() throws Exception {
        doTestValidations();
        return;
    }

    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos =
                new ValidationsTestProblemMarkerInfo[] {

						/*
						 * Sid ACE-8369 Case Id's are now set to mandatory automatically on migration to 5.x - so
						 * problem marker will no longer exist...
						 */
						// new ValidationsTestProblemMarkerInfo(
						// "/CaseAttributesValidationTest/Business Objects/CaseAttributesValidationTest.bom",
						// //$NON-NLS-1$
						// "ace.bom.caseid.must.be.mandatory.nonarray", //$NON-NLS-1$
						// "_TuXhcHJxEem0P8x9fz4Hqw", //$NON-NLS-1$
						// "BPM : Case identifier attribute must be mandatory and non-array (multiplicity must be 1)
						// (autoCaseIdentifier1 (com.example.caseattributesvalidationtest))", //$NON-NLS-1$
						// "Make the attribute mandatory and non-array"), //$NON-NLS-1$

                        new ValidationsTestProblemMarkerInfo(
                                "/CaseAttributesValidationTest/Business Objects/CaseAttributesValidationTest.bom", //$NON-NLS-1$
                                "ace.bom.caseid.must.be.text", //$NON-NLS-1$
                                "_TuXhcHJxEem0P8x9fz4Hqw", //$NON-NLS-1$
                                "BPM  : Case identifier attributes must be Text type (autoCaseIdentifier1 (com.example.caseattributesvalidationtest))", //$NON-NLS-1$
                                "Change to Text type"), //$NON-NLS-1$

						/* Sid ACE-6504 Case-state is now automatically set to mandatory on migration from 4.x */
						// new ValidationsTestProblemMarkerInfo(
						// "/CaseAttributesValidationTest/Business Objects/CaseAttributesValidationTest.bom",
						// //$NON-NLS-1$
						// "ace.bom.casestate.must.be.mandatory.nonarray", //$NON-NLS-1$
						// "_UlbFQHJxEem0P8x9fz4Hqw", //$NON-NLS-1$
						// "BPM : Case state attribute must be mandatory and non-array (multiplicity must be 1)
						// (caseState1 (com.example.caseattributesvalidationtest))", //$NON-NLS-1$
						// "Make the attribute mandatory and non-array"), //$NON-NLS-1$

                        new ValidationsTestProblemMarkerInfo(
                                "/CaseAttributesValidationTest/Business Objects/CaseAttributesValidationTest.bom", //$NON-NLS-1$
                                "casestate.no.terminal.states.issue", //$NON-NLS-1$
                                "_UlbFQHJxEem0P8x9fz4Hqw", //$NON-NLS-1$
                                "BPM  : A Case State must have at least one Terminal State set (caseState1 (com.example.caseattributesvalidationtest))", //$NON-NLS-1$
                                ""), //$NON-NLS-1$

                        new ValidationsTestProblemMarkerInfo(
                                "/CaseAttributesValidationTest/Business Objects/CaseAttributesValidationTest.bom", //$NON-NLS-1$
                                "ace.bom.caseid.mindigits.max.15", //$NON-NLS-1$
                                "_TuXhcHJxEem0P8x9fz4Hqw", //$NON-NLS-1$
                                "BPM  : Auto case identifier minimum digits must not be greater than 15 (autoCaseIdentifier1 (com.example.caseattributesvalidationtest))", //$NON-NLS-1$
                                ""), //$NON-NLS-1$

                        new ValidationsTestProblemMarkerInfo(
                                "CaseAttributesValidationTest/Business Objects/SummaryAttrs.bom", //$NON-NLS-1$
                                "ace.bom.max.5.summary", //$NON-NLS-1$
                                "_LRLZUINMEeOcadlsg2IG1w", //$NON-NLS-1$
                                "BPM  : Case classes cannot have more than 5 summary attributes (LoginDetails (com.example.businessdataproject))", //$NON-NLS-1$
                                ""), //$NON-NLS-1$

                };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "CaseAttributesValidationTest"; //$NON-NLS-1$
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.sce.test"; //$NON-NLS-1$
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources = new TestResourceInfo[] {
                new TestResourceInfo("resources/CaseAttributesValidationTest", //$NON-NLS-1$
                        "CaseAttributesValidationTest/Business Objects{bom}/CaseAttributesValidationTest.bom"), //$NON-NLS-1$
                new TestResourceInfo("resources/CaseAttributesValidationTest", //$NON-NLS-1$
                        "CaseAttributesValidationTest/Business Objects{bom}/SummaryAttrs.bom") //$NON-NLS-1$
        };

        return testResources;
    }

}
