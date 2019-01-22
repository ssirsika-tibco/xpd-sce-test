/* 
** 
**  MODULE:             $RCSfile: DefaultSecurityPlugIn.java $ 
**                      $Revision: 1.1 $ 
**                      $Date: 2005/01/26 16:35:05Z $ 
** 
** DESCRIPTION    :           
**                                              
** 
**  ENVIRONMENT:  Java - Platform independent 
** 
**  COPYRIGHT:    (c) 2005 TIBCO Software Inc., All Rights Reserved. 
** 
**  MODIFICATION HISTORY: 
** 
**    $Log: DefaultSecurityPlugIn.java $ 
**    Revision 1.1  2005/01/26 16:35:05Z  KamleshU 
**    Initial revision 
**    Revision 1.0  20-Jan-2005  KamleshU 
**    Initial revision 
** 
*/
package com.tibco.inteng.security;

/**
 * TODO Description of 'DefaultSecurityPlugIN' class
 * 
 * @author KamleshU
 */
public class DefaultSecurityPlugIn implements SecurityPlugIn {
    /**
     * @param performerName
     * @param performerType
     * @return
     */
    public boolean isUserAllowed(String performerId, String performerName, String performerType){
        return false;
    }
}
