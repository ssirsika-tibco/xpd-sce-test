package com.tibco.bds.designtime.generator;

import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.tibco.xpd.bom.gen.api.BOMGenerator2;
import com.tibco.xpd.resources.util.ProjectUtil;

public abstract class AbstractBDSGenerator extends BOMGenerator2 {

    public static final String DEFAULT_BUNDLE_VERSION = "1.0.0";

	@Override
    public boolean supports(Collection<IFile> bomResources, IProgressMonitor monitor) throws CoreException {
        boolean result = super.supports(bomResources, monitor);
        if (result) {
            result = BDSUtils.shouldGenerateBDSPlugin(bomResources);
        }
        return result;
    }

	/**
	 * Resolves the generated plugin version for a given Bom File
	 * 
	 * @param bomResource
	 * 
	 * @return the generated plugin version, null if the bom resource is invalid
	 */
	protected String resolveGeneratedPluginVersion(IResource bomResource) {
	
	    if (bomResource != null) {
	        IProject project = bomResource.getProject();
	
	        if (project != null) {
	            // Returning the project version
	            return ProjectUtil.getProjectVersion(project);
	        }
	    }
	
	    return DEFAULT_BUNDLE_VERSION;
	}

    public void addJavaPDEBuildersToGeneratedProject(IFile bomFile) throws CoreException {
        super.addJavaPDEBuilders(getProject(bomFile));
    }
}
