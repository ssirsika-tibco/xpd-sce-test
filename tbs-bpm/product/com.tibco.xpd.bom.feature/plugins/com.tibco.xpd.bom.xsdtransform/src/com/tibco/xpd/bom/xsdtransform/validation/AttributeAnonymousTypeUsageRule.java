/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.bom.xsdtransform.validation;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Notify problems if a user defined bom class has attributes pointing to
 * anonymous simple/complex types and top level simple types.
 * 
 * @author bharge
 * @since 25 Sep 2015
 */
public class AttributeAnonymousTypeUsageRule implements IValidationRule {

    private static final String ANONYMOUS_TYPE_ISSUE_ID =
            "anonymousTypeCannotBeUsedForAttributeType.issue"; //$NON-NLS-1$

    private static final String PRIMITIVE_TLE_OR_ANONYMOUS_TYPE_ISSUE_ID =
            "primitiveTLEOrAnonymousTypeCannotBeUsedForAttribType.issue"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.rules.IValidationRule#getTargetClass()
     * 
     * @return
     */
    @Override
    public Class<?> getTargetClass() {

        return org.eclipse.uml2.uml.Class.class;
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

        org.eclipse.uml2.uml.Class bomClass = (org.eclipse.uml2.uml.Class) o;

        IFile file = WorkingCopyUtil.getFile(bomClass);
        /*
         * We don't want to validate if the bom file is not found or if it is a
         * generated bom!
         */
        if (null == file || BOMValidationUtil.isGeneratedBom(file)) {

            return;
        }

        EList<Property> attributes = bomClass.getAttributes();
        for (Property property : attributes) {

            Type type = property.getType();
            if (type instanceof PrimitiveType || type instanceof Enumeration) {

                Classifier primitiveType = (Classifier) type;
                Boolean classifierTopLevelElement =
                        XSDUtil.isClassifierTopLevelElement(primitiveType);
                /*
                 * Top level element or is anonymous type then disallow.
                 */
                Boolean classifierAnonymousType =
                        XSDUtil.isClassifierAnonymousType(primitiveType);

                if (classifierTopLevelElement || classifierAnonymousType) {

                    scope.createIssue(PRIMITIVE_TLE_OR_ANONYMOUS_TYPE_ISSUE_ID,
                            BOMValidationUtil.getLocation(property),
                            property.eResource().getURIFragment(property));
                }
            } else if (type instanceof org.eclipse.uml2.uml.Class) {

                org.eclipse.uml2.uml.Class class1 =
                        (org.eclipse.uml2.uml.Class) type;

                Boolean classifierTopLevelElement =
                        XSDUtil.isClassifierTopLevelElement(class1);
                /*
                 * Top level element and is anonymous type then allow. Not a top
                 * level element but is anonymous type then disallow
                 */
                Boolean classifierAnonymousType =
                        XSDUtil.isClassifierAnonymousType(class1);

                if (!classifierTopLevelElement && classifierAnonymousType) {

                    /*
                     * XPD-7977: When the anonymous type is referenced from
                     * parent class then we should not complain. Complain only
                     * when the reference to this anonymous type is from a
                     * different class in a different bom.
                     */
                    if (type.eContainer() instanceof org.eclipse.uml2.uml.Model
                            && bomClass.eContainer() instanceof org.eclipse.uml2.uml.Model
                            && type.eContainer() != bomClass.eContainer()) {

                        scope.createIssue(ANONYMOUS_TYPE_ISSUE_ID,
                                BOMValidationUtil.getLocation(property),
                                property.eResource().getURIFragment(property));
                    }
                }
            }
        }
    }

}
