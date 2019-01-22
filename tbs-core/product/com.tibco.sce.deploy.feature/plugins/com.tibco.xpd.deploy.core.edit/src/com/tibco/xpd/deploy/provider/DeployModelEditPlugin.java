/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.deploy.provider;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.EMFPlugin;

import org.eclipse.emf.common.util.ResourceLocator;

/**
 * This is the central singleton for the Deploy Model edit plugin. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
public final class DeployModelEditPlugin extends EMFPlugin {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.";

    /**
     * Keep track of the singleton. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     * 
     * @generated
     */
    public static final DeployModelEditPlugin INSTANCE = new DeployModelEditPlugin();

    /**
     * Keep track of the singleton. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     * 
     * @generated
     */
    private static Implementation plugin;

    /**
     * Create the instance.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public DeployModelEditPlugin() {
        super(new ResourceLocator[] {});
    }

    /**
     * Returns the singleton instance of the Eclipse plugin.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @return the singleton instance.
     * @generated
     */
    @Override
    public ResourceLocator getPluginResourceLocator() {
        return plugin;
    }

    /**
     * Returns the singleton instance of the Eclipse plugin.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @return the singleton instance.
     * @generated
     */
    public static Implementation getPlugin() {
        return plugin;
    }

    /**
     * The actual implementation of the Eclipse <b>Plugin</b>. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static class Implementation extends EclipsePlugin {
        private static String PLUGIN_ID = "com.tibco.xpd.deploy.core.edit"; //$NON-NLS-1$

        /**
         * Creates an instance.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        public Implementation() {
            super();

            // Remember the static instance.
            //
            plugin = this;
        }

        @Override
        protected Object doGetImage(String key) throws IOException {
            try {
                URL url = new URL(getBaseURL() + "icons/" + key + ".png");
                InputStream inputStream = url.openStream();
                inputStream.close();
                return url;
            } catch (Exception e) {
                URL url = new URL(getBaseURL() + "icons/" + key + ".gif");
                InputStream inputStream = url.openStream();
                inputStream.close();
                return url;
            }
        }

        public void logWarning(String message, Exception e) {
            log(new Status(IStatus.WARNING, PLUGIN_ID, 0, message, e));
        }

        public void logError(String message, Exception e) {
            log(new Status(IStatus.ERROR, PLUGIN_ID, 0, message, e));
        }
    }

}
