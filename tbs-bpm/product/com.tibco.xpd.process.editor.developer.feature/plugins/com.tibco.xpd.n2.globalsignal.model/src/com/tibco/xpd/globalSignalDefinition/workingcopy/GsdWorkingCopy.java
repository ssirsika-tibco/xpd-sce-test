/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.globalSignalDefinition.workingcopy;

import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;

import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitionPackage;
import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions;
import com.tibco.xpd.globalSignalDefinition.provider.GlobalSignalDefinitionItemProviderAdapterFactory;
import com.tibco.xpd.resources.wc.AbstractTransactionalWorkingCopy;

/**
 * Working copy for Global Signal Definition.
 * 
 * @author sajain
 * @since Jan 28, 2015
 */
public class GsdWorkingCopy extends
        AbstractTransactionalWorkingCopy {

    /**
     * @param resources
     */
    public GsdWorkingCopy(List<IResource> resources) {
        super(resources);
    }

    /**
     * @see com.tibco.xpd.resources.WorkingCopy#getWorkingCopyEPackage()
     * 
     * @return
     */
    @Override
    public EPackage getWorkingCopyEPackage() {
        return GlobalSignalDefinitionPackage.eINSTANCE;
    }

    /**
     * Reverts the WorkingCopy Model changes and fires WorkingCopy Dirty Flag
     * changed notification.
     */
    public void revertModelChanges() {
        /*
         * Reload WorkingCopy
         */
        super.reLoad();
        /*
         * The notification is required to refresh the Project Explorer's dirty
         * flag
         */
        fireWCDirtyFlagChanged();
    }

    /**
     * 
     * @return {@link GlobalSignalDefinitions} root model entry, returns null if
     *         the model is empty.
     */
    public GlobalSignalDefinitions getGlobalSignalDefinitions() {
        EObject rootElement = getRootElement();
        if (rootElement instanceof GlobalSignalDefinitions) {
            return ((GlobalSignalDefinitions) rootElement);
        }
        return null;
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
                    .addAdapterFactory(new GlobalSignalDefinitionItemProviderAdapterFactory());
        }

        return af;
    }

}
