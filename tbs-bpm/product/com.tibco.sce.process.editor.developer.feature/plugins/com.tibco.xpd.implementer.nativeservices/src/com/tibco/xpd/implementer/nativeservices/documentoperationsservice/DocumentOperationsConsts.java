/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.implementer.nativeservices.documentoperationsservice;

import com.tibco.xpd.implementer.nativeservices.internal.Messages;

/**
 * Class holds necessary Constants to be used in validation rules for errors
 * messages. using these as constants, such that any change in UI labels is
 * reflected into the error messages automatically.
 * 
 * @author aprasad
 * @since 05-Sep-2014
 */
public class DocumentOperationsConsts {

    public static final String DOCUMENT_REF_FIELD_LABEL =
            Messages.CaseDocumentOperationPage_DocumentReferenceFieldLabel;

    public static final String CASE_REF_FIELD_LABEL =
            Messages.CaseDocFindOperationPage_CaseReferenceFieldLabel;

    public static final String FIND_RET_DOC_REF_FIELD_LABEL =
            Messages.CaseDocFindOperationPage_ReturnCaseDocumentRefFieldLabel;

    public static final String FIND_FILENAME_FIELD_LABEL =
            Messages.CaseDocFindOperationPage_FileNameFieldLabel;

    public static final String FIND_CMIS_QUERY_BALE =
            Messages.CaseDocFindOperationPage_FindByQueryLabel;

    public static final String CASE_DOC_REF_FIELD_LABEL =
            Messages.CaseDocumentOperationPage_DocumentReferenceFieldLabel;

    public static final String SOURCE_CASE_FIELD_LABEL =
            Messages.CaseDocMoveOperationPage_SourceCaseReferenceFieldLabel;

    public static final String TARGET_CASE_FIELD_LABEL =
            Messages.CaseDocMoveOperationPage_TargetCaseReferenceFieldLabel;

    public static final String LINK_SYS_DOC_ID_FIELD_LABEL =
            Messages.LinkSystemDocumentOperationPage_DocumentIdFieldLabel;

    public static final String LINK_SYS_RET_DOC_REF_FIELD_LABEL =
            Messages.LinkSystemDocumentOperationPage_ReturnDocRefFieldLabel;

}
