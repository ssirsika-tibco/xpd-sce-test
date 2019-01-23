/* 
** 
**  MODULE:             $RCSfile: NoOpValidator.java $ 
**                      $Revision: 1.1 $ 
**                      $Date: 2004/06/15 11:57:33Z $ 
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
**    $Log: NoOpValidator.java $ 
**    Revision 1.1  2004/06/15 11:57:33Z  TimSt 
**    Initial revision 
**    Revision 1.0  14-Jun-2004  TimSt 
**    Initial revision 
** 
*/
package com.tibco.inteng.validator.validators;

import java.io.InputStream;
import java.util.List;
import java.util.Vector;

import com.tibco.inteng.validator.Validator;

/**
 * Validator implementation that does nothing. 
 * 
 * <p>Useful in testing the Validator application.</p>  
 * 
 * @author TimSt
 */
public class NoOpValidator implements Validator {

    /**
     * @see com.tibco.inteng.validator.Validator#validate(InputStream)
     * @return <code>List</code> of <code>ValidatorMsg</code>s, never null. 
     */
    public List validate(InputStream xpdlIS) {
        System.out.println("No-Op Validator called ") ; 
        return new Vector() ; 
    }

}
