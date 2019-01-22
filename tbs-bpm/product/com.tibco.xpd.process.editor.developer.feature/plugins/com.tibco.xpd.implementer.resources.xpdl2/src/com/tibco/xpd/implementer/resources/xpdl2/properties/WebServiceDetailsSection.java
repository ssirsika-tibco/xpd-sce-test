/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.resources.xpdl2.properties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.analyst.resources.xpdl2.pickers.DataFilterPicker;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.DataFilterPicker.DataPickerType;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDestinationUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.implementer.resources.xpdl2.Messages;
import com.tibco.xpd.implementer.script.ActivityMessageProvider;
import com.tibco.xpd.implementer.script.ActivityMessageProviderFactory;
import com.tibco.xpd.implementer.script.Xpdl2WsdlUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.registry.ui.wizard.WebServiceOperationPickerPage;
import com.tibco.xpd.registry.ui.wizard.WsdlImportWizard;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.util.WsdlIndexerUtil;
import com.tibco.xpd.wsdl.WsdlServiceKey;
import com.tibco.xpd.wsdl.ui.pickers.OperationPicker;
import com.tibco.xpd.wsdl.ui.pickers.OperationPicker.WsdlType;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.EndPoint;
import com.tibco.xpd.xpdl2.EndPointTypeType;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Service;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.commands.AbstractLateExecuteCommand;
import com.tibco.xpd.xpdl2.edit.util.ControlUtils;
import com.tibco.xpd.xpdl2.util.DecisionFlowUtil;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Property section for selection of a Web Service or BW Service and it's
 * details.
 * 
 * @author aallway (copied from nwilson's TaskWebServiceSection.class)
 */
public abstract class WebServiceDetailsSection extends
        AbstractFilteredTransactionalSection {

    /** The web service name. */
    protected Text serviceNameText;

    /** The port Type Name */
    protected Text portTypeNameText;

    /** The port Name */
    protected Text portNameText;

    /** The operation Name */
    protected Text operationNameText;

    /** The transport Name */
    protected CCombo transportNameCombo;

    /** Button to select an available web service. */
    protected Button selectButton;

    /** Button to clear the webservice selection. */
    protected Button clearButton;

    /** Button to import a wsdl */
    protected Button importWsdlButton;

    /** Radio button for local WSDL file. */
    protected Button localWsdl;

    /** Read-only location of local or remote WSDL file. */
    protected Text wsdlLocationText;

    /** Read-only location of local WSDL file. */
    // private Text localWsdlLocation;
    /** Radio button for remote WSDL file. */
    protected Button remoteWsdl;

    /** Read-only location of remote WSDL file. */
    // private Text remoteWsdlLocation;
    /** The adapter for access to message information. */
    protected ActivityMessageProvider activityMessage;

    /** Read only text WSDL alias. */
    protected Text aliasText;

    /** Security profile for web services. */
    protected Text securityProfileText;

    protected Label securityProfileLabel;

    /** Participant picker for alias value */
    protected Button aliasBrowse;

    /** clearButton alias value */
    protected Button clearAlias;

    private static final String BROWSE_FOR_ALIAS = "BrowseForAlias"; //$NON-NLS-1$

    private static final String CLEAR_ALIAS = "ClearAlias"; //$NON-NLS-1$

    private WebServiceOperationPickerPage pickerPage = null;

    protected static final String SERVICE_VIRTUALIZATION_LABEL =
            Messages.TaskWebSeriveSection_ServiceVirtualisation_label;

    protected static final String SOAP_OVER_HTTP_LABEL =
            Messages.TaskWebServiceSection_SoapOverHttp_label;

    protected static final String SOAP_OVER_JMS_LABEL =
            Messages.TaskWebServiceSection_SoapOverJms_label;

    private Composite root;

    private Label generatedServiceLabel;

    /**
     * Constructor.
     */
    public WebServiceDetailsSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());
    }

    /**
     * @return true if the implementation typ is BW (switches off security
     *         profile and sets brose for service type to BW).
     */
    protected abstract boolean isBWImplementation();

    /**
     * @param parent
     *            The parent composite.
     * @param tk
     *            The toolkit to use for creating controls.
     * @return The created control.
     * @see com.tibco.xpd.ui.properties.BaseXpdSection#doCreateControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit tk) {
        if (root != null && !root.isDisposed()) {
            return root;
        }
        root = tk.createComposite(parent, SWT.NONE);
        GridLayout gLayout = new GridLayout(2, false);
        gLayout.marginHeight = 10;
        gLayout.marginWidth = 2;
        gLayout.marginBottom = 20;
        root.setLayout(gLayout);

        createOperationSelectionAndClearBtns(root, tk);

        createOtherWsdlTextInfo(root, tk);

        createEndpointResolution(root, tk);

        manageControl(serviceNameText);
        manageControl(portTypeNameText);
        manageControl(portNameText);
        manageControl(operationNameText);
        manageControl(selectButton);
        manageControl(clearButton);
        manageControl(importWsdlButton);
        manageControl(localWsdl);
        manageControl(wsdlLocationText);
        manageControl(transportNameCombo);
        manageControl(remoteWsdl);
        manageControl(aliasText);
        manageControl(aliasBrowse);
        manageControl(clearAlias);
        manageControl(securityProfileText);

        return root;
    }

    /**
     * Creates the operationNameText selection and clearButton buttons
     * 
     * @param parent
     * @param tk
     * @return
     */
    protected void createOperationSelectionAndClearBtns(Composite parent,
            XpdFormToolkit tk) {
        // create label
        Label lbl =
                tk.createLabel(parent,
                        Messages.TaskWebServiceSection_OperationLabel);
        lbl.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
        ControlUtils.forceWidgetVisible(lbl, lbl.getText());

        // create composite for selectButton and clearButton buttons
        Composite buttonContainer = tk.createComposite(parent);
        GridLayout layout = new GridLayout(3, false);
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        buttonContainer.setLayout(layout);
        buttonContainer.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        // create selectButton button
        selectButton =
                tk.createButton(buttonContainer,
                        Messages.TaskWebServiceSection_SelectButton,
                        SWT.PUSH);
        selectButton.setLayoutData(new GridData(SWT.CENTER, SWT.NONE, false,
                false));

        // create clearButton button
        clearButton =
                tk.createButton(buttonContainer,
                        Messages.WebServiceDetailsSection_clear_button,
                        SWT.PUSH);
        clearButton.setLayoutData(new GridData(SWT.CENTER, SWT.NONE, false,
                false));

        // create importWsdlButton button
        importWsdlButton =
                tk.createButton(buttonContainer,
                        Messages.TaskWebServiceSection_ImportWsdlButton,
                        SWT.PUSH);
        importWsdlButton.setLayoutData(new GridData(SWT.CENTER, SWT.NONE,
                false, false));

    }

    /**
     * Creates the text fields containing the wsdl information
     * 
     * @param parent
     * @param tk
     * @return
     */
    protected void createOtherWsdlTextInfo(Composite parent, XpdFormToolkit tk) {
        // create portNameText type label and text field
        Label lbl =
                tk.createLabel(parent,
                        Messages.TaskWebServiceSection_PortTypeNameLabel);
        GridData gData = new GridData(SWT.NONE, SWT.NONE, false, false);
        gData.widthHint = STANDARD_LABEL_WIDTH;
        gData.horizontalIndent = 10;
        lbl.setLayoutData(gData);
        ControlUtils.forceWidgetVisible(lbl, lbl.getText());

        portTypeNameText =
                tk.createText(parent, null, XpdExtensionPackage.eINSTANCE
                        .getPortTypeOperation_PortTypeName());
        gData = new GridData(SWT.FILL, SWT.NONE, true, false);
        portTypeNameText.setLayoutData(gData);
        portTypeNameText.setEditable(false);

        // create operationNameText label and text field
        lbl =
                tk.createLabel(parent,
                        Messages.TaskWebServiceSection_OperationNameLabel);
        gData = new GridData(SWT.NONE, SWT.NONE, false, false);
        gData.widthHint = STANDARD_LABEL_WIDTH;
        gData.horizontalIndent = 10;
        lbl.setLayoutData(gData);
        ControlUtils.forceWidgetVisible(lbl, lbl.getText());

        Composite operationNameContainer = tk.createComposite(parent);
        operationNameContainer.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL));
        GridLayout gl = new GridLayout(2, false);
        gl.marginWidth = 0;
        gl.marginLeft = gl.marginRight = 0;
        gl.marginHeight = 0;
        operationNameContainer.setLayout(gl);

        operationNameText =
                tk.createText(operationNameContainer,
                        null,
                        Xpdl2Package.eINSTANCE
                                .getWebServiceOperation_OperationName());
        gData = new GridData(GridData.FILL_HORIZONTAL);
        operationNameText.setLayoutData(gData);
        operationNameText.setEditable(false);

        generatedServiceLabel =
                tk.createLabel(operationNameContainer, "", SWT.LEFT); //$NON-NLS-1$
        gData = new GridData();
        gData.widthHint = 0; // 0 width by default until there's some text to
        // display.
        generatedServiceLabel.setLayoutData(gData);

        // create portNameText label and text field
        lbl =
                tk.createLabel(parent,
                        Messages.TaskWebServiceSection_PortNameLabel);
        gData = new GridData(SWT.NONE, SWT.NONE, false, false);
        gData.widthHint = STANDARD_LABEL_WIDTH;
        gData.horizontalIndent = 10;
        lbl.setLayoutData(gData);
        ControlUtils.forceWidgetVisible(lbl, lbl.getText());

        portNameText =
                tk.createText(parent,
                        null,
                        Xpdl2Package.eINSTANCE.getService_PortName());
        gData = new GridData(SWT.FILL, SWT.NONE, true, false);
        portNameText.setLayoutData(gData);
        portNameText.setEditable(false);

        // create service label and text field
        lbl =
                tk.createLabel(parent,
                        Messages.TaskWebServiceSection_ServiceNameLabel);
        gData = new GridData(SWT.NONE, SWT.NONE, false, false);
        gData.widthHint = STANDARD_LABEL_WIDTH;
        gData.horizontalIndent = 10;
        lbl.setLayoutData(gData);
        ControlUtils.forceWidgetVisible(lbl, lbl.getText());

        serviceNameText =
                tk.createText(parent,
                        null,
                        Xpdl2Package.eINSTANCE.getService_ServiceName());
        gData = new GridData(SWT.FILL, SWT.NONE, true, false);
        serviceNameText.setLayoutData(gData);
        serviceNameText.setEditable(false);

        // create transportNameText label and text field
        lbl =
                tk.createLabel(parent,
                        Messages.TaskWebServiceSection_TransportLabel);
        gData = new GridData(SWT.NONE, SWT.NONE, false, false);
        gData.widthHint = STANDARD_LABEL_WIDTH;
        gData.horizontalIndent = 10;
        lbl.setLayoutData(gData);
        ControlUtils.forceWidgetVisible(lbl, lbl.getText());

        transportNameCombo = tk.createCCombo(parent, SWT.NONE);
        gData = new GridData(SWT.FILL, SWT.NONE, true, false);
        transportNameCombo.setLayoutData(gData);
        transportNameCombo.setEditable(false);

        // XPD-734 moved setup of combo to refresh as is now dependent on
        // api/invoke activity.
    }

    /**
     * Creates the other endpoint resolution information
     * 
     * @param parent
     * @param tk
     * @return
     */
    protected void createEndpointResolution(Composite parent, XpdFormToolkit tk) {
        // create end point resolution header label and span 2 columns
        Label lbl =
                tk.createLabel(parent,
                        Messages.TaskWebServiceSection_EndpointResolutionLabel);
        GridData gData = new GridData(SWT.NONE, SWT.NONE, false, false, 2, 1);
        gData.verticalIndent = 10;
        lbl.setLayoutData(gData);
        ControlUtils.forceWidgetVisible(lbl, lbl.getText());

        // create wsdl label
        lbl = tk.createLabel(parent, Messages.TaskWebServiceSection_WSDLLabel);
        gData = new GridData(SWT.NONE, SWT.NONE, false, false);
        gData.horizontalIndent = 10;
        lbl.setLayoutData(gData);
        ControlUtils.forceWidgetVisible(lbl, lbl.getText());

        // create composite ready for containing the 2 radio buttons
        Composite localRemoteContainer = tk.createComposite(parent);
        GridLayout layout = new GridLayout(2, true);
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        localRemoteContainer.setLayout(layout);
        localRemoteContainer.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL));

        // create local wsdl radio button inside the above composite
        localWsdl =
                tk.createButton(localRemoteContainer,
                        Messages.TaskWebServiceSection_LocalWsdlLabel,
                        SWT.RADIO);
        gData = new GridData(SWT.FILL, SWT.NONE, false, false);
        localWsdl.setLayoutData(gData);

        // create remote wsdl radio button inside the above composite
        remoteWsdl =
                tk.createButton(localRemoteContainer,
                        Messages.TaskWebServiceSection_RemoteWsdlLabel,
                        SWT.RADIO);
        gData = new GridData(SWT.FILL, SWT.NONE, false, false);
        remoteWsdl.setLayoutData(gData);

        // create wsdl label and text box to contain the wsdl location
        lbl =
                tk.createLabel(parent,
                        Messages.TaskWebServiceSection_WSDLLocationLabel);
        gData = new GridData(SWT.NONE, SWT.NONE, false, false);
        gData.horizontalIndent = 10;
        lbl.setLayoutData(gData);
        ControlUtils.forceWidgetVisible(lbl, lbl.getText());

        wsdlLocationText = tk.createText(parent, ""); //$NON-NLS-1$
        gData = new GridData(SWT.FILL, SWT.NONE, true, false);
        wsdlLocationText.setLayoutData(gData);

        // create the endpoint label
        lbl =
                tk.createLabel(parent,
                        Messages.EventTriggerWebServiceSection_AliasLabel,
                        SWT.NONE);
        gData = new GridData(SWT.NONE, SWT.NONE, false, false);
        gData.horizontalIndent = 10;
        lbl.setLayoutData(gData);

        // create the composite to contain the endpoint text box and the
        // selectButton and clearButton button associated with it
        Composite aliasContainer = tk.createComposite(parent, SWT.NONE);
        layout = new GridLayout(3, false);
        layout.marginHeight = 0;
        layout.marginWidth = 1;
        aliasContainer.setLayout(layout);
        gData = new GridData(SWT.FILL, SWT.FILL, true, false);
        aliasContainer.setLayoutData(gData);
        aliasText = tk.createText(aliasContainer, ""); //$NON-NLS-1$        
        aliasText
                .setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        aliasBrowse = tk.createButton(aliasContainer, "...", SWT.PUSH); //$NON-NLS-1$
        aliasBrowse.setData(BROWSE_FOR_ALIAS);
        aliasBrowse.setEnabled(false);
        clearAlias =
                tk.createButton(aliasContainer,
                        Messages.WebServiceDetailsSection_clear_button,
                        SWT.PUSH);
        clearAlias.setData(CLEAR_ALIAS);
        clearAlias.setEnabled(false);

        // create the security profile label and text box
        securityProfileLabel =
                tk.createLabel(parent,
                        Messages.EventTriggerWebServiceSection_SecurityProfileLabel,
                        SWT.NONE);
        gData = new GridData(SWT.NONE, SWT.NONE, false, false);
        gData.horizontalIndent = 10;
        securityProfileLabel.setLayoutData(gData);
        securityProfileText = tk.createText(parent, "", SWT.NONE); //$NON-NLS-1$
        securityProfileText.setLayoutData(new GridData(SWT.FILL, SWT.NONE,
                true, false));
    }

    /**
     * @param obj
     *            The object to get the command for.
     * @return The command.
     * @see com.tibco.xpd.ui.properties.BaseXpdSection#doGetCommand(java.lang.Object)
     */
    @Override
    public Command doGetCommand(Object obj) {

        Command command = null;
        EditingDomain ed = WorkingCopyUtil.getEditingDomain(getInput());

        if (obj == selectButton) {

            if (getInput() != null) {
                Shell shell = getSite().getShell();
                WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(getInput());

                if (wc != null) {
                    IProject project =
                            wc.getEclipseResources().get(0).getProject();

                    OperationPicker picker =
                            new OperationPicker(shell,
                                    isBWImplementation() ? WsdlType.BW
                                            : WsdlType.STANDARD);
                    WsdlServiceKey currentKey =
                            ProcessUIUtil
                                    .getSpecificWsdlServiceKey(getActivityInput());
                    IndexerItem currentOpItem =
                            WsdlIndexerUtil.getOperationItem(WorkingCopyUtil
                                    .getProjectFor(getActivityInput()),
                                    currentKey,
                                    true,
                                    true);
                    picker.setInitialElementSelections(Collections
                            .singletonList(currentOpItem));

                    if (picker.open() == OperationPicker.OK) {

                        String serviceName = picker.getServiceName();
                        String portTypeName = picker.getPortTypeName();
                        String portOperationName =
                                portTypeName != null ? picker
                                        .getOperationName() : null;
                        String portName = picker.getPortName();
                        String operationName =
                                serviceName != null ? picker.getOperationName()
                                        : null;
                        String wsdlUrl = picker.getLocalFilePath();

                        boolean isRemote = false;

                        WebServiceOperation webServiceOperation =
                                activityMessage
                                        .getWebServiceOperation(getActivityInput());

                        if (null != webServiceOperation) {
                            String aliasId =
                                    (String) Xpdl2ModelUtil
                                            .getOtherAttribute(webServiceOperation,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getDocumentRoot_Alias());
                            /*
                             * Ensure that when end point participant is set,
                             * the IsRemote flag is also set.
                             */
                            if (null != aliasId) {
                                isRemote = true;
                            }
                        }
                        WsdlServiceKey key =
                                new WsdlServiceKey(serviceName, portName,
                                        operationName, portTypeName,
                                        portOperationName,
                                        picker.getLocalFilePath());

                        String projectName = picker.getProjectName();
                        if (projectName != null) {

                            IProject opProject =
                                    project.getWorkspace().getRoot()
                                            .getProject(projectName);

                            updateWsdlServiceKeyWithTransportName(opProject,
                                    key);

                            command =
                                    new AssignWebServiceCommand(ed,
                                            getActivityInput(), project,
                                            opProject, wsdlUrl, isRemote, key);
                        }
                    }
                }
            }

        } else if (obj == clearButton) {
            WebServiceOperation wso =
                    activityMessage.getWebServiceOperation(getActivityInput());
            if (wso != null) {
                EObject[] emptyArray = new EObject[] {};
                CompoundCommand cmd =
                        new CompoundCommand(Messages.clearWebServiceCommand);
                Command performersCommand =
                        TaskObjectUtil.getSetPerformersCommand(ed,
                                getActivityInput(),
                                emptyArray);
                // Only add it if it's not empty
                if (performersCommand != null && performersCommand.canExecute()) {
                    cmd.append(performersCommand);
                }
                cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                        wso,
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_Alias(),
                        "")); //$NON-NLS-1$
                cmd.append(activityMessage.getClearWebServiceCommand(ed,
                        getActivityInput()));
                command = cmd;
            }
        } else if (obj == importWsdlButton) {
            pickerPage = null;
            Display display = getSite().getShell().getDisplay();
            display.syncExec(new Runnable() {
                @Override
                public void run() {
                    WorkingCopy wc =
                            WorkingCopyUtil.getWorkingCopyFor(getInput());
                    IProject project =
                            wc.getEclipseResources().get(0).getProject();
                    ArrayList<String> disabledSubWizards =
                            new ArrayList<String>();
                    if (!isBWImplementation()) {
                        disabledSubWizards
                                .add("com.tibco.xpd.bw.eai.livelink53"); //$NON-NLS-1$
                    }
                    // XPD-4342: we do not require to distinguish between
                    // web-service and BW service
                    // Setting wsdlPickerMode to 2 so that any web-service
                    // operation can be selected.
                    int wsdlPickerMode = 2;
                    if (isBWImplementation()) {
                        wsdlPickerMode = 1;
                    }
                    WsdlImportWizard wizard =
                            new WsdlImportWizard(true, wsdlPickerMode, project,
                                    disabledSubWizards);
                    wizard.init(PlatformUI.getWorkbench(),
                            new StructuredSelection(project));
                    WizardDialog dialog =
                            new WizardDialog(getSite().getShell(), wizard);
                    dialog.open();
                    if (dialog.getReturnCode() == Dialog.OK) {
                        pickerPage = wizard.getWebServiceOperationPickerPage();
                    }
                }
            });
            if (pickerPage != null) {
                String serviceName = pickerPage.getServiceName();
                String portTypeName = pickerPage.getPortTypeName();
                String portOperationName = pickerPage.getPortOperationName();
                String portName = pickerPage.getPortName();
                String operationName = pickerPage.getOperationName();

                boolean isRemote = false;

                WsdlServiceKey key =
                        new WsdlServiceKey(serviceName, portName,
                                operationName, portTypeName, portOperationName,
                                pickerPage.getFileLocation());

                updateWsdlServiceKeyWithTransportName(getProject(), key);
                IPath relativePath =
                        WsdlIndexerUtil.getRelativePath(getProject(),
                                key,
                                true,
                                true);
                String wsdlUrl = null;
                if (relativePath != null) {
                    wsdlUrl = relativePath.toPortableString();
                }
                if (portName == null || TaskObjectUtil.isRemoteURL(wsdlUrl)) {
                    isRemote = true;
                }

                /*
                 * Sid XPD-7467: Check and add project reference properly (using
                 * method that does cyclic dependency checks etc)
                 */
                String projectName = pickerPage.getProjectName();
                IProject project = getProject();
                if (project != null && project.exists() && projectName != null) {
                    IProject opProject =
                            project.getWorkspace().getRoot()
                                    .getProject(projectName);

                    if (opProject != null
                            && opProject.exists()
                            && ProcessUIUtil.checkAndAddProjectReference(null,
                                    project,
                                    opProject)) {
                        command =
                                activityMessage.getAssignWebServiceCommand(ed,
                                        getActivityInput().getProcess(),
                                        getActivityInput(),
                                        wsdlUrl,
                                        isRemote,
                                        key);

                    }
                }
            }

        } else if (obj == localWsdl) {

            command = getSetIsRemoteCommand(ed, false);

        } else if (obj == remoteWsdl) {
            command =
                    activityMessage.getAssignWebServiceIsRemoteCommand(ed,
                            getActivityInput(),
                            true);

        } else if (obj == wsdlLocationText) {
            command =
                    activityMessage.getAssignWebServiceEndpointCommand(ed,
                            getActivityInput(),
                            wsdlLocationText.getText());
        } else if (obj == securityProfileText) {
            WebServiceOperation wso =
                    activityMessage.getWebServiceOperation(getActivityInput());
            command =
                    Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                            wso,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_SecurityProfile(),
                            securityProfileText.getText().trim());
        } else if (obj == transportNameCombo) {
            WebServiceOperation wso =
                    activityMessage.getWebServiceOperation(getActivityInput());
            String transportName =
                    (String) transportNameCombo.getData(transportNameCombo
                            .getText());

            String currentTransport =
                    (String) Xpdl2ModelUtil.getOtherAttribute(wso,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_Transport());

            if (!transportName.equals(currentTransport)) {
                command =
                        Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                                wso,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_Transport(),
                                transportName);
            }
        } else if (obj instanceof Button) {
            Object data = ((Button) obj).getData();
            WebServiceOperation wso =
                    activityMessage.getWebServiceOperation(getActivityInput());

            if (BROWSE_FOR_ALIAS.equals(data)) {

                // Alias browsing (uses participants)
                DataFilterPicker picker =
                        new DataFilterPicker(getSite().getShell(),
                                DataPickerType.PARTICIPANTS, false);
                picker.setScope(getActivityInput());

                int ret = picker.open();
                if (ret == DataFilterPicker.OK) {
                    Object newPerfs = picker.getFirstResult();
                    if (newPerfs != null) {
                        List<EObject> perfs = new ArrayList<EObject>();
                        if (newPerfs instanceof EObject) {
                            perfs.add((EObject) newPerfs);
                        }
                        CompoundCommand cCmd = new CompoundCommand();
                        Command cmd =
                                TaskObjectUtil.getSetPerformersCommand(ed,
                                        getActivityInput(),
                                        perfs.toArray(new EObject[0]));
                        if (cmd != null) {
                            cCmd.append(cmd);
                        }
                        cmd =
                                Xpdl2ModelUtil
                                        .getSetEndpointFromDataPickerSelectionCommand(ed,
                                                newPerfs,
                                                wso);
                        cCmd.append(cmd);
                        if (!isBWImplementation()) {
                            cmd = getSetIsRemoteCommand(ed, true);
                            cCmd.append(cmd);
                        }
                        command = cCmd;
                    }
                }
            } else if (CLEAR_ALIAS.equals(data)) {
                EObject[] emptyArray = new EObject[] {};
                CompoundCommand cCmd = new CompoundCommand();
                Command cmd =
                        TaskObjectUtil.getSetPerformersCommand(ed,
                                getActivityInput(),
                                emptyArray);
                if (cmd != null) {
                    cCmd.append(cmd);
                }
                cmd =
                        Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                                wso,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_Alias(),
                                ""); //$NON-NLS-1$
                cCmd.append(cmd);
                cmd = getSetIsRemoteCommand(ed, false);
                cCmd.append(cmd);
                // localWsdl.setSelection(true);
                command = cCmd;
            }
        }

        return command;
    }

    /**
     * @param ed
     * @param isRemote
     */
    private Command getSetIsRemoteCommand(EditingDomain ed, boolean isRemote) {
        Command tempCommand =
                activityMessage.getAssignWebServiceIsRemoteCommand(ed,
                        getActivityInput(),
                        isRemote);
        return tempCommand;
    }

    /**
     * @see com.tibco.xpd.ui.properties.BaseXpdSection#doRefresh()
     */
    @Override
    protected void doRefresh() {

        //
        // SID : NEVER use updateText() to set controls back to "" when you're
        // going to set them again later in refresh - for editable fields this
        // causes the cursor to be returned to first character when the timed
        // model update ocurs (we get a refresh after timed model change,
        // updateText("") sets 'last cursor location to 0)
        //

        String serviceName = ""; //$NON-NLS-1$
        String portTypeName = ""; //$NON-NLS-1$
        String portName = ""; //$NON-NLS-1$
        String operationName = ""; //$NON-NLS-1$
        String alias = ""; //$NON-NLS-1$
        String wsdlLocation = ""; //$NON-NLS-1$
        String transportName = ""; //$NON-NLS-1$
        String securityProfile = ""; //$NON-NLS-1$

        boolean securityProfileVisible = true;

        localWsdl.setSelection(false);
        remoteWsdl.setSelection(false);
        localWsdl.setEnabled(true);
        remoteWsdl.setEnabled(true);
        wsdlLocationText.setEditable(true);
        aliasText.setEditable(false);
        aliasBrowse.setEnabled(false);
        securityProfileText.setEnabled(true);
        transportNameCombo.setEnabled(false);

        Activity activity = getActivityInput();

        /*
         * earlier there used to be a condition not to add service
         * virtualisation transport for API activities. in fact we show service
         * virtualisation for api activities when they implement abstract
         * operations. although we do not enable transport name combo in most of
         * the cases, the condition is no longer valid.
         */
        transportNameCombo.setItems(new String[] {
                SERVICE_VIRTUALIZATION_LABEL, SOAP_OVER_HTTP_LABEL,
                SOAP_OVER_JMS_LABEL });
        transportNameCombo.setData(SERVICE_VIRTUALIZATION_LABEL,
                Xpdl2WsdlUtil.SERVICE_VIRTUALIZATION_URL);
        transportNameCombo.setData(SOAP_OVER_HTTP_LABEL,
                Xpdl2WsdlUtil.SOAP_OVER_HTTP_URL);
        transportNameCombo.setData(SOAP_OVER_JMS_LABEL,
                Xpdl2WsdlUtil.XML_OVER_JMS_URL);

        /*
         * XPD-8122: Saket: Need to set activityMessage if it's null.
         */
        if (null == activityMessage && null != activity) {
            activityMessage =
                    ActivityMessageProviderFactory.INSTANCE
                            .getMessageProvider(activity);
        }

        if (activityMessage != null) {
            securityProfileVisible = !isBWImplementation();

            WebServiceOperation wso =
                    activityMessage.getWebServiceOperation(activity);
            IProject project = getProject();
            if (wso == null && project != null) {
                securityProfileText.setEnabled(false);
                wsdlLocationText.setEditable(false);
                localWsdl.setEnabled(false);
                remoteWsdl.setEnabled(false);
            } else if (wso != null && project != null) {
                // WsdlCache cache =
                // Activator.getDefault().getWsdlCache(project);
                Service ws = wso.getService();
                boolean isRemote =
                        Xpdl2ModelUtil.getOtherAttributeAsBoolean(ws,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_IsRemote());

                String portOperationName = null;

                if (ws != null) {
                    serviceName = ifEmptySetToNull(ws.getServiceName());
                    portName = ifEmptySetToNull(ws.getPortName());
                }
                operationName = ifEmptySetToNull(wso.getOperationName());

                PortTypeOperation portTypeOperation =
                        activityMessage.getPortTypeOperation(activity);
                if (portTypeOperation != null) {
                    portTypeName =
                            ifEmptySetToNull(portTypeOperation
                                    .getPortTypeName());
                    portOperationName =
                            ifEmptySetToNull(portTypeOperation
                                    .getOperationName());
                }

                if (serviceName == null) {
                    operationName = portOperationName;
                }

                securityProfile =
                        (String) Xpdl2ModelUtil.getOtherAttribute(wso,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_SecurityProfile());

                transportName = getTransportSelectionFromModel(wso);
                if (SOAP_OVER_JMS_LABEL.equals(transportName)) {
                    securityProfileText.setEnabled(false);
                }

                if ((serviceName != null && portName != null && operationName != null)
                        || (portTypeName != null && portOperationName != null)) {

                    String location = ""; //$NON-NLS-1$
                    String localFileName = ""; //$NON-NLS-1$

                    WsdlServiceKey key =
                            new WsdlServiceKey(
                                    serviceName,
                                    portName,
                                    operationName,
                                    portTypeName,
                                    portOperationName,
                                    ws != null ? Xpdl2WsdlUtil.getLocation(ws)
                                            : Xpdl2WsdlUtil
                                                    .getLocation(portTypeOperation));
                    IPath rPath =
                            WsdlIndexerUtil.getRelativePath(project,
                                    key,
                                    true,
                                    true);
                    if (rPath != null) {
                        localFileName = rPath.toString();
                        EndPoint endPoint = ws.getEndPoint();
                        if (endPoint != null) {
                            if (EndPointTypeType.WSDL_LITERAL.equals(endPoint
                                    .getEndPointType())) {
                                ExternalReference reference =
                                        endPoint.getExternalReference();
                                if (reference != null) {
                                    location = reference.getLocation();
                                }
                            }
                        }
                    }

                    String endPointParticipantId =
                            (String) Xpdl2ModelUtil.getOtherAttribute(wso,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_Alias());
                    boolean hasAlias = false;
                    if (endPointParticipantId != null
                            && endPointParticipantId.trim().length() > 0) {
                        hasAlias = true;
                    }

                    // if interface selected
                    if (serviceName == null) {
                        localWsdl.setEnabled(false);
                        isRemote = true;
                        if (location.trim().length() == 0
                                || (!hasAlias && location
                                        .equals(Messages.TaskWebServiceSection_URLTakenFromRuntimeAliasLabel))
                                || (!hasAlias && location
                                        .equals(Messages.TaskWebServiceSection_AliasIsActualTargetNameLabel))) {
                            location = localFileName;
                        }
                        // XPD-614
                        // transportNameCombo.setEnabled(true);

                    } else {
                        transportName =
                                getTransportSelectionFromWsdl(project, key);
                    }

                    if (Xpdl2ModelUtil.isGeneratedRequestActivity(activity)) {
                        aliasBrowse.setEnabled(false);
                        /* XPD-1785: */
                        clearAlias.setEnabled(false);
                    } else {
                        aliasBrowse.setEnabled(true);
                        /* XPD-1785: */
                        clearAlias.setEnabled(true);
                    }

                    if (isRemote) {
                        localWsdl.setSelection(false);
                        remoteWsdl.setSelection(true);

                        wsdlLocation = location;
                        if (hasAlias) {
                            securityProfileText.setEnabled(false);
                        } else {
                            securityProfileText.setEnabled(true);
                        }
                    } else {
                        localWsdl.setSelection(true);
                        remoteWsdl.setSelection(false);
                        wsdlLocation = localFileName;
                        wsdlLocationText.setEditable(false);

                    }

                    if (hasAlias) {
                        alias =
                                TaskObjectUtil
                                        .getParticipantNameForPassedId(endPointParticipantId,
                                                activity);
                        wsdlLocation =
                                Messages.TaskWebServiceSection_URLTakenFromRuntimeAliasLabel;

                        if (SOAP_OVER_JMS_LABEL.equals(transportName)) {
                            wsdlLocation =
                                    Messages.TaskWebServiceSection_AliasIsActualTargetNameLabel;
                        }

                        wsdlLocationText.setEditable(false);
                        localWsdl.setEnabled(false);
                    } else {
                        clearAlias.setEnabled(false);

                        if (isRemote
                                && location
                                        .equals(Messages.TaskWebServiceSection_URLTakenFromRuntimeAliasLabel)) {
                            wsdlLocation = ""; //$NON-NLS-1$

                        } else if (isRemote
                                && location
                                        .equals(Messages.TaskWebServiceSection_AliasIsActualTargetNameLabel)) {
                            wsdlLocation = ""; //$NON-NLS-1$
                        }

                    }
                }
            }
        }

        //
        // Having decided the values, set them in controls.
        //
        updateText(serviceNameText, serviceName);
        updateText(portTypeNameText, portTypeName);
        updateText(portNameText, portName);
        updateText(operationNameText, operationName);
        updateText(aliasText, alias);
        updateText(wsdlLocationText, wsdlLocation);
        transportNameCombo.setText(transportName);
        updateText(securityProfileText, securityProfile);

        securityProfileText.setVisible(securityProfileVisible);
        securityProfileLabel.setVisible(securityProfileVisible);

        //
        // If this is a reply to upstream request activity then disable the
        // controls because the request activity defines the operation etc.
        //
        if (activity != null) {
            boolean isReply = ReplyActivityUtil.isReplyActivity(activity);
            boolean isInterfaceImplementor =
                    ProcessInterfaceUtil.isEventImplemented(activity);

            boolean isGenerated = false;
            if (isReply) {
                Activity requestAct =
                        ReplyActivityUtil
                                .getRequestActivityForReplyActivity(activity);
                if (requestAct != null
                        && Xpdl2ModelUtil
                                .isGeneratedRequestActivity(requestAct)) {
                    isGenerated = true;
                }
            } else {
                if (Xpdl2ModelUtil.isGeneratedRequestActivity(activity)) {
                    isGenerated = true;
                }
            }

            selectButton.setEnabled(!isReply && !isInterfaceImplementor);
            clearButton.setEnabled(!isReply && !isInterfaceImplementor);
            importWsdlButton.setEnabled(!isReply && !isInterfaceImplementor);
            portNameText.setEnabled(!isReply && !isInterfaceImplementor);
            portTypeNameText.setEnabled(!isReply && !isInterfaceImplementor);
            operationNameText.setEnabled(!isReply && !isInterfaceImplementor);
            generatedServiceLabel.setEnabled(!isReply
                    && !isInterfaceImplementor);
            serviceNameText.setEnabled(!isReply && !isInterfaceImplementor);
            wsdlLocationText.setEnabled(!isReply && !isInterfaceImplementor);
            aliasText.setEnabled(!isReply);

            // Some aren't always enabled so only disable if we are definitely
            // a reply.
            if (isReply) {
                transportNameCombo.setEnabled(false);
                localWsdl.setEnabled(false);
                remoteWsdl.setEnabled(false);
                clearAlias.setEnabled(false);
                aliasBrowse.setEnabled(false);
                securityProfileText.setEnabled(false);
            }

            if (isInterfaceImplementor && !isReply) {
                localWsdl.setEnabled(false);
            }

            String genServiceText = ""; //$NON-NLS-1$

            if (isGenerated) {
                genServiceText =
                        Messages.WebServiceDetailsSection_defaultGeneratedService_label;
            }

            if (!genServiceText.equals(generatedServiceLabel.getText())) {

                generatedServiceLabel.setText(genServiceText);
                if (genServiceText.length() == 0) {
                    GridData gd = new GridData();
                    gd.widthHint = 0;
                    generatedServiceLabel.setLayoutData(gd);
                } else {
                    generatedServiceLabel.setLayoutData(new GridData());
                }

                operationNameText.setLayoutData(new GridData(
                        GridData.FILL_HORIZONTAL));
                operationNameText.getParent().layout();
            }

            /*
             * XPD-4640: Should base transport selection enablement on
             * individual process destination NOT whole project.
             */
            Process process = activity.getProcess();
            boolean bpmDestination =
                    ProcessDestinationUtil.isBPMDestinationSelected(process);

            /*
             * XPD-4637: Don't ever enable for decision flows with decisions
             * destination set either.
             */
            boolean isDecisionsDestDecisionsFlow =
                    ProcessDestinationUtil
                            .isDecisionsDestinationSelected(process)
                            && DecisionFlowUtil.isDecisionFlow(process);

            boolean isAPIActivity =
                    Xpdl2ModelUtil.isProcessAPIActivity(activity);

            if (isAPIActivity && serviceName == null && !isReply
                    && !bpmDestination && !isDecisionsDestDecisionsFlow) {
                transportNameCombo.setEnabled(Boolean.TRUE);
            }

            // generatedServiceLabel.setForeground(ColorConstants.blue);
        }

    }

    /**
     * If the given value is <code>null</code> or an empty string then return
     * <code>null</code>.
     * 
     * @param value
     * @return
     */
    private String ifEmptySetToNull(String value) {
        return value == null || value.length() == 0 ? null : value;
    }

    /**
     * Resolves and returns the Transport name (as it appears on the combo box)
     * from what is currently stored in the model (model stores the actual uri
     * syntax not the user friendly name)
     * 
     * @param wso
     * @return
     */
    private String getTransportSelectionFromModel(WebServiceOperation wso) {
        String transport = ""; // XPD-614 //$NON-NLS-1$
        String transportUri =
                (String) Xpdl2ModelUtil.getOtherAttribute(wso,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_Transport());
        if (transportUri != null) {
            if (transportUri.toLowerCase().contains("/http")) { //$NON-NLS-1$
                transport = SOAP_OVER_HTTP_LABEL;
            } else if (transportUri.toLowerCase().contains("/jms")) { //$NON-NLS-1$
                transport = SOAP_OVER_JMS_LABEL;
            } else {
                transport = SERVICE_VIRTUALIZATION_LABEL;
            }
        }
        return transport;
    }

    /**
     * Resolves and returns the Transport name (as it appears on the combo box)
     * from what is set in the wsdl (wsdl contains the actual transport uri
     * syntax not the user friendly name)
     * 
     * @param wsdlWorkingCopy
     * @return
     */
    private String getTransportSelectionFromWsdl(IProject project,
            WsdlServiceKey key) {
        String transportName =
                WsdlIndexerUtil.getTransportUri(project, key, true, true);
        // XPD-614
        String transport = SERVICE_VIRTUALIZATION_LABEL;
        if (transportName != null) {
            if (transportName.toLowerCase().contains("/http")) { //$NON-NLS-1$
                transport = SOAP_OVER_HTTP_LABEL;
            } else if (transportName.toLowerCase().contains("/jms")) { //$NON-NLS-1$
                transport = SOAP_OVER_JMS_LABEL;
            } else {
                transport = SERVICE_VIRTUALIZATION_LABEL;
            }
        }
        return transport;
    }

    /**
     * Correct type for section input.
     * 
     * @return The activity.
     */
    protected Activity getActivityInput() {
        if (getInput() instanceof Activity) {
            return (Activity) getInput();
        }

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.AbstractEObjectSection#setInput(java.util
     * .Collection)
     */
    @Override
    public void setInput(Collection<?> items) {
        super.setInput(items);
        Activity act = getActivityInput();
        if (act == null) {
            activityMessage = null;
        } else {
            activityMessage =
                    ActivityMessageProviderFactory.INSTANCE
                            .getMessageProvider((Activity) getInput());
        }
    }

    protected void updateWsdlServiceKeyWithTransportName(IProject project,
            WsdlServiceKey key) {
        if (project != null && key != null) {
            String transportUri =
                    WsdlIndexerUtil.getTransportUri(project, key, true, true);

            if (transportUri == null) {
                /* Set default values */
                // IndexerItem item =
                // WsdlIndexerUtil.getOperationItem(project,
                // key,
                // true,
                // true);
                // if (item != null) {
                // if (WsdlIndexerUtil.isBW(item)) {
                // transportUri = Xpdl2WsdlUtil.XML_OVER_JMS_URL;
                // } else if (WsdlIndexerUtil.isSoapJms(item)) {
                // transportUri = Xpdl2WsdlUtil.SOAP_OVER_JMS_URL;
                // } else if (WsdlIndexerUtil.isSoapHttp(item)) {
                // transportUri = Xpdl2WsdlUtil.SOAP_OVER_HTTP_URL;
                // } else {
                // transportUri = Xpdl2WsdlUtil.SERVICE_VIRTUALIZATION_URL;
                // }
                // }

                // XPD-614: null transportUri means its a abstract wsdl.
                // defaulting abstract wsdls to service virtualisaton
                transportUri = Xpdl2WsdlUtil.SERVICE_VIRTUALIZATION_URL;
            }
            key.setTransportURI(transportUri);
        }
    }

    /**
     * Late execution command to assign the selected web service operation. This
     * will also take care of setting a project reference if required.
     */
    private class AssignWebServiceCommand extends AbstractLateExecuteCommand {

        private String wsdlUrl;

        private boolean isRemote;

        private WsdlServiceKey serviceKey;

        private final IProject project;

        private final IProject refProject;

        private Boolean canExecute;

        /**
         * Late execution command that will set project reference, if required,
         * before assigning web service operation.
         * 
         * @param editingDomain
         * @param contextObject
         *            {@link Activity}
         * @param project
         *            host project
         * @param refProject
         *            reference project (project from which operation selected)
         * @param wsdlUrl
         *            wsdl URL
         * @param isRemote
         *            <code>true</code> to set "Use Remote", <code>false</code>
         *            to set "Use Local"
         * @param serviceKey
         *            wsdl service key
         */
        public AssignWebServiceCommand(EditingDomain editingDomain,
                Object contextObject, IProject project, IProject refProject,
                String wsdlUrl, boolean isRemote, WsdlServiceKey serviceKey) {
            super(editingDomain, contextObject);
            this.project = project;
            this.refProject = refProject;
            this.wsdlUrl = wsdlUrl;
            this.isRemote = isRemote;
            this.serviceKey = serviceKey;
        }

        /**
         * @see com.tibco.xpd.xpdl2.commands.AbstractLateExecuteCommand#canExecute()
         * 
         * @return
         */
        @Override
        public boolean canExecute() {
            // value cached as this method may be called multiple times when
            // true is returned
            if (canExecute == null) {
                canExecute = Boolean.TRUE;
                if (refProject != null && refProject.exists()) {
                    /*
                     * If the selected operation is from an external project
                     * then a project reference needs to be set up
                     */
                    /*
                     * Sid XPD-7467: Check and add project reference properly
                     * (using method that does cyclic dependency checks etc)
                     */
                    if (!ProcessUIUtil.checkAndAddProjectReference(null,
                            project,
                            refProject)) {
                        // User refused to set project reference so
                        // cannot set operation
                        canExecute = Boolean.FALSE;
                    }
                }
            }

            return canExecute;
        }

        /**
         * @see com.tibco.xpd.xpdl2.commands.AbstractLateExecuteCommand#createCommand(org.eclipse.emf.edit.domain.EditingDomain,
         *      java.lang.Object)
         * 
         * @param editingDomain
         * @param contextObject
         * @return
         */
        @Override
        protected Command createCommand(EditingDomain editingDomain,
                Object contextObject) {
            if (contextObject instanceof Activity) {
                Activity activity = (Activity) contextObject;

                return activityMessage
                        .getAssignWebServiceCommand(editingDomain,
                                activity.getProcess(),
                                activity,
                                wsdlUrl,
                                isRemote,
                                serviceKey);
            }
            return null;
        }

    }
}
