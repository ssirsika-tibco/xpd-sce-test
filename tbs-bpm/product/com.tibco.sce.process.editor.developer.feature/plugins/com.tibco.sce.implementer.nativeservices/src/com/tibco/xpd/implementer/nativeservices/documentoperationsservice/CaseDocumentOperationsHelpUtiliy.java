/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.implementer.nativeservices.documentoperationsservice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.xpdExtension.CMISQueryOperator;
import com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression;
import com.tibco.xpd.xpdExtension.DocumentOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Utility Class for Case Document Find By Query Operation's CMIS Query Editor
 * support.The Utility Class provides methods and Constants used for the Editor.
 * 
 * @author aprasad
 * @since 02-Sep-2014
 */
public class CaseDocumentOperationsHelpUtiliy {

    /* Operators to be displayed in the CMISQL Editor */
    private static final String EQUAL_OPR = "="; //$NON-NLS-1$

    private static final String NOT_EQUAL_OPR = "<>"; //$NON-NLS-1$

    private static final String GREATER_THAN_OPR = ">"; //$NON-NLS-1$

    private static final String GREATER_THAN_EQUAL_OPR = ">="; //$NON-NLS-1$

    private static final String LESS_THAN_OPR = "<"; //$NON-NLS-1$

    private static final String LESS_THAN_EQUAL_OPR = "<="; //$NON-NLS-1$

    private static final String NOT_NULL_OPR = "IS NOT NULL"; //$NON-NLS-1$

    private static final String NULL_OPR = "IS NULL"; //$NON-NLS-1$

    private static final String NOT_LIKE_OPR = "NOT LIKE"; //$NON-NLS-1$

    private static final String NOT_CONTAINS_OPR = "NOT CONTAINS"; //$NON-NLS-1$

    private static final String NOT_IN_OPR = "NOT IN"; //$NON-NLS-1$

    /**
     * Map Containing CMIS Operator and Data Type Compatibility chart to be used
     * while validating the correct type usage with an Operator.
     */
    public static Map<CMISQueryOperator, List<BasicTypeType>> CMIS_OPERATOR_TYPE_COMPATIBILITY_MAP;

    static {

        /* initialize the Compatibility Map */
        CMIS_OPERATOR_TYPE_COMPATIBILITY_MAP =
                new HashMap<CMISQueryOperator, List<BasicTypeType>>();

        /* EQUAL [DateTime, Decimal, Integer,ID, String, URI, Boolean] */
        CMIS_OPERATOR_TYPE_COMPATIBILITY_MAP.put(CMISQueryOperator.EQUAL,
                Arrays.asList(BasicTypeType.DATETIME_LITERAL,
                        BasicTypeType.FLOAT_LITERAL,
                        BasicTypeType.INTEGER_LITERAL,
                        BasicTypeType.STRING_LITERAL,
                        BasicTypeType.BOOLEAN_LITERAL));

        /* NOT EQUAL [DateTime, Decimal, Integer,ID, String, URI] */
        CMIS_OPERATOR_TYPE_COMPATIBILITY_MAP.put(CMISQueryOperator.NOT_EQUAL,
                Arrays.asList(BasicTypeType.DATETIME_LITERAL,
                        BasicTypeType.FLOAT_LITERAL,
                        BasicTypeType.INTEGER_LITERAL,
                        BasicTypeType.STRING_LITERAL));

        /* LESS THAN [DateTime, Decimal, Integer] */
        CMIS_OPERATOR_TYPE_COMPATIBILITY_MAP.put(CMISQueryOperator.LESS_THAN,
                Arrays.asList(BasicTypeType.DATETIME_LITERAL,
                        BasicTypeType.FLOAT_LITERAL,
                        BasicTypeType.INTEGER_LITERAL));

        /* LESS THAN EQUAL [DateTime, Decimal, Integer] */
        CMIS_OPERATOR_TYPE_COMPATIBILITY_MAP
                .put(CMISQueryOperator.LESS_THAN_EQUAL, Arrays
                        .asList(BasicTypeType.DATETIME_LITERAL,
                                BasicTypeType.FLOAT_LITERAL,
                                BasicTypeType.INTEGER_LITERAL));

        /* GREATER THAN [DateTime, Decimal, Integer] */
        CMIS_OPERATOR_TYPE_COMPATIBILITY_MAP
                .put(CMISQueryOperator.GREATER_THAN, Arrays
                        .asList(BasicTypeType.DATETIME_LITERAL,
                                BasicTypeType.FLOAT_LITERAL,
                                BasicTypeType.INTEGER_LITERAL));

        /* GREATER THAN EQUAL [DateTime, Decimal, Integer] */
        CMIS_OPERATOR_TYPE_COMPATIBILITY_MAP
                .put(CMISQueryOperator.GREATER_THAN_EQUAL, Arrays
                        .asList(BasicTypeType.DATETIME_LITERAL,
                                BasicTypeType.FLOAT_LITERAL,
                                BasicTypeType.INTEGER_LITERAL));

        /* IN [DateTime, Decimal, Integer, ID, String, URI] */
        CMIS_OPERATOR_TYPE_COMPATIBILITY_MAP.put(CMISQueryOperator.IN, Arrays
                .asList(BasicTypeType.DATETIME_LITERAL,
                        BasicTypeType.FLOAT_LITERAL,
                        BasicTypeType.INTEGER_LITERAL,
                        BasicTypeType.STRING_LITERAL));

        /* NOT IN [DateTime, Decimal, Integer, ID, String, URI] */
        CMIS_OPERATOR_TYPE_COMPATIBILITY_MAP.put(CMISQueryOperator.NOT_IN,
                Arrays.asList(BasicTypeType.DATETIME_LITERAL,
                        BasicTypeType.FLOAT_LITERAL,
                        BasicTypeType.INTEGER_LITERAL,
                        BasicTypeType.STRING_LITERAL));

        /* LIKE [String, URI] */
        CMIS_OPERATOR_TYPE_COMPATIBILITY_MAP.put(CMISQueryOperator.LIKE,
                Arrays.asList(BasicTypeType.STRING_LITERAL));

        /* NOT LIKE [String, URI] */
        CMIS_OPERATOR_TYPE_COMPATIBILITY_MAP.put(CMISQueryOperator.NOT_LIKE,
                Arrays.asList(BasicTypeType.STRING_LITERAL));

        /* CONTAINS [String] */
        CMIS_OPERATOR_TYPE_COMPATIBILITY_MAP.put(CMISQueryOperator.CONTAINS,
                Arrays.asList(BasicTypeType.STRING_LITERAL));

        /* NOT CONTAINA [String] */
        CMIS_OPERATOR_TYPE_COMPATIBILITY_MAP
                .put(CMISQueryOperator.NOT_CONTAINS,
                        Arrays.asList(BasicTypeType.STRING_LITERAL));
    }

    /**
     * Array of Supported CMISQL properties.
     */
    public static final CMISQL_PROPERTY[] SUPPORTED_CMIS_PROPERTIES =
            new CMISQL_PROPERTY[] { CMISQL_PROPERTY.CMISQL_NAME,
                    CMISQL_PROPERTY.CMISQL_CREATED_BY,
                    CMISQL_PROPERTY.CMISQL_CREATION_DATE,
                    CMISQL_PROPERTY.CMISQL_LAST_MODIFIED_BY,
                    CMISQL_PROPERTY.CMISQL_LAST_MODIFICATION_DATE,
                    CMISQL_PROPERTY.CMISQL_DOCUMENT };

    /**
     * 
     * Enumeration of CMIS Property containing the CMIS Property and
     * valid/allowed DataTypes.
     * 
     * @author aprasad
     * @since 02-Sep-2014
     */
    public enum CMISQL_PROPERTY {

        CMISQL_NAME("cmis:name", Arrays.asList(BasicTypeType.STRING_LITERAL)), CMISQL_CREATION_DATE( //$NON-NLS-1$
                "cmis:creationDate", Arrays.asList(//$NON-NLS-1$
                        BasicTypeType.DATETIME_LITERAL)), CMISQL_CREATED_BY(
                "cmis:createdBy", Arrays.asList(BasicTypeType.STRING_LITERAL)), CMISQL_LAST_MODIFIED_BY( //$NON-NLS-1$
                "cmis:lastModifiedBy", Arrays //$NON-NLS-1$
                        .asList(BasicTypeType.STRING_LITERAL)), CMISQL_LAST_MODIFICATION_DATE(
                "cmis:lastModificationDate", Arrays //$NON-NLS-1$
                        .asList(BasicTypeType.DATETIME_LITERAL)), CMISQL_DOCUMENT(
                CaseDocumentQueryExpression.CMIS_DOCUMENT_PROPERTY, Arrays
                        .asList(BasicTypeType.STRING_LITERAL));

        /**
         * CMIS Property Name.
         */
        private final String cmisPropertyName;

        /**
         * Supported Data Types.
         */
        private final List<BasicTypeType> supportedDataTypes;

        /**
         * Constructor takes CMIS property name and the List of Supported Data
         * Type for the given property
         * 
         * @param name
         * @param supportedDataType
         */
        CMISQL_PROPERTY(String name, List<BasicTypeType> supportedDataType) {

            this.cmisPropertyName = name;
            this.supportedDataTypes = supportedDataType;
        }

        /**
         * @return CMIS Property Name.
         */
        public String getPropertyName() {
            return cmisPropertyName;
        }

        /**
         * @return List of Supported Data Types for this CMIS Property.
         */
        public List<BasicTypeType> getSupportedDataTypes() {

            return supportedDataTypes;
        }

        /**
         * Return {@link CMISQL_PROPERTY} with the given name.
         * 
         * @param propertyName
         *            name of the property to search for.
         * @return {@link CMISQL_PROPERTY} with the given name.
         */
        public static final CMISQL_PROPERTY getCMISPropertyByName(
                String propertyName) {

            for (CMISQL_PROPERTY property : values()) {

                if (property.getPropertyName().equals(propertyName)) {
                    return property;
                }
            }
            return null;
        }
    }

    /**
     * Returns the map of Operators with their String representations to be
     * Displayed in the Query Editor Drop Down.
     * 
     * @return Map of Supported Operators <Display String for Operator,Model
     *         element CMISQueryOperator>
     */
    public static Map<String, CMISQueryOperator> getSupportedOperators() {

        LinkedHashMap<String, CMISQueryOperator> supportedOperators =
                new LinkedHashMap<String, CMISQueryOperator>();

        supportedOperators.put(EQUAL_OPR, CMISQueryOperator.EQUAL);
        supportedOperators.put(NOT_EQUAL_OPR, CMISQueryOperator.NOT_EQUAL);

        supportedOperators
                .put(GREATER_THAN_OPR, CMISQueryOperator.GREATER_THAN);

        supportedOperators.put(GREATER_THAN_EQUAL_OPR,
                CMISQueryOperator.GREATER_THAN_EQUAL);

        supportedOperators.put(LESS_THAN_OPR, CMISQueryOperator.LESS_THAN);

        supportedOperators.put(LESS_THAN_EQUAL_OPR,
                CMISQueryOperator.LESS_THAN_EQUAL);

        supportedOperators.put(NULL_OPR, CMISQueryOperator.NULL);

        supportedOperators.put(NOT_NULL_OPR, CMISQueryOperator.NOT_NULL);

        supportedOperators.put(CMISQueryOperator.CONTAINS.getName(),
                CMISQueryOperator.CONTAINS);

        supportedOperators
                .put(NOT_CONTAINS_OPR, CMISQueryOperator.NOT_CONTAINS);

        supportedOperators.put(CMISQueryOperator.LIKE.getName(),
                CMISQueryOperator.LIKE);

        supportedOperators.put(NOT_LIKE_OPR, CMISQueryOperator.NOT_LIKE);

        supportedOperators.put(CMISQueryOperator.IN.getName(),
                CMISQueryOperator.IN);

        supportedOperators.put(NOT_IN_OPR, CMISQueryOperator.NOT_IN);

        return supportedOperators;
    }

    /**
     * Returns display String for the given {@link CMISQueryOperator}, to be
     * used in the Query Editor.
     * 
     * @param cmisOperator
     *            {@link CMISQueryOperator} for which the display String is to
     *            be returned.
     * @return display String for the given {@link CMISQueryOperator}, to be
     *         used in the Query Editor.
     */
    public static final String getDisplayOperatorString(
            CMISQueryOperator cmisOperator) {

        switch (cmisOperator.getValue()) {

        case CMISQueryOperator.CONTAINS_VALUE:
            return CMISQueryOperator.CONTAINS.getName();

        case CMISQueryOperator.NOT_CONTAINS_VALUE:
            return CMISQueryOperator.NOT_CONTAINS.getName();

        case CMISQueryOperator.EQUAL_VALUE:
            return EQUAL_OPR;

        case CMISQueryOperator.NOT_EQUAL_VALUE:
            return NOT_EQUAL_OPR;

        case CMISQueryOperator.GREATER_THAN_EQUAL_VALUE:
            return GREATER_THAN_EQUAL_OPR;

        case CMISQueryOperator.GREATER_THAN_VALUE:
            return GREATER_THAN_OPR;

        case CMISQueryOperator.IN_VALUE:
            return CMISQueryOperator.IN.getName();

        case CMISQueryOperator.NOT_IN_VALUE:
            return CMISQueryOperator.NOT_IN.getName();

        case CMISQueryOperator.LESS_THAN_EQUAL_VALUE:
            return LESS_THAN_EQUAL_OPR;

        case CMISQueryOperator.LESS_THAN_VALUE:
            return LESS_THAN_OPR;

        case CMISQueryOperator.LIKE_VALUE:
            return CMISQueryOperator.LIKE.getName();

        case CMISQueryOperator.NOT_LIKE_VALUE:
            return CMISQueryOperator.NOT_LIKE.getName();

        }

        return null;
    }

    /**
     * Returns {@link DocumentOperation} for the given Activity if it is an
     * instance of Document Operations Service task.Returns null if the Task
     * Activity is not Document Operations Service Task Activity.Returns null
     * otherwise.
     * 
     * @param activity
     *            {@link Activity} to get the {@link DocumentOperation} for.
     * @return {@link DocumentOperation} if this is a Document Operations
     *         Service Task Activity and contains {@link DocumentOperation},
     *         null otherwise.
     */
    public static DocumentOperation getDocumentOperation(Activity activity) {

        TaskType taskType = TaskObjectUtil.getTaskTypeStrict(activity);

        if (TaskType.SERVICE_LITERAL.equals(taskType)) {

            String extensionId =
                    TaskObjectUtil.getTaskImplementationExtensionId(activity);

            if (TaskImplementationTypeDefinitions.DOCUMENT_OPERATIONS
                    .equals(extensionId)) {

                TaskService taskService =
                        ((Task) activity.getImplementation()).getTaskService();

                Object otherElement =
                        Xpdl2ModelUtil.getOtherElement(taskService,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_DocumentOperation());

                if (otherElement instanceof DocumentOperation) {
                    return (DocumentOperation) otherElement;
                }
            }

        }
        return null;
    }

}
