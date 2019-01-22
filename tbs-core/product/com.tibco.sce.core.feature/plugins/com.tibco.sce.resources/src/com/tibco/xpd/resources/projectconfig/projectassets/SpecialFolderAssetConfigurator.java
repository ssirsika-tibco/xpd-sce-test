/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.resources.projectconfig.projectassets;

import java.util.Hashtable;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;

import com.tibco.xpd.resources.projectconfig.specialfolders.ISpecialFolderModel;
import com.tibco.xpd.resources.projectconfig.specialfolders.extpoint.SpecialFoldersExtensionPoint;

/**
 * Project asset configurator for an asset that wants to set up a special folder
 * (and also wants to associate that special folder with this asset type). If
 * this asset configurator is used then the configuration set to
 * {@link SpecialFolderAssetConfiguration}.
 * <p>
 * The following initialisation data will be required:
 * <ul>
 * <li><strong>kind</strong> - the special folder kind that this asset type will
 * configure.</li>
 * <li><strong>defaultFolder</strong> - (since 3.2) the default name of the
 * folder.</li>
 * </ul>
 * </p>
 * <p>
 * For information on how to provide initialisation data see
 * <code>{@link IExecutableExtension#setInitializationData(IConfigurationElement, String, Object)}</code>
 * .
 * </p>
 * 
 * @see SpecialFolderAssetConfiguration
 * 
 * @author njpatel
 * 
 */
public class SpecialFolderAssetConfigurator extends
        AbstractSpecialFolderAssetConfigurator implements IExecutableExtension {

    private static final String PARAM_KIND = "kind"; //$NON-NLS-1$
    private static final String PARAM_DEFAULTFOLDER = "defaultFolder"; //$NON-NLS-1$
    private String specialFolderKind;
    private String defaultFolderName;

    @Override
    protected String getSpecialFolderKind() {
        if (specialFolderKind == null) {
            throw new NullPointerException("Special Folder kind is null."); //$NON-NLS-1$
        }
        return specialFolderKind;
    }

    @Override
    protected String getSpecialFolderName() {
        SpecialFolderAssetConfiguration config = getSpecialFolderConfig();

        if (config != null && config.getSpecialFolderName() != null
                && config.getSpecialFolderName().length() > 0) {
            return config.getSpecialFolderName();
        }
        return getDefaultFolderName();
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.resources.projectconfig.projectassets.
     * AbstractSpecialFolderAssetConfigurator#getDefaultFolderName()
     */
    public String getDefaultFolderName() {
        return defaultFolderName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.core.runtime.IExecutableExtension#setInitializationData(org
     * .eclipse.core.runtime.IConfigurationElement, java.lang.String,
     * java.lang.Object)
     */
    public void setInitializationData(IConfigurationElement config,
            String propertyName, Object data) throws CoreException {
        if (data instanceof String) {
            String[] values = ((String) data).split("(\\A|\\s)+-"); //$NON-NLS-1$

            for (String value : values) {
                if (value.indexOf(' ') > 0) {
                    String parameter = value.substring(0, value.indexOf(' '));
                    String paramValue = value.substring(value.indexOf(' '))
                            .trim();

                    if (parameter.equals(PARAM_KIND)) {
                        specialFolderKind = paramValue;
                    } else if (parameter.equals(PARAM_DEFAULTFOLDER)) {
                        defaultFolderName = paramValue;
                    }

                }
            }
        } else if (data instanceof Hashtable) {
            Hashtable<?, ?> table = (Hashtable<?, ?>) data;

            specialFolderKind = (String) table.get(PARAM_KIND);
            defaultFolderName = (String) table.get(PARAM_DEFAULTFOLDER);
        }

        if (specialFolderKind == null) {
            throw new NullPointerException(
                    "Kind of special folder in SpecialFolderAssetConfigurator is null."); //$NON-NLS-1$
        } else {
            ISpecialFolderModel extensionByKind = SpecialFoldersExtensionPoint
                    .getInstance().getExtensionByKind(specialFolderKind);
            if (extensionByKind == null) {
                // Special folder of the given kind not found
                throw new IllegalArgumentException(String.format(
                        "Special folder of kind '%s' not found.", //$NON-NLS-1$
                        specialFolderKind));
            }
        }
    }

    /**
     * Get the special folder configuration.
     * 
     * @return
     */
    private SpecialFolderAssetConfiguration getSpecialFolderConfig() {
        Object config = getConfiguration();

        if (config instanceof SpecialFolderAssetConfiguration) {
            return (SpecialFolderAssetConfiguration) config;
        }

        return null;
    }

}
