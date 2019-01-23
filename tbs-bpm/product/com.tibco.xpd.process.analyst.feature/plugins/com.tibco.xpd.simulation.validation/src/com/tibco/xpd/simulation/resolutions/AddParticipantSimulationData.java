/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.simulation.resolutions;

import java.math.BigInteger;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.simulation.ParticipantSimulationDataType;
import com.tibco.xpd.simulation.SimulationConstants;
import com.tibco.xpd.simulation.SimulationFactory;
import com.tibco.xpd.simulation.SimulationPackage;
import com.tibco.xpd.simulation.TimeDisplayUnitType;
import com.tibco.xpd.simulation.TimeUnitCostType;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * @author nwilson
 */
public class AddParticipantSimulationData extends AbstractWorkingCopyResolution {

    /**
     * @param ed
     *            The editing domain.
     * @param target
     *            The target.
     * @param marker
     *            The problem marker.
     * @return The command to make the change.
     * @throws ResolutionException
     *             If there was a problem creating the command.
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution
     *      #getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     */
    @Override
    protected Command getResolutionCommand(EditingDomain ed, EObject target,
            IMarker marker) throws ResolutionException {
        Command command = null;
        if (target instanceof Participant) {
            Participant participant = (Participant) target;
            ExtendedAttribute ea = Xpdl2Factory.eINSTANCE
                    .createExtendedAttribute();
            ea.setName(SimulationConstants.SIM_PARTICIPANT_DATA);

            // creating and attaching default distribution.
            ParticipantSimulationDataType participantSimData = SimulationFactory.eINSTANCE
                    .createParticipantSimulationDataType();

            participantSimData.setInstances(BigInteger.valueOf(Long
                    .parseLong("1"))); //$NON-NLS-1$
            TimeUnitCostType timeUnitCost = SimulationFactory.eINSTANCE
                    .createTimeUnitCostType();
            timeUnitCost.setCost(Double.parseDouble("5.0")); //$NON-NLS-1$
            timeUnitCost.setTimeDisplayUnit(TimeDisplayUnitType.HOUR_LITERAL);
            participantSimData.setTimeUnitCost(timeUnitCost);

            ea.getMixed().add(
                    SimulationPackage.eINSTANCE
                            .getDocumentRoot_ParticipantSimulationData(),
                    participantSimData);

            // adding extended attribute.
            command = AddCommand.create(ed, participant, Xpdl2Package.eINSTANCE
                    .getExtendedAttributesContainer_ExtendedAttributes(), ea);
        }
        return command;
    }

}
