/* 
** 
**  MODULE:             $RCSfile: EngineExteption.java $ 
**                      $Revision: 1.8 $ 
**                      $Date: 2004/08/24 12:48:17Z $ 
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
**    $Log: EngineExteption.java $ 
**    Revision 1.8  2004/08/24 12:48:17Z  TimSt 
**    removed spelling mistake 'bussiness' - defect 21063 
**    Revision 1.7  2004/08/02 16:13:13Z  WojciechZ 
**    New Features: 
**    - resource locator 
**    - interaction factory 
**    - different interface to automatic application (possibility to mix: install predefined aplication and configure it for every call using extended attributes) 
**    Revision 1.6  2004/08/02 12:02:34Z  TimSt 
**    misnamed subclass files to maintain backward compatibility with correct named ones 
**    Revision 1.4  2004/08/02 09:13:29Z  TimSt 
**    temp stop gap while introduce correctly named class 
** 
*/
package com.tibco.inteng.exceptions;


/**
 * Critical errors in engine. 
 * 
 * @author WojciechZ
 */
public class EngineException extends RuntimeException {

	/**
	 * 
	 */
	public EngineException() {
		super();
	}
	/**
	 * @see java.lang.RuntimeException
	 */
	public EngineException(String message) {		
		super(message);
	}
	/**
     * @see java.lang.RuntimeException
	 */
	public EngineException(String message, Throwable cause) {
		super(message, cause);
	}
	/**
     * @see java.lang.RuntimeException
	 */
	public EngineException(Throwable cause) {
		super(cause);
	}
}
