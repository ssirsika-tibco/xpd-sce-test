/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.resources.projectconfig.specialfolders.extpoint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.specialfolders.ISpecialFolderModel;
import com.tibco.xpd.resources.projectconfig.specialfolders.extpoint.SpecialFoldersExtension;

/**
 * Helper class to get the list of extensions that extend the <i>specialFolders</i>
 * extension point. This will define all special folders in a project.
 * <p>
 * NOTE: Use <code>{@link #getInstance()}</code> to get the singleton instance
 * of this class.
 * </p>
 * 
 * @author njpatel
 */
public final class SpecialFoldersExtensionPoint {

    private static final String SPECIALFOLDERS_EXTENSIONPOINT = "specialFolders"; //$NON-NLS-1$

    /**
     * List of extensions of the <i>specialFolders</i> extension point
     */
    private ISpecialFolderModel[] sfExtensions = null;

    /**
     * Singleton instance of <code>{@link SpecialFoldersExtensionPoint}</code>.
     */
    private static final SpecialFoldersExtensionPoint INSTANCE = new SpecialFoldersExtensionPoint();

    /**
     * Get the singleton instance of this helper class.
     * 
     * @return
     */
    public static SpecialFoldersExtensionPoint getInstance() {
        return INSTANCE;
    }

    /**
     * Get all registered extension for this special folder kind extension point
     * 
     * @return Array of <code>ISpecialFolderKindInfo</code> objects that
     *         describe the kind extension.
     */
    public ISpecialFolderModel[] getExtensions() {
        // Get the list of extensions
        if (sfExtensions == null) {
            IExtensionPoint point = Platform.getExtensionRegistry()
                    .getExtensionPoint(XpdResourcesPlugin.ID_PLUGIN,
                            SPECIALFOLDERS_EXTENSIONPOINT);

            if (point != null) {
                // Get all extensions
                IExtension[] extensions = point.getExtensions();

                if (extensions != null) {
                    ArrayList<ISpecialFolderModel> extArray = new ArrayList<ISpecialFolderModel>();

                    for (IExtension ext : extensions) {
                        // For each extension get the configuration elements
                        IConfigurationElement[] elements = ext
                                .getConfigurationElements();

                        if (elements != null) {
                            for (IConfigurationElement elem : elements) {
                                // For each configuration element create a new
                                // ProjectFolderExtension and add to the set
                                extArray.add(new SpecialFoldersExtension(elem));
                            }
                        }
                    }
                    Collections.sort(extArray);
                    sfExtensions = extArray
                            .toArray(new ISpecialFolderModel[extArray.size()]);
                }
            }
        }

        return sfExtensions;
    }

    /**
     * Get the extension of the <i>specialFolders</i> extension point that has
     * the given kind value
     * 
     * @param kind
     * @return <code>ISpecialFolderKindInfo</code> with the given kind,
     *         <b>null</b> if one is not found.
     */
    public ISpecialFolderModel getExtensionByKind(String kind) {
        ISpecialFolderModel ext = null;

        if (kind != null) {
            ISpecialFolderModel[] extensions = getExtensions();

            if (extensions != null) {
                // Find the extension with the given ID
                for (ISpecialFolderModel e : extensions) {
                    if (kind.equals(e.getKind())) {
                        ext = e;
                        break;
                    }
                }
            }
        }

        return ext;
    }

    /**
     * <i>specialFolder</i> extension point helper class.
     */
    private SpecialFoldersExtensionPoint() {
        // Singleton instance class
    }

}
