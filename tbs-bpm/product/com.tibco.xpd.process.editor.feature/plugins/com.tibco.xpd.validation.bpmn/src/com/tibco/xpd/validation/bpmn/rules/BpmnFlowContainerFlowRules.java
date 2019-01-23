/**
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.rules;

/**
 * BpmnParallelJoinFromDownstreamRule
 * 
 * @author aallway
 */
@SuppressWarnings("unchecked")
public class BpmnFlowContainerFlowRules extends AbstractFlowContainerFlowRules {

    private static final String PARAJOIN_FROM_DOWNSTREAM_ID =
            "bpmn.parallelJoinFromDownstream"; //$NON-NLS-1$

    private static final String INCLJOIN_FROM_DOWNSTREAM_ID =
            "bpmn.inclusiveJoinFromDownstream"; //$NON-NLS-1$

    private static final String REQ_NOT_UPSTREAM_OF_REPLY_ID =
            "bpmn.requestNotUpstreamOfReply"; //$NON-NLS-1$

    private static final String REQ_IMPLEMENTOR_NOT_UPSTREAM_OF_ERROR =
            "bpmn.requestNotUpstreamOfError"; //$NON-NLS-1$

    private static final String REQ_NOT_UPSTREAM_OF_ERROR =
            "bpmn.requestNotUpstreamOfNonImplementingError"; //$NON-NLS-1$

    @Override
    protected String getIssueId(ISSUE_TYPE issueType) {
        if (ISSUE_TYPE.PARALLEL_JOIN_FROM_DOWNSTREAM.equals(issueType)) {
            return PARAJOIN_FROM_DOWNSTREAM_ID;
        } else if (ISSUE_TYPE.REQUEST_NOT_UPSTREAM_OF_REPLY.equals(issueType)) {
            return REQ_NOT_UPSTREAM_OF_REPLY_ID;
        } else if (ISSUE_TYPE.REQUESTIMPLEMENTOR_NOT_UPSTREAM_OF_ERROR
                .equals(issueType)) {
            return REQ_IMPLEMENTOR_NOT_UPSTREAM_OF_ERROR;
        } else if (ISSUE_TYPE.REQUEST_NOT_UPSTREAM_OF_ERROR.equals(issueType)) {
            return REQ_NOT_UPSTREAM_OF_ERROR;
        } else if (ISSUE_TYPE.INCLUSIVE_JOIN_FROM_DOWNSTREAM.equals(issueType)) {
            return INCLJOIN_FROM_DOWNSTREAM_ID;
        }

        return null; // DON'T VALIDATE FOR THIS ISSUE
    }

}
