/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.validator.test.rules;

import com.tibco.xpd.bom.test.BOMTestCase;
import com.tibco.xpd.bom.test.IBOMTestCase;
import com.tibco.xpd.bom.validator.test.TestValidationProviderExtensionRule;
import com.tibco.xpd.core.test.util.TestUtil;

/**
 * I try to create a BOM model with one class. The test should check wether the
 * ValidationRule of the validation provider's validate method was called.
 * 
 * @author rassisi
 * 
 */
public class ProviderExtensionRuleTest extends BOMTestCase implements
        IBOMTestCase {

    @Override
    public void setConceptName() {
    }

    @Override
    public void setProjectName() {
        super.setProjectName("com.tibco.xpd.bom.validator.test.ProviderExtensionRuleTest"); //$NON-NLS-1$
    }

    @Override
    protected boolean doClearBuild() {
        return true;
    }

    @Override
    protected void defineMarkerIssues() {
    }

    /**
     * I try to create a concept with two identical attributes to test wether
     * the validator produces Markers
     * 
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void testValidationProviderExtensionRule() throws Exception {
        doTest(1);
    }

    @Override
    protected void execute(int testNumber) throws Exception {
        switch (testNumber) {
        case 1:
            validationProviderExtensionRuleTest();
            break;
        }
    }

    /**
     * execute a test.
     * 
     * @throws Exception
     */
    @SuppressWarnings("nls")
    protected void validationProviderExtensionRuleTest() throws Exception {
        checkMarkers = false;
        TestValidationProviderExtensionRule.wasCalled = false;
        addClass("");
        TestUtil.waitForJobs();
        TestUtil.waitForValidatior();
        if (!TestValidationProviderExtensionRule.wasCalled) {
            fail("The rules provided by the validation provider extension was not called.");
        }
    }

}
