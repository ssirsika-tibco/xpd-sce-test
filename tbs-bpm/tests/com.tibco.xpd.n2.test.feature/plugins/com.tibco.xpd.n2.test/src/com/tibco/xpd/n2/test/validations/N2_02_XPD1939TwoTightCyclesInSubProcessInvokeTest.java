/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.n2.test.validations;

import com.tibco.xpd.n2.test.core.validation.AbstractN2BaseValidationTest;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;

/**
 * XPD1939TwoTightCyclesInSubProcessInvokeTest
 * <p>
 * XPD1939TwoTightCyclesInSubProcessInvokeTest - Test selected validations are correctly raised.
 * </p>
 * <p>
 * Generated by: createBaseTest.javajet
 * </p>
 *
 * A1 refs B1 which refs A2.  A1 refs C1 which refs A2
 *
 * @author
 * @since
 */
public class N2_02_XPD1939TwoTightCyclesInSubProcessInvokeTest extends AbstractN2BaseValidationTest {

	public N2_02_XPD1939TwoTightCyclesInSubProcessInvokeTest() {
		super(true);
	}

	/**
     * XPD1939TwoTightCyclesInSubProcessInvokeTest
     * 
     * @throws Exception
     */
    public void testXPD1939TwoTightCyclesInSubProcessInvokeTest() throws Exception {
		doTestValidations();        
        return;
	}

	@Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos = new ValidationsTestProblemMarkerInfo[] { 
			
			    new ValidationsTestProblemMarkerInfo(
			    		"/XPD-1939-A/Process Packages/PKG A.xpdl", //$NON-NLS-1$ 
			    		"bx.subProcessTaskCausedXpdlRefCycle", //$NON-NLS-1$ 
			    		"_j_vbUHyPEeCYT8zoULwQ6g", //$NON-NLS-1$ 
			    		"Process Manager 1.x : Sub-process invocation caused a cyclic package reference (PKG A.xpdl->PKG C.xpdl->PKG A.xpdl). (PROCESSA1:PROCESSC1)", //$NON-NLS-1$ 
			    		""), //$NON-NLS-1$ 
			    		
			
			    new ValidationsTestProblemMarkerInfo(
			    		"/XPD-1939-A/Process Packages/PKG A.xpdl", //$NON-NLS-1$ 
			    		"bx.subProcessTaskCausedXpdlRefCycle", //$NON-NLS-1$ 
			    		"_0-FagHyJEeCV6N3VeCbrcQ", //$NON-NLS-1$ 
			    		"Process Manager 1.x : Sub-process invocation caused a cyclic package reference (PKG A.xpdl->PKG B.xpdl->PKG A.xpdl). (PROCESSA1:PROCESSB1)", //$NON-NLS-1$ 
			    		""), //$NON-NLS-1$ 
			    		
			
			    new ValidationsTestProblemMarkerInfo(
			    		"/XPD-1939-A/Process Packages/PKG B.xpdl", //$NON-NLS-1$ 
			    		"bx.subProcessTaskCausedXpdlRefCycle", //$NON-NLS-1$ 
			    		"_n3qAsHySEeCYT8zoULwQ6g", //$NON-NLS-1$ 
			    		"Process Manager 1.x : Sub-process invocation caused a cyclic package reference (PKG B.xpdl->PKG A.xpdl->PKG B.xpdl). (PROCESSB1:PROCESSA2)", //$NON-NLS-1$ 
			    		""), //$NON-NLS-1$ 
			    		
			
			    new ValidationsTestProblemMarkerInfo(
			    		"/XPD-1939-A/Process Packages/PKG C.xpdl", //$NON-NLS-1$ 
			    		"bx.subProcessTaskCausedXpdlRefCycle", //$NON-NLS-1$ 
			    		"_y_hF8HySEeCYT8zoULwQ6g", //$NON-NLS-1$ 
			    		"Process Manager 1.x : Sub-process invocation caused a cyclic package reference (PKG C.xpdl->PKG A.xpdl->PKG C.xpdl). (PROCESSC1:PROCESSA2)", //$NON-NLS-1$ 
			    		""), //$NON-NLS-1$ 
			    		
			                
        };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "XPD1939TwoTightCyclesInSubProcessInvokeTest"; //$NON-NLS-1$
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.n2.test"; //$NON-NLS-1$
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources = new TestResourceInfo[] {
            new TestResourceInfo("resources/XPD1939 Two Tight Cycles In SubProcess Invoke", "XPD-1939-A/Process Packages{processes}/PKG A.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
            new TestResourceInfo("resources/XPD1939 Two Tight Cycles In SubProcess Invoke", "XPD-1939-A/Process Packages{processes}/PKG B.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
            new TestResourceInfo("resources/XPD1939 Two Tight Cycles In SubProcess Invoke", "XPD-1939-A/Process Packages{processes}/PKG C.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
        };
    
        return testResources;
    }

}
