/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.processscriptlibrary.resource.wizards;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.processscriptlibrary.resource.ProcessScriptLibraryResourcePluginActivtor;
import com.tibco.xpd.processscriptlibrary.resource.config.ProcessScriptLibraryConstants;
import com.tibco.xpd.processscriptlibrary.resource.editor.util.PslEditorUtil;
import com.tibco.xpd.processscriptlibrary.resource.internal.Messages;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.ui.dialogs.WizardNewFileInSpecialFolderCreationPage;

/**
 * Wizard page to create a .psl file.
 *
 * @author cbabar
 * @since Jan 3, 2024
 */
public class ProcessScriptLibraryCreationWizardPage extends WizardNewFileInSpecialFolderCreationPage
{

	/**
	 * @param pageName
	 * @param selection
	 */
	public ProcessScriptLibraryCreationWizardPage(String pageName, IStructuredSelection selection)
	{
		super(pageName, selection);

		/*
		 * Add selection validator to warn user if file not created in PSL special folder
		 */
		setSpecialFolderSelectionValidator(ProcessScriptLibraryConstants.PSL_SPECIAL_FOLDER_KIND,
				new Status(IStatus.ERROR, ProcessScriptLibraryResourcePluginActivtor.PLUGIN_ID,
						Messages.ProcessScriptLibraryCreationWizardPage_WrongFolder_Msg));

		/*
		 * Only show Process Script Library special folders in the tree viewer
		 */
		addFilter(new ViewerFilter()
		{
			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element)
			{

				if (element instanceof SpecialFolder)
				{

					return ProcessScriptLibraryConstants.PSL_SPECIAL_FOLDER_KIND
							.equals(((SpecialFolder) element).getKind());

				}
				return true;
			}
		});
	}

	/**
	 * Returns the File extension for Process Script Library.
	 * 
	 * @return
	 */
	private String getExtension()
	{
		return ProcessScriptLibraryConstants.PSL_SPECIAL_FOLDER_KIND;
	}

	/**
	 * Returns a encoded workspace-relative path string, by calling 'createPlatformResourceURI' utility method.
	 * 
	 * @return
	 */
	public URI getURI()
	{

		return URI.createPlatformResourceURI(getFilePath().toString(), true);
	}

	/**
	 * Returns the File path, with the ProcessScriptLibrary file extension '.psl' appended , if does not already end
	 * with the same.
	 * 
	 * @return
	 */
	protected IPath getFilePath()
	{

		IPath path = getContainerFullPath();

		if (path == null)
		{
			path = new Path(""); //$NON-NLS-1$
		}

		String fileName = getFileName();

		if (fileName != null)
		{
			path = path.append(fileName);
		}

		/*
		 * If the path does not have the correct file extension then append the correct file extension
		 */
		if (path != null
				&& (path.getFileExtension() == null || !path.getFileExtension().equalsIgnoreCase(getExtension())))
		{

			path = path.addFileExtension(getExtension());
		}

		return path;
	}

	/**
	 * Sets the default unique file name derived from the Project name/ existing Process Script Library files under the
	 * parent Special Folder.
	 */
	@Override
	public void createControl(Composite parent)
	{

		super.createControl(parent);

		setFileName(PslEditorUtil.getUniqueFileName(getContainerFullPath(), getFileName(), getExtension()));

		setPageComplete(validatePage());

	}

}
