/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.bom.validator.rules.xsd;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Stereotype;

import com.tibco.xpd.bom.resources.utils.BOMProfileUtils;
import com.tibco.xpd.bom.validator.XsdIssueIds;
import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * XSD Validation enforcing that a subclass Class cannot be a top level element representation
 * 
 * @author glewis
 */
public class GeneralisedClassIsElementRule extends
        AbstractXsdRule {
    
    private static final String XSD_BASED_CLASS =
        "XsdBasedClass"; //$NON-NLS-1$
    
    private static final String XSD_ANON_TYPE =
        "xsdIsAnonType"; //$NON-NLS-1$

    @Override
    public Class<?> getTargetClass() {
        return org.eclipse.uml2.uml.Class.class;
    }

    @Override
    public void performXSDValidation(IValidationScope scope, Object o) {
        if (!enabledInCapabilities()) {
            return;
        }

        if (o instanceof org.eclipse.uml2.uml.Class) {
            org.eclipse.uml2.uml.Class cls = (org.eclipse.uml2.uml.Class) o;
            if (BOMProfileUtils.isXSDProfileApplied(cls.getModel())) {
                return;
            }
           
            EList<Generalization> generalizations = cls.getGeneralizations();

            if (generalizations != null && !generalizations.isEmpty()) {
                Classifier general = generalizations.get(0).getGeneral();

                if (!BOMProfileUtils.isXSDProfileApplied(general.getModel())) {
                    return;
                }
                List<Stereotype> appliedStereotypes =
                        general.getAppliedStereotypes();
                if (appliedStereotypes == null) {
                    return;
                }
                for (Stereotype stereo : appliedStereotypes) {

                    if (general instanceof org.eclipse.uml2.uml.Class) {
                        if (XSD_BASED_CLASS.equals(stereo.getName())) {
                            Object value =
                                    general.getValue(stereo,
                                            XSD_ANON_TYPE);
                            if (value != null && value instanceof Boolean) {
                                Boolean booleanValue = (Boolean) value;
                                if (booleanValue.booleanValue()){                                        
                                    scope
                                            .createIssue(XsdIssueIds.CLASS_GENERALIZATION_OF_ANON_TYPE_TYPE,
                                                    cls.getQualifiedName(),
                                                    cls
                                                            .eResource()
                                                            .getURIFragment(cls));
                                }
                            }
                        }                    
                    }

                }

            }
        }

    }
}
