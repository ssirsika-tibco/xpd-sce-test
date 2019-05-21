package com.tibco.xpd.bom.validator.rules.generic;

import java.math.BigDecimal;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.validator.GenericIssueIds;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

public class PropertyRules implements IValidationRule {

    @Override
    public Class<?> getTargetClass() {
        return Property.class;
    }

    @Override
    public void validate(IValidationScope scope, Object o) {
        if (o instanceof Property) {
            Property prop = (Property) o;

            Type type = prop.getType();

            // Check the case where we have a primitive type
            if (type != null && type instanceof PrimitiveType) {
                PrimitiveType propType = (PrimitiveType) type;

                PrimitiveType basePType =
                        PrimitivesUtil.getBasePrimitiveType(propType);

                // Check the case where we have an attribute that uses a user
                // defined primitive type
                if (basePType != propType) {
                    checkPrimitiveTypeRestrictions(scope,
                            basePType,
                            propType,
                            prop);
                }

                ResourceSet rSet =
                        XpdResourcesPlugin.getDefault().getEditingDomain()
                                .getResourceSet();

                if (basePType == PrimitivesUtil
                        .getStandardPrimitiveTypeByName(rSet,
                                PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME)) {

                    // Check Max Text Length
                    Object facetPropertyValue =
                            PrimitivesUtil
                                    .getFacetPropertyValue(propType,
                                            PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_LENGTH,
                                            prop);

                    if (facetPropertyValue instanceof Integer) {
                        Integer maxTextLen = (Integer) facetPropertyValue;

                        if (maxTextLen == 0) {
                            // create issue
                            scope.createIssue(GenericIssueIds.MAXTEXTLENGTH_ZERO,
                                    BOMValidationUtil.getLocation(prop),
                                    prop.eResource()
                                            .getURIFragment(prop));
                        }
                    }
                }
            }

            /*
             * Sid ACE-1327 We really should validate against having no type
             * selected for a property (actually this used to be done in th XSD
             * conversion related rules, but really it's a generic thing so we
             * deal with it here now.
             * 
             * We don't complain about case state and case identifier though
             * because they have their own dedicated rules about allowed types
             * in ACE>
             */
            if (type == null && !BOMGlobalDataUtils.isCID(prop)
                    && !BOMGlobalDataUtils.isCaseState(prop)) {
                scope.createIssue(GenericIssueIds.ATTRIBUTE_TYPE_NOT_SET,
                        BOMValidationUtil.getLocation(prop),
                        prop.eResource().getURIFragment(prop));
            }
        }
    }

    /**
     * Checks that an attribute using a user defined primitive type does not
     * exceed the restriction defined in the base primitive type
     * 
     * These are generally things that when we convert into an XSD Schema will
     * result in an invalid schema being created
     * 
     * @param basePType
     * @param propType
     * @param prop
     */
    private void checkPrimitiveTypeRestrictions(IValidationScope scope,
            PrimitiveType basePType, PrimitiveType propType, Property prop) {
        ResourceSet rSet =
                XpdResourcesPlugin.getDefault().getEditingDomain()
                        .getResourceSet();

        // Check the rules for text types
        if (basePType == PrimitivesUtil.getStandardPrimitiveTypeByName(rSet,
                PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME)) {
            // The only think to check for Text types is that the "Max Length"
            // is not increased by the attributes compared to it's primitive
            // type
            Object maxLengthAttribute =
                    PrimitivesUtil.getFacetPropertyValue(propType,
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_LENGTH,
                            prop);
            Object maxLengthPrimType =
                    PrimitivesUtil.getFacetPropertyValue(propType,
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_LENGTH);

            if ((maxLengthAttribute instanceof Integer)
                    && (maxLengthPrimType instanceof Integer)) {
                if (((Integer) maxLengthAttribute) > ((Integer) maxLengthPrimType)) {
                    // create issue
                    scope.createIssue(GenericIssueIds.PRIMITIVE_TYPE_RESTRICTION_BROKEN,
                            BOMValidationUtil.getLocation(prop),
                            prop.eResource()
                                    .getURIFragment(prop));
                }
            }
        } else if (basePType == PrimitivesUtil
                .getStandardPrimitiveTypeByName(rSet,
                        PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME)) {
            // Handle rules for all Decimal Types
            // First check to make sure the lower value is not invalid
            Object lowerAttribute =
                    PrimitivesUtil.getFacetPropertyValue(propType,
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LOWER,
                            prop);
            Object lowerPrimType =
                    PrimitivesUtil.getFacetPropertyValue(propType,
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LOWER);

            if ((lowerAttribute instanceof String)
                    && (lowerPrimType instanceof String)) {
                // Get the values of the lower limit
                BigDecimal lowerAttr = new BigDecimal((String) lowerAttribute);
                BigDecimal lowerPrimT = new BigDecimal((String) lowerPrimType);
                if (lowerAttr.compareTo(lowerPrimT) < 0) {
                    // create issue
                    scope.createIssue(GenericIssueIds.PRIMITIVE_TYPE_RESTRICTION_BROKEN,
                            BOMValidationUtil.getLocation(prop),
                            prop.eResource()
                                    .getURIFragment(prop));
                }
            }

            // Next check to make sure the upper value is not invalid
            Object upperAttribute =
                    PrimitivesUtil.getFacetPropertyValue(propType,
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_UPPER,
                            prop);
            Object upperPrimType =
                    PrimitivesUtil.getFacetPropertyValue(propType,
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_UPPER);

            if ((upperAttribute instanceof String)
                    && (upperPrimType instanceof String)) {
                // Get the values of the upper limit
                BigDecimal upperAttr = new BigDecimal((String) upperAttribute);
                BigDecimal upperPrimT = new BigDecimal((String) upperPrimType);
                if (upperAttr.compareTo(upperPrimT) > 0) {
                    // create issue
                    scope.createIssue(GenericIssueIds.PRIMITIVE_TYPE_RESTRICTION_BROKEN,
                            BOMValidationUtil.getLocation(prop),
                            prop.eResource()
                                    .getURIFragment(prop));
                }
            }

            Object decimalSubType =
                    PrimitivesUtil.getFacetPropertyValue(propType,
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_SUBTYPE);
            // Only do the decimal place and number length check if dealing with
            // Fixed Decimal
            if ((decimalSubType instanceof EnumerationLiteral)
                    && PrimitivesUtil.DECIMAL_SUBTYPE_FIXEDPOINT
                            .equals(((EnumerationLiteral) decimalSubType)
                                    .getName())) {
                // Check to make sure the Decimal Places value is not invalid
                Object placesAttribute =
                        PrimitivesUtil
                                .getFacetPropertyValue(propType,
                                        PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_PLACES,
                                        prop);
                Object placesPrimType =
                        PrimitivesUtil
                                .getFacetPropertyValue(propType,
                                        PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_PLACES);

                if ((placesAttribute instanceof Integer)
                        && (placesPrimType instanceof Integer)) {
                    if (((Integer) placesAttribute) > ((Integer) placesPrimType)) {
                        // create issue
                        scope.createIssue(GenericIssueIds.PRIMITIVE_TYPE_RESTRICTION_BROKEN,
                                BOMValidationUtil.getLocation(prop),
                                prop.eResource()
                                        .getURIFragment(prop));
                    }
                }

                // Check to make sure the Number Length value is not invalid
                Object lengthAttribute =
                        PrimitivesUtil
                                .getFacetPropertyValue(propType,
                                        PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LENGTH,
                                        prop);
                Object lengthPrimType =
                        PrimitivesUtil
                                .getFacetPropertyValue(propType,
                                        PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LENGTH);

                if ((lengthAttribute instanceof Integer)
                        && (lengthPrimType instanceof Integer)) {
                    if (((Integer) lengthAttribute) > ((Integer) lengthPrimType)) {
                        // create issue
                        scope.createIssue(GenericIssueIds.PRIMITIVE_TYPE_RESTRICTION_BROKEN,
                                BOMValidationUtil.getLocation(prop),
                                prop.eResource()
                                        .getURIFragment(prop));
                    }
                }
            }
        } else if (basePType == PrimitivesUtil
                .getStandardPrimitiveTypeByName(rSet,
                        PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME)) {
            // Handle rules for all Integer Types
            // First check to make sure the lower value is not invalid
            Object lowerAttribute =
                    PrimitivesUtil.getFacetPropertyValue(propType,
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LOWER,
                            prop);
            Object lowerPrimType =
                    PrimitivesUtil.getFacetPropertyValue(propType,
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LOWER);

            if ((lowerAttribute instanceof String)
                    && (lowerPrimType instanceof String)) {
                // Get the values of the lower limit
                BigDecimal lowerAttr = new BigDecimal((String) lowerAttribute);
                BigDecimal lowerPrimT = new BigDecimal((String) lowerPrimType);
                if (lowerAttr.compareTo(lowerPrimT) < 0) {
                    // create issue
                    scope.createIssue(GenericIssueIds.PRIMITIVE_TYPE_RESTRICTION_BROKEN,
                            BOMValidationUtil.getLocation(prop),
                            prop.eResource()
                                    .getURIFragment(prop));
                }
            }

            // Next check to make sure the upper value is not invalid
            Object upperAttribute =
                    PrimitivesUtil.getFacetPropertyValue(propType,
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_UPPER,
                            prop);
            Object upperPrimType =
                    PrimitivesUtil.getFacetPropertyValue(propType,
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_UPPER);

            if ((upperAttribute instanceof String)
                    && (upperPrimType instanceof String)) {
                // Get the values of the upper limit
                BigDecimal upperAttr = new BigDecimal((String) upperAttribute);
                BigDecimal upperPrimT = new BigDecimal((String) upperPrimType);
                if (upperAttr.compareTo(upperPrimT) > 0) {
                    // create issue
                    scope.createIssue(GenericIssueIds.PRIMITIVE_TYPE_RESTRICTION_BROKEN,
                            BOMValidationUtil.getLocation(prop),
                            prop.eResource()
                                    .getURIFragment(prop));
                }
            }

            Object integerSubType =
                    PrimitivesUtil.getFacetPropertyValue(propType,
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_SUBTYPE);
            // Only do the decimal place and number length check if dealing with
            // Fixed Decimal
            if ((integerSubType instanceof EnumerationLiteral)
                    && PrimitivesUtil.INTEGER_SUBTYPE_FIXEDLENGTH
                            .equals(((EnumerationLiteral) integerSubType)
                                    .getName())) {
                // Check to make sure the Number Length value is not invalid
                Object lengthAttribute =
                        PrimitivesUtil
                                .getFacetPropertyValue(propType,
                                        PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LENGTH,
                                        prop);
                Object lengthPrimType =
                        PrimitivesUtil
                                .getFacetPropertyValue(propType,
                                        PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LENGTH);

                if ((lengthAttribute instanceof Integer)
                        && (lengthPrimType instanceof Integer)) {
                    if (((Integer) lengthAttribute) > ((Integer) lengthPrimType)) {
                        // create issue
                        scope.createIssue(GenericIssueIds.PRIMITIVE_TYPE_RESTRICTION_BROKEN,
                                BOMValidationUtil.getLocation(prop),
                                prop.eResource()
                                        .getURIFragment(prop));
                    }
                }
            }
        }
    }
}
