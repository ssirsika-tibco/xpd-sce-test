/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.analyst.qa.test;

import com.tibco.xpd.core.test.validations.AbstractBaseValidationTest;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;

/**
 * ErrorEventsSyncTest
 * <p>
 * ErrorEventsSyncTest - Test selected validations are correctly raised.
 * </p>
 * <p>
 * Generated by: createBaseTest.javajet
 * </p>
 *
 * @author
 * @since
 */
public class ErrorEventsSyncTest extends AbstractBaseValidationTest {

	/**
     * ErrorEventsSyncTest
     * 
     * @throws Exception
     */
    public void testErrorEventsSyncTest() throws Exception {
		doTestValidations();        
        return;
	}

	@Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos = new ValidationsTestProblemMarkerInfo[] { 
			
			    new ValidationsTestProblemMarkerInfo(
			    		"/Test123/Process Packages/Test Error Events Sync.xpdl", //$NON-NLS-1$ 
			    		"bpmn.errorEventOutOfSync", //$NON-NLS-1$ 
			    		"_fLd7sN6QEd6rs_sRtHWCDg", //$NON-NLS-1$ 
			    		"BPMN : Implementing error event is out of synch with the interface request activity type. (TestErrorEventsSyncProcess:ThrowErrorError2)", //$NON-NLS-1$ 
			    		""), //$NON-NLS-1$ 
			    		
			
			    new ValidationsTestProblemMarkerInfo(
			    		"/Test123/Process Packages/Test Error Events Sync.xpdl", //$NON-NLS-1$ 
			    		"bpmn.errorEventOutOfSync", //$NON-NLS-1$ 
			    		"_nqBcYN6QEd6rs_sRtHWCDg", //$NON-NLS-1$ 
			    		"BPMN : Implementing error event is out of synch with the interface request activity type. (TestErrorEventsSyncProcess:ThrowErrorError)", //$NON-NLS-1$ 
			    		""), //$NON-NLS-1$ 
			    		
			
			    new ValidationsTestProblemMarkerInfo(
			    		"/Test123/Process Packages/Test Error Events Sync.xpdl", //$NON-NLS-1$ 
			    		"bpmn.errorEventOutOfSync", //$NON-NLS-1$ 
			    		"_fLKZsN6QEd6rs_sRtHWCDg", //$NON-NLS-1$ 
			    		"BPMN : Implementing error event is out of synch with the interface request activity type. (TestErrorEventsSyncProcess:ThrowErrorError)", //$NON-NLS-1$ 
			    		"Fix implementing error event."), //$NON-NLS-1$ 
			    		
			                
        };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "ErrorEventsSyncTest"; //$NON-NLS-1$
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.analyst.qa.test"; //$NON-NLS-1$
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources = new TestResourceInfo[] {
            new TestResourceInfo("resources/ErrorEventsSyncTest", "Test123/Process Packages{processes}/Test Error Events Sync.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
        };
    
        return testResources;
    }

}
