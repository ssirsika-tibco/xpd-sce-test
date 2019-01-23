/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.worklistfacade.resource.newproject;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.resource.Resource;

import com.tibco.xpd.resources.projectconfig.projectassets.AbstractSpecialFolderAssetConfigurator;
import com.tibco.xpd.resources.projectconfig.projectassets.IProjectAssetVersionProvider;
import com.tibco.xpd.resources.projectconfig.projectassets.SpecialFolderAssetConfiguration;
import com.tibco.xpd.worklistfacade.resource.WorkListFacadeResourcePlugin;
import com.tibco.xpd.worklistfacade.resource.editor.util.WorkListFacadeEditorUtil;
import com.tibco.xpd.worklistfacade.resource.util.Messages;

/**
 * Configurator for WorkListFacade Special Folder Asset .
 * 
 * @author aprasad
 * @since 26-Sep-2013
 */
/**
 * @author aprasad
 * 
 */
public class WorkListFacadeAssetConfigurator extends
		AbstractSpecialFolderAssetConfigurator implements
		IProjectAssetVersionProvider {

	/**
	 * Returns WorkListFacade Special Folder Kind .
	 * 
	 * @see com.tibco.xpd.resources.projectconfig.projectassets.AbstractSpecialFolderAssetConfigurator#getSpecialFolderKind()
	 * 
	 * @return
	 */
	@Override
	protected String getSpecialFolderKind() {
		return WorkListFacadeResourcePlugin.WLF_SPECIAL_FOLDER_KIND;
	}

	/**
	 * Returns the name for WorkListFacade special folder.
	 * 
	 * @see com.tibco.xpd.resources.projectconfig.projectassets.AbstractSpecialFolderAssetConfigurator#getSpecialFolderName()
	 * 
	 * @return
	 */
	@Override
	protected String getSpecialFolderName() {
		Object config = getConfiguration();
		if (config instanceof SpecialFolderAssetConfiguration) {
			String name = ((SpecialFolderAssetConfiguration) config)
					.getSpecialFolderName();

			if (name != null && name.length() > 0) {
				return name;
			}
		}
		return getDefaultFolderName();
	}

	/**
	 * Returns the Default name for WorkListFacade special folder.
	 * 
	 * @see com.tibco.xpd.resources.projectconfig.projectassets.AbstractSpecialFolderAssetConfigurator#getDefaultFolderName()
	 * 
	 * @return
	 */
	@Override
	public String getDefaultFolderName() {
        return Messages.WorkListFacadeAssetWizardPage_Default_WLF_Folder_Name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.xpd.resources.projectconfig.projectassets.
	 * AbstractSpecialFolderAssetConfigurator
	 * #configure(org.eclipse.core.resources.IProject, java.lang.Object)
	 */
	@Override
	public void configure(IProject project, Object configuration)
			throws CoreException {
		super.configure(project, configuration);
		// Handle WorkListFacade Asset Configuration.
		if (configuration instanceof WorkListFacadeAssetConfigration) {

			WorkListFacadeAssetConfigration wlfConf = (WorkListFacadeAssetConfigration) configuration;

			if (wlfConf.isCreateFile()) {
				// Get Special Folder Name
				String folderName = wlfConf.getSpecialFolderName();
				// Create the folder
				if (folderName != null) {
					IFolder folder = project.getFolder(folderName);

					if (folder.exists()) {
						// Create the sample facade model in this folder
						IPath path = folder.getFullPath();
						path = path.append(wlfConf.getFacadeFileName());
						// Create WorkListFacade file with initial model.
						Resource newWorkListFacadeFile = WorkListFacadeEditorUtil
								.createWorkListFacadeFile(path,
										new NullProgressMonitor());
						// Open new file in Editor.
						WorkListFacadeEditorUtil
								.openWorkListFacadeEditor(newWorkListFacadeFile);
					}
				}

			}

		}

	}

	/*
	 * (non-Javadoc) Returns the Format version for this Asset.
	 * 
	 * @see com.tibco.xpd.resources.projectconfig.projectassets.
	 * IProjectAssetVersionProvider
	 * #getVersion(org.eclipse.core.resources.IProject)
	 */
	@Override
	public int getVersion(IProject project) {
		return Integer.parseInt(WorkListFacadeResourcePlugin.FORMAT_VERSION);
	}

}
