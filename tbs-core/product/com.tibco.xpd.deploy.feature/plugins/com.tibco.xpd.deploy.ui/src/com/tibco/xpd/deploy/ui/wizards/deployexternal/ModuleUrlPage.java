/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.wizards.deployexternal;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.deploy.ui.internal.Messages;
import com.tibco.xpd.deploy.ui.wizards.BaseDeployWizardPage;

/**
 * Page for entering the URL of a module to be deployed.
 * <p>
 * <i>Created: 3 September 2006</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class ModuleUrlPage extends BaseDeployWizardPage {

    private Text urlText;

    private URI selectedURI = null;

    private boolean ignoreModifyEvent = false;

    public ModuleUrlPage() {
        super("ExternalModuleSelection"); //$NON-NLS-1$
        setTitle(Messages.ModuleUrlPage_Page_title);
        setDescription(Messages.ModuleUrlPage_Page_longdesc2);
    }

    public void createControl(Composite parent) {
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 1;
        gridLayout.verticalSpacing = 10;
        Composite composite = new Composite(parent, SWT.NONE);
        PlatformUI.getWorkbench().getHelpSystem()
                .setHelp(composite, getTitle());
        composite.setLayout(gridLayout);

        Group serverTypeGroup = new Group(composite, SWT.NULL);
        serverTypeGroup
                .setText(Messages.ModuleUrlPage_ExternalModuleGroup_label);
        GridData serverGroupGridData = new GridData(GridData.FILL_HORIZONTAL);
        serverTypeGroup.setLayoutData(serverGroupGridData);
        GridLayout paramGroupLayout = new GridLayout();
        paramGroupLayout.numColumns = 3;
        serverTypeGroup.setLayout(paramGroupLayout);

        createLabel(serverTypeGroup, Messages.ModuleUrlPage_ModuleUrl_label);
        urlText = createText(serverTypeGroup, ""); //$NON-NLS-1$
        urlText.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                setPageComplete(validatePage(true));
            }
        });

        Button browse = new Button(serverTypeGroup, SWT.PUSH);
        browse.setText(Messages.ModuleUrlPage_Browse_label);
        browse.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                browseFileSystem();
            }
        });

        initializePageDefaults();
        setPageComplete(validatePage(false));
        urlText.setFocus();
        urlText.selectAll();
        setControl(composite);
    }

    private void initializePageDefaults() {
        urlText.setText(""); //$NON-NLS-1$

    }

    private boolean validatePage(boolean showMessages) {
        if (!ignoreModifyEvent) {
            selectedURI = null;

            setMessage(null);
            setErrorMessage(null);
            // errors
            if (getModuleControlText().length() == 0) {
                if (showMessages) {
                    setErrorMessage(Messages.ModuleUrlPage_EmptyUrl_message);
                }
                urlText.setFocus();
                return false;
            }

            boolean validUrl = false;
            try {
                File file = new File(getModuleControlText());

                if (file.exists()) {
                    // This will throw a malformedURL exception if the path is
                    // rubbish.
                    URL url = file.toURL();
    
                    selectedURI = file.toURI();
    
                    validUrl = true;
                }
            } catch (MalformedURLException e) {
                validUrl = false;
            }

            if (!validUrl) {
                if (showMessages) {
                    setErrorMessage(Messages.ModuleUrlPage_IncorrectUrl_message);
                }
                urlText.setFocus();
                return false;
            }
            // warnings

            // no warnings and errors
            setMessage(null);
            setErrorMessage(null);
        }
        return true;
    }

    String getModuleControlText() {
        return urlText.getText().trim();
    }

    void setModuleUrlTextControl(String url) {
        urlText.setText(url);
    }

    URI getModuleURI() {
        return selectedURI;
    }

    protected boolean browseFileSystem() {
        FileDialog fileDialog =
                new FileDialog(getShell(), SWT.OPEN | SWT.SINGLE);

        URI modelLocation = getModuleURI();
        if (modelLocation != null) {
            File file = new File(modelLocation);
            if (file.exists()) {
                fileDialog.setFileName(file.toString());
            }
        }

        if (fileDialog.open() != null && fileDialog.getFileNames().length == 1) {
            String fileName = fileDialog.getFileName();
            String filePath = fileDialog.getFilterPath();
            try {
                // Don't re-validate when browsing.
                ignoreModifyEvent = true;

                File file = new File(filePath, fileName);

                // This will throw a malformedURL exception if the path is
                // rubbish.
                URL url = file.toURL();

                selectedURI = file.toURI();
                setModuleUrlTextControl(file.getAbsolutePath());

                return true;

            } catch (MalformedURLException e) {
                // Log exception
                DeployUIActivator.getDefault().getLogger().error(e);
            } finally {
                ignoreModifyEvent = false;
            }
        }
        return false;
    }

    public void performHelp() {
        PlatformUI.getWorkbench().getHelpSystem().displayHelp(getTitle());
    }

}
