/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.internal.engine;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.destinations.Destination;
import com.tibco.xpd.validation.provider.IScopeProvider;
import com.tibco.xpd.validation.provider.IValidationItem;

/**
 * Analyses the scope of the detected change, including any dependencies, and
 * adds the affected objects to the validation queue. The default scope provider
 * adds all objects in the affected models regardless of what has changed. The
 * main purpose of this class is for use during a clean build.
 * 
 * @author nwilson
 */
public class DefaultScopeProvider implements IScopeProvider {

    /**
     * @param destination
     *            The destination environment.
     * @param providerId
     *            The validation provider ID.
     * @param item
     *            The validation item.
     * @return A collection of objects that will need validation.
     * @see com.tibco.xpd.validation.provider.IScopeProvider#getAffectedObjects(
     *      com.tibco.xpd.validation.destinations.Destination, java.lang.String,
     *      com.tibco.xpd.validation.provider.IValidationItem)
     */
    public Collection<EObject> getAffectedObjects(Destination destination,
            String providerId, IValidationItem item) {
        Collection<EObject> objects = new ArrayList<EObject>();
        WorkingCopy wc = item.getWorkingCopy();
        if (wc != null) {
            addAllObjects(objects, wc);
            Collection<IResource> ress = WorkingCopyUtil
                    .getAffectedResources(wc.getEclipseResources().get(0));
            for (IResource res : ress) {
                WorkingCopy ewc = XpdResourcesPlugin.getDefault()
                        .getWorkingCopy(res);
                if (ewc != null) {
                    addAllObjects(objects, ewc);
                }
            }
        }
        return objects;
    }

    /**
     * @param objects
     *            The collection to add to.
     * @param wc
     *            The working copy to get the object sfrom.
     */
    private void addAllObjects(Collection<EObject> objects, WorkingCopy wc) {
        EObject root = wc.getRootElement();
        if (root != null) {
            objects.add(root);
            TreeIterator iter = root.eAllContents();
            while (iter.hasNext()) {
                Object next = iter.next();
                if (next instanceof EObject) {
                    objects.add((EObject) next);
                }
            }
        }
    }
}
