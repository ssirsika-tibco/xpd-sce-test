/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */

package com.tibco.xpd.sce.tests.processscriptlibrary;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.junit.Test;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.processscriptlibrary.resource.util.ProcessScriptLibraryReferenceException;
import com.tibco.xpd.processscriptlibrary.resource.util.ProcessScriptLibraryReferenceProvider;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.ProjectImporter;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;

import junit.framework.TestCase;

/**
 * ACE-7546: JUnits for {@link ProcessScriptLibraryReferenceProvider}
 *
 * @author ssirsika
 * @since 26-Dec-2023
 */
public class ProcessScriptLibraryReferenceProviderTest extends TestCase
{

	private ProjectImporter							projectImporter;

	private ProcessScriptLibraryReferenceProvider	fixture;

	public void setUp()
	{
		fixture = new ProcessScriptLibraryReferenceProvider();
	}

	/**
	 * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#tearDown()
	 *
	 * @throws Exception
	 */
	public void tearDown() throws Exception
	{
		if (projectImporter != null)
		{
			projectImporter.performDelete();
		}
	}

	/**
	 * Test to find the direct references of PSL file.
	 * 
	 * Preconditions: Project named 'BaseProject' has a Tasks which has references to PSL method defined in 'PSL1'
	 * project.
	 * 
	 * Project Structure : BaseProject.xpdl -> PSL1
	 * 
	 * There are no transitive dependencies here.
	 * 
	 * Expected outcome: PSL reference library
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetScriptLibraryReferences() throws Exception
	{
		// Arrange
		projectImporter = importProjects(new String[]{"/resources/PSLProjects/PSL1/", //$NON-NLS-1$
				"/resources/PSLProjects/PSL2/", //$NON-NLS-1$
				"/resources/PSLProjects/PSL3/", //$NON-NLS-1$
				"resources/PSLProjects/BaseProject/"}, //$NON-NLS-1$
				new String[]{"PSL1", "PSL2", "PSL3", "BaseProject"}); //$NON-NLS-1$ //$NON-NLS-2$

		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("BaseProject"); //$NON-NLS-1$

		IFile xpdlFile = project.getFile("Process Packages/BaseProject.xpdl"); //$NON-NLS-1$
		WorkingCopy testWC = WorkingCopyUtil.getWorkingCopy(xpdlFile);
		Package pkg = (Package) testWC.getRootElement();

		Process baseProjectProcess = pkg.getProcesses().get(0);

		// Act
		Set<Process> result = fixture.getScriptLibraryReferences(baseProjectProcess);
		// Assert
		assertPSLReferences(result, "/PSL1/Process Script Library/Util1.psl", "/PSL3/Process Script Library/Util3.psl");

		/*
		 * -----------------------------------------------------------------------------------------------------
		 * Iteration 1 : Direct | Call again on same instance of fixture to get same result
		 * -----------------------------------------------------------------------------------------------------
		 */
		// Act
		result = fixture.getScriptLibraryReferences(baseProjectProcess);
		// Assert
		assertPSLReferences(result, "/PSL1/Process Script Library/Util1.psl", "/PSL3/Process Script Library/Util3.psl");

		/*
		 * -----------------------------------------------------------------------------------------------------
		 * Iteration 2 : Deep | Call again on same instance of fixture to get same result
		 * -----------------------------------------------------------------------------------------------------
		 */
		// Act
		result = fixture.getDeepScriptLibraryReferences(baseProjectProcess);
		// Assert
		assertPSLReferences(result, "/PSL1/Process Script Library/Util1.psl", "/PSL2/Process Script Library/Util2.psl",
				"/PSL3/Process Script Library/Util3.psl");

	}

	/**
	 * Test to find the deep references of PSL file including a cyclic library function references.
	 * 
	 * Preconditions: Project named 'BaseProject' has a Tasks which has references to PSL method defined in 'PSL1'
	 * project.
	 * 
	 * Project Structure : BaseProject.xpdl -> PSL1
	 * 
	 * There are no transitive dependencies here.
	 * 
	 * Expected outcome: PSL reference library
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetDeepScriptLibraryReferences() throws Exception
	{
		// Arrange
		projectImporter = importProjects(new String[]{"/resources/PSLProjects/PSL1/", //$NON-NLS-1$
				"/resources/PSLProjects/PSL2/", //$NON-NLS-1$
				"/resources/PSLProjects/PSL3/", //$NON-NLS-1$
				"resources/PSLProjects/BaseProject/"}, //$NON-NLS-1$
				new String[]{"PSL1", "PSL2", "PSL3", "BaseProject"}); //$NON-NLS-1$ //$NON-NLS-2$

		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("BaseProject"); //$NON-NLS-1$

		IFile xpdlFile = project.getFile("Process Packages/BaseProject.xpdl"); //$NON-NLS-1$
		WorkingCopy testWC = WorkingCopyUtil.getWorkingCopy(xpdlFile);
		Package pkg = (Package) testWC.getRootElement();

		Process baseProjectProcess = pkg.getProcesses().get(0);

		// Act
		Set<Process> result = fixture.getDeepScriptLibraryReferences(baseProjectProcess);
		// Assert
		assertPSLReferences(result, "/PSL1/Process Script Library/Util1.psl", "/PSL2/Process Script Library/Util2.psl",
				"/PSL3/Process Script Library/Util3.psl");

		/*
		 * -----------------------------------------------------------------------------------------------------
		 * Iteration 1 : Direct | Call again on same instance of fixture
		 * -----------------------------------------------------------------------------------------------------
		 */
		// Act
		result = fixture.getScriptLibraryReferences(baseProjectProcess);
		// Assert
		assertPSLReferences(result, "/PSL1/Process Script Library/Util1.psl", "/PSL3/Process Script Library/Util3.psl");

		/*
		 * -----------------------------------------------------------------------------------------------------
		 * Iteration 2 : Deep | Call again on same instance of fixture to get same result
		 * -----------------------------------------------------------------------------------------------------
		 */
		// Act
		result = fixture.getDeepScriptLibraryReferences(baseProjectProcess);
		// Assert
		assertPSLReferences(result, "/PSL1/Process Script Library/Util1.psl", "/PSL2/Process Script Library/Util2.psl",
				"/PSL3/Process Script Library/Util3.psl");

	}

	/**
	 * Test to find the deep references of PSL file.
	 * 
	 * Preconditions: Project named 'BaseProject' has a Tasks which has references to PSL method defined in 'PSL1'
	 * project.
	 * 
	 * <pre>
	 * Project Structure : 
	 * 		MultilevelBaseProject2.xpdl  
	 * 					|   --> User Task -- > bpmScripts.PSL2.Util2.checkStatus
	 * 					|										|
	 * 					|										| --> bpmScripts.PSL3.Util3.isTrue
	 * 					|										|					| --> bpmScripts.PSL1.Util1.multiply
	 *    				|										|					| --> bpmScripts.PSL2.Util2.divide _______________________________________
	 *    				|										|										| --> bpmScripts.PSL3.Util3.getNumber				  |
	 *    				|										|										| --> bpmScripts.PSL3.Util3.getNumberRnd			  | * Loop *	
	 *    				|										|														| --> bpmScripts.PSL2.Util2.divide	__|					
	 * 					|										| --> bpmScripts.PSL1.Util1.add
	 * 					| --> End event --- > bpmScripts.PSL3.Util3.getNumber
	 * 
	 * </pre>
	 * 
	 * Expected outcome: PSL reference library
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetDeepScriptLibraryReferences2() throws Exception
	{
		// Arrange
		projectImporter = importProjects(new String[]{"/resources/PSLProjects/PSL1/", //$NON-NLS-1$ ,
				"/resources/PSLProjects/PSL2/", //$NON-NLS-1$
				"/resources/PSLProjects/PSL3/", //$NON-NLS-1$
				"resources/PSLProjects/BaseProject/"}, //$NON-NLS-1$
				new String[]{"PSL1", "PSL2", "PSL3", "BaseProject"}); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("BaseProject"); //$NON-NLS-1$

		IFile xpdlFile = project.getFile("Process Packages/MultilevelBaseProject2.xpdl"); //$NON-NLS-1$
		WorkingCopy testWC = WorkingCopyUtil.getWorkingCopy(xpdlFile);
		Package pkg = (Package) testWC.getRootElement();

		Process baseProjectProcess = pkg.getProcesses().get(0);

		/*
		 * -----------------------------------------------------------------------------------------------------
		 * Iteration 1 : Deep | Call again on same instance of fixture
		 * -----------------------------------------------------------------------------------------------------
		 */
		// Act
		Set<Process> result = fixture.getDeepScriptLibraryReferences(baseProjectProcess);

		// Assert
		assertPSLReferences(result, "/PSL1/Process Script Library/Util1.psl", "/PSL2/Process Script Library/Util2.psl",
				"/PSL3/Process Script Library/Util3.psl");

		Optional<Process> out = result.stream().filter(p -> WorkingCopyUtil.getFile(p).getFullPath().toString()
				.equals("/PSL2/Process Script Library/Util2.psl")).findFirst();
		assertTrue(out.isPresent());
		Process pslProcess = out.get();

		/*
		 * -----------------------------------------------------------------------------------------------------
		 * Iteration 2 : Deep | Call again on same instance of fixture to result on dependent library
		 * -----------------------------------------------------------------------------------------------------
		 */
		// Act
		result = fixture.getDeepScriptLibraryReferences(pslProcess);

		// Assert
		assertPSLReferences(result, "/PSL1/Process Script Library/Util1.psl",
				"/PSL3/Process Script Library/Util3.psl");

		/*
		 * -----------------------------------------------------------------------------------------------------
		 * Iteration 3 : Direct | Call again on same instance of fixture.
		 * -----------------------------------------------------------------------------------------------------
		 */
		// Act
		result = fixture.getScriptLibraryReferences(baseProjectProcess);

		// Assert
		assertPSLReferences(result, "/PSL2/Process Script Library/Util2.psl",
				"/PSL3/Process Script Library/Util3.psl");
	}

	/**
	 * Test to check that correct exception is thrown when project name mentioned in the script to reference PSL is not
	 * present.
	 * 
	 * Preconditions: Do not import the project which has the PSL itself.
	 * 
	 * Expected outcome: ProcessScriptLibraryReferenceException
	 */
	@Test
	public void testGetScriptLibraryReferencesWithMissingProjectRef() throws Exception
	{
		// Do not import the PSL project.
		projectImporter = importProjects(new String[]{"resources/PSLProjects/BaseProject/"}, //$NON-NLS-1$
				new String[]{"BaseProject"}); //$NON-NLS-1$

		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("BaseProject"); //$NON-NLS-1$

		IFile xpdlFile = project.getFile("Process Packages/BaseProject.xpdl"); //$NON-NLS-1$
		WorkingCopy testWC = WorkingCopyUtil.getWorkingCopy(xpdlFile);
		Package pkg = (Package) testWC.getRootElement();

		Process baseProjectProcess = pkg.getProcesses().get(0);

		try
		{
			fixture.getScriptLibraryReferences(baseProjectProcess);
			fail("Should throw ProcessScriptLibraryReferenceException"); //$NON-NLS-1$
		}
		catch (ProcessScriptLibraryReferenceException e)
		{
			assertEquals("Project 'PSL1' referenced in 'bpmScripts.PSL1.Util1.add' does not exists", //$NON-NLS-1$
					e.getMessage());
		}
	}

	/**
	 * This is same as above JUnit (testGetScriptLibraryReferencesWithMissingProjectRef) except it call to check for
	 * deep references.
	 * 
	 * Code to get the direct and deep references are almost same , so just testing one scenario where deep ref method
	 * is called. No need to test for every combination.
	 * 
	 * Preconditions: Do not import the project which has the PSL itself.
	 * 
	 * Expected outcome: ProcessScriptLibraryReferenceException
	 */
	@Test
	public void testGetDeepScriptLibraryReferencesWithMissingProjectRef() throws Exception
	{
		// Do not import the PSL project.
		projectImporter = importProjects(new String[]{"resources/PSLProjects/BaseProject/"}, //$NON-NLS-1$
				new String[]{"BaseProject"}); //$NON-NLS-1$

		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("BaseProject"); //$NON-NLS-1$

		IFile xpdlFile = project.getFile("Process Packages/BaseProject.xpdl"); //$NON-NLS-1$
		WorkingCopy testWC = WorkingCopyUtil.getWorkingCopy(xpdlFile);
		Package pkg = (Package) testWC.getRootElement();

		Process baseProjectProcess = pkg.getProcesses().get(0);

		try
		{
			fixture.getDeepScriptLibraryReferences(baseProjectProcess);
			fail("Should throw ProcessScriptLibraryReferenceException"); //$NON-NLS-1$
		}
		catch (ProcessScriptLibraryReferenceException e)
		{
			assertEquals("Project 'PSL1' referenced in 'bpmScripts.PSL1.Util1.add' does not exists", //$NON-NLS-1$
					e.getMessage());
		}
	}

	/**
	 * Test to check that correct exception is thrown when PSL file name mentioned in the script is not valid.
	 * 
	 * Preconditions: 'BrokenRefBaseProject' contains reference to NOTEXISTS.psl which is not present in PSL project.
	 * 
	 * Expected outcome: ProcessScriptLibraryReferenceException
	 */
	@Test
	public void testGetScriptLibraryReferencesWithMissingPSLRef() throws Exception
	{
		projectImporter = importProjects(new String[]{"/resources/PSLProjects/PSL1/", //$NON-NLS-1$
				"resources/PSLProjects/BrokenRefBaseProject/"}, //$NON-NLS-1$
				new String[]{"PSL1", "BrokenRefBaseProject"}); //$NON-NLS-1$ //$NON-NLS-2$

		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("BrokenRefBaseProject"); //$NON-NLS-1$

		IFile xpdlFile = project.getFile("Process Packages/BaseProject.xpdl"); //$NON-NLS-1$
		WorkingCopy testWC = WorkingCopyUtil.getWorkingCopy(xpdlFile);
		Package pkg = (Package) testWC.getRootElement();

		Process baseProjectProcess = pkg.getProcesses().get(0);

		try
		{
			fixture.getScriptLibraryReferences(baseProjectProcess);
			fail("Should throw ProcessScriptLibraryReferenceException"); //$NON-NLS-1$
		}
		catch (ProcessScriptLibraryReferenceException e)
		{
			assertEquals("File 'NOTEXISTS.psl' referenced in 'bpmScripts.PSL1.NOTEXISTS.multiply' does not exists", //$NON-NLS-1$
					e.getMessage());
		}
	}

	/**
	 * Test to find the references to PSL file in repetition.
	 * 
	 * Preconditions: 'BaseProject' Business Project has references to function present in 'PSL2' project and 'PSL2'
	 * project has reference of function defined in 'PSL3' project.
	 * 
	 * Only one instance of ProcessScriptLibraryReferenceProvider is created and same instance is used to get the next
	 * level references.
	 * 
	 * <pre>
	 * Project Structure : 
	 * 		MultilevelBaseProject.xpdl  
	 * 					| --> User Task -- > bpmScripts.PSL2.Util2.divide 
	 * 					| --> End event --- > bpmScripts.PSL3.Util3.getNumber
	 *
	 *      Util1.psl
	 *          | --> multiply() 
	 *          | --> add()
	 *
	 * 
	 *      Util2.psl
	 *          | --> divide() 
	 *          |       | --> bpmScripts.PSL3.Util3.getNumber
	 *          |       | --> bpmScripts.PSL3.Util3.getNumberRnd 
	 *          | --> checkStatus()
	 *          		| --> bpmScripts.PSL3.Util3.isTrue
	 *          		| --> bpmScripts.PSL1.Util1.add
	 *
	 *      Util3.psl
	 *          | --> getNumberRnd() 
	 *          |       | --> bpmScripts.PSL2.Util2.divide
	 *          | --> getNumber()
	 *          | --> isTrue()
	 *          		| --> bpmScripts.PSL1.Util1.multiply
	 *          		| --> bpmScripts.PSL2.Util2.divide
	 * 
	 * </pre>
	 * 
	 * Expected outcome: PSL process references for each level of call to reference provider.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetScriptLibraryRefForTransitiveDependency() throws Exception
	{
		projectImporter = importProjects(new String[]{"/resources/PSLProjects/PSL1/", //$NON-NLS-1$
				"/resources/PSLProjects/PSL2/", //$NON-NLS-1$
				"/resources/PSLProjects/PSL3/", //$NON-NLS-1$
				"resources/PSLProjects/BaseProject/"}, //$NON-NLS-1$
				new String[]{"PSL1", "PSL2", "PSL3", "BaseProject"}); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("BaseProject"); //$NON-NLS-1$

		IFile xpdlFile = project.getFile("Process Packages/MultilevelBaseProject.xpdl"); //$NON-NLS-1$
		WorkingCopy testWC = WorkingCopyUtil.getWorkingCopy(xpdlFile);
		Package pkg = (Package) testWC.getRootElement();

		Process baseProjectProcess = pkg.getProcesses().get(0);

		/*
		 * -----------------------------------------------------------------------------------------------------
		 * Iteration 1 : Direct dependency
		 * -----------------------------------------------------------------------------------------------------
		 */
		// Act
		Set<Process> result = fixture.getScriptLibraryReferences(baseProjectProcess);

		// Assert
		assertPSLReferences(result, "/PSL2/Process Script Library/Util2.psl", "/PSL3/Process Script Library/Util3.psl");

		Optional<Process> out = result.stream().filter(p -> WorkingCopyUtil.getFile(p).getFullPath().toString()
				.equals("/PSL2/Process Script Library/Util2.psl")).findFirst();
		assertTrue(out.isPresent());
		Process pslProcess = out.get();

		/*
		 * -----------------------------------------------------------------------------------------------------
		 * Iteration 2 : Direct dependency on Util2
		 * -----------------------------------------------------------------------------------------------------
		 */
		// Act
		result = fixture.getScriptLibraryReferences(pslProcess);

		// Assert
		assertPSLReferences(result, "/PSL1/Process Script Library/Util1.psl", "/PSL3/Process Script Library/Util3.psl");

		out = result.stream().filter(p -> WorkingCopyUtil.getFile(p).getFullPath().toString()
				.equals("/PSL3/Process Script Library/Util3.psl")).findFirst();
		assertTrue(out.isPresent());
		pslProcess = out.get();

		out = result.stream().filter(p -> WorkingCopyUtil.getFile(p).getFullPath().toString()
				.equals("/PSL1/Process Script Library/Util1.psl")).findFirst();
		assertTrue(out.isPresent());
		Process pslProcess1 = out.get();

		/*
		 * -----------------------------------------------------------------------------------------------------
		 * Iteration 3 : Direct dependency on Util1
		 * -----------------------------------------------------------------------------------------------------
		 */
		// Act
		result = fixture.getScriptLibraryReferences(pslProcess1);
		// Assert
		assertTrue("Result should be empty as PSL1 does not refer any other PSL", result.isEmpty());

		/*
		 * -----------------------------------------------------------------------------------------------------
		 * Iteration 4 : Direct dependency on Util3
		 * -----------------------------------------------------------------------------------------------------
		 */
		// Act
		result = fixture.getScriptLibraryReferences(pslProcess);
		// Assert
		assertPSLReferences(result, "/PSL1/Process Script Library/Util1.psl", "/PSL2/Process Script Library/Util2.psl");

		out = result.stream().filter(p -> WorkingCopyUtil.getFile(p).getFullPath().toString()
				.equals("/PSL2/Process Script Library/Util2.psl")).findFirst();
		assertTrue(out.isPresent());
		pslProcess = out.get();

		out = result.stream().filter(p -> WorkingCopyUtil.getFile(p).getFullPath().toString()
				.equals("/PSL1/Process Script Library/Util1.psl")).findFirst();
		assertTrue(out.isPresent());
		pslProcess1 = out.get();

		/*
		 * -----------------------------------------------------------------------------------------------------
		 * Iteration 5 : Direct dependency on Util2
		 * -----------------------------------------------------------------------------------------------------
		 */
		result = fixture.getScriptLibraryReferences(pslProcess);
		// Assert
		assertPSLReferences(result, "/PSL1/Process Script Library/Util1.psl", "/PSL3/Process Script Library/Util3.psl");

		out = result.stream().filter(p -> WorkingCopyUtil.getFile(p).getFullPath().toString()
				.equals("/PSL3/Process Script Library/Util3.psl")).findFirst();
		assertTrue(out.isPresent());
		pslProcess = out.get();

		/*
		 * -----------------------------------------------------------------------------------------------------
		 * Iteration 6 : Direct dependency on Util3
		 * -----------------------------------------------------------------------------------------------------
		 */
		out = result.stream().filter(p -> WorkingCopyUtil.getFile(p).getFullPath().toString()
				.equals("/PSL1/Process Script Library/Util1.psl")).findFirst();
		assertTrue(out.isPresent());
		pslProcess1 = out.get();

		/*
		 * -----------------------------------------------------------------------------------------------------
		 * Iteration 7 : Direct dependency on Util1
		 * -----------------------------------------------------------------------------------------------------
		 */
		// Act
		result = fixture.getScriptLibraryReferences(pslProcess1);
		// Assert
		assertTrue("Result should be empty as PSL1 does not refer any other PSL", result.isEmpty());

		/*
		 * -----------------------------------------------------------------------------------------------------
		 * Iteration 8 : Direct dependency on Util3
		 * -----------------------------------------------------------------------------------------------------
		 */
		// Act
		result = fixture.getScriptLibraryReferences(pslProcess);
		// Assert
		assertPSLReferences(result, "/PSL1/Process Script Library/Util1.psl", "/PSL2/Process Script Library/Util2.psl");
	}

	/**
	 * Test to check all the invalid references i.e reference with invalid format or reference which is commented
	 * (single line or block comments ) or reference which is present inside the quoted strings (i.e double or single
	 * quotes) or reference with half commented text.
	 * 
	 * Preconditions: 'IVUtil.psl' contains references to other PSL files but in invalid formats.
	 * 
	 * Expected outcome: 'fixture' under test should not return any reference form IVUtil.psl file.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetScriptLibraryReferencesWithInvalidReferences() throws Exception
	{
		  projectImporter = importProjects(new String[]{ "/resources/PSLProjects/PSL1/", 
		  "/resources/PSLProjects/PSL2/", "/resources/PSLProjects/PSL3/",
					"/resources/PSLProjects/InvalidPSL/", "/resources/PSLProjects/BrokenRefBaseProject/"},
					new String[]{"PSL1", "PSL2", "PSL3", "InvalidPSL", "BrokenRefBaseProject"});
		 
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("BrokenRefBaseProject"); //$NON-NLS-1$

		IFile xpdlFile = project.getFile("Process Packages/InvalidRefBaseProject.xpdl"); //$NON-NLS-1$
		WorkingCopy testWC = WorkingCopyUtil.getWorkingCopy(xpdlFile);
		Package pkg = (Package) testWC.getRootElement();

		Process baseProjectProcess = pkg.getProcesses().get(0);

		Set<Process> result = fixture.getScriptLibraryReferences(baseProjectProcess);
		assertPSLReferences(result, "/InvalidPSL/Process Script Library/IVUtil.psl"); //$NON-NLS-1$

		Process pslProcess = result.toArray(new Process[0])[0];
		result = fixture.getScriptLibraryReferences(pslProcess);
		assertTrue(result.isEmpty());
	}

	/**
	 * Test method to check the pattern used for checking the PSL invocation expression statement.
	 * 
	 * Preconditions: Same regular expression as {@link ProcessScriptLibraryReferenceProvider} should be used while
	 * performing the test.
	 * 
	 * Test Scenario : Should match the Javascript PSL function invocation statement pattern which is in the format
	 * <p>
	 * bpmScripts.<PSL Project name>.<PSL file name without extension>.<PSL function name>
	 * </p>
	 */
	@SuppressWarnings("nls")
	@Test
	public void testRegularExpressionForProcessScriptLibraryStatementPattern()
	{
		String pslPattern = "(?<![\\w\\.])bpmScripts\\s*\\.\\s*\\w+\\s*\\.\\s*\\w+\\s*\\.\\s*\\w+\\s*\\("; //$NON-NLS-1$
		Pattern pattern = Pattern.compile(pslPattern);

		String input = "bpmScripts.PSL.Util.getNumber(parameter1);";
		assertPattern(pattern, input, "bpmScripts.PSL.Util.getNumber(");

		input = "bpmScripts  .  PSL  .   Util\n.   getNumber(parameter1);";
		assertPattern(pattern, input, "bpmScripts  .  PSL  .   Util\n.   getNumber(");

		// Assignment expression
		input = "var abc=bpmScripts.PSL.Util.getNumber1(parameter1);";
		assertPattern(pattern, input, "bpmScripts.PSL.Util.getNumber1(");

		// suffixed by space
		input = "var abc = bpmScripts.PSL.Util.getNumber(parameter1);";
		assertPattern(pattern, input, "bpmScripts.PSL.Util.getNumber(");

		// suffixed by semicolon
		input = "var abc;bpmScripts.PSL.Util.getNumber(parameter1);";
		assertPattern(pattern, input, "bpmScripts.PSL.Util.getNumber(");

		// suffixed by comma
		input = "var abc,bpmScripts.PSL.Util.getNumber(parameter1);";
		assertPattern(pattern, input, "bpmScripts.PSL.Util.getNumber(");

		// suffixed by spaces
		input = "  bpmScripts.PSL.Util.getNumber(parameter1);";
		assertPattern(pattern, input, "bpmScripts.PSL.Util.getNumber(");

		// suffixed by open bracket
		input = "(bpmScripts.PSL.Util.getNumber(parameter1));";
		assertPattern(pattern, input, "bpmScripts.PSL.Util.getNumber(");

		// multiple PSL patterns
		input = "var abc=bpmScripts.PSL.Util.getNumber(parameter1);\n" + "bpmScripts.PSL.Util.new();";
		assertPattern(pattern, input, "bpmScripts.PSL.Util.getNumber(", "bpmScripts.PSL.Util.new(");

		// multiple PSL patterns
		input = "for(i=0;bpmScripts.PSL.Util.isValid(i);i++){\n" + "bpmScripts.PSL.Util.print()}";
		assertPattern(pattern, input, "bpmScripts.PSL.Util.isValid(", "bpmScripts.PSL.Util.print(");

		// Check for arithmetic expressions
		input = "var z=bpmScripts.PSL.Util.getNum(a)+bpmScripts.PSL.Util.getSum(a)-bpmScripts.PSL.Util.getFoo(a)*bpmScripts.PSL.Util.getXX(a)/bpmScripts.PSL.Util.new(a)";
		assertPattern(pattern, input, "bpmScripts.PSL.Util.getNum(", "bpmScripts.PSL.Util.getSum(",
				"bpmScripts.PSL.Util.getFoo(", "bpmScripts.PSL.Util.getXX(", "bpmScripts.PSL.Util.new(");

		input = "true&&bpmScripts.PSL.Util.getNumber(parameter1);";
		assertPattern(pattern, input, "bpmScripts.PSL.Util.getNumber(");

		// Expressions which does not match PSL statement pattern
		input = "abc.bpmScripts.PSL.Util.getNumber(parameter1);";
		assertFalse(pattern.matcher(input).find());

		input = ".bpmScripts.PSL.Util.getNumber(parameter1);";
		assertFalse(pattern.matcher(input).find());

		input = "abcbpmScripts.PSL.Util.getNumber(parameter1);";
		assertFalse(pattern.matcher(input).find());

		input = "123bpmScripts.PSL.Util.getNumber(parameter1);";
		assertFalse(pattern.matcher(input).find());

		input = "bpmScriptsaPSLaUtilagetNumber(parameter1);";
		assertFalse(pattern.matcher(input).find());

		input = "";
		assertFalse(pattern.matcher(input).find());

		input = "   ";
		assertFalse(pattern.matcher(input).find());

	}

	/**
	 * Test method to check the pattern used to find any 'return' keyword
	 * 
	 * Preconditions: Same regular expression as {@link ProcessScriptLibraryReferenceProvider} should be used while
	 * performing the test.
	 * 
	 * Test Scenario : Should match the provided regular expression.
	 * 
	 */
	@SuppressWarnings("nls")
	@Test
	public void testRegularExpressionForReturnKeyword()
	{
		String patternStr = "\\breturn\\b"; //$NON-NLS-1$
		Pattern pattern = Pattern.compile(patternStr);

		String input = "return abc";
		assertTrue(pattern.matcher(input).find());

		input = " return abc; ";
		assertTrue(pattern.matcher(input).find());

		// Expressions which does not match 'return' pattern.
		input = "RETURN";
		assertFalse(pattern.matcher(input).find());

		input = "abcreturn";
		assertFalse(pattern.matcher(input).find());


		input = "returnabc";
		assertFalse(pattern.matcher(input).find());
	}

	/**
	 * Assert that actualProcesses and expected PSL process are same.
	 */
	private void assertPSLReferences(Set<Process> anActualProcesses, String... aExpectedPSLs)
	{
		List<String> expectedPSLList = new LinkedList<>(Arrays.asList(aExpectedPSLs));
		assertEquals("Size of expected and actual processes does not match", aExpectedPSLs.length, //$NON-NLS-1$
				anActualProcesses.size());
		Set<String> actualProcessPaths = new HashSet<>();
		for (Process process : anActualProcesses)
		{
			IFile pslFile = WorkingCopyUtil.getFile(process);
			actualProcessPaths.add(pslFile.getFullPath().toString());
		}

		expectedPSLList.removeAll(actualProcessPaths);

		assertTrue(String.format("Expected PSL reference(s) not found : %s", expectedPSLList), //$NON-NLS-1$
				expectedPSLList.isEmpty());
	}

	/**
	 * Assert the passed input using the {@link Pattern} against all the expected stings given in order which they
	 * should be tested.
	 * 
	 * @param aPattern
	 *            Regular expression {@link Pattern}
	 * @param anInput
	 *            input string to test
	 * @param anOrderedExpected
	 *            Array of expected stings in order
	 */
	private void assertPattern(Pattern aPattern, String anInput, String... anOrderedExpected)
	{
		Matcher matcher = aPattern.matcher(anInput);

		for (String expect : anOrderedExpected)
		{
			assertTrue(matcher.find());
			assertEquals(expect, matcher.group());
		}

		// If find any extra match then fail the test.
		assertFalse(matcher.find());
	}

	/**
	 * @param resourcePath
	 *            the list of strings representing bundle relative project URIs. Two forms are allowed:
	 *            <li>plug-in relative path to folder containing project (resource/myProject/)</li>
	 *            <li>plug-in relative path to zip file containing project content (resource/myProject.zip)</li>. If the
	 *            project is the folder the relative URI should have trailing path separator '/'.
	 * 
	 * @param expectedProjectNames
	 *            The expected projects in the resourcePath
	 * 
	 * @return The {@link ProjectImporter} used to import th projects or <code>null</code> on failure
	 */
	private ProjectImporter importProjects(String[] resourcePaths, String[] expectedProjectNames)
	{
		ProjectImporter importer = TestUtil.importProjectsFromZip("com.tibco.xpd.sce.test", //$NON-NLS-1$
				resourcePaths, // $NON-NLS-1$
				expectedProjectNames);

		assertTrue("Failed to load projects", //$NON-NLS-1$
				importer != null);

		TestUtil.buildAndWait();

		return importer;
	}

}
