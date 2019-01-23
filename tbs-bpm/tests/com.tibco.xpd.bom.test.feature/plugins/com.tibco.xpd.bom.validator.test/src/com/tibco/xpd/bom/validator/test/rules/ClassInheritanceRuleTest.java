/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.validator.test.rules;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import junit.framework.TestCase;

import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.tests.BOMTestUtils;
import com.tibco.xpd.bom.test.BOMTestCase;
import com.tibco.xpd.bom.test.IBOMTestCase;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;

/**
 * I try to create a BOM model with two attributes with identical names. The
 * validator should report an error after the build.
 * 
 * @author wzurek, ramin
 */
public class ClassInheritanceRuleTest extends BOMTestCase implements
		IBOMTestCase {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.xpd.bom.test.BOMTestCase#setConceptFileName()
	 */
	public void setConceptName() {
		// can be empty
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.xpd.bom.test.BOMTestCase#setProjectName()
	 */
	@SuppressWarnings("nls")
    public void setProjectName() {
		super
				.setProjectName("com.tibco.xpd.bom.validator.test.PropertyNameDuplicateRuleTest");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.xpd.bom.test.BOMTestCase#doClearBuild()
	 */
	protected boolean doClearBuild() {
		return true;
	}

	@SuppressWarnings("nls")
    @Override
	protected void defineMarkerIssues() {
		addExpectedMarkerIssues(1,
				new String[] { "inheritanceLoop.issue#3" });
	}

	/**
	 * I try to create a concept with two identical attributes to test wether
	 * the validator produces Markers
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void testPropertyNameDuplicateRule() throws Exception {
		doTest(1);
	}

	protected void execute(int testNumber) throws Exception {
		switch (testNumber) {
		case 1:
			classInheritanceRuleTest();
			break;
		}
	}

	/**
	 * execute a test.
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("nls")
    protected void classInheritanceRuleTest() throws Exception {
		// TestCase.fail("class inheritance rule test is not implemented yet");

		final Class classA = addClass("A");
		final Class classB = addClass("B");
		final Class classC = addClass("C");

		Generalization generalization = UMLFactory.eINSTANCE
				.createGeneralization();
		generalization.setGeneral(classA);

		Model mdl = getModel();
		TransactionalEditingDomain ed = TransactionUtil.getEditingDomain(mdl);

		RecordingCommand cmd = new RecordingCommand(ed) {
			@Override
			protected void doExecute() {
				UMLFactory f = UMLFactory.eINSTANCE;
				Generalization g1 = f.createGeneralization();
				Generalization g2 = f.createGeneralization();
				Generalization g3 = f.createGeneralization();

				g1.setGeneral(classA);
				g1.setSpecific(classB);

				g2.setGeneral(classB);
				g2.setSpecific(classC);

				g3.setGeneral(classC);
				g3.setSpecific(classA);
				TestUtil.waitForValidatior();
			}
		};
		ed.getCommandStack().execute(cmd);

		// test it
	}

}
