/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.wsdl.ui;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.graphics.Image;

import javax.wsdl.BindingOperation;
import javax.wsdl.Fault;
import javax.wsdl.Operation;
import javax.wsdl.Port;
import javax.wsdl.PortType;
import javax.wsdl.Service;

import com.tibco.xpd.ui.projectexplorer.providers.ProjectExplorerLabelProvider;

/**
 * Project Explorer label provider for the <b>WSDL Content</b>.
 * 
 * @author njpatel
 */
public class WsdlLabelProvider extends ProjectExplorerLabelProvider {

    /** The image cache. */
    private final ImageCache cache;

    /**
     * Constructor.
     */
    public WsdlLabelProvider() {
        // Set up image cache
        cache = WsdlUIPlugin.getDefault().getImageCache();
    }

    /**
     * @param element
     *            The element to get the image for.
     * @return The image.
     * @see com.tibco.xpd.ui.projectexplorer.providers.ProjectExplorerLabelProvider#
     *      getImage(java.lang.Object)
     */
    @Override
    public Image getImage(Object element) {
        Image image = null;

        if (element instanceof IStructuredSelection) {
            IStructuredSelection selection = (IStructuredSelection) element;
            if (selection.size() == 1) {
                element = selection.getFirstElement();
            }
        }
        if (element instanceof IWsdlObjectWrapper) {
            element = ((IWsdlObjectWrapper) element).getObject();
        }

        if (element instanceof IFile
                && "wsdl".equalsIgnoreCase(((IFile) element).getFileExtension())) { //$NON-NLS-1$
            image = cache.getImage(ImageCache.FILE);
        } else if (element instanceof Service) {
            image = cache.getImage(ImageCache.SERVICE);
        } else if (element instanceof Port) {
            image = cache.getImage(ImageCache.PORT);
        } else if (element instanceof PortType) {
            image = cache.getImage(ImageCache.PORT_TYPE);
        } else if (element instanceof Operation) {
            image = cache.getImage(ImageCache.OPERATION);
        } else if (element instanceof BindingOperation) {
            image = cache.getImage(ImageCache.OPERATION_BINDING);
        } else if (element instanceof Fault) {
            image = cache.getImage(ImageCache.FAULT);
        }

        return image != null ? image : super.getImage(element);
    }

    /**
     * @param element
     *            The element to get the text for.
     * @return The text.
     * @see com.tibco.xpd.ui.projectexplorer.providers.ProjectExplorerLabelProvider#
     *      getText(java.lang.Object)
     */
    @Override
    public String getText(Object element) {
        if (element instanceof IWsdlObjectWrapper) {
            return ((IWsdlObjectWrapper) element).getWsdlObjectLocalName();
        }

        if (element instanceof Service) {
            return ((Service)element).getQName().getLocalPart();
        } else if (element instanceof Port) {
            return ((Port)element).getName();
        } else if (element instanceof PortType) {
            return ((PortType)element).getQName().getLocalPart();
        } else if (element instanceof Operation) {
            return ((Operation)element).getName();
        } else if (element instanceof BindingOperation) {
            return ((BindingOperation)element).getName();
        } else if (element instanceof Fault) {
            return ((Fault)element).getName();
        }

        return super.getText(element);
    }

    /*
     * @see com.tibco.xpd.ui.projectexplorer.providers.ProjectExplorerLabelProvider#getDescription(java.lang.Object)
     */
    @Override
    public String getDescription(Object element) {
        if (element instanceof IWsdlObjectWrapper) {
            IWsdlObjectWrapper w = ((IWsdlObjectWrapper) element);
            return new StringBuilder().append(w.getWsdlTypeName()).append(' ')
                    .append('-').append(' ').append(w.getWsdlObjectLocalName())
                    .toString();
        }
        return super.getDescription(element);
    }

}
