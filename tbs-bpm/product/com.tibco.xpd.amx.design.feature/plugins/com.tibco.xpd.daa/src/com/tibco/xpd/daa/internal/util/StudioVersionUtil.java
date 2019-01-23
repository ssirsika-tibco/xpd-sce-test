/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.daa.internal.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

import com.tibco.xpd.daa.internal.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;

/**
 * Utilities for looking up the Studio version and contributing it to a DAA.
 * 
 * @author nwilson
 * @since 27 Mar 2014
 */
public final class StudioVersionUtil {

    public static final Logger LOG = XpdResourcesPlugin.getDefault()
            .getLogger();

    /**
     * Includes the Studio version number in the MANIFEST.MF file of the
     * provided DAA.
     * 
     * @param target
     *            The DAA file.
     * @throws IOException
     *             If there were problems reading or modifying the file.
     * @throws CoreException
     *             If there were problems refreshing the workspace.
     */
    public static void includeVersionFile(IFile target) throws IOException,
            CoreException {
        if ("daa".equalsIgnoreCase(target.getFileExtension())) { //$NON-NLS-1$
            File src = target.getLocation().toFile();
            File temp = new File(src.getParentFile(), "tmp.daa"); //$NON-NLS-1$
            if (temp.exists()) {
                if (!temp.delete()) {
                    throw new IOException(
                            Messages.StudioVersionUtil_FileDeleteFailed);
                }
            }
            src.renameTo(temp);

            ZipFile daa = new ZipFile(temp);
            ZipOutputStream modified =
                    new ZipOutputStream(new FileOutputStream(src));

            // first, copy contents from existing war
            Enumeration<? extends ZipEntry> entries = daa.entries();
            while (entries.hasMoreElements()) {
                ZipEntry e = entries.nextElement();
                if (e.isDirectory()) {
                    modified.putNextEntry(e);
                } else {
                    if (e.getName().endsWith("/META-INF/MANIFEST.MF")) { //$NON-NLS-1$
                        // Modified file requires a new ZipEntry.
                        ZipEntry ze = new ZipEntry(e.getName());
                        modified.putNextEntry(ze);
                        Manifest mf = new Manifest(daa.getInputStream(e));
                        mf.getMainAttributes().putValue("Studio-Version", //$NON-NLS-1$
                                getProductVersion());
                        mf.write(modified);
                    } else {
                        modified.putNextEntry(e);
                        copy(daa.getInputStream(e), modified);
                    }
                }
                modified.closeEntry();
            }

            // close
            daa.close();
            modified.close();
            temp.delete();
            target.getParent().refreshLocal(IResource.DEPTH_ONE, null);
        }
    }

    /**
     * Retrieves the Studio product version string.
     * 
     * @return The Studio product version.
     */
    private static String getProductVersion() {
        String version = null;
        // Backup to support development environment without features.
        Bundle brandingBundle = Platform.getBundle("com.tibco.xpd.branding"); //$NON-NLS-1$
        version = getVersionString(brandingBundle);
        if (version == null) {
            version = "unknown (probably development)"; //$NON-NLS-1$
        }
        return version;
    }

    /**
     * Safely extracts the version string from a bundle.
     * 
     * @param bundle
     *            The bundle.
     * @return The bundle version.
     */
    private static String getVersionString(Bundle bundle) {
        String version = null;
        if (bundle != null) {
            URL url = bundle.getResource("about.mappings"); //$NON-NLS-1$
            if (url != null) {
                InputStream is = null;
                try {
                    is = url.openStream();
                    Properties properties = new Properties();
                    properties.load(is);
                    version = properties.getProperty("2"); //$NON-NLS-1$
                } catch (IOException e) {
                    LOG.error(e);
                } finally {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            LOG.error(e);
                        }
                    }
                }

            }
        }
        return version;
    }

    private static void copy(InputStream input, OutputStream output)
            throws IOException {
        int bytesRead;
        byte[] buffer = new byte[1024];
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }

}
