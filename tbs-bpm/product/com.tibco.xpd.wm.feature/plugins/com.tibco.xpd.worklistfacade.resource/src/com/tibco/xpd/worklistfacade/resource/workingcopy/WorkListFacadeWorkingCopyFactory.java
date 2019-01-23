/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.worklistfacade.resource.workingcopy;

import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.WorkingCopyFactory;
import com.tibco.xpd.worklistfacade.resource.WorkListFacadeResourcePlugin;

/**
 * Working copy factory for Work List Facade resources.
 * 
 * @author aprasad
 * 
 */
public class WorkListFacadeWorkingCopyFactory implements WorkingCopyFactory {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.xpd.resources.WorkingCopyFactory#getWorkingCopy(org.eclipse
	 * .core.resources.IResource)
	 */
	public WorkingCopy getWorkingCopy(IResource resource) {
		return new WorkListFacadeWorkingCopy(
				Collections.singletonList(resource));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.xpd.resources.WorkingCopyFactory#isFactoryFor(org.eclipse.core
	 * .resources.IResource)
	 */
	public boolean isFactoryFor(IResource resource) {
		boolean isFactory = false;
		if (resource instanceof IFile) {
			String ext = ((IFile) resource).getFileExtension();

			isFactory = (ext != null && ext
					.equalsIgnoreCase(WorkListFacadeResourcePlugin.WLF_FILE_EXTENSION));
		}
		return isFactory;
	}

}
