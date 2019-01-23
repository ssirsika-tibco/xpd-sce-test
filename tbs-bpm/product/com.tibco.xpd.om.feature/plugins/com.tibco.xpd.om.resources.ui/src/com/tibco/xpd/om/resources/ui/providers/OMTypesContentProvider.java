/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.providers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.xpd.om.resources.ui.models.OMTypeDetails;

/**
 * Content provider for the om types picker.
 * 
 * @author glewis
 * 
 */
public class OMTypesContentProvider implements IStructuredContentProvider {

    private List<OMTypeDetails> omTypesDetailsArray = null;

    List<Class> omInitialSelectedTypesArray = new ArrayList<Class>();

    /**
     * 
     */
    public OMTypesContentProvider() {
        super();
    }

    /**
     * @param omInitialSelectedTypesArray
     */
    public OMTypesContentProvider(List<Class> omInitialSelectedTypesArray) {
        super();
        this.omInitialSelectedTypesArray = omInitialSelectedTypesArray;
    }

    /**
     * @param inputElement
     *            The input element to get the content for.
     * @return An array of content objects.
     * @see org.eclipse.jface.viewers.IStructuredContentProvider#
     *      getElements(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    public Object[] getElements(Object input) {
        if (omTypesDetailsArray == null) {
            omTypesDetailsArray = new ArrayList<OMTypeDetails>();
            if (input instanceof List) {
                List<Class> types = (List<Class>) input;
                Iterator<Class> iter = types.iterator();
                while (iter.hasNext()) {
                    Class tempCls = iter.next();
                    OMTypeDetails omTypeDetails = new OMTypeDetails(
                            omInitialSelectedTypesArray.contains(tempCls),
                            tempCls);
                    omTypesDetailsArray.add(omTypeDetails);
                }
            }
        }
        return omTypesDetailsArray.toArray();
    }

    public void dispose() {
    }

    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }
}