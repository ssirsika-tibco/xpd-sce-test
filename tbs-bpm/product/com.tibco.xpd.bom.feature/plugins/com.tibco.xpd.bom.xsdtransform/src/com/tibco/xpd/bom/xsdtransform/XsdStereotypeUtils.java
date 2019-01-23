package com.tibco.xpd.bom.xsdtransform;

import java.util.Iterator;
import java.util.List;

import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.xsd.XSDForm;

/**
 * @author glewis
 * 
 */
public class XsdStereotypeUtils {

    public static final String XSD_DIRTY = "xsdDirty"; //$NON-NLS-1$

    public static final String XSD_TARGET_NAMESPACE = "xsdTargetNamespace"; //$NON-NLS-1$

    public static final String XSD_SCHEMA_LOCATION = "xsdSchemaLocation"; //$NON-NLS-1$

    public static final String XSD_STUDIO_VERSION = "xsdStudioVersion"; //$NON-NLS-1$

    public static final String XSD_IMPORTED_SCHEMAS = "xsdImportedSchemas"; //$NON-NLS-1$    

    public static final String XSD_DOCUMENT_ROOT = "xsdDocumentRoot"; //$NON-NLS-1$

    public static final String XSD_BASED_MODEL = "XsdBasedModel"; //$NON-NLS-1$

    public static final String XSD_BASED_CLASS = "XsdBasedClass"; //$NON-NLS-1$

    public static final String XSD_BASED_ELEMENT = "XsdBasedElement"; //$NON-NLS-1$

    public static final String XSD_BASED_PROPERTY = "XsdBasedProperty"; //$NON-NLS-1$

    public static final String XSD_ELEMENT = "XsdElement"; //$NON-NLS-1$    

    public static final String XSD_ATTRIBUTE = "XsdAttribute"; //$NON-NLS-1$

    public static final String TOP_LEVEL_ELEMENTS = "TopLevelElements"; //$NON-NLS-1$

    public static final String TOP_LEVEL_ELEMENT = "TopLevelElement"; //$NON-NLS-1$

    public static final String TOP_LEVEL_ATTRIBUTES = "TopLevelAttributes"; //$NON-NLS-1$

    public static final String TOP_LEVEL_ATTRIBUTE = "TopLevelAttribute"; //$NON-NLS-1$

    public static final String XSD_BASED_ENUMERATION = "XsdBasedEnumeration"; //$NON-NLS-1$

    public static final String XSD_BASED_RESTRICTION = "XsdBasedRestriction"; //$NON-NLS-1$

    public static final String XSD_BASED_UNION = "XsdBasedUnion"; //$NON-NLS-1$

    public static final String XSD_SEQUENCE = "XsdSequence"; //$NON-NLS-1$

    public static final String XSD_BASED_PRIMITIVETYPE =
            "XsdBasedPrimitiveType"; //$NON-NLS-1$

    public static final String XSD_BASED_ENUMERATION_LITERAL =
            "XsdBasedEnumerationLiteral"; //$NON-NLS-1$

    public static final String XSD_NOTATION_PROFILE_NAME = "XsdNotationProfile"; //$NON-NLS-1$

    public final static String XSD_MODEL_ELEMENT_FORM_DEFAULT =
            "xsdElementForm"; //$NON-NLS-1$

    public final static String XSD_MODEL_ATTRIBUTE_FORM_DEFAULT =
            "xsdAttributeForm"; //$NON-NLS-1$

    public final static String XSD_PROPERTY_NAME = "xsdName"; //$NON-NLS-1$

    public final static String XSD_PROPERTY_TYPE = "xsdType"; //$NON-NLS-1$

    public final static String XSD_PROPERTY_FORM = "xsdForm"; //$NON-NLS-1$

    public final static String XSD_FORM_QUALIFIED = XSDForm.QUALIFIED_LITERAL
            .getLiteral();

    public final static String XSD_FORM_UNQUALIFIED =
            XSDForm.UNQUALIFIED_LITERAL.getLiteral();

    public final static String XSD_PROPERTY_DEFAULT = "xsdDefault"; //$NON-NLS-1$

    public final static String XSD_PROPERTY_ID = "xsdId"; //$NON-NLS-1$

    public final static String XSD_PROPERTY_FIXED = "xsdFixed"; //$NON-NLS-1$

    public final static String XSD_PROPERTY_MIXED = "xsdMixed"; //$NON-NLS-1$

    public final static String XSD_PROPERTY_REF = "xsdRef"; //$NON-NLS-1$

    public final static String XSD_PROPERTY_USE = "xsdUse"; //$NON-NLS-1$

    public final static String XSD_PROPERTY_IS_SIMPLE_CONTENT_EXTENSION =
            "xsdIsSimpleContentExtension"; //$NON-NLS-1$

    public final static String XSD_PROPERTY_IS_SIMPLE_CONTENT_RESTRICTION =
            "xsdIsSimpleContentRestriction"; //$NON-NLS-1$

    public final static String XSD_PROPERTY_ABSTRACT = "xsdAbstract"; //$NON-NLS-1$

    public final static String XSD_PROPERTY_BLOCK = "xsdBlock"; //$NON-NLS-1$

    public final static String XSD_PROPERTY_FINAL = "xsdFinal"; //$NON-NLS-1$

    public final static String XSD_PROPERTY_ENUM_LITERALS = "xsdEnumLiterals"; //$NON-NLS-1$

    public final static String XSD_PROPERTY_MIN_OCCURS = "xsdMinOccurs"; //$NON-NLS-1$

    public final static String XSD_PROPERTY_MAX_OCCURS = "xsdMaxOccurs"; //$NON-NLS-1$

    public final static String XSD_PROPERTY_NILLABLE = "xsdNillable"; //$NON-NLS-1$

    public final static String XSD_PROPERTY_IS_ATTRIBUTE = "xsdIsAttribute"; //$NON-NLS-1$

    public final static String XSD_PROPERTY_NAMESPACE = "xsdNamespace"; //$NON-NLS-1$

    public final static String XSD_PROPERTY_PROCESS_CONTENTS =
            "xsdProcessContents"; //$NON-NLS-1$

    public final static String XSD_PROPERTY_IS_COMPLEX_TYPE =
            "xsdIsComplexType"; //$NON-NLS-1$   

    public final static String XSD_PROPERTY_IS_GROUP = "xsdIsGroup"; //$NON-NLS-1$    

    public final static String XSD_PROPERTY_EXPLICIT_GROUP_HIERARCHY =
            "xsdExplicitGroupHierarchy"; //$NON-NLS-1$

    public final static String XSD_PROPERTY_IS_ATTRIBUTE_GROUP =
            "xsdIsAttributeGroup"; //$NON-NLS-1$

    public final static String XSD_PROPERTY_SUBSTITUTION_GROUP =
            "xsdSubstitutionGroup"; //$NON-NLS-1$

    public final static String XSD_PROPERTY_SIMPLETYPE_NAME =
            "xsdSimpleTypeName"; //$NON-NLS-1$

    public final static String XSD_PROPERTY_SIMPLETYPE_ID = "xsdSimpleTypeId"; //$NON-NLS-1$

    public final static String XSD_PROPERTY_SIMPLETYPE_FINAL =
            "xsdSimpleTypeFinal"; //$NON-NLS-1$

    public final static String XSD_PROPERTY_RESTRICTION_ID = "xsdRestrictionId"; //$NON-NLS-1$

    public final static String XSD_PROPERTY_RESTRICTION_BASE =
            "xsdRestrictionBase"; //$NON-NLS-1$

    public final static String XSD_PROPERTY_IS_ANON_UNION_SIMPLE_TYPE =
            "xsdIsAnonUnionSimpleType"; //$NON-NLS-1$

    public final static String XSD_PROPERTY_IS_STANDARD_UNION_SIMPLE_TYPE =
            "xsdIsStandardUnionSimpleType"; //$NON-NLS-1$

    public final static String XSD_PROPERTY_VALUE = "xsdValue"; //$NON-NLS-1$

    public final static String XSD_PROPERTY_IS_ANON_CONTAINER =
            "xsdIsAnonContainer"; //$NON-NLS-1$

    public final static String XSD_PROPERTY_IS_RESTRICTION = "xsdIsRestriction"; //$NON-NLS-1$

    public final static String XSD_PROPERTY_IS_ANON_TYPE = "xsdIsAnonType"; //$NON-NLS-1$

    public final static String XSD_PROPERTY_IS_ALL_MULTIPLICITY_SET =
            "xsdIsAllMultiplicitySet"; //$NON-NLS-1$

    public final static String XSD_PROPERTY_SEQUENCE_LIST = "xsdSequenceList"; //$NON-NLS-1$

    public final static String XSD_PROPERTY_PARENT_SEQUENCE =
            "xsdParentSequence"; //$NON-NLS-1$

    public final static String XSD_MAX_LENGTH_ID = "xsdMaxLengthId"; //$NON-NLS-1$

    public final static String XSD_MAX_LENGTH_FIXED = "xsdMaxLengthFixed"; //$NON-NLS-1$

    public final static String XSD_MAX_LENGTH_VALUE = "xsdMaxLengthValue"; //$NON-NLS-1$

    public final static String XSD_FRACTION_DIGITS_ID = "xsdFractionDigitsId"; //$NON-NLS-1$

    public final static String XSD_FRACTION_DIGITS_FIXED =
            "xsdFractionDigitsFixed"; //$NON-NLS-1$

    public final static String XSD_FRACTION_DIGITS_VALUE =
            "xsdFractionDigitsValue"; //$NON-NLS-1$

    public final static String XSD_LENGTH_ID = "xsdLengthId"; //$NON-NLS-1$

    public final static String XSD_LENGTH_FIXED = "xsdLengthFixed"; //$NON-NLS-1$

    public final static String XSD_LENGTH_VALUE = "xsdLengthValue"; //$NON-NLS-1$

    public final static String XSD_MAX_EXCLUSIVE_ID = "xsdMaxExclusiveId"; //$NON-NLS-1$

    public final static String XSD_MAX_EXCLUSIVE_FIXED = "xsdMaxExclusiveFixed"; //$NON-NLS-1$

    public final static String XSD_MAX_EXCLUSIVE_VALUE = "xsdMaxExclusiveValue"; //$NON-NLS-1$

    public final static String XSD_MAX_INCLUSIVE_ID = "xsdMaxInclusiveId"; //$NON-NLS-1$

    public final static String XSD_MAX_INCLUSIVE_FIXED = "xsdMaxInclusiveFixed"; //$NON-NLS-1$

    public final static String XSD_MAX_INCLUSIVE_VALUE = "xsdMaxInclusiveValue"; //$NON-NLS-1$

    public final static String XSD_MIN_EXCLUSIVE_ID = "xsdMinExclusiveId"; //$NON-NLS-1$

    public final static String XSD_MIN_EXCLUSIVE_FIXED = "xsdMinExclusiveFixed"; //$NON-NLS-1$

    public final static String XSD_MIN_EXCLUSIVE_VALUE = "xsdMinExclusiveValue"; //$NON-NLS-1$

    public final static String XSD_MIN_INCLUSIVE_ID = "xsdMinInclusiveId"; //$NON-NLS-1$

    public final static String XSD_MIN_INCLUSIVE_FIXED = "xsdMinInclusiveFixed"; //$NON-NLS-1$

    public final static String XSD_MIN_INCLUSIVE_VALUE = "xsdMinInclusiveValue"; //$NON-NLS-1$

    public final static String XSD_MIN_LENGTH_ID = "xsdMinLengthId"; //$NON-NLS-1$

    public final static String XSD_MIN_LENGTH_FIXED = "xsdMinLengthFixed"; //$NON-NLS-1$

    public final static String XSD_MIN_LENGTH_VALUE = "xsdMinLengthValue"; //$NON-NLS-1$

    public final static String XSD_PATTERN_ID = "xsdPatternId"; //$NON-NLS-1$

    public final static String XSD_PATTERN_FIXED = "xsdPatternFixed"; //$NON-NLS-1$

    public final static String XSD_PATTERN_VALUE = "xsdPatternValue"; //$NON-NLS-1$

    public final static String XSD_TOTAL_DIGITS_ID = "xsdTotalDigitsId"; //$NON-NLS-1$

    public final static String XSD_TOTAL_DIGITS_FIXED = "xsdTotalDigitsFixed"; //$NON-NLS-1$

    public final static String XSD_TOTAL_DIGITS_VALUE = "xsdTotalDigitsValue"; //$NON-NLS-1$

    public final static String XSD_WHITESPACE_ID = "xsdWhitespaceId"; //$NON-NLS-1$

    public final static String XSD_WHITESPACE_FIXED = "xsdWhitespaceFixed"; //$NON-NLS-1$

    public final static String XSD_WHITESPACE_VALUE = "xsdWhitespaceValue"; //$NON-NLS-1$

    public final static String XSD_UNION_MEMBER_TYPES = "xsdMemberTypes"; //$NON-NLS-1$

    public final static String XSD_TOP_LEVEL_ELEMENT_ELEMENTS = "elements"; //$NON-NLS-1$

    public final static String XSD_ELEMENT_NAME = "elements"; //$NON-NLS-1$

    public final static String XSD_TOP_LEVEL_ATTRIBUTE_ATTRIBUTES =
            "attributes"; ////$NON-NLS-1$

    public final static String SCHEMA_LOCATION_DELIMITER = ";"; //$NON-NLS-1$

    public static final String W3_ORG_SCHEMA_NAMESPACE =
            "http://www.w3.org/2001/XMLSchema"; //$NON-NLS-1$

    /**
     * @param property
     * 
     * @return The value for a particular stereotype on the given bom property
     *         if it is defined or <code>null</code> if not defined.
     */
    public static Object getValueForStereotype(Property property,
            String stereoTypeName, String valueName) {

        Stereotype xsdPropertyTypeStereoType =
                getStereotype(property, stereoTypeName);

        if (null != xsdPropertyTypeStereoType) {
            Object xsdPropertyTypeObj =
                    property.getValue(xsdPropertyTypeStereoType, valueName);
            return xsdPropertyTypeObj;
        }
        return null;
    }

    /**
     * 
     * @param property
     * @param stereoTypeName
     * 
     * @return given StereoType or <code>null</code> if not applied to property.
     */
    public static Stereotype getStereotype(Property property,
            String stereoTypeName) {
        List<Stereotype> appliedStereotypes = property.getAppliedStereotypes();

        for (Stereotype stereotype : appliedStereotypes) {
            if (stereoTypeName.equals(stereotype.getName())) {
                return stereotype;
            }
        }
        return null;
    }

    /**
     * Gets an XSD Notation Profile stereotype value for a given element
     * 
     * @param model
     * @param element
     * @param stereotypeName
     * @return
     */
    public static Stereotype getAppliedXsdStereotype(Model model,
            Element element, String stereotypeName) {
        if (model != null) {
            Stereotype stereotype = null;
            Iterator<Profile> profilesIter =
                    model.getAppliedProfiles().iterator();
            while (profilesIter.hasNext()) {
                Profile profile = profilesIter.next();
                if (profile.getName()
                        .equals(XsdStereotypeUtils.XSD_NOTATION_PROFILE_NAME)) {
                    Object obj = profile.getPackagedElement(stereotypeName);
                    if (obj instanceof Stereotype) {
                        stereotype = (Stereotype) obj;
                        if (stereotype.getName().equals(stereotypeName)) {
                            Stereotype alreadyAppliedStereotype =
                                    element.getAppliedStereotype(stereotype
                                            .getQualifiedName());
                            return alreadyAppliedStereotype;
                        }
                    }
                }
            }
        }
        return null;
    }
}
