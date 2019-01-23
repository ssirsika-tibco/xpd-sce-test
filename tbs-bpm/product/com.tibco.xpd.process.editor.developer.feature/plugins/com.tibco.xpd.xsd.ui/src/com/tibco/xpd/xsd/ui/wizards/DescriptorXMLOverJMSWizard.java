/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.xsd.ui.wizards;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.wsdl.extensions.soap.SOAPAddress;
import javax.xml.namespace.QName;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.Transaction;
import org.eclipse.emf.transaction.impl.InternalTransactionalEditingDomain;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.wst.wsdl.Binding;
import org.eclipse.wst.wsdl.BindingInput;
import org.eclipse.wst.wsdl.BindingOperation;
import org.eclipse.wst.wsdl.BindingOutput;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.Input;
import org.eclipse.wst.wsdl.Message;
import org.eclipse.wst.wsdl.Operation;
import org.eclipse.wst.wsdl.Output;
import org.eclipse.wst.wsdl.Part;
import org.eclipse.wst.wsdl.Port;
import org.eclipse.wst.wsdl.PortType;
import org.eclipse.wst.wsdl.Service;
import org.eclipse.wst.wsdl.Types;
import org.eclipse.wst.wsdl.WSDLFactory;
import org.eclipse.wst.wsdl.XSDSchemaExtensibilityElement;
import org.eclipse.wst.wsdl.binding.soap.SOAPBinding;
import org.eclipse.wst.wsdl.binding.soap.SOAPBody;
import org.eclipse.wst.wsdl.binding.soap.SOAPFactory;
import org.eclipse.wst.wsdl.binding.soap.SOAPOperation;
import org.eclipse.wst.wsdl.util.WSDLResourceImpl;
import org.eclipse.xsd.XSDSchema;

import com.tibco.xpd.registry.ui.wizard.IImportWizardWithOperationPicker;
import com.tibco.xpd.registry.ui.wizard.OperationPickerPage;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.internal.indexer.IndexerServiceImpl;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xsd.ui.internal.Messages;
import com.tibco.xpd.xsd.ui.models.ElementModel;
import com.tibco.xpd.xsd.ui.pages.DocumentMappingPage;
import com.tibco.xpd.xsd.ui.pages.DocumentSelectionPage;
import com.tibco.xpd.xsd.ui.pages.WsdlConfigurationPage;

/**
 * XML over JMS WSDL import wizard.
 * 
 * @author glewis
 */
public class DescriptorXMLOverJMSWizard extends Wizard implements
        IImportWizardWithOperationPicker {

    /** The document selection page */
    private final DocumentSelectionPage documentSelectionPage;

    /** The document mapping page */
    private final DocumentMappingPage documentMappingPage;

    /** The wsdl details page */
    private final WsdlConfigurationPage wsdlConfigPage;

    /**
     * Operation picker page (only shows if called from an action button from a
     * section)
     */
    private OperationPickerPage operationPickerPage = null;

    private static final String SOAP_PROTOCOL = "SOAP"; //$NON-NLS-1$

    private static final String HTTP_PROTOCOL = "HTTP"; //$NON-NLS-1$

    public String defaultName = "NewWSDLFile"; //$NON-NLS-1$

    public String defaultFileExtension = ".wsdl"; //$NON-NLS-1$

    private static String charSet = "UTF-8"; //$NON-NLS-1$

    private static String wsdlPrefix = "wsdl"; //$NON-NLS-1$                    

    private static String targetPrefix = "tns"; //$NON-NLS-1$

    private static String schemaPrefix = "sns"; //$NON-NLS-1$

    private static String soapPrefix = "soap"; //$NON-NLS-1$

    private String newFileName;

    private IPath selectedPath;

    private static final String XML_OVER_JMS_URL =
            "http://www.tibco.com/namespaces/ws/2004/soap/binding/JMS"; //$NON-NLS-1$

    private static final String SOAP_TARGET_NAMESPACE =
            "http://schemas.xmlsoap.org/wsdl/soap/"; //$NON-NLS-1$

    /**
     * Constructor initialises pages
     */
    public DescriptorXMLOverJMSWizard() {
        super();
        setWindowTitle(Messages.DescriptorXMLOverJMSWizard_Title);
        setNeedsProgressMonitor(true);
        documentSelectionPage = new DocumentSelectionPage();
        documentMappingPage = new DocumentMappingPage();
        wsdlConfigPage = new WsdlConfigurationPage();
    }

    /**
     * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
     *      org.eclipse.jface.viewers.IStructuredSelection) Finds the current
     *      project selection so we can default it for location fields - if a
     *      file then we get the parent folder path.
     * @param workbench
     * @param selection
     */
    @Override
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        if (selection != null && selection.size() > 0) {
            Object target = selection.getFirstElement();
            if (target instanceof IAdaptable) {
                IAdaptable adaptable = (IAdaptable) target;
                target = adaptable.getAdapter(IResource.class);
                if (target instanceof IFolder) {
                    selectedPath = ((IFolder) target).getFullPath();
                } else if (target instanceof IFile) {
                    selectedPath = ((IFile) target).getParent().getFullPath();
                }
            }
        }
    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#addPages()
     */
    @Override
    public void addPages() {
        // add pages to wizard
        addPage(documentSelectionPage);
        addPage(documentMappingPage);
        addPage(wsdlConfigPage);

        // add operation picker page if required
        if (operationPickerPage != null) {
            addPage(operationPickerPage);
        }
    }

    /**
     * @param page
     *            The current page.
     * @return The next page.
     * @see org.eclipse.jface.wizard.Wizard#getNextPage(org.eclipse.jface.wizard.IWizardPage)
     */
    @Override
    public IWizardPage getNextPage(IWizardPage page) {
        // if current page is documentSelectionPage then we refresh the next
        // page (documentMappingPage) passing
        // the incoming and outgoing paths so it can create the appropriate
        // mapping lists from the user inputted xml / xsd
        if (page.equals(documentSelectionPage)) {
            documentMappingPage.doRefresh(documentSelectionPage
                    .getIncomingElements(), documentSelectionPage
                    .getOutgoingElements());
        } else if (page.equals(documentMappingPage)) {
            newFileName = createDefaultFileName();
            wsdlConfigPage.doRefresh(newFileName);
        }

        IWizardPage nextPage = super.getNextPage(page);
        // if operation picker page is not null then we know this page has been
        // called from an action and we
        // need to import and show the wsdl methods before user can finish.
        if (operationPickerPage != null && operationPickerPage.equals(nextPage)) {
            if (wsdlConfigPage.getOutputFolderIPath() == null
                    || !wsdlConfigPage.getOutputFolderIPath().toOSString()
                            .equals(operationPickerPage.getNavigateLocation())) {
                if (!performFinish()) {
                    nextPage = null;
                }
            }
            operationPickerPage.setNavigateLocation(wsdlConfigPage
                    .getOutputFolderIPath().toOSString());
        }
        return nextPage;
    }

    /**
     * Recalcualtes the wsdl file name to be created - checks location currently
     * specified in the wsdlConfigPage and ensures a unique file name for that
     * particular folder.
     */
    public void recalculateFileName() {
        newFileName = createDefaultFileName();
        // refresh the other fields (port type etc) now we have the new file
        // name
        wsdlConfigPage.doRefresh(newFileName);
    }

    /**
     * @return Default protocol options for wsdl
     */
    public Object[] getProtocolOptions() {
        Object bool[] = new Boolean[3];
        bool[0] = new Boolean(true);
        bool[2] = new Boolean(false);
        return bool;
    }

    /**
     * Keeps incrementing filename until it is unique inside the container
     * folder and then it returns this.
     * 
     * @return unique file name
     */
    private String createDefaultFileName() {
        int count = 0;
        String fileName = defaultName + defaultFileExtension;
        IPath newFilePath = wsdlConfigPage.getOutputFolderIPath();
        if (newFilePath != null) {
            while (true) {
                IPath path = newFilePath.append(fileName);
                if (ResourcesPlugin.getWorkspace().getRoot().exists(path)) {
                    count++;
                    fileName = defaultName + count + defaultFileExtension;
                } else {
                    break;
                }
            }
        }
        return fileName;
    }

    @Override
    public boolean performFinish() {
        // if currently on operation picker and it is marked as complete then we
        // do nothing as wsdl
        // is already created at this point (operation picker only deals with
        // the operation picking!)
        if (operationPickerPage != null && operationPickerPage.isPageComplete()) {
            return true;
        } else { // create the xml over jms transport type wsdl with its xsd
            // input and output schemas and default bindings and port type
            // etc
            // get the user defined wsdl config names
            final String serverTargetNamespace =
                    wsdlConfigPage.getServerTargetNameSpace().getText();
            final String operationName =
                    wsdlConfigPage.getOperation().getText();
            final String portType = wsdlConfigPage.getPortType().getText();
            final String typeNamespace =
                    wsdlConfigPage.getTypeNameSpace().getText();

            IRunnableWithProgress op = new IRunnableWithProgress() {
                @Override
                public void run(IProgressMonitor monitor)
                        throws InvocationTargetException {
                    Transaction tx = null;
                    try {
                        monitor.beginTask(Messages.DescriptorXMLOverJMSWizard_WizardFinish_label,
                                IProgressMonitor.UNKNOWN);

                        // get folder which we want to create wsdl inside
                        IPath outputFolderPath =
                                wsdlConfigPage.getOutputFolderIPath();
                        IFolder folder =
                                ResourcesPlugin.getWorkspace().getRoot()
                                        .getFolder(outputFolderPath);

                        // create temp wsdl file in this folder with the user
                        // specified file name
                        IFile file = folder.getFile(newFileName);
                        URI uri =
                                URI.createPlatformResourceURI(file
                                        .getFullPath().toString(), true);

                        // Assign the definition name as the file name
                        String definitionName = wsdlConfigPage.getNewFileName();

                        // create the resource
                        Resource resource =
                                XpdResourcesPlugin.getDefault()
                                        .getEditingDomain().getResourceSet()
                                        .createResource(uri);

                        /*
                         * Create active read-write transaction to update and
                         * save the wsdl
                         */
                        Map<String, Boolean> txOptions =
                                new HashMap<String, Boolean>();
                        // Don't want any post-commit notifications
                        txOptions.put(Transaction.OPTION_NO_NOTIFICATIONS,
                                Boolean.TRUE);
                        tx =
                                ((InternalTransactionalEditingDomain) XpdResourcesPlugin
                                        .getDefault().getEditingDomain())
                                        .startTransaction(false, txOptions);

                        // create definition and add it to the resource
                        Definition definition =
                                WSDLFactory.eINSTANCE.createDefinition();
                        resource.getContents().add(definition);

                        // set definition attributes like namespaces etc
                        definition.setTargetNamespace(serverTargetNamespace);
                        definition.setLocation(file.getLocation().toString());
                        definition.setEncoding(charSet);
                        definition.setQName(new QName(wsdlPrefix,
                                definitionName));
                        definition.addNamespace(soapPrefix,
                                SOAP_TARGET_NAMESPACE);
                        definition.addNamespace(targetPrefix,
                                serverTargetNamespace);
                        definition.addNamespace(schemaPrefix, typeNamespace);

                        // add default schema
                        Types types = definition.getETypes();
                        if (types == null) {
                            types = WSDLFactory.eINSTANCE.createTypes();
                            definition.setTypes(types);
                        }

                        // If incoming schema defined add this
                        if (documentSelectionPage.getIncomingSchema() != null) {
                            addXSDSchema(types,
                                    typeNamespace,
                                    documentSelectionPage.getIncomingSchema());
                        }

                        // If outgoing schema defined add this
                        if (documentSelectionPage.getOutgoingSchema() != null) {
                            addXSDSchema(types,
                                    typeNamespace,
                                    documentSelectionPage.getOutgoingSchema());
                        }

                        // add namespaces
                        definition
                                .addNamespace("wsdl", "http://schemas.xmlsoap.org/wsdl/"); //$NON-NLS-1$  //$NON-NLS-2$
                        definition
                                .addNamespace("xsd", "http://www.w3.org/2001/XMLSchema"); //$NON-NLS-1$  //$NON-NLS-2$

                        // update the definition
                        definition.updateElement(true);

                        // create the service
                        Service service = WSDLFactory.eINSTANCE.createService();
                        service.setQName(new QName(definitionName));
                        definition.addService(service);

                        // create the port type
                        PortType tempPortType =
                                WSDLFactory.eINSTANCE.createPortType();
                        tempPortType.setQName(new QName(serverTargetNamespace,
                                portType));
                        definition.addPortType(tempPortType);

                        // create the operation
                        Operation operation =
                                WSDLFactory.eINSTANCE.createOperation();
                        operation.setName(operationName);
                        tempPortType.addOperation(operation);

                        // create the input message (used by the input)
                        Message inputMessage =
                                WSDLFactory.eINSTANCE.createMessage();
                        inputMessage.setQName(new QName(serverTargetNamespace,
                                operationName + "Request")); //$NON-NLS-1$
                        definition.addMessage(inputMessage);

                        // create the output message (used by the output)
                        Message outputMessage =
                                WSDLFactory.eINSTANCE.createMessage();
                        outputMessage.setQName(new QName(serverTargetNamespace,
                                operationName + "Response")); //$NON-NLS-1$
                        definition.addMessage(outputMessage);

                        // create the input and set the corresponding
                        // message
                        Input input = WSDLFactory.eINSTANCE.createInput();
                        input.setMessage(inputMessage);

                        // create the output and set the corresponding
                        // message
                        Output output = WSDLFactory.eINSTANCE.createOutput();
                        output.setMessage(outputMessage);

                        // set both input and output on operation we created
                        // earlier
                        operation.setInput(input);
                        operation.setOutput(output);

                        // set input and output operations
                        setInputMessageParts(definition);
                        setOutputMessagesParts(definition);

                        // create the binding and all its necessary
                        // attributes
                        Binding binding =
                                createBindingDetails(definition,
                                        tempPortType,
                                        serverTargetNamespace,
                                        definitionName,
                                        operationName);

                        // add a port set its binding to the one created
                        // earlier and add to service
                        Port port = WSDLFactory.eINSTANCE.createPort();
                        port.setBinding(binding);
                        port.setName(definitionName + SOAP_PROTOCOL);
                        service.addPort(port);

                        // add a soap address and set it as the server
                        // target namespace (probably user will want to
                        // change manually to something that does exist)
                        SOAPAddress soapAddress =
                                SOAPFactory.eINSTANCE.createSOAPAddress();
                        soapAddress.setLocationURI(serverTargetNamespace);
                        port.addExtensibilityElement(soapAddress);

                        // update definition
                        definition.updateElement(true);

                        // save changes made to the file
                        Map<?, ?> options = null;
                        if (resource instanceof WSDLResourceImpl) {
                            options =
                                    ((WSDLResourceImpl) resource)
                                            .getDefaultSaveOptions();
                        }
                        resource.save(options);

                        // Unload the resource now so that when the working copy
                        // loads it it is read properly from file
                        resource.unload();

                        /*
                         * Sid SIA-36. The resource as we saved it with
                         * operation bindings in extensibility elements cannot
                         * be indexed properly (which will have been attempted
                         * on save) so must re-index explicitly after unload and
                         * reload.
                         */
                        /* Touch it first to get it re-indexed. */
                        WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(file);
                        if (wc != null) {
                            ((IndexerServiceImpl) XpdResourcesPlugin
                                    .getDefault().getIndexerService())
                                    .index(wc, true);
                        }

                        // open the wsdl in the editor if we don't need to
                        // select any operation on next page i.e. time to
                        // finish!
                        if (operationPickerPage == null) {
                            final IFile editFile = file;
                            getShell().getDisplay().asyncExec(new Runnable() {
                                @Override
                                public void run() {
                                    openEditor(editFile);
                                }
                            });
                        }
                    } catch (Exception e) {
                        throw new InvocationTargetException(e);
                    } finally {
                        if (tx != null) {
                            // Commit the transaction
                            try {
                                tx.commit();
                            } catch (RollbackException e) {
                                showError(e.getStatus());
                            }
                        }
                        monitor.done();
                    }
                }
            };
            try {
                getContainer().run(true, false, op);
            } catch (InterruptedException e) {
                return false;
            } catch (InvocationTargetException e) {
                Throwable th = e;

                if (e.getCause() != null) {
                    th = e.getCause();
                }
                Status status =
                        new Status(IStatus.ERROR,
                                com.tibco.xpd.xsd.ui.Activator.PLUGIN_ID,
                                IStatus.OK, th.getLocalizedMessage(), th);
                showError(status);
                return false;
            }
        }
        return true;
    }

    /**
     * Show an error dialog with the given status.
     * 
     * @param status
     */
    private void showError(IStatus status) {
        ErrorDialog.openError(getShell(),
                Messages.DescriptorXMLOverJMSWizard_WizardFinish_message,
                Messages.DescriptorXMLOverJMSWizard_WizardFinish_error,
                status);
    }

    /**
     * Creates the binding for this new wsdl with all the appropriate attributes
     * and elements
     * 
     * @param definition
     * @param tempPortType
     * @param serverTargetNamespace
     * @param definitionName
     * @param operationName
     * @return
     */
    private Binding createBindingDetails(Definition definition,
            PortType tempPortType, String serverTargetNamespace,
            String definitionName, String operationName) {
        // create the binding and ensure its target namespace is correct
        Binding binding = WSDLFactory.eINSTANCE.createBinding();
        binding.setQName(new QName(serverTargetNamespace, definitionName
                + SOAP_PROTOCOL));
        binding.setPortType(tempPortType);
        definition.addBinding(binding);

        // create a soap binding for this binding and set the xml over jms
        // transport
        SOAPBinding soapBinding = SOAPFactory.eINSTANCE.createSOAPBinding();
        soapBinding.setTransportURI(XML_OVER_JMS_URL);
        soapBinding.setStyle("document"); //$NON-NLS-1$
        binding.addExtensibilityElement(soapBinding);

        // add a binding operation
        BindingOperation bindingOperation =
                WSDLFactory.eINSTANCE.createBindingOperation();
        bindingOperation.setName(operationName);
        binding.addBindingOperation(bindingOperation);

        // add a soap operation
        SOAPOperation soapOperation =
                SOAPFactory.eINSTANCE.createSOAPOperation();
        soapOperation.setSoapActionURI(serverTargetNamespace + operationName);
        bindingOperation.addExtensibilityElement(soapOperation);

        // add soap body (used by binding input)
        SOAPBody soapBody = SOAPFactory.eINSTANCE.createSOAPBody();
        soapBody.setUse("literal"); //$NON-NLS-1$

        // add a binding input and set the soap body
        BindingInput bindingOperationInput =
                WSDLFactory.eINSTANCE.createBindingInput();
        bindingOperationInput.addExtensibilityElement(soapBody);
        bindingOperation.setBindingInput(bindingOperationInput);

        // add soap body (used by binding output)
        soapBody = SOAPFactory.eINSTANCE.createSOAPBody();
        soapBody.setUse("literal"); //$NON-NLS-1$

        // add a binding output and set the soap body
        BindingOutput bindingOperationOutput =
                WSDLFactory.eINSTANCE.createBindingOutput();
        bindingOperationOutput.addExtensibilityElement(soapBody);
        bindingOperation.setBindingOutput(bindingOperationOutput);

        return binding;
    }

    /**
     * Get the input messages and rename to the appropriate element names that
     * we want to use and also prefix them with the relevant schema namespace so
     * it knows where to find it.
     * 
     * @param definition
     */
    private void setInputMessageParts(Definition definition) {
        if (documentSelectionPage.getIncomingSchema() != null) {
            List<ElementModel> incomingElementsList =
                    documentMappingPage.getIncomingSelection();
            // get port types
            List<PortType> portTypes = definition.getEPortTypes();
            for (PortType pt : portTypes) {
                List<Operation> operations = pt.getEOperations();
                // Add the new parts to the Operation - basically these are the
                // input elements specified in selection mapping page
                for (Operation op : operations) {
                    List<Part> parts = op.getEInput().getEMessage().getEParts();
                    int index = 0;
                    for (ElementModel elementModel : incomingElementsList) {
                        Part part = WSDLFactory.eINSTANCE.createPart();
                        part.setElementName(new QName(schemaPrefix,
                                schemaPrefix + ":" + elementModel.getName())); //$NON-NLS-1$
                        part.setName("parameters" + (index + 1)); //$NON-NLS-1$  
                        parts.add(part);
                        index++;
                    }
                }
            }
        }
    }

    /**
     * Get the output messages and rename to the appropriate element names that
     * we want to use and also prefix them with the relevant schema namespace so
     * it knows where to find it.
     * 
     * @param definition
     */
    private void setOutputMessagesParts(Definition definition) {
        if (documentSelectionPage.getOutgoingSchema() != null) {
            List<ElementModel> outgoingElementsList =
                    documentMappingPage.getOutgoingSelection();
            String partName = ""; //$NON-NLS-1$
            // get port types
            List<PortType> portTypes = definition.getEPortTypes();
            for (PortType pt : portTypes) {
                List<Operation> operations = pt.getEOperations();
                // Add the new parts to the Operation - basically these are the
                // output elements specified in selection mapping page
                for (Operation op : operations) {
                    List<Part> parts =
                            op.getEOutput().getEMessage().getEParts();
                    int index = 0;
                    for (ElementModel elementModel : outgoingElementsList) {
                        Part part = WSDLFactory.eINSTANCE.createPart();
                        part.setElementName(new QName(schemaPrefix,
                                schemaPrefix + ":" + elementModel.getName())); //$NON-NLS-1$
                        part.setName(partName + (index + 1));
                        parts.add(part);
                        index++;
                    }
                }
            }
        }
    }

    /**
     * Adds the passed in XSD schema to the wsdl file and sets the appropriate
     * namespaces.
     * 
     * @param types
     * @param namespace
     * @param schema
     */
    private void addXSDSchema(Types types, String namespace, XSDSchema schema) {
        XSDSchemaExtensibilityElement extensibilityElement =
                WSDLFactory.eINSTANCE.createXSDSchemaExtensibilityElement();
        extensibilityElement.setEnclosingDefinition(types
                .getEnclosingDefinition());
        schema.setTargetNamespace(namespace);
        schema.setSchemaLocation(namespace);
        schema.setSchemaForSchemaQNamePrefix("xsd"); //$NON-NLS-1$
        extensibilityElement.setSchema(schema);
        types.addExtensibilityElement(extensibilityElement);
    }

    /**
     * Opens the wsdl file in the editor window for further processing.
     * 
     * @param iFile
     */
    static public void openEditor(final IFile iFile) {
        if (iFile != null) {
            IWorkbench workbench = PlatformUI.getWorkbench();
            final IWorkbenchWindow workbenchWindow =
                    workbench.getActiveWorkbenchWindow();

            Display.getDefault().asyncExec(new Runnable() {
                @Override
                public void run() {
                    try {
                        String editorId = null;
                        IEditorDescriptor editor =
                                PlatformUI
                                        .getWorkbench()
                                        .getEditorRegistry()
                                        .getDefaultEditor(iFile.getLocation()
                                                .toOSString(),
                                                iFile.getContentDescription()
                                                        .getContentType());
                        if (editor != null) {
                            editorId = editor.getId();
                        }
                        workbenchWindow.getActivePage()
                                .openEditor(new FileEditorInput(iFile),
                                        editorId);
                    } catch (PartInitException ex) {
                    } catch (CoreException ex) {
                    }
                }
            });
        }
    }

    /**
     * @return
     */
    public IPath getSelectedPath() {
        return selectedPath;
    }

    /**
     * @see com.tibco.xpd.registry.ui.wizard.IImportWizardWithOperationPicker#setOperationPickerPage(com.tibco.xpd.registry.ui.wizard.OperationPickerPage)
     * 
     * @param operationPickerPage
     */
    @Override
    public final void setOperationPickerPage(
            OperationPickerPage operationPickerPage) {
        this.operationPickerPage = operationPickerPage;
    }

    /**
     * @see com.tibco.xpd.registry.ui.wizard.IImportWizardWithOperationPicker#getOperationPickerPage()
     * 
     * @return
     */
    @Override
    public final OperationPickerPage getOperationPickerPage() {
        return operationPickerPage;
    }
}
