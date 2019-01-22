package com.tibco.xpd.bizassets.resources.migrate;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;

import com.tibco.xpd.bizassets.resources.BusinessAssetsConstants;
import com.tibco.xpd.bizassets.resources.Messages;
import com.tibco.xpd.resources.migrateproject.MigrateProject;

public class Prince2Files extends MigrateProject {

	@Override
	public void migrate(IProject project, IProgressMonitor monitor) throws CoreException {
		SubProgressMonitor sub=new SubProgressMonitor(monitor,3);
		sub.beginTask(Messages.Prince2Files_MovingPrince2Files_message, 3);
		sub.worked(1);
		doTask(project,sub);
	}

	private void doTask(IProject project, SubProgressMonitor sub) throws CoreException {
		IFolder source = project.getFolder("Prince2"); //$NON-NLS-1$
		sub.worked(1);
		if(source.isAccessible()){
			IFolder destinationFolder = project.getFolder(BusinessAssetsConstants.BUSINESSASSETS_FOLDER);
			IPath fullPath = destinationFolder.getFolder("Prince2").getFullPath(); //$NON-NLS-1$
			source.move(fullPath, true, null);
			sub.worked(1);
		} else {
			sub.worked(1);
		}

	}

}
