/* 
** 
**  MODULE:             $RCSfile: ValidatorMsg.java $ 
**                      $Revision: 1.1 $ 
**                      $Date: 2004/06/17 09:26:13Z $ 
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
**    $Log: ValidatorMsg.java $ 
**    Revision 1.1  2004/06/17 09:26:13Z  TimSt 
**    Initial revision 
**    Revision 1.0  16-Jun-2004  TimSt 
**    Initial revision 
** 
*/
package com.tibco.inteng.validator;

/**
 * Represents a validation message from one of the Validator classes. 
 * 
 * <p>Different thresholds of message may be returned, 
 * e.g. Error, Warning etc.</p>
 * 
 * @author TimSt
 */
public class ValidatorMsg {

    /**
     * A predefined threshold for Validator messages. 
     */
    public static final int ERROR_THRESHOLD = 4 ; 

    /**
     * A predefined threshold for Validator messages. 
     */    
    public static final int WARN_THRESHOLD = 3 ; 
    
    /** 
     * The threshold of the message from the validator. 
     */
    private int threshold = ERROR_THRESHOLD ; 
    
    /**
     * The description of the message raised by the validator.  
     */
    private String msg ; 
    
    /**
     * 
     * @param threshold One of the thresholds predefined by constants 
     *      in this class. 
     * @param msg The English description of the message. 
     */
    public ValidatorMsg(int newThreshold, String newMsg) {
        if (newThreshold != WARN_THRESHOLD && newThreshold != ERROR_THRESHOLD) {
            threshold = newThreshold ; 
        }
        msg = newMsg ; 
    }
    
    /**
     * @return Returns the msg.
     */
    public String getMsg() {
        return msg;
    }
    /**
     * @return Returns the threshold.
     */
    public int getThreshold() {
        return threshold;
    }

    /**
     * @return String representation of Threshold. 
     */
    public String getThresholdString() {
        String sTheshold = null ;
        switch (threshold) {
        case ERROR_THRESHOLD: 
            sTheshold = "ERROR" ; 
            break ; 
        case WARN_THRESHOLD: 
            sTheshold = "WARN" ; 
            break ; 
        }
        return sTheshold;
    }
}
