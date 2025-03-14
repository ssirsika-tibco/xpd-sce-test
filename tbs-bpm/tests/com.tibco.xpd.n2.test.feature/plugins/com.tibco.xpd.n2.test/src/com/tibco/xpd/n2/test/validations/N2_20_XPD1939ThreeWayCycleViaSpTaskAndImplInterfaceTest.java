/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.n2.test.validations;

import com.tibco.xpd.n2.test.core.validation.AbstractN2BaseValidationTest;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;

/**
 * XPD1939ThreeWayCycleViaSpTaskAndImplInterfaceTest
 * <p>
 * XPD1939ThreeWayCycleViaSpTaskAndImplInterfaceTest - Test selected validations are correctly raised.
 * </p>
 * <p>
 * Generated by: createBaseTest.javajet
 * </p>
 *
 * AMX BPM can't handle cycles in XPD package references via sub-proces task or process interface implementations. 
 * Each XPDL is a component in the composite/DAA and currently cyclic dependency in these is not allowed.
 *
 * @author
 * @since
 */
public class N2_20_XPD1939ThreeWayCycleViaSpTaskAndImplInterfaceTest extends AbstractN2BaseValidationTest {

	public N2_20_XPD1939ThreeWayCycleViaSpTaskAndImplInterfaceTest() {
		super(true);
	}

	/**
     * XPD1939ThreeWayCycleViaSpTaskAndImplInterfaceTest
     * 
     * @throws Exception
     */
    public void testXPD1939ThreeWayCycleViaSpTaskAndImplInterfaceTest() throws Exception {
		doTestValidations();        
        return;
	}

	@Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos = new ValidationsTestProblemMarkerInfo[] { 
			
			    new ValidationsTestProblemMarkerInfo(
			    		"/XPD-1939-A/Process Packages/PKG A.xpdl", //$NON-NLS-1$ 
			    		"bx.subProcessTaskCausedXpdlRefCycle", //$NON-NLS-1$ 
			    		"_0-FagHyJEeCV6N3VeCbrcQ", //$NON-NLS-1$ 
			    		"Process Manager 1.x : Sub-process invocation caused a cyclic package reference (PKG A.xpdl->PKG B.xpdl->PKG C.xpdl->PKG A.xpdl). (PROCESSA1:PROCESSB1)", //$NON-NLS-1$ 
			    		""), //$NON-NLS-1$ 
			    		
			
			    new ValidationsTestProblemMarkerInfo(
			    		"/XPD-1939-A/Process Packages/PKG A.xpdl", //$NON-NLS-1$ 
			    		"bx.subProcessTaskCausedXpdlRefCycle", //$NON-NLS-1$ 
			    		"_zh-zcnyKEeCV6N3VeCbrcQ", //$NON-NLS-1$ 
			    		"Process Manager 1.x : Sub-process invocation caused a cyclic package reference (PKG A.xpdl->PKG B.xpdl->PKG C.xpdl->PKG A.xpdl). (PROCESSA2:PROCESSB2)", //$NON-NLS-1$ 
			    		""), //$NON-NLS-1$ 
			    		
			
			    new ValidationsTestProblemMarkerInfo(
			    		"/XPD-1939-A/Process Packages/PKG B.xpdl", //$NON-NLS-1$ 
			    		"bx.subProcessTaskCausedXpdlRefCycle", //$NON-NLS-1$ 
			    		"_ZoiW8XyPEeCYT8zoULwQ6g", //$NON-NLS-1$ 
			    		"Process Manager 1.x : Sub-process invocation caused a cyclic package reference (PKG B.xpdl->PKG C.xpdl->PKG A.xpdl->PKG B.xpdl). (PROCESSB1:PROCESSC1)", //$NON-NLS-1$ 
			    		""), //$NON-NLS-1$ 
			    		
			
			    new ValidationsTestProblemMarkerInfo(
			    		"/XPD-1939-A/Process Packages/PKG C.xpdl", //$NON-NLS-1$ 
			    		"bx.implementedInterfaceCausedXpdlRefCycle", //$NON-NLS-1$ 
			    		"_LldakHyLEeCV6N3VeCbrcQ", //$NON-NLS-1$ 
			    		"Process Manager 1.x : Implemented process interface caused a cyclic package reference (PKG C.xpdl->PKG A.xpdl->PKG B.xpdl->PKG C.xpdl). (PROCESSC1)", //$NON-NLS-1$ 
			    		""), //$NON-NLS-1$ 
			    		
			                
        };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "XPD1939ThreeWayCycleViaSpTaskAndImplInterfaceTest"; //$NON-NLS-1$
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.n2.test"; //$NON-NLS-1$
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources = new TestResourceInfo[] {
            new TestResourceInfo("resources/XPD1939ThreeWayCycleViaSpTaskAndImplInterfaceTest", "XPD-1939-A/Process Packages{processes}/PKG A.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
            new TestResourceInfo("resources/XPD1939ThreeWayCycleViaSpTaskAndImplInterfaceTest", "XPD-1939-A/Process Packages{processes}/PKG B.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
            new TestResourceInfo("resources/XPD1939ThreeWayCycleViaSpTaskAndImplInterfaceTest", "XPD-1939-A/Process Packages{processes}/PKG C.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
        };
    
        return testResources;
    }

}
