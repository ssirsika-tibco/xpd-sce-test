/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.validator.test.rules;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.tests.BOMTestUtils;
import com.tibco.xpd.bom.test.BOMTestCase;
import com.tibco.xpd.bom.test.IBOMTestCase;

/**
 * I try to create a BOM model with two attributes with identical names.
 * The validator should report an error after the build.
 * 
 * @author wzurek, ramin
 */
public class PropertyNameDuplicateRuleTest extends BOMTestCase implements IBOMTestCase{

	/* (non-Javadoc)
	 * @see com.tibco.xpd.bom.test.BOMTestCase#setConceptFileName()
	 */
	public void setConceptName(){
		// can be empty
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.xpd.bom.test.BOMTestCase#setProjectName()
	 */
	@SuppressWarnings("nls")
    public void setProjectName(){
		super.setProjectName("com.tibco.xpd.bom.validator.test.PropertyNameDuplicateRuleTest");
	}

	/* (non-Javadoc)
	 * @see com.tibco.xpd.bom.test.BOMTestCase#doClearBuild()
	 */
	protected boolean doClearBuild(){
		return true;
	}

	@SuppressWarnings("nls")
    @Override
	protected void defineMarkerIssues() {
		addExpectedMarkerIssues(1, new String[]{"propertyNameDuplicate.issue#2"});
	}
	
	/**
	 * I try to create a concept with two identical attributes to test
	 * wether the validator produces Markers
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public void testPropertyNameDuplicateRule() throws Exception {
		
		doTest(1);
		
	}

	/**
	 * do all sub tests, every sub test creates a ne diagram and do all prparation
	 * for a new test, after the execution of the test all field will be cleared, so that
	 * a new sub test can be carried out.
	 * 
	 * @param testNumber
	 * @throws Exception 
	 */
	protected void execute(int testNumber) throws Exception {
		switch(testNumber){
		case 1:
			propertyNameDuplicateRuleTest();
			break;
		}
	}
	
	/**
	 * Create a concept with an attribute in given working copy.
	 * 
	 * @param wc
	 * @throws Exception 
	 */
	@SuppressWarnings("nls")
    private void propertyNameDuplicateRuleTest() throws Exception{
		
		Class clazz = addClass("Concept1");
		_testIsWorkingCopyDirty("Working Copy not dirty after executing a command.");

		clazz.getOwnedAttributes();
		Command cmd;
		// create the first attribute
		Property prop_1 = UMLFactory.eINSTANCE.createProperty();
		prop_1.setName("AAA");
		cmd = AddCommand.create(getWorkingCopy().getEditingDomain(), clazz,
				UMLPackage.eINSTANCE.getStructuredClassifier_OwnedAttribute(),
				prop_1);
		assertTrue("Cannot add property", cmd.canExecute());
		executeCommand(cmd);

		// create the second attribute with the same name
		Property prop_2 = UMLFactory.eINSTANCE.createProperty();
		prop_2.setName("AAA");
		cmd = AddCommand.create(getWorkingCopy().getEditingDomain(), clazz,
				UMLPackage.eINSTANCE.getStructuredClassifier_OwnedAttribute(),
				prop_2);
		assertTrue("Cannot add property", cmd.canExecute());
		executeCommand(cmd);
				
	}

	
	
}
