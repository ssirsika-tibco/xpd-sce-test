/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processinterface.properties;

import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.ActivityManagerEvent;
import org.eclipse.ui.activities.IActivityManagerListener;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.part.Page;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.views.properties.tabbed.ISection;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDestinationUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.extensions.EventImplementationElement;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.SashDividedNamedElementSection;
import com.tibco.xpd.processwidget.ProcessWidgetPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.util.CapabilityUtil;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author rsomayaj
 * 
 * 
 */
public abstract class BaseMethodSection extends SashDividedNamedElementSection
        implements IPluginContribution {

    private static final String DEVELOPER_CAPABILITIES_ID =
            "com.tibco.xpd.capabilities.developer"; //$NON-NLS-1$

    protected Label triggerTypeLabel;

    protected Button noneTrigger;

    protected Button msgTrigger;

    private PageBook messageDescriptionPageBook;

    private MessageDescriptionPage messageDescriptionPage;

    private NonePage nonePage;

    private IActivityManagerListener activityListener;

    private Text txtMessageName;

    private Text txtTo;

    private Text txtFrom;

    private Text txtFaultName;

    private boolean showDetail;

    private PageBook implBook;

    private static final String IMPL_SECTION_EXTENSION_POINT_ID =
            "eventImplementation"; //$NON-NLS-1$

    private EventImplementationElement impl;

    private FormText solutionDesignForm;

    public BaseMethodSection(EClass reference, String actionPrefixId) {
        super(reference, actionPrefixId);

        activityListener = new IActivityManagerListener() {
            @Override
            public void activityManagerChanged(
                    ActivityManagerEvent activityManagerEvent) {
                if (activityManagerEvent.haveEnabledActivityIdsChanged()) {
                    refreshTabs();
                    refreshDetails();
                }

            }
        };
    }

    @Override
    protected String getDuplicateNameMessage() {
        return Messages.BaseMethodSection_NameExistsError_shortmsg;
    }

    @Override
    public void setInput(Collection<?> items) {
        super.setInput(items);

        NamedElement element = (NamedElement) getInput();
        if (element != null
                && ProcessInterfaceUtil.getProcessInterface(element) == null) {
            String baseName = element.getName();
            String finalName = baseName;

            EObject container = getInputContainer();
            if (container instanceof ProcessInterface) {
                int idx = 1;
                while (getDuplicateDisplayMethod((ProcessInterface) container,
                        element,
                        finalName) != null
                        || getDuplicateMethod((ProcessInterface) container,
                                element,
                                NameUtil.getInternalName(finalName, false)) != null) {
                    idx++;
                    finalName = baseName + idx;
                }
                element.setName(NameUtil.getInternalName(finalName, false));
                Xpdl2ModelUtil.setOtherAttribute(element,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_DisplayName(),
                        finalName);
            }
        }
    }

    protected abstract NamedElement getDuplicateMethod(
            ProcessInterface processInterface, NamedElement element,
            String finalName);

    protected abstract NamedElement getDuplicateDisplayMethod(
            ProcessInterface processInterface, NamedElement element,
            String finalName);

    @Override
    protected Command doGetCommand(Object obj) {
        InterfaceMethod interfaceMethod = (InterfaceMethod) getInput();

        EditingDomain editingDomain = getEditingDomain();
        if (editingDomain != null) {
            if (obj == name || obj == display) {
                if (nameValid && displayNameValid) {
                    Command nameCommand = super.doGetCommand(obj);
                    if (nameCommand != null) {
                        String ctrlText = name.getText();
                        if (ctrlText == null) {
                            ctrlText = ""; //$NON-NLS-1$
                        }

                        CompoundCommand cmd =
                                new CompoundCommand(
                                        Messages.BaseMethodSection_CmdMethodName_label);
                        cmd.append(nameCommand);
                        if (interfaceMethod.getTriggerResultMessage() != null) {
                            PortTypeOperation porttypeOperation =
                                    (PortTypeOperation) Xpdl2ModelUtil
                                            .getOtherElement(interfaceMethod
                                                    .getTriggerResultMessage(),
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getDocumentRoot_PortTypeOperation());
                            if (porttypeOperation != null) {
                                cmd.append(SetCommand
                                        .create(editingDomain,
                                                porttypeOperation,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getPortTypeOperation_OperationName(),
                                                ctrlText));
                            }
                        }
                        return cmd;
                    }
                }
            } else if (obj == noneTrigger) {
                CompoundCommand cmd =
                        new CompoundCommand(
                                Messages.BaseMethodSection_CmdTriggerTypeNone_shortdesc);
                cmd.append(SetCommand.create(editingDomain,
                        interfaceMethod,
                        XpdExtensionPackage.eINSTANCE
                                .getInterfaceMethod_Trigger(),
                        TriggerType.NONE_LITERAL));

                if (interfaceMethod.getTriggerResultMessage() != null) {
                    cmd.append(RemoveCommand.create(editingDomain,
                            interfaceMethod.getTriggerResultMessage()));
                }

                return cmd;

            } else if (obj == msgTrigger) {
                CompoundCommand cmdC =
                        new CompoundCommand(
                                Messages.BaseMethodSection_CmdMsgEventType_shortdesc);
                cmdC.append(SetCommand.create(editingDomain,
                        interfaceMethod,
                        XpdExtensionPackage.eINSTANCE
                                .getInterfaceMethod_Trigger(),
                        TriggerType.MESSAGE_LITERAL));

                ProcessInterface processInterface =
                        (ProcessInterface) getInputContainer();

                if (ProcessDestinationUtil
                        .shouldGenerateWSDLForProcessInterfaceDestinations(processInterface)) {

                    CompoundCommand cmd =
                            getUpdateMethodWsdlInfoCommand(editingDomain,
                                    processInterface,
                                    interfaceMethod);
                    cmdC.append(cmd);
                }

                return cmdC;

            } else if (obj == txtMessageName) {
                TriggerResultMessage triggerResultMessage =
                        interfaceMethod.getTriggerResultMessage();
                if (triggerResultMessage != null) {
                    Message msg = triggerResultMessage.getMessage();
                    if (msg != null) {
                        CompoundCommand cmd =
                                new CompoundCommand(
                                        Messages.BaseMethodSection_SetMsgName_shortdesc);
                        cmd.append(SetCommand.create(editingDomain,
                                msg,
                                Xpdl2Package.eINSTANCE.getNamedElement_Name(),
                                txtMessageName.getText()));
                        return cmd;
                    }
                }

            } else if (obj == txtFrom) {
                TriggerResultMessage triggerResultMessage =
                        interfaceMethod.getTriggerResultMessage();
                if (triggerResultMessage != null) {
                    Message msg = triggerResultMessage.getMessage();
                    if (msg != null) {
                        CompoundCommand cmd =
                                new CompoundCommand(
                                        Messages.BaseMethodSection_SetMsgName_shortdesc);
                        cmd.append(SetCommand.create(editingDomain,
                                msg,
                                Xpdl2Package.eINSTANCE.getMessage_From(),
                                txtFrom.getText()));
                        return cmd;
                    }
                }
            } else if (obj == txtTo) {
                TriggerResultMessage triggerResultMessage =
                        interfaceMethod.getTriggerResultMessage();
                if (triggerResultMessage != null) {
                    Message msg = triggerResultMessage.getMessage();
                    if (msg != null) {
                        CompoundCommand cmd =
                                new CompoundCommand(
                                        Messages.BaseMethodSection_SetMsgName_shortdesc);
                        cmd.append(SetCommand.create(editingDomain,
                                msg,
                                Xpdl2Package.eINSTANCE.getMessage_To(),
                                txtTo.getText()));
                        return cmd;
                    }
                }
            } else if (obj == txtFaultName) {
                TriggerResultMessage triggerResultMessage =
                        interfaceMethod.getTriggerResultMessage();
                if (triggerResultMessage != null) {
                    Message msg = triggerResultMessage.getMessage();
                    if (msg != null) {
                        CompoundCommand cmd =
                                new CompoundCommand(
                                        Messages.BaseMethodSection_SetMsgName_shortdesc);
                        cmd.append(SetCommand.create(editingDomain,
                                msg,
                                Xpdl2Package.eINSTANCE.getMessage_FaultName(),
                                txtFaultName.getText()));
                        return cmd;
                    }
                }
            } else {
                return super.doGetCommand(obj);
            }
        }
        return null;
    }

    /**
     * @param editingDomain
     * @param interfaceMethod
     * @return Command to clear the WSDL detail out of the interface method.
     */
    public static CompoundCommand getClearInterfaceMethodWsdlCommand(
            EditingDomain editingDomain, InterfaceMethod interfaceMethod) {
        if (interfaceMethod.getTriggerResultMessage() != null) {
            CompoundCommand cmd = new CompoundCommand();
            TriggerResultMessage trm =
                    Xpdl2Factory.eINSTANCE.createTriggerResultMessage();
            trm.setMessage(Xpdl2Factory.eINSTANCE.createMessage());

            cmd.append(SetCommand.create(editingDomain,
                    interfaceMethod,
                    XpdExtensionPackage.eINSTANCE
                            .getInterfaceMethod_TriggerResultMessage(),
                    trm));

            return cmd;
        }

        return null;
    }

    /**
     * @param editingDomain
     * @param processInterface
     * @param interfaceMethod
     * @return Command to add the WSDL detail to of the interface method.
     */
    public static CompoundCommand getUpdateMethodWsdlInfoCommand(
            EditingDomain editingDomain, ProcessInterface processInterface,
            InterfaceMethod interfaceMethod) {
        CompoundCommand cmd = new CompoundCommand();

        if (processInterface != null && interfaceMethod != null) {
            TriggerResultMessage triggerResultMessage =
                    Xpdl2Factory.eINSTANCE.createTriggerResultMessage();
            cmd.append(SetCommand.create(editingDomain,
                    interfaceMethod,
                    XpdExtensionPackage.eINSTANCE
                            .getInterfaceMethod_TriggerResultMessage(),
                    triggerResultMessage));

            Message message = triggerResultMessage.getMessage();
            if (message == null) {
                message = Xpdl2Factory.eINSTANCE.createMessage();
                cmd.append(SetCommand.create(editingDomain,
                        triggerResultMessage,
                        Xpdl2Package.eINSTANCE
                                .getTriggerResultMessage_Message(),
                        message));
            }
            PortTypeOperation portTypeOperation =
                    (PortTypeOperation) Xpdl2ModelUtil
                            .getOtherElement(triggerResultMessage,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_PortTypeOperation());
            if (portTypeOperation == null) {
                portTypeOperation =
                        XpdExtensionFactory.eINSTANCE.createPortTypeOperation();
                cmd.append(Xpdl2ModelUtil
                        .getSetOtherElementCommand(editingDomain,
                                triggerResultMessage,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_PortTypeOperation(),
                                portTypeOperation));
            }

            cmd.append(SetCommand.create(editingDomain,
                    portTypeOperation,
                    XpdExtensionPackage.eINSTANCE
                            .getPortTypeOperation_PortTypeName(),
                    processInterface.getName()));

            cmd.append(SetCommand.create(editingDomain,
                    portTypeOperation,
                    XpdExtensionPackage.eINSTANCE
                            .getPortTypeOperation_OperationName(),
                    interfaceMethod.getName()));

            ExternalReference externalReference =
                    Xpdl2Factory.eINSTANCE.createExternalReference();
            externalReference.setLocation(getWsdlFileName(processInterface));

            cmd.append(SetCommand.create(editingDomain,
                    portTypeOperation,
                    XpdExtensionPackage.eINSTANCE
                            .getPortTypeOperation_ExternalReference(),
                    externalReference));
        }
        return cmd;
    }

    private static String getWsdlFileName(ProcessInterface procInterface) {
        if (procInterface != null) {
            IFile file = WorkingCopyUtil.getFile(procInterface);

            if (file != null) {
                String fileName = file.getName();
                if (fileName.indexOf('.') != -1) {
                    return fileName.substring(0, fileName.indexOf('.'))
                            + ".wsdl"; //$NON-NLS-1$
                }
                return fileName;
            }
        }
        return null;
    }

    protected boolean shouldShowSolutionDesignForm() {
        return false;
    }

    /**
     * Get the selected input object as an activity
     * 
     * @return activity for event or null on error.
     */
    public Activity getActivity() {
        Object o = getInput();
        if (o instanceof Activity) {
            Activity act = (Activity) o;

            return act;
        }
        return null;
    }

    @Override
    protected void doRefreshDetailsSection() {
        if (solutionDesignForm != null) {
            if (!CapabilityUtil.isDeveloperActivityEnabled()) {
                solutionDesignForm
                        .setText(com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages.AddSolutionDesignCapability_form,
                                true,
                                false);
            } else {
                solutionDesignForm
                        .setText(com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages.RemoveSolutionDesignCapability_form,
                                true,
                                false);
            }
            if (shouldShowSolutionDesignForm()) {
                solutionDesignForm.setVisible(true);
            } else {
                solutionDesignForm.setVisible(false);
            }
        }
        InterfaceMethod interfaceMethod = (InterfaceMethod) getInput();
        if (interfaceMethod != null) {
            updateText(name, interfaceMethod.getName());
        }
        if (!display.isDisposed()) {
            String displayName =
                    (String) Xpdl2ModelUtil.getOtherAttribute(interfaceMethod,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName());
            if (displayName == null) {
                displayName = ""; //$NON-NLS-1$
            }
            updateText(display, displayName);
        }

        TriggerType trigger =
                (TriggerType) interfaceMethod
                        .eGet(XpdExtensionPackage.eINSTANCE
                                .getInterfaceMethod_Trigger());

        switch (trigger.getValue()) {
        case TriggerType.NONE:
            noneTrigger.setSelection(true);
            msgTrigger.setSelection(false);
            if (nonePage != null) {
                messageDescriptionPageBook.showPage(nonePage.getControl());
                setSashPercent(1.0f);
            }
            if (implBook != null) {
                implBook.setVisible(false);
            }
            break;

        case TriggerType.MESSAGE:
            noneTrigger.setSelection(false);
            msgTrigger.setSelection(true);
            if (messageDescriptionPage != null) {
                messageDescriptionPageBook.showPage(messageDescriptionPage
                        .getControl());
            }

            if (implBook != null && impl != null) {
                try {
                    implBook.setVisible(true);
                    if (impl != null && impl.getISectionExec() != null) {
                        impl.getISectionExec().refresh();
                    }
                } catch (CoreException e) {
                    e.printStackTrace();
                }
            }
            if (getSectionContainerType() == ContainerType.PROPERTYVIEW) {
                refreshDetails();
            }

            break;
        }
        if (interfaceMethod.getTriggerResultMessage() != null
                && interfaceMethod.getTriggerResultMessage().getMessage() != null) {
            if (txtMessageName != null) {
                updateText(txtMessageName, interfaceMethod
                        .getTriggerResultMessage().getMessage().getName());
            }
            if (txtFaultName != null) {
                updateText(txtFaultName, interfaceMethod
                        .getTriggerResultMessage().getMessage().getFaultName());
            }
            if (txtFrom != null) {
                updateText(txtFrom, interfaceMethod.getTriggerResultMessage()
                        .getMessage().getFrom());
            }
            if (txtTo != null) {
                updateText(txtTo, interfaceMethod.getTriggerResultMessage()
                        .getMessage().getTo());
            }
        }
        /*
         * XPD-4739: Saket: We have decided to deprecate Process Interface
         * Message Event capability. Although we still support them for backward
         * compatibility. Henceforth we won't allow the user to create a NEW
         * Message Event in a Process Interface because it is practically of
         * little use and has many issues associated with it.
         */
        if (TriggerType.NONE_LITERAL.equals(trigger)) {
            msgTrigger.setVisible(false);
            noneTrigger.setVisible(false);
            triggerTypeLabel.setVisible(false);
        } else {
            msgTrigger.setVisible(true);
            noneTrigger.setVisible(true);
            triggerTypeLabel.setVisible(true);
        }
        updateVisibility();
    }

    @Override
    protected void doRefreshImplementationSection() {
    }

    @Override
    protected Composite doCreateDetailsSection(Composite parent,
            XpdFormToolkit toolkit) {
        Composite root = toolkit.createComposite(parent);
        root.setLayout(new GridLayout());

        // Create controls for the extensions
        createImplControls(root, toolkit);
        implBook.setLayoutData(new GridData(GridData.FILL_BOTH));

        return root;
    }

    private Control createImplControls(Composite parent, XpdFormToolkit toolkit) {
        implBook = new PageBook(parent, SWT.NONE);// toolkit.createPageBook(parent

        impl = new EventImplementationElement(getMessageConfigElement());

        Composite page = toolkit.createComposite(implBook);
        FillLayout fillLayout = new FillLayout();
        fillLayout.marginHeight = 0;
        fillLayout.marginWidth = 0;
        page.setLayout(fillLayout);

        ISection implSection = getImplSectionFromConfig(impl);
        if (implSection != null) {
            implSection.createControls(page, getPropertySheetPage());
        }

        // Register listener for changes to capability that affect this section.
        PlatformUI.getWorkbench().getActivitySupport().getActivityManager()
                .addActivityManagerListener(activityListener);

        implBook.showPage(page);

        return implBook;
    }

    /**
     * Get the properties section a given configuration for the message
     * implementation. Handle exception in cofiguration (This will probably
     * cause NPE downstream so log cause here).
     * 
     * @param impl
     *            Config Element.
     * @return Instance of section (or null if exception occurred).
     */
    private ISection getImplSectionFromConfig(EventImplementationElement impl) {
        ISection implSection = null;
        try {
            implSection = impl.getISectionExec();
        } catch (CoreException e) {
            Logger logger = Xpdl2ProcessEditorPlugin.getDefault().getLogger();
            logger.error(e);
        }
        return implSection;
    }

    private IConfigurationElement getMessageConfigElement() {
        IExtensionRegistry er = Platform.getExtensionRegistry();

        IExtensionPoint ep =
                er.getExtensionPoint(ProcessWidgetPlugin.ID,
                        IMPL_SECTION_EXTENSION_POINT_ID);

        IExtension[] extensions = ep.getExtensions();
        if (extensions != null) {
            for (int i = 0; i < extensions.length; i++) {
                IExtension extension = extensions[i];
                IConfigurationElement[] configElements =
                        extension.getConfigurationElements();
                for (int j = 0; j < configElements.length; j++) {
                    IConfigurationElement configElement = configElements[j];
                    if (configElement.getAttribute("triggerResultType") != null //$NON-NLS-1$
                            && configElement.getAttribute("triggerResultType") //$NON-NLS-1$
                                    .equals(Messages.BaseMethodSection_1)
                            && getMethodTypeName()
                                    .equals(configElement.getAttribute("name"))) { //$NON-NLS-1$
                        return configElement;
                    }
                }
            }
        }

        // New process editor extension.
        ep =
                er.getExtensionPoint(Xpdl2ProcessEditorPlugin.ID,
                        IMPL_SECTION_EXTENSION_POINT_ID);

        extensions = ep.getExtensions();
        if (extensions != null) {
            for (int i = 0; i < extensions.length; i++) {
                IExtension extension = extensions[i];
                IConfigurationElement[] configElements =
                        extension.getConfigurationElements();
                for (int j = 0; j < configElements.length; j++) {
                    IConfigurationElement configElement = configElements[j];
                    if (configElement.getAttribute("triggerResultType") != null //$NON-NLS-1$
                            && configElement.getAttribute("triggerResultType") //$NON-NLS-1$
                                    .equals("Message") //$NON-NLS-1$
                            && getMethodTypeName()
                                    .equals(configElement.getAttribute("name"))) { //$NON-NLS-1$
                        return configElement;
                    }
                }
            }
        }

        return null;
    }

    protected abstract String getMethodTypeName();

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        if (getSectionContainerType() == ContainerType.PROPERTYVIEW) {
            return super.doCreateControls(parent, toolkit);
        } else if (getSectionContainerType() == ContainerType.WIZARD) {
            return createGeneralSection(parent, toolkit);
        }

        return super.doCreateControls(parent, toolkit);
    }

    @Override
    protected Composite doCreateGeneralSection(Composite parent,
            XpdFormToolkit toolkit) {
        Composite root = toolkit.createComposite(parent);
        GridLayout gl = new GridLayout(2, false);
        gl.marginLeft = gl.marginWidth + 3;
        gl.marginWidth = 0;
        root.setLayout(gl);

        if (getSectionContainerType() == ContainerType.WIZARD) {
            root.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        }

        triggerTypeLabel =
                toolkit.createLabel(root, Messages.BaseMethodSection_Type_label);

        Composite btnComposite = toolkit.createComposite(root);
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        btnComposite.setLayoutData(gd);

        GridLayout gridLayout = new GridLayout(2, true);
        btnComposite.setLayout(gridLayout);

        noneTrigger =
                toolkit.createButton(btnComposite,
                        Messages.BaseMethodSection_NoneTriggerType_label,
                        SWT.RADIO);
        noneTrigger.setLayoutData(new GridData());
        manageControl(noneTrigger);

        msgTrigger =
                toolkit.createButton(btnComposite,
                        Messages.BaseMethodSection_MessageTriggerType_label,
                        SWT.RADIO);
        msgTrigger.setLayoutData(new GridData());
        manageControl(msgTrigger);

        if (getSectionContainerType() == ContainerType.PROPERTYVIEW) {
            messageDescriptionPageBook = new PageBook(root, SWT.None);
            gd = new GridData(GridData.FILL_BOTH);
            gd.horizontalSpan = 2;

            messageDescriptionPageBook.setLayoutData(gd);
            messageDescriptionPage = new MessageDescriptionPage(toolkit);
            messageDescriptionPage.createControl(messageDescriptionPageBook);

            nonePage = new NonePage();
            nonePage.createControl(messageDescriptionPageBook);

        }

        // updateVisibility();

        return root;
    }

    @Override
    public String getLocalId() {
        return "analyst.methods"; //$NON-NLS-1$
    }

    @Override
    public String getPluginId() {
        return Xpdl2ResourcesPlugin.PLUGIN_ID;
    }

    @Override
    public IPluginContribution getPluginContributon() {
        return this;
    }

    class MessageDescriptionPage extends Page {
        private XpdFormToolkit toolkit;

        private Composite messageDescriptionComposite;

        public MessageDescriptionPage() {

        }

        public MessageDescriptionPage(XpdFormToolkit toolkit) {
            this.toolkit = toolkit;
        }

        @SuppressWarnings("deprecation")
        @Override
        public void createControl(Composite parent) {
            messageDescriptionComposite =
                    toolkit.createComposite(parent, SWT.None);
            GridData gd = new GridData(GridData.FILL_BOTH);
            gd.horizontalSpan = 2;
            messageDescriptionComposite.setLayoutData(gd);
            // messageDescriptionComposite.setBackground(new Color(null, 2, 2,
            // 2));
            GridLayout gl = new GridLayout(2, false);
            gl.marginHeight = 0;
            gl.marginWidth = 0;
            messageDescriptionComposite.setLayout(gl);
            toolkit.createLabel(messageDescriptionComposite,
                    Messages.BaseMethodSection_MessageName_label);
            txtMessageName =
                    toolkit.createText(messageDescriptionComposite, ""); //$NON-NLS-1$
            txtMessageName
                    .setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
            manageControl(txtMessageName);

            toolkit.createLabel(messageDescriptionComposite,
                    Messages.BaseMethodSection_MessageTo_label);
            txtTo = toolkit.createText(messageDescriptionComposite, ""); //$NON-NLS-1$
            txtTo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
            manageControl(txtTo);

            toolkit.createLabel(messageDescriptionComposite,
                    Messages.BaseMethodSection_MessageFrom_label);
            txtFrom = toolkit.createText(messageDescriptionComposite, ""); //$NON-NLS-1$
            txtFrom.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
            manageControl(txtFrom);

            toolkit.createLabel(messageDescriptionComposite,
                    Messages.BaseMethodSection_MessageFault_label);
            txtFaultName = toolkit.createText(messageDescriptionComposite, ""); //$NON-NLS-1$
            txtFaultName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
            manageControl(txtFaultName);

            solutionDesignForm =
                    toolkit.createFormText(messageDescriptionComposite, true);

            /*
             * Must ensure we set SOME text on FormText before we do first
             * layout else subsequent ones will do nothing!
             */
            solutionDesignForm
                    .setText(com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages.AddSolutionDesignCapability_form,
                            true,
                            false);

            gd =
                    new GridData(GridData.FILL_HORIZONTAL
                            | GridData.HORIZONTAL_ALIGN_END);
            gd.horizontalSpan = 2;
            solutionDesignForm.setLayoutData(gd);
            manageControl(solutionDesignForm);
        }

        @Override
        public Control getControl() {
            return messageDescriptionComposite;
        }

        @Override
        public void setFocus() {

        }

    }

    class NonePage extends Page {

        private Composite nonePageComposite;

        private final Color WHITE = new Color(null, 255, 255, 255);

        public NonePage() {
        }

        @Override
        public void createControl(Composite parent) {
            nonePageComposite = new Composite(parent, SWT.None);
            GridData gd = new GridData(GridData.FILL_BOTH);
            nonePageComposite.setLayoutData(gd);
            nonePageComposite.setBackground(WHITE);
        }

        @Override
        public Control getControl() {
            return nonePageComposite;
        }

        @Override
        public void setFocus() {

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.properties.general.SashDividedSection#
     * aboutToBeHidden()
     */
    @Override
    public void aboutToBeHidden() {
        PlatformUI.getWorkbench().getActivitySupport().getActivityManager()
                .removeActivityManagerListener(activityListener);
        super.aboutToBeHidden();
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.properties.general.SashDividedSection#
     * aboutToBeShown()
     */
    @Override
    public void aboutToBeShown() {
        super.aboutToBeShown();
        PlatformUI.getWorkbench().getActivitySupport().getActivityManager()
                .addActivityManagerListener(activityListener);
    }

    /**
     * @param taskType
     */
    private void refreshDetails() {
        EObject method = getInput();

        if (method != null && impl != null) {
            ISection section = getImplSectionFromConfig(impl);
            if (section != null) {
                section.setInput(getPart(), new StructuredSelection(method));
                section.refresh();
            }
        }

        showDetail =
                PlatformUI.getWorkbench().getActivitySupport()
                        .getActivityManager()
                        .getActivity(DEVELOPER_CAPABILITIES_ID).isEnabled();
        if (method != null && showDetail) {
            if (getTriggerType() == TriggerType.MESSAGE_LITERAL) {
                setSashPercentToLastUserSelected();
            } else {
                setSashPercent(1.0f);
            }

        } else {
            setSashPercent(1.0f);
        }
    }

    protected abstract TriggerType getTriggerType();

}
