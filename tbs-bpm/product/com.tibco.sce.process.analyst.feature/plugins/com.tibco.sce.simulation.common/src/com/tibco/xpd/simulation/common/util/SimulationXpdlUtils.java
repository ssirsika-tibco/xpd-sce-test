/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.common.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.simulation.ActivitySimulationDataType;
import com.tibco.xpd.simulation.EnumBasedExpressionType;
import com.tibco.xpd.simulation.ExpressionType;
import com.tibco.xpd.simulation.ParameterBasedDistribution;
import com.tibco.xpd.simulation.ParameterDependentDistribution;
import com.tibco.xpd.simulation.ParticipantSimulationDataType;
import com.tibco.xpd.simulation.SimulationPackage;
import com.tibco.xpd.simulation.SimulationRealDistributionType;
import com.tibco.xpd.simulation.SplitSimulationDataType;
import com.tibco.xpd.simulation.StartSimulationDataType;
import com.tibco.xpd.simulation.StructuredConditionType;
import com.tibco.xpd.simulation.TimeDisplayUnitType;
import com.tibco.xpd.simulation.TransitionSimulationDataType;
import com.tibco.xpd.simulation.WorkflowProcessSimulationDataType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Condition;
import com.tibco.xpd.xpdl2.ConditionType;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.JoinSplitType;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Split;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TransitionRestriction;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * @author nwilson
 */
public final class SimulationXpdlUtils {

    /** The default time unit. */
    public static final TimeDisplayUnitType DEFAULT_TIME_UNIT =
            TimeDisplayUnitType.MINUTE_LITERAL;

    private static boolean preprocessorMode = false;

    /**
     * Private constructor.
     */
    private SimulationXpdlUtils() {
    }

    /**
     * Returns the activity display name. Used by simulation reports.
     * 
     * @param activity
     *            The activity to get the display name for.
     * @return The display name.
     */
    public static String getActivityDisplayName(Activity activity) {
        String name = nullToEmptyString(activity.getName());
        if (!"".equals(name)) { //$NON-NLS-1$
            return name;
        }
        Event event = activity.getEvent();
        if (event instanceof StartEvent) {
            return Messages.SimulationXpdlUtils_StartEvent + activity.getId();
        }
        if (activity.getImplementation() instanceof Task) {
            return Messages.SimulationXpdlUtils_Task + activity.getId();
        }
        return name;
    }

    /**
     * Gets ActivitySimulationData for Activity if this data exist otherwise
     * returns null.
     * 
     * @param activity
     *            xpdl activity.
     * @return AstivitySimulationData for Activity if tihs data exists otherwise
     *         returns null.
     */
    public static ActivitySimulationDataType getActivitySimulationData(
            Activity activity) {
        for (Iterator iter = activity.getExtendedAttributes().iterator(); iter
                .hasNext();) {
            ExtendedAttribute ea = (ExtendedAttribute) iter.next();
            if ("ActivitySimulationData".equals(ea.getName())) { //$NON-NLS-1$
                // found extended attribute
                EList simDataList =
                        (EList) ea.getMixed().get(SimulationPackage.eINSTANCE
                                .getDocumentRoot_ActivitySimulationData(),
                                true);
                // there can only be one of these
                if (simDataList.size() == 1 && simDataList.get(0) != null) {
                    return (ActivitySimulationDataType) simDataList.get(0);
                }
                return null;
            }
        }
        return null;
    }

    /**
     * Gets ParticipantSimulationData for Participant if this data exists
     * otherwise returns null.
     * 
     * @param participant
     *            xpdl participant.
     * @return ParticipantSimulationData for Participant if tihs data exists
     *         otherwise returns null.
     */
    public static ParticipantSimulationDataType getParticipantSimulationData(
            Participant participant) {
        EStructuralFeature dataFeature =
                SimulationPackage.eINSTANCE
                        .getDocumentRoot_ParticipantSimulationData();
        for (Iterator iter = participant.getExtendedAttributes().iterator(); iter
                .hasNext();) {
            ExtendedAttribute ea = (ExtendedAttribute) iter.next();
            if ("ParticipantSimulationData".equals(ea.getName())) { //$NON-NLS-1$
                // found extended attribute
                EList simProcessList =
                        (EList) ea.getMixed().get(dataFeature, true);
                // there can only be one of these
                if (simProcessList.size() == 1 && simProcessList.get(0) != null) {
                    return (ParticipantSimulationDataType) simProcessList
                            .get(0);
                }
                return null;
            }
        }
        return null;
    }

    /**
     * Search by participantId for participant from any xpdl Package object
     * child. First search in the containing process if possible and then in the
     * package. If participant not found then null is returned.
     * 
     * @param baseObject
     *            the base EObject from wchich search is started.
     * @param participantId
     *            The participant ID.
     * @return participant form process or package or null if not found.
     */
    public static Participant findParticipant(EObject baseObject,
            String participantId) {
        if (participantId == null) {
            return null;
        }
        EObject container = baseObject;
        // search in process first if possible then in package
        while (container != null) {
            if (container instanceof Process) {
                Process process = (Process) container;
                List participants = process.getParticipants();
                for (Iterator iter = participants.iterator(); iter.hasNext();) {
                    Participant participant = (Participant) iter.next();
                    if (participantId.equals(participant.getId())) {
                        return participant;
                    }
                }
            }
            if (container instanceof Package) {
                Package thePackage = (Package) container;
                List participants = thePackage.getParticipants();
                for (Iterator iter = participants.iterator(); iter.hasNext();) {
                    Participant participant = (Participant) iter.next();
                    if (participantId.equals(participant.getId())) {
                        return participant;
                    }
                }
            }
            container = container.eContainer();
        }
        return null;
    }

    /**
     * Returns WorkflowProcess for workflow process child or null if parameter
     * is not WorkflowProcess child.
     * 
     * @param workflowProcessChild
     *            child of WorkflowProcess.
     * @return WorkflowProcess for workflow process child or null if parameter
     *         is not WorkflowProcess child.
     */
    public static Process getWorkflowProcess(EObject workflowProcessChild) {
        if (workflowProcessChild instanceof Process) {
            return (Process) workflowProcessChild;
        } else {
            EObject container = workflowProcessChild;
            while ((container = container.eContainer()) != null) {
                if (container instanceof Process) {
                    return (Process) container;
                }
            }
        }
        return null;
    }

    /**
     * @param activityChild
     *            The child object.
     * @return The containing parent activity.
     */
    public static Activity getActivity(EObject activityChild) {
        if (activityChild instanceof Activity) {
            return (Activity) activityChild;
        } else {
            EObject container = activityChild;
            while ((container = container.eContainer()) != null) {
                if (container instanceof Activity) {
                    return (Activity) container;
                }
            }
        }
        return null;
    }

    /**
     * Returns Package for package child object or null if parameter is not
     * Package child object.
     * 
     * @param packageChild
     *            child of Package.
     * @return Package for package child or null if parameter is not Package
     *         child.
     */
    public static Package getPackage(EObject packageChild) {
        if (packageChild instanceof Package) {
            return (Package) packageChild;
        } else {
            EObject container = packageChild;
            while ((container = container.eContainer()) != null) {
                if (container instanceof Package) {
                    return (Package) container;
                }
            }
        }
        return null;
    }

    /**
     * Gets all outgoing transitions from an activity.
     * 
     * @param flowContainer
     *            flow container which contains activity.
     * @param activity
     *            a xpdl activity.
     * @return array of outgoing transitions.
     */
    public static Transition[] getOutgoingTransitions(
            FlowContainer flowContainer, Activity activity) {
        List<Transition> outTrans = new ArrayList<Transition>();
        String activityId = activity.getId();
        for (Iterator iter = flowContainer.getTransitions().iterator(); iter
                .hasNext();) {
            Transition transition = (Transition) iter.next();
            if (activityId.equals(transition.getFrom())) {
                outTrans.add(transition);
            }
        }
        return (Transition[]) outTrans.toArray(new Transition[outTrans.size()]);
    }

    /**
     * Gets StartSimulationDataType for Activity if this data exists otherwise
     * returns null.
     * 
     * @param activity
     *            xpdl activity.
     * @return StartSimulationDataType for Activity if tihs data exists
     *         otherwise returns null.
     */
    public static StartSimulationDataType getStartSimulationData(
            Activity activity) {
        for (Iterator iter = activity.getExtendedAttributes().iterator(); iter
                .hasNext();) {
            ExtendedAttribute ea = (ExtendedAttribute) iter.next();
            if ("StartSimulationData".equals(ea.getName())) { //$NON-NLS-1$
                // found extended attribute
                EList simDataList =
                        (EList) ea.getMixed().get(SimulationPackage.eINSTANCE
                                .getDocumentRoot_StartSimulationData(),
                                true);
                // there can only be one of these
                if (simDataList.size() == 1 && simDataList.get(0) != null) {
                    return (StartSimulationDataType) simDataList.get(0);
                }
                return null;
            }
        }
        return null;
    }

    /**
     * If parameter is null then convert it to empty string otherwise return the
     * same string.
     * 
     * @param s
     *            input string.
     * @return the same string if parameter is not null and empty string when it
     *         is null.
     */
    public static String nullToEmptyString(String s) {
        return s != null ? s : ""; //$NON-NLS-1$
    }

    /**
     * Returns the activity display name. Used by simulation reports.
     * 
     * @param activity
     * @return
     */
    /*
     * public static String getActivityDisplayName(Activity activity) { String
     * name = nullToEmptyString(activity.getName()); if (!"".equals(name)) {
     * return name; } if (XPDExtUtil.isStartEvent(activity)) { return "Start
     * Event: " + activity.getId(); } if (XPDExtUtil.isTaskActivity(activity)) {
     * return "Task: " + activity.getId(); } return name; }
     */

    /**
     * Gets workflow simulation parameters from workflow extended attributes.
     * 
     * @param workflowProcess
     *            XPDL Workflow process.
     * @return list of ParameterDistributionType objects.
     */
    public static List getWorkflowSimulationParameters(Process workflowProcess) {
        for (Iterator iter = workflowProcess.getExtendedAttributes().iterator(); iter
                .hasNext();) {
            ExtendedAttribute ea = (ExtendedAttribute) iter.next();
            if ("WorkflowProcessSimulationData".equals(ea.getName())) { //$NON-NLS-1$
                // found extended attribute
                FeatureMap content = ea.getMixed();
                EList list =
                        content
                                .list(SimulationPackage.eINSTANCE
                                        .getDocumentRoot_WorkflowProcessSimulationData());
                if (list.size() == 1) {
                    WorkflowProcessSimulationDataType wpsd =
                            (WorkflowProcessSimulationDataType) list.get(0);
                    return wpsd.getParameterDistribution();
                }
            }
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * Gets workflow simulation parameters Map from workflow extended
     * attributes.
     * 
     * @param workflowProcess
     *            XPDL Workflow process.
     * @return map of parameters where keys (String) are parameter ids and
     *         values are xpdl FormalParameter.
     */
    public static Map getWorkflowParametersMap(Process workflowProcess) {
        Map<String, FormalParameter> params =
                new LinkedHashMap<String, FormalParameter>();
        for (Iterator iter =
                ProcessInterfaceUtil.getAllFormalParameters(workflowProcess)
                        .iterator(); iter.hasNext();) {
            FormalParameter formalParam = (FormalParameter) iter.next();
            params.put(formalParam.getName(), formalParam);
        }
        return params;
    }

    /**
     * Gets SplitSimulationDataType for Activity if this element exists
     * otherwise returns null.
     * 
     * @param activity
     *            xpdl activity.
     * @return SplitSimulationData for Activity if this element exists otherwise
     *         returns null.
     */
    public static SplitSimulationDataType getSplitSimulationData(
            Activity activity) {
        for (Iterator iter = activity.getExtendedAttributes().iterator(); iter
                .hasNext();) {
            ExtendedAttribute ea = (ExtendedAttribute) iter.next();
            if ("SplitSimulationData".equals(ea.getName())) { //$NON-NLS-1$
                // found extended attribute
                EList eaContent =
                        (EList) ea.getMixed().get(SimulationPackage.eINSTANCE
                                .getDocumentRoot_SplitSimulationData(),
                                true);
                // there can only be one of these
                if (eaContent.size() == 1 && eaContent.get(0) != null) {
                    return (SplitSimulationDataType) eaContent.get(0);
                }
            }
        }
        return null;
    }

    /**
     * Gets SplitType from first found restriction for Activity if this element
     * exists otherwise returns null.
     * 
     * @param activity
     *            xpdl activity.
     * @return SplitType for Activity if this element exists otherwise returns
     *         null.
     */
    public static JoinSplitType getSplitType(Activity activity) {
        for (Iterator iter = activity.getTransitionRestrictions().iterator(); iter
                .hasNext();) {
            TransitionRestriction restriction =
                    (TransitionRestriction) iter.next();
            Split split = restriction.getSplit();
            if (split != null) {
                return split.getType();
            }
        }
        return null;
    }

    /**
     * Gets TransitionSimulationDataType for transition if this element exists
     * otherwise returns null.
     * 
     * @param transition
     *            xpdl transition.
     * @return TransitionSimulationDataType for transition if this element
     *         exists otherwise returns null.
     */
    public static TransitionSimulationDataType getTransitionSimulationData(
            Transition transition) {
        EStructuralFeature dataFeature =
                SimulationPackage.eINSTANCE
                        .getDocumentRoot_TransitionSimulationData();
        for (Iterator iter = transition.getExtendedAttributes().iterator(); iter
                .hasNext();) {
            ExtendedAttribute ea = (ExtendedAttribute) iter.next();
            if ("TransitionSimulationData".equals(ea.getName())) { //$NON-NLS-1$
                // found extended attribute
                EList eaContent = (EList) ea.getMixed().get(dataFeature, true);
                // there can only be one of these
                if (eaContent.size() == 1 && eaContent.get(0) != null) {
                    return (TransitionSimulationDataType) eaContent.get(0);
                }
            }
        }
        return null;
    }

    /**
     * Gets condition for transition if this element exists otherwise returns
     * null.
     * 
     * @param t
     *            xpdl transition.
     * @return TransitionSimulationDataType for transition if this element
     *         exists otherwise returns null.
     */
    public static String getTransitionCondition(Transition t) {
        Condition cond = t.getCondition();
        if (cond != null) {
            EList condContent =
                    (EList) cond.getMixed().get(XMLTypePackage.eINSTANCE
                            .getXMLTypeDocumentRoot_Text(),
                            true);
            // there can only be one of these
            if (condContent.size() == 1 && condContent.get(0) != null) {
                return (String) condContent.get(0);
            }
        }
        return null;
    }

    /**
     * @param process
     *            The conaining process.
     * @param transitionId
     *            The transition ID.
     * @return The transition.
     */
    public static Transition getTransition(Process process, String transitionId) {
        Transition transition = null;
        EList transitions = process.getTransitions();
        for (Iterator i = transitions.iterator(); i.hasNext();) {
            Transition next = (Transition) i.next();
            if (next.getId().equals(transitionId)) {
                transition = next;
                break;
            }
        }
        return transition;
    }

    /**
     * This method will return the first Activity with the passed Id.
     * 
     * @param process
     *            The conaining process.
     * @param id
     *            The activity ID.
     * @return The activity.
     */
    public static Activity getActivity(Process process, String id) {
        EList contents = process.eContents();
        for (Iterator i = contents.iterator(); i.hasNext();) {
            Object nexti = i.next();
            if (nexti instanceof Activity) {
                Activity activity = (Activity) nexti;
                if (activity.getId().equals(id)) {
                    return activity;
                }
            }
        }
        return null;
    }

    /**
     * Gets WorkflowProcessSimulationData for process if this data exists
     * otherwise returns null.
     * 
     * @param process
     *            xpdl process.
     * @return WorkflowProcessSimulationDataType for process if tihs data exists
     *         otherwise returns null.
     */
    public static WorkflowProcessSimulationDataType getWorkflowProcessSimulationData(
            Process process) {

        if (process == null) {
            return null;
        }
        EStructuralFeature dataFeature =
                SimulationPackage.eINSTANCE
                        .getDocumentRoot_WorkflowProcessSimulationData();
        for (Iterator iter = process.getExtendedAttributes().iterator(); iter
                .hasNext();) {
            ExtendedAttribute ea = (ExtendedAttribute) iter.next();
            if ("WorkflowProcessSimulationData".equals(ea.getName())) { //$NON-NLS-1$
                // found extended attribute
                EList simProcessList =
                        (EList) ea.getMixed().get(dataFeature, true);
                // there can only be one of these
                if (simProcessList.size() == 1 && simProcessList.get(0) != null) {
                    return (WorkflowProcessSimulationDataType) simProcessList
                            .get(0);
                }
                return null;
            }
        }
        return null;
    }

    /**
     * This method tells us whether the activity is manual or not.
     * 
     * @param process
     * @param activityId
     * @return
     */
    /*
     * public static boolean isManualActivity(Process process, String
     * activityId) { Activity activity = SimulationXpdlUtils
     * .getActivity(process, activityId); if
     * (XPDExtUtil.isTaskActivity(activity)) { return true; } return false; }
     */

    /**
     * Create command which adds ActivitySimulationData for activity.
     * 
     * @param domain
     *            The editing domain.
     * @param activity
     *            The actvitiy.
     * @param actSimulationData
     *            The simulation data to add.
     * @return command The command to add simulation data.
     */
    public static Command getActivitySimulationDataAddCommand(
            EditingDomain domain, Activity activity,
            ActivitySimulationDataType actSimulationData) {
        ExtendedAttribute ea = Xpdl2Factory.eINSTANCE.createExtendedAttribute();
        ea.setName("ActivitySimulationData"); //$NON-NLS-1$

        // creating and attaching default distribution.
        ea.getMixed().add(SimulationPackage.eINSTANCE
                .getDocumentRoot_ActivitySimulationData(),
                actSimulationData);

        // adding extended attribute.
        EReference extAttFeature =
                Xpdl2Package.eINSTANCE
                        .getExtendedAttributesContainer_ExtendedAttributes();
        return AddCommand.create(domain, activity, extAttFeature, ea);
    }

    /**
     * This method returns the split simulation data for an Activity.
     * 
     * @param activity
     *            The activity.
     * @return SplitSimulationDataType
     */
    public static SplitSimulationDataType getActivitySplitSimulationData(
            Activity activity) {
        for (Iterator iter = activity.getExtendedAttributes().iterator(); iter
                .hasNext();) {
            ExtendedAttribute ea = (ExtendedAttribute) iter.next();
            if ("SplitSimulationData".equals(ea.getName())) { //$NON-NLS-1$
                // found extended attribute
                EList simDataList =
                        (EList) ea.getMixed().get(SimulationPackage.eINSTANCE
                                .getDocumentRoot_SplitSimulationData(),
                                true);
                // there can only be one of these
                if (simDataList.size() == 1 && simDataList.get(0) != null) {
                    return (SplitSimulationDataType) simDataList.get(0);
                }
                return null;
            }
        }
        return null;
    }

    /**
     * This method returns a list of ParameterDependentDistribution from the
     * activity extended attribute which has enum based expression for the
     * passed formal parameter.
     * 
     * @param activity
     *            The activity.
     * @param fpId
     *            The formal parameter ID.
     * @return the distribution.
     */
    public static List getParameterDependentDistribution(Activity activity,
            String fpId) {
        List<ParameterDependentDistribution> toReturn = null;
        ActivitySimulationDataType activitySimulationData =
                SimulationXpdlUtils.getActivitySimulationData(activity);
        if (activitySimulationData == null) {
            return toReturn;
        }
        SimulationRealDistributionType duration =
                activitySimulationData.getDuration();
        if (duration == null) {
            return toReturn;
        }
        ParameterBasedDistribution parameterBasedDistribution =
                duration.getParameterBasedDistribution();
        if (parameterBasedDistribution == null) {
            return toReturn;
        }
        EList parameterDependentDistributions =
                parameterBasedDistribution.getParameterDependentDistributions();
        for (Iterator iterator = parameterDependentDistributions.iterator(); iterator
                .hasNext();) {
            ParameterDependentDistribution element =
                    (ParameterDependentDistribution) iterator.next();
            ExpressionType expression = element.getExpression();
            if (expression == null) {
                continue;
            }
            EnumBasedExpressionType enumBasedExpression =
                    expression.getEnumBasedExpression();
            if (enumBasedExpression == null) {
                continue;
            }
            String paramName = (String) enumBasedExpression.getParamName();
            if (paramName.equals(fpId)) {
                if (toReturn == null) {
                    toReturn = new ArrayList<ParameterDependentDistribution>();
                }
                toReturn.add(element);
            }
        }
        return toReturn;
    }

    /**
     * @param process
     *            The workflow process to get the map for
     * @return A map of transition parameter value strings to an Arraylist of
     *         Transitions using it.
     */
    public static HashMap getTransitionMap(Process process) {
        HashMap<String, List> map = new HashMap<String, List>();
        EList transitions = process.getTransitions();
        for (Iterator i = transitions.iterator(); i.hasNext();) {
            Transition transition = (Transition) i.next();
            TransitionSimulationDataType data =
                    SimulationXpdlUtils.getTransitionSimulationData(transition);
            if (data != null) {
                StructuredConditionType condition =
                        data.getStructuredCondition();
                if (condition != null) {
                    String value = condition.getParameterValue();
                    ArrayList<Transition> list =
                            (ArrayList<Transition>) map.get(value);
                    if (list == null) {
                        list = new ArrayList<Transition>();
                        map.put(value, list);
                    }
                    list.add(transition);
                }
            }
        }
        return map;
    }

    /**
     * Gets transition name or provide descriptive name if transition doesn't
     * have a name.
     * 
     * @param process
     *            The containing process.
     * @param transition
     *            The transition.
     * @return The transition name.
     */
    public static String getTransitionName(Process process,
            Transition transition) {
        String name = ""; //$NON-NLS-1$
        String description = transition.getName();
        if (description != null && description.length() != 0) {
            name = description;
        } else {
            Activity in =
                    SimulationXpdlUtils.getActivity(process, transition
                            .getFrom());
            Activity out =
                    SimulationXpdlUtils
                            .getActivity(process, transition.getTo());
            String activityInName = in == null ? "" : in.getName(); //$NON-NLS-1$
            if (activityInName == null || activityInName.length() == 0) {
                activityInName = Messages.SimulationXpdlUtils_Unnamed;
            }
            String activityOutName = out == null ? "" : out.getName(); //$NON-NLS-1$
            if (activityOutName == null || activityOutName.length() == 0) {
                activityOutName = Messages.SimulationXpdlUtils_Unnamed;
            }
            name =
                    String.format(activityInName,
                            Messages.SimulationXpdlUtils_To,
                            activityOutName);
        }
        return name;
    }

    /**
     * @param activity
     *            The activity.
     * @return true if it's a route.
     */
    public static boolean isRouteActivity(Activity activity) {
        boolean toReturn = false;
        if (activity != null) {
            if (activity.getRoute() != null) {
                toReturn = true;
            }
        }
        return toReturn;
    }

    /**
     * This method returns true when there is atleast one conditional outgoing
     * transition from the passed activity.
     * 
     * @param activity
     *            The activity.
     * @return true if it's a conditional split.
     */
    public static boolean isConditionalSplit(Activity activity) {
        boolean toReturn = false;
        String actId = activity.getId();
        Process workflowProcess = getWorkflowProcess(activity);
        EList transitions = workflowProcess.getTransitions();
        if (transitions == null) {
            return toReturn;
        }
        ArrayList<Transition> tranList = new ArrayList<Transition>();
        for (Iterator iter = transitions.iterator(); iter.hasNext();) {
            Transition tran = (Transition) iter.next();
            if (tran.getFrom().equals(actId)) {
                Condition condition = tran.getCondition();
                if (condition != null
                        && ConditionType.CONDITION_LITERAL.equals(condition
                                .getType())) {
                    tranList.add(tran);
                }
            }
        }
        if (tranList.size() > 0) {
            toReturn = true;
        }
        return toReturn;

    }

    public synchronized static boolean isPreprocessorMode() {
        return preprocessorMode;
    }

    public synchronized static void setPreprocessorMode(boolean preprocessorMode) {
        SimulationXpdlUtils.preprocessorMode = preprocessorMode;
    }
}
