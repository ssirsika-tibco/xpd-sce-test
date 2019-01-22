package com.tibco.bx.emulation.core.resource;

import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;

import com.tibco.bx.emulation.core.EmulationCoreActivator;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.WorkingCopyFactory;

public class EMWorkingCopyFactory  implements WorkingCopyFactory{

	public EMWorkingCopyFactory()
	{	 
	}
	 
	public WorkingCopy getWorkingCopy(IResource resource) {
		 return new EMWorkingCopy(Collections.singletonList(resource));
	}

	public boolean isFactoryFor(IResource resource) {
		boolean flag = false;
		if (resource instanceof IFile)
			flag = EmulationCoreActivator.EMULATION_FILE_EXTENSION.equalsIgnoreCase(((IFile) resource).getFileExtension());
		return flag;
	}

}
