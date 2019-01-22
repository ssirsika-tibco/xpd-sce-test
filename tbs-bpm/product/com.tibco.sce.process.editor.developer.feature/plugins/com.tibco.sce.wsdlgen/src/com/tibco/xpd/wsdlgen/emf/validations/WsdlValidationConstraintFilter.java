/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.wsdlgen.emf.validations;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.model.Category;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.eclipse.emf.validation.service.IConstraintFilter;

/**
 * @author rsomayaj
 * 
 */
public class WsdlValidationConstraintFilter implements IConstraintFilter {

    /**
     * 
     */
    private static final String WSDLGEN_CATEGORY =
            "com.tibco.xpd.wsdlgen.xpdl.category";

    /**
     * @see org.eclipse.emf.validation.service.IConstraintFilter#accept(org.eclipse.emf.validation.service.IConstraintDescriptor,
     *      org.eclipse.emf.ecore.EObject)
     * 
     * @param constraint
     * @param target
     * @return
     */
    public boolean accept(IConstraintDescriptor constraint, EObject target) {
        Set<Category> categories = constraint.getCategories();
        for (Category category : categories) {
            if (WSDLGEN_CATEGORY.equals(category.getPath())) {
                return true;
            }
        }
        return false;
    }

}
