package com.tibco.bx.debug.core.util;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

public class CreateBxLaunchProjectOperation extends WorkspaceModifyOperation {
	
	protected IProject project;
	protected IProjectDescription description;

	public CreateBxLaunchProjectOperation(IPath locationPath) throws CoreException {
		project = LauncherSyncHelper.getBxLaunchProject();
		description = ResourcesPlugin.getWorkspace().newProjectDescription(LauncherConstants.BX_LAUNCH_PROJECT_NAME);
		description.setLocation(locationPath);
	}

	@Override
	protected void execute(IProgressMonitor monitor) throws CoreException, InvocationTargetException, InterruptedException {
		try {
			if (project.exists()) {
				return;
			}

			monitor.beginTask("Create new .com.tibco.bx.launch Project", 2); // $NON-NLS-1$

			// Create project
			project.create(description, new SubProgressMonitor(monitor, 1));

			project.open(new SubProgressMonitor(monitor, 1));
			
			// Not add XPD nature
//			ProjectUtil.addNature(project, XpdConsts.PROJECT_NATURE_ID);
//			XpdResourcesPlugin.getDefault().createDefaultProjectConfigFile(project);

		} finally {
			monitor.done();
		}
		
	}
}
