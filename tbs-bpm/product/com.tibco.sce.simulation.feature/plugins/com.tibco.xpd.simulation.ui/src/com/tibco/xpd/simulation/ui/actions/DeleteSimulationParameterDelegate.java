package com.tibco.xpd.simulation.ui.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.simulation.EnumerationValueType;
import com.tibco.xpd.simulation.ParameterDistributionType;
import com.tibco.xpd.simulation.SimulationPackage;
import com.tibco.xpd.simulation.StructuredConditionType;
import com.tibco.xpd.simulation.TransitionSimulationDataType;
import com.tibco.xpd.simulation.WorkflowProcessSimulationDataType;
import com.tibco.xpd.simulation.common.util.ParameterDestination;
import com.tibco.xpd.simulation.common.util.ParameterSource;
import com.tibco.xpd.simulation.common.util.SimulationXpdlUtils;
import com.tibco.xpd.simulation.ui.Messages;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.Xpdl2Package;

public class DeleteSimulationParameterDelegate implements IViewActionDelegate,
        IEditorActionDelegate {
    IStructuredSelection selection;

    public void init(IViewPart view) {
    }

    public void run(IAction action) {
        CompoundCommand command =
                new CompoundCommand(
                        Messages.DeleteSimulationParameterDelegate_DeleteParameters);
        EditingDomain ed = null;
        Process process = null;
        ArrayList parameters = new ArrayList();
        ArrayList enumerations = new ArrayList();
        for (Iterator i = selection.iterator(); i.hasNext();) {
            Object next = i.next();
            if (next instanceof ParameterSource) {
                ParameterSource source = (ParameterSource) next;
                if (ed == null) {
                    process = source.getProcess();
                    ed = WorkingCopyUtil.getEditingDomain(process);
                }
                WorkflowProcessSimulationDataType simulationData =
                        SimulationXpdlUtils
                                .getWorkflowProcessSimulationData(process);
                EList paramDistList = simulationData.getParameterDistribution();
                for (Iterator j = paramDistList.iterator(); j.hasNext();) {
                    ParameterDistributionType paramDist =
                            (ParameterDistributionType) j.next();
                    if (paramDist.getParameterId().equals(source
                            .getParameterId())) {
                        parameters.add(paramDist);
                    }
                }
            } else if (next instanceof ParameterDestination) {
                ParameterDestination destination = (ParameterDestination) next;
                if (ed == null) {
                    process = destination.getSource().getProcess();
                    ed = WorkingCopyUtil.getEditingDomain(process);
                }
                EnumerationValueType enumeration = destination.getEnumeration();
                enumerations.add(enumeration);
            }
        }
        for (Iterator i = enumerations.iterator(); i.hasNext();) {
            EnumerationValueType enumeration = (EnumerationValueType) i.next();
            ParameterDistributionType paramDist =
                    (ParameterDistributionType) enumeration.eContainer();
            if (!parameters.contains(paramDist)) {
                deleteEnumerationType(process,
                        command,
                        ed,
                        enumeration,
                        paramDist);
            }
        }
        for (Iterator i = parameters.iterator(); i.hasNext();) {
            ParameterDistributionType paramDist =
                    (ParameterDistributionType) i.next();
            EList enumList = paramDist.getEnumerationValue();
            for (Iterator j = enumList.iterator(); j.hasNext();) {
                EnumerationValueType enumeration =
                        (EnumerationValueType) j.next();
                deleteEnumerationType(process,
                        command,
                        ed,
                        enumeration,
                        paramDist);
            }
            WorkflowProcessSimulationDataType simulationData =
                    (WorkflowProcessSimulationDataType) paramDist.eContainer();
            command
                    .append(RemoveCommand
                            .create(ed,
                                    simulationData,
                                    SimulationPackage.eINSTANCE
                                            .getWorkflowProcessSimulationDataType_ParameterDistribution(),
                                    paramDist));
            String fpToDelete = paramDist.getParameterId();
            List formalParameters =
                    ProcessInterfaceUtil.getAllFormalParameters(process);
            for (Iterator j = formalParameters.iterator(); j.hasNext();) {
                FormalParameter fp = (FormalParameter) j.next();
                if (fp.getName().equals(fpToDelete)) {
                    command.append(RemoveCommand.create(ed,
                            process,
                            Xpdl2Package.eINSTANCE.getFormalParameter(),
                            fp));
                }
            }
        }
        if (command.canExecute()) {
            if (ed != null) {
                ed.getCommandStack().execute(command);
            }
        }
    }

    private void deleteEnumerationType(Process process,
            CompoundCommand command, EditingDomain ed,
            EnumerationValueType enumeration,
            ParameterDistributionType paramDist) {
        String valueToRemove = enumeration.getValue();
        command.append(RemoveCommand.create(ed,
                paramDist,
                SimulationPackage.eINSTANCE.getEnumerationValueType(),
                enumeration));
        EList transitions = process.getTransitions();
        for (Iterator i = transitions.iterator(); i.hasNext();) {
            Transition transition = (Transition) i.next();
            for (Iterator iter = transition.getExtendedAttributes().iterator(); iter
                    .hasNext();) {
                ExtendedAttribute ea = (ExtendedAttribute) iter.next();
                if ("TransitionSimulationData".equals(ea.getName())) { //$NON-NLS-1$
                    // found extended attribute
                    EList eaContent =
                            (EList) ea
                                    .getMixed()
                                    .get(SimulationPackage.eINSTANCE
                                            .getDocumentRoot_TransitionSimulationData(),
                                            true);
                    // there can only be one of these
                    if (eaContent.size() == 1 && eaContent.get(0) != null) {
                        TransitionSimulationDataType data =
                                SimulationXpdlUtils
                                        .getTransitionSimulationData(transition);
                        if (data != null) {
                            StructuredConditionType condition =
                                    data.getStructuredCondition();
                            if (condition != null) {
                                String value = condition.getParameterValue();
                                if (value != null
                                        && value.equals(valueToRemove)) {
                                    command.append(RemoveCommand.create(ed,
                                            transition,
                                            Xpdl2Package.eINSTANCE
                                                    .getExtendedAttribute(),
                                            ea));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void selectionChanged(IAction action, ISelection selection) {
        if (selection instanceof IStructuredSelection) {
            this.selection = (IStructuredSelection) selection;
        }
    }

    public void setActiveEditor(IAction action, IEditorPart targetEditor) {
    }
}
