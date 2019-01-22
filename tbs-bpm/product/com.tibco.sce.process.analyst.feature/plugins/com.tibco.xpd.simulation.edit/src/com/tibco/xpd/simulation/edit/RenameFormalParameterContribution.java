package com.tibco.xpd.simulation.edit;

import java.util.Iterator;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.simulation.ActivitySimulationDataType;
import com.tibco.xpd.simulation.ParameterDistribution;
import com.tibco.xpd.simulation.SimulationPackage;
import com.tibco.xpd.simulation.SplitParameterType;
import com.tibco.xpd.simulation.SplitSimulationDataType;
import com.tibco.xpd.simulation.StructuredConditionType;
import com.tibco.xpd.simulation.TransitionSimulationDataType;
import com.tibco.xpd.simulation.WorkflowProcessSimulationDataType;
import com.tibco.xpd.simulation.common.util.SimulationXpdlUtils;
import com.tibco.xpd.simulation.utils.SimulationCommandUtils;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.FormalParametersContainer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.commands.SetCommandWrapper;

public class RenameFormalParameterContribution implements SetCommandWrapper {

	public Command createSetCommand(Command originalCommand,
			EditingDomain domain, EObject owner, EStructuralFeature feature,
			Object value, int index) {
		CompoundCommand compoundCmd = new CompoundCommand();
		compoundCmd.append(originalCommand);
		if (owner instanceof FormalParameter
				&& feature == Xpdl2Package.eINSTANCE.getNamedElement_Name()) {
			FormalParameter fp = (FormalParameter) owner;
			FormalParametersContainer parameterContainer = (FormalParametersContainer) fp
					.eContainer();
			String fpId = fp.getName();
			if (parameterContainer instanceof Process) {
				Process process = (Process) parameterContainer;
				Command cmd = renameParameterInProcessEA(domain, process, fpId,
						value);
				if (cmd != null) {
					compoundCmd.append(cmd);
				}
				cmd = renameParameterInActivityEA(domain, process, fpId, value);
				if (cmd != null) {
					compoundCmd.append(cmd);
				}
				cmd = renameParameterInTransitionEA(domain, process, fpId,
						value);
				if (cmd != null) {
					compoundCmd.append(cmd);
				}
				originalCommand = compoundCmd.unwrap();
			}
		}
		return originalCommand;
	}

	private Command renameParameterInProcessEA(EditingDomain ed,
			Process process, String fpId, Object newValue) {
		Command cmd = null;
		if (process == null) {
			// can happen in the case of wizard
			return cmd;
		}
		WorkflowProcessSimulationDataType workflowProcessSimulationData = SimulationXpdlUtils
				.getWorkflowProcessSimulationData(process);
		if (workflowProcessSimulationData == null) {
			return cmd;
		}
		EList parameterDistribution = workflowProcessSimulationData
				.getParameterDistribution();
		ParameterDistribution dist = null;
		for (Iterator iter = parameterDistribution.iterator(); iter.hasNext();) {
			ParameterDistribution paraDistribution = (ParameterDistribution) iter
					.next();
			if (paraDistribution.getParameterId().equalsIgnoreCase(fpId)) {
				dist = paraDistribution;
				break;
			}
		}
		if (dist != null) {
			cmd = SetCommand.create(ed, dist, SimulationPackage.eINSTANCE
					.getParameterDistribution_ParameterId(), newValue);
		}
		return cmd;
	}

	private Command renameParameterInActivityEA(EditingDomain ed,
			Process process, String fpId, Object newValue) {
		if (process == null) {
			// can happend when in wizard
			return null;
		}
		EList activities = process.getActivities();
		CompoundCommand cmd = null;
		for (Iterator iter = activities.iterator(); iter.hasNext();) {
			Activity activity = (Activity) iter.next();
			ActivitySimulationDataType activitySimulationData = SimulationXpdlUtils
					.getActivitySimulationData(activity);
			if (activitySimulationData != null) {
				Command command = SimulationCommandUtils
						.changeFPNameInActivityEA(activity, ed, fpId, newValue);
				if (command != null) {
					if (cmd == null) {
						cmd = new CompoundCommand();
					}
					cmd.append(command);
				}
			}
			SplitSimulationDataType activitySplitSimulationData = SimulationXpdlUtils
					.getActivitySplitSimulationData(activity);
			if (activitySplitSimulationData == null) {
				continue;
			}
			if (activitySplitSimulationData.isParameterDeterminedSplit()) {
				SplitParameterType splitParameter = activitySplitSimulationData
						.getSplitParameter();
				if (splitParameter.getParameterId().equalsIgnoreCase(fpId)) {
					Command idCommand = SetCommand.create(ed, splitParameter,
							SimulationPackage.eINSTANCE
									.getSplitParameterType_ParameterId(),
							newValue);
					if (cmd == null) {
						cmd = new CompoundCommand();
					}
					cmd.append(idCommand);
				}
			}
		}
		return cmd;
	}

	private Command renameParameterInTransitionEA(EditingDomain ed,
			Process process, String fpId, Object newValue) {
		if (process == null) {
			// can happend when in wizard
			return null;
		}
		EList transitions = process.getTransitions();
		CompoundCommand cmd = null;
		for (Iterator iter = transitions.iterator(); iter.hasNext();) {
			Transition eachTransition = (Transition) iter.next();
			TransitionSimulationDataType transitionSimulationData = SimulationXpdlUtils
					.getTransitionSimulationData(eachTransition);
			if (transitionSimulationData == null) {
				continue;
			}
			StructuredConditionType structuredCondition = transitionSimulationData
					.getStructuredCondition();
			if (structuredCondition.getParameterId().equals(fpId)) {
				Command idCommand = SetCommand.create(ed, structuredCondition,
						SimulationPackage.eINSTANCE
								.getStructuredConditionType_ParameterId(),
						newValue);
				if (cmd == null) {
					cmd = new CompoundCommand();
				}
				cmd.append(idCommand);
			}
		}
		return cmd;
	}
}
