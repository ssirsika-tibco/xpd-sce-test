/*

 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.

 */

package com.tibco.xpd.xsd.ui.pages;

import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

import com.tibco.xpd.navigator.pickers.BaseObjectPicker;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.SpecialFoldersOnlyFilter;
import com.tibco.xpd.wsdl.ui.WsdlUIPlugin;
import com.tibco.xpd.xsd.ui.Activator;
import com.tibco.xpd.xsd.ui.internal.Messages;
import com.tibco.xpd.xsd.ui.wizards.DescriptorXMLOverJMSWizard;

/**
 * Wizard page for user to change the default attributes assigned to the wsdl
 * about to be created. Note: As a new output folder is chosen the wsdl file
 * name could change along with all default values.
 * 
 * @author glewis
 */
public class WsdlConfigurationPage extends AbstractXpdWizardPage {

    private Text operation;

    private Text portType;

    private Text serverTargetNameSpace;

    private Text typeNameSpace;

    private Text outputFolder;

    private IPath outputFolderIPath = null;

    private static String defaultFileExtension = ".wsdl"; //$NON-NLS-1$

    private String newFileName;

    private boolean portmodified = false;

    private boolean operationmodified = false;

    private boolean serverNameSpaceModified = false;

    private boolean typeNameSpaceModified = false;

    /** Listener for text modification events. */
    private final ModifyListener modify;

    /**
     * Constructor.
     */
    public WsdlConfigurationPage() {
        super(Messages.WsdlConfigurationPage_Title);
        setTitle(Messages.WsdlConfigurationPage_Title);
        setDescription(Messages.WsdlConfigurationPage_Description);
        modify = new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                updatePageComplete();
                // XPD-841 check if the user has modified any of the fields
                if (e.widget instanceof Text) {
                    Text txt = (Text) e.widget;
                    if (txt == portType)
                        portmodified = true;
                    if (txt == operation)
                        operationmodified = true;
                    if (txt == serverTargetNameSpace)
                        serverNameSpaceModified = true;
                    if (txt == typeNameSpace)
                        typeNameSpaceModified = true;
                }
            }
        };
    }

    /**
     * @param parent
     *            The parent to add the controls to.
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl(Composite parent) {
        Composite control = new Composite(parent, SWT.NONE);
        control.setLayout(new GridLayout());

        Group detailsGrp = new Group(control, SWT.NONE);
        detailsGrp.setText(Messages.WsdlConfigurationPage_detailsGroup_title);
        detailsGrp
                .setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        detailsGrp.setLayout(new GridLayout(2, false));

        // create operation label and text box
        Label operationLabel = new Label(detailsGrp, SWT.NONE);
        operationLabel.setText(Messages.WsdlConfigurationPage_OperationLabel);
        operation = new Text(detailsGrp, SWT.BORDER | SWT.FLAT);
        operation.setLayoutData(new GridData(GridData.FILL_HORIZONTAL
                | GridData.GRAB_HORIZONTAL));

        // create port type label and text box
        Label portTypeLabel = new Label(detailsGrp, SWT.NONE);
        portTypeLabel.setText(Messages.WsdlConfigurationPage_PortTypeLabel);
        portType = new Text(detailsGrp, SWT.BORDER | SWT.FLAT);
        portType.setLayoutData(new GridData(GridData.FILL_HORIZONTAL
                | GridData.GRAB_HORIZONTAL));
        // create server namespace label and text box
        Label nameSpaceLabel = new Label(detailsGrp, SWT.NONE);
        nameSpaceLabel.setText(Messages.WsdlConfigurationPage_NameSpaceLabel);
        serverTargetNameSpace = new Text(detailsGrp, SWT.BORDER | SWT.FLAT);
        serverTargetNameSpace.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));

        // create schema type namespace label and text box
        Label serviceNSLabel = new Label(detailsGrp, SWT.NONE);
        serviceNSLabel.setText(Messages.WsdlConfigurationPage_ServiceNSLabel);
        typeNameSpace = new Text(detailsGrp, SWT.BORDER | SWT.FLAT);
        typeNameSpace.setLayoutData(new GridData(GridData.FILL_HORIZONTAL
                | GridData.GRAB_HORIZONTAL));

        // create new composite to hold output folder label , text and browse
        // button
        Group outputGrp = new Group(control, SWT.NONE);
        outputGrp
                .setText(Messages.WsdlConfigurationPage_destinationGroup_title);
        outputGrp
                .setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        outputGrp.setLayout(new GridLayout(3, false));

        // create output folder label
        Label outputFolderLabel = new Label(outputGrp, SWT.NONE);
        outputFolderLabel
                .setText(Messages.WsdlConfigurationPage_OutputFolderLabel);

        // create output folder text control
        outputFolder = new Text(outputGrp, SWT.BORDER);
        outputFolder.setLayoutData(new GridData(GridData.FILL_HORIZONTAL
                | GridData.GRAB_HORIZONTAL));
        IPath selectedPath =
                ((DescriptorXMLOverJMSWizard) getWizard()).getSelectedPath();
        if (selectedPath != null) {
            outputFolder.setText(selectedPath.makeRelative().toString());
            outputFolderIPath = selectedPath;
        }

        // create output folder browse button
        Button browse = new Button(outputGrp, SWT.NONE);
        browse.setText(Messages.WsdlConfigurationPage_BrowseLabel);
        addBrowseListener(browse,
                outputFolder,
                Messages.WsdlConfigurationPage_OutputFolderPicker_title,
                Messages.WsdlConfigurationPage_OutputFolderPicker_shortdesc,
                Messages.WsdlConfigurationPage_OutputFolderPickerError_message);

        // add modify listeners
        operation.addModifyListener(modify);
        portType.addModifyListener(modify);
        serverTargetNameSpace.addModifyListener(modify);
        typeNameSpace.addModifyListener(modify);
        outputFolder.addModifyListener(modify);

        setControl(control);
        updatePageComplete();
    }

    /**
     * Adds listener to browse button - parameters are used to customise any
     * error messages and also to specify locations text box ( to avoid multiple
     * methods doing same thing)
     * 
     * @param browseButton
     * @param location
     * @param title
     * @param shortDesc
     * @param error
     */
    private void addBrowseListener(final Button browseButton,
            final Text location, final String title, final String shortDesc,
            final String error) {
        browseButton.addSelectionListener(new SelectionListener() {
            public void widgetDefaultSelected(SelectionEvent e) {
            }

            public void widgetSelected(SelectionEvent e) {
                ServicesFolderPicker picker =
                        new ServicesFolderPicker(getShell());
                picker.setTitle(title);
                picker.setMessage(shortDesc);
                picker.setInput(ResourcesPlugin.getWorkspace().getRoot());
                picker.setInitialSelection(outputFolderIPath);

                if (picker.open() == ServicesFolderPicker.OK) {
                    Object result = picker.getFirstResult();

                    if (result instanceof SpecialFolder) {
                        IFolder folder = ((SpecialFolder) result).getFolder();
                        // set location with folder path
                        outputFolderIPath = folder.getFullPath();
                        ((DescriptorXMLOverJMSWizard) getWizard())
                                .recalculateFileName();

                        location.setText(outputFolderIPath.makeRelative()
                                .toString());
                    }
                }
            }
        });
    }

    /**
     * Sets the page complete status based on user input.
     */
    private void updatePageComplete() {
        setErrorMessage(null);

        boolean complete = true;
        if (operation.getText().length() == 0) {
            complete = false;
        } else if (portType.getText().length() == 0) {
            complete = false;
        } else if (serverTargetNameSpace.getText().length() == 0) {
            complete = false;
        } else if (typeNameSpace.getText().length() == 0) {
            complete = false;
        } else if (outputFolder.getText().length() == 0) {
            complete = false;
        }

        if (serverTargetNameSpace.getText().length() > 0) {
            if (serverTargetNameSpace.getText().charAt(serverTargetNameSpace
                    .getText().length() - 1) != '/') {
                setErrorMessage(Messages.WsdlConfigurationPage_InvalidServerNamespaceMessage);
                complete = false;
            }
        }
        if (typeNameSpace.getText().length() > 0) {
            if (typeNameSpace.getText()
                    .charAt(typeNameSpace.getText().length() - 1) != '/') {
                setErrorMessage(Messages.WsdlConfigurationPage_InvalidTypeNamespaceMessage);
                complete = false;
            }
        }
        if (serverTargetNameSpace.getText().equals(typeNameSpace.getText())) {
            setErrorMessage(Messages.WsdlConfigurationPage_NamespaceIdenticalMessage);
            complete = false;
        }

        setPageComplete(complete);
    }

    /**
     * Refreshes all the page field names using the passed in newFileName
     * 
     * @param newFileName
     */
    public void doRefresh(String newFileName) {
        newFileName = newFileName.replace(defaultFileExtension, ""); //$NON-NLS-1$
        // XPD-841: if any of the values is modified by the user then ignore the
        // default values and retain user entered values
        if (!serverNameSpaceModified) {
            serverTargetNameSpace
                    .setText("http://www.tibco.com/ServerNS/" + newFileName + "/"); //$NON-NLS-1$ //$NON-NLS-2$
        } else {
            serverNameSpaceModified = false;
        }
        if (!portmodified) {
            portType.setText(newFileName + "Port"); //$NON-NLS-1$
        } else {
            portmodified = false;
        }
        if (!operationmodified) {
            operation.setText(newFileName + "Operation"); //$NON-NLS-1$
        } else {
            operationmodified = false;
        }
        if (!typeNameSpaceModified) {
            typeNameSpace
                    .setText("http://www.tibco.com/TypeNS/" + newFileName + "/"); //$NON-NLS-1$ //$NON-NLS-2$
        } else {
            typeNameSpaceModified = false;
        }
        this.newFileName = newFileName;

    }

    /**
     * @return
     */
    public Text getOperation() {
        return operation;
    }

    /**
     * @param operation
     */
    public void setOperation(Text operation) {
        this.operation = operation;
    }

    /**
     * @return
     */
    public Text getPortType() {
        return portType;
    }

    /**
     * @param portType
     */
    public void setPortType(Text portType) {
        this.portType = portType;
    }

    /**
     * @return
     */
    public Text getServerTargetNameSpace() {
        return serverTargetNameSpace;
    }

    /**
     * @return
     */
    public Text getTypeNameSpace() {
        return typeNameSpace;
    }

    /**
     * @return
     */
    public Text getOutputFolder() {
        return outputFolder;
    }

    /**
     * @return
     */
    public IPath getOutputFolderIPath() {
        return outputFolderIPath;
    }

    /**
     * @return
     */
    public String getNewFileName() {
        return newFileName;
    }

    /**
     * A picker that allows for selection of a services special folder.
     * 
     * @author njpatel
     * 
     */
    private class ServicesFolderPicker extends BaseObjectPicker {

        public ServicesFolderPicker(Shell parent) {
            super(parent);

            // Only show Service special folders
            addFilter(new SpecialFoldersOnlyFilter(Collections
                    .singleton(WsdlUIPlugin.WSDL_SPECIALFOLDER_KIND)) {
                @Override
                public boolean select(Viewer viewer, Object parentElement,
                        Object element) {
                    if (element instanceof IFile) {
                        return false;
                    }

                    return super.select(viewer, parentElement, element);
                }
            });

            setValidator(new ISelectionStatusValidator() {

                public IStatus validate(Object[] selection) {
                    if (selection != null && selection.length == 1) {
                        if (selection[0] instanceof SpecialFolder) {
                            return new Status(IStatus.OK, Activator.PLUGIN_ID,
                                    "", null); //$NON-NLS-1$
                        }
                    }
                    return new Status(
                            IStatus.ERROR,
                            Activator.PLUGIN_ID,
                            Messages.WsdlConfigurationPage_selectServicesFolder_message,
                            null);
                }

            });
        }

        @Override
        public void setInitialSelection(Object selection) {
            if (selection instanceof IPath) {
                IResource resource =
                        ResourcesPlugin.getWorkspace().getRoot()
                                .findMember(((IPath) selection));

                if (resource instanceof IFolder) {
                    ProjectConfig config =
                            XpdResourcesPlugin.getDefault()
                                    .getProjectConfig(resource.getProject());

                    if (config != null) {
                        SpecialFolder folder =
                                config.getSpecialFolders()
                                        .getFolder((IFolder) resource);

                        selection = folder != null ? folder : resource;
                    }
                } else {
                    selection = resource;
                }
            }
            super.setInitialSelection(selection);
        }

    }
}
