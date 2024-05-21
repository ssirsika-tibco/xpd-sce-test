package com.tibco.xpd.om.modeler.custom.internal.projectasset;

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

import com.tibco.xpd.om.modeler.custom.Activator;
import com.tibco.xpd.om.modeler.custom.internal.Messages;
import com.tibco.xpd.om.resources.OMResourcesActivator;
import com.tibco.xpd.resources.projectconfig.projectassets.AbstractSpecialFolderAssetWizardPage;
import com.tibco.xpd.resources.projectconfig.projectassets.IAssetProjectPropertyChangeListener;
import com.tibco.xpd.resources.projectconfig.projectassets.SpecialFolderAssetConfiguration;

public class OMAssetWizardPage extends AbstractSpecialFolderAssetWizardPage {

    private String omFileName = null;

    private Text txtFileName = null;

    private Button createDefaultMMCheckbox = null;

    private Button createDefaultMMandApplyCheckBox = null;

    private Button createModelCheckbox = null;

    private final static String DEFAULT_OM_FILENAME =
            Messages.OMAssetWizardPage_DefaultOMFile_name;

    public OMAssetWizardPage() {
        super("OmAssetWizard");//$NON-NLS-1$
        setTitle(Messages.OMAssetWizardPage_Title_label);
        setDescription(Messages.OMAssetWizardPage_Description_label);
        omFileName = DEFAULT_OM_FILENAME; //$NON-NLS-1$

		/* Sid ACE-7330: Show correct wizard banner icon for asset type. */
		setImageDescriptor(Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/wizban/NewOMModel.png"));

    }

    @Override
    protected String getDefaultSpecialFolderName() {
        Object conf = getConfiguration();
        String folderName = null;

        if (conf instanceof SpecialFolderAssetConfiguration) {
            SpecialFolderAssetConfiguration config =
                    (SpecialFolderAssetConfiguration) conf;
            /*
             * If a selection is set then override the default folder name with
             * the selection
             */
            if (config.getSelection() != null
                    && config.getSelection().getFirstElement() instanceof IFolder) {
                folderName =
                        ((IFolder) config.getSelection().getFirstElement())
                                .getProjectRelativePath().toString();
            } else {
                folderName = Messages.default_omfolder_name;
            }
        }

        return folderName;
    }

    @Override
	public void updateConfiguration() {
        Object conf = getConfiguration();

        if (conf instanceof SpecialFolderAssetConfiguration) {
            ((SpecialFolderAssetConfiguration) conf)
                    .setSpecialFolderName(getSpecialFolderName());

            if (conf instanceof OMSpecialFolderAssetConfiguration) {
                OMSpecialFolderAssetConfiguration omConf =
                        (OMSpecialFolderAssetConfiguration) conf;

                omConf.setOmFileName(omFileName);

                if (createModelCheckbox == null) {
                    omConf.setCreateModel(true);
                    omConf.setCreateSchema(true);
                    omConf.setApplyType(false);
                } else {
                    omConf.setCreateModel(createModelCheckbox.getSelection());

                    if (createDefaultMMCheckbox == null) {
                        omConf.setCreateSchema(true);
                        omConf.setApplyType(false);
                    } else {

                        omConf.setCreateSchema(createDefaultMMCheckbox
                                .getSelection());

                        if (createDefaultMMandApplyCheckBox == null) {
                            omConf.setApplyType(false);
                        } else {
                            omConf.setApplyType(createDefaultMMandApplyCheckBox
                                    .getSelection());
                        }

                    }

                }

            }
        }

    }

    @Override
    protected Composite createMainContent(Composite parent) {
        Composite root = new Composite(parent, SWT.NONE);
        root.setLayout(new GridLayout());

        Control modelGrp = createModelGroup(root);
        modelGrp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        return root;
    }

    /**
     * 
     * Creates the Group composite containing the Model Details controls.
     * 
     * @param parent
     * @return Control
     */
    private Control createModelGroup(Composite parent) {
        // Create the group
        Group modelGrp = new Group(parent, SWT.NONE);
        modelGrp.setText(Messages.OMAssetWizardPage_ModelDetail_label);
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
        createModelCheckbox
                .setText(Messages.OMAssetWizardPage_CreateInitialOM_label);
        GridData gdModelCheckBox = new GridData(GridData.FILL_HORIZONTAL);
        gdModelCheckBox.horizontalSpan = 2;
        createModelCheckbox.setLayoutData(gdModelCheckBox);

        final Label lblFilename = new Label(modelGrp, SWT.NONE);
        lblFilename.setLayoutData(new GridData());
        lblFilename.setText(Messages.OMAssetWizardPage_Filename_label);

        txtFileName = new Text(modelGrp, SWT.BORDER);
        GridData gData = new GridData(GridData.FILL_HORIZONTAL);
        gData.widthHint = TEXT_WIDTH_HINT;
        txtFileName.setLayoutData(gData);

        // Set the default filename
        /* SID XPD-2139: FIle name may have been preset. */
        if (omFileName != null && omFileName.length() > 0) {
            txtFileName.setText(omFileName);
        } else {
            txtFileName.setText(DEFAULT_OM_FILENAME);
        }

        txtFileName.addModifyListener(new ModifyListener() {

            @Override
			public void modifyText(ModifyEvent e) {
                omFileName = txtFileName.getText();
            }
        });

        txtFileName.addFocusListener(new FocusListener() {

            @Override
			public void focusGained(FocusEvent e) {
            }

            @Override
			public void focusLost(FocusEvent e) {
                String name = txtFileName.getText();

                if (!name.endsWith(".om")) { //$NON-NLS-1$
                    txtFileName.setText(name + ".om"); //$NON-NLS-1$
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

        // Check box to create model with default schema
        createDefaultMMCheckbox = new Button(schemaComp, SWT.CHECK);
        createDefaultMMCheckbox.setSelection(true);
        createDefaultMMCheckbox
                .setText(Messages.OMAssetWizardPage_CreateDefaultSchema_label);
        createDefaultMMCheckbox.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL));

        // Checkbox to apply Standard Organization Type. Will be enabled only if
        // the createDefaultMMCheckBox is enabled.
        createDefaultMMandApplyCheckBox = new Button(schemaComp, SWT.CHECK);
        createDefaultMMandApplyCheckBox.setSelection(false);
        createDefaultMMandApplyCheckBox
                .setText(Messages.OMAssetWizardPage_ApplyDefaultType_label);
        createDefaultMMandApplyCheckBox.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL));

        createDefaultMMCheckbox.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                super.widgetSelected(e);
                if (e.getSource() == createDefaultMMCheckbox) {
                    Button source = (Button) e.getSource();

                    if (createDefaultMMandApplyCheckBox != null) {
                        if (!source.getSelection()) {
                            createDefaultMMandApplyCheckBox.setEnabled(false);
                        } else {
                            createDefaultMMandApplyCheckBox.setEnabled(true);
                        }
                    }
                }
            }
        });

        createModelCheckbox.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                super.widgetSelected(e);
                if (e.getSource() == createModelCheckbox) {
                    Button source = (Button) e.getSource();

                    if (txtFileName != null) {
                        if (!source.getSelection()) {
                            lblFilename.setEnabled(false);
                            txtFileName.setEnabled(false);
                            createDefaultMMCheckbox.setEnabled(false);
                            createDefaultMMandApplyCheckBox.setEnabled(false);

                        } else {
                            lblFilename.setEnabled(true);
                            txtFileName.setEnabled(true);
                            createDefaultMMCheckbox.setEnabled(true);
                            createDefaultMMandApplyCheckBox.setEnabled(true);

                        }
                    }
                }
            }
        });

        return modelGrp;
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
            String nameForOldName =
                    oldName + "." + OMResourcesActivator.OM_FILE_EXTENSION; //$NON-NLS-1$

            if (omFileName == null || omFileName.length() == 0
                    || omFileName.equals(DEFAULT_OM_FILENAME)
                    || omFileName.equals(nameForOldName)) {

                omFileName =
                        newName + "." + OMResourcesActivator.OM_FILE_EXTENSION; //$NON-NLS-1$

                if (txtFileName != null && !txtFileName.isDisposed()) {
                    txtFileName.setText(omFileName);
                }

                setPageComplete(validatePage());
            }
        }
    }

}
