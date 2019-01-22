package com.tibco.xpd.rql.script;

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

import com.tibco.xpd.rql.script.contentassist.RQLContentAssistConstants;

/**
 * The activator class controls the plug-in life cycle
 */
public class RQLScriptPlugin extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "com.tibco.xpd.rql.script";

    // The shared instance
    private static RQLScriptPlugin plugin;

    /**
     * The constructor
     */
    public RQLScriptPlugin() {
    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
     */
    public void start(BundleContext context) throws Exception {
        super.start(context);
        imageCache = new ImageCache();
        plugin = this;
    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
     */
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
    public static RQLScriptPlugin getDefault() {
        return plugin;
    }

    private TemplateStore rqlTemplateStore;

    public TemplateStore getRQLTemplateStore() {
        if (rqlTemplateStore == null) {
            rqlTemplateStore =
                    new ContributionTemplateStore(
                            getContextTypeRegistry(),
                            getDefault().getPreferenceStore(),
                            com.tibco.xpd.rql.script.contentassist.RQLContentAssistConstants.RQL_TEMPLATE);
            try {
                rqlTemplateStore.load();
            } catch (IOException e) {
                getDefault().getLog().log(new Status(4, PLUGIN_ID, 0, "", e)); //$NON-NLS-1$
            }
        }
        return rqlTemplateStore;
    }

    private ContributionContextTypeRegistry fRegistry;

    public ContextTypeRegistry getContextTypeRegistry() {
        if (fRegistry == null) {
            fRegistry = new ContributionContextTypeRegistry();
            fRegistry
                    .addContextType(RQLContentAssistConstants.RQL_TEMPLATE_CONTEXT);
        }
        return fRegistry;
    }

    private static ImageCache imageCache;

    public ImageCache getImageCache() {
        return imageCache;
    }

    protected void initializeImageRegistry(ImageRegistry reg) {
        String[] imgs = RQLContentAssistConstants.IMAGES;
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

}
