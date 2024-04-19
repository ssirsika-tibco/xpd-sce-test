/*
* Copyright (c) 2004-2023. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.processscriptlibrary.resource.project;

import com.tibco.xpd.resources.projectconfig.projectassets.SpecialFolderAssetConfiguration;
import com.tibco.xpd.xpdl2.Package;

/**
 * Process Script Library Special Folder Asset Configuration class .
 *
 * @author ssirsika
 * @since 06-Dec-2023
 */
public class ProcessScriptLibraryAssetConfiguration extends SpecialFolderAssetConfiguration
{

	/**
	 * Option to check if the file should be created.
	 */
	private boolean	createPslFile;

	private String	pslFileName;

	private Package	xpdl2Package;


	/**
	 * @return the createPslFile
	 */
	public boolean isCreatePslFile()
	{
		return createPslFile;
	}

	/**
	 * @param createPslFile the createPslFile to set
	 */
	public void setCreatePslFile(boolean createPslFile)
	{
		this.createPslFile = createPslFile;
	}

	/**
	 * @return the xpdl2Package
	 */
	public Package getXpdl2Package()
	{
		return xpdl2Package;
	}

	/**
	 * @param xpdl2Package the xpdl2Package to set
	 */
	public void setXpdl2Package(Package xpdl2Package)
	{
		this.xpdl2Package = xpdl2Package;
	}

	/**
	 * @return the pslFileName
	 */
	public String getPslFileName()
	{
		return pslFileName;
	}

	/**
	 * @param pslFileName
	 *            the pslFileName to set
	 */
	public void setPslFileName(String pslFileName)
	{
		this.pslFileName = pslFileName;
	}

}
