/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.util;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Stereotype;

/**
 * 
 * A class to encapsulate validation of XSD profile stereotypes applied to UML2
 * objects.
 * 
 * 
 * @author rgreen
 * 
 */
public class XSDStereotypeValidator {

    private List<XsdStereotypeValidationItem> validationItems =
            new ArrayList<XsdStereotypeValidationItem>();

    private Element element2Validate;

    public XSDStereotypeValidator(Element elem) {
        element2Validate = elem;
    }

    public void addStereotypeValidationItem(String stereotype, String facet,
            Object value) {
        XsdStereotypeValidationItem item =
                new XsdStereotypeValidationItem(stereotype, facet, value);
        validationItems.add(item);
    }

    private class XsdStereotypeValidationItem {

        private final String stereotype;

        private final String facet;

        private final Object value;

        public XsdStereotypeValidationItem(String stereotype, String facet,
                Object value) {
            this.stereotype = stereotype;
            this.facet = facet;
            this.value = value;
        }

        public String getStereotype() {
            return stereotype;
        }

        public String getFacet() {
            return facet;
        }

        public Object getValue() {
            return value;
        }

    }

    public List<XsdStereotypeValidationItem> getValidationItems() {
        return validationItems;
    }

    public boolean isValidStereotypes() {

        for (XsdStereotypeValidationItem item : validationItems) {

            // 1. Check stereotype is applied
            boolean stereoIsApplied = false;
            EList<Stereotype> appliedStereotypes =
                    element2Validate.getAppliedStereotypes();

            for (Stereotype stereotype : appliedStereotypes) {
                if (stereotype.getName().equals(item.getStereotype())) {
                    stereoIsApplied = true;
                    Object value =
                            element2Validate.getValue(stereotype, item
                                    .getFacet());

                    // 2. Check facet values
                    if (value instanceof String) {

                        Assert.assertTrue("Data types must match", item
                                .getFacet() instanceof String);

                        Assert
                                .assertEquals("Stereotype facet values should match",
                                        (String) item.getValue(),
                                        (String) value);
                    }

                    break;
                }
            }

            Assert.assertTrue("Stereotype " + item.getStereotype()
                    + " is applied", stereoIsApplied);
        }

        return true;

    }

}
