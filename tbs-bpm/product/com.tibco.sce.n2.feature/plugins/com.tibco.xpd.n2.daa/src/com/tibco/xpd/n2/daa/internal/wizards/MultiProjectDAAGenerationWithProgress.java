package com.tibco.xpd.n2.daa.internal.wizards;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;

import com.tibco.xpd.daa.internal.BaseProjectDAAGenerator;
import com.tibco.xpd.daa.internal.util.CompositeUtil;
import com.tibco.xpd.daa.internal.wizards.AbstractMultiProjectDAAGenerationWithProgress;
import com.tibco.xpd.n2.daa.Activator;
import com.tibco.xpd.n2.daa.ProjectDAAGenerator;
import com.tibco.xpd.n2.daa.utils.N2PENamingUtils;
import com.tibco.xpd.resources.logger.Logger;

/**
 * Represents the DAA generation operation for multiple project. The operation
 * will report progress.
 * 
 * @author Jan Arciuchiewicz
 */
public class MultiProjectDAAGenerationWithProgress extends
        AbstractMultiProjectDAAGenerationWithProgress {

    public static final Logger LOG = Activator.getDefault().getLogger();

    /**
     * Creates operation instance.
     * 
     * @param projects
     *            the list of project for which the DAA should be generated.
     */
    public MultiProjectDAAGenerationWithProgress(List<IProject> projects,
            boolean preserveDaaAfterGeneration) {
        super(preserveDaaAfterGeneration);
        this.selectedProjects = new ArrayList<IProject>(projects);
    }

    /**
     * Performs additional operation after DAA generation finished successfully.
     * This method might be invoked from the background thread.
     * 
     * @param project
     *            the project.
     * @param monitor
     *            the progress monitor to report the progress.
     * @return the status of the operation
     */
    @Override
    protected IStatus postGenerationTask(IProject project,
            IProgressMonitor monitor) {
        return super.postGenerationTask(project, monitor);
    }

    /**
     * @see com.tibco.xpd.daa.internal.wizards.AbstractMultiProjectDAAGenerationWithProgress#getProjectDaaGenerator()
     * 
     * @return
     */
    @Override
    protected BaseProjectDAAGenerator getProjectDaaGenerator() {
        return ProjectDAAGenerator.getInstance();
    }

    /**
     * @see com.tibco.xpd.daa.internal.wizards.AbstractMultiProjectDAAGenerationWithProgress#hasErrorLevelProblemMarkers(org.eclipse.core.resources.IProject)
     * 
     * @param project
     * @return
     */
    @Override
    protected boolean hasErrorLevelProblemMarkers(IProject project) {
        try {
            return CompositeUtil.hasErrorLevelProblemMarkers(project);
        } catch (CoreException e) {
            LOG.error(e);
            return false;
        }
    }

    /**
     * @see com.tibco.xpd.daa.internal.wizards.AbstractMultiProjectDAAGenerationWithProgress#getCompositeContributorContextId()
     * 
     * @return
     */
    @Override
    protected String getCompositeContributorContextId() {
        return N2PENamingUtils.BPM_COMPOSITE_CONTRIBUTION_CONTEXT;
    }

}