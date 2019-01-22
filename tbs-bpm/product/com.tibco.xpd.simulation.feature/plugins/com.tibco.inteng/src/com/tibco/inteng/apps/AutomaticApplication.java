/* 
 ** 
 **  MODULE:             $RCSfile: IXpdlAutomaticApplication.java $ 
 **                      $Revision: 1.6 $ 
 **                      $Date: 2004/08/02 16:13:12Z $ 
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
 **    $Log: IXpdlAutomaticApplication.java $ 
 **    Revision 1.6  2004/08/02 16:13:12Z  WojciechZ 
 **    New Features: 
 **    - resource locator 
 **    - interaction factory 
 **    - different interface to automatic application (possibility to mix: install predefined aplication and configure it for every call using extended attributes) 
 **    Revision 1.5  2004/06/07 16:24:24Z  WojciechZ 
 **    add XpdlBussinessException declaration 
 **    Revision 1.4  2004/04/13 16:32:50Z  WojciechZ 
 **    work up to 13/04/2004 
 **    Revision 1.3  2004/04/08 16:02:10Z  WojciechZ 
 **    code review 
 **    move to apache xml beans 
 **    xpdl data use xml beans to hold the data 
 **    Revision 1.2  2004/03/29 08:48:56Z  WojciechZ 
 **    Work up to 29-03-2004 
 **    Revision 1.0  23-Mar-2004  WojciechZ 
 **    Initial revision 
 ** 
 */
package com.tibco.inteng.apps;

import com.tibco.inteng.ExtendedAttribute;
import com.tibco.inteng.exceptions.XpdlBusinessException;
import com.tibco.inteng.xpdldata.XpdlData;

/**
 * Interface to invoke automatic application from InteractionImpl engine.
 * 
 * @author WojciechZ
 */
public interface AutomaticApplication {
	/**
	 * Invoke application with parameters
	 * 
	 * @param applicationId -
	 *            Id of current application
	 * @param extendedAttributes
	 *            array of extended attributes form current application:
	 *            Extended attribute name -> ExtendedAttributeImpl
	 * 
	 * @param args
	 *            parameters for application
	 * @throws XpdlBusinessException
	 */
	public abstract void invoke(String applicationId,
			ExtendedAttribute[] extendedAttributes, XpdlData[] args)
			throws XpdlBusinessException;
}
