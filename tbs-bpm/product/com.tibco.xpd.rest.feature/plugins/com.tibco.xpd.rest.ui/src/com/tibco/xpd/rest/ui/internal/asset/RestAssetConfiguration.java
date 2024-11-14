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

	private boolean		importFromSwaggerFile;

	private boolean		importFromSwaggerURL;

	private boolean		importFromSwaggerContent;

	private String		rsdFileName;

	private String[]	swaggerFileNames;

	private String		swaggerSourcePath;

	private String		swaggerSourceURLContent;

	private String		swaggerImportURLFileName;

	private String		swaggerContent;

	private String		swaggerImportContentFileName;

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

	public String[] getSwaggerFileNames()
	{
		return swaggerFileNames;
	}

	public void setSwaggerFileNames(String[] fileNames)
	{
		this.swaggerFileNames = fileNames;
	}

	public void setSwaggerSourcePath(String swaggerSourcePath)
	{
		this.swaggerSourcePath = swaggerSourcePath;
	}

	public String getSwaggerSourcePath()
	{
		return swaggerSourcePath;
	}

	public void setSwaggerSourceURLContent(String swaggerSourceURLContent)
	{
		this.swaggerSourceURLContent = swaggerSourceURLContent;
	}

	public String getSwaggerSourceURLContent()
	{
		return swaggerSourceURLContent;
	}

	public String getSwaggerImportURLFileName()
	{
		return swaggerImportURLFileName;
	}

	public void setSwaggerImportURLFileName(String swaggerImportURLFileName)
	{
		this.swaggerImportURLFileName = swaggerImportURLFileName;
	}

	public void setImportFromSwaggerFile(boolean importFromSwaggerFile)
	{
		this.importFromSwaggerFile = importFromSwaggerFile;
	}

	public void setImportFromSwaggerURL(boolean importFromSwaggerURL)
	{
		this.importFromSwaggerURL = importFromSwaggerURL;
	}

	public boolean importFromSwaggerFile()
	{
		return importFromSwaggerFile;
	}

	public boolean importFromSwaggerURL()
	{
		return importFromSwaggerURL;
	}

	/**
	 * @return the importFromSwaggerContent
	 */
	public boolean isImportFromSwaggerContent()
	{
		return importFromSwaggerContent;
	}

	/**
	 * @param importFromSwaggerContent the importFromSwaggerContent to set
	 */
	public void setImportFromSwaggerContent(boolean importFromSwaggerContent)
	{
		this.importFromSwaggerContent = importFromSwaggerContent;
	}

	/**
	 * @return the swaggerContent
	 */
	public String getSwaggerContent()
	{
		return swaggerContent;
	}

	/**
	 * @param swaggerContent the swaggerContent to set
	 */
	public void setSwaggerContent(String swaggerContent)
	{
		this.swaggerContent = swaggerContent;
	}

	/**
	 * @return the swaggerImportContentFileName
	 */
	public String getSwaggerImportContentFileName()
	{
		return swaggerImportContentFileName;
	}

	/**
	 * @param swaggerImportContentFileName the swaggerImportContentFileName to set
	 */
	public void setSwaggerImportContentFileName(String swaggerImportContentFileName)
	{
		this.swaggerImportContentFileName = swaggerImportContentFileName;
	}

}
