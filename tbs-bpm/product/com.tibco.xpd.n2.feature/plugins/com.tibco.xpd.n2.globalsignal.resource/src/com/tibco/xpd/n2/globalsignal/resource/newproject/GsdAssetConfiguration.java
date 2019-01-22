/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.n2.globalsignal.resource.newproject;

import com.tibco.xpd.resources.projectconfig.projectassets.SpecialFolderAssetConfiguration;

/**
 * Global Signal Definition Special Folder Asset Configuration class.
 * 
 * @author sajain
 * @since 27-Jan-2015
 */
public class GsdAssetConfiguration extends
        SpecialFolderAssetConfiguration {

    /**
     * Option to check if the file should be created.
     */
    private boolean createFile;

    /**
     * GSD file name.
     */
    private String gsdFileName;

    /**
     * Returns 'Create File' option.
     * 
     * @return
     */
    public boolean isCreateFile() {
        return createFile;
    }

    /**
     * Set create file boolean option.
     * 
     * @param createModel
     */
    public void setCreateFile(boolean createModel) {
        this.createFile = createModel;
    }

    /**
     * Returns the Global Signal Definition file name.
     * 
     * @return
     */
    public String getGSDFileName() {
        return gsdFileName;
    }

    /**
     * Set the Global Signal Definition File name.
     * 
     * @param gsdFileName
     */
    public void setGSDFileName(String gsdFileName) {
        this.gsdFileName = gsdFileName;
    }

}
