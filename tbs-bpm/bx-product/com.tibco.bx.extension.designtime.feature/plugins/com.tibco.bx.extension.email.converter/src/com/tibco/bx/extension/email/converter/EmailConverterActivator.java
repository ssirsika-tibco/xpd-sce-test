package com.tibco.bx.extension.email.converter;

import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class EmailConverterActivator extends AbstractUIPlugin {

    public static final String PLUGIN_ID =
            "com.tibco.bx.extension.email.converter"; //$NON-NLS-1$

    private static EmailConverterActivator plugin =
            new EmailConverterActivator();

    public EmailConverterActivator() {
        plugin = this;
    }

    public static AbstractUIPlugin getDefault() {
        return plugin;
    }

    /**
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#initializeImageRegistry(org.eclipse.jface.resource.ImageRegistry)
     * 
     * @param reg
     */
    @Override
    protected void initializeImageRegistry(ImageRegistry reg) {
        for (String imgPath : IMAGES) {
            reg.put(imgPath, AbstractUIPlugin
                    .imageDescriptorFromPlugin(PLUGIN_ID, imgPath));
        }
    }

    public static final String ICON_SERVER_UNAVAILABLE =
            "icons/serverUnavailable.gif";

    public static final String ICON_AUTHENTIFICATION_ERROR =
            "icons/authentificationError.gif";

    public static final String ICON_EMAIL_TASK = "icons/emailTask.png";

    private static String IMAGES[] =
            { ICON_SERVER_UNAVAILABLE, ICON_AUTHENTIFICATION_ERROR,
                    ICON_EMAIL_TASK };

}
