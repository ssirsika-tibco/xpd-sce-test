/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.wizards;

import java.beans.PropertyChangeEvent;

import org.eclipse.core.resources.IFolder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.n2.globalsignal.resource.GsdResourcePlugin;
import com.tibco.xpd.n2.globalsignal.resource.internal.Messages;
import com.tibco.xpd.n2.globalsignal.resource.newproject.GsdAssetConfiguration;
import com.tibco.xpd.resources.projectconfig.projectassets.AbstractSpecialFolderAssetWizardPage;
import com.tibco.xpd.resources.projectconfig.projectassets.IAssetProjectPropertyChangeListener;
import com.tibco.xpd.resources.projectconfig.projectassets.SpecialFolderAssetConfiguration;
import com.tibco.xpd.ui.util.NameUtil;

/**
 * The Wizard Page for creation of Global Signal Definition Content , which is
 * chained into New Project Wizard.
 * 
 * @author sajain
 * @since Jan 27, 2015
 */
public class GsdAssetWizardPage extends
        AbstractSpecialFolderAssetWizardPage {

    private String gsdFileName = null;

    private Text txtFileName = null;

    /**
     * Option to specify , if the file containing initial model should be
     * created.
     */
    private Button createModelCheckbox = null;

    /**
     * @param id
     */
    public GsdAssetWizardPage() {
        super("GSDAssetWizard"); //$NON-NLS-1$
        setTitle(Messages.GlobalSignalDefinitionAssetWizardPage_WizardTitle);
        setDescription(Messages.GlobalSignalDefinitionAssetWizardPage_WizardDesc);
        gsdFileName = getDefaultFileName();
    }

    /**
     * Get default GSD file name.
     * 
     * @return default filename for .gsd file.
     */
    public static String getDefaultFileName() {
        return Messages.GlobalSignalDefinitionAssetWizardPage_DefaultFileName
                + "." + GsdResourcePlugin.GSD_FILE_EXTENSION; //$NON-NLS-1$
    }

    /**
     * 
     * @see com.tibco.xpd.resources.projectconfig.projectassets.IAssetWizardPage#updateConfiguration()
     */
    @Override
    public void updateConfiguration() {

        Object conf = getConfiguration();

        if (conf instanceof SpecialFolderAssetConfiguration) {

            ((SpecialFolderAssetConfiguration) conf)
                    .setSpecialFolderName(getSpecialFolderName());

            if (conf instanceof GsdAssetConfiguration) {
                GsdAssetConfiguration gsdConfig =
                        (GsdAssetConfiguration) conf;

                if (createModelCheckbox == null) {

                    /*
                     * Use a default as this wizard page wasn't created
                     */
                    gsdConfig.setCreateFile(true);

                } else {

                    gsdConfig.setCreateFile(createModelCheckbox.getSelection());
                }

                gsdConfig.setGSDFileName(gsdFileName);

            }
        }
    }

    /**
     * Returns the default special folder name for Global Signal Definition.
     * 
     * @see com.tibco.xpd.resources.projectconfig.projectassets.AbstractSpecialFolderAssetWizardPage#getDefaultSpecialFolderName()
     * 
     * @return
     */
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

                folderName =
                        Messages.GlobalSignalDefinitionAssetWizardPage_DefaultFolderName;
            }
        }

        return folderName;
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

        /*
         * Create the group
         */

        Group modelGrp = new Group(parent, SWT.NONE);
        modelGrp.setText(Messages.GlobalSignalDefinitionAssetWizardPage_ModelDetailsLabel);
        modelGrp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        GridLayout grpGridLayout = new GridLayout();
        grpGridLayout.marginHeight = 8;
        grpGridLayout.marginWidth = 8;
        grpGridLayout.horizontalSpacing = 8;
        grpGridLayout.verticalSpacing = 8;
        grpGridLayout.marginRight = 0;
        grpGridLayout.numColumns = 2;
        modelGrp.setLayout(grpGridLayout);

        /*
         * Create check-box for option to create the initial model
         */
        createModelCheckbox = new Button(modelGrp, SWT.CHECK);
        createModelCheckbox.setSelection(true);
        createModelCheckbox
                .setText(Messages.GlobalSignalDefinitionAssetWizardPage_CreateInitialGSDLabel);
        GridData gdModelCheckBox = new GridData(GridData.FILL_HORIZONTAL);
        gdModelCheckBox.horizontalSpan = 2;
        createModelCheckbox.setLayoutData(gdModelCheckBox);

        final Label lblFilename = new Label(modelGrp, SWT.NONE);
        lblFilename.setLayoutData(new GridData());
        lblFilename
                .setText(Messages.GlobalSignalDefinitionAssetWizardPage_FilenameLabel);

        txtFileName = new Text(modelGrp, SWT.BORDER);
        GridData gData = new GridData(GridData.FILL_HORIZONTAL);
        gData.widthHint = TEXT_WIDTH_HINT;
        txtFileName.setLayoutData(gData);

        /*
         * Allow Asset Pages To Listen to project name change - the filename may
         * have been pre-set.
         */
        if (gsdFileName != null && gsdFileName.length() > 0) {
            txtFileName.setText(gsdFileName);
        } else {
            txtFileName.setText(getDefaultFileName());
        }

        txtFileName.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent e) {
                gsdFileName = txtFileName.getText();
            }
        });

        txtFileName.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                String name = txtFileName.getText();

                if (!name
                        .endsWith("." + GsdResourcePlugin.GSD_FILE_EXTENSION)) { //$NON-NLS-1$
                    txtFileName
                            .setText(name
                                    + "." + GsdResourcePlugin.GSD_FILE_EXTENSION); //$NON-NLS-1$
                }

            }
        });

        return modelGrp;
    }

    /**
     * When informed of project name change then change our asset name to match
     * (unless user has changed to something else).
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
             * If current GSD file name is unset, default or equivalent
             * same-name for previously selected project name then update it to
             * equivalent of new project name.
             */
            String nameForOldName =
                    NameUtil.getInternalName(oldName, false)
                            + "." + GsdResourcePlugin.GSD_FILE_EXTENSION; //$NON-NLS-1$

            if (gsdFileName == null || gsdFileName.length() == 0
                    || gsdFileName.equals(getDefaultFileName())
                    || gsdFileName.equals(nameForOldName)) {

                gsdFileName =
                        NameUtil.getInternalName(newName, false)
                                + "." + GsdResourcePlugin.GSD_FILE_EXTENSION; //$NON-NLS-1$

                if (txtFileName != null && !txtFileName.isDisposed()) {
                    txtFileName.setText(gsdFileName);
                }

                setPageComplete(validatePage());
            }
        }
    }

}
