package com.tibco.example.workingcopy;

/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;

import com.tibco.example.model.DocumentRoot;
import com.tibco.example.model.MainElementType;
import com.tibco.example.model.ModelPackage;
import com.tibco.example.model.provider.ModelItemProviderAdapterFactory;
import com.tibco.xpd.resources.wc.AbstractTransactionalWorkingCopy;

/**
 * Example working copy for com.tibco.example.model Ecore Model.
 * <p>
 * This is utilised via the {@link ExampleWorkingCopyFactory}
 * 
 * @author aallway
 * @since 17 Feb 2015
 */
public class ExampleWorkingCopy extends AbstractTransactionalWorkingCopy {

    /**
     * @param resources
     */
    public ExampleWorkingCopy(List<IResource> resources) {
        super(resources);
    }

    /**
     * The true root element of our EMF model is the DocumentRoot (but that's
     * just a wrapper for a real root element that we serialise, MainElement)
     * 
     * @return The real root element of the model.
     */
    public MainElementType getMainElement() {
        EObject rootElement = getRootElement();
        if (rootElement instanceof DocumentRoot) {
            return ((DocumentRoot) rootElement).getMainElement();
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.resources.WorkingCopy#getWorkingCopyEPackage()
     * 
     * @return
     */
    @Override
    public EPackage getWorkingCopyEPackage() {
        return ModelPackage.eINSTANCE;
    }

    /**
     * @see com.tibco.xpd.resources.wc.AbstractTransactionalWorkingCopy#createAdapterFactory()
     * 
     * @return
     */
    @Override
    protected AdapterFactory createAdapterFactory() {
        AdapterFactory af = super.createAdapterFactory();

        if (af instanceof ComposedAdapterFactory) {
            ((ComposedAdapterFactory) af)
                    .addAdapterFactory(new ModelItemProviderAdapterFactory());
        }

        return af;
    }
}
