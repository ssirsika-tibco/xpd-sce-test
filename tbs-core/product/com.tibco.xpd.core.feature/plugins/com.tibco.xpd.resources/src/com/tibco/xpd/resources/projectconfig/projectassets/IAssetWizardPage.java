/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.resources.projectconfig.projectassets;

import org.eclipse.jface.wizard.IWizardPage;

/**
 * Interface used by the project asset extension point. This will allow users to
 * define wizard pages for a project asset type.
 * <p>
 * The configuration set by the implementer will subsequently be used to
 * configure this asset type when the project is being created. This will be
 * done through the associated <code>{@link IAssetConfigurator}</code>.
 * </p>
 * 
 * @see IAssetConfigurator
 * 
 * @author njpatel
 */
public interface IAssetWizardPage extends IWizardPage {

    /**
     * Initialise the wizard page with the given configuration object. This
     * configuration is created from the configuration object provided in the
     * projectAsset extension. This method will be called before the controls
     * are created.
     * 
     * @param config
     *            Configuration object as set in the extension. This will be
     *            <code>null</code> if no configuration object was specified.
     */
    public void init(Object config);

    /**
     * Update the configuration object passed in the
     * <code>{@link #init(Object)}</code> with the data required to configure
     * this asset type for a new project.
     */
    public void updateConfiguration();

}
