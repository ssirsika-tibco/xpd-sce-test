/* 
 ** 
 **  MODULE:             $RCSfile: ResourceRTException.java $ 
 **                      $Revision: 1.3 $ 
 **                      $Date: 2005/04/29 15:51:38Z $ 
 ** 
 ** DESCRIPTION    :           
 **                                              
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2004 TIBCO Software Inc, All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: ResourceRTException.java $ 
 **    Revision 1.3  2005/04/29 15:51:38Z  pburton 
 **    Added code to explicitly close streams, readers and writers. 
 **    Revision 1.2  2005/02/18 10:38:32Z  wzurek 
 **    updated copyright 
 */
package com.tibco.inteng.resources;

/**
 * Exception when locating or fetching resources
 * 
 * @author WojciechZ
 *
 */
public class ResourceRTException extends RuntimeException {
    /**
     * @param message
     */
    public ResourceRTException(String message) {
        super(message);
    }
    /**
     * @param message
     * @param cause
     */
    public ResourceRTException(String message, Throwable cause) {
        super(message, cause);
    }
    /**
     * @param cause
     */
    public ResourceRTException(Throwable cause) {
        super(cause);
    }
}
