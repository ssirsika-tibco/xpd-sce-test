/* 
** 
**  MODULE:             $RCSfile: XpdlBussinessException.java $ 
**                      $Revision: 1.8 $ 
**                      $Date: 2004/11/15 09:56:23Z $ 
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
**    $Log: XpdlBussinessException.java $ 
**    Revision 1.8  2004/11/15 09:56:23Z  Timst 
**    enhanced javadoc only  
**    Revision 1.7  2004/08/09 08:52:35Z  WojciechZ 
**    added: Extenrnal Package References 
**    Revision 1.6  2004/08/02 16:13:25Z  WojciechZ 
**    New Features: 
**    - resource locator 
**    - interaction factory 
**    - different interface to automatic application (possibility to mix: install predefined aplication and configure it for every call using extended attributes) 
**    Revision 1.5  2004/08/02 12:02:35Z  TimSt 
**    misnamed subclass files to maintain backward compatibility with correct named ones 
**    Revision 1.3  2004/08/02 09:12:17Z  TimSt 
**    deprecated stop-gap to correctly named class 
** 
*/
package com.tibco.inteng.exceptions;

/**
 * Exception thrown from Xpdl Application, 
 * it is caught by InteractionImpl Engine and can be accessed through
 * 'Transition On Exception' xpdl feature.
 * 
 * <p>Message is required - it is treated as the key to property file
 * so that a message may be returned to the user. </p>
 * 
 * @author WojciechZ
 */
public class XpdlBusinessException extends Exception {
    /**
     * 
     * @param message
     */
    public XpdlBusinessException(String message) {
        super(message);
    }
    /**
     * 
     * @param message
     * @param cause
     */
    public XpdlBusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
