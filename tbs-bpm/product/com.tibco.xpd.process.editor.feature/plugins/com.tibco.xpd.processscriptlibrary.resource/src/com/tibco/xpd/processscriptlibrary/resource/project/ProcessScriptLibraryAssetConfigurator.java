/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */

package com.tibco.xpd.processscriptlibrary.resource.project;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import com.tibco.xpd.processscriptlibrary.resource.config.ProcessScriptLibraryConstants;
import com.tibco.xpd.processscriptlibrary.resource.editor.util.PslEditorUtil;
import com.tibco.xpd.processscriptlibrary.resource.internal.Messages;
import com.tibco.xpd.resources.projectconfig.projectassets.AbstractSpecialFolderAssetConfigurator;
import com.tibco.xpd.resources.projectconfig.projectassets.SpecialFolderAssetConfiguration;
import com.tibco.xpd.xpdl2.DocumentRoot;
import com.tibco.xpd.xpdl2.Xpdl2Factory;

/**
 * Configurator for ProcessScriptLibrary Special Folder Asset.
 *
 * @author ssirsika
 * @since 06-Dec-2023
 */
public class ProcessScriptLibraryAssetConfigurator extends AbstractSpecialFolderAssetConfigurator
{

	/**
	 * @see com.tibco.xpd.resources.projectconfig.projectassets.IAssetConfigurator#configure(org.eclipse.core.resources.IProject,
	 *      java.lang.Object)
	 *
	 * @param project
	 * @param configuration
	 * @throws CoreException
	 */
	@Override
	public void configure(IProject project, Object configuration) throws CoreException
	{
		super.configure(project, configuration);
		// Handle ProcessScriptLibrary Asset Configuration.
		if (configuration instanceof ProcessScriptLibraryAssetConfiguration)
		{
			ProcessScriptLibraryAssetConfiguration pslAssetConfiguration = (ProcessScriptLibraryAssetConfiguration) configuration;

			if (pslAssetConfiguration.isCreatePslFile())
			{
				String specialFolderName = pslAssetConfiguration.getSpecialFolderName();
				// Create the folder
				if (specialFolderName != null)
				{
					IFolder folder = project.getFolder(specialFolderName);

					if (folder.exists())
					{
						String pslFileName = pslAssetConfiguration.getPslFileName();
						IFile file = folder.getFile(pslFileName);
						if (!file.exists())
						{
							URI uri = URI.createPlatformResourceURI(pslFileName, false);
							ResourceSet rset = new ResourceSetImpl();

							DocumentRoot docRoot = Xpdl2Factory.eINSTANCE.createDocumentRoot();

							Resource resource = rset.createResource(uri);

							docRoot.setPackage(pslAssetConfiguration.getXpdl2Package());
							resource.getContents().add(docRoot);

							ByteArrayOutputStream os = new ByteArrayOutputStream();
							try
							{
								resource.save(os, null);
							}
							catch (IOException e)
							{
								System.err.println("BusinessProcessAssetConfigurator:: " //$NON-NLS-1$
										+ e.getMessage());
							}
							file.create(new ByteArrayInputStream(os.toByteArray()), true, new NullProgressMonitor());

							PslEditorUtil.openEditor(file);
						}
					}
				}
			}

		}

	}

	/**
	 * @see com.tibco.xpd.resources.projectconfig.projectassets.AbstractSpecialFolderAssetConfigurator#getSpecialFolderKind()
	 *
	 * @return
	 */
	@Override
	protected String getSpecialFolderKind()
	{
		return ProcessScriptLibraryConstants.PSL_SPECIAL_FOLDER_KIND;
	}

	/**
	 * @see com.tibco.xpd.resources.projectconfig.projectassets.AbstractSpecialFolderAssetConfigurator#getSpecialFolderName()
	 *
	 * @return
	 */
	@Override
	protected String getSpecialFolderName()
	{
		Object config = getConfiguration();
		if (config instanceof SpecialFolderAssetConfiguration)
		{
			String name = ((SpecialFolderAssetConfiguration) config).getSpecialFolderName();

			if (name != null && name.length() > 0)
			{
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
	public String getDefaultFolderName()
	{
		return Messages.ProcessScriptLibraryAssetConfigurator_DefaultFolderName;
	}
}
