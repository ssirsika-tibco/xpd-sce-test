/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.importexport.pluginGenerator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.importexport.ImportExportPluginConstants;
import com.tibco.xpd.importexport.internal.Messages;
import com.tibco.xpd.importexport.pluginGenerator.IPluginData.IWizardDataProvider;
import com.tibco.xpd.importexport.pluginGenerator.NewPluginGeneratorWizard.WizardType;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;

/**
 * The wizard information collection page of the generate new import/export
 * wizard
 * 
 * @author njpatel
 */
public class WizardInfoPage extends AbstractXpdWizardPage implements IWizardDataProvider,
        ModifyListener, SelectionListener {

    private Text txtWizardTitle = null;

    private String wizardTitle = ""; //$NON-NLS-1$

    private Text txtWizardDesc = null;

    private String wizardDesc = ""; //$NON-NLS-1$

    private Text txtOutputFileExt = null;

    private String outputFileExt = ""; //$NON-NLS-1$

    private Text txtXSLT = null;

    private String xslt = ""; //$NON-NLS-1$

    private Button btnXSLTBrowse = null;

    private Text txtSchema = null;

    private String schema = ""; //$NON-NLS-1$

    private Composite exportFolderContainer;

    private Button chkExportFolder = null;

    private Text txtExportFolder = null;

    private String exportFolder = ""; //$NON-NLS-1$

    private Button btnSchemaBrowse = null;

    private Label lblIconPreview = null;

    private Text txtIcon = null;

    private String icon = ""; //$NON-NLS-1$

    private Button btnIconBrowse = null;

    private Image imgIcon = null;

    private Image imgIconPlaceHolder = null;

    /*
     * Icon size
     */
    private static final int ICONWIDTH = 16;

    private static final int ICONHEIGHT = 16;

    private File iconFile = null;

    private File xsltFile = null;

    private File schemaFile = null;

    private Combo cmbCategory;

    private String categoryId;

    /**
     * Create the Import Wizard information collection page
     * 
     * @param pageName
     * @param titleImage
     */
    public WizardInfoPage(String pageName, ImageDescriptor titleImage) {
        super(pageName);
        if (titleImage != null) {
            setImageDescriptor(titleImage);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl(Composite parent) {
        Composite container = new Composite(parent, SWT.NONE);
        container.setLayout(new GridLayout());

        // Create import wizard info control
        Composite exportControl = createExportInfoControl(container);
        exportControl.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        // Create the category selection control
        Composite categoryControl = createCategoryControl(container);
        categoryControl.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        // Create the icon selection control
        Composite iconControl = createIconSelectionControl(container);
        iconControl.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        // Create export folder selection control
        exportFolderContainer = createExportFolderControl(container);
        exportFolderContainer.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL));

        setControl(container);

        // Set page to incomplete
        setMessage(Messages.WizardInfoPage_default_message);
           
        txtExportFolder.setText(txtWizardTitle.getText());
        doValidatePage();       
    }

    @Override
    public void setVisible(boolean visible) {

        if (visible) {
            WizardType wizardType = getWizardType();

            // Update the category selection combo
            updateCategoryList(wizardType);

            /*
             * If creating an export folder then show the project export folder
             * section, otherwise hide it
             */
            if (wizardType.equals(WizardType.EXPORT)) {
                exportFolderContainer.setVisible(true);
                setTitle(Messages.WizardInfoPage_exportWizard_title);
            } else {
                exportFolderContainer.setVisible(false);
                setTitle(Messages.WizardInfoPage_importWizard_title);
            }
        }

        super.setVisible(visible);
    }

    /**
     * Create the export wizard information collection group
     * 
     * @param parent
     */
    private Composite createExportInfoControl(Composite parent) {

        Group grpExport = new Group(parent, SWT.NONE);
        grpExport.setText(Messages.WizardInfoPage_wizardInfoGroup_label);

        GridLayout gridLayout = new GridLayout(3, false);
        gridLayout.horizontalSpacing = 10;
        gridLayout.marginTop = 5;
        gridLayout.marginBottom = 5;
        gridLayout.marginLeft = 5;
        gridLayout.marginRight = 5;
        grpExport.setLayout(gridLayout);

        // Wizard Title
        Label lblWizardTitle = new Label(grpExport, SWT.NONE);
        lblWizardTitle.setText(Messages.WizardInfoPage_title_label);

        txtWizardTitle = createText(grpExport, 2);
        txtWizardTitle.setText(Messages.WizardInfoPage_default_title);
        txtWizardTitle.addModifyListener(this);

        // Wizard Description
        Label lblWizardDesc = new Label(grpExport, SWT.NONE);
        lblWizardDesc.setText(Messages.WizardInfoPage_description_label);

        txtWizardDesc = createText(grpExport, 2);
        txtWizardDesc.setText(Messages.WizardInfoPage_default_desc);
        txtWizardDesc.addModifyListener(this);

        // File extension filter
        Label lblOutputFileExt = new Label(grpExport, SWT.NONE);
        lblOutputFileExt.setText(Messages.WizardInfoPage_outputFileExt_label);

        txtOutputFileExt = createText(grpExport, 2);
        txtOutputFileExt.setText(Messages.WizardInfoPage_default_file_ext_notrans);
        txtOutputFileExt.addModifyListener(this);

        // XSLT
        Label lblXSLT = new Label(grpExport, SWT.NONE);
        lblXSLT.setText(Messages.WizardInfoPage_xslt_label);

        txtXSLT = createText(grpExport, 1);
        txtXSLT.addModifyListener(this);

        btnXSLTBrowse = new Button(grpExport, SWT.NONE);
        btnXSLTBrowse.setText("..."); //$NON-NLS-1$
        btnXSLTBrowse.addSelectionListener(this);

        // Schema
        Label lblSchema = new Label(grpExport, SWT.NONE);
        lblSchema.setText(Messages.WizardInfoPage_schema_label);

        txtSchema = createText(grpExport, 1);
        txtSchema.addModifyListener(this);

        btnSchemaBrowse = new Button(grpExport, SWT.NONE);
        btnSchemaBrowse.setText("..."); //$NON-NLS-1$
        btnSchemaBrowse.addSelectionListener(this);

        return grpExport;
    }

    /**
     * Create the export folder selection group
     * 
     * @param parent
     */
    private Composite createExportFolderControl(Composite parent) {
        GridData gData;
        Group grpExportFolder = new Group(parent, SWT.NONE);
        grpExportFolder.setText(Messages.WizardInfoPage_projectExportFolder_label);

        GridLayout gridLayout = new GridLayout(2, false);
        gridLayout.horizontalSpacing = 10;
        gridLayout.marginTop = 5;
        gridLayout.marginBottom = 5;
        gridLayout.marginLeft = 5;
        gridLayout.marginRight = 5;

        grpExportFolder.setLayout(gridLayout);

        // Check control to select/deselect the default value
        chkExportFolder = new Button(grpExportFolder, SWT.CHECK);
        chkExportFolder.setText(Messages.WizardInfoPage_defaultExportFolder_label);
        gData = new GridData(GridData.FILL_HORIZONTAL);
        gData.widthHint = ImportExportPluginConstants.TEXT_WIDTH_HINT;
        gData.horizontalSpan = 2;
        chkExportFolder.setLayoutData(gData);
        chkExportFolder.setSelection(true);
        chkExportFolder.addSelectionListener(this);

        // Folder entry
        Label lblFolder = new Label(grpExportFolder, SWT.NONE);
        lblFolder.setText(Messages.WizardInfoPage_folder_label);

        gData = new GridData();
        gData.horizontalIndent = 5;

        lblFolder.setLayoutData(gData);

        txtExportFolder = createText(grpExportFolder, 1);
        txtExportFolder.setEnabled(false);
        txtExportFolder.addModifyListener(this);

        return grpExportFolder;
    }

    /**
     * Create the export category selection control.
     * 
     * @param parent
     */
    private Composite createCategoryControl(Composite parent) {

        Group grpCategory = new Group(parent, SWT.NONE);
        grpCategory.setText(Messages.WizardInfoPage_categoryGroup_label);
        grpCategory.setLayout(new GridLayout(2, false));

        Label lbl = new Label(grpCategory, SWT.NONE);
        lbl.setText(Messages.WizardInfoPage_category_label);

        cmbCategory = new Combo(grpCategory, SWT.BORDER | SWT.READ_ONLY);
        cmbCategory.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        updateCategoryList(getWizardType());
        cmbCategory.addModifyListener(this);

        return grpCategory;
    }

    /**
     * Update the category selection combo if the wizard type selection has
     * changed.
     */
    private void updateCategoryList(WizardType type) {

        if (cmbCategory != null && !cmbCategory.isDisposed()) {

            // If the wizard type selection has changed then update the combo
            // listing
            if (type != cmbCategory.getData()) {
                String extPointName = null;
                if (type.equals(WizardType.EXPORT)) {
                    extPointName = "exportWizards"; //$NON-NLS-1$
                } else if (type.equals(WizardType.IMPORT)) {
                    extPointName = "importWizards"; //$NON-NLS-1$
                }

                if (extPointName != null) {
                    Map<String, String> categories = new HashMap<String, String>();

                    // Get the import/export extension point
                    IExtensionPoint point = Platform.getExtensionRegistry()
                            .getExtensionPoint("org.eclipse.ui", extPointName); //$NON-NLS-1$

                    if (point != null) {
                        // Get all extensions
                        IExtension[] extensions = point.getExtensions();

                        if (extensions != null) {

                            for (IExtension ext : extensions) {
                                // For each extension get the configuration
                                // elements
                                IConfigurationElement[] elements = ext
                                        .getConfigurationElements();

                                // Get categories from the exportWizard
                                // extension
                                if (elements != null) {
                                    for (IConfigurationElement elem : elements) {
                                        if (elem.getName().equals("category")) { //$NON-NLS-1$
                                            String name = elem
                                                    .getAttribute("name"); //$NON-NLS-1$

                                            if (!categories.containsKey(name)) {
                                                categories.put(name, elem
                                                        .getAttribute("id")); //$NON-NLS-1$
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    // Add new values to the combo
                    if (!categories.isEmpty()) {
                        // Sort the keys
                        TreeSet<String> sortedKeys = new TreeSet<String>();
                        sortedKeys.addAll(categories.keySet());

                        cmbCategory.removeAll();

                        // Add a blank entry for no category selection
                        cmbCategory.add(""); //$NON-NLS-1$

                        for (String name : sortedKeys) {
                            cmbCategory.add(name);
                            cmbCategory.setData(name, categories.get(name));
                        }
                        cmbCategory.setData(type);
                    }
                }
            }
        }
    }

    /**
     * Create the icon selection group
     * 
     * @param parent
     */
    private Composite createIconSelectionControl(Composite parent) {

        Group grpIcon = new Group(parent, SWT.NONE);
        grpIcon.setText(Messages.WizardInfoPage_iconGroup_label);
        GridLayout gridLayout = new GridLayout(3, false);
        gridLayout.horizontalSpacing = 5;
        grpIcon.setLayout(gridLayout);

        // Import Listing icon
        lblIconPreview = new Label(grpIcon, SWT.NONE);
        lblIconPreview.setSize(ICONWIDTH, ICONHEIGHT);
        lblIconPreview.setToolTipText(Messages.WizardInfoPage_iconPreview_tooltip);

        // Set the icon place holder
        setTempIcon();

        // Dispose of the icon when it's not needed any more
        lblIconPreview.addDisposeListener(new DisposeListener() {

            public void widgetDisposed(DisposeEvent e) {
                if (imgIcon != null) {
                    imgIcon.dispose();
                    imgIcon = null;
                }

                if (imgIconPlaceHolder != null) {
                    imgIconPlaceHolder.dispose();
                    imgIconPlaceHolder = null;
                }
            }
        });

        // Add the image selection text control
        txtIcon = createText(grpIcon, 1);
        txtIcon.addModifyListener(this);

        // Add the browse button
        btnIconBrowse = new Button(grpIcon, SWT.NONE);
        btnIconBrowse.setText("..."); //$NON-NLS-1$
        btnIconBrowse.addSelectionListener(this);

        return grpIcon;
    }

    /**
     * Create a text controls of the given horizontal span
     * 
     * @param parent
     * @param horizSpan
     * @return
     */
    private Text createText(Composite parent, int horizSpan) {
        Text text = new Text(parent, SWT.BORDER);
        GridData gData = new GridData(GridData.FILL_HORIZONTAL);
        gData.horizontalSpan = horizSpan;
        gData.widthHint = ImportExportPluginConstants.TEXT_WIDTH_HINT;
        text.setLayoutData(gData);

        return text;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
     */
    public void modifyText(ModifyEvent e) {
        // If source is one of the entries that loads a file then reset the
        // corresponding file so that doValidatePage will update the file
        if (e.getSource() == txtIcon) {
            iconFile = null;
        } else if (e.getSource() == txtXSLT) {
            xsltFile = null;
        } else if (e.getSource() == txtSchema) {
            schemaFile = null;
        } else if (e.getSource() == txtWizardTitle) {
            // If project folder is set to default then set it to the value of
            // the wizard title
            if (chkExportFolder.getSelection()) {
                txtExportFolder.setText(txtWizardTitle.getText());
            }
        }

        // Validate the page
        doValidatePage();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
     */
    public void widgetSelected(SelectionEvent e) {
        FileDialog fd = new FileDialog(getShell());
        String szSelection = null;

        if (e.getSource() == chkExportFolder) {
            // Update the export folder selected text control
            txtExportFolder.setEnabled(!chkExportFolder.getSelection());

            // Update the export folder value if the user selected the default
            if (chkExportFolder.getSelection()) {
                txtExportFolder.setText(txtWizardTitle.getText());
            }

            doValidatePage();
        } else {
            // Set the correct file name/filter and dialog title
            if (e.getSource() == btnXSLTBrowse) {
                // XSLT file selection
                fd.setText(Messages.WizardInfoPage_xslDialog_title);

                fd.setFilterExtensions(new String[] { "*.xsl; *.xslt" }); //$NON-NLS-1$
                fd
                        .setFilterNames(new String[] { Messages.WizardInfoPage_xslFileDialogFilterName_message });

                if (xslt.length() > 0)
                    fd.setFilterPath(xslt);
            } else if (e.getSource() == btnIconBrowse) {
                // Icon selection
                fd.setText(Messages.WizardInfoPage_iconDialog_title);

                if (icon.length() > 0)
                    fd.setFilterPath(icon);
            } else if (e.getSource() == btnSchemaBrowse) {
                // Schema selection
                fd.setText(Messages.WizardInfoPage_schemaDialog_title);

                fd.setFilterExtensions(new String[] { "*.dtd", "*.xsd" }); //$NON-NLS-1$ //$NON-NLS-2$
                fd.setFilterNames(new String[] {
                        Messages.WizardInfoPage_dtdFilter_message,
                        Messages.WizardInfoPage_schemaFilter_message });
            }

            // Show the file dialog
            szSelection = fd.open();

            // Update the corresponding text control with the selection
            if (szSelection != null) {
                if (e.getSource() == btnXSLTBrowse)
                    txtXSLT.setText(szSelection);
                else if (e.getSource() == btnIconBrowse)
                    txtIcon.setText(szSelection);
                else if (e.getSource() == btnSchemaBrowse)
                    txtSchema.setText(szSelection);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
     */
    public void widgetDefaultSelected(SelectionEvent e) {

    }

    @Override
    public void dispose() {

        if (lblIconPreview != null && !lblIconPreview.isDisposed()) {
            Image img = lblIconPreview.getImage();

            // Dispose the preview icon
            if (img != null) {
                img.dispose();
            }
        }

        if (imgIconPlaceHolder != null && !imgIconPlaceHolder.isDisposed()) {
            imgIconPlaceHolder.dispose();
        }

        super.dispose();
    }

    /**
     * Validate the page controls. All required information must be available
     * before allowing progress to the next page in the wizard
     * 
     * @param src -
     *            The object on the page being validated (can be set to <b>null</b>)
     */
    private void doValidatePage() {
        String szErrMsg = null;

        setPageComplete(false);

        // Update all values entered
        wizardTitle = txtWizardTitle.getText().trim();
        wizardDesc = txtWizardDesc.getText().trim();
        outputFileExt = txtOutputFileExt.getText().trim();
        xslt = txtXSLT.getText().trim();
        schema = txtSchema.getText().trim();
        icon = txtIcon.getText().trim();
        exportFolder = txtExportFolder.getText().trim();
        categoryId = (String) cmbCategory.getData(cmbCategory.getText());

        // Check that wizard title has been set
        if (szErrMsg == null && getWizardTitle().length() == 0) {
            szErrMsg = Messages.WizardInfoPage_wizardTitleNotSetErr_shortdesc;
        }

        // Check that the file extension filter has been set
        if (szErrMsg == null) {
            if (getOutputFileExt().length() == 0) {
                szErrMsg = Messages.WizardInfoPage_outputFileExtNotSetErr_shortdesc;
            } else if (!getOutputFileExt().matches("[\\w]+")) { //$NON-NLS-1$
                szErrMsg = Messages.WizardInfoPage_invalidFileExtErr_shortdesc;
            }
        }

        // Check that the XSLT is set
        if (szErrMsg == null) {
            if (xslt.length() == 0) {
                szErrMsg = Messages.WizardInfoPage_xsltNotSet_shortdesc;
            } else {
                // Check if the file has been loaded already, if it hasn't then
                // attempt to load it
                if (xsltFile == null) {
                    xsltFile = getFile(xslt);
                    if (xsltFile == null) {
                        szErrMsg = Messages.WizardInfoPage_xsltFileNotFoundErr_shortdesc;
                    }
                }
            }
        }

        // Check the schema file if one is provided
        if (szErrMsg == null && schema.length() > 0 && schemaFile == null) {
            // Verify the file
            schemaFile = getFile(schema);

            if (schemaFile == null) {
                szErrMsg = Messages.WizardInfoPage_schemaFileNotFound_shortdesc;
            }
        }

        // Check the export folder selection if not set to default
        if (szErrMsg == null && !chkExportFolder.getSelection()) {
            // Check that we have a project export folder set
            if (txtExportFolder.getText().length() == 0) {
                szErrMsg = Messages.WizardInfoPage_noProjectExportFolderErr_shortdesc;
            }
        }

        // Check the icon file if one provided
        if (icon.length() > 0 && iconFile == null) {
            // Verify the file
            iconFile = getFile(icon);
            String iconErr = null;

            if (iconFile != null) {
                // Load the icon
                try {
                    setIcon(iconFile);
                } catch (SWTException e) {
                    if (e.getLocalizedMessage().length() > 0) {
                        szErrMsg = String.format(Messages.WizardInfoPage_iconErr_title, e
                                .getLocalizedMessage());
                    } else {
                        iconErr = Messages.WizardInfoPage_imageLoadFailedErr_shortdesc;
                    }
                    // Failed to load icon so reset file
                    iconFile = null;
                } catch (FileNotFoundException e) {
                    iconErr = Messages.WizardInfoPage_iconFileNotFoundErr_shortdesc;
                    // Failed to load icon so reset file
                    iconFile = null;
                }
            } else {
                iconErr = Messages.WizardInfoPage_iconFileNotFoundErr_shortdesc;
            }

            // If error then set temp icon
            if (iconErr != null) {
                setTempIcon();

                // Update the error message if it doesn't already have an error
                if (szErrMsg == null) {
                    szErrMsg = iconErr;
                }
            }
        }

        setErrorMessage(szErrMsg);

        // If no error then allow progress to next page
        if (szErrMsg == null)
            setPageComplete(true);
    }

    /**
     * Update the icon preview control with the new image
     * 
     * @param fImageFile
     * @throws SWTException
     * @throws FileNotFoundException
     */
    private void setIcon(File fImageFile) throws SWTException,
            FileNotFoundException {

        // If there is an image already present then dispose it
        if (imgIcon != null) {
            imgIcon.dispose();
            imgIcon = null;
        }

        // Load the new image icon
        ImageLoader iLoader = new ImageLoader();

        iLoader.load(new FileInputStream(fImageFile));

        // Scale the image to the icon dimensions
        imgIcon = new Image(lblIconPreview.getDisplay(), iLoader.data[0]
                .scaledTo(ICONWIDTH, ICONHEIGHT));

        // Load the icon in the preview control
        lblIconPreview.setImage(imgIcon);
    }

    /**
     * Update the icon preview control with a blank image - a place holder for
     * the icon.
     */
    private void setTempIcon() {
        // If image not available then create it
        if (imgIconPlaceHolder == null) {
            // Create a new temporary icon as place holder
            // this will be a single colour image
            Color bgColor = lblIconPreview.getBackground();
            int red = bgColor.getRed();
            int green = bgColor.getGreen();
            int blue = bgColor.getBlue();

            // Modify the RGB colour to distinguish it from the background
            red = red >= 15 ? red - 15 : red + 15;
            green = green >= 15 ? green - 15 : green + 15;
            blue = blue >= 15 ? blue - 15 : blue + 15;

            PaletteData palette = new PaletteData(new RGB[] { new RGB(red,
                    green, blue) });

            ImageData imgData = new ImageData(ICONWIDTH, ICONHEIGHT, 1, palette);

            for (int x = 0; x < ICONWIDTH; x++) {
                for (int y = 0; y < ICONHEIGHT; y++) {
                    imgData.setPixel(x, y, 0);
                }
            }

            imgIconPlaceHolder = new Image(lblIconPreview.getDisplay(), imgData);
        }

        // Set the image if not already set
        if (lblIconPreview.getImage() != imgIconPlaceHolder)
            lblIconPreview.setImage(imgIconPlaceHolder);
    }

    /**
     * Get the file corresponding to the given file name.
     * 
     * @param fileName
     * @return <code>File</code> if the given file name is an existing file,
     *         <b>null</b> will be returned otherwise.
     */
    private File getFile(String fileName) {

        if (fileName != null) {
            File file = new File(fileName);

            if (file.isFile()) {
                return file;
            }
        }

        return null;
    }

    /**
     * Get the wizard type being created.
     * 
     * @return
     */
    private WizardType getWizardType() {
        return ((NewPluginGeneratorWizard) getWizard()).getWizardType();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.importexport.pluginGenerator.IPluginData.IWizardDataProvider#getExportWorkspaceFolder()
     */
    public String getExportWorkspaceFolder() {
        return exportFolder;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.importexport.pluginGenerator.IPluginData.IWizardDataProvider#getOutputFileExt()
     */
    public String getOutputFileExt() {
        return outputFileExt;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.importexport.pluginGenerator.IPluginData.IWizardDataProvider#getSchemaFile()
     */
    public File getSchemaFile() {
        return schemaFile;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.importexport.pluginGenerator.IPluginData.IWizardDataProvider#getWizardCategoryId()
     */
    public String getWizardCategoryId() {
        return categoryId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.importexport.pluginGenerator.IPluginData.IWizardDataProvider#getWizardDescription()
     */
    public String getWizardDescription() {
        return wizardDesc;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.importexport.pluginGenerator.IPluginData.IWizardDataProvider#getWizardIconFile()
     */
    public File getWizardIconFile() {
        return iconFile;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.importexport.pluginGenerator.IPluginData.IWizardDataProvider#getWizardTitle()
     */
    public String getWizardTitle() {
        return wizardTitle;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.importexport.pluginGenerator.IPluginData.IWizardDataProvider#getXsltFile()
     */
    public File getXsltFile() {
        return xsltFile;
    }

}
