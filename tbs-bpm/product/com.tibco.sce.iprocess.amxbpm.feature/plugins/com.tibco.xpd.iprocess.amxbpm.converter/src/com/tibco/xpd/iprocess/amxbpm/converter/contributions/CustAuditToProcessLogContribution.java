/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.iprocess.amxbpm.converter.contributions;

import java.util.List;

import com.tibco.xpd.iprocess.amxbpm.converter.internal.Messages;

/**
 * Contribution to replaces all custaudit(proc, casenum, auditID, stepname,
 * stepdesc, user) calls by Process.auditLog('AuditId::'+auditId) call. The 3rd
 * parameter passed to CUSTAUDIT() method is the Audit Id that we are concerned
 * about.
 * 
 * @author kthombar
 * @since 01-May-2014
 */
public class CustAuditToProcessLogContribution extends
        AbstractiProcessScriptMethodConversionContribution {

    private static final String PROCESS_AUDIT_LOG = "Process.auditLog("; //$NON-NLS-1$

    /**
     * @param scriptMethodToConvert
     */
    public CustAuditToProcessLogContribution() {
        super("custaudit", 6); //$NON-NLS-1$
    }

    /**
     * 
     * @see com.tibco.xpd.iprocess.amxbpm.converter.contributions.AbstractiProcessScriptMethodConversionContribution#convertEachScriptLine(java.lang.String,
     *      int, int, int, java.util.List)
     * 
     * @param stringtoConvert
     * @param startOfMethodIndex
     * @param startBracketIndex
     * @param endBracketIndex
     * @param listOfParametersInOrder
     * @return
     */
    @Override
    protected String convertEachScriptLine(String stringtoConvert,
            int startOfMethodIndex, int startBracketIndex, int endBracketIndex,
            List<String> listOfParametersInOrder) {
        /* get the string before custaudit method */
        String stringBeforeCustAuditMethod =
                stringtoConvert.substring(0, startOfMethodIndex);
        /* get the string after custaudit method */
        String stringAfterCustauditMethod =
                stringtoConvert.substring(endBracketIndex,
                        stringtoConvert.length());

        StringBuffer buffer = new StringBuffer();

        buffer.append(stringBeforeCustAuditMethod);
        buffer.append(PROCESS_AUDIT_LOG);
        buffer.append(Messages.CustAuditToProcessLogContribution_AuditId_msg);
        /* the 3rd parameter is the audit id */
        buffer.append(listOfParametersInOrder.get(2));
        buffer.append(stringAfterCustauditMethod);

        return buffer.toString();
    }

}