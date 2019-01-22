/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.ui.internal.asset;

import com.tibco.xpd.resources.projectconfig.projectassets.SpecialFolderAssetConfiguration;

/**
 * Stores configuration data for REST project asset.
 *
 * @author jarciuch
 * @since 4 Aug 2015
 */
public class RestAssetConfiguration extends SpecialFolderAssetConfiguration {

    private boolean createRsdFile;

    private String rsdFileName;

    public boolean createRsdFile() {
        return createRsdFile;
    }

    public void setCreateRsdFile(boolean createRsdFile) {
        this.createRsdFile = createRsdFile;
    }

    public String getRsdFileName() {
        return rsdFileName;
    }

    public void setRsdFileName(String fileName) {
        this.rsdFileName = fileName;
    }

}
