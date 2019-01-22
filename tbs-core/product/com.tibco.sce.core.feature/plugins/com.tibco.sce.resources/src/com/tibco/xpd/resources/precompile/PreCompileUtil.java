/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.resources.precompile;

import java.util.List;
import java.util.zip.CRC32;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.ProjectDetails;

/**
 * Utilities for enable/disable pre-compile on a project.
 * 
 * @author bharge
 * @since 4 Jun 2015
 */
public class PreCompileUtil {

    public static final String PROJECT_DETAILS_CHECK_SUM_KEY =
            "ProjectDetailsCheckSum"; //$NON-NLS-1$

    /**
     * Recursively deletes all files under given folder and its sub folders
     * (never deletes the folder(s) but just the files under folder(s))
     * 
     * @param folder
     * @throws CoreException
     */
    public static void deleteFolderMembers(IFolder folder) throws CoreException {

        if (folder.isAccessible()) {

            IResource[] members = folder.members();
            for (int i = 0; i < members.length; i++) {

                if (members[i] != null && members[i].isAccessible()) {

                    /* Delete if it is a file */
                    if (members[i] instanceof IFile) {

                        members[i].delete(true, new NullProgressMonitor());
                    } else if (members[i] instanceof IFolder) {

                        /*
                         * If it is a folder, recursively call this method to
                         * delete files under it
                         */
                        IFolder memberFolder = (IFolder) members[i];
                        deleteFolderMembers(memberFolder);
                    }
                }
            }
        }
    }

    /**
     * TODO REMOVE IF NOT NECESSARY FROM AbstractPreCompileCOntributor
     * Determines whether the given resource is a team private member
     * 
     * @param resource
     * @return <code>true</code> if this resource is a team private member, and
     *         <code>false</code> otherwise
     */
    private static boolean isIgnoredResource(IResource resource) {

        boolean teamPrivateMember =
                resource.isTeamPrivateMember(IResource.TEAM_PRIVATE);
        boolean teamPrivateMember2 =
                resource.isTeamPrivateMember(IResource.CHECK_ANCESTORS);

        return teamPrivateMember || teamPrivateMember2;
    }

    /**
     * The only call to this method from AbstractPreCompileContributor is
     * commented. But still leaving this here just in case if we realise that we
     * might need to have it. As I can't recollect why we wanted to do this in
     * the first place!
     * 
     * <p>
     * Set the given source resource members team private member to false
     * </p>
     * 
     * @param project
     * @param progressMonitor
     * @param sourceResource
     * @param precompileTargetPath
     * 
     * @throws CoreException
     */
    public static void setTeamPrivateMember(final IProject project,
            final IProgressMonitor progressMonitor,
            final IResource sourceResource, final IPath precompileTargetPath)
            throws CoreException {

        if (sourceResource instanceof IFolder) {

            IResource[] members = ((IFolder) sourceResource).members();
            for (IResource resource : members) {

                boolean ignoredResource =
                        PreCompileUtil.isIgnoredResource(resource);
                if (ignoredResource) {

                    resource.setTeamPrivateMember(false);
                }
            }
        }
    }

    /**
     * Goes thru the resources in a given project and checks for all the
     * resources that are enabled for pre-compile and returns the list of source
     * resources by reference
     * 
     * @param refProject
     * @return
     */
    public static void getEnabledSourceArtifacts(IProject project,
            final List<IResource> resourcesEnabledForPrecompile) {

        try {
            project.accept(new IResourceVisitor() {

                @Override
                public boolean visit(IResource resource) throws CoreException {

                    boolean resEnabledForPrecompile =
                            PreCompileContributorManager.getInstance()
                                    .isPrecompiledSourceArtefact(resource);
                    if (resEnabledForPrecompile) {

                        resourcesEnabledForPrecompile.add(resource);
                        /*
                         * Exit if we find one resource that is enabled for
                         * pre-compile
                         */
                        return false;
                    }
                    return true;
                }
            });
        } catch (CoreException e) {

            XpdResourcesPlugin.getDefault().getLogger().error(e.getMessage());
        }
    }

    /**
     * For a given project calculates the checksum on the project id, version
     * and enabled destination(s)
     * 
     * @param project
     * @return check sum value string. Can return null, caller must ensure null
     *         check.
     */
    public static String getProjectDetailsChecksumValue(IProject project) {

        ProjectConfig config =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);
        if (null != config && null != config.getProjectDetails()) {

            ProjectDetails projectDetails = config.getProjectDetails();
            String version = projectDetails.getVersion();
            String id = projectDetails.getId();

            final CRC32 crc = new CRC32();
            if (null != id) {

                crc.update(id.getBytes());
            }
            if (null != version) {

                crc.update(version.getBytes());
            }
            crc.update(projectDetails.getEnabledGlobalDestinationIds()
                    .toString().getBytes());

            String checksumValue = String.valueOf(crc.getValue());
            return checksumValue;
        }
        return null;
    }
}
