/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.validation.wm.test;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;

/**
 * 
 * 
 * @author rsawant
 * @since 18-Dec-2012
 */
public class N2JavaScriptInequalityOperatorValidatorTest extends
        AbstractJavaScriptValidatorTest {

    public N2JavaScriptInequalityOperatorValidatorTest() {
        super(true);
    }

    /**
     * N2JavaScriptEqualityOperatorValidatorTest
     * 
     * @throws Exception
     */
    public void testN2JavaScriptInequalityOperatorValidatorTest() throws Exception {
        doTestValidations();        
        return;
    }

    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos = new ValidationsTestProblemMarkerInfo[] { 
                            
        };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "N2JavaScriptInequalityOperatorValidatorTest"; //$NON-NLS-1$
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.validation.wm.test"; //$NON-NLS-1$
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources = new TestResourceInfo[] {
            new TestResourceInfo("resources/N2JavaScriptInequalityOperatorValidator", "N2JavaScriptInequalityOperatorValidation/Process Packages{processes}/N2JavaScriptInequalityOperatorValidation.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
            new TestResourceInfo("resources/N2JavaScriptInequalityOperatorValidator", "N2JavaScriptInequalityOperatorValidation/Business Objects{bom}/N2JavaScriptValidation.bom"), //$NON-NLS-1$ //$NON-NLS-2$
        };
    
        return testResources;
    }
}
