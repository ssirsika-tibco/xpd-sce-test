/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.bds.designtime.generator.internal;

import java.util.EnumMap;
import java.util.Stack;

/**
 * The base class for gathering performance metrics This class by default
 * supplies the metrics support when it is turned off. This means that calls can
 * still exist in the code and perform no operation
 * 
 */
public class PerfMetrics {

    // List of operation for performance figures
    public enum Operations {
        Generate, Bom2Xsd, GenerateBDS, CreateProject, StudioCreateProject, ECoreSave, ECoreForeignRefs, GlobalData, ECoreReadXSDs, Metafile, GenModelCreation, GenModelSave, GenModelGenerate, Templates, Total
    }

    // Records the offset since the operation was started
    protected Stack<Long> startOffset = new Stack<Long>();

    protected EnumMap<Operations, Long> metricsMap =
            new EnumMap<Operations, Long>(Operations.class);

    private final double scalerValue = 1000000000.0;

    /**
     * Calculates the duration based on the last offset time
     * 
     * @return
     */
    protected long calculateDuration() {
        return (System.nanoTime() - startOffset.pop());
    }

    /**
     * Works through each of the metrics and scales them to second granularity
     * 
     * @return Map of the metrics in second granularity
     */
    protected EnumMap<Operations, Double> scaleToSeconds() {
        EnumMap<Operations, Double> scaledMap =
                new EnumMap<Operations, Double>(Operations.class);

        // Make sure there is an entry for all of the metrics
        for (Operations opName : Operations.values()) {
            double scaledValue = 0.0;
            if (metricsMap.containsKey(opName)) {
                scaledValue = metricsMap.get(opName) / scalerValue;
            }
            scaledMap.put(opName, scaledValue);
        }

        return scaledMap;
    }

    /**
     * Marks the start of a given operation so that the duration can be
     * calculated
     */
    public void startOffset() {
        // Do nothing on the default, performance calculations are off
    }

    /**
     * Stores the given timing duration
     * 
     * @param operationCompleted
     *            The operation completed
     */
    public void setTiming(Operations operationCompleted) {
        // Do nothing on the default, performance calculations are off
    }

    /**
     * Starts the metrics collection operation
     */
    public void startMetrics() {
        // Do nothing on the default, performance calculations are off
    }

    /**
     * Ends the Metrics collection operation, printing the results
     */
    public void completeMetrics() {
        // Do nothing on the default, performance calculations are off
    }
}
