package com.tibco.xpd.simulation.edit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.simulation.ParameterDistribution;
import com.tibco.xpd.simulation.SimulationPackage;
import com.tibco.xpd.simulation.SplitParameterType;
import com.tibco.xpd.simulation.SplitSimulationDataType;
import com.tibco.xpd.simulation.StructuredConditionType;
import com.tibco.xpd.simulation.TransitionSimulationDataType;
import com.tibco.xpd.simulation.WorkflowProcessSimulationDataType;
import com.tibco.xpd.simulation.common.util.SimulationXpdlUtils;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.commands.DeleteCommandWrapper;

public class DeleteFormalParameterContribution implements DeleteCommandWrapper {

    public Command createDeleteCommand(Command originalCommand,
            EditingDomain domain, EObject owner, EStructuralFeature feature,
            Collection collection) {
        
        CompoundCommand compoundCmd = new CompoundCommand();
        compoundCmd.append(originalCommand);
        if (feature == Xpdl2Package.eINSTANCE
                .getFormalParametersContainer_FormalParameters()) {
            Process process = (Process) owner;
            for (Iterator iter = collection.iterator(); iter.hasNext();) {
                FormalParameter fp = (FormalParameter) iter.next();
                String fpId = fp.getName();
                Command paramDistcommand = deleteParameterDistribution(domain,
                        process, fpId);
                if (paramDistcommand != null) {
                    compoundCmd.append(paramDistcommand);
                }
                Command activityCommand = resetActivityEA(domain, process, fpId);
                if (activityCommand != null) {
                    compoundCmd.append(activityCommand);
                }
                Command tranCommand = resetTransitionSimulationData(domain,
                        process, fpId);
                if (tranCommand != null) {
                    compoundCmd.append(tranCommand);
                }
            }
            originalCommand = compoundCmd.unwrap();
        }
        return originalCommand;
    }

    private Command deleteParameterDistribution(EditingDomain domain,
            Process process, String fpId) {
        Command command = null;
        WorkflowProcessSimulationDataType workflowProcessSimulationData = SimulationXpdlUtils
                .getWorkflowProcessSimulationData(process);
        if (workflowProcessSimulationData == null) {
            return command;
        }
        EList parameterDistribution = workflowProcessSimulationData
                .getParameterDistribution();
        ArrayList list = new ArrayList();
        for (Iterator iter = parameterDistribution.iterator(); iter.hasNext();) {
            ParameterDistribution paraDistribution = (ParameterDistribution) iter
                    .next();
            if (paraDistribution.getParameterId().equalsIgnoreCase(fpId)) {
                list.add(paraDistribution);
            }
        }
        if (list.size() > 0) {
            command = RemoveCommand.create(domain, list);
        }
        return command;
    }

    private Command resetActivityEA(EditingDomain domain, Process process,
            String fpId) {
        EList activities = process.getActivities();
        CompoundCommand compoundCommand = null;
        for (Iterator iter = activities.iterator(); iter.hasNext();) {
            Activity activity = (Activity) iter.next();
            /*
             * kam::not deleting the paramter dependent distributin as we would
             * like to report the problem by a validation rule. EList
             * parameterDependentDistribution = SimulationXpdlUtils
             * .getParameterDependentDistribution(activity, fpId); if
             * (parameterDependentDistribution != null) { if (compoundCommand ==
             * null) { compoundCommand = new CompoundCommand(); } Command
             * command = RemoveCommand.create(domain,
             * parameterDependentDistribution); compoundCommand.append(command); }
             */
            SplitSimulationDataType splitSimulationData = SimulationXpdlUtils
                    .getSplitSimulationData(activity);
            if (splitSimulationData == null) {
                continue;
            }
            if (splitSimulationData.isParameterDeterminedSplit()) {
                SplitParameterType splitParameter = splitSimulationData
                        .getSplitParameter();
                if (splitParameter.getParameterId().equals(fpId)) {
                    Command command = SetCommand.create(domain, splitParameter,
                            SimulationPackage.eINSTANCE
                                    .getSplitParameterType_ParameterId(), ""); //$NON-NLS-1$
                    if (compoundCommand == null) {
                        compoundCommand = new CompoundCommand();
                    }
                    compoundCommand.append(command);
                }

            }

        }
        return compoundCommand;
    }

    private Command resetTransitionSimulationData(EditingDomain domain,
            Process process, String fpId) {
        EList transitions = process.getTransitions();
        CompoundCommand compoundCommand = null;
        for (Iterator iter = transitions.iterator(); iter.hasNext();) {
            Transition transition = (Transition) iter.next();
            TransitionSimulationDataType transitionSimulationData = SimulationXpdlUtils
                    .getTransitionSimulationData(transition);
            if (transitionSimulationData != null) {
                if (transitionSimulationData.isParameterDeterminedCondition()) {
                    StructuredConditionType structuredCondition = transitionSimulationData
                            .getStructuredCondition();
                    if (structuredCondition.getParameterId().equals(fpId)) {
                        Command idCommand = SetCommand
                                .create(
                                        domain,
                                        structuredCondition,
                                        SimulationPackage.eINSTANCE
                                                .getStructuredConditionType_ParameterId(),
                                        ""); //$NON-NLS-1$
                        Command valueCommand = SetCommand
                                .create(
                                        domain,
                                        structuredCondition,
                                        SimulationPackage.eINSTANCE
                                                .getStructuredConditionType_ParameterValue(),
                                        ""); //$NON-NLS-1$
                        if (compoundCommand == null) {
                            compoundCommand = new CompoundCommand();
                        }
                        compoundCommand.append(idCommand);
                        compoundCommand.append(valueCommand);

                    }

                }
            }
        }
        return compoundCommand;
    }

}
