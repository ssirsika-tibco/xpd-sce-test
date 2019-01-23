/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.bom.validator.rules.xsd;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EValidator.PatternMatcher;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xml.type.util.XMLTypeUtil;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.validator.XsdIssueIds;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * 
 * An XSD validation rule to check the values of restrictions set on a Property.
 * For example, whether default values exceed upper and lower limits.
 * 
 * @author rgreen
 * 
 */
public class PropertyRestrictionsRule implements IValidationRule {

    private static final String XSD_PROPERTY_CONSTRAINTS_DECPLACESEXCEEDSNUMLEN_ISSUE =
            "xsd.property.constraints.decplacesexceedsnumlen.issue";

    private static final String XSD_PROPERTY_CONSTRAINTS_PATTERNMISMATCH_ISSUE =
            "xsd.property.constraints.patternmismatch.issue";

    private static final String XSD_PROPERTY_CONSTRAINTS_DEFVALDECPLACES_ISSUE =
            "xsd.property.constraints.defvaldecplaces.issue";

    private static final String XSD_PROPERTY_CONSTRAINTS_UPPERLIMITDECPLACES_ISSUE =
            "xsd.property.constraints.upperlimitdecplaces.issue";

    private static final String XSD_PROPERTY_CONSTRAINTS_LOWERLIMITDECPLACES_ISSUE =
            "xsd.property.constraints.lowerlimitdecplaces.issue";

    private static final String XSD_PROPERTY_CONSTRAINTS_DEFVALLENEXCEEDSNUMLEN_ISSUE =
            "xsd.property.constraints.defvallenexceedsnumlen.issue";

    private static final String XSD_PROPERTY_CONSTRAINTS_UPPERLIMITLENEXCEEDSNUMLEN_ISSUE =
            "xsd.property.constraints.upperlimitlenexceedsnumlen.issue";

    private static final String XSD_PROPERTY_CONSTRAINTS_LOWERMIMITLENEXCCEEDSNUMLEN_ISSUE =
            "xsd.property.constraints.lowerlimitlenexceedsnumlen.issue";

    private static final String XSD_PROPERTY_CONSTRAINTS_DEFAULTEXCEEDSUPPERLIMIT_ISSUE =
            "xsd.property.constraints.defaultexceedsupperlimit.issue";

    private static final String XSD_PROPERTY_CONSTRAINTS_LOWERLIMITEXCEEDSDEFAULT_ISSUE =
            "xsd.property.constraints.lowerlimitexceedsdefault.issue";

    private static final String XSD_PROPERTY_CONSTRAINTS_LOWEREXCEEDSUPPERLIMIT_ISSUE =
            "xsd.property.constraints.lowerexceedsupperlimit.issue";

    private static final String XSD_PROPERTY_CONSTRAINTS_MAXTEXTLEN_ISSUE =
            "xsd.property.constraints.maxtextlen.issue";

    @Override
    public java.lang.Class<?> getTargetClass() {
        return Property.class;
    }

    @Override
    public void validate(IValidationScope scope, Object o) {
        if (o instanceof Property) {
            Property prop = (Property) o;
            Type type = prop.getType();

            if (type == null) {
                createIssue(scope, prop, XsdIssueIds.PROPERTY_NO_TYPE_SET, null);
            } else if (type instanceof PrimitiveType && !type.eIsProxy()) {
                PrimitiveType pt = (PrimitiveType) type;
                PrimitiveType basePrimitiveType =
                        PrimitivesUtil.getBasePrimitiveType(pt);

                ResourceSet rSet =
                        XpdResourcesPlugin.getDefault().getEditingDomain()
                                .getResourceSet();
                /*
                 * XPD-4011: Saket: Primitive type restriction based rules
                 * should ignore Generated BOMs.
                 */
                boolean isGenerateType = false;

                IFile file = WorkingCopyUtil.getFile(pt);
                if (null != file) {
                    IResource res =
                            ResourcesPlugin.getWorkspace().getRoot()
                                    .findMember(file.getFullPath());
                    if (res != null && BOMValidationUtil.isGeneratedBom(res)) {
                        isGenerateType = true;
                    }
                }

                // Only run validations if this is not a generated BOM type
                if (!isGenerateType) {
                    if (basePrimitiveType != null) {
                        if (basePrimitiveType == PrimitivesUtil
                                .getStandardPrimitiveTypeByName(rSet,
                                        PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME)) {
                            validateObjectType(prop, pt, scope);

                        } else if (basePrimitiveType == PrimitivesUtil
                                .getStandardPrimitiveTypeByName(rSet,
                                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME)) {
                            validateText(prop, pt, scope);

                        } else if (basePrimitiveType == PrimitivesUtil
                                .getStandardPrimitiveTypeByName(rSet,
                                        PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME)) {
                            validateInteger(prop, pt, scope);

                        } else if (basePrimitiveType == PrimitivesUtil
                                .getStandardPrimitiveTypeByName(rSet,
                                        PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME)) {
                            validateDecimal(prop, pt, scope);
                        }
                    }
                }
            }
        }
    }

    /**
     * 
     * Assumes that the Property passed in is of type "Object" and checks to see
     * if there are any other attributes of type "Object" within it's container
     * (includes hierarchy).
     * 
     * @param prop
     * @param pt
     * @param scope
     * @param basePrimitiveType
     */
    private void validateObjectType(Property prop, PrimitiveType pt,
            IValidationScope scope) {

        String subType = "";

        ResourceSet rSet =
                XpdResourcesPlugin.getDefault().getEditingDomain()
                        .getResourceSet();

        PrimitiveType baseObjectType =
                PrimitivesUtil.getStandardPrimitiveTypeByName(rSet,
                        PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME);

        if (pt != baseObjectType) {
            return;
        } else {
            // Check the subtype first. We only need to validate the "any" and
            // "anyAttribute" subtypes.
            Object value =
                    PrimitivesUtil.getFacetPropertyValue(pt,
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_OBJECT_SUBTYPE,
                            prop);

            if (value instanceof EnumerationLiteral) {
                subType = ((EnumerationLiteral) value).getName();
                if (subType
                        .equals(PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANYSIMPLETYPE)
                        || subType
                                .equals(PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANYTYPE)) {
                    return;
                }
            }
        }

        EObject container = prop.eContainer();

        if (container instanceof Class) {
            Class cl = (Class) container;

            validateSuperClass(prop, cl, scope);
            validateSubClasses(prop, cl, scope);

            // Check Object is last in the list of Properties in a Class
            EList<Property> ownedAttributes = cl.getOwnedAttributes();

            int size = ownedAttributes.size();

            if (size > 1) {
                if (prop.lowerBound() != prop.getUpper()) {

                    // Only really need to give an error if an xsd:any
                    // xsd:anyAttributes are not ordered in the XML so it
                    // does not matter
                    if (!subType
                            .equals(PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANYATTRIBUTE)) {
                        // This is the xsdAny subtype and
                        // element should be at the bottom of the list
                        Property lastProperty = ownedAttributes.get(size - 1);
                        if (lastProperty != prop) {
                            // create issue
                            createIssue(scope,
                                    prop,
                                    XsdIssueIds.PROPERTY_OBJECT_TYPE_NOT_LAST,
                                    null);
                        } else {
                            // The property immediately prior MUST have fixed
                            // multiplicity
                            Property secondFromLastProperty =
                                    ownedAttributes.get(size - 2);

                            if (secondFromLastProperty.upperBound() != secondFromLastProperty
                                    .lowerBound()) {
                                // create issue
                                createIssue(scope,
                                        secondFromLastProperty,
                                        XsdIssueIds.PROPERTY_MULTIPLICITY_PRE_OBJECT_TYPE,
                                        null);
                            }
                        }
                    }

                } else {
                    // Case where multiplicity is fixed
                    int indexOfObjectProp = ownedAttributes.indexOf(prop);

                    if ((indexOfObjectProp - 1) >= 0) {
                        Property preProp =
                                ownedAttributes.get(indexOfObjectProp - 1);

                        if (preProp.lowerBound() != preProp.upperBound()) {
                            // create issue
                            createIssue(scope,
                                    preProp,
                                    XsdIssueIds.PROPERTY_MULTIPLICITY_PRE_OBJECT_TYPE,
                                    null);
                        }
                    }
                    if ((indexOfObjectProp + 1) < size) {
                        Property postProp =
                                ownedAttributes.get(indexOfObjectProp + 1);

                        if (postProp.lowerBound() != postProp.upperBound()) {
                            // create issue
                            createIssue(scope,
                                    postProp,
                                    XsdIssueIds.PROPERTY_MULTIPLICITY_POST_OBJECT_TYPE,
                                    null);
                        }
                    }
                }
            }

            // Get ALL the attributes in the hierarchy
            EList<Property> attrs = cl.getAllAttributes();
            for (Property property : attrs) {
                if (prop == property) {
                    continue;
                }
                if (property.getType() == baseObjectType) {

                    Object subTypeObj =
                            PrimitivesUtil
                                    .getFacetPropertyValue((PrimitiveType) property
                                            .getType(),
                                            PrimitivesUtil.BOM_PRIMITIVE_FACET_OBJECT_SUBTYPE,
                                            property);

                    if (subTypeObj instanceof EnumerationLiteral) {
                        EnumerationLiteral subTypeEnumLit =
                                (EnumerationLiteral) subTypeObj;

                        if (subTypeEnumLit.getName()
                                .equals(PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANY)
                                || subTypeEnumLit
                                        .getName()
                                        .equals(PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANYATTRIBUTE)) {
                            createIssue(scope,
                                    prop,
                                    XsdIssueIds.PROPERTY_DUPLICATE_OBJECT_TYPE,
                                    null);
                        }
                    }
                }
            }
        }

    }

    /**
     * The first Property of a SuperClass must have fixed multiplicity
     * 
     * @param prop
     * @param cl
     * @param scope
     */
    private void validateSuperClass(Property prop, Class cl,
            IValidationScope scope) {
        EList<Generalization> generalizations = cl.getGeneralizations();

        if (!generalizations.isEmpty()) {
            Generalization gen = generalizations.get(0);
            Classifier general = gen.getGeneral();
            EList<Property> attributes = general.getAttributes();

            if (!attributes.isEmpty()) {
                Property firstProp = attributes.get(0);

                if (firstProp.getLower() != firstProp.getUpper()) {
                    // Create issue
                    createIssue(scope,
                            firstProp,
                            XsdIssueIds.PROPERTY_CONSTRAINTS_SUBCLASS_CONTAINS_TYPE_ANY,
                            null);
                }
            }
        }
    }

    /**
     * 
     * For a Property of type Object the containing Class needs to be checked to
     * see whether is is extended by any other classes. Whether it is or not
     * will determine the validity of the multiplicity assigned to the Property.
     * 
     * @param Property
     * @param Class
     * @param IValidationScope
     * 
     */
    private void validateSubClasses(Property prop, Class cl,
            IValidationScope scope) {
        boolean hasSubAttributes = doesSubClassContainProperties(cl);

        if (hasSubAttributes) {
            // Multiplicity of object attribute must be fixed
            if (prop.lowerBound() != prop.upperBound()) {
                // create issue
                createIssue(scope,
                        prop,
                        XsdIssueIds.PROPERTY_MULTIPLICITY_OBJECT_TYPE_GENERALIZATION,
                        null);
            }
        }

    }

    /**
     * For the given Class, checks whether its sub-Classes have at least one
     * Property. Returns when the first Property has been found.
     * 
     * @param Class
     * @return boolean
     */
    private boolean doesSubClassContainProperties(Class cl) {
        return doesSubClassContainProperties(cl, null);
    }

    /**
     * For the given Class, checks whether its sub-Classes have at least one
     * Property. Returns when the first Property has been found.<br />
     * <br />
     * The Set parameter is used for recursive class of this method so that we
     * can keep a history of parent classes and check for (illegal) circular
     * generalizations. This has the danger of causing an infinite loop. This
     * parameter should be set to null on first call of this method.
     * 
     * @param Class
     * @param Set
     *            &ltClass&gt
     * @return boolean
     */
    private boolean doesSubClassContainProperties(Class cl,
            Set<Class> accumClasses) {
        boolean bFound = false;

        if (accumClasses == null) {
            accumClasses = new HashSet<Class>();
        }
        accumClasses.add(cl);

        List<Class> directSubClasses = BOMUtils.getDirectSubClasses(cl);

        if (!directSubClasses.isEmpty()) {
            for (Class class1 : directSubClasses) {

                if (accumClasses.contains(class1)) {
                    // This would be a circular relationship and we would never
                    // return! Besides, we would already have tested this class
                    // for Properties
                    continue;
                }

                if (class1.getOwnedAttributes().size() > 0) {
                    // We've found a Property so we've done our job
                    bFound = true;
                    break;
                } else {
                    // May have to drill down
                    bFound =
                            doesSubClassContainProperties(class1, accumClasses);
                }
            }

        }
        return bFound;

    }

    /**
     * @param prop
     * @param pt
     * @param scope
     */
    private void validateText(Property prop, PrimitiveType pt,
            IValidationScope scope) {

        String defaultVal = null;
        String patternVal = null;
        Integer maxLen = null;

        /*
         * Initialize values
         */
        // default text value
        Object defaultTextValue =
                PrimitivesUtil.getFacetPropertyValue((PrimitiveType) prop
                        .getType(),
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_DEFAULT_VALUE,
                        prop);

        if (defaultTextValue != null && defaultTextValue instanceof String) {
            defaultVal = (String) defaultTextValue;
        }

        // Max text length
        Object maxTextLength =
                PrimitivesUtil.getFacetPropertyValue((PrimitiveType) prop
                        .getType(),
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_LENGTH,
                        prop);

        if (maxTextLength != null && maxTextLength instanceof Integer) {
            maxLen = (Integer) maxTextLength;
        }

        // Pattern value
        Object objPatternValue =
                PrimitivesUtil.getFacetPropertyValue((PrimitiveType) prop
                        .getType(),
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_PATTERN_VALUE,
                        prop);

        if (objPatternValue != null && ((String) objPatternValue) != "") {
            patternVal = (String) objPatternValue;
        }

        /*
         * Now do some checks
         */
        if ((defaultVal != null && defaultVal != "")
                && (maxLen != null && maxLen != -1)) {
            if (defaultVal.length() > Integer.valueOf(maxLen)) {
                createIssue(scope,
                        prop,
                        XSD_PROPERTY_CONSTRAINTS_MAXTEXTLEN_ISSUE,
                        null);

            }

            if (patternVal != null && patternVal != "") {
                PatternMatcher pm =
                        XMLTypeUtil.createPatternMatcher(patternVal);

                if (!pm.matches(defaultVal)) {
                    createIssue(scope,
                            prop,
                            XSD_PROPERTY_CONSTRAINTS_PATTERNMISMATCH_ISSUE,
                            null);
                }
            }

        }

    }

    /**
     * @param scope
     * @param prop
     * @param messageID
     * @param messageList
     */
    private void createIssue(IValidationScope scope, Property prop,
            String messageID, List<String> messageList) {

        if (messageList == null) {
            scope.createIssue(messageID, prop.getLabel(), prop.eResource()
                    .getURIFragment(prop));

        } else {
            scope.createIssue(messageID, prop.getLabel(), prop.eResource()
                    .getURIFragment(prop), messageList);
        }

    }

    /**
     * @param prop
     * @param pt
     * @param scope
     */
    private void validateInteger(Property prop, PrimitiveType pt,
            IValidationScope scope) {

        BigInteger lowerLimit = null;
        BigInteger upperLimit = null;
        BigInteger defaultVal = null;
        Integer numLen = null;
        EnumerationLiteral subType = null;

        // Initialize values
        // Lower limit
        Object objLowerLimit =
                PrimitivesUtil.getFacetPropertyValue((PrimitiveType) prop
                        .getType(),
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LOWER,
                        prop);

        if ((objLowerLimit != null) && (objLowerLimit instanceof String)) {
            if (!((String) objLowerLimit).isEmpty()) {
                lowerLimit = new BigInteger((String) objLowerLimit);
            }
        }

        // Upper Limit
        Object objUpperLimit =
                PrimitivesUtil.getFacetPropertyValue((PrimitiveType) prop
                        .getType(),
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_UPPER,
                        prop);

        if ((objUpperLimit != null) && (objUpperLimit instanceof String)) {
            if (!((String) objUpperLimit).isEmpty()) {
                upperLimit = new BigInteger((String) objUpperLimit);
            }
        }

        // Default Value
        Object defaultIntegerValue =
                PrimitivesUtil
                        .getFacetPropertyValue((PrimitiveType) prop.getType(),
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_DEFAULT_VALUE,
                                prop);

        if ((defaultIntegerValue != null)
                && (defaultIntegerValue instanceof String)) {
            if (!((String) defaultIntegerValue).isEmpty()) {
                defaultVal = new BigInteger((String) defaultIntegerValue);
            }
        }

        // Subtype
        Object objSubType =
                PrimitivesUtil.getFacetPropertyValue((PrimitiveType) prop
                        .getType(),
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_SUBTYPE,
                        prop);

        if (objSubType != null) {
            subType = (EnumerationLiteral) objSubType;

            if (subType.getName()
                    .equals(PrimitivesUtil.INTEGER_SUBTYPE_FIXEDLENGTH)) {

                // Retrieve number length
                Object objNumLen =
                        PrimitivesUtil
                                .getFacetPropertyValue((PrimitiveType) prop
                                        .getType(),
                                        PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LENGTH,
                                        prop);

                if (objNumLen != null && objNumLen instanceof Integer
                        && (Integer) objNumLen != -1) {
                    numLen = (Integer) objNumLen;
                }
            }
        }

        /*
         * Now perform some checks
         */
        // If both limits set
        if (lowerLimit != null && upperLimit != null) {
            if (upperLimit.compareTo(lowerLimit) == -1) {
                createIssue(scope,
                        prop,
                        XSD_PROPERTY_CONSTRAINTS_LOWEREXCEEDSUPPERLIMIT_ISSUE,
                        null);
            }
        }

        // Check limits against default value
        if (defaultVal != null) {
            if (lowerLimit != null) {
                if (lowerLimit.compareTo(defaultVal) == 1) {
                    createIssue(scope,
                            prop,
                            XSD_PROPERTY_CONSTRAINTS_LOWERLIMITEXCEEDSDEFAULT_ISSUE,
                            null);
                }

            }

            if (upperLimit != null) {
                if (upperLimit.compareTo(defaultVal) == -1) {
                    createIssue(scope,
                            prop,
                            XSD_PROPERTY_CONSTRAINTS_DEFAULTEXCEEDSUPPERLIMIT_ISSUE,
                            null);
                }
            }

        }

        // Fixed Length Specific tests i.e. NUMBER LENGTH
        if (subType != null
                && subType.getName()
                        .equals(PrimitivesUtil.INTEGER_SUBTYPE_FIXEDLENGTH)
                && numLen != null && numLen > 0) {

            // Check Lower Limit
            // Removed for XPD-3838

            // Check Upper Limit
            // Removed for XPD-3838

            // Check default value
            if (defaultVal != null) {
                if (defaultVal.compareTo(new BigInteger("0")) >= 0) {
                    // For positive values
                    if (String.valueOf(defaultVal).length() > numLen) {
                        createIssue(scope,
                                prop,
                                XSD_PROPERTY_CONSTRAINTS_DEFVALLENEXCEEDSNUMLEN_ISSUE,
                                null);
                    }
                } else {
                    // For negative values
                    if (((String.valueOf(defaultVal).length()) - 1) > numLen) {
                        createIssue(scope,
                                prop,
                                XSD_PROPERTY_CONSTRAINTS_DEFVALLENEXCEEDSNUMLEN_ISSUE,
                                null);
                    }
                }

            }

        }

    }

    /**
     * @param prop
     * @param pt
     * @param scope
     */
    private void validateDecimal(Property prop, PrimitiveType pt,
            IValidationScope scope) {

        BigDecimal lowerLimit = null;
        BigDecimal upperLimit = null;
        Boolean maxInclusive = false;
        Boolean minInclusive = false;
        BigDecimal defaultVal = null;
        Integer numLen = null;
        EnumerationLiteral subType = null;
        Integer decPlaces = null;

        // Initialize values
        // Lower limit
        Object objLowerLimit =
                PrimitivesUtil.getFacetPropertyValue((PrimitiveType) prop
                        .getType(),
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LOWER,
                        prop);

        if (objLowerLimit != null) {
            lowerLimit = new BigDecimal((String) objLowerLimit);
        }

        // Upper Limit
        Object objUpperLimit =
                PrimitivesUtil.getFacetPropertyValue((PrimitiveType) prop
                        .getType(),
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_UPPER,
                        prop);

        if (objUpperLimit != null) {
            upperLimit = new BigDecimal((String) objUpperLimit);
        }

        // max inclusive
        Object objMaxInclusive =
                PrimitivesUtil
                        .getFacetPropertyValue((PrimitiveType) prop.getType(),
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_UPPER_INCLUSIVE,
                                prop);

        if (objMaxInclusive != null) {
            maxInclusive = (Boolean) objMaxInclusive;
        }

        // min inclusive
        Object objMinInclusive =
                PrimitivesUtil
                        .getFacetPropertyValue((PrimitiveType) prop.getType(),
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LOWER_INCLUSIVE,
                                prop);

        if (objMinInclusive != null) {
            minInclusive = (Boolean) objMinInclusive;
        }

        // Default Value
        Object objDefaultDecimalValue =
                PrimitivesUtil
                        .getFacetPropertyValue((PrimitiveType) prop.getType(),
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_DEFAULT_VALUE,
                                prop);

        if (objDefaultDecimalValue != null
                && objDefaultDecimalValue instanceof String) {

            String strObjDefVal = (String) objDefaultDecimalValue;

            if (strObjDefVal != "") {
                defaultVal = new BigDecimal((String) objDefaultDecimalValue);
            }
        }

        // Subtype
        Object objSubType =
                PrimitivesUtil.getFacetPropertyValue((PrimitiveType) prop
                        .getType(),
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_SUBTYPE,
                        prop);

        if (objSubType != null) {
            subType = (EnumerationLiteral) objSubType;

            if (subType.getName()
                    .equals(PrimitivesUtil.DECIMAL_SUBTYPE_FIXEDPOINT)) {

                // Retrieve number length
                Object objNumLen =
                        PrimitivesUtil
                                .getFacetPropertyValue((PrimitiveType) prop
                                        .getType(),
                                        PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LENGTH,
                                        prop);

                if (objNumLen != null && objNumLen instanceof Integer
                        && (Integer) objNumLen != -1) {

                    numLen = (Integer) objNumLen;
                }

                // Retrieve decimal places
                Object objDecPlaces =
                        PrimitivesUtil
                                .getFacetPropertyValue((PrimitiveType) prop
                                        .getType(),
                                        PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_PLACES,
                                        prop);

                if (objNumLen != null && objNumLen instanceof Integer
                        && (Integer) objNumLen != -1) {
                    decPlaces = (Integer) objDecPlaces;
                }

            }
        }

        /*
         * Now perform some checks
         */
        // If both limits set
        if (lowerLimit != null && upperLimit != null) {
            if (upperLimit.compareTo(lowerLimit) == -1) {
                createIssue(scope,
                        prop,
                        XSD_PROPERTY_CONSTRAINTS_LOWEREXCEEDSUPPERLIMIT_ISSUE,
                        null);
            }
        }

        // Check limits against default value
        if (defaultVal != null) {
            if (lowerLimit != null) {
                if (minInclusive) {
                    if (lowerLimit.compareTo(defaultVal) == 1) {
                        createIssue(scope,
                                prop,
                                XSD_PROPERTY_CONSTRAINTS_LOWERLIMITEXCEEDSDEFAULT_ISSUE,
                                null);
                    }
                } else {
                    if (lowerLimit.compareTo(defaultVal) == 1
                            || lowerLimit.compareTo(defaultVal) == 0) {
                        createIssue(scope,
                                prop,
                                XSD_PROPERTY_CONSTRAINTS_LOWERLIMITEXCEEDSDEFAULT_ISSUE,
                                null);
                    }
                }
            }

            if (upperLimit != null) {
                if (maxInclusive) {
                    if (upperLimit.compareTo(defaultVal) == -1) {
                        createIssue(scope,
                                prop,
                                XSD_PROPERTY_CONSTRAINTS_DEFAULTEXCEEDSUPPERLIMIT_ISSUE,
                                null);
                    }
                } else {
                    if (upperLimit.compareTo(defaultVal) == -1
                            || upperLimit.compareTo(defaultVal) == 0) {
                        createIssue(scope,
                                prop,
                                XSD_PROPERTY_CONSTRAINTS_DEFAULTEXCEEDSUPPERLIMIT_ISSUE,
                                null);
                    }
                }
            }
        }

        // Fixed Point Specific tests i.e. NUMBER LENGTH and DECIMAL PLACES
        if (subType != null
                && subType.getName()
                        .equals(PrimitivesUtil.DECIMAL_SUBTYPE_FIXEDPOINT)) {

            int adjustForDecimalPlace = 0;
            int adjustmentForLeadingZero = 0;

            // Checks for number length
            if (numLen != null && numLen > 0) {
                // Check Lower Limit
                if (lowerLimit != null) {
                    String strLowerLimit = lowerLimit.toString();

                    if (strLowerLimit.contains(".")) {
                        adjustForDecimalPlace = -1;

                        // and strip the leading zeros
                        if (strLowerLimit.startsWith("0.")
                                || strLowerLimit.startsWith("-0.")) {
                            adjustmentForLeadingZero = -1;
                        }
                    }

                    if (lowerLimit.compareTo(new BigDecimal(0)) >= 0) {
                        // For positive values
                        if ((strLowerLimit.length() + adjustForDecimalPlace + adjustmentForLeadingZero) > numLen) {
                            createIssue(scope,
                                    prop,
                                    XSD_PROPERTY_CONSTRAINTS_LOWERMIMITLENEXCCEEDSNUMLEN_ISSUE,
                                    null);
                        }

                    } else {
                        // For negative values
                        if ((strLowerLimit.length() - 1 + adjustForDecimalPlace + adjustmentForLeadingZero) > numLen) {
                            createIssue(scope,
                                    prop,
                                    XSD_PROPERTY_CONSTRAINTS_LOWERMIMITLENEXCCEEDSNUMLEN_ISSUE,
                                    null);
                        }
                    }
                }

                // Check Upper Limit
                if (upperLimit != null) {
                    String strUpperLimit = upperLimit.toString();
                    if (strUpperLimit.contains(".")) {
                        adjustForDecimalPlace = -1;

                        // and strip the leading zeros
                        if (strUpperLimit.startsWith("0.")
                                || strUpperLimit.startsWith("-0.")) {
                            adjustmentForLeadingZero = -1;
                        }
                    }

                    if (upperLimit.compareTo(new BigDecimal(0)) >= 0) {
                        // For positive values
                        if ((strUpperLimit.length() + adjustForDecimalPlace + adjustmentForLeadingZero) > numLen) {
                            createIssue(scope,
                                    prop,
                                    XSD_PROPERTY_CONSTRAINTS_UPPERLIMITLENEXCEEDSNUMLEN_ISSUE,
                                    null);
                        }
                    } else {
                        // For negative values
                        if ((strUpperLimit.length() - 1 + adjustForDecimalPlace + adjustmentForLeadingZero) > numLen) {
                            createIssue(scope,
                                    prop,
                                    XSD_PROPERTY_CONSTRAINTS_UPPERLIMITLENEXCEEDSNUMLEN_ISSUE,
                                    null);
                        }
                    }
                }

                // Check default value
                if (defaultVal != null) {

                    String strDefaultVal = defaultVal.toString();
                    if (strDefaultVal.contains(".")) {
                        adjustForDecimalPlace = -1;

                        // and strip the leading zeros
                        if (strDefaultVal.startsWith("0.")
                                || strDefaultVal.startsWith("-0.")) {
                            adjustmentForLeadingZero = -1;
                        }

                    }
                    if (defaultVal.compareTo(new BigDecimal(0)) >= 0) {
                        // For positive values
                        if ((strDefaultVal.length() + adjustForDecimalPlace + adjustmentForLeadingZero) > numLen) {
                            createIssue(scope,
                                    prop,
                                    XSD_PROPERTY_CONSTRAINTS_DEFVALLENEXCEEDSNUMLEN_ISSUE,
                                    null);
                        }
                    } else {
                        // For negative values
                        if ((strDefaultVal.length() - 1 + adjustForDecimalPlace + adjustmentForLeadingZero) > numLen) {
                            createIssue(scope,
                                    prop,
                                    XSD_PROPERTY_CONSTRAINTS_DEFVALLENEXCEEDSNUMLEN_ISSUE,
                                    null);
                        }
                    }
                }
            }

            // Finally check decimal places
            if (decPlaces != null && decPlaces > -1) {
                // Check Lower Limit
                if (lowerLimit != null) {
                    String strLowerLimit = lowerLimit.toString();
                    String strDecimals = "";
                    int lowerLimitDecPlaces = 0;

                    if (strLowerLimit.contains(".")) {
                        strDecimals =
                                strLowerLimit.substring(strLowerLimit
                                        .indexOf(".") + 1);

                        lowerLimitDecPlaces = strDecimals.length();

                    }

                    if (lowerLimitDecPlaces > decPlaces) {
                        createIssue(scope,
                                prop,
                                XSD_PROPERTY_CONSTRAINTS_LOWERLIMITDECPLACES_ISSUE,
                                null);
                    }
                }

                // Check Upper Limit
                if (upperLimit != null) {
                    String strUpperLimit = upperLimit.toString();
                    String strDecimals = "";
                    int upperLimitDecPlaces = 0;

                    if (strUpperLimit.contains(".")) {
                        strDecimals =
                                strUpperLimit.substring(strUpperLimit
                                        .indexOf(".") + 1);

                        upperLimitDecPlaces = strDecimals.length();

                    }

                    if (upperLimitDecPlaces > decPlaces) {
                        createIssue(scope,
                                prop,
                                XSD_PROPERTY_CONSTRAINTS_UPPERLIMITDECPLACES_ISSUE,
                                null);
                    }
                }

                // Check Default Value
                if (defaultVal != null) {
                    String strDefaultVal = defaultVal.toString();
                    String strDecimals = "";
                    int defValueDecPlaces = 0;

                    if (strDefaultVal.contains(".")) {
                        strDecimals =
                                strDefaultVal.substring(strDefaultVal
                                        .indexOf(".") + 1);

                        defValueDecPlaces = strDecimals.length();

                    }

                    if (defValueDecPlaces > decPlaces) {
                        createIssue(scope,
                                prop,
                                XSD_PROPERTY_CONSTRAINTS_DEFVALDECPLACES_ISSUE,
                                null);
                    }
                }

                // Check that decimal places does not exceed number length
                if (decPlaces > numLen) {
                    createIssue(scope,
                            prop,
                            XSD_PROPERTY_CONSTRAINTS_DECPLACESEXCEEDSNUMLEN_ISSUE,
                            null);
                }

            }

        }

    }
}
