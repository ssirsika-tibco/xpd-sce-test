/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.ui.internal.wizard;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.rest.schema.ui.RestConstants;
import com.tibco.xpd.rest.ui.internal.Messages;
import com.tibco.xpd.rest.ui.internal.RestServicesActivator;
import com.tibco.xpd.rest.ui.internal.asset.RestSourceSelectionControl;
import com.tibco.xpd.rest.ui.internal.asset.SectionValidityListener;
import com.tibco.xpd.ui.dialogs.WizardNewFileInSpecialFolderCreationPage;

/**
 * Wizard page for specifying the target folder and other details for the Import from swagger/OAS option
 * 
 * @author nkelkar
 * @since Jul 18 2024
 */
public class SwaggerFilePage extends
		WizardNewFileInSpecialFolderCreationPage implements SectionValidityListener
{
	private RestSourceSelectionControl restSourceSelection;

	/**
	 * 
	 * @param selection
	 */
	public SwaggerFilePage(IStructuredSelection selection)
	{
		this(Messages.ImportSwaggerWizard_windowTitle, selection);
		restSourceSelection = new RestSourceSelectionControl(false /* showCreateRSD */);
		restSourceSelection.addValidityChangeListener(this);
    }

    /**
     * @param selection
     *            The current navigator selection.
     */
    public SwaggerFilePage(String title, IStructuredSelection selection) {
        super(title, selection);

        setSpecialFolderSelectionValidator(RestConstants.REST_SPECIAL_FOLDER_KIND,
				new Status(IStatus.ERROR, RestServicesActivator.PLUGIN_ID,
						Messages.SwaggerFilePage_Description));

		addFilter(new ViewerFilter()
		{
			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element)
			{
				if (element instanceof SpecialFolder)
				{
					return RestConstants.REST_SPECIAL_FOLDER_KIND.equals(((SpecialFolder) element).getKind());
				}
				return true;
			}
		});
    }

	/**
	 * 
	 * @see com.tibco.xpd.ui.dialogs.WizardNewFileInSpecialFolderCreationPage#createControl(org.eclipse.swt.widgets.Composite)
	 *
	 * @param parent
	 */
    @Override
	public void createControl(Composite parent)
	{
		super.createControl(parent);

		// We don't need the file controls on this page, hide them
		hideFileGroup();

		// After all controls are added, validate the controls in RestResourceSelectionControl
		/*
		 * Sid ACE-8813 Set error message as well as page complete so that we get initial error message in dialog banner
		 */
		String validityErrorMessage = restSourceSelection.getValidityErrorMessage();

		setErrorMessage(validityErrorMessage);
		setPageComplete(validityErrorMessage == null);
    }

	/**
	 * 
	 * @see com.tibco.xpd.ui.dialogs.WizardNewFileInSpecialFolderCreationPage#createAdvancedControls(org.eclipse.swt.widgets.Composite)
	 *
	 * @param topLevel
	 * @return
	 */
	@Override
	protected Composite createAdvancedControls(Composite topLevel)
	{
		Composite advanced = new Composite(topLevel, SWT.NONE);
		advanced.setLayout(new GridLayout());

		Composite swaggerFileGroup = restSourceSelection.createControls(advanced);
		if (swaggerFileGroup != null)
		{
			// Set up the advanced group layout data
			swaggerFileGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		}

		return advanced;
	}

	/**
	 * @see com.tibco.xpd.rest.ui.internal.asset.SectionValidityListener#validityChanged(boolean, java.lang.String)
	 *
	 * @param isValid
	 * @param errorMsg
	 */
	@Override
	public void validityChanged(boolean isValid, String errorMsg)
	{
		setErrorMessage(errorMsg);
		// If the child page is invalid, set to false directly; otherwise call the validatePage method
		setPageComplete(isValid && validatePage());
	}

	/**
	 * Sid ACE-8813 Validity is a combination of the restSourceSelection section and superclass's idea of validity
	 * state.
	 * 
	 * @see com.tibco.xpd.resources.projectconfig.projectassets.AbstractSpecialFolderAssetWizardPage#validatePage()
	 *
	 * @return
	 */
	@Override
	protected boolean validatePage()
	{
		/* Validity is a combination of the restSourceSelection section and superclass's idea of validity state. */
		if (!super.validatePage())
		{
			return false;
		}

		String validityErrorMessage = restSourceSelection.getValidityErrorMessage();
		setErrorMessage(validityErrorMessage);

		return validityErrorMessage == null;
	}

	/**
	 * 
	 * @see com.tibco.xpd.ui.dialogs.WizardNewFileInSpecialFolderCreationPage#createNewFile()
	 *
	 * @return
	 */
	@Override
	public IFile createNewFile()
	{
		String aFileName = null;
		if (restSourceSelection.isImportFromSwaggerFile())
		{
			String[] fileNames = restSourceSelection.getSwaggerFileNames();
			IFile lastCreatedFile = null;
			for (String aFile : fileNames)
			{
				lastCreatedFile = super.createNewFile(true, aFile, getContentInputStream(aFile));
			}
			// For multiple file, return the pointer to the last created file
			return lastCreatedFile;
		}
		else if (restSourceSelection.isImportFromSwaggerURL())
		{
			aFileName = restSourceSelection.getSwaggerImportURLFileName();
			aFileName += getFileExtension(aFileName, restSourceSelection.getSwaggerSourceURLContent());
		}
		else if (restSourceSelection.isImportFromSwaggerContent())
		{
			aFileName = restSourceSelection.getSwaggerImportContentFileName();
			aFileName += getFileExtension(aFileName, restSourceSelection.getSwaggerContent());
		}

		return super.createNewFile(true, aFileName, getContentInputStream(aFileName));

	}

	/**
	 * Returns a stream containing the contents to be given to new file resource instance.
	 * 
	 * @param aFileName
	 * @return
	 */
	protected InputStream getContentInputStream(String aFileName)
	{
		try
		{
			if (restSourceSelection.isImportFromSwaggerFile())
			{
				String fileSourcePath = restSourceSelection.getSwaggerSourcePath();
				return new FileInputStream(fileSourcePath + File.separator + aFileName);
			}
			else if (restSourceSelection.isImportFromSwaggerURL())
			{
				String swaggerContent = restSourceSelection.getSwaggerSourceURLContent();
				return new ByteArrayInputStream(swaggerContent.getBytes(StandardCharsets.UTF_8));
			}
			else if (restSourceSelection.isImportFromSwaggerContent())
			{
				String swaggerContent = restSourceSelection.getSwaggerContent();
				return new ByteArrayInputStream(swaggerContent.getBytes(StandardCharsets.UTF_8));
			}
		}
		catch (IOException e)
		{
			RestServicesActivator.getDefault().getLog().log(new Status(IStatus.ERROR, RestServicesActivator.PLUGIN_ID,
					"Problem with Swagger file creation.", e)); //$NON-NLS-1$
		}

		return null;
	}

	/**
	 * Returns the file extension to be added to a filename based on the type of content
	 * 
	 * @param aFileName
	 * @param content
	 * @return
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
}
