/* 
** 
**  MODULE:             $RCSfile: OneStartOneEndActivityValidator.java $ 
**                      $Revision: 1.1 $ 
**                      $Date: 2004/06/17 08:45:28Z $ 
** 
** DESCRIPTION    :           
**                                              
** 
**  ENVIRONMENT:  Java - Platform independent 
** 
**  COPYRIGHT:    (c) 2004 Staffware plc, All Rights Reserved. 
** 
**  MODIFICATION HISTORY: 
** 
**    $Log: OneStartOneEndActivityValidator.java $ 
**    Revision 1.1  2004/06/17 08:45:28Z  TimSt 
**    Initial revision 
**    Revision 1.0  15-Jun-2004  TimSt 
**    Initial revision 
** 
*/
package com.tibco.inteng.validator.validators;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import org.wfmc.x2002.xpdl10.ActivitiesDocument.Activities;
import org.wfmc.x2002.xpdl10.ActivityDocument.Activity;
import org.wfmc.x2002.xpdl10.PackageDocument.Package;
import org.wfmc.x2002.xpdl10.TransitionDocument.Transition;
import org.wfmc.x2002.xpdl10.TransitionsDocument.Transitions;
import org.wfmc.x2002.xpdl10.WorkflowProcessDocument.WorkflowProcess;

import com.tibco.inteng.validator.Validator;
import com.tibco.inteng.validator.ValidatorMsg;

/**
 * TODO Description of 'OnlyOneStartActivityValidator' class
 * 
 * @author TimSt
 */
public class OneStartOneEndActivityValidator extends XpdlComplianceValidator
        implements
            Validator {

    private static Logger logger = 
            Logger.getLogger(OneStartOneEndActivityValidator.class) ; 
    
    /**
     * Validate the XPDL package testing for just one start activity.
     * 
     * <p>The algorithm checks every activity, ensuring just one lacks 
     * an influent transition.</p>  
     * 
     * @see com.tibco.inteng.validator.validators.XpdlComplianceValidator#validate()
     */
    public List validate() throws IOException {
        Vector list = new Vector() ;
        Package pack = getPackage() ;
        
        for (int i = 0 ; 
                i < pack.getWorkflowProcesses().sizeOfWorkflowProcessArray() ; 
                i++) {
            WorkflowProcess proc = 
                    pack.getWorkflowProcesses().getWorkflowProcessArray(i) ;
            
            ArrayList startActivities = new ArrayList() ;
            ArrayList endActivities = new ArrayList() ; 
            Activities activities = proc.getActivities() ; 
            // Iterate Activities... 
            for (int j = 0 ; j < activities.sizeOfActivityArray() ; j++) {
                Activity activity = activities.getActivityArray(j) ;
                logger.warn("Testing if Activity: " + activity.getId() 
                        + " is start point...") ; 
                 
                // Iterate transitions for this activity...  
                Transitions trans = proc.getTransitions() ; 
                boolean startFound = false; 
                for (int k = 0 ; k < trans.sizeOfTransitionArray() ; k++) {
                    Transition tran = trans.getTransitionArray(k) ; 
                    if (tran.getTo().equals(activity.getId())) {
                        startFound = true ; 
                        continue ; 
                    }
                }             
                // hold on to Activities with no influent Transition
                if (!startFound) {
                    logger.warn("... Activity: " + activity.getId() 
                            + " IS start point.") ;                 
                    startActivities.add(activity) ;
                } 
                
                logger.warn("Testing if Activity: " + activity.getId() 
                        + " is end point...") ;                    
                boolean endFound = false; 
                for (int k = 0 ; k < trans.sizeOfTransitionArray() ; k++) {
                    Transition tran = trans.getTransitionArray(k) ; 
                    if (tran.getFrom().equals(activity.getId())) {
                        endFound = true ; 
                        continue ; 
                    }
                }
                // hold on to Activities with no outgoing Transition
                if (!endFound) {
                    logger.warn("... Activity: " + activity.getId() 
                            + " IS end point.") ;                 
                    startActivities.add(activity) ;
                }                 
            }
            if (startActivities.size() > 1) {
                // Too many start activities
                String msgText = "Too many start activities" ; 
                ValidatorMsg msg = new ValidatorMsg(
                        ValidatorMsg.ERROR_THRESHOLD, msgText) ;  
                list.add(msg) ; 
            } else if (startActivities.size() < 1) {
                String msgText = "Too few start activities" ; 
                ValidatorMsg msg = new ValidatorMsg(
                        ValidatorMsg.ERROR_THRESHOLD, msgText) ;  
                list.add(msg) ; 
            }
            if (endActivities.size() > 1) {
                // Too many start activities
                String msgText = "Too many end activities" ; 
                ValidatorMsg msg = new ValidatorMsg(
                        ValidatorMsg.ERROR_THRESHOLD, msgText) ;  
                list.add(msg) ; 
            } else if (endActivities.size() < 1) {
                String msgText = "Too few end activities" ; 
                ValidatorMsg msg = new ValidatorMsg(
                        ValidatorMsg.ERROR_THRESHOLD, msgText) ;  
                list.add(msg) ; 
            }
        }
        return list;
    }
}
