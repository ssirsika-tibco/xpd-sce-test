/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.validation.wm.test;

import com.tibco.xpd.validation.wm.test.AbstractJavaScriptValidatorTest;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;

/**
 * N2JavaScriptEqualityOperatorValidatorTest
 * <p>
 * N2JavaScriptEqualityOperatorValidatorTest - Test selected validations are correctly raised.
 * </p>
 * <p>
 * Generated by: createBaseTest.javajet
 * </p>
 *
 * 
 *
 * @author
 * @since
 */
public class N2JavaScriptEqualityOperatorValidatorTest extends AbstractJavaScriptValidatorTest {

	public N2JavaScriptEqualityOperatorValidatorTest() {
		super(true);
	}

	/**
     * N2JavaScriptEqualityOperatorValidatorTest
     * 
     * @throws Exception
     */
    public void testN2JavaScriptEqualityOperatorValidatorTest() throws Exception {
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
        return "N2JavaScriptEqualityOperatorValidatorTest"; //$NON-NLS-1$
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.validation.wm.test"; //$NON-NLS-1$
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources = new TestResourceInfo[] {
            new TestResourceInfo("resources/N2JavaScriptEqualityOperatorValidator", "N2JavaScriptEqualityOperatorValidation/Process Packages{processes}/N2JavaScriptEqualityOperatorValidation.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
            new TestResourceInfo("resources/N2JavaScriptEqualityOperatorValidator", "N2JavaScriptEqualityOperatorValidation/Business Objects{bom}/N2JavaScriptValidation.bom"), //$NON-NLS-1$ //$NON-NLS-2$
        };
    
        return testResources;
    }

}
