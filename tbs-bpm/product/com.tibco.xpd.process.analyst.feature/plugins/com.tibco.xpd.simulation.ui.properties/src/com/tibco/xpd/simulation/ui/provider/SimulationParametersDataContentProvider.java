/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.ui.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.xpd.simulation.EnumerationValueType;
import com.tibco.xpd.simulation.ParameterDistribution;
import com.tibco.xpd.simulation.WorkflowProcessSimulationDataType;
import com.tibco.xpd.simulation.common.util.ParameterDestination;
import com.tibco.xpd.simulation.common.util.ParameterSource;
import com.tibco.xpd.simulation.common.util.SimulationXpdlUtils;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.XpdlSearchUtil;

/**
 * Simulation parameters data content provider.
 */
public class SimulationParametersDataContentProvider implements
        ITreeContentProvider {

    /** Parameter sources. */
    private ParameterSource[] sources;

    /**
     * @param parentElement
     *            The parent element.
     * @return The children of the parent.
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(
     *      java.lang.Object)
     */
    public Object[] getChildren(Object parentElement) {
        if (parentElement instanceof ParameterSource) {
            ParameterSource source = (ParameterSource) parentElement;
            return source.getDestinations();
        }
        return null;
    }

    /**
     * @param element
     *            The element.
     * @return The parent of the element.
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(
     *      java.lang.Object)
     */
    public Object getParent(Object element) {
        if (element instanceof ParameterDestination) {
            ParameterDestination destinationNode = (ParameterDestination) element;
            return destinationNode.getSource();
        }
        return null;
    }

    /**
     * @param element
     *            The element.
     * @return true if the element has children.
     * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(
     *      java.lang.Object)
     */
    public boolean hasChildren(Object element) {
        if (element instanceof ParameterSource) {
            ParameterSource source = (ParameterSource) element;
            ParameterDestination[] destinations = source.getDestinations();
            if (destinations != null && destinations.length > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param inputElement
     *            The input element.
     * @return An array of top level elements.
     * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(
     *      java.lang.Object)
     */
    public Object[] getElements(Object inputElement) {
        return sources;
    }

    /**
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     */
    public void dispose() {
        sources = null;
    }

    /**
     * @param viewer
     *            The viewer.
     * @param oldInput
     *            The old input.
     * @param newInput
     *            The new input.
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(
     *      org.eclipse.jface.viewers.Viewer, java.lang.Object,
     *      java.lang.Object)
     */
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        update(newInput);
    }

    /**
     * @param inputElement
     *            The input element.
     */
    public void update(Object inputElement) {
        HashMap<String, ParameterSource> sourceList = new HashMap<String, ParameterSource>();
        if (inputElement instanceof Process) {
            Process process = (Process) inputElement;
            WorkflowProcessSimulationDataType simulationData = SimulationXpdlUtils
                    .getWorkflowProcessSimulationData(process);
            if (simulationData != null) {
                HashMap transitionMap = SimulationXpdlUtils
                        .getTransitionMap(process);
                EList parameterList = simulationData.getParameterDistribution();
                for (Iterator i = parameterList.iterator(); i.hasNext();) {
                    Object object = i.next();
                    if (object instanceof ParameterDistribution) {
                        ParameterDistribution parameter = (ParameterDistribution) object;
                        String parameterId = parameter.getParameterId();
                        ParameterSource paramSource = (ParameterSource) sourceList
                                .get(parameterId);
                        if (paramSource == null) {
                            paramSource = new ParameterSource(process,
                                    parameterId, XpdlSearchUtil
                                            .findFormalParameter(process,
                                                    parameterId));
                            sourceList.put(parameterId, paramSource);
                        }
                        EList enumerationList = parameter.getEnumerationValue();
                        for (Iterator j = enumerationList.iterator(); j
                                .hasNext();) {
                            object = j.next();
                            if (object instanceof EnumerationValueType) {
                                EnumerationValueType enumeration = (EnumerationValueType) object;
                                ParameterSource source = (ParameterSource) sourceList
                                        .get(parameterId);
                                String value = enumeration.getValue();
                                ParameterDestination destination = new ParameterDestination(
                                        source, value, enumeration
                                                .getWeightingFactor(),
                                        enumeration);
                                ArrayList transitions = (ArrayList) transitionMap
                                        .get(value);
                                if (transitions != null) {
                                    for (Iterator k = transitions.iterator(); k
                                            .hasNext();) {
                                        Transition transition = (Transition) k
                                                .next();
                                        String name = SimulationXpdlUtils
                                                .getTransitionName(process,
                                                        transition);
                                        destination.addTransition(name);
                                    }
                                }
                                source.addDestination(destination);
                            }
                        }
                    }
                }
            }
        }
        sources = new ParameterSource[sourceList.size()];
        sourceList.values().toArray(sources);
    }
}
