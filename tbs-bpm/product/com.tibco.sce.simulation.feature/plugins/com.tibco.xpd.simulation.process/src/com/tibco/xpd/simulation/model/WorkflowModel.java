/* 
 ** 
 **  MODULE:             $RCSfile: WorkflowModel.java $ 
 **                      $Revision: 1.0 $ 
 **                      $Date: 2005-09-15 $ 
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
package com.tibco.xpd.simulation.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Factory;

import com.tibco.inteng.IntEngConsts;
import com.tibco.inteng.InteractionEngine;
import com.tibco.inteng.InteractionEngineFactory;
import com.tibco.inteng.InteractionRepository;
import com.tibco.inteng.InteractionRepositoryImpl;
import com.tibco.inteng.ProcessState;
import com.tibco.inteng.apps.AutomaticApplication;
import com.tibco.inteng.resources.DefaultLocationResources;
import com.tibco.inteng.resources.FileLocator;
import com.tibco.inteng.resources.ResourceLocator;
import com.tibco.inteng.resources.URLResources;
import com.tibco.inteng.security.SecurityPlugIn;
import com.tibco.inteng.xpdldata.XpdlData;
import com.tibco.xpd.scheduling.ISchedulingCalendar;
import com.tibco.xpd.scheduling.SchedulingPlugin;
import com.tibco.xpd.simulation.AbstractBasicDistribution;
import com.tibco.xpd.simulation.ActivitySimulationDataType;
import com.tibco.xpd.simulation.ConstantRealDistribution;
import com.tibco.xpd.simulation.ExponentialRealDistribution;
import com.tibco.xpd.simulation.ExpressionType;
import com.tibco.xpd.simulation.ExternalEmpiricalDistribution;
import com.tibco.xpd.simulation.LoopControlType;
import com.tibco.xpd.simulation.MaxElapseTimeStrategyType;
import com.tibco.xpd.simulation.MaxLoopCountStrategyType;
import com.tibco.xpd.simulation.Messages;
import com.tibco.xpd.simulation.NormalDistributionStrategyType;
import com.tibco.xpd.simulation.NormalRealDistribution;
import com.tibco.xpd.simulation.ParameterDependentDistribution;
import com.tibco.xpd.simulation.ParticipantSimulationDataType;
import com.tibco.xpd.simulation.SimulationProcessActivator;
import com.tibco.xpd.simulation.SimulationRealDistributionType;
import com.tibco.xpd.simulation.StartSimulationDataType;
import com.tibco.xpd.simulation.TimeUnitCostType;
import com.tibco.xpd.simulation.TransitionSimulationDataType;
import com.tibco.xpd.simulation.UniformRealDistribution;
import com.tibco.xpd.simulation.common.util.SimulationXpdlUtils;
import com.tibco.xpd.simulation.preprocess.CaseGenerator;
import com.tibco.xpd.simulation.preprocess.impl.RealDataCaseGeneratorImpl;
import com.tibco.xpd.simulation.realdata.Case;
import com.tibco.xpd.simulation.realdata.Cases;
import com.tibco.xpd.simulation.realdata.util.DateUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;
import com.tibco.xpd.xpdl2.util.XpdlSearchUtil;

import desmoj.core.dist.RealDist;
import desmoj.core.dist.RealDistConstant;
import desmoj.core.dist.RealDistExponential;
import desmoj.core.dist.RealDistNormal;
import desmoj.core.dist.RealDistUniform;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.Queue;
import desmoj.core.simulator.SimTime;
import desmoj.core.simulator.Units;
import desmoj.core.statistic.Count;
import desmoj.core.statistic.Tally;

public class WorkflowModel extends Model implements PropertyChangeListener {

    public static final String STARTED_CASES_PROPERTY = "statedCases"; //$NON-NLS-1$

    public static final String FINISHED_CASES_PROPERTY = "finishedCases"; //$NON-NLS-1$

    public static final String ACTIVITY_CURRENT_LOOP_COUNT =
            "Activity_CurrentLoopCount_"; //$NON-NLS-1$

    public static final String ACTIVITY_MAX_LOOP_COUNT =
            "Activity_MaxLoopCount_"; //$NON-NLS-1$

    public static final String ACTIVITY_MAX_ELAPSE_TIME =
            "Activity_MaxElapseTime_"; //$NON-NLS-1$

    public interface SimModelCasesData {
        int getNoOfCases();

        int getStartedCases();

        int getFinishedCases();

        double getAverageCaseTime();

        double getMinCaseTime();

        double getMaxCaseTime();

        SimModelCost getCost();

        void addCost(double value);
    }

    public interface SimModelParticipant {
        String getId();

        String getName();

        int getNoOfInstances();

        Queue getIdleParticipantQueue();

        double getCostPerTimeUnit();

        SimModelCost getCost();

        double getBusyTime();

        void addCost(double value);

        void addBusyTime(double serviceTimeForActivity);

        Queue getNonWorkingParticipantQueue();
    }

    public interface SimModelActivity {
        String getId();

        String getName();

        Queue getActivityQueue();

        String getPerformarId();

        Object getActivityDurationDistribution();

        SimModelCost getCost();

        double getTotalServiceTime();

        void addCost(double value);

        void addServiceTime(double serviceTimeForActivity);
    }

    public interface SimModelCost {
        double getAverageCost();

        double getMinCost();

        double getMaxCost();

        double getCumulativeCost();
    }

    public interface LoopCountStrategy {
        String getParameterId();

        String getParameterValueToSet();

        String getDecisionActivityId();

        String getToActivityId();

    }

    public interface MaxLoopCountStrategy extends LoopCountStrategy {
        int getMaxLoopCount();
    }

    public interface NormalDistLoopCountStrategy extends LoopCountStrategy {
        int getLoopCountValue();
    }

    public interface MaxElapsedTimeStrategy extends LoopCountStrategy {
        /**
         * This method returns the max elapsed time in minutes
         * 
         * @return
         */
        double getMaxElapsedTime();
    }

    private abstract class LoopCountStrategyImpl implements LoopCountStrategy {
        protected Activity activity;

        private TransitionSimulationDataType simTransition;

        public LoopCountStrategyImpl(Activity activity) {
            this.activity = activity;
        }

        @Override
        public String getParameterId() {
            return getTransition().getStructuredCondition().getParameterId();
        }

        @Override
        public String getParameterValueToSet() {
            return getTransition().getStructuredCondition().getParameterValue();
        }

        private TransitionSimulationDataType getTransition() {
            if (simTransition == null) {
                Process process = activity.getProcess();
                String decisionActivityId = getDecisionActivityId();
                Activity decisionActivity =
                        SimulationXpdlUtils.getActivity(process,
                                decisionActivityId);
                String toActivityId = getToActivityId();
                Transition[] outgoingTransitions =
                        SimulationXpdlUtils.getOutgoingTransitions(process,
                                decisionActivity);
                for (int index = 0; index < outgoingTransitions.length; index++) {
                    Transition eachTransition = outgoingTransitions[index];
                    if (eachTransition.getTo().equals(toActivityId)) {
                        simTransition =
                                SimulationXpdlUtils
                                        .getTransitionSimulationData(eachTransition);
                        break;
                    }
                }

            }
            return simTransition;
        }
    }

    private class MaxLoopCountStrategyImpl extends LoopCountStrategyImpl
            implements MaxLoopCountStrategy {

        private int maxLoopCount = Integer.MIN_VALUE;

        private MaxLoopCountStrategyType maxLoopCountStrategy;

        public MaxLoopCountStrategyImpl(Activity activity) {
            super(activity);
        }

        @Override
        public int getMaxLoopCount() {
            if (maxLoopCount == Integer.MIN_VALUE) {
                MaxLoopCountStrategyType maxLoopCountStrategy =
                        getMaxLoopCountStrategy();
                maxLoopCount = maxLoopCountStrategy.getMaxLoopCount();
            }
            return maxLoopCount;
        }

        @Override
        public String getDecisionActivityId() {
            MaxLoopCountStrategyType maxLoopCountStrategy =
                    getMaxLoopCountStrategy();
            return maxLoopCountStrategy.getDecisionActivity();
        }

        @Override
        public String getToActivityId() {
            MaxLoopCountStrategyType maxLoopCountStrategy =
                    getMaxLoopCountStrategy();
            return maxLoopCountStrategy.getToActivity();
        }

        private MaxLoopCountStrategyType getMaxLoopCountStrategy() {
            if (maxLoopCountStrategy == null) {
                ActivitySimulationDataType activitySimulationData =
                        SimulationXpdlUtils.getActivitySimulationData(activity);
                LoopControlType loopControl =
                        activitySimulationData.getLoopControl();
                maxLoopCountStrategy = loopControl.getMaxLoopCountStrategy();
            }
            return maxLoopCountStrategy;
        }
    }

    private class NormalDistLoopCountStrategyImpl extends LoopCountStrategyImpl
            implements NormalDistLoopCountStrategy {

        private final String NORMAL_DIST_LOOP_STRATEGY =
                Messages.WorkflowModel_Normal;

        private NormalDistributionStrategyType normalDistLoopStrategy;

        private RealDistNormal realDistNormal = null;

        public NormalDistLoopCountStrategyImpl(Activity activity) {
            super(activity);
            createRealDistribution();
        }

        private void createRealDistribution() {
            NormalDistributionStrategyType normalDistLoopCountStrategy =
                    getNormalDistributionStrategy();
            double mean = normalDistLoopCountStrategy.getMean();
            double stdDeviation =
                    normalDistLoopCountStrategy.getStandardDeviation();
            realDistNormal =
                    new RealDistNormal(WorkflowModel.this,
                            NORMAL_DIST_LOOP_STRATEGY + activity.getName(), //$NON-NLS-1$
                            mean, stdDeviation, false, false);
            realDistNormal.setNonNegative(true);
        }

        @Override
        public int getLoopCountValue() {
            return (int) realDistNormal.sample();
        }

        @Override
        public String getDecisionActivityId() {
            NormalDistributionStrategyType normalDistributionStrategy =
                    getNormalDistributionStrategy();
            return normalDistributionStrategy.getDecisionActivity();
        }

        @Override
        public String getToActivityId() {
            NormalDistributionStrategyType normalDistributionStrategy =
                    getNormalDistributionStrategy();
            return normalDistributionStrategy.getToActivity();
        }

        private NormalDistributionStrategyType getNormalDistributionStrategy() {
            if (normalDistLoopStrategy == null) {
                ActivitySimulationDataType activitySimulationData =
                        SimulationXpdlUtils.getActivitySimulationData(activity);
                LoopControlType loopControl =
                        activitySimulationData.getLoopControl();
                normalDistLoopStrategy =
                        loopControl.getNormalDistributionStrategy();
            }
            return normalDistLoopStrategy;
        }
    }

    private class MaxElapsedTimeStrategyImpl extends LoopCountStrategyImpl
            implements MaxElapsedTimeStrategy {

        private MaxElapseTimeStrategyType maxElapsedTimeStrategy;

        public MaxElapsedTimeStrategyImpl(Activity activity) {
            super(activity);
        }

        @Override
        public String getDecisionActivityId() {
            MaxElapseTimeStrategyType maxElapseTimeStrategy =
                    getMaxElapseTimeStrategy();
            return maxElapseTimeStrategy.getDecisionActivity();
        }

        @Override
        public String getToActivityId() {
            MaxElapseTimeStrategyType maxElapseTimeStrategy =
                    getMaxElapseTimeStrategy();
            return maxElapseTimeStrategy.getToActivity();
        }

        private MaxElapseTimeStrategyType getMaxElapseTimeStrategy() {
            if (maxElapsedTimeStrategy == null) {
                ActivitySimulationDataType activitySimulationData =
                        SimulationXpdlUtils.getActivitySimulationData(activity);
                LoopControlType loopControl =
                        activitySimulationData.getLoopControl();
                maxElapsedTimeStrategy = loopControl.getMaxElapseTimeStrategy();
            }
            return maxElapsedTimeStrategy;
        }

        @Override
        public double getMaxElapsedTime() {
            MaxElapseTimeStrategyType maxElapseTimeStrategy =
                    getMaxElapseTimeStrategy();
            double maxElapseTime = maxElapseTimeStrategy.getMaxElapseTime();
            return maxElapseTime;
        }
    }

    private class SimModelCasesDataImpl implements SimModelCasesData {
        private Tally caseDurationTimeStatistic;

        private SimModelCostImpl cost;

        public SimModelCasesDataImpl() {
            caseDurationTimeStatistic =
                    new Tally(WorkflowModel.this,
                            "Case duration times", false, false); //$NON-NLS-1$
            caseDurationTimeStatistic.reset();
            cost = new SimModelCostImpl();
        }

        @Override
        public int getNoOfCases() {
            return getNoOfCases();
        }

        @Override
        public int getStartedCases() {
            return (int) startedCasesCount.getValue();
        }

        @Override
        public int getFinishedCases() {
            return (int) finishedCasesCount.getValue();
        }

        @Override
        public double getAverageCaseTime() {
            return caseDurationTimeStatistic.getMean();
        }

        @Override
        public double getMinCaseTime() {
            return caseDurationTimeStatistic.getMinimum();
        }

        @Override
        public double getMaxCaseTime() {
            return caseDurationTimeStatistic.getMaximum();
        }

        public void addCaseDurationTime(double caseDurationTime) {
            caseDurationTimeStatistic.update(caseDurationTime);
        }

        @Override
        public SimModelCost getCost() {
            return cost;
        }

        @Override
        public void addCost(double value) {
            cost.addCostValue(value);
        }

    }

    private class SimModelParticipantImpl implements SimModelParticipant {
        private String id;

        private String name;

        private int noOfInstances;

        private Queue idleParticipantQueue;

        private Queue nonWorkingParticipantQueue;

        private double costPerTimeUnit;

        private SimModelCostImpl cost;

        private double busyTime;

        public SimModelParticipantImpl(String id, String name,
                int noOfInstances, Queue idleParticipantQueue,
                Queue nonWorkingParticipantQueue) {
            this.id = id;
            this.name = name;
            this.noOfInstances = noOfInstances;
            this.idleParticipantQueue = idleParticipantQueue;
            this.nonWorkingParticipantQueue = nonWorkingParticipantQueue;
            cost = new SimModelCostImpl();
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public int getNoOfInstances() {
            return noOfInstances;
        }

        @Override
        public Queue getIdleParticipantQueue() {
            return idleParticipantQueue;
        }

        @Override
        public Queue getNonWorkingParticipantQueue() {
            return nonWorkingParticipantQueue;
        }

        @Override
        public double getCostPerTimeUnit() {
            return costPerTimeUnit;
        }

        public void setCostPerTimeUnit(double costPerTimeUnit) {
            this.costPerTimeUnit = costPerTimeUnit;
        }

        @Override
        public SimModelCost getCost() {
            return cost;
        }

        @Override
        public double getBusyTime() {
            return busyTime;
        }

        @Override
        public void addCost(double value) {
            cost.addCostValue(value);
        }

        @Override
        public void addBusyTime(double serviceTimeForActivity) {
            busyTime += serviceTimeForActivity;
        }
    }

    public class SimModelActivityImpl implements SimModelActivity {

        private String id;

        private String name;

        private String performerId;

        private Object activityDurationDisstribution;

        private SimModelCostImpl cost;

        private double totalServiceTime;

        public SimModelActivityImpl(String id, String name, String performerId) {
            this.id = id;
            this.name = name;
            this.performerId = performerId;
            cost = new SimModelCostImpl();
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public Queue getActivityQueue() {
            if (taskActivityThreadQueue != null) {
                return taskActivityThreadQueue.getActivityQueue(id);
            }
            return null;
        }

        @Override
        public String getPerformarId() {
            return performerId;
        }

        @Override
        public Object getActivityDurationDistribution() {
            return activityDurationDisstribution;
        }

        @Override
        public SimModelCost getCost() {
            return cost;
        }

        @Override
        public double getTotalServiceTime() {
            return totalServiceTime;
        }

        @Override
        public void addCost(double value) {
            cost.addCostValue(value);
        }

        @Override
        public void addServiceTime(double serviceTimeForActivity) {
            totalServiceTime += serviceTimeForActivity;
        }
    }

    private class SimModelCostImpl implements SimModelCost {

        private Tally costStatistic;

        private double cumulativeCost;

        public SimModelCostImpl() {
            costStatistic =
                    new Tally(WorkflowModel.this,
                            "Case duration times", false, false) {//$NON-NLS-1$
                        @Override
                        public void reset() {
                            super.reset();
                            cumulativeCost = 0.0D;
                        }
                    };
            costStatistic.reset();
        }

        @Override
        public double getAverageCost() {
            return costStatistic.getMean();
        }

        @Override
        public double getMinCost() {
            return costStatistic.getMinimum();
        }

        @Override
        public double getMaxCost() {
            return costStatistic.getMaximum();
        }

        @Override
        public double getCumulativeCost() {
            return cumulativeCost;
        }

        private void addCostValue(double value) {
            costStatistic.update(value);
            cumulativeCost += value;
        }
    }

    private static class CaseGenerationData {
        private final RealDist intervalDistribution;

        private int noOfCases;

        public CaseGenerationData(RealDist intervalDistribution, int noOfCases) {
            this.intervalDistribution = intervalDistribution;
            this.noOfCases = noOfCases;
        }

        public RealDist getIntervalDistribution() {
            return intervalDistribution;
        }

        public int getNoOfCases() {
            return noOfCases;
        }
    }

    private static class RealDataCaseGenerationData {

        private final Date startDate;

        private final int noOfCases;

        private Cases cases;

        private final String reference;

        private final CaseGenerator caseGenerator;

        public RealDataCaseGenerationData(Process wProcess, String reference,
                int requestedNoOfCases) {
            this.reference = reference;

            if (reference != null) {
                cases = getRealDataModel(this.reference);
            }

            // evaluate no of cases
            int fileNoOfCases = 0;
            if (cases != null) {
                fileNoOfCases = this.cases.getCase().size();
            }
            if (requestedNoOfCases <= 0) {
                noOfCases = fileNoOfCases;
            } else {
                noOfCases = Math.min(requestedNoOfCases, fileNoOfCases);
            }

            // evaluate start date
            if (cases != null && cases.getCase().size() > 0) {
                startDate =
                        DateUtil.parseDate(((Case) cases.getCase().get(0))
                                .getStartTime().toString());
            } else {
                startDate = null;
            }

            caseGenerator =
                    new RealDataCaseGeneratorImpl(wProcess, cases, noOfCases);
        }

        public Date getStartTime() {
            return startDate;
        }

        public String getReference() {
            return reference;
        }

        public int getNoOfCases() {
            return noOfCases;
        }

        public CaseGenerator getCaseGenerator() {
            return caseGenerator;
        };

        private Cases getRealDataModel(String filePath) {
            // Read realdata file into EMF model
            IWorkspaceRoot workspaceRoot =
                    ResourcesPlugin.getWorkspace().getRoot();
            IFile f = workspaceRoot.getFile(new Path(filePath));
            if (f == null || !f.exists()) {
                System.err.println(""); //$NON-NLS-1$
                return null;
            }
            String realDataPath = f.getLocation().toString();
            Cases cases = getCasesScenario(realDataPath);
            return cases;
        }

        private static Resource getResource(String filePath) {
            Resource result = null;
            URI uri = URI.createFileURI(filePath);
            Factory fact = Resource.Factory.Registry.INSTANCE.getFactory(uri);
            result = fact.createResource(uri);
            try {
                result.load(null);
            } catch (IOException e) {
                result = null;
            }
            return result;
        }

        private static Cases getCasesScenario(String filePath) {
            Resource resource = getResource(filePath);

            if (resource != null
                    && !resource.getContents().isEmpty()
                    && resource.getContents().get(0) instanceof com.tibco.xpd.simulation.realdata.DocumentRoot) {
                Cases cases =
                        ((com.tibco.xpd.simulation.realdata.DocumentRoot) resource
                                .getContents().get(0)).getCases();
                return cases;
            } else {
                return null;
            }
        }
    }

    /**
     * Map with all participants. Keys are participantIds (String) and values
     * are (SimModelParticipant) model participant objects.
     */
    private Map simModelParticipants;

    /**
     * Map with all activities. Keys are activityIds (String) and values are
     * (SimModelActivity) model activity objects.
     */
    private Map simModelActivities;

    /**
     * This Map will contain the maxLoopCount value for each Task Activity, if
     * it was specified.
     */
    private Map actLoopControl;

    /** Cases data information */
    private SimModelCasesDataImpl simModelCasesData;

    private PropertyChangeSupport propChangeSupport =
            new PropertyChangeSupport(this);

    /**
     * EMF XPDL package (inc. extensions).
     */
    private Package xpdlPackage;

    /** EMF process which will be simulated. */
    private Process process;

    /** Interaction Engine repository */
    private InteractionRepository repository;

    /**
     * Distributions of time intervals between case generation. Keys are the
     * activityIds (java.lang.String) of all start events and values are
     * distributions (RealDist).
     */
    private Map caseGenerationDistributionTimes;

    /** Counter for started cases */
    private Count startedCasesCount;

    /** Counter for finished cases */
    private Count finishedCasesCount;

    /**
     * Distributions of activity execution times. Keys are the activityIds
     * (java.lang.String) and values are distributions (RealDist).
     */
    private Map activityExecutionTimes;

    private TaskActivityThreadQueue taskActivityThreadQueue;

    /** Map of participantId to ISchedulingCalendar. */
    private Map participantCalendar;

    /**
     * Maps participant to activity. The keys are activities (type Acitivity)
     * and values are performer (type String)
     */
    private Map activityParticipantMap;

    private final URL defaultRepoLocation;

    /** Id of the start event */
    private String startEventId;

    private String processId;

    private String processName;

    /*
     * XPD-4199: Saket- Need a processLabel attribute to store the
     * "Display name"/"Label" of a process.
     */
    private String processLabel;

    private String packageId;

    private String packageName;

    private String packageFilePath;

    private boolean hasActivities;

    /** The real start time as specified on the controller. */
    private Date realStartTime;

    private double timeUnitConversionFactor;

    public WorkflowModel(Model owner, String name, boolean showInReport,
            boolean showIntrace, URL defaultRepoLocation, String xpdlPath,
            Process process) {

        super(owner, name, showInReport, showIntrace);
        this.process = process;
        this.xpdlPackage = XpdlSearchUtil.getXPDLPackage(process);
        if (xpdlPackage == null) {
            throw new RuntimeException("Process is not part of the package!"); //$NON-NLS-1$
        }
        this.packageName = xpdlPackage.getName();
        this.defaultRepoLocation = defaultRepoLocation;

        this.processId = process.getId();
        this.processName = process.getName();
        /*
         * XPD-4199: Saket- Fetch the process label/display name and store it
         * locally.
         */
        this.processLabel = Xpdl2ModelUtil.getDisplayNameOrName(process);
        this.packageFilePath = xpdlPath;
        /*
         * Resource resource = xpdlPackage.eResource(); if (resource != null) {
         * this.packageFileName = resource.getURI().lastSegment(); } else {
         * this.packageFileName = packageId + ".xpdl"; }
         */
        try {
            setupRepository(defaultRepoLocation.toString());
        } catch (MalformedURLException e) {
            throw new RuntimeException("MalformedURLException was thrown: " + e); //$NON-NLS-1$
        }
        installAutomaticApplications();
    }

    // default visibility to permit testing
    private void installAutomaticApplications() {
        // install automatic applications
        // TODO get all types of automatic applications from xpdl and install
        // this
        // AutomaticSimApplicationDispatcher for every type.
        AutomaticApplication simulationApplication =
                new AutomaticSimApplicationDispatcher();
        repository.installAutomaticApplication("ScriptApplication", //$NON-NLS-1$
                simulationApplication);

    }

    @Override
    public String description() {
        return Messages.WorkflowModel_Description;
    }

    /**
     * @see desmoj.core.simulator.Model#init()
     */
    @Override
    public void init() {

        try {
            setHasActivities();
            createParticipants();
            createActivityParticipatMap();
            createActivityExecutionTimes();
            storeLoopControl();
            startedCasesCount =
                    new Count(this, Messages.WorkflowModel_StartedCases_label,
                            true, false);
            finishedCasesCount =
                    new Count(this, Messages.WorkflowModel_FinishedCases_label,
                            true, false);
            simModelCasesData = new SimModelCasesDataImpl();
        } catch (Exception e) {
            SimulationProcessActivator.getLogger().error(e,
                    "Exception was thrown while simulation initialization."); //$NON-NLS-1$
        }
    }

    private void storeLoopControl() {
        actLoopControl = new HashMap();
        for (Iterator iter = process.getActivities().iterator(); iter.hasNext();) {
            Activity activity = (Activity) iter.next();
            Implementation implementation = activity.getImplementation();
            if (implementation instanceof Task
                    || implementation instanceof SubFlow) {
                ActivitySimulationDataType activitySimulationData =
                        SimulationXpdlUtils.getActivitySimulationData(activity);
                LoopControlType loopControl =
                        activitySimulationData.getLoopControl();
                if (loopControl != null) {
                    MaxLoopCountStrategyType maxLoopCountStrategy =
                            loopControl.getMaxLoopCountStrategy();
                    if (maxLoopCountStrategy != null) {
                        actLoopControl.put(activity.getId(),
                                new MaxLoopCountStrategyImpl(activity));
                    }
                    NormalDistributionStrategyType normalDistStrategy =
                            loopControl.getNormalDistributionStrategy();
                    if (normalDistStrategy != null) {
                        actLoopControl.put(activity.getId(),
                                new NormalDistLoopCountStrategyImpl(activity));
                    }
                    MaxElapseTimeStrategyType maxElapseTimeStrategy =
                            loopControl.getMaxElapseTimeStrategy();
                    if (maxElapseTimeStrategy != null) {
                        actLoopControl.put(activity.getId(),
                                new MaxElapsedTimeStrategyImpl(activity));
                    }
                }
            }
        }

    }

    private void createActivityExecutionTimes() {
        activityExecutionTimes = new HashMap();
        caseGenerationDistributionTimes = new HashMap();
        for (Iterator iter = process.getActivities().iterator(); iter.hasNext();) {
            Activity activity = (Activity) iter.next();
            createDurationDistributionForActivity(activity);
        }
        for (Iterator iterator = process.getActivitySets().iterator(); iterator
                .hasNext();) {
            for (Iterator iter =
                    ((ActivitySet) iterator.next()).getActivities().iterator(); iter
                    .hasNext();) {
                Activity activity = (Activity) iter.next();
                createDurationDistributionForActivity(activity);
            }
        }
    }

    private void setHasActivities() {
        hasActivities = false;
        for (Iterator iter = process.getActivities().iterator(); iter.hasNext();) {
            hasActivities = true;
            return;
        }
        for (Iterator iterator = process.getActivitySets().iterator(); iterator
                .hasNext();) {
            for (Iterator iter =
                    ((ActivitySet) iterator.next()).getActivities().iterator(); iter
                    .hasNext();) {
                hasActivities = true;
                return;
            }
        }
    }

    private void createDurationDistributionForActivity(Activity activity) {
        if (activity == null) {
            return;
        }
        Event event = activity.getEvent();
        if (event instanceof StartEvent) {
            // generate start event distribution.
            createCaseGenerationDistributionForActivity(activity);
            return;
        }
        Implementation implementation = activity.getImplementation();
        if (!(implementation instanceof Task)
                && !(implementation instanceof SubFlow)) {
            // create simulation data only for task activities.
            return;
        }

        ActivitySimulationDataType actSimData =
                SimulationXpdlUtils.getActivitySimulationData(activity);
        if (actSimData == null) {
            String message = "No simulation data for activity! " + "[id: " //$NON-NLS-1$ //$NON-NLS-2$
                    + activity.getId() + ", name: " + activity.getName() //$NON-NLS-1$
                    + ", description: " + activity.getDescription() + "]"; //$NON-NLS-1$ //$NON-NLS-2$
            throw new RuntimeException(message);
        }

        Object dist =
                getRealDistribution(actSimData.getDuration(),
                        activity,
                        "ActivityDurationStream ", true, false); //$NON-NLS-1$
        activityExecutionTimes.put(activity.getId(), dist);
        ((SimModelActivityImpl) simModelActivities.get(activity.getId())).activityDurationDisstribution =
                dist;

    }

    private Object getRealDistribution(SimulationRealDistributionType duration,
            Activity activity, String namePrefix, boolean showInReport,
            boolean showInTrace) {

        String activityName =
                SimulationXpdlUtils.getActivityDisplayName(activity);

        // parameter based distribution
        if (duration.getParameterBasedDistribution() != null) {
            ParameterBasedRealDistribution paramDist =
                    new ParameterBasedRealDistribution();
            List paramDependentDistList =
                    duration.getParameterBasedDistribution()
                            .getParameterDependentDistributions();
            for (Iterator iter = paramDependentDistList.iterator(); iter
                    .hasNext();) {
                ParameterDependentDistribution paramDependentDist =
                        (ParameterDependentDistribution) iter.next();
                RealDist realDesmoDist =
                        getRealBasicDistribution(paramDependentDist.getBasicDistribution(),
                                activity,
                                namePrefix,
                                showInReport,
                                showInTrace);
                ExpressionType expression = paramDependentDist.getExpression();
                if (expression.getEnumBasedExpression() != null) {
                    String paramId =
                            expression.getEnumBasedExpression().getParamName()
                                    .toString();
                    Object enumValue =
                            expression.getEnumBasedExpression().getEnumValue();
                    if (paramDist.getParameterId() == null) {
                        paramDist.setParameterId(paramId);
                    }
                    if (!paramDist.getParameterId().equals(paramId)) {
                        throw new RuntimeException(
                                "Many parameters used in ParameterBasedDistribution for activity: " //$NON-NLS-1$
                                        + activityName);
                    }
                    paramDist.putValue(enumValue, realDesmoDist);
                } else if (expression.getDefault() != null) {
                    paramDist.setDefaultDistribution(realDesmoDist);
                } else {
                    // ignored other types of expressions
                }
            }
            return paramDist;
        }

        // basic distribution
        AbstractBasicDistribution distribution =
                duration.getBasicDistribution();
        return getRealBasicDistribution(distribution,
                activity,
                namePrefix,
                showInReport,
                showInTrace);
    }

    private RealDist getRealBasicDistribution(
            AbstractBasicDistribution distribution, Activity activity,
            String namePrefix, boolean showInReport, boolean showInTrace) {

        String activityName =
                SimulationXpdlUtils.getActivityDisplayName(activity);
        if (distribution instanceof ConstantRealDistribution) {
            ConstantRealDistribution constDist =
                    (ConstantRealDistribution) distribution;
            double constantValue = constDist.getConstantValue();
            if (constantValue >= 0.0) {
                return new RealDistConstant(this, namePrefix + activityName, //$NON-NLS-1$
                        constantValue, showInReport, showInTrace);
            } else {
                String message = "Invalid simulation data for activity/event: " //$NON-NLS-1$
                        + activityName;
                message += "\nValue must not be negative."; //$NON-NLS-1$
                throw new RuntimeException(message);
            }

        }
        if (distribution instanceof UniformRealDistribution) {
            UniformRealDistribution uniformDist =
                    (UniformRealDistribution) distribution;
            double lowerBorder = uniformDist.getLowerBorder();
            double upperBorder = uniformDist.getUpperBorder();
            if (lowerBorder >= 0.0 && upperBorder >= 0.0
                    && upperBorder >= lowerBorder) {
                return new RealDistUniform(this, namePrefix + activityName, //$NON-NLS-1$
                        lowerBorder, upperBorder, showInReport, showInTrace);
            } else {
                String message = "Invalid simulation data for activity/event: " //$NON-NLS-1$
                        + activityName;
                message +=
                        "\nUpper and lower borders nust not be negative and uppes border must be greater or equal lower."; //$NON-NLS-1$
                throw new RuntimeException(message);
            }
        }
        if (distribution instanceof NormalRealDistribution) {
            NormalRealDistribution normalDist =
                    (NormalRealDistribution) distribution;
            double mean = normalDist.getMean();
            double standardDeviation = normalDist.getStandardDeviation();
            if (mean >= 0 && standardDeviation >= 0) {
                RealDistNormal realDistNormal =
                        new RealDistNormal(this,
                                namePrefix + activityName, //$NON-NLS-1$
                                mean, standardDeviation, showInReport,
                                showInTrace);
                realDistNormal.setNonNegative(true);
                return realDistNormal;
            } else {
                String message = "Invalid simulation data for activity/event: " //$NON-NLS-1$
                        + activityName;
                message +=
                        "\nMean and standard deviation must not be negative."; //$NON-NLS-1$
                throw new RuntimeException(message);
            }

        }
        if (distribution instanceof ExponentialRealDistribution) {
            ExponentialRealDistribution exponentialDist =
                    (ExponentialRealDistribution) distribution;
            double mean = exponentialDist.getMean();
            if (mean >= 0) {
                RealDistExponential realDistExponential =
                        new RealDistExponential(this,
                                namePrefix + activityName, //$NON-NLS-1$
                                mean, showInReport, showInTrace);
                realDistExponential.setNonNegative(true);
                return realDistExponential;
            } else {
                String message = "Invalid simulation data for activity/event: " //$NON-NLS-1$
                        + activityName;
                message += "\nMean must not be negative."; //$NON-NLS-1$
                throw new RuntimeException(message);
            }

        }
        if (distribution instanceof ExternalEmpiricalDistribution) {
            // ExternalEmpiricalDistribution empiricalDist =
            // (ExternalEmpiricalDistribution) distribution;
            throw new RuntimeException(
                    "Empirical duration distribution is not yet supported. Activity/Event: " //$NON-NLS-1$
                            + activityName);
        }
        throw new RuntimeException(
                "Unsupported distribution type. Activity/Event: " //$NON-NLS-1$
                        + activityName);
    }

    private void createCaseGenerationDistributionForActivity(Activity activity) {

        if (caseGenerationDistributionTimes == null) {
            caseGenerationDistributionTimes = new HashMap();
        }

        StartSimulationDataType actSimData =
                SimulationXpdlUtils.getStartSimulationData(activity);
        if (actSimData == null) {
            String message = "No simulation data for start event! " + "[id: " //$NON-NLS-1$ //$NON-NLS-2$
                    + activity.getId() + ", name: " + activity.getName() //$NON-NLS-1$
                    + ", description: " + activity.getDescription() + "]"; //$NON-NLS-1$ //$NON-NLS-2$
            throw new RuntimeException(message);
        }

        EObject distribution = actSimData.getDuration();
        if (distribution instanceof ExternalEmpiricalDistribution) {
            ExternalEmpiricalDistribution empiricalDist =
                    (ExternalEmpiricalDistribution) distribution;
            String reference = empiricalDist.getReference();
            startEventId = activity.getId();
            caseGenerationDistributionTimes.put(startEventId,
            // TODO change from null to file
                    new RealDataCaseGenerationData(process, reference,
                            (int) actSimData.getNumberOfCases()));
        } else {
            RealDist dist =
                    getRealBasicDistribution(actSimData.getDuration(),
                            activity,
                            Messages.WorkflowModel_CaseGeneration,
                            true,
                            false);
            startEventId = activity.getId();
            caseGenerationDistributionTimes.put(startEventId,
                    new CaseGenerationData(dist, (int) actSimData
                            .getNumberOfCases()));
        }
    }

    // default visibility only to faciliate testing
    private void createParticipants() {
        simModelParticipants = new HashMap();
        participantCalendar = new HashMap();
        // TODO Each participant should have their own locale/calendar
        ISchedulingCalendar calendar =
                SchedulingPlugin.getDefault().getCalendar("default"); //$NON-NLS-1$
        for (Iterator iter = xpdlPackage.getParticipants().iterator(); iter
                .hasNext();) {
            Participant participant = (Participant) iter.next();
            String participantId = participant.getId();
            String participantName = participant.getName();
            participantCalendar.put(participantId, calendar);
            ParticipantSimulationDataType participantSimData =
                    SimulationXpdlUtils
                            .getParticipantSimulationData(participant);
            Integer instances = null;
            double costPerTimeUnit = 0.0D;
            if (participantSimData != null) {
                instances =
                        new Integer(participantSimData.getInstances()
                                .intValue());
                TimeUnitCostType timeUnitCost =
                        participantSimData.getTimeUnitCost();
                if (timeUnitCost != null) {
                    costPerTimeUnit = timeUnitCost.getCost();
                }
            }
            if (instances != null) {
                Queue queue =
                        new Queue(this, Messages.WorkflowModel_Idle
                                + participantName, true, false);
                Queue queue2 =
                        new Queue(this, Messages.WorkflowModel_NonWorking
                                + participantName, true, false);
                SimModelParticipantImpl simModelParticipantImpl =
                        new SimModelParticipantImpl(participantId,
                                participantName, instances.intValue(), queue,
                                queue2);
                simModelParticipantImpl.setCostPerTimeUnit(costPerTimeUnit);
                simModelParticipants.put(participant.getId(),
                        simModelParticipantImpl);
            } else {
                throw new RuntimeException(
                        "Participant simulation data is not present or valid. Participant: " //$NON-NLS-1$
                                + participant.getName());
            }
        }
        // if participants on the process level have the same id that one on
        // package level
        // then the process level participant will overwrite the package level
        // participant.
        for (Iterator iter = process.getParticipants().iterator(); iter
                .hasNext();) {
            Participant participant = (Participant) iter.next();
            String participantId = participant.getId();
            String participantName = participant.getName();
            participantCalendar.put(participantId, calendar);
            ParticipantSimulationDataType participantSimData =
                    SimulationXpdlUtils
                            .getParticipantSimulationData(participant);
            Integer instances = null;
            double costPerTimeUnit = 0.0D;
            if (participantSimData != null) {
                instances =
                        new Integer(participantSimData.getInstances()
                                .intValue());
                TimeUnitCostType timeUnitCost =
                        participantSimData.getTimeUnitCost();
                if (timeUnitCost != null) {
                    costPerTimeUnit = timeUnitCost.getCost();
                }
            }
            if (instances != null) {
                Queue queue =
                        new Queue(this, Messages.WorkflowModel_Idle
                                + participantName, true, false);
                Queue queue2 =
                        new Queue(this, Messages.WorkflowModel_NonWorking
                                + participantName, true, false);
                SimModelParticipantImpl simModelParticipantImpl =
                        new SimModelParticipantImpl(participantId,
                                participantName, instances.intValue(), queue,
                                queue2);
                simModelParticipantImpl.setCostPerTimeUnit(costPerTimeUnit);
                simModelParticipants.put(participant.getId(),
                        simModelParticipantImpl);
            } else {
                throw new RuntimeException(
                        "Participant simulation data is not present or valid. Participant: " //$NON-NLS-1$
                                + participant.getName());
            }
        }
    }

    private void createActivityParticipatMap() {
        activityParticipantMap = new HashMap();
        simModelActivities = new HashMap();
        for (Iterator iter = process.getActivities().iterator(); iter.hasNext();) {
            Activity activity = (Activity) iter.next();
            Implementation implementation = activity.getImplementation();
            if (implementation instanceof Task
                    || implementation instanceof SubFlow) {

                Performer performer = null;
                EList performers = activity.getPerformerList();

                if (performers != null && performers.size() > 0) {
                    performer = (Performer) performers.get(0);
                }

                if (performer != null) {
                    String performerId = performer.getValue();
                    activityParticipantMap.put(activity, performerId);
                    SimModelActivity simModelActivity =
                            new SimModelActivityImpl(activity.getId(),
                                    activity.getName(), performerId);
                    simModelActivities.put(activity.getId(), simModelActivity);
                }
            }
        }
        for (Iterator iterator = process.getActivitySets().iterator(); iterator
                .hasNext();) {
            for (Iterator iter =
                    ((ActivitySet) iterator.next()).getActivities().iterator(); iter
                    .hasNext();) {
                Activity activity = (Activity) iter.next();
                Implementation implementation = activity.getImplementation();
                if (implementation instanceof Task
                        || implementation instanceof SubFlow) {

                    Performer performer = null;
                    EList performers = activity.getPerformerList();

                    if (performers != null && performers.size() > 0) {
                        performer = (Performer) performers.get(0);
                    }

                    if (performer != null) {
                        String performerId = performer.getValue();
                        activityParticipantMap.put(activity, performerId);
                        SimModelActivity simModelActivity =
                                new SimModelActivityImpl(activity.getId(),
                                        activity.getName(), performerId);
                        simModelActivities.put(activity.getId(),
                                simModelActivity);
                    }
                }
            }
        }
        taskActivityThreadQueue =
                new TaskActivityThreadQueue(this, true, true,
                        activityParticipantMap);
    }

    @Override
    public void doInitialSchedules() {
        // create and activate the participants(s)
        for (Iterator iter = simModelParticipants.values().iterator(); iter
                .hasNext();) {
            SimModelParticipant participant = (SimModelParticipant) iter.next();
            String participantId = participant.getId();
            String participantName = participant.getName();
            int noOfInstances = participant.getNoOfInstances();
            double nextWorkingStartTime =
                    getNextWorkingStartTime(participantId, new SimTime(0));
            double nextWorkingStopTime =
                    getNextWorkingStopTime(participantId, new SimTime(0));
            for (int i = 0; i < noOfInstances; i++) {
                SimParticipant simParticipant =
                        new SimParticipant(
                                this,
                                "Participant " + participantName + "#" + (i + 1), true, participantId); //$NON-NLS-1$ //$NON-NLS-2$
                simParticipant
                        .setAvailable(nextWorkingStartTime > nextWorkingStopTime);
                simParticipant.activate(new SimTime(0.0));
                // ATTENTION:
                // Don't use SimTime.NOW or SimTime.now() here, because it
                // leads to "strange" results due to a DESMO-J weakness here.
                WorkingStartTimer startTimer =
                        new WorkingStartTimer(
                                this,
                                "Participant Timer " + participantName + "#" + (i + 1), //$NON-NLS-1$ //$NON-NLS-2$
                                true, simParticipant);
                WorkingStopTimer stopTimer =
                        new WorkingStopTimer(
                                this,
                                "Participant Timer " + participantName + "#" + (i + 1), //$NON-NLS-1$ //$NON-NLS-2$
                                true, simParticipant);
                startTimer.activate(new SimTime(nextWorkingStartTime));
                stopTimer.activate(new SimTime(nextWorkingStopTime));
            }
        }

        int caseGenIndex = 1;
        for (Iterator iter =
                caseGenerationDistributionTimes.keySet().iterator(); iter
                .hasNext();) {
            if (caseGenIndex > 1) {
                throw new RuntimeException("Meny start events are not allowed!"); //$NON-NLS-1$
            }
            startEventId = (String) iter.next();
            Object obj = caseGenerationDistributionTimes.get(startEventId);
            if (obj instanceof CaseGenerationData) {
                int noOfCases = ((CaseGenerationData) obj).getNoOfCases();
                SimCaseGenerator generator =
                        new SimCaseGenerator(this,
                                "CaseGenerator" + " #" + caseGenIndex, false, //$NON-NLS-1$ //$NON-NLS-2$
                                noOfCases, process, packageFilePath, processId,
                                startEventId);
                generator.addPropertyChangeListener(this);
                generator.activate(new SimTime(0.0));
            } else if (obj instanceof RealDataCaseGenerationData) {
                int noOfCases =
                        ((RealDataCaseGenerationData) obj).getNoOfCases();
                SimRealDataCaseGenerator generator =
                        new SimRealDataCaseGenerator(this,
                                "CaseGenerator" + " #" + caseGenIndex, false, //$NON-NLS-1$ //$NON-NLS-2$
                                noOfCases, process, packageFilePath, processId,
                                startEventId);
                generator.addPropertyChangeListener(this);
                generator.activate(new SimTime(0.0));
            }
            caseGenIndex++;
        }

    }

    public double getNextWorkingStartTime(String participantId, SimTime time) {
        ISchedulingCalendar schedule =
                (ISchedulingCalendar) participantCalendar.get(participantId);
        GregorianCalendar now = new GregorianCalendar();
        now.setTime(realStartTime);
        long millisToAdd = simTimeToRealTime(time) + 1;
        now.setTimeInMillis(now.getTimeInMillis() + millisToAdd);
        Calendar calendar = schedule.getNextStartTime(now);
        double offset =
                realTimeToSimTime(calendar.getTimeInMillis()
                        - realStartTime.getTime());
        return offset;
    }

    public double getNextWorkingStopTime(String participantId, SimTime time) {
        ISchedulingCalendar schedule =
                (ISchedulingCalendar) participantCalendar.get(participantId);
        GregorianCalendar now = new GregorianCalendar();
        now.setTime(realStartTime);
        long millisToAdd = simTimeToRealTime(time) + 1;
        now.setTimeInMillis(now.getTimeInMillis() + millisToAdd);
        Calendar calendar = schedule.getNextFinishTime(now);
        double offset =
                realTimeToSimTime(calendar.getTimeInMillis()
                        - realStartTime.getTime());
        return offset;
    }

    private long simTimeToRealTime(SimTime time) {
        return (long) (time.getTimeValue() * timeUnitConversionFactor);
    }

    private double realTimeToSimTime(long realTime) {
        return realTime / timeUnitConversionFactor;
    }

    public InteractionEngine getInteractionEngine() {
        return InteractionEngineFactory.getInteractionEngine();
    }

    public com.tibco.inteng.Process getXPDLProcess(String packageName,
            String processName) {
        com.tibco.inteng.Process xpdlProcess =
                getXPDLPackage(packageName).getProcess(processName);
        if (xpdlProcess == null) {
            RuntimeException e =
                    new RuntimeException(
                            "Package does not contain process with id = " //$NON-NLS-1$
                                    + processName);
            throw e;
        }
        return xpdlProcess;
    }

    public com.tibco.inteng.Package getXPDLPackage(String packageName) {
        com.tibco.inteng.Package xpdlPackage = null;
        try {
            Map configParams = new HashMap();
            configParams.put(IntEngConsts.IGNORE_APP_REFERENCE, Boolean.TRUE);
            configParams.put(IntEngConsts.IGNORE_SUBFLOW_REFERENCE,
                    Boolean.TRUE);
            repository.setConfigParameters(configParams);
            xpdlPackage = repository.getPackage(packageName);
        } catch (IOException e) {
            throw new RuntimeException(
                    "IOException was thrown during package obtaining: " + e); //$NON-NLS-1$
        }
        return xpdlPackage;
    }

    public double getCaseGenerationIntervalTime(String eventId) {
        return ((CaseGenerationData) caseGenerationDistributionTimes
                .get(eventId)).getIntervalDistribution().sample();
    }

    public RealDist getCaseGenerationIntervalDistribution() {
        Object object = caseGenerationDistributionTimes.get(startEventId);
        if (object instanceof CaseGenerationData) {
            CaseGenerationData caseGenerationData =
                    ((CaseGenerationData) object);
            return caseGenerationData.getIntervalDistribution();
        }
        return null;
    }

    private void setupRepository(String defaultLocation)
            throws MalformedURLException {
        ResourceLocator resLoc = new URLResources();

        if (defaultLocation != null) {
            resLoc = new DefaultLocationResources(defaultLocation, resLoc);
        } else {
            resLoc =
                    new DefaultLocationResources(
                            "http://localhost/processes/", resLoc); //$NON-NLS-1$
        }
        resLoc = new FileLocator(resLoc);

        repository = new InteractionRepositoryImpl();
        repository.setResourceLocator(resLoc);

        // install security plugin
        repository.setSecurityPlugIn(new SecurityPlugIn() {
            @Override
            public boolean isUserAllowed(String performerId,
                    String performerName, String performerType) {
                return true;
            }
        });
    }

    public TaskActivityThreadQueue getManualActivityThreadQueue() {
        return taskActivityThreadQueue;
    }

    public double getServiceTimeForActivity(String activityId,
            ProcessState processState) {
        Object realDist = activityExecutionTimes.get(activityId);
        if (realDist instanceof RealDist) {
            return ((RealDist) realDist).sample();
        } else if (realDist instanceof ParameterBasedRealDistribution) {
            ParameterBasedRealDistribution paramBasedDist =
                    (ParameterBasedRealDistribution) realDist;
            Object parameter =
                    processState.getFields()
                            .get(paramBasedDist.getParameterId());
            Object value = null;
            if (parameter instanceof XpdlData) {
                value = ((XpdlData) parameter).getValue();
            }
            return paramBasedDist.sample(value);
        }
        return ((RealDist) realDist).sample();
    }

    public Queue getIdleParticipantQueue(String participantId) {
        Object simModelParticipant = simModelParticipants.get(participantId);
        if (simModelParticipant instanceof SimModelParticipant) {
            return ((SimModelParticipant) simModelParticipant)
                    .getIdleParticipantQueue();
        }
        throw new IllegalArgumentException(
                "Invalid participant id = " + participantId); //$NON-NLS-1$
    }

    public Queue getNonWorkingQueue(String participantId) {
        Object simModelParticipant = simModelParticipants.get(participantId);
        if (simModelParticipant instanceof SimModelParticipant) {
            return ((SimModelParticipant) simModelParticipant)
                    .getNonWorkingParticipantQueue();
        }
        throw new IllegalArgumentException(
                "Invalid participant id = " + participantId); //$NON-NLS-1$
    }

    /**
     * Cases call that method before just start.
     */
    void updateStartedCasesCount() {
        startedCasesCount.update();
        propChangeSupport
                .firePropertyChange(WorkflowModel.STARTED_CASES_PROPERTY,
                        (int) (startedCasesCount.getValue() - 1),
                        (int) startedCasesCount.getValue());
    }

    public long getStartedCasesCount() {
        return finishedCasesCount.getValue();
    }

    /**
     * Cases call that method before just finish.
     */
    void updateFinishedCasesCount() {
        finishedCasesCount.update();
        propChangeSupport
                .firePropertyChange(WorkflowModel.FINISHED_CASES_PROPERTY,
                        (int) (finishedCasesCount.getValue() - 1),
                        (int) finishedCasesCount.getValue());
    }

    public long getFinishedCaseCount() {
        return finishedCasesCount.getValue();
    }

    public synchronized void addPropertyChangeListener(
            PropertyChangeListener listener) {
        propChangeSupport.addPropertyChangeListener(listener);
    }

    public synchronized void addPropertyChangeListener(String propertyName,
            PropertyChangeListener listener) {
        propChangeSupport.addPropertyChangeListener(propertyName, listener);
    }

    public synchronized void removePropertyChangeListener(
            PropertyChangeListener listener) {
        propChangeSupport.removePropertyChangeListener(listener);
    }

    public synchronized void removePropertyChangeListener(String propertyName,
            PropertyChangeListener listener) {
        propChangeSupport.removePropertyChangeListener(propertyName, listener);
    }

    /**
     * This method is gruping all notificaton from model.
     * 
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
     */
    @Override
    public void propertyChange(PropertyChangeEvent event) {
        propChangeSupport.firePropertyChange(event);
    }

    /**
     * Finished cases at the end notify the model about duration time.
     * 
     * @param time
     *            duration time of finished case.
     */
    void addCaseDurationTime(double time) {
        simModelCasesData.addCaseDurationTime(time);
    }

    public URL getDefaultRepoLocation() {
        return defaultRepoLocation;
    }

    /**
     * This method returns sumary number of cases for process. Model must be
     * first initialized to use this method correctly.
     * 
     * @return number of cases to start.
     */
    public int getNoOfCases() {
        int noOfCases = 0;
        for (Iterator iter =
                caseGenerationDistributionTimes.values().iterator(); iter
                .hasNext();) {
            Object obj = iter.next();
            if (obj instanceof CaseGenerationData) {
                noOfCases += ((CaseGenerationData) obj).getNoOfCases();
            } else if (obj instanceof RealDataCaseGenerationData) {
                noOfCases += ((RealDataCaseGenerationData) obj).getNoOfCases();
            }

        }
        return noOfCases;
    }

    /**
     * Start reference time for simulation.
     * 
     * @return start reference time for simulation or null is set by the model.
     */
    public Date getReferenceStartTime() {
        Object obj = caseGenerationDistributionTimes.get(startEventId);
        if (obj instanceof RealDataCaseGenerationData) {
            return ((RealDataCaseGenerationData) obj).getStartTime();
        }
        return null;
    }

    /**
     * @return number of cases to start for start event.
     */
    int getNoOfCasesForStartEvent(String eventId) {
        Object obj = caseGenerationDistributionTimes.get(startEventId);
        if (obj instanceof CaseGenerationData) {
            return ((CaseGenerationData) obj).getNoOfCases();
        } else if (obj instanceof RealDataCaseGenerationData) {
            return ((RealDataCaseGenerationData) obj).getNoOfCases();
        }
        return 0;
    }

    CaseGenerator getStartEventCaseGenerator(String eventId) {
        Object obj = caseGenerationDistributionTimes.get(eventId);
        if (obj instanceof RealDataCaseGenerationData) {
            return ((RealDataCaseGenerationData) obj).getCaseGenerator();
        }
        return null;
    }

    public RealDist getActivityServiceTimeDistribution(String activityId) {
        return ((RealDist) activityExecutionTimes.get(activityId));
    }

    public String getPackageId() {
        return packageId;
    }

    public String getProcessName() {
        return processName;
    }

    public String getProcessLabel() {
        return processLabel;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getProcessId() {
        return processId;
    }

    public Map getSimModelActivities() {
        return simModelActivities;
    }

    public Map getSimModelParticipants() {
        return simModelParticipants;
    }

    public Map getActivityLoopControls() {
        return actLoopControl;
    }

    public SimModelCasesData getSimModelCasesData() {
        return simModelCasesData;
    }

    public void activityServed(String activityId, String participantId,
            double serviceTimeForActivity) {
        SimModelActivityImpl simModelActivity =
                ((SimModelActivityImpl) simModelActivities.get(activityId));
        SimModelParticipantImpl simModelParticipant =
                ((SimModelParticipantImpl) simModelParticipants
                        .get(participantId));
        if (simModelActivity != null && simModelParticipant != null) {
            double activityCost =
                    simModelParticipant.getCostPerTimeUnit()
                            * serviceTimeForActivity;
            simModelActivity.addCost(activityCost);
            simModelActivity.addServiceTime(serviceTimeForActivity);
            simModelParticipant.addBusyTime(serviceTimeForActivity);
            simModelParticipant.addCost(activityCost);
        }
    }

    public double getActivityCost(String participantId,
            double serviceTimeForActivity) {
        double activityCost = 0;
        SimModelParticipantImpl simModelParticipant =
                ((SimModelParticipantImpl) simModelParticipants
                        .get(participantId));
        if (simModelParticipant != null) {
            activityCost =
                    simModelParticipant.getCostPerTimeUnit()
                            * serviceTimeForActivity;
        }
        return activityCost;
    }

    public boolean hasActivities() {
        return hasActivities;
    }

    public String getPackageFilePath() {
        return packageFilePath;
    }

    public Process getWorkFlowProcess() {
        return this.process;
    }

    public void setRealStartTime(Date realStartTime) {
        this.realStartTime = realStartTime;
    }

    public void setTimeUnit(int timeUnit) {
        timeUnitConversionFactor = 1;
        switch (timeUnit) {
        case Units.H:
            timeUnitConversionFactor = 3600000;
            break;
        case Units.MIN:
            timeUnitConversionFactor = 60000;
            break;
        case Units.S:
            timeUnitConversionFactor = 1000;
            break;
        }
    }

    public void addCaseCost(double cost) {
        simModelCasesData.addCost(cost);
    }

    /**
     * Checks if activity is subflow activity
     * 
     * @param activity
     * @return true if activity is task.
     */
    private boolean isSubFlowActivity(Activity activity) {
        Implementation implementation = activity.getImplementation();
        return implementation instanceof SubFlow;
    }

    public double getWorkingTime(String participantId, SimTime currentTime) {
        double time = 0;
        ISchedulingCalendar calendar =
                (ISchedulingCalendar) participantCalendar.get(participantId);
        GregorianCalendar start = new GregorianCalendar();
        GregorianCalendar now = new GregorianCalendar();
        if (realStartTime != null) {
            start.setTimeInMillis(realStartTime.getTime());
            now.setTimeInMillis(realStartTime.getTime()
                    + simTimeToRealTime(currentTime));
            time =
                    realTimeToSimTime((long) calendar.getWorkingTime(start,
                            now,
                            GregorianCalendar.MILLISECOND));
        }
        return time;
    }
}
