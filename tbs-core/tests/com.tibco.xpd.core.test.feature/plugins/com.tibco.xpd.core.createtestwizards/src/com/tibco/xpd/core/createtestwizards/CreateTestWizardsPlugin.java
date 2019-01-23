package com.tibco.xpd.core.createtestwizards;

import java.lang.reflect.Field;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class CreateTestWizardsPlugin extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID =
            "com.tibco.xpd.core.createtestwizards"; //$NON-NLS-1$

    // The shared instance
    private static CreateTestWizardsPlugin plugin;

    /**
     * The constructor
     */
    public CreateTestWizardsPlugin() {
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
    public static CreateTestWizardsPlugin getDefault() {
        return plugin;
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

    @Override
    protected void initializeImageRegistry(ImageRegistry reg) {

        CreateTestWizardsConstants constClass =
                new CreateTestWizardsConstants();

        Field[] fields = CreateTestWizardsConstants.class.getFields();
        if (fields != null) {
            for (Field field : fields) {
                if (field.getName().startsWith("IMG")
                        || field.getName().startsWith("ICON")) {
                    if (field.getType() == String.class) {
                        String imagePath;
                        try {
                            imagePath = (String) field.get(constClass);
                            reg.put(imagePath, getImageDescriptor(imagePath));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        return;
    }
}
