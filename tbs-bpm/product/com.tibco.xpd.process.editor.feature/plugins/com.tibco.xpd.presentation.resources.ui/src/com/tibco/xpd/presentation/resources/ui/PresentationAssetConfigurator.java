/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.presentation.resources.ui;

import com.tibco.xpd.presentation.resources.ui.internal.Messages;
import com.tibco.xpd.presentation.resources.ui.internal.util.PresentationManager;
import com.tibco.xpd.resources.projectconfig.projectassets.AbstractSpecialFolderAssetConfigurator;
import com.tibco.xpd.resources.projectconfig.projectassets.SpecialFolderAssetConfiguration;

/**
 * Configures presentation project asset.
 * <p>
 * <i>Created: 29 Apr 2009</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public class PresentationAssetConfigurator extends
        AbstractSpecialFolderAssetConfigurator {

    @Override
    protected String getSpecialFolderName() {
        Object config = getConfiguration();
        if (config instanceof SpecialFolderAssetConfiguration) {
            String name =
                    ((SpecialFolderAssetConfiguration) config)
                            .getSpecialFolderName();

            if (name != null && name.length() > 0) {
                return name;
            }
        }

        return getDefaultFolderName();
    }

    @Override
    public String getDefaultFolderName() {
        return Messages.PresentationAssetConfigurator_defaultPresentationSFName_label;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getSpecialFolderKind() {
        return PresentationManager.PRESENTATION_SPECIAL_FOLDER_KIND;
    }

}
