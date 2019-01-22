/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.n2.daa;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.amf.sca.model.extensionpoints.PolicySet;
import com.tibco.amf.sca.model.externalpolicy.ExternalPolicySetContainerDocument;
import com.tibco.xpd.n2.resources.BundleActivator;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "com.tibco.xpd.n2.daa"; //$NON-NLS-1$

    public static final String N2PE_PLUGIN_FEATURE_FILE_EXTENSION = "jar"; //$NON-NLS-1$

    // The shared instance
    private static Activator plugin;

    private final Logger logger = LoggerFactory.createLogger(PLUGIN_ID);

    public static final Logger LOG = BundleActivator.getDefault().getLogger();

    /**
     * The constructor
     */
    public Activator() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
     * )
     */
    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
     * )
     */
    @Override
    public void stop(BundleContext context) throws Exception {
        plugin = null;
        super.stop(context);
    }

    /**
     * Returns the shared instance
     * 
     * @return the shared instance
     */
    public static Activator getDefault() {
        return plugin;
    }

    /**
     * Returns logger for the plug-in.
     * 
     * @return plug-in's logger.
     */
    public Logger getLogger() {
        return logger;
    }

    @Override
    protected void initializeImageRegistry(ImageRegistry reg) {
        String[] imgs = DAAConstants.IMAGES;
        for (int i = 0; i < imgs.length; i++) {
            reg.put(imgs[i], getImageDescriptor(imgs[i]));
        }
    }

    /**
     * Returns an image descriptor for the image file at the given plug-in
     * relative path
     * 
     * @param path
     *            the path
     * @return the image descriptor
     */
    public static ImageDescriptor getImageDescriptor(String path) {
        return imageDescriptorFromPlugin(PLUGIN_ID, path);
    }

    /**
     * Returns the standard display to be used. The method first checks, if the
     * thread calling this method has an associated display. If so, this display
     * is returned. Otherwise the method returns the default display.
     */
    public static Display getStandardDisplay() {
        Display display = Display.getCurrent();
        if (display == null) {
            display = Display.getDefault();
        }
        return display;
    }

    /** */
    private static final String PLATFORM_PLUGIN_RES_URI_PREFIX =
            "platform:/plugin/" + Activator.PLUGIN_ID //$NON-NLS-1$
                    + "/resources/"; //$NON-NLS-1$

    public static final String PREPARE_BEFORE_UNDEPLOY_POLCICYSET =
            "PrepareBeforeUndeploy.policysets";

    public PolicySet getCreatePolicySet(IContainer stagingArea,
            String policyFileName) {
        IFolder policyFolder = ((IFolder) stagingArea).getFolder("resources"); //$NON-NLS-1$
        try {
            if (!policyFolder.exists()) {
                ProjectUtil.createFolder(policyFolder, true, null);
            }
            IFile policyFile = policyFolder.getFile(policyFileName);
            if (!policyFile.exists()) {
                InputStream policyInputStream =
                        new ResourceSetImpl()
                                .getURIConverter()
                                .createInputStream(URI
                                        .createURI(PLATFORM_PLUGIN_RES_URI_PREFIX
                                                + policyFileName));
                // policyFile.create(policyInputStream, IResource.FORCE
                // | IResource.DERIVED, null);
                policyFile.create(policyInputStream, IResource.FORCE, null);
            }
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(policyFile);
            wc.reLoad();
            EObject root = wc.getRootElement();
            if (root instanceof ExternalPolicySetContainerDocument) {
                ExternalPolicySetContainerDocument policyContainerDocument =
                        (ExternalPolicySetContainerDocument) root;
                return policyContainerDocument.getPolicySetContainer()
                        .getEmbeddedPolicySets().get(0);
            }

        } catch (CoreException e) {
            LOG.error(e);
        } catch (IOException e) {
            LOG.error(e);
        }
        return null;
    }
}
