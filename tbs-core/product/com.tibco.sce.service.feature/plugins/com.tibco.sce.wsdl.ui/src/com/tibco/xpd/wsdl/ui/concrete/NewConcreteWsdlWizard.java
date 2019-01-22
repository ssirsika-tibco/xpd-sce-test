/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.wsdl.ui.concrete;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.ide.IDE;
import org.eclipse.wst.wsdl.Binding;
import org.eclipse.wst.wsdl.BindingInput;
import org.eclipse.wst.wsdl.BindingOperation;
import org.eclipse.wst.wsdl.BindingOutput;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.Import;
import org.eclipse.wst.wsdl.Operation;
import org.eclipse.wst.wsdl.Port;
import org.eclipse.wst.wsdl.PortType;
import org.eclipse.wst.wsdl.Service;
import org.eclipse.wst.wsdl.WSDLFactory;
import org.eclipse.wst.wsdl.binding.soap.SOAPAddress;
import org.eclipse.wst.wsdl.binding.soap.SOAPBinding;
import org.eclipse.wst.wsdl.binding.soap.SOAPBody;
import org.eclipse.wst.wsdl.binding.soap.SOAPFactory;
import org.eclipse.wst.wsdl.binding.soap.SOAPOperation;
import org.eclipse.wst.wsdl.util.WSDLConstants;
import org.eclipse.wst.wsdl.util.WSDLResourceFactoryRegistry;
import org.w3c.dom.Document;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.wsdl.Activator;
import com.tibco.xpd.wsdl.ui.ImageCache;
import com.tibco.xpd.wsdl.ui.WsdlUIPlugin;
import com.tibco.xpd.wsdl.ui.internal.Messages;

/**
 * Wizard to create a new concrete WSDL for a given XPDL, the abstract
 * definition will be derived from the abstract created by the WSDL Gen Builder
 * 
 * @author rsomayaj
 * @since 3.3 (3 Jun 2010)
 */
public class NewConcreteWsdlWizard extends Wizard implements INewWizard {

    /**
     * 
     */
    private static final String LITERAL = "literal"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String WSDL_SEL_PAGE = "wsdlSelPage"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String SOAP_NAMESPACE_URI =
            "http://schemas.xmlsoap.org/wsdl/soap/"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String SCHEMA_TRANSPORT =
            "http://schemas.xmlsoap.org/soap/http"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String SOAP_SUFFIX = "SOAP"; //$NON-NLS-1$

    /**
     * 
     */
    public static final String WSDL_FILE_EXTN =
            WsdlUIPlugin.WSDL_FILE_EXTENSION;

    /**
     * 
     */
    private static final String RPC = "rpc"; //$NON-NLS-1$

    private WSDLSelectionPage wsdlSelectionPage;

    /**
     * 
     */
    private static final String WSDL = "wsdl";//$NON-NLS-1$

    /**
     * 
     */
    private static final String SOAP = "soap";//$NON-NLS-1$

    /**
     * 
     */
    private static final String NS = "ns";//$NON-NLS-1$

    /**
     * 
     * 
     */
    private static final String XSD = "xsd";//$NON-NLS-1$

    /**
     * 
     */

    private static final Logger LOG = XpdResourcesPlugin.getDefault()
            .getLogger();

    private IWorkbench workbench;

    /**
     * 
     */
    public NewConcreteWsdlWizard() {
        this.setWindowTitle(Messages.NewConcreteWsdlWizard_WindowTitle_label);
        this.setDefaultPageImageDescriptor(WsdlUIPlugin
                .imageDescriptorFromPlugin(WsdlUIPlugin.PLUGIN_ID,
                        ImageCache.WSDL_WIZ));
        setNeedsProgressMonitor(true);
    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     * 
     * @return
     */
    @Override
    public boolean performFinish() {

        try {
            final IFile[] concreteWsdlFile = new IFile[] { null };
            final IPath concreteWsdlPath =
                    wsdlSelectionPage.getConcreteWsdlPath();
            final IFile abstractWSDLFile =
                    wsdlSelectionPage.getAbstractWsdlFile();
            final String soapAddress = wsdlSelectionPage.getSoapAddress();
            final String bindingStyle = wsdlSelectionPage.getBindingStyle();

            getContainer().run(true, false, new WorkspaceModifyOperation() {
                @Override
                protected void execute(IProgressMonitor monitor)
                        throws CoreException, InvocationTargetException,
                        InterruptedException {

                    concreteWsdlFile[0] =
                            createConcreteWsdlFile(concreteWsdlPath,
                                    abstractWSDLFile,
                                    soapAddress,
                                    bindingStyle,
                                    monitor);
                }
            });

            /*
             * Fire off an async to load the editor so that this wizard is not
             * held up while the editor is loading.
             */
            if (concreteWsdlFile[0] != null) {
                getShell().getDisplay().asyncExec(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            IDE.openEditor(workbench.getActiveWorkbenchWindow()
                                    .getActivePage(), concreteWsdlFile[0]);
                        } catch (PartInitException e) {
                            LOG.error(e);
                        }
                    }
                });
            }

        } catch (Exception e) {
            LOG.error(e);
        }

        return true;
    }

    /**
     * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
     *      org.eclipse.jface.viewers.IStructuredSelection)
     * 
     * @param workbench
     * @param selection
     */
    @Override
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        this.workbench = workbench;
        this.wsdlSelectionPage = new WSDLSelectionPage(WSDL_SEL_PAGE);
        this.wsdlSelectionPage.init(selection);
    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#addPages()
     * 
     */
    @Override
    public void addPages() {
        addPage(wsdlSelectionPage);
    }

    /**
     * Create the concrete WSDL file.
     * 
     * @param concreteWsdlPath
     * @param abstractWSDLFile
     * @param soapAddress
     * @param bindingStyle
     * @param monitor
     * @return
     */
    private IFile createConcreteWsdlFile(IPath concreteWsdlPath,
            IFile abstractWSDLFile, String soapAddress, String bindingStyle,
            IProgressMonitor monitor) {

        // Need to set the task name separately as otherwise it may not update
        // the monitor message
        monitor.beginTask(Messages.NewConcreteWsdlWizard_CreatingConcreteWsdl_monitor_shortdesc,
                6);
        monitor.setTaskName(Messages.NewConcreteWsdlWizard_CreatingConcreteWsdl_monitor_shortdesc);

        Definition concreteDefinition =
                WSDLFactory.eINSTANCE.createDefinition();
        Definition abstractDefinition = null;

        if (abstractWSDLFile != null && abstractWSDLFile.isAccessible()) {
            Definition importedDefn = null;
            WorkingCopy importedWSDLWC =
                    WorkingCopyUtil.getWorkingCopy(abstractWSDLFile);
            if (importedWSDLWC != null
                    && importedWSDLWC.getRootElement() instanceof Definition) {

                monitor.worked(1);

                abstractDefinition =
                        (Definition) importedWSDLWC.getRootElement();

                concreteDefinition.setDocument(createDOMDocument());
                ResourceSet rset = new ResourceSetImpl();
                rset.setResourceFactoryRegistry(new WSDLResourceFactoryRegistry(
                        Resource.Factory.Registry.INSTANCE));
                Resource wsdlRes =
                        rset.createResource(URI.createFileURI(concreteWsdlPath
                                .toString()));
                wsdlRes.getContents().add(concreteDefinition);
                setNamespaces(abstractDefinition,
                        concreteDefinition,
                        abstractWSDLFile);

                monitor.worked(1);

                if (importedWSDLWC != null) {
                    EObject importedWSDLDefnObj =
                            importedWSDLWC.getRootElement();
                    if (importedWSDLDefnObj instanceof Definition) {
                        importedDefn = (Definition) importedWSDLDefnObj;

                        // Setup the wsdl import
                        Import wsdlImport =
                                WSDLFactory.eINSTANCE.createImport();
                        IFile newFile =
                                ResourcesPlugin.getWorkspace().getRoot()
                                        .getFile(concreteWsdlPath);
                        URI uriFilePath =
                                URI.createURI(newFile.getLocationURI()
                                        .toString(), true);
                        URI uriWsdlFileImported =
                                URI.createURI(abstractWSDLFile.getLocationURI()
                                        .toString(), true);

                        monitor.worked(1);

                        URI deresolve =
                                uriWsdlFileImported.deresolve(uriFilePath);
                        wsdlImport.setLocationURI(deresolve.toString());
                        wsdlImport.setNamespaceURI(importedDefn
                                .getTargetNamespace());
                        concreteDefinition.getEImports().add(wsdlImport);
                        addConcreteOperations(concreteDefinition,
                                importedDefn,
                                soapAddress,
                                bindingStyle);

                        monitor.worked(1);

                        try {
                            wsdlRes.save(Collections.EMPTY_MAP);

                            // definition.getElement returns null before the
                            // resource is saved.
                            addDoNotGenerateFlag(abstractDefinition,
                                    concreteDefinition);

                            addConcreteFlag(concreteDefinition);

                            wsdlRes.save(Collections.EMPTY_MAP);
                        } catch (IOException e) {
                            LOG.error(e);
                        }

                        monitor.worked(1);
                    }
                }
            }
        }

        monitor.done();

        IFile concreteWsdlFile =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getFile(concreteWsdlPath);
        if (concreteWsdlFile != null && concreteWsdlFile.isAccessible()) {
            return concreteWsdlFile;
        }
        return null;
    }

    /**
     * @param definition
     * @param xpdlFile
     */
    private void addConcreteFlag(Definition definition) {
        definition.getElement().setAttribute(WsdlUIPlugin.TIBEX_CONCRETE_FLAG,
                Boolean.TRUE.toString());
    }

    /**
     * @param definition
     * @param xpdlFile
     */
    private void addDoNotGenerateFlag(Definition abstractDefinition,
            Definition concreteDefinition) {

        String tibxPlcHolderVal =
                abstractDefinition.getElement()
                        .getAttribute(WsdlUIPlugin.TIBEX_XPDL_PLACEHOLDER);
        if (tibxPlcHolderVal != null) {
            concreteDefinition.getElement()
                    .setAttribute(WsdlUIPlugin.TIBEX_XPDL_PLACEHOLDER,
                            tibxPlcHolderVal);
        }

        String tibxServiceTaskPlcHolderVal =
                abstractDefinition.getElement()
                        .getAttribute(WsdlUIPlugin.TIBEX_SERVICE_TASK);
        if (tibxServiceTaskPlcHolderVal != null
                && tibxServiceTaskPlcHolderVal.length() > 0) {
            concreteDefinition.getElement()
                    .setAttribute(WsdlUIPlugin.TIBEX_SERVICE_TASK,
                            tibxServiceTaskPlcHolderVal);
        }
    }

    /**
     * @param concreteDefinition
     * @param abstractDefinition
     * @param soapAddressUri
     * @param bindingStyle
     * 
     * @return service.
     */
    @SuppressWarnings("unchecked")
    public static Service addConcreteOperations(Definition concreteDefinition,
            Definition abstractDefinition, String soapAddressUri,
            String bindingStyle) {

        if (concreteDefinition != null && abstractDefinition != null
                && soapAddressUri != null && bindingStyle != null) {
            List portTypes = abstractDefinition.getEPortTypes();

            Service wsdlService = WSDLFactory.eINSTANCE.createService();
            wsdlService.setQName(new QName(concreteDefinition
                    .getTargetNamespace(), concreteDefinition.getQName()
                    .getLocalPart()));
            concreteDefinition.getEServices().add(wsdlService);
            for (Object object : portTypes) {
                if (object instanceof PortType) {
                    PortType portType = (PortType) object;
                    Binding wsdlBinding = WSDLFactory.eINSTANCE.createBinding();
                    String portTypeName = portType.getQName().getLocalPart();
                    wsdlBinding.setEPortType(portType);
                    wsdlBinding.setQName(new QName(concreteDefinition
                            .getTargetNamespace(), portTypeName + SOAP_SUFFIX));
                    concreteDefinition.getEBindings().add(wsdlBinding);
                    SOAPBinding soapBinding =
                            SOAPFactory.eINSTANCE.createSOAPBinding();
                    soapBinding.setStyle(bindingStyle);
                    soapBinding.setTransportURI(SCHEMA_TRANSPORT);
                    wsdlBinding.getEExtensibilityElements().add(soapBinding);

                    List operations = portType.getEOperations();

                    for (Object opObj : operations) {
                        if (opObj instanceof Operation) {
                            Operation op = (Operation) opObj;

                            BindingOperation bindingOperation =
                                    WSDLFactory.eINSTANCE
                                            .createBindingOperation();
                            bindingOperation.setName(op.getName());
                            SOAPOperation soapOperation =
                                    SOAPFactory.eINSTANCE.createSOAPOperation();
                            String soapActionURI =
                                    String.format("%1$s/%2$s/%3$s", //$NON-NLS-1$
                                            soapAddressUri,
                                            concreteDefinition.getQName()
                                                    .getLocalPart(),
                                            op.getName());
                            soapOperation.setSoapActionURI(soapActionURI);

                            bindingOperation
                                    .addExtensibilityElement(soapOperation);

                            // Set the Binding Input
                            BindingInput bindingInput =
                                    WSDLFactory.eINSTANCE.createBindingInput();
                            SOAPBody soapBodyForInput =
                                    SOAPFactory.eINSTANCE.createSOAPBody();

                            soapBodyForInput.setUse(LITERAL);
                            //                        String soapBodyInNsUri = String.format("%1$s%2$s", //$NON-NLS-1$
                            // soapAddressUri,
                            // concreteDefinition.getQName().getLocalPart());
                            // soapBodyForInput.setNamespaceURI(soapBodyInNsUri);

                            bindingInput
                                    .addExtensibilityElement(soapBodyForInput);

                            bindingOperation.setBindingInput(bindingInput);

                            // Set the Binding Output
                            if (op.getEOutput() != null) {
                                SOAPBody soapBodyForOutput =
                                        SOAPFactory.eINSTANCE.createSOAPBody();

                                soapBodyForOutput.setUse(LITERAL);
                                // soapBodyForOutput.setNamespaceURI(soapBodyInNsUri);
                                BindingOutput bindingOutput =
                                        WSDLFactory.eINSTANCE
                                                .createBindingOutput();
                                bindingOutput
                                        .addExtensibilityElement(soapBodyForOutput);

                                bindingOperation
                                        .setBindingOutput(bindingOutput);
                            }
                            wsdlBinding.getEBindingOperations()
                                    .add(bindingOperation);
                        }
                    }

                    Port port = WSDLFactory.eINSTANCE.createPort();
                    port.setName(portTypeName);
                    SOAPAddress soapAddress =
                            SOAPFactory.eINSTANCE.createSOAPAddress();
                    soapAddress.setLocationURI(soapAddressUri);
                    port.getEExtensibilityElements().add(soapAddress);

                    port.setBinding(wsdlBinding);
                    wsdlService.getEPorts().add(port);
                }
            }
            return wsdlService;
        }
        return null;
    }

    /**
     * @param definition
     * @param concreteDefinition
     * @param abstractWSDLFile
     * @param xpdlFile
     * @param id
     * @param name
     */
    private void setNamespaces(Definition definition,
            Definition concreteDefinition, IFile abstractWSDLFile) {
        concreteDefinition.setTargetNamespace(definition.getTargetNamespace());
        concreteDefinition.addNamespace(XSD, WSDLConstants.XSD_NAMESPACE_URI);
        concreteDefinition.addNamespace(NS, definition.getTargetNamespace());
        concreteDefinition.addNamespace(WSDL, WSDLConstants.WSDL_NAMESPACE_URI);
        concreteDefinition.addNamespace(SOAP, SOAP_NAMESPACE_URI);
        concreteDefinition.addNamespace(Activator.TIBEX, Activator.TIBEX_URI);

        String localPart = ""; //$NON-NLS-1$

        /*
         * If no name set then use the file name as the local part of the QName
         */
        if (definition.getQName() != null) {
            localPart = definition.getQName().getLocalPart();
        } else {
            IPath path =
                    new Path(abstractWSDLFile.getName()).removeFileExtension();

            localPart = path.toString().replaceAll("\\W*", ""); //$NON-NLS-1$ //$NON-NLS-2$
        }

        concreteDefinition.setQName(new QName(definition.getTargetNamespace(),
                localPart));

    }

    /**
     * @return
     */
    private Document createDOMDocument() {
        Document document = null;
        try {
            document =
                    DocumentBuilderFactory.newInstance().newDocumentBuilder()
                            .newDocument();
        } catch (ParserConfigurationException e1) {
            LOG.error(e1);
        }
        return document;

    }

}