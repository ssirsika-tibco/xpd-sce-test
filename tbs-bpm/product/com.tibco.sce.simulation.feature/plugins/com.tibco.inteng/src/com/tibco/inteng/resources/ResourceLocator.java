/* 
** 
**  MODULE:             $RCSfile: ResourceLocator.java $ 
**                      $Revision: 1.1 $ 
**                      $Date: 2004/08/02 16:19:24Z $ 
** 
** DESCRIPTION    :           
**                                              
** 
**  ENVIRONMENT:  Java - Platform independent 
** 
**  COPYRIGHT:    (c) 2004 TIBCO, All Rights Reserved. 
** 
**  MODIFICATION HISTORY: 
** 
**    $Log: ResourceLocator.java $ 
**    Revision 1.1  2004/08/02 16:19:24Z  WojciechZ 
**    Initial revision 
** 
*/
package com.tibco.inteng.resources;

import java.io.IOException;
import java.io.InputStream;

/**
 * Interface for Resource Locator for InteractionImpl Engine
 * 
 * InteractionImpl Engine use resource locators to locate and/or
 * fetch resources. All external references from XPDL file
 * are located using instaled resources.
 * 
 * Helper 'ResourceLocatorAdapter' allows to join ResourceLocators
 * in pipe, when resource locator cannot localize resource
 * it ask it's parent to do that.
 * @see com.tibco.inteng.resources.ResourceLocatorAdapter  
 * 
 * @author WojciechZ
 */
public interface ResourceLocator {
    /**
     * Open and return InputStream based on location
     * 
     * @param location - description of location, format
     * depends on set of instaled resources.
     * @return open and ready to read InputStream
     * @throws IOException - on IO problem
     */
    InputStream getStream(String location) throws IOException;
    /**
     * Open, fetch, and return content of given location
     * 
     * @param location - description of location, format
     * depends on set of instaled resources.
     * @return content of given location in String
     * @throws IOException - on IO problem
     */
    String getString(String location) throws IOException;
}
