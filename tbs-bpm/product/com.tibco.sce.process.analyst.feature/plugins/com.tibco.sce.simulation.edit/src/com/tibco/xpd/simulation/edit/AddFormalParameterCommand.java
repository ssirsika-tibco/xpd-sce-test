package com.tibco.xpd.simulation.edit;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.simulation.ParameterDistribution;
import com.tibco.xpd.simulation.SimulationFactory;
import com.tibco.xpd.simulation.SimulationPackage;
import com.tibco.xpd.simulation.WorkflowProcessSimulationDataType;
import com.tibco.xpd.simulation.common.util.SimulationXpdlUtils;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.commands.AddCommandWrapper;

public class AddFormalParameterCommand implements AddCommandWrapper {

    public Command createAddCommand(Command originalCommand,
            EditingDomain domain, EObject owner, EStructuralFeature feature,
            Collection collection, int index) {
        CompoundCommand compoundCmd = new CompoundCommand();
        compoundCmd.append(originalCommand);
        if (feature == Xpdl2Package.eINSTANCE
                .getFormalParametersContainer_FormalParameters()
                && !SimulationXpdlUtils.isPreprocessorMode()) {
            /*
             * WorkflowProcess process = (WorkflowProcess) owner; if
             * (!XPDExtUtil.hasDestinationEnvironment(process,
             * SimulationConstants.SIMULATION_DEST_ENV_1_0_ID)) {
             * originalCommand = compoundCmd.unwrap(); return originalCommand; }
             */
            compoundCmd.append(new AddFPSimDataCommand(domain, owner, feature,
                    collection, index));
            originalCommand = compoundCmd.unwrap();

            /*
             * WorkflowProcessSimulationDataType workflowProcessSimulationData =
             * SimulationXpdlUtils .getWorkflowProcessSimulationData(process);
             * if (workflowProcessSimulationData == null) { ExtendedAttribute
             * extAttribute = createWorkFlowExtendedAttr(); Command command =
             * AddCommand .create( domain, process, Xpdlr2Package.eINSTANCE
             * .getExtendedAttributeContainer_ExtendedAttributes(),
             * extAttribute); compoundCmd.append(command); EList simProcessList =
             * (EList) extAttribute .getMixed() .get(
             * SimulationPackage.eINSTANCE
             * .getDocumentRoot_WorkflowProcessSimulationData(), true);
             * 
             * workflowProcessSimulationData =
             * (WorkflowProcessSimulationDataType) simProcessList .get(0); } for
             * (Iterator iter = collection.iterator(); iter.hasNext();) {
             * FormalParameter element = (FormalParameter) iter.next(); String
             * fpId = element.getId(); ParameterDistribution
             * parameterDistribution = getParameterDistribution(
             * workflowProcessSimulationData, fpId); if (parameterDistribution !=
             * null) { continue; } parameterDistribution =
             * SimulationFactory.eINSTANCE .createParameterDistribution();
             * parameterDistribution.setParameterId(element.getId()); Command
             * command = AddCommand .create( domain,
             * workflowProcessSimulationData, SimulationPackage.eINSTANCE
             * .getWorkflowProcessSimulationDataType_ParameterDistribution(),
             * parameterDistribution); compoundCmd.append(command); } }
             */

        }
        return originalCommand;
    }

    private ParameterDistribution getParameterDistribution(
            WorkflowProcessSimulationDataType workflowProcessSimulationData,
            String fpId) {
        EList parameterDistribution = workflowProcessSimulationData
                .getParameterDistribution();
        ParameterDistribution dist = null;
        for (Iterator iter = parameterDistribution.iterator(); iter.hasNext();) {
            ParameterDistribution element = (ParameterDistribution) iter.next();
            if (element.getParameterId().equals(fpId)) {
                dist = element;
                break;
            }
        }
        return dist;
    }

    /**
     * Returns Extended Attribute for a transition which has Transition
     * simulation data and populates the simulation data with dfault value and
     * parameterId from the SplitSimulationData of the Activity.
     * 
     * @return
     */
    private static ExtendedAttribute createWorkFlowExtendedAttr() {
        ExtendedAttribute extAttribute = Xpdl2Factory.eINSTANCE
                .createExtendedAttribute();
        extAttribute.setName("WorkflowProcessSimulationData"); //$NON-NLS-1$
        WorkflowProcessSimulationDataType processSimData = SimulationFactory.eINSTANCE
                .createWorkflowProcessSimulationDataType();
        extAttribute.getMixed().add(
                SimulationPackage.eINSTANCE
                        .getDocumentRoot_WorkflowProcessSimulationData(),
                processSimData);
        return extAttribute;
    }

}
