/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.validator.rules.generic;

import java.net.URI;
import java.util.Collections;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.TypedElement;

import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Validation rule to validate the contents of the default attribute of URI type
 * {@link Property Properties} and {@link PrimitiveType Primitive Types}. This
 * class expects either one of the following as it's configuration parameter:
 * "PrimitiveType" or "TypedElement".
 * 
 * @author njpatel
 * 
 */
public class URITypeValidationRule implements IValidationRule,
        IExecutableExtension {

    private static final String INVALID_URI_ISSUE_ID = "invalidUri.issue"; //$NON-NLS-1$
    private Stereotype restrictedTypeStereotype;
    private PrimitiveType baseURIType;
    private EStructuralFeature defaultFeature;

    private enum Option {
        PrimitiveType, TypedElement;
    }

    private Option option;

    /**
     * URI default value validation.
     */
    public URITypeValidationRule() {
        ResourceSet rSet = XpdResourcesPlugin.getDefault().getEditingDomain()
                .getResourceSet();
        restrictedTypeStereotype = PrimitivesUtil.getFacetsStereotype(rSet);
        baseURIType = PrimitivesUtil.getStandardPrimitiveTypeByName(rSet,
                PrimitivesUtil.BOM_PRIMITIVE_URI_NAME);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.validation.rules.IValidationRule#getTargetClass()
     */
    public Class<?> getTargetClass() {
        if (option != null) {
            switch (option) {
            case PrimitiveType:
                return PrimitiveType.class;
            case TypedElement:
                return TypedElement.class;
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.rules.IValidationRule#validate(com.tibco.xpd
     * .validation.provider.IValidationScope, java.lang.Object)
     */
    public void validate(IValidationScope scope, Object obj) {
        if (scope != null && obj instanceof NamedElement) {
            NamedElement target = (NamedElement) obj;
            PrimitiveType primitiveType = getType(target);

            if (primitiveType != null) {
                primitiveType = PrimitivesUtil
                        .getBasePrimitiveType(primitiveType);
            }

            // Check that we are dealing with a URI type
            if (primitiveType == baseURIType) {
                EObject application = target
                        .getStereotypeApplication(restrictedTypeStereotype);

                if (application != null) {
                    EStructuralFeature feature = getDefaultFeature(application
                            .eClass());
                    if (feature != null) {
                        Object value = application.eGet(feature);
                        if (value instanceof String) {
                            String defaultValue = (String) value;
                            try {
                                URI.create(defaultValue);
                            } catch (IllegalArgumentException e) {
                                if (e.getCause() != null) {
                                    scope.createIssue(INVALID_URI_ISSUE_ID,
                                            target.getLabel(), target
                                                    .eResource()
                                                    .getURIFragment(target),
                                            Collections.singleton(e.getCause()
                                                    .getLocalizedMessage()));
                                } else {
                                    scope.createIssue(INVALID_URI_ISSUE_ID,
                                            target.getLabel(), target
                                                    .eResource()
                                                    .getURIFragment(target));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Get the target's type.
     * 
     * @param target
     *            the element being validated
     * @return
     */
    private PrimitiveType getType(EObject target) {
        PrimitiveType pType = null;
        if (target instanceof PrimitiveType) {
            pType = (PrimitiveType) target;
        } else if (target instanceof TypedElement) {
            Type type = ((TypedElement) target).getType();
            if (type instanceof PrimitiveType) {
                pType = (PrimitiveType) type;
            }
        }
        return pType;
    }

    /**
     * Get the default feature of the RestrictedType stereotype.
     * 
     * @param eClass
     * @return
     */
    private EStructuralFeature getDefaultFeature(EClass eClass) {
        if (defaultFeature == null && eClass != null) {
            EList<EStructuralFeature> features = eClass
                    .getEAllStructuralFeatures();
            if (features != null) {
                for (EStructuralFeature feature : features) {
                    if (PrimitivesUtil.BOM_PRIMITIVE_FACET_URI_DEFAULT_VALUE
                            .equals(feature.getName())) {
                        defaultFeature = feature;
                        break;
                    }
                }
            }
        }
        return defaultFeature;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.core.runtime.IExecutableExtension#setInitializationData(org
     * .eclipse.core.runtime.IConfigurationElement, java.lang.String,
     * java.lang.Object)
     */
    public void setInitializationData(IConfigurationElement config,
            String propertyName, Object data) throws CoreException {
        if (data instanceof String) {
            option = Option.valueOf((String) data);
        }
    }

}
