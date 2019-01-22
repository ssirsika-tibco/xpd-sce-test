/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.worklistfacade.resource.validation;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.validation.destinations.Destination;
import com.tibco.xpd.validation.provider.IScopeProvider;
import com.tibco.xpd.validation.provider.IValidationItem;
import com.tibco.xpd.worklistfacade.model.WorkItemAttribute;
import com.tibco.xpd.worklistfacade.model.WorkListFacade;
import com.tibco.xpd.worklistfacade.resource.workingcopy.WorkListFacadeWorkingCopy;

/**
 * Validation Scope provider for Work List Facade Resource.
 * 
 * @author aprasad
 * @since 15-Nov-2013
 */
public class WorkListFacadeScopeProvider implements IScopeProvider {

    /**
     * @see com.tibco.xpd.validation.provider.IScopeProvider#getAffectedObjects(com.tibco.xpd.validation.destinations.Destination,
     *      java.lang.String, com.tibco.xpd.validation.provider.IValidationItem)
     * 
     * @param destination
     * @param providerId
     * @param item
     * @return
     */
    @Override
    public Collection<EObject> getAffectedObjects(Destination destination,
            String providerId, IValidationItem item) {
        Set<EObject> itemToValidate = new HashSet<EObject>();
        WorkingCopy wc = item.getWorkingCopy();
        if (wc instanceof WorkListFacadeWorkingCopy) {
            // Get the elements to validate from the working copy
            addItemsToValidate(wc, itemToValidate);

        }

        return itemToValidate;
    }

    /**
     * Add the affected objects in the working copy to the list of objs to
     * validate.
     * 
     * @param wc
     *            <code>WorkingCopy</code>.
     * @param objs
     *            Set to add the validation items to.
     */
    protected void addItemsToValidate(WorkingCopy wc, Set<EObject> objs) {
        if (wc != null && objs != null) {
            EObject root = wc.getRootElement();

            if (root != null) {
                // Add root object
                objs.add(root);
                // Add root's content
                TreeIterator<Object> iter =
                        EcoreUtil.getAllProperContents(root, false);

                if (iter != null) {
                    while (iter.hasNext()) {
                        Object next = iter.next();

                        if (next instanceof EObject && include((EObject) next)) {
                            objs.add((EObject) next);
                        }
                    }
                }
            }
        }
    }

    /**
     * Check if the given EObject should be included in the validation.
     * 
     * @param eo
     *            <code>EObject</code>.
     * @return <code>true</code> if object of interest, <code>false</code>
     *         otherwise.
     */
    protected boolean include(EObject eo) {

        return eo != null
                && (eo instanceof WorkListFacade || eo instanceof WorkItemAttribute);
    }
}
