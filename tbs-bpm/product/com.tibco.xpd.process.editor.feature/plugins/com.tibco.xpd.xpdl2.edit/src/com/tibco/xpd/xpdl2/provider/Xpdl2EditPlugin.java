/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.provider;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.MissingResourceException;

import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.EMFPlugin;

import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.common.util.WrappedException;

/**
 * This is the central singleton for the Xpdl2 edit plugin.
 * <!-- begin-user-doc
 * --> <!-- end-user-doc -->
 * @generated
 */
public final class Xpdl2EditPlugin extends EMFPlugin {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * @generated NOT
     */
    public static final String PLUGIN_ID = "com.tibco.xpd.xpdl2.edit"; //$NON-NLS-1$

    /**
     * Keep track of the singleton. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     * 
     * @generated
     */
    public static final Xpdl2EditPlugin INSTANCE = new Xpdl2EditPlugin();

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
    public Xpdl2EditPlugin() {
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
     * TIBCO STUDIO...
     * The base EMF doGetImage() only deals with .gif (ONLY because it appends ".gif" to the end.
     * So if we tried and failed with the .gif then try again with .png
     * Try with png instead.
     * DO NOT REMOVED "NOT" from @generated or you will lose this ability.
     * 
     * @generated NOT
     */
    public static class Implementation extends EclipsePlugin {
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

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.emf.common.EMFPlugin.EclipsePlugin#doGetImage(java.lang.String)
         */
        @Override
        protected Object doGetImage(String key) throws IOException {
            try {
                // The base EMF doGetImage() only deals with .gif (ONLY because it appends ".gif" to the end.
                // So if we tried and failed with the .gif then try again with .png
                // Try with png instead.
                URL url = new URL(getBaseURL() + "icons/" + key + ".png"); //$NON-NLS-1$ //$NON-NLS-2$
                InputStream inputStream = url.openStream();
                inputStream.close();
                return url;
            } catch (MalformedURLException exception) {
                throw new WrappedException(exception);
            } catch (IOException exception) {
                try {
                    // PNG is not available, get gif instead.
                    return super.doGetImage(key);
                } catch (MalformedURLException e2) {
                    throw new WrappedException(e2);
                } catch (IOException e2) {
                    throw new MissingResourceException(
                            CommonPlugin.INSTANCE.getString("_UI_StringResourceNotFound_exception", //$NON-NLS-1$
                                    new Object[] { key }),
                            getClass().getName(), key);
                }
            }

        }
    }

}
