/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.common.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.simulation.EnumBasedExpressionType;
import com.tibco.xpd.simulation.EnumerationValueType;
import com.tibco.xpd.simulation.ExpressionType;
import com.tibco.xpd.simulation.ParameterDependentDistribution;
import com.tibco.xpd.simulation.ParameterDistribution;
import com.tibco.xpd.simulation.SimulationFactory;
import com.tibco.xpd.simulation.SimulationPackage;
import com.tibco.xpd.simulation.SplitSimulationDataType;
import com.tibco.xpd.simulation.StructuredConditionType;
import com.tibco.xpd.simulation.TransitionSimulationDataType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * @author nwilson
 */
public final class SimulationCommandUtils {

    /**
     * Private constructor.
     */
    private SimulationCommandUtils() {
    }

    /**
     * This method will change the formal parameter value in the transition
     * extended attributes when it is being changed for a particular split.
     * 
     * @param originalCommand The original command.
     * @param domain The editing domain.
     * @param owner The owning object.
     * @param feature The feature to change.
     * @param value The new value.
     * @return The command to perform the change.
     */
    public static Command assignSplitParameterInTransitionEA(
            Command originalCommand, EditingDomain domain, EObject owner,
            EStructuralFeature feature, Object value) {
        CompoundCommand compoundCmd = new CompoundCommand();
        compoundCmd.append(originalCommand);
        String newParameterId = (String) value;
        Activity activity = SimulationXpdlUtils.getActivity(owner);
        SplitSimulationDataType activitySplitData =
                SimulationXpdlUtils.getSplitSimulationData(activity);
        EStructuralFeature eaFeature =
                Xpdl2Package.eINSTANCE
                        .getExtendedAttributesContainer_ExtendedAttributes();
        List tranList = getOutgoingTransitionList(owner);
        for (Iterator iter = tranList.iterator(); iter.hasNext();) {
            Transition tran = (Transition) iter.next();
            TransitionSimulationDataType transitionSimulationData =
                    SimulationXpdlUtils.getTransitionSimulationData(tran);
            if (transitionSimulationData == null) {
                ExtendedAttribute extAttribute =
                        createTransitionExtendedAttr(tran, newParameterId);
                Command command =
                        AddCommand
                                .create(domain, tran, eaFeature, extAttribute);
                compoundCmd.append(command);
                continue;
            }
            StructuredConditionType structuredCondition =
                    transitionSimulationData.getStructuredCondition();
            String parameterId = structuredCondition.getParameterId();
            if (newParameterId.equals(parameterId)) {
                continue;
            }
            Command command =
                    SetCommand.create(domain, structuredCondition,
                            SimulationPackage.eINSTANCE
                                    .getStructuredConditionType_ParameterId(),
                            newParameterId);
            compoundCmd.append(command);
            command =
                    SetCommand
                            .create(
                                    domain,
                                    structuredCondition,
                                    SimulationPackage.eINSTANCE
                                            .getStructuredConditionType_Operator(),
                                    "="); //$NON-NLS-1$
            compoundCmd.append(command);
            command =
                    SetCommand
                            .create(
                                    domain,
                                    structuredCondition,
                                    SimulationPackage.eINSTANCE
                                            .getStructuredConditionType_ParameterValue(),
                                    ""); //$NON-NLS-1$
            compoundCmd.append(command);

        }
        originalCommand = compoundCmd.unwrap();
        return originalCommand;
    }

    /**
     * This method add or delete simulation extended attribute to the transition
     * depending on the value passed in the value parameter.
     * 
     * @param originalCommand The original command.
     * @param domain The editing domain.
     * @param owner The owning object.
     * @param feature The feature to change.
     * @param value The new value.
     * @return The command to perform the change.
     */
    public static Command updateParameterBasedSplitTransitionEA(
            Command originalCommand, EditingDomain domain, EObject owner,
            EStructuralFeature feature, Object value) {
        CompoundCommand compoundCmd = new CompoundCommand();
        compoundCmd.append(originalCommand);
        Boolean parameterDependent = (Boolean) value;
        Activity activity = SimulationXpdlUtils.getActivity(owner);
        SplitSimulationDataType activitySplitData =
                SimulationXpdlUtils.getSplitSimulationData(activity);
        List tranList = getOutgoingTransitionList(owner);
        EStructuralFeature eaFeature =
                Xpdl2Package.eINSTANCE
                        .getExtendedAttributesContainer_ExtendedAttributes();
        for (Iterator iter = tranList.iterator(); iter.hasNext();) {
            Transition tran = (Transition) iter.next();
            TransitionSimulationDataType transitionSimulationData =
                    SimulationXpdlUtils.getTransitionSimulationData(tran);
            Command command = null;
            if (parameterDependent.booleanValue()) {
                if (transitionSimulationData != null) {
                    command =
                            RemoveCommand.create(domain,
                                    transitionSimulationData.eContainer());
                    compoundCmd.append(command);
                }
                String parameterId = "";//$NON-NLS-1$
                if (activitySplitData != null
                        && activitySplitData.getSplitParameter() != null) {
                    parameterId =
                            activitySplitData.getSplitParameter()
                                    .getParameterId();
                }
                ExtendedAttribute extAttribute =
                        createTransitionExtendedAttr(tran, parameterId);
                command =
                        AddCommand
                                .create(domain, tran, eaFeature, extAttribute);
                compoundCmd.append(command);
            } else {
                // delete transition sim data
                if (transitionSimulationData != null) {
                    command =
                            RemoveCommand.create(domain,
                                    transitionSimulationData.eContainer());
                    compoundCmd.append(command);
                }
            }
        }
        originalCommand = compoundCmd.unwrap();
        return originalCommand;
    }

    /**
     * @param owner The owning object.
     * @return A list of associated transitions.
     */
    private static List getOutgoingTransitionList(EObject owner) {
        Process process = SimulationXpdlUtils.getWorkflowProcess(owner);
        Activity activity = SimulationXpdlUtils.getActivity(owner);
        String actId = activity.getId();
        EList transitions = process.getTransitions();
        ArrayList<Transition> tranList = new ArrayList<Transition>();
        for (Iterator iter = transitions.iterator(); iter.hasNext();) {
            Transition tran = (Transition) iter.next();
            if (tran.getFrom().equals(actId)) {
                tranList.add(tran);
            }
        }
        return tranList;
    }

    /**
     * This method changes the enumeration value of the formal parameter of the
     * process in the transition and activity.
     * 
     * @param cmd The original command.
     * @param domain The editing domain.
     * @param owner The owning object.
     * @param feature The feature to modify.
     * @param value The new value.
     * @return The command to perform the change.
     */
    public static Command changeFPEnumerationValue(Command cmd,
            EditingDomain domain, EObject owner, EStructuralFeature feature,
            Object value) {
        if (feature == SimulationPackage.eINSTANCE
                .getEnumerationValueType_Value()) {
            EnumerationValueType enumValue = (EnumerationValueType) owner;
            ParameterDistribution paraDist =
                    (ParameterDistribution) enumValue.eContainer();
            Process process = SimulationXpdlUtils.getWorkflowProcess(paraDist);
            String parameterId = paraDist.getParameterId();
            CompoundCommand compoundCmd = new CompoundCommand();
            Command command =
                    changeFPValueInActivityEA(process, domain, parameterId,
                            enumValue.getValue(), value);
            if (command != null) {
                compoundCmd.append(command);
            }
            command =
                    changeFPValueInTransitionEA(process, domain, parameterId,
                            enumValue.getValue(), value);
            if (command != null) {
                compoundCmd.append(command);
            }
            compoundCmd.append(cmd);
            cmd = compoundCmd.unwrap();
        }
        return cmd;
    }

    /**
     * This method will update the enum value of the formal parameter in the
     * activity if it is using parameter based distribution.
     * 
     * @param process The containing process.
     * @param domain The editing domain.
     * @param fpId The formal parameter ID.
     * @param oldValue The old value.
     * @param newValue The new value.
     * @return The command to perform the change.
     */
    private static Command changeFPValueInActivityEA(Process process,
            EditingDomain domain, String fpId, String oldValue, Object newValue) {
        EList activities = process.getActivities();
        CompoundCommand cmd = null;
        for (Iterator iter = activities.iterator(); iter.hasNext();) {
            Activity eachActivity = (Activity) iter.next();
            List parameterDependentDistribution =
                    SimulationXpdlUtils.getParameterDependentDistribution(
                            eachActivity, fpId);
            if (parameterDependentDistribution == null) {
                continue;
            }
            for (Iterator iterator = parameterDependentDistribution.iterator(); iterator
                    .hasNext();) {
                ParameterDependentDistribution element =
                        (ParameterDependentDistribution) iterator.next();
                ExpressionType expression = element.getExpression();
                EnumBasedExpressionType enumBasedExpression =
                        expression.getEnumBasedExpression();
                String paramValue = (String) enumBasedExpression.getEnumValue();
                if (!paramValue.equals(oldValue)) {
                    continue;
                }
                // only the enumBasedExpression with the old value
                if (paramValue.equals(newValue)) {
                    continue;
                }
                Command idCommand =
                        SetCommand
                                .create(
                                        domain,
                                        enumBasedExpression,
                                        SimulationPackage.eINSTANCE
                                                .getEnumBasedExpressionType_EnumValue(),
                                        newValue);
                if (cmd == null) {
                    cmd = new CompoundCommand();
                }
                cmd.append(idCommand);

            }
        }
        return cmd;
    }

    /**
     * This method will update the formal parameter name in the activity if it
     * is using parameter based distribution.
     * 
     * @param activity The activity containing the parameter.
     * @param domain The editing domain.
     * @param fpId The formal parameter ID.
     * @param newValue The new value.
     * @return The command to perform the change.
     */
    public static Command changeFPNameInActivityEA(Activity activity,
            EditingDomain domain, String fpId, Object newValue) {
        CompoundCommand cmd = null;
        List parameterDependentDistributionList =
                SimulationXpdlUtils.getParameterDependentDistribution(activity,
                        fpId);
        if (parameterDependentDistributionList == null) {
            return cmd;
        }
        for (Iterator iterator = parameterDependentDistributionList.iterator(); iterator
                .hasNext();) {
            ParameterDependentDistribution element =
                    (ParameterDependentDistribution) iterator.next();
            ExpressionType expression = element.getExpression();
            EnumBasedExpressionType enumBasedExpression =
                    expression.getEnumBasedExpression();
            Command idCommand =
                    SetCommand.create(domain, enumBasedExpression,
                            SimulationPackage.eINSTANCE
                                    .getEnumBasedExpressionType_ParamName(),
                            newValue);
            if (cmd == null) {
                cmd = new CompoundCommand();
            }
            cmd.append(idCommand);
        }
        return cmd;
    }

    /**
     * @param process The containing process.
     * @param domain The editing domain.
     * @param fpId The formal parameter ID.
     * @param oldValue The old value.
     * @param newValue The new value.
     * @return The command to perform the change.
     */
    private static Command changeFPValueInTransitionEA(Process process,
            EditingDomain domain, String fpId, String oldValue, Object newValue) {
        EList transitions = process.getTransitions();
        CompoundCommand cmd = null;
        EStructuralFeature paramFeature =
                SimulationPackage.eINSTANCE
                        .getStructuredConditionType_ParameterValue();
        for (Iterator iter = transitions.iterator(); iter.hasNext();) {
            Transition eachTransition = (Transition) iter.next();
            TransitionSimulationDataType transitionSimulationData =
                    SimulationXpdlUtils
                            .getTransitionSimulationData(eachTransition);
            if (transitionSimulationData == null) {
                continue;
            }
            StructuredConditionType structuredCondition =
                    transitionSimulationData.getStructuredCondition();
            if (structuredCondition.getParameterId().equals(fpId)
                    && structuredCondition.getParameterValue().equals(oldValue)) {
                Command idCommand =
                        SetCommand
                                .create(
                                        domain,
                                        structuredCondition,
                                        paramFeature,
                                        newValue);
                if (cmd == null) {
                    cmd = new CompoundCommand();
                }
                cmd.append(idCommand);
            }
        }
        return cmd;
    }

    /**
     * Returns Extended Attribute for a transition which has Transition
     * simulation data and populates the simulation data with dfault value and
     * parameterId from the SplitSimulationData of the Activity.
     * 
     * @param splitData The split simulation data type.
     * @param tran The transition.
     * @return The extended attribute.
     */
    private static ExtendedAttribute createTransitionExtendedAttr(Transition tran, String parameterId) {
        ExtendedAttribute extAttribute =
                Xpdl2Factory.eINSTANCE.createExtendedAttribute();
        extAttribute.setName("TransitionSimulationData"); //$NON-NLS-1$
        TransitionSimulationDataType tranSimData =
                SimulationFactory.eINSTANCE
                        .createTransitionSimulationDataType();
        tranSimData.setParameterDeterminedCondition(true);
        StructuredConditionType condType =
                SimulationFactory.eINSTANCE.createStructuredConditionType();
        condType.setParameterId(parameterId);
        condType.setOperator("="); //$NON-NLS-1$
        condType.setParameterValue(""); //$NON-NLS-1$
        // Condition condition = tran.getCondition();
        // if (condition.getType().equals(ConditionType.OTHERWISE_LITERAL)) {
        // condType.setParameterValue("DEFAULT");
        // }
        tranSimData.setStructuredCondition(condType);
        extAttribute.getMixed().add(
                SimulationPackage.eINSTANCE
                        .getDocumentRoot_TransitionSimulationData(),
                tranSimData);
        return extAttribute;
    }

}
