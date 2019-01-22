/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.modeler.custom.internal.projectasset;

import com.tibco.xpd.resources.projectconfig.projectassets.SpecialFolderAssetConfiguration;

public class OMSpecialFolderAssetConfiguration extends
        SpecialFolderAssetConfiguration {

    private String omFileName;

    private boolean createModel;

    private boolean createSchema;

    private boolean applyType;

    public String getOmFileName() {
        return omFileName;
    }

    public void setOmFileName(String omFileName) {
        this.omFileName = omFileName;
    }

    public boolean isCreateModel() {
        return createModel;
    }

    public void setCreateModel(boolean createModel) {
        this.createModel = createModel;
    }

    public boolean isCreateSchema() {
        return createSchema;
    }

    public void setCreateSchema(boolean createSchema) {
        this.createSchema = createSchema;
    }

    public boolean isApplyType() {
        return applyType;
    }

    public void setApplyType(boolean applyType) {
        this.applyType = applyType;
    }

}
