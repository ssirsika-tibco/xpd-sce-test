/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.deploy.webdav.ui;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ResourceSelectionDialog;

import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.webdav.ui.internal.Messages;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;

/**
 * Wizard page for selecting target location within the WebDAV server.
 */
public class SelectWebDAVLocationPage extends AbstractXpdWizardPage {

    private Text mFileText;
    private boolean mInitialized;
    private Button mFileBrowseButton;

    public SelectWebDAVLocationPage(Server server) {
        super(Messages.SelectWebDAVLocationPage_label);
        setTitle(Messages.SelectWebDAVLocationPage_title);
        setDescription(Messages.SelectWebDAVLocationPage_desc);
    }

    public void createControl(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 1;
        gridLayout.verticalSpacing = 10;
        composite.setLayout(gridLayout);

        Group modulesGroup = new Group(composite, SWT.NULL);
        modulesGroup.setText(Messages.SelectWebDAVLocationPage_targetLocation_label);
        GridDataFactory.fillDefaults().grab(true, false).applyTo(modulesGroup);
        GridLayout paramGroupLayout = new GridLayout();
        paramGroupLayout.numColumns = 3;
        modulesGroup.setLayout(paramGroupLayout);

        Label fileLabel = new Label(modulesGroup, SWT.NONE);
        fileLabel.setText(Messages.SelectWebDAVLocationPage_file_label);
        GridDataFactory.swtDefaults().applyTo(fileLabel);

        mFileText = new Text(modulesGroup, SWT.SINGLE | SWT.BORDER);
        GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(mFileText);
        mFileText.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                if (mInitialized) {
                    setPageComplete(validatePage(false));
                }
            }
        });

        mFileBrowseButton = new Button(modulesGroup, SWT.PUSH);
        mFileBrowseButton.setText(Messages.SelectWebDAVLocationPage_browseServer_label);
        GridDataFactory.swtDefaults().applyTo(mFileBrowseButton);
        mFileBrowseButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                ResourceSelectionDialog dialog = new ResourceSelectionDialog(getShell(), ResourcesPlugin.getWorkspace().getRoot(),
                    Messages.SelectWebDAVLocationPage_selectTargetLocation_label);
                dialog.setInitialSelections(new Object[] {});
                if (dialog.open() == Window.OK) {
                    Object[] result = dialog.getResult();
                    for (Object element : result) {
                        if (element instanceof IFile) {
                            IFile file = (IFile)element;
                            mFileText.setText(file.getFullPath().toPortableString());
                            break;
                        }
                    }

                }
                ;
            }
        });

        initializePageDefaults();
        setPageComplete(validatePage(false));
        setControl(composite);
    }

    private void initializePageDefaults() {
        mInitialized = true;
    }

    private boolean validatePage(boolean showMessages) {
        setMessage(null);
        setErrorMessage(null);

        return true;
    }

}
