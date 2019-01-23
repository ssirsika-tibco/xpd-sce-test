/* 
 ** 
 **  MODULE:             $RCSfile: SimulationApplication.java $ 
 **                      $Revision: 1.0 $ 
 **                      $Date: 2005-09-06 $ 
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
package com.tibco.xpd.simulation.model;


import com.tibco.ie.apps.ServicesConsts;
import com.tibco.inteng.ExtendedAttribute;
import com.tibco.inteng.apps.AbstractApplication;
import com.tibco.inteng.apps.AutomaticApplication;
import com.tibco.inteng.exceptions.XpdlException;
import com.tibco.inteng.xpdldata.XpdlData;

public class AutomaticSimApplicationDispatcher extends AbstractApplication implements
        AutomaticApplication {

    public AutomaticSimApplicationDispatcher() {

    }

    /**
     * Gets extended attribute.
     * 
     *
     * @param exts
     * @param applicationId
     * @return
     */
    public String getSimulationData(ExtendedAttribute[] exts, String applicationId) {

        ExtendedAttribute ext = getExtendedAttribute(
                ServicesConsts.SCRIPT_APP_NAME, exts);
        if (ext == null) {
            XpdlException e = new XpdlException(
                    "There is no 'ScriptApplication' extended attribute in application: '" //$NON-NLS-1$
                            + applicationId + "'"); //$NON-NLS-1$
            throw e;
        }
        String simulationData = ext.getContent();
        if (simulationData == null) {
            XpdlException e = new XpdlException(
                    "There is no 'ScriptApplication' extended attribute in application: '" //$NON-NLS-1$
                            + applicationId + "'"); //$NON-NLS-1$
            throw e;
        }
        return simulationData;
    }

    /**
     * invoke application
     * 
     * @param applicationId
     * @param exts
     * @param args
     *            arguments
     */
    public void invoke(String applicationId, ExtendedAttribute[] exts,
            XpdlData[] args) {
       // String simulationData = getSimulationData(exts, applicationId);
    }

}
