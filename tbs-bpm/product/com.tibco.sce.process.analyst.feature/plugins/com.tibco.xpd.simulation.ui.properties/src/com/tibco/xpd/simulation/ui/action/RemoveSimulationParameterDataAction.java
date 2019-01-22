/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.ui.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;

import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.simulation.EnumBasedExpressionType;
import com.tibco.xpd.simulation.EnumerationValueType;
import com.tibco.xpd.simulation.ExpressionType;
import com.tibco.xpd.simulation.ParameterDependentDistribution;
import com.tibco.xpd.simulation.ParameterDistribution;
import com.tibco.xpd.simulation.SimulationPackage;
import com.tibco.xpd.simulation.SplitParameterType;
import com.tibco.xpd.simulation.SplitSimulationDataType;
import com.tibco.xpd.simulation.StructuredConditionType;
import com.tibco.xpd.simulation.TransitionSimulationDataType;
import com.tibco.xpd.simulation.WorkflowProcessSimulationDataType;
import com.tibco.xpd.simulation.common.util.ParameterDestination;
import com.tibco.xpd.simulation.common.util.ParameterSource;
import com.tibco.xpd.simulation.common.util.SimulationXpdlUtils;
import com.tibco.xpd.simulation.ui.properties.PropertiesMessage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;

/**
 * Action for removing simulation data.
 */
public class RemoveSimulationParameterDataAction extends Action implements
        ISelectionChangedListener {
    /** The viewer providing the selection. */
    private final StructuredViewer viewer;

    /**
     * @param viewer The viewer providing the selection.
     */
    public RemoveSimulationParameterDataAction(StructuredViewer viewer) {
        this.viewer = viewer;
        setText(PropertiesMessage.getString("RemoveSimulationParameterDataAction.Remove")); //$NON-NLS-1$
        viewer.addSelectionChangedListener(this);
        /*
         * setImageDescriptor(ProcessWidgetPlugin.getDefault()
         * .getImageRegistry().getDescriptor(
         * ProcessWidgetConstants.IMG_DELETE));
         * viewer.addSelectionChangedListener(this);
         */
    }

    /**
     * @see org.eclipse.jface.action.Action#run()
     */
    public void run() {
        CompoundCommand command =
                new CompoundCommand(PropertiesMessage.getString("RemoveSimulationParameterDataAction.Delete")); //$NON-NLS-1$
        EditingDomain ed = null;
        Process process = null;
        ArrayList<ParameterDistribution> parameters =
                new ArrayList<ParameterDistribution>();
        ArrayList<EnumerationValueType> enumerations =
                new ArrayList<EnumerationValueType>();
        IStructuredSelection selection =
                (IStructuredSelection) viewer.getSelection();
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
                    ParameterDistribution paramDist =
                            (ParameterDistribution) j.next();
                    if (paramDist.getParameterId().equals(
                            source.getParameterId())) {
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
        for (EnumerationValueType enumeration : enumerations) {
            ParameterDistribution paramDist =
                    (ParameterDistribution) enumeration.eContainer();
            if (!parameters.contains(paramDist)) {
                deleteEnumerationType(process, command, ed, enumeration,
                        paramDist);
            }
        }
        for (ParameterDistribution paramDist : parameters) {
            EList enumList = paramDist.getEnumerationValue();
            for (Iterator j = enumList.iterator(); j.hasNext();) {
                EnumerationValueType enumeration =
                        (EnumerationValueType) j.next();
                deleteEnumerationType(process, command, ed, enumeration,
                        paramDist);
            }
            WorkflowProcessSimulationDataType simulationData =
                    (WorkflowProcessSimulationDataType) paramDist.eContainer();
            command.append(RemoveCommand.create(ed, simulationData,
                    SimulationPackage.eINSTANCE.getParameterDistribution(),
                    paramDist));
            Command deleteCommand =
                    deleteSplitParameterAssignment(process, ed, paramDist);
            if (deleteCommand != null) {
                command.append(deleteCommand);
            }
        }
        if (command.canExecute()) {
            if (ed != null) {
                ed.getCommandStack().execute(command);
            }
        }
    }

    /**
     * @param event Selection changed event.
     * @see org.eclipse.jface.viewers.ISelectionChangedListener#
     *      selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
     */
    public void selectionChanged(SelectionChangedEvent event) {
        ISelection selection = viewer.getSelection();
        if (selection instanceof StructuredSelection) {
            StructuredSelection structSelection =
                    (StructuredSelection) selection;
            if (!structSelection.isEmpty()) {
                boolean isEnabled = true;
                for (Iterator iter = structSelection.iterator(); iter.hasNext();) {
                    Object element = iter.next();
                    if (!(element instanceof ParameterDestination)) {
                        isEnabled = false;
                        break;
                    }
                }
                setEnabled(isEnabled);
            } else {
                setEnabled(false);
            }
        }
        firePropertyChange(IAction.HANDLED, null, null);
    }

    /**
     * This method deletes the simulation specific extended attribute of the
     * transition where the enumeration value is in use.
     * 
     * @param process The containing process.
     * @param command The command to add to.
     * @param ed The editing domain.
     * @param enumeration The enumeration type.
     * @param paramDist The parameter distribution.
     */
    private void deleteEnumerationType(Process process,
            CompoundCommand command, EditingDomain ed,
            EnumerationValueType enumeration, ParameterDistribution paramDist) {
        String valueToRemove = enumeration.getValue();
        command.append(RemoveCommand.create(ed, paramDist,
                SimulationPackage.eINSTANCE.getEnumerationValueType(),
                enumeration));
        EList transitions = process.getTransitions();
        for (Iterator i = transitions.iterator(); i.hasNext();) {
            Transition transition = (Transition) i.next();
            TransitionSimulationDataType transitionSimulationData =
                    SimulationXpdlUtils.getTransitionSimulationData(transition);
            if (transitionSimulationData == null) {
                continue;
            }
            StructuredConditionType condition =
                    transitionSimulationData.getStructuredCondition();
            if (condition == null) {
                continue;
            }
            String value = condition.getParameterValue();
            if (value != null && value.equals(valueToRemove)) {
                // command.append(RemoveCommand.create(ed, transition,
                // Xpdlr2Package.eINSTANCE.getExtendedAttribute(),
                // transitionSimulationData.eContainer()));
                command.append(SetCommand.create(ed, condition,
                        SimulationPackage.eINSTANCE
                                .getStructuredConditionType_ParameterValue(),
                        "")); //$NON-NLS-1$
            }
        }
        // working on activities, removing the parameter dependent distribution
        String enumerationValue = enumeration.getValue();
        String fpId = paramDist.getParameterId();
        String parameterId = paramDist.getParameterId();
        EList activities = process.getActivities();
        ArrayList<ParameterDependentDistribution> toDelete =
                new ArrayList<ParameterDependentDistribution>();
        for (Iterator i = activities.iterator(); i.hasNext();) {
            Activity activity = (Activity) i.next();
            List parameterDependentDistribution =
                    SimulationXpdlUtils.getParameterDependentDistribution(
                            activity, parameterId);
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
                String paramName = (String) enumBasedExpression.getParamName();
                if (paramName.equals(fpId)
                        && paramValue.equals(enumerationValue)) {
                    toDelete.add(element);
                }
            }
            if (toDelete.size() > 0) {
                command.append(RemoveCommand.create(ed, toDelete));
            }
        }
    }

    /**
     * This method removes the activity split simulation data from the activity
     * if the parameter which is used for split is being deleted from the
     * process extended attribute.
     * 
     * @param process The containing process.
     * @param ed The editing domain.
     * @param paramDist The parameter distribution.
     * @return The delete command.
     */
    private Command deleteSplitParameterAssignment(Process process,
            EditingDomain ed, ParameterDistribution paramDist) {
        CompoundCommand compCmd = new CompoundCommand();
        EList activities = process.getActivities();
        // ArrayList list = new ArrayList();
        for (Iterator iter = activities.iterator(); iter.hasNext();) {
            Activity activity = (Activity) iter.next();
            SplitSimulationDataType activitySplitSimulationData =
                    SimulationXpdlUtils
                            .getActivitySplitSimulationData(activity);
            if (activitySplitSimulationData == null) {
                continue;
            }
            SplitParameterType splitParam =
                    activitySplitSimulationData.getSplitParameter();
            String parameterId = splitParam.getParameterId();
            if (parameterId.equals(paramDist.getParameterId())) {
                compCmd.append(SetCommand.create(ed, splitParam,
                        SimulationPackage.eINSTANCE
                                .getSplitParameterType_ParameterId(), "")); //$NON-NLS-1$
                // list.add(activitySplitSimulationData.eContainer());
            }
        }
        // if (list.size() > 0) {
        // cmd = RemoveCommand.create(ed, list);
        // }
        return compCmd;
    }
}
