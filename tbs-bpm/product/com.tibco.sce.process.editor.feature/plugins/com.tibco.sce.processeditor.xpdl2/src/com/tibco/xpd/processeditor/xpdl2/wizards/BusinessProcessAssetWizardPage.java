/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.wizards;

import java.beans.PropertyChangeEvent;
import java.util.Date;

import org.eclipse.core.resources.IFolder;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.newproject.BusinessProcessAssetConfig;
import com.tibco.xpd.analyst.resources.xpdl2.wizards.pages.AbstractXpdlProjectSelectPage;
import com.tibco.xpd.analyst.resources.xpdl2.wizards.pages.IPackageTextAndContainerPage;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.resources.projectconfig.projectassets.AbstractSpecialFolderAssetWizardPage;
import com.tibco.xpd.resources.projectconfig.projectassets.IAssetProjectPropertyChangeListener;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.PackageHeader;
import com.tibco.xpd.xpdl2.RedefinableHeader;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.resources.XpdlMigrate;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Business Process asset wizard page for the new project wizard.This allows the
 * selection of the process package folder during the creation of the project.
 * Also allows the selection of package/process templates.
 * 
 * @author njpatel
 * 
 */
public class BusinessProcessAssetWizardPage extends
        AbstractSpecialFolderAssetWizardPage implements
        IPackageTextAndContainerPage {

    private Button packageSelect;

    private boolean createPackage = true;

    private com.tibco.xpd.xpdl2.Package xpdl2Package;

    private String packageFileName;

    private Text txtPackageFile;

    private IWizardPage defaultNextPage;

    private IWizardPage nextPage;

    /**
     * Default constructor.
     */
    public BusinessProcessAssetWizardPage() {
        super("processPackageSelection"); //$NON-NLS-1$
        setTitle(Messages.BusinessProcessAssetWizardPage_ProcessAssetWindowTitle_title);
        setMessage(Messages.BusinessProcessAssetWizardPage_WindowDesc_shortdesc);
        createPackage();
    }

    @Override
    protected String getDefaultSpecialFolderName() {
        String folderName = null;
        BusinessProcessAssetConfig config = getAssetConfig();
        /*
         * If the configuration object has a selection set then set that as the
         * default folder, otherwise get the default special folder name.
         */
        if (config.getSelection() != null
                && config.getSelection().getFirstElement() instanceof IFolder) {
            folderName =
                    ((IFolder) config.getSelection().getFirstElement())
                            .getProjectRelativePath().toString();
        }

        return folderName != null ? folderName : config.getSpecialFolderName();
    }

    @Override
    protected Composite createMainContent(Composite parent) {
        Composite root = new Composite(parent, SWT.NONE);
        GridLayout grpGridLayout = new GridLayout();
        grpGridLayout.marginHeight = 8;
        grpGridLayout.marginWidth = 8;
        grpGridLayout.horizontalSpacing = 8;
        grpGridLayout.verticalSpacing = 8;
        grpGridLayout.marginRight = 0;
        grpGridLayout.numColumns = 2;

        root.setLayout(grpGridLayout);
        PlatformUI
                .getWorkbench()
                .getHelpSystem()
                .setHelp(parent,
                        "com.tibco.xpd.process.analyst.doc.AnalysisProj4"); //$NON-NLS-1$
        Group packageGroup = new Group(root, SWT.NONE);
        packageGroup
                .setText(Messages.BusinessProcessAssetWizardPage_PacakgeGroup_label);
        packageGroup.setLayout(new GridLayout(2, false));
        packageGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        packageSelect = new Button(packageGroup, SWT.CHECK);
        packageSelect
                .setText(Messages.BusinessProcessAssetWizardPage_CreateProcessChkBox_label);
        GridData gridData = new GridData();
        gridData.horizontalSpan = 2;
        packageSelect.setLayoutData(gridData);
        packageSelect.setSelection(true);
        packageSelect.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (packageSelect.getSelection()) {
                    createPackage = true;
                    createPackage();
                    txtPackageFile.setEnabled(true);
                    nextPage = defaultNextPage;
                } else {
                    createPackage = false;
                    xpdl2Package = null;
                    txtPackageFile.setEnabled(false);
                    nextPage = getNextAssetPage();
                }
                getWizard().getContainer().updateButtons();
            }
        });

        // Add any package selection container
        createPackageSelection(packageGroup);
        return root;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.ui.properties.CreationWizardProjectSelectionPage#
     * createPackageSelection(org.eclipse.swt.widgets.Composite)
     */
    protected void createPackageSelection(Composite parent) {

        // Add the package selection to the UI

        createLabel(parent,
                Messages.BusinessProcessAssetWizardPage_FileText_label);

        txtPackageFile = createText(parent);

        /* SID Xpd-2139: filename may have been preset */
        if (packageFileName != null && packageFileName.length() > 0) {
            txtPackageFile.setText(packageFileName);
        } else {
            txtPackageFile.setText(Xpdl2ResourcesConsts.DEFAULT_PACKAGE_NAME
                    + Xpdl2ResourcesConsts.DEFAULT_PACKAGE_FILENAME_EXT);
        }

        txtPackageFile.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {

                // Update package file name
                packageFileName = txtPackageFile.getText();

                // If the right file extension isn't added then do so
                if (packageFileName != null && packageFileName.length() > 0) {
                    String regEx = ".+\\" + getFileExtension(); //$NON-NLS-1$
                    if (!packageFileName.matches(regEx)) {
                        packageFileName += getFileExtension();
                    }
                }

                setPageComplete(validatePage());
            }
        });
    }

    /**
     * @param parent
     * @param string
     */
    private Label createLabel(Composite parent, String label) {
        Label lbl = new Label(parent, SWT.WRAP);
        GridData gData = new GridData();
        // gData.horizontalIndent = indent;
        // gData.widthHint = 95;
        lbl.setLayoutData(gData);
        lbl.setText(label);

        return lbl;
    }

    public void createPackage() {
        if (xpdl2Package == null) {
            xpdl2Package = Xpdl2Factory.eINSTANCE.createPackage();
            // In case package file name hasn't been initialized.
            if (packageFileName == null) {
                packageFileName = getDefaultPackageName();
            }
            String pckgName =
                    packageFileName.substring(0,
                            packageFileName.indexOf(getFileExtension()));
            xpdl2Package.setName(NameUtil.getInternalName(pckgName, false));
            Xpdl2ModelUtil
                    .setOtherAttribute(xpdl2Package,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName(),
                            pckgName);

            PackageHeader packageHeader =
                    Xpdl2Factory.eINSTANCE.createPackageHeader();
            packageHeader.setVendor(AbstractXpdlProjectSelectPage.VENDOR_NAME);
            packageHeader.setCreated(new Date().toString());
            xpdl2Package.setPackageHeader(packageHeader);

            RedefinableHeader redefinableHeader =
                    Xpdl2Factory.eINSTANCE.createRedefinableHeader();
            redefinableHeader
                    .setAuthor(System
                            .getProperty(AbstractXpdlProjectSelectPage.USER_NAME_PROPERTY));
            redefinableHeader
                    .setVersion(AbstractXpdlProjectSelectPage.VERSION_ID);
            xpdl2Package.setRedefinableHeader(redefinableHeader);

            ExtendedAttribute attrib =
                    Xpdl2Factory.eINSTANCE.createExtendedAttribute();
            attrib.setName(AbstractXpdlProjectSelectPage.CREATEDBYATTRIB);
            attrib.setValue(AbstractXpdlProjectSelectPage.BUSINESS_STUDIO_CONST);
            xpdl2Package.getExtendedAttributes().add(attrib);

            attrib = Xpdl2Factory.eINSTANCE.createExtendedAttribute();
            attrib.setName(XpdlMigrate.FORMAT_VERSION_ATT_NAME);
            attrib.setValue(XpdlMigrate.FORMAT_VERSION_ATT_VALUE);
            xpdl2Package.getExtendedAttributes().add(attrib);

            /* Store the OriginalFormatVersion that xpdl was created in. */
            attrib = Xpdl2Factory.eINSTANCE.createExtendedAttribute();
            attrib.setName(XpdlMigrate.ORIGINAL_FORMAT_VERSION_ATT_NAME);
            attrib.setValue(XpdlMigrate.FORMAT_VERSION_ATT_VALUE);
            xpdl2Package.getExtendedAttributes().add(attrib);

        }
    }

    /**
     * @return The default package file name.
     */
    private String getDefaultPackageName() {
        return getBaseFileName() + getFileExtension();
    }

    private String getBaseFileName() {
        return Xpdl2ResourcesConsts.DEFAULT_PACKAGE_NAME;
    }

    protected String getFileExtension() {
        return Xpdl2ResourcesConsts.DEFAULT_PACKAGE_FILENAME_EXT;
    }

    /**
     * Create a text control
     * 
     * @param container
     * @return
     */
    protected Text createText(Composite container) {
        Text txt = new Text(container, SWT.BORDER);
        GridData gData = new GridData(GridData.FILL_HORIZONTAL);
        gData.widthHint = 150;
        txt.setLayoutData(gData);

        return txt;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.projectconfig.projectassets.IAssetWizardPage#
     * updateConfiguration()
     */
    @Override
    public void updateConfiguration() {
        BusinessProcessAssetConfig config = getAssetConfig();

        // Update the special folder name
        config.setSpecialFolderName(getSpecialFolderName());

        config.setXpdl2Package(xpdl2Package);
        if (txtPackageFile != null && txtPackageFile.getText() != null
                && !txtPackageFile.getText().equals("")) { //$NON-NLS-1$
            config.setPackageFileName(txtPackageFile.getText());
        } else {
            config.setPackageFileName(packageFileName);
        }
    }

    @Override
    public void performHelp() {
        PlatformUI.getWorkbench().getHelpSystem()
                .displayHelp("com.tibco.xpd.process.analyst.doc.AnalysisProj4"); //$NON-NLS-1$      
    }

    /**
     * Get the business process asset configuration object.
     * 
     * @return
     * @throws IllegalArgumentException
     *             If the wrong type asset configuration object is received.
     */
    private BusinessProcessAssetConfig getAssetConfig() {
        Object configuration = getConfiguration();

        if (configuration instanceof BusinessProcessAssetConfig) {
            return (BusinessProcessAssetConfig) configuration;
        } else {
            throw new IllegalArgumentException("Incorrect asset configuration."); //$NON-NLS-1$
        }
    }

    /**
     * @see org.eclipse.jface.wizard.WizardPage#getNextPage()
     * 
     * @return
     */
    @Override
    public IWizardPage getNextPage() {
        defaultNextPage = super.getNextPage();

        // If "CreatePage" checkbox is unchecked, then need to find the next
        // asset wizard page.
        if (!createPackage) {
            nextPage = getNextAssetPage();
        }
        // else return the defaultNextPage
        else {
            nextPage = defaultNextPage;
        }
        return nextPage;
    }

    private IWizardPage getNextAssetPage() {
        IWizardPage nextPage = null;
        IWizardPage page = getWizard().getPage("packageTemplateSelection"); //$NON-NLS-1$
        if (page != null)
            nextPage = getWizard().getNextPage(page);
        return nextPage;
    }

    /**
     * 
     * @see org.eclipse.jface.wizard.WizardPage#canFlipToNextPage()
     * 
     * @return
     */
    @Override
    public boolean canFlipToNextPage() {
        return createPackage || getNextPage() != null;
    }

    /**
     * @return the txtPackageFile
     */
    @Override
    public Text getTxtPackageFile() {
        return txtPackageFile;
    }

    @Override
    public IFolder getPackagesFolderContainer() {
        IFolder folder = null;
        if (getConfiguration() instanceof BusinessProcessAssetConfig) {
            BusinessProcessAssetConfig config =
                    (BusinessProcessAssetConfig) getConfiguration();
            folder = config.getProcessPackageFolder();
        }
        return folder;
    }

    /**
     * @see com.tibco.xpd.resources.projectconfig.projectassets.AbstractSpecialFolderAssetWizardPage#validatePage()
     * 
     * @return
     */
    @Override
    protected boolean validatePage() {
        boolean superResult = super.validatePage();
        if (superResult) {
            String errMsg = null;
            if (txtPackageFile != null
                    && !txtPackageFile
                            .getText()
                            .endsWith(Xpdl2ResourcesConsts.DEFAULT_PACKAGE_FILENAME_EXT)) {
                errMsg =
                        Messages.BusinessProcessAssetWizardPage_ProcPackageErr_shortdesc;
            } else {
                errMsg = null;
            }
            setErrorMessage(errMsg);
            return errMsg == null;
        }
        return superResult;
    }

    /**
     * Sid XPD-2139 When informed of project name change then change our asset
     * name to match (unless user has changed to something else.
     * 
     * @see com.tibco.xpd.resources.projectconfig.projectassets.AbstractSpecialFolderAssetWizardPage#projectPropertyChanged(java.beans.PropertyChangeEvent)
     * 
     * @param evt
     */
    @Override
    public void projectPropertyChanged(PropertyChangeEvent evt) {
        super.projectPropertyChanged(evt);

        if (IAssetProjectPropertyChangeListener.PROJECTNAME_PROPERTY.equals(evt
                .getPropertyName())) {
            String oldName = (String) evt.getOldValue();
            if (oldName == null) {
                oldName = ""; //$NON-NLS-1$
            }

            String newName = (String) evt.getNewValue();
            if (newName == null) {
                newName = ""; //$NON-NLS-1$
            }

            /*
             * If current package file name is unset, default or equivalent
             * same-name for previously selected project name then update it to
             * equivalent of new project name.
             */
            String nameForOldName = oldName + getFileExtension();
            if (packageFileName == null || packageFileName.length() == 0
                    || packageFileName.equals(getDefaultPackageName())
                    || packageFileName.equals(nameForOldName)) {

                packageFileName = newName + getFileExtension();

                String packageLabel =
                        Xpdl2ModelUtil.getDisplayName(xpdl2Package);

                if (oldName.equals(packageLabel)
                        || getBaseFileName().equals(packageLabel)) {
                    Xpdl2ModelUtil.setOtherAttribute(xpdl2Package,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName(),
                            newName);
                    xpdl2Package.setName(NameUtil.getInternalName(newName,
                            false));
                }

                if (txtPackageFile != null && !txtPackageFile.isDisposed()) {
                    txtPackageFile.setText(packageFileName);
                }

                setPageComplete(validatePage());
            }
        }
    }
}
