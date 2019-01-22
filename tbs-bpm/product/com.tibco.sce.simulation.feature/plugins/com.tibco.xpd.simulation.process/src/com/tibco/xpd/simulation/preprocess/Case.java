/* 
** 
**  MODULE:             $RCSfile: Case.java $ 
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
package com.tibco.xpd.simulation.preprocess;

import java.util.Date;


/**
 * Case represents a workflow case which can be started by workflow engine.
 *
 * @author jarciuch
 */
public interface Case {

    
    /**
     * Returns case Id.
     *
     * @return case Id.
     */
    public String getId();
    
    /**
     * Returns an array of case parameters.
     *
     * @return an array of case parameters.
     */
    public CaseParameter[] getCaseParameters();
    
    /**
     * Returns the caseParameter with the specified parameterId.
     *
     * @param parameterId
     * @return caseParameter with the specified parameterId.
     * @throws NullPointerException if parameterId is null.
     */
    public CaseParameter getCaseParameter(String parameterId);
    
    /**
     * Gets the name of the top level package.
     *
     * @return top level package name.
     */
    public String getPackageName();
    
    /**
     * Sets the name of the top level package.
     */
    public void setPackageName(String packageName);
    
    /**
     * Gets the name of the top level process. 
     *
     * @return top level process name.
     */
    public String getProcessName();
    
    /**
     * Sets the name of the top level process. 
     */
    public void setProcessName(String processName);
    
    /**
     * Gets optional case start date or null it there was no date assigned
     * 
     * @return date of case start
     */
    public Date getCaseStartDate();
    
}
