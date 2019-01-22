package com.tibco.xpd.processwidget;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

/**
 * The main plugin class to be used in the desktop.
 */
public class ProcessWidgetPlugin extends AbstractUIPlugin {

    public static final String ID = "com.tibco.xpd.processwidget"; //$NON-NLS-1$

    // The shared instance.
    private static ProcessWidgetPlugin plugin;

    private Map<RGB, Color> colors = new HashMap<RGB, Color>();

    /**
     * Plugin's logger object
     */
    private Logger logger;

    /**
     * The constructor.
     */
    public ProcessWidgetPlugin() {
        plugin = this;
    }

    /**
     * This method is called upon plug-in activation
     */
    @Override
    public void start(BundleContext context) throws Exception {
        plugin = this;
        super.start(context);
    }

    /**
     * This method is called when the plug-in is stopped
     */
    @Override
    public void stop(BundleContext context) throws Exception {
        super.stop(context);
        plugin = null;
        for (Iterator iter = colors.values().iterator(); iter.hasNext();) {
            Color c = (Color) iter.next();
            c.dispose();
        }
    }

    /**
     * Returns the shared instance.
     */
    public static ProcessWidgetPlugin getDefault() {
        return plugin;
    }

    @Override
    protected void initializeImageRegistry(ImageRegistry reg) {
        String[] imgs = ProcessWidgetConstants.IMAGES;
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
        ImageDescriptor desc =
                AbstractUIPlugin
                        .imageDescriptorFromPlugin("com.tibco.xpd.processwidget", path); //$NON-NLS-1$
        if (desc == null) {
            System.err.println("Invalid image in process widget: " + path); //$NON-NLS-1$
        }
        return desc;
    }

    public Color getColor(RGB rgb) {
        Color result = colors.get(rgb);

        if (result != null && result.isDisposed()) {
            // Ooops, callers should not dispose this, but just in case
            // recreate colour
            colors.remove(rgb);
            result = null;
        }

        if (result == null) {
            if (PlatformUI.isWorkbenchRunning()) {
                result = new Color(PlatformUI.getWorkbench().getDisplay(), rgb);
            } else {
                result = new Color(Display.getDefault(), rgb);
            }
            colors.put(rgb, result);
        }

        return result;
    }

    public Logger getLogger() {
        if (logger == null) {
            logger = LoggerFactory.createLogger(ID);
        }
        return logger;
    }

}
