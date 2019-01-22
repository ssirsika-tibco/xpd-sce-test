/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.daa.internal.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.zip.CRC32;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.osgi.service.prefs.BackingStoreException;

import com.tibco.xpd.daa.DaaActivator;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.util.CyclicDependencyException;
import com.tibco.xpd.resources.util.ProjectUtil2;

/**
 * Utility to add checksums to a BPM projects (and to later verify the
 * checksums) for the project and the exported DAA file.
 * 
 * @author jarciuch
 * @since 9 Jul 2015
 */
public class BpmProjectChecksumUtils {

    /**
     * Plug-in logger.
     */
    private static final Logger LOG = DaaActivator.getDefault().getLogger();

    /**
     * The key used to store combined project and DAA checksum in project
     * settings.
     */
    private static final String DAA_CHECKSUM = "DAA_CHECKSUM"; //$NON-NLS-1$

    /**
     * Calculate the current checksum of the project and daaFile and store the
     * combined value in the project's settings.
     * 
     * @param project
     *            the project used as a source of the DAA.
     * @param daaFile
     *            the DAA file.
     */
    public static void calculateAndSetChecksum(IProject project, IFile daaFile) {
        try {

            String daaChecksum = calculateContentChecksum(daaFile);
            if (daaChecksum == null) {
                return;
            }

            String projectChecksum = calculateChecksum(project);
            if (projectChecksum == null) {
                return;
            }

            String value =
                    new ChecksumValue(daaChecksum, projectChecksum).toString();
            updatePreference(project, DAA_CHECKSUM, value);
            if (Platform.inDebugMode()) {
                System.out.println("Project checksum was updated with: " //$NON-NLS-1$
                        + value);
            }

        } catch (CoreException e) {
            DaaActivator.getDefault().getLog().log(e.getStatus());
        } catch (BackingStoreException e) {
            LOG.error(e);
        }

    }

    /**
     * Checks if the project has changed for the specified DAA.
     */
    public static boolean hasProjectChangedForDaa(IProject project,
            IFile daaFile) {
        try {

            ChecksumValue checksumValue =
                    ChecksumValue.parseString(getPreferenceValue(project,
                            DAA_CHECKSUM));
            if (checksumValue == null) {
                return true;
            }

            // Check if DAA checksum is the same.
            String daaChecksum = calculateContentChecksum(daaFile);
            if (daaChecksum == null
                    || !daaChecksum.equals(checksumValue.getDaaChecksum())) {
                // Don't check further if the daa chacksum doesn't match.
                return true;
            }

            String projectChecksum = calculateChecksum(project);
            if (projectChecksum != null
                    && projectChecksum.equals(checksumValue
                            .getProjectChecksum())) {
                // Calculated project checksum matches the stored project
                // checksum.
                return false;
            }
        } catch (CoreException e) {
            DaaActivator.getDefault().getLog().log(e.getStatus());
        } catch (BackingStoreException e) {
            LOG.error(e);
        }
        return true;
    }

    /**
     * Calculate the checksum of this project. It will also use all referenced
     * projects (including transient references).
     * 
     * @param project
     *            project to checksum.
     * @return string representing checksum long value.
     */
    private static String calculateChecksum(IProject project)
            throws CoreException {

        long start = 0, t = 0;
        start = System.currentTimeMillis();

        final CRC32 crc = new CRC32();

        updateChecksumForProject(project, crc);

        // Update checksum with referenced projects checksums.
        try {
            boolean wantXpdNatureOnly = false;
            ArrayList<IProject> referencedProjects =
                    new ArrayList<IProject>(
                            ProjectUtil2
                                    .getReferencedProjectsHierarchy(project,
                                            wantXpdNatureOnly));
            Collections.sort(referencedProjects, new Comparator<IProject>() {
                @Override
                public int compare(IProject p1, IProject p2) {
                    return p1.getName().compareTo(p2.getName());
                }
            });
            for (IProject p : referencedProjects) {
                updateChecksumForProject(p, crc);
            }
            t = System.currentTimeMillis() - start;
            if (Platform.inDebugMode()) {
                System.out
                        .println(String
                                .format("'%1$s' project has %2$d referenced project(s).", project.getName(), referencedProjects.size())); //$NON-NLS-1$
                System.out.println(String
                        .format("Project '%s' checksum time [ms]: %d", //$NON-NLS-1$
                                project.getName(),
                                t));
            }

        } catch (CyclicDependencyException e) {
            LOG.error(e);
        }

        return Long.toString(crc.getValue());
    }

    /**
     * Updates checksum with all project's relevant resources. The checksum will
     * use individual resource's workspace path and modification stamp to update
     * checksum.
     * 
     * @param project
     *            the project for which the crc should be update.
     * @param crc
     *            the checksum object.
     * @throws CoreException
     */
    private static void updateChecksumForProject(final IProject project,
            final CRC32 crc) throws CoreException {
        final List<IResource> relevantResources = getRelevantResources(project);
        for (IResource r : relevantResources) {
            if (r instanceof IFile) {
                IFile file = (IFile) r;
                if (file.isAccessible()) {
                    long modificationStamp = file.getModificationStamp();
                    // Update CRC for a file's workspace path.
                    crc.update(file.getFullPath().toPortableString().getBytes());
                    // ...and modification stamp.
                    crc.update(Long.toString(modificationStamp).getBytes());
                } else {
                    String msg =
                            String.format("File is not available for checksum: %1$s", //$NON-NLS-1$
                                    file.getFullPath());
                    throw new CoreException(new Status(IStatus.ERROR,
                            DaaActivator.PLUGIN_ID, msg));
                }
            } else if (r instanceof IFolder) {
                crc.update(r.getFullPath().toPortableString().getBytes());
            }
        }
    }

    /**
     * 
     * @param file
     * @return checksum long value converted to String.
     * @throws CoreException
     */
    private static String calculateContentChecksum(IFile file)
            throws CoreException {
        if (file != null && file.isAccessible()) {
            long start = 0, t = 0;
            start = System.currentTimeMillis();

            final CRC32 crc = new CRC32();
            updateCrcForFile(crc, file);

            t = System.currentTimeMillis() - start;
            if (Platform.inDebugMode()) {
                System.out.println(String
                        .format("File '%s' checksum time [ms]: %d", //$NON-NLS-1$
                                file.getName(),
                                t));
            }
            return Long.toString(crc.getValue());
        }
        return null;
    }

    /**
     * Updates CRC for a file.
     * 
     * @param crc
     *            the CRC to update.
     * @param file
     *            IFile.
     * @throws CoreException
     */
    private static void updateCrcForFile(final CRC32 crc, IFile file)
            throws CoreException {
        final byte[] dataBytes = new byte[1024];
        try {
            InputStream is = file.getContents(true);
            int nread = 0;
            while ((nread = is.read(dataBytes)) != -1) {
                crc.update(dataBytes, 0, nread);
            }
            is.close();
        } catch (IOException e) {
            String msg =
                    String.format("Problem while calculating checksum for: %1$s", //$NON-NLS-1$
                            file.getFullPath());
            throw new CoreException(new Status(IStatus.ERROR,
                    DaaActivator.PLUGIN_ID, msg, e));
        }
    }

    /**
     * Gets the list of resources for the project which shoud be used for
     * checksum.
     * 
     * @param project
     *            the context project.
     * @return
     */
    private static List<IResource> getRelevantResources(IProject project)
            throws CoreException {
        final List<IResource> resources = new ArrayList<IResource>();
        if (project.isAccessible()) {
            project.accept(new IResourceVisitor() {
                @Override
                public boolean visit(IResource r) throws CoreException {
                    if (r instanceof IFolder) {
                        // Exclude all hidden folders (and all resources in
                        // hidden folders).
                        if (r.getName().startsWith(".")) { //$NON-NLS-1$
                            // Prune the tree here.
                            return false;
                        }
                        // Don't add folders.
                        // resources.add(r);
                    } else if (r instanceof IFile) {
                        // Always add .project and .config files.
                        if (r.getName().equals(".project") //$NON-NLS-1$
                                || r.getName().equals(".config")) { //$NON-NLS-1$
                            resources.add(r);
                            return false;
                        }

                        // Exclude all (other) hidden files.
                        if (r.getName().startsWith(".")) { //$NON-NLS-1$
                            return false;
                        }
                        // Exclude DAA files.
                        /* Sid XPD-7753, not all files have extensions! */
                        if (r.getFileExtension() != null
                                && r.getFileExtension().equals("daa")) { //$NON-NLS-1$
                            return false;
                        }
                        resources.add(r);
                    }
                    return true;
                }
            });
        }

        // Adding ProjectChannels.channels from project settings if it exists.
        IFile projectChannelsFile =
                project.getFile(".settings/ProjectChannels.channels"); //$NON-NLS-1$
        if (projectChannelsFile.isAccessible()) {
            resources.add(projectChannelsFile);
        }

        return resources;
    }

    /**
     * Update the project preference with the info given.
     * 
     * @param project
     * @param key
     * @param value
     * @throws BackingStoreException
     */
    private static void updatePreference(IProject project, String key,
            String value) throws BackingStoreException {
        ProjectScope scope = new ProjectScope(project);
        if (scope != null) {
            IEclipsePreferences preference =
                    scope.getNode(DaaActivator.PLUGIN_ID);
            if (preference != null) {
                preference.put(key, value);
                preference.flush();
            }
        }
    }

    /**
     * Read the value of the preference from the given project.
     * 
     * @param project
     * @param key
     * @return set value for the given key, <code>null</code> if not set.
     * @throws BackingStoreException
     */
    private static String getPreferenceValue(IProject project, String key)
            throws BackingStoreException {
        ProjectScope scope = new ProjectScope(project);
        if (scope != null) {
            IEclipsePreferences preference =
                    scope.getNode(DaaActivator.PLUGIN_ID);

            if (preference != null) {
                return preference.get(key, null);

            }
        }
        return null;
    }

    /**
     * Represent checksum value for the DAA which is composed of DAA's content
     * checksum and project checksum.
     */
    private static class ChecksumValue {
        private static char separator = '|';

        private String daaChecksum;

        private String projectChecksum;

        public ChecksumValue(String daaChecksum, String projectChecksum) {
            this.daaChecksum = daaChecksum;
            this.projectChecksum = projectChecksum;
        }

        public static ChecksumValue parseString(String checksumValue) {
            if (checksumValue != null) {
                String[] vals = checksumValue.trim().split("\\|"); //$NON-NLS-1$
                if (vals.length == 2) {
                    return new ChecksumValue(vals[0], vals[1]);
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return (new StringBuilder()).append(getDaaChecksum())
                    .append(separator).append(getProjectChecksum()).toString();
        }

        public String getDaaChecksum() {
            return daaChecksum;
        }

        public String getProjectChecksum() {
            return projectChecksum;
        }
    }

}
