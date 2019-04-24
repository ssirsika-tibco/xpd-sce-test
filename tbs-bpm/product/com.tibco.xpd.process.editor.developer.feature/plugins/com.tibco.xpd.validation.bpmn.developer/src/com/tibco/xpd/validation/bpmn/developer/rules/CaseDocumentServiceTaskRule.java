/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.rules;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceData;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceDataUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.implementer.nativeservices.documentoperationsservice.CaseDocumentOperationsHelpUtiliy;
import com.tibco.xpd.implementer.nativeservices.documentoperationsservice.CaseDocumentOperationsHelpUtiliy.CMISQL_PROPERTY;
import com.tibco.xpd.implementer.nativeservices.documentoperationsservice.DocumentOperationsConsts;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.bpmn.developer.internal.Messages;
import com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule;
import com.tibco.xpd.xpdExtension.CMISQueryOperator;
import com.tibco.xpd.xpdExtension.CaseDocFindOperations;
import com.tibco.xpd.xpdExtension.CaseDocRefOperations;
import com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression;
import com.tibco.xpd.xpdExtension.DocumentOperation;
import com.tibco.xpd.xpdExtension.FindByFileNameOperation;
import com.tibco.xpd.xpdExtension.FindByQueryOperation;
import com.tibco.xpd.xpdExtension.LinkCaseDocOperation;
import com.tibco.xpd.xpdExtension.LinkSystemDocumentOperation;
import com.tibco.xpd.xpdExtension.MoveCaseDocOperation;
import com.tibco.xpd.xpdExtension.UnlinkCaseDocOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Validation Rule for Document Operations Activity Service Task.Validates the
 * type and multiplicity of the inputs, and missing inputs.
 * 
 * <p>
 * Validates CMIS Query Expression for following.
 * <ol>
 * <li>First Condition Expression Should not contain a Logical Grouping.</li>
 * <li>CONTAINS expression should not use a CMIS Property.</li>
 * <li>'NULL' or '! Null' expressions should not contain a value.</li>
 * </ol>
 * <p>
 * 
 * @author aprasad
 * @since 21-Aug-2014
 */
public class CaseDocumentServiceTaskRule extends
        ProcessActivitiesValidationRule {

    /* Sid ACE-475 suppress Decision service task config rules for ACE env' */
    private static final boolean suppressDocumentTaskValidationsForAce = true;

    private static final String OPERATION_NOT_SELECTED_ERROR =
            "bpmn.dev.caseDocumentTask.operationNotSelected"; //$NON-NLS-1$

    private static final String FIELD_PARAM_DOES_NOT_EXIST =
            "bpmn.dev.caseDocumentTask.fieldOrParamDoesNotExist"; //$NON-NLS-1$

    private static final String MOVE_DOC_SRC_TRGT_CANNOT_BE_SAME =
            "bpmn.dev.caseDocumentTask.moveDocOperationSrcTargetInvalid"; //$NON-NLS-1$

    private static final String ARRAY_FIELDS_NOT_ALLOWED =
            "bpmn.dev.caseDocumentTask.noArrayRef"; //$NON-NLS-1$

    private static final String DATA_TYPE_MISMATCH =
            "bpmn.dev.caseDocumentTask.DataTypeMismatch"; //$NON-NLS-1$

    /* CMIS Query Error Messages */
    private static final String MISSING_LOGICAL_GROUP =
            "bpmn.dev.caseDocumentTask.MissingLogicalGrouping"; //$NON-NLS-1$

    private static final String MISSING_FIELD_VALUE =
            "bpmn.dev.caseDocumentTask.MissingFieldValue"; //$NON-NLS-1$

    private static final String MISSING_CMIS_PROPERTY =
            "bpmn.dev.caseDocumentTask.MissingCmisProperty"; //$NON-NLS-1$

    private static final String FIELD_NOT_FOUND =
            "bpmn.dev.caseDocumentTask.FieldNotFound"; //$NON-NLS-1$

    private static final String CMISPROPERTY_TYPE_MISMATCH =
            "bpmn.dev.caseDocumentTask.CmisPropertyProcessDataFieldMismatch"; //$NON-NLS-1$

    private static final String OPERATION_PROCESS_DATA_MISMATCH =
            "bpmn.dev.caseDocumentTask.ProcessDataOperationMismatch"; //$NON-NLS-1$

    private static final String FIRST_EXP_LOGICAL_JOIN_NOT_ALLOWED =
            "bpmn.dev.caseDocumentTask.FirstExpLogicalJoinNotAllowed"; //$NON-NLS-1$

    private static final String CONTAINS_SHOULD_ONLY_USE_CMIS_DOCUMENT =
            "bpmn.dev.caseDocumentTask.ContainsOnlyAllowedWithCmisDocumentProperty"; //$NON-NLS-1$

    private static final String NULL_OPERATOR_VALUE_NOT_ALLOWED =
            "bpmn.dev.caseDocumentTask.ValueNotAllowedForNullOperator"; //$NON-NLS-1$

    private static final String OPERATION_MANDATORY_FOR_EXPRESSION =
            "bpmn.dev.caseDocumentTask.OperationMandatoryForExpression"; //$NON-NLS-1$

    private static final String MULTIVALUED_NOT_ALLOWED_FOR_OPERATOR =
            "bpmn.dev.caseDocumentTask.MultivaluedNotAllowedWithOperator"; //$NON-NLS-1$

    private static final String SINGLE_VALUED_NOT_ALLOWED_FOR_OPERATOR =
            "bpmn.dev.caseDocumentTask.SinglevaluedNotAllowedWithOperator"; //$NON-NLS-1$

    private static final String OPERATOR_NOT_SUPPORTED =
            "bpmn.dev.caseDocumentTask.OperatorNotSupported"; //$NON-NLS-1$

    private static final String CMISPROPERTY_NOT_SUPPORTED =
            "bpmn.dev.caseDocumentTask.CmisPropertyNotSupported"; //$NON-NLS-1$

    private static final String EXPRESSIONS_NESTING_INCORRECT =
            "bpmn.dev.caseDocumentTask.CmisQueryExpressionsNestingIncorrect"; //$NON-NLS-1$

    private static final String CMIS_DOC_ONLY_WITH_CONTAINS =
            "bpmn.dev.caseDocumentTask.CmisDocumentPropertyOnlyAllowedWithContains"; //$NON-NLS-1$


    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule#validate(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     */
    @Override
    protected void validate(Activity activity) {
        if (suppressDocumentTaskValidationsForAce) {
            return;
        }

        /* Validate only Case Document Service Task Activity */
        TaskType taskType = TaskObjectUtil.getTaskTypeStrict(activity);

        if (TaskType.SERVICE_LITERAL.equals(taskType)) {

            String extensionId =
                    TaskObjectUtil.getTaskImplementationExtensionId(activity);

            if (TaskImplementationTypeDefinitions.DOCUMENT_OPERATIONS
                    .equals(extensionId)) {

                DocumentOperation documentOperation = null;

                TaskService taskService =
                        ((Task) activity.getImplementation()).getTaskService();

                Object otherElement =
                        Xpdl2ModelUtil.getOtherElement(taskService,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_DocumentOperation());

                if (otherElement instanceof DocumentOperation) {
                    documentOperation = (DocumentOperation) otherElement;
                }

                if (!isDocumentOperationSet(documentOperation)) {

                    addIssue(OPERATION_NOT_SELECTED_ERROR, activity);
                    /*
                     * Other validations are not possible if Operation itself is
                     * not set so return from here
                     */
                    return;
                }

                validateInputs(documentOperation, activity);

            }

        }

    }

    /**
     * Validates the Inputs of the Document Operation.
     * 
     * @param documentOperation
     *            , Document Operation to validate.
     * @param activity
     *            Document Operations Activity.
     */
    private void validateInputs(DocumentOperation documentOperation,
            Activity activity) {

        CaseDocFindOperations caseDocFindOperations =
                documentOperation.getCaseDocFindOperations();

        CaseDocRefOperations caseDocRefOperation =
                documentOperation.getCaseDocRefOperation();

        LinkSystemDocumentOperation linkSysDocOperation =
                documentOperation.getLinkSystemDocumentOperation();

        Map<String, ActivityInterfaceData> activityInterfaceDataMap =
                new HashMap<String, ActivityInterfaceData>();

        for (ActivityInterfaceData data : ActivityInterfaceDataUtil
                .getActivityInterfaceData(activity)) {

            activityInterfaceDataMap.put(data.getName(), data);
        }

        /* Validate Find Operations */
        if (caseDocFindOperations != null) {

            validateFindOperationInputs(activity,
                    caseDocFindOperations,
                    activityInterfaceDataMap);
        }

        /* Validate Link System Operation */
        if (linkSysDocOperation != null) {

            validateLinkSystemOperationInputs(activity,
                    linkSysDocOperation,
                    activityInterfaceDataMap);
        }

        /* Validate Document Reference Operations */
        if (caseDocRefOperation != null) {

            String oprName =
                    getOperationNameForErrorMsgString(documentOperation);

            /* Validate CaseDocumentRef Field */
            if (!isValidDocumentReferenceField(activity,
                    oprName,
                    caseDocRefOperation.getCaseDocumentRefField(),
                    activityInterfaceDataMap,
                    true,
                    false,
                    DocumentOperationsConsts.DOCUMENT_REF_FIELD_LABEL)) {
                return;
            }

            /* Validate LinkCaseDocumentOperation */
            LinkCaseDocOperation linkCaseDocOperation =
                    caseDocRefOperation.getLinkCaseDocOperation();

            if (linkCaseDocOperation != null) {

                isValidCaseReferenceField(activity,
                        oprName,
                        linkCaseDocOperation.getTargetCaseRefField(),
                        activityInterfaceDataMap,
                        true,
                        false,
                        DocumentOperationsConsts.CASE_REF_FIELD_LABEL);

            }

            /* Validate Unlink CaseDocumentOperation */
            UnlinkCaseDocOperation unlinkCaseDocOperation =
                    caseDocRefOperation.getUnlinkCaseDocOperation();

            if (unlinkCaseDocOperation != null) {

                isValidCaseReferenceField(activity,
                        oprName,
                        unlinkCaseDocOperation.getSourceCaseRefField(),
                        activityInterfaceDataMap,
                        true,
                        false,
                        DocumentOperationsConsts.CASE_REF_FIELD_LABEL);

            }

            /* Validate Move Operation */
            MoveCaseDocOperation moveCaseDocOperation =
                    caseDocRefOperation.getMoveCaseDocOperation();

            if (moveCaseDocOperation != null) {

                String sourceCaseRefField =
                        moveCaseDocOperation.getSourceCaseRefField();
                if (!isValidCaseReferenceField(activity,
                        oprName,
                        sourceCaseRefField,
                        activityInterfaceDataMap,
                        true,
                        false,
                        DocumentOperationsConsts.SOURCE_CASE_FIELD_LABEL)) {
                    return;
                }

                String targetCaseRefField =
                        moveCaseDocOperation.getTargetCaseRefField();

                if (!isValidCaseReferenceField(activity,
                        oprName,
                        targetCaseRefField,
                        activityInterfaceDataMap,
                        true,
                        false,
                        DocumentOperationsConsts.TARGET_CASE_FIELD_LABEL)) {
                    return;
                }

                if (sourceCaseRefField != null && targetCaseRefField != null) {

                    if (sourceCaseRefField.equals(targetCaseRefField)) {
                        addIssue(MOVE_DOC_SRC_TRGT_CANNOT_BE_SAME, activity);
                    }
                }

            }

        }
    }

    /**
     * Validates {@link LinkSystemDocumentOperation} and elements of the
     * operation for Correct type valid DataField and completeness.
     * 
     * @param activity
     *            Document Operation Task
     * @param linkSysDocOperation
     *            {@link LinkSystemDocumentOperation} to validate.
     * @param activityInterfaceDataMap
     *            Activity interface data Map<Data Field Name,
     *            {@link ActivityInterfaceData}>.
     */
    private void validateLinkSystemOperationInputs(Activity activity,
            LinkSystemDocumentOperation linkSysDocOperation,
            Map<String, ActivityInterfaceData> activityInterfaceDataMap) {

        if (linkSysDocOperation != null) {

            /* Validate DocumentId field */
            String documentId = linkSysDocOperation.getDocumentId();
            String oprName =
                    Messages.CaseDocumentServiceTaskRule_LinkSysDocOprStringForErrorMsg;

            if (isMissingEntry(documentId,
                    activity,
                    oprName,
                    DocumentOperationsConsts.LINK_SYS_DOC_ID_FIELD_LABEL)) {
                /* Do Not Validate Further */
                return;
            }

            BasicType textType = Xpdl2Factory.eINSTANCE.createBasicType();
            textType.setType(BasicTypeType.STRING_LITERAL);

            List<String> addInfo = new LinkedList<String>();
            addInfo.add(DocumentOperationsConsts.LINK_SYS_DOC_ID_FIELD_LABEL);
            addInfo.add(BasicTypeType.STRING_LITERAL.getLiteral());

            if (!isValidActivityRelevantData(activity,
                    documentId,
                    activityInterfaceDataMap,
                    false,
                    false,
                    textType,
                    DATA_TYPE_MISMATCH,
                    addInfo)) {
                /* Do Not Validate Further */
                return;
            }

            /* Validate Case Reference */

            if (!isValidCaseReferenceField(activity,
                    oprName,
                    linkSysDocOperation.getCaseRefField(),
                    activityInterfaceDataMap,
                    false,
                    false,
                    DocumentOperationsConsts.CASE_REF_FIELD_LABEL)) {
                /* Do Not Validate Further */
                return;
            }

            /* validate Return Document Reference */

            isValidDocumentReferenceField(activity,
                    oprName,
                    linkSysDocOperation.getReturnCaseDocRefField(),
                    activityInterfaceDataMap,
                    false,
                    false,
                    DocumentOperationsConsts.LINK_SYS_RET_DOC_REF_FIELD_LABEL);
        }

    }

    /**
     * Validates the input of the FindByName and Find By Query Operations,and
     * elements of the operation for Correct type valid DataField and
     * completeness.
     * 
     * @param activity
     *            Document Operation Task
     * @param caseDocFindOperations
     *            {@link CaseDocFindOperations} to validate.
     * @param activityInterfaceDataMap
     *            Activity interface data Map<Data Field Name,
     *            {@link ActivityInterfaceData}>.
     */
    private void validateFindOperationInputs(Activity activity,
            CaseDocFindOperations caseDocFindOperations,
            Map<String, ActivityInterfaceData> activityInterfaceDataMap) {

        if (caseDocFindOperations != null) {

            String oprName =
                    Messages.CaseDocumentServiceTaskRule_FindDocOprStringForErrorMsg;
            /* Validate caseRef Field */

            if (!isValidCaseReferenceField(activity,
                    oprName,
                    caseDocFindOperations.getCaseRefField(),
                    activityInterfaceDataMap,
                    true,
                    false,
                    DocumentOperationsConsts.CASE_REF_FIELD_LABEL)) {
                /* Do Not validate Further */
                return;
            }

            /* Validate Case Document Reference Field */
            if (!isValidDocumentReferenceField(activity,
                    oprName,
                    caseDocFindOperations.getReturnCaseDocRefsField(),
                    activityInterfaceDataMap,
                    false,
                    false,
                    DocumentOperationsConsts.FIND_RET_DOC_REF_FIELD_LABEL)) {
                /* Do Not validate Further */
                return;
            }

            FindByFileNameOperation findByFileNameOperation =
                    caseDocFindOperations.getFindByFileNameOperation();

            if (findByFileNameOperation != null) {

                String fileNameFieldValue =
                        findByFileNameOperation.getFileNameField();

                if (isMissingEntry(fileNameFieldValue,
                        activity,
                        Messages.CaseDocumentServiceTaskRule_FindDocOprStringForErrorMsg,
                        DocumentOperationsConsts.FIND_FILENAME_FIELD_LABEL)) {
                    /* Do not validate further */
                    return;
                }

                BasicType fileNameType =
                        Xpdl2Factory.eINSTANCE.createBasicType();
                fileNameType.setType(BasicTypeType.STRING_LITERAL);

                List<String> addInfo = new LinkedList<String>();
                addInfo.add(DocumentOperationsConsts.FIND_FILENAME_FIELD_LABEL);
                addInfo.add(BasicTypeType.STRING_LITERAL.getName());

                /* Validate FileName Field */
                if (!isValidActivityRelevantData(activity,
                        fileNameFieldValue,
                        activityInterfaceDataMap,
                        true,
                        false,
                        fileNameType,
                        DATA_TYPE_MISMATCH,
                        addInfo)) {
                    /* Do Not validate Further */
                    return;
                }
            }

            FindByQueryOperation findByQueryOperation =
                    caseDocFindOperations.getFindByQueryOperation();

            if (findByQueryOperation != null) {
                /* There should be atleast one Expression */
                if (findByQueryOperation.getCaseDocumentQueryExpression() == null
                        || findByQueryOperation
                                .getCaseDocumentQueryExpression().isEmpty()) {

                    addIssue("bpmn.dev.caseDocumentTask.missingExpression", //$NON-NLS-1$
                            activity);
                    /* Do Not Validate Further */
                    return;
                }

                /* Validate Query Field */
                int i = 0;
                for (CaseDocumentQueryExpression queryExpression : findByQueryOperation
                        .getCaseDocumentQueryExpression()) {

                    /* Validate Each Query Expression */
                    if (!isValidCMISQueryExpression(queryExpression,
                            (i == 0),
                            activity,
                            activityInterfaceDataMap)) {
                        /* Do Not validate Further */
                        return;
                    }

                    i++;

                }

                validateBracketGroupings(findByQueryOperation.getCaseDocumentQueryExpression(),
                        activity);

            }
        }
    }

    /**
     * Validates bracket groupings to check all the Brackets have correct
     * opening and closing.
     * 
     * @param caseDocumentQueryExpression
     *            List of {@link CaseDocumentQueryExpression} to check the
     *            brackets.
     * @param activity
     *            owner Case Document Task Activity.
     */
    private void validateBracketGroupings(
            EList<CaseDocumentQueryExpression> caseDocumentQueryExpression,
            Activity activity) {

        String openingBracket = "("; //$NON-NLS-1$
        String closingBracket = ")"; //$NON-NLS-1$
        Stack<String> brackets = new Stack<String>();

        for (CaseDocumentQueryExpression expression : caseDocumentQueryExpression) {

            if (expression.getOpenBracketCount() > 0) {

                /* push opening Bracket */
                for (int i = 0; i < expression.getOpenBracketCount(); i++) {

                    brackets.push(openingBracket);
                }
            }
            if (expression.getCloseBracketCount() > 0) {

                /* pop opening brackets for each paired closing bracket if found */
                for (int i = 0; i < expression.getCloseBracketCount(); i++) {

                    if (!brackets.isEmpty()
                            && openingBracket.equals(brackets.peek())) {
                        /* POP paired opening bracket */
                        brackets.pop();
                    } else {
                        /* push the closing bracket */
                        brackets.push(closingBracket);
                    }

                }

            }
        }
        /* Some brackets remaining in stack says , the nesting is not correct */
        if (!brackets.isEmpty()) {
            addIssue(EXPRESSIONS_NESTING_INCORRECT, activity);
        }
    }

    /**
     * Validates CMIS Query Expression for following.
     * 
     * <p>
     * <ol>
     * <li>First Condition Expression Should not contain a Logical Grouping.</li>
     * <li>CONTAINS expression should not use a CMIS Property.</li>
     * <li>'NULL' or '! Null' expressions should not contain a value.</li>
     * </ol>
     * <p>
     * 
     * @param queryExpression
     *            Query Expression to validate.
     * @param firstExpression
     *            true if this is the first Expression or the Query, false
     *            otherwise.
     * @param activity
     *            owner Case Document Task Activity.
     * @param activityInterfaceDataMap
     *            Activity interface data Map<Data Field Name,
     *            {@link ActivityInterfaceData}>.
     * @return true if this is a valid CMIS query expression, false if it fails
     *         any of the validation criteria
     */
    private boolean isValidCMISQueryExpression(
            CaseDocumentQueryExpression queryExpression,
            boolean firstExpression, Activity activity,
            Map<String, ActivityInterfaceData> activityInterfaceDataMap) {

        /*
         * Operator is Mandatory (if the whole thing is missing then we'll treat
         * that the same as missing operator.
         */
        if (queryExpression == null || queryExpression.getOperator() == null) {
            addIssue(OPERATION_MANDATORY_FOR_EXPRESSION, activity);

            /*
             * No point checking everything else is haven't even got an
             * operator.
             */
            return false;
        }

        CMISQueryOperator operator = queryExpression.getOperator();

        /*
         * CMIS Property is mandatory on everything except "[NOT] CONTAINS"
         */
        if (!CMISQueryOperator.CONTAINS.equals(operator)
                && !CMISQueryOperator.NOT_CONTAINS.equals(operator)) {

            /* XPD-6848:cmis:document can only be used with contains Operator */
            if (CMISQL_PROPERTY.CMISQL_DOCUMENT.getPropertyName()
                    .equals(queryExpression.getCmisPropertyForDisplay())) {

                addIssue(CMIS_DOC_ONLY_WITH_CONTAINS,
                        activity,
                        Collections
                                .singletonList(CaseDocumentOperationsHelpUtiliy
                                        .getDisplayOperatorString(operator)));
                return false;
            }

            if (isEmpty(queryExpression.getCmisPropertyForDisplay())) {

                addIssue(MISSING_CMIS_PROPERTY,
                        activity,
                        Collections
                                .singletonList(CaseDocumentOperationsHelpUtiliy
                                        .getDisplayOperatorString(operator)));
                return false;
            }
        }

        /*
         * First Condition Expression Should not specify a Logical Grouping.
         */
        if (firstExpression) {
            if (queryExpression.getQueryExpressionJoinType() != null) {
                // Raise error that first expression should not specify
                // logical grouping.
                addIssue(FIRST_EXP_LOGICAL_JOIN_NOT_ALLOWED, activity);
                return false;
            }
        } else {
            /*
             * Incomplete if this is not the first Expression and Logical
             * Grouping is missing
             */
            if (queryExpression.getQueryExpressionJoinType() == null) {
                addIssue(MISSING_LOGICAL_GROUP, activity);
                return false;
            }
        }

        /*
         * Now check the operation specific stuff.
         */
        String processDataField = queryExpression.getProcessDataField();

        switch (operator.getValue()) {

        /*
         * 'NULL' or '! Null' expressions should not contain a Process Data
         * Value.
         */
        case CMISQueryOperator.NULL_VALUE:
        case CMISQueryOperator.NOT_NULL_VALUE:

            if (!isEmpty(processDataField)) {
                addIssue(NULL_OPERATOR_VALUE_NOT_ALLOWED, activity);
                return false;
            }
            /*
             * For [NOT] NULL Type Compatibility check is not required as [NOT]
             * NULL does not take a Process Data Field
             */
            break;

        case CMISQueryOperator.EQUAL_VALUE:
        case CMISQueryOperator.NOT_EQUAL_VALUE:
            /*
             * TODO MULTIVALUED CHECK, In later release when 'ANY' is supported
             * Mulivalued Process Data Field will be allowed, not allowed for
             * not
             */

        case CMISQueryOperator.GREATER_THAN_VALUE:
        case CMISQueryOperator.GREATER_THAN_EQUAL_VALUE:
        case CMISQueryOperator.LESS_THAN_VALUE:
        case CMISQueryOperator.LESS_THAN_EQUAL_VALUE:
        case CMISQueryOperator.LIKE_VALUE:
        case CMISQueryOperator.NOT_LIKE_VALUE:
            if (!validBasicExpressionFieldValue(queryExpression,
                    activity,
                    activityInterfaceDataMap,
                    false)) {
                return false;
            }

            break;

        /*
         * CONTAINS and NOT CONTAINS expressions should not specify CMIS
         * Property
         */
        case CMISQueryOperator.CONTAINS_VALUE:
        case CMISQueryOperator.NOT_CONTAINS_VALUE:
            if (!validContainsOperator(queryExpression,
                    activity,
                    activityInterfaceDataMap)) {
                return false;
            }

            break;
        case CMISQueryOperator.IN_VALUE:
        case CMISQueryOperator.NOT_IN_VALUE:
            if (!validBasicExpressionFieldValue(queryExpression,
                    activity,
                    activityInterfaceDataMap,
                    true)) {
                return false;
            }
            break;

        default:
            /* Raise error Operator Not Supported. */
            addIssue(OPERATOR_NOT_SUPPORTED,
                    activity,
                    Arrays.asList(operator.getName()));
            return false;
        }

        return true;
    }

    /**
     * Performs Basic validations for a Query Expression like Operator-DataType
     * Compatibility check, CMIS Property-DataType Compatibility check. Operator
     * and DataType multiplicity check.
     * 
     * @param queryExpression
     *            {@link CaseDocumentQueryExpression} to validate.
     * @param activity
     *            owner Case Document Task Activity.
     * @param activityInterfaceDataMap
     *            Data Map of Case Document Activity Interface.
     * @return true if this is a valid Basic Expressions field value, false if
     *         it fails any of the checks.
     */
    private boolean validBasicExpressionFieldValue(
            CaseDocumentQueryExpression queryExpression, Activity activity,
            Map<String, ActivityInterfaceData> activityInterfaceDataMap,
            boolean multivalued) {

        String processDataField = queryExpression.getProcessDataField();

        if (processDataField == null || processDataField.length() == 0) {
            CMISQueryOperator operator = queryExpression.getOperator();

            addIssue(MISSING_FIELD_VALUE,
                    activity,
                    Collections
                            .singletonList((operator != null ? CaseDocumentOperationsHelpUtiliy
                                    .getDisplayOperatorString(operator) : "??"))); //$NON-NLS-1$

            return false;
        }

        ActivityInterfaceData aIData =
                activityInterfaceDataMap.get(processDataField);

        if (aIData == null || aIData.getData() == null) {
            addIssue(FIELD_NOT_FOUND,
                    activity,
                    Collections.singletonList(processDataField));
            return false;
        }

        if (!validDataTypeCompatibility(queryExpression.getCmisPropertyForDisplay(),
                queryExpression.getOperator(),
                aIData,
                activityInterfaceDataMap,
                activity)) {
            return false;
        }

        if (!validProcessDataMultiplicity(aIData,
                multivalued,
                activity,
                queryExpression.getOperator())) {
            return false;
        }
        // Validate CMIS Property

        if (!isSupportedCMISProperty(queryExpression, activity)) {
            return false;
        }
        return true;
    }

    /**
     * Validates the CONTAINS operator usage in the Expression. The CONTAINS
     * Operator cannot be used with a CMIS Property.
     * 
     * @param queryExpression
     *            {@link CaseDocumentQueryExpression} to validate.
     * @param activity
     *            owner Case Document Task Activity.
     * @param activityInterfaceDataMap
     *            Data Map of Case Document Activity Interface.
     * @return if this is a valid CONTAINS expression, false if this CONTAINS
     *         expression uses a CMIS Property.
     */
    private boolean validContainsOperator(
            CaseDocumentQueryExpression queryExpression, Activity activity,
            Map<String, ActivityInterfaceData> activityInterfaceDataMap) {

        if (queryExpression.getCmisPropertyForDisplay() != null) {
            if (!isEmpty(queryExpression.getCmisPropertyForDisplay())) {

                /*
                 * Raise Error
                 * "[NOT] CONTAINS can only use cmis:document Property"
                 */
                if (!CMISQL_PROPERTY.CMISQL_DOCUMENT.getPropertyName()
                        .equals(queryExpression.getCmisPropertyForDisplay())) {

                    addIssue(CONTAINS_SHOULD_ONLY_USE_CMIS_DOCUMENT, activity);
                    return false;
                }
            }
        }

        return validBasicExpressionFieldValue(queryExpression,
                activity,
                activityInterfaceDataMap,
                false);

    }

    /**
     * Validates Process Data Field and CMIS Property for Multi/Single value
     * specified by the flag multiValuedAllowed.Raises Error when Process Data
     * field/CMISProperty's multiplicity does not satisfy the flag
     * multiValuedAllowed.
     * 
     * 
     * @param activityInterfaceData
     *            Activity Interface Data for the Process Data Field used in the
     *            Expression.
     * @param multiValuedAllowed
     *            true if multivalued is allowed for this Expression, fale
     *            otherwise.
     * @param activity
     *            Case Document Service Task.
     * @return true if process data multiplicity is correct as per the
     *         expression usage, false otherwise.
     */
    private boolean validProcessDataMultiplicity(
            ActivityInterfaceData activityInterfaceData,
            boolean multiValuedAllowed, Activity activity,
            CMISQueryOperator cmisQueryOperator) {

        if (cmisQueryOperator != null && activityInterfaceData != null
                && activityInterfaceData.getData() != null) {

            if (multiValuedAllowed
                    && !activityInterfaceData.getData().isIsArray()) {

                addIssue(SINGLE_VALUED_NOT_ALLOWED_FOR_OPERATOR,
                        activity,
                        Arrays.asList(activityInterfaceData.getName(),
                                CaseDocumentOperationsHelpUtiliy
                                        .getDisplayOperatorString(cmisQueryOperator)));

                return false;
            }

            if (!multiValuedAllowed
                    && activityInterfaceData.getData().isIsArray()) {

                addIssue(MULTIVALUED_NOT_ALLOWED_FOR_OPERATOR,
                        activity,
                        Arrays.asList(activityInterfaceData.getName(),
                                CaseDocumentOperationsHelpUtiliy
                                        .getDisplayOperatorString(cmisQueryOperator)));
                return false;
            }

        }
        return true;
    }

    /**
     * This method checks the validity of usage of the Process Data Field with
     * the given Operator.This uses the following Compatibility Chart as per
     * CMIS Specification.
     * <p>
     * <table style="width:100%">
     * 
     * <tr>
     * <td>=</td>
     * <td>DateTime, Decimal, Integer,ID, String, URI, Boolean</td>
     * </tr>
     * 
     * <tr>
     * <td><></td>
     * <td>DateTime, Decimal, Integer,ID, String, URI</td>
     * </tr>
     * 
     * <tr>
     * <td><, <=, >, >=</td>
     * <td>DateTime, Decimal, Integer</td>
     * </tr>
     * 
     * <tr>
     * <td>[NOT] IN</td>
     * <td>DateTime, Decimal, ID, Integer, String, URI</td>
     * </tr>
     * 
     * <tr>
     * <td>[NOT] LIKE</td>
     * <td>String, URI</td>
     * </tr>
     * 
     * <tr>
     * <td>IS [NOT] NULL</td>
     * <td>All Data Types, Returns true always as Process Data Field is not
     * applicable for NULL and NOT NULL Operators</td>
     * </tr>
     * 
     * </table>
     * 
     * <table>
     * <tr>
     * <td>Supported CMS Property</td>
     * <td>Compatible Types</td>
     * </tr>
     * 
     * <tr>
     * <td>cmis:name</td>
     * <td>String</td>
     * </tr>
     * 
     * <tr>
     * <td>cmis:creationDate</td>
     * <td>DateTime</td>
     * </tr>
     * 
     * <tr>
     * <td>cmis:createdBy</td>
     * <td>String</td>
     * </tr>
     * 
     * <tr>
     * <td>cmis:lastModifiedBy</td>
     * <td>String</td>
     * </tr>
     * 
     * <tr>
     * <td>cmis:lastModificationDate</td>
     * <td>DateTime</td>
     * </tr>
     * 
     * 
     * </table>
     * </p>
     * 
     * @param cmisProperty
     *            CMIS Property used in the Expression.
     * 
     * @param operator
     *            CMIS Operator used in the Query Condition Expression.
     * @param processDataFieldName
     *            Name of the Process Data Field used in the Query Condition
     *            Expression.
     * @param activityInterfaceDataMap
     *            Activity interface data Map<Data Field Name,
     *            {@link ActivityInterfaceData}>.
     * 
     * @param activity
     *            Case Document Task.
     * @return true if the DataType compatibility is valid as per the rules
     *         above,false otherwise.
     */
    private boolean validDataTypeCompatibility(String cmisProperty,
            CMISQueryOperator operator, ActivityInterfaceData aIData,
            Map<String, ActivityInterfaceData> activityInterfaceDataMap,
            Activity activity) {

        ProcessRelevantData processRelevantData = aIData.getData();

        if (processRelevantData != null) {

            boolean dataTypeAndOperatorCompatible = true;
            boolean dataTypeAndCmisPropertyCompatible = true;

            Object baseType =
                    BasicTypeConverterFactory.INSTANCE
                            .getBaseType(processRelevantData, true);

            /* Only Basic Types are allowed */
            if (baseType == null || !(baseType instanceof BasicType)) {
                dataTypeAndOperatorCompatible = false;
                dataTypeAndCmisPropertyCompatible = false;

            } else {
                /* Validate Type Compatibility for Operator */
                List<BasicTypeType> compatibleDataTypes =
                        CaseDocumentOperationsHelpUtiliy.CMIS_OPERATOR_TYPE_COMPATIBILITY_MAP
                                .get(operator);

                if (compatibleDataTypes == null
                        || !compatibleDataTypes.contains(((BasicType) baseType)
                                .getType())) {

                    dataTypeAndOperatorCompatible = false;
                }

                /*
                 * Validate CMIS Property and Process Data Field Data Type
                 * Compatibility
                 */
                if (cmisProperty != null) {
                    CMISQL_PROPERTY cmisPropertyType =
                            CaseDocumentOperationsHelpUtiliy.CMISQL_PROPERTY
                                    .getCMISPropertyByName(cmisProperty);

                    if (cmisPropertyType != null) {

                        List<BasicTypeType> supportedDataTypesForCMISProp =
                                cmisPropertyType.getSupportedDataTypes();

                        if (!supportedDataTypesForCMISProp
                                .contains(((BasicType) baseType).getType())) {

                            dataTypeAndCmisPropertyCompatible = false;
                        }
                    }
                }
            }

            if (!dataTypeAndOperatorCompatible) {
                addIssue(OPERATION_PROCESS_DATA_MISMATCH,
                        activity,
                        Arrays.asList(CaseDocumentOperationsHelpUtiliy
                                .getDisplayOperatorString(operator),
                                processRelevantData.getName()));
                return false;
            }

            /*
             * Correct existence of cmis property are checked elsewhere so don't
             * check type compat if no cmis prop.
             */
            if (!isEmpty(cmisProperty)) {
                if (!dataTypeAndCmisPropertyCompatible) {
                    addIssue(CMISPROPERTY_TYPE_MISMATCH,
                            activity,
                            Arrays.asList(cmisProperty,
                                    processRelevantData.getName()));
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Validates CMIS Property value
     * 
     * @param queryExpression
     *            {@link CaseDocumentQueryExpression} to validate.
     * 
     * @param activity
     *            owner Case Document Task Activity.
     * @return true if the used CMIS property is supported, false otherwise.
     */
    private boolean isSupportedCMISProperty(
            CaseDocumentQueryExpression queryExpression, Activity activity) {

        /* Validate supported CMIS Property */
        String cmisProperty = queryExpression.getCmisPropertyForDisplay();

        if (!isEmpty(cmisProperty)) {

            if (CaseDocumentOperationsHelpUtiliy.CMISQL_PROPERTY
                    .getCMISPropertyByName(cmisProperty) == null) {

                addIssue(CMISPROPERTY_NOT_SUPPORTED,
                        activity,
                        Arrays.asList(cmisProperty));
                return false;
            }
        }
        return true;
    }

    /**
     * Validates the Document Reference Field Inputs for Correct type valid
     * DataField and completeness.
     * 
     * @param activity
     *            Document Operations Activity.
     * @param caseDocOperation
     *            Case Document Operation name.
     * @param docRefField
     *            Document reference Field.
     * @param activityInterfaceDataMap
     *            Activity interface data Map<Data Field Name,
     *            {@link ActivityInterfaceData}>.
     * @param valiateMultiplicity
     *            checks for multiplicity if this flag is set.
     * @param isMultiple
     *            flag specifying the field should be multiple or not. * @return
     *            true if the data is valid, false if any of the validation
     *            checks fail.
     * @return true if used DocumentRef field is valid, Process Data in scope of
     *         the Case Document Task Activity.
     */
    private boolean isValidDocumentReferenceField(Activity activity,
            String caseDocOperation, String docRefField,
            Map<String, ActivityInterfaceData> activityInterfaceDataMap,
            boolean valiateMultiplicity, boolean isMultiple,
            String docRefFieldLabel) {

        if (isMissingEntry(docRefField,
                activity,
                caseDocOperation,
                docRefFieldLabel)) {
            /* Do not validate further */
            return false;
        }

        BasicType docRefType = Xpdl2Factory.eINSTANCE.createBasicType();
        docRefType.setType(BasicTypeType.STRING_LITERAL);

        List<String> addInfo = new LinkedList<String>();
        addInfo.add(docRefFieldLabel);
        addInfo.add(BasicTypeType.STRING_LITERAL.getLiteral());

        return isValidActivityRelevantData(activity,
                docRefField,
                activityInterfaceDataMap,
                valiateMultiplicity,
                isMultiple,
                docRefType,
                DATA_TYPE_MISMATCH,
                addInfo);

    }

    /**
     * Validates the Case Reference Field Inputs for Correct type valid
     * DataField and completeness.
     * 
     * @param activity
     *            Document Operations Activity.
     * @param caseDocOperationName
     *            name of the case Document Operation.
     * @param caseRefField
     *            case reference Field.
     * @param activityInterfaceDataMap
     *            Activity interface data Map<Data Field Name,
     *            {@link ActivityInterfaceData}>.
     * @param valiateMultiplicity
     *            checks for multiplicity if this flag is set.
     * @param isMultiple
     *            flag specifying the field should be multiple or not.
     * @return true if this is a valid Case Reference Field in scope of the Case
     *         Document Service Task Activity, false otherwise.
     */
    private boolean isValidCaseReferenceField(Activity activity,
            String caseDocOperationName, String caseRefField,
            Map<String, ActivityInterfaceData> activityInterfaceDataMap,
            boolean validateMultiplicity, boolean isMultiple,
            String caseRefFieldLabel) {

        if (isMissingEntry(caseRefField,
                activity,
                caseDocOperationName,
                caseRefFieldLabel)) {
            /* Do not validate further */
            return false;
        }

        RecordType caseRefType = Xpdl2Factory.eINSTANCE.createRecordType();

        List<String> addInfo = new LinkedList<String>();
        addInfo.add(caseRefFieldLabel);
        addInfo.add("Case Reference"); //$NON-NLS-1$

        return isValidActivityRelevantData(activity,
                caseRefField,
                activityInterfaceDataMap,
                validateMultiplicity,
                isMultiple,
                caseRefType,
                DATA_TYPE_MISMATCH,
                addInfo);

    }

    /**
     * Checks the given String is null or Empty.
     * 
     * @param string
     *            String value to check
     */
    private boolean isEmpty(String string) {

        if (string == null || string.isEmpty()) {
            return true;
        }

        return false;
    }

    /**
     * Returns true if any of the Operation Type is set, returns false when none
     * of the Operation is Set.
     * 
     * @param documentOperation
     *            {@link DocumentOperation} to validate.
     * @return true or false depending on the existence of the Document
     *         Operation.
     */
    private boolean isDocumentOperationSet(DocumentOperation documentOperation) {

        boolean operationSet = false;

        /*
         * No if the root element itself does not exist or neither of Find /
         * Document reference Operation is set
         */
        if (documentOperation != null) {

            CaseDocFindOperations caseDocFindOperations =
                    documentOperation.getCaseDocFindOperations();

            CaseDocRefOperations caseDocRefOperation =
                    documentOperation.getCaseDocRefOperation();

            LinkSystemDocumentOperation linkSysDocOperation =
                    documentOperation.getLinkSystemDocumentOperation();

            if (caseDocFindOperations != null) {
                /* Either FindByName or FindByQuery is set */
                if ((caseDocFindOperations.getFindByFileNameOperation() != null)
                        || (caseDocFindOperations.getFindByQueryOperation() != null)) {
                    operationSet = true;
                }

            } else if (caseDocRefOperation != null) {

                if ((caseDocRefOperation.getDeleteCaseDocOperation() != null)
                        || (caseDocRefOperation.getMoveCaseDocOperation() != null)
                        || (caseDocRefOperation.getLinkCaseDocOperation() != null)
                        || (caseDocRefOperation.getUnlinkCaseDocOperation() != null)) {

                    operationSet = true;
                }
            } else if (linkSysDocOperation != null) {
                operationSet = true;
            }
        }

        return operationSet;
    }

    /**
     * Find the data with the given name in scope of the activity (takes into
     * account the activity interface). If the field is defined in the process
     * but is not in scope of this activity then an issue will be created.
     * 
     * @param activity
     *            to validate data for.
     * @param fieldName
     *            field to validate.
     * @param activityInterfaceDataMap
     *            Activity interface data Map<Data Field Name,
     *            {@link ActivityInterfaceData}>.
     * @param checkMultiInstance
     *            validates multiInstance only if this flag is set, else
     *            ignores.
     * @param isMultiInstance
     *            flag to specify if this field allows multi instance.
     * @param expectedDataType
     *            expected type of the field to validate against.
     * @param dataTypeMismatchIssue
     *            Issue id for the Data Type mismatch error.
     * @param additionalInfoDataMismatch
     *            additional info for Data Type Mismatch info message, the
     *            additional info for generic error message DATA_TYPE_MISMATCH,
     *            should pass list containing field name and data type name.
     *            Passing null will use a Singleton list of fieldName, which is
     *            useful in cases like DocRef and Case Reference validation
     *            which has its own error message and does not use the generic
     *            one.
     * @return true if the data is valid, false if any of the validation checks
     *         fail.
     */
    private boolean isValidActivityRelevantData(Activity activity,
            String fieldName,
            Map<String, ActivityInterfaceData> activityInterfaceDataMap,
            boolean validateMultiplicity, boolean isMultiple,
            DataType expectedDataType, String dataTypeMismatchIssue,
            List<String> additionalInfoDataMismatch) {

        ProcessRelevantData data = null;

        ActivityInterfaceData iData = activityInterfaceDataMap.get(fieldName);
        if (iData != null) {
            data = iData.getData();
        }

        if (data == null) {

            addIssue(FIELD_PARAM_DOES_NOT_EXIST,
                    activity,
                    Collections.singletonList(fieldName));
            return false;

        } else {

            DataType actualDataType = data.getDataType();

            if (!isValidDataType(expectedDataType, actualDataType)) {

                addIssue(dataTypeMismatchIssue,
                        activity,
                        (additionalInfoDataMismatch != null) ? additionalInfoDataMismatch
                                : Collections.singletonList(fieldName));
                return false;
            }

            if (validateMultiplicity) {

                if (data.isIsArray() != isMultiple) {

                    addIssue(ARRAY_FIELDS_NOT_ALLOWED,
                            activity,
                            Collections.singletonList(fieldName));
                    return false;

                }
            }
        }
        return true;

    }

    /**
     * Validates the Actual Data Type against the Expected Data Type. If the
     * Expected is instance of {@link RecordType} the actual should also be
     * {@link RecordType}.Returns false if this does not satisfy.Here the check
     * is only on the instance of Record Type, as we only intend to check that
     * it should be a Case Reference Type, the actual Type does not matter.
     * Secondly if the Expected is a {@link BasicType}, the actual should also
     * be {@link BasicType} with their {@link BasicTypeType} also
     * matching.Returns false if this is not the case.Here we are trying to
     * match the exact {@link BasicType} strictly, so STRING can match to only
     * to STRING.
     * 
     * @param expectedDataType
     *            Type of Data Expected.
     * @param actualDataType
     *            Type of Data Passed.
     * @return true if any of the above matching criteria satisfies, false if it
     *         doesn't.
     */
    private boolean isValidDataType(DataType expectedDataType,
            DataType actualDataType) {

        /* Invalid when expected is RecordType and actual is NOT RecordType */
        if (expectedDataType instanceof RecordType) {

            if (actualDataType instanceof RecordType) {
                return true;
            }
            return false;

        }

        Object expectedBaseType =
                BasicTypeConverterFactory.INSTANCE
                        .getBaseType(expectedDataType, true);
        Object actualBaseType =
                BasicTypeConverterFactory.INSTANCE.getBaseType(actualDataType,
                        true);

        /* Invalid when expected is BasicType and actual is NOT BasicType */
        if (expectedBaseType instanceof BasicType) {

            if (actualBaseType instanceof BasicType) {

                /* Invalid when expected and actual BasicType are not same */
                if (!((BasicType) expectedBaseType).getType()
                        .equals(((BasicType) actualBaseType).getType())) {
                    return false;
                }

            } else {
                return false;
            }
        }

        return true;
    }

    /**
     * Validates the field value for missing entry, raises an error if the
     * fieldValue is null/empty.
     * 
     * @param fieldValue
     *            Field value to be checked.
     * @param caseDocTask
     *            Owner Case Document Service Task Activity.
     * 
     * @param operationName
     *            Operation name to which the input fieldValue belongs.
     * @param fieldName
     *            name of the Field for which this value is provided.
     * @return true if the entry is missing.
     */
    private boolean isMissingEntry(String fieldValue, Activity caseDocTask,
            String operationName, String fieldName) {

        List<String> addInfo = Arrays.asList(operationName, fieldName);
        if (fieldValue == null || isEmpty(fieldValue)) {

            /* INPUT is MANDATORY */

            addIssue("bpmn.dev.caseDocumentTask.missingFieldInput", //$NON-NLS-1$
                    caseDocTask,
                    addInfo);

            return true;

        }
        return false;
    }

    /**
     * Returns Operation name to be used in the Error Message String.
     * 
     * @param docOpr
     *            {@link DocumentOperation}
     * @return Operation name to be used in the Error Message String.
     */
    private String getOperationNameForErrorMsgString(DocumentOperation docOpr) {

        String docOperationName = ""; //$NON-NLS-1$

        if (docOpr != null) {

            CaseDocFindOperations caseDocFindOperations =
                    docOpr.getCaseDocFindOperations();

            if (caseDocFindOperations != null) {

                docOperationName =
                        Messages.CaseDocumentServiceTaskRule_FindDocOprStringForErrorMsg;
            }

            CaseDocRefOperations docRefOpr = docOpr.getCaseDocRefOperation();
            if (docRefOpr != null) {

                if (docRefOpr.getDeleteCaseDocOperation() != null) {
                    docOperationName =
                            Messages.CaseDocumentServiceTaskRule_DelDocOprStringForErrorMsg;

                } else if (docRefOpr.getLinkCaseDocOperation() != null) {

                    docOperationName =
                            Messages.CaseDocumentServiceTaskRule_LinkDocOprStringForErrorMsg;

                } else if (docRefOpr.getMoveCaseDocOperation() != null) {

                    docOperationName =
                            Messages.CaseDocumentServiceTaskRule_MoveDocOprStringForErrorMsg;

                } else if (docRefOpr.getUnlinkCaseDocOperation() != null) {
                    docOperationName =
                            Messages.CaseDocumentServiceTaskRule_UnlinkDocOprStringForErrorMsg;
                }

            }

            if (docOpr.getLinkSystemDocumentOperation() != null) {
                docOperationName =
                        Messages.CaseDocumentServiceTaskRule_LinkSysDocOprStringForErrorMsg;
            }
        }

        return docOperationName;
    }
}
