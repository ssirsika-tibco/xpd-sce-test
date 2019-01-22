/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.validator.rules.generic;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EValidator.PatternMatcher;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xml.type.internal.RegEx.ParseException;
import org.eclipse.emf.ecore.xml.type.util.XMLTypeUtil;
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
 * Validation rule to validate the contents of the pattern attribute of Text
 * type {@link Property Properties} and {@link PrimitiveType Primitive Types}.
 * This class expects either one of the following as it's configuration
 * parameter: "PrimitiveType" or "TypedElement".
 * 
 * @author njpatel
 * 
 */
public class TextTypePatternValidationRule implements IValidationRule,
        IExecutableExtension {

    private static final String PATTERN_ISSUE_ID =
            "invalidTextTypePattern,issue"; //$NON-NLS-1$

    private static final String PATTERN_XML_REGEX_ISSUE_ID =
            "invalidTextTypeXMLPattern.issue"; //$NON-NLS-1$

    private final Stereotype restrictedTypeStereotype;

    private EStructuralFeature patternFeature;

    private PrimitiveType baseTextType;

    private enum Option {
        PrimitiveType, TypedElement;
    }

    private Option option;

    /**
     * 
     */
    public TextTypePatternValidationRule() {
        ResourceSet rSet =
                XpdResourcesPlugin.getDefault().getEditingDomain()
                        .getResourceSet();
        restrictedTypeStereotype = PrimitivesUtil.getFacetsStereotype(rSet);
        baseTextType =
                PrimitivesUtil.getStandardPrimitiveTypeByName(rSet,
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
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
                primitiveType =
                        PrimitivesUtil.getBasePrimitiveType(primitiveType);
            }

            // Check that we are dealing with a Text type
            if (primitiveType == baseTextType) {
                EObject application =
                        target
                                .getStereotypeApplication(restrictedTypeStereotype);

                if (application != null) {
                    EStructuralFeature feature =
                            getPatternFeature(application.eClass());
                    if (feature != null) {
                        Object value = application.eGet(feature);
                        if (value instanceof String) {
                            createIssueIfPatternInvalid((String) value,
                                    scope,
                                    target);
                        }
                    }
                }
            }
        }
    }

    /**
     * Validates the supplied pattern and raises an issue if it is invalid. At
     * the time of writing, a CDS validation rule extends this class and
     * overrides this method (as it has different pattern validation logic).
     * 
     * @param pattern
     * @param scope
     * @param target
     */
    protected void createIssueIfPatternInvalid(String pattern,
            IValidationScope scope, NamedElement target) {

        if (pattern.length() > 0) {
            try {
                // EMF Uses this to deal with patterns, not java.util.regex
                @SuppressWarnings("unused")
                PatternMatcher pm = XMLTypeUtil.createPatternMatcher(pattern);
            } catch (ParseException e) {
                List<String> params = new ArrayList<String>();
                params.add(pattern);
                params.add(e.getLocalizedMessage());
                params.add(String.valueOf(e.getLocation()));

                scope.createIssue(PATTERN_XML_REGEX_ISSUE_ID, target
                        .getQualifiedName(), target.eResource()
                        .getURIFragment(target), params);

                return;
            }
        }
    }

    /**
     * Get the pattern feature of the RestrictedType stereotype.
     * 
     * @param eClass
     * @return
     */
    private EStructuralFeature getPatternFeature(EClass eClass) {
        if (patternFeature == null && eClass != null) {
            EList<EStructuralFeature> features =
                    eClass.getEAllStructuralFeatures();
            if (features != null) {
                for (EStructuralFeature feature : features) {
                    if (PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_PATTERN_VALUE
                            .equals(feature.getName())) {
                        patternFeature = feature;
                        break;
                    }
                }
            }
        }
        return patternFeature;
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
