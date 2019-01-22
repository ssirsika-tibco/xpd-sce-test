/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.registry.ui.wizard;

import java.util.Collection;
import java.util.List;

import javax.wsdl.Definition;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.wst.wsdl.ExtensibilityElement;
import org.eclipse.wst.wsdl.WSDLPackage;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.tibco.xpd.registry.ui.internal.Messages;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.ui.resources.StatusInfo;

/**
 * Web service operation picker that allows filtering of standard/bw type wsdl
 * operations.
 * 
 * @author glewis
 * 
 */
public class WebServiceOperationPickerPage extends OperationPickerPage {

    /**
     * 
     */
    private static final String TARGET_ADDRESS = "targetAddress"; //$NON-NLS-1$

    public enum WSDL_TYPE {
        ANY, STANDARD, BW
    };

    private WSDL_TYPE wsdlType = WSDL_TYPE.ANY;

    public WebServiceOperationPickerPage(WSDL_TYPE wsdlType) {
        super();
        this.wsdlType = wsdlType;

        if (WSDL_TYPE.BW.equals(wsdlType)) {
            setMessage(Messages.WebServiceOperationPicker_SelectBWService_longdesc);
        }

        addFilter(new ViewerFilter() {
            // filtering PortTypes
            @Override
            public boolean select(Viewer viewer, Object parentElement,
                    Object element) {
                // Let super class deal with most of the filtering.
                // We'll filter out whole wsdl files depending on type of
                // service (standard/bw)
                if (!WSDL_TYPE.ANY
                        .equals(WebServiceOperationPickerPage.this.wsdlType)) {
                    if (element instanceof IFile) {

                        IFile file = (IFile) element;

                        WSDL_TYPE actualWsdlType = getWsdlFileType(file);
                        if (!actualWsdlType
                                .equals(WebServiceOperationPickerPage.this.wsdlType)) {
                            return false;
                        }
                    }
                }
                return true;
            }
        });
    }

    /**
     * Get the type of WSDL (STANDARD or BW) of the given file (if it is indeed
     * a WSDL file.
     * <p>
     * This works on the premise that BW wsdl files have ExtensibilityElement
     * with name "targetAddress" on first port in first service.
     * 
     * @param file
     * @return
     */
    private WSDL_TYPE getWsdlFileType(IFile file) {
        WSDL_TYPE actualWsdlType = WSDL_TYPE.STANDARD;

        WorkingCopy wc = XpdResourcesPlugin.getDefault().getWorkingCopy(file);
        if (wc != null) {
            EObject root = wc.getRootElement();
            if (root instanceof Definition) {
                Definition definition = (Definition) root;
                if (definition != null) {
                    Collection<?> services = definition.getServices().values();
                    if (services.size() > 0) {
                        javax.wsdl.Service service =
                                (javax.wsdl.Service) services.iterator().next();
                        Collection<?> ports = service.getPorts().values();
                        if (ports.size() > 0) {
                            org.eclipse.wst.wsdl.Port port =
                                    (org.eclipse.wst.wsdl.Port) ports
                                            .iterator().next();

                            List<ExtensibilityElement> elems =
                                    port.getEExtensibilityElements();
                            for (ExtensibilityElement elem : elems) {

                                Object eoValue =
                                        elem.eGet(WSDLPackage.eINSTANCE
                                                .getWSDLElement_Element());

                                if (eoValue instanceof Element) {
                                    Element domElement = (Element) eoValue;
                                    if (TARGET_ADDRESS.equals(domElement
                                            .getLocalName())) {
                                        if (domElement.getFirstChild() instanceof Text) {
                                            Text textContent =
                                                    (Text) domElement
                                                            .getFirstChild();

                                            if (textContent.getData().length() > 0) {
                                                actualWsdlType = WSDL_TYPE.BW;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return actualWsdlType;
    }

    @Override
    public IStatus validate(Object[] selection) {
        IStatus status = super.validate(selection);

        // Override message for BW specific if required.
        if (WSDL_TYPE.BW.equals(wsdlType)) {
            if (status.getSeverity() != Status.OK) {
                status =
                        new StatusInfo(
                                Status.ERROR,
                                Messages.WebServiceOperationPicker_SelectBWService_longdesc);
            }
        }
        return status;
    }
}
