/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.bds.designtime.generator.internal;

import java.util.EnumMap;

import com.tibco.bds.designtime.generator.Activator;

/**
 * This class deals with the performance information so that assessments can be
 * made with regards to how long given operations take.
 * 
 */
public class PerfMetricsEnabled extends PerfMetrics {

    private boolean printToSystemOut = false;

    public PerfMetricsEnabled(boolean printToConsole) {
        printToSystemOut = printToConsole;
    }

    /**
     * @see com.tibco.bds.designtime.generator.internal.PerfMetrics#setTiming(com.tibco.bds.designtime.generator.internal.PerfMetrics.Operations)
     * 
     * @param operationCompleted
     */
    @Override
    public void setTiming(Operations operationCompleted) {
        // Calculate this duration
        long totalReading = calculateDuration();
        // If there was already a reading add this one to it
        if (metricsMap.containsKey(operationCompleted)) {
            totalReading += metricsMap.get(operationCompleted);
        }

        metricsMap.put(operationCompleted, totalReading);
    }

    /**
     * Resets the metrics for a new start
     */
    @Override
    public void startMetrics() {
        metricsMap.clear();
        startOffset.clear();
        startOffset();
    }

    /**
     * Start sub timing offset
     */
    @Override
    public void startOffset() {
        startOffset.push(System.nanoTime());
    }

    /**
     * Marks the metrics as complete and logs the results
     */
    @Override
    public void completeMetrics() {
        if (metricsMap.isEmpty()) {
            return;
        }

        setTiming(Operations.Total);

        EnumMap<Operations, Double> metrics = scaleToSeconds();

        // *** Creating the Project ***
        double bdsProjectCreateTime =
                metrics.get(Operations.CreateProject)
                        - metrics.get(Operations.StudioCreateProject);

        String resultsProjectCreate =
                String
                        .format("createProject  : %.4f (Studio: %.4f, BDS: %.4f)\n",
                                metrics.get(Operations.CreateProject),
                                metrics.get(Operations.StudioCreateProject),
                                bdsProjectCreateTime);

        // *** Creating the ECore File ***
        double eCoreTime =
                metrics.get(Operations.GenerateBDS)
                        - metrics.get(Operations.GenModelCreation);
        double eCoreOtherTime =
                eCoreTime - metrics.get(Operations.ECoreSave)
                        - metrics.get(Operations.ECoreForeignRefs)
                        - metrics.get(Operations.ECoreReadXSDs)
                        - metrics.get(Operations.GlobalData);

        String resultsECore =
                String
                        .format("\t\tECore Creation      : %.4f\n"
                                + "\t\t\tEMF Save    : %.4f [%.1f%%]\n\t\t\tForeign Refs: %.4f [%.1f%%]\n"
                                + "\t\t\tGlobal Data : %.4f [%.1f%%]\n"
                                + "\t\t\tLoad XSDs   : %.4f [%.1f%%]\n\t\t\tOther       : %.4f [%.1f%%]\n",
                                eCoreTime,
                                metrics.get(Operations.ECoreSave),
                                (metrics.get(Operations.ECoreSave) / eCoreTime) * 100,
                                metrics.get(Operations.ECoreForeignRefs),
                                (metrics.get(Operations.ECoreForeignRefs) / eCoreTime) * 100,
                                metrics.get(Operations.GlobalData),
                                (metrics.get(Operations.GlobalData) / eCoreTime) * 100,
                                metrics.get(Operations.ECoreReadXSDs),
                                (metrics.get(Operations.ECoreReadXSDs) / eCoreTime) * 100,
                                eCoreOtherTime,
                                (eCoreOtherTime / eCoreTime) * 100);

        // *** Generating GenModel File ***
        double otherGenModel =
                metrics.get(Operations.GenModelCreation)
                        - metrics.get(Operations.GenModelSave)
                        - metrics.get(Operations.GenModelGenerate)
                        - metrics.get(Operations.Templates)
                        - metrics.get(Operations.Metafile);

        String resultsGenModel =
                String
                        .format("\t\tGenModel Creation   : %.4f\n"
                                + "\t\t\tEMF Save    : %.4f [%.1f%%]\n"
                                + "\t\t\tEMF Generate: %.4f [%.1f%%]\n\t\t\tTemplates   : %.4f [%.1f%%]\n"
                                + "\t\t\tMetafile    : %.4f [%.1f%%]\n\t\t\tOther       : %.4f [%.1f%%]\n",
                                metrics.get(Operations.GenModelCreation),
                                metrics.get(Operations.GenModelSave),
                                (metrics.get(Operations.GenModelSave) / metrics
                                        .get(Operations.GenModelCreation)) * 100,
                                metrics.get(Operations.GenModelGenerate),
                                (metrics.get(Operations.GenModelGenerate) / metrics
                                        .get(Operations.GenModelCreation)) * 100,
                                metrics.get(Operations.Templates),
                                (metrics.get(Operations.Templates) / metrics
                                        .get(Operations.GenModelCreation)) * 100,
                                metrics.get(Operations.Metafile),
                                (metrics.get(Operations.Metafile) /  metrics
                                        .get(Operations.GenModelCreation)) * 100,
                                otherGenModel,
                                (otherGenModel / metrics
                                        .get(Operations.GenModelCreation)) * 100);

        // The total BDS generation time from generate without the studio bits
        double totalBDSTime =
                metrics.get(Operations.Generate)
                        - metrics.get(Operations.Bom2Xsd)
                        + bdsProjectCreateTime;
        double totalStudioTime = metrics.get(Operations.Total) - totalBDSTime;
        double totalEMFTime =
                metrics.get(Operations.ECoreSave)
                        + metrics.get(Operations.ECoreReadXSDs)
                        + metrics.get(Operations.GenModelSave)
                        + metrics.get(Operations.GenModelGenerate);

        String summaryOutputString =
                String
                        .format("Total: %.4f (Studio: %.4f [%.1f%%], BDS: %.4f [%.1f%%], EMF: %.4f [%.1f%%])\n",
                                metrics.get(Operations.Total),
                                totalStudioTime,
                                (totalStudioTime / metrics
                                        .get(Operations.Total)) * 100,
                                totalBDSTime - totalEMFTime,
                                ((totalBDSTime - totalEMFTime) / metrics
                                        .get(Operations.Total)) * 100,
                                totalEMFTime,
                                (totalEMFTime / metrics.get(Operations.Total)) * 100);

        String resultsOutputString =
                String.format("** ModelGenerator Timings **\n"
                        + resultsProjectCreate + "Generate       : %.4f\n"
                        + "\tBom2Xsd (Studio)     : %.4f \n"
                        + "\tGenerate BDS Project: %.4f\n" + "%s%s%s",
                        metrics.get(Operations.Generate),
                        metrics.get(Operations.Bom2Xsd),
                        metrics.get(Operations.GenerateBDS),
                        resultsECore,
                        resultsGenModel,
                        summaryOutputString);

        Activator.getDefault().getLogger().debug(resultsOutputString);

        if (printToSystemOut) {
            System.out.println(resultsOutputString);
        }
    }
}
