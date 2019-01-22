package com.tibco.xpd.rql.parser.validator;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.rql.parser.validator.messages"; //$NON-NLS-1$

    public static String RQLExpressionValidator_nameFollowingOrEqualsWord;

    public static String RQLExpressionValidator_nameFollowingWord;

    public static String RQLExpressionValidator_namePrecedingOrEqualWord;

    public static String RQLExpressionValidator_namePrecedingWord;

    public static String RQLExpressionValidator_NavigabilityError;

    public static String RQLExpressionValidator_No_Entities_orTypeFound;

    public static String RQLExpressionValidator_NoAttributesWithName;

    public static String RQLExpressionValidator_NoEntitiesEndingWithName;

    public static String RQLExpressionValidator_NoEntitiesStartingWithName;

    public static String RQLExpressionValidator_NoEntitiesWithName;

    public static String RQLExpressionValidator_NoEntitiesWithType;

    public static String RQLExpressionValidator_NoEntitiesWithTypeEndingWith;

    public static String RQLExpressionValidator_NoEntitiesWithTypeStartingWith;

    public static String RQLExpressionValidator_QualifierNotValidForEntities;

    public static String RQLExpressionValidator_typeFollowingOrEqualWord;

    public static String RQLExpressionValidator_typeFollowingWord;

    public static String RQLExpressionValidator_typePrecedingOrEqualWord;

    public static String RQLExpressionValidator_typePrecedingWord;

    public static String RQLExpressionValidator_TypeNotValidForEntity;

    public static String RQLExpressionValidator_No_Entities_ForQueryInContext;

    public static String RQLExpressionValidator_DynamicRootReference;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
