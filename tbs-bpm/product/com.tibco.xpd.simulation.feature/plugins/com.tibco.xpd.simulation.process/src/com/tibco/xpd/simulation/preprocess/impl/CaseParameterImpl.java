/* 
 ** 
 **  MODULE:             $RCSfile: CaseParameterImpl.java $ 
 **                      $Revision: 1.0 $ 
 **                      $Date: 2005-08-25 $ 
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

import com.tibco.xpd.simulation.preprocess.CaseParameter;

public class CaseParameterImpl implements CaseParameter {

    private final String id;

    private final Object value;

    public CaseParameterImpl(String id, Object value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public Object getValue() {
        return value;
    }

    public String toString() {
        return id + " = \"" + value + "\""; //$NON-NLS-1$ //$NON-NLS-2$
    }

    public boolean equals(Object param) {
        CaseParameterImpl caseParam = (CaseParameterImpl) param;
        return (id != null && id.equals(caseParam.getId()) || id == null
                && caseParam.getId() == null)

                && (value != null && value.equals(caseParam.getValue()) || value == null
                        && caseParam.getValue() == null);
    }
}
