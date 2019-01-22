/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.wsdl;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.jsch.core.IJSchService;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;
import com.tibco.xpd.wsdl.internal.WsdlCopier;

/**
 * The activator class controls the plug-in life cycle.
 */
public class Activator extends Plugin {

    /** The plug-in ID. */
    public static final String PLUGIN_ID = "com.tibco.xpd.wsdl"; //$NON-NLS-1$

    /**
     * WSDL Special folder kind.
     */
    public static final String WSDL_SPECIALFOLDER_KIND = "wsdl"; //$NON-NLS-1$

    /**
     * WSDL File extension
     */
    public static final String WSDL_FILE_EXTENSION = "wsdl"; //$NON-NLS-1$

    /**
     * XSD File extension
     */
    public static final String XSD_FILE_EXTENSION = "xsd"; //$NON-NLS-1$

    /**
     * Wsdl extension namespace prefix
     */
    public static final String TIBEX = "tibex";//$NON-NLS-1$

    /**
     * Wsdl extension namespace
     */
    public static final String TIBEX_URI =
            "http://www.tibco.com/bs3.2/extensions"; //$NON-NLS-1$

    public static final String TIBEX_CONCRETE_FLAG = TIBEX + ":Concrete";

    /** The shared instance. */
    private static Activator plugin;

    private final Logger logger = LoggerFactory.createLogger(PLUGIN_ID);

    private ServiceTracker serviceTrackerForJavaSecureChannel;

    /**
     * The constructor.
     */
    public Activator() {
        plugin = this;
    }

    /**
     * @param context
     *            The bundle context.
     * @throws Exception
     *             if the plugin could not be started.
     * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
     */
    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        serviceTrackerForJavaSecureChannel =
                new ServiceTracker(getBundle().getBundleContext(),
                        IJSchService.class.getName(), null);
        serviceTrackerForJavaSecureChannel.open();

    }

    /**
     * @param context
     *            The bundle context.
     * @throws Exception
     *             if the plugin could not be stopped.
     * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
     */
    @Override
    public void stop(BundleContext context) throws Exception {
        plugin = null;
        serviceTrackerForJavaSecureChannel.close();

        // saveUrlMap();
        super.stop(context);
    }

    /**
     * Returns the shared instance.
     * 
     * @return the shared instance
     */
    public static Activator getDefault() {
        return plugin;
    }

    /**
     * This is a convenience method, please see overload. The keystore file
     * located in the user's home directory will be used
     */
    public void copyWsdl(String wsdlSourceUrl, IFile destinationIFile,
            CertificateAcceptanceTracker certificateAcceptanceTracker)
            throws IOException {
        copyWsdl(wsdlSourceUrl,
                destinationIFile,
                certificateAcceptanceTracker,
                new NullProgressMonitor());
    }

    /**
     * This is a convenience method, please see overload. The keystore file
     * located in the user's home directory will be used
     * 
     * @since 3.3
     */
    public void copyWsdl(String wsdlSourceUrl, IFile destinationIFile,
            CertificateAcceptanceTracker certificateAcceptanceTracker,
            IProgressMonitor monitor) throws IOException {
        WsdlCopier.copy(wsdlSourceUrl,
                destinationIFile,
                getKeyStoreFile(),
                certificateAcceptanceTracker,
                monitor);
    }

    /**
     * Make a local copy of a wsdl and any referenced xsd files.
     * 
     * @param keyStoreFile
     *            A file from which a keyStore can be loaded. If the
     *            wsdlSourceUrl is an https url then this keystore will be used
     *            to determine the validity of the certificate issued by the
     *            server.
     */
    public void copyWsdl(String wsdlSourceUrl, IFile destinationIFile,
            File keyStoreFile,
            CertificateAcceptanceTracker certificateAcceptanceTracker)
            throws IOException {
        copyWsdl(wsdlSourceUrl,
                destinationIFile,
                keyStoreFile,
                certificateAcceptanceTracker,
                new NullProgressMonitor());
    }

    /**
     * Make a local copy of a wsdl and any referenced xsd files.
     * 
     * @param keyStoreFile
     *            A file from which a keyStore can be loaded. If the
     *            wsdlSourceUrl is an https url then this keystore will be used
     *            to determine the validity of the certificate issued by the
     *            server.
     * @since 3.3
     */
    public void copyWsdl(String wsdlSourceUrl, IFile destinationIFile,
            File keyStoreFile,
            CertificateAcceptanceTracker certificateAcceptanceTracker,
            IProgressMonitor monitor) throws IOException {
        WsdlCopier.copy(wsdlSourceUrl,
                destinationIFile,
                keyStoreFile,
                certificateAcceptanceTracker,
                monitor);
    }

    /**
     * This is a convenience method, please see overload. The keystore file
     * located in the user's home directory will be used
     */
    public void copyWsdl(String wsdlSourceUrl, IFile destinationIFile,
            CertificateAcceptanceTracker certificateAcceptanceTracker,
            boolean isOverwriteExistingResources) throws IOException {
        copyWsdl(wsdlSourceUrl,
                destinationIFile,
                certificateAcceptanceTracker,
                isOverwriteExistingResources,
                new NullProgressMonitor());
    }

    /**
     * This is a convenience method, please see overload. The keystore file
     * located in the user's home directory will be used
     * 
     * @since 3.3
     */
    public void copyWsdl(String wsdlSourceUrl, IFile destinationIFile,
            CertificateAcceptanceTracker certificateAcceptanceTracker,
            boolean isOverwriteExistingResources, IProgressMonitor monitor)
            throws IOException {
        WsdlCopier.copy(wsdlSourceUrl,
                destinationIFile,
                getKeyStoreFile(),
                certificateAcceptanceTracker,
                isOverwriteExistingResources,
                monitor);
    }

    /**
     * Make a local copy of a wsdl and any referenced xsd files.
     * 
     * @param keyStoreFile
     *            A file from which a keyStore can be loaded. If the
     *            wsdlSourceUrl is an https url then this keystore will be used
     *            to determine the validity of the certificate issued by the
     *            server.
     */
    public void copyWsdl(String wsdlSourceUrl, IFile destinationIFile,
            File keyStoreFile,
            CertificateAcceptanceTracker certificateAcceptanceTracker,
            boolean isOverwriteExistingResources) throws IOException {
        copyWsdl(wsdlSourceUrl,
                destinationIFile,
                keyStoreFile,
                certificateAcceptanceTracker,
                isOverwriteExistingResources,
                new NullProgressMonitor());
    }

    /**
     * Make a local copy of a wsdl and any referenced xsd files.
     * 
     * @param keyStoreFile
     *            A file from which a keyStore can be loaded. If the
     *            wsdlSourceUrl is an https url then this keystore will be used
     *            to determine the validity of the certificate issued by the
     *            server.
     * @since 3.3
     */
    public void copyWsdl(String wsdlSourceUrl, IFile destinationIFile,
            File keyStoreFile,
            CertificateAcceptanceTracker certificateAcceptanceTracker,
            boolean isOverwriteExistingResources, IProgressMonitor monitor)
            throws IOException {
        WsdlCopier.copy(wsdlSourceUrl,
                destinationIFile,
                keyStoreFile,
                certificateAcceptanceTracker,
                isOverwriteExistingResources,
                monitor);
    }

    /**
     * Perform the copy of a wsdl imported from a file system/url to a Service
     * Descriptors folder.
     * 
     * @param wsdlSourceUrl
     * @param targetFile
     * @param keyStoreFile
     * @param certificateAcceptanceTracker
     * @param isOverwriteExistingResources
     * @param monitor
     * @return {@link WsdlCopyResult} which contains a map of wsdl/xsd files
     *         with their original location, and {@link IStatus} object
     * 
     * @since 3.6 This was requested by SDS team. see SCF-188
     */
    public static WsdlCopyResult copyWithResult(String wsdlSourceUrl,
            final IFile targetFile, final File keyStoreFile,
            final CertificateAcceptanceTracker certificateAcceptanceTracker,
            final boolean isOverwriteExistingResources,
            final IProgressMonitor monitor) {

        return WsdlCopier.copyWithResult(wsdlSourceUrl,
                targetFile,
                keyStoreFile,
                certificateAcceptanceTracker,
                isOverwriteExistingResources,
                monitor);
    }

    public Logger getLogger() {
        return logger;
    }

    public File getKeyStoreFile() {
        final File userHomeDirectory =
                new File(System.getProperty("user.home")); //$NON-NLS-1$
        return new File(userHomeDirectory, "bsKeyStore"); //$NON-NLS-1$
    }

}
