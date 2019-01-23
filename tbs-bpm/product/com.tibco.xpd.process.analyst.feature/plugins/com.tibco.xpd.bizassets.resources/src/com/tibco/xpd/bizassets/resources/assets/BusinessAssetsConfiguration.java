/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bizassets.resources.assets;

import com.tibco.xpd.bizassets.resources.BusinessAssetsConstants;

/**
 * Business Assets project asset configuration object.
 * 
 * @author njpatel
 * 
 */
public class BusinessAssetsConfiguration {

    private String folderName;

    /**
     * Constructor
     */
    public BusinessAssetsConfiguration() {
        // Set default business assets folder name
        folderName = BusinessAssetsConstants.BUSINESSASSETS_FOLDER;
    }

    /**
     * Set the name of the Business Assets special folder.
     * 
     * @param name
     */
    public void setFolderName(String name) {
        folderName = name;
    }

    /**
     * Get the name of the Business Assets special folder.
     * 
     * @return
     */
    public String getFolderName() {
        return folderName;
    }

}
