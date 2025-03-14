/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.validation.wm.test;

import com.tibco.xpd.validation.wm.test.AbstractJavaScriptValidatorTest;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;

/**
 * N2JavaScriptIdentifierValidatorTest
 * <p>
 * N2JavaScriptIdentifierValidatorTest - Test selected validations are correctly raised.
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
public class N2JavaScriptIdentifierValidatorTest extends AbstractJavaScriptValidatorTest {

	public N2JavaScriptIdentifierValidatorTest() {
		super(true);
	}

	/**
     * N2JavaScriptIdentifierValidatorTest
     * 
     * @throws Exception
     */
    public void testN2JavaScriptIdentifierValidatorTest() throws Exception {
		doTestValidations();        
        return;
	}

	@Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos = new ValidationsTestProblemMarkerInfo[] { 
			
			    new ValidationsTestProblemMarkerInfo(
			    		"/N2JavaScriptIdentifierValidatorTest/Process Packages/N2JavaScriptIdentifierValidatorTest.xpdl", //$NON-NLS-1$ 
			    		"bx.validateScriptTask", //$NON-NLS-1$ 
			    		"_9aJFwCwsEd-nEIBvGM9NAg", //$NON-NLS-1$ 
			    		"Process Manager 1.x : At Line:1 column:7, Variable String not defined or is not associated in the task interface. (IdentifierExpressions:INValidIdentifierExpr)", //$NON-NLS-1$ 
			    		""), //$NON-NLS-1$ 
			    		
			
			    new ValidationsTestProblemMarkerInfo(
			    		"/N2JavaScriptIdentifierValidatorTest/Process Packages/N2JavaScriptIdentifierValidatorTest.xpdl", //$NON-NLS-1$ 
			    		"bx.validateScriptTask", //$NON-NLS-1$ 
			    		"_9aJFwCwsEd-nEIBvGM9NAg", //$NON-NLS-1$ 
			    		"Process Manager 1.x : At Line:11 column:22, Variable RoundingMode not defined or is not associated in the task interface. (IdentifierExpressions:INValidIdentifierExpr)", //$NON-NLS-1$ 
			    		""), //$NON-NLS-1$ 
			    		
			
			    new ValidationsTestProblemMarkerInfo(
			    		"/N2JavaScriptIdentifierValidatorTest/Process Packages/N2JavaScriptIdentifierValidatorTest.xpdl", //$NON-NLS-1$ 
			    		"bx.validateScriptTask", //$NON-NLS-1$ 
			    		"_9aJFwCwsEd-nEIBvGM9NAg", //$NON-NLS-1$ 
			    		"Process Manager 1.x : At Line:13 column:22, Variable Boolean not defined or is not associated in the task interface. (IdentifierExpressions:INValidIdentifierExpr)", //$NON-NLS-1$ 
			    		""), //$NON-NLS-1$ 
			    		
			
			    new ValidationsTestProblemMarkerInfo(
			    		"/N2JavaScriptIdentifierValidatorTest/Process Packages/N2JavaScriptIdentifierValidatorTest.xpdl", //$NON-NLS-1$ 
			    		"bx.validateScriptTask", //$NON-NLS-1$ 
			    		"_9aJFwCwsEd-nEIBvGM9NAg", //$NON-NLS-1$ 
			    		"Process Manager 1.x : At Line:17 column:9, Variable Number not defined or is not associated in the task interface. (IdentifierExpressions:INValidIdentifierExpr)", //$NON-NLS-1$ 
			    		""), //$NON-NLS-1$ 
			    		
			
			    new ValidationsTestProblemMarkerInfo(
			    		"/N2JavaScriptIdentifierValidatorTest/Process Packages/N2JavaScriptIdentifierValidatorTest.xpdl", //$NON-NLS-1$ 
			    		"bx.validateScriptTask", //$NON-NLS-1$ 
			    		"_9aJFwCwsEd-nEIBvGM9NAg", //$NON-NLS-1$ 
			    		"Process Manager 1.x : At Line:19 column:13, Variable BigDecimal not defined or is not associated in the task interface. (IdentifierExpressions:INValidIdentifierExpr)", //$NON-NLS-1$ 
			    		""), //$NON-NLS-1$ 
			    		
			
			    new ValidationsTestProblemMarkerInfo(
			    		"/N2JavaScriptIdentifierValidatorTest/Process Packages/N2JavaScriptIdentifierValidatorTest.xpdl", //$NON-NLS-1$ 
			    		"bx.validateScriptTask", //$NON-NLS-1$ 
			    		"_9aJFwCwsEd-nEIBvGM9NAg", //$NON-NLS-1$ 
			    		"Process Manager 1.x : At Line:21 column:13, Variable BigInteger not defined or is not associated in the task interface. (IdentifierExpressions:INValidIdentifierExpr)", //$NON-NLS-1$ 
			    		""), //$NON-NLS-1$ 
			    		
			
			    new ValidationsTestProblemMarkerInfo(
			    		"/N2JavaScriptIdentifierValidatorTest/Process Packages/N2JavaScriptIdentifierValidatorTest.xpdl", //$NON-NLS-1$ 
			    		"bx.validateScriptTask", //$NON-NLS-1$ 
			    		"_9aJFwCwsEd-nEIBvGM9NAg", //$NON-NLS-1$ 
			    		"Process Manager 1.x : At Line:3 column:6, Variable Array not defined or is not associated in the task interface. (IdentifierExpressions:INValidIdentifierExpr)", //$NON-NLS-1$ 
			    		""), //$NON-NLS-1$ 
			    		
			
			    new ValidationsTestProblemMarkerInfo(
			    		"/N2JavaScriptIdentifierValidatorTest/Process Packages/N2JavaScriptIdentifierValidatorTest.xpdl", //$NON-NLS-1$ 
			    		"bx.validateScriptTask", //$NON-NLS-1$ 
			    		"_9aJFwCwsEd-nEIBvGM9NAg", //$NON-NLS-1$ 
			    		"Process Manager 1.x : At Line:5 column:13, Variable DateTimeUtil not defined or is not associated in the task interface. (IdentifierExpressions:INValidIdentifierExpr)", //$NON-NLS-1$ 
			    		""), //$NON-NLS-1$ 
			    		
			
			    new ValidationsTestProblemMarkerInfo(
			    		"/N2JavaScriptIdentifierValidatorTest/Process Packages/N2JavaScriptIdentifierValidatorTest.xpdl", //$NON-NLS-1$ 
			    		"bx.validateScriptTask", //$NON-NLS-1$ 
			    		"_9aJFwCwsEd-nEIBvGM9NAg", //$NON-NLS-1$ 
			    		"Process Manager 1.x : At Line:7 column:24, Variable Date not defined or is not associated in the task interface. (IdentifierExpressions:INValidIdentifierExpr)", //$NON-NLS-1$ 
			    		""), //$NON-NLS-1$ 
			    		
			
			    new ValidationsTestProblemMarkerInfo(
			    		"/N2JavaScriptIdentifierValidatorTest/Process Packages/N2JavaScriptIdentifierValidatorTest.xpdl", //$NON-NLS-1$ 
			    		"bx.validateScriptTask", //$NON-NLS-1$ 
			    		"_9aJFwCwsEd-nEIBvGM9NAg", //$NON-NLS-1$ 
			    		"Process Manager 1.x : At Line:9 column:31, Variable String not defined or is not associated in the task interface. (IdentifierExpressions:INValidIdentifierExpr)", //$NON-NLS-1$ 
			    		""), //$NON-NLS-1$ 
			    		
			                
        };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "N2JavaScriptIdentifierValidatorTest"; //$NON-NLS-1$
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.validation.wm.test"; //$NON-NLS-1$
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources = new TestResourceInfo[] {
            new TestResourceInfo("resources/N2JavaScriptIdentifierValidator", "N2JavaScriptIdentifierValidatorTest/Process Packages{processes}/N2JavaScriptIdentifierValidatorTest.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
            new TestResourceInfo("resources/N2JavaScriptIdentifierValidator", "N2JavaScriptIdentifierValidatorTest/Business Objects{bom}/IndirectlyReferencedModel.bom"), //$NON-NLS-1$ //$NON-NLS-2$
            new TestResourceInfo("resources/N2JavaScriptIdentifierValidator", "N2JavaScriptIdentifierValidatorTest/Business Objects{bom}/Enumerations.bom"), //$NON-NLS-1$ //$NON-NLS-2$
        };
    
        return testResources;
    }

}
