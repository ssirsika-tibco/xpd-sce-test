/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.resources.migrateproject.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Utility class that manages the migrate project extension point.
 * <p>
 * Use method <code>{@link #createInstance()}</code> to create an instance of
 * this class.
 * </p>
 * 
 * @author njpatel
 */
public class MigrateProjectManager {

    private static final String MIGRATEPROJECT_EXT = "migrateProject"; //$NON-NLS-1$

    private List<MigrateProjectElem> extensions = new ArrayList<MigrateProjectElem>();

    /**
     * Default constructor.
     */
    private MigrateProjectManager() {
        IExtensionPoint point = Platform.getExtensionRegistry()
                .getExtensionPoint(XpdResourcesPlugin.ID_PLUGIN,
                        MIGRATEPROJECT_EXT);

        if (point != null) {
            // Get all extension of the migrate project extension point
            IExtension[] extensions = point.getExtensions();

            if (extensions != null) {
                for (IExtension ext : extensions) {
                    IConfigurationElement[] elements = ext
                            .getConfigurationElements();

                    if (elements != null) {
                        for (IConfigurationElement elem : elements) {
                            this.extensions.add(new MigrateProjectElem(elem));
                        }
                    }
                }
            }
        }
    }

    /**
     * Create an instance of this class.
     * 
     * @return Instance of this class.
     */
    public static MigrateProjectManager createInstance() {
        return new MigrateProjectManager();
    }

    /**
     * Get all extensions of the migrate project extension point.
     * 
     * @return Array of wrappers of the extensions.
     */
    public MigrateProjectElem[] getExtensions() {
        return extensions.toArray(new MigrateProjectElem[extensions.size()]);
    }

    /**
     * Get the extension with the given <i>id</i>.
     * 
     * @param id
     *            ID of the extension.
     * @return Wrapper of the extension.
     */
    public MigrateProjectElem getExtensionById(String id) {
        MigrateProjectElem elem = null;

        if (id != null) {
            for (MigrateProjectElem ext : extensions) {
                if (ext.getId().equals(id)) {
                    elem = ext;
                    break;
                }
            }
        }

        return elem;
    }

}
