package com.tibco.xpd.process.xpath.parser.validator.xpath;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.process.xpath.parser.validator.xpath.messages"; //$NON-NLS-1$

    public static String XPathVariableReferenceValidator_variable_undefined;
    
    public static String XPathVariableReferenceValidator_node_undefined;
    
    public static String XPathVariableReferenceValidator_attribute_undefined;
    
    public static String XPathVariableReferenceValidator_method_unsupported;
    
    public static String XPathVariableReferenceValidator_method_wrong_parameter_number;
    
    public static String XPathVariableReferenceValidator_method_wrong_parameter_type;

    public static String XPathVariableReferenceValidator_method_wrong_parameter_multiplicity;

    public static String XPathVariableReferenceValidator_method_unresolved_parameter_type;

    public static String XPathVariableReferenceValidator_script_unresolved_expression_type;

    public static String XPathVariableReferenceValidator_variable_unknown_property;
    
    public static String XPathVariableReferenceValidator_variable_unknown_attribute;
    
    public static String XPathVariableReferenceValidator_mapping_unmatch_types;
    
    public static String XPathVariableReferenceValidator_mapping_unmatch_multiplicity;
    
    public static String XPathVariableReferenceValidator_illegal_axis_name;

    public static String XPathVariableReferenceValidator_wrong_indexInPredicate;
    
    public static String XPathVariableReferenceValidator_only_boolean_Number_PredicateAllowed;
    
    public static String XPathVariableReferenceValidator_invalid_logicalExpression_sides;
    
    public static String XPathVariableReferenceValidator_literal_mapping_unmatch_types;
    
    public static String XPathVariableReferenceValidator_literal_dateFormat;

    public static String XPathVariableReferenceValidator_literal_timeFormat;

    public static String XPathVariableReferenceValidator_literal_dateTimeFormat;
    
    public static String XPathVariableReferenceValidator_literal_validBooleanValues;
    
    public static String XPathVariableReferenceValidator_number_mapping_unmatch_types;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
