/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.ui.importexport.importwizard.pages;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.ui.dialogs.FileSystemElement;
import org.eclipse.ui.model.AdaptableList;
import org.eclipse.ui.wizards.datatransfer.IImportStructureProvider;

/**
 * The <code>FSElement</code> is a <code>FileSystemElement</code> that knows
 * if it has been populated or not.
 * 
 * @author njpatel
 */
public class FilteredFSElement extends FileSystemElement {
	private boolean populated = false;

	private static List<String> fileExtensionsFilter = null;

	/**
	 * Create a <code>FSElement</code> with the supplied name and parent.
	 * 
	 * @param name
	 *            the name of the file element this represents
	 * @param parent
	 *            the containing parent
	 * @param isDirectory
	 *            indicated if this could have children or not
	 */
	public FilteredFSElement(String name, FileSystemElement parent,
			boolean isDirectory) {
		super(name, parent, isDirectory);
	}

	/**
	 * If file extension filter is specified then the children that comply with
	 * the filter will only be added, otherwise all children will be added
	 */
	public void addChild(FileSystemElement child) {
		boolean bAdd = true;

		// If this is a file and we have a file extension filter then only
		// add the file if it matches the extension filter
		if (fileExtensionsFilter != null && !child.isDirectory()) {
			bAdd = fileExtensionsFilter.contains(child.getFileNameExtension());
		}

		if (bAdd)
			super.addChild(child);
	}

	/**
	 * Returns a list of the files that are immediate children. Use the supplied
	 * provider if it needs to be populated. of this folder.
	 */
	public AdaptableList getFiles(IImportStructureProvider provider) {
		if (!populated) {
			populate(provider);
		}
		return super.getFiles();
	}

	/**
	 * Returns a list of the folders that are immediate children. Use the
	 * supplied provider if it needs to be populated. of this folder.
	 */
	public AdaptableList getFolders(IImportStructureProvider provider) {
		if (!populated) {
			populate(provider);
		}
		return super.getFolders();
	}

	/**
	 * This will set the file extensions filter so that only files of the given
	 * extension will be listed
	 * 
	 * @param lstFileExtFilter
	 */
	public static void setFileExtensionFilter(List<String> lstFileExtFilter) {
		fileExtensionsFilter = lstFileExtFilter;
	}

	/**
	 * Return whether or not population has happened for the receiver.
	 */
	boolean isPopulated() {
		return this.populated;
	}

	/**
	 * Populate the files and folders of the receiver using the suppliec
	 * structure provider.
	 * 
	 * @param provider
	 *            org.eclipse.ui.wizards.datatransfer.IImportStructureProvider
	 */
	private void populate(IImportStructureProvider provider) {

		Object fileSystemObject = getFileSystemObject();

		List<?> children = provider.getChildren(fileSystemObject);
		if (children == null) {
			children = new ArrayList<Object>(1);
		}
		Iterator<?> childrenEnum = children.iterator();
		while (childrenEnum.hasNext()) {
			Object child = childrenEnum.next();

			String elementLabel = provider.getLabel(child);
			// Create one level below
			FilteredFSElement result = new FilteredFSElement(elementLabel,
					this, provider.isFolder(child));
			result.setFileSystemObject(child);
			// }
		}
		setPopulated();
	}

	/**
	 * Set whether or not population has happened for the receiver to true.
	 */
	public void setPopulated() {
		this.populated = true;
	}
}
