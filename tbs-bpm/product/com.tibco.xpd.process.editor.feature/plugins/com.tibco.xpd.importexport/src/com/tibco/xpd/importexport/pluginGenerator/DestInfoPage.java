/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.importexport.pluginGenerator;

import java.io.File;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.importexport.internal.Messages;
import com.tibco.xpd.importexport.pluginGenerator.NewPluginGeneratorWizard.PluginDestination;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;

/**
 * The destination selection page of the generate custom plug-in wizard. This
 * will allow user to export the plug-in to a given location.
 * 
 * 
 * @author njpatel
 */
public class DestInfoPage extends AbstractXpdWizardPage implements
        SelectionListener {

    private Label labelFolder = null;

    private Button btnBrowse = null;

    private Text txtFolder = null;

    /**
     * Create the Destination wizard page
     * 
     * @param pageName
     * @param title
     * @param titleImage
     */
    public DestInfoPage(String pageName, String title,
            ImageDescriptor titleImage) {
        super(pageName, title, titleImage);
    }

    /**
     * Get the destination selection
     * 
     * @return <code>{@link PluginDestination#INSTALLPLUGIN}</code> - If the
     *         plug-in needs installing into the plug-ins folder<br>
     *         <code>{@link PluginDestination#CREATEINFOLDER}</code> - If the
     *         plug-in needs to be created in the given folder
     */
    public PluginDestination getDestinationSelection() {
        /*
         * XPD-7040: We have now removed the 'Install Plug-in' option from the
         * destination.
         */
        return PluginDestination.CREATEINFOLDER;
    }

    /**
     * Get the folder to create the plug-in in (use if folder destination
     * selected)
     * 
     * @return Absolute path of the selected folder in the file system
     */
    public String getDestinationPath() {
        return txtFolder.getText().trim();
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

        createDestinationControls(container);

        setControl(container);

        // Validate content
        doValidatePage();
    }

    /**
     * Create the destination selection group
     * 
     * @param parent
     */
    private void createDestinationControls(Composite parent) {
        GridData gridData = null;

        Group grpDest = new Group(parent, SWT.NONE);
        grpDest.setText(Messages.DestPage_DestinationGrpTitle);
        grpDest.setLayout(new GridLayout(3, false));
        gridData = new GridData(GridData.FILL_HORIZONTAL);
        grpDest.setLayoutData(gridData);

        // Create plug-in at given path option
        labelFolder = new Label(grpDest, SWT.NONE);
        labelFolder.setText(Messages.DestPage_FolderRadioBtn);

        txtFolder = new Text(grpDest, SWT.BORDER);
        gridData = new GridData(GridData.FILL_HORIZONTAL);
        txtFolder.setLayoutData(gridData);
        txtFolder.setEnabled(true);
        txtFolder.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent e) {
                // Check that the given path is a valid folder
                String szPath = getDestinationPath();
                String szErrMsg = null;

                setPageComplete(false);

                if (szPath.length() > 0) {
                    File f = new File(szPath);

                    if (!f.isDirectory()) {
                        szErrMsg = Messages.DestPage_FolderNotValid;
                    }
                }

                // Set the error message
                setErrorMessage(szErrMsg);

                // If no error message set here then validate the page further
                if (szErrMsg == null)
                    doValidatePage();
            }

        });

        // Browse button
        btnBrowse = new Button(grpDest, SWT.NONE);
        btnBrowse.setText("..."); //$NON-NLS-1$
        btnBrowse.setEnabled(true);
        btnBrowse.addSelectionListener(this);
    }

    /**
     * Validate the page to make sure all required data has been provided
     */
    private void doValidatePage() {
        String szErrMsg = null;

        setPageComplete(false);

        // If path selected and no path given then error
        if (getDestinationSelection() == PluginDestination.CREATEINFOLDER) {
            if (getDestinationPath().length() == 0)
                szErrMsg = Messages.DestPage_NoPathSelected;
            else
                setMessage(Messages.DestPage_FolderSelectionMsg);
        } else {
            // Install plug-in selected
            setMessage(Messages.DestPage_InstallMessage);
        }

        setErrorMessage(szErrMsg);
        setPageComplete(szErrMsg == null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt
     * .events.SelectionEvent)
     */
    @Override
    public void widgetSelected(SelectionEvent e) {
        // Enable the path selection if Path selected
        if (e.getSource() == btnBrowse) {
            String szFolder = null;
            DirectoryDialog dDialog = new DirectoryDialog(getShell());
            dDialog.setMessage(Messages.DestPage_SelectFolder);

            // Set the initial filter
            dDialog.setFilterPath(getDestinationPath());

            // Open the dialog
            szFolder = dDialog.open();

            if (szFolder != null)
                txtFolder.setText(szFolder);
        }

        doValidatePage();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse
     * .swt.events.SelectionEvent)
     */
    @Override
    public void widgetDefaultSelected(SelectionEvent e) {
        // Nothing to do
    }

}
