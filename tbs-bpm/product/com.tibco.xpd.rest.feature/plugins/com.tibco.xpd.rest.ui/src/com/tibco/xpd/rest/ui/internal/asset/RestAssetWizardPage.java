/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.ui.internal.asset;

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

import com.tibco.xpd.resources.projectconfig.projectassets.AbstractSpecialFolderAssetWizardPage;
import com.tibco.xpd.resources.projectconfig.projectassets.IAssetProjectPropertyChangeListener;
import com.tibco.xpd.resources.projectconfig.projectassets.SpecialFolderAssetConfiguration;
import com.tibco.xpd.rest.ui.RestServicesUtil;
import com.tibco.xpd.rest.ui.internal.Messages;
import com.tibco.xpd.rsd.wc.RsdWorkingCopy;

/**
 * The Wizard Page for creation of default REST asset content, which is chained
 * into New Project Wizard.
 *
 * @author jarciuch
 * @since 4 Aug 2015
 */
public class RestAssetWizardPage extends AbstractSpecialFolderAssetWizardPage {

    private String rsdFileName = null;

    private Text txtFileName = null;

    private Button createModelCheckbox = null;

    public RestAssetWizardPage() {
        super("RestAssetWizard");//$NON-NLS-1$
        setTitle(Messages.RestAssetWizardPage_RestServices_title);
        setDescription(Messages.RestAssetWizardPage_RestServices_desc);
        rsdFileName = RestServicesUtil.RSD_DEFAULT_FILENAME;

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
                folderName = RestServicesUtil.REST_SPECIAL_FOLDER_DEFAULT_NAME;
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

            if (conf instanceof RestAssetConfiguration) {
                RestAssetConfiguration restAssetConfig =
                        (RestAssetConfiguration) conf;

                restAssetConfig.setRsdFileName(rsdFileName);

                if (createModelCheckbox == null) {
                    restAssetConfig.setCreateRsdFile(true);
                } else {
                    restAssetConfig.setCreateRsdFile(createModelCheckbox
                            .getSelection());
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
     * Creates the Group composite containing the Model Details controls.
     * 
     * @param parent
     * @return Control
     */
    private Control createModelGroup(Composite parent) {
        // Create the group
        Group modelGrp = new Group(parent, SWT.NONE);
        modelGrp.setText(Messages.RestAssetWizardPage_DescriptorDetails_label);
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
                .setText(Messages.RestAssetWizardPage_CreateInitialRsd_label);
        GridData gdModelCheckBox = new GridData(GridData.FILL_HORIZONTAL);
        gdModelCheckBox.horizontalSpan = 2;
        createModelCheckbox.setLayoutData(gdModelCheckBox);

        final Label lblFilename = new Label(modelGrp, SWT.NONE);
        lblFilename.setLayoutData(new GridData());
        lblFilename.setText(Messages.RestAssetWizardPage_FilaName_label);

        txtFileName = new Text(modelGrp, SWT.BORDER);
        GridData gData = new GridData(GridData.FILL_HORIZONTAL);
        gData.widthHint = TEXT_WIDTH_HINT;
        txtFileName.setLayoutData(gData);

        if (rsdFileName != null && rsdFileName.length() > 0) {
            txtFileName.setText(rsdFileName);
        } else {
            txtFileName.setText(RestServicesUtil.RSD_DEFAULT_FILENAME);
        }

        txtFileName.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                rsdFileName = txtFileName.getText();
            }
        });

        txtFileName.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                String name = txtFileName.getText();

                if (!name.endsWith(RsdWorkingCopy.RSD_FILE_EXTENSION)) {
                    txtFileName.setText(name
                            + RsdWorkingCopy.RSD_FILE_EXTENSION);
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
                        } else {
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
                    oldName + "." + RsdWorkingCopy.RSD_FILE_EXTENSION; //$NON-NLS-1$

            if (rsdFileName == null
                    || rsdFileName.length() == 0
                    || rsdFileName
                            .equals(RestServicesUtil.RSD_DEFAULT_FILENAME)
                    || rsdFileName.equals(nameForOldName)) {

                rsdFileName = newName + "." + RsdWorkingCopy.RSD_FILE_EXTENSION; //$NON-NLS-1$

                if (txtFileName != null && !txtFileName.isDisposed()) {
                    txtFileName.setText(rsdFileName);
                }

                setPageComplete(validatePage());
            }
        }
    }

}
