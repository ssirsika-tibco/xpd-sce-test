/* 
** 
**  MODULE:             $RCSfile: Validator.java $ 
**                      $Revision: 1.1 $ 
**                      $Date: 2004/06/15 11:57:32Z $ 
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
**    $Log: Validator.java $ 
**    Revision 1.1  2004/06/15 11:57:32Z  TimSt 
**    Initial revision 
**    Revision 1.0  11-Jun-2004  TimSt 
**    Initial revision 
** 
*/
package com.tibco.inteng.validator;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Defines interface for classes wishing to apply validation rules to the XPDL. 
 * 
 * @author TimSt
 */
public interface Validator {

    /**
     * @param xpdlIS to validate. 
     * @return <code>List</code> of <code>ValidatorMsg</code>s, never null. 
     */
    List validate(InputStream xpdlIS) throws IOException ;

}
