/*
* Copyright (c) 2004-2023. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.processscriptlibrary.resource.wizards;

import java.beans.PropertyChangeEvent;

import org.eclipse.core.resources.IFolder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.processscriptlibrary.resource.ProcessScriptLibraryResourcePluginActivtor;
import com.tibco.xpd.processscriptlibrary.resource.config.ProcessScriptLibraryConstants;
import com.tibco.xpd.processscriptlibrary.resource.editor.util.PslEditorUtil;
import com.tibco.xpd.processscriptlibrary.resource.internal.Messages;
import com.tibco.xpd.processscriptlibrary.resource.project.ProcessScriptLibraryAssetConfiguration;
import com.tibco.xpd.resources.projectconfig.projectassets.AbstractSpecialFolderAssetWizardPage;
import com.tibco.xpd.resources.projectconfig.projectassets.IAssetProjectPropertyChangeListener;
import com.tibco.xpd.resources.projectconfig.projectassets.SpecialFolderAssetConfiguration;

/**
 * The Wizard Page for creation of Process Script Library , which is chained into New Project Wizard.
 *
 * @author ssirsika
 * @since 06-Dec-2023
 */
public class ProcessScriptLibraryAssetWizardPage extends AbstractSpecialFolderAssetWizardPage
{
	private String						pslFileName				= null;

	private Text						txtFileName				= null;

	private Button						createModelCheckbox		= null;

	/*
	 * Constant for 'JavaScript' grammar type.
	 */
	private static final String			JAVASCRIPT				= "JavaScript";		//$NON-NLS-1$

	/** The destination environment xpdl2 extended attribute name. */
	public static final String			DESTINATION_ATTRIBUTE	= "Destination";	//$NON-NLS-1$

	/*
	 * Stores the xpdl package reference.
	 */
	private com.tibco.xpd.xpdl2.Package	xpdl2Package;

	/**
	 * @param id
	 */
	public ProcessScriptLibraryAssetWizardPage()
	{
		super("PSLAssetWizard"); //$NON-NLS-1$

		setTitle(Messages.ProcessScriptLibraryAssetWizardPage_title);
		setDescription(Messages.ProcessScriptLibraryAssetWizardPage_desc);
		pslFileName = Messages.ProcessScriptLibrary_DefaultPslFile_name;

		/* Sid ACE-7330: Show correct wizard banner icon for asset type. */
		setImageDescriptor(ProcessScriptLibraryResourcePluginActivtor.imageDescriptorFromPlugin(
				ProcessScriptLibraryResourcePluginActivtor.PLUGIN_ID, "icons/obj16/processScriptsFileWizard.png"));

		createPackage();
	}

	/**
	 * @see com.tibco.xpd.resources.projectconfig.projectassets.IAssetWizardPage#updateConfiguration()
	 *
	 */
	@Override
	public void updateConfiguration()
	{
		ProcessScriptLibraryAssetConfiguration assetConfig = getAssetConfig();

		assetConfig.setXpdl2Package(xpdl2Package);

		assetConfig.setSpecialFolderName(getSpecialFolderName());

		assetConfig.setPslFileName(pslFileName);

		if (createModelCheckbox == null)
		{
			assetConfig.setCreatePslFile(true);
		}
		else
		{
			assetConfig.setCreatePslFile(createModelCheckbox.getSelection());
		}
	}

	@Override
	protected Composite createMainContent(Composite parent)
	{
		Composite root = new Composite(parent, SWT.NONE);
		root.setLayout(new GridLayout());

		Control modelGrp = createModelGroup(root);
		modelGrp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		return root;
	}

	/**
	 * @param root
	 * @return
	 */
	private Control createModelGroup(Composite parent)
	{
		// Create the group
		Group modelGrp = new Group(parent, SWT.NONE);
		modelGrp.setText(Messages.ProcessScriptLibrary_DescriptorDetails_label);
		modelGrp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		GridLayout grpGridLayout = new GridLayout();
		grpGridLayout.marginHeight = 8;
		grpGridLayout.marginWidth = 8;
		grpGridLayout.horizontalSpacing = 8;
		grpGridLayout.verticalSpacing = 8;
		grpGridLayout.marginRight = 0;
		grpGridLayout.numColumns = 2;
		modelGrp.setLayout(grpGridLayout);

		// Create checkbox for option to create the initial model
		createModelCheckbox = new Button(modelGrp, SWT.CHECK);
		createModelCheckbox.setSelection(true);
		createModelCheckbox.setText(Messages.ProcessScriptLibraryWizardPage_CreateInitialPsl_label);
		GridData gdModelCheckBox = new GridData(GridData.FILL_HORIZONTAL);
		gdModelCheckBox.horizontalSpan = 2;
		createModelCheckbox.setLayoutData(gdModelCheckBox);

		final Label lblFilename = new Label(modelGrp, SWT.NONE);
		lblFilename.setLayoutData(new GridData());
		lblFilename.setText(Messages.ProcessScriptLibraryWizardPage_FilaName_label);

		txtFileName = new Text(modelGrp, SWT.BORDER);
		GridData gData = new GridData(GridData.FILL_HORIZONTAL);
		gData.widthHint = TEXT_WIDTH_HINT;
		txtFileName.setLayoutData(gData);

		if (pslFileName != null && pslFileName.length() > 0)
		{
			txtFileName.setText(pslFileName);
		}
		else
		{
			txtFileName.setText(Messages.ProcessScriptLibrary_DefaultPslFile_name);
		}

		txtFileName.addModifyListener(new ModifyListener()
		{
			@Override
			public void modifyText(ModifyEvent e)
			{
				pslFileName = txtFileName.getText();
			}
		});

		txtFileName.addFocusListener(new FocusListener()
		{
			@Override
			public void focusGained(FocusEvent e)
			{
			}

			@Override
			public void focusLost(FocusEvent e)
			{
				String name = txtFileName.getText();

				if (!name.endsWith(ProcessScriptLibraryConstants.PSL_FILE_EXTENSION))
				{
					txtFileName.setText(name + "." + ProcessScriptLibraryConstants.PSL_FILE_EXTENSION); //$NON-NLS-1$
				}
			}

		});

		Composite schemaComp = new Composite(modelGrp, SWT.NONE);
		GridData gdSchemaComp = new GridData();
		gdSchemaComp.horizontalSpan = 2;
		schemaComp.setLayoutData(gdSchemaComp);
		GridLayout layoutSchemaComp = new GridLayout();
		layoutSchemaComp.numColumns = 1;
		schemaComp.setLayout(layoutSchemaComp);

		createModelCheckbox.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				super.widgetSelected(e);
				if (e.getSource() == createModelCheckbox)
				{
					Button source = (Button) e.getSource();
					if (txtFileName != null)
					{
						if (!source.getSelection())
						{
							lblFilename.setEnabled(false);
							txtFileName.setEnabled(false);
						}
						else
						{
							lblFilename.setEnabled(true);
							txtFileName.setEnabled(true);
						}
					}
				}
			}
		});

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

			/*
			 * If current package file name is unset, default or equivalent same-name for previously selected project
			 * name then update it to equivalent of new project name.
			 */
			String nameForOldName = oldName + Messages.PslEditorUtil_FileNameSuffix + "." //$NON-NLS-1$
					+ ProcessScriptLibraryConstants.PSL_FILE_EXTENSION;

			if (pslFileName == null || pslFileName.length() == 0
					|| pslFileName.equals(
							Messages.ProcessScriptLibrary_DefaultPslFile_name)
					|| pslFileName.equals(nameForOldName))
			{

				pslFileName = newName + Messages.PslEditorUtil_FileNameSuffix + "." //$NON-NLS-1$
						+ ProcessScriptLibraryConstants.PSL_FILE_EXTENSION;

				if (txtFileName != null && !txtFileName.isDisposed())
				{
					txtFileName.setText(pslFileName);
				}

				setPageComplete(validatePage());
			}
		}

	}

	/**
	 * @see com.tibco.xpd.resources.projectconfig.projectassets.AbstractSpecialFolderAssetWizardPage#getDefaultSpecialFolderName()
	 *
	 * @return
	 */
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
				folderName = Messages.ProcessScriptLibraryAssetConfigurator_DefaultFolderName;
			}
		}

		return folderName;
	}

	/**
	 * Create process package for default psl file contents.
	 */
	private void createPackage()
	{
		if (xpdl2Package == null)
		{
			xpdl2Package = PslEditorUtil.createPSLModel();
		}
	}

	/**
	 * Get the process script library asset configuration object.
	 * 
	 * @return
	 * @throws IllegalArgumentException
	 *             If the wrong type asset configuration object is received.
	 */
	private ProcessScriptLibraryAssetConfiguration getAssetConfig()
	{
		Object configuration = getConfiguration();

		if (configuration instanceof ProcessScriptLibraryAssetConfiguration)
		{
			return (ProcessScriptLibraryAssetConfiguration) configuration;
		}
		else
		{
			throw new IllegalArgumentException("Incorrect asset configuration."); //$NON-NLS-1$
		}
	}
}
