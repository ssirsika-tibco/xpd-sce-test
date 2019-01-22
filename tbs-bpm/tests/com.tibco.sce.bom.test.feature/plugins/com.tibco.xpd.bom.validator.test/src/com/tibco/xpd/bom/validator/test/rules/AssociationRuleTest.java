/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.validator.test.rules;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import junit.framework.TestCase;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.ui.IEditorPart;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLFactory;

import com.tibco.xpd.bom.test.BOMTestCase;
import com.tibco.xpd.bom.test.IBOMTestCase;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;

/**
 * I try to create a BOM model with two attributes with identical names.
 * The validator should report an error after the build.
 * 
 * @author wzurek, ramin
 */
public class AssociationRuleTest extends BOMTestCase implements IBOMTestCase{

	/* (non-Javadoc)
	 * @see com.tibco.xpd.bom.test.BOMTestCase#setConceptFileName()
	 */
	public void setConceptName(){
		// can be empty
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.xpd.bom.test.BOMTestCase#setProjectName()
	 */
	public void setProjectName(){
		super.setProjectName("com.tibco.xpd.bom.validator.test.PropertyNameDuplicateRuleTest"); //$NON-NLS-1$
	}

	/* (non-Javadoc)
	 * @see com.tibco.xpd.bom.test.BOMTestCase#doClearBuild()
	 */
	protected boolean doClearBuild(){
		return true;
	}

	@Override
	protected void defineMarkerIssues() {
//		addExpectedMarkerIssues(1, new String[]{"propertyNameDuplicate.issue"});
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

	protected void execute(int testNumber) {
		switch(testNumber){
		case 1:
			associationRuleTest();
			break;
		}
	}

	/**
	 * execute a test.
	 */
	protected void associationRuleTest() {
		TestCase.fail("association rule is not implemented yet"); //$NON-NLS-1$
	}

	
	
}
