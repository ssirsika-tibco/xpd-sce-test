/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.resources.ui.picker.filters;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.viewers.IFilter;

import com.tibco.xpd.resources.ui.picker.PickerItem;

/**
 * Limits the selected items to only these coming from provided resources.
 * 
 * @since 3.3.1
 * @author Jan Arciuchiewicz
 */
public class ResourcesFilter implements IFilter {

    private final Set<String> fullPaths = new HashSet<String>();

    /**
     * Creates new resources filter.
     * 
     * @param queryResources
     *            collection of resources. Only items from these resources will
     *            be selected.
     */
    public ResourcesFilter(Collection<IResource> queryResources) {
        for (IResource res : queryResources) {
            String fullPath = res.getFullPath().toPortableString();
            if (fullPath != null) {
                fullPaths.add(fullPath);
            }
        }
    }

    /**
     * Creates new resources filter.
     * 
     * @param queryResources
     *            resources to be included. Only items from these resources will
     *            be selected.
     */
    public ResourcesFilter(IResource... queryResources) {
        this(Arrays.asList(queryResources));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean select(Object toTest) {
        if (toTest instanceof PickerItem) {
            String uriString = ((PickerItem) toTest).getURI();
            if (uriString != null) {
                URI uri = URI.createURI(uriString);
                if (uri != null) {
                    String platformStr = uri.toPlatformString(true);
                    return platformStr != null
                            && fullPaths.contains(platformStr);
                }
            }
        }
        return true;
    }

}
