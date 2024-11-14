package com.tibco.xpd.rest.ui.internal.asset;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.resources.projectconfig.projectassets.AbstractSpecialFolderAssetConfigurator;
import com.tibco.xpd.resources.projectconfig.projectassets.IProjectAssetVersionProvider;
import com.tibco.xpd.resources.projectconfig.projectassets.SpecialFolderAssetConfiguration;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.rest.schema.ui.RestConstants;
import com.tibco.xpd.rest.ui.RestServicesUtil;
import com.tibco.xpd.rest.ui.internal.RestServicesActivator;
import com.tibco.xpd.rsd.ui.wizards.NewRsdFileWizard;

/**
 * Asset configurator to add the REST nature to projects containing REST
 * resources.
 * 
 * @author nwilson
 * @since 30 Jan 2015
 */
public class RestAssetConfigurator extends
        AbstractSpecialFolderAssetConfigurator implements
        IProjectAssetVersionProvider {

    @Override
    public void configure(IProject project, Object configuration)
            throws CoreException {
        super.configure(project, configuration);
        if (project != null) {
            ProjectUtil.addNature(project, RestServicesProjectNature.ID);

            if (configuration instanceof RestAssetConfiguration) {
                RestAssetConfiguration restAssetConf =
                        (RestAssetConfiguration) configuration;
				String folderName = getSpecialFolderName();

				// Nikita ACE-8263 "Create Initial RSD" is NOT the default option anymore, there are 2 more options
				// 'Import from Swagger file' and 'Import from Swagger URL'. Handle the configuration for each option
				// separately

				// Create the folder
				if (folderName != null)
				{
					IFolder folder = project.getFolder(folderName);
					if (folder.exists())
					{
						if (restAssetConf.createRsdFile())
						{
							// Configure 'Create Initial RSD'
							configureCreateRsdFile(restAssetConf, project, folderName);
						}
						else if (restAssetConf.importFromSwaggerFile())
						{
							// Configure 'Import from Swagger File'
							configureImportFromSwaggerFile(restAssetConf, project, folder);
						}
						else if (restAssetConf.importFromSwaggerURL())
						{
							// Configure 'Import from Swagger URL'
							configureImportFromSwaggerURL(restAssetConf, project, folder);
						}
						else if (restAssetConf.isImportFromSwaggerContent())
						{
							// Configure 'Import from Swagger File'
							configureImportFromSwaggerContent(restAssetConf, project, folder);
						}
					}
				}
            }
        }
    }

	/**
	 * Handles the creation of a new RSD file in a specified folder in the REST project
	 * 
	 * @param restAssetConf
	 * @param project
	 * @param folderName
	 */
	private void configureCreateRsdFile(RestAssetConfiguration restAssetConf, IProject project, String folderName)
	{
		String fileName = restAssetConf.getRsdFileName();

		/*
		 * Create the sample rsd file in this folder. We will reuse NwRsdFileWizard, to make sure that we create the
		 * same RSD model as if it was created by the wizard.
		 */
		IPath filePath = project.getFullPath().append(folderName).append(fileName);
		NewRsdFileWizard newRsdFileWizard = new NewRsdFileWizard();
		IRunnableWithProgress op = newRsdFileWizard.getCreateOperation(filePath);
		try
		{
			op.run(new NullProgressMonitor());
			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(filePath);
			if (file != null)
			{
				newRsdFileWizard.openEditor(file);
				IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
				NewRsdFileWizard.selectAndReveal(file, workbenchWindow);
			}
		}
		catch (InterruptedException | InvocationTargetException e)
		{
			RestServicesActivator.getDefault().getLog().log(new Status(IStatus.ERROR, RestServicesActivator.PLUGIN_ID,
					"Problem with default rsd file creation.", e)); //$NON-NLS-1$
		}
	}

	/**
	 * Handles the creation of a Swagger/OAS file (which is a copy of a file from the local system) in the specified
	 * folder
	 * 
	 * @param restAssetConf
	 * @param project
	 * @param folder
	 */
	private void configureImportFromSwaggerFile(RestAssetConfiguration restAssetConf, IProject project,
			IFolder folder)
	{
		String[] fileNames = restAssetConf.getSwaggerFileNames();
		String fileSourcePath = restAssetConf.getSwaggerSourcePath();

		if (fileSourcePath != null)
		{
			// Copy all files to target folder
			for (String aFile : fileNames)
			{
				try
				{
					IFile targetFile = folder.getFile(aFile);
					InputStream oInStream = new FileInputStream(fileSourcePath + File.separator + aFile);
					targetFile.create(oInStream, true, null);
				}
				catch (CoreException | FileNotFoundException e)
				{
					RestServicesActivator.getDefault().getLog().log(new Status(IStatus.ERROR,
							RestServicesActivator.PLUGIN_ID, "Problem with Swagger file creation.", e)); //$NON-NLS-1$
				}
			}
		}
	}

	/**
	 * Handles the creation of a Swagger/OAS file (created by reading content from an external URL) in a specified
	 * folder
	 * 
	 * @param restAssetConf
	 * @param project
	 * @param folder
	 */
	private void configureImportFromSwaggerURL(RestAssetConfiguration restAssetConf, IProject project, IFolder folder)
	{
		String aFileName = restAssetConf.getSwaggerImportURLFileName();
		String sourceURLContent = restAssetConf.getSwaggerSourceURLContent();

		if (sourceURLContent != null)
		{
			// Create file in target folder
			try
			{
				aFileName += getFileExtension(aFileName, sourceURLContent);
				IFile targetFile = folder.getFile(aFileName);
				InputStream oInStream = new ByteArrayInputStream(sourceURLContent.getBytes(StandardCharsets.UTF_8));
				targetFile.create(oInStream, true, null);
			}
			catch (CoreException e)
			{
				RestServicesActivator.getDefault().getLog().log(new Status(IStatus.ERROR,
						RestServicesActivator.PLUGIN_ID, "Problem with Swagger file creation.", e)); //$NON-NLS-1$
			}
		}
	}

	/**
	 * Handles the creation of a Swagger/OAS file (created by reading the entered content) in a specified folder
	 * 
	 * @param restAssetConf
	 * @param project
	 * @param folder
	 */
	private void configureImportFromSwaggerContent(RestAssetConfiguration restAssetConf, IProject project,
			IFolder folder)
	{
		String aFileName = restAssetConf.getSwaggerImportContentFileName();
		String swaggerContent = restAssetConf.getSwaggerContent();

		if (swaggerContent != null)
		{
			// Create file in target folder
			try
			{
				aFileName += getFileExtension(aFileName, swaggerContent);
				IFile targetFile = folder.getFile(aFileName);
				InputStream oInStream = new ByteArrayInputStream(swaggerContent.getBytes(StandardCharsets.UTF_8));
				targetFile.create(oInStream, true, null);
			}
			catch (CoreException e)
			{
				RestServicesActivator.getDefault().getLog().log(new Status(IStatus.ERROR,
						RestServicesActivator.PLUGIN_ID, "Problem with Swagger file creation.", e)); //$NON-NLS-1$
			}
		}
	}

	/**
	 * Returns the file extension to be added to a filename based on the type of content
	 * 
	 * @param aFileName
	 * @param content
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("nls")
	private String getFileExtension(String aFileName, String content)
	{
		if (content.startsWith("{"))
		{
			if (!aFileName.endsWith("." + RestConstants.JSON_FILE_EXTENSION))
			{
				return "." + RestConstants.JSON_FILE_EXTENSION;
			}
		}
		else
		{
			if (!aFileName.endsWith("." + RestConstants.YAML_FILE_EXTENSION))
			{
				return "." + RestConstants.YAML_FILE_EXTENSION;
			}
		}

		return "";
	}

    /**
     * {@inheritDoc}
     */
    @Override
    public int getVersion(IProject project) {
        return Integer.parseInt(RestServicesUtil.REST_ASSET_VERSION);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getSpecialFolderKind() {
        return RestServicesUtil.REST_SPECIAL_FOLDER_KIND;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getSpecialFolderName() {
        Object config = getConfiguration();
        if (config instanceof SpecialFolderAssetConfiguration) {
            String name =
                    ((SpecialFolderAssetConfiguration) config)
                            .getSpecialFolderName();

            if (name != null && name.length() > 0) {
                return name;
            }
        }

        return getDefaultFolderName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDefaultFolderName() {
        return RestServicesUtil.REST_SPECIAL_FOLDER_DEFAULT_NAME;
    }
}
