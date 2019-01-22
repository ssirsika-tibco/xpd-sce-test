/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.rcp.internal.svn;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.rcp.internal.Messages;

/**
 * This class is copy of
 * org.tigris.subversion.subclipse.ui.wizards.CheckoutWizardProjectPage. I
 * needed to override the default location behaviour
 * 
 * @author kupadhya
 * @since 30 Nov 2012
 */
public class SVNCheckOutWizardChkOutLocPage extends WizardPage {

    private Label locationLabel;

    private Text locationText;

    private Button browseButton;

    public SVNCheckOutWizardChkOutLocPage(String pageName, String title,
            ImageDescriptor titleImage) {
        super(pageName, title, titleImage);
        setPageComplete(true);
    }

    @Override
    public void createControl(Composite parent) {
        Composite outerContainer = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.numColumns = 3;
        outerContainer.setLayout(layout);
        outerContainer.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
                | GridData.HORIZONTAL_ALIGN_FILL));

        locationLabel = new Label(outerContainer, SWT.NONE);
        locationLabel.setText(Messages.SVNCheckoutWizardProjectPage_location);
        locationText = new Text(outerContainer, SWT.BORDER);
        GridData data =
                new GridData(GridData.FILL_HORIZONTAL
                        | GridData.GRAB_HORIZONTAL);
        locationText.setLayoutData(data);
        locationText.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                setPageComplete();
            }
        });

        browseButton = new Button(outerContainer, SWT.PUSH);
        browseButton.setText(Messages.SVNCheckoutWizardProjectPage_browse);

        browseButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                DirectoryDialog dialog = new DirectoryDialog(getShell());
                dialog.setMessage(Messages.SVNCheckoutInto_message);
                String directory = dialog.open();
                if (directory != null) {
                    locationText.setText(directory);
                }
            }
        });

        setLocationEnablement();

        setMessage(Messages.SVNCheckoutWizardProjectPage_text);

        setControl(outerContainer);
    }

    private void setLocationEnablement() {
        locationLabel.setEnabled(true);
        locationText.setEnabled(true);
        browseButton.setEnabled(true);
    }

    private void setPageComplete() {
        boolean isValidLocation = isValidLocation();
        setPageComplete(isValidLocation);
        if (!isValidLocation) {
            setErrorMessage(Messages.SVNCheckOutWizardChkOutLocPage_InvalidCheckOutLocation);
        } else {
            setErrorMessage(null);
        }
    }

    public String getLocation() {
        if (locationText == null) {
            SVNCheckOutWizard wizard = (SVNCheckOutWizard) getWizard();
            return ResourcesPlugin.getWorkspace().getRoot().getLocation()
                    .toString()
                    + File.separator + wizard.getProjectName();
        } else
            return locationText.getText().trim();
    }

    public String getCanonicalLocation() {
        return normalizeCase(getLocation());
    }

    private String normalizeCase(String location) {
        File dir = new File(location);
        String caseFixed;
        String original = dir.getAbsolutePath();
        try {
            caseFixed = dir.getCanonicalPath();
        } catch (IOException e) {
            return location;
        }
        // Make sure the path name did not change. If the
        // path is a symlink, then getCanonical will change
        // the path to the real path and we just have to go
        // with the original.
        if (caseFixed.equalsIgnoreCase(original))
            return caseFixed;
        else
            return location;
    }

    /**
     * @see org.eclipse.jface.wizard.WizardPage#isPageComplete()
     * 
     * @return
     */
    @Override
    public boolean isPageComplete() {
        return isValidLocation();
    }

    private boolean isValidLocation() {
        String currentWorkArea =
                ResourcesPlugin.getWorkspace().getRoot().getLocation()
                        .toString();
        String selectedLocation = locationText.getText();
        selectedLocation = selectedLocation.replace("\\", "/"); //$NON-NLS-1$ //$NON-NLS-2$
        selectedLocation = selectedLocation.trim();
        File chckOutLocation = new File(selectedLocation);
        boolean isNotInWorkArea =
                selectedLocation.length() > 0
                        && !selectedLocation.contains(currentWorkArea);
        if (isNotInWorkArea) {
            if (chckOutLocation.isDirectory()) {
                // if it exists then it should be empty
                isNotInWorkArea = chckOutLocation.list().length == 0;
            }
            // if the directory does not exists then there are 'n' number of
            // checks so will leave it to the user to ensure he gets it right
        }
        return isNotInWorkArea;
    }
}
