/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.ui.internal.asset;

import java.beans.PropertyChangeEvent;

import org.eclipse.core.resources.IFolder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;

import com.tibco.xpd.resources.projectconfig.projectassets.AbstractSpecialFolderAssetWizardPage;
import com.tibco.xpd.resources.projectconfig.projectassets.IAssetProjectPropertyChangeListener;
import com.tibco.xpd.resources.projectconfig.projectassets.SpecialFolderAssetConfiguration;
import com.tibco.xpd.rest.ui.RestServicesUtil;
import com.tibco.xpd.rest.ui.internal.Messages;
import com.tibco.xpd.rest.ui.internal.RestImage;
import com.tibco.xpd.rest.ui.internal.RestServicesActivator;

/**
 * The Wizard Page for creation of default REST asset content, which is chained into New Project Wizard.
 *
 * @author jarciuch
 * @since 4 Aug 2015
 */
public class RestAssetWizardPage extends AbstractSpecialFolderAssetWizardPage implements SectionValidityListener
{
	private RestSourceSelectionControl	restSourceSelection;

	public RestAssetWizardPage()
	{
		super("RestAssetWizard");//$NON-NLS-1$
		setTitle(Messages.RestAssetWizardPage_RestServices_title);
		setDescription(Messages.RestAssetWizardPage_RestServices_desc);
		/* Sid ACE-7330: Show correct wizard banner icon for asset type. */
		setImageDescriptor(RestServicesActivator.getDefault().getImageDescriptor(RestImage.IMPORT_SWAGGER_WIZARD));

		restSourceSelection = new RestSourceSelectionControl(true /* showCreateRSD */);
		restSourceSelection.addValidityChangeListener(this);
	}

	@Override
	protected String getDefaultSpecialFolderName()
	{
		Object conf = getConfiguration();
		String folderName = null;

		if (conf instanceof SpecialFolderAssetConfiguration)
		{
			SpecialFolderAssetConfiguration config = (SpecialFolderAssetConfiguration) conf;
			/*
			 * If a selection is set then override the default folder name with the selection
			 */
			if (config.getSelection() != null && config.getSelection().getFirstElement() instanceof IFolder)
			{
				folderName = ((IFolder) config.getSelection().getFirstElement()).getProjectRelativePath().toString();
			}
			else
			{
				folderName = RestServicesUtil.REST_SPECIAL_FOLDER_DEFAULT_NAME;
			}
		}

		return folderName;
	}

	@Override
	public void updateConfiguration()
	{
		Object conf = getConfiguration();

		if (conf instanceof SpecialFolderAssetConfiguration)
		{
			((SpecialFolderAssetConfiguration) conf).setSpecialFolderName(getSpecialFolderName());

			if (conf instanceof RestAssetConfiguration)
			{
				RestAssetConfiguration restAssetConfig = (RestAssetConfiguration) conf;

				// Nikita ACE-8263 "Create Initial RSD" is NOT the default option anymore
				// Check for a specific radio button selection and set flags accordingly
				if (restSourceSelection.isImportFromSwaggerFile())
				{
					restAssetConfig.setImportFromSwaggerFile(true);
					restAssetConfig.setImportFromSwaggerURL(false);
					restAssetConfig.setImportFromSwaggerContent(false);
					restAssetConfig.setCreateRsdFile(false);

					restAssetConfig.setSwaggerFileNames(restSourceSelection.getSwaggerFileNames());
					restAssetConfig.setSwaggerSourcePath(restSourceSelection.getSwaggerSourcePath());
				}
				else if (restSourceSelection.isImportFromSwaggerURL())
				{
					restAssetConfig.setImportFromSwaggerFile(false);
					restAssetConfig.setImportFromSwaggerURL(true);
					restAssetConfig.setImportFromSwaggerContent(false);
					restAssetConfig.setCreateRsdFile(false);

					restAssetConfig
							.setSwaggerImportURLFileName(restSourceSelection.getSwaggerImportURLFileName());
					restAssetConfig.setSwaggerSourceURLContent(restSourceSelection.getSwaggerSourceURLContent());
				}
				else if (restSourceSelection.isImportFromSwaggerContent())
				{
					restAssetConfig.setImportFromSwaggerFile(false);
					restAssetConfig.setImportFromSwaggerURL(false);
					restAssetConfig.setImportFromSwaggerContent(true);
					restAssetConfig.setCreateRsdFile(false);

					restAssetConfig.setSwaggerImportContentFileName(
							restSourceSelection.getSwaggerImportContentFileName());
					restAssetConfig.setSwaggerContent(restSourceSelection.getSwaggerContent());
				}
				else if (restSourceSelection.isCreateInitialRSD())
				{
					restAssetConfig.setImportFromSwaggerFile(false);
					restAssetConfig.setImportFromSwaggerURL(false);
					restAssetConfig.setImportFromSwaggerContent(false);
					restAssetConfig.setCreateRsdFile(true);

					restAssetConfig.setRsdFileName(restSourceSelection.getRsdFileName());
				}
			}
		}

	}

	@Override
	protected Composite createMainContent(Composite parent)
	{
		Composite root = new Composite(parent, SWT.NONE);
		root.setLayout(new GridLayout());

		Control modelGrp = createModelGroup(root);
		GridData modelGrpLayout = new GridData(SWT.FILL, SWT.TOP | SWT.FILL, true, true);
		modelGrpLayout.heightHint = 350;
		modelGrp.setLayoutData(modelGrpLayout);

		return root;
	}

	/**
	 * Creates the Group composite containing the Model Details controls.
	 * 
	 * @param parent
	 * @return Control
	 */
	private Control createModelGroup(Composite parent)
	{
		// Create the group
		Group modelGrp = new Group(parent, SWT.NONE);
		modelGrp.setText(Messages.RestAssetWizardPage_DescriptorDetails_label);

		/* COnfigure the layout for the children of Details box */
		GridLayout grpGridLayout = new GridLayout();
		grpGridLayout.marginHeight = 8;
		grpGridLayout.marginWidth = 8;
		grpGridLayout.marginRight = 0;
		modelGrp.setLayout(grpGridLayout);

		Composite advancedGroup = restSourceSelection.createControls(modelGrp);
		if (advancedGroup != null)
		{
			// Set up the advanced group layout data
			advancedGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		}

		return modelGrp;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void projectPropertyChanged(PropertyChangeEvent evt)
	{
		super.projectPropertyChanged(evt);

		if (IAssetProjectPropertyChangeListener.PROJECTNAME_PROPERTY.equals(evt.getPropertyName()))
		{
			String oldName = (String) evt.getOldValue();
			if (oldName == null)
			{
				oldName = ""; //$NON-NLS-1$
			}

			String newName = (String) evt.getNewValue();
			if (newName == null)
			{
				newName = ""; //$NON-NLS-1$
			}
			restSourceSelection.projectNameChanged(oldName, newName);
			setPageComplete(validatePage());
		}
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

		/*
		 * ACE-8813 Validity is a combination of the restSourceSelection section and superclass's idea of validity
		 * state. (and use super.validatePage() so we don't infinite loop)
		 */
		setPageComplete(isValid && super.validatePage());
	}

	/**
	 * @see com.tibco.xpd.resources.projectconfig.projectassets.AbstractSpecialFolderAssetWizardPage#validatePage()
	 *
	 * @return
	 */
	@Override
	protected boolean validatePage()
	{
		/*
		 * Sid ACE-8813 Validity is a combination of the restSourceSelection section and superclass's idea of validity
		 * state.
		 */
		if (!super.validatePage())
		{
			return false;
		}

		String validityErrorMessage = restSourceSelection.getValidityErrorMessage();
		setErrorMessage(validityErrorMessage);

		return validityErrorMessage == null;
	}

	/**
	 * Sid ACE-8813 In the REST service project wizard, this service seletor page must be visited and completed before
	 * Finish can be pressed (i.e. no early finish on project name page).
	 * 
	 * @see com.tibco.xpd.resources.projectconfig.projectassets.AbstractSpecialFolderAssetWizardPage#validityRequiredForProjectCreate()
	 *
	 * @return
	 */
	@Override
	public boolean validityRequiredForProjectCreate()
	{
		return true;
	}

}
