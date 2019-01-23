/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.validator.test.rules;

import com.tibco.xpd.bom.test.BOMTestCase;
import com.tibco.xpd.bom.test.IBOMTestCase;

/**
 * I try to create a BOM model with two attributes with identical names.
 * The validator should report an error after the build.
 * 
 * @author wzurek, ramin
 */
public class ClassNameRuleTest extends BOMTestCase implements IBOMTestCase{

	public void setConceptName(){
	}
	
	@SuppressWarnings("nls")
    public void setProjectName(){
		super.setProjectName("com.tibco.xpd.bom.validator.test.PackageNameDuplicateRuleTest");
	}

	protected boolean doClearBuild(){
		return true;
	}
	
	@SuppressWarnings("nls")
    @Override
	protected void defineMarkerIssues() {
        addExpectedMarkerIssues(1, new String[]{"elementNoName.issue#1", "java.name.javaconvention.issue#1"});
	}
	
	/**
	 * I try to create a concept with two identical attributes to test
	 * wether the validator produces Markers
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public void testClassNameRule() throws Exception {

		doTest(1);
				
	}

	protected void execute(int testNumber) throws Exception {
		switch(testNumber){
		case 1:
			classNameRuleTest();
			break;
		}
	}

	/**
	 * execute a test.
	 * @throws Exception 
	 */
	@SuppressWarnings("nls")
    protected void classNameRuleTest() throws Exception {
		addClass("");
	}

	
	
}
