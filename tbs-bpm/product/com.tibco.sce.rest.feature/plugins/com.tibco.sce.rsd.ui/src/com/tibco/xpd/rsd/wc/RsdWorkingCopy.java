/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */
package com.tibco.xpd.rsd.wc;

import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;

import com.tibco.xpd.resources.wc.AbstractTransactionalWorkingCopy;
import com.tibco.xpd.rsd.RsdPackage;
import com.tibco.xpd.rsd.Service;
import com.tibco.xpd.rsd.provider.RsdItemProviderAdapterFactory;
import com.tibco.xpd.rsd.ui.RsdUIPlugin;

/**
 * Working copy for the REST Service Descriptor.
 * 
 * @author jarciuch
 * @since 15 Dec 2014
 */
public class RsdWorkingCopy extends AbstractTransactionalWorkingCopy {

    /**
     * Extension for the REST Service Descriptor file.
     */
    public static final String RSD_FILE_EXTENSION = "rsd".toString(); //$NON-NLS-1$

    public RsdWorkingCopy(List<IResource> resources) {
        super(resources);
        registerResourceProvider(RsdPackage.eINSTANCE, RsdUIPlugin.getPlugin());
    }

    /** {@inheritDoc} */
    @Override
    public EPackage getWorkingCopyEPackage() {
        return RsdPackage.eINSTANCE;
    }

    /** {@inheritDoc} */
    @Override
    public AdapterFactory getAdapterFactory() {
        AdapterFactory af = super.createAdapterFactory();

        if (af instanceof ComposedAdapterFactory) {
            ((ComposedAdapterFactory) af)
                    .addAdapterFactory(new RsdItemProviderAdapterFactory());
        }

        return af;
    }

    /**
     * Returns root service element.
     * 
     * @return root service element.
     */
    public Service getService() {
        EObject rootElement = getRootElement();
        if (rootElement instanceof Service) {
            return ((Service) rootElement);
        }
        return null;
    }
}
