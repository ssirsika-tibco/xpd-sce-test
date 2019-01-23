package com.tibco.xpd.simulation.edit;

import java.util.Iterator;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.simulation.SimulationFactory;
import com.tibco.xpd.simulation.SimulationPackage;
import com.tibco.xpd.simulation.SplitParameterType;
import com.tibco.xpd.simulation.SplitSimulationDataType;
import com.tibco.xpd.simulation.StructuredConditionType;
import com.tibco.xpd.simulation.TransitionSimulationDataType;
import com.tibco.xpd.simulation.common.util.SimulationXpdlUtils;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.commands.SetCommandWrapper;
import com.tibco.xpd.xpdl2.util.XpdlSearchUtil;

public class AddSimTranDataCommand implements SetCommandWrapper {

    public static final String TRAN_SIM_DATA = "TransitionSimulationData"; //$NON-NLS-1$

    public Command createSetCommand(Command originalCommand,
            EditingDomain domain, EObject owner, EStructuralFeature feature,
            Object newValue, int index) {
        if (feature == Xpdl2Package.eINSTANCE.getTransition_Condition()
                && !SimulationXpdlUtils.isPreprocessorMode()) {
            if (owner instanceof Transition) {
                CompoundCommand compoundCmd = new CompoundCommand();
                compoundCmd.append(originalCommand);
                assignSimDataToTransition(domain, (Transition) owner,
                        compoundCmd);
                originalCommand = compoundCmd.unwrap();
            }

        }
        return originalCommand;

    }

    private void assignSimDataToTransition(EditingDomain domain,
            Transition transition, CompoundCommand compoundCmd) {
        ExtendedAttribute ea = XpdlSearchUtil.findExtendedAttribute(transition,
                TRAN_SIM_DATA);
        if (ea != null) {
            removeTransitionSimulationData(domain, ea, compoundCmd);
        }
        createTransitionSimulationData(domain, transition, compoundCmd);
    }

    private void removeTransitionSimulationData(EditingDomain domain,
            ExtendedAttribute ea, CompoundCommand compoundCmd) {
        Command command = RemoveCommand.create(domain, ea);
        compoundCmd.append(command);
    }

    private void createTransitionSimulationData(EditingDomain domain,
            Transition transition, CompoundCommand compoundCmd) {
        // creating extended attributes.
        ExtendedAttribute ea = Xpdl2Factory.eINSTANCE.createExtendedAttribute();
        ea.setName(TRAN_SIM_DATA);

        // creating and attaching object to extended attr.
        TransitionSimulationDataType transSimData = SimulationFactory.eINSTANCE
                .createTransitionSimulationDataType();
        transSimData.setParameterDeterminedCondition(true);
        StructuredConditionType structuredCondition = createStructuredCondition(transition);
        transSimData.setStructuredCondition(structuredCondition);
        ea.getMixed().add(
                SimulationPackage.eINSTANCE
                        .getDocumentRoot_TransitionSimulationData(),
                transSimData);

        // adding extended attribute.
        Command cmd = AddCommand.create(domain, transition,
                Xpdl2Package.eINSTANCE
                        .getExtendedAttributesContainer_ExtendedAttributes(),
                ea);
        compoundCmd.append(cmd);
    }

    private StructuredConditionType createStructuredCondition(Transition trans) {
        StructuredConditionType structType = SimulationFactory.eINSTANCE
                .createStructuredConditionType();
        structType.setParameterId(""); //$NON-NLS-1$
        structType.setOperator("="); //$NON-NLS-1$
        structType.setParameterValue(""); //$NON-NLS-1$
        String fromActvityId = trans.getFrom();
        com.tibco.xpd.xpdl2.Process workflowProcess = SimulationXpdlUtils
                .getWorkflowProcess(trans);
        EList activities = workflowProcess.getActivities();
        Activity activity = null;
        for (Iterator iter = activities.iterator(); iter.hasNext();) {
            Activity tempActivity = (Activity) iter.next();
            if (fromActvityId.equals(tempActivity.getId())) {
                activity = tempActivity;
                break;
            }
        }
        if (activity == null) {
            return structType;
        }
        SplitSimulationDataType splitSimulationData = SimulationXpdlUtils
                .getSplitSimulationData(activity);
        if (splitSimulationData == null) {
            return structType;
        }
        SplitParameterType splitParameter = splitSimulationData
                .getSplitParameter();
        if (splitParameter == null) {
            return structType;
        }
        structType.setParameterId(splitParameter.getParameterId());
        return structType;
    }

}
