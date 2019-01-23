/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.scripteditors;

import java.io.IOException;

import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.text.templates.ContextTypeRegistry;
import org.eclipse.jface.text.templates.persistence.TemplateStore;
import org.eclipse.ui.editors.text.templates.ContributionContextTypeRegistry;
import org.eclipse.ui.editors.text.templates.ContributionTemplateStore;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;
import com.tibco.xpd.scripteditors.internal.ImageCache;
import com.tibco.xpd.scripteditors.internal.javascript.contentassist.JsContentAssistConstants;
import com.tibco.xpd.scripteditors.internal.xpath.contentassist.XPathContentAssistConstants;

/**
 * The activator class controls the plug-in life cycle
 * 
 * @author rsomayaj
 * 
 */
public class ScriptEditorsPlugin extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "com.tibco.xpd.scripteditors"; //$NON-NLS-1$

    // The shared instance
    private static ScriptEditorsPlugin plugin;

    /** logger instance. */
    private final Logger logger = LoggerFactory.createLogger(PLUGIN_ID);

    /**
     * The constructor
     */
    public ScriptEditorsPlugin() {
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
        imageCache = new ImageCache();
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
        imageCache.dispose();
        super.stop(context);
    }

    /**
     * Returns the shared instance
     * 
     * @return the shared instance
     */
    public static ScriptEditorsPlugin getDefault() {
        return plugin;
    }

    private TemplateStore javaScriptTemplateStore;

    public TemplateStore getJavaScriptTemplateStore() {
        if (javaScriptTemplateStore == null) {
            javaScriptTemplateStore =
                    new ContributionTemplateStore(getJSContextTypeRegistry(),
                            getDefault().getPreferenceStore(),
                            JsContentAssistConstants.JS_TEMPLATE);
            try {
                javaScriptTemplateStore.load();
            } catch (IOException e) {
                getDefault().getLog().log(new Status(4,
                        ScriptEditorsPlugin.PLUGIN_ID, 0, "", e)); //$NON-NLS-1$
            }
        }
        return javaScriptTemplateStore;
    }

    private TemplateStore xpathTemplateStore;

    public TemplateStore getXPathTemplateStore() {
        if (xpathTemplateStore == null) {
            xpathTemplateStore =
                    new ContributionTemplateStore(
                            getXPathContextTypeRegistry(), getDefault()
                                    .getPreferenceStore(),
                            XPathContentAssistConstants.XPATH_TEMPLATE);
            try {
                xpathTemplateStore.load();
            } catch (IOException e) {
                getDefault().getLog().log(new Status(4,
                        ScriptEditorsPlugin.PLUGIN_ID, 0, "", e)); //$NON-NLS-1$
            }
        }
        return xpathTemplateStore;
    }

    private ContributionContextTypeRegistry fJSRegistry;

    public ContextTypeRegistry getJSContextTypeRegistry() {
        if (fJSRegistry == null) {
            fJSRegistry = new ContributionContextTypeRegistry();
            fJSRegistry
                    .addContextType(JsContentAssistConstants.JS_TEMPLATE_CONTEXT);
        }
        return fJSRegistry;
    }

    private ContributionContextTypeRegistry fXPathRegistry;

    public ContextTypeRegistry getXPathContextTypeRegistry() {
        if (fXPathRegistry == null) {
            fXPathRegistry = new ContributionContextTypeRegistry();
            fXPathRegistry
                    .addContextType(XPathContentAssistConstants.XPATH_TEMPLATE_CONTEXT);
        }
        return fXPathRegistry;
    }

    private static ImageCache imageCache;

    public ImageCache getImageCache() {
        return imageCache;
    }

    @Override
    protected void initializeImageRegistry(ImageRegistry reg) {
        String[] imgs = JsContentAssistConstants.IMAGES;
        for (int i = 0; i < imgs.length; i++) {
            reg.put(imgs[i], getImageDescriptor(imgs[i]));
        }
        imgs = XPathContentAssistConstants.IMAGES;
        for (int i = 0; i < imgs.length; i++) {
            reg.put(imgs[i], getImageDescriptor(imgs[i]));
        }
    }

    /**
     * Returns an image descriptor for the image file at the given plug-in
     * relative path.
     * 
     * @param path
     *            the path
     * @return the image descriptor
     */
    public static ImageDescriptor getImageDescriptor(String path) {
        return AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID, path);
    }

    /**
     * @return the logger
     */
    public Logger getLogger() {
        return logger;
    }
}
