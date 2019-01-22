/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.resources.projectconfig.projectassets;

/**
 * Interface used by the project asset extension point. The implementation of
 * this interface will be called during the creation of the new XPD project to
 * configure the asset type. The asset configuration object will come from the
 * associated wizard pages of this asset type.
 * <p>
 * If no access is required to configuration objects of other asset types then
 * <code>{@link IAssetConfigurator}</code> should be used instead.
 * </p>
 * 
 * @see IAssetConfigurator
 * 
 * @author njpatel
 * 
 */
public interface IAssetConfigurator2 extends IAssetConfigurator {

    /**
     * Set the asset configuration provider. This provider can be used to access
     * configuration objects of other selected assets.
     * 
     * @param provider asset configuration provider
     */
    public void setAssetConfigurationProvider(
            IAssetConfigurationProvider provider);
}
