/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.implementer.resources.xpdl2.dependencies;

import java.util.Collection;
import java.util.HashSet;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.implementer.resources.xpdl2.properties.RestServiceTaskAdapter;
import com.tibco.xpd.resources.IWorkingCopyDependencyProvider;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;

/**
 * Dependency provider for XPDL2 files to RSD and Swagger files.
 *
 * @author nkelkar
 * @since Aug 23, 2024
 */
public class XpdlToRestWorkingCopyDependencyProvider implements IWorkingCopyDependencyProvider
{

	private RestServiceTaskAdapter rsta;

	public XpdlToRestWorkingCopyDependencyProvider()
	{
		rsta = new RestServiceTaskAdapter();
	}

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
	 *            The working copy.
	 * @return A list of RSD or Swagger resources that this XPDL2 resource depends on.
	 */
	@Override
	public Collection<IResource> getDependencies(WorkingCopy wc)
	{
		Collection<IResource> resources = new HashSet<IResource>();
		if (wc != null)
		{
			EObject root = wc.getRootElement();
			if (root instanceof Package)
			{
				Package pckg = (Package) root;
				for (Object next : pckg.getProcesses())
				{
					Process process = (Process) next;
					addDependencies(process, resources);
					for (Object nextSet : process.getActivitySets())
					{
						ActivitySet set = (ActivitySet) nextSet;
						addDependencies(set, resources);
					}
				}
			}
		}
		return resources;
	}

	/**
	 * Searches the flow container for REST invocation activities and adds referenced RSD or Swagger file resources.
	 * 
	 * @param container
	 *            The flow container.
	 * @param resources
	 *            The resources to add dependencies to.
	 */
	private void addDependencies(FlowContainer container, Collection<IResource> resources)
	{
		for (Object next : container.getActivities())
		{
			Activity activity = (Activity) next;
			IFile restFile = rsta.getRSOFile(activity);
			if (restFile != null)
			{
				resources.add(restFile);
			}

		}
	}

}