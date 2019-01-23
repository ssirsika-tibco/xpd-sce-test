/**
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.resources.util;

/**
 * ElapsedTimerHelper
 * 
 * @author aallway
 */
public class ElapsedTimerHelper {

    private static long lastTime = System.currentTimeMillis();

    private static long warningThreshold = 100;

    public static void initTiming(long warningThresholdMs) {
        lastTime = System.currentTimeMillis();
        warningThreshold = warningThresholdMs;
    }

    public static void timedMsg(String msg) {
    
    	// Comment this out temporarily if you want to enable you rtimings messages.
    	if (true) {
    		return;
    	}
    
        long newTime = System.currentTimeMillis();

        long elapsed = newTime - lastTime;

        String ret;
        
        if (warningThreshold <= 0 || warningThreshold > elapsed) {
            ret = msg + " @" //$NON-NLS-1$ 
                    + newTime + "  (elapsed: " //$NON-NLS-1$
                    + elapsed + " ms)"; //$NON-NLS-1$
        } else {
            ret = "**** " + msg + " @" //$NON-NLS-1$ //$NON-NLS-2$ 
                    + newTime + "  (elapsed: " //$NON-NLS-1$
                    + elapsed + " ms) **** EXCEEDED THRESHHOLD OF: "+warningThreshold; //$NON-NLS-1$

        }

        System.out.println(ret);
        
        lastTime = newTime;
        return ;
    }

}
