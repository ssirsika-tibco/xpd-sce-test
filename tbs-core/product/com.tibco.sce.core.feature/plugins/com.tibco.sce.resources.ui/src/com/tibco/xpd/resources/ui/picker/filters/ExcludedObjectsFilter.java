/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.resources.ui.picker.filters;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.viewers.IFilter;

import com.tibco.xpd.resources.ui.picker.PickerItem;

/**
 * Eliminate a collection of objects from a view. Objects can be either
 * PickerItems or EObject (EObjects must be contained in resource).
 * 
 * @since 3.3.1
 * @author Jan Arciuchiewicz
 */
public class ExcludedObjectsFilter implements IFilter {

    private final Set<String> objectURIsToExclude;

    /**
     * Creates filter with a collection of object to exclude.
     */
    public ExcludedObjectsFilter(Collection<?> objectToExclude) {
        objectURIsToExclude = new HashSet<String>();
        for (Object o : objectToExclude) {
            if (o instanceof EObject) {
                EObject eObject = (EObject) o;
                Resource modelElementResource = eObject.eResource();
                if (modelElementResource != null) {
                    URI uri =
                            modelElementResource.getURI()
                                    .appendFragment(modelElementResource
                                            .getURIFragment(eObject));
                    objectURIsToExclude.add(uri.toString());
                }
            } else if (o instanceof PickerItem) {
                Assert.isTrue(((PickerItem) o).getURI() != null);
                objectURIsToExclude.add(((PickerItem) o).getURI());
            }

        }

    }

    public ExcludedObjectsFilter(Object... objectsToExclude) {
        this(Arrays.asList(objectsToExclude));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean select(Object toTest) {
        if (toTest instanceof PickerItem) {
            String uriString = ((PickerItem) toTest).getURI();
            return !objectURIsToExclude.contains(uriString);
        }
        return true;
    }
}
