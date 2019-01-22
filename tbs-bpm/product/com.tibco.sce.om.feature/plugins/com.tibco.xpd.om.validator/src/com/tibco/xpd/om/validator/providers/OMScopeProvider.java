/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.om.validator.providers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.validation.destinations.Destination;
import com.tibco.xpd.validation.provider.IScopeProvider;
import com.tibco.xpd.validation.provider.IValidationItem;

/**
 * @author glewis
 * 
 */
public class OMScopeProvider implements IScopeProvider {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.provider.IScopeProvider#getAffectedObjects(com
     * .tibco.xpd.validation.destinations.Destination, java.lang.String,
     * com.tibco.xpd.validation.provider.IValidationItem)
     */
    @Override
    public Collection<EObject> getAffectedObjects(Destination destination,
            String providerId, IValidationItem item) {
        if (doValidate(item.getObjects())) {
            WorkingCopy wc = item.getWorkingCopy();

            // Get the elements to validate from the working copy
            if (wc != null && wc.getRootElement() instanceof OrgModel) {
                return addItemsToValidate((OrgModel) wc.getRootElement());
            }
        }

        return new ArrayList<EObject>(0);
    }

    /**
     * @param root
     *            OM root element
     * @return
     */
    private Collection<EObject> addItemsToValidate(OrgModel model) {
        Set<EObject> items = new HashSet<EObject>();

        for (TreeIterator<EObject> iter = model.eAllContents(); iter.hasNext();) {
            Object next = iter.next();

            if (next instanceof NamedElement) {
                items.add((EObject) next);
            }
        }

        return items;
    }

    /**
     * Check if the validation item should be revalidated.
     * 
     * @param eObjects
     *            collection of affected objects
     * @return <code>true</code> if the validation item should be revalidated,
     *         <code>false</code> otherwise.
     */
    private boolean doValidate(Collection<EObject> eObjects) {
        if (eObjects != null) {
            for (EObject eo : eObjects) {
                if (eo instanceof NamedElement) {
                    // Validate model
                    return true;
                }
            }
        }

        return false;
    }

}
