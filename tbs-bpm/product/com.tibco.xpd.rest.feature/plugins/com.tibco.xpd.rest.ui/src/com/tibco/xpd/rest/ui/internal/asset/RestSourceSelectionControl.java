/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.rest.ui.internal.asset;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.eclipse.draw2d.Cursors;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.rest.schema.ui.RestConstants;
import com.tibco.xpd.rest.ui.RestServicesUtil;
import com.tibco.xpd.rest.ui.internal.Messages;
import com.tibco.xpd.rsd.wc.RsdWorkingCopy;
import com.tibco.xpd.ui.util.NameUtil;

import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.core.models.ParseOptions;
import io.swagger.v3.parser.core.models.SwaggerParseResult;

/**
 * A page to be included in wizards for specifying the external REST resources to import or create - either swagger
 * resources(using a file, URL, content) or a RSD file
 *
 * @author nkelkar
 * @since Jul 22, 2024
 */
public class RestSourceSelectionControl
{

	private Button								createModelRadio;

	private Label								lblFilename;

	private Text								txtFileName;

	private String								rsdFileName;

	private Button								createFromSwaggerRadio;

	private Label								lblSwaggerFiles;

	private Text								txtSwaggerFileNames;

	private Button								swaggerSchemaBrowse;

	private Button								createFromSwaggerURLRadio;

	private Label								lblSwaggerURL;

	private Text								txtSwaggerURL;

	private Label								lblSwaggerURLFileName;

	private Text								txtSwaggerURLFileName;

	private Button								createFromSwaggerContentRadio;

	private Text								txtSwaggerContent;

	private Label								lblSwaggerContent;

	private Label								lblSwaggerContentFileName;

	private Text								txtSwaggerContentFileName;

	private Composite							swaggerFileControls;

	private Composite							rootContainer;

	private Composite							swaggerURLControls;

	private Composite							createRSDControls;

	private Composite							swaggerContentControls;

	private URL									validSwaggerURL;

	private String								validSwaggerContent;

	protected String							swaggerSourcePath;

	private boolean								showCreateRSD;

	private List<SectionValidityListener>	changeListeners			= new ArrayList<SectionValidityListener>();

	protected static final int					TEXT_WIDTH_HINT			= 150;

	private static final String					FILE_NAMES_DELIMITER	= ", ";											//$NON-NLS-1$

	/*
	 * The default error message on import from swagger URL is defined here to be used at multiple places in the class.
	 * The error message might change based on the type of error.
	 */
	private String								importFromURLErrorMessage	= Messages.RestAssetWizardPage_InvalidURL_desc;

	/**
	 * Constructor that accepts a boolean to determine whether the 'Create Initial RSD' option should be shown
	 * 
	 * @param showCreateRSD
	 * @param showImportFromContent
	 */
	public RestSourceSelectionControl(boolean showCreateRSD)
	{
		rsdFileName = RestServicesUtil.RSD_DEFAULT_FILENAME;

		this.showCreateRSD = showCreateRSD;
	}

	/**
	 * Adds a new listener that will respond to a validity change event in this page
	 * 
	 * @param changeListener
	 */
	public void addValidityChangeListener(SectionValidityListener changeListener)
	{
		changeListeners.add(changeListener);
	}

	/**
	 * 
	 * @see org.eclipse.ui.part.Page#createControl(org.eclipse.swt.widgets.Composite)
	 *
	 * @param parent
	 * @return
	 */
	public Composite createControls(Composite parent)
	{
		// Create the root Container
		rootContainer = new Composite(parent, SWT.NONE);
		rootContainer.setLayout(new GridLayout(3, false));

		// Create option to import from Swagger File
		createImportFromSwaggerFileControls(rootContainer);

		// Create option to import from Swagger URL
		createImportFromSwaggerURLControls(rootContainer);

		// Create option to import from Swagger Content
		createImportFromSwaggerContentControls(rootContainer);

		// Create option to create the initial RSD model
		// only if the flag is set to true
		if (showCreateRSD)
		{
			createInitialRSDControls(rootContainer);
		}

		// On first entry to this dialog, 'Import from Swagger' is the selected option; Hide others
		showImportFromSwaggerFile(true);
		showImportFromSwaggerURL(false);
		showImportFromSwaggerContent(false);
		showCreateInitialRSD(false);

		return rootContainer;

	}

	/**
	 * Creates the controls for the option to create the Model using 'Import from Swagger File'
	 * 
	 * @param parent
	 * 
	 */
	private void createImportFromSwaggerFileControls(Composite parent)
	{
		createFromSwaggerRadio = new Button(parent, SWT.RADIO);
		createFromSwaggerRadio.setSelection(true);
		createFromSwaggerRadio.setText(Messages.RestAssetWizardPage_ImportSwaggerServices_label);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 3;
		createFromSwaggerRadio.setLayoutData(gd);
		// Add selection listener
		addRadioSelectionListener(createFromSwaggerRadio);

		// Container that holds all controls related to 'Import from swagger file'
		swaggerFileControls = new Composite(parent, SWT.NONE);
		swaggerFileControls.setLayout(new GridLayout(3, false));

		lblSwaggerFiles = new Label(swaggerFileControls, SWT.NONE);
		GridData lblGData = new GridData(SWT.LEAD, SWT.CENTER, false, true);
		lblSwaggerFiles.setLayoutData(lblGData);
		lblSwaggerFiles.setText(Messages.RestAssetWizardPage_Files_label);

		txtSwaggerFileNames = new Text(swaggerFileControls, SWT.BORDER);
		GridData gData = new GridData(SWT.FILL, SWT.CENTER, true, true);
		gData.widthHint = TEXT_WIDTH_HINT;
		txtSwaggerFileNames.setLayoutData(gData);
		txtSwaggerFileNames.setEditable(false);

		swaggerSchemaBrowse = new Button(swaggerFileControls, SWT.PUSH);
		swaggerSchemaBrowse.setLayoutData(new GridData(SWT.TRAIL, SWT.FILL, false, true));
		swaggerSchemaBrowse.setText(Messages.RestAssetWizardPage_Browse_label);

		swaggerSchemaBrowse.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				FileDialog fileDialog = showSelectFilesDialog();
				if (fileDialog != null)
				{
					String[] files = fileDialog.getFileNames();
					swaggerSourcePath = fileDialog.getFilterPath();

					txtSwaggerFileNames.setText(String.join(FILE_NAMES_DELIMITER, files));

					validateAndNotifyListeners();
				}
			}
		});
	}

	/**
	 * Creates the controls for the option to create the Model using 'Import from Swagger URL'
	 * 
	 * @param parent
	 * 
	 */
	private void createImportFromSwaggerURLControls(Composite parent)
	{
		createFromSwaggerURLRadio = new Button(parent, SWT.RADIO);
		createFromSwaggerURLRadio.setSelection(false);
		createFromSwaggerURLRadio.setText(Messages.RestAssetWizardPage_ImportSwagger_from_url_label);
		GridData gdModelRadio = new GridData(GridData.FILL_HORIZONTAL);
		gdModelRadio.verticalIndent = 5;
		gdModelRadio.horizontalSpan = 3;
		createFromSwaggerURLRadio.setLayoutData(gdModelRadio);
		// Add selection listener
		addRadioSelectionListener(createFromSwaggerURLRadio);

		// container that holds all controls related to 'Import from swagger URL'
		swaggerURLControls = new Composite(parent, SWT.NONE);
		swaggerURLControls.setLayout(new GridLayout(3, false));

		GridData labelGData = new GridData(SWT.LEAD, SWT.CENTER, false, false);
		GridData textGData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		textGData.widthHint = TEXT_WIDTH_HINT;
		textGData.horizontalSpan = 2;

		lblSwaggerURL = new Label(swaggerURLControls, SWT.NONE);
		lblSwaggerURL.setLayoutData(labelGData);
		lblSwaggerURL.setText(Messages.RestAssetWizardPage_URL_label);

		txtSwaggerURL = new Text(swaggerURLControls, SWT.BORDER);
		txtSwaggerURL.setLayoutData(textGData);

		lblSwaggerURLFileName = new Label(swaggerURLControls, SWT.NONE);
		lblSwaggerURLFileName.setLayoutData(labelGData);
		lblSwaggerURLFileName.setText(Messages.RestAssetWizardPage_FilaName_label);

		txtSwaggerURLFileName = new Text(swaggerURLControls, SWT.BORDER);
		txtSwaggerURLFileName.setLayoutData(textGData);

		txtSwaggerURL.addModifyListener(new ModifyListener()
		{

			@Override
			public void modifyText(ModifyEvent e)
			{
				validSwaggerURL = null;
				try
				{
					// new URL(...) - will throw a 'MalformedURLException' when it finds a malformed syntax in the input
					// String object.
					URL validURL = new URL(txtSwaggerURL.getText());

					// toURI() - This is done to ensure that any URL string that complies with RC 2396 is converted to
					// URL. If the String passed doesn't qualify for the URL syntax, a 'URISyntaxException' will be
					// thrown
					validURL.toURI();

					// validSwaggerURL is being set here to be available for focus lost listener below to check content
					// of URL.
					validSwaggerURL = validURL;
					validateAndNotifyListeners();
				}
				catch (URISyntaxException | MalformedURLException e1)
				{
					validSwaggerURL = null;
					importFromURLErrorMessage = Messages.RestAssetWizardPage_InvalidURL_desc;

					validateAndNotifyListeners();
				}
			}
		});

		txtSwaggerURL.addFocusListener(new FocusListener()
		{
			@Override
			public void focusGained(FocusEvent e)
			{
			}

			@Override
			@SuppressWarnings("nls")
			public void focusLost(FocusEvent e)
			{
				if (validSwaggerURL != null)
				{
					Shell activeShell = getShell();
					Cursor oldCursor = null;

					// Set cursor to 'waiting'
					if (activeShell != null)
					{
						oldCursor = activeShell.getCursor();
						activeShell.setCursor(Cursors.WAIT);
					}
					try
					{

						OpenAPI swaggerModel = getSwaggerModelFromURL(validSwaggerURL.toString());

						// If the URL contains a valid Swagger Model, determine the file name; else show an error for
						// invalid content at URL
						if (swaggerModel != null)
						{
							String[] urlParts = validSwaggerURL.getPath().split("/");
							if (urlParts.length > 0)
							{
								String filename = urlParts[urlParts.length - 1];
								if (filename.endsWith("." + RestConstants.JSON_FILE_EXTENSION)
										|| filename.endsWith("." + RestConstants.YAML_FILE_EXTENSION))
								{
									txtSwaggerURLFileName.setText(filename);
								}
							}
						}
						else
						{
							validSwaggerURL = null;
							importFromURLErrorMessage = Messages.RestAssetWizardPage_InvalidURLContent_desc;
							validateAndNotifyListeners();
						}
					}
					finally
					{
						// Reset the cursor
						if (activeShell != null)
						{
							activeShell.setCursor(oldCursor);
						}
					}
				}
			}

		});

		txtSwaggerURLFileName.addModifyListener(new ModifyListener()
		{
			@Override
			public void modifyText(ModifyEvent e)
			{
				validateAndNotifyListeners();
			}
		});
	}

	/**
	 * Creates the controls for the option to create the Model using 'Import from Swagger URL'
	 * 
	 * @param parent
	 */
	private void createImportFromSwaggerContentControls(Composite parent)
	{

		createFromSwaggerContentRadio = new Button(parent, SWT.RADIO);
		createFromSwaggerContentRadio.setSelection(false);
		createFromSwaggerContentRadio.setText(Messages.RestAssetWizardPage_ImportSwagger_from_content_label);
		GridData gdModelRadio = new GridData(GridData.FILL_HORIZONTAL);
		gdModelRadio.verticalIndent = 5;
		gdModelRadio.horizontalSpan = 3;
		createFromSwaggerContentRadio.setLayoutData(gdModelRadio);
		// Add selection listener
		addRadioSelectionListener(createFromSwaggerContentRadio);

		// container that holds all controls related to 'Import from swagger URL'
		swaggerContentControls = new Composite(parent, SWT.NONE);
		swaggerContentControls.setLayout(new GridLayout(3, false));

		GridData labelGData = new GridData(SWT.LEAD, SWT.TOP, false, false);
		GridData textGData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		textGData.widthHint = TEXT_WIDTH_HINT;
		textGData.horizontalSpan = 2;

		lblSwaggerContent = new Label(swaggerContentControls, SWT.NONE);
		lblSwaggerContent.setLayoutData(labelGData);
		lblSwaggerContent.setText(Messages.RestAssetWizardPage_Content_label);

		txtSwaggerContent = new Text(swaggerContentControls, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL);
		GridData contentTextLayout = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		contentTextLayout.heightHint = 100;
		txtSwaggerContent.setLayoutData(contentTextLayout);

		lblSwaggerContentFileName = new Label(swaggerContentControls, SWT.NONE);
		lblSwaggerContentFileName.setLayoutData(labelGData);
		lblSwaggerContentFileName.setText(Messages.RestAssetWizardPage_FilaName_label);

		txtSwaggerContentFileName = new Text(swaggerContentControls, SWT.BORDER);
		txtSwaggerContentFileName.setLayoutData(textGData);

		txtSwaggerContent.addModifyListener(new ModifyListener()
		{
			@Override
			public void modifyText(ModifyEvent e)
			{
				if (txtSwaggerContent.getText() != null)
				{
					Shell activeShell = getShell();
					Cursor oldCursor = null;

					// Set cursor to 'waiting'
					if (activeShell != null)
					{
						oldCursor = activeShell.getCursor();
						activeShell.setCursor(Cursors.WAIT);
					}
					try
					{

						OpenAPI swaggerModel = getSwaggerModelFromStringContent(txtSwaggerContent.getText());

						// If the text is valid Swagger/OAS content
						if (swaggerModel != null)
						{
							validSwaggerContent = txtSwaggerContent.getText();
						}
						else
						{
							validSwaggerContent = null;
						}
						validateAndNotifyListeners();
					}
					finally
					{
						// Reset the cursor
						if (activeShell != null)
						{
							activeShell.setCursor(oldCursor);
						}
					}
				}
			}
		});

		txtSwaggerContentFileName.addModifyListener(new ModifyListener()
		{
			@Override
			public void modifyText(ModifyEvent e)
			{
				validateAndNotifyListeners();
			}
		});

	}
	/**
	 * Creates the controls for the option to create the Model using a RSD File
	 * 
	 * @param parent
	 */
	private void createInitialRSDControls(Composite parent)
	{
		createModelRadio = new Button(parent, SWT.RADIO);
		createModelRadio.setSelection(false);
		createModelRadio.setText(Messages.RestAssetWizardPage_CreateInitialRsd_label);
		GridData gdModelRadio = new GridData(GridData.FILL_HORIZONTAL);
		gdModelRadio.verticalIndent = 5;
		gdModelRadio.horizontalSpan = 3;
		createModelRadio.setLayoutData(gdModelRadio);
		// Add selection listener
		addRadioSelectionListener(createModelRadio);

		// container that holds all controls related to 'Create initial RSD'
		createRSDControls = new Composite(parent, SWT.NONE);
		createRSDControls.setLayout(new GridLayout(3, false));

		lblFilename = new Label(createRSDControls, SWT.NONE);
		GridData lblGData = new GridData(SWT.LEAD, SWT.CENTER, false, false);
		lblFilename.setLayoutData(lblGData);
		lblFilename.setText(Messages.RestAssetWizardPage_FilaName_label);

		txtFileName = new Text(createRSDControls, SWT.BORDER);
		GridData gData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gData.widthHint = TEXT_WIDTH_HINT;
		gData.horizontalSpan = 2;
		txtFileName.setLayoutData(gData);

		if (rsdFileName != null && rsdFileName.length() > 0)
		{
			txtFileName.setText(rsdFileName);
		}
		else
		{
			txtFileName.setText(RestServicesUtil.RSD_DEFAULT_FILENAME);
		}

		txtFileName.addModifyListener(new ModifyListener()
		{
			@Override
			public void modifyText(ModifyEvent e)
			{
				rsdFileName = txtFileName.getText();
				validateAndNotifyListeners();
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

				if (!name.endsWith(RsdWorkingCopy.RSD_FILE_EXTENSION))
				{
					txtFileName.setText(name + "." + RsdWorkingCopy.RSD_FILE_EXTENSION); //$NON-NLS-1$
				}
			}

		});
	}

	/**
	 * Adds a selection listener to radio buttons
	 * 
	 * @param aRadioButton
	 */
	private void addRadioSelectionListener(Button aRadioButton)
	{
		aRadioButton.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				super.widgetSelected(e);
				Button source = (Button) e.getSource();
				if (e.getSource() == aRadioButton)
				{
					if (source.getSelection())
					{
						showImportFromSwaggerFile(e.getSource() == createFromSwaggerRadio);
						showImportFromSwaggerURL(e.getSource() == createFromSwaggerURLRadio);
						showImportFromSwaggerContent(e.getSource() == createFromSwaggerContentRadio);
						showCreateInitialRSD(e.getSource() == createModelRadio);						

						// Validate the page
						validateAndNotifyListeners();

						// Re-layout the root container so that the visibility of child controls is updated correctly
						rootContainer.requestLayout();
					}
				}
			}
		});
	}

	/**
	 * Returns the Grid data for a container based on the visibility flag
	 * 
	 * @param isContainerEnabled
	 * @return
	 */
	private GridData getContainerGridLayout(boolean isContainerVisible)
	{
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 3;
		gd.horizontalIndent = 15;
		if (!isContainerVisible)
		{
			// Hide the container if the flag is set to 'false'
			gd.heightHint = 1;
		}
		
		return gd;
	}
	/**
	 * Shows/hides all controls related to the 'Import from Swagger File' option based on the 'isVisible' flag
	 */
	private void showImportFromSwaggerFile(boolean isVisible)
	{
		swaggerFileControls.setLayoutData(getContainerGridLayout(isVisible));
		swaggerFileControls.setVisible(isVisible);
		swaggerFileControls.setEnabled(isVisible);
	}

	/**
	 * Returns true if the 'Import from Swagger File' option is selected
	 * 
	 * @return
	 */
	public boolean isImportFromSwaggerFile()
	{
		return createFromSwaggerRadio != null && createFromSwaggerRadio.getSelection();
	}

	/**
	 * Shows/Hides all controls related to the 'Import from Swagger URL' option based on the 'isVisible' flag
	 */
	private void showImportFromSwaggerURL(boolean isVisible)
	{
		swaggerURLControls.setLayoutData(getContainerGridLayout(isVisible));
		swaggerURLControls.setVisible(isVisible);
		swaggerURLControls.setEnabled(isVisible);
	}

	/**
	 * Returns true if the 'Import from Swagger URL' option is selected
	 * 
	 * @return
	 */
	public boolean isImportFromSwaggerURL()
	{
		return createFromSwaggerURLRadio != null && createFromSwaggerURLRadio.getSelection();
	}

	/**
	 * Shows/Hides all controls related to the 'Create Initial RSD' option based on the 'isVisible' flag
	 */
	private void showCreateInitialRSD(boolean isVisible)
	{
		if (showCreateRSD)
		{
			createRSDControls.setLayoutData(getContainerGridLayout(isVisible));
			createRSDControls.setVisible(isVisible);
			createRSDControls.setEnabled(isVisible);
		}
	}

	/**
	 * Returns true if the 'Create Initial RSD' option is selected
	 * 
	 * @return
	 */
	public boolean isCreateInitialRSD()
	{
		return createModelRadio != null && createModelRadio.getSelection();
	}

	/**
	 * Shows/Hides all controls related to the 'Import from Swagger Content' option based on the 'isVisible' flag
	 */
	private void showImportFromSwaggerContent(boolean isVisible)
	{
		swaggerContentControls.setLayoutData(getContainerGridLayout(isVisible));
		swaggerContentControls.setVisible(isVisible);
		swaggerContentControls.setEnabled(isVisible);
	}

	/**
	 * Returns true if the 'Import from Swagger URL' option is selected
	 * 
	 * @return
	 */
	public boolean isImportFromSwaggerContent()
	{
		return createFromSwaggerContentRadio != null && createFromSwaggerContentRadio.getSelection();
	}
	/**
	 * Open a file selection dialog and return the dialog instance
	 * 
	 * @return the opened dialog instance
	 */
	protected FileDialog showSelectFilesDialog()
	{
		Shell activeShell = getShell();
		if (activeShell != null)
		{
			FileDialog dlg = new FileDialog(activeShell, SWT.OPEN | SWT.MULTI);
			String[] validFileExtensions = new String[]{"*.json;*.yaml"}; //$NON-NLS-1$ //$NON-NLS-2$
			dlg.setFilterExtensions(validFileExtensions);

			if (dlg.open() != null)
			{
				return dlg;
			}
		}
		return null;
	}

	/**
	 * Returns the currently active Shell
	 * 
	 * @return
	 */
	private Shell getShell()
	{
		return Display.getDefault().getActiveShell();
	}

	/**
	 * Validate the entries on this page. It also notifies all validity change listeners
	 * 
	 * Sid ACE-8813 Separate validating the page from notifying the listeners. When asked by a consumer what the page
	 * state is we don't want to notify listsners. When we detect a modification in the controls in section then we do.
	 * 
	 * @return <code>true</code> if page valid else <code>false</code>
	 */
	private boolean validateAndNotifyListeners()
	{
		String validityErrorMessage = getValidityErrorMessage();

		boolean isValid = validityErrorMessage == null;

		notifyValidityChangeListeners(isValid, validityErrorMessage);

		return isValid;
	}

	/**
	 * Sid ACE-8813 Validate the entries on this page by checking and returning a erro rmessage or <code>null</code> if valid.
	 * 
	 * Allows the consumer to check 'isValid' AND get reason at the same time.
	 * 
	 * This function does not notify listeners (as it is used to ASK for validity status rather than check validity
	 * status after a control change within this section itself.
	 * 
	 * @return Message if the section controls are not complete and valid else <code>null</code>
	 */
	public String getValidityErrorMessage()
	{
		String errMsg = null;

		// If the 'Import from Swagger File' option is selected, validate that at least one file has been selected
		if (createFromSwaggerRadio != null && createFromSwaggerRadio.getSelection())
		{
			if (!inputIsNotEmpty(txtSwaggerFileNames))
			{
				errMsg = Messages.RestAssetWizardPage_MissingFileName_desc;
			}
		}
		else if (createFromSwaggerURLRadio != null && createFromSwaggerURLRadio.getSelection())
		{
			// If the 'Import from Swagger URL' option is selected, validate the URL and filename
			if (validSwaggerURL == null)
			{
				if (!inputIsNotEmpty(txtSwaggerURL))
				{
					errMsg = Messages.RestAssetWizardPage_MissingURL_desc;
				}
				else
				{
					errMsg = importFromURLErrorMessage;
				}
			}
			else if (!inputIsNotEmpty(txtSwaggerURLFileName))
			{
				errMsg = Messages.RestAssetWizardPage_MissingURLFileName_desc;
			}
			else if (isValidFileName(txtSwaggerURLFileName.getText()))
			{
				errMsg = Messages.RestAssetWizardPage_InvalidFileName_desc;
			}
		}
		else if (createFromSwaggerContentRadio != null && createFromSwaggerContentRadio.getSelection())
		{
			// If the 'Import from Swagger Content' option is selected, validate that some content is entered
			if (validSwaggerContent == null)
			{
				if (!inputIsNotEmpty(txtSwaggerContent))
				{
					errMsg = Messages.RestAssetWizardPage_MissingSwaggerContent_desc;
				}
				else
				{
					errMsg = Messages.RestAssetWizardPage_InvalidContent_desc;
				}
			}
			else if (!inputIsNotEmpty(txtSwaggerContentFileName))
			{
				errMsg = Messages.RestAssetWizardPage_MissingURLFileName_desc;
			}
			else if (isValidFileName(txtSwaggerContentFileName.getText()))
			{
				errMsg = Messages.RestAssetWizardPage_InvalidFileName_desc;
			}
		}
		/* Sid ACE-8813 Added missing validity check on RSD file name */
		else if (createModelRadio != null && createModelRadio.getSelection())
		{
			if (!inputIsNotEmpty(txtFileName))
			{
				errMsg = Messages.RestAssetWizardPage_InvalidFileName_desc;
			}
			else if (isValidFileName(txtFileName.getText())
					|| ("." + RsdWorkingCopy.RSD_FILE_EXTENSION).equals(txtFileName.getText().trim())) //$NON-NLS-1$
			{
				errMsg = Messages.RestAssetWizardPage_InvalidFileName_desc;
			}

		}
		else
		{
			// Default is 'no file selected for default option if controls not created yet.
			errMsg = Messages.RestAssetWizardPage_MissingFileName_desc;
		}

		return errMsg;
	}

	/**
	 * Returns true if the filename is valid, i.e., it contains only alphabets, numerics and underscore; false otherwise
	 * 
	 * @param aFileName
	 * @return
	 */
	private boolean isValidFileName(String aFileName)
	{
		// Split the filename at the last dot to separate the base name and extension
        int dotIndex = aFileName.lastIndexOf('.');
        String baseName = (dotIndex == -1) ? aFileName : aFileName.substring(0, dotIndex);
        
		return !NameUtil.isValidName(baseName, true);
	}
	/**
	 * Notify all listeners of the page validity change event
	 * 
	 * @param isValid
	 * @param errorMsg
	 */
	private void notifyValidityChangeListeners(boolean isValid, String errorMsg)
	{
		// Notify everybody that may be interested.
		for (SectionValidityListener listener : changeListeners)
		{
			listener.validityChanged(isValid, errorMsg);
		}
	}
	/**
	 * Returns true if the text input box is not null and not empty; false otherwise
	 * 
	 * @param aInput
	 * @return
	 */
	private boolean inputIsNotEmpty(Text aInput)
	{
		return aInput != null && !aInput.getText().isEmpty();
	}

	/**
	 * When the project name change, update the name of the RSD file accordingly
	 * 
	 * @param oldName
	 * @param newName
	 */
	public void projectNameChanged(String oldName, String newName)
	{
		/*
		 * If current package file name is unset, default or equivalent same-name for previously selected project name
		 * then update it to equivalent of new project name.
		 */
		String nameForOldName = oldName + "." + RsdWorkingCopy.RSD_FILE_EXTENSION; //$NON-NLS-1$

		if (rsdFileName == null || rsdFileName.length() == 0
				|| rsdFileName.equals(RestServicesUtil.RSD_DEFAULT_FILENAME) || rsdFileName.equals(nameForOldName))
		{

			rsdFileName = newName + "." + RsdWorkingCopy.RSD_FILE_EXTENSION; //$NON-NLS-1$

			if (txtFileName != null && !txtFileName.isDisposed())
			{
				txtFileName.setText(rsdFileName);
			}
		}

		validateAndNotifyListeners();
	}

	/**
	 * Returns the Swagger/OAS OpenAPI model if the passed in URL is a valid URL that points to a swagger resource; null
	 * otherwise
	 * 
	 * @param swaggerURL
	 * @return
	 */
	private OpenAPI getSwaggerModelFromURL(String swaggerURL)
	{
		OpenAPIParser openAPIParser = new OpenAPIParser();

		// Validate the Swagger/OAS File
		SwaggerParseResult swaggerResult = openAPIParser.readLocation(swaggerURL, null, null);
		OpenAPI openAPI = swaggerResult.getOpenAPI();
		// Get the validation errors and warnings during the parsing
		List<String> errorMessages = swaggerResult.getMessages();

		if ((errorMessages != null && !errorMessages.isEmpty()) || openAPI == null)
		{
			return null;
		}
		return openAPI;
	}

	/**
	 * Returns the Swagger/OAS OpenAPI model if the passed in String is valid Swagger content; null otherwise
	 * 
	 * @param stringContent
	 * @return
	 */
	private OpenAPI getSwaggerModelFromStringContent(String stringContent)
	{
		OpenAPIParser openAPIParser = new OpenAPIParser();

		ParseOptions options = new ParseOptions();
		options.setInferSchemaType(true);

		SwaggerParseResult swaggerResult = openAPIParser.readContents(stringContent, null, options);
		OpenAPI openAPI = swaggerResult.getOpenAPI();
		// Get the validation errors and warnings during the parsing
		List<String> errorMessages = swaggerResult.getMessages();

		if ((errorMessages != null && !errorMessages.isEmpty()) || openAPI == null)
		{
			return null;
		}
		return openAPI;
	}

	/* The following methods are all getters to return the control values */

	/**
	 * @return rsdFileName
	 */
	public String getRsdFileName()
	{
		return rsdFileName;
	}

	/**
	 * @return text in txtSwaggerURLFileName
	 */
	public String getSwaggerImportURLFileName()
	{
		return txtSwaggerURLFileName.getText();
	}

	/**
	 * @return validSwaggerURL
	 */
	public URL getSwaggerSourceURL()
	{
		return validSwaggerURL;
	}

	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	public String getSwaggerSourceURLContent()
	{
		try
		{
			return IOUtils.toString(validSwaggerURL.openStream(), StandardCharsets.UTF_8);
		}
		catch (IOException e)
		{
			return null;
		}
	}

	/**
	 * @return array of strings - derived using the text in txtSwaggerFileNames split by comma(,)
	 */
	public String[] getSwaggerFileNames()
	{
		return txtSwaggerFileNames.getText().split(FILE_NAMES_DELIMITER);
	}

	/**
	 * @return swaggerSourcePath
	 */
	public String getSwaggerSourcePath()
	{
		return swaggerSourcePath;
	}

	/**
	 * @return text in txtSwaggerContentFileName
	 */
	public String getSwaggerImportContentFileName()
	{
		return txtSwaggerContentFileName.getText();
	}

	/**
	 * @return validSwaggerContent
	 */
	public String getSwaggerContent()
	{
		return validSwaggerContent;
	}
}
