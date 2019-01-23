/* 
** 
**  MODULE:             $RCSfile: ExperimentParamConstants.java $ 
**                      $Revision: 1.0 $ 
**                      $Date: 2005-10-04 $ 
** 
**  DESCRIPTION:           
**                                              
** 
**  ENVIRONMENT:  Java - Platform independent 
** 
**  COPYRIGHT:    (c) 2005 TIBCO Software Inc, All Rights Reserved.
** 
**  MODIFICATION HISTORY: 
** 
**    $Log: $ 
** 
*/
package com.tibco.xpd.simulation.launch;

import java.text.SimpleDateFormat;
import java.util.Locale;

public final class ExperimentParamConstants {

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
            "dd.MM.yyyy HH:mm:ss:SSS", Locale.GERMANY); //$NON-NLS-1$
    
    private ExperimentParamConstants() {
    }
    
    /** Experiment setting "name" */
    public final static String EXP_NAME = "name"; //$NON-NLS-1$

    /** Experiment setting "outputPath" */
    public final static String EXP_OUTPUT_PATH = "outputPath"; //$NON-NLS-1$

    /** Experiment setting "isTimed" */
    public final static String EXP_IS_TIMED = "isTimed"; //$NON-NLS-1$

    /** Experiment setting "referenceTime" */
    public final static String EXP_REF_TIME = "referenceTime"; //$NON-NLS-1$

    /** Experiment setting "referenceUnit" */
    public final static String EXP_REF_UNIT = "referenceUnit"; //$NON-NLS-1$

    /** Experiment setting "startTime" */
    public final static String EXP_START_TIME = "startTime"; //$NON-NLS-1$

    /** Experiment setting "stopTime" */
    public final static String EXP_STOP_TIME = "stopTime"; //$NON-NLS-1$

    /** Experiment setting "showProgressBar" */
    public final static String EXP_SHOW_PROG_BAR = "showProgressBar"; //$NON-NLS-1$

    /** Experiment setting "traceStartTime" */
    public final static String EXP_TRACE_START = "traceStartTime"; //$NON-NLS-1$

    /** Experiment setting "traceStopTime" */
    public final static String EXP_TRACE_STOP = "traceStopTime"; //$NON-NLS-1$

    /** Experiment setting "randomizeConcurrentEvents" */
    public final static String EXP_RAND_EVENTS = "randomizeConcurrentEvents"; //$NON-NLS-1$

    /** Experiment setting "formatter" */
    public final static String EXP_FORMATTER = "formatter"; //$NON-NLS-1$
    
    /** Experiment setting "reportOutputType" */
    public final static String EXP_R_OUTTYPE = "reportOutputType"; //$NON-NLS-1$

    /** Experiment setting "traceOutputType" */
    public final static String EXP_T_OUTTYPE = "traceOutputType"; //$NON-NLS-1$

    /** Experiment setting "errorOutputType" */
    public final static String EXP_E_OUTTYPE = "errorOutputType"; //$NON-NLS-1$

    /** Experiment setting "debugOutputType" */
    public final static String EXP_D_OUTTYPE = "debugOutputType"; //$NON-NLS-1$

}
