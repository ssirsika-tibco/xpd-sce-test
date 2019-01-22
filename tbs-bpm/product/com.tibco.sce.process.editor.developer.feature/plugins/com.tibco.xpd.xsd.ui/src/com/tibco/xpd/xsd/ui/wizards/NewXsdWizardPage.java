/*
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.xsd.ui.wizards;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;

import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;
import com.tibco.xpd.xsd.ui.internal.Messages;

/**
 * The "New" wizard page allows setting the container for the new file as well
 * as the file name. The page will only accept file name without the extension
 * OR with the extension that matches the expected one (mpe).
 */

public class NewXsdWizardPage extends AbstractXpdWizardPage {
    private static final String EXT_XML = "xml"; //$NON-NLS-1$

    private static final String EXT_XSD = "xsd"; //$NON-NLS-1$

    private static final String[] XML_EXTENSIONS = new String[] { "*.xml" }; //$NON-NLS-1$

    private Text containerText;

    private Text xmlFileText;

    private Text xsdFileText;

    private ISelection selection;

    /**
     * Constructor.
     * 
     * @param pageName
     */
    public NewXsdWizardPage(ISelection selection) {
        super("wizardPage"); //$NON-NLS-1$
        setTitle(Messages.NewXsdWizardPage_XmlSchemaFromXmlDocTitle);
        setDescription(Messages.NewXsdWizardPage_XmlSchemaFromXmlDocShortDesc);
        this.selection = selection;
    }

    /**
     * @see IDialogPage#createControl(Composite)
     */
    @Override
    public void createControl(Composite parent) {
        Composite container = new Composite(parent, SWT.NULL);
        GridLayout layout = new GridLayout();
        container.setLayout(layout);
        layout.numColumns = 3;
        layout.verticalSpacing = 9;

        // input file
        Label label = new Label(container, SWT.NULL);
        label.setText(Messages.NewXsdWizardPage_XmlDocumentFileLabel);

        xmlFileText = new Text(container, SWT.BORDER | SWT.SINGLE);
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        xmlFileText.setLayoutData(gd);
        xmlFileText.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                dialogChanged();
            }
        });
        Button button = new Button(container, SWT.PUSH);
        button.setText(Messages.NewXsdWizardPage_BrowseButton);
        button.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                handleFileBrowse();
            }
        });

        // container
        label = new Label(container, SWT.NULL);
        label.setText(Messages.NewXsdWizardPage_FolderLabel);

        containerText = new Text(container, SWT.BORDER | SWT.SINGLE);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        containerText.setLayoutData(gd);
        containerText.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                dialogChanged();
            }
        });

        button = new Button(container, SWT.PUSH);
        button.setText(Messages.NewXsdWizardPage_BrowseButton);
        button.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                handleContainerBrowse();
            }
        });

        // output file
        label = new Label(container, SWT.NULL);
        label.setText(Messages.NewXsdWizardPage_XsdFileLabel);

        xsdFileText = new Text(container, SWT.BORDER | SWT.SINGLE);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        xsdFileText.setLayoutData(gd);
        xsdFileText.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                dialogChanged();
            }
        });
        initialize();
        dialogChanged();
        setControl(container);
    }

    /**
     * Tests if the current workbench selection is a suitable container to use.
     */

    private void initialize() {
        if (selection != null && selection.isEmpty() == false
                && selection instanceof IStructuredSelection) {
            IStructuredSelection ssel = (IStructuredSelection) selection;
            if (ssel.size() > 1)
                return;
            Object obj = ssel.getFirstElement();
            if (obj instanceof IResource) {
                IContainer container;
                if (obj instanceof IContainer)
                    container = (IContainer) obj;
                else
                    container = ((IResource) obj).getParent();
                containerText.setText(container.getFullPath().toString());
            }
        }
        xsdFileText.setText(Messages.NewXsdWizardPage_NewSchemaFileName);
    }

    /**
     * Uses the standard container selection dialog to choose the new value for
     * the container field.
     */
    private void handleContainerBrowse() {
        ContainerSelectionDialog dialog =
                new ContainerSelectionDialog(getShell(), ResourcesPlugin
                        .getWorkspace().getRoot(), false,
                        Messages.NewXsdWizardPage_SelectFolderMessage);
        if (dialog.open() == ContainerSelectionDialog.OK) {
            Object[] result = dialog.getResult();
            if (result.length == 1) {
                containerText.setText(((Path) result[0]).toString());
            }
        }
    }

    /**
     * Uses the standard container selection dialog to choose the new value for
     * the container field.
     */
    private void handleFileBrowse() {
        FileDialog dialog = new FileDialog(getShell(), SWT.OPEN);
        dialog.setFilterExtensions(XML_EXTENSIONS);
        dialog.setText(Messages.NewXsdWizardPage_SelectXmlDocumentFileMessage);
        String result = dialog.open();
        if (result != null && result.length() > 0) {
            xmlFileText.setText(result);
        }
    }

    /**
     * Ensures that both text fields are set.
     */
    private void dialogChanged() {
        IResource container =
                ResourcesPlugin.getWorkspace().getRoot()
                        .findMember(new Path(getContainerName()));
        String xmlFileName = getXmlFileName();
        String xsdFileName = getXsdFileName();

        // validations for container (i.e. folder or project)
        if (getContainerName().length() == 0) {
            updateStatus(Messages.NewXsdWizardPage_FolderRequiredMessage);
            return;
        }
        if (container == null
                || (container.getType() & (IResource.PROJECT | IResource.FOLDER)) == 0) {
            updateStatus(Messages.NewXsdWizardPage_FolderMustExistMessage);
            return;
        }
        if (!container.isAccessible()) {
            updateStatus(Messages.NewXsdWizardPage_ProjectMustBeWritableMessage);
            return;
        }

        // validations for xsd
        if (xmlFileName.length() == 0) {
            updateStatus(Messages.NewXsdWizardPage_FileRequiredMessage);
            return;
        }
        // if (xmlFileName.replace('\\', '/').indexOf('/', 1) > 0) {
        // updateStatus("File name must be valid");
        // return;
        // }
        int dotLoc = xmlFileName.lastIndexOf('.');
        if (dotLoc != -1) {
            String ext = xmlFileName.substring(dotLoc + 1);
            if (ext.equalsIgnoreCase(EXT_XML) == false) {
                updateStatus(String
                        .format(Messages.NewXsdWizardPage_WrongFileExtensionMessage,
                                EXT_XML));
                return;
            }
        }

        // validations for xsd
        if (xsdFileName.length() == 0) {
            updateStatus(Messages.NewXsdWizardPage_FileRequiredMessage);
            return;
        }
        dotLoc = xsdFileName.lastIndexOf('.');
        if (dotLoc != -1) {
            String ext = xsdFileName.substring(dotLoc + 1);
            if (ext.equalsIgnoreCase(EXT_XSD) == false) {
                updateStatus(String
                        .format(Messages.NewXsdWizardPage_WrongFileExtensionMessage,
                                EXT_XSD));
                return;
            }
        }
        updateStatus(null);
    }

    private void updateStatus(String message) {
        setErrorMessage(message);
        setPageComplete(message == null);
    }

    public String getContainerName() {
        return containerText.getText();
    }

    public String getXmlFileName() {
        return xmlFileText.getText();
    }

    public String getXsdFileName() {
        return xsdFileText.getText();
    }
}