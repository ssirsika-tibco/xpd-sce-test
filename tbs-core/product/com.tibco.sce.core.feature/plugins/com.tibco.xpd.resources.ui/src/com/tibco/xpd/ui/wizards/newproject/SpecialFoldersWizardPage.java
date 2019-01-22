/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.ui.wizards.newproject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.resources.projectconfig.projectassets.AbstractSpecialFolderAssetConfigurator;
import com.tibco.xpd.resources.projectconfig.projectassets.SpecialFolderAssetConfiguration;
import com.tibco.xpd.resources.projectconfig.projectassets.util.ProjectAssetElement;
import com.tibco.xpd.resources.projectconfig.specialfolders.ISpecialFolderModel;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.internal.Messages;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;

/**
 * Wizard page that will display Special Folders to be set when creating a new
 * project.
 * 
 * @author njpatel
 * @since 3.2
 */
/* public */class SpecialFoldersWizardPage extends AbstractXpdWizardPage {

    private final Collection<ProjectAssetElement> assets;
    private List<Image> images = new ArrayList<Image>();
    private final Map<Text, ProjectAssetElement> textControls;
    private final FolderModifyListener modifyListener;

    /**
     * Wizard page to display special folders to be set during creation of new
     * project. The <i>specialFolderAssets</i> should all be using the
     * {@link AbstractSpecialFolderAssetConfigurator} and
     * {@link SpecialFolderAssetConfiguration}.
     * 
     * @param pageName
     * @param specialFolderAssets
     *            all assets that need their Special Folders set.
     */
    protected SpecialFoldersWizardPage(String pageName,
            Collection<ProjectAssetElement> specialFolderAssets) {
        super(pageName);
        this.assets = specialFolderAssets;
        setTitle(Messages.SpecialFoldersWizardPage_title);

        textControls = new HashMap<Text, ProjectAssetElement>();
        modifyListener = new FolderModifyListener();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets
     * .Composite)
     */
    public void createControl(Composite parent) {
        ScrolledComposite scrolledComp = new ScrolledComposite(parent,
                SWT.V_SCROLL);
        FillLayout layout = new FillLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        scrolledComp.setLayout(layout);

        Composite root = new Composite(scrolledComp, SWT.NONE);
        scrolledComp.setContent(root);
        scrolledComp.setExpandHorizontal(true);
        scrolledComp.setExpandVertical(true);
        root.setLayout(new GridLayout());

        for (ProjectAssetElement asset : assets) {
            try {
                if (asset.getConfigurator() instanceof AbstractSpecialFolderAssetConfigurator
                        && asset.getConfiguration() instanceof SpecialFolderAssetConfiguration) {
                    AbstractSpecialFolderAssetConfigurator configurator = (AbstractSpecialFolderAssetConfigurator) asset
                            .getConfigurator();
                    SpecialFolderAssetConfiguration config = (SpecialFolderAssetConfiguration) asset
                            .getConfiguration();

                    ISpecialFolderModel folderKind = configurator
                            .getFolderKind();
                    if (folderKind != null) {
                        Image img = null;
                        Group folderGrp = new Group(root, SWT.NONE);
                        folderGrp.setLayoutData(new GridData(SWT.FILL,
                                SWT.FILL, true, false));
                        folderGrp.setLayout(new GridLayout(3, false));
                        folderGrp.setText(folderKind.getName());

                        if (folderKind.getIcon() != null) {
                            img = folderKind.getIcon().createImage();
                            images.add(img);
                        }

                        createCLabel(folderGrp,
                                Messages.SpecialFoldersWizardPage_folder_label,
                                img);
                        /*
                         * Get the name of the folder name from the
                         * configuration and if not set then get the default
                         * folder name from the configurator
                         */
                        String folderName = config.getSpecialFolderName();
                        if (folderName == null || folderName.length() == 0) {
                            folderName = configurator.getDefaultFolderName();
                        }

                        Text text = createText(folderGrp, folderName);
                        text.addModifyListener(modifyListener);
                        textControls.put(text, asset);
                    }
                }
            } catch (CoreException e) {
                XpdResourcesUIActivator.getDefault().getLogger().error(e,
                        "Failed to get special folder configurator for " //$NON-NLS-1$
                                + asset.getName());
                setErrorMessage(String.format(
                        Messages.SpecialFoldersWizardPage_exception_label, e
                                .getLocalizedMessage()));
                setPageComplete(false);
            }
        }

        scrolledComp.setMinSize(root.computeSize(SWT.DEFAULT, SWT.DEFAULT));
        setControl(scrolledComp);

        setPageComplete(doValidatePage());
    }

    @Override
    public void dispose() {
        for (Image img : images) {
            img.dispose();
        }
        images.clear();

        super.dispose();
    }

    /**
     * Validate the input in this page.
     * 
     * @return <code>true</code> if page is valid, <code>false</code> otherwise.
     */
    private boolean doValidatePage() {
        List<String> folderNames = new ArrayList<String>();
        String message = Messages.SpecialFoldersWizardPage_setSpecialFolders_label;
        int severity = NONE;

        for (Entry<Text, ProjectAssetElement> entry : textControls.entrySet()) {
            String folderName = entry.getKey().getText();
            // Folder cannot be blank
            if (folderName == null || folderName.length() == 0) {
                ISpecialFolderModel kind = null;
                try {
                    kind = ((AbstractSpecialFolderAssetConfigurator) entry
                            .getValue().getConfigurator()).getFolderKind();
                } catch (CoreException e) {
                    XpdResourcesUIActivator.getDefault().getLogger().error(e,
                            "Failed to get special folder configurator for " //$NON-NLS-1$
                                    + entry.getValue().getName());
                }
                if (kind != null) {
                    setErrorMessage(String
                            .format(
                                    Messages.SpecialFoldersWizardPage_specialFolderCannotBeEmpty_error_shortdesc,
                                    kind.getName()));
                } else {
                    setErrorMessage(Messages.SpecialFoldersWizardPage_folderCannotBeEmpty_error_shortdesc);
                }
                return false;
            }

            // Validate folder name
            IStatus status = ResourcesPlugin.getWorkspace().validateName(
                    folderName, IResource.FOLDER);
            if (!status.isOK()) {
                switch (status.getSeverity()) {
                case IStatus.ERROR:
                    setErrorMessage(status.getMessage());
                    return false;
                case IStatus.WARNING:
                case IStatus.INFO:
                    message = status.getMessage();
                    severity = status.getSeverity() == IStatus.WARNING ? WARNING
                            : INFORMATION;
                    break;
                }
            }
            if (!nameContainedInList(folderName, folderNames)) {
                folderNames.add(folderName);
            } else {
                setErrorMessage(String
                        .format(
                                Messages.SpecialFoldersWizardPage_duplicateFolderName_error_shortdesc,
                                folderName));
                return false;
            }
        }

        setErrorMessage(null);
        setMessage(message, severity);
        return true;
    }

    /**
     * Check if the given name is already contained in the list. This will run a
     * case-insensitive search if running on a Windows platform.
     * 
     * @param name
     *            folder name to search
     * @param names
     *            folders name list
     * @return <code>true</code> if list already contains a value with the same
     *         name, <code>false</code> otherwise.
     */
    private boolean nameContainedInList(String name, List<String> names) {
        if (name != null && names != null && !names.isEmpty()) {
            /*
             * If running on Windows platform then need to make a
             * case-insensitive search for existing name
             */
            if (Platform.getOS().equals(Platform.OS_WIN32)) {
                for (String value : names) {
                    if (name.equalsIgnoreCase(value)) {
                        return true;
                    }
                }
            } else {
                return names.contains(name);
            }
        }
        return false;
    }

    /**
     * Create a {@link CLabel} and set it's label, image and grid data.
     * 
     * @param parent
     * @param label
     * @param img
     * @return created control
     */
    private CLabel createCLabel(Composite parent, String label, Image img) {
        CLabel lbl = new CLabel(parent, SWT.NONE);
        lbl.setText(label != null ? label : ""); //$NON-NLS-1$
        if (img != null) {
            lbl.setImage(img);
        }
        GridData data = new GridData();
        data.widthHint = 75;
        lbl.setLayoutData(data);
        return lbl;
    }

    /**
     * Create a {@link Text} control and set it's text, and grid data.
     * 
     * @param parent
     * @param text
     * @return created control
     */
    private Text createText(Composite parent, String text) {
        Text txt = new Text(parent, SWT.BORDER);
        GridData data = new GridData(SWT.FILL, SWT.CENTER, true, false);
        data.widthHint = 125;
        txt.setLayoutData(data);
        if (text != null) {
            txt.setText(text);
        }
        return txt;
    }

    /**
     * Listener of the folder text control modifications.
     * 
     * @author njpatel
     * 
     */
    private class FolderModifyListener implements ModifyListener {

        public void modifyText(ModifyEvent e) {
            boolean isValid = doValidatePage();
            setPageComplete(isValid);
            if (isValid) {
                ProjectAssetElement element = textControls.get(e.widget);
                if (element != null) {
                    try {
                        ((SpecialFolderAssetConfiguration) element
                                .getConfiguration())
                                .setSpecialFolderName(((Text) e.widget)
                                        .getText());
                    } catch (CoreException e1) {
                        XpdResourcesUIActivator.getDefault().getLogger().error(
                                e1,
                                "Failed to get special folder configurator for " //$NON-NLS-1$
                                        + element.getName());
                        setErrorMessage(String
                                .format(
                                        Messages.SpecialFoldersWizardPage_exception_label,
                                        e1.getLocalizedMessage()));
                        setPageComplete(false);
                    }
                }
            }
        }
    }
}
