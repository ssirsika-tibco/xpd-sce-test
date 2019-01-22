/* 
** 
**  MODULE:             $RCSfile: XpdlException.java $ 
**                      $Revision: 1.2 $ 
**                      $Date: 2004/04/08 16:02:14Z $ 
** 
** DESCRIPTION    :           
**                                              
** 
**  ENVIRONMENT:  Java - Platform independent 
** 
**  COPYRIGHT:    (c) 2003 Staffware plc, All Rights Reserved. 
** 
**  MODIFICATION HISTORY: 
** 
**    $Log: XpdlException.java $ 
**    Revision 1.2  2004/04/08 16:02:14Z  WojciechZ 
**    code review 
**    move to apache xml beans 
**    xpdl data use xml beans to hold the data 
**    Revision 1.1  2004/03/26 15:07:57Z  WojciechZ 
**    Initial revision 
**    Revision 1.2  2004/03/24 14:29:27Z  WojciechZ 
**    Work up to 24-03-2004 
**    Revision 1.1  2004/03/18 13:01:11Z  WojciechZ 
**    Initial revision 
*/
package com.tibco.inteng.exceptions;

/**
 * Critical (logical) errors in XPDL file
 * 
 * @author WojciechZ
 */
public class XpdlException extends RuntimeException {

	/**
     * @see java.lang.RuntimeException
	 */
	public XpdlException() {
		super(); 
	}

	/**
     * @see java.lang.RuntimeException
	 */
	public XpdlException(String message) {
		super(message);
	}

	/**
     * @see java.lang.RuntimeException
	 */
	public XpdlException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
     * @see java.lang.RuntimeException
	 */
	public XpdlException(Throwable cause) {
		super(cause);
	}
}
