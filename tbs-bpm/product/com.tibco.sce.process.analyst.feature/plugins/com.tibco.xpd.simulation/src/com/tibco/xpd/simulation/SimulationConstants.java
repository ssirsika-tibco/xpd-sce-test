/**
 * 
 */
package com.tibco.xpd.simulation;

/**
 * Variuos constants that needs to be shared across all Simulation plugins
 * 
 * @author wzurek
 */
public class SimulationConstants {

    /**
     * Destination Enviroment ID for simulation v1.2. It is used by validator
     * and luncher
     */
    public static final String SIMULATION_GLOBAL_DEST =
            "com.tibco.xpd.simulation.globalDestination"; //$NON-NLS-1$

    public static final String SIMULATION_DEST_ENV_1_2_ID = "simulation1.2"; //$NON-NLS-1$

    public static final String SIM_PARTICIPANT_DATA =
            "ParticipantSimulationData"; //$NON-NLS-1$

    public static final String SIM_START_EVENT_DATA = "StartSimulationData"; //$NON-NLS-1$

    public static final String SIM_ACTIVITY_DATA = "ActivitySimulationData"; //$NON-NLS-1$

    public static final String SIMULATION_FOLDER = "Simulation"; //$NON-NLS-1$

    /** The reporting time unit ID. */
    public static final String REPORTING_TIME_UNIT = "ReportingTimeUnit"; //$NON-NLS-1$

    /** The default reporting time unit. */
    public static final String REPORTING_TIME_UNIT_VALUE = "Minute"; //$NON-NLS-1$

    /** The reporting currency unit ID. */
    public static final String REPORTING_CURRENCY_UNIT =
            "ReportingCurrencyUnit"; //$NON-NLS-1$

    /** The default reporting currency unit. */
    public static final String REPORTING_CURRENCY_UNIT_VALUE = "USD"; //$NON-NLS-1$

}
