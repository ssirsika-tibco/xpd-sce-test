/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.ui.provider;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeItem;

import com.tibco.xpd.simulation.EnumerationValueType;
import com.tibco.xpd.simulation.SimulationPackage;
import com.tibco.xpd.simulation.common.util.ParameterDestination;
import com.tibco.xpd.simulation.common.util.SimulationCommandUtils;
import com.tibco.xpd.simulation.ui.properties.PropertiesMessage;
import com.tibco.xpd.ui.properties.AbstractXpdSection;
import com.tibco.xpd.ui.resources.StatusInfo;

/**
 * Simulation parameters cell modifier.
 */
public class SimulationParametersDataCellModifier implements ICellModifier {

    /** Flow label. */
    private static final String FLOW = PropertiesMessage.getString("FLOW"); //$NON-NLS-1$

    /** Weighting label. */
    private static final String WEIGHTING =
            PropertiesMessage.getString("WEIGHTING"); //$NON-NLS-1$

    /** Error title. */
    private static final String ERROR_TITLE =
            PropertiesMessage.getString("ERROR_TITLE"); //$NON-NLS-1$

    /** Weighting error message. */
    private static final String WEIGHTING_ERROR =
            PropertiesMessage.getString("WEIGHTING_ERROR"); //$NON-NLS-1$

    /** Flow error message. */
    private static final String FLOW_ERROR =
            PropertiesMessage.getString("FLOW_ERROR"); //$NON-NLS-1$

    /** The current section. */
    private final AbstractXpdSection section;

    /**
     * The constructor.
     * 
     * @param section The current section.
     */
    public SimulationParametersDataCellModifier(AbstractXpdSection section) {
        this.section = section;
    }

    /**
     * @param element The element to check.
     * @param property The property to check.
     * @return true if the property can be modified.
     * @see org.eclipse.jface.viewers.ICellModifier#canModify(java.lang.Object,
     *      java.lang.String)
     */
    public boolean canModify(Object element, String property) {
        return element instanceof ParameterDestination
                && (WEIGHTING.equals(property) || FLOW.equals(property));
    }

    /**
     * @param element The element to get the value for.
     * @param property The property to get.
     * @return The value of the property.
     * @see org.eclipse.jface.viewers.ICellModifier#getValue(java.lang.Object,
     *      java.lang.String)
     */
    public Object getValue(Object element, String property) {
        if (element instanceof ParameterDestination) {
            ParameterDestination destinationNode =
                    (ParameterDestination) element;
            if (WEIGHTING.equals(property)) {
                return "" + destinationNode.getWeightingFactor(); //$NON-NLS-1$
            } else if (FLOW.equals(property)) {
                return destinationNode.getValue();
            }
        }
        return null;
    }

    /**
     * @param element The element to modify.
     * @param property The property to modify.
     * @param value The new value.
     * @see org.eclipse.jface.viewers.ICellModifier#modify(java.lang.Object,
     *      java.lang.String, java.lang.Object)
     */
    public void modify(Object element, String property, Object value) {
        if (value != null) {
            ParameterDestination destinationNode = null;
            if (element instanceof ParameterDestination) {
                destinationNode = (ParameterDestination) element;
            } else if (element instanceof TreeItem) {
                Object data = ((TreeItem) element).getData();
                if (data instanceof ParameterDestination) {
                    destinationNode = (ParameterDestination) data;
                }
            }
            if (destinationNode != null) {
                if (WEIGHTING.equals(property)) {
                    try {
                        Double newValue = new Double(value.toString());
                        if (newValue.doubleValue() != destinationNode
                                .getWeightingFactor()) {
                            EnumerationValueType enumeration =
                                    destinationNode.getEnumeration();
                            EditingDomain ed = this.section.getEditingDomain();
                            Object feature =
                                    SimulationPackage.eINSTANCE
                                            .getEnumerationValueType_WeightingFactor();
                            Command command =
                                    SetCommand.create(ed, enumeration, feature,
                                            newValue);
                            if (command.canExecute()) {
                                ed.getCommandStack().execute(command);
                            }
                        }
                    } catch (Exception e) {
                        Shell shell =
                                this.section.getPart().getSite().getShell();
                        ErrorDialog.openError(shell, ERROR_TITLE, null,
                                new StatusInfo(IStatus.ERROR, WEIGHTING_ERROR));
                    }
                } else if (FLOW.equals(property)) {
                    try {
                        String newValue = (String) value;
                        if (!newValue.equals(destinationNode.getValue())) {
                            EnumerationValueType enumeration =
                                    destinationNode.getEnumeration();
                            EditingDomain ed = this.section.getEditingDomain();
                            EStructuralFeature feature =
                                    SimulationPackage.eINSTANCE
                                            .getEnumerationValueType_Value();
                            Command command =
                                    SetCommand.create(ed, enumeration, feature,
                                            newValue);
                            command =
                                    SimulationCommandUtils
                                            .changeFPEnumerationValue(command,
                                                    ed, enumeration, feature,
                                                    newValue);
                            if (command.canExecute()) {
                                ed.getCommandStack().execute(command);
                            }
                        }
                    } catch (Exception e) {
                        Shell shell =
                                this.section.getPart().getSite().getShell();
                        ErrorDialog.openError(shell, ERROR_TITLE, null,
                                new StatusInfo(IStatus.ERROR, FLOW_ERROR));
                    }
                }
            }
        }
    }
}
