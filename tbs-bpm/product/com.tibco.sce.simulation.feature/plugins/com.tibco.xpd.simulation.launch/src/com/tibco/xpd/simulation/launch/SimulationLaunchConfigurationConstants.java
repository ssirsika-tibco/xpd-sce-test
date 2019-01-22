/* 
** 
**  MODULE:             $RCSfile: SimulationLaunchConfigurationConstants.java $ 
**                      $Revision: 1.0 $ 
**                      $Date: 2006-01-13 $ 
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
 * Class containing all simulation launch configuration constants.
 *
 * @author jarciuch
 */
public class SimulationLaunchConfigurationConstants {

    
    /**
     * Identifier for the Local Simulation launch configuration type
     * (value <code>"org.eclipse.jdt.launching.localJavaApplication"</code>).
     */
    public static final String ID_SIMULATION = "com.tibco.xpd.simulation.simulationLaunch"; //$NON-NLS-1$
    
    /**
     * Launch configuration attribute key. The value is a name of
     * a project associated with a Simulation launch configuration.
     */
    public static final String ATTR_PROJECT_NAME = LaunchPlugin.getUniqueIdentifier() + ".PROJECT_ATTR"; //$NON-NLS-1$
    
    /**
     * Launch configuration attribute key. The value is a name of
     * a package associated with a Simulation launch configuration.
     */
    public static final String ATTR_PACKAGE = LaunchPlugin.getUniqueIdentifier() + ".PACKAGE_ATTR"; //$NON-NLS-1$

    /**
     * Launch configuration attribute key. The value is a name of
     * a process name associated with a Simulation launch configuration.
     */
    public static final String ATTR_PROCESS_NAME = LaunchPlugin.getUniqueIdentifier() + ".PROCESS_NAME_ATTR"; //$NON-NLS-1$

    /**
     * Launch configuration attribute key. The value is a name of
     * a process id associated with a Simulation launch configuration.
     */
    public static final String ATTR_PROCESS_ID = LaunchPlugin.getUniqueIdentifier() + ".PROCESS_ID_ATTR"; //$NON-NLS-1$
    
    /**
     * The private constructor to prevent instantation.
     */
    private SimulationLaunchConfigurationConstants() {
        super();
        // TODO Auto-generated constructor stub
    }

}
