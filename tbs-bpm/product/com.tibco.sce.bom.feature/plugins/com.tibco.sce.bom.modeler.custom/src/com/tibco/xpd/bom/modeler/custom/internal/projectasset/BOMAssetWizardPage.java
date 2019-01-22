/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.internal.projectasset;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFolder;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.modeler.diagram.part.BOMDiagramEditorPlugin;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.firstclassprofiles.FirstClassProfileManager;
import com.tibco.xpd.bom.resources.firstclassprofiles.IFirstClassProfileExtension;
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.resources.projectconfig.projectassets.AbstractSpecialFolderAssetWizardPage;
import com.tibco.xpd.resources.projectconfig.projectassets.IAssetProjectPropertyChangeListener;
import com.tibco.xpd.resources.projectconfig.projectassets.SpecialFolderAssetConfiguration;
import com.tibco.xpd.ui.util.NameUtil;

public class BOMAssetWizardPage extends AbstractSpecialFolderAssetWizardPage {

    private String bomFilename = null;

    private String extId = null;

    private Text txtFileName = null;

    private static final String BOM_IMAGE_PATH = "icons/obj16/wizard.png"; //$NON-NLS-1$

    private Button createModelCheckbox = null;

    public BOMAssetWizardPage() {
        super("BomAssetWizard");//$NON-NLS-1$
        setTitle(com.tibco.xpd.bom.modeler.diagram.part.Messages.BOMAssetWizardPage_Title_label);
        setDescription(com.tibco.xpd.bom.modeler.diagram.part.Messages.BOMAssetWizardPage_Description_label);
        bomFilename = getDefaultFileName(); //$NON-NLS-1$
    }

    /**
     * XPD-2139
     * 
     * @return default filename for bom.
     */
    private String getDefaultFileName() {
        return com.tibco.xpd.bom.modeler.diagram.part.Messages.UMLDiagramEditorUtil_CreateDiagramDefaultFileNameLabel
                + "." + BOMResourcesPlugin.BOM_FILE_EXTENSION; //$NON-NLS-1$
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
                folderName = Messages.default_bomfolder_name;
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

            if (conf instanceof BOMSpecialFolderAssetConfiguration) {
                BOMSpecialFolderAssetConfiguration bomConf =
                        (BOMSpecialFolderAssetConfiguration) conf;

                if (createModelCheckbox == null) {
                    // Use a default as this wizard page wasn't created
                    bomConf.setCreateModel(true);
                } else {
                    bomConf.setCreateModel(createModelCheckbox.getSelection());
                }

                bomConf.setBomFileName(bomFilename);
                bomConf.setBomType(extId);

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
        modelGrp.setText(com.tibco.xpd.bom.modeler.diagram.part.Messages.BOMAssetWizardPage_ModelDetailsGroup_label);
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
                .setText(com.tibco.xpd.bom.modeler.diagram.part.Messages.BOMAssetWizardPage_CreateInitialCheckBox_label);
        GridData gdModelCheckBox = new GridData(GridData.FILL_HORIZONTAL);
        gdModelCheckBox.horizontalSpan = 2;
        createModelCheckbox.setLayoutData(gdModelCheckBox);

        final Label lblFilename = new Label(modelGrp, SWT.NONE);
        lblFilename.setLayoutData(new GridData());
        lblFilename
                .setText(com.tibco.xpd.bom.modeler.diagram.part.Messages.BOMAssetWizardPage_Filename_label);

        txtFileName = new Text(modelGrp, SWT.BORDER);
        GridData gData = new GridData(GridData.FILL_HORIZONTAL);
        gData.widthHint = TEXT_WIDTH_HINT;
        txtFileName.setLayoutData(gData);

        // Set the default file name
        /*
         * SID XPD-2139 Allow Asset Pages To Listen to project name change - the
         * filename may have been pre-set.
         */
        if (bomFilename != null && bomFilename.length() > 0) {
            txtFileName.setText(bomFilename);
        } else {
            txtFileName.setText(getDefaultFileName());
        }

        txtFileName.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent e) {
                bomFilename = txtFileName.getText();
            }
        });

        txtFileName.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                String name = txtFileName.getText();

                if (!name.endsWith(".bom")) { //$NON-NLS-1$
                    txtFileName.setText(name + ".bom"); //$NON-NLS-1$
                }

            }
        });

        // Add radio buttons to select BOM model type, OR not

        List<IFirstClassProfileExtension> profileExts =
                FirstClassProfileManager.getInstance().getExtensions();
        Set<String> excludedExts = Collections.<String>singleton(BOMUtils.CDS_EXTENSION_ID);
        
        profileExts =
                BOMUtils.getCreatableProfileExtensions(profileExts,
                        excludedExts);
        if ((profileExts != null) && !profileExts.isEmpty()) {

            // label for BOM type field
            final Label lblType = new Label(modelGrp, SWT.NONE);
            GridData gdLblType = new GridData();
            gdLblType.verticalAlignment = SWT.TOP;
            gdLblType.verticalIndent = 5;
            lblType.setLayoutData(gdLblType);
            lblType.setText(com.tibco.xpd.bom.modeler.diagram.part.Messages.BOMAssetWizardPage_Type_label);

            // Default BOM type option
            Composite radioBtnsComp = new Composite(modelGrp, SWT.NONE);
            radioBtnsComp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
                    true, false));
            GridLayout radioGridLayout = new GridLayout();
            radioGridLayout.numColumns = 1;
            radioBtnsComp.setLayout(radioGridLayout);
            final Button[] radiosBOMType = new Button[profileExts.size() + 1];
            List<Image> images = new ArrayList<Image>();
            radiosBOMType[0] = new Button(radioBtnsComp, SWT.RADIO);
            radiosBOMType[0]
                    .setText(com.tibco.xpd.bom.modeler.diagram.part.Messages.UMLCreationBOMTypeWizardPage_BOMType_label);

            ImageDescriptor desc =
                    BOMDiagramEditorPlugin
                            .getBundledImageDescriptor(BOM_IMAGE_PATH);

            if (desc != null) {
                Image img = desc.createImage();
                images.add(img);
                radiosBOMType[0].setImage(img);
            }

            ButtonSelectionListener listener = new ButtonSelectionListener();
            radiosBOMType[0].addSelectionListener(listener);
            radiosBOMType[0].setSelection(true);

            // Other BOM types
            int idx = 1;
            for (IFirstClassProfileExtension ext : profileExts) {
                Button btn = new Button(radioBtnsComp, SWT.RADIO);
                btn.setText(ext.getLabel());
                btn.setData(ext.getId());

                ImageDescriptor icon = ext.getIcon();
                if (icon != null) {
                    Image img = icon.createImage();
                    btn.setImage(img);
                    images.add(img);
                }

                btn.addSelectionListener(listener);
                radiosBOMType[idx] = btn;
                idx++;
            }

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
                                lblType.setEnabled(false);
                                for (Button btn : radiosBOMType) {
                                    btn.setEnabled(false);
                                }

                            } else {
                                lblFilename.setEnabled(true);
                                txtFileName.setEnabled(true);
                                lblType.setEnabled(true);
                                for (Button btn : radiosBOMType) {
                                    btn.setEnabled(true);
                                }
                            }
                        }
                    }
                }
            });

        }

        return modelGrp;
    }

    /**
     * Button selection adapter that will listen for model type selection.
     * 
     * @author njpatel
     * 
     */
    private class ButtonSelectionListener extends SelectionAdapter {
        @Override
        public void widgetSelected(SelectionEvent e) {
            if (e.widget instanceof Button
                    && ((Button) e.widget).getSelection()) {
                Object data = e.widget.getData();
                if (data instanceof String) {
                    extId = (String) data;
                } else {
                    extId = null;
                }
            }
        }
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
             * 
             * Remove spaces etc cos this is never valid for BOM (at least in
             * BPM dest env).
             */
            String nameForOldName =
                    NameUtil.getInternalName(oldName, false)
                            + "." + BOMResourcesPlugin.BOM_FILE_EXTENSION; //$NON-NLS-1$

            if (bomFilename == null || bomFilename.length() == 0
                    || bomFilename.equals(getDefaultFileName())
                    || bomFilename.equals(nameForOldName)) {

                bomFilename =
                        NameUtil.getInternalName(newName, false)
                                + "." + BOMResourcesPlugin.BOM_FILE_EXTENSION; //$NON-NLS-1$

                if (txtFileName != null && !txtFileName.isDisposed()) {
                    txtFileName.setText(bomFilename);
                }

                setPageComplete(validatePage());
            }
        }
    }

}
