/* 
 ** 
 **  MODULE:             $RCSfile: CaseImpl.java $ 
 **                      $Revision: 1.0 $ 
 **                      $Date: 2005-08-23 $ 
 ** 
 **  DESCRIPTION:           
 **                                              
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc, All Rights Reserved.
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */
package com.tibco.xpd.simulation.preprocess.impl;

import java.util.Date;

import com.tibco.xpd.simulation.preprocess.Case;
import com.tibco.xpd.simulation.preprocess.CaseParameter;

/**
 * Case is a class that holds all the data needed to start workflow case.
 *
 * @author jarciuch
 */
public class CaseImpl implements Case {

    private final String caseId;
    private CaseParameter[] caseParameters;
    private String packageName;
    private String processName;
    private final Date startDate;

    /**
     * The constructor.
     * 
     * @param caseId
     * @param caseParameters
     */
    public CaseImpl(String caseId, CaseParameter[] caseParameters) {
        this.startDate = null;
        this.caseId = caseId;
        this.caseParameters = new CaseParameter[caseParameters.length];
        System.arraycopy(caseParameters, 0, this.caseParameters, 0, caseParameters.length);
    }
    
    /**
     * The constructor.
     * 
     * @param caseId
     * @param caseParameters
     */
    public CaseImpl(String caseId, CaseParameter[] caseParameters, Date startDate) {
        this.caseId = caseId;
        this.startDate = startDate;
        this.caseParameters = new CaseParameter[caseParameters.length];
        System.arraycopy(caseParameters, 0, this.caseParameters, 0, caseParameters.length);
    }

    /**
     * @see com.tibco.xpd.simulation.preprocess.Case#getCaseParameters()
     */
    public CaseParameter[] getCaseParameters() {
        CaseParameter[] caseParameters = new CaseParameter[this.caseParameters.length];
        System.arraycopy(this.caseParameters, 0, caseParameters, 0, caseParameters.length);
        return caseParameters;
    }

    /**
     * @see com.tibco.xpd.simulation.preprocess.Case#getCaseParameter(java.lang.String)
     */
    public CaseParameter getCaseParameter(String parameterId) {
        for (int i = 0; i < caseParameters.length; i++) {
            if (parameterId.equals(caseParameters[i].getId())) {
                return caseParameters[i];
            }
        }
        return null;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuffer sb = new StringBuffer("Case ID="+caseId); //$NON-NLS-1$
        sb.append(" StartDate=").append(startDate); //$NON-NLS-1$
        sb.append("\n "); //$NON-NLS-1$
        for (int i = 0; i < caseParameters.length; i++) {
            sb.append("["); //$NON-NLS-1$
            sb.append(caseParameters[i]);
            sb.append("]"); //$NON-NLS-1$
            if (i < caseParameters.length - 1) {
                sb.append("\n "); //$NON-NLS-1$
            }
        }
        return sb.toString();
    }

    /**
     * @see com.tibco.xpd.simulation.preprocess.Case#getId()
     */
    public String getId() {
        return caseId;
    }

    /**
     * @see com.tibco.xpd.simulation.preprocess.Case#getPackageName()
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * @see com.tibco.xpd.simulation.preprocess.Case#setPackageName(java.lang.String)
     */
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    /**
     * @see com.tibco.xpd.simulation.preprocess.Case#getProcessName()
     */
    public String getProcessName() {
        return processName;
    }

    /**
     * @see com.tibco.xpd.simulation.preprocess.Case#setProcessName(java.lang.String)
     */
    public void setProcessName(String processName) {
        this.processName = processName;
    }

    /**
     * @see com.tibco.xpd.simulation.preprocess.Case#getCaseStartDate()
     */
    public Date getCaseStartDate() {
        return startDate;
    }
    
    
}
