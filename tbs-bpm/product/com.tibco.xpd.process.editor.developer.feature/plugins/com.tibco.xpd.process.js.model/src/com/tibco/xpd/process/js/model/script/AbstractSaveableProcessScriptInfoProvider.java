/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.process.js.model.script;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.processscriptlibrary.resource.util.ProcessScriptLibraryReferenceException;
import com.tibco.xpd.processscriptlibrary.resource.util.ProcessScriptLibraryReferenceProvider;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider;
import com.tibco.xpd.xpdl2.Process;

/**
 *
 *
 * @author cbabar
 * @since Mar 1, 2024
 */
public abstract class AbstractSaveableProcessScriptInfoProvider extends AbstractScriptInfoProvider
{

	
	/**
	 * This is flag to make sure if we are already executing the executeSaveCommand.
	 * 
	 * ACE-7401 :- As part of this we are adding Project dependencies required for references of Process Script
	 * Function.
	 * 
	 * The executeSaveCommand is called on 
	 * 
	 * 1. When there is change in script while inline editing. (i.e. timed modal update) 
	 * 2. When there is focus lost.
	 * 
	 * So when executeSaveCommand is called once there is change in script, we check the new script for Project
	 * dependencies required for references of Process Script Function, if the references are found we display standard
	 * modal dialog for adding Project dependencies.
	 * 
	 * Once the modal dialog is display is called, the focus is lost which again leads to call of executeSaveCommand.
	 * 
	 * So this flag, helps us to identify that weather we are already in the execution of executeSaveCommand or not.
	 * 
	 * If we are already executing the executeSaveCommand, we prevent executing the save command again as it is
	 * unnessary since we are already in the middle of executing it.
	 */
	public boolean alreadyExecutingSave = false;
	
	/**
	 * @see com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider#executeSaveCommand(org.eclipse.jface.text.IDocument)
	 *
	 * @param document
	 */
	@Override
	public final boolean executeSaveCommand(IDocument document)
	{

		/**
		 * ACE-7401 :- Project dependencies required for references of Process Script Function.
		 * 
		 * Condition of Protection against multiple calls to executeSaveCommand, if we are already in the middle of
		 * execution of the same.
		 */
		if (alreadyExecutingSave)
		{
			return true;
		}
		try
		{
			alreadyExecutingSave = true;

			String scriptGrammar = getScriptGrammar();

			if (ScriptGrammarFactory.JAVASCRIPT.equals(scriptGrammar))
			{
				String script = document.get();
				ProcessScriptLibraryReferenceProvider refProvider = new ProcessScriptLibraryReferenceProvider();

				try
				{
					Set<Process> gatherRefs = refProvider.gatherRefs(script);

					if (!gatherRefs.isEmpty())
					{

						IProject project = WorkingCopyUtil.getProjectFor(getInput());

						Set<IProject> gatherProjectsRefs = new HashSet<>();
						for (Iterator iterator = gatherRefs.iterator(); iterator.hasNext();)
						{
							Process process = (Process) iterator.next();
							IProject refProject = WorkingCopyUtil.getProjectFor(process);

							if (process != null)
							{
								gatherProjectsRefs.add(refProject);
							}
						}


						/**
						 * ACE-7401 :- Project dependencies required for references of Process Script Function
						 * 
						 * If the user selects NO to add the references of depenedent project return false i.e. abort
						 * the current executeSaveCommand which resets the script view back to the content it had before
						 * the add project references was triggered.
						 */
						if (!ProcessUIUtil.checkAndAddProjectReference(Display.getDefault().getActiveShell(),
								project, gatherProjectsRefs))
						{
							return false;
						}

					}

				}
				catch (ProcessScriptLibraryReferenceException e)
				{
					e.printStackTrace();
				}
			}

			doExecuteSaveCommand(document);

		}
		finally
		{
			// ACE-7401 :- Unsetting the flag once we have completed executing the save command.
			alreadyExecutingSave = false;
		}

		return true;
	}

	protected abstract void doExecuteSaveCommand(IDocument document);

}
