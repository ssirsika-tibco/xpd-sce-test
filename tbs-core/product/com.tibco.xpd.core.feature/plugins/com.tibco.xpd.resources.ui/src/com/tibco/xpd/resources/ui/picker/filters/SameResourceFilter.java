/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.resources.ui.picker.filters;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.ui.picker.PickerItem;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Limits the selected items to only these coming from the same resource as
 * provided EObject.
 * 
 * @since 3.3.1
 * @author Jan Arciuchiewicz
 */
public class SameResourceFilter implements IFilter {

    private String contextResourceFullPath = null;

    /**
     * Creates new filter.
     * 
     * @param contextEObject
     *            only items from the same resource will be selected. If
     *            contextObject is not contained in a resource then all passed
     *            object will be selected.
     */
    public SameResourceFilter(EObject contextObject) {
        IResource contextResource = null;
        WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(contextObject);

        if (wc != null && wc.getEclipseResources() != null) {
            contextResource = wc.getEclipseResources().get(0);
            if (contextResource != null) {
                contextResourceFullPath =
                        contextResource.getFullPath().toPortableString();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean select(Object toTest) {
        if (toTest instanceof PickerItem && contextResourceFullPath != null) {
            String uriString = ((PickerItem) toTest).getURI();
            if (uriString != null) {
                URI uri = URI.createURI(uriString);
                if (uri != null) {
                    String platformStr = uri.toPlatformString(true);
                    return platformStr != null
                            && contextResourceFullPath.equals(platformStr);
                }
            }
        }
        return true;
    }

}
