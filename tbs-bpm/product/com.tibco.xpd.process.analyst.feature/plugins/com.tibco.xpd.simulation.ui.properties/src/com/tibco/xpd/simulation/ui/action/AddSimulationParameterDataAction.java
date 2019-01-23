/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.ui.action;

import java.util.Iterator;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.command.AddCommand;
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
import com.tibco.xpd.simulation.EnumerationValueType;
import com.tibco.xpd.simulation.ParameterDistribution;
import com.tibco.xpd.simulation.SimulationFactory;
import com.tibco.xpd.simulation.SimulationPackage;
import com.tibco.xpd.simulation.WorkflowProcessSimulationDataType;
import com.tibco.xpd.simulation.common.util.ParameterSource;
import com.tibco.xpd.simulation.common.util.SimulationXpdlUtils;
import com.tibco.xpd.simulation.ui.properties.PropertiesMessage;
import com.tibco.xpd.xpdl2.Process;

/**
 * Action for adding simulation data.
 */
public class AddSimulationParameterDataAction extends Action implements
        ISelectionChangedListener {
    /** The default weighting factor. */
    private static final double DEFAULT_WEIGHTING_FACTOR = 10.0D;

    /** The viewer providing the selection. */
    private final StructuredViewer viewer;

    /**
     * @param viewer The viewer providing the selection.
     */
    public AddSimulationParameterDataAction(StructuredViewer viewer) {
        this.viewer = viewer;
        setText(PropertiesMessage.getString("AddSimulationParameterDataAction.AddValue")); //$NON-NLS-1$
        viewer.addSelectionChangedListener(this);
        /*
         * setImageDescriptor(ProcessWidgetPlugin.getDefault()
         * .getImageRegistry().getDescriptor( ProcessWidgetConstants.IMG_ADD));
         */
    }

    /**
     * @see org.eclipse.jface.action.Action#run()
     */
    public void run() {
        IStructuredSelection structSelection =
                (IStructuredSelection) viewer.getSelection();
        EditingDomain ed = null;
        Process process = null;
        for (Iterator i = structSelection.iterator(); i.hasNext();) {
            Object next = i.next();
            if (next instanceof ParameterSource) {
                ParameterSource source = (ParameterSource) next;
                if (ed == null) {
                    process = source.getProcess();
                    ed = WorkingCopyUtil.getEditingDomain(process);
                }
                String parameterId = source.getParameterId();
                WorkflowProcessSimulationDataType simulationData =
                        SimulationXpdlUtils
                                .getWorkflowProcessSimulationData(process);
                EList parameterDistribution =
                        simulationData.getParameterDistribution();
                ParameterDistribution paraDist = null;
                for (Iterator iter = parameterDistribution.iterator(); iter
                        .hasNext();) {
                    ParameterDistribution element =
                            (ParameterDistribution) iter.next();
                    if (element.getParameterId().equals(parameterId)) {
                        paraDist = element;
                        break;
                    }
                }
                String newEnumValue = getUniqueValue(paraDist);
                EnumerationValueType enumType =
                        SimulationFactory.eINSTANCE
                                .createEnumerationValueType();
                enumType.setWeightingFactor(DEFAULT_WEIGHTING_FACTOR);
                enumType.setValue(newEnumValue);
                Command command =
                        AddCommand.create(ed, paraDist,
                                SimulationPackage.eINSTANCE
                                        .getEnumerationValueType(), enumType);
                if (command.canExecute()) {
                    if (ed != null) {
                        ed.getCommandStack().execute(command);
                    }
                }
            }
        }

    }

    /**
     * @param paraDist The parameter distribution.
     * @return A unique value key.
     */
    public String getUniqueValue(ParameterDistribution paraDist) {
        String toReturn = PropertiesMessage.getString("AddSimulationParameterDataAction.NewValue"); //$NON-NLS-1$
        int index = 0;
        while (true) {
            boolean valuePresent = false;
            EList enumerationValue = paraDist.getEnumerationValue();
            for (Iterator iter = enumerationValue.iterator(); iter.hasNext();) {
                EnumerationValueType element =
                        (EnumerationValueType) iter.next();
                if (element.getValue().equals(toReturn + index)) {
                    valuePresent = true;
                    break;
                }
            }
            if (!valuePresent) {
                break;
            } else {
                ++index;
            }
        }
        return toReturn + index;

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
                if (structSelection.size() == 1
                        && structSelection.getFirstElement() instanceof ParameterSource) {
                    setEnabled(true);
                } else {
                    setEnabled(false);
                }
            } else {
                setEnabled(false);
            }
        }
        firePropertyChange(IAction.HANDLED, null, null);
    }
}
