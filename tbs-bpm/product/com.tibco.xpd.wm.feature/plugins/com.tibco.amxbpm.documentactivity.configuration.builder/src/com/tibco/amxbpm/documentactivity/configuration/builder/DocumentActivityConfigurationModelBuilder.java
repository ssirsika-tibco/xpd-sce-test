/*
 * Copyright (c) TIBCO Software Inc 2014. All rights reserved.
 */
package com.tibco.amxbpm.documentactivity.configuration.builder;

import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.amxbpm.documentactivity.model.DeleteDocumentType;
import com.tibco.amxbpm.documentactivity.model.DocumentActivityDataModelType;
import com.tibco.amxbpm.documentactivity.model.DocumentActivityFactory;
import com.tibco.amxbpm.documentactivity.model.DocumentSpecifierType;
import com.tibco.amxbpm.documentactivity.model.FindDocumentsType;
import com.tibco.amxbpm.documentactivity.model.FindResultsType;
import com.tibco.amxbpm.documentactivity.model.JoinTypeType;
import com.tibco.amxbpm.documentactivity.model.LinkDocumentType;
import com.tibco.amxbpm.documentactivity.model.MoveDocumentType;
import com.tibco.amxbpm.documentactivity.model.OperatorType;
import com.tibco.amxbpm.documentactivity.model.QueryDetailsType;
import com.tibco.amxbpm.documentactivity.model.UnlinkDocumentType;
import com.tibco.bx.xpdl2bpel.converter.ConversionException;
import com.tibco.bx.xpdl2bpel.extensions.IActivityConfigurationModelBuilder;
import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.xpdExtension.CMISQueryOperator;
import com.tibco.xpd.xpdExtension.CaseDocFindOperations;
import com.tibco.xpd.xpdExtension.CaseDocRefOperations;
import com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression;
import com.tibco.xpd.xpdExtension.DeleteCaseDocOperation;
import com.tibco.xpd.xpdExtension.DocumentOperation;
import com.tibco.xpd.xpdExtension.FindByFileNameOperation;
import com.tibco.xpd.xpdExtension.FindByQueryOperation;
import com.tibco.xpd.xpdExtension.LinkCaseDocOperation;
import com.tibco.xpd.xpdExtension.LinkSystemDocumentOperation;
import com.tibco.xpd.xpdExtension.MoveCaseDocOperation;
import com.tibco.xpd.xpdExtension.QueryExpressionJoinType;
import com.tibco.xpd.xpdExtension.UnlinkCaseDocOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

public class DocumentActivityConfigurationModelBuilder implements
        IActivityConfigurationModelBuilder {

    public DocumentActivityConfigurationModelBuilder() {

    }

    public EObject transformConfigModel(Activity activity,
            Map<String, Participant> participantMap) throws ConversionException {
        EObject result = null;
        try {
            result = getDocumentActivityData(activity);
        } catch (RuntimeException e) {
            // Catch any Runtime exceptions. The following logger command
            // will print an error in the eclipse log with the one line
            // message displayed and then the full dialog allowing the
            // user to see the full exception
            Activator.getDefault().getLogger().error(e, e.getMessage());
            throw e;
        }

        // Enable the following in order to Dump the model to console
        // Resource resource = new
        // DocumentactivityResourceFactoryImpl().createResource(URI
        // .createFileURI("dummy.xml"));
        // resource.getContents().add(result);
        // try {
        // resource.save(System.out, Collections.EMPTY_MAP);
        // } catch (IOException e) {
        // e.printStackTrace();
        // }

        return result;
    }

    /**
     * Creates the Model that will be used to represent the Document Operation
     * 
     * @param activity
     *            Activity that represents the process activity
     * @return The EMF model
     */
    private DocumentActivityDataModelType getDocumentActivityData(
            Activity activity) {
        // Create the Activity data that will be returned
        DocumentActivityDataModelType model =
                DocumentActivityFactory.eINSTANCE
                        .createDocumentActivityDataModelType();

        // Read the activity to find the service request type
        DocumentOperation docOperation = getDocumentOperation(activity);

        // Get the operation that has been selected
        EObject operationType = getOperationType(docOperation);

        // Check if this is a delete operation
        if (operationType instanceof DeleteCaseDocOperation) {
            // Create the delete document and set the values needed
            DeleteDocumentType deleteDocumentType =
                    DocumentActivityFactory.eINSTANCE
                            .createDeleteDocumentType();
            // Only the case reference is required for the delete
            deleteDocumentType.setDocReference(docOperation
                    .getCaseDocRefOperation().getCaseDocumentRefField());
            // Now add the delete to the model
            model.setDeleteDocument(deleteDocumentType);
        }
        // Check if this is a link operation
        else if (operationType instanceof LinkCaseDocOperation) {
            // Create the link document and set the values needed
            LinkDocumentType linkDocumentType =
                    DocumentActivityFactory.eINSTANCE.createLinkDocumentType();
            linkDocumentType.setDocReference(docOperation
                    .getCaseDocRefOperation().getCaseDocumentRefField());
            linkDocumentType
                    .setTargetOwnerReference(((LinkCaseDocOperation) operationType)
                            .getTargetCaseRefField());
            // Now add the link to the model
            model.setLinkDocument(linkDocumentType);
        }
        // Check if this is a move operation
        else if (operationType instanceof MoveCaseDocOperation) {
            MoveCaseDocOperation moveOp = (MoveCaseDocOperation) operationType;
            // Create the move document and set the values needed
            MoveDocumentType moveDocumentType =
                    DocumentActivityFactory.eINSTANCE.createMoveDocumentType();
            moveDocumentType.setDocReference(docOperation
                    .getCaseDocRefOperation().getCaseDocumentRefField());
            moveDocumentType.setSourceOwnerReference(moveOp
                    .getSourceCaseRefField());
            moveDocumentType.setTargetOwnerReference(moveOp
                    .getTargetCaseRefField());
            // Now add the move to the model
            model.setMoveDocument(moveDocumentType);
        }
        // Check if this is a unlink operation
        else if (operationType instanceof UnlinkCaseDocOperation) {
            // Create the link document and set the values needed
            UnlinkDocumentType unlinkDocumentType =
                    DocumentActivityFactory.eINSTANCE
                            .createUnlinkDocumentType();
            unlinkDocumentType.setDocReference(docOperation
                    .getCaseDocRefOperation().getCaseDocumentRefField());
            unlinkDocumentType
                    .setSourceOwnerReference(((UnlinkCaseDocOperation) operationType)
                            .getSourceCaseRefField());
            // Now add the unlink to the model
            model.setUnlinkDocument(unlinkDocumentType);
        }
        // Check if this is a link system document operation
        else if (operationType instanceof LinkSystemDocumentOperation) {
            LinkSystemDocumentOperation linkOp =
                    (LinkSystemDocumentOperation) operationType;
            // Create the link document and set the values needed
            LinkDocumentType linkDocumentType =
                    DocumentActivityFactory.eINSTANCE.createLinkDocumentType();
            linkDocumentType.setTargetOwnerReference(linkOp.getCaseRefField());
            // Add the details for the system link operation
            DocumentSpecifierType docSpecifier =
                    DocumentActivityFactory.eINSTANCE
                            .createDocumentSpecifierType();
            docSpecifier.setId(linkOp.getDocumentId());
            docSpecifier.setDocReferenceResult(linkOp
                    .getReturnCaseDocRefField());
            linkDocumentType.setDocumentSpecifier(docSpecifier);
            // Now add the link to the model
            model.setLinkDocument(linkDocumentType);
        }
        // Check if this is a find operation
        else if (operationType instanceof CaseDocFindOperations) {
            CaseDocFindOperations findOp =
                    (CaseDocFindOperations) operationType;
            // Create the find document and set the values needed
            FindDocumentsType findDocumentsType =
                    DocumentActivityFactory.eINSTANCE.createFindDocumentsType();
            findDocumentsType.setOwnerReference(findOp.getCaseRefField());
            // Check if it is a find by name operation
            FindByFileNameOperation findByFileNameOperation =
                    findOp.getFindByFileNameOperation();
            if (findByFileNameOperation != null) {
                findDocumentsType.setName(findByFileNameOperation
                        .getFileNameField());
            }

            FindByQueryOperation findByQueryOperation =
                    findOp.getFindByQueryOperation();
            if (findByQueryOperation != null) {
                EList<CaseDocumentQueryExpression> queryExpressionList =
                        findByQueryOperation.getCaseDocumentQueryExpression();
                if (queryExpressionList != null) {
                    for (CaseDocumentQueryExpression queryExpression : queryExpressionList) {
                        QueryDetailsType queryDetails =
                                DocumentActivityFactory.eINSTANCE
                                        .createQueryDetailsType();
                        // Check to see if the join is set
                        JoinTypeType joinType =
                                convertJoinType(queryExpression
                                        .getQueryExpressionJoinType());
                        if (joinType != null) {
                            queryDetails.setJoinType(joinType);
                        }

                        // Check if there is an opening bracket
                        if (queryExpression.isSetOpenBracketCount()) {
                            queryDetails.setOpenBracketCount(queryExpression
                                    .getOpenBracketCount());
                        }

                        // Check if the CMIS Property is set
                        if (queryExpression.isSetCmisProperty()) {
                            String cmisProperty =
                                    queryExpression.getCmisProperty();
                            if ((cmisProperty != null)
                                    && !cmisProperty.isEmpty()) {
                                queryDetails.setPropertyName(cmisProperty);
                            }
                        }

                        // Check to see if there is an operator
                        if (queryExpression.isSetOperator()) {
                            OperatorType operator =
                                    convertOperator(queryExpression
                                            .getOperator());
                            if (operator != null) {
                                queryDetails.setOperator(operator);
                            }
                        }

                        // Check if the argument is set
                        if (queryExpression.isSetProcessDataField()) {
                            String processDataField =
                                    queryExpression.getProcessDataField();
                            if (processDataField != null) {
                                queryDetails.setArgumentField(processDataField);
                            }
                        }

                        // Check if there is a closing bracket
                        if (queryExpression.isSetCloseBracketCount()) {
                            queryDetails.setCloseBracketCount(queryExpression
                                    .getCloseBracketCount());
                        }

                        findDocumentsType.getQuery().add(queryDetails);
                    }
                }
            }

            // Get the data field that will be used for the return data
            FindResultsType findResultsType =
                    DocumentActivityFactory.eINSTANCE.createFindResultsType();
            findResultsType
                    .setDocReferences(findOp.getReturnCaseDocRefsField());
            findDocumentsType.setSearchResults(findResultsType);

            // Now add the find to the model
            model.setFindDocuments(findDocumentsType);
        }

        return model;
    }

    /**
     * Check the activity to see if it is a document operation service task. If
     * it is, then return the Document Operation details, otherwise null
     * 
     * @param activity
     *            Activity to check
     * @return DocumentOperation or null
     */
    private DocumentOperation getDocumentOperation(Activity activity) {
        DocumentOperation docOperation = null;

        Implementation impl = activity.getImplementation();
        if (impl instanceof Task) {
            TaskService taskService = ((Task) impl).getTaskService();
            if (taskService != null) {
                String extensionId =
                        TaskObjectUtil
                                .getTaskImplementationExtensionId(activity);

                // Only interested in Document Operations, so filter on that
                if (TaskImplementationTypeDefinitions.DOCUMENT_OPERATIONS
                        .equals(extensionId)) {
                    Object otherElement =
                            Xpdl2ModelUtil
                                    .getOtherElement(taskService,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_DocumentOperation());
                    if (otherElement instanceof DocumentOperation) {
                        docOperation = (DocumentOperation) otherElement;
                    }
                }
            }
        }

        return docOperation;
    }

    /**
     * Calculate which operation is being performed in the Document Operation
     * and return it
     * 
     * @param docOperation
     *            Document Operation to check
     * @return Operation, of null if not supported
     */
    private EObject getOperationType(DocumentOperation docOperation) {
        // Support passing null in, just return null as there is no operation
        // defined for this activity that is for us
        if (docOperation == null) {
            return null;
        }

        // Check what type of request is being made
        CaseDocRefOperations caseDocRefOp =
                docOperation.getCaseDocRefOperation();

        // Is this an operation that need the case document reference
        if (caseDocRefOp != null) {
            // Check if this is a delete operation
            DeleteCaseDocOperation deleteCaseDocOperation =
                    caseDocRefOp.getDeleteCaseDocOperation();
            if (deleteCaseDocOperation != null) {
                return deleteCaseDocOperation;
            }
            // Check if this is a link operation
            LinkCaseDocOperation linkCaseDocOperation =
                    caseDocRefOp.getLinkCaseDocOperation();
            if (linkCaseDocOperation != null) {
                return linkCaseDocOperation;
            }
            // Check if this is a move operation
            MoveCaseDocOperation moveCaseDocOperation =
                    caseDocRefOp.getMoveCaseDocOperation();
            if (moveCaseDocOperation != null) {
                return moveCaseDocOperation;
            }
            // Check if this is a unlink operation
            UnlinkCaseDocOperation unlinkCaseDocOperation =
                    caseDocRefOp.getUnlinkCaseDocOperation();
            if (unlinkCaseDocOperation != null) {
                return unlinkCaseDocOperation;
            }
        }

        // Check if this is a link system document operation
        LinkSystemDocumentOperation linkSysDocOperation =
                docOperation.getLinkSystemDocumentOperation();
        if (linkSysDocOperation != null) {
            return linkSysDocOperation;
        }

        CaseDocFindOperations caseDocFindOperations =
                docOperation.getCaseDocFindOperations();

        // Check if this is a find operation
        if (caseDocFindOperations != null) {
            return caseDocFindOperations;
        }

        return null;
    }

    /**
     * Converts a join type from the XPDL version to the DocumentActivity
     * version in the model
     * 
     * @param xpdlJoinType
     * @return
     */
    private JoinTypeType convertJoinType(QueryExpressionJoinType xpdlJoinType) {
        JoinTypeType joinType = null;

        if (xpdlJoinType != null) {
            switch (xpdlJoinType.getValue()) {
            case QueryExpressionJoinType.AND_VALUE:
                joinType = JoinTypeType.AND;
                break;
            case QueryExpressionJoinType.OR_VALUE:
                joinType = JoinTypeType.OR;
                break;
            default:
                break;
            }
        }
        return joinType;
    }

    /**
     * Converts an operator type from the XPDL version to the DocumentActivity
     * version in the model
     * 
     * @param xpdlOperator
     * @return
     */
    private OperatorType convertOperator(CMISQueryOperator xpdlOperator) {
        OperatorType opType = null;

        if (xpdlOperator != null) {
            switch (xpdlOperator.getValue()) {
            case CMISQueryOperator.CONTAINS_VALUE:
                opType = OperatorType.CONTAINS;
                break;
            case CMISQueryOperator.EQUAL_VALUE:
                opType = OperatorType.EQUAL;
                break;
            case CMISQueryOperator.GREATER_THAN_EQUAL_VALUE:
                opType = OperatorType.GREATERTHANEQUAL;
                break;
            case CMISQueryOperator.GREATER_THAN_VALUE:
                opType = OperatorType.GREATERTHAN;
                break;
            case CMISQueryOperator.IN_VALUE:
                opType = OperatorType.IN;
                break;
            case CMISQueryOperator.LESS_THAN_EQUAL_VALUE:
                opType = OperatorType.LESSTHANEQUAL;
                break;
            case CMISQueryOperator.LESS_THAN_VALUE:
                opType = OperatorType.LESSTHAN;
                break;
            case CMISQueryOperator.LIKE_VALUE:
                opType = OperatorType.LIKE;
                break;
            case CMISQueryOperator.NOT_CONTAINS_VALUE:
                opType = OperatorType.NOTCONTAINS;
                break;
            case CMISQueryOperator.NOT_EQUAL_VALUE:
                opType = OperatorType.NOTEQUAL;
                break;
            case CMISQueryOperator.NOT_IN_VALUE:
                opType = OperatorType.NOTIN;
                break;
            case CMISQueryOperator.NOT_LIKE_VALUE:
                opType = OperatorType.NOTLIKE;
                break;
            case CMISQueryOperator.NOT_NULL_VALUE:
                opType = OperatorType.NOTNULL;
                break;
            case CMISQueryOperator.NULL_VALUE:
                opType = OperatorType.NULL;
                break;
            default:
                break;
            }
        }

        return opType;
    }
}
