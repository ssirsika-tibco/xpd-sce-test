package com.tibco.xpd.bom.validator.rules.xsd;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.PrimitiveType;

import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.validator.BOMValidatorActivator;
import com.tibco.xpd.bom.validator.XsdIssueIds;
import com.tibco.xpd.bom.validator.internal.Messages;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

public class PrimitiveTypeRestrictions implements IValidationRule {

    private static final String DECIMAL_PLACES =
            Messages.PrimitiveTypeRestrictions_DecimalPlaces_label;

    private static final String NUMBER_LENGTH =
            Messages.PrimitiveTypeRestrictions_NumberLen_label;

    private static final String LOWER_LIMIT =
            Messages.PrimitiveTypeRestrictions_LowerLimit_label;

    private static final String UPPER_LIMIT =
            Messages.PrimitiveTypeRestrictions_UpperLimit_label;

    private static final String MAXIMUM_TEXT_LENGTH =
            Messages.PrimitiveTypeRestrictions_MaxTextLength_label;

    private static final String XSD_PROPERTY_CONSTRAINTS_PATTERNMISMATCH_ISSUE =
            "xsd.property.constraints.patternmismatch.issue"; //$NON-NLS-1$

    private static final String XSD_PROPERTY_CONSTRAINTS_DEFVALDECPLACES_ISSUE =
            "xsd.property.constraints.defvaldecplaces.issue"; //$NON-NLS-1$

    private static final String XSD_PROPERTY_CONSTRAINTS_UPPERLIMITDECPLACES_ISSUE =
            "xsd.property.constraints.upperlimitdecplaces.issue"; //$NON-NLS-1$

    private static final String XSD_PROPERTY_CONSTRAINTS_LOWERLIMITDECPLACES_ISSUE =
            "xsd.property.constraints.lowerlimitdecplaces.issue"; //$NON-NLS-1$

    private static final String XSD_PROPERTY_CONSTRAINTS_DEFVALLENEXCEEDSNUMLEN_ISSUE =
            "xsd.property.constraints.defvallenexceedsnumlen.issue"; //$NON-NLS-1$

    private static final String XSD_PROPERTY_CONSTRAINTS_UPPERLIMITLENEXCEEDSNUMLEN_ISSUE =
            "xsd.property.constraints.upperlimitlenexceedsnumlen.issue"; //$NON-NLS-1$

    private static final String XSD_PROPERTY_CONSTRAINTS_LOWERMIMITLENEXCCEEDSNUMLEN_ISSUE =
            "xsd.property.constraints.lowerlimitlenexceedsnumlen.issue"; //$NON-NLS-1$

    private static final String XSD_PROPERTY_CONSTRAINTS_DEFAULTEXCEEDSUPPERLIMIT_ISSUE =
            "xsd.property.constraints.defaultexceedsupperlimit.issue"; //$NON-NLS-1$

    private static final String XSD_PROPERTY_CONSTRAINTS_LOWERLIMITEXCEEDSDEFAULT_ISSUE =
            "xsd.property.constraints.lowerlimitexceedsdefault.issue"; //$NON-NLS-1$

    private static final String XSD_PROPERTY_CONSTRAINTS_LOWEREXCEEDSUPPERLIMIT_ISSUE =
            "xsd.property.constraints.lowerexceedsupperlimit.issue"; //$NON-NLS-1$

    private static final String XSD_PROPERTY_CONSTRAINTS_MAXTEXTLEN_ISSUE =
            "xsd.property.constraints.maxtextlen.issue"; //$NON-NLS-1$

    private static final String XSD_PROPERTY_PATTERN_INVALID_ISSUE =
            "xsd.property.constraints.patterninvalid.issue"; //$NON-NLS-1$

    @Override
    public Class<?> getTargetClass() {
        return PrimitiveType.class;
    }

    @Override
    public void validate(IValidationScope scope, Object o) {

        if (o instanceof PrimitiveType) {
            PrimitiveType pt = (PrimitiveType) o;

            // Does this have a generalization?
            EList<Generalization> lstGens = pt.getGeneralizations();

            if (lstGens.isEmpty()) {
                return;
            }

            Generalization gen = lstGens.get(0);
            Classifier general = gen.getGeneral();
            PrimitiveType ptGen = null;

            if (!(general instanceof PrimitiveType)) {
                return;
            } else {
                ptGen = (PrimitiveType) general;
            }

            // Check each type
            PrimitiveType basePT = PrimitivesUtil.getBasePrimitiveType(pt);
            ResourceSet rSet =
                    XpdResourcesPlugin.getDefault().getEditingDomain()
                            .getResourceSet();
            // If we have no generalisations to another user-defined
            // PrimitiveType then we will pick up the generalisation to the base
            // PrimitiveType (if a type is set). We are allowed to override the
            // facets of the base PrimitiveType so just return.
            if (basePT == ptGen) {
                /*
                 * XPD-4011: Saket: Primitive type restriction based rules
                 * should ignore Generated BOMs.
                 */
                IFile file = WorkingCopyUtil.getFile(pt);
                if (null != file) {
                    IResource res =
                            ResourcesPlugin.getWorkspace().getRoot()
                                    .findMember(file.getFullPath());
                    if (res != null && !BOMValidationUtil.isGeneratedBom(res)) {
                        validatePrimitiveType(pt, rSet, scope);
                    }
                    return;
                }
            }

            if (basePT == PrimitivesUtil.getStandardPrimitiveTypeByName(rSet,
                    PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME)) {
                validateTextType(scope, rSet, pt, ptGen);

            } else if (basePT == PrimitivesUtil
                    .getStandardPrimitiveTypeByName(rSet,
                            PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME)) {
                validateIntegerType(scope, rSet, pt, ptGen);
            } else if (basePT == PrimitivesUtil
                    .getStandardPrimitiveTypeByName(rSet,
                            PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME)) {
                validateDecimalType(scope, rSet, pt, ptGen);
            }

        }

    }

    /**
     * @param o
     * @param rSet
     * @param scope
     */
    private void validatePrimitiveType(PrimitiveType primitiveType,
            ResourceSet rSet, IValidationScope scope) {
        // Check each type
        PrimitiveType basePT =
                PrimitivesUtil.getBasePrimitiveType(primitiveType);
        if (basePT == PrimitivesUtil.getStandardPrimitiveTypeByName(rSet,
                PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME)) {
            validateTextPrimitiveType(primitiveType, scope);
        } else if (basePT == PrimitivesUtil
                .getStandardPrimitiveTypeByName(rSet,
                        PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME)) {
            validateIntegerPrimitiveType(primitiveType, scope);
        } else if (basePT == PrimitivesUtil
                .getStandardPrimitiveTypeByName(rSet,
                        PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME)) {
            validateDecimalPrimitiveType(primitiveType, scope);
        }
    }

    /**
     * @param primitiveType
     * @param scope
     */
    private void validateTextPrimitiveType(PrimitiveType primitiveType,
            IValidationScope scope) {
        Object facetLength =
                PrimitivesUtil.getFacetPropertyValue(primitiveType,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_LENGTH);
        Object facetPattern =
                PrimitivesUtil.getFacetPropertyValue(primitiveType,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_PATTERN_VALUE);
        Object facetDefaultValue =
                PrimitivesUtil.getFacetPropertyValue(primitiveType,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_DEFAULT_VALUE);
        Integer length =
                (facetLength instanceof String) ? Integer
                        .parseInt((String) facetLength)
                        : (facetLength instanceof Integer) ? ((Integer) facetLength)
                                : null;
        String patternValue = null;
        String defaultValue = null;
        if (facetDefaultValue != null) {
            defaultValue = (String) facetDefaultValue;
        }
        if (facetPattern != null) {
            patternValue = (String) facetPattern;
            if (defaultValue != null && !defaultValue.isEmpty()) {
                if (!patternValue.isEmpty()) {
                    try {
                        Pattern pattern = Pattern.compile(patternValue);
                        if (!pattern.matcher(defaultValue).matches()) {
                            // default value does not match pattern
                            scope.createIssue(XSD_PROPERTY_CONSTRAINTS_PATTERNMISMATCH_ISSUE,
                                    primitiveType.getQualifiedName(),
                                    primitiveType.eResource()
                                            .getURIFragment(primitiveType));
                        }
                    } catch (PatternSyntaxException pSE) {
                        scope.createIssue(XSD_PROPERTY_PATTERN_INVALID_ISSUE,
                                primitiveType.getQualifiedName(),
                                primitiveType.eResource()
                                        .getURIFragment(primitiveType));

                    }
                }
                // No length validation needed if unbound
                if ((length != null) && (length != -1)) {
                    if (length < defaultValue.length()) {
                        // default value length does not satisfy length
                        // restriction
                        scope.createIssue(XSD_PROPERTY_CONSTRAINTS_MAXTEXTLEN_ISSUE,
                                primitiveType.getQualifiedName(),
                                primitiveType.eResource()
                                        .getURIFragment(primitiveType));
                    }
                }

            }
        }

    }

    /**
     * @param primitiveType
     * @param scope
     */
    private void validateDecimalPrimitiveType(PrimitiveType primitiveType,
            IValidationScope scope) {
        Object facetDefaultValue =
                PrimitivesUtil
                        .getFacetPropertyValue(primitiveType,
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_DEFAULT_VALUE);
        Object facetNumLen =
                PrimitivesUtil.getFacetPropertyValue(primitiveType,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LENGTH);
        Object facetLower =
                PrimitivesUtil.getFacetPropertyValue(primitiveType,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LOWER);
        Object facetUpper =
                PrimitivesUtil.getFacetPropertyValue(primitiveType,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_UPPER);
        Object facetDecimalPlace =
                PrimitivesUtil.getFacetPropertyValue(primitiveType,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_PLACES);
        Object facetSubType =
                PrimitivesUtil.getFacetPropertyValue(primitiveType,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_SUBTYPE);
        Object facetLowerInclusive =
                PrimitivesUtil
                        .getFacetPropertyValue(primitiveType,
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LOWER_INCLUSIVE);
        Object facetUpperInclusive =
                PrimitivesUtil
                        .getFacetPropertyValue(primitiveType,
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_UPPER_INCLUSIVE);
        BigDecimal lowerLimit = null;
        BigDecimal upperLimit = null;
        BigDecimal defaultVal = null;
        Integer decimalPlace = null;
        Integer numLen = null;

        lowerLimit = getBigDecimalValue(facetLower);
        upperLimit = getBigDecimalValue(facetUpper);
        defaultVal = getBigDecimalValue(facetDefaultValue);

        // check values with respect to length
        if (facetSubType != null
                && facetSubType instanceof EnumerationLiteral
                && ((EnumerationLiteral) facetSubType).getName()
                        .equals(PrimitivesUtil.DECIMAL_SUBTYPE_FIXEDPOINT)) {
            numLen =
                    (facetNumLen instanceof String) ? Integer
                            .parseInt((String) facetNumLen)
                            : (facetNumLen instanceof Integer) ? ((Integer) facetNumLen)
                                    : null;
            decimalPlace =
                    (facetDecimalPlace instanceof String) ? Integer
                            .parseInt((String) facetDecimalPlace)
                            : (facetDecimalPlace instanceof Integer) ? ((Integer) facetDecimalPlace)
                                    : null;
            // check defaultValue Length
            if (defaultVal != null) {
                checkLengthAndDecimalPlaces(scope,
                        primitiveType,
                        defaultVal,
                        numLen,
                        decimalPlace,
                        XSD_PROPERTY_CONSTRAINTS_DEFVALDECPLACES_ISSUE,
                        XSD_PROPERTY_CONSTRAINTS_DEFVALLENEXCEEDSNUMLEN_ISSUE);
            }
            // check lowerLimit Length
            if (lowerLimit != null) {
                checkLengthAndDecimalPlaces(scope,
                        primitiveType,
                        lowerLimit,
                        numLen,
                        decimalPlace,
                        XSD_PROPERTY_CONSTRAINTS_LOWERLIMITDECPLACES_ISSUE,
                        XSD_PROPERTY_CONSTRAINTS_LOWERMIMITLENEXCCEEDSNUMLEN_ISSUE);
            }
            // check upperLimit Length
            if (upperLimit != null) {
                checkLengthAndDecimalPlaces(scope,
                        primitiveType,
                        upperLimit,
                        numLen,
                        decimalPlace,
                        XSD_PROPERTY_CONSTRAINTS_UPPERLIMITDECPLACES_ISSUE,
                        XSD_PROPERTY_CONSTRAINTS_UPPERLIMITLENEXCEEDSNUMLEN_ISSUE);
            }
        }
        // If both limits set
        validateValue(primitiveType,
                scope,
                lowerLimit,
                upperLimit,
                XSD_PROPERTY_CONSTRAINTS_LOWEREXCEEDSUPPERLIMIT_ISSUE,
                true);

        // Check limits against default value
        validateValue(primitiveType,
                scope,
                lowerLimit,
                defaultVal,
                XSD_PROPERTY_CONSTRAINTS_LOWERLIMITEXCEEDSDEFAULT_ISSUE,
                true);
        // Only check the default value if it is set
        if ((facetLowerInclusive instanceof Boolean) && (defaultVal != null)) {
            // if lower is not inclusive, default should not be equal to lower
            Boolean lowerInclusive = (Boolean) facetLowerInclusive;
            if (!lowerInclusive && (defaultVal.compareTo(lowerLimit) == 0)) {
                scope.createIssue(XSD_PROPERTY_CONSTRAINTS_LOWERLIMITEXCEEDSDEFAULT_ISSUE,
                        primitiveType.getQualifiedName(),
                        primitiveType.eResource().getURIFragment(primitiveType));
            }
        }

        validateValue(primitiveType,
                scope,
                defaultVal,
                upperLimit,
                XSD_PROPERTY_CONSTRAINTS_DEFAULTEXCEEDSUPPERLIMIT_ISSUE,
                false);
        // Only check the default value if it is set
        if ((facetUpperInclusive instanceof Boolean) && (defaultVal != null)) {
            // if upper is not inclusive, default should not be equal to upper
            Boolean upperInclusive = (Boolean) facetUpperInclusive;
            if (!upperInclusive && (defaultVal.compareTo(upperLimit) == 0)) {
                scope.createIssue(XSD_PROPERTY_CONSTRAINTS_DEFAULTEXCEEDSUPPERLIMIT_ISSUE,
                        primitiveType.getQualifiedName(),
                        primitiveType.eResource().getURIFragment(primitiveType));
            }
        }
    }

    /**
     * @param decimalValue
     * @param string
     * @param decimalPlace
     * @param numLen
     */
    private void checkLengthAndDecimalPlaces(IValidationScope scope,
            PrimitiveType primitiveType, BigDecimal decimalValue,
            Integer numLen, Integer decimalPlace,
            String decimalPlacesErrorMessage, String numberLengthErrorMessage) {
        String decimalValueString = decimalValue.toPlainString();
        Integer numberValueLength = null;
        Integer decimalPlacesLength = null;
        int indexOfDot = decimalValueString.indexOf(".");
        /* check value for number places */
        if (null != numLen) {
            String valueNumStr =
                    decimalValueString.substring(0,
                            (indexOfDot == -1) ? decimalValueString.length()
                                    : indexOfDot); //$NON-NLS-1$
            numberValueLength = valueNumStr.length();
            if (numberValueLength > numLen) {
                scope.createIssue(numberLengthErrorMessage,
                        primitiveType.getQualifiedName(),
                        primitiveType.eResource().getURIFragment(primitiveType));
            }
        }
        if (null != decimalPlace) {
            String valueDecStr =
                    (indexOfDot == -1) ? "" : decimalValueString.substring(indexOfDot + 1); //$NON-NLS-1$
            decimalPlacesLength = valueDecStr.length();

            if (decimalPlacesLength > decimalPlace) {
                scope.createIssue(decimalPlacesErrorMessage,
                        primitiveType.getQualifiedName(),
                        primitiveType.eResource().getURIFragment(primitiveType));
            }
        }

    }

    /**
     * @param primitiveType
     * @param scope
     */
    private void validateIntegerPrimitiveType(PrimitiveType primitiveType,
            IValidationScope scope) {
        Object facetDefaultValue =
                PrimitivesUtil
                        .getFacetPropertyValue(primitiveType,
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_DEFAULT_VALUE);
        Object facetNumLen =
                PrimitivesUtil.getFacetPropertyValue(primitiveType,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LENGTH);
        Object facetLower =
                PrimitivesUtil.getFacetPropertyValue(primitiveType,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LOWER);
        Object facetUpper =
                PrimitivesUtil.getFacetPropertyValue(primitiveType,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_UPPER);
        Object facetSubType =
                PrimitivesUtil.getFacetPropertyValue(primitiveType,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_SUBTYPE);
        BigInteger lowerLimit = null;
        BigInteger upperLimit = null;
        BigInteger defaultVal = null;
        Integer numLen = null;

        numLen =
                (facetNumLen instanceof String) ? Integer
                        .parseInt((String) facetNumLen)
                        : (facetNumLen instanceof Integer) ? ((Integer) facetNumLen)
                                : null;
        lowerLimit = getBigIntegerValue(facetLower);
        upperLimit = getBigIntegerValue(facetUpper);
        defaultVal = getBigIntegerValue(facetDefaultValue);

        // check values with respect to length
        if (facetSubType != null
                && facetSubType instanceof EnumerationLiteral
                && ((EnumerationLiteral) facetSubType).getName()
                        .equals(PrimitivesUtil.INTEGER_SUBTYPE_FIXEDLENGTH)) {
            if (numLen != null) {
                // check defaultValue Length
                if (defaultVal != null) {
                    checkIntegerLength(primitiveType,
                            scope,
                            defaultVal,
                            numLen,
                            XSD_PROPERTY_CONSTRAINTS_DEFVALLENEXCEEDSNUMLEN_ISSUE);
                }
                // check lowerLimit Length
                if (lowerLimit != null) {
                    checkIntegerLength(primitiveType,
                            scope,
                            lowerLimit,
                            numLen,
                            XSD_PROPERTY_CONSTRAINTS_LOWERMIMITLENEXCCEEDSNUMLEN_ISSUE);
                }
                // check upperLimit Length
                if (upperLimit != null) {
                    checkIntegerLength(primitiveType,
                            scope,
                            upperLimit,
                            numLen,
                            XSD_PROPERTY_CONSTRAINTS_UPPERLIMITLENEXCEEDSNUMLEN_ISSUE);

                }
            }
        }
        // If both limits set
        validateValue(primitiveType,
                scope,
                lowerLimit,
                upperLimit,
                XSD_PROPERTY_CONSTRAINTS_LOWEREXCEEDSUPPERLIMIT_ISSUE,
                true);

        // Check limits against default value
        validateValue(primitiveType,
                scope,
                lowerLimit,
                defaultVal,
                XSD_PROPERTY_CONSTRAINTS_LOWERLIMITEXCEEDSDEFAULT_ISSUE,
                true);

        validateValue(primitiveType,
                scope,
                defaultVal,
                upperLimit,
                XSD_PROPERTY_CONSTRAINTS_DEFAULTEXCEEDSUPPERLIMIT_ISSUE,
                false);

    }

    /**
     * @param primitiveType
     * @param scope
     * @param defaultVal
     * @param numLen
     */
    private void checkIntegerLength(PrimitiveType primitiveType,
            IValidationScope scope, BigInteger value, Integer numLen,
            String errorMessage) {
        Integer length;
        // abs() is taken to handle +ve and -ve values
        length = Integer.valueOf(value.abs().toString().length());
        validateValue(primitiveType, scope, length, numLen, errorMessage, true);
    }

    /**
     * This method checks the values of upperValue and lowerValue , depending on
     * the upperLessThan flag. for upperLessThan set to true, it checks if upper
     * value is less than lower value, otherwise checks if lower value is
     * greater than upper value. Might sound confusing, as both means same, with
     * a slight difference. To be used for purposes like, given a range LL-UL ,
     * for check x in range [both inclusive], x[upperValue] less than
     * LL[lowerValue] is error, x[lowerValue] greaterThan UL[upperValue] is an
     * error.
     * 
     * @param primitiveType
     * @param scope
     * @param lowerValue
     * @param upperValue
     */
    private void validateValue(PrimitiveType primitiveType,
            IValidationScope scope, Comparable lowerValue,
            Comparable upperValue, String errorMessage, boolean upperLessThan) {
        boolean error = false;
        if (lowerValue != null && upperValue != null) {
            if (upperLessThan) {
                // if upper limit less than lower limit
                if (upperValue.compareTo(lowerValue) == -1) {
                    error = true;
                }
            } else {
                // lower limit greater than upper limit
                if (lowerValue.compareTo(upperValue) == 1) {
                    error = true;
                }
            }
            if (error) {
                scope.createIssue(errorMessage,
                        primitiveType.getQualifiedName(),
                        primitiveType.eResource().getURIFragment(primitiveType));
            }
        }
    }

    /**
     * @param value
     */
    private BigInteger getBigIntegerValue(Object value) {
        BigInteger bigIntValue = null;
        try {
            if (null != value && value != "") {
                if (value instanceof String) {
                    bigIntValue = new BigInteger((String) value);
                }
                if (value instanceof Integer) {
                    bigIntValue = BigInteger.valueOf((Integer) value);
                }
            }
        } catch (NumberFormatException nFE) {
            BOMValidatorActivator.getLogger().error(nFE,
                    XsdIssueIds.ISSUE_INVALID_VALUE);
        }
        return bigIntValue;
    }

    /**
     * @param value
     */
    private BigDecimal getBigDecimalValue(Object value) {
        BigDecimal bigDecimalValue = null;
        try {
            if (null != value && value != "") {
                if (value instanceof String) {
                    bigDecimalValue = new BigDecimal((String) value);
                }
                if (value instanceof Double) {
                    bigDecimalValue = BigDecimal.valueOf((Double) value);
                }
            }
        } catch (NumberFormatException nFE) {
            BOMValidatorActivator.getLogger().error(nFE,
                    XsdIssueIds.ISSUE_INVALID_VALUE);
        }
        return bigDecimalValue;
    }

    /**
     * @param scope
     * @param set
     * @param specific
     * @param general
     */
    private void validateDecimalType(IValidationScope scope, ResourceSet set,
            PrimitiveType specific, PrimitiveType general) {

        // Deal with upper
        Object generalUpper =
                PrimitivesUtil.getFacetPropertyValue(general,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_UPPER);

        Object specificUpper =
                PrimitivesUtil.getFacetPropertyValue(specific,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_UPPER);

        if (specificUpper != null && generalUpper != null) {
            Object generalUpperInc =
                    PrimitivesUtil
                            .getFacetPropertyValue(general,
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_UPPER_INCLUSIVE);

            Object specificUpperInc =
                    PrimitivesUtil
                            .getFacetPropertyValue(specific,
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_UPPER_INCLUSIVE);

            Double intGenUpper = Double.valueOf((String) generalUpper);
            Double intSpecUpper = Double.valueOf((String) specificUpper);
            int checkEquality = intSpecUpper.compareTo(intGenUpper);

            if (specificUpperInc == Boolean.TRUE) {
                if (generalUpperInc == Boolean.TRUE) {
                    if (checkEquality > 0) {
                        List<String> messages = new ArrayList<String>();
                        messages.add(UPPER_LIMIT);

                        scope.createIssue(XsdIssueIds.PRIMITIVETYPE_RESTRICTIONS,
                                specific.getQualifiedName(),
                                specific.eResource().getURIFragment(specific),
                                messages);
                    }
                } else if (generalUpperInc == Boolean.FALSE) {
                    if (checkEquality >= 0) {
                        List<String> messages = new ArrayList<String>();
                        messages.add(UPPER_LIMIT);

                        scope.createIssue(XsdIssueIds.PRIMITIVETYPE_RESTRICTIONS,
                                specific.getQualifiedName(),
                                specific.eResource().getURIFragment(specific),
                                messages);
                    }
                }

            } else if (specificUpperInc == Boolean.FALSE) {
                // Regardless of being Inclusive or Exclusive the specfics
                // value
                // cannot exceeed the general's
                if (checkEquality > 0) {
                    List<String> messages = new ArrayList<String>();
                    messages.add(UPPER_LIMIT);

                    scope.createIssue(XsdIssueIds.PRIMITIVETYPE_RESTRICTIONS,
                            specific.getQualifiedName(),
                            specific.eResource().getURIFragment(specific),
                            messages);
                }
            }

        }

        // Deal with Lower
        Object generalLower =
                PrimitivesUtil.getFacetPropertyValue(general,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LOWER);

        Object specificLower =
                PrimitivesUtil.getFacetPropertyValue(specific,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LOWER);

        if (specificLower != null && generalLower != null) {
            Object generalLowerInc =
                    PrimitivesUtil
                            .getFacetPropertyValue(general,
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LOWER_INCLUSIVE);

            Object specificLowerInc =
                    PrimitivesUtil
                            .getFacetPropertyValue(specific,
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LOWER_INCLUSIVE);

            Double intGenLower = Double.valueOf((String) generalLower);
            Double intSpecLower = Double.valueOf((String) specificLower);
            int checkEquality = intSpecLower.compareTo(intGenLower);

            if (specificLowerInc == Boolean.TRUE) {
                if (generalLowerInc == Boolean.TRUE) {
                    if (checkEquality < 0) {
                        List<String> messages = new ArrayList<String>();
                        messages.add(LOWER_LIMIT);

                        scope.createIssue(XsdIssueIds.PRIMITIVETYPE_RESTRICTIONS,
                                specific.getQualifiedName(),
                                specific.eResource().getURIFragment(specific),
                                messages);
                    }
                } else if (generalLowerInc == Boolean.FALSE) {
                    if (checkEquality <= 0) {
                        List<String> messages = new ArrayList<String>();
                        messages.add(LOWER_LIMIT);

                        scope.createIssue(XsdIssueIds.PRIMITIVETYPE_RESTRICTIONS,
                                specific.getQualifiedName(),
                                specific.eResource().getURIFragment(specific),
                                messages);
                    }
                }

            } else if (specificLowerInc == Boolean.FALSE) {
                // Regardless of being Inclusive or Exclusive the specific's
                // value
                // cannot exceed the general's
                if (checkEquality < 0) {
                    List<String> messages = new ArrayList<String>();
                    messages.add(LOWER_LIMIT);

                    scope.createIssue(XsdIssueIds.PRIMITIVETYPE_RESTRICTIONS,
                            specific.getQualifiedName(),
                            specific.eResource().getURIFragment(specific),
                            messages);
                }
            }

        }

        // Number length

        // Only need to check general's sub-type because the specific's should
        // be the same.
        Object genSubType =
                PrimitivesUtil.getFacetPropertyValue(general,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_SUBTYPE);

        if (genSubType != null
                && ((EnumerationLiteral) genSubType).getName()
                        .equals(PrimitivesUtil.DECIMAL_SUBTYPE_FIXEDPOINT)) {

            Object generalLen =
                    PrimitivesUtil.getFacetPropertyValue(general,
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LENGTH);

            Object specificLen =
                    PrimitivesUtil.getFacetPropertyValue(specific,
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LENGTH);

            if (generalLen != null && specificLen != null) {
                int checkEquality =
                        ((Integer) specificLen).compareTo((Integer) generalLen);

                if (checkEquality > 0) {
                    List<String> messages = new ArrayList<String>();
                    messages.add(NUMBER_LENGTH);

                    scope.createIssue(XsdIssueIds.PRIMITIVETYPE_RESTRICTIONS,
                            specific.getQualifiedName(),
                            specific.eResource().getURIFragment(specific),
                            messages);
                }
            }

            // Decimal Places
            Object generalDecPl =
                    PrimitivesUtil.getFacetPropertyValue(general,
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_PLACES);

            Object specificDecPl =
                    PrimitivesUtil.getFacetPropertyValue(specific,
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_PLACES);

            if (generalDecPl != null && specificDecPl != null) {
                int checkEquality =
                        ((Integer) specificDecPl)
                                .compareTo((Integer) generalDecPl);

                if (checkEquality > 0) {
                    List<String> messages = new ArrayList<String>();
                    messages.add(DECIMAL_PLACES);

                    scope.createIssue(XsdIssueIds.PRIMITIVETYPE_RESTRICTIONS,
                            specific.getQualifiedName(),
                            specific.eResource().getURIFragment(specific),
                            messages);
                }
            }

        }

    }

    /**
     * @param scope
     * @param set
     * @param specific
     * @param general
     */
    private void validateIntegerType(IValidationScope scope, ResourceSet set,
            PrimitiveType specific, PrimitiveType general) {
        // Deal with upper
        Object generalUpper =
                PrimitivesUtil.getFacetPropertyValue(general,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_UPPER);

        Object specificUpper =
                PrimitivesUtil.getFacetPropertyValue(specific,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_UPPER);

        if (specificUpper instanceof String && generalUpper instanceof String) {

            String specificUpperStr = ((String) specificUpper).trim();
            String generalUpperStr = ((String) generalUpper).trim();
            if (!specificUpperStr.isEmpty() && !generalUpperStr.isEmpty()) {

                BigInteger intGenUpper = new BigInteger(generalUpperStr);
                BigInteger intSpecUpper = new BigInteger(specificUpperStr);

                int checkEquality = intSpecUpper.compareTo(intGenUpper);

                if (checkEquality > 0) {
                    List<String> messages = new ArrayList<String>();
                    messages.add(UPPER_LIMIT);

                    scope.createIssue(XsdIssueIds.PRIMITIVETYPE_RESTRICTIONS,
                            specific.getQualifiedName(),
                            specific.eResource().getURIFragment(specific),
                            messages);
                }
            }
        }

        // Deal with Lower
        Object generalLower =
                PrimitivesUtil.getFacetPropertyValue(general,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LOWER);

        Object specificLower =
                PrimitivesUtil.getFacetPropertyValue(specific,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LOWER);

        if (generalLower != null && specificLower != null) {
            Long intGenLower = Long.valueOf((String) generalLower);
            Long intSpecLower = Long.valueOf((String) specificLower);
            int checkEquality = intSpecLower.compareTo(intGenLower);

            if (checkEquality < 0) {
                List<String> messages = new ArrayList<String>();
                messages.add(LOWER_LIMIT);

                scope.createIssue(XsdIssueIds.PRIMITIVETYPE_RESTRICTIONS,
                        specific.getQualifiedName(),
                        specific.eResource().getURIFragment(specific),
                        messages);
            }
        }

        // Number length
        // Only need to check general's sub-type because the specific's should
        // be the same.
        Object genSubType =
                PrimitivesUtil.getFacetPropertyValue(general,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_SUBTYPE);

        if (genSubType != null
                && ((EnumerationLiteral) genSubType).getName()
                        .equals(PrimitivesUtil.INTEGER_SUBTYPE_FIXEDLENGTH)) {

            Object generalLen =
                    PrimitivesUtil.getFacetPropertyValue(general,
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LENGTH);

            Object specificLen =
                    PrimitivesUtil.getFacetPropertyValue(specific,
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LENGTH);

            if (generalLen != null && specificLen != null) {
                int checkEquality =
                        ((Integer) specificLen).compareTo((Integer) generalLen);

                if (checkEquality > 0) {
                    List<String> messages = new ArrayList<String>();
                    messages.add(NUMBER_LENGTH);

                    scope.createIssue(XsdIssueIds.PRIMITIVETYPE_RESTRICTIONS,
                            specific.getQualifiedName(),
                            specific.eResource().getURIFragment(specific),
                            messages);
                }
            }
        }

    }

    /**
     * @param scope
     * @param set
     * @param specific
     * @param general
     */
    private void validateTextType(IValidationScope scope, ResourceSet set,
            PrimitiveType specific, PrimitiveType general) {

        Object generalTextLengh =
                PrimitivesUtil.getFacetPropertyValue(general,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_LENGTH);

        Object specificTextLengh =
                PrimitivesUtil.getFacetPropertyValue(specific,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_LENGTH);

        if (generalTextLengh != null && specificTextLengh != null) {
            if (((Integer) generalTextLengh) != -1) {
                int checkEquality =
                        ((Integer) specificTextLengh)
                                .compareTo((Integer) generalTextLengh);

                if (checkEquality > 0) {
                    List<String> messages = new ArrayList<String>();
                    messages.add(MAXIMUM_TEXT_LENGTH);

                    scope.createIssue(XsdIssueIds.PRIMITIVETYPE_RESTRICTIONS,
                            specific.getQualifiedName(),
                            specific.eResource().getURIFragment(specific),
                            messages);
                }
            }

        }

    }

    private PrimitiveType getFirstGeneralWithRestriction(ResourceSet rSet,
            Generalization genz, String facet) {

        Classifier generalClf = genz.getGeneral();
        PrimitiveType generalPT = null;

        if (!(generalClf instanceof PrimitiveType)) {
            return null;
        } else {
            generalPT = (PrimitiveType) generalClf;
        }

        if (PrimitivesUtil.getBasePrimitiveType(generalPT) == PrimitivesUtil
                .getStandardPrimitiveTypeByName(rSet,
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME)) {

            if (facet.equals(PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_LENGTH)) {
                Object facetPropertyValue =
                        PrimitivesUtil.getFacetPropertyValue(generalPT, facet);

                if (facetPropertyValue != null) {
                    return generalPT;
                } else {
                    EList<Generalization> gens = generalPT.getGeneralizations();
                    if (!gens.isEmpty()) {
                        return (getFirstGeneralWithRestriction(rSet,
                                gens.get(0),
                                facet));
                    }
                }
            }

        }

        return null;
    }

}
