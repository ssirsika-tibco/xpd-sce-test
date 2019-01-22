/* 
** 
**  MODULE:             $RCSfile: XpdlDataFormatException.java $ 
**                      $Revision: 1.1 $ 
**                      $Date: 2004/04/16 08:59:28Z $ 
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
**    $Log: XpdlDataFormatException.java $ 
**    Revision 1.1  2004/04/16 08:59:28Z  WojciechZ 
**    Initial revision 
** 
*/
package com.tibco.inteng.exceptions;

/**
 * Exception thrown on setting xpdl data with value of different type
 * 
 * @author WojciechZ
 */
public class XpdlDataFormatException extends Exception {

    /**
     * the constructor 
     */
    public XpdlDataFormatException() {
        super();
    }

    /**
     * the constructor 
     * @param message error message
     */
    public XpdlDataFormatException(String message) {
        super(message);
    }

    /**
     * the constructor 
     * @param message error message
     * @param cause source exception
     */
    public XpdlDataFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * the constructor 
     * @param cause source exception
     */
    public XpdlDataFormatException(Throwable cause) {
        super(cause);
    }

}
