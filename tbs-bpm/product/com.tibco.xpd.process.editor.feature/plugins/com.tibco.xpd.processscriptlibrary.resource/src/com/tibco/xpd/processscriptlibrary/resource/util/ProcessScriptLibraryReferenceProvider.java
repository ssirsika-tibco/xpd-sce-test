/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */

package com.tibco.xpd.processscriptlibrary.resource.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.processscriptlibrary.resource.config.ProcessScriptLibraryConstants;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Process;

/**
 * ACE-7546: Utility class which can be used to find out references of process script libraries. User can pass 'process'
 * 'process script library process' to <code>getScriptLibraryReferences</code> method to get all the direct references.
 * To get all the dependencies including transitive (i.e indirect), use <code>getDeepScriptLibraryReferences</code>
 * method.
 * 
 * <p>
 * For performance reasons, it is recommended that a single instance of this class is used per-macro-operation (e.g.
 * generation of an individual RASC). Results from previous requests are cached within a single instance of this class,
 * therefore handling of repeated indirect references in referenced script libraries is fast (especially if the caller
 * wishes to recursively process result sets to get all direct/indirect script library references).
 * </p>
 * 
 * @author ssirsika
 * @since 21-Dec-2023
 */
public class ProcessScriptLibraryReferenceProvider
{
	/**
	 * Regular expression pattern for PSL function invocation.
	 * 
	 * <p>
	 * 
	 * <pre>
	 * Pattern description : 
	 *   (?<![\\w\\.]) : Negative lookbehind which matches true only when any word [a-zA-Z0-9_]) or dot is not present.
	 *   bpmScripts    : Matches the characters bpmScripts literally (case sensitive)
	 *   \w*           : Matches zero or unlimited any word character (equivalent to [a-zA-Z0-9_])
	 * 
	 * Example of matches :
	 *  bpmScripts.PSL1.Util.add(
	 *  bpmScripts  .  PSL1  .  Util  .  add  (
	 *  +bpmScripts.PSL1.Util.add(
	 *  *bpmScripts.PSL1.Util.add(
	 *  
	 * Example of invalid matches:
	 *  _bpmScripts.PSL1.Util.add(
	 *  abc.bpmScripts.PSL1.Util.add(
	 * 
	 * </pre>
	 * </p>
	 * NOTE: WHENEVER THIS REGULAR EXPRESSION CONSTANT IS CHANGED, PLEASE UPDATE PATTERN USED IN
	 * <code>ProcessScriptLibraryReferenceProviderTest.testRegularExpressionForProcessScriptLibraryStatementPattern</code>
	 * AS WELL.
	 * 
	 */
	private static final String		PSL_PATTERN_STR						= "(?<![\\w\\.])bpmScripts\\.\\w+\\.\\w+\\.\\w+\\(";	//$NON-NLS-1$

	private static final Pattern	PSL_PATTERN						= Pattern.compile(PSL_PATTERN_STR);

	/**
	 * This pattern matches 'return' keyword. '\b' assert position at a word boundary: (^\w|\w$|\W\w|\w\W)
	 * 
	 * NOTE: WHENEVER THIS REGULAR EXPRESSION CONSTANT IS CHANGED, PLEASE UPDATE PATTERN USED IN
	 * <code>ProcessScriptLibraryReferenceProviderTest.testRegularExpressionForReturn</code> AS WELL.
	 */
	private static final String		RETURN_KEYWORD_PATTERN_STR		= "\\breturn\\b";																	//$NON-NLS-1$

	private static final Pattern	RETURN_KEYWORD_PATTERN			= Pattern.compile(RETURN_KEYWORD_PATTERN_STR);
	/**
	 * Cache contains map of process (XPDL or PSL) to PSL processes i.e process to reference processes.
	 */
	Map<Process, Set<Process>>		processScriptLibraryRefsCache	= new HashMap<>();

	/**
	 * Cache contains map of PSL invocation reference to library process.
	 * 
	 * <pre>
	 * For example, 
	 * 			bpmScripts.PSL.Util <-> Process 
	 * where 'PSL' is the PSL project name and 'Util' is the name of PSL file without extension.
	 * </pre>
	 */
	Map<String, Process>			stringScriptLibraryRefCache		= new HashMap<>();

	/**
	 * Return the reference script libraries for passed process or process script library (PSL) process. Note that, this
	 * utility method will only return direct dependencies.
	 * 
	 * @param aProcessOrLibrary
	 *            Process or process script library process
	 * @return Set of referenced process script libraries (PSL).
	 */
	public Set<Process> getScriptLibraryReferences(Process aProcessOrLibrary)
			throws ProcessScriptLibraryReferenceException
	{
		return internalGetScriptLibraryReferences(aProcessOrLibrary);
	}

	/**
	 * Return the deep references of script libraries for the passed process or process script library (PSL) process.
	 * This method will return all the transitive dependencies for passed process/library.
	 * 
	 * @param aProcessOrLibrary
	 *            Process or process script library process
	 * @return Set of referenced process script libraries (PSL).
	 */
	public Set<Process> getDeepScriptLibraryReferences(Process aProcessOrLibrary)
			throws ProcessScriptLibraryReferenceException
	{
		Set<Process> resultRefs = new HashSet<>();
		internalGetDeepScriptLibraryReferences(aProcessOrLibrary, resultRefs, aProcessOrLibrary);
		return resultRefs;
	}

	/**
	 * @param aProcessOrLibrary
	 *            Process or PSL process for which deep references need to be find out.
	 * @param aResultRefs
	 *            Result {@link Set} of reference
	 * @param anInputProcess
	 *            Initial process for which script library references should be found out. It's final and set only first
	 *            time.
	 * @return
	 * @throws ProcessScriptLibraryReferenceException
	 */
	private void internalGetDeepScriptLibraryReferences(Process aProcessOrLibrary, Set<Process> aResultRefs,
			final Process anInputProcess)
			throws ProcessScriptLibraryReferenceException
	{
		Set<Process> directRefs = internalGetScriptLibraryReferences(aProcessOrLibrary);

		for (Process process : directRefs)
		{
			if (!aResultRefs.contains(process) && anInputProcess != process)
			{
				aResultRefs.add(process);
				internalGetDeepScriptLibraryReferences(process, aResultRefs, anInputProcess);
			}
		}
	}

	/**
	 * Return the direct script library references (i.e PSL processes) referred in the passed process or library. This
	 * method first uses internal cache to find out the references, if can't found the record in the cache then it calls
	 * underline methods to retrieve the references and update the cache for further use.
	 * 
	 * @param aProcessOrLibrary
	 *            Process or PSL process
	 * @return Set of referenced process script libraries (PSL).
	 * @throws ProcessScriptLibraryReferenceException
	 */
	private Set<Process> internalGetScriptLibraryReferences(Process aProcessOrLibrary)
			throws ProcessScriptLibraryReferenceException
	{
		// Check in cache first.
		Set<Process> cachedRefs = processScriptLibraryRefsCache.get(aProcessOrLibrary);
		if (cachedRefs != null)
		{
			return cachedRefs;
		}
		Set<Process> resultProcessRefs = new HashSet<>();
		for (TreeIterator<EObject> allContents = EcoreUtil.getAllContents(aProcessOrLibrary, true); allContents
				.hasNext();)
		{

			EObject eObject = allContents.next();

			if (eObject instanceof Expression
					&& ScriptGrammarFactory.JAVASCRIPT.equals(((Expression) eObject).getScriptGrammar()))
			{
				String expText = ProcessScriptUtil.getExpressionAsString((Expression) eObject);

				if (expText != null)
				{
					resultProcessRefs.addAll(gatherRefs(expText));
				}
			}
		}
		// Put in direct cache.
		processScriptLibraryRefsCache.put(aProcessOrLibrary, resultProcessRefs);
		return resultProcessRefs;
	}

	/**
	 * Gather the references form passed 'anExpressionText'.
	 * 
	 * @param anExpressionText
	 *            Expression Script text to look for PSL references.
	 * @return {@link Set} of referenced PSL processes.
	 * @throws ProcessScriptLibraryReferenceException
	 */
	public Set<Process> gatherRefs(String anExpressionText)
			throws ProcessScriptLibraryReferenceException
	{
		Set<Process> resultProcessRefs = new HashSet<>();
		// Find PSL function invocations.
		Set<String> pslStrRefs = findPSLInvocationRefs(anExpressionText);

		for (String pslStrRef : pslStrRefs)
		{
			// Find actual library process.
			Process libRef = findLibrary(pslStrRef);
			if (libRef != null)
			{
				resultProcessRefs.add(libRef);
			}
		}
		return resultProcessRefs;
	}

	/**
	 * Parse the passed input character by character and empty the quoted (single and double) stings and comments
	 * (single and multi-line).
	 * 
	 * <pre>
	 * For example, 
	 *   This is 'single' and "double" quotes. 
	 *   
	 *   .... will become --> 
	 *   
	 *   This is '' and "" quotes.
	 * 
	 * </pre>
	 * 
	 * @param anInput
	 *            Input stringAbstractAssetGroup
	 * @return resulted string without commented and quoted texts.
	 */
	public static String removeQuotedStringsAndComments(String anInput)
	{
		StringBuilder output = new StringBuilder();
		boolean insideSingleQuote = false;
		boolean insideDoubleQuote = false;
		boolean insideSingleLineComment = false;
		boolean insideMultiLineComment = false;
		boolean escapeNext = false;

		for (int i = 0; i < anInput.length(); i++)
		{
			char ch = anInput.charAt(i);

			if (!escapeNext)
			{
				// Found escape... now ignore next char
				if (ch == '\\')
				{
					escapeNext = true;
				}
				else if (!insideSingleQuote && !insideDoubleQuote)
				{
					if (ch == '\'' && !insideDoubleQuote && !insideSingleLineComment && !insideMultiLineComment)
					{
						insideSingleQuote = true;
						output.append(ch);
					}
					else if (ch == '\"' && !insideSingleQuote && !insideSingleLineComment && !insideMultiLineComment)
					{
						insideDoubleQuote = true;
						output.append(ch);
					}
					else if (ch == '/' && !insideSingleQuote && !insideDoubleQuote && !insideSingleLineComment
							&& !insideMultiLineComment)
					{
						// We have reached the end
						if (anInput.length() == i + 1)
						{
							output.append(ch);
						}
						else
						{
							char nextChar = anInput.charAt(i + 1);
							if (nextChar == '/')
							{
								insideSingleLineComment = true;
								i++; // Skip next character as it's part of the single-line comment
							}
							else if (nextChar == '*')
							{
								insideMultiLineComment = true;
								i++; // Skip next character as it's part of the multi-line comment
							}
							else
							{
								output.append(ch);
							}
						}
					}
				}
				else
				{
					if (ch == '\'' && insideSingleQuote)
					{
						insideSingleQuote = false;
						output.append(ch);
					}
					else if (ch == '\"' && insideDoubleQuote)
					{
						insideDoubleQuote = false;
						output.append(ch);
					}
				}
				// If not in quotes or inside the comments
				if (!insideSingleQuote && !insideDoubleQuote && !insideSingleLineComment && !insideMultiLineComment)
				{
					output.append(ch);
				}
			}
			else
			{
				escapeNext = false;
			}

			/*
			 * Sid ACE-8668 Allow for (the very ODD!!) circumstance where expressions have single '\r' carriage-return
			 * linebreaks with no new-lines).
			 * 
			 * The script source viewer maintains whatever line termination it finds first in the expression, so even
			 * editing the script dowsn't do any good
			 * 
			 * So we'll just treat '\r' as a line terminator in its own right (if it is \r\n then it'll be treated as 2
			 * line terminations, but that doesn't matter when we're looking for comments
			 * 
			 */
			if (insideSingleLineComment && (ch == '\n' || ch == '\r'))
			{
				insideSingleLineComment = false;
			}
			else if (insideMultiLineComment && ch == '*' && i + 1 < anInput.length() && anInput.charAt(i + 1) == '/')
			{
				insideMultiLineComment = false;
				i++; // Skip next character as it's part of the multi-line comment
			}
		}

		return output.toString();
	}

	/**
	 * Append '+' after matching any 'return' word.
	 * 
	 * @param scriptTxt
	 *            Script to be worked on.
	 * @return modified script with all 'return' replaced with 'return+'.
	 */
	private String appendPlusAfterReturn(String scriptTxt)
	{
		// Use a Matcher to find 'return' statements in the JavaScript code
		Matcher matcher = RETURN_KEYWORD_PATTERN.matcher(scriptTxt);

		// Use StringBuffer to modify the JavaScript code
		StringBuffer modifiedCode = new StringBuffer();

		// Iterate through matches and append '+' after 'return' statements
		while (matcher.find())
		{
			matcher.appendReplacement(modifiedCode, "return+"); //$NON-NLS-1$
		}
		matcher.appendTail(modifiedCode);

		// Return the modified JavaScript code
		return modifiedCode.toString();
	}

	/**
	 * Find the PSL invocation pattern in the passed script text and return all the invocation statement strings.
	 * 
	 * For examples, if scriptTask contains following expression...
	 * 
	 * <p>
	 * 
	 * <code>
	 * var abc = bpmScripts.PSL.Util.getNumber(parameter1); var xyz = bpmScripts.PSL.Util.newRecord();
	 * </code>
	 * </p>
	 * 
	 * ... then return set containing 'bpmScripts.PSL.Util.getNumber' and 'bpmScripts.PSL.Util.newRecord'
	 * 
	 * ASSUMPTION : AS PER OUR CURRENT SCRIPT EXPRESSION IMPLEMENTATION, EACH JAVASCRIPT STATEMENT HAS TO BE TERMINATED
	 * WITH ';' (SEMICOLON). FOLLOWING METHOD(i.e CONTAINING REGULAR EXPREESION) WILL NOT WORK CORRECTLY IF THAT
	 * RESTRICTION IS REMOVED.
	 * 
	 * @param aScriptTxt
	 *            Script expression text
	 * @return Set of PSL invocation references
	 */
	private Set<String> findPSLInvocationRefs(String aScriptTxt)
	{
		// Remove quoted strings and comments
		aScriptTxt = removeQuotedStringsAndComments(aScriptTxt);

		/*
		 * Append '+' after the 'return' keyword. In next step we are going to removed all the space characters. So the
		 * statement like
		 * 
		 * return bpmScripts.PSL.Util.getNumber(parameter1);
		 * 
		 * will become
		 * 
		 * returnbpmScripts.PSL.Util.getNumber(parameter1);
		 * 
		 * Our PSL_PATTERN regular expression will not able to find PSL references. So to avoid this, we explicitly add
		 * '+' after the 'return' to make it something like (after removal of spaces)
		 * 
		 * return+bpmScripts.PSL.Util.getNumber(parameter1);
		 * 
		 * Due to extra '+" symbol, our PSL_PATTERN can match reference.
		 */
		aScriptTxt = appendPlusAfterReturn(aScriptTxt);

		/*
		 * Remove all the whitespace characters. This will make sure that "bpmScripts.xxx.yyy.zzz" will become
		 * contiguous string.
		 */
		aScriptTxt = aScriptTxt.replaceAll("\\s+", ""); //$NON-NLS-1$ //$NON-NLS-2$

		Set<String> refSet = new HashSet<>();

		Matcher matcher = PSL_PATTERN.matcher(aScriptTxt);
		while (matcher.find())
		{
			String group = matcher.group();
			// Store string without ending opening bracket i.e '('.
			String libStr = group.substring(0, group.length() - 1);
			refSet.add(libStr);
		}
		return refSet;
	}

	/**
	 * Finds the library process reference from passed String reference. This method actually access the
	 * location/objects mentioned in the passed PSL invocation string reference.
	 * 
	 * <p>
	 * That means, if <code>bpmScripts.PSL.Util.getNumber</code> is passed as <i>aPslInvocationStrRef</i>, then method
	 * will access process with name 'PSL' then find the PSL file with name 'Util.psl'. It would not check for
	 * 'getNumber' function's existence, as that would have been already checked by the script validators.
	 * </p>
	 * 
	 * @param aPslInvocationStrRef
	 *            PSL invocation string
	 * @return PSL process represented by the passed <code>aPslInvocationStrRef</code>
	 * @throws ProcessScriptLibraryReferenceException
	 *             Appropriate exception occurred while finding the referencing Process Script Library function.
	 */
	private Process findLibrary(String aPslInvocationStrRef)
			throws ProcessScriptLibraryReferenceException
	{
		if (aPslInvocationStrRef.trim().equals("")) //$NON-NLS-1$
		{
			return null;
		}

		/*
		 * We should always expect the format something like
		 * 
		 * bpmScripts.<PSL Project Name>.<PSL File name without extension>.<Function Name>
		 */
		String[] split = aPslInvocationStrRef.split("\\."); //$NON-NLS-1$
		if (split.length != 4)
		{
			throw new ProcessScriptLibraryReferenceException(
					String.format("Invalid Process Script Library reference pattern '%s'", aPslInvocationStrRef)); //$NON-NLS-1$
		}

		// Get the PSL invocation reference with function name.
		String pslRefWithoutFuncName = String.join(".", split[0], split[1], split[2]); //$NON-NLS-1$

		/*
		 * If found in the cache then just return it.
		 */
		Process cachedProcessLib = stringScriptLibraryRefCache.get(pslRefWithoutFuncName);
		if (cachedProcessLib != null)
		{
			return cachedProcessLib;
		}


		String projectName = split[1];

		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);

		if (!project.exists())
		{
			throw new ProcessScriptLibraryReferenceException(
					String.format("Project '%s' referenced in '%s' does not exists", project.getName(), //$NON-NLS-1$
							aPslInvocationStrRef));
		}

		String pslFileName = split[2] + "." + ProcessScriptLibraryConstants.PSL_FILE_EXTENSION; //$NON-NLS-1$

		IFile pslFile = findPSLFile(project, pslFileName);
		if (pslFile == null)
		{
			throw new ProcessScriptLibraryReferenceException(
					String.format("File '%s' referenced in '%s' does not exists", pslFileName, //$NON-NLS-1$
							aPslInvocationStrRef));
		}

		WorkingCopy pslWC = WorkingCopyUtil.getWorkingCopy(pslFile);

		if (pslWC == null)
		{
			throw new ProcessScriptLibraryReferenceException(
					String.format("No working copy found for '%s'", aPslInvocationStrRef)); //$NON-NLS-1$
		}

		com.tibco.xpd.xpdl2.Package pkg = (com.tibco.xpd.xpdl2.Package) pslWC.getRootElement();
		Process resultLib = pkg.getProcesses().get(0);

		if (resultLib != null)
		{
			stringScriptLibraryRefCache.put(pslRefWithoutFuncName, resultLib); // $NON-NLS-1$
		}
		else
		{
			throw new ProcessScriptLibraryReferenceException(
					String.format("Process Script Library for '%s' not found", aPslInvocationStrRef)); //$NON-NLS-1$
		}

		return resultLib;
	}

	/**
	 * Find PSL file with supplied name in the passed project.
	 * 
	 * @param aProject
	 *            Project under which PSL file needs to be found.
	 * @param aPslFileNameWithExt
	 *            Name of psl file with extension. (i.e xyz.psl)
	 * @return {@link IFile} if file found otherwise <code>null</code>
	 */
	private IFile findPSLFile(IProject aProject, String aPslFileNameWithExt)
	{
		/*
		 * Following SpecialFolderUtil works even if there are multiple special folders in the single project and the
		 * file is directly under the script libraries special folder.
		 */
		IFile pslFile = SpecialFolderUtil.resolveSpecialFolderRelativePath(aProject,
				ProcessScriptLibraryConstants.PSL_SPECIAL_FOLDER_KIND, aPslFileNameWithExt);

		/*
		 * If not found then the file could be in a sub-folder under the special folder (in theory) so find it in list
		 * of all libraries in project...
		 */
		if (pslFile == null || !pslFile.exists())
		{
			// ... following method checks for PSL present in normal sub-folder nested in special folder.
			List<IResource> allPslFiles = SpecialFolderUtil.getAllDeepResourcesInSpecialFolderOfKind(aProject,
					ProcessScriptLibraryConstants.PSL_SPECIAL_FOLDER_KIND, aPslFileNameWithExt, false);
			pslFile = (IFile) allPslFiles.stream().filter(f -> f.getName().equals(aPslFileNameWithExt)).findFirst()
					.orElse(null);

		}
		return pslFile;
	}
}
