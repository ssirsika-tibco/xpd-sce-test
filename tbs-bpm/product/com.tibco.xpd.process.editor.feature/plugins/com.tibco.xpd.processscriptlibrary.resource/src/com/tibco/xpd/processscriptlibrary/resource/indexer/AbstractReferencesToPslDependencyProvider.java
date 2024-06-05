/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.processscriptlibrary.resource.indexer;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.processscriptlibrary.resource.util.ProcessScriptLibraryReferenceException;
import com.tibco.xpd.processscriptlibrary.resource.util.ProcessScriptLibraryReferenceProvider;
import com.tibco.xpd.resources.IWorkingCopyDependencyProvider;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;

/**
 * Abstract Class that does base handling of dependencies between a {@link Xpdl2WorkingCopyImpl} files and PSL library
 * files.
 * 
 * Sid ACE-8238
 *
 * @author aallway
 * @since 22 May 2024
 */
public abstract class AbstractReferencesToPslDependencyProvider implements IWorkingCopyDependencyProvider
{
	/**
	 * Get the File extension that this dependency provider is for.
	 * 
	 * @return
	 */
	protected abstract String getFileExtension();

	/**
	 * @see com.tibco.xpd.resources.IWorkingCopyDependencyProvider#getWorkingCopyClass()
	 *
	 * @return
	 */
	@Override
	public Class< ? extends WorkingCopy> getWorkingCopyClass()
	{
		return Xpdl2WorkingCopyImpl.class;
	}

	/**
	 * @see com.tibco.xpd.resources.IWorkingCopyDependencyProvider#getDependencies(com.tibco.xpd.resources.WorkingCopy)
	 *
	 * @param wc
	 * @return
	 */
	@Override
	public Collection<IResource> getDependencies(WorkingCopy wc)
	{
		IFile thisFile = WorkingCopyUtil.getFile(wc.getRootElement());

		if (thisFile == null || !thisFile.getFileExtension().equals(getFileExtension()))
		{
			return Collections.emptyList();
		}

		Set<IResource> pslFileDependencies = new HashSet<>();
		
		if (wc != null)
		{
			/* Get processes that sub-class wants us to check for PSL dependencies */
			Collection<Process> processes = getProcesses(wc);

			if (processes != null && !processes.isEmpty())
			{
				/*
				 * Look for dependencies in each process, creating a set of PSL files that this working copy's file
				 * depends on
				 */
				Set<Process> pslProcessDependencies = new HashSet<>();

				ProcessScriptLibraryReferenceProvider referenceProvider = new ProcessScriptLibraryReferenceProvider();

				for (Process process : processes)
				{
					/*
					 * Get the direct dependencies (for working copy dependencies we only need to revalidate when things
					 * we directly reference are changed, so only need to get direct dependencies, rather deep
					 * dependencies recursively).
					 */
					try
					{
						pslProcessDependencies.addAll(referenceProvider.getScriptLibraryReferences(process));
					}
					catch (ProcessScriptLibraryReferenceException e)
					{
						/*
						 * We don't care about exceptions, because these will be due to badly formatted expression text
						 * or references to missing PSL's which are possible in normal use-cases.
						 */
					}
				}
				
				/*
				 * Build a list of PSL files from PSL processes (except any cross-dependency on the file we're checking
				 * for dependencies in the first place.
				 */
				for (Process pslProcess : pslProcessDependencies)
				{
					IFile pslFile = WorkingCopyUtil.getFile(pslProcess);
					
					if (pslFile != null && !pslFile.equals(thisFile))
					{
						pslFileDependencies.add(pslFile);
					}
				}
			}
		}

		return pslFileDependencies;
	}

	/**
	 * Get the set of processes that might have expressions that reference PSL functions.
	 * 
	 * @param wc
	 * 
	 * @return set of processes that might have expressions that reference PSL functions.
	 */
	private Collection<Process> getProcesses(WorkingCopy wc)
	{
		EObject rootElement = wc.getRootElement();

		if (rootElement instanceof Package)
		{
			return ((Package) rootElement).getProcesses();
		}

		return null;
	}
}
