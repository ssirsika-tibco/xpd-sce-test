/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.wsdl.ui;

import java.text.Collator;

import javax.wsdl.PortType;
import javax.wsdl.Service;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.ViewerSorter;

/**
 * Sorter for WSDL top level elemnets.
 * <p>
 * <i>Created: 30 Jul 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class WsdlElementSorter extends ViewerSorter {

    private static final int PORT_TYPE_CATEGORY = 10;
    private static final int SERVICE_CATEGORY = 20;

    // For Eclipse resources
    private static final int CAT_CONTAINER = 1;

    private static final int CAT_FILE = 2;

    private static final int CAT_OTHER = 10;
    public WsdlElementSorter() {
    }

    public WsdlElementSorter(Collator collator) {
        super(collator);
    }

    /*
     * (non-Javadoc) Port types should be always displayed before services.
     * 
     * @see org.eclipse.jface.viewers.ViewerComparator#category(java.lang.Object)
     */
    @Override
    public int category(Object element) {
        // add sort order for eclipse resources files and folders
        int cat = CAT_OTHER;
        if (element instanceof IContainer) {
            cat = CAT_CONTAINER;
        } else if (element instanceof IFile) {
            cat = CAT_FILE;
        } else if (element instanceof IWsdlObjectWrapper) {
            IWsdlObjectWrapper wrapper = (IWsdlObjectWrapper) element;
            if (wrapper.getObject() instanceof Service) {
                return SERVICE_CATEGORY;
            } else if (wrapper.getObject() instanceof PortType) {
                return PORT_TYPE_CATEGORY;
            }
        }
        return cat;
    }
}
