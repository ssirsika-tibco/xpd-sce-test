/* 
** 
**  MODULE:             $RCSfile: InvalidSimulationConfigException.java $ 
**                      $Revision: 1.0 $ 
**                      $Date: 2006-03-06 $ 
** 
**  DESCRIPTION:           
**                                              
** 
**  ENVIRONMENT:  Java - Platform independent 
** 
**  COPYRIGHT:    (c) 2006 TIBCO Software Inc, All Rights Reserved.
** 
**  MODIFICATION HISTORY: 
** 
**    $Log: $ 
** 
*/
package com.tibco.xpd.simulation.launch;

/**
 * Signals problem with the simulation launch configuration.
 *
 * @author jarciuch
 */
public class InvalidSimulationLaunchConfigException extends Exception {

    /** Default serial UID. */
    private static final long serialVersionUID = 1L;

    public InvalidSimulationLaunchConfigException() {
        super();
    }

    public InvalidSimulationLaunchConfigException(String message) {
        super(message);
    }

    public InvalidSimulationLaunchConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidSimulationLaunchConfigException(Throwable cause) {
        super(cause);
    }

}
