/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.internal.projectasset;

import com.tibco.xpd.resources.projectconfig.projectassets.SpecialFolderAssetConfiguration;

public class BOMSpecialFolderAssetConfiguration extends
        SpecialFolderAssetConfiguration {

    private boolean createModel;

    private String bomFileName;

    private String bomType;

    public boolean isCreateModel() {
        return createModel;
    }

    public void setCreateModel(boolean createModel) {
        this.createModel = createModel;
    }

    public String getBomFileName() {
        return bomFileName;
    }

    public void setBomFileName(String bomFileName) {
        this.bomFileName = bomFileName;
    }

    public String getBomType() {
        return bomType;
    }

    public void setBomType(String bomType) {
        this.bomType = bomType;
    }

}
