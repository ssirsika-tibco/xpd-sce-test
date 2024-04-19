/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorUtil;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.utils.ActionUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.processeditor.xpdl2.extensions.IScriptGrammarConverter;
import com.tibco.xpd.processeditor.xpdl2.extensions.IScriptGrammarConverter.GrammarConversionCancelledException;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.script.BaseProcessScriptSection;
import com.tibco.xpd.processwidget.adapters.EventAdapter.TimerTriggerMode;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.script.ui.internal.BaseScriptSection;
import com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType;
import com.tibco.xpd.xpdExtension.Audit;
import com.tibco.xpd.xpdExtension.AuditEvent;
import com.tibco.xpd.xpdExtension.AuditEventType;
import com.tibco.xpd.xpdExtension.CatchErrorMappings;
import com.tibco.xpd.xpdExtension.ConditionalParticipant;
import com.tibco.xpd.xpdExtension.CorrelationDataMappings;
import com.tibco.xpd.xpdExtension.DurationCalculation;
import com.tibco.xpd.xpdExtension.EnablementType;
import com.tibco.xpd.xpdExtension.MultiInstanceScripts;
import com.tibco.xpd.xpdExtension.RescheduleDurationType;
import com.tibco.xpd.xpdExtension.RescheduleTimerScript;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.UserTaskScripts;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.Condition;
import com.tibco.xpd.xpdl2.ConditionType;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.Deadline;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Loop;
import com.tibco.xpd.xpdl2.LoopMultiInstance;
import com.tibco.xpd.xpdl2.LoopStandard;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantType;
import com.tibco.xpd.xpdl2.ParticipantTypeElem;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskReceive;
import com.tibco.xpd.xpdl2.TaskScript;
import com.tibco.xpd.xpdl2.TaskSend;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.TaskUser;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.TriggerTimer;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.commands.LateExecuteCompoundCommand;
import com.tibco.xpd.xpdl2.util.DecisionFlowUtil;
import com.tibco.xpd.xpdl2.util.ThrowErrorEventUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Some process model script related utilities.
 * 
 * @author aallway
 * 
 */
public class ProcessScriptUtil {

    private static final String EMPTY_STRING = "";//$NON-NLS-1$

    private static final String WEBSERVICE_SERVICE_TYPE =
            TaskImplementationTypeDefinitions.WEB_SERVICE;

    private static final String BWSERVICE_SERVICE_TYPE =
            TaskImplementationTypeDefinitions.BW_SERVICE;

    private static final String JAVA_SERVICE_TYPE =
            TaskImplementationTypeDefinitions.JAVA_SERVICE;

    private static final String MI_LOOP_EXPR = "MILoopExpr"; //$NON-NLS-1$

    private static final String MI_COMPLEX_EXIT_EXPR = "MIComplexExitExpr"; //$NON-NLS-1$

    /**
     * Get list of conditional sequence flows with a script of the given
     * grammar.
     * 
     * @param flowContainer
     * @param scriptGrammar
     * @return
     */
    public static List<Transition> getSeqFlowsWithScriptType(
            FlowContainer flowContainer, String scriptGrammar) {
        List<Transition> toReturn = null;
        List<Transition> tranList = flowContainer.getTransitions();
        if (tranList == null) {
            return toReturn;
        }
        for (Transition tran : tranList) {
            boolean b = isSeqFlowWithScriptType(tran, scriptGrammar);
            if (b) {
                if (toReturn == null) {
                    toReturn = new ArrayList<Transition>();
                }
                toReturn.add(tran);
            }
        }
        return toReturn;
    }

    /**
     * Is the given sequence flow conditional with a script defined in the given
     * grammar.
     * 
     * @param transition
     * @param scriptGrammar
     * @return
     */
    public static boolean isSeqFlowWithScriptType(Transition transition,
            String scriptGrammar) {
        Condition condition = transition.getCondition();
        if (condition == null) {
            return false;
        }
        Expression expression = condition.getExpression();
        if (expression == null) {
            return false;
        }
        ConditionType type = condition.getType();
        if (ConditionType.CONDITION == type.getValue()
                && scriptGrammar
                        .equalsIgnoreCase(expression.getScriptGrammar())) {
            return true;
        }
        return false;
    }

    /**
     * Get the expression script from the given sequence flow.
     * 
     * @param scriptTransition
     * @return
     */
    public static String getSeqFlowScript(Transition scriptTransition) {
        Condition condition = scriptTransition.getCondition();
        String conditonScript = null;
        if (condition == null) {
            return conditonScript;
        }
        Expression expression = condition.getExpression();
        if (expression != null) {
            conditonScript = getExpressionAsString(expression);
        }
        return conditonScript;
    }

    /**
     * Get the query expression from the participant.
     * 
     * @param participant
     * @return
     */
    public static String getQueryParticipantScript(EObject input) {
        if (input instanceof Participant) {
            Participant participant = (Participant) input;
            ParticipantTypeElem participantType =
                    participant.getParticipantType();
            Object objParticipantQ =
                    Xpdl2ModelUtil.getOtherElement(participantType,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ParticipantQuery());

            if (objParticipantQ instanceof Expression) {
                Expression participantQuery = (Expression) objParticipantQ;
                return getExpressionAsString(participantQuery);
            }
        } else if (input instanceof DataField) {
            DataField dataField = (DataField) input;
            if (dataField.getDataType() instanceof BasicType) {
                BasicType bt = (BasicType) dataField.getDataType();
                if (bt.getType() != null
                        && bt.getType().equals(BasicTypeType.PERFORMER_LITERAL)) {
                    Object objParticipantQ =
                            Xpdl2ModelUtil
                                    .getOtherElement(bt,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ParticipantQuery());

                    if (objParticipantQ instanceof Expression) {
                        Expression participantQuery =
                                (Expression) objParticipantQ;
                        return getExpressionAsString(participantQuery);
                    }
                }
            }
        }

        return null;
    }

    /**
     * This method returns a command for updating the script.
     * 
     * @param editingDomain
     * @param newScript
     * @param eObject
     * @return
     */
    public static Command getSetSequenceFlowScriptCommand(
            EditingDomain editingDomain, String newScript,
            Transition transition, String grammar) {
        Condition condition = transition.getCondition();
        if (condition != null) {
            // Only update model when script has changed.
            Expression currScript = condition.getExpression();

            if (hasScriptChanged(newScript, grammar, currScript)) {

                CompoundCommand cmd = new CompoundCommand();
                cmd.setLabel(Messages.ProcessScriptUtil_SetSeqFlowScript_menu);

                Expression expression =
                        Xpdl2Factory.eINSTANCE.createExpression();
                expression.setScriptGrammar(grammar);
                expression
                        .getMixed()
                        .add(XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(),
                                newScript);
                cmd.append(SetCommand.create(editingDomain,
                        condition,
                        Xpdl2Package.eINSTANCE.getCondition_Expression(),
                        expression));
                return cmd;
            }
        }
        return null;
    }

    /**
     * Get list of Script Task activities that have script defined in the given
     * grammar.
     * 
     * @param flowContainer
     * @param scriptGrammar
     * @return
     */
    public static List<Activity> getScriptTasksWithScriptType(
            FlowContainer flowContainer, String scriptGrammar) {
        List<Activity> toReturn = null;
        List<Activity> activityList = flowContainer.getActivities();
        if (activityList == null) {
            return toReturn;
        }
        for (Activity activity : activityList) {
            Implementation implementation = activity.getImplementation();
            if (implementation instanceof Task) {
                Task task = (Task) implementation;
                TaskScript taskScript = task.getTaskScript();
                if (taskScript == null) {
                    continue;
                }
                Expression expr = taskScript.getScript();
                if (expr == null) {
                    continue;
                }
                if (scriptGrammar.equalsIgnoreCase(expr.getScriptGrammar())) {
                    if (toReturn == null) {
                        toReturn = new ArrayList<Activity>();
                    }
                    toReturn.add(activity);
                }
            }
        }
        return toReturn;
    }

    /**
     * @param flowContainer
     * @param scriptGrammar
     * @return
     */
    public static List<Activity> getActivitiesWithInitiatedScriptType(
            FlowContainer flowContainer, String scriptGrammar) {
        return getActivitiesWithScriptType(flowContainer,
                scriptGrammar,
                AuditEventType.INITIATED_LITERAL);
    }

    /**
     * Returns list of activities with completed script type.
     * 
     * @param flowContainer
     * @param scriptGrammar
     * @return
     */
    public static List<Activity> getActivitiesWithCompletedScriptType(
            FlowContainer flowContainer, String scriptGrammar) {
        return getActivitiesWithScriptType(flowContainer,
                scriptGrammar,
                AuditEventType.COMPLETED_LITERAL);
    }

    /**
     * Returns list of activities with deadline expired script type.
     * 
     * @param flowContainer
     * @param scriptGrammar
     * @return
     */
    public static List<Activity> getActivitiesWithDeadlineExpiredScriptType(
            FlowContainer flowContainer, String scriptGrammar) {
        return getActivitiesWithScriptType(flowContainer,
                scriptGrammar,
                AuditEventType.DEADLINE_EXPIRED_LITERAL);
    }

    /**
     * Returns list of activities with cancelled script type.
     * 
     * @param flowContainer
     * @param scriptGrammar
     * @return
     */
    public static List<Activity> getActivitiesWithCancelledScriptType(
            FlowContainer flowContainer, String scriptGrammar) {
        return getActivitiesWithScriptType(flowContainer,
                scriptGrammar,
                AuditEventType.CANCELLED_LITERAL);
    }

    /**
     * @param flowContainer
     * @param scriptGrammar
     * @param auditEventType
     * @return
     */
    private static List<Activity> getActivitiesWithScriptType(
            FlowContainer flowContainer, String scriptGrammar,
            AuditEventType auditEventType) {
        List<Activity> toReturn = null;
        List<Activity> activityList = flowContainer.getActivities();
        if (activityList == null) {
            return toReturn;
        }
        for (Activity activity : activityList) {
            Audit audit = TaskObjectUtil.getAudit(activity);
            if (audit != null) {
                Expression script =
                        TaskObjectUtil.getAuditScriptExpression(auditEventType,
                                audit);
                if (script == null) {
                    continue;
                }
                if (scriptGrammar.equals(script.getScriptGrammar())) {
                    if (toReturn == null) {
                        toReturn = new ArrayList<Activity>();
                    }
                    toReturn.add(activity);
                }
            }
        }
        return toReturn;
    }

    /**
     * @param flowContainer
     * @return
     */
    public static List<Activity> getActivitiesWithDurationCalculationScripts(
            FlowContainer flowContainer) {
        List<Activity> toReturn = new ArrayList<Activity>();
        List<Activity> activityList = flowContainer.getActivities();
        if (activityList == null) {
            return toReturn;
        }
        for (Activity activity : activityList) {
            DurationCalculation durationCalculation =
                    (DurationCalculation) Xpdl2ModelUtil
                            .getOtherElement(activity,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_DurationCalculation());
            if (durationCalculation != null) {
                toReturn.add(activity);
            }
        }
        return toReturn;
    }

    /**
     * @param activity
     * @return
     */
    public static boolean isActivityWithDurationCalculationScripts(
            Activity activity) {
        boolean toReturn = false;
        if (activity != null) {
            DurationCalculation durationCalculation =
                    (DurationCalculation) Xpdl2ModelUtil
                            .getOtherElement(activity,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_DurationCalculation());
            if (durationCalculation != null) {
                toReturn = true;
            }
        }
        return toReturn;
    }

    /**
     * Get list of Task with Standard loop expression with the specific grammar.
     * 
     * @param flowContainer
     * @param scriptGrammar
     * @return
     */
    public static List<Activity> getActivityListWithStdLoopExpr(
            FlowContainer flowContainer, String scriptGrammar) {
        List<Activity> toReturn = null;
        List<Activity> activityList = flowContainer.getActivities();
        if (activityList == null) {
            return toReturn;
        }
        for (Activity activity : activityList) {
            boolean taskStdLoopExprWithGrammar =
                    isActivityStdLoopExprHasGrammar(activity, scriptGrammar);
            if (taskStdLoopExprWithGrammar) {
                if (toReturn == null) {
                    toReturn = new ArrayList<Activity>();
                }
                toReturn.add(activity);
            }
        }
        return toReturn;
    }

    /**
     * Get list of Task with MI loop expression with the specific grammar.
     * 
     * @param flowContainer
     * @param scriptGrammar
     * @return
     */
    public static List<Activity> getActivityListWithMILoopExpr(
            FlowContainer flowContainer, String scriptGrammar) {
        List<Activity> toReturn = null;
        List<Activity> activityList = flowContainer.getActivities();
        if (activityList == null) {
            return toReturn;
        }
        for (Activity activity : activityList) {
            boolean taskMILoopExprWithGrammar =
                    isActivityMILoopExprHasGrammar(activity, scriptGrammar);
            if (taskMILoopExprWithGrammar) {
                if (toReturn == null) {
                    toReturn = new ArrayList<Activity>();
                }
                toReturn.add(activity);
            }
        }
        return toReturn;
    }

    /**
     * Get list of Task with MI complex exit expression with the specific
     * grammar.
     * 
     * @param flowContainer
     * @param scriptGrammar
     * @return
     */
    public static List<Activity> getActivityListWithMIComplexExitExpr(
            FlowContainer flowContainer, String scriptGrammar) {
        List<Activity> toReturn = null;
        List<Activity> activityList = flowContainer.getActivities();
        if (activityList == null) {
            return toReturn;
        }
        for (Activity activity : activityList) {
            boolean taskComplexExitExprWithGrammar =
                    isActivityMIComplexExitExprHasGrammar(activity,
                            scriptGrammar);
            if (taskComplexExitExprWithGrammar) {
                if (toReturn == null) {
                    toReturn = new ArrayList<Activity>();
                }
                toReturn.add(activity);
            }
        }
        return toReturn;
    }

    /**
     * Get list of Task with MI additional instances expression with the
     * specific grammar.
     * 
     * @param flowContainer
     * @param scriptGrammar
     * @return
     */
    public static List<Activity> getActivityListWithMIAdditionalInstancesExpr(
            FlowContainer flowContainer, String scriptGrammar) {
        List<Activity> toReturn = null;
        List<Activity> activityList = flowContainer.getActivities();
        if (activityList == null) {
            return toReturn;
        }
        for (Activity activity : activityList) {
            boolean actAdditionalInstancesExprWithGrammar =
                    isActivityMIAdditionalInstancesExprHasGrammar(activity,
                            scriptGrammar);
            if (actAdditionalInstancesExprWithGrammar) {
                if (toReturn == null) {
                    toReturn = new ArrayList<Activity>();
                }
                toReturn.add(activity);
            }
        }
        return toReturn;
    }

    /**
     * Get list of Bw Service Task activities.
     * 
     * @param flowContainer
     * @param scriptGrammar
     * @return
     */
    public static List<Activity> getBwServiceTasks(FlowContainer flowContainer) {
        return getServiceTasks(flowContainer, BWSERVICE_SERVICE_TYPE);
    }

    /**
     * Get list of Web Service Task activities.
     * 
     * @param flowContainer
     * @param scriptGrammar
     * @return
     */
    public static List<Activity> getWebServiceTasks(FlowContainer flowContainer) {
        return getServiceTasks(flowContainer, WEBSERVICE_SERVICE_TYPE);
    }

    /**
     * Get list of Java Service Task activities.
     * 
     * @param flowContainer
     * @param scriptGrammar
     * @return
     */
    public static List<Activity> getJavaServiceTasks(FlowContainer flowContainer) {
        return getServiceTasks(flowContainer, JAVA_SERVICE_TYPE);
    }

    /**
     * Get list of Service Task activities that are of the given type.
     * 
     * @param flowContainer
     * @param serviceType
     * @return
     */
    public static List<Activity> getServiceTasks(FlowContainer flowContainer,
            String serviceType) {
        List<Activity> toReturn = new ArrayList<Activity>();
        List<Activity> activityList = flowContainer.getActivities();
        if (activityList == null) {
            return toReturn;
        }
        for (Activity activity : activityList) {
            Implementation implementation = activity.getImplementation();
            if (implementation instanceof Task) {
                Task task = (Task) implementation;
                TaskService service = task.getTaskService();
                if (service != null) {
                    String type =
                            (String) Xpdl2ModelUtil
                                    .getOtherAttribute(service,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ImplementationType());
                    if (type != null && serviceType != null
                            && type.equals(serviceType)) {
                        toReturn.add(activity);
                    }
                }
            }
        }
        return toReturn;
    }

    /**
     * Get list of Service Event activities that are of the given type.
     * 
     * @param flowContainer
     * @param implementationType
     * @return
     */
    public static List<Activity> getEvents(FlowContainer flowContainer,
            String implementationType) {
        List<Activity> toReturn = new ArrayList<Activity>();
        List<Activity> activityList = flowContainer.getActivities();
        if (activityList == null) {
            return toReturn;
        }
        for (Activity activity : activityList) {
            if (activity.getEvent() != null) {
                ImplementationType eventImplementationType =
                        EventObjectUtil.getEventImplementationType(activity);
                if (eventImplementationType != null
                        && implementationType.equals(eventImplementationType)) {
                    toReturn.add(activity);
                }
            }
        }
        return toReturn;
    }

    /**
     * Get list of events.
     * 
     * @param flowContainer
     * @param serviceType
     * @return
     */
    public static List<Event> getEvents(FlowContainer flowContainer) {
        List<Event> toReturn = new ArrayList<Event>();
        List<Activity> activityList = flowContainer.getActivities();
        if (activityList == null) {
            return toReturn;
        }
        for (Activity activity : activityList) {
            Event event = activity.getEvent();
            if (event != null) {
                toReturn.add(event);
            }
        }
        return toReturn;
    }

    /**
     * Get list of send tasks.
     * 
     * @param flowContainer
     * @param serviceType
     * @return
     */
    public static List<TaskSend> getSendTask(FlowContainer flowContainer) {
        List<TaskSend> toReturn = new ArrayList<TaskSend>();
        List<Activity> activityList = flowContainer.getActivities();
        if (activityList == null) {
            return toReturn;
        }
        for (Activity activity : activityList) {
            Implementation implementation = activity.getImplementation();
            if (implementation instanceof Task) {
                Task task = (Task) implementation;
                TaskSend taskSend = task.getTaskSend();
                if (taskSend != null) {
                    toReturn.add(taskSend);
                }
            }
        }
        return toReturn;
    }

    /**
     * Get list of receive tasks.
     * 
     * @param flowContainer
     * @param serviceType
     * @return
     */
    public static List<TaskReceive> getReceiveTask(FlowContainer flowContainer) {
        List<TaskReceive> toReturn = new ArrayList<TaskReceive>();
        List<Activity> activityList = flowContainer.getActivities();
        if (activityList == null) {
            return toReturn;
        }
        for (Activity activity : activityList) {
            Implementation implementation = activity.getImplementation();
            if (implementation instanceof Task) {
                Task task = (Task) implementation;
                TaskReceive taskReceive = task.getTaskReceive();
                if (taskReceive != null) {
                    toReturn.add(taskReceive);
                }
            }
        }
        return toReturn;
    }

    /**
     * Get list of Web Service Task activities that have script defined in the
     * given grammar.
     * 
     * @param flowContainer
     * @param scriptGrammar
     * @return
     */
    public static List<Activity> getSubProcTasks(FlowContainer flowContainer) {
        List<Activity> toReturn = new ArrayList<Activity>();
        List<Activity> activityList = flowContainer.getActivities();
        if (activityList == null) {
            return toReturn;
        }
        for (Activity activity : activityList) {
            Implementation implementation = activity.getImplementation();
            if (implementation instanceof SubFlow) {
                toReturn.add(activity);
            }
        }
        return toReturn;
    }

    /**
     * Get list of Web Service Task activities that have script defined in the
     * given grammar.
     * 
     * @param flowContainer
     * @param scriptGrammar
     * @return
     */
    public static List<Activity> getDecFlowTasks(FlowContainer flowContainer) {
        List<Activity> toReturn = new ArrayList<Activity>();
        List<Activity> activityList = flowContainer.getActivities();
        if (activityList == null) {
            return toReturn;
        }
        for (Activity activity : activityList) {
            if (DecisionFlowUtil.isDecisionServiceTask(activity)) {
                toReturn.add(activity);
            }
        }
        return toReturn;
    }

    /**
     * Get list of Script Information for the given activity have script defined
     * in the given grammar, and for the given direction.
     * 
     * Note: this will return just the unmapped script informations
     * 
     * @param activity
     * @param scriptGrammar
     * @param direction
     * @return
     */
    public static List<ScriptInformation> getScriptInformationTasksWithScriptType(
            Activity activity, String scriptGrammar, String direction) {
        List<ScriptInformation> scriptInformationList =
                new ArrayList<ScriptInformation>();
        if (activity != null) {
            List otherElementScriptList =
                    Xpdl2ModelUtil.getOtherElementList(activity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_Script());
            for (Iterator iterator = otherElementScriptList.iterator(); iterator
                    .hasNext();) {
                Object obj = iterator.next();
                if (obj instanceof ScriptInformation) {
                    ScriptInformation scriptInformation =
                            (ScriptInformation) obj;
                    if (scriptInformation != null
                            && scriptInformation.getName() != null) {
                        Expression expression =
                                scriptInformation.getExpression();
                        if (expression != null) {
                            String expressionScriptGrammar =
                                    expression.getScriptGrammar();
                            if (expressionScriptGrammar != null
                                    && expressionScriptGrammar
                                            .equals(scriptGrammar)) {
                                DirectionType scriptInfoDirection =
                                        scriptInformation.getDirection();
                                if (direction != null) {
                                    if (direction
                                            .equals(DirectionType.OUT_LITERAL
                                                    .getLiteral())) {
                                        if (scriptInfoDirection != null
                                                && scriptInfoDirection
                                                        .getLiteral()
                                                        .equals(DirectionType.OUT_LITERAL
                                                                .getLiteral())) {
                                            scriptInformationList
                                                    .add(scriptInformation);
                                        }
                                    } else {
                                        if (scriptInfoDirection == null
                                                || !scriptInfoDirection
                                                        .getLiteral()
                                                        .equals(DirectionType.OUT_LITERAL
                                                                .getLiteral())) {
                                            scriptInformationList
                                                    .add(scriptInformation);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return scriptInformationList;
    }

    /**
     * Get list of Data Mappings that have script defined in the given grammar
     * for activities that are ServiceTasks.
     * 
     * @param flowContainer
     * @param scriptGrammar
     * @return
     */
    public static List<DataMapping> getScriptDataMappingForServiceWithScriptType(
            Activity activity, DirectionType direction, String scriptGrammar) {
        List dataMappingList = Collections.EMPTY_LIST;
        if (activity != null) {
            Implementation implementation = activity.getImplementation();
            if (implementation instanceof Task) {
                Task task = (Task) implementation;
                if (task != null) {
                    TaskService taskService = task.getTaskService();
                    if (taskService != null) {
                        Message message;
                        if (DirectionType.IN_LITERAL.equals(direction)) {
                            message = taskService.getMessageIn();
                        } else {
                            message = taskService.getMessageOut();
                        }
                        if (message != null) {
                            dataMappingList =
                                    getDataMappingsFromMessage(scriptGrammar,
                                            message,
                                            direction);
                        }
                    }
                }
            }
        }
        return dataMappingList;
    }

    /**
     * @param direction
     * @param scriptGrammar
     * @param message
     * @param dataMappingList
     */
    public static List<DataMapping> getDataMappingsFromMessage(
            String scriptGrammar, Message message, DirectionType direction) {
        List<DataMapping> dataMappingList = new ArrayList<DataMapping>();
        List<DataMapping> tempDataMappingList = message.getDataMappings();
        if (tempDataMappingList != null) {
            for (Iterator<?> iterator = tempDataMappingList.iterator(); iterator
                    .hasNext();) {
                Object obj = iterator.next();
                if (obj instanceof DataMapping) {
                    DataMapping dataMapping = (DataMapping) obj;
                    if (dataMapping != null) {
                        if (isAScriptMapping(dataMapping)) {
                            Expression expression;
                            if (DirectionType.IN_LITERAL.equals(direction)) {
                                expression = dataMapping.getActual();
                            } else {
                                expression =
                                        getScriptInformationFromDataMapping(dataMapping)
                                                .getExpression();
                            }
                            if (expression != null) {
                                if (expression.getScriptGrammar() != null
                                        && expression.getScriptGrammar()
                                                .equals(scriptGrammar)) {
                                    dataMappingList.add(dataMapping);
                                }
                            }
                        }
                    }
                }
            }
        }
        return dataMappingList;
    }

    /**
     * Get list of Input Data Mappings that have script defined in the given
     * grammar for activities that are SubProcess.
     * 
     * @param flowContainer
     * @param scriptGrammar
     * @return
     */
    public static List<DataMapping> getScriptDataInputMappingForSubProcWithScriptType(
            Activity activity, String scriptGrammar) {
        List<DataMapping> dataMappingList = new ArrayList<DataMapping>();
        if (activity != null) {
            Implementation implementation = activity.getImplementation();
            if (implementation instanceof SubFlow) {
                SubFlow subFlow = (SubFlow) implementation;
                if (subFlow != null) {
                    EList tempDataMappingList = subFlow.getDataMappings();
                    if (tempDataMappingList != null) {
                        for (Iterator iterator = tempDataMappingList.iterator(); iterator
                                .hasNext();) {
                            Object obj = iterator.next();
                            if (obj instanceof DataMapping) {
                                DataMapping dataMapping = (DataMapping) obj;
                                if (dataMapping != null) {
                                    DirectionType directionType =
                                            dataMapping.getDirection();
                                    if (directionType != null) {
                                        if (directionType.getLiteral() != null
                                                && directionType
                                                        .getLiteral()
                                                        .equals(DirectionType.IN_LITERAL
                                                                .getLiteral())) {
                                            if (isAScriptMapping(dataMapping)) {
                                                Expression actual =
                                                        dataMapping.getActual();
                                                if (actual != null) {
                                                    if (actual
                                                            .getScriptGrammar() != null
                                                            && actual
                                                                    .getScriptGrammar()
                                                                    .equals(scriptGrammar)) {
                                                        dataMappingList
                                                                .add(dataMapping);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return dataMappingList;
    }

    /**
     * Get list of all Data Mappings that have script defined in the given
     * grammar for activities that are SubProcess.
     * 
     * @param flowContainer
     * @param scriptGrammar
     * @return
     */
    public static List<DataMapping> getAllSubProcessTaskScriptDataMappings(
            Activity activity, String scriptGrammar) {
        List<DataMapping> dataMappingList = new ArrayList<DataMapping>();
        if (activity != null) {
            Implementation implementation = activity.getImplementation();
            if (implementation instanceof SubFlow) {
                SubFlow subFlow = (SubFlow) implementation;
                if (subFlow != null) {
                    EList tempDataMappingList = subFlow.getDataMappings();
                    if (tempDataMappingList != null) {
                        dataMappingList =
                                ProcessScriptUtil
                                        .getScriptDataMappings(tempDataMappingList,
                                                scriptGrammar);
                    }
                }
            }
        }
        return dataMappingList;
    }

    /**
     * Get list of all Data Mappings of the activity for a given grammar
     * 
     * Activities supported: - Service Task - Sub Process Task - Send Task -
     * Receive Task - Start Event - Intermediate Event - End Event - Implemented
     * Start Event - Implemented Intermediate Event
     * 
     * 
     * @param activity
     * @return
     */
    public static List<DataMapping> getActivityDataMappingList(
            Activity activity, String scriptGrammar) {
        List<DataMapping> dataMappingList =
                Xpdl2ModelUtil.getDataMappings(activity,
                        DirectionType.IN_LITERAL);
        dataMappingList.addAll(Xpdl2ModelUtil.getDataMappings(activity,
                DirectionType.OUT_LITERAL));

        List<DataMapping> returnMappingList = new ArrayList<DataMapping>();
        if (dataMappingList != null) {
            for (DataMapping dataMapping : dataMappingList) {
                String grammar =
                        ProcessScriptUtil.getDataMappingGrammar(dataMapping);
                if (grammar != null && grammar.equals(scriptGrammar)) {
                    returnMappingList.add(dataMapping);
                }
            }
        }
        return returnMappingList;
    }

    /**
     * Get list of all Data Mappings of the service task for a given grammar
     * 
     * 
     * @param activity
     * @return
     */
    public static List<DataMapping> getServiceTaskDataMappingList(
            TaskService taskService, String scriptGrammar) {
        List<DataMapping> dataMappingList = new ArrayList<DataMapping>();
        if (taskService != null) {
            List<DataMapping> allGrammarDataMappingList =
                    new ArrayList<DataMapping>();
            Message messageIn = taskService.getMessageIn();
            if (messageIn != null && messageIn.getDataMappings() != null) {
                allGrammarDataMappingList.addAll(messageIn.getDataMappings());
            }
            Message messageOut = taskService.getMessageOut();
            if (messageOut != null && messageOut.getDataMappings() != null) {
                allGrammarDataMappingList.addAll(messageOut.getDataMappings());
            }
            for (DataMapping dataMapping : allGrammarDataMappingList) {
                String grammar =
                        ProcessScriptUtil.getDataMappingGrammar(dataMapping);
                if (grammar != null && grammar.equals(scriptGrammar)) {
                    dataMappingList.add(dataMapping);
                }
            }
        }
        return dataMappingList;
    }

    /**
     * Get list of all Data Mappings of the send task for a given grammar
     * 
     * 
     * @param activity
     * @return
     */
    public static List<DataMapping> getSendTaskDataMappingList(
            TaskSend taskSend, String scriptGrammar) {
        List<DataMapping> dataMappingList = new ArrayList<DataMapping>();
        if (taskSend != null) {
            List<DataMapping> allGrammarDataMappingList =
                    new ArrayList<DataMapping>();
            Message message = taskSend.getMessage();
            if (message != null && message.getDataMappings() != null) {
                allGrammarDataMappingList.addAll(message.getDataMappings());
            }
            for (DataMapping dataMapping : allGrammarDataMappingList) {
                String grammar =
                        ProcessScriptUtil.getDataMappingGrammar(dataMapping);
                if (grammar != null && grammar.equals(scriptGrammar)) {
                    dataMappingList.add(dataMapping);
                }
            }
        }
        return dataMappingList;
    }

    /**
     * Get list of all Data Mappings of the receive task for a given grammar
     * 
     * 
     * @param activity
     * @return
     */
    public static List<DataMapping> getReceiveTaskDataMappingList(
            TaskReceive taskReceive, String scriptGrammar) {
        List<DataMapping> dataMappingList = new ArrayList<DataMapping>();
        if (taskReceive != null) {
            List<DataMapping> allGrammarDataMappingList =
                    new ArrayList<DataMapping>();
            Message message = taskReceive.getMessage();
            if (message != null && message.getDataMappings() != null) {
                allGrammarDataMappingList.addAll(message.getDataMappings());
            }
            for (DataMapping dataMapping : allGrammarDataMappingList) {
                String grammar =
                        ProcessScriptUtil.getDataMappingGrammar(dataMapping);
                if (grammar != null && grammar.equals(scriptGrammar)) {
                    dataMappingList.add(dataMapping);
                }
            }
        }
        return dataMappingList;
    }

    /**
     * Get list of all Data Mappings that have script defined in the given
     * grammar for activities that are Event.
     * 
     * @param flowContainer
     * @param scriptGrammar
     * @return
     * @deprecated use {@link ProcessScriptUtil getAllDataMappings} instead
     */
    @Deprecated
    public static List<DataMapping> getAllEventScriptDataMappings(
            Activity activity, String scriptGrammar) {
        return getAllDataMappings(activity, scriptGrammar);
    }

    /**
     * @param activity
     * @param scriptGrammar
     * @return
     */
    private static List<DataMapping> getAllDataMappings(Activity activity,
            String scriptGrammar) {
        List<DataMapping> dataMappingList = new ArrayList<DataMapping>();
        if (activity != null) {
            /*
             * XPD-3671 Didn't use to take correlation mappings into account -
             * Xpdl2ModelUtil.getDataMappings() does!
             */
            List<DataMapping> tempDataMappingList =
                    Xpdl2ModelUtil.getDataMappings(activity,
                            DirectionType.IN_LITERAL);
            tempDataMappingList.addAll(Xpdl2ModelUtil.getDataMappings(activity,
                    DirectionType.OUT_LITERAL));

            if (tempDataMappingList != null) {
                dataMappingList =
                        getScriptDataMappings(tempDataMappingList,
                                scriptGrammar);
            }
        }
        return dataMappingList;
    }

    /**
     * Get list of all Data Mappings that are of type script for the given
     * grammar.
     * 
     * @param flowContainer
     * @param scriptGrammar
     * @return
     */
    private static List<DataMapping> getScriptDataMappings(
            List dataMappingList, String scriptGrammar) {
        List<DataMapping> scriptDataMappingList = new ArrayList<DataMapping>();
        if (dataMappingList != null) {
            for (Iterator iterator = dataMappingList.iterator(); iterator
                    .hasNext();) {
                Object obj = iterator.next();
                if (obj instanceof DataMapping) {
                    DataMapping dataMapping = (DataMapping) obj;
                    if (dataMapping != null) {
                        if (isAScriptMapping(dataMapping)) {
                            DirectionType directionType =
                                    dataMapping.getDirection();
                            if (directionType != null) {
                                if (directionType.getLiteral() != null
                                        && directionType
                                                .getLiteral()
                                                .equals(DirectionType.OUT_LITERAL
                                                        .getLiteral())) {

                                    Object scriptInfoObj =
                                            Xpdl2ModelUtil
                                                    .getOtherElement(dataMapping,
                                                            XpdExtensionPackage.eINSTANCE
                                                                    .getDocumentRoot_Script());
                                    if (scriptInfoObj instanceof ScriptInformation) {
                                        ScriptInformation scriptInformation =
                                                (ScriptInformation) scriptInfoObj;
                                        Expression expression =
                                                scriptInformation
                                                        .getExpression();
                                        if (expression != null) {
                                            if (expression.getScriptGrammar() != null
                                                    && expression
                                                            .getScriptGrammar()
                                                            .equals(scriptGrammar)) {
                                                scriptDataMappingList
                                                        .add(dataMapping);
                                            }
                                        }
                                    }
                                } else if (directionType.getLiteral() != null
                                        && directionType
                                                .getLiteral()
                                                .equals(DirectionType.IN_LITERAL
                                                        .getLiteral())) {
                                    Expression actual = dataMapping.getActual();
                                    if (actual != null) {
                                        if (actual.getScriptGrammar() != null
                                                && actual.getScriptGrammar()
                                                        .equals(scriptGrammar)) {
                                            scriptDataMappingList
                                                    .add(dataMapping);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return scriptDataMappingList;
    }

    /**
     * Get list of all Data Mappings that have script defined in the given
     * grammar for activities that are Send Task.
     * 
     * @param flowContainer
     * @param scriptGrammar
     * @return
     */
    public static List<DataMapping> getAllSendTaskScriptDataMappings(
            Activity activity, String scriptGrammar) {
        List<DataMapping> dataMappingList = new ArrayList<DataMapping>();
        if (activity != null) {
            Implementation implementation = activity.getImplementation();
            if (implementation instanceof Task) {
                Task task = (Task) implementation;
                TaskSend taskSend = task.getTaskSend();
                if (taskSend != null && taskSend.getMessage() != null) {
                    EList tempDataMappingList =
                            taskSend.getMessage().getDataMappings();
                    if (tempDataMappingList != null) {
                        dataMappingList =
                                getScriptDataMappings(tempDataMappingList,
                                        scriptGrammar);
                    }
                }
            }
        }
        return dataMappingList;
    }

    /**
     * Get list of all Data Mappings that have script defined in the given
     * grammar for activities that are Send Task.
     * 
     * @param flowContainer
     * @param scriptGrammar
     * @return
     * @deprecated use {@link ProcessScriptUtil getAllDataMappings} instead
     */
    @Deprecated
    public static List<DataMapping> getAllReceiveTaskScriptDataMappings(
            Activity activity, String scriptGrammar) {
        return getAllDataMappings(activity, scriptGrammar);
    }

    /**
     * Get list of Output Data Mappings that have script defined in the given
     * grammar for activities that are SubProcess.
     * 
     * @param flowContainer
     * @param scriptGrammar
     * @return
     */
    public static List<DataMapping> getScriptDataOutputMappingForSubProcWithScriptType(
            Activity activity, String scriptGrammar) {
        List<DataMapping> dataMappingList = new ArrayList<DataMapping>();
        if (activity != null) {
            Implementation implementation = activity.getImplementation();
            if (implementation instanceof SubFlow) {
                SubFlow subFlow = (SubFlow) implementation;
                if (subFlow != null) {
                    EList tempDataMappingList = subFlow.getDataMappings();
                    if (tempDataMappingList != null) {
                        for (Iterator iterator = tempDataMappingList.iterator(); iterator
                                .hasNext();) {
                            Object obj = iterator.next();
                            if (obj instanceof DataMapping) {
                                DataMapping dataMapping = (DataMapping) obj;
                                if (dataMapping != null) {
                                    DirectionType directionType =
                                            dataMapping.getDirection();
                                    if (directionType != null) {
                                        if (directionType.getLiteral() != null
                                                && directionType
                                                        .getLiteral()
                                                        .equals(DirectionType.OUT_LITERAL
                                                                .getLiteral())) {
                                            if (isAScriptMapping(dataMapping)) {

                                                Object scriptInfoObj =
                                                        Xpdl2ModelUtil
                                                                .getOtherElement(dataMapping,
                                                                        XpdExtensionPackage.eINSTANCE
                                                                                .getDocumentRoot_Script());
                                                if (scriptInfoObj instanceof ScriptInformation) {
                                                    ScriptInformation scriptInformation =
                                                            (ScriptInformation) scriptInfoObj;
                                                    Expression expression =
                                                            scriptInformation
                                                                    .getExpression();
                                                    if (expression != null) {
                                                        if (expression
                                                                .getScriptGrammar() != null
                                                                && expression
                                                                        .getScriptGrammar()
                                                                        .equals(scriptGrammar)) {
                                                            dataMappingList
                                                                    .add(dataMapping);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return dataMappingList;
    }

    public static ScriptInformation getScriptInformationFromDataMapping(
            DataMapping mapping) {
        ScriptInformation scriptInformation = null;
        if (isAScriptMapping(mapping)) {
            Object obj =
                    Xpdl2ModelUtil.getOtherElement(mapping,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_Script());
            if (obj instanceof ScriptInformation) {
                scriptInformation = (ScriptInformation) obj;
            }
        }
        return scriptInformation;
    }

    /**
     * Get list of Output Data Mappings that have script defined in the given
     * grammar for activities that are SubProcess.
     * 
     * @param flowContainer
     * @param scriptGrammar
     * @return
     */
    public static List<DataMapping> getScriptDataOutputMappingForDecFlowWithScriptType(
            Activity activity, String scriptGrammar) {
        List<DataMapping> dataMappingList = new ArrayList<DataMapping>();
        if (activity != null) {
            SubFlow decServiceFlow =
                    DecisionFlowUtil.getDecisionServiceExt(activity);
            if (decServiceFlow != null) {
                EList tempDataMappingList = decServiceFlow.getDataMappings();
                if (tempDataMappingList != null) {
                    for (Iterator iterator = tempDataMappingList.iterator(); iterator
                            .hasNext();) {
                        Object obj = iterator.next();
                        if (obj instanceof DataMapping) {
                            DataMapping dataMapping = (DataMapping) obj;
                            if (dataMapping != null) {
                                DirectionType directionType =
                                        dataMapping.getDirection();
                                if (directionType != null) {
                                    if (directionType.getLiteral() != null
                                            && directionType
                                                    .getLiteral()
                                                    .equals(DirectionType.OUT_LITERAL
                                                            .getLiteral())) {
                                        if (isAScriptMapping(dataMapping)) {

                                            Object scriptInfoObj =
                                                    Xpdl2ModelUtil
                                                            .getOtherElement(dataMapping,
                                                                    XpdExtensionPackage.eINSTANCE
                                                                            .getDocumentRoot_Script());
                                            if (scriptInfoObj instanceof ScriptInformation) {
                                                ScriptInformation scriptInformation =
                                                        (ScriptInformation) scriptInfoObj;
                                                Expression expression =
                                                        scriptInformation
                                                                .getExpression();
                                                if (expression != null) {
                                                    if (expression
                                                            .getScriptGrammar() != null
                                                            && expression
                                                                    .getScriptGrammar()
                                                                    .equals(scriptGrammar)) {
                                                        dataMappingList
                                                                .add(dataMapping);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return dataMappingList;
    }

    /**
     * Get list of Input Data Mappings that have script defined in the given
     * grammar for activities that are SubProcess.
     * 
     * @param flowContainer
     * @param scriptGrammar
     * @return
     */
    public static List<DataMapping> getScriptDataInputMappingForDecFlowWithScriptType(
            Activity activity, String scriptGrammar) {
        List<DataMapping> dataMappingList = new ArrayList<DataMapping>();
        if (activity != null) {
            SubFlow decServiceFlow =
                    DecisionFlowUtil.getDecisionServiceExt(activity);
            if (decServiceFlow != null) {
                EList tempDataMappingList = decServiceFlow.getDataMappings();
                if (tempDataMappingList != null) {
                    for (Iterator iterator = tempDataMappingList.iterator(); iterator
                            .hasNext();) {
                        Object obj = iterator.next();
                        if (obj instanceof DataMapping) {
                            DataMapping dataMapping = (DataMapping) obj;
                            if (dataMapping != null) {
                                DirectionType directionType =
                                        dataMapping.getDirection();
                                if (directionType != null) {
                                    if (directionType.getLiteral() != null
                                            && directionType
                                                    .getLiteral()
                                                    .equals(DirectionType.IN_LITERAL
                                                            .getLiteral())) {
                                        if (isAScriptMapping(dataMapping)) {
                                            Expression actual =
                                                    dataMapping.getActual();
                                            if (actual != null) {
                                                if (actual.getScriptGrammar() != null
                                                        && actual
                                                                .getScriptGrammar()
                                                                .equals(scriptGrammar)) {
                                                    dataMappingList
                                                            .add(dataMapping);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return dataMappingList;
    }

    /**
     * Check if this data mapping is a script mapping
     * 
     * @param mapping
     * @return
     */
    public static boolean isAScriptMapping(DataMapping mapping) {
        boolean isScriptMapping = false;
        if (mapping != null) {
            Object scriptInfoObj =
                    Xpdl2ModelUtil.getOtherElement(mapping,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_Script());
            if (scriptInfoObj instanceof ScriptInformation) {
                isScriptMapping = true;
            }
        }
        return isScriptMapping;
    }

    /**
     * Check if this activity is a script task with a script defined in the
     * given grammar.
     * 
     * @param activity
     * @param scriptGrammar
     * @return
     */
    public static boolean isScriptTaskWithScriptType(Activity activity,
            String scriptGrammar) {
        boolean toReturn = false;
        Implementation implementation = activity.getImplementation();
        if (implementation instanceof Task) {
            Task task = (Task) implementation;
            TaskScript taskScript = task.getTaskScript();
            if (taskScript == null) {
                return toReturn;
            }
            Expression expr = taskScript.getScript();
            if (expr == null) {
                return toReturn;
            }
            if (scriptGrammar.equalsIgnoreCase(expr.getScriptGrammar())) {
                toReturn = true;
            }
        }
        return toReturn;
    }

    public static boolean isScriptPerformerWithScriptType(
            ProcessRelevantData processRelevantData, String scriptGrammar) {
        boolean toReturn = false;
        if (null != processRelevantData) {
            Object otherElement =
                    Xpdl2ModelUtil.getOtherElement(processRelevantData,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ConditionalParticipant());
            if (otherElement instanceof ConditionalParticipant) {
                Expression expression =
                        ((ConditionalParticipant) otherElement)
                                .getPerformerScript();
                if (null == expression) {
                    return toReturn;
                }
                if (scriptGrammar.equalsIgnoreCase(expression
                        .getScriptGrammar())) {
                    toReturn = true;
                }
            }
        }
        return toReturn;
    }

    /**
     * Check if this activity has an initiated script defined in the given
     * grammar.
     * 
     * @param activity
     * @param scriptGrammar
     * @return
     */
    public static boolean isActivityWithInitiatedScriptType(Activity activity,
            String scriptGrammar) {
        return ProcessScriptUtil.isActivityWithAuditScriptType(activity,
                scriptGrammar,
                AuditEventType.INITIATED_LITERAL);
    }

    /**
     * Check if this activity has an completed script defined in the given
     * grammar.
     * 
     * @param activity
     * @param scriptGrammar
     * @return
     */
    public static boolean isActivityWithCompletedScriptType(Activity activity,
            String scriptGrammar) {
        return ProcessScriptUtil.isActivityWithAuditScriptType(activity,
                scriptGrammar,
                AuditEventType.COMPLETED_LITERAL);
    }

    /**
     * Check if this activity has an DeadlineExpired script defined in the given
     * grammar.
     * 
     * @param activity
     * @param scriptGrammar
     * @return
     */
    public static boolean isActivityWithDeadlineExpiredScriptType(
            Activity activity, String scriptGrammar) {
        return ProcessScriptUtil.isActivityWithAuditScriptType(activity,
                scriptGrammar,
                AuditEventType.DEADLINE_EXPIRED_LITERAL);
    }

    /**
     * Check if this activity has an Cancelled script defined in the given
     * grammar.
     * 
     * @param activity
     * @param scriptGrammar
     * @return
     */
    public static boolean isActivityWithCancelledScriptType(Activity activity,
            String scriptGrammar) {
        return ProcessScriptUtil.isActivityWithAuditScriptType(activity,
                scriptGrammar,
                AuditEventType.CANCELLED_LITERAL);
    }

    private static boolean isActivityWithAuditScriptType(Activity activity,
            String scriptGrammar, AuditEventType auditEventType) {
        boolean toReturn = false;
        if (activity != null) {
            Expression script =
                    ProcessScriptUtil.getAuditScriptExpression(activity,
                            auditEventType);
            if (script == null) {
                return toReturn;
            }
            if (scriptGrammar.equalsIgnoreCase(script.getScriptGrammar())) {
                toReturn = true;
            }
        }
        return toReturn;
    }

    /**
     * Check if this activity is a sub process task with any script defined in
     * the given grammar.
     * 
     * @param activity
     * @param scriptGrammar
     * @return
     */
    public static boolean isSubProcessTaskWithScriptType(Activity activity,
            String scriptGrammar) {
        boolean toReturn = false;
        Implementation implementation = activity.getImplementation();
        if (implementation instanceof SubFlow) {
            List<ScriptInformation> subProcessTaskScriptInformations =
                    getActivityUnmappedScriptInformations(activity,
                            scriptGrammar);
            if (subProcessTaskScriptInformations != null
                    && !subProcessTaskScriptInformations.isEmpty()) {
                toReturn = true;
            }
            List<DataMapping> serviceDataMappings =
                    getActivityDataMappingList(activity, scriptGrammar);
            if (serviceDataMappings != null && !serviceDataMappings.isEmpty()) {
                toReturn = true;
            }
        }
        return toReturn;
    }

    /**
     * Check if this activity is a java service task with any script defined in
     * the given grammar.
     * 
     * @param activity
     * @param scriptGrammar
     * @return
     */
    public static boolean isJavaServiceTaskWithScriptType(Activity activity,
            String scriptGrammar) {
        boolean toReturn = false;
        Implementation implementation = activity.getImplementation();
        if (implementation instanceof Task) {
            Task task = (Task) implementation;
            TaskService taskService = task.getTaskService();
            if (taskService == null) {
                return toReturn;
            }
            String type =
                    (String) Xpdl2ModelUtil.getOtherAttribute(taskService,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ImplementationType());
            if (type != null && type.equals(JAVA_SERVICE_TYPE)) {
                toReturn = isServiceTaskWithScriptType(activity, scriptGrammar);
            }
        }
        return toReturn;
    }

    /**
     * Check if this activity is a message event with any script defined in the
     * given grammar.
     * 
     * @param activity
     * @param scriptGrammar
     * @return
     */
    public static boolean isMessageEventWithScriptType(Activity activity,
            String scriptGrammar) {

        if (EventObjectUtil.isWebServiceRelatedEvent(activity)) {
            return isEventWithScriptType(activity, scriptGrammar);
        }
        return false;
    }

    /**
     * Check if this activity is a message event with any script defined in the
     * given grammar.
     * 
     * @param activity
     * @param scriptGrammar
     * @return
     */
    public static boolean isMessageMethodWithScriptType(Activity activity,
            String scriptGrammar) {
        boolean isMessageMethod = false;
        if (!ProcessInterfaceUtil.isEventImplemented(activity)) {
            return false;
        }
        List<DataMapping> methodDataMappings =
                ProcessScriptUtil.getActivityDataMappingList(activity,
                        scriptGrammar);
        if (methodDataMappings != null && !methodDataMappings.isEmpty()) {
            isMessageMethod = true;
        }
        return isMessageMethod;
    }

    /**
     * Check if this activity is a web service task with any script defined in
     * the given grammar.
     * 
     * @param activity
     * @param scriptGrammar
     * @return
     */
    public static boolean isWebServiceTaskWithScriptType(Activity activity,
            String scriptGrammar) {
        boolean toReturn = false;
        Implementation implementation = activity.getImplementation();
        if (implementation instanceof Task) {
            Task task = (Task) implementation;
            TaskService taskService = task.getTaskService();
            if (taskService == null) {
                return toReturn;
            }
            String type =
                    (String) Xpdl2ModelUtil.getOtherAttribute(taskService,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ImplementationType());
            if (type != null && type.equals(WEBSERVICE_SERVICE_TYPE)) {
                toReturn = isServiceTaskWithScriptType(activity, scriptGrammar);
            }
        }
        return toReturn;
    }

    /**
     * Check if this activity is a BW service task with any script defined in
     * the given grammar.
     * 
     * @param activity
     * @param scriptGrammar
     * @return
     */
    public static boolean isBwServiceTaskWithScriptType(Activity activity,
            String scriptGrammar) {
        boolean toReturn = false;
        Implementation implementation = activity.getImplementation();
        if (implementation instanceof Task) {
            Task task = (Task) implementation;
            TaskService taskService = task.getTaskService();
            if (taskService == null) {
                return toReturn;
            }
            String type =
                    (String) Xpdl2ModelUtil.getOtherAttribute(taskService,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ImplementationType());
            if (type != null && type.equals(BWSERVICE_SERVICE_TYPE)) {
                toReturn = isServiceTaskWithScriptType(activity, scriptGrammar);
            }
        }
        return toReturn;
    }

    /**
     * Check if this activity is a BW service task
     * 
     * @param activityr
     * @return true if the activity is a Bw Service
     */
    public static boolean isBwService(Activity activity) {
        boolean isBw = false;
        if (activity != null) {
            Implementation impl = activity.getImplementation();
            if (impl instanceof Task) {
                Task task = (Task) impl;
                TaskService service = task.getTaskService();
                if (service != null) {
                    String type =
                            (String) Xpdl2ModelUtil
                                    .getOtherAttribute(service,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ImplementationType());
                    if (BWSERVICE_SERVICE_TYPE.equals(type)) {
                        isBw = true;
                    }
                }
            }
        }
        return isBw;
    }

    /**
     * Check if this activity is a send task with any script defined in the
     * given grammar.
     * 
     * @param activity
     * @param scriptGrammar
     * @return
     */
    public static boolean isSendTaskWithScriptType(Activity activity,
            String scriptGrammar) {
        boolean toReturn = false;
        Implementation implementation = activity.getImplementation();
        if (implementation instanceof Task) {
            Task task = (Task) implementation;
            TaskSend taskSend = task.getTaskSend();
            if (taskSend == null) {
                return toReturn;
            }

            String type =
                    TaskObjectUtil.getTaskImplementationExtensionId(activity);
            /* Sid XPD-2111 - Allow for InvokeBusinessProcess send task. */
            if (WEBSERVICE_SERVICE_TYPE.equals(type)
                    || ActionUtil.INVOKE_BUSINESS_PROCESS.equals(type)) {
                List<ScriptInformation> sendTaskScriptInformations =
                        getActivityUnmappedScriptInformations(activity,
                                scriptGrammar);
                if (sendTaskScriptInformations != null
                        && !sendTaskScriptInformations.isEmpty()) {
                    toReturn = true;
                }
                List<DataMapping> serviceDataMappings =
                        getActivityDataMappingList(activity, scriptGrammar);
                if (serviceDataMappings != null
                        && !serviceDataMappings.isEmpty()) {
                    toReturn = true;
                }
            }
        }
        return toReturn;
    }

    /**
     * Check if this activity is a receive task with any script defined in the
     * given grammar.
     * 
     * @param activity
     * @param scriptGrammar
     * @return
     */
    public static boolean isReceiveTaskWithScriptType(Activity activity,
            String scriptGrammar) {
        boolean toReturn = false;
        Implementation implementation = activity.getImplementation();
        if (implementation instanceof Task) {
            Task task = (Task) implementation;
            TaskReceive taskReceive = task.getTaskReceive();
            if (taskReceive == null) {
                return toReturn;
            }
            String type =
                    (String) Xpdl2ModelUtil.getOtherAttribute(taskReceive,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ImplementationType());
            if (type != null && type.equals(WEBSERVICE_SERVICE_TYPE)) {
                List<ScriptInformation> receiveTaskScriptInformations =
                        getActivityUnmappedScriptInformations(activity,
                                scriptGrammar);
                if (receiveTaskScriptInformations != null
                        && !receiveTaskScriptInformations.isEmpty()) {
                    toReturn = true;
                }
                List<DataMapping> serviceDataMappings =
                        getActivityDataMappingList(activity, scriptGrammar);
                if (serviceDataMappings != null
                        && !serviceDataMappings.isEmpty()) {
                    toReturn = true;
                } else {
                    List<DataMapping> correlationDatamappings =
                            getCorrelationDataMappings(activity, scriptGrammar);
                    if (correlationDatamappings != null
                            && !correlationDatamappings.isEmpty()) {
                        toReturn = true;
                    }
                }
            }
        }
        return toReturn;
    }

    /**
     * Check if this activity is a event with any script defined in the given
     * grammar.
     * 
     * @param activity
     * @param scriptGrammar
     * @return
     */
    public static boolean isEventWithScriptType(Activity activity,
            String scriptGrammar) {
        boolean toReturn = false;
        List<ScriptInformation> eventScriptInformations =
                getActivityUnmappedScriptInformations(activity, scriptGrammar);
        if (eventScriptInformations != null
                && !eventScriptInformations.isEmpty()) {
            toReturn = true;
        }
        List<DataMapping> serviceDataMappings =
                getActivityDataMappingList(activity, scriptGrammar);
        if (serviceDataMappings != null && !serviceDataMappings.isEmpty()) {
            toReturn = true;
        } else {
            List<DataMapping> correlationDatamappings =
                    getCorrelationDataMappings(activity, scriptGrammar);
            if (correlationDatamappings != null
                    && !correlationDatamappings.isEmpty()) {
                toReturn = true;
            }
        }
        return toReturn;
    }

    /**
     * Check if this activity is a web service task with any script defined in
     * the given grammar.
     * 
     * @param activity
     * @param scriptGrammar
     * @return
     */
    public static boolean isServiceTaskWithScriptType(Activity activity,
            String scriptGrammar) {
        boolean toReturn = false;
        List<ScriptInformation> serviceScriptInformations =
                getActivityUnmappedScriptInformations(activity, scriptGrammar);
        if (serviceScriptInformations != null
                && !serviceScriptInformations.isEmpty()) {
            toReturn = true;
        }
        List<DataMapping> serviceDataMappings =
                getActivityDataMappingList(activity, scriptGrammar);
        if (serviceDataMappings != null && !serviceDataMappings.isEmpty()) {
            toReturn = true;
        }
        return toReturn;
    }

    /**
     * get all the script informations of the sub process task for the given
     * grammar.
     * 
     * @param activity
     * @param scriptGrammar
     * @return
     */
    public static List<ScriptInformation> getAllSubProcessTaskScriptInformations(
            Activity activity, String scriptGrammar) {
        List<ScriptInformation> allScriptInformations =
                new ArrayList<ScriptInformation>();
        Implementation implementation = activity.getImplementation();
        if (implementation instanceof SubFlow) {
            // Get the unmapped script informations
            List<ScriptInformation> tempScriptInformationList =
                    getActivityUnmappedScriptInformations(activity,
                            scriptGrammar);
            if (tempScriptInformationList != null
                    && !tempScriptInformationList.isEmpty()) {
                allScriptInformations.addAll(tempScriptInformationList);
            }
            // Get the mapped script informations
            List<DataMapping> allSubProcScriptDataMappings =
                    ProcessScriptUtil
                            .getAllSubProcessTaskScriptDataMappings(activity,
                                    scriptGrammar);
            allScriptInformations
                    .addAll(getAllMappedScriptInformation(allSubProcScriptDataMappings));
        }
        return allScriptInformations;
    }

    /**
     * get all the script informations of the receive task for the given
     * grammar.
     * 
     * @param activity
     * @param scriptGrammar
     * @return
     */
    public static List<ScriptInformation> getAllReceiveTaskScriptInformations(
            Activity activity, String scriptGrammar) {
        List<ScriptInformation> allScriptInformations =
                new ArrayList<ScriptInformation>();
        Implementation implementation = activity.getImplementation();
        if (implementation instanceof Task) {
            Task task = (Task) implementation;
            TaskReceive taskReceive = task.getTaskReceive();
            if (taskReceive == null) {
                return allScriptInformations;
            }
            // Get the unmapped script informations
            List<ScriptInformation> tempScriptInformationList =
                    getActivityUnmappedScriptInformations(activity,
                            scriptGrammar);
            if (tempScriptInformationList != null
                    && !tempScriptInformationList.isEmpty()) {
                allScriptInformations.addAll(tempScriptInformationList);
            }
            // Get the mapped script informations
            List<DataMapping> allReceiveTaskScriptDataMappings =
                    ProcessScriptUtil
                            .getAllReceiveTaskScriptDataMappings(activity,
                                    scriptGrammar);
            allScriptInformations
                    .addAll(getAllMappedScriptInformation(allReceiveTaskScriptDataMappings));

        }
        return allScriptInformations;
    }

    /**
     * get all the script informations of the send task for the given grammar.
     * 
     * @param activity
     * @param scriptGrammar
     * @return
     */
    public static List<ScriptInformation> getAllSendTaskScriptInformations(
            Activity activity, String scriptGrammar) {
        List<ScriptInformation> allScriptInformations =
                new ArrayList<ScriptInformation>();
        Implementation implementation = activity.getImplementation();
        if (implementation instanceof Task) {
            Task task = (Task) implementation;
            TaskSend taskSend = task.getTaskSend();
            if (taskSend == null) {
                return allScriptInformations;
            }
            // Get the unmapped script informations
            allScriptInformations
                    .addAll(getActivityUnmappedScriptInformations(activity,
                            scriptGrammar));
            // Get the mapped script informations
            List<DataMapping> allSendTaskScriptDataMappings =
                    ProcessScriptUtil
                            .getAllSendTaskScriptDataMappings(activity,
                                    scriptGrammar);
            allScriptInformations
                    .addAll(getAllMappedScriptInformation(allSendTaskScriptDataMappings));

        }
        return allScriptInformations;
    }

    /**
     * get all the script informations of the event for the given grammar.
     * 
     * @param activity
     * @param scriptGrammar
     * @return
     */
    public static List<ScriptInformation> getAllEventScriptInformations(
            Activity activity, String scriptGrammar) {
        List<ScriptInformation> allScriptInformations =
                new ArrayList<ScriptInformation>();
        Event event = activity.getEvent();
        if (event != null) {
            List<ScriptInformation> tempScriptInformationList =
                    getActivityUnmappedScriptInformations(activity,
                            scriptGrammar);
            if (tempScriptInformationList != null
                    && !tempScriptInformationList.isEmpty()) {
                allScriptInformations.addAll(tempScriptInformationList);
            }
            // Get the mapped script informations
            List<DataMapping> allEventScriptDataMappings =
                    ProcessScriptUtil.getAllEventScriptDataMappings(activity,
                            scriptGrammar);
            allScriptInformations
                    .addAll(getAllMappedScriptInformation(allEventScriptDataMappings));

        }
        return allScriptInformations;
    }

    /**
     * get all the script informations of the service task for the given
     * grammar.
     * 
     * @param activity
     * @param scriptGrammar
     * @return
     */
    public static List<ScriptInformation> getAllServiceScriptInformations(
            Activity activity, String scriptGrammar) {
        List<ScriptInformation> allScriptInformations =
                new ArrayList<ScriptInformation>();

        Implementation implementation = activity.getImplementation();

        Event event = activity.getEvent();
        if (event != null && EventObjectUtil.isWebServiceRelatedEvent(activity)) {
            // find whether it is a web-service implementation
            // XPD-3793 Handle catch/throw error Events
            boolean isWSEvent = false;
            EObject eventTriggerTypeNode = event.getEventTriggerTypeNode();

            if (eventTriggerTypeNode instanceof TriggerResultMessage) {
                isWSEvent = true;
            } else if (eventTriggerTypeNode instanceof ResultError) {
                if (ThrowErrorEventUtil.isThrowFaultMessageErrorEvent(activity)) {
                    isWSEvent = true;
                } else if (CatchWsdlErrorEventUtil
                        .isCatchWsdlFaultErrorEvent(activity)) {
                    isWSEvent = true;
                }
            }

            if (isWSEvent) {
                allScriptInformations
                        .addAll(getActivityUnmappedScriptInformations(activity,
                                scriptGrammar));
                // Get the mapped script informations
                allScriptInformations
                        .addAll(getEventMappedScriptInformations(activity,
                                scriptGrammar));
            }
        }

        if (implementation instanceof Task) {
            Task task = (Task) implementation;
            TaskService taskService = task.getTaskService();
            if (taskService != null) {
                // Get the unmapped script informations
                allScriptInformations
                        .addAll(getActivityUnmappedScriptInformations(activity,
                                scriptGrammar));
                // Get the mapped script informations
                allScriptInformations
                        .addAll(getServiceMappedScriptInformations(activity,
                                scriptGrammar));
            } else {
                TaskReceive taskReceive = task.getTaskReceive();
                if (taskReceive != null) {
                    allScriptInformations
                            .addAll(getActivityUnmappedScriptInformations(activity,
                                    scriptGrammar));
                    allScriptInformations
                            .addAll(getAllReceiveTaskScriptInformations(activity,
                                    scriptGrammar));
                } else {
                    TaskSend taskSend = task.getTaskSend();
                    if (taskSend != null) {
                        allScriptInformations
                                .addAll(getActivityUnmappedScriptInformations(activity,
                                        scriptGrammar));
                        allScriptInformations
                                .addAll(getAllSendTaskScriptInformations(activity,
                                        scriptGrammar));
                    }
                }

            }

        }
        return allScriptInformations;
    }

    /**
     * get all the script informations of the activity for the given grammar.
     * 
     * @param activity
     * @param scriptGrammar
     * @return
     */
    public static List<ScriptInformation> getActivityUnmappedScriptInformations(
            Activity activity, String scriptGrammar) {
        List<ScriptInformation> allScriptInformations =
                new ArrayList<ScriptInformation>();
        // Check for unmapped Scripts
        List<ScriptInformation> tempScriptInformationList =
                ProcessScriptUtil
                        .getScriptInformationTasksWithScriptType(activity,
                                scriptGrammar,
                                DirectionType.IN_LITERAL.getLiteral());
        if (tempScriptInformationList != null
                && !tempScriptInformationList.isEmpty()) {
            allScriptInformations.addAll(tempScriptInformationList);
        }
        tempScriptInformationList =
                ProcessScriptUtil
                        .getScriptInformationTasksWithScriptType(activity,
                                scriptGrammar,
                                DirectionType.OUT_LITERAL.getLiteral());
        if (tempScriptInformationList != null
                && !tempScriptInformationList.isEmpty()) {
            allScriptInformations.addAll(tempScriptInformationList);
        }

        return allScriptInformations;
    }

    /**
     * get all the mapped script informations of the service for the given
     * grammar.
     * 
     * @param activity
     * @param scriptGrammar
     * @return
     */
    private static List<ScriptInformation> getServiceMappedScriptInformations(
            Activity activity, String scriptGrammar) {
        List<ScriptInformation> allMappedScriptInformations =
                new ArrayList<ScriptInformation>();
        List<DataMapping> tempDataMappingList =
                ProcessScriptUtil
                        .getScriptDataMappingForServiceWithScriptType(activity,
                                DirectionType.IN_LITERAL,
                                scriptGrammar);
        allMappedScriptInformations
                .addAll(getAllMappedScriptInformation(tempDataMappingList));
        tempDataMappingList =
                ProcessScriptUtil
                        .getScriptDataMappingForServiceWithScriptType(activity,
                                DirectionType.OUT_LITERAL,
                                scriptGrammar);
        allMappedScriptInformations
                .addAll(getAllMappedScriptInformation(tempDataMappingList));
        return allMappedScriptInformations;
    }

    /**
     * @param allMappedScriptInformations
     * @param tempDataMappingList
     */
    public static List<ScriptInformation> getAllMappedScriptInformation(
            List<DataMapping> tempDataMappingList) {

        List<ScriptInformation> allMappedScriptInformations =
                new ArrayList<ScriptInformation>();
        if (tempDataMappingList != null && !tempDataMappingList.isEmpty()) {
            for (DataMapping dataMapping : tempDataMappingList) {
                ScriptInformation dataMappingScriptInfo =
                        ProcessScriptUtil
                                .getScriptInformationFromDataMapping(dataMapping);
                if (dataMappingScriptInfo != null) {
                    allMappedScriptInformations.add(dataMappingScriptInfo);
                }
            }
        }
        return allMappedScriptInformations;
    }

    /**
     * @param activity
     * @param scriptGrammar
     * @return
     */
    private static List<ScriptInformation> getEventMappedScriptInformations(
            Activity activity, String scriptGrammar) {
        TriggerResultMessage triggerResultMessage =
                EventObjectUtil.getTriggerResultMessage(activity);
        ResultError resultError = EventObjectUtil.getResultError(activity);
        List dataMappings = new ArrayList();
        Message message = null;
        // message event
        if (triggerResultMessage != null) {
            dataMappings.addAll(triggerResultMessage.getMessage()
                    .getDataMappings());
        } else if (resultError != null) {
            // Catch and Throw Error/Fault Event
            // XPD-3793
            Activity attachedToActivity =
                    BpmnCatchableErrorUtil.getAttachedToTask(activity);
            if (CatchWsdlErrorEventUtil.isCatchWsdlFaultErrorEvent(activity)
                    || SubProcUtil.isSubProcessActivity(attachedToActivity)) {
                CatchErrorMappings catchErrorMappings =
                        (CatchErrorMappings) Xpdl2ModelUtil
                                .getOtherElement(resultError,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_CatchErrorMappings());
                message =
                        (catchErrorMappings != null) ? catchErrorMappings
                                .getMessage() : null;
            } else if (ThrowErrorEventUtil
                    .isThrowFaultMessageErrorEvent(activity)) {
                message =
                        ThrowErrorEventUtil.getThrowErrorFaultMessage(activity);
            }
        }
        if (message != null) {
            dataMappings.addAll(message.getDataMappings());
        }

        // to include correlation data mappings
        dataMappings
                .addAll(getCorrelationDataMappings(activity, scriptGrammar));

        return (dataMappings != null) ? getAllMappedScriptInformation(dataMappings)
                : Collections.EMPTY_LIST;
    }

    /**
     * Check if this activity has std. loop expr. with a script defined in the
     * given grammar.
     * 
     * @param activity
     * @param scriptGrammar
     * @return
     */
    public static boolean isActivityStdLoopExprHasGrammar(Activity activity,
            String scriptGrammar) {
        boolean toReturn = false;
        Expression stdLoopExpression = getStdLoopExpression(activity);
        if (stdLoopExpression == null) {
            return toReturn;
        }
        if (scriptGrammar
                .equalsIgnoreCase(stdLoopExpression.getScriptGrammar())) {
            toReturn = true;
        }

        return toReturn;
    }

    /**
     * Check if this activity has MI loop expr. with a script defined in the
     * given grammar.
     * 
     * @param activity
     * @param scriptGrammar
     * @return
     */
    public static boolean isActivityMILoopExprHasGrammar(Activity activity,
            String scriptGrammar) {
        boolean toReturn = false;
        Expression miLoopExpression = getMILoopExpression(activity);
        if (miLoopExpression == null) {
            return toReturn;
        }
        if (scriptGrammar.equalsIgnoreCase(miLoopExpression.getScriptGrammar())) {
            toReturn = true;
        }
        return toReturn;
    }

    /**
     * Check if this activity has MI Complex exit expr.. with a script defined
     * in the given grammar.
     * 
     * @param activity
     * @param scriptGrammar
     * @return
     */
    public static boolean isActivityMIComplexExitExprHasGrammar(
            Activity activity, String scriptGrammar) {
        boolean toReturn = false;
        Expression complexExitExpression = getMIComplexExitExpression(activity);
        if (complexExitExpression == null) {
            return toReturn;
        }
        if (scriptGrammar.equalsIgnoreCase(complexExitExpression
                .getScriptGrammar())) {
            toReturn = true;
        }
        return toReturn;
    }

    /**
     * Returns whether the activity contains a script of the given type
     * 
     * @param activity
     * @param scriptGrammar
     * @return true if the activity contains a script of the given type, false
     *         otherwise
     */
    public static boolean isActivityWithScriptGrammarOfType(Activity activity,
            String scriptGrammar) {
        if (activity != null) {
            if (ProcessScriptUtil.isTimerEventWithScriptType(activity,
                    scriptGrammar)) {
                return true;
            } else if (ProcessScriptUtil
                    .isRescheduleTimerEventWithScriptType(activity,
                            scriptGrammar)) {
                return true;
            } else if (ProcessScriptUtil.isScriptTaskWithScriptType(activity,
                    scriptGrammar)) {
                return true;
            } else if (ProcessScriptUtil.isServiceTaskWithScriptType(activity,
                    scriptGrammar)) {
                return true;
            } else if (ProcessScriptUtil
                    .isBwServiceTaskWithScriptType(activity, scriptGrammar)) {
                return true;
            } else if (ProcessScriptUtil
                    .isJavaServiceTaskWithScriptType(activity, scriptGrammar)) {
                return true;
            } else if (ProcessScriptUtil.isSendTaskWithScriptType(activity,
                    scriptGrammar)) {
                return true;
            } else if (ProcessScriptUtil.isReceiveTaskWithScriptType(activity,
                    scriptGrammar)) {
                return true;
            } else if (ProcessScriptUtil.isUserTaskWithOpenScriptType(activity,
                    scriptGrammar)) {
                return true;
            } else if (ProcessScriptUtil
                    .isUserTaskWithCloseScriptType(activity, scriptGrammar)) {
                return true;
            } else if (ProcessScriptUtil
                    .isUserTaskWithSubmitScriptType(activity, scriptGrammar)) {
                return true;
            } else if (ProcessScriptUtil
                    .isUserTaskWithScheduleScriptType(activity, scriptGrammar)) {
                return true;
            } else if (ProcessScriptUtil
                    .isUserTaskWithRescheduleScriptType(activity, scriptGrammar)) {
                return true;
            } else if (ProcessScriptUtil
                    .isActivityWithInitiatedScriptType(activity, scriptGrammar)) {
                return true;
            } else if (ProcessScriptUtil
                    .isActivityWithCompletedScriptType(activity, scriptGrammar)) {
                return true;
            } else if (ProcessScriptUtil
                    .isActivityWithDeadlineExpiredScriptType(activity,
                            scriptGrammar)) {
                return true;
            } else if (ProcessScriptUtil
                    .isActivityWithCancelledScriptType(activity, scriptGrammar)) {
                return true;
            } else if (ProcessScriptUtil
                    .isActivityStdLoopExprHasGrammar(activity, scriptGrammar)) {
                return true;
            } else if (ProcessScriptUtil
                    .isActivityMILoopExprHasGrammar(activity, scriptGrammar)) {
                return true;
            } else if (ProcessScriptUtil
                    .isActivityMIComplexExitExprHasGrammar(activity,
                            scriptGrammar)) {
                return true;
            } else if (ProcessScriptUtil
                    .isActivityMIAdditionalInstancesExprHasGrammar(activity,
                            scriptGrammar)) {
                return true;
            } else if (ProcessScriptUtil
                    .isAdhocPreconditionTaskWithScriptType(activity,
                            scriptGrammar)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if this activity has MI additional Instances expr.. with a script
     * defined in the given grammar.
     * 
     * @param activity
     * @param scriptGrammar
     * @return
     */
    public static boolean isActivityMIAdditionalInstancesExprHasGrammar(
            Activity activity, String scriptGrammar) {
        boolean toReturn = false;
        Expression additionalInstancesExpression =
                getMIAdditionalLoopExpression(activity);
        if (additionalInstancesExpression == null) {
            return toReturn;
        }
        if (scriptGrammar.equalsIgnoreCase(additionalInstancesExpression
                .getScriptGrammar())) {
            toReturn = true;
        }

        return toReturn;
    }

    /**
     * Check if this activity is a script task with a script defined in the
     * given grammar.
     * 
     * @param activity
     * @param scriptGrammar
     * @return
     */
    public static boolean isScriptInformationWithScriptType(
            ScriptInformation scriptInformation, String scriptGrammar) {
        boolean toReturn = false;

        if (scriptInformation != null) {
            String scriptType = null;
            if (scriptInformation.eContainer() instanceof DataMapping) {
                scriptType =
                        DataMappingUtil
                                .getGrammar((DataMapping) scriptInformation
                                        .eContainer());
            } else {
                Expression expression = scriptInformation.getExpression();
                if (expression != null) {
                    scriptType = expression.getScriptGrammar();
                }
            }
            if (scriptType != null && scriptGrammar != null
                    && scriptType.equals(scriptGrammar)) {
                toReturn = true;
            }
        }
        return toReturn;
    }

    /**
     * Check if this DataMapping is a script mapping with a script defined in
     * the given grammar.
     * 
     * @param dataMapping
     * @param scriptGrammar
     * @return
     */
    public static boolean isDataMappingWithScriptType(DataMapping dataMapping,
            String scriptGrammar) {
        boolean toReturn = false;
        if (dataMapping != null) {
            if (ProcessScriptUtil.isAScriptMapping(dataMapping)) {
                Expression expression;
                if (DirectionType.IN_LITERAL.equals(dataMapping.getDirection())) {
                    expression = dataMapping.getActual();
                } else {
                    expression =
                            getScriptInformationFromDataMapping(dataMapping)
                                    .getExpression();
                }
                if (expression != null) {
                    String scriptType = expression.getScriptGrammar();
                    if (scriptType != null && scriptGrammar != null
                            && scriptType.equals(scriptGrammar)) {
                        toReturn = true;
                    }
                }
            }
        }
        return toReturn;
    }

    /**
     * Get script defined for the given script task activity.
     * 
     * @param scriptActivity
     * @return script or null if undefined or not a script task.
     */
    public static String getScriptTaskScript(Activity scriptActivity) {
        String activityScript = null;
        if (null != scriptActivity) {
            Implementation implementation = scriptActivity.getImplementation();
            if (implementation instanceof Task) {
                Task tsk = (Task) implementation;
                TaskScript taskScript = tsk.getTaskScript();
                if (taskScript == null) {
                    return activityScript;
                }
                Expression script = taskScript.getScript();
                if (script == null) {
                    return activityScript; // There is a script but it is not
                    // defined.
                }

                activityScript = getExpressionAsString(script);
            }
        }
        return activityScript;
    }

    /**
     * Gets the Adhoc Task Precondition Script.
     * 
     * @param activity
     *            the Adhoc Activity whose Precondition Script is to be fetched.
     * @return the Adhoc Task Precondition Script.
     */
    public static String getAdhocTaskPreconditionScript(Activity activity) {
        String adhocScript = null;
        if (null != activity) {
            Object adhocConfig =
                    Xpdl2ModelUtil.getOtherElement(activity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_AdHocTaskConfiguration());

            if (adhocConfig instanceof AdHocTaskConfigurationType) {

                AdHocTaskConfigurationType adhocConfigType =
                        (AdHocTaskConfigurationType) adhocConfig;

                EnablementType enablement = adhocConfigType.getEnablement();

                if (enablement != null) {
                    Expression preconditionExpression =
                            enablement.getPreconditionExpression();

                    if (preconditionExpression != null) {
                        adhocScript = preconditionExpression.getText();
                    }
                }
            }
        }
        return adhocScript;
    }

    /**
     * Get script defined for the given script performer data field.
     * 
     * @param data
     *            field of type performer
     * @return script or null if undefined or not a performer.
     */
    public static String getScriptPerformerScript(
            ProcessRelevantData processRelevantData) {
        String performerScript = null;
        ConditionalParticipant conditionalParticipant =
                (ConditionalParticipant) Xpdl2ModelUtil
                        .getOtherElement(processRelevantData,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_ConditionalParticipant());
        if (null == conditionalParticipant) {
            return performerScript;
        }
        Expression script = conditionalParticipant.getPerformerScript();
        if (null == script) {
            return performerScript;
        }
        performerScript = getExpressionAsString(script);

        return performerScript;
    }

    /**
     * Get script defined for the given script task scriptInformation.
     * 
     * @param scriptInformation
     * @return script or null if undefined or not a script task.
     */
    public static String getTaskScript(ScriptInformation scriptInformation) {
        String script = ""; //$NON-NLS-1$
        if (scriptInformation != null) {
            if (scriptInformation.eContainer() instanceof DataMapping) {
                script =
                        DataMappingUtil
                                .getScript((DataMapping) scriptInformation
                                        .eContainer());
            } else {
                Expression expression = scriptInformation.getExpression();
                if (expression != null) {
                    script = expression.getText();
                }
            }
        }
        return script;
    }

    /**
     * Get grammar defined for the given script task scriptInformation.
     * 
     * @param scriptInformation
     * @return script or null if undefined or not a script task.
     */
    public static String getScriptGrammar(ScriptInformation scriptInformation) {
        String grammar = null;
        if (scriptInformation != null) {
            if (scriptInformation.eContainer() instanceof DataMapping) {
                grammar =
                        DataMappingUtil
                                .getGrammar((DataMapping) scriptInformation
                                        .eContainer());
            } else {
                Expression expression = scriptInformation.getExpression();
                if (expression != null) {
                    grammar = expression.getScriptGrammar();
                }
            }
        }
        return grammar;
    }

    /**
     * Get script defined for the given script data mapping.
     * 
     * @param scriptInformation
     * @return script or null if undefined or not a script task.
     */
    public static String getDataMappingScript(DataMapping dataMapping) {
        String script = ""; //$NON-NLS-1$
        if (isAScriptMapping(dataMapping)) {
            Expression expression;
            // XPD-7080 - Data Mapper always stores the script in
            // ScriptInformation expression.
            boolean dataMapper = isADataMapperMapping(dataMapping);
            if (!dataMapper
                    && DirectionType.IN_LITERAL.equals(dataMapping
                            .getDirection())) {
                expression = dataMapping.getActual();
            } else {
                expression =
                        getScriptInformationFromDataMapping(dataMapping)
                                .getExpression();
            }
            if (expression != null) {
                script = expression.getText();
            }
        }
        return script;
    }

    /**
     * @param dataMapping
     * @return
     */
    private static boolean isADataMapperMapping(DataMapping dataMapping) {
        boolean isADataMapperMapping = false;
        EObject dmContainer = dataMapping.eContainer();
        if (dmContainer instanceof ScriptDataMapper) {
            isADataMapperMapping = true;
        }
        return isADataMapperMapping;
    }

    /**
     * Get grammar type defined for the given data mapping.
     * 
     * @param scriptInformation
     * @return script or null if undefined or not a script task.
     */
    public static String getDataMappingGrammar(DataMapping dataMapping) {
        String grammar = null;
        if (dataMapping != null) {
            Expression expression = dataMapping.getActual();
            if (expression != null) {
                grammar = expression.getScriptGrammar();
            }
        }
        return grammar;
    }

    /**
     * Get command to set script task script.
     * 
     * @param editingDomain
     * @param newScript
     * @param activity
     * @param grammar
     * @return
     */
    public static Command getSetScriptTaskScriptCommand(
            EditingDomain editingDomain, String newScript, Activity activity,
            String grammar) {
		return interalGetSetScriptCommand(editingDomain, newScript, activity, grammar,
				Messages.ProcessScriptUtil_SetScriptTaskScript_menu);
    }

	/**
	 * Get command to set PSL function script in the underline script task
	 * 
	 * @param editingDomain
	 * @param newScript
	 * @param activity
	 * @param grammar
	 * @return
	 */
	public static Command getSetPSLFunctionScriptCommand(EditingDomain editingDomain, String newScript,
			Activity activity, String grammar)
	{

		return interalGetSetScriptCommand(editingDomain, newScript, activity, grammar,
				Messages.ProcessScriptUtil_SetPSLFunctionScript_menu);
	}

	/**
	 * Returns a command to set the Script in the ScriptTask
	 * 
	 * @param editingDomain
	 * @param newScript
	 * @param activity
	 * @param grammar
	 * @param aLabel
	 *            Label of the command
	 * @return Command to set the script in the activity.
	 */
	private static Command interalGetSetScriptCommand(EditingDomain editingDomain, String newScript, Activity activity,
			String grammar, String aLabel)
	{
		CompoundCommand cmd = null;

        Implementation implementation = activity.getImplementation();
        if (implementation instanceof Task) {

            Task task = (Task) implementation;
            TaskScript taskScript = task.getTaskScript();
            if (taskScript != null) {

                // Only update model when script has changed.
                Expression currScript = taskScript.getScript();

                if (hasScriptChanged(newScript, grammar, currScript)) {
                    cmd = new CompoundCommand();
					cmd.setLabel(aLabel);

                    Expression expression =
                            Xpdl2Factory.eINSTANCE.createExpression();
                    expression.setScriptGrammar(grammar);
                    expression
                            .getMixed()
                            .add(XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(),
                                    newScript);

                    cmd.append(SetCommand.create(editingDomain,
                            taskScript,
                            Xpdl2Package.eINSTANCE.getTaskScript_Script(),
                            expression));

                }
            }
        }

        return cmd;
    }

    /**
     * Creates a command to set the Adhoc Task Precondtion Script[as specified]
     * and returns it.
     * 
     * @param editingDomain
     * @param newScript
     *            the new Script to set
     * @param activity
     * @param grammar
     *            the new grammar to set
     * @return the command to Set the Adhoc Task Precondition Script.
     */
    public static Command getSetAdhocScriptCommand(EditingDomain editingDomain,
            String newScript, Activity activity, String grammar) {

        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.ProcessScriptUtil_SetAdhocTaskScriptCommand_label);

        if (activity != null) {

            Object adhocConfig =
                    Xpdl2ModelUtil.getOtherElement(activity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_AdHocTaskConfiguration());

            if (adhocConfig instanceof AdHocTaskConfigurationType) {

                AdHocTaskConfigurationType adhocConfigType =
                        (AdHocTaskConfigurationType) adhocConfig;

                Expression expression =
                        Xpdl2Factory.eINSTANCE.createExpression();

                expression.setScriptGrammar(grammar);

                expression
                        .getMixed()
                        .add(XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(),
                                newScript);

                EnablementType enablement = adhocConfigType.getEnablement();

                if (enablement == null) {
                    enablement =
                            XpdExtensionFactory.eINSTANCE
                                    .createEnablementType();

                    enablement.setPreconditionExpression(expression);

                    cmd.append(SetCommand
                            .create(editingDomain,
                                    adhocConfigType,
                                    XpdExtensionPackage.eINSTANCE
                                            .getAdHocTaskConfigurationType_Enablement(),
                                    enablement));

                } else {

                    cmd.append(SetCommand
                            .create(editingDomain,
                                    enablement,
                                    XpdExtensionPackage.eINSTANCE
                                            .getEnablementType_PreconditionExpression(),
                                    expression));
                }
            }
        }
        return cmd;
    }

    /**
     * Get command to set script performer script.
     * 
     * @param editingDomain
     * @param newScript
     * @param activity
     * @param grammar
     * @return
     */
    public static Command getSetScriptPerfomerScriptCommand(
            EditingDomain editingDomain, String newScript,
            ProcessRelevantData processRelevantData, String grammar) {
        CompoundCommand cmd = null;
        if (null != processRelevantData.getDataType()) {
            Object otherElement =
                    Xpdl2ModelUtil.getOtherElement(processRelevantData,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ConditionalParticipant());
            if (otherElement instanceof ConditionalParticipant) {
                Expression currScript =
                        ((ConditionalParticipant) otherElement)
                                .getPerformerScript();
                if (hasScriptChanged(newScript, grammar, currScript)) {
                    cmd = new CompoundCommand();
                    Expression expression =
                            Xpdl2Factory.eINSTANCE.createExpression();
                    expression.setScriptGrammar(grammar);
                    expression
                            .getMixed()
                            .add(XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(),
                                    newScript);
                    cmd.append(SetCommand
                            .create(editingDomain,
                                    otherElement,
                                    XpdExtensionPackage.eINSTANCE
                                            .getConditionalParticipant_PerformerScript(),
                                    expression));
                }
            }
        }
        return cmd;
    }

    /**
     * Get a list of User Task activities that have open script defined in the
     * given grammar.
     * 
     * @param flowContainer
     * @param scriptGrammar
     * @return
     */
    public static List<Activity> getUserTasksWithOpenScriptType(
            FlowContainer flowContainer, String scriptGrammar) {
        return getUserTasksWithScriptType(flowContainer,
                scriptGrammar,
                XpdExtensionPackage.eINSTANCE.getUserTaskScripts_OpenScript());
    }

    /**
     * Get a list of User Task activities that have schedule script defined in
     * the given grammar.
     * 
     * @param flowContainer
     * @param scriptGrammar
     * @return
     */
    public static List<Activity> getUserTasksWithScheduleScriptType(
            FlowContainer flowContainer, String scriptGrammar) {
        return getUserTasksWithScriptType(flowContainer,
                scriptGrammar,
                XpdExtensionPackage.eINSTANCE
                        .getUserTaskScripts_ScheduleScript());
    }

    /**
     * Get a list of User Task activities that have reschedule script defined in
     * the given grammar.
     * 
     * @param flowContainer
     * @param scriptGrammar
     * @return
     */
    public static List<Activity> getUserTasksWithRescheduleScriptType(
            FlowContainer flowContainer, String scriptGrammar) {
        return getUserTasksWithScriptType(flowContainer,
                scriptGrammar,
                XpdExtensionPackage.eINSTANCE
                        .getUserTaskScripts_RescheduleScript());
    }

    /**
     * Get a list of User Task activities that have close script defined in the
     * given grammar.
     * 
     * @param flowContainer
     * @param scriptGrammar
     * @return
     */
    public static List<Activity> getUserTasksWithCloseScriptType(
            FlowContainer flowContainer, String scriptGrammar) {
        return getUserTasksWithScriptType(flowContainer,
                scriptGrammar,
                XpdExtensionPackage.eINSTANCE.getUserTaskScripts_CloseScript());
    }

    /**
     * Get a list of User Task activities that have submitscript defined in the
     * given grammar.
     * 
     * @param flowContainer
     * @param scriptGrammar
     * @return
     */
    public static List<Activity> getUserTasksWithSubmitScriptType(
            FlowContainer flowContainer, String scriptGrammar) {
        return getUserTasksWithScriptType(flowContainer,
                scriptGrammar,
                XpdExtensionPackage.eINSTANCE.getUserTaskScripts_SubmitScript());
    }

    /**
     * Get a list of User Task activities that have open/close/submit (as
     * defined by scriptFeature) script defined in the given grammar.
     * 
     * @param flowContainer
     * @param scriptGrammar
     * @return
     */
    private static List<Activity> getUserTasksWithScriptType(
            FlowContainer flowContainer, String scriptGrammar,
            EStructuralFeature scriptFeature) {
        List<Activity> toReturn = null;
        List<Activity> activityList = flowContainer.getActivities();
        if (activityList == null) {
            return toReturn;
        }
        for (Activity activity : activityList) {
            Expression script =
                    getUserTaskScriptExpression(activity, scriptFeature);
            if (script == null) {
                continue;
            }
            String setScriptGrammar = script.getScriptGrammar();
            if (setScriptGrammar == null) {
                continue;
            }
            if (scriptGrammar.equals(setScriptGrammar)) {

                String str = getExpressionAsString(script);
                if (str == null || str.length() < 1) {
                    continue;
                }
                if (toReturn == null) {
                    toReturn = new ArrayList<Activity>();
                }
                toReturn.add(activity);
            }
        }
        return toReturn;
    }

    /**
     * Get a list of Timer event activities that have script defined in the
     * given grammar.
     * 
     * @param flowContainer
     * @param scriptGrammar
     * @return
     */
    public static List<Activity> getTimerEventsWithScriptType(
            FlowContainer flowContainer, String scriptGrammar) {
        List<Activity> toReturn = null;
        List<Activity> activityList = flowContainer.getActivities();
        if (activityList == null) {
            return toReturn;
        }
        for (Activity activity : activityList) {
            boolean isTimerEvent =
                    isTimerEventWithScriptType(activity, scriptGrammar);
            if (isTimerEvent) {
                if (toReturn == null) {
                    toReturn = new ArrayList<Activity>();
                }
                toReturn.add(activity);
            }
        }
        return toReturn;
    }

    /**
     * Get a list of Timer event activities that have script defined in the
     * given grammar.
     * 
     * @param flowContainer
     * @param scriptGrammar
     * @return timer event activities with reschedule timer event scripts
     *         defined.
     */
    public static List<Activity> getRescheduleTimerEventsWithScriptType(
            FlowContainer flowContainer, String scriptGrammar) {
        List<Activity> toReturn = null;
        List<Activity> activityList = flowContainer.getActivities();
        if (activityList == null) {
            return toReturn;
        }
        for (Activity activity : activityList) {
            boolean isTimerEvent =
                    isRescheduleTimerEventWithScriptType(activity,
                            scriptGrammar);
            if (isTimerEvent) {
                if (toReturn == null) {
                    toReturn = new ArrayList<Activity>();
                }
                toReturn.add(activity);
            }
        }
        return toReturn;
    }

    /**
     * If the given activity a timer event with a script defined in the given
     * grammar.
     * 
     * @param activity
     * @param scriptGrammar
     * @return
     */
    public static boolean isTimerEventWithScriptType(Activity activity,
            String scriptGrammar) {
        Event ev = activity.getEvent();
        TriggerTimer timer = null;
        if (ev != null && (ev instanceof StartEvent)) {
            timer = ((StartEvent) ev).getTriggerTimer();
        } else if (ev != null && (ev instanceof IntermediateEvent)) {
            timer = ((IntermediateEvent) ev).getTriggerTimer();
        }
        if (timer != null) {
            // It is an event with a timer trigger.
            // Check for no time cycle rather than presence of time date.
            if (timer.getTimeCycle() == null) {
                Deadline deadline = getDeadline(activity);
                if (deadline == null) {
                    return false;
                }
                Expression deadlineDuration = deadline.getDeadlineDuration();
                if (deadlineDuration == null) {
                    return false;
                }
                if (scriptGrammar.equalsIgnoreCase(deadlineDuration
                        .getScriptGrammar())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * If the given activity a timer event with a reschedule script defined in
     * the given grammar.
     * 
     * @param activity
     * @param scriptGrammar
     * @return <code>true</code> if is is else <code>false</code>.
     */
    public static boolean isRescheduleTimerEventWithScriptType(
            Activity activity, String scriptGrammar) {
        RescheduleTimerScript rescheduleTimerScript =
                EventObjectUtil.getRescheduleTimerScript(activity);

        if (rescheduleTimerScript != null
                && scriptGrammar.equals(rescheduleTimerScript
                        .getScriptGrammar())) {
            return true;
        }

        return false;
    }

    /**
     * Get the script from the given timer event script.
     * 
     * @param timerEvent
     * @return
     */
    public static String getTimerEventScript(Activity timerEvent) {
        if (timerEvent == null) {
            return null;
        }
        Deadline deadline = getDeadline(timerEvent);
        if (deadline == null) {
            return null;
        }

        String timerEventDeadlineScript =
                deadline.getDeadlineDuration() == null ? null : deadline
                        .getDeadlineDuration().getText();
        return timerEventDeadlineScript;
    }

    /**
     * Get the reschedule timer event script from activity
     * 
     * @param timerEvent
     * @return script or <code>null</code>
     */
    public static String getRescheduleTimerEventScript(Activity timerEvent) {
        RescheduleTimerScript rescheduleTimerScript =
                EventObjectUtil.getRescheduleTimerScript(timerEvent);

        if (rescheduleTimerScript != null) {
            String script = rescheduleTimerScript.getText();
            return script;
        }

        return null;
    }

    /**
     * This method returns a command for updating the timer event script script.
     * 
     * @param editingDomain
     * @param string
     * @param eObject
     * @return
     */
    public static Command getSetTimerEventScriptCommand(
            EditingDomain editingDomain, String newScript, Activity activity,
            String grammar) {
        Deadline deadline = getDeadline(activity);
        if (deadline != null) {
            // Only update model when script has changed.
            Expression currScript = deadline.getDeadlineDuration();

            if (hasScriptChanged(newScript, grammar, currScript)) {
                Expression exp = Xpdl2Factory.eINSTANCE.createExpression();
                exp.getMixed()
                        .add(XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(),
                                newScript);
                exp.setScriptGrammar(grammar);

                CompoundCommand cmd = new CompoundCommand();
                cmd.setLabel(Messages.ProcessScriptUtil_SetTimerEventScript_menu);
                cmd.append(SetCommand.create(editingDomain,
                        deadline,
                        Xpdl2Package.eINSTANCE.getDeadline_DeadlineDuration(),
                        exp));
                return cmd;
            }
        }
        return null;
    }

    /**
     * Returns a command for updating the script of a data mapping.
     * 
     * @param editingDomain
     *            The editing domain.
     * @param newScript
     *            New script to be set on data mapping.
     * @param activity
     *            Activity in which data mapping is present.
     * @param grammar
     *            Script grammar.
     * @param scriptInfo
     *            Script to be changed.
     * 
     * @return Command for updating the script of a data mapping.
     */
    public static Command getSetDataMappingScriptCommand(
            EditingDomain editingDomain, String newScript, Activity activity,
            String grammar, ScriptInformation scriptInfo) {

        if (scriptInfo != null) {

            /*
             * Only update model when script has changed.
             */

            Expression currScript = scriptInfo.getExpression();

            if (hasScriptChanged(newScript, grammar, currScript)) {

                Expression exp = Xpdl2Factory.eINSTANCE.createExpression();

                exp.getMixed()
                        .add(XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(),
                                newScript);

                exp.setScriptGrammar(grammar);

                CompoundCommand cmd = new CompoundCommand();

                cmd.setLabel(Messages.ProcessScriptUtil_SetDataMappingScript_menu);

                cmd.append(SetCommand.create(editingDomain,
                        scriptInfo,
                        XpdExtensionPackage.eINSTANCE
                                .getScriptInformation_Expression(),
                        exp));

                return cmd;
            }
        }
        return null;
    }

    /**
     * This method returns a command for updating the timer event script script.
     * 
     * @param editingDomain
     * @param string
     * @param eObject
     * @return Command to reset the script within the existing reschedule timer
     *         script
     */
    public static Command getSetRescheduleTimerEventScriptCommand(
            EditingDomain editingDomain, String newScript, Activity activity,
            String grammar) {
        RescheduleTimerScript rescheduleTimerScript =
                EventObjectUtil.getRescheduleTimerScript(activity);

        if (rescheduleTimerScript != null) {

            // Only update model when script has changed.

            if (hasScriptChanged(newScript, grammar, rescheduleTimerScript)) {

                RescheduleDurationType curretDurationRelTo =
                        rescheduleTimerScript.getDurationRelativeTo();

                RescheduleTimerScript newRescheduleTimerScript =
                        XpdExtensionFactory.eINSTANCE
                                .createRescheduleTimerScript();

                newRescheduleTimerScript
                        .setDurationRelativeTo(curretDurationRelTo);

                newRescheduleTimerScript
                        .getMixed()
                        .add(XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(),
                                newScript);
                newRescheduleTimerScript.setScriptGrammar(grammar);

                CompoundCommand cmd = new CompoundCommand();
                cmd.setLabel(Messages.ProcessScriptUtil_SetRescheduleTimerScript_menu);
                cmd.append(Xpdl2ModelUtil
                        .getSetOtherElementCommand(editingDomain,
                                (TriggerTimer) activity.getEvent()
                                        .getEventTriggerTypeNode(),
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_RescheduleTimerScript(),
                                newRescheduleTimerScript));
                return cmd;
            }
        }
        return null;
    }

    /**
     * Get deadline from the given timer event activity.
     * 
     * @param activity
     * @return
     */
    public static Deadline getDeadline(Activity activity) {
        EList listOfDl = activity.getDeadline();
        if (listOfDl != null && listOfDl.size() > 0) {
            return (Deadline) listOfDl.get(0);
        } else {
            return null;
        }
    }

    /**
     * Get the initiated script from the given activity.
     * 
     * @param activity
     * @return script text or "" if undefined or not a user task.
     */
    public static String getInitiatedScript(Activity activity) {
        Expression script =
                getAuditScriptExpression(activity,
                        AuditEventType.INITIATED_LITERAL);
        if (script == null) {
            return ""; //$NON-NLS-1$
        }
        String str = getExpressionAsString(script);
        return str;
    }

    /**
     * Get the completed script from the given activity.
     * 
     * @param activity
     * @return script text or "" if undefined or not a user task.
     */
    public static String getCompletedScript(Activity activity) {
        Expression script =
                getAuditScriptExpression(activity,
                        AuditEventType.COMPLETED_LITERAL);
        if (script == null) {
            return ""; //$NON-NLS-1$
        }
        String str = getExpressionAsString(script);
        return str;
    }

    /**
     * Get the Deadline Expired script from the given activity.
     * 
     * @param activity
     * @return script text or "" if undefined or not a user task.
     */
    public static String getDeadlineExpiredScript(Activity activity) {
        Expression script =
                getAuditScriptExpression(activity,
                        AuditEventType.DEADLINE_EXPIRED_LITERAL);
        if (script == null) {
            return ""; //$NON-NLS-1$
        }
        String str = getExpressionAsString(script);
        return str;
    }

    /**
     * Get the Cancelled script from the given activity.
     * 
     * @param activity
     * @return script text or "" if undefined or not a user task.
     */
    public static String getCancelledScript(Activity activity) {
        Expression completedScript =
                getAuditScriptExpression(activity,
                        AuditEventType.CANCELLED_LITERAL);
        if (completedScript == null) {
            return ""; //$NON-NLS-1$
        }
        String str = getExpressionAsString(completedScript);
        return str;
    }

    /**
     * Get the open script from the given user task activity.
     * 
     * @param activity
     * @return script text or "" if undefined or not a user task.
     */
    public static String getUserTaskOpenScript(Activity activity) {
        Expression openScript =
                getUserTaskScriptExpression(activity,
                        XpdExtensionPackage.eINSTANCE
                                .getUserTaskScripts_OpenScript());
        if (openScript == null) {
            return ""; //$NON-NLS-1$
        }
        String str = getExpressionAsString(openScript);
        return str;
    }

    /**
     * Get the schedule script from the given user task activity.
     * 
     * @param activity
     * @return script text or "" if undefined or not a user task.
     */
    public static String getUserTaskScheduleScript(Activity activity) {
        Expression openScript =
                getUserTaskScriptExpression(activity,
                        XpdExtensionPackage.eINSTANCE
                                .getUserTaskScripts_ScheduleScript());
        if (openScript == null) {
            return ""; //$NON-NLS-1$
        }
        String str = getExpressionAsString(openScript);
        return str;
    }

    /**
     * Get the reschedule script from the given user task activity.
     * 
     * @param activity
     * @return script text or "" if undefined or not a user task.
     */
    public static String getUserTaskRescheduleScript(Activity activity) {
        Expression openScript =
                getUserTaskScriptExpression(activity,
                        XpdExtensionPackage.eINSTANCE
                                .getUserTaskScripts_RescheduleScript());
        if (openScript == null) {
            return ""; //$NON-NLS-1$
        }
        String str = getExpressionAsString(openScript);
        return str;
    }

    /**
     * Get the open script grammar for the given user task activity.
     * 
     * @param activity
     * @return script grammar or null if undefined or not a user task.
     */
    public static String getUserTaskOpenScriptGrammar(Activity activity) {
        Expression openScript =
                getUserTaskScriptExpression(activity,
                        XpdExtensionPackage.eINSTANCE
                                .getUserTaskScripts_OpenScript());
        if (openScript == null) {
            return null;
        }
        String grammar = openScript.getScriptGrammar();
        return grammar;
    }

    /**
     * Get the close script from the given user task activity.
     * 
     * @param activity
     * @return script text or "" if undefined or not a user task.
     */
    public static String getUserTaskCloseScript(Activity activity) {
        Expression closeScript =
                getUserTaskScriptExpression(activity,
                        XpdExtensionPackage.eINSTANCE
                                .getUserTaskScripts_CloseScript());
        if (closeScript == null) {
            return ""; //$NON-NLS-1$
        }
        String str = getExpressionAsString(closeScript);
        return str;
    }

    /**
     * Get the close script grammar for the given user task activity.
     * 
     * @param activity
     * @return script grammar or null if undefined or not a user task.
     */
    public static String getUserTaskCloseScriptGrammar(Activity activity) {
        Expression closeScript =
                getUserTaskScriptExpression(activity,
                        XpdExtensionPackage.eINSTANCE
                                .getUserTaskScripts_CloseScript());
        if (closeScript == null) {
            return null;
        }
        String scriptGrammar = closeScript.getScriptGrammar();
        return scriptGrammar;
    }

    /**
     * Get the submit script from the given user task activity.
     * 
     * @param activity
     * @return script text or "" if undefined or not a user task.
     */
    public static String getUserTaskSubmitScript(Activity activity) {
        Expression submitScript =
                getUserTaskScriptExpression(activity,
                        XpdExtensionPackage.eINSTANCE
                                .getUserTaskScripts_SubmitScript());
        if (submitScript == null) {
            return ""; //$NON-NLS-1$
        }
        String str = getExpressionAsString(submitScript);
        return str;
    }

    /**
     * Get the submit script grammar for the given user task activity.
     * 
     * @param activity
     * @return script grammar or null if undefined or not a user task.
     */
    public static String getUserTaskSubmitScriptGrammar(Activity activity) {
        Expression submitScript =
                getUserTaskScriptExpression(activity,
                        XpdExtensionPackage.eINSTANCE
                                .getUserTaskScripts_SubmitScript());
        if (submitScript == null) {
            return null;
        }
        String scriptGrammar = submitScript.getScriptGrammar();
        return scriptGrammar;
    }

    /**
     * Return the script expression for the given open/close/submit script
     * feature (i.e. XpdExtension.eInstance.getUserTaskScripts_OpenScript()).
     * 
     * @param activity
     * @param scriptFeature
     * @return
     */
    private static Expression getUserTaskScriptExpression(Activity activity,
            EStructuralFeature scriptFeature) {
        Implementation implementation = activity.getImplementation();
        if (implementation instanceof Task) {
            Task task = (Task) implementation;

            TaskUser taskUser = task.getTaskUser();
            if (taskUser == null) {
                return null;
            }

            UserTaskScripts userTaskScripts =
                    (UserTaskScripts) taskUser
                            .getOtherElement(XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_UserTaskScripts()
                                    .getName());
            if (userTaskScripts == null) {
                return null;
            }
            Expression script =
                    (Expression) userTaskScripts.eGet(scriptFeature);
            return script;
        }
        return null;
    }

    /**
     * Return the script expression for the given initiated/completed script
     * feature (i.e. XpdExtension.eInstance.getAuditScripts_Initiated()).
     * 
     * Activities supported: - Service Task - Script Task
     * 
     * @param activity
     * @param scriptFeature
     * @return
     */
    public static Expression getAuditScriptExpression(Activity activity,
            AuditEventType auditEventType) {
        Expression script = null;
        if (activity != null) {
            Audit audit = TaskObjectUtil.getAudit(activity);
            if (audit != null) {
                script =
                        TaskObjectUtil.getAuditScriptExpression(auditEventType,
                                audit);
            }
        }
        return script;
    }

    /**
     * Get the expression->mixed script content as a string.
     * 
     * @param mixed
     * @return the expression as String NOTE: this method can return null
     *         expression as string so protect against it.
     */
    public static String getExpressionAsString(Expression expression) {
        String strScript = null;
        if (expression == null) {
            return strScript;
        }
        FeatureMap mixed = expression.getMixed();
        List texts =
                (List) mixed.get(XMLTypePackage.eINSTANCE
                        .getXMLTypeDocumentRoot_Text(), false);
        if (texts == null) {
            return strScript;
        } else if (texts.size() == 1) {
            strScript = (String) texts.get(0);
        } else {
            StringBuffer sb = new StringBuffer();
            for (Iterator iter = texts.iterator(); iter.hasNext();) {
                sb.append(iter.next());
            }
            strScript = sb.toString();
        }
        if (strScript == null) {
            strScript = "";//$NON-NLS-1$
        }
        return strScript;
    }

    /**
     * Does the given activity have a user task open script with the given
     * grammar.
     * 
     * @param activity
     * @param scriptGrammar
     * @return
     */
    public static boolean isUserTaskWithOpenScriptType(Activity activity,
            String scriptGrammar) {
        return isUserTaskScript(activity,
                scriptGrammar,
                XpdExtensionPackage.eINSTANCE.getUserTaskScripts_OpenScript());
    }

    /**
     * Does the given activity have a user task schedule script with the given
     * grammar.
     * 
     * @param activity
     * @param scriptGrammar
     * @return
     */
    public static boolean isUserTaskWithScheduleScriptType(Activity activity,
            String scriptGrammar) {
        return isUserTaskScript(activity,
                scriptGrammar,
                XpdExtensionPackage.eINSTANCE
                        .getUserTaskScripts_ScheduleScript());
    }

    /**
     * Does the given activity have a user task schedule script with any
     * grammar.
     * 
     * @param activity
     * @param scriptGrammar
     * @return
     */
    public static boolean isUserTaskWithScheduleScriptType(Activity activity) {
        return isUserTaskScript(activity,
                XpdExtensionPackage.eINSTANCE
                        .getUserTaskScripts_ScheduleScript());
    }

    /**
     * Does the given activity have a user task reschedule script with the given
     * grammar.
     * 
     * @param activity
     * @param scriptGrammar
     * @return
     */
    public static boolean isUserTaskWithRescheduleScriptType(Activity activity,
            String scriptGrammar) {
        return isUserTaskScript(activity,
                scriptGrammar,
                XpdExtensionPackage.eINSTANCE
                        .getUserTaskScripts_RescheduleScript());
    }

    /**
     * Does the given activity have a user task reschedule script with any
     * grammar.
     * 
     * @param activity
     * @param scriptGrammar
     * @return
     */
    public static boolean isUserTaskWithRescheduleScriptType(Activity activity) {
        return isUserTaskScript(activity,
                XpdExtensionPackage.eINSTANCE
                        .getUserTaskScripts_RescheduleScript());
    }

    /**
     * Does the given activity have a user task close script with the given
     * grammar.
     * 
     * @param activity
     * @param scriptGrammar
     * @return
     */
    public static boolean isUserTaskWithCloseScriptType(Activity activity,
            String scriptGrammar) {
        return isUserTaskScript(activity,
                scriptGrammar,
                XpdExtensionPackage.eINSTANCE.getUserTaskScripts_CloseScript());
    }

    /**
     * Does the given activity have a user task submit script with the given
     * grammar.
     * 
     * @param activity
     * @param scriptGrammar
     * @return
     */
    public static boolean isUserTaskWithSubmitScriptType(Activity activity,
            String scriptGrammar) {
        return isUserTaskScript(activity,
                scriptGrammar,
                XpdExtensionPackage.eINSTANCE.getUserTaskScripts_SubmitScript());
    }

    /**
     * Does the open/close/submit script (id'd by scriptFEature) for the given
     * user task activity have a script defined in the given grammar.
     * 
     * @param activity
     * @param scriptGrammar
     * @param scriptFeature
     * @return
     */
    private static boolean isUserTaskScript(Activity activity,
            String scriptGrammar, EStructuralFeature scriptFeature) {
        Implementation implementation = activity.getImplementation();
        if (implementation instanceof Task) {
            Task task = (Task) implementation;

            TaskUser taskUser = task.getTaskUser();
            if (taskUser != null) {
                UserTaskScripts userTaskScripts =
                        (UserTaskScripts) taskUser
                                .getOtherElement(XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_UserTaskScripts()
                                        .getName());
                if (userTaskScripts != null) {

                    Expression script =
                            (Expression) userTaskScripts.eGet(scriptFeature);
                    if (script != null) {

                        if (scriptGrammar.equalsIgnoreCase(script
                                .getScriptGrammar())) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * Does the open/close/submit script (id'd by scriptFEature) for the given
     * user task activity have a script defined in any grammar.
     * 
     * @param activity
     * @param scriptGrammar
     * @param scriptFeature
     * @return
     */
    private static boolean isUserTaskScript(Activity activity,
            EStructuralFeature scriptFeature) {
        Implementation implementation = activity.getImplementation();
        if (implementation instanceof Task) {
            Task task = (Task) implementation;

            TaskUser taskUser = task.getTaskUser();
            if (taskUser != null) {
                UserTaskScripts userTaskScripts =
                        (UserTaskScripts) taskUser
                                .getOtherElement(XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_UserTaskScripts()
                                        .getName());
                if (userTaskScripts != null) {

                    Expression script =
                            (Expression) userTaskScripts.eGet(scriptFeature);
                    if (script != null) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * This method returns a command for updating the script.
     * 
     * @param editingDomain
     * @param newScript
     * @param eObject
     * @return
     */
    public static Command getSetUserTaskSubmitScriptCommand(
            EditingDomain editingDomain, String newScript, EObject eObject,
            String grammar) {
        return getSetUserTaskScriptCommand(editingDomain,
                newScript,
                grammar,
                eObject,
                XpdExtensionPackage.eINSTANCE.getUserTaskScripts_SubmitScript(),
                Messages.ProcessScriptUtil_SetUserTaskSubmitScriptCommand);
    }

    /**
     * This method returns a command for updating the script.
     * 
     * @param editingDomain
     * @param newScript
     * @param eObject
     * @return
     */
    public static Command getSetUserTaskCloseScriptCommand(
            EditingDomain editingDomain, String newScript, EObject eObject,
            String grammar) {
        return getSetUserTaskScriptCommand(editingDomain,
                newScript,
                grammar,
                eObject,
                XpdExtensionPackage.eINSTANCE.getUserTaskScripts_CloseScript(),
                Messages.ProcessScriptUtil_SetUserTaskCloseScriptCommand);
    }

    /**
     * This method returns a command for updating the script.
     * 
     * @param editingDomain
     * @param newScript
     * @param eObject
     * @return
     */
    public static Command getSetUserTaskOpenScriptCommand(
            EditingDomain editingDomain, String newScript, EObject eObject,
            String grammar) {
        return getSetUserTaskScriptCommand(editingDomain,
                newScript,
                grammar,
                eObject,
                XpdExtensionPackage.eINSTANCE.getUserTaskScripts_OpenScript(),
                Messages.ProcessScriptUtil_SetUserTaskOpenScriptCommand);
    }

    /**
     * This method returns a command for updating the script.
     * 
     * @param editingDomain
     * @param newScript
     * @param eObject
     * @return
     */
    public static Command getSetUserTaskScheduleScriptCommand(
            EditingDomain editingDomain, String newScript, EObject eObject,
            String grammar) {
        return getSetUserTaskScriptCommand(editingDomain,
                newScript,
                grammar,
                eObject,
                XpdExtensionPackage.eINSTANCE
                        .getUserTaskScripts_ScheduleScript(),
                Messages.ProcessScriptUtil_SetScheduledScriptCommand2);
    }

    /**
     * This method returns a command for updating the script.
     * 
     * @param editingDomain
     * @param newScript
     * @param eObject
     * @return
     */
    public static Command getSetUserTaskRescheduleScriptCommand(
            EditingDomain editingDomain, String newScript, EObject eObject,
            String grammar) {
        return getSetUserTaskScriptCommand(editingDomain,
                newScript,
                grammar,
                eObject,
                XpdExtensionPackage.eINSTANCE
                        .getUserTaskScripts_RescheduleScript(),
                Messages.ProcessScriptUtil_SetRescheduleScript_menu);
    }

    /**
     * Get the command that sets the open/close/submit (id'd by scriptFeature)
     * in the given user task activity.
     * 
     * @param editingDomain
     * @param newScript
     * @param eObject
     * @param scriptFeature
     * @param cmdLabel
     * @return
     */
    private static Command getSetUserTaskScriptCommand(
            EditingDomain editingDomain, String newScript, String grammar,
            EObject eObject, EStructuralFeature scriptFeature, String cmdLabel) {

        Activity activity = (Activity) eObject;
        Implementation implementation = activity.getImplementation();
        if (implementation instanceof Task) {
            Task task = (Task) implementation;
            TaskUser taskUser = task.getTaskUser();

            UserTaskScripts userTaskScripts =
                    (UserTaskScripts) taskUser
                            .getOtherElement(XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_UserTaskScripts()
                                    .getName());

            CompoundCommand cmd = new CompoundCommand();
            cmd.setLabel(cmdLabel);

            if (userTaskScripts == null) {
                userTaskScripts =
                        XpdExtensionFactory.eINSTANCE.createUserTaskScripts();
                cmd.append(Xpdl2ModelUtil
                        .getSetOtherElementCommand(editingDomain,
                                taskUser,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_UserTaskScripts(),
                                userTaskScripts));
            }

            Expression currScript =
                    (Expression) userTaskScripts.eGet(scriptFeature);
            if (hasScriptChanged(newScript, grammar, currScript)) {
                Expression newExpr = Xpdl2Factory.eINSTANCE.createExpression();

                newExpr.setScriptGrammar(grammar);

                newExpr.getMixed()
                        .add(XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(),
                                newScript);

                cmd.append(SetCommand.create(editingDomain,
                        userTaskScripts,
                        scriptFeature,
                        newExpr));
            }
            return cmd;
        }

        return null;
    }

    /**
     * Get the command that sets query script for a given participant
     * 
     * @param editingDomain
     * @param newScript
     * @param eObject
     * @param scriptFeature
     * @param cmdLabel
     * @return
     */
    public static Command getSetParticipantQueryScriptCommand(
            EditingDomain editingDomain, String newScript, String grammar,
            EObject eObject, String cmdLabel) {
        OtherElementsContainer owner = null;
        if (eObject instanceof Participant) {
            Participant participant = (Participant) eObject;
            owner = participant.getParticipantType();

        } else if (eObject instanceof DataField) {
            DataField dataField = (DataField) eObject;
            if (dataField.getDataType() instanceof BasicType) {
                BasicType bt = (BasicType) dataField.getDataType();
                if (bt.getType() != null
                        && bt.getType().equals(BasicTypeType.PERFORMER_LITERAL)) {
                    owner = bt;
                }
            }
        }
        Object objParticipantQ =
                Xpdl2ModelUtil.getOtherElement(owner,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ParticipantQuery());
        CompoundCommand cmd = new CompoundCommand();
        cmd.setLabel(cmdLabel);

        if (objParticipantQ == null) {
            Expression participantQuery =
                    Xpdl2Factory.eINSTANCE.createExpression();
            participantQuery.setScriptGrammar(grammar);
            participantQuery
                    .getMixed()
                    .add(XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(),
                            newScript);
            Command setParticipantQueryCmd =
                    Xpdl2ModelUtil.getSetOtherElementCommand(editingDomain,
                            owner,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ParticipantQuery(),
                            participantQuery);

            cmd.append(setParticipantQueryCmd);
        } else if (objParticipantQ instanceof Expression) {
            Expression participantQuery = (Expression) objParticipantQ;
            if (hasScriptChanged(newScript, grammar, participantQuery)) {
                Expression participantQExpression =
                        Xpdl2Factory.eINSTANCE.createExpression();

                participantQExpression
                        .getMixed()
                        .add(XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(),
                                newScript);

                participantQExpression.setScriptGrammar(grammar);

                cmd.append(Xpdl2ModelUtil
                        .getSetOtherElementCommand(editingDomain,
                                owner,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_ParticipantQuery(),
                                participantQExpression));

            }
        }
        return cmd;

    }

    /**
     * This method returns a command for updating the script.
     * 
     * @param editingDomain
     * @param newScript
     * @param eObject
     * @return
     */
    public static Command getSetInitiatedScriptCommand(
            EditingDomain editingDomain, String newScript, EObject eObject,
            String grammar) {
        return getSetAuditScriptCommand(editingDomain,
                newScript,
                grammar,
                eObject,
                AuditEventType.INITIATED_LITERAL,
                Messages.ProcessScriptUtil_SetInitiatedScriptCommand);
    }

    /**
     * This method returns a command for updating the script.
     * 
     * @param editingDomain
     * @param newScript
     * @param eObject
     * @return
     */
    public static Command getSetCompletedScriptCommand(
            EditingDomain editingDomain, String newScript, EObject eObject,
            String grammar) {
        return getSetAuditScriptCommand(editingDomain,
                newScript,
                grammar,
                eObject,
                AuditEventType.COMPLETED_LITERAL,
                Messages.ProcessScriptUtil_SetCompletedScriptCommand);
    }

    /**
     * This method returns a command for updating the script.
     * 
     * @param editingDomain
     * @param newScript
     * @param eObject
     * @return
     */
    public static Command getSetDeadlineExpiredScriptCommand(
            EditingDomain editingDomain, String newScript, EObject eObject,
            String grammar) {
        return getSetAuditScriptCommand(editingDomain,
                newScript,
                grammar,
                eObject,
                AuditEventType.DEADLINE_EXPIRED_LITERAL,
                Messages.ProcessScriptUtil_SetDeadlineExpiredScriptCommand);
    }

    /**
     * This method returns a command for updating the script.
     * 
     * @param editingDomain
     * @param newScript
     * @param eObject
     * @return
     */
    public static Command getSetCancelledScriptCommand(
            EditingDomain editingDomain, String newScript, EObject eObject,
            String grammar) {
        return getSetAuditScriptCommand(editingDomain,
                newScript,
                grammar,
                eObject,
                AuditEventType.CANCELLED_LITERAL,
                Messages.ProcessScriptUtil_SetCancelledScriptCommand);
    }

    /**
     * Get the command that sets the audit script for the activity.
     * 
     * @param editingDomain
     * @param newScript
     * @param eObject
     * @param auditEventType
     * @param cmdLabel
     * @return
     */
    private static Command getSetAuditScriptCommand(
            EditingDomain editingDomain, String newScript, String grammar,
            EObject eObject, AuditEventType auditEventType, String cmdLabel) {

        Activity activity = (Activity) eObject;
        CompoundCommand cmd = new CompoundCommand();
        boolean createAuditEvent = false;
        Audit audit = TaskObjectUtil.getAudit(activity);
        if (audit == null) {
            audit = XpdExtensionFactory.eINSTANCE.createAudit();
            cmd.append(Xpdl2ModelUtil.getSetOtherElementCommand(editingDomain,
                    activity,
                    XpdExtensionPackage.eINSTANCE.getDocumentRoot_Audit(),
                    audit));
        }
        if (audit != null) {
            AuditEvent auditEvent =
                    TaskObjectUtil.getAuditEvent(auditEventType, audit);
            if (auditEvent == null) {
                auditEvent = XpdExtensionFactory.eINSTANCE.createAuditEvent();
                auditEvent.setType(auditEventType);
                createAuditEvent = true;
            }
            Expression currScript =
                    TaskObjectUtil.getAuditScriptExpression(auditEventType,
                            audit);
            if (hasScriptChanged(newScript, grammar, currScript)) {
                Expression newExpr = Xpdl2Factory.eINSTANCE.createExpression();

                newExpr.setScriptGrammar(grammar);

                newExpr.getMixed()
                        .add(XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(),
                                newScript);

                cmd.append(SetCommand.create(editingDomain,
                        auditEvent,
                        XpdExtensionPackage.eINSTANCE
                                .getAuditEvent_Information(),
                        newExpr));
                if (createAuditEvent) {
                    cmd.append(AddCommand
                            .create(editingDomain,
                                    audit,
                                    XpdExtensionPackage.eINSTANCE
                                            .getAudit_AuditEvent(),
                                    auditEvent));
                }
                cmd.setLabel(cmdLabel);
            } else {
                if (createAuditEvent) {
                    cmd.append(AddCommand
                            .create(editingDomain,
                                    audit,
                                    XpdExtensionPackage.eINSTANCE
                                            .getAudit_AuditEvent(),
                                    auditEvent));
                    cmd.setLabel(cmdLabel);
                }
            }
            return cmd;
        }

        return null;
    }

    /**
     * Return true if the script or script grammar has changed compared with
     * current expression for script.
     * 
     * @param newScript
     * @param grammar
     * @param currScript
     * @return
     */
    private static boolean hasScriptChanged(String newScript, String grammar,
            Expression currScript) {
        boolean changed = false;
        if (currScript == null) {
            changed = true;
        } else {
            if (grammar == null) {
                grammar = ""; //$NON-NLS-1$
            }

            if (!grammar.equals((currScript.getScriptGrammar()))) {
                changed = true;
            } else if (!newScript.equals(getExpressionAsString(currScript))) {
                changed = true;
            }
        }
        return changed;
    }

    private static TimerTriggerMode getTimerTriggerMode(Activity activity) {
        TriggerTimer tt = getTriggerTimer(activity);
        if (tt == null)
            return TimerTriggerMode.DATETIME;
        if (tt.getTimeCycle() != null) {
            return TimerTriggerMode.CYCLE;
        } else {
            return TimerTriggerMode.DATETIME;
        }
    }

    private static TriggerTimer getTriggerTimer(Activity activity) {
        Event event = activity.getEvent();
        TriggerTimer triggerTimer = null;
        if (event instanceof StartEvent) {
            triggerTimer = ((StartEvent) event).getTriggerTimer();
        } else if (event instanceof IntermediateEvent) {
            triggerTimer = ((IntermediateEvent) event).getTriggerTimer();
        }
        return triggerTimer;
    }

    public static UserTaskScripts getUserTaskScripts(Activity activity) {
        return TaskObjectUtil.getUserTaskScripts(activity);
    }

    public static LoopStandard getLoopStandard(Activity activity) {
        Loop loop = activity.getLoop();
        if (loop != null) {
            LoopStandard loopStandard = loop.getLoopStandard();
            if (loopStandard != null) {
                return loopStandard;
            }
        }
        return null;
    }

    public static MultiInstanceScripts getMILoopScripts(Activity activity) {
        Loop loop = activity.getLoop();
        if (loop != null) {
            LoopMultiInstance loopMultiInstance = loop.getLoopMultiInstance();
            if (loopMultiInstance != null) {
                MultiInstanceScripts miLoopScript =
                        (MultiInstanceScripts) Xpdl2ModelUtil
                                .getOtherElement(loopMultiInstance,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_MultiInstanceScripts());
                if (miLoopScript != null) {
                    return miLoopScript;
                }
            }
        }
        return null;
    }

    public static LoopMultiInstance getLoopMultiInstance(Activity activity) {
        Loop loop = activity.getLoop();
        if (loop != null) {
            LoopMultiInstance loopMultiInstance = loop.getLoopMultiInstance();
            if (loopMultiInstance != null) {
                return loopMultiInstance;
            }
        }
        return null;
    }

    /**
     * This method returns a command for updating the script.
     * 
     * @param editingDomain
     * @param newScript
     * @param eObject
     * @return
     */
    public static Command getSetStdLoopExpressionScriptCommand(
            EditingDomain editingDomain, String newScript, Activity activity,
            String grammar) {
        CompoundCommand cmd = new CompoundCommand();

        LoopStandard loopStandard =
                getOrAddLoopStandard(editingDomain, activity, cmd);

        if (loopStandard != null) {
            Expression loopExpression = loopStandard.getLoopCondition();

            if (hasScriptChanged(newScript, grammar, loopExpression)) {
                cmd.setLabel(Messages.ProcessScriptUtil_SetStdLoopScript);

                Expression newExpr = Xpdl2Factory.eINSTANCE.createExpression();
                newExpr.setScriptGrammar(grammar);
                newExpr.getMixed()
                        .add(XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(),
                                newScript);

                cmd.append(SetCommand.create(editingDomain,
                        loopStandard,
                        Xpdl2Package.eINSTANCE.getLoopStandard_LoopCondition(),
                        newExpr));
                return cmd;
            }
        }
        return null;
    }

    /**
     * Return the loop standard element of the given activity - if it's not
     * there then append commands to the given compound command to add it.
     * 
     * @param editingDomain
     * @param activity
     * @param cmd
     * @return
     */
    public static LoopStandard getOrAddLoopStandard(
            EditingDomain editingDomain, Activity activity, CompoundCommand cmd) {
        LoopStandard loopStandard = null;

        Loop loop = activity.getLoop();
        if (loop == null) {
            loop = Xpdl2Factory.eINSTANCE.createLoop();
            loopStandard = Xpdl2Factory.eINSTANCE.createLoopStandard();
            loop.setLoopStandard(loopStandard);
            cmd.append(SetCommand.create(editingDomain,
                    activity,
                    Xpdl2Package.eINSTANCE.getActivity_Loop(),
                    loop));

        } else {
            loopStandard = loop.getLoopStandard();
            if (loopStandard == null) {
                loopStandard = Xpdl2Factory.eINSTANCE.createLoopStandard();

                cmd.append(SetCommand.create(editingDomain,
                        loop,
                        Xpdl2Package.eINSTANCE.getLoop_LoopStandard(),
                        loopStandard));
            }
        }

        return loopStandard;
    }

    public static String getStdLoopExpressionScript(Activity activity) {
        Expression loopExpression = getStdLoopExpression(activity);
        if (loopExpression == null) {
            return ""; //$NON-NLS-1$
        }
        String str = getExpressionAsString(loopExpression);
        return str;
    }

    public static Expression getStdLoopExpression(Activity activity) {
        LoopStandard loopStandard = getLoopStandard(activity);
        if (loopStandard != null) {
            return loopStandard.getLoopCondition();
        }
        return null;
    }

    /**
     * Return the loop standard element of the given activity - if it's not
     * there then append commands to the given compound command to add it.
     * 
     * @param editingDomain
     * @param activity
     * @param cmd
     * @return
     */
    public static LoopMultiInstance getOrAddLoopMultiInstance(
            EditingDomain editingDomain, Activity activity, CompoundCommand cmd) {
        LoopMultiInstance loopMultiInstance = null;

        Loop loop = activity.getLoop();
        if (loop == null) {
            loop = Xpdl2Factory.eINSTANCE.createLoop();
            loopMultiInstance =
                    Xpdl2Factory.eINSTANCE.createLoopMultiInstance();
            loop.setLoopMultiInstance(loopMultiInstance);
            cmd.append(SetCommand.create(editingDomain,
                    activity,
                    Xpdl2Package.eINSTANCE.getActivity_Loop(),
                    loop));

        } else {
            loopMultiInstance = loop.getLoopMultiInstance();
            if (loopMultiInstance == null) {
                loopMultiInstance =
                        Xpdl2Factory.eINSTANCE.createLoopMultiInstance();

                cmd.append(SetCommand.create(editingDomain,
                        loop,
                        Xpdl2Package.eINSTANCE.getLoop_LoopMultiInstance(),
                        loopMultiInstance));
            }
        }

        return loopMultiInstance;
    }

    /**
     * This method returns a command for updating the script.
     * 
     * @param editingDomain
     * @param newScript
     * @param eObject
     * @return
     */
    public static Command getSetMILoopExprScriptCommand(
            EditingDomain editingDomain, String newScript, Activity activity,
            String grammar) {
        return getSetMultiInstanceScriptCommand(editingDomain,
                Xpdl2Package.eINSTANCE.getLoopMultiInstance_MICondition(),
                grammar,
                Messages.ProcessScriptUtil_SetMIExprScript,
                activity,
                newScript,
                MI_LOOP_EXPR);
    }

    /**
     * This method returns a command for updating the script.
     * 
     * @param editingDomain
     * @param newScript
     * @param eObject
     * @return
     */
    public static Command getSetMIComplexExitExprScriptCommand(
            EditingDomain editingDomain, String newScript, Activity activity,
            String grammar) {
        return getSetMultiInstanceScriptCommand(editingDomain,
                Xpdl2Package.eINSTANCE
                        .getLoopMultiInstance_ComplexMIFlowCondition(),
                grammar,
                Messages.ProcessScriptUtil_SetMIComplexExprScript,
                activity,
                newScript,
                MI_COMPLEX_EXIT_EXPR);
    }

    /**
     * This method returns a command for updating the script.
     * 
     * @param editingDomain
     * @param newScript
     * @param eObject
     * @return
     */
    public static Command getSetMIAdditionalInstancesExprScriptCommand(
            EditingDomain editingDomain, String newScript, Activity activity,
            String grammar) {
        if (activity == null) {
            return null;
        }

        CompoundCommand cmd = new CompoundCommand();

        LoopMultiInstance loopMultiInstance =
                getOrAddLoopMultiInstance(editingDomain, activity, cmd);

        MultiInstanceScripts miScripts = getMILoopScripts(activity);
        if (miScripts == null) {
            miScripts =
                    XpdExtensionFactory.eINSTANCE.createMultiInstanceScripts();
            cmd.append(Xpdl2ModelUtil.getSetOtherElementCommand(editingDomain,
                    loopMultiInstance,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_MultiInstanceScripts(),
                    miScripts));
        }

        Expression oldExpression = null;
        oldExpression = miScripts.getAdditionalInstances();

        if (hasScriptChanged(newScript, grammar, oldExpression)) {
            Expression newExpression =
                    Xpdl2Factory.eINSTANCE.createExpression();
            newExpression.setScriptGrammar(grammar);
            newExpression
                    .getMixed()
                    .add(XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(),
                            newScript);
            cmd.append(SetCommand.create(editingDomain,
                    miScripts,
                    XpdExtensionPackage.eINSTANCE
                            .getMultiInstanceScripts_AdditionalInstances(),
                    newExpression));
            cmd.setLabel(Messages.ProcessScriptUtil_SetMIAdditionalInstancesScript);
        }

        return cmd;
    }

    private static Command getSetMultiInstanceScriptCommand(
            EditingDomain editingDomain, EStructuralFeature scriptFeature,
            String scriptGrammar, String cmdLabel, Activity activity,
            String newScript, String scriptType) {

        if (activity == null) {
            return null;
        }

        CompoundCommand cmd = new CompoundCommand();

        LoopMultiInstance loopMultiInstance =
                getOrAddLoopMultiInstance(editingDomain, activity, cmd);

        Expression oldExpression = null;
        if (scriptType.equalsIgnoreCase(MI_LOOP_EXPR)) {
            oldExpression = loopMultiInstance.getMICondition();
        } else if (scriptType.equalsIgnoreCase(MI_COMPLEX_EXIT_EXPR)) {
            oldExpression = loopMultiInstance.getComplexMIFlowCondition();
        }

        if (hasScriptChanged(newScript, scriptGrammar, oldExpression)) {
            Expression newExpression =
                    Xpdl2Factory.eINSTANCE.createExpression();
            newExpression.setScriptGrammar(scriptGrammar);
            newExpression
                    .getMixed()
                    .add(XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(),
                            newScript);
            cmd.append(SetCommand.create(editingDomain,
                    loopMultiInstance,
                    scriptFeature,
                    newExpression));
            cmd.setLabel(cmdLabel);
        }
        return cmd;
    }

    public static String getMILoopExpressionScript(Activity activity) {
        Expression loopExpression = getMILoopExpression(activity);
        if (loopExpression == null) {
            return ""; //$NON-NLS-1$
        }
        String str = getExpressionAsString(loopExpression);
        return str;
    }

    public static Expression getMILoopExpression(Activity activity) {
        LoopMultiInstance loopMultiInstance = getLoopMultiInstance(activity);
        if (loopMultiInstance != null) {
            return loopMultiInstance.getMICondition();

        }
        return null;
    }

    public static String getMIComplexExitExpressionScript(Activity activity) {
        Expression loopExpression = getMIComplexExitExpression(activity);
        if (loopExpression == null) {
            return ""; //$NON-NLS-1$
        }
        String str = getExpressionAsString(loopExpression);
        return str;
    }

    public static Expression getMIComplexExitExpression(Activity activity) {
        LoopMultiInstance loopMultiInstance = getLoopMultiInstance(activity);
        if (loopMultiInstance != null) {
            return loopMultiInstance.getComplexMIFlowCondition();
        }
        return null;
    }

    public static String getMIAdditionalLoopExpressionScript(Activity activity) {
        Expression loopExpression = getMIAdditionalLoopExpression(activity);
        if (loopExpression == null) {
            return ""; //$NON-NLS-1$
        }
        String str = getExpressionAsString(loopExpression);
        return str;
    }

    public static Expression getMIAdditionalLoopExpression(Activity activity) {
        MultiInstanceScripts miLoopScripts = getMILoopScripts(activity);
        if (miLoopScripts == null) {
            return null;
        }
        Expression loopExpression = miLoopScripts.getAdditionalInstances();
        return loopExpression;
    }

    public static boolean isWebServiceActivityWithScriptType(Activity activity,
            String scriptGrammar) {
        if (activity.getImplementation() instanceof Task) {
            Task task = (Task) activity.getImplementation();

            if (task.getTaskService() != null) {
                return isWebServiceTaskWithScriptType(activity, scriptGrammar);

            } else if (task.getTaskSend() != null) {
                return isSendTaskWithScriptType(activity, scriptGrammar);

            } else if (task.getTaskReceive() != null) {
                return isReceiveTaskWithScriptType(activity, scriptGrammar);
            }

        } else if (activity.getEvent() != null) {
            return isMessageEventWithScriptType(activity, scriptGrammar);
        }

        return false;
    }

    /**
     * @param activity
     * @return list of co-relation data mappings for the given activity
     */
    public static List<DataMapping> getCorrelationDataMappings(
            Activity activity, String scriptGrammar) {
        List<DataMapping> dataMappingList = new ArrayList<DataMapping>();
        List<DataMapping> returnMappingList = new ArrayList<DataMapping>();
        if (activity != null) {
            TaskType activityTaskType = TaskObjectUtil.getTaskType(activity);
            if (TaskType.RECEIVE_LITERAL.equals(activityTaskType)) {
                CorrelationDataMappings correlationDataMappingsObject =
                        getCorrelationDataMappingsObject(((Task) activity
                                .getImplementation()).getTaskReceive()
                                .getMessage());
                if (null != correlationDataMappingsObject) {
                    dataMappingList.addAll(correlationDataMappingsObject
                            .getDataMappings());
                }
            } else {
                TriggerResultMessage triggerResultMessage =
                        EventObjectUtil.getTriggerResultMessage(activity);
                if (triggerResultMessage != null
                        && triggerResultMessage.getMessage() != null) {
                    CorrelationDataMappings correlationDataMappingsObject =
                            getCorrelationDataMappingsObject(triggerResultMessage
                                    .getMessage());
                    if (null != correlationDataMappingsObject) {
                        dataMappingList.addAll(correlationDataMappingsObject
                                .getDataMappings());
                    }

                }

            }
        }

        if (dataMappingList != null) {
            for (DataMapping dataMapping : dataMappingList) {
                String grammar =
                        ProcessScriptUtil.getDataMappingGrammar(dataMapping);
                if (grammar != null && grammar.equals(scriptGrammar)) {
                    returnMappingList.add(dataMapping);
                }
            }
        }

        /*
         * Sid XPD-2111 - Would help if we returned the mappings that matched
         * the script grammar NOT the all mappings regardless of grammar -
         * because things rely on us doing that to tell if activity is useing
         * the given grammar.
         */
        return returnMappingList;
    }

    private static CorrelationDataMappings getCorrelationDataMappingsObject(
            Message msg) {

        return (CorrelationDataMappings) Xpdl2ModelUtil.getOtherElement(msg,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_CorrelationDataMappings());
    }

    /**
     * Get list of Service Event activities that are of the given type.
     * 
     * @param flowContainer
     * @param implementationType
     * @return
     */
    public static List<Activity> getWebServiceActivities(
            FlowContainer flowContainer) {
        List<Activity> toReturn = new ArrayList<Activity>();
        List<Activity> activityList = flowContainer.getActivities();
        if (activityList == null) {
            return toReturn;
        }
        if (flowContainer instanceof Process) {
            Process process = (Process) flowContainer;
            for (ActivitySet activitySet : process.getActivitySets()) {
                for (Activity activity : activitySet.getActivities()) {
                    toReturn.add(activity);
                }
            }
        }
        for (Activity activity : activityList) {
            Implementation implementation = activity.getImplementation();
            if (implementation instanceof Task) {
                Task task = (Task) implementation;
                TaskService service = task.getTaskService();
                if (service != null) {
                    String type =
                            (String) Xpdl2ModelUtil
                                    .getOtherAttribute(service,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ImplementationType());
                    if (type != null && WEBSERVICE_SERVICE_TYPE != null
                            && type.equals(WEBSERVICE_SERVICE_TYPE)) {
                        toReturn.add(activity);
                    }
                }
            } else if (activity.getEvent() != null) {
                ImplementationType eventImplementationType =
                        EventObjectUtil.getEventImplementationType(activity);
                if (eventImplementationType != null
                        && ImplementationType.WEB_SERVICE_LITERAL
                                .equals(eventImplementationType)) {
                    toReturn.add(activity);
                }
            }
        }
        return toReturn;
    }

    public static boolean isScriptDefined(BaseProcessScriptSection scriptSection) {
        if (scriptSection != null
                && scriptSection.getCurrentSetScriptGrammarId() != null
                && !scriptSection.getCurrentSetScriptGrammarId()
                        .equals(BaseScriptSection.UNDEFINED_SCRIPTGRAMMAR)) {
            return true;
        }
        return false;
    }

    public static Command getSetScriptTaskGrammarCommand(
            EditingDomain editingDomain, String scriptGrammar, EObject eObject,
            boolean preserveExistingScript, String context) {
        Activity activity = (Activity) eObject;
        if (activity == null) {
            return null;
        }
        Implementation currentImpl = activity.getImplementation();
        if (!(currentImpl instanceof Task)) {
            return null;
        }
        Task task = (Task) currentImpl;
        TaskScript taskScript = task.getTaskScript();
        if (taskScript == null) {
            return null;
        }
        String newScript = EMPTY_STRING;
        if (preserveExistingScript) {
            Expression existingExpr = taskScript.getScript();

            /*
             * Sid XPD-7078 Allow user cancellation of script grammar
             * conversion.
             */
            try {
                String expressionAsString =
                        convertScriptGrammar(scriptGrammar,
                                eObject,
                                existingExpr,
                                context);

                if (expressionAsString != null) {
                    newScript = expressionAsString;

                }

            } catch (GrammarConversionCancelledException e) {
                return null;
            }
        }

        Expression newExpr = Xpdl2Factory.eINSTANCE.createExpression();
        newExpr.setScriptGrammar(scriptGrammar);
        newExpr.getMixed()
                .add(XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(),
                        newScript);
        CompoundCommand cmd = new CompoundCommand();
        cmd.append(SetCommand.create(editingDomain,
                taskScript,
                Xpdl2Package.eINSTANCE.getTaskScript_Script(),
                newExpr));
        cmd.setLabel(Messages.Xpdl2TaskAdapter_SetScriptGrammar);
        return cmd;
    }

    /**
     * Creates a command to set the Adhoc Task Precondition Script Grammar and
     * returns it.
     * 
     * @param editingDomain
     * @param scriptGrammar
     * @param eObject
     * @param preserveExistingScript
     * @return the command to set the Adhoc Task Precondition Script Grammar.
     */
    public static Command getSetAdhocGrammarCommand(
            EditingDomain editingDomain, String scriptGrammar, EObject eObject,
            boolean preserveExistingScript) {
        Activity activity = (Activity) eObject;
        if (activity == null) {
            return null;
        }

        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.ProcessScriptUtil_SetAdhocTaskScriptGrammarCommand_label);

        if (activity != null) {

            Object adhocConfig =
                    Xpdl2ModelUtil.getOtherElement(activity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_AdHocTaskConfiguration());

            if (adhocConfig instanceof AdHocTaskConfigurationType) {

                AdHocTaskConfigurationType adhocConfigType =
                        (AdHocTaskConfigurationType) adhocConfig;

                Expression expression =
                        Xpdl2Factory.eINSTANCE.createExpression();

                expression.setScriptGrammar(scriptGrammar);

                EnablementType enablement = adhocConfigType.getEnablement();

                if (enablement == null) {

                    enablement =
                            XpdExtensionFactory.eINSTANCE
                                    .createEnablementType();

                    enablement.setPreconditionExpression(expression);

                    cmd.append(SetCommand
                            .create(editingDomain,
                                    adhocConfigType,
                                    XpdExtensionPackage.eINSTANCE
                                            .getAdHocTaskConfigurationType_Enablement(),
                                    enablement));

                } else {

                    cmd.append(SetCommand
                            .create(editingDomain,
                                    enablement,
                                    XpdExtensionPackage.eINSTANCE
                                            .getEnablementType_PreconditionExpression(),
                                    expression));
                }
            }
        }
        return cmd;

    }

    public static Command getSetQueryParticipantScriptGrammarCommand(
            EditingDomain editingDomain, String scriptGrammar, EObject eObject) {
        if (eObject instanceof Participant) {
            Participant participant = (Participant) eObject;
            ParticipantTypeElem participantType =
                    participant.getParticipantType();
            if (ParticipantType.RESOURCE_SET_LITERAL.equals(participantType
                    .getType())) {
                Object otherElement =
                        Xpdl2ModelUtil.getOtherElement(participantType,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_ParticipantQuery());
                if (otherElement instanceof Expression) {
                    Expression participantQuery = (Expression) otherElement;
                    if (participantQuery != null) {
                        return SetCommand.create(editingDomain,
                                participantQuery,
                                Xpdl2Package.eINSTANCE
                                        .getExpression_ScriptGrammar(),
                                scriptGrammar);
                    }
                }
            }

        } else if (eObject instanceof DataField) {
            DataField dataField = (DataField) eObject;
            DataType dataType = dataField.getDataType();
            if (dataType instanceof BasicType) {
                BasicType bt = (BasicType) dataType;
                if (bt.getType() != null
                        && bt.getType().equals(BasicTypeType.PERFORMER_LITERAL)) {
                    Object otherElement =
                            Xpdl2ModelUtil
                                    .getOtherElement(bt,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ParticipantQuery());
                    CompoundCommand cmd = new LateExecuteCompoundCommand();
                    if (otherElement == null) {
                        Expression participantQuery =
                                Xpdl2Factory.eINSTANCE.createExpression();
                        participantQuery.setScriptGrammar(scriptGrammar);

                        Command setParticipantQueryCmd =
                                Xpdl2ModelUtil
                                        .getSetOtherElementCommand(editingDomain,
                                                bt,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_ParticipantQuery(),
                                                participantQuery);

                        cmd.append(setParticipantQueryCmd);
                    } else {
                        cmd.append(SetCommand.create(editingDomain,
                                otherElement,
                                Xpdl2Package.eINSTANCE
                                        .getExpression_ScriptGrammar(),
                                scriptGrammar));
                    }
                    return cmd;
                }
            }
        }
        return null;
    }

    public static Command getSetPerformerScriptGrammarCommand(
            EditingDomain editingDomain, String scriptGrammar, EObject eObject,

            boolean preserveExistingScript) {

        CompoundCommand cmd = new CompoundCommand();

        ProcessRelevantData processRelevantData = (ProcessRelevantData) eObject;
        ConditionalParticipant conditionalParticipant = null;
        String newScript = EMPTY_STRING;

        if (preserveExistingScript) {
            conditionalParticipant =
                    (ConditionalParticipant) Xpdl2ModelUtil
                            .getOtherElement(processRelevantData,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_ConditionalParticipant());

            /**
             * if there is no ConditionalParticipant Performer Script existing -
             * create one for FreeText or JavaScript
             * */
            if (null == conditionalParticipant) {
                conditionalParticipant =
                        XpdExtensionFactory.eINSTANCE
                                .createConditionalParticipant();
                cmd.append(Xpdl2ModelUtil
                        .getSetOtherElementCommand(editingDomain,
                                processRelevantData,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_ConditionalParticipant(),
                                conditionalParticipant));
            }

            /*
             * Sid XPD-7078 Allow user cancellation of script grammar
             * conversion.
             */
            try {
                String existingScript =
                        convertScriptGrammar(scriptGrammar,
                                eObject,
                                conditionalParticipant.getPerformerScript());
                if (existingScript.trim().length() == 0) {
                    StringBuffer sb = new StringBuffer();
                    sb.append("if (condition) { \n"); //$NON-NLS-1$
                    sb.append("\t//provide your participant implementation code here \n"); //$NON-NLS-1$
                    sb.append("} else { \n"); //$NON-NLS-1$
                    sb.append("\t//provide your participant implementation code here \n"); //$NON-NLS-1$
                    sb.append("}"); //$NON-NLS-1$
                    newScript = sb.toString();

                } else {
                    newScript = existingScript;
                }

            } catch (GrammarConversionCancelledException e) {
                return null;
            }

        }

        Expression newExpr = Xpdl2Factory.eINSTANCE.createExpression();
        newExpr.setScriptGrammar(scriptGrammar);
        newExpr.getMixed()
                .add(XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(),
                        newScript);
        cmd.append(SetCommand.create(editingDomain,
                conditionalParticipant,
                XpdExtensionPackage.eINSTANCE
                        .getConditionalParticipant_PerformerScript(),
                newExpr));
        cmd.setLabel(Messages.Xpdl2TaskAdapter_SetScriptGrammar);

        return cmd;
    }

    public static Command getRemoveScriptTaskGrammarCommand(
            EditingDomain editingDomain, EObject eObject) {
        Activity activity = (Activity) eObject;
        if (activity == null) {
            return null;
        }
        Implementation currentImpl = activity.getImplementation();
        if (!(currentImpl instanceof Task)) {
            return null;
        }
        Task task = (Task) currentImpl;
        TaskScript taskScript = task.getTaskScript();
        if (taskScript == null) {
            return null;
        }
        CompoundCommand cmd = new CompoundCommand();
        cmd.append(SetCommand.create(editingDomain,
                taskScript,
                Xpdl2Package.eINSTANCE.getTaskScript_Script(),
                null));
        cmd.setLabel(Messages.Xpdl2TaskAdapter_RemoveScriptGrammar);
        return cmd;
    }

    /**
     * Creates and returns a command to remove the Adhoc Task Precondition
     * Script.
     * 
     * @param editingDomain
     * @param eObject
     * @return
     */
    public static Command getRemoveAdhocScriptGrammarCommand(
            EditingDomain editingDomain, EObject eObject) {
        Activity activity = (Activity) eObject;
        if (activity == null) {
            return null;
        }
        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.ProcessScriptUtil_RemoveAdhocTaskScriptCommand_label);

        Object adhocConfig =
                Xpdl2ModelUtil.getOtherElement(activity,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_AdHocTaskConfiguration());

        if (adhocConfig instanceof AdHocTaskConfigurationType) {

            AdHocTaskConfigurationType adhocConfigType =
                    (AdHocTaskConfigurationType) adhocConfig;

            EnablementType enablement = adhocConfigType.getEnablement();

            Expression preconditionExpression =
                    enablement.getPreconditionExpression();

            if (preconditionExpression != null) {
                cmd.append(SetCommand.create(editingDomain,
                        enablement,
                        XpdExtensionPackage.eINSTANCE
                                .getEnablementType_PreconditionExpression(),
                        SetCommand.UNSET_VALUE));
            }
        }
        return cmd;
    }

    public static Command getRemovePerfomerScriptGrammarCommand(
            EditingDomain editingDomain, EObject eObject) {
        CompoundCommand cmd = new CompoundCommand();
        if (eObject instanceof ProcessRelevantData) {
            ProcessRelevantData processRelevantData =
                    (ProcessRelevantData) eObject;
            if (processRelevantData.getDataType() instanceof BasicType) {
                BasicType basicType =
                        (BasicType) processRelevantData.getDataType();
                BasicTypeType type = basicType.getType();
                if (BasicTypeType.PERFORMER_LITERAL.equals(type)) {
                    cmd.append(Xpdl2ModelUtil
                            .getSetOtherElementCommand(editingDomain,
                                    processRelevantData,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_ConditionalParticipant(),
                                    null));
                    cmd.setLabel(Messages.Xpdl2DataField_RemovePerformerScriptGrammar);
                    return cmd;
                }

            }
        }
        return cmd;
    }

    /**
     * Set the open script grammar for user task.
     */
    public static Command getSetOpenUserTaskScriptGrammarCommand(
            EditingDomain editingDomain, String grammar, EObject eObject,

            boolean preserveExistingScript, String context) {
        return getSetUserTaskScriptGrammarCommand(editingDomain,
                XpdExtensionPackage.eINSTANCE.getUserTaskScripts_OpenScript(),
                grammar,
                Messages.Xpdl2TaskAdapter_SetOpenScriptGrammar,
                eObject,
                preserveExistingScript,
                context);
    }

    /**
     * Set the schedule script grammar for user task.
     */
    public static Command getSetScheduleUserTaskScriptGrammarCommand(
            EditingDomain editingDomain, String grammar, EObject eObject,

            boolean preserveExistingScript, String context) {
        return getSetUserTaskScriptGrammarCommand(editingDomain,
                XpdExtensionPackage.eINSTANCE
                        .getUserTaskScripts_ScheduleScript(),
                grammar,
                Messages.Xpdl2TaskAdapter_SetScheduledScriptGrammar,
                eObject,
                preserveExistingScript,
                context);
    }

    /**
     * Set the schedule script grammar for user task.
     */
    public static Command getSetRescheduleUserTaskScriptGrammarCommand(
            EditingDomain editingDomain, String grammar, EObject eObject,

            boolean preserveExistingScript, String context) {
        return getSetUserTaskScriptGrammarCommand(editingDomain,
                XpdExtensionPackage.eINSTANCE
                        .getUserTaskScripts_RescheduleScript(),
                grammar,
                Messages.ProcessScriptUtil_SetRescheduleGrammar_menu,
                eObject,
                preserveExistingScript,
                context);
    }

    public static Command getDeleteOpenUserTaskScriptGrammarCommand(
            EditingDomain editingDomain, EObject eObject) {
        return getDeleteUserTaskScriptGrammarCommand(editingDomain,
                XpdExtensionPackage.eINSTANCE.getUserTaskScripts_OpenScript(),
                Messages.Xpdl2TaskAdapter_RemoveOpenScriptGrammar,
                eObject);
    }

    public static Command getDeleteScheduleUserTaskScriptGrammarCommand(
            EditingDomain editingDomain, EObject eObject) {
        return getDeleteUserTaskScriptGrammarCommand(editingDomain,
                XpdExtensionPackage.eINSTANCE
                        .getUserTaskScripts_ScheduleScript(),
                Messages.Xpdl2TaskAdapter_RemoveScheduledScriptGrammar,
                eObject);
    }

    public static Command getDeleteRescheduleUserTaskScriptGrammarCommand(
            EditingDomain editingDomain, EObject eObject) {
        return getDeleteUserTaskScriptGrammarCommand(editingDomain,
                XpdExtensionPackage.eINSTANCE
                        .getUserTaskScripts_RescheduleScript(),
                Messages.ProcessScriptUtil_RemoveRescheduleScript_menu,
                eObject);
    }

    public static Command getSetCloseUserTaskScriptGrammarCommand(
            EditingDomain editingDomain, String grammar, EObject eObject,

            boolean preserveExistingScript, String context) {
        return getSetUserTaskScriptGrammarCommand(editingDomain,
                XpdExtensionPackage.eINSTANCE.getUserTaskScripts_CloseScript(),
                grammar,
                Messages.Xpdl2TaskAdapter_SetCloseScriptGrammar,
                eObject,
                preserveExistingScript,
                context);
    }

    public static Command getDeleteCloseUserTaskScriptGrammarCommand(
            EditingDomain editingDomain, EObject eObject) {
        return getDeleteUserTaskScriptGrammarCommand(editingDomain,
                XpdExtensionPackage.eINSTANCE.getUserTaskScripts_CloseScript(),
                Messages.Xpdl2TaskAdapter_RemoveCloseScriptGrammar,
                eObject);
    }

    public static Command getSetSubmitUserTaskScriptGrammarCommand(
            EditingDomain editingDomain, String grammar, EObject eObject,

            boolean preserveExistingScript, String context) {
        return getSetUserTaskScriptGrammarCommand(editingDomain,
                XpdExtensionPackage.eINSTANCE.getUserTaskScripts_SubmitScript(),
                grammar,
                Messages.Xpdl2TaskAdapter_SetSubmitScriptGrammar,
                eObject,
                preserveExistingScript,
                context);

    }

    public static Command getDeleteSubmitUserTaskScriptGrammarCommand(
            EditingDomain editingDomain, EObject eObject) {
        return getDeleteUserTaskScriptGrammarCommand(editingDomain,
                XpdExtensionPackage.eINSTANCE.getUserTaskScripts_SubmitScript(),
                Messages.Xpdl2TaskAdapter_RemoveSubmitScriptGrammar,
                eObject);
    }

    /**
     * Set the grammar for the UserTaskScript/Open|Close|Submit as defined by
     * scriptFeature.
     * 
     * @param editingDomain
     * @param scriptFeature
     * @param grammar
     * @param cmdLabel
     * @return
     */
    private static Command getSetUserTaskScriptGrammarCommand(
            EditingDomain editingDomain, EStructuralFeature scriptFeature,
            String grammar, String cmdLabel, EObject eObject,
            boolean preserveExistingScript, String context) {
        CompoundCommand cmd = null;

        Activity activity = (Activity) eObject;
        if (activity == null) {
            return null;
        }
        // Before setting user task script grammar, activity must already be set
        // up as a TaskUser.
        if (activity.getImplementation() instanceof Task) {
            Task task = (Task) activity.getImplementation();

            TaskUser taskUser = task.getTaskUser();
            if (taskUser != null) {
                cmd = new CompoundCommand();

                // Get or create the user task scripts element under TaskUser
                UserTaskScripts userTaskScripts =
                        (UserTaskScripts) Xpdl2ModelUtil
                                .getOtherElement(taskUser,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_UserTaskScripts());
                if (userTaskScripts == null) {
                    // Not there already - add it.
                    userTaskScripts =
                            XpdExtensionFactory.eINSTANCE
                                    .createUserTaskScripts();
                    cmd.append(Xpdl2ModelUtil
                            .getSetOtherElementCommand(editingDomain,
                                    taskUser,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_UserTaskScripts(),
                                    userTaskScripts));
                }
                String newScript = EMPTY_STRING;
                if (preserveExistingScript) {
                    Expression oldExpression = null;

                    oldExpression =
                            (Expression) userTaskScripts.eGet(scriptFeature);

                    /*
                     * Sid XPD-7078 Allow user cancellation of script grammar
                     * conversion.
                     */
                    try {
                        String expressionAsString =
                                convertScriptGrammar(grammar,
                                        eObject,
                                        oldExpression,
                                        context);

                        if (expressionAsString != null) {
                            newScript = expressionAsString;

                        }

                    } catch (GrammarConversionCancelledException e) {
                        return null;
                    }
                }

                // Create/Reset the script under UserTaskScripts
                Expression newExpression =
                        Xpdl2Factory.eINSTANCE.createExpression();
                newExpression.setScriptGrammar(grammar);
                newExpression
                        .getMixed()
                        .add(XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(),
                                newScript);
                cmd.append(SetCommand.create(editingDomain,
                        userTaskScripts,
                        scriptFeature,
                        newExpression));
                cmd.setLabel(cmdLabel);
            }
        }

        return cmd;
    }

    /**
     * Delete the grammar for the UserTaskScript/Open|Close|Submit as defined by
     * scriptFeature.
     * 
     * @param editingDomain
     * @param scriptFeature
     * @param grammar
     * @param cmdLabel
     * @return
     */
    private static Command getDeleteUserTaskScriptGrammarCommand(
            EditingDomain editingDomain, EStructuralFeature scriptFeature,
            String cmdLabel, EObject eObject) {
        CompoundCommand cmd = null;

        Activity activity = (Activity) eObject;
        if (activity == null) {
            return null;
        }
        // Before setting user task script grammar, activity must already be set
        // up as a TaskUser.
        if (activity.getImplementation() instanceof Task) {
            Task task = (Task) activity.getImplementation();

            TaskUser taskUser = task.getTaskUser();
            if (taskUser != null) {
                cmd = new CompoundCommand();

                // Get or create the user task scripts element under TaskUser
                UserTaskScripts userTaskScripts =
                        (UserTaskScripts) Xpdl2ModelUtil
                                .getOtherElement(taskUser,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_UserTaskScripts());
                if (userTaskScripts != null) {
                    cmd.append(SetCommand.create(editingDomain,
                            userTaskScripts,
                            scriptFeature,
                            null));
                    cmd.setLabel(cmdLabel);
                }
            }
        }

        return cmd;
    }

    /**
     * Delete the grammar .
     * 
     * @param editingDomain
     * @param scriptFeature
     * @param grammar
     * @param cmdLabel
     * @return
     */
    private static Command getDeleteAuditScriptGrammarCommand(
            EditingDomain editingDomain, AuditEventType auditEventType,
            String cmdLabel, EObject eObject) {
        CompoundCommand cmd = null;

        Activity activity = (Activity) eObject;
        if (activity == null) {
            return null;
        }
        Audit audit = TaskObjectUtil.getAudit(activity);
        if (audit != null) {
            AuditEvent auditEvent =
                    TaskObjectUtil.getAuditEvent(auditEventType, audit);
            if (auditEvent != null) {
                cmd = new CompoundCommand();
                cmd.append(RemoveCommand.create(editingDomain,
                        audit,
                        XpdExtensionPackage.eINSTANCE.getAuditEvent(),
                        auditEvent));
                cmd.setLabel(cmdLabel);
            }
        }
        return cmd;
    }

    public static Command getSetInitiatedScriptGrammarCommand(
            EditingDomain editingDomain, String grammar, EObject eObject,

            boolean preserveExistingScript, String context) {
        return getSetAuditScriptGrammarCommand(editingDomain,
                AuditEventType.INITIATED_LITERAL,
                grammar,
                Messages.Xpdl2TaskAdapter_SetInitiatedScriptGrammar,
                eObject,

                preserveExistingScript,
                context);
    }

    public static Command getDeleteInitiatedScriptGrammarCommand(
            EditingDomain editingDomain, EObject eObject) {
        return getDeleteAuditScriptGrammarCommand(editingDomain,
                AuditEventType.INITIATED_LITERAL,
                Messages.Xpdl2TaskAdapter_RemoveInitiatedScriptGrammar,
                eObject);
    }

    public static Command getSetCompletedScriptGrammarCommand(
            EditingDomain editingDomain, String grammar, EObject eObject,

            boolean preserveExistingScript, String context) {
        return getSetAuditScriptGrammarCommand(editingDomain,
                AuditEventType.COMPLETED_LITERAL,
                grammar,
                Messages.Xpdl2TaskAdapter_SetCompletedScriptGrammar,
                eObject,

                preserveExistingScript,
                context);
    }

    public static Command getDeleteCompletedScriptGrammarCommand(
            EditingDomain editingDomain, EObject eObject) {
        return getDeleteAuditScriptGrammarCommand(editingDomain,
                AuditEventType.COMPLETED_LITERAL,
                Messages.Xpdl2TaskAdapter_RemoveCompletedScriptGrammar,
                eObject);
    }

    public static Command getSetDeadlineExpiredScriptGrammarCommand(
            EditingDomain editingDomain, String grammar, EObject eObject,

            boolean preserveExistingScript, String context) {
        return getSetAuditScriptGrammarCommand(editingDomain,
                AuditEventType.DEADLINE_EXPIRED_LITERAL,
                grammar,
                Messages.Xpdl2TaskAdapter_SetDeadlineExpiredScriptGrammar,
                eObject,

                preserveExistingScript,
                context);
    }

    public static Command getDeleteDeadlineExpiredScriptGrammarCommand(
            EditingDomain editingDomain, EObject eObject) {
        return getDeleteAuditScriptGrammarCommand(editingDomain,
                AuditEventType.DEADLINE_EXPIRED_LITERAL,
                Messages.Xpdl2TaskAdapter_RemoveDeadlineExpiredScriptGrammar,
                eObject);
    }

    public static Command getSetCancelledScriptGrammarCommand(
            EditingDomain editingDomain, String grammar, EObject eObject,

            boolean preserveExistingScript, String context) {
        return getSetAuditScriptGrammarCommand(editingDomain,
                AuditEventType.CANCELLED_LITERAL,
                grammar,
                Messages.Xpdl2TaskAdapter_SetCancelledScriptGrammar,
                eObject,

                preserveExistingScript,
                context);
    }

    public static Command getDeleteCancelledScriptGrammarCommand(
            EditingDomain editingDomain, EObject eObject) {
        return getDeleteAuditScriptGrammarCommand(editingDomain,
                AuditEventType.CANCELLED_LITERAL,
                Messages.Xpdl2TaskAdapter_RemoveCancelledScriptGrammar,
                eObject);
    }

    /**
     * Set the grammar for the AuditScript/initiated|Complete as defined by
     * scriptFeature.
     * 
     * Activities supported:
     * 
     * - Service Task - Script Taks
     * 
     * @param editingDomain
     * @param scriptFeature
     * @param grammar
     * @param cmdLabel
     * @return
     */
    private static Command getSetAuditScriptGrammarCommand(
            EditingDomain editingDomain, AuditEventType auditEventType,
            String grammar, String cmdLabel, EObject eObject,
            boolean preserveExistingScript, String context) {
        CompoundCommand cmd = null;
        boolean createAuditEvent = false;
        Activity activity = (Activity) eObject;
        if (activity == null) {
            return null;
        }

        Audit audit = TaskObjectUtil.getAudit(activity);
        cmd = new CompoundCommand();
        if (audit == null) {
            // Not there already - add it.
            audit = XpdExtensionFactory.eINSTANCE.createAudit();
            cmd.append(Xpdl2ModelUtil.getSetOtherElementCommand(editingDomain,
                    activity,
                    XpdExtensionPackage.eINSTANCE.getDocumentRoot_Audit(),
                    audit));
        }
        if (audit != null) {
            String newScript = EMPTY_STRING;
            AuditEvent auditEvent = null;

            Expression oldExpression = null;
            auditEvent = TaskObjectUtil.getAuditEvent(auditEventType, audit);
            if (auditEvent == null) {
                auditEvent = XpdExtensionFactory.eINSTANCE.createAuditEvent();
                auditEvent.setType(auditEventType);
                createAuditEvent = true;
            }

            if (preserveExistingScript) {

                oldExpression = auditEvent.getInformation();
                /*
                 * Sid XPD-7078 Allow user cancellation of script grammar
                 * conversion.
                 */
                try {
                    String expressionAsString =
                            convertScriptGrammar(grammar,
                                    eObject,
                                    oldExpression,
                                    context);

                    if (expressionAsString != null) {
                        newScript = expressionAsString;

                    }

                } catch (GrammarConversionCancelledException e) {
                    return null;
                }
            }

            // Create/Reset the script
            Expression newExpression =
                    Xpdl2Factory.eINSTANCE.createExpression();
            newExpression.setScriptGrammar(grammar);
            newExpression
                    .getMixed()
                    .add(XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(),
                            newScript);
            cmd.append(SetCommand.create(editingDomain,
                    auditEvent,
                    XpdExtensionPackage.eINSTANCE.getAuditEvent_Information(),
                    newExpression));
            if (createAuditEvent) {
                cmd.append(AddCommand.create(editingDomain,
                        audit,
                        XpdExtensionPackage.eINSTANCE.getAudit_AuditEvent(),
                        auditEvent));
            }
            cmd.setLabel(cmdLabel);
        }
        return cmd;
    }

    public static Command getSetTimerEventScriptGrammarCommand(
            EditingDomain editingDomain, String grammar, EObject eObject,

            boolean preserveExistingScript) {
        Activity activity = (Activity) eObject;
        if (activity == null) {
            return UnexecutableCommand.INSTANCE;
        }
        Deadline oldDeadline = getDeadline(activity);
        if (oldDeadline == null) {
            return UnexecutableCommand.INSTANCE;
        }
        TimerTriggerMode mode = getTimerTriggerMode(activity);

        String newScript = EMPTY_STRING;
        if (preserveExistingScript) {
            Expression oldDeadlineDuration = oldDeadline.getDeadlineDuration();
            /*
             * Sid XPD-7078 Allow user cancellation of script grammar
             * conversion.
             */
            try {
                String expressionAsString =
                        convertScriptGrammar(grammar,
                                eObject,
                                oldDeadlineDuration,
                                null);

                if (expressionAsString != null) {
                    newScript = expressionAsString;
                }

            } catch (GrammarConversionCancelledException e) {
                return null;
            }
        }

        Deadline newDeadline = Xpdl2Factory.eINSTANCE.createDeadline();
        Expression newExpr = Xpdl2Factory.eINSTANCE.createExpression();
        newExpr.getMixed()
                .add(XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(),
                        newScript);
        newExpr.setScriptGrammar(grammar);
        newDeadline.setDeadlineDuration(newExpr);
        Command removeCommand =
                RemoveCommand.create(editingDomain,
                        activity,
                        Xpdl2Package.eINSTANCE.getActivity_Deadline(),
                        oldDeadline);
        Command addCommand =
                AddCommand.create(editingDomain,
                        activity,
                        Xpdl2Package.eINSTANCE.getActivity_Deadline(),
                        newDeadline);
        CompoundCommand cmd = new CompoundCommand();
        cmd.setLabel(Messages.Xpdl2EventAdapter_SetEventScriptGrammar);
        cmd.append(removeCommand);
        cmd.append(addCommand);
        return cmd;
    }

    /**
     * @param editingDomain
     * @param grammar
     * @param eObject
     * 
     * @return The command to reset the reschedule timer event script.
     */
    public static Command getSetRescheduleTimerEventScriptGrammarCommand(
            EditingDomain editingDomain, String grammar, EObject eObject,
            boolean preserveExistingScript, String context) {

        if (eObject instanceof Activity) {
            Activity activity = (Activity) eObject;

            if (activity.getEvent() != null
                    && activity.getEvent().getEventTriggerTypeNode() instanceof TriggerTimer) {
                TriggerTimer triggerTimer =
                        (TriggerTimer) activity.getEvent()
                                .getEventTriggerTypeNode();

                RescheduleTimerScript rescheduleTimerScript =
                        XpdExtensionFactory.eINSTANCE
                                .createRescheduleTimerScript();

                rescheduleTimerScript.setScriptGrammar(grammar);

                /*
                 * Preserve existing duration relative to setting. And also the
                 * scrkipt text if so requested.
                 */
                RescheduleTimerScript existingScript =
                        (RescheduleTimerScript) Xpdl2ModelUtil
                                .getOtherElement(triggerTimer,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_RescheduleTimerScript());

                String preserveScript = EMPTY_STRING;

                if (existingScript != null) {
                    rescheduleTimerScript.setDurationRelativeTo(existingScript
                            .getDurationRelativeTo());

                    if (preserveExistingScript) {
                        /*
                         * Sid XPD-7078 Allow user cancellation of script
                         * grammar conversion.
                         */
                        try {
                            String expressionAsString =
                                    convertScriptGrammar(grammar,
                                            eObject,
                                            existingScript,
                                            context);

                            if (expressionAsString != null) {
                                preserveScript = expressionAsString;

                            }

                        } catch (GrammarConversionCancelledException e) {
                            return null;
                        }
                    }
                }

                rescheduleTimerScript
                        .getMixed()
                        .add(XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(),
                                preserveScript);

                CompoundCommand cmd =
                        new CompoundCommand(
                                Messages.ProcessScriptUtil_SetRescheduleTimerScriptGrammar_menu);

                cmd.append(Xpdl2ModelUtil
                        .getSetOtherElementCommand(editingDomain,
                                triggerTimer,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_RescheduleTimerScript(),
                                rescheduleTimerScript));

                return cmd;
            }
        }

        return UnexecutableCommand.INSTANCE;
    }

    public static Command getRemoveTimerEventScriptGrammarCommand(
            EditingDomain editingDomain, EObject eObject) {
        Activity activity = (Activity) eObject;
        if (activity == null) {
            return UnexecutableCommand.INSTANCE;
        }
        Deadline oldDeadline = getDeadline(activity);
        if (oldDeadline == null) {
            return UnexecutableCommand.INSTANCE;
        }
        Command removeCommand =
                SetCommand.create(editingDomain,
                        oldDeadline,
                        Xpdl2Package.eINSTANCE.getDeadline_DeadlineDuration(),
                        null);
        CompoundCommand cmd = new CompoundCommand();
        cmd.setLabel(Messages.Xpdl2EventAdapter_RemoveEventScriptGrammar);
        cmd.append(removeCommand);
        return cmd;
    }

    /**
     * @param editingDomain
     * @param activity
     * @return Command to delete the reschedule timer script from a timer event.
     */
    public static Command getRemoveRescheduleTimerEventScriptGrammarCommand(
            EditingDomain editingDomain, EObject eObject) {

        if (eObject instanceof Activity) {

            Activity activity = (Activity) eObject;

            if (activity != null
                    && activity.getEvent() != null
                    && activity.getEvent().getEventTriggerTypeNode() instanceof TriggerTimer) {

                TriggerTimer triggerTimer =
                        (TriggerTimer) activity.getEvent()
                                .getEventTriggerTypeNode();

                RescheduleTimerScript rescheduleTimerScript =
                        (RescheduleTimerScript) Xpdl2ModelUtil
                                .getOtherElement(triggerTimer,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_RescheduleTimerScript());

                if (rescheduleTimerScript != null) {
                    CompoundCommand cmd =
                            new CompoundCommand(
                                    Messages.ProcessScriptUtil_RemvoeRescheduleTimerScript_menu);

                    cmd.append(Xpdl2ModelUtil
                            .getRemoveOtherElementCommand(editingDomain,
                                    triggerTimer,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_RescheduleTimerScript(),
                                    rescheduleTimerScript));

                    return cmd;
                }

            }
        }

        return null;
    }

    public static Command getSetSequenceFlowScriptGrammarCommand(
            EditingDomain editingDomain, String scriptGrammar, EObject eObject,

            boolean preserveExistingScript) {
        CompoundCommand cmd = new CompoundCommand();
        cmd.setLabel(Messages.SetTransitionScriptGrammarCommand);
        Transition transition = (Transition) eObject;
        if (transition == null) {
            return null;
        }
        Condition oldCondition = transition.getCondition();
        if (oldCondition == null) {
            return cmd;
        }
        String newScript = EMPTY_STRING;
        if (preserveExistingScript) {
            Expression oldExpression = oldCondition.getExpression();
            /*
             * Sid XPD-7078 Allow user cancellation of script grammar
             * conversion.
             */
            try {
                String expressionAsString =
                        convertScriptGrammar(scriptGrammar,
                                eObject,
                                oldExpression,
                                null);

                if (expressionAsString != null) {
                    newScript = expressionAsString;

                }

            } catch (GrammarConversionCancelledException e) {
                return null;
            }
        }

        Condition newCondition = Xpdl2Factory.eINSTANCE.createCondition();
        newCondition.setType(ConditionType.CONDITION_LITERAL);
        Expression expression = Xpdl2Factory.eINSTANCE.createExpression();
        expression.setScriptGrammar(scriptGrammar);
        expression.getMixed()
                .add(XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(),
                        newScript);
        newCondition.setExpression(expression);
        Command removeCommand =
                SetCommand.create(editingDomain,
                        transition,
                        Xpdl2Package.eINSTANCE.getTransition_Condition(),
                        null);
        Command setCommand =
                SetCommand.create(editingDomain,
                        transition,
                        Xpdl2Package.eINSTANCE.getTransition_Condition(),
                        newCondition);
        cmd.append(removeCommand);
        cmd.append(setCommand);
        return cmd;
    }

    public static Command getRemoveSequenceFlowScriptGrammarCommand(
            EditingDomain editingDomain, EObject eObject) {
        CompoundCommand cmd = new CompoundCommand();
        cmd.setLabel(Messages.RemoveTransitionScriptGrammarCommand);
        Transition transition = (Transition) eObject;
        if (transition == null) {
            return null;
        }
        Condition oldCondition = transition.getCondition();
        if (oldCondition == null) {
            return cmd;
        }
        Command removeCommand =
                SetCommand.create(editingDomain,
                        oldCondition,
                        Xpdl2Package.eINSTANCE.getCondition_Expression(),
                        null);
        cmd.append(removeCommand);
        return cmd;
    }

    public static Command getSetStandardLoopExpressionGrammarCommand(
            EditingDomain editingDomain, String scriptGrammar,
            Activity activity, boolean preserveExistingScript) {
        if (activity == null) {
            return null;
        }

        CompoundCommand cmd = new CompoundCommand();

        LoopStandard loopStandard =
                getOrAddLoopStandard(editingDomain, activity, cmd);

        String newScript = EMPTY_STRING;
        if (preserveExistingScript) {
            Expression oldExpression = loopStandard.getLoopCondition();
            /*
             * Sid XPD-7078 Allow user cancellation of script grammar
             * conversion.
             */
            try {
                String expressionAsString =
                        convertScriptGrammar(scriptGrammar,
                                activity,
                                oldExpression);
                if (expressionAsString != null) {
                    newScript = expressionAsString;
                }

            } catch (GrammarConversionCancelledException e) {
                return null;
            }
        }

        // Create/Reset the script under UserTaskScripts
        Expression newExpression = Xpdl2Factory.eINSTANCE.createExpression();
        newExpression.setScriptGrammar(scriptGrammar);
        newExpression.getMixed()
                .add(XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(),
                        newScript);
        cmd.append(SetCommand.create(editingDomain,
                loopStandard,
                Xpdl2Package.eINSTANCE.getLoopStandard_LoopCondition(),
                newExpression));
        cmd.setLabel(Messages.ProcessScriptUtil_SetStdLoopExprGrammar);
        return cmd;
    }

    public static Command getRemoveStandardLoopExpressionGrammarCommand(
            EditingDomain editingDomain, Activity activity) {
        CompoundCommand cmd = new CompoundCommand();
        if (activity == null) {
            return null;
        }

        LoopStandard loopStandard =
                getOrAddLoopStandard(editingDomain, activity, cmd);

        cmd.append(SetCommand.create(editingDomain,
                loopStandard,
                Xpdl2Package.eINSTANCE.getLoopStandard_LoopCondition(),
                null));
        cmd.setLabel(Messages.ProcessScriptUtil_RemoveStdLoopExprGrammar);
        return cmd;
    }

    public static Command getSetMILoopExprScriptGrammarCommand(
            EditingDomain editingDomain, String grammar, Activity activity,

            boolean preserveExistingScript) {

        return getSetMultiInstanceScriptGrammarCommand(editingDomain,
                Xpdl2Package.eINSTANCE.getLoopMultiInstance_MICondition(),
                grammar,
                Messages.ProcessScriptUtil_SetMIExprGrammar,
                activity,

                preserveExistingScript,
                MI_LOOP_EXPR);
    }

    public static Command getRemoveMILoopExprScriptGrammarCommand(
            EditingDomain editingDomain, Activity activity) {
        return getRemoveMultiInstanceScriptGrammarCommand(editingDomain,
                Xpdl2Package.eINSTANCE.getLoopMultiInstance_MICondition(),
                Messages.ProcessScriptUtil_RemoveMIExprGrammar,
                activity,
                MI_LOOP_EXPR);
    }

    public static Command getSetMIComplexExitScriptGrammarCommand(
            EditingDomain editingDomain, String grammar, Activity activity,

            boolean preserveExistingScript) {
        return getSetMultiInstanceScriptGrammarCommand(editingDomain,
                Xpdl2Package.eINSTANCE
                        .getLoopMultiInstance_ComplexMIFlowCondition(),
                grammar,
                Messages.ProcessScriptUtil_SetMIComplexExitExprGrammar,
                activity,

                preserveExistingScript,
                MI_COMPLEX_EXIT_EXPR);
    }

    public static Command getRemoveMIComplexExitScriptGrammarCommand(
            EditingDomain editingDomain, Activity activity) {
        return getRemoveMultiInstanceScriptGrammarCommand(editingDomain,
                Xpdl2Package.eINSTANCE
                        .getLoopMultiInstance_ComplexMIFlowCondition(),
                Messages.ProcessScriptUtil_RemoveMIComplexExitExprGrammar,
                activity,
                MI_COMPLEX_EXIT_EXPR);
    }

    public static Command getSetMIAdditionalInstancesScriptGrammarCommand(
            EditingDomain editingDomain, String grammar, Activity activity,

            boolean preserveExistingScript) {

        if (activity == null) {
            return null;
        }

        CompoundCommand cmd = new CompoundCommand();

        LoopMultiInstance loopMultiInstance =
                getOrAddLoopMultiInstance(editingDomain, activity, cmd);
        if (loopMultiInstance != null) {
            MultiInstanceScripts miScripts = getMILoopScripts(activity);
            if (miScripts == null) {
                miScripts =
                        XpdExtensionFactory.eINSTANCE
                                .createMultiInstanceScripts();
                cmd.append(Xpdl2ModelUtil
                        .getSetOtherElementCommand(editingDomain,
                                loopMultiInstance,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_MultiInstanceScripts(),
                                miScripts));
            }

            String newScript = EMPTY_STRING;
            if (preserveExistingScript) {
                Expression oldExpression = null;
                oldExpression = miScripts.getAdditionalInstances();
                /*
                 * Sid XPD-7078 Allow user cancellation of script grammar
                 * conversion.
                 */
                try {
                    String expressionAsString =
                            convertScriptGrammar(grammar,
                                    activity,
                                    oldExpression);
                    if (expressionAsString != null) {
                        newScript = expressionAsString;
                    }

                } catch (GrammarConversionCancelledException e) {
                    return null;
                }
            }

            // Create/Reset the script under UserTaskScripts
            Expression newExpression =
                    Xpdl2Factory.eINSTANCE.createExpression();
            newExpression.setScriptGrammar(grammar);
            newExpression
                    .getMixed()
                    .add(XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(),
                            newScript);
            cmd.append(SetCommand.create(editingDomain,
                    miScripts,
                    XpdExtensionPackage.eINSTANCE
                            .getMultiInstanceScripts_AdditionalInstances(),
                    newExpression));
            cmd.setLabel(Messages.ProcessScriptUtil_RemoveMIAdditionalInstancesExprGrammar);
        }

        return cmd;
    }

    public static Command getRemoveMIAdditionalInstancesScriptGrammarCommand(
            EditingDomain editingDomain, Activity activity) {

        if (activity == null) {
            return null;
        }

        CompoundCommand cmd = new CompoundCommand();

        Loop loop = activity.getLoop();
        if (loop != null) {
            MultiInstanceScripts miScripts = getMILoopScripts(activity);
            if (miScripts != null) {
                Expression oldExpression = miScripts.getAdditionalInstances();

                if (oldExpression != null) {
                    // Remove the script under UserTaskScripts
                    cmd.append(SetCommand
                            .create(editingDomain,
                                    miScripts,
                                    XpdExtensionPackage.eINSTANCE
                                            .getMultiInstanceScripts_AdditionalInstances(),
                                    null));
                    cmd.setLabel(Messages.ProcessScriptUtil_RemoveMIAdditionalInstancesExprGrammar);
                }
            }
        }

        return cmd;
    }

    private static Command getSetMultiInstanceScriptGrammarCommand(
            EditingDomain editingDomain, EStructuralFeature scriptFeature,
            String scriptGrammar, String cmdLabel, Activity activity,

            boolean preserveExistingScript, String scriptType) {

        if (activity == null) {
            return null;
        }

        CompoundCommand cmd = new CompoundCommand();

        LoopMultiInstance loopMultiInstance =
                getOrAddLoopMultiInstance(editingDomain, activity, cmd);
        if (loopMultiInstance != null) {

            String newScript = EMPTY_STRING;
            if (preserveExistingScript) {
                Expression oldExpression = null;
                if (scriptType.equalsIgnoreCase(MI_LOOP_EXPR)) {
                    oldExpression = loopMultiInstance.getMICondition();

                } else if (scriptType.equalsIgnoreCase(MI_COMPLEX_EXIT_EXPR)) {
                    oldExpression =
                            loopMultiInstance.getComplexMIFlowCondition();

                }

                /*
                 * Sid XPD-7078 Allow user cancellation of script grammar
                 * conversion.
                 */
                try {
                    String expressionAsString =
                            convertScriptGrammar(scriptGrammar,
                                    activity,
                                    oldExpression);
                    if (expressionAsString != null) {
                        newScript = expressionAsString;
                    }

                } catch (GrammarConversionCancelledException e) {
                    return null;
                }
            }

            // Create/Reset the script under UserTaskScripts
            Expression newExpression =
                    Xpdl2Factory.eINSTANCE.createExpression();
            newExpression.setScriptGrammar(scriptGrammar);
            newExpression
                    .getMixed()
                    .add(XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(),
                            newScript);
            cmd.append(SetCommand.create(editingDomain,
                    loopMultiInstance,
                    scriptFeature,
                    newExpression));
            cmd.setLabel(cmdLabel);

        }

        return cmd;
    }

    private static Command getRemoveMultiInstanceScriptGrammarCommand(
            EditingDomain editingDomain, EStructuralFeature scriptFeature,
            String cmdLabel, Activity activity, String scriptType) {
        CompoundCommand cmd = new CompoundCommand();

        if (activity == null) {
            return null;
        }
        Loop loop = activity.getLoop();
        if (loop != null) {
            LoopMultiInstance loopMultiInstance = loop.getLoopMultiInstance();

            if (loopMultiInstance != null) {
                Expression oldExpression = null;
                if (scriptType.equalsIgnoreCase(MI_LOOP_EXPR)) {
                    oldExpression = loopMultiInstance.getMICondition();

                } else if (scriptType.equalsIgnoreCase(MI_COMPLEX_EXIT_EXPR)) {
                    oldExpression =
                            loopMultiInstance.getComplexMIFlowCondition();
                }
                if (oldExpression != null) {
                    // Remove the script under UserTaskScripts
                    cmd.append(SetCommand.create(editingDomain,
                            loopMultiInstance,
                            scriptFeature,
                            null));
                    cmd.setLabel(cmdLabel);
                }
            }
        }
        return cmd;
    }

    /**
     * Get the expression as text from expression object then convert it using
     * the script grammar converter.
     * 
     * @param scriptConverter
     *            Converter for grammar (or <code>null</code> to use script as
     *            is).
     * @param newGrammar
     *            New grammar
     * @param expression
     *            Expression
     * @param contextObject
     *            The context object (activity, flow etc).
     * 
     * @return The converted (or not if no converter) script.
     * 
     * @throws GrammarConversionCancelledException
     *             If user cancels the operation.
     */
    private static String convertScriptGrammar(String newGrammar,
            EObject contextObject, Expression expression, String context)
            throws GrammarConversionCancelledException {
        String expressionText = null;

        if (expression != null) {
            expressionText = getExpressionAsString(expression);

            if (expressionText != null) {

                /*
                 * Get the highest priority, enabled converter contributed for
                 * these grammars
                 */
                IScriptGrammarConverter scriptConverter =
                        ScriptGrammarConverterExtPointHelper
                                .getScriptGrammarConverter(expression
                                        .getScriptGrammar(),
                                        newGrammar,
                                        contextObject);

                if (scriptConverter != null) {

                    expressionText =
                            scriptConverter.convertScriptForGrammar(expression
                                    .getScriptGrammar(),
                                    expressionText,
                                    newGrammar,
                                    expression,
                                    context);
                }
            }
        }

        return expressionText != null ? expressionText : ""; //$NON-NLS-1$
    }

    /**
     * Get the expression as text from expression object then convert it using
     * the script grammar converter.
     * 
     * @param scriptConverter
     *            Converter for grammar (or <code>null</code> to use script as
     *            is).
     * @param newGrammar
     *            New grammar
     * @param expression
     *            Expression
     * @param contextObject
     *            The context object (activity, flow etc).
     * 
     * @return The converted (or not if no converter) script.
     * @throws GrammarConversionCancelledException
     *             USer cancelled grammar conversion
     */
    private static String convertScriptGrammar(String newGrammar,
            EObject contextObject, Expression expression)
            throws GrammarConversionCancelledException {

        return convertScriptGrammar(newGrammar, contextObject, expression, null);
    }

    /**
     * get all the script informations of the sub process task for the given
     * grammar.
     * 
     * @param activity
     * @param scriptGrammar
     * @return
     */
    public static List<ScriptInformation> getAllCatchSignalScriptInformations(
            Activity activity, String scriptGrammar) {
        List<ScriptInformation> allScriptInformations =
                new ArrayList<ScriptInformation>();
        if (EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL.equals(EventObjectUtil
                .getEventTriggerType(activity))) {
            // Get the unmapped script informations
            List<ScriptInformation> tempScriptInformationList =
                    getActivityUnmappedScriptInformations(activity,
                            scriptGrammar);
            if (tempScriptInformationList != null
                    && !tempScriptInformationList.isEmpty()) {
                allScriptInformations.addAll(tempScriptInformationList);
            }

            // Get the mapped script informations
            List<DataMapping> scriptMappings =
                    getScriptDataMappings(Xpdl2ModelUtil.getDataMappings(activity,
                            DirectionType.IN_LITERAL),
                            scriptGrammar);
            scriptMappings.addAll(getScriptDataMappings(Xpdl2ModelUtil
                    .getDataMappings(activity, DirectionType.OUT_LITERAL),
                    scriptGrammar));

            allScriptInformations
                    .addAll(getAllMappedScriptInformation(scriptMappings));
        }
        return allScriptInformations;
    }

    /**
     * Checks if the Activity passed is an Adhoc Task with Precondition Script
     * with Specified Grammar.
     * 
     * @param activity
     *            the Activity
     * @param scriptGrammar
     *            the type of Script grammar expected.
     * @return <code>true</code> if the Activity is an Adhoc Task having
     *         precondition Script of the specified grammar, else
     *         <code>false</code>
     */
    public static boolean isAdhocPreconditionTaskWithScriptType(
            Activity activity, String scriptGrammar) {
        boolean toReturn = false;
        if (null != activity) {
            Object adhocConfig =
                    Xpdl2ModelUtil.getOtherElement(activity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_AdHocTaskConfiguration());

            if (adhocConfig instanceof AdHocTaskConfigurationType) {

                AdHocTaskConfigurationType adhocConfigType =
                        (AdHocTaskConfigurationType) adhocConfig;

                EnablementType enablement = adhocConfigType.getEnablement();

                if (enablement != null) {
                    Expression preconditionExpression =
                            enablement.getPreconditionExpression();

                    if (preconditionExpression == null) {
                        return toReturn;
                    }
                    if (scriptGrammar.equalsIgnoreCase(preconditionExpression
                            .getScriptGrammar())) {
                        toReturn = true;
                    }
                }
            }
        }
        return toReturn;
    }
}
