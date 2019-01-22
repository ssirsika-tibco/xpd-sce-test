/* 
** 
**  MODULE:             $RCSfile: DelegateException.java $ 
**                      $Revision: 1.3 $ 
**                      $Date: 2004/11/15 09:56:15Z $ 
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
**    $Log: DelegateException.java $ 
**    Revision 1.3  2004/11/15 09:56:15Z  Timst 
**    enhanced Javadoc only  
**    Revision 1.2  2004/06/07 15:37:00Z  WojciechZ 
**    new comments 
**    Revision 1.1  2004/05/07 08:02:45Z  WojciechZ 
**    Initial revision 
**    Revision 1.0  26-Apr-2004  WojciechZ 
**    Initial revision 
** 
*/
package com.tibco.inteng.exceptions;

/**
 * Exception thrown when External Delegate throws an exception.
 * 
 * <p>This is a system exception, i.e. something that the user is 
 * not expected to be able to recover from. </p> 
 * 
 * @author WojciechZ
 */
public class DelegateException extends RuntimeException {
    public DelegateException() {
        super();
    }
    public DelegateException(String message) {
        super(message);
    }
    public DelegateException(String message, Throwable cause) {
        super(message, cause);
    }
    public DelegateException(Throwable cause) {
        super(cause);
    }
}
