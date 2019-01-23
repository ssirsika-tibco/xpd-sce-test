package com.tibco.xpd.process.js.parser.util;

public class Consts {

    /**
     * SID: This constant exists here PURELY for use in explicitly loading JS
     * Script classes for iprocess so that the translator can explicitly create
     * class definition readers.
     */
    public static final String IPM_DEST_FOR_EXPLICIT_JSCLASS_SUPPORT_OR_TRANSLATION =
            "ipm10.x"; //$NON-NLS-1$

    public static final String SCRIPT_TASK = "ScriptTask"; //$NON-NLS-1$

    public static final String USER_TASK = "UserTask"; //$NON-NLS-1$

    public static final String TRANSITION = "Transition"; //$NON-NLS-1$

    public static final String TIMER_EVENT = "TimerEvent"; //$NON-NLS-1$

    public static final int maxDayOfMonth = 31;

    public static final int maxDayOfFebMonth = 29;

    public static final int maxYearSupported = 2999;

    public static final int maxHrsInDay = 23;

    public static final int maxMinutesInHour = 59;
}
