package com.tibco.bds.designtime.api.validation;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.datatype.DatatypeConfigurationException;

import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;

import com.tibco.bds.designtime.api.internal.Messages;
import com.tibco.bds.designtime.api.validation.ValidationResult.Severity;
import com.tibco.bds.shared.dto.search.AttributeSearchConditionDTO;
import com.tibco.bds.shared.dto.search.ConditionGroupDTO;
import com.tibco.bds.shared.dto.search.SearchConditionDTO;
import com.tibco.bds.shared.dto.search.SearchOperatorDTO;
import com.tibco.bds.shared.dto.search.SortAttributeDTO;
import com.tibco.bds.shared.search.CalendarUtil;
import com.tibco.bds.shared.search.SearchDTOUtil;
import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.modeler.custom.enumlitext.DomainValue;
import com.tibco.xpd.bom.modeler.custom.enumlitext.SingleValue;
import com.tibco.xpd.bom.modeler.custom.enumlitext.util.EnumLitValueUtil;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.xsdtransform.XsdStereotypeUtils;

/**
 * Performs a semantic check of search condition and sort attributes within the
 * context of a given BOM (UML) class. This does _not_ rely on the correct
 * behaviour of the DQL parser. For example, it will check that a condition
 * using the 'between' operator has two values; This situation would only occur
 * if the DQL parser is malfunctioning, or the search DTOs were constructed
 * outside the context of the DQL parser.
 * 
 * @author smorgan
 * 
 */
public class SemanticSearchValidator {

    private enum SuffixType {
        ALL, INDEX, TAG
    }

    private static final String RX_INDEX_SUFFIX = "\\d+"; //$NON-NLS-1$

    private static final String RX_TAG_SUFFIX = "\\$[A-Za-z_][A-Za-z0-9_]*"; //$NON-NLS-1$

    private static final String RX_FUNCTION_CALL = "(.*?)\\((.*)\\)"; //$NON-NLS-1$

    private static final String RX_FUNCTION_NAMES = "(?i)(lower|upper)"; //$NON-NLS-1$

    private static final int RX_FUNCTION_CALL_FUNCTION_GROUP = 1;

    private static final int RX_FUNCTION_CALL_OPERAND_GROUP = 2;

    private Pattern patFunctionCall;

    private Pattern patIndexSuffix;

    private Pattern patTagSuffix;

    private Pattern patFunctionNames;

    private List<ValidationResult> results;

    private CalendarUtil calendarUtil;

    public SemanticSearchValidator() throws DatatypeConfigurationException {
        patFunctionCall = Pattern.compile(RX_FUNCTION_CALL);
        patIndexSuffix = Pattern.compile(RX_INDEX_SUFFIX);
        patTagSuffix = Pattern.compile(RX_TAG_SUFFIX);
        patFunctionNames = Pattern.compile(RX_FUNCTION_NAMES);
        results = new ArrayList<ValidationResult>();
        calendarUtil = new CalendarUtil();
    }

    public List<ValidationResult> getResults() {
        return results;
    }

    protected void addError(String message) {
        results.add(new ValidationResult(Severity.ERROR, message));
    }

    protected void addWarning(String message) {
        results.add(new ValidationResult(Severity.WARNING, message));
    }

    /**
     * Performs a depth-first traversal of the condition tree extracting the
     * leaves (ASCs) and returns them as a flat list.
     */
    protected static List<AttributeSearchConditionDTO> flattenToASCs(
            SearchConditionDTO condition) {
        List<AttributeSearchConditionDTO> result =
                new ArrayList<AttributeSearchConditionDTO>();
        if (condition instanceof AttributeSearchConditionDTO) {
            result.add((AttributeSearchConditionDTO) condition);
        } else if (condition instanceof ConditionGroupDTO) {
            for (SearchConditionDTO child : ((ConditionGroupDTO) condition)
                    .getConditions()) {
                result.addAll(flattenToASCs(child));
            }
        }
        return result;
    }

    protected SuffixType determineSuffixType(String suffix) {
        SuffixType result = null;
        if (suffix.equalsIgnoreCase("ALL")) { //$NON-NLS-1$
            result = SuffixType.ALL;
        } else if (patIndexSuffix.matcher(suffix).matches()) {
            result = SuffixType.INDEX;
        } else if (patTagSuffix.matcher(suffix).matches()) {
            result = SuffixType.TAG;
        }
        return result;
    }

    protected String checkAndRemoveFunctionWrapping(String attrPath) {
        String result = attrPath;
        Matcher matcher = patFunctionCall.matcher(attrPath);
        if (matcher.matches()) {
            String functionName =
                    matcher.group(RX_FUNCTION_CALL_FUNCTION_GROUP);
            if (!patFunctionNames.matcher(functionName).matches()) {
                // error - no such function name
                addError(String
                        .format(Messages.SemanticSearchValidator_bad_function,
                                functionName,
                                attrPath));
            }
            result = matcher.group(RX_FUNCTION_CALL_OPERAND_GROUP);
        }
        return result;
    }

    protected void validateOperatorValidity(AttributeSearchConditionDTO asc,
            Type type, String leafName, boolean isMany, boolean hasSuffix) {
        if (type instanceof PrimitiveType
                && getBaseTypeName((PrimitiveType) type)
                        .equals(PrimitivesUtil.BOM_PRIMITIVE_DURATION_NAME)) {
            // Not allowed to search on a Duration
            addError(String
                    .format(Messages.SemanticSearchValidator_type_duration,
                            leafName));
        } else {
            SearchOperatorDTO op = asc.getOperator();
            if (op == SearchOperatorDTO.SIZE_EQ
                    || op == SearchOperatorDTO.SIZE_NEQ
                    || op == SearchOperatorDTO.SIZE_GT
                    || op == SearchOperatorDTO.SIZE_GTE
                    || op == SearchOperatorDTO.SIZE_LT
                    || op == SearchOperatorDTO.SIZE_LTE) {
                if (!isMany) {
                    // error - size operators only apply to manys
                    addError(String
                            .format(Messages.SemanticSearchValidator_bad_size,
                                    leafName));
                } else if (hasSuffix) {
                    addError(String
                            .format(Messages.SemanticSearchValidator_size_with_suffix,
                                    operatorToString(op),
                                    leafName));
                }
                //
            } else if (op == SearchOperatorDTO.EQ
                    || op == SearchOperatorDTO.NEQ
                    || op == SearchOperatorDTO.IN
                    || op == SearchOperatorDTO.NOT_IN) {
                if (!(type instanceof PrimitiveType || type instanceof Enumeration)) {
                    // error - operator only applies to attributes
                    addError(String
                            .format(Messages.SemanticSearchValidator_operator_not_for_object,
                                    operatorToString(op)));
                }
            } else if (op == SearchOperatorDTO.GT
                    || op == SearchOperatorDTO.GTE
                    || op == SearchOperatorDTO.LT
                    || op == SearchOperatorDTO.LTE
                    || op == SearchOperatorDTO.BETWEEN
                    || op == SearchOperatorDTO.NOT_BETWEEN) {

                if (type instanceof PrimitiveType) {
                    String typeName = getBaseTypeName((PrimitiveType) type);
                    if (!(typeName
                            .equals(PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME)
                            || typeName
                                    .equals(PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME)
                            || typeName
                                    .equals(PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME)
                            || typeName
                                    .equals(PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME)
                            || typeName
                                    .equals(PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME) || typeName
                                .equals(PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME))) {
                        addError(String
                                .format(Messages.SemanticSearchValidator_operator_not_for_type,
                                        operatorToString(op),
                                        leafName,
                                        typeName));
                    }
                } else if (type instanceof Enumeration) {
                    addError(String
                            .format(Messages.SemanticSearchValidator_operator_not_for_enumeration,
                                    operatorToString(op),
                                    leafName));
                } else {
                    addError(String
                            .format(Messages.SemanticSearchValidator_operator_not_for_object,
                                    operatorToString(op)));
                }

            } else if (op == SearchOperatorDTO.TYPE_OF) {
                if (!(type instanceof Class)) {
                    // error - only applies to refs
                    addError(String
                            .format(Messages.SemanticSearchValidator_typeof_only_for_object,
                                    asc.getAttrPath()));
                }
            } else if (op == SearchOperatorDTO.NULL
                    || op == SearchOperatorDTO.NOT_NULL) {
                if (isMany && hasSuffix) {
                    addError(String
                            .format(Messages.SemanticSearchValidator_null_not_null_with_suffix,
                                    leafName));
                }
            }
        }
    }

    protected String operatorToString(SearchOperatorDTO op) {
        String result = null;
        switch (op) {
        case EQ:
            result = Messages.SemanticSearchValidator_op_equal_to;
            break;
        case NEQ:
            result = Messages.SemanticSearchValidator_op_not_equal_to;
            break;
        case GT:
            result = Messages.SemanticSearchValidator_op_greater_than;
            break;
        case GTE:
            result =
                    Messages.SemanticSearchValidator_op_greater_than_or_equal_to;
            break;
        case LT:
            result = Messages.SemanticSearchValidator_op_less_than;
            break;
        case LTE:
            result = Messages.SemanticSearchValidator_op_less_than_or_equal_to;
            break;
        case BETWEEN:
            result = Messages.SemanticSearchValidator_op_between;
            break;
        case NOT_BETWEEN:
            result = Messages.SemanticSearchValidator_op_not_between;
            break;
        case IN:
            result = Messages.SemanticSearchValidator_op_in;
            break;
        case NOT_IN:
            result = Messages.SemanticSearchValidator_op_not_in;
            break;
        case TYPE_OF:
            result = Messages.SemanticSearchValidator_op_type_of;
            break;
        case NULL:
            result = Messages.SemanticSearchValidator_op_null;
            break;
        case NOT_NULL:
            result = Messages.SemanticSearchValidator_op_not_null;
            break;
        case SIZE_EQ:
            result = Messages.SemanticSearchValidator_op_size_equal_to;
            break;
        case SIZE_NEQ:
            result = Messages.SemanticSearchValidator_op_size_not_equal_to;
            break;
        case SIZE_GT:
            result = Messages.SemanticSearchValidator_op_size_greater_than;
            break;
        case SIZE_GTE:
            result =
                    Messages.SemanticSearchValidator_op_size_greater_than_or_equal_to;
            break;
        case SIZE_LT:
            result = Messages.SemanticSearchValidator_op_size_less_than;
            break;
        case SIZE_LTE:
            result =
                    Messages.SemanticSearchValidator_op_size_less_than_or_equal_to;
        }
        return result;
    }

    private String getBaseTypeName(PrimitiveType type) {
        PrimitiveType baseType =
                PrimitivesUtil.getBasePrimitiveType((PrimitiveType) type);
        String typeName = baseType.getName();
        return typeName;
    }

    /**
     * Gets the literal value of the EnumerationLiteral (i.e. the literal value
     * that would appear in XML, as opposed to the (Java constant) name).
     * 
     * @param lit
     * @return
     */
    private String getEnumerationLiteralLiteral(EnumerationLiteral lit) {
        String result = null;
        List<Stereotype> stereotypes = lit.getAppliedStereotypes();
        Stereotype st = null;
        Iterator<Stereotype> iter = stereotypes.iterator();
        while (st == null & iter.hasNext()) {
            Stereotype aStereotype = iter.next();
            if (aStereotype.getName()
                    .equals(XsdStereotypeUtils.XSD_BASED_ENUMERATION_LITERAL)) {
                st = aStereotype;
            }
        }
        if (st != null) {
            Object val =
                    lit.getValue(st, XsdStereotypeUtils.XSD_PROPERTY_VALUE);
            if (val instanceof String) {
                result = (String) val;
            }
        } else {
            // Not XSD-derived
            DomainValue domainValue = EnumLitValueUtil.getDomainValue(lit);
            if (domainValue instanceof SingleValue) {
                // Value is stored in EnumLitExt extension
                // (as is the case for Enumerations that extend
                // PrimitiveTypes)
                result = ((SingleValue) domainValue).getValue();
            } else {
                // Value is implied by the name
                result = lit.getName();
            }
        }
        return result;
    }

    protected boolean isSizeOperator(SearchOperatorDTO op) {
        return op == SearchOperatorDTO.SIZE_EQ
                || op == SearchOperatorDTO.SIZE_NEQ
                || op == SearchOperatorDTO.SIZE_GT
                || op == SearchOperatorDTO.SIZE_GTE
                || op == SearchOperatorDTO.SIZE_LT
                || op == SearchOperatorDTO.SIZE_LTE;
    }

    protected void validateValuesValidity(AttributeSearchConditionDTO asc,
            Property property, Type type) {
        // Validate that the value(s), if literal, are appropriate for
        // the data type. If a named parameter
        // is set, and there are zero values, then there's nothing to
        // check (although check them if they _are_ there)
        SearchOperatorDTO op = asc.getOperator();
        List<String> values = asc.getValues();

        int actualValueCount = values.size();
        int expectedValueCount =
                SearchDTOUtil.getExpectedValueCount(asc.getOperator());
        boolean hasNP = asc.getParameterName() != null;
        boolean valid = true;

        // If values are given, then the quantity must match the
        // expected value count. If a parameter name is specified,
        // then it's acceptable to have no values
        if (expectedValueCount == -1) {
            if (actualValueCount == 0) {
                // A many-value operator having no values
                // is a problem unless there's a named parameter
                if (!hasNP) {
                    // error - must have at least one
                    addError(String
                            .format(Messages.SemanticSearchValidator_value_count_one_plus,
                                    operatorToString(op)));
                    valid = false;
                }
            }
        } else if (expectedValueCount == 0 && actualValueCount != 0) {
            // error - operator does not allow values
            addError(String
                    .format(Messages.SemanticSearchValidator_value_count_zero,
                            operatorToString(op)));
            valid = false;
        } else if (expectedValueCount != actualValueCount) {
            // A value count mismatch is a problem
            // unless there is a named parameter
            // and no values are specified
            if (!(actualValueCount == 0 && hasNP)) {
                // error - requires X values
                addError(String
                        .format(Messages.SemanticSearchValidator_value_count_number,
                                operatorToString(op),
                                expectedValueCount));
                valid = false;
            }
        }

        // If the count was valid, now check that the
        // string representation reflects the appropriate
        // data type.
        if (valid) {

            if (isSizeOperator(op)) {
                // Size operators require an integer,
                // regardless of the property's datatype.
                try {
                    Integer.parseInt(values.get(0));
                } catch (NumberFormatException e) {
                    addFormatError(property.getName(), op, values.get(0));
                }
            } else {
                if (type instanceof Enumeration) {
                    Enumeration enu = (Enumeration) type;
                    for (String value : values) {
                        boolean found = false;
                        for (Iterator<EnumerationLiteral> iter =
                                enu.getOwnedLiterals().iterator(); iter
                                .hasNext() && !found;) {
                            String lit =
                                    getEnumerationLiteralLiteral(iter.next());
                            if (lit != null && lit.equals(value)) {
                                found = true;
                            }
                        }
                        if (!found) {
                            // value was not a valid member of the enumeration
                            addError(String
                                    .format(Messages.SemanticSearchValidator_bad_enum,
                                            value,
                                            enu.getName()));
                        }
                    }
                } else if (type instanceof PrimitiveType) {
                    PrimitiveType primType = (PrimitiveType) type;
                    String typeName = getBaseTypeName(primType);
                    if (typeName
                            .equals(PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME)) {

                        // Ensure the value can be used to construct either an
                        // Integer or BigInteger, as per the property sub-type.
                        boolean isBigInteger = false;
                        Object subType =
                                PrimitivesUtil
                                        .getFacetPropertyValue(primType,
                                                PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_SUBTYPE,
                                                property);
                        if ((subType != null)
                                && (subType instanceof EnumerationLiteral)
                                && ((EnumerationLiteral) subType)
                                        .getName()
                                        .equals(PrimitivesUtil.INTEGER_SUBTYPE_FIXEDLENGTH)) {
                            isBigInteger = true;
                        }

                        for (String value : values) {
                            try {
                                if (isBigInteger) {
                                    new BigInteger(value);
                                } else {
                                    Integer.parseInt(value);
                                }
                            } catch (NumberFormatException e) {
                                // If it's a floating-point number with
                                // zero value after the decimal point, we can
                                // ignore that part.
                                String[] parts = value.split("\\.");
                                try {
                                    if (parts.length == 2
                                            && Integer.parseInt(parts[1]) == 0) {
                                        // Part after decimal point is zero
                                        // so just use the part before
                                        if (isBigInteger) {
                                            new BigInteger(parts[0]);
                                        } else {
                                            Integer.parseInt(parts[0]);
                                        }
                                    } else {
                                        // throw the original NFE
                                        throw e;
                                    }
                                } catch (NumberFormatException e1) {
                                    addFormatError(property.getName(),
                                            op,
                                            value);
                                }
                            }
                        }
                    } else if (typeName
                            .equals(PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME)) {
                        // Check validity of decimal-type values
                        boolean isBigDecimal = false;
                        Object subType =
                                PrimitivesUtil
                                        .getFacetPropertyValue(primType,
                                                PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_SUBTYPE,
                                                property);

                        // Check the sub-type as we are interested in fixed
                        // length
                        // integers
                        if ((subType != null)
                                && (subType instanceof EnumerationLiteral)
                                && ((EnumerationLiteral) subType)
                                        .getName()
                                        .equals(PrimitivesUtil.DECIMAL_SUBTYPE_FIXEDPOINT)) {
                            isBigDecimal = true;
                        }

                        for (String value : values) {
                            try {
                                if (isBigDecimal) {
                                    new BigDecimal(value);
                                } else {
                                    Double doub = Double.parseDouble(value);
                                    if (doub.isInfinite()) {
                                        addFormatError(property.getName(),
                                                op,
                                                value);
                                    }
                                }
                            } catch (NumberFormatException e) {
                                addFormatError(property.getName(), op, value);
                            }
                        }
                    } else if (typeName
                            .equals(PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME)) {
                        for (String value : values) {
                            if (!(value.equalsIgnoreCase(Boolean.TRUE
                                    .toString()) || value
                                    .equalsIgnoreCase(Boolean.FALSE.toString()))) {
                                addError(String
                                        .format(Messages.SemanticSearchValidator_value_format,
                                                property.getName(),
                                                operatorToString(op),
                                                value));
                            }
                        }
                    } else if (typeName
                            .equals(PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME)) {
                        for (String value : values) {
                            try {
                                calendarUtil.parseDateTimeValue(value, false);
                            } catch (IllegalArgumentException e) {
                                addError(String
                                        .format(Messages.SemanticSearchValidator_bad_datetime,
                                                value));
                            }
                        }

                    } else if (typeName
                            .equals(PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME)) {
                        for (String value : values) {
                            try {
                                calendarUtil.parseDateTimeTZValue(value, false);
                            } catch (IllegalArgumentException e) {
                                addError(String
                                        .format(Messages.SemanticSearchValidator_bad_datetimetz,
                                                value));
                            }
                        }
                    } else if (typeName
                            .equals(PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME)) {
                        for (String value : values) {
                            try {
                                calendarUtil.parseDateValue(value, false);
                            } catch (IllegalArgumentException e) {
                                addError(String
                                        .format(Messages.SemanticSearchValidator_bad_date,
                                                value));
                            }
                        }
                    } else if (typeName
                            .equals(PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME)) {
                        for (String value : values) {
                            try {
                                calendarUtil.parseTimeValue(value, false);
                            } catch (IllegalArgumentException e) {
                                addError(String
                                        .format(Messages.SemanticSearchValidator_bad_time,
                                                value));
                            }
                        }
                    }
                    // No need to check:
                    // Text - A string's a string
                    // ID, URI - are strings

                }
            }
        }
    }

    protected void addFormatError(String propertyName,
            SearchOperatorDTO operator, String value) {
        addError(String.format(Messages.SemanticSearchValidator_value_format,
                propertyName,
                operatorToString(operator),
                value));
    }

    private Property getClassifierProperty(Classifier cfr, String name) {
        // Attempt to find a feature with the name
        Property prop = (Property) cfr.getFeature(name);
        // If not found, attempt to get from supertype
        if (prop == null) {
            NamedElement member = cfr.getInheritedMember(name);
            if (member instanceof Property) {
                prop = (Property) member;
            }
        }
        return prop;
    }

    /**
     * Checks that the given sort attribute path corresponds to an attribute
     * that has no multiplicity-many in its path, generating an error if it
     * does.
     * 
     * @param clazz
     * @param sortAttribute
     */
    private void validateSortAttribute(org.eclipse.uml2.uml.Class clazz,
            SortAttributeDTO sortAttribute) {
        Class aClass = clazz;
        String fragments[] = sortAttribute.getPath().split("\\.");
        int fragCount = fragments.length;
        boolean valid = false;
        for (int i = 0; i < fragCount; i++) {
            String fragment = fragments[i];
            Property prop = getClassifierProperty(aClass, fragment);
            if (prop != null) {
                Type type = prop.getType();
                if (i == fragCount - 1) {
                    // leaf fragment
                    if (!(type instanceof Class)) {
                        if (prop.getUpper() == 1) {
                            valid = true;
                        }
                    }
                } else {
                    // non-leaf fragment
                    if (!(type instanceof Class)) {
                        break;
                    }
                    if (prop.getUpper() != 1) {
                        break;
                    }
                    aClass = (Class) type;
                }
            }
        }
        if (!valid) {
            addError(String
                    .format(Messages.SemanticSearchValidator_bad_sort_attribute,
                            sortAttribute.getPath()));
        }
    }

    public void validate(org.eclipse.uml2.uml.Class clazz,
            SearchConditionDTO condition, List<SortAttributeDTO> sortAttributes) {

        // Enforce clazz is a case type
        if (!BOMGlobalDataUtils.isCaseClass(clazz)) {
            addError(String
                    .format(Messages.SemanticSearchValidator_not_case_class,
                            clazz.getName()));
            return;
        }

        // Validate sort attributes
        for (SortAttributeDTO sortAttribute : sortAttributes) {
            validateSortAttribute(clazz, sortAttribute);
        }

        // Validate conditions
        List<AttributeSearchConditionDTO> ascs = flattenToASCs(condition);
        for (AttributeSearchConditionDTO asc : ascs) {
            String attrPath = asc.getAttrPath();
            attrPath = checkAndRemoveFunctionWrapping(attrPath);
            String fragments[] = attrPath.split("\\."); //$NON-NLS-1$

            // Keep track of the current type as we
            // navigate through the fragments of the attribute
            // path. We start with the top level class.
            Type type = clazz;
            Property prop = null;

            // Go through each fragment
            int fragCount = fragments.length;
            String suffix = null;
            for (int i = 0; i < fragCount; i++) {
                suffix = null;
                String fragment = fragments[i];
                int openPos = fragment.indexOf("["); //$NON-NLS-1$
                String namePart =
                        openPos == -1 ? fragment : fragment.substring(0,
                                openPos);

                // Attempt to find a feature with that name
                prop = getClassifierProperty((Classifier) type, namePart);

                if (prop == null) {
                    addError(String
                            .format(Messages.SemanticSearchValidator_bad_property,
                                    type.getName(),
                                    namePart));
                    break;
                } else {
                    type = prop.getType();

                    if (openPos != -1) {
                        int closePos = fragment.indexOf("]", openPos + 1); //$NON-NLS-1$
                        if (closePos != -1) {
                            suffix = fragment.substring(openPos + 1, closePos);
                            SuffixType st = determineSuffixType(suffix);
                            if (st == null) {
                                // This is theoretically impossible if the DTO
                                // was produced by the DQL parser.
                                addError(String
                                        .format(Messages.SemanticSearchValidator_bad_fragment_suffix,
                                                suffix,
                                                fragment));
                                break;
                            }
                            if (prop.getUpper() == 1) {
                                addError(String
                                        .format(Messages.SemanticSearchValidator_fragment_suffix_on_non_many,
                                                fragment));
                                break;
                            }
                        } else {
                            // error - no closing square bracket
                            addError(String
                                    .format(Messages.SemanticSearchValidator_close_square_bracket_missing,
                                            fragment));
                            break;
                        }
                    }
                }

                if (type instanceof PrimitiveType) {
                    // Ensure this is the leaf
                    if (i != fragCount - 1) {
                        addError(String
                                .format(Messages.SemanticSearchValidator_PT_on_non_leaf,
                                        fragment));
                        break;
                    }
                } else if (type instanceof Enumeration) {
                    // Ensure this is the leaf
                    if (i != fragCount - 1) {
                        addError(String
                                .format(Messages.SemanticSearchValidator_enumeration_on_non_leaf,
                                        fragment));
                        break;
                    }
                }
            }

            // Validate that the operator is appropriate for the attribute.
            if (prop != null) {
                validateOperatorValidity(asc,
                        type,
                        prop.getName(),
                        prop.getUpper() != 1,
                        suffix != null);

                validateValuesValidity(asc, prop, type);

                // Warning: If non-CID/searchable used, warn re performance
                // (as long as it's an attribute rather than an association)
                if (prop.getAssociation() == null) {
                    if (!BOMGlobalDataUtils.isCID(prop)
                            && !BOMGlobalDataUtils.isSearchable(prop)) {
                        addWarning(String
                                .format(Messages.SemanticSearchValidator_non_searchable_performance,
                                        prop.getName()));
                    }
                }
            }

        }
    }
}
