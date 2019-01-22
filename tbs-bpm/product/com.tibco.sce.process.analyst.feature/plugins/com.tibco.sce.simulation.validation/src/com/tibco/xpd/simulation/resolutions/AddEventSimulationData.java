/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.simulation.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.simulation.ConstantRealDistribution;
import com.tibco.xpd.simulation.SimulationConstants;
import com.tibco.xpd.simulation.SimulationFactory;
import com.tibco.xpd.simulation.SimulationPackage;
import com.tibco.xpd.simulation.StartSimulationDataType;
import com.tibco.xpd.simulation.TimeDisplayUnitType;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * @author bharge
 * 
 */
public class AddEventSimulationData extends AbstractWorkingCopyResolution {

    /**
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     * 
     * @param editingDomain
     * @param target
     * @param marker
     * @return
     * @throws ResolutionException
     */
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        Command command = null;
        if (target instanceof Activity){
            Activity activity = (Activity)target;
            if (activity.getEvent() instanceof StartEvent){
                StartEvent startEvent = (StartEvent)activity.getEvent();
                ExtendedAttribute ea =
                        Xpdl2Factory.eINSTANCE.createExtendedAttribute();
                ea.setName(SimulationConstants.SIM_START_EVENT_DATA);
                
                //creating and attaching default distribution
                StartSimulationDataType startSimData =
                        SimulationFactory.eINSTANCE
                                .createStartSimulationDataType();
                startSimData.setNumberOfCases(100);
                
                ConstantRealDistribution constantRealDistribution =
                        SimulationFactory.eINSTANCE
                                .createConstantRealDistribution();
                constantRealDistribution.setConstantValue(5.0);
                startSimData.setDuration(constantRealDistribution);
                
                startSimData.setDisplayTimeUnit(TimeDisplayUnitType.MINUTE_LITERAL);

                ea.getMixed().add(SimulationPackage.eINSTANCE
                        .getDocumentRoot_StartSimulationData(),startSimData);
                
                //adding extended attribute
                command = AddCommand.create(editingDomain, activity, Xpdl2Package.eINSTANCE
                        .getExtendedAttributesContainer_ExtendedAttributes(), ea);
            }
        }
        return command;
    }
}
