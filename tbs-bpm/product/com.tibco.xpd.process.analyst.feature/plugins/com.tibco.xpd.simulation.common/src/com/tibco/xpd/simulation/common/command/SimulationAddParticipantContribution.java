/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.common.command;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.simulation.ParticipantSimulationDataType;
import com.tibco.xpd.simulation.SimulationConstants;
import com.tibco.xpd.simulation.SimulationFactory;
import com.tibco.xpd.simulation.SimulationPackage;
import com.tibco.xpd.simulation.TimeDisplayUnitType;
import com.tibco.xpd.simulation.TimeUnitCostType;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.ExtendedAttributesContainer;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantsContainer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.commands.AddCommandWrapper;

/**
 * @author wzurek
 */
public class SimulationAddParticipantContribution implements AddCommandWrapper {

    /** Default number of participants. */
    private static final BigInteger DEFAULT_PARTICIPANT_INSTANCES =
            BigInteger.valueOf(Long.parseLong("1")); //$NON-NLS-1$

    /** Default per hour participant cost. */
    private static final double DEFAULT_PARTICIPANT_HOUR_COST = 50.0;

    /** Default participant time unit. */
    private static final TimeDisplayUnitType DEFAULT_PARTICIPANT_TIME_UNIT =
            TimeDisplayUnitType.HOUR_LITERAL;

    /** Minutes per hour. */
    private static final double HOUR = 60.0;

    /**
     * @param originalCommand The original command.
     * @param domain The editing domain.
     * @param owner The container object.
     * @param feature The feature to change.
     * @param collection The collection to modify.
     * @param index The index to insert at.
     * @return The command.
     * @see com.tibco.xpd.xpdl2.commands.AddCommandWrapper#
     *      createAddCommand(org.eclipse.emf.common.command.Command,
     *      org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject,
     *      org.eclipse.emf.ecore.EStructuralFeature, java.util.Collection, int)
     */
    public Command createAddCommand(Command originalCommand,
            EditingDomain domain, EObject owner, EStructuralFeature feature,
            Collection collection, int index) {

        if (owner instanceof ParticipantsContainer
                && owner instanceof ExtendedAttributesContainer) {
            if (feature == Xpdl2Package.eINSTANCE
                    .getParticipantsContainer_Participants()) {
                boolean destinationEnabled = false;
                String detinationId =
                        SimulationConstants.SIMULATION_DEST_ENV_1_2_ID;
                if (owner instanceof Package) {
                    destinationEnabled =
                            DestinationUtil.isValidationDestinationEnabled(
                                    (Package) owner, detinationId);
                } else if (owner instanceof Process) {
                    destinationEnabled =
                            DestinationUtil.isValidationDestinationEnabled(
                                    (Process) owner, detinationId);
                }
                if (destinationEnabled) {
                    CompoundCommand ccmd = new CompoundCommand();
                    ccmd.append(originalCommand);
                    for (Iterator iter = collection.iterator(); iter.hasNext();) {
                        Object obj = iter.next();
                        if (obj instanceof Participant) {
                            assignSimDataToParticipant(domain,
                                    (Participant) obj, ccmd);
                        }
                    }
                    originalCommand = ccmd.unwrap();
                }
            }
        }

        return originalCommand;
    }

    /**
     * @param domain The editing domain.
     * @param participant The participant.
     * @param ccmd The command to add to.
     */
    public static void assignSimDataToParticipant(EditingDomain domain,
            Participant participant, CompoundCommand ccmd) {

        Command cmd = null;

        // remowing existing ParticipantSimulationData extended attributes if
        // any exists.
        List<ExtendedAttribute> existingEASimData =
                new ArrayList<ExtendedAttribute>();
        for (Iterator iter = participant.getExtendedAttributes().iterator(); iter
                .hasNext();) {
            ExtendedAttribute ea = (ExtendedAttribute) iter.next();
            if ("ParticipantSimulationData".equals(ea.getName())) { //$NON-NLS-1$
                existingEASimData.add(ea);
            }
        }
        EReference extAttsFeature =
                Xpdl2Package.eINSTANCE
                        .getExtendedAttributesContainer_ExtendedAttributes();
        if (existingEASimData.size() > 0) {
            cmd =
                    RemoveCommand.create(domain, participant, extAttsFeature,
                            existingEASimData);
            ccmd.append(cmd);
        }

        // creating ParticipantSimulationData extended attributes.
        ExtendedAttribute ea = Xpdl2Factory.eINSTANCE.createExtendedAttribute();
        ea.setName("ParticipantSimulationData"); //$NON-NLS-1$

        // creating and attaching default distribution.
        ParticipantSimulationDataType participantSimData =
                SimulationFactory.eINSTANCE
                        .createParticipantSimulationDataType();

        participantSimData.setInstances(DEFAULT_PARTICIPANT_INSTANCES);
        TimeUnitCostType timeUnitCost =
                SimulationFactory.eINSTANCE.createTimeUnitCostType();
        timeUnitCost.setCost(DEFAULT_PARTICIPANT_HOUR_COST / HOUR);
        timeUnitCost.setTimeDisplayUnit(DEFAULT_PARTICIPANT_TIME_UNIT);
        participantSimData.setTimeUnitCost(timeUnitCost);

        ea.getMixed().add(
                SimulationPackage.eINSTANCE
                        .getDocumentRoot_ParticipantSimulationData(),
                participantSimData);

        // adding extended attribute.
        cmd = AddCommand.create(domain, participant, extAttsFeature, ea);
        ccmd.append(cmd);
    }
}
