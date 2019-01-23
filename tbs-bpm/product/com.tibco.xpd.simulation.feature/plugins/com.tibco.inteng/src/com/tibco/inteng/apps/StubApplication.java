/* 
 ** 
 **  MODULE:             $RCSfile: StubApplication.java $ 
 **                      $Revision: 1.3 $ 
 **                      $Date: 2005/05/12 09:38:33Z $ 
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
 **    $Log: StubApplication.java $ 
 **    Revision 1.3  2005/05/12 09:38:33Z  wzurek 
 **    defect #21724 
 **    Revision 1.2  2004/08/02 16:13:10Z  WojciechZ 
 **    New Features: 
 **    - resource locator 
 **    - interaction factory 
 **    - different interface to automatic application (possibility to mix: install predefined aplication and configure it for every call using extended attributes) 
 **    Revision 1.1  2004/06/07 15:32:14Z  WojciechZ 
 **    Initial revision 
 **    Revision 1.0  04-Jun-2004  WojciechZ 
 **    Initial revision 
 ** 
 */
package com.tibco.inteng.apps;

import com.tibco.inteng.ExtendedAttribute;
import com.tibco.inteng.exceptions.XpdlBusinessException;
import com.tibco.inteng.impl.ExtendedAttributeImpl;
import com.tibco.inteng.xpdldata.XpdlData;

/**
 * Dummy Xpdl Application - can be used to hold a place during designing interaction
 * 
 * @author WojciechZ
 */
public class StubApplication implements AutomaticApplication {

    /**
     * The constructor 
     */
    public StubApplication() {
        // do nothing
    }

    /**
     * @see com.tibco.inteng.apps.AutomaticApplication#invoke(String, ExtendedAttributeImpl[], XpdlData[])
     */
    public void invoke(String applicationId, ExtendedAttribute[] exts,
            XpdlData[] args) throws XpdlBusinessException {
    }
}