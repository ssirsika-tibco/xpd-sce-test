/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.bom.validator.rules.xsd;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

import com.tibco.xpd.bom.resources.utils.BOMProfileUtils;
import com.tibco.xpd.bom.validator.XsdIssueIds;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * XSD Validation enforcing that a subclass Class cannot have same attribute
 * name
 * 
 * @author glewis
 */
public class GeneralisedClassContainsDuplicateAttributeRule extends
        AbstractXsdRule {

    @Override
    public Class<?> getTargetClass() {
        return Property.class;
    }

    @Override
    public void performXSDValidation(IValidationScope scope, Object o) {
        if (!enabledInCapabilities()) {
            return;
        }

        if (o instanceof Property) {
            Property prop = (Property) o;
            String uri = prop.eResource().getURIFragment(prop);
            EObject container = prop.eContainer();

            if (container instanceof org.eclipse.uml2.uml.Class) {
                org.eclipse.uml2.uml.Class cl =
                        (org.eclipse.uml2.uml.Class) container;

                EList<Generalization> generalizations = cl.getGeneralizations();

                if (!generalizations.isEmpty()) {
                    Generalization gen = generalizations.get(0);
                    Classifier superClassifier = gen.getGeneral();

                    if (superClassifier instanceof org.eclipse.uml2.uml.Class) {
                        org.eclipse.uml2.uml.Class superClass =
                                (org.eclipse.uml2.uml.Class) superClassifier;

                        EList<Property> allAttributes =
                                superClass.getAllAttributes();

                        for (Property property : allAttributes) {
                            String propName = prop.getName();
                            String superPropName = property.getName();

                            if (propName.equals(superPropName)) {
                                // If the subclass represents a restriction of a
                                // complex type then we allow duplicate names
                                if (!isComplexRestriction(prop)) {
                                    List<String> additionalMessages =
                                            new ArrayList<String>();
                                    additionalMessages.add(cl.getName());
                                    additionalMessages.add(propName);
                                    scope.createIssue(XsdIssueIds.GENERALISED_CLASS_CONTAINS_DUPLICATE_ATTRIBUTE,
                                            BOMValidationUtil.getLocation(prop),
                                            uri,
                                            additionalMessages);
                                    break;
                                }

                            }
                        }

                    }

                }

            }

        }

    }

    /**
     * 
     * Returns true if the property's parent class is a complex type restriction
     * of another complex type
     * 
     * @param prop
     * @return
     */
    private boolean isComplexRestriction(Property prop) {

        if (BOMProfileUtils.isXSDProfileApplied(prop.getModel())) {
            Element owner = prop.getOwner();

            if (owner != null && owner instanceof org.eclipse.uml2.uml.Class) {
                org.eclipse.uml2.uml.Class parentClass =
                        (org.eclipse.uml2.uml.Class) owner;

                Stereotype appliedStereotype =
                        owner.getAppliedStereotype("XsdNotationProfile" + "::"
                                + "XsdBasedClass");

                if (appliedStereotype != null
                        && BOMProfileUtils
                                .isProfileXSDProfile(appliedStereotype
                                        .getProfile())) {

                    Object xsdPropertyTypeObj =
                            owner.getValue(appliedStereotype,
                                    "xsdIsRestriction");

                    if (xsdPropertyTypeObj instanceof Boolean) {
                        Boolean isSet = (Boolean) xsdPropertyTypeObj;

                        if (isSet.booleanValue() == Boolean.TRUE) {
                            return true;
                        }
                    }
                }
            }

        }

        return false;

    }

}
