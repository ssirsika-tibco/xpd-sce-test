/* 
 ** 
 **  MODULE:             $RCSfile: AbstractApplication.java $ 
 **                      $Revision: 1.2 $
 **                       
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
 **     
 **   Initial revision 
 ** 
 */
package com.tibco.inteng.apps;

import com.tibco.inteng.ExtendedAttribute;

/**
 * This class is an Abtstract class which has some utility methods whcih are 
 * helpful to the child classes 
 *  
 * @author KamleshU
 */
public abstract class AbstractApplication implements AutomaticApplication {

    /**
     * This method will return the value of an extended attribute specified by 
     * the name of the extended attribute 
     * 
     * @param name
     * @param attribs
     * @return
     */
    protected String getExtendedAttributeValue(String name,
            ExtendedAttribute[] attribs) {
        for (int i = 0; i < attribs.length; i++) {
            if (name.equals(attribs[i].getName())) {
                return attribs[i].getValue();
            }
        }
        return null;
    }
    /**
     * This method will return the extended attribute specified by 
     * the name of the extended attribute 
     * 
     * @param name
     * @param attribs
     * @return
     */
    protected ExtendedAttribute getExtendedAttribute(String name,
            ExtendedAttribute[] attribs) {
        for (int i = 0; i < attribs.length; i++) {
            if (name.equals(attribs[i].getName())) {
                return attribs[i];
            }
        }
        return null;
    }
}