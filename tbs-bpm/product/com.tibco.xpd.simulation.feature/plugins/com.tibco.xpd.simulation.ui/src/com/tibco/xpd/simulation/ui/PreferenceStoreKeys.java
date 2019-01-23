/**
 * 
 */
package com.tibco.xpd.simulation.ui;

/**
 * Key holder for preferences.
 * 
 * @author mmaciuki
 */
public class PreferenceStoreKeys {
    
    /**
     * TODO comment me!
     */
    private  PreferenceStoreKeys() {}

    /**
     * Simulation delay
     */
    public static final String SIMULATION_DELAY = "simulation_delay"; //$NON-NLS-1$
    
    /**
     * Shall switch to simulation perspective
     */
    public static final String SWITCH_PERSPECTIVE = "switch_to_simulation_perspective"; //$NON-NLS-1$
    
    /**
     * Shall ask if switch to simulation perspective
     */
    public static final String DONT_ASK_TO_SWITCH_PERSPECTIVE = "ask_to_switch_to_simulation_perspective"; //$NON-NLS-1$

    /**
     * Last set simulation start time
     */
    public static final String SIMULATION_START_TIME = "simulation_start_time"; //$NON-NLS-1$
}
