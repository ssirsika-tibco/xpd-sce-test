/* 
** 
**  MODULE:             $RCSfile: SecurityPlugIn.java $ 
**                      $Revision: 1.1 $ 
**                      $Date: 2005/01/26 16:35:04Z $ 
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
**    $Log: SecurityPlugIn.java $ 
**    Revision 1.1  2005/01/26 16:35:04Z  KamleshU 
**    Initial revision 
**    Revision 1.0  18-Jan-2005  KamleshU 
**    Initial revision 
** 
*/
package com.tibco.inteng.security;

/**
 * TODO Description of 'SecurityPlugIn' class
 * 
 * @author KamleshU
 */
public interface SecurityPlugIn {
    
    /**
     * @param performerName
     * @param performerType
     * @return
     */
    public boolean isUserAllowed(String performerId, String performerName, String performerType);

}
