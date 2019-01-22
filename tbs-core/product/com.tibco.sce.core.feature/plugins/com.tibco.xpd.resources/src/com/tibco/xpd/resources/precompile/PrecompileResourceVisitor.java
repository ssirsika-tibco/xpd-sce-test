/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.resources.precompile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Resource visitor for a pre-compile project that generates a hash map of
 * source resources (that are enabled for pre-compile) project relative path and
 * check sum as key value pairs.
 * 
 * @author bharge
 * @since 18 May 2015
 */
public class PrecompileResourceVisitor implements IResourceVisitor {

    private Map<String, String> sourceResChecksumMap =
            new HashMap<String, String>();

    /**
     * @return the sourceResChecksum
     */
    public Map<String, String> getSourceResChecksumMap() {

        return sourceResChecksumMap;
    }

    /**
     * @see org.eclipse.core.resources.IResourceVisitor#visit(org.eclipse.core.resources.IResource)
     * 
     * @param resource
     * @return
     * @throws CoreException
     */
    @Override
    public boolean visit(IResource resource) throws CoreException {

        /*
         * Check if the given resource is a source resource enabled for
         * pre-compile
         */
        boolean resEnabledForPrecompile =
                PreCompileContributorManager.getInstance()
                        .isPrecompiledSourceArtefact(resource);
        if (resEnabledForPrecompile && resource instanceof IFile) {

            IPath srcResFullPath = resource.getFullPath();
            /* Calculate the check sum on the given source resource */
            String checksum = getChecksum(resource);

            /*
             * Update the map with source resource's workspace relative path and
             * check sum as key value pair
             */
            sourceResChecksumMap.put(srcResFullPath.toString(), checksum);
        }
        return true;
    }

    /**
     * Calculates and returns the check sum value for the given
     * {@link IResource}
     * 
     * @param resource
     * @return {@link String} representing the check sum value on the given
     *         {@link IResource}
     */
    private String getChecksum(IResource resource) {

        String checksum = null;
        if (resource instanceof IFile) {

            IPath location = resource.getLocation();
            File ioFile = location.toFile();
            if (null != ioFile && ioFile.exists()) {

                FileInputStream inputStream;
                try {

                    inputStream = new FileInputStream(ioFile);
                    CheckedInputStream check =
                            new CheckedInputStream(inputStream, new CRC32());
                    BufferedInputStream in = new BufferedInputStream(check);
                    while (in.read() != -1) {
                        /* Read input file completely */
                    }
                    checksum = check.getChecksum().getValue() + ""; //$NON-NLS-1$
                    in.close();
                    inputStream.close();
                } catch (FileNotFoundException e) {

                    XpdResourcesPlugin.getDefault().getLogger()
                            .error(e.getMessage());
                } catch (IOException ioe) {

                    XpdResourcesPlugin.getDefault().getLogger()
                            .error(ioe.getMessage());
                }
            }
        }
        return checksum;
    }

}
