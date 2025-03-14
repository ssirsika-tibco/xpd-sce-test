/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.sce.tests.validation;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.n2.test.core.validation.AbstractN2BaseValidationTest;

/**
 * PSLReturnTypeValidationTest
 * <p>
 * PSLReturnTypeValidationTest - Test selected validations are correctly raised.
 * </p>
 * <p>
 * Generated by: createBaseTest.javajet
 * </p>
 * 
 * Check the returnType Validations for PSL Functions.
 * The Test File PSLReturnTypeValidationTest.psl , contains the returnType Validations todo's raised
 * 
 * 1. When PSL Function has $RETURN parameter of Type Void and PSL function contains return statement.
 * 2. When there is incompitable type between return statement type and type of $RETURN parameter.
 * 		- The Rule also checks for multipilicity as well.
 * 		- The Rule is also checked for all intermidate return statements in the PSL Function.
 * 3. When PSL Function has $RETURN parameter of Type NON Void and PSL function does not end with return statement.
 * 4. When PSL Function has $RETURN parameter of Type NON Void and PSL function does end with only void return statement.
 *
 *
 * @author
 * @since
 */
public class PSLReturnTypeValidationTest extends AbstractN2BaseValidationTest {

	public PSLReturnTypeValidationTest() {
		super(true);
	}

	/**
     * PSLReturnTypeValidationTest
     * 
     * @throws Exception
     */
    public void testPSLReturnTypeValidationTest() throws Exception {
		doTestValidations();        
        return;
	}

	@Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos = new ValidationsTestProblemMarkerInfo[] { 
			
			    new ValidationsTestProblemMarkerInfo(
			    		"/PSLReturnTypeValidationTest/Process Script Library/PSLReturnTypeValidationTest.psl", //$NON-NLS-1$ 
			    		"bx.validateScriptTask", //$NON-NLS-1$ 
			    		"_lREScfM-Ee6xou8OpmkOyg", //$NON-NLS-1$ 
						"BPM  : At Line:10 column:1, Return type 'bpmScripts' with differing multiplicities is not compatible with the function return type 'Time[]'. (PSLReturnTypeValidationTest.psl:function1)", //$NON-NLS-1$
			    		""), //$NON-NLS-1$ 
			    		
			
			    new ValidationsTestProblemMarkerInfo(
			    		"/PSLReturnTypeValidationTest/Process Script Library/PSLReturnTypeValidationTest.psl", //$NON-NLS-1$ 
			    		"bx.validateScriptTask", //$NON-NLS-1$ 
			    		"_lREScfM-Ee6xou8OpmkOyg", //$NON-NLS-1$ 
						"BPM  : At Line:14 column:1, Return type 'String' with differing multiplicities is not compatible with the function return type 'Time[]'. (PSLReturnTypeValidationTest.psl:function1)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLReturnTypeValidationTest/Process Script Library/PSLReturnTypeValidationTest.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_lREScfM-Ee6xou8OpmkOyg", //$NON-NLS-1$
						"BPM  : At Line:15 column:1, Functions that return a value must end with a return statement. (PSLReturnTypeValidationTest.psl:function1)", //$NON-NLS-1$
			    		""), //$NON-NLS-1$ 
			    		
			
			    new ValidationsTestProblemMarkerInfo(
			    		"/PSLReturnTypeValidationTest/Process Script Library/PSLReturnTypeValidationTest.psl", //$NON-NLS-1$ 
			    		"bx.validateScriptTask", //$NON-NLS-1$ 
			    		"_lREScfM-Ee6xou8OpmkOyg", //$NON-NLS-1$ 
						"BPM  : At Line:5 column:1, Return statements without a value are not supported for functions that return a non-void type. (PSLReturnTypeValidationTest.psl:function1)", //$NON-NLS-1$
			    		""), //$NON-NLS-1$ 
			    		
			
			    new ValidationsTestProblemMarkerInfo(
			    		"/PSLReturnTypeValidationTest/Process Script Library/PSLReturnTypeValidationTest.psl", //$NON-NLS-1$ 
			    		"bx.validateScriptTask", //$NON-NLS-1$ 
			    		"_lREScfM-Ee6xou8OpmkOyg", //$NON-NLS-1$ 
						"BPM  : At Line:9 column:1, Return type 'Date' with differing multiplicities is not compatible with the function return type 'Time[]'. (PSLReturnTypeValidationTest.psl:function1)", //$NON-NLS-1$
			    		""), //$NON-NLS-1$ 
			    		
			
			    new ValidationsTestProblemMarkerInfo(
			    		"/PSLReturnTypeValidationTest/Process Script Library/PSLReturnTypeValidationTest.psl", //$NON-NLS-1$ 
			    		"bx.validateScriptTask", //$NON-NLS-1$ 
			    		"_rl34YPM-Ee6xou8OpmkOyg", //$NON-NLS-1$ 
						"BPM  : At Line:10 column:1, Return statements with value are not supported for functions with a Void return type. (PSLReturnTypeValidationTest.psl:function2)", //$NON-NLS-1$
			    		""), //$NON-NLS-1$ 
			    		
			
			    new ValidationsTestProblemMarkerInfo(
			    		"/PSLReturnTypeValidationTest/Process Script Library/PSLReturnTypeValidationTest.psl", //$NON-NLS-1$ 
			    		"bx.validateScriptTask", //$NON-NLS-1$ 
			    		"_rl34YPM-Ee6xou8OpmkOyg", //$NON-NLS-1$ 
						"BPM  : At Line:13 column:1, Return statements with value are not supported for functions with a Void return type. (PSLReturnTypeValidationTest.psl:function2)", //$NON-NLS-1$
			    		""), //$NON-NLS-1$ 
			    		
			
			    new ValidationsTestProblemMarkerInfo(
			    		"/PSLReturnTypeValidationTest/Process Script Library/PSLReturnTypeValidationTest.psl", //$NON-NLS-1$ 
			    		"bx.validateScriptTask", //$NON-NLS-1$ 
			    		"_sex2UPM-Ee6xou8OpmkOyg", //$NON-NLS-1$ 
						"BPM  : At Line:11 column:1, Return type 'bpmScripts' is not compatible with the function return type 'Date'. (PSLReturnTypeValidationTest.psl:function3)", //$NON-NLS-1$
			    		""), //$NON-NLS-1$ 
			    		
			
			    new ValidationsTestProblemMarkerInfo(
			    		"/PSLReturnTypeValidationTest/Process Script Library/PSLReturnTypeValidationTest.psl", //$NON-NLS-1$ 
			    		"bx.validateScriptTask", //$NON-NLS-1$ 
			    		"_sex2UPM-Ee6xou8OpmkOyg", //$NON-NLS-1$ 
						"BPM  : At Line:14 column:1, Return type 'String' is not compatible with the function return type 'Date'. (PSLReturnTypeValidationTest.psl:function3)", //$NON-NLS-1$
			    		""), //$NON-NLS-1$ 
			    		
			
			    new ValidationsTestProblemMarkerInfo(
			    		"/PSLReturnTypeValidationTest/Process Script Library/PSLReturnTypeValidationTest.psl", //$NON-NLS-1$ 
			    		"bx.validateScriptTask", //$NON-NLS-1$ 
			    		"_sex2UPM-Ee6xou8OpmkOyg", //$NON-NLS-1$ 
						"BPM  : At Line:5 column:1, Return statements without a value are not supported for functions that return a non-void type. (PSLReturnTypeValidationTest.psl:function3)", //$NON-NLS-1$
			    		""), //$NON-NLS-1$ 

				new ValidationsTestProblemMarkerInfo(
						"/PSLReturnTypeValidationTest/Process Script Library/PSLReturnTypeValidationTest.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_4qjfwBgWEe-4jNGr0EOkSg", //$NON-NLS-1$
						"BPM  : At Line:0 column:0, Functions that return a value must end with a return statement. (PSLReturnTypeValidationTest.psl:noContentScript)", //$NON-NLS-1$
						""), //$NON-NLS-1$
			    		
			                
        };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "PSLReturnTypeValidationTest"; //$NON-NLS-1$
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.sce.test"; //$NON-NLS-1$
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources = new TestResourceInfo[] {
				new TestResourceInfo("resources/PSLReturnTypeValidationTest", //$NON-NLS-1$
						"PSLReturnTypeValidationTest/Process Script Library{psl}/PSLReturnTypeValidationTest.psl"), //$NON-NLS-1$
        };
    
        return testResources;
    }

}
