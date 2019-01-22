/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.wizards.srvctask;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.Service;
import org.eclipse.wst.wsdl.WSDLFactory;
import org.eclipse.wst.wsdl.util.WSDLConstants;
import org.eclipse.wst.wsdl.util.WSDLResourceFactoryRegistry;
import org.w3c.dom.Document;

import com.tibco.xpd.implementer.resources.xpdl2.Activator;
import com.tibco.xpd.implementer.resources.xpdl2.ImageConstants;
import com.tibco.xpd.implementer.resources.xpdl2.internal.Messages;
import com.tibco.xpd.implementer.resources.xpdl2.wizards.srvctask.BindingTypeWizardPage.WSDLType;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.wsdl.ui.WsdlUIPlugin;
import com.tibco.xpd.wsdl.ui.concrete.NewConcreteWsdlWizard;
import com.tibco.xpd.wsdlgen.transform.XtendTransformerXpdl2Wsdl;
import com.tibco.xpd.xpdExtension.SoapBindingStyle;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;

/**
 * Wizard to create a WSDL for a Service task in the process. WSDL is created
 * using the Service task name, process name in which the service task belongs.
 * WSDL has parts equivalent to datafields and process formal parameters.
 * 
 * This is extended to use for One way requests of Service tasks, Intermediate
 * throw message events, End Message events.
 * 
 * @author rsomayaj
 * 
 */
public class ServiceTaskWsdlCreationWizard extends Wizard {

    private static final Logger LOG = XpdResourcesPlugin.getDefault()
            .getLogger();

    private final Package xpdlPackage;

    private final IProject project;

    private final Activity serviceTaskActivity;

    private ServiceTaskWsdlCreationWizardPage serviceTaskWsdlCreationWizardPage;

    private String fileName;

    /**
     * 
     */
    private static final String WSDL =
            com.tibco.xpd.wsdl.Activator.WSDL_FILE_EXTENSION;

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
    private static final String TARGETNS_PREFIX = "http://www.tibco.com/bs3.0/"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String XSD_NAMESPACE = WSDLConstants.XSD_NAMESPACE_URI;

    private BindingTypeWizardPage bindingTypeWizardPage;

    private Service service;

    /**
     * 
     */
    public ServiceTaskWsdlCreationWizard(
            com.tibco.xpd.xpdl2.Package xpdlPackage, IProject project,
            Activity serviceTaskActivity) {
        setWindowTitle(Messages.ServiceTaskWsdlCreationWizard_WindowTitle_label);
        setNeedsProgressMonitor(true);

        setDefaultPageImageDescriptor(Activator
                .getImageDescriptor(ImageConstants.WSDL_WIZARD));
        this.xpdlPackage = xpdlPackage;
        this.project = project;
        this.serviceTaskActivity = serviceTaskActivity;
    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     * 
     * @return
     */
    @Override
    public boolean performFinish() {
        final IPath filePath = serviceTaskWsdlCreationWizardPage.getFilePath();
        final String wsdlTargetNamespace =
                bindingTypeWizardPage.getWsdlNamespace();
        final SoapBindingStyle bindingStyle =
                bindingTypeWizardPage.getBindingType();
        final WSDLType wsdlType = bindingTypeWizardPage.getWsdlType();
        final String soapAddress = bindingTypeWizardPage.getSOAPAddress();

        fileName = serviceTaskWsdlCreationWizardPage.getFileName();

        try {
            getContainer().run(true, false, new WorkspaceModifyOperation() {
                @Override
                protected void execute(IProgressMonitor monitor)
                        throws CoreException, InvocationTargetException,
                        InterruptedException {

                    createWsdlFile(filePath,
                            wsdlTargetNamespace,
                            bindingStyle,
                            wsdlType,
                            soapAddress,
                            monitor);
                }
            });
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            ErrorDialog
                    .openError(getShell(),
                            Messages.ServiceTaskWsdlCreationWizard_errorDuringWsdlCreation_errorDlg_title,
                            Messages.ServiceTaskWsdlCreationWizard_errorDuringWsdlCreation_errorDlg_message,
                            new Status(IStatus.ERROR, Activator.PLUGIN_ID, e
                                    .getLocalizedMessage(), e.getCause()));
        } catch (InterruptedException e) {
            Activator
                    .getDefault()
                    .getLogger()
                    .error(e,
                            "Error reported during wsdl creation from a Service step."); //$NON-NLS-1$
        }

        return true;
    }

    public String getWSDLFileName() {
        return fileName;
    }

    /**
     * If a concrete WSDL was created this will return the {@link Service}.
     * 
     * @return Service if concrete wsdl, <code>null</code> otherwise.
     */
    public Service getService() {
        return service;
    }

    /**
     * 
     */
    private void createWsdlFile(IPath filePath, String wsdlTargetNamespace,
            SoapBindingStyle bindingStyle, WSDLType wsdlType,
            String soapAddress, IProgressMonitor monitor) {

        // Need to set task name too as the label doesn't get updated otherwise
        monitor.beginTask(Messages.ServiceTaskWsdlCreationWizard_creatingWsdl_monitor_shortdesc,
                7);
        monitor.setTaskName(Messages.ServiceTaskWsdlCreationWizard_creatingWsdl_monitor_shortdesc);
        Definition definition = WSDLFactory.eINSTANCE.createDefinition();
        String xpdlPkgName = xpdlPackage.getName();
        /*
         * XPD-5911: if a xpdl package name has leading digit(s) then prefix
         * with underscore
         */
        if (xpdlPkgName != null && Character.isDigit(xpdlPkgName.charAt(0))) {

            xpdlPkgName = "_" + xpdlPkgName; //$NON-NLS-1$
        }

        setNamespaces(definition,
                xpdlPackage.getId(),
                xpdlPkgName,
                wsdlTargetNamespace);

        monitor.worked(1);

        definition.setDocument(createDOMDocument());
        ResourceSet rset = new ResourceSetImpl();
        rset.setResourceFactoryRegistry(new WSDLResourceFactoryRegistry(
                Resource.Factory.Registry.INSTANCE));
        Resource wsdlRes =
                rset.createResource(URI.createPlatformResourceURI(filePath
                        .toString(), true));
        wsdlRes.getContents().add(definition);

        monitor.worked(1);

        // Create the underlying DOM element.
        definition.updateElement(true);

        monitor.worked(1);

        if (definition.getElement() != null) {
            setDefinitionAttributes(definition);
        }

        monitor.worked(1);

        new XtendTransformerXpdl2Wsdl().transformServiceTaskToWsdl(definition,
                serviceTaskActivity,
                bindingStyle);

        monitor.worked(1);

        if (wsdlType == WSDLType.CONCRETE) {
            service =
                    NewConcreteWsdlWizard
                            .addConcreteOperations(definition,
                                    definition,
                                    soapAddress,
                                    bindingStyle == SoapBindingStyle.RPC_LITERAL ? "rpc" //$NON-NLS-1$
                                            : "document"); //$NON-NLS-1$
        }

        monitor.worked(1);

        // Re-save to apply the concrete changes
        try {
            wsdlRes.save(Collections.EMPTY_MAP);
        } catch (IOException e) {
            LOG.error(e);
        }

        monitor.done();
    }

    /**
     * These attributes determines whether a BOM needs to be generated for this
     * WSDL
     * 
     * @param definition
     */
    private void setDefinitionAttributes(Definition definition) {
        definition.getElement()
                .setAttribute(WsdlUIPlugin.TIBEX_XPDL_PLACEHOLDER,
                        getXPDLFileName(xpdlPackage));
        definition.getElement().setAttribute(WsdlUIPlugin.TIBEX_PROCESS,
                serviceTaskActivity.getProcess().getId());
        definition.getElement().setAttribute(WsdlUIPlugin.TIBEX_SERVICE_TASK,
                serviceTaskActivity.getId());
        definition.getElement()
                .setAttribute(WsdlUIPlugin.TIBEX_XPDL_PLACEHOLDER,
                        getXPDLFileName(xpdlPackage));
        try {
            definition.eResource().save(Collections.EMPTY_MAP);
        } catch (IOException e) {
            LOG.error("Cannot save after adding ext attributes"); //$NON-NLS-1$
        }
    }

    private String getXPDLFileName(Package xpdlPackage) {
        if (xpdlPackage != null) {
            IFile xpdlFile = WorkingCopyUtil.getFile(xpdlPackage);
            if (xpdlFile != null) {
                return xpdlFile.getName();
            }
        }
        return null;
    }

    private Document createDOMDocument() {
        Document document = null;
        try {
            document =
                    DocumentBuilderFactory.newInstance().newDocumentBuilder()
                            .newDocument();
        } catch (ParserConfigurationException e1) {
            e1.printStackTrace();
        }
        return document;

    }

    /**
     * @param definition
     * @param id
     * @param name
     */
    private void setNamespaces(Definition definition, String id, String name,
            String wsdlTargetNamespace) {
        definition.setTargetNamespace(wsdlTargetNamespace);
        // End of change
        definition.addNamespace(XSD, XSD_NAMESPACE);
        definition.addNamespace(NS, wsdlTargetNamespace);
        definition.addNamespace(WSDL, WSDLConstants.WSDL_NAMESPACE_URI);
        definition.setQName(new QName(TARGETNS_PREFIX + id, name));
        definition.addNamespace(com.tibco.xpd.wsdl.Activator.TIBEX,
                com.tibco.xpd.wsdl.Activator.TIBEX_URI);
    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#addPages()
     * 
     */
    @Override
    public void addPages() {
        String processName = serviceTaskActivity.getProcess().getName();
        IStructuredSelection sel = getInitialSelection();
        serviceTaskWsdlCreationWizardPage =
                new ServiceTaskWsdlCreationWizardPage(
                        Messages.ServiceTaskWsdlCreationWizard_PageName_label,
                        sel,
                        com.tibco.xpd.wsdl.Activator.WSDL_FILE_EXTENSION,
                        String.format("%1$s-%2$s", processName, serviceTaskActivity.getName())); //$NON-NLS-1$
        addPage(serviceTaskWsdlCreationWizardPage);
        bindingTypeWizardPage =
                new BindingTypeWizardPage(
                        "WsdlBindingTypeWizardPage", //$NON-NLS-1$
                        Messages.ServiceTaskWsdlCreationWizard_bindingTypeWsdlPage_shortdesc,
                        serviceTaskActivity);
        addPage(bindingTypeWizardPage);
    }

    /**
     * @return
     */
    private IStructuredSelection getInitialSelection() {
        ProjectConfig projectConfig =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);
        List<IFolder> wsdlEclipseFolders =
                projectConfig
                        .getSpecialFolders()
                        .getEclipseIFoldersOfKind(com.tibco.xpd.wsdl.Activator.WSDL_SPECIALFOLDER_KIND);
        IStructuredSelection sel = null;
        if (wsdlEclipseFolders != null && !wsdlEclipseFolders.isEmpty()) {
            IFolder folder = wsdlEclipseFolders.get(0);
            SpecialFolder specialFolder =
                    projectConfig.getSpecialFolders().getFolder(folder);
            sel = new StructuredSelection(specialFolder);
        } else {
            sel = new StructuredSelection(project);
        }
        return sel;
    }
}
