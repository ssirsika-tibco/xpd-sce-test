/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.validator.test.rules;

import com.tibco.xpd.bom.test.BOMTestCase;
import com.tibco.xpd.bom.test.IBOMTestCase;
import com.tibco.xpd.core.test.util.TestUtil;

/**
 * I try to create a BOM model with two attributes with identical names. The
 * validator should report an error after the build.
 * 
 * @author wzurek, ramin
 */
public class PackageNameRuleTest extends BOMTestCase implements IBOMTestCase {

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.bom.test.BOMTestCase#setConceptFileName()
     */
    @Override
    public void setConceptName() {
        // can be empty
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.bom.test.BOMTestCase#setProjectName()
     */
    @Override
    public void setProjectName() {
        super.setProjectName("com.tibco.xpd.bom.validator.test.PropertyNameDuplicateRuleTest"); //$NON-NLS-1$
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.bom.test.BOMTestCase#doClearBuild()
     */
    @Override
    protected boolean doClearBuild() {
        return true;
    }

    @Override
    protected void defineMarkerIssues() {
        addExpectedMarkerIssues(1, new String[] { "elementNoName.issue#1", //$NON-NLS-1$
                "java.name.javaconvention.issue#1" }); //$NON-NLS-1$
    }

    /**
     * I try to create a concept with two identical attributes to test wether
     * the validator produces Markers
     * 
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void testPackageNameRule() throws Exception {

        doTest(1);

    }

    @Override
    protected void execute(int testNumber) throws Exception {
        switch (testNumber) {
        case 1:
            packageNameRuleTest();
            break;
        }
    }

    /**
     * execute a test.
     * 
     * @throws Exception
     */
    protected void packageNameRuleTest() throws Exception {
        addPackage(""); //$NON-NLS-1$

        TestUtil.waitForValidatior();
    }

}
