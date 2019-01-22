/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.registry.ui.wizard;

import java.util.Collections;
import java.util.Iterator;

import javax.wsdl.BindingOperation;
import javax.wsdl.Operation;
import javax.wsdl.Port;
import javax.wsdl.PortType;
import javax.wsdl.Service;
import javax.wsdl.extensions.soap.SOAPBinding;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

import com.tibco.xpd.ui.projectexplorer.viewerfilters.SpecialFoldersOnlyFilter;
import com.tibco.xpd.ui.resources.StatusInfo;
import com.tibco.xpd.util.WsdlIndexerUtil;
import com.tibco.xpd.wsdl.WsdlServiceKey;
import com.tibco.xpd.wsdl.ui.IWsdlObjectWrapper;
import com.tibco.xpd.wsdl.ui.WsdlUIPlugin;
import com.tibco.xpd.wsdl.ui.internal.Messages;
import com.tibco.xpd.wsdl.ui.pickers.OperationPicker;

/**
 * Service operation picker. Acts same way as the OperationPicker only this is
 * based inside a wizard.
 * 
 * @see OperationPicker
 * 
 * @author glewis
 */
public class OperationPickerPage extends BaseObjectPickerPage implements
        ISelectionStatusValidator {

    /** The expected selection segment count. */
    private static final int SEGMENT_COUNT = 3;

    /** The selected service. */
    private Service service;

    /** The project to select from. */
    private IProject project;

    /** The selected port. */
    private Port port;

    /** The selected operation. */
    private Operation operation;

    /** The selected port type. */
    private PortType portType;

    /** The selected port operation. */
    private Operation portOperation = null;

    private String fileURL = null;

    /** The transport URI of selected binding */
    private String transportURI = null;

    private OperationPicker picker;

    /**
     * Navigation location used as root of selection tree
     */
    private String navigateLocation;

    /**
     * @param parent
     */
    public OperationPickerPage() {
        super();
        setTitle(Messages.OperationPicker_Dialog_title);
        setMessage(Messages.OperationPicker_Dialog_longdesc);
        addFilter(new SpecialFoldersOnlyFilter(Collections
                .singleton(WsdlUIPlugin.WSDL_SPECIALFOLDER_KIND)));
        setValidator(this);
    }

    /**
     * @param input
     *            The input project.
     * @see org.eclipse.ui.dialogs.ElementTreeSelectionDialog#setInput(java.lang.Object)
     */
    @Override
    public void setInput(Object input) {
        if (input instanceof IProject) {
            project = (IProject) input;
        }
        super.setInput(input);
    }

    public String getFileLocation() {
        return fileURL;
    }

    /**
     * @return The selected service.
     */
    public Service getService() {
        return service;
    }

    /**
     * @return The selected port.
     */
    public Port getPort() {
        return port;
    }

    /**
     * @return The selected operation.
     */
    public Operation getOperation() {
        return operation;
    }

    /**
     * @return The selected port type.
     */
    public PortType getPortType() {
        return portType;
    }

    /**
     * @return The selected port operation.
     */
    public Operation getPortOperation() {
        return portOperation;
    }

    /**
     * @return The selected service name.
     */
    public String getServiceName() {
        String serviceName = null;
        if (service != null) {
            serviceName = service.getQName().getLocalPart();
        }
        return serviceName;
    }

    /**
     * @return The selected port name.
     */
    public String getPortName() {
        String portName = null;
        if (port != null) {
            portName = port.getName();
        }
        return portName;
    }

    /**
     * @return The selected operation name.
     */
    public String getOperationName() {
        String operationName = null;
        if (operation != null) {
            operationName = operation.getName();
        }
        return operationName;
    }

    /**
     * @return The selected port type name.
     */
    public String getPortTypeName() {
        String portTypeName = null;
        if (portType != null) {
            portTypeName = portType.getQName().getLocalPart();
        }
        return portTypeName;
    }

    /**
     * @return The selected port operation name.
     */
    public String getPortOperationName() {
        String portOperationName = null;
        if (portOperation != null) {
            portOperationName = portOperation.getName();
        }
        return portOperationName;
    }

    /**
     * @return
     */
    public String getTransportURI() {
        return transportURI;
    }

    /**
     * @return The URL of the WSDL file.
     */
    public String getWsdlUrl() {
        String url = null;
        if (project != null && operation != null) {
            // TODO: If we have the file name then use that to get remote URL??
            WsdlServiceKey op =
                    new WsdlServiceKey(service.getQName().getLocalPart(), port
                            .getName(), operation.getName(), getPortTypeName(),
                            getPortOperationName(), fileURL);
            url = WsdlIndexerUtil.getWsdlUrl(project, op, true, true);
        }

        return url;
    }

    /**
     * Get the name of the project the operation has been picked from.
     * 
     * @return
     */
    public String getProjectName() {
        // String name = null;
        // if (project != null && operation != null) {
        // WsdlServiceKey op =
        // new WsdlServiceKey(service.getQName().getLocalPart(), port
        // .getName(), operation.getName(), getPortTypeName(),
        // getPortOperationName());
        //
        // IndexerItem item =
        // WsdlIndexerUtil.getOperationItem(project, op, true, true);
        // if (item != null) {
        // name = WsdlIndexerUtil.getProjectName(item);
        // }
        // }
        // return name;
        return project != null ? project.getName() : null;
    }

    /**
     * @param selection
     *            The current selection.
     * @return The selection status.
     * @see org.eclipse.ui.dialogs.ISelectionStatusValidator#
     *      validate(java.lang.Object[])
     */
    public IStatus validate(Object[] selection) {
        IStatus status =
                new StatusInfo(Status.ERROR,
                        Messages.OperationPicker_selectOperation_message);
        if (selection.length == 1) {
            Object item = selection[0];
            if (item instanceof IWsdlObjectWrapper) {
                item = ((IWsdlObjectWrapper) item).getObject();
            }
            if (item instanceof BindingOperation) {
                status = new StatusInfo();
                updateBindingOperation();
            } else if (item instanceof Operation) {
                status = new StatusInfo();
                updateOperation();
            }
        }
        return status;
    }

    /**
     * Updates the Binding Operation data.
     */
    public void updateBindingOperation() {
        portType = null;
        portOperation = null;
        service = null;
        port = null;
        operation = null;
        fileURL = null;
        ISelection selection = getTreeViewer().getSelection();
        if (selection instanceof ITreeSelection) {
            ITreeSelection ts = (ITreeSelection) selection;
            TreePath[] paths = ts.getPaths();
            if (paths.length == 1) {
                TreePath path = paths[0];
                int count = path.getSegmentCount();
                if (count >= SEGMENT_COUNT) {
                    Object s0 = path.getSegment(count - SEGMENT_COUNT);
                    Object s1 = path.getSegment(count - 2);
                    Object s2 = path.getSegment(count - 1);
                    if (s0 instanceof IWsdlObjectWrapper) {
                        s0 = ((IWsdlObjectWrapper) s0).getObject();
                    }
                    if (s1 instanceof IWsdlObjectWrapper) {
                        s1 = ((IWsdlObjectWrapper) s1).getObject();
                    }
                    if (s2 instanceof IWsdlObjectWrapper) {
                        s2 = ((IWsdlObjectWrapper) s2).getObject();
                    }
                    service = (Service) s0;
                    port = (Port) s1;
                    operation = ((BindingOperation) s2).getOperation();

                    javax.wsdl.Binding binding = port.getBinding();
                    if (binding != null) {
                        portType = binding.getPortType();
                        portOperation = operation;
                        // get transport uri
                        Iterator it =
                                binding.getExtensibilityElements().iterator();
                        while (it.hasNext()) {
                            Object obj = it.next();
                            if (obj instanceof SOAPBinding) {
                                SOAPBinding soapBinding = (SOAPBinding) obj;
                                transportURI = soapBinding.getTransportURI();
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Updates the Operation data.
     */
    public void updateOperation() {
        portType = null;
        portOperation = null;
        service = null;
        port = null;
        operation = null;
        fileURL = null;
        transportURI = null;
        ISelection selection = getTreeViewer().getSelection();
        if (selection instanceof ITreeSelection) {
            ITreeSelection ts = (ITreeSelection) selection;
            TreePath[] paths = ts.getPaths();
            if (paths.length == 1) {
                TreePath path = paths[0];
                int count = path.getSegmentCount();
                if (count >= SEGMENT_COUNT) {
                    Object s0 = path.getSegment(count - SEGMENT_COUNT);
                    Object s1 = path.getSegment(count - 2);
                    Object s2 = path.getSegment(count - 1);

                    if (s0 instanceof IFile) {
                        s0 = (IFile) s0;
                    }
                    if (s1 instanceof IWsdlObjectWrapper) {
                        s1 = ((IWsdlObjectWrapper) s1).getObject();
                    }
                    if (s2 instanceof IWsdlObjectWrapper) {
                        s2 = ((IWsdlObjectWrapper) s2).getObject();
                    }
                    fileURL = ((IFile) s0).getFullPath().toOSString();
                    portType = (PortType) s1;
                    portOperation = (Operation) s2;
                }
            }
        }
    }

    /**
     * @return
     */
    public String getNavigateLocation() {
        return navigateLocation;
    }

    /**
     * @param navigateLocation
     */
    public void setNavigateLocation(String navigateLocation) {
        this.navigateLocation = navigateLocation;
    }
}
