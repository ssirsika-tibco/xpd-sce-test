package com.tibco.xpd.simulation.edit;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.simulation.ParameterDistribution;
import com.tibco.xpd.simulation.SimulationFactory;
import com.tibco.xpd.simulation.SimulationPackage;
import com.tibco.xpd.simulation.WorkflowProcessSimulationDataType;
import com.tibco.xpd.simulation.common.util.SimulationXpdlUtils;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * This class is added as we are facing a problem of adding
 * workflowsimulationData when the fp is added by prepare simulation.
 * 
 * @author KamleshU
 * 
 */
public class AddFPSimDataCommand extends AbstractCommand {

    private final EditingDomain ed;

    private final Process process;

    private CompoundCommand cmd;

    private Collection collection;

    public AddFPSimDataCommand(EditingDomain domain, EObject owner,
            EStructuralFeature feature, Collection collection, int index) {
        super();
        this.ed = domain;
        this.process = (Process) owner;
        this.collection = collection;
        setLabel(Messages.AddFPSimDataCommand_Add);
    }

    public void execute() {
        cmd = new CompoundCommand();
        Command command = generateCommand();
        if (command != null) {
            cmd.appendAndExecute(command);
        }

    }

    private Command generateCommand() {
        CompoundCommand toReturn = new CompoundCommand();
        WorkflowProcessSimulationDataType workflowProcessSimulationData = SimulationXpdlUtils
                .getWorkflowProcessSimulationData(process);
        if (workflowProcessSimulationData == null) {
            ExtendedAttribute extAttribute = createWorkFlowExtendedAttr();
            Command command = AddCommand
                    .create(
                            ed,
                            process,
                            Xpdl2Package.eINSTANCE
                                    .getExtendedAttributesContainer_ExtendedAttributes(),
                            extAttribute);
            toReturn.append(command);
            EList simProcessList = (EList) extAttribute.getMixed().get(
                    SimulationPackage.eINSTANCE
                            .getDocumentRoot_WorkflowProcessSimulationData(),
                    true);

            workflowProcessSimulationData = (WorkflowProcessSimulationDataType) simProcessList
                    .get(0);
        }
        for (Iterator iter = collection.iterator(); iter.hasNext();) {
            FormalParameter element = (FormalParameter) iter.next();
            String fpId = element.getName();
            ParameterDistribution parameterDistribution = getParameterDistribution(
                    workflowProcessSimulationData, fpId);
            if (parameterDistribution != null) {
                // if not then it will create a duplicate parameter distribution
                // for the same parameter during prepare simulation.
                continue;
            }
            parameterDistribution = SimulationFactory.eINSTANCE
                    .createParameterDistribution();
            parameterDistribution.setParameterId(fpId);
            Command cmd = AddCommand
                    .create(
                            ed,
                            workflowProcessSimulationData,
                            SimulationPackage.eINSTANCE
                                    .getWorkflowProcessSimulationDataType_ParameterDistribution(),
                            parameterDistribution);
            toReturn.append(cmd);
        }

        return toReturn;
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

    public void redo() {
        cmd.redo();
    }

    public void undo() {
        cmd.undo();
    }

    protected boolean prepare() {
        return true;
    }
    
    @Override
    public Collection<?> getAffectedObjects() {
        return cmd.getAffectedObjects();
    }

}
