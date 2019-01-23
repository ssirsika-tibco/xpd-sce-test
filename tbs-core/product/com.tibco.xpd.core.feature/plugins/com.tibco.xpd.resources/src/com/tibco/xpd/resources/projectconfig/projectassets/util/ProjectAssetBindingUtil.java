/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.resources.projectconfig.projectassets.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Util class for the New Project Wizard Asset binding extension point.
 * 
 * @author njpatel
 */
public final class ProjectAssetBindingUtil {

    private static final String PROJECT_ASSET_BINDING_EXTENSIONPOINT = "projectAssetBinding"; //$NON-NLS-1$

    /**
     * Information about the asset binding.
     * 
     * @author njpatel
     * @since 3.2
     * 
     */
    public interface IAssetBinding {
        /**
         * Get the asset id
         * 
         * @return
         */
        String getAssetId();

        /**
         * Check if this asset is mandatory.
         * 
         * @return
         */
        boolean isMandatory();
    }

    /**
     * New project wizard asset binding extension element.
     * 
     * @author njpatel
     */
    private static class AssetBinding implements IAssetBinding {
        private static final String ATT_PROJID = "newProjectWizardId"; //$NON-NLS-1$

        private static final String ATT_ASSETID = "assetId"; //$NON-NLS-1$

        private static final String ATT_MANDATORY = "mandatory"; //$NON-NLS-1$

        private final IConfigurationElement config;

        /**
         * Constructor.
         * 
         * @param config
         */
        public AssetBinding(IConfigurationElement config) {
            this.config = config;
        }

        /**
         * Get the New Project Wizard ID.
         * 
         * @return Wizard ID.
         */
        public String getNewProjectWizardId() {
            return getAttribute(ATT_PROJID);
        }

        /*
         * (non-Javadoc)
         * 
         * @seecom.tibco.xpd.resources.projectconfig.projectassets.util.
         * ProjectAssetBindingUtil.IAssetBinding#getAssetId()
         */
        public String getAssetId() {
            return getAttribute(ATT_ASSETID);
        }

        /*
         * (non-Javadoc)
         * 
         * @seecom.tibco.xpd.resources.projectconfig.projectassets.util.
         * ProjectAssetBindingUtil.IAssetBinding#isMandatory()
         */
        public boolean isMandatory() {
            String value = getAttribute(ATT_MANDATORY);
            if (value != null) {
                return Boolean.parseBoolean(value);
            }

            return false;
        }

        /**
         * Get the value of the given attribute.
         * 
         * @param attribute
         * @return value of the attribute if set, empty string otherwise.
         */
        private String getAttribute(String attribute) {
            return config != null ? config.getAttribute(attribute) : ""; //$NON-NLS-1$
        }
    }

    /**
     * Get the asset id that are bound to the given New Project Wizard.
     * 
     * @param newProjectWizardId
     * @return Array of asset ids. Empty array if no assets are bound to the
     *         given project.
     */
    public static String[] getAssetIdsForNewProjectWizard(
            String newProjectWizardId) {
        String[] assetIds = null;

        if (newProjectWizardId != null) {
            List<AssetBinding> bindings = getBindings();

            if (bindings != null) {
                List<String> assetIdList = new ArrayList<String>();

                for (AssetBinding binding : bindings) {
                    if (newProjectWizardId.equals(binding
                            .getNewProjectWizardId())) {
                        String assetId = binding.getAssetId();

                        // Add the asset id to list if not already done so
                        if (!assetIdList.contains(assetId)) {
                            assetIdList.add(binding.getAssetId());
                        }
                    }
                }

                assetIds = assetIdList.toArray(new String[assetIdList.size()]);
            }
        } else {
            throw new NullPointerException("New project wizard id is null."); //$NON-NLS-1$
        }

        return assetIds != null ? assetIds : new String[0];
    }

    /**
     * Get assets bound to the given new project wizard id. This method gives
     * additional information on the mandatory status of the assets.
     * 
     * @param newProjectWizardId
     * @return list of {@link IAssetBinding}s, empty list if none bound to this
     *         new project wizard id.
     * @since 3.2
     */
    public static List<IAssetBinding> getAssetBindings(String newProjectWizardId) {
        List<AssetBinding> bindings = getBindings();
        List<IAssetBinding> ret = new ArrayList<IAssetBinding>();
        if (bindings != null) {
            for (AssetBinding binding : bindings) {
                if (binding.getNewProjectWizardId().equals(newProjectWizardId)) {
                    ret.add(binding);
                }
            }
        }
        return ret;
    }

    /**
     * Get the asset binding extensions.
     * 
     * @return
     */
    private static List<AssetBinding> getBindings() {
        List<AssetBinding> bindindsList = new ArrayList<AssetBinding>();

        IExtensionPoint point = Platform.getExtensionRegistry()
                .getExtensionPoint(XpdResourcesPlugin.ID_PLUGIN,
                        PROJECT_ASSET_BINDING_EXTENSIONPOINT);

        if (point != null) {
            IExtension[] extensions = point.getExtensions();

            if (extensions != null) {
                for (IExtension ext : extensions) {
                    // For each extension get the configuration elements
                    IConfigurationElement[] elements = ext
                            .getConfigurationElements();

                    if (elements != null) {
                        for (IConfigurationElement elem : elements) {
                            bindindsList.add(new AssetBinding(elem));
                        }
                    }
                }
            }
        }

        return bindindsList;
    }
}
