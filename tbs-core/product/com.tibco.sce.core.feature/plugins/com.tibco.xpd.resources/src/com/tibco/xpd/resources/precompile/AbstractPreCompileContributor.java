/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.resources.precompile;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.internal.Messages;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.util.ProjectUtil;

/**
 * Abstract contributor class that executes the life cycle methods on project(s)
 * when pre-compile is enabled or disabled
 * 
 * @author bharge
 * @since 24 Oct 2014
 */
public abstract class AbstractPreCompileContributor {

    Logger logger = XpdResourcesPlugin.getDefault().getLogger();

    private String contributorId;

    /**
     * For a project marked as pre compile project, return the source resource
     * (IFolder/IFile) from each contribution, that needs to be under
     * .precompiled folder
     * 
     * @param project
     *            - project marked as pre compile project
     * @return <code>IResource</code> (<code>IFolder</code> or
     *         <code>IFile</code>) that needs to be under the .precompiled
     *         folder
     */
    public abstract IResource getSourceResource(IProject project);

    /**
     * For a project marked as pre compile project, return the target IPath to
     * resource (IFolder/IFile) from each contribution, that needs to be put
     * under .precompiled folder
     * 
     * <p>
     * Provide the path such that it is not configured as an ignored resource by
     * the version control system
     * </p>
     * 
     * @param project
     *            - project marked as pre compile project
     * @return <code>IPath</code> to the target resource that goes under
     *         .precompiled folder
     */
    public abstract IPath getPrecompileTarget(IProject project);

    /* package */void setContributorId(String contributorId) {

        this.contributorId = contributorId;
    }

    public String getContributorId() {

        return contributorId;
    }

    /**
     * Prepares the project for contribution. Should check if there is anything
     * that needs to be done before the artifacts are generated under
     * precompiled folder. This method must be called before the
     * preservePrecompiledArtifacts method that actually copies the artifacts
     * under precompiled folder.
     * <p>
     * <b>Note that the default implementation of this class does nothing and
     * therefore execution by override methods is unnecessary</b>
     * </p>
     * 
     * @param project
     *            project that is marked as pre-compiled project
     * @param preCompileFolder
     *            folder where the implementation classes have to generate their
     *            artefacts
     * @param progressMonitor
     *            progress monitor
     * @return IStatus to determine if the contribution class is prepared to
     *         generate the artefacts under pre compiled folder. If status is
     *         anything other than IStatus.OK or IStatus.WARNING then the
     *         contribute method must <strong>not</strong> be called
     */
    public abstract IStatus precompile(IProject project,
            IFolder preCompileFolder, IProgressMonitor progressMonitor)
            throws CoreException;

    /**
     * Generate or copy the required artifacts for the pre-compiled project
     * under the pre compiled folder.
     * 
     * 
     * @param project
     *            project that is marked as pre-compiled project
     * @preCompileFolder folder where the implementation classes have to
     *                   generate/copy their artifacts
     * @param progressMonitor
     *            progress monitor
     * @return IStatus to determine if the artifacts are generated fine. If
     *         status is anything other than IStatus.OK or IStatus.WARNING then
     *         precompile folder must not have any artifacts from that
     *         contribution
     * @throws CoreException
     */
    public final IStatus preservePrecompiledArtifacts(final IProject project,
            IFolder preCompileFolder, final IProgressMonitor progressMonitor)
            throws CoreException {

        try {
            progressMonitor.beginTask("", 1); //$NON-NLS-1$

            progressMonitor
                    .subTask(Messages.EnablePreCompileProjectAction_EnablePreCompile_precompile_message);

            final IResource sourceResource = getSourceResource(project);

            final IPath precompilePathFromContribution =
                    getPrecompileTarget(project);

            if (null != sourceResource
                    && null != precompilePathFromContribution) {

                if (sourceResource.isAccessible()) {

                    assert sourceResource.getProject().equals(project) : "Resource should be from the same project.";

                    /*
                     * set team private member to false
                     */
                    /*
                     * Check-in/check-out to SVN without this is fine. So I
                     * don't think we need this, but leaving it here as
                     * commented as I can't re-collect why we wanted to do this
                     * in the first place!
                     */
                    // PreCompileUtil.setTeamPrivateMember(project,
                    // progressMonitor,
                    // sourceResource,
                    // precompilePathFromContribution);

                    /* create target folder structure from source */
                    IFolder targetFolder =
                            project.getFolder(precompilePathFromContribution);
                    /*
                     * Target folder must already be created and available. No
                     * need to create again, but just in case if it is not
                     * created!
                     */
                    ProjectUtil.createFolder(targetFolder,
                            false,
                            new NullProgressMonitor());
                    /*
                     * We expect that source artifacts are always under
                     * folder(s)
                     */
                    if (sourceResource instanceof IFolder) {

                        IFolder sourceFolder = (IFolder) sourceResource;
                        for (IResource sourceRes : sourceFolder.members()) {

                            /*
                             * Copy the generated source artifact file from
                             * source folder to target folder
                             */
                            sourceRes.copy(targetFolder.getFullPath()
                                    .append(sourceRes.getName()),
                                    true,
                                    new NullProgressMonitor());
                        }
                    } else {

                        assert false : "Folder is expected";
                    }

                } else {

                    logger.info(String
                            .format(Messages.AbstractPreCompileContributor_preservePrecompileArtifacts_no_artifacts_message,
                                    project.getName()));
                }
            } else {

                logger.info(String
                        .format(Messages.AbstractPreCompileContributor_preservePrecompileArtifacts_no_artifacts_message,
                                project.getName()));
            }

            progressMonitor.worked(1);

            return Status.OK_STATUS;

        } finally {
            progressMonitor.done();
        }
    }

    /**
     * This method should be invoked if there is anything that needs to be done
     * after the artefacts are contributed - i.e. calling the indexers if there
     * are any issues in indexing. If contributor has not finished properly or
     * has generated artefacts with error markers, then individual contributors
     * must use this method to clean up the precompiled folder and do any other
     * clean up task(s).
     * 
     * <p>
     * Each individual contribution must ensure to call super postPrecompile,
     * because in super method we ensure all the artifacts from under source
     * folder are deleted (because they are already copied under target folder).
     * Sub classes must implement this only if they have to do any specific
     * clean-up or indexer tasks.
     * </p>
     * 
     * @param project
     *            project that is marked as pre-compiled project
     * @param preCompileFolder
     *            folder where the implementation classes have to generate their
     *            artefacts
     * @param progressMonitor
     *            progress monitor
     * @return IStatus to determine if the post contribute is finished fine. If
     *         status is anything other than IStatus.OK or IStatus.WARNING then
     *         precompile folder must not have any artefacts from that
     *         contribution
     */
    public IStatus postPrecompile(IProject project, IFolder preCompileFolder,
            IProgressMonitor progressMonitor) throws CoreException {

        try {

            progressMonitor.beginTask("", 1); //$NON-NLS-1$
            progressMonitor
                    .subTask(Messages.EnablePreCompileProjectAction_EnablePreCompile_postPrecompile_message);
            /*
             * Delete source folder content.
             */
            final IResource sourceResource = getSourceResource(project);
            if (sourceResource instanceof IFolder && sourceResource.exists()) {

                PreCompileUtil.deleteFolderMembers((IFolder) sourceResource);
                sourceResource.refreshLocal(IResource.DEPTH_INFINITE,
                        new NullProgressMonitor());
            }

            progressMonitor.worked(1);
            return Status.OK_STATUS;

        } finally {

            progressMonitor.done();
        }

    }

    /**
     * 
     * @param project
     *            project that is marked as pre-compiled project
     * @param preCompileFolder
     *            folder where artefacts have been generated by the pre compile
     *            contributors
     * @param progressMonitor
     *            progress monitor
     * @return IStatus to determine if the contribution is successful
     */
    public IStatus aboutToDisablePrecompile(IProject project,
            IFolder preCompileFolder, IProgressMonitor progressMonitor) {

        return Status.OK_STATUS;
    }

    /**
     * 
     * @param project
     *            project that is marked as pre-compiled project
     * @param preCompileFolder
     *            folder where artefacts have been generated by the pre compile
     *            contributors
     * @param progressMonitor
     *            progress monitor
     * @return IStatus to determine if the contribution is successful
     */
    public IStatus postPrecompileDisabled(IProject project,
            IFolder preCompileFolder, IProgressMonitor progressMonitor) {

        try {

            PreCompileUtil.deleteFolderMembers(preCompileFolder);

        } catch (CoreException e) {

            Status status =
                    new Status(
                            Status.ERROR,
                            XpdResourcesPlugin.ID_PLUGIN,
                            Messages.AbstractPreCompileContributor_DisablePrecompile_deleting_resources_error_message,
                            e);
            return status;
        }
        return Status.OK_STATUS;
    }

}
