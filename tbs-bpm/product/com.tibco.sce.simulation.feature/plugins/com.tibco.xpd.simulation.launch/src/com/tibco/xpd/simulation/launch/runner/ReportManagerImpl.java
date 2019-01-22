/* 
 ** 
 **  MODULE:             $RCSfile: ReportManager.java $ 
 **                      $Revision: 1.0 $ 
 **                      $Date: 2005-12-13 $ 
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
package com.tibco.xpd.simulation.launch.runner;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import com.tibco.simulation.report.DocumentRoot;
import com.tibco.simulation.report.SimRepActivities;
import com.tibco.simulation.report.SimRepActivity;
import com.tibco.simulation.report.SimRepActivityQueue;
import com.tibco.simulation.report.SimRepCases;
import com.tibco.simulation.report.SimRepCost;
import com.tibco.simulation.report.SimRepDistribution;
import com.tibco.simulation.report.SimRepDistributionCategory;
import com.tibco.simulation.report.SimRepExperiment;
import com.tibco.simulation.report.SimRepExperimentData;
import com.tibco.simulation.report.SimRepExperimentState;
import com.tibco.simulation.report.SimRepFactory;
import com.tibco.simulation.report.SimRepParticipant;
import com.tibco.simulation.report.SimRepParticipants;
import com.tibco.simulation.report.SimRepReferenceTimeUnit;
import com.tibco.simulation.report.util.SimRepResourceFactoryImpl;
import com.tibco.xpd.simulation.launch.ExperimentParamConstants;
import com.tibco.xpd.simulation.launch.LaunchPlugin;
import com.tibco.xpd.simulation.launch.Messages;
import com.tibco.xpd.simulation.launch.ReportManager;
import com.tibco.xpd.simulation.launch.SimulationControler;
import com.tibco.xpd.simulation.model.ParameterBasedRealDistribution;
import com.tibco.xpd.simulation.model.WorkflowModel;
import com.tibco.xpd.simulation.model.WorkflowModel.SimModelActivity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.PackageHeader;

import desmoj.core.dist.Distribution;
import desmoj.core.dist.RealDist;
import desmoj.core.dist.RealDistConstant;
import desmoj.core.dist.RealDistExponential;
import desmoj.core.dist.RealDistNormal;
import desmoj.core.dist.RealDistUniform;
import desmoj.core.simulator.Queue;
import desmoj.core.simulator.SimTime;
import desmoj.core.simulator.Units;
import desmoj.core.util.ExperimentParameter;
import desmoj.core.util.SimRunEvent;

public class ReportManagerImpl implements ReportManager {

    private static final double UPDATE_DELTA_TIME = 0.5;

    private static final double UNDEFINED = -1.0D;

    private SimRepExperiment simRepExperiment;

    private final WorkflowModel model;

    private final ExperimentRunner experimentRunner;

    private double previousTime = 0.0D;

    private static final String iso8601Pattern = "yyyy-MM-dd'T'HH:mm:ssz"; //$NON-NLS-1$ 

    /**
     * Cache to quickly find references to Report Model participant by id. Keys
     * are participantIds (String) and values are SimulationReport model
     * participant references (SimRepParticipant).
     */
    private Map participantsCache;

    /**
     * Cache to quickly find references to Report Model activities by id. Keys
     * are activityIds (String) and values are SimulationReport model activities
     * references (SimRepActivity).
     */
    private Map activitiesCache;

    /** Used to parse real time string from simulation */
    private DateFormat timeConverterDateFormat;

    public ReportManagerImpl(ExperimentRunner experimentRunner) {
        this.experimentRunner = experimentRunner;
        this.model = (WorkflowModel) experimentRunner.getModel();
        participantsCache = new HashMap();
        activitiesCache = new HashMap();
        createSimRepExperiment(this.model);

        timeConverterDateFormat =
                new SimpleDateFormat("dd.MM.yyyy HH:mm:ss:SSS"); //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.simulation.ui.ReportManager#getSimRepExperiment()
     */
    @Override
    public SimRepExperiment getSimRepExperiment() {
        return simRepExperiment;
    }

    private static Package getPackage(EObject eObj) {
        while (eObj != null && !(eObj instanceof Package)) {
            eObj = eObj.eContainer();
        }

        if (eObj instanceof Package) {
            return (Package) eObj;
        }
        return null;
    }

    /**
     * @return
     */
    private String getPackageCostUnit(WorkflowModel model) {
        String costUnit = "USD"; //$NON-NLS-1$        
        Package xpdlPackage = getPackage(model.getWorkFlowProcess());
        PackageHeader packageHeader = xpdlPackage.getPackageHeader();
        if (packageHeader != null && packageHeader.getCostUnit() != null) {
            costUnit = packageHeader.getCostUnit().getValue();
        }
        return costUnit;
    }

    private void createSimRepExperiment(WorkflowModel model) {
        simRepExperiment = SimRepFactory.eINSTANCE.createSimRepExperiment();
        simRepExperiment.setId(model.getPackageId()
                + "_" + model.getProcessId()); //$NON-NLS-1$
        /*
         * XPD-4199: Saket- Replacing process name with process label.
         */
        simRepExperiment.setName(Messages.ReportManagerImpl_ExperimentFor
                + (model.getProcessLabel()));

        // ExperimentData
        SimRepExperimentData expData =
                SimRepFactory.eINSTANCE.createSimRepExperimentData();
        expData.setExperimentState(SimRepExperimentState.NOT_STARTED_LITERAL);
        SimTime currentTime = model.currentTime();
        expData.setSimulationTime(currentTime.getTimeValue());
        expData.setReferenceStartTime(model.getExperiment().getReferenceTime());
        expData.setRealTime(model.getExperiment().toTrueTime(currentTime));
        expData.setReferenceTimeUnit(SimRepReferenceTimeUnit.MINUTE_LITERAL);
        expData.setReferenceCostUnit(getPackageCostUnit(model));

        simRepExperiment.setExperimentData(expData);

        // Cases
        SimRepCases expCases = SimRepFactory.eINSTANCE.createSimRepCases();
        // intervalDistribution
        RealDist desmojIntervalDist =
                model.getCaseGenerationIntervalDistribution();
        if (desmojIntervalDist != null) {
            SimRepDistribution intervalDistribution =
                    getSimRepDistribution(desmojIntervalDist);
            expCases.setCaseStartIntervalDistribution(intervalDistribution);
        } else {
            expCases.setCaseStartIntervalDistribution(null);
        }
        expCases.setStartedCases(0);
        expCases.setFinishedCases(0);
        expCases.setMinCaseTime(0.0D);
        expCases.setMaxCaseTime(0.0D);
        expCases.setAverageCaseTime(0.0D);
        // Cases Cost
        expCases.setCaseCost(getSimRepCost());
        simRepExperiment.setCases(expCases);

        // Participants
        SimRepParticipants expParticipants =
                SimRepFactory.eINSTANCE.createSimRepParticipants();
        Map modelParticipants = model.getSimModelParticipants();
        for (Iterator iter = modelParticipants.values().iterator(); iter
                .hasNext();) {
            WorkflowModel.SimModelParticipant participant =
                    (WorkflowModel.SimModelParticipant) iter.next();
            String participantId = participant.getId();
            SimRepParticipant expParticipant =
                    SimRepFactory.eINSTANCE.createSimRepParticipant();
            expParticipant.setId(participantId);
            expParticipant.setName(participant.getName());
            expParticipant.setNoOfInstances(participant.getNoOfInstances());

            Queue idleQueue = participant.getIdleParticipantQueue();
            expParticipant.setIdleInstances(idleQueue.length());
            expParticipant.setAverageIdle(idleQueue.averageLength());
            expParticipant.setAverageIdleTime(idleQueue.averageWaitTime()
                    .getTimeValue());

            expParticipant.setCostOfWorkForCase(getSimRepCost());
            expParticipants.getParticipant().add(expParticipant);
            participantsCache.put(participantId, expParticipant);
        }
        simRepExperiment.setParticipants(expParticipants);

        // Activities
        SimRepActivities expActivities =
                SimRepFactory.eINSTANCE.createSimRepActivities();
        Map modelActivities = model.getSimModelActivities();
        for (Iterator iter = modelActivities.values().iterator(); iter
                .hasNext();) {
            WorkflowModel.SimModelActivity modelActivity =
                    (WorkflowModel.SimModelActivity) iter.next();
            SimRepActivity expActivity =
                    SimRepFactory.eINSTANCE.createSimRepActivity();
            expActivity.setId(modelActivity.getId());
            expActivity.setName(modelActivity.getName());

            expActivity
                    .setDurationDistribution(getSimRepDistribution(modelActivity
                            .getActivityDurationDistribution()));
            Queue activityQueue = modelActivity.getActivityQueue();
            expActivity.setActivityQueue(getSimRepActivityQueue(activityQueue));
            expActivity.setActivityCost(getSimRepCost());
            expActivity.setAverageDuration(getAverageDuration(modelActivity,
                    activityQueue));
            expActivities.getActivity().add(expActivity);
            activitiesCache.put(modelActivity.getId(), expActivity);
        }
        simRepExperiment.setActivities(expActivities);
    }

    private SimRepActivityQueue getSimRepActivityQueue(Queue desmojActivityQueue) {
        SimRepActivityQueue expActivityQueue =
                SimRepFactory.eINSTANCE.createSimRepActivityQueue();
        expActivityQueue.setObservedCases((int) desmojActivityQueue
                .getObservations());
        expActivityQueue.setCurrentSize(desmojActivityQueue.length());
        expActivityQueue.setMaxSize(desmojActivityQueue.maxLength());
        expActivityQueue.setAverageSize(desmojActivityQueue.averageLength());
        expActivityQueue.setAverageWait(desmojActivityQueue.averageWaitTime()
                .getTimeValue());
        return expActivityQueue;
    }

    private SimRepDistribution getSimRepDistribution(Object desmojDist) {
        SimRepDistribution intervalDistribution =
                SimRepFactory.eINSTANCE.createSimRepDistribution();
        if (desmojDist instanceof RealDist) {
            RealDist desmojRealDist = (RealDist) desmojDist;
            intervalDistribution
                    .setDistributionCategory(getSimRepDistributionCategory(desmojRealDist));
            intervalDistribution.setLastResetTime(desmojRealDist.resetAt()
                    .getTimeValue());
            intervalDistribution.setObservedElements(desmojRealDist
                    .getObservations());
            intervalDistribution
                    .setParameter1(getSimRepDistributionParameter(desmojRealDist,
                            1));
            intervalDistribution
                    .setParameter2(getSimRepDistributionParameter(desmojRealDist,
                            2));
            intervalDistribution.setSeed(desmojRealDist.getInitialSeed());
        } else if (desmojDist instanceof ParameterBasedRealDistribution) {
            intervalDistribution
                    .setDistributionCategory(SimRepDistributionCategory.PARAMETER_BASED_LITERAL);
            intervalDistribution.setLastResetTime(0.0D);
            intervalDistribution.setObservedElements(0L);
            intervalDistribution.setParameter1(0.0D);
            intervalDistribution.setParameter2(0.0D);
            intervalDistribution.setSeed(0L);
        } else {
            // TODO JA Add new value like N/A to SimRepDistributionCategory and
            // put it hiere.
            intervalDistribution
                    .setDistributionCategory(SimRepDistributionCategory.EMPIRICAL_LITERAL);
            intervalDistribution.setLastResetTime(0.0D);
            intervalDistribution.setObservedElements(0L);
            intervalDistribution.setParameter1(0.0D);
            intervalDistribution.setParameter2(0.0D);
            intervalDistribution.setSeed(0L);
        }
        return intervalDistribution;
    }

    private SimRepCost getSimRepCost() {
        SimRepCost caseCost = SimRepFactory.eINSTANCE.createSimRepCost();
        caseCost.setMinCost(0.0D);
        caseCost.setMaxCost(0.0D);
        caseCost.setAverageCost(0.0D);
        caseCost.setCumulativeAverageCost(0.0D);
        return caseCost;
    }

    private SimRepDistributionCategory getSimRepDistributionCategory(
            Distribution dist) {
        if (dist instanceof RealDistConstant) {
            return SimRepDistributionCategory.REAL_CONSTANT_LITERAL;
        } else if (dist instanceof RealDistUniform) {
            return SimRepDistributionCategory.REAL_UNIFORM_LITERAL;
        } else if (dist instanceof RealDistNormal) {
            return SimRepDistributionCategory.REAL_NORMAL_LITERAL;
        } else if (dist instanceof RealDistExponential) {
            return SimRepDistributionCategory.REAL_EXPONENTIAL_LITERAL;
        } else {
            throw new RuntimeException("Unsuported distribution!"); //$NON-NLS-1$
        }
    }

    /**
     * Gets value of specific distribution parameter
     * 
     * @param dist
     *            desmoj distribution
     * @param paramNo
     *            parameter number (1 or 2 are only allowed values)
     * @return
     */
    private double getSimRepDistributionParameter(Distribution dist, int paramNo) {
        if (paramNo != 1 && paramNo != 2) {
            throw new IllegalArgumentException(
                    "Only 1 or 2 is allowed for paramNo!"); //$NON-NLS-1$
        }
        if (dist instanceof RealDistConstant) {
            switch (paramNo) {
            case 1:
                return ((RealDistConstant) dist).getConstantValue();
            case 2:
                return 0.0D;
            }
        } else if (dist instanceof RealDistUniform) {
            switch (paramNo) {
            case 1:
                return ((RealDistUniform) dist).getLower();
            case 2:
                return ((RealDistUniform) dist).getUpper();
            }
        } else if (dist instanceof RealDistNormal) {
            switch (paramNo) {
            case 1:
                return ((RealDistNormal) dist).getMean();
            case 2:
                return ((RealDistNormal) dist).getStdDev();
            }
        } else if (dist instanceof RealDistExponential) {
            switch (paramNo) {
            case 1:
                return ((RealDistExponential) dist).getMean();
            case 2:
                return 0.0D;
            }
        }
        throw new RuntimeException(Messages.ReportManagerImpl_Distribution1);
    }

    private SimRepReferenceTimeUnit getSimRepReferenceTimeUnit(
            int desmojTimeUnit) {
        switch (desmojTimeUnit) {
        case Units.S:
            return SimRepReferenceTimeUnit.SECOND_LITERAL;
        case Units.MIN:
            return SimRepReferenceTimeUnit.MINUTE_LITERAL;
        case Units.H:
            return SimRepReferenceTimeUnit.HOUR_LITERAL;
        default:
            throw new IllegalArgumentException(
                    "Incorect desmojTimeUnit was used: " + desmojTimeUnit); //$NON-NLS-1$
        }
    }

    private SimRepExperimentState getSimRepExperimentState() {
        switch (experimentRunner.getStatus()) {
        case ExperimentRunner.CREATED:
        case ExperimentRunner.INITIALIZED:
            return SimRepExperimentState.NOT_STARTED_LITERAL;
        case ExperimentRunner.RUNNING:
            return SimRepExperimentState.RUNNING_LITERAL;
        case ExperimentRunner.PAUSED:
            return SimRepExperimentState.PAUSED_LITERAL;
        case ExperimentRunner.STOPPED:
            return SimRepExperimentState.FINISHED_LITERAL;
        default:
            throw new IllegalStateException("Incorect State"); //$NON-NLS-1$
        }
    }

    private void updateSimRepCost(SimRepCost repCost,
            WorkflowModel.SimModelCost simModelCost) {
        if (simModelCost != null && repCost != null) {
            repCost.setMinCost(simModelCost.getMinCost());
            repCost.setMaxCost(simModelCost.getMaxCost());
            repCost.setAverageCost(simModelCost.getAverageCost());
            repCost.setCumulativeAverageCost(simModelCost.getCumulativeCost());
        }
    }

    /**
     * No syync with UI thread is required here Model listeners should be
     * careful abour that. Example code to synchronize with UI thread:
     * 
     * <code>
     * Display display = PlatformUI.getWorkbench().getDisplay();
     *      display.syncExec(new Runnable() {
     *          public void run() {
     *              doUpdateReportExperiment();
     *              }
     *          });
     * </code>
     * 
     */
    private void updateReportExperiment() {
        Display display = PlatformUI.getWorkbench().getDisplay();
        if (display != null) {
            display.syncExec(new Runnable() {
                @Override
                public void run() {
                    doUpdateReportExperiment();
                }
            });
        } else {
            doUpdateReportExperiment();
        }
    }

    private void doUpdateReportExperiment() {
        simRepExperiment.setId("0"); //$NON-NLS-1$ //TODO reserved because can be used later.
        simRepExperiment.setName(model.getExperiment().getName());
        simRepExperiment.setPackageId(model.getPackageId());
        simRepExperiment.setPackageName(model.getPackageName());
        simRepExperiment.setProcessId(model.getProcessId());
        simRepExperiment.setProcessName(model.getProcessName());
        /*
         * XPD-4199: Saket- Need to update the process label as well since we
         * are replacing process name with process label in simulation control
         * view.
         */
        simRepExperiment.setProcessLabel(model.getProcessLabel());

        // ExperimentData

        SimRepExperimentData expData = simRepExperiment.getExperimentData();
        expData.setExperimentState(getSimRepExperimentState());
        SimTime currentTime = model.currentTime();
        expData.setSimulationTime(currentTime.getTimeValue());
        String referenceStartTime =
                parseAsDateString(model.getExperiment().getReferenceTime());
        expData.setReferenceStartTime(referenceStartTime);
        String realTime =
                parseAsDateString(model.getExperiment().toTrueTime(currentTime));
        expData.setRealTime(realTime);
        expData.setReferenceTimeUnit(getSimRepReferenceTimeUnit(model
                .getExperiment().getReferenceUnit()));
        expData.setReferenceCostUnit(getPackageCostUnit(model));

        WorkflowModel.SimModelCasesData casesData =
                model.getSimModelCasesData();
        SimRepCases expCases = simRepExperiment.getCases();
        expCases.setStartedCases(casesData.getStartedCases());
        expCases.setFinishedCases(casesData.getFinishedCases());
        expCases.setMinCaseTime(casesData.getMinCaseTime());
        expCases.setMaxCaseTime(casesData.getMaxCaseTime());
        double averageCaseTime = casesData.getAverageCaseTime();
        expCases.setAverageCaseTime((averageCaseTime == UNDEFINED) ? 0.0D
                : averageCaseTime); // Cases
        // Cost
        // TODO Calculate cost. At the moment cost is not calculated.
        updateSimRepCost(expCases.getCaseCost(), casesData.getCost());
        // expCases.setCaseCost(getSimRepCost());

        // Participants

        Map modelParticipants = model.getSimModelParticipants();
        for (Iterator iter = modelParticipants.values().iterator(); iter
                .hasNext();) {
            WorkflowModel.SimModelParticipant participant =
                    (WorkflowModel.SimModelParticipant) iter.next();
            String participantId = participant.getId();
            SimRepParticipant expParticipant =
                    (SimRepParticipant) participantsCache.get(participantId);
            if (expParticipant != null) {
                expParticipant.setId(participantId);
                expParticipant.setName(participant.getName());
                expParticipant.setNoOfInstances(participant.getNoOfInstances());

                Queue idleQueue = participant.getIdleParticipantQueue();
                expParticipant.setIdleInstances(idleQueue.length());
                expParticipant.setAverageIdle(idleQueue.averageLength());
                double workingTime =
                        model.getWorkingTime(participantId,
                                idleQueue.currentTime());
                double averageIdleTime =
                        participant.getNoOfInstances() == 0 ? 0 : workingTime
                                - (participant.getBusyTime() / participant
                                        .getNoOfInstances());
                // floating point calculation errors can make the idle time
                // slightly less than zero
                averageIdleTime = Math.floor(averageIdleTime * 10000) / 10000;
                expParticipant.setAverageIdleTime(averageIdleTime < 0 ? 0
                        : averageIdleTime);
                expParticipant.setCostOfWorkForCase(getSimRepCost());
                double averageBusyTime =
                        participant.getNoOfInstances() == 0 ? 0 : participant
                                .getBusyTime() / participant.getNoOfInstances();
                averageBusyTime = Math.floor(averageBusyTime * 10000) / 10000;
                expParticipant.setAverageBusyTime(averageBusyTime);
            }
        }

        // Activities
        Map modelActivities = model.getSimModelActivities();
        for (Iterator iter = modelActivities.values().iterator(); iter
                .hasNext();) {
            WorkflowModel.SimModelActivity modelActivity =
                    (WorkflowModel.SimModelActivity) iter.next();
            SimRepActivity expActivity =
                    (SimRepActivity) activitiesCache.get(modelActivity.getId());
            if (expActivity != null) {
                expActivity.setId(modelActivity.getId());
                expActivity.setName(modelActivity.getName());

                expActivity
                        .setDurationDistribution(getSimRepDistribution(modelActivity
                                .getActivityDurationDistribution()));
                Queue activityQueue = modelActivity.getActivityQueue();
                expActivity
                        .setActivityQueue(getSimRepActivityQueue(activityQueue));

                updateSimRepCost(expActivity.getActivityCost(),
                        modelActivity.getCost());
                expActivity
                        .setAverageDuration(getAverageDuration(modelActivity,
                                activityQueue));
            }
        }

    }

    private double getAverageDuration(SimModelActivity modelActivity,
            Queue activityQueue) {
        double average = 0;
        if (activityQueue.getObservations() != 0) {
            average =
                    (modelActivity.getTotalServiceTime() / activityQueue
                            .getObservations())
                            + activityQueue.averageWaitTime().getTimeValue();
        }
        return average;
    }

    /**
     * Localise the date from time converter so we display report in localised
     * format each time.
     * 
     * @param formattedTime
     * @return localised date string
     */
    private String parseAsDateString(String formattedTime) {
        String localisedDate =
                DateFormat.getDateTimeInstance(DateFormat.FULL,
                        DateFormat.MEDIUM).format(new Date());
        try {
            Date tempDate = timeConverterDateFormat.parse(formattedTime);

            SimpleDateFormat tempDateFormat = new SimpleDateFormat();
            tempDateFormat.applyPattern(iso8601Pattern);
            String iso8601Date = tempDateFormat.format(tempDate);

            SimpleDateFormat localisedDateFormat = new SimpleDateFormat();
            localisedDateFormat.applyPattern(iso8601Pattern);
            tempDate = localisedDateFormat.parse(iso8601Date);
            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);
            DateFormat timeFormat =
                    DateFormat.getTimeInstance(DateFormat.MEDIUM);
            localisedDate =
                    dateFormat.format(tempDate)
                            + " " + timeFormat.format(tempDate); //$NON-NLS-1$            

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return localisedDate;
    }

    @Override
    public void clockAdvanced(SimRunEvent e) {
        double currentTime = e.getSimTime().getTimeValue();
        double delta = currentTime - previousTime;
        if (delta > UPDATE_DELTA_TIME) {
            previousTime = currentTime;
            updateReportExperiment();
        }

    }

    @Override
    public void experimentRunning(SimRunEvent e) {
        updateReportExperiment();

    }

    @Override
    public void experimentStopped(SimRunEvent e) {
        updateReportExperiment();
        if (!e.getExperiment().isAborted()) {
            // updateAverageCaseCost();
            writeResultFile();
        }
    }

    @Override
    public void experimentPaused(SimRunEvent e) {
        updateReportExperiment();

    }

    @Override
    public int getTotalCaseCount() {
        if (model != null) {
            return model.getNoOfCases();
        }
        return 0;
    }

    @Override
    public URL getRepositoryLocation() {
        return model.getDefaultRepoLocation();
    }

    @Override
    public String getPackageFilePath() {
        return model.getPackageFilePath();
    }

    /**
     * TODO This should be done during the simulation, not summed at the end
     */
    /*
     * private void updateAverageCaseCost() { double average = 0; EList
     * activities = simRepExperiment.getActivities().getActivity(); for
     * (Iterator i = activities.iterator(); i.hasNext();) { SimRepActivity
     * activity = (SimRepActivity) i.next(); SimRepCost cost =
     * activity.getActivityCost(); average += cost.getAverageCost(); }
     * SimRepCost caseCost = simRepExperiment.getCases().getCaseCost();
     * caseCost.setAverageCost(average); }
     */

    private void writeResultFile() {
        // Save the simulation results
        SimulationControler controler = LaunchPlugin.getSimulationControler();
        IContainer simFolder = controler.getSimulationFolder();
        DocumentRoot root = SimRepFactory.eINSTANCE.createDocumentRoot();
        root.setExperiment(simRepExperiment);
        String packageName = simRepExperiment.getPackageName().trim();
        String processName = simRepExperiment.getProcessName().trim();
        ExperimentParameter pathParameter =
                (ExperimentParameter) experimentRunner.getExperimentSettings()
                        .get(ExperimentParamConstants.EXP_OUTPUT_PATH);
        String simFolderPath = (String) pathParameter.getValue();
        IPath resultsFolderPath =
                new Path(simFolderPath).append(packageName).append(processName);
        File resultsFolder = resultsFolderPath.toFile();
        SimpleDateFormat dateFormat =
                new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss"); //$NON-NLS-1$
        String timeStamp = dateFormat.format(new Date());
        File file = new File(resultsFolder, timeStamp + ".sim"); //$NON-NLS-1$
        try {
            resultsFolder.mkdirs();
            file.createNewFile();
            URI uri = URI.createFileURI(file.getPath());
            Resource resource =
                    new SimRepResourceFactoryImpl().createResource(uri);
            resource.getContents().add(root);
            resource.save(Collections.EMPTY_MAP);
            resource.getContents().remove(root);
            resource.unload();
            simFolder.refreshLocal(IResource.DEPTH_INFINITE, null);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CoreException e) {
            e.printStackTrace();
        }
    }
}
