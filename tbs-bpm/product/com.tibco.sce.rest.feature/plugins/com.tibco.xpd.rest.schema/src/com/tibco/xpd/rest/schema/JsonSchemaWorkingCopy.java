/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.schema;

import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.edit.providers.UMLItemProviderAdapterFactory;

import com.tibco.xpd.resources.wc.AbstractTransactionalWorkingCopy;

/**
 * Working copy implementation for the JSON Schema file. This uses the UML EMF
 * model.
 * 
 * @author nwilson
 * @since 16 Jan 2015
 */
public class JsonSchemaWorkingCopy extends AbstractTransactionalWorkingCopy {

    /**
     * File extension.
     */
    public static final String JSD_FILE_EXTENSION = "jsd"; //$NON-NLS-1$

    /**
     * @param resources
     *            The JSON schema resource file.
     */
    public JsonSchemaWorkingCopy(List<IResource> resources) {
        super(resources);
    }

    /**
     * @see com.tibco.xpd.resources.WorkingCopy#getWorkingCopyEPackage()
     * 
     * @return The UML Package instance.
     */
    @Override
    public EPackage getWorkingCopyEPackage() {
        return UMLPackage.eINSTANCE;
    }

    /**
     * @see com.tibco.xpd.resources.wc.AbstractTransactionalWorkingCopy
     *      #createAdapterFactory()
     * 
     * @return The JSON Schema adapter factory.
     */
    @Override
    protected AdapterFactory createAdapterFactory() {
        AdapterFactory af = super.createAdapterFactory();

        if (af instanceof ComposedAdapterFactory) {
            ((ComposedAdapterFactory) af)
                    .addAdapterFactory(new UMLItemProviderAdapterFactory());
        }

        return af;
    }

}
