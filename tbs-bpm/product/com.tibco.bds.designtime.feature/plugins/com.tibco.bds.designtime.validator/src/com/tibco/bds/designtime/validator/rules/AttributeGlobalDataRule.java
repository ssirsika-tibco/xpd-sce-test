package com.tibco.bds.designtime.validator.rules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.bds.designtime.generator.common.BDSConstants;
import com.tibco.bds.designtime.validator.CDSIssueIds;
import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Handles all validations for Attributes in a global data BOM
 * 
 */
public class AttributeGlobalDataRule implements IValidationRule {

    // Maximum number of characters allowed in an attribute name
    private static final int MAX_ATTRIBUTE_NAME_SIZE = 24;

    private static final String reservedAttributeNames[] = {
            BDSConstants.BDS_ID_COLNAME, BDSConstants.BDS_OWNER_ID_COLNAME,
            BDSConstants.BDS_VERSION_COLNAME,
            BDSConstants.BDS_CASE_TYPE_COLNAME };

    /**
     * @see com.tibco.xpd.validation.rules.IValidationRule#getTargetClass()
     * 
     * @return
     */
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

        // Only handle UML Property types as they are attributes in the BOM
        if (o instanceof Property) {
            Property prop = (Property) o;

            org.eclipse.uml2.uml.Class class_ = prop.getClass_();
            if (class_ == null) {
                return;
            }

            // Check if this is an attribute on one of the global data classes
            if (BOMGlobalDataUtils.isCaseClass(class_)
                    || BOMGlobalDataUtils.isGlobalClass(class_)) {
                // Start by checking that the name rules are followed for Global
                // Data BOMs
                checkAttributeName(scope, prop);
                // Check that the type information is correct for Global Data
                // BOMs
                checkAttributeType(scope, prop);

                // Check for name clashes in inheritance
                checkAttributeNameClash(scope, prop);
            }
        }
    }

    /**
     * Check restrictions that we have for attribute names
     * 
     * @param scope
     * @param prop
     *            UML Property object
     */
    private void checkAttributeName(IValidationScope scope, Property prop) {
        String attribName = prop.getName();

        if (attribName == null) {
            return;
        }

        // For global data BOMs we have to restrict some names as they
        // are auto-generate by Teneo
        String upperCaseAttribName = attribName.toUpperCase();
        if (upperCaseAttribName.endsWith("_IDX")
                || upperCaseAttribName.endsWith("_BDSID")) {
            scope.createIssue(CDSIssueIds.ATTRIBUTE_GLOBAL_DB_CLASH_ENDING,
                    BOMValidationUtil.getLocation(prop),
                    prop.eResource().getURIFragment(prop));
        }

        // Check for reserved names
        for (String reservedWord : reservedAttributeNames) {
            if (upperCaseAttribName.compareToIgnoreCase(reservedWord) == 0) {
                scope.createIssue(CDSIssueIds.NAME_ILLEGAL_ATTRIBUTE_RESERVED_WORD,
                        BOMValidationUtil.getLocation(prop),
                        prop.eResource().getURIFragment(prop),
                        Collections.singleton(attribName));
            }
        }

        // Make sure the length of the names is restricted
        // This is because there is a direct mapping from attribute to column
        // name
        // and some databases do not allow columns over a given size
        if (attribName.length() > MAX_ATTRIBUTE_NAME_SIZE) {
            String strSize = Integer.toString(MAX_ATTRIBUTE_NAME_SIZE);
            scope.createIssue(CDSIssueIds.ATTRIBUTE_GLOBAL_NAME_SIZE,
                    BOMValidationUtil.getLocation(prop),
                    prop.eResource().getURIFragment(prop),
                    Collections.singleton(strSize));
        }
    }

    /**
     * Check restrictions that we have for attribute types
     * 
     * @param scope
     * @param prop
     *            UML Property object
     */
    private void checkAttributeType(IValidationScope scope, Property prop) {
        Type type = prop.getType();

        /* Sid ACE-2505 - handle max length 400 constraint for enumeration values */
        boolean isSearchable = BOMGlobalDataUtils.isSearchable(prop);
        boolean isStateAttribute = BOMGlobalDataUtils.isCaseState(prop);

        if (type instanceof Enumeration) {
            if (isSearchable || isStateAttribute) {
                int maxEnumLength = 0;

                EList<EnumerationLiteral> enumLiterals = ((Enumeration) type).getOwnedLiterals();

                if (enumLiterals != null) {
                    for (EnumerationLiteral literal : enumLiterals) {
                        String name = literal.getName();
                        if (name != null) {
                            maxEnumLength = Math.max(maxEnumLength, name.length());
                        }
                    }
                }

                if (maxEnumLength > BDSConstants.CASE_DATA_STORE_DEFAULT_MINIMUM_STRING_LENGTH) {
                    String strLength = Integer.toString(BDSConstants.CASE_DATA_STORE_DEFAULT_MINIMUM_STRING_LENGTH);
                    scope.createIssue(CDSIssueIds.ATTRIBUTE_GLOBAL_TYPE_SEARCHABLE_ENUM_LENGTH,
                            BOMValidationUtil.getLocation(prop),
                            prop.eResource().getURIFragment(prop),
                            Collections.singleton(strLength));
                }
            }
        }

        // Else Only deal with Primitive Types
        if (!(type instanceof PrimitiveType)) {
            return;
        }

        // Get the base type in case this is a BOM primitive type defined by
        // the user, we want to get the type of that for checking
        PrimitiveType primType =
                PrimitivesUtil.getBasePrimitiveType((PrimitiveType) type);

        // Make sure Objects are not used as attribute types
        if (PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME.equals(primType.getName())) {
            scope.createIssue(CDSIssueIds.ATTRIBUTE_GLOBAL_TYPE_OBJECT,
                    BOMValidationUtil.getLocation(prop),
                    prop.eResource().getURIFragment(prop));
        }

        // Check to see if this attribute is supported type to be search-able
        if (isSearchable) {
            if (PrimitivesUtil.BOM_PRIMITIVE_ID_NAME.equals(primType.getName())) {
                scope.createIssue(CDSIssueIds.ATTRIBUTE_GLOBAL_TYPE_NOT_SEARCHABLE,
                        BOMValidationUtil.getLocation(prop),
                        prop.eResource().getURIFragment(prop));
            }
        }

        // Handle the rules for Case Identifiers
        boolean isCID = BOMGlobalDataUtils.isCID(prop);
        if (BOMGlobalDataUtils.isAutoCID(prop)) {
            /*
             * Sid ACE-526: Auto case Id's must now be text (but this is
             * implemented by AceCaseClassRules now; So removed check from here.
             */
        } else if (isCID) {
            String typeName = primType.getName();

            if (typeName != null) {
                // Need to make sure that the CID is not any of these types:
                // Date/Time/DateTime/DateTimeTZ/Object/Attachment/Boolean/Duration/ID
                if (typeName.equals(PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME)
                        || typeName
                                .equals(PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME)
                        || typeName
                                .equals(PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME)
                        || typeName
                                .equals(PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME)
                        || typeName
                                .equals(PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME)
                        || typeName
                                .equals(PrimitivesUtil.BOM_PRIMITIVE_DURATION_NAME)
                        || typeName
                                .equals(PrimitivesUtil.BOM_PRIMITIVE_ID_NAME)) {
                    scope.createIssue(CDSIssueIds.ATTRIBUTE_GLOBAL_TYPE_NOT_CID,
                            BOMValidationUtil.getLocation(prop),
                            prop.eResource().getURIFragment(prop));
                }
            }
        }

        // Handle rules that apply for Integer types
        if (PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME
                .equals(primType.getName())) {

            Object subType =
                    PrimitivesUtil.getFacetPropertyValue(primType,
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_SUBTYPE,
                            prop);

            // Check the sub-type as we are interested in fixed length
            // integers
            if ((subType != null)
                    && (subType instanceof EnumerationLiteral)
                    && PrimitivesUtil.INTEGER_SUBTYPE_FIXEDLENGTH
                            .equals(((EnumerationLiteral) subType).getName())) {
                Object length =
                        PrimitivesUtil
                                .getFacetPropertyValue(primType,
                                        PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LENGTH,
                                        prop);

                // Make sure that the length does not exceed the supported limit
                if ((length != null) && (length instanceof Integer)) {
                    int intValue = ((Integer) length).intValue();
                    if ((intValue < 0)
                            || (intValue > BDSConstants.CASE_DATA_STORE_DEFAULT_MAXIMUM_NUMERIC_PRECISION)) {
                        String strLength =
                                Integer.toString(BDSConstants.CASE_DATA_STORE_DEFAULT_MAXIMUM_NUMERIC_PRECISION);
                        scope.createIssue(CDSIssueIds.ATTRIBUTE_GLOBAL_TYPE_NUMERIC_LENGTH,
                                BOMValidationUtil.getLocation(prop),
                                prop.eResource().getURIFragment(prop),
                                Collections.singleton(strLength));
                    }
                }
            }
        }

        // Handle rules that apply for Decimal types
        if (PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME
                .equals(primType.getName())) {

            Object subType =
                    PrimitivesUtil.getFacetPropertyValue(primType,
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_SUBTYPE,
                            prop);

            // Check the sub-type as we are interested in fixed length
            // integers
            if ((subType != null)
                    && (subType instanceof EnumerationLiteral)
                    && PrimitivesUtil.DECIMAL_SUBTYPE_FIXEDPOINT
                            .equals(((EnumerationLiteral) subType).getName())) {
                Object length =
                        PrimitivesUtil
                                .getFacetPropertyValue(primType,
                                        PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LENGTH,
                                        prop);

                // Make sure that the length does not exceed the supported limit
                if ((length != null) && (length instanceof Integer)) {
                    int intValue = ((Integer) length).intValue();
                    if ((intValue < 0)
                            || (intValue > BDSConstants.CASE_DATA_STORE_DEFAULT_MAXIMUM_NUMERIC_PRECISION)) {
                        String strLength =
                                Integer.toString(BDSConstants.CASE_DATA_STORE_DEFAULT_MAXIMUM_NUMERIC_PRECISION);
                        scope.createIssue(CDSIssueIds.ATTRIBUTE_GLOBAL_TYPE_NUMERIC_LENGTH,
                                BOMValidationUtil.getLocation(prop),
                                prop.eResource().getURIFragment(prop),
                                Collections.singleton(strLength));
                    }
                }
            }
        }

        // Handle the rules for text values
        if (PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME.equals(primType.getName())) {
            // There is a restriction on CID's and search-able values to
            // ensure they are not too large
            if (isSearchable || isCID) {
                Object length =
                        PrimitivesUtil.getFacetPropertyValue(primType,
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_LENGTH,
                                prop);
                if ((length != null) && (length instanceof Integer)) {
                    int intValue = ((Integer) length).intValue();
                    // Make sure it is not unlimited or exceeding the supported
                    // size
                    if ((intValue < 0)
                            || (intValue > BDSConstants.CASE_DATA_STORE_DEFAULT_MINIMUM_STRING_LENGTH)) {
                        String strLength =
                                Integer.toString(BDSConstants.CASE_DATA_STORE_DEFAULT_MINIMUM_STRING_LENGTH);
                        scope.createIssue(CDSIssueIds.ATTRIBUTE_GLOBAL_TYPE_SEARCHABLE_LENGTH,
                                BOMValidationUtil.getLocation(prop),
                                prop.eResource().getURIFragment(prop),
                                Collections.singleton(strLength));
                    }
                }
            }
        }
    }

    /**
     * Check for names that clash in different branches of inheritance
     * 
     * @param scope
     * @param prop
     *            Property to check for clashes
     */
    private void checkAttributeNameClash(IValidationScope scope, Property prop) {
        String attribName = prop.getName();

        // Get the Class that this is an attribute for
        org.eclipse.uml2.uml.Class containingClass = prop.getClass_();

        // Assume that this class is the root by default
        org.eclipse.uml2.uml.Class rootClass = containingClass;

        // Check if this class has a different base class
        EList<Generalization> generalisations =
                containingClass.getGeneralizations();

        // Maintain a list of classes that have already been checked, this
        // will ensure that we do not go into an infinite loop if someone
        // creates an invalid recursive generalisation
        // i.e. Class1 -> Class2 -> Class1
        List<org.eclipse.uml2.uml.Class> processedClass =
                new ArrayList<org.eclipse.uml2.uml.Class>();

        while (!generalisations.isEmpty()) {
            // Only ever actually one base class as we do not support multiple
            // inheritance
            Classifier general = generalisations.get(0).getGeneral();
            if (!(general instanceof org.eclipse.uml2.uml.Class)) {
                break; // make sure if something goes wrong we do not loop
                       // forever!
            } else if (processedClass.contains(general)) {
                // Recursive loop - stop running - there is a separate
                // validation error that will cover this
                return;
            }
            rootClass = (org.eclipse.uml2.uml.Class) general;
            // Add to the list of processed classes so we don't do it again
            processedClass.add(rootClass);
            // Check to see if this is the root
            generalisations = rootClass.getGeneralizations();
        }

        // Now that we have the root class we can work our way out to
        // ensure that there is no names that clash in any class with
        // the same base
        List<Property> matchedProps =
                getInheritedPropertiesByName(attribName, rootClass);

        // If there is more than one, there is a duplicate
        if (matchedProps.size() > 1) {
            // Need to mark all clashes
            for (Property property : matchedProps) {
                scope.createIssue(CDSIssueIds.NAME_CLASH_PROPERTY_IGNORECASE,
                        BOMValidationUtil.getLocation(property),
                        property.eResource().getURIFragment(property),
                        Collections.singleton(property.getName()));
            }
        }
    }

    /**
     * Searches the sub-class hierarchy for attributes of a given name
     * 
     * @param attribName
     *            Name of the attribute to check for
     * @param rootClass
     *            Class to start the search from
     * @return List of matching properties
     */
    private List<Property> getInheritedPropertiesByName(String attribName,
            org.eclipse.uml2.uml.Class rootClass) {
        List<Property> matchedProps = new ArrayList<Property>();

        // Check the class passed in to see if it contains an
        // attribute with the name we are looking for
        for (Property property : rootClass.getAttributes()) {
            if (attribName.compareToIgnoreCase(property.getName()) == 0) {
                matchedProps.add(property);
            }
        }

        // Need to check if there are any classes that extend this one
        for (org.eclipse.uml2.uml.Class subClass : BOMUtils
                .getDirectSubClasses(rootClass)) {
            List<Property> subMatches =
                    getInheritedPropertiesByName(attribName, subClass);
            matchedProps.addAll(subMatches);
        }

        return matchedProps;
    }
}
