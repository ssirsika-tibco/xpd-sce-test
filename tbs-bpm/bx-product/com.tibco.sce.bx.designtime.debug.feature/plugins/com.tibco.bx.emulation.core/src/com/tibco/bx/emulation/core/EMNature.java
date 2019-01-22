package com.tibco.bx.emulation.core;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.resources.util.ProjectUtil;

public class EMNature  implements IProjectNature
{

	 public static final String EM_NATURE_ID = EmulationCoreActivator.PLUGIN_ID + ".emNature"; //$NON-NLS-1$

	    public static final String EM_BUILDER_ID = EmulationCoreActivator.PLUGIN_ID + ".emBuilder"; //$NON-NLS-1$
	    											
	    private IProject project;

	    public void configure() throws CoreException {
	        ProjectUtil.addBuilderToProject(project, EM_BUILDER_ID);
	    }

	    public void deconfigure() throws CoreException {
	        ProjectUtil.removeBuilderFromProject(project, EM_BUILDER_ID);
	    }

	    public IProject getProject() {
	        return project;
	    }

	    public void setProject(IProject project) {
	        this.project = project;
	    }
}