/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.openspacegwtgadget.integration.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Manifest;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.pde.internal.core.project.PDEProject;

import com.tibco.xpd.openspacegwtgadget.integration.OpenspaceGadgetPlugin;
import com.tibco.xpd.openspacegwtgadget.integration.internal.Messages;

/**
 * Configures a project manifest for openspace gwt gadget development
 * 
 * @author aallway
 * @since 7 Dec 2012
 */
public class OpenspaceGadgetManifestConfigurator {

    private static final String MF_REQUIRE_BUNDLE = "Require-Bundle"; //$NON-NLS-1$

    private static final String MF_BUNDLE_VERSION = "bundle-version"; //$NON-NLS-1$

    /**
     * Set of required plugins to add to project manifest
     */
    private static ManifestRequiredPlugin[] OPENSPACEGADGET_REQUIRED_PLUGINS =
            {
                    new ManifestRequiredPlugin(
                            "com.tibco.bpm.web.client.model", "", ""), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                    new ManifestRequiredPlugin(
                            "com.tibco.bpm.web.client.services", "", ""), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                    new ManifestRequiredPlugin(
                            "com.tibco.tpcl.gwt.dispatch", "", ""), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                    new ManifestRequiredPlugin(
                            "com.tibco.tpcl.gwt.gadgets", "", ""), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                    new ManifestRequiredPlugin(
                            "com.tibco.tpcl.jsonmaker", "", ""), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
            };

    /**
     * Add plugin dependencies to target project.
     * 
     * @param project
     * @throws CoreException
     */
    @SuppressWarnings("restriction")
    public void addPluginDependencies(IProject project) throws CoreException {

        /* Get the manifest. */
        IFile manifestFile = PDEProject.getManifest(project);
        Manifest manifest = getManifest(manifestFile);

        /* Add missing required bundles (plugin dependencies) */
        addRequiredBundles(manifest);

        /* Write the manifest back again. */
        saveManifest(manifestFile, manifest);

    }

    /**
     * Add plugin dependencies to manifest.
     * 
     * @param manifest
     */
    private void addRequiredBundles(Manifest manifest) {

        /* Get the current set of required plugins. */
        List<ManifestRequiredPlugin> existingPlugins =
                getExistingRequiredPlugins(manifest);

        /*
         * Create a new set with any missing plugins that openspace gadget
         * project requires added
         */
        List<ManifestRequiredPlugin> newPluginSet =
                new ArrayList<ManifestRequiredPlugin>(existingPlugins);

        for (ManifestRequiredPlugin extraPlugin : OPENSPACEGADGET_REQUIRED_PLUGINS) {
            boolean exists = false;

            for (ManifestRequiredPlugin existingPlugin : existingPlugins) {
                if (existingPlugin.pluginId.equals(existingPlugin.pluginId)) {
                    exists = true;
                    break;
                }
            }

            if (!exists) {
                newPluginSet.add(extraPlugin);
            }
        }

        /* Rebuild the required-bundles attribute value. */
        StringBuilder newRequireBundlesValue = new StringBuilder();
        boolean first = true;
        for (ManifestRequiredPlugin plugin : newPluginSet) {
            if (!first) {
                newRequireBundlesValue.append(","); //$NON-NLS-1$
                newRequireBundlesValue.append(plugin.toString());
            } else {
                newRequireBundlesValue.append(plugin.toString());
                first = false;
            }

        }

        /* Set it back into manifest. */
        manifest.getMainAttributes().putValue(MF_REQUIRE_BUNDLE,
                newRequireBundlesValue.toString());

    }

    /**
     * Save the manifest back to file.
     * 
     * @param manifestFile
     * @param manifest
     */
    private void saveManifest(IFile manifestFile, Manifest manifest) {
        ByteArrayOutputStream outStream = null;
        ByteArrayInputStream inputStream = null;
        try {
            outStream = new ByteArrayOutputStream();
            manifest.write(outStream);

            inputStream = new ByteArrayInputStream(outStream.toByteArray());

            manifestFile.setContents(inputStream,
                    IResource.FORCE,
                    new NullProgressMonitor());

        } catch (Exception e) {

        } finally {
            try {
                if (outStream != null) {
                    outStream.close();
                }

                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
            }
        }
    }

    /**
     * @param manifest
     * 
     * @return list of existing required-plugin definitions in the manifest.
     */
    private List<ManifestRequiredPlugin> getExistingRequiredPlugins(
            Manifest manifest) {
        List<ManifestRequiredPlugin> existingPlugins =
                new ArrayList<ManifestRequiredPlugin>();

        String requireBundle =
                manifest.getMainAttributes().getValue(MF_REQUIRE_BUNDLE);

        if (requireBundle != null && requireBundle.length() > 0) {

            requireBundle = requireBundle.replaceAll("[ \t\r\n].", ""); //$NON-NLS-1$ //$NON-NLS-2$

            String[] currentPlugins = requireBundle.split(","); //$NON-NLS-1$

            if (currentPlugins != null) {
                for (String plugin : currentPlugins) {
                    existingPlugins.add(new ManifestRequiredPlugin(plugin));
                }
            }
        }
        return existingPlugins;
    }

    /**
     * @param manifestFile
     * @return Manifest descriptor for the give manifest file.
     * @throws CoreException
     */
    private Manifest getManifest(IFile manifestFile) throws CoreException {

        if (manifestFile != null && manifestFile.exists()) {
            InputStream manifestStream = null;
            try {
                manifestStream = manifestFile.getContents();

                Manifest manifest = new Manifest(manifestStream);
                if (manifest != null) {
                    return manifest;
                }

            } catch (Exception e) {
                OpenspaceGadgetPlugin.getDefault().getLogger()
                        .error(e, "Error accessing plugin manifest: " //$NON-NLS-1$
                                + manifestFile.getFullPath().toString());

                throw new CoreException(
                        new Status(
                                IStatus.ERROR,
                                OpenspaceGadgetPlugin.PLUGIN_ID,
                                String.format(Messages.OpenspaceGadgetManifestConfigurator_ErrorAddingPluginDependencies_message,
                                        manifestFile.getFullPath().toString()),
                                e));
            } finally {
                if (manifestStream != null) {
                    try {
                        manifestStream.close();
                    } catch (IOException e) {
                    }
                }
            }
        }

        throw new CoreException(
                new Status(
                        IStatus.ERROR,
                        OpenspaceGadgetPlugin.PLUGIN_ID,
                        String.format(Messages.OpenspaceGadgetManifestConfigurator_ErrorAddingPluginDependencies2_message,
                                manifestFile.getFullPath().toString())));
    }

    /**
     * Little data class for static definition of required plugin dependencies
     * for Openspace GWT gadget projects.
     * 
     * @author aallway
     * @since 7 Dec 2012
     */
    private static class ManifestRequiredPlugin {
        public final String pluginId;

        public final String bundleVersionAndOptionsSpec;

        public ManifestRequiredPlugin(String pluginId, String minimumVersion,
                String maximumVersion) {
            super();
            this.pluginId = pluginId;

            if (minimumVersion.length() > 0 && maximumVersion.length() > 0) {
                bundleVersionAndOptionsSpec = MF_BUNDLE_VERSION + "=\"[" + //$NON-NLS-1$
                        minimumVersion + "," + //$NON-NLS-1$
                        maximumVersion + ")\""; //$NON-NLS-1$

            } else if (minimumVersion.length() > 0) {
                bundleVersionAndOptionsSpec = MF_BUNDLE_VERSION + "=\"" + //$NON-NLS-1$
                        minimumVersion + "\""; //$NON-NLS-1$

            } else {
                bundleVersionAndOptionsSpec = ""; //$NON-NLS-1$
            }
        }

        public ManifestRequiredPlugin(String requiredPluginSpec) {
            String[] elements = requiredPluginSpec.split(";", 2); //$NON-NLS-1$
            pluginId = elements[0];

            if (elements.length > 1 && elements[1] != null) {
                bundleVersionAndOptionsSpec = elements[1];
            } else {
                bundleVersionAndOptionsSpec = ""; //$NON-NLS-1$
            }
        }

        /**
         * @see java.lang.Object#toString()
         * 
         * @return
         */
        @Override
        public String toString() {
            if (bundleVersionAndOptionsSpec.length() > 0) {
                return pluginId + ";" + bundleVersionAndOptionsSpec; //$NON-NLS-1$
            }
            return pluginId;
        }
    }
}
