/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.worklistfacade.resource.newproject;

import com.tibco.xpd.resources.projectconfig.projectassets.SpecialFolderAssetConfiguration;

/**
 * Work List Facade Special Folder Asset Configuration class .
 * 
 * @author aprasad
 * @since 26-Sep-2013
 */
public class WorkListFacadeAssetConfigration extends
		SpecialFolderAssetConfiguration {

	/**
	 * Option to check if the file should be created.
	 */
	private boolean createFile;

	private String facadeFileName;

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
	 * Returns the WorkListFacade file name.
	 * 
	 * @return
	 */
	public String getFacadeFileName() {
		return facadeFileName;
	}

	/**
	 * Set the WorkListFacade File name.
	 * 
	 * @param facadeFileName
	 */
	public void setFacadeFileName(String facadeFileName) {
		this.facadeFileName = facadeFileName;
	}

}
