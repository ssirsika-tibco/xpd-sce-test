/*
 * Copyright (c) TIBCO Software Inc 2014. All rights reserved.
 */

package com.tibco.xpd.bom.validator.rules.globaldata;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * This rule validates the Case State attribute
 * 
 */
public class CaseStateValidationRule implements IValidationRule {

    private static final String ISSUE_CASESTATE_MULTIPLICITY_INVALID =
            "casestate.invalid.multiplicity.issue"; //$NON-NLS-1$

    private static final String ISSUE_CASESTATE_TYPE_INVALID =
            "casestate.invalid.type.issue"; //$NON-NLS-1$

    private static final String ISSUE_CASESTATE_CONTAINING_CLASS_INVALID =
            "casestate.invalid.containing.class.issue"; //$NON-NLS-1$

    private static final String ISSUE_CASESTATE_MULTIPLE_INVALID =
            "casestate.invalid.multiple.issue"; //$NON-NLS-1$

    @Override
    public Class<?> getTargetClass() {
        return Property.class;
    }

    /**
     * @see com.tibco.xpd.validation.rules.IValidationRule#validate(com.tibco.xpd.validation.provider.IValidationScope,
     *      java.lang.Object)
     * 
     * @param scope
     * @param o
     */
    @Override
    public void validate(IValidationScope scope, Object o) {
        if (o instanceof Property) {
            Property prop = (Property) o;
            List<String> additionalMessages = new ArrayList<String>();
            if (GlobalDataProfileManager.getInstance().isCaseState(prop)) {
                // Make sure the case state has max multiplicity of 1
                // so can be either 0..1 or 1..1
                if ((prop.getUpper() > 1) || (prop.getUpper() == -1)) {
                    additionalMessages.add(prop.getName());
                    scope.createIssue(ISSUE_CASESTATE_MULTIPLICITY_INVALID,
                            BOMValidationUtil.getLocation(prop),
                            prop.eResource().getURIFragment(prop),
                            additionalMessages);
                }
                // Make sure the type is an enumeration, if there is no type set
                // for the property, there will already be a validation marker
                Type propType = prop.getType();
                if (propType != null) {
                    if (propType instanceof Enumeration) {
                        Enumeration enumProp = (Enumeration) propType;
                        for (Classifier baseType : enumProp.getGenerals()) {
                            if (baseType instanceof PrimitiveType) {
                                PrimitiveType primType =
                                        PrimitivesUtil
                                                .getBasePrimitiveType((PrimitiveType) baseType);
                                // Make sure it is a text enumeration
                                if (!PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME
                                        .equals(primType.getName())) {
                                    additionalMessages.add(prop.getName());
                                    scope.createIssue(ISSUE_CASESTATE_TYPE_INVALID,
                                            BOMValidationUtil.getLocation(prop),
                                            prop.eResource()
                                                    .getURIFragment(prop),
                                            additionalMessages);
                                }
                            }
                        }
                    } else {
                        additionalMessages.add(prop.getName());
                        scope.createIssue(ISSUE_CASESTATE_TYPE_INVALID,
                                BOMValidationUtil.getLocation(prop),
                                prop.eResource()
                                .getURIFragment(prop), additionalMessages);
                    }
                }

                org.eclipse.uml2.uml.Class clazz = prop.getClass_();

                if (clazz != null) {
                    // Make sure that the containing class is a Case Class
                    if (!GlobalDataProfileManager.getInstance().isCase(clazz)) {
                        additionalMessages.add(prop.getName());
                        scope.createIssue(ISSUE_CASESTATE_CONTAINING_CLASS_INVALID,
                                BOMValidationUtil.getLocation(prop),
                                prop.eResource().getURIFragment(prop),
                                additionalMessages);
                    }

                    // Make sure there is only one Case State attribute in the
                    // class
                    // This is to include all extended classes
                    List<Property> caseStates = new ArrayList<Property>();
                    // Check all properties including
                    for (Property property : clazz.getAllAttributes()) {
                        // If the property is a case state, then add it to the
                        // list of case states
                        if (GlobalDataProfileManager.getInstance()
                                .isCaseState(property)) {
                            caseStates.add(property);
                        }
                    }

                    // If there is more than one case state then we need to flag
                    // an error as you are only allowed one per case class
                    if (caseStates.size() > 1) {
                        for (Property caseStateProp : caseStates) {
                            additionalMessages.add(caseStateProp.getName());
                            scope.createIssue(ISSUE_CASESTATE_MULTIPLE_INVALID,
                                    BOMValidationUtil
                                            .getLocation(caseStateProp),
                                    caseStateProp.eResource()
                                            .getURIFragment(prop),
                                    additionalMessages);
                        }
                    }

                }
            }
        }
    }
}
