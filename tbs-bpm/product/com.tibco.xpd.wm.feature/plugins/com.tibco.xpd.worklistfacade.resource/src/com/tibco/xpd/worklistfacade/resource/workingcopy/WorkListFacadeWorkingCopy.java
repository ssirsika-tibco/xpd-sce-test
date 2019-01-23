/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.worklistfacade.resource.workingcopy;

import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;

import com.tibco.xpd.resources.wc.AbstractTransactionalWorkingCopy;
import com.tibco.xpd.worklistfacade.model.DocumentRoot;
import com.tibco.xpd.worklistfacade.model.WorkListFacade;
import com.tibco.xpd.worklistfacade.model.WorkListFacadePackage;
import com.tibco.xpd.worklistfacade.provider.WorkListFacadeItemProviderAdapterFactory;

/**
 * Working Copy for the Work List Facade.
 * 
 * @author aprasad
 * @since 27-Sep-2013
 */
public class WorkListFacadeWorkingCopy extends AbstractTransactionalWorkingCopy {

    /**
     * @param resources
     * @param ePackage
     */
    public WorkListFacadeWorkingCopy(List<IResource> resources) {
        super(resources);
    }

    @Override
    public EPackage getWorkingCopyEPackage() {
        return WorkListFacadePackage.eINSTANCE;
    }

    /**
     * Reverts the WorkingCopy Model changes and fires WorkingCopy Dirty Flag
     * changed notification.
     */
    public void revertModelChanges() {
        // Reload WorkingCopy
        super.reLoad();
        // The notification is required to refresh the Project Explorer's dirty
        // flag
        fireWCDirtyFlagChanged();
    }

    /**
     * 
     * @return {@link WorkListFacadeType} root model entry, returns null if the
     *         model is empty.
     */
    public WorkListFacade getWorkListFacade() {
        EObject rootElement = getRootElement();
        if (rootElement instanceof DocumentRoot) {
            return ((DocumentRoot) rootElement).getWorkListFacade();
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
                    .addAdapterFactory(new WorkListFacadeItemProviderAdapterFactory());
        }

        return af;
    }
}
