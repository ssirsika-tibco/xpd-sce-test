/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.newproject;

import org.eclipse.core.resources.IFolder;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.resources.projectconfig.projectassets.SpecialFolderAssetConfiguration;
import com.tibco.xpd.xpdl2.Package;

/**
 * Business Process asset configuration object. This will capture the package
 * name and also a JET template wizard selected (if one is selected).
 * 
 * @author njpatel
 * 
 */
public class BusinessProcessAssetConfig extends SpecialFolderAssetConfiguration {

    /**
     * Default process package folder name
     */
    private static final String DEFAULT_FOLDER_PACKAGE_NAME =
            Xpdl2ResourcesConsts.DEFAULT_PACKAGE_FOLDER_NAME;

    private IFolder processPackageFolder;

    private Package xpdl2Package;

    private com.tibco.xpd.xpdl2.Process process;

    private String packageFileName;

    private String processToOpenName;

    public BusinessProcessAssetConfig() {
        // Set default folder name
        setSpecialFolderName(DEFAULT_FOLDER_PACKAGE_NAME);
    }

    public IFolder getProcessPackageFolder() {
        return processPackageFolder;
    }

    public void setProcessPackageFolder(IFolder processPackageFolder) {
        this.processPackageFolder = processPackageFolder;
    }

    public Package getXpdl2Package() {
        return xpdl2Package;
    }

    public void setXpdl2Package(Package xpdl2Package) {
        this.xpdl2Package = xpdl2Package;
    }

    public com.tibco.xpd.xpdl2.Process getProcess() {
        return process;
    }

    public void setProcess(com.tibco.xpd.xpdl2.Process process) {
        this.process = process;
    }

    public String getPackageFileName() {
        return packageFileName;
    }

    public void setPackageFileName(String packageFileName) {
        this.packageFileName = packageFileName;
    }

    public String getProcessToOpen() {
        return processToOpenName;
    }

    public void setProcessToOpen(String processToOpenName) {
        this.processToOpenName = processToOpenName;
    }
}