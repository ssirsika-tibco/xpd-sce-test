/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.worklistfacade.resource.wizards;

import java.beans.PropertyChangeEvent;

import org.eclipse.core.resources.IFolder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
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
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.worklistfacade.resource.WorkListFacadeResourcePlugin;
import com.tibco.xpd.worklistfacade.resource.newproject.WorkListFacadeAssetConfigration;
import com.tibco.xpd.worklistfacade.resource.util.Messages;

/**
 * 
 * The Wizard Page for creation of Work List Facade Content , which is chained
 * into New Project Wizard.
 * 
 * @author aprasad
 * @since 26-Sep-2013
 */
public class WorkListFacadeAssetWizardPage extends
        AbstractSpecialFolderAssetWizardPage {

    private String wlfFilename = null;

    private Text txtFileName = null;

    /**
     * Option to specify , if the file containing initial model should be
     * created.
     */
    private Button createModelCheckbox = null;

    public WorkListFacadeAssetWizardPage() {
        super("WLFAssetWizard");//$NON-NLS-1$
        setTitle(Messages.WorkListFacadeAssetWizardPage_WLFAssetWizardTitle);
        setDescription(Messages.WorkListFacadeAssetWizardPage_WLFAssetWizardPageDescription);
        wlfFilename = getDefaultFileName();

		/* Sid ACE-7330: Show correct wizard banner icon for asset type. */
		setImageDescriptor(WorkListFacadeResourcePlugin.imageDescriptorFromPlugin(
				WorkListFacadeResourcePlugin.PLUGIN_ID, "icons/obj16/workListFacadeWizard.png"));

    }

    /**
     * 
     * @return default filename for WorkListFacade.
     */
    public static String getDefaultFileName() {
        return Messages.WorkListFacadeAssetWizardPage_WorkListFacade_CreateDefaultFileName
                + "." + WorkListFacadeResourcePlugin.WLF_FILE_EXTENSION; //$NON-NLS-1$
    }

    /**
     * Returns the default special folder name for WorkListFacade.
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
                        Messages.WorkListFacadeAssetWizardPage_Default_WLF_Folder_Name;
            }
        }

        return folderName;
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
        Object conf = getConfiguration();

        if (conf instanceof SpecialFolderAssetConfiguration) {
            ((SpecialFolderAssetConfiguration) conf)
                    .setSpecialFolderName(getSpecialFolderName());

            if (conf instanceof WorkListFacadeAssetConfigration) {
                WorkListFacadeAssetConfigration wlfConf =
                        (WorkListFacadeAssetConfigration) conf;

                if (createModelCheckbox == null) {
                    // Use a default as this wizard page wasn't created
                    wlfConf.setCreateFile(true);
                } else {
                    wlfConf.setCreateFile(createModelCheckbox.getSelection());
                }

                wlfConf.setFacadeFileName(wlfFilename);

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
        modelGrp.setText(com.tibco.xpd.worklistfacade.resource.util.Messages.WorkListFacadeAssetWizardPage_FacadeDetailsMsg);
        modelGrp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        GridLayout grpGridLayout = new GridLayout();
        grpGridLayout.marginHeight = 8;
        grpGridLayout.marginWidth = 8;
        grpGridLayout.horizontalSpacing = 8;
        grpGridLayout.verticalSpacing = 8;
        grpGridLayout.marginRight = 0;
        grpGridLayout.numColumns = 2;
        modelGrp.setLayout(grpGridLayout);

        // Create check-box for option to create the initial model
        createModelCheckbox = new Button(modelGrp, SWT.CHECK);
        createModelCheckbox.setSelection(true);
        createModelCheckbox
                .setText(com.tibco.xpd.worklistfacade.resource.util.Messages.WorkListFacadeAssetWizardPage_CreateInitialModelMsg);
        GridData gdModelCheckBox = new GridData(GridData.FILL_HORIZONTAL);
        gdModelCheckBox.horizontalSpan = 2;
        createModelCheckbox.setLayoutData(gdModelCheckBox);

        final Label lblFilename = new Label(modelGrp, SWT.NONE);
        lblFilename.setLayoutData(new GridData());
        lblFilename
                .setText(com.tibco.xpd.worklistfacade.resource.util.Messages.WorkListFacadeAssetWizardPage_Filename_Label);

        txtFileName = new Text(modelGrp, SWT.BORDER);
        GridData gData = new GridData(GridData.FILL_HORIZONTAL);
        gData.widthHint = TEXT_WIDTH_HINT;
        txtFileName.setLayoutData(gData);

        // Set the default file name
        /*
         * Allow Asset Pages To Listen to project name change - the filename may
         * have been pre-set.
         */
        if (wlfFilename != null && wlfFilename.length() > 0) {
            txtFileName.setText(wlfFilename);
        } else {
            txtFileName.setText(getDefaultFileName());
        }
        // Asset Page is hidden , Should use Default File Name.
        // txtFileName.addModifyListener(new ModifyListener() {
        //
        // @Override
        // public void modifyText(ModifyEvent e) {
        // wlfFilename = txtFileName.getText();
        // }
        // });

        txtFileName.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                String name = txtFileName.getText();

                if (!name
                        .endsWith("." + WorkListFacadeResourcePlugin.WLF_FILE_EXTENSION)) { //$NON-NLS-1$
                    txtFileName
                            .setText(name
                                    + "." + WorkListFacadeResourcePlugin.WLF_FILE_EXTENSION); //$NON-NLS-1$
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
             * If current package file name is unset, default or equivalent
             * same-name for previously selected project name then update it to
             * equivalent of new project name.
             * 
             * Remove spaces etc cos this is never valid for BOM (at least in
             * BPM dest env).
             */
            String nameForOldName =
                    NameUtil.getInternalName(oldName, false)
                            + "." + WorkListFacadeResourcePlugin.WLF_FILE_EXTENSION; //$NON-NLS-1$

            if (wlfFilename == null || wlfFilename.length() == 0
                    || wlfFilename.equals(getDefaultFileName())
                    || wlfFilename.equals(nameForOldName)) {

                wlfFilename =
                        NameUtil.getInternalName(newName, false)
                                + "." + WorkListFacadeResourcePlugin.WLF_FILE_EXTENSION; //$NON-NLS-1$

                if (txtFileName != null && !txtFileName.isDisposed()) {
                    txtFileName.setText(wlfFilename);
                }

                setPageComplete(validatePage());
            }
        }
    }

}
