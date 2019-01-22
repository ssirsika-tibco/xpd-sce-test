package com.tibco.xpd.processeditor.xpdl2.wizards;

import java.text.DateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.fieldassist.ContentAssistCommandAdapter;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.newproject.BusinessProcessAssetConfig;
import com.tibco.xpd.analyst.resources.xpdl2.wizards.pages.IPackageTextAndContainerPage;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.ProjectDetails;
import com.tibco.xpd.resources.projectconfig.ProjectStatus;
import com.tibco.xpd.resources.projectconfig.projectassets.IAssetWizardPage;
import com.tibco.xpd.resources.util.UserInfo;
import com.tibco.xpd.resources.util.UserInfoUtil;
import com.tibco.xpd.ui.util.CapabilityUtil;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.ui.wizards.newproject.IProjectDetailsProvider;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.CostUnit;
import com.tibco.xpd.xpdl2.Description;
import com.tibco.xpd.xpdl2.Documentation;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.PublicationStatusType;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.edit.ui.Xpdl2UiPlugin;
import com.tibco.xpd.xpdl2.edit.ui.contentassist.CurrencyCodeContentProposalProvider;
import com.tibco.xpd.xpdl2.edit.ui.contentassist.LanguageContentProposalProvider;
import com.tibco.xpd.xpdl2.edit.util.FieldAssistUtil;
import com.tibco.xpd.xpdl2.edit.util.LocaleUtils;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Wizard page to collect all package information, including the package header
 * content
 * <p>
 * Used to be PackageInformationPage class but main bulk abstracted here for
 * re-use in task library wizard.
 * 
 * @author njpatel, aallway
 * 
 */
public abstract class AbstractXpdlPackageInformationPage extends WizardPage
        implements IAssetWizardPage, VerifyListener {

    public static final String PAGE_ID = "packageinformation"; //$NON-NLS-1$

    /*
     * Default values
     */
    private static final String PACKAGEDESC = ""; //$NON-NLS-1$

    private static final String DOCUMENTATION = ""; //$NON-NLS-1$

    private static final String STATUS = Xpdl2ResourcesConsts.DEFAULT_STATUS;

    private static final String BUSINESSVERSION =
            Xpdl2ResourcesConsts.DEFAULT_BUSINESS_VERSION;

    private static final String COSTUNIT = LocaleUtils.getCurrencyCode(Locale
            .getDefault());

    private static final String LANGUAGE = Locale.getDefault().getDisplayName();

    private Object configurationObject;

    protected CLabel nameLabel;

    private Text name;

    protected CLabel displayLabel;

    private Text display;

    private Text author;

    private Text created;

    private Text packageDesc;

    private Text documentation;

    private Combo status;

    private Text businessVer;

    private Text costUnitText;

    private Text languageText;

    private String packageNameValue;

    protected boolean nameValid = true;

    protected boolean displayNameValid = true;

    protected String err = null;

    private String userName;

    public AbstractXpdlPackageInformationPage() {
        super(PAGE_ID);
        setTitle(Messages.PackageInformationPage_TITLE);
        setMessage(Messages.PackageInformationPage_MESSAGE);
        /**
         * if project is passed, project preferences are returned. if null is
         * passed workspace preferences are returned
         */
        UserInfo info = UserInfoUtil.getWorkspacePreferences();
        userName = info != null ? info.getUserName() : ""; //$NON-NLS-1$
    }

    /**
     * @return The default package name.
     */
    protected abstract String getDefaultPackageName();

    /**
     * @return THe fil extension.
     */
    protected abstract String getPackageFileExtension();

    /**
     * @return get the package name group label
     */
    protected abstract String getPackageUINameGroupLabel();

    /**
     * @return get the package label name label
     */
    protected abstract String getPackageUILabelNameLabel();

    /**
     * @return get the package name label
     */
    protected abstract String getPackageUINameLabel();

    /**
     * @return get the package header info label
     */
    protected abstract String getPackageUIHeaderInfoLabel();

    // MR 39946 - begin
    public ProjectDetails getProjectDetails() {
        IWizard wizard = super.getWizard();
        if (wizard instanceof IProjectDetailsProvider) {
            IProjectDetailsProvider provider = (IProjectDetailsProvider) wizard;
            ProjectDetails projectDetails = provider.getProjectDetails();
            return projectDetails;
        }
        return null;
    }

    // MR 39946 - end

    /**
     * Get the package name.
     * 
     * @return
     */
    public String getPackageName() {
        if (name != null) {
            return name.getText();
        }
        return null;

    }

    public Text getTxtPackageName() {
        return name;
    }

    /**
     * Get the author.
     * 
     * @return
     */
    public String getAuthor() {
        if (author != null) {
            return author.getText();
        }
        return null;
    }

    /**
     * Get the created date (as entered in the wizard page).
     * 
     * @return
     */
    public Date getCreated() {
        if (created != null && created.getData() != null) {
            return (Date) created.getData();
        }
        return null;
    }

    /**
     * Get the package description.
     * 
     * @return
     */
    public String getPackageDescription() {
        if (packageDesc != null) {
            return packageDesc.getText();
        }
        return null;
    }

    /**
     * Get the documentation location.
     * 
     * @return
     */
    public String getDocumentation() {
        if (documentation != null) {
            return documentation.getText();
        }
        return null;
    }

    /**
     * Get the business version.
     * 
     * @return
     */
    public String getBusinessVersion() {
        if (businessVer != null) {
            return businessVer.getText();
        }
        return null;
    }

    /**
     * Get the Cost Unit.
     * 
     * @return
     */
    public String getCostUnit() {
        if (costUnitText != null) {
            return costUnitText.getText();
        }
        return null;
    }

    /**
     * Get the Language.
     * 
     * @return
     */
    public String getLanguage() {
        if (languageText != null) {
            return languageText.getText();
        }
        return null;
    }

    /**
     * Get the publication status. This will return the string representation of
     * the status.
     * 
     * @return
     */
    public String getStatus() {
        if (status != null) {
            return status.getText();
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets
     * .Composite)
     */
    @Override
    public void createControl(Composite parent) {
        Composite container = new Composite(parent, SWT.NONE);
        container.setLayout(new GridLayout());
        container.setLayoutData(new GridData(GridData.FILL_BOTH));
        PlatformUI
                .getWorkbench()
                .getHelpSystem()
                .setHelp(container,
                        "com.tibco.xpd.process.analyst.doc.CreatePack2"); //$NON-NLS-1$

        // Package information group
        Group grpPackage = new Group(container, SWT.NONE);
        grpPackage.setText(getPackageUINameGroupLabel());
        grpPackage.setLayout(new GridLayout(2, false));
        grpPackage.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        // Package name
        displayLabel = new CLabel(grpPackage, SWT.NONE);
        displayLabel.setLayoutData(new GridData());
        displayLabel.setText(getPackageUILabelNameLabel());
        display = addTextControl(grpPackage, getDefaultPackageName());
        nameLabel = new CLabel(grpPackage, SWT.NONE);
        nameLabel.setLayoutData(new GridData());
        nameLabel.setText(getPackageUINameLabel());
        nameLabel.setVisible(CapabilityUtil.isDeveloperActivityEnabled());
        name =
                addTextControl(grpPackage,
                        getDefaultPackageName(),
                        CapabilityUtil.isDeveloperActivityEnabled());

        display.addVerifyListener(this);
        name.addVerifyListener(this);

        // Package header group
        Group grpHeader = new Group(container, SWT.NONE);
        grpHeader.setText(getPackageUIHeaderInfoLabel());
        grpHeader.setLayout(new GridLayout(2, false));
        grpHeader.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        // Add the package header input controls
        author =
                addTextControl(grpHeader,
                        Messages.PackageInformationPage_6,
                        userName);

        Date todayDate = new Date();
        String iso8601Date = LocaleUtils.getISO8601Date(todayDate);
        String localisedDate =
                LocaleUtils.getLocalisedDateTime(iso8601Date,
                        DateFormat.FULL,
                        DateFormat.MEDIUM);
        if (localisedDate.length() == 0) {
            localisedDate = iso8601Date;
        }
        created =
                addTextControl(grpHeader,
                        Messages.PackageInformationPage_7,
                        localisedDate);
        created.setData(todayDate);
        packageDesc =
                addTextControl(grpHeader,
                        Messages.PackageInformationPage_8,
                        PACKAGEDESC);
        documentation =
                addTextControl(grpHeader,
                        Messages.PackageInformationPage_9,
                        DOCUMENTATION);

        // Add status combo
        Label lblStatus = new Label(grpHeader, SWT.NONE);
        lblStatus.setText(Messages.PackageInformationPage_10);

        status = new Combo(grpHeader, SWT.READ_ONLY);

        List values = PublicationStatusType.VALUES;
        for (Iterator iter = values.iterator(); iter.hasNext();) {
            PublicationStatusType statusType =
                    (PublicationStatusType) iter.next();

            status.add(statusType.getLiteral());
        }

        status.setText(STATUS);

        globaliseStatusCombo();

        // Business version
        businessVer =
                addTextControl(grpHeader,
                        Messages.PackageInformationPage_11,
                        BUSINESSVERSION);

        updatePackageNameAndVersion();

        // create anonymous text content adaptor that forces the selection of a
        // content assist to
        // overwrite the whole text box clearing any previous data rather than
        // concatenating it
        TextContentAdapter textContentAdapter = new TextContentAdapter() {
            @Override
            public void insertControlContents(Control control, String text,
                    int cursorPosition) {
                if (control instanceof Text) {
                    ((Text) control).setText(text);
                } else {
                    super.insertControlContents(control, text, cursorPosition);
                }
            }
        };

        // Cost Unit
        costUnitText =
                addTextControl(grpHeader,
                        Messages.PackageInformationPage_12,
                        COSTUNIT);
        costUnitText.setTextLimit(3);
        ContentAssistCommandAdapter codesContentAssistCommandAdapter =
                new ContentAssistCommandAdapter(costUnitText,
                        textContentAdapter,
                        new CurrencyCodeContentProposalProvider(LocaleUtils
                                .getCurrencyCodes()), null,
                        FieldAssistUtil.getAlphaNumericChars());

        // Language
        languageText =
                addTextControl(grpHeader,
                        Messages.PackageInformationPage_13,
                        LANGUAGE);
        ContentAssistCommandAdapter contentAssistCommandAdapter =
                new ContentAssistCommandAdapter(languageText,
                        textContentAdapter,
                        new LanguageContentProposalProvider(LocaleUtils
                                .getISOCodes()), null,
                        FieldAssistUtil.getAlphaNumericChars());

        setControl(container);
    }

    // MR# 32276. Listener added to ProcessPackage File Name. Have to retain it
    // to display ProcessPackage name.
    IPackageTextAndContainerPage packageTextAndContainerPage = null;

    private void updatePackageNameAndVersion() {

        if (getPreviousPage() instanceof IPackageTextAndContainerPage) {
            packageTextAndContainerPage =
                    (IPackageTextAndContainerPage) getPreviousPage();
        } else {
            /*
             * Look through the pages in the wizard for the container page
             * (should be first page). This part is necessary as
             * getPreviousPage() in the RCP application will return null as we
             * hide the project/file selection page.
             */
            IWizardPage page = getWizard().getPage("containerSelectionPage"); //$NON-NLS-1$
            if (page instanceof IPackageTextAndContainerPage) {
                packageTextAndContainerPage =
                        (IPackageTextAndContainerPage) page;
            }
        }

        if (packageTextAndContainerPage != null) {
            if (null != packageTextAndContainerPage.getTxtPackageFile()) {
                final String packageFileName =
                        packageTextAndContainerPage.getTxtPackageFile()
                                .getText();
                String displayName =
                        getPackageFileNameWithoutExtension(packageFileName);
                String nameText = NameUtil.getInternalName(displayName, false);
                name.setText(nameText);
                display.setText(displayName);

                packageTextAndContainerPage.getTxtPackageFile()
                        .addListener(SWT.Modify, new Listener() {
                            @Override
                            public void handleEvent(Event event) {
                                if (packageTextAndContainerPage != null
                                        && packageTextAndContainerPage
                                                .getTxtPackageFile() != null) {
                                    String displayName =
                                            getPackageFileNameWithoutExtension(packageTextAndContainerPage
                                                    .getTxtPackageFile()
                                                    .getText());
                                    String nameText =
                                            NameUtil.getInternalName(displayName,
                                                    false);
                                    name.setText(nameText);
                                    display.setText(displayName);
                                }

                            }
                        });
            }
            // MR 39946 - begin
            // Get Initial Project Version (project may not be set
            // yet). (Get the packagefolder from the control itself)
            ProjectDetails projectDetails = getProjectDetails();
            if (null != projectDetails) {
                String version = projectDetails.getVersion();
                ProjectStatus pStatus = projectDetails.getStatus();
                status.setText(pStatus.name() == null ? STATUS : pStatus.name());
                businessVer
                        .setText(version == null ? BUSINESSVERSION : version);
            } else {
                if (null != packageTextAndContainerPage
                        .getPackagesFolderContainer()) {
                    IProject project =
                            packageTextAndContainerPage
                                    .getPackagesFolderContainer().getProject();
                    ProjectConfig config =
                            XpdResourcesPlugin.getDefault()
                                    .getProjectConfig(project);
                    /**
                     * if project is not null, project preferences are returned.
                     * if project is null workspace preferences are returned
                     */
                    String projectUserName =
                            UserInfoUtil.getProjectPreferences(project)
                                    .getUserName();
                    author.setText(projectUserName != null ? projectUserName
                            : userName);
                    if (null != config) {
                        if (null != config.getProjectDetails()) {
                            String version =
                                    config.getProjectDetails().getVersion();
                            ProjectStatus pStatus =
                                    config.getProjectDetails().getStatus();
                            status.setText(pStatus.name() == null ? STATUS
                                    : pStatus.name());
                            businessVer
                                    .setText(version == null ? BUSINESSVERSION
                                            : version);
                        }
                    }
                }
            }

        } else if (packageNameValue != null) {
            name.setText(NameUtil.getInternalName(packageNameValue, false));
            display.setText(packageNameValue);
        }
    }

    private String getPackageFileNameWithoutExtension(String packageFileName) {

        if (packageFileName.length() > 0
                && packageFileName.indexOf(getPackageFileExtension()) != -1) {
            String packageName =
                    packageFileName.substring(0,
                            packageFileName.indexOf(getPackageFileExtension()));
            return packageName;
        }
        return packageFileName;
    }

    /**
     * Add a label and text control for the given label
     * 
     * @param container
     * @param label
     * @return Text
     */
    private Text addTextControl(Composite container, String label, String value) {
        return addTextControl(container, label, value, true);
    }

    /**
     * Add a label and text control for the given label
     * 
     * @param container
     * @param label
     * @return Text
     */
    private Text addTextControl(Composite container, String label,
            String value, boolean visible) {
        Text txt;

        // Set the label
        Label lbl = new Label(container, SWT.NONE);
        lbl.setText(label);
        lbl.setVisible(visible);
        // Set the text control
        txt = new Text(container, SWT.BORDER);
        GridData gData = new GridData(GridData.FILL_HORIZONTAL);
        gData.widthHint = 150;
        txt.setLayoutData(gData);
        txt.setText(value != null ? value : ""); //$NON-NLS-1$

        txt.setVisible(visible);
        return txt;
    }

    /**
     * Add a label and text control for the given label
     * 
     * @param container
     * @param label
     * @return Text
     */
    private Text addTextControl(Composite container, String value) {
        return addTextControl(container, value, true);
    }

    /**
     * Add a label and text control for the given label
     * 
     * @param container
     * @param label
     * @return Text
     */
    private Text addTextControl(Composite container, String value,
            boolean visible) {
        Text txt;

        // Set the text control
        txt = new Text(container, SWT.BORDER);
        GridData gData = new GridData(GridData.FILL_HORIZONTAL);
        gData.widthHint = 150;
        txt.setLayoutData(gData);
        txt.setText(value != null ? value : ""); //$NON-NLS-1$

        txt.setVisible(visible);
        return txt;
    }

    @Override
    public void init(Object config) {
        this.configurationObject = config;

    }

    /**
     * Changes the status text to what is in the messages.properties file for
     * support with other languages.
     */
    private void globaliseStatusCombo() {
        int itemCount = status.getItemCount();

        for (int i = 0; i < itemCount; i++) {
            String name = status.getItem(i);
            PublicationStatusType pubStatusType =
                    PublicationStatusType.getByName(name);
            if (pubStatusType != null) {
                String uiText =
                        PublicationStatusType.getUIText(pubStatusType
                                .getValue());
                status.setItem(i, uiText);
                status.setData(uiText, pubStatusType.getLiteral());
            }
        }
    }

    @Override
    public void updateConfiguration() {
        // if (isControlCreated()) {
        Package processPackage =
                ((BusinessProcessAssetConfig) configurationObject)
                        .getXpdl2Package();
        if (processPackage != null) {
            updatePackageObject(processPackage);
        }
    }

    public void updatePackageObject(Package processPackage) {
        // MR 39946 - begin
        String version = null;
        PublicationStatusType publicationStatusType = null;

        ProjectDetails projectDetails = getProjectDetails();
        if (null != projectDetails) {
            version = projectDetails.getVersion();
            ProjectStatus pStatus = projectDetails.getStatus();
            if (null != pStatus) {
                publicationStatusType =
                        PublicationStatusType.get(pStatus.name());
            }
        }
        // MR 39946 - end
        if (getPackageName() != null) {
            processPackage.setName(getPackageName());
        }

        if (getPackageDisplayName() != null) {
            Xpdl2ModelUtil
                    .setOtherAttribute(processPackage,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName(),
                            getPackageDisplayName());
        }

        Documentation documentation =
                Xpdl2Factory.eINSTANCE.createDocumentation();
        if (getDocumentation() != null) {
            documentation.setValue(getDocumentation());
        } else {
            documentation.setValue(DOCUMENTATION);
        }
        processPackage.getPackageHeader().setDocumentation(documentation);

        Description description = Xpdl2Factory.eINSTANCE.createDescription();
        if (getPackageDescription() != null) {
            description.setValue(getPackageDescription());
        } else {
            description.setValue(PACKAGEDESC);
        }
        processPackage.getPackageHeader().setDescription(description);

        Date todayDate = new Date();
        if (getCreated() != null) {
            todayDate = getCreated();
        }
        processPackage.getPackageHeader()
                .setCreated(LocaleUtils.getISO8601Date(todayDate));

        if (getBusinessVersion() != null) {
            processPackage.getRedefinableHeader()
                    .setVersion(getBusinessVersion());
        } else {
            processPackage.getRedefinableHeader()
                    .setVersion(version == null ? BUSINESSVERSION : version);
        }
        if (getAuthor() != null) {
            processPackage.getRedefinableHeader().setAuthor(getAuthor());
        } else {
            processPackage.getRedefinableHeader().setAuthor(userName);
        }
        if (getStatus() != null) {
            String value = (String) status.getData(getStatus());
            processPackage.getRedefinableHeader()
                    .setPublicationStatus(PublicationStatusType.get(value));

        } else {
            // setting the status to default STATUS if not retrieved from
            // project
            if (null == publicationStatusType) {
                publicationStatusType = PublicationStatusType.get(STATUS);
            }
            processPackage.getRedefinableHeader()
                    .setPublicationStatus(publicationStatusType);
        }

        CostUnit costUnit = processPackage.getPackageHeader().getCostUnit();
        if (costUnit == null) {
            costUnit = Xpdl2Factory.eINSTANCE.createCostUnit();
        }

        if (getCostUnit() != null) {
            costUnit.setValue(getCostUnit());
        } else {
            costUnit.setValue(COSTUNIT);
        }
        processPackage.getPackageHeader().setCostUnit(costUnit);

        // if we can't find a match then set to default locale isa code
        String tempLocaleIsaCode =
                LocaleUtils.getLocaleISOFromDisplayName(getLanguage());
        if (tempLocaleIsaCode == null || tempLocaleIsaCode.length() == 0) {
            tempLocaleIsaCode = Locale.getDefault().toString();
        }
        Xpdl2ModelUtil.setOtherAttribute(processPackage.getPackageHeader(),
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_Language(),
                tempLocaleIsaCode);
    }

    private String getPackageDisplayName() {
        if (display != null) {
            return display.getText();
        }

        return null;
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
    }

    @Override
    public void performHelp() {
        PlatformUI.getWorkbench().getHelpSystem()
                .displayHelp("com.tibco.xpd.process.analyst.doc.CreatePack2"); //$NON-NLS-1$      
    }

    @Override
    public void verifyText(VerifyEvent event) {
        boolean beforeNameValid = nameValid;
        boolean beforeDisplayNameValid = displayNameValid;

        Text textControl = ((Text) event.widget);
        if (textControl == name) {
            String nameText =
                    textControl.getText(0, event.start - 1)
                            + event.text
                            + textControl.getText(event.end,
                                    textControl.getCharCount() - 1);
            verifyName(nameText);
        } else if (textControl == display) {
            String text =
                    textControl.getText(0, event.start - 1)
                            + event.text
                            + textControl.getText(event.end,
                                    textControl.getCharCount() - 1);

            verifyDisplayName(text);
        }

        boolean complete = nameValid && displayNameValid;
        if (complete) {
            err = null;
        }
        setPageComplete(complete);
        setErrorMessage(err);

        if (beforeDisplayNameValid != displayNameValid
                || beforeNameValid != nameValid) {
            // Force layout on whole section to ensure that there is room for
            // error icon
            getShell().layout(true, true);
        }
    }

    /**
     * @param text
     */
    private void verifyDisplayName(String text) {
        displayNameValid = true;

        updateNameFromDisplayName(text);

        if (text.length() == 0) {
            displayNameValid = false;
            err = Messages.NamedElementSection_LabelEmpty;
        } else if (!text.trim().equals(text)) {
            displayNameValid = false;
            err = Messages.NamedElementSection_LeadingTrailingSpaces1;
        }
        if (!displayNameValid) {
            displayLabel.setImage(Xpdl2UiPlugin.getDefault().getImageRegistry()
                    .get(Xpdl2UiPlugin.IMG_ERROR));
            displayLabel.setToolTipText(err);
            displayLabel.setLayoutData(new GridData());
            displayLabel.getParent().layout();
        } else {
            displayLabel.setImage(null);
            displayLabel.setToolTipText(""); //$NON-NLS-1$
            displayLabel.setLayoutData(new GridData());
            displayLabel.getParent().layout();
        }
    }

    protected void updateNameFromDisplayName(String text) {
        if (name.getText().equals(NameUtil.getInternalName(display.getText(),
                false))) {
            String nameText = NameUtil.getInternalName(text, false);
            name.setText(nameText);
            verifyName(nameText);
        }
    }

    protected void verifyName(String nameText) {
        nameValid = true;
        if (nameText == null || nameText.length() == 0) {
            nameValid = false;
            err = Messages.NamedElementSection_NameEmpty;
        } else if (!NameUtil.isValidName(nameText, true)) {
            nameValid = false;
            err = Messages.NamedElementSection_invalidNameErrorMessage;
        }
        if (!nameValid) {
            nameLabel.setImage(Xpdl2UiPlugin.getDefault().getImageRegistry()
                    .get(Xpdl2UiPlugin.IMG_ERROR));
            nameLabel.setToolTipText(err);
            nameLabel.setLayoutData(new GridData());
            nameLabel.getParent().layout();
        } else {
            nameLabel.setImage(null);
            nameLabel.setToolTipText(""); //$NON-NLS-1$
            nameLabel.setLayoutData(new GridData());
            nameLabel.getParent().layout();
        }

    }
}
