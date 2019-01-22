/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.common.command;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.simulation.ActivitySimulationDataType;
import com.tibco.xpd.simulation.ConstantRealDistribution;
import com.tibco.xpd.simulation.SimulationConstants;
import com.tibco.xpd.simulation.SimulationFactory;
import com.tibco.xpd.simulation.SimulationPackage;
import com.tibco.xpd.simulation.SimulationRealDistributionType;
import com.tibco.xpd.simulation.SplitParameterType;
import com.tibco.xpd.simulation.SplitSimulationDataType;
import com.tibco.xpd.simulation.StartSimulationDataType;
import com.tibco.xpd.simulation.TimeDisplayUnitType;
import com.tibco.xpd.simulation.common.util.SimulationXpdlUtils;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.ExtendedAttributesContainer;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.commands.AddCommandWrapper;
import com.tibco.xpd.xpdl2.commands.LateExecuteCompoundCommand;
import com.tibco.xpd.xpdl2.util.XpdlSearchUtil;

/**
 * @author wzurek
 */
public class SimulationAddActivityContribution implements AddCommandWrapper {

    /** Activity simulation data identifier. */
    private static final String SIM_ACTIVITY_DATA = "ActivitySimulationData"; //$NON-NLS-1$

    /** Split simulation data identifier. */
    private static final String SPLIT_SIM_ACTIVITY_DATA = "SplitSimulationData"; //$NON-NLS-1$

    /** Default time unit. */
    private static final TimeDisplayUnitType DEFAULT_TIME_UNIT =
            TimeDisplayUnitType.MINUTE_LITERAL;

    /** Start event simulation data identifier. */
    public static final String SIM_START_EVENT_DATA = "StartSimulationData"; //$NON-NLS-1$

    /** Constant distribution default value. */
    public static final double CONSTANT_DIST_VALUE_DEFAULT = 5.0D;

    /** Default number of cases. */
    public static final int NUMBER_OF_CASES_DEFAULT = 100;

    /**
     * @param originalCommand
     *            The original commmand.
     * @param domain
     *            The editing domain.
     * @param owner
     *            The owner.
     * @param feature
     *            The feature to modify.
     * @param collection
     *            The collection to add to.
     * @param index
     *            the index to add at.
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

        if (owner instanceof FlowContainer
                && owner instanceof ExtendedAttributesContainer) {
            if (feature == Xpdl2Package.eINSTANCE.getFlowContainer_Activities()) {
                CompoundCommand ccmd = new CompoundCommand();
                ccmd.append(originalCommand);
                boolean hasDestination = false;
                if (owner instanceof Process) {
                    String destinationId =
                            SimulationConstants.SIMULATION_DEST_ENV_1_2_ID;
                    hasDestination =
                            DestinationUtil
                                    .isValidationDestinationEnabled((Process) owner,
                                            destinationId);
                }
                for (Iterator iter = collection.iterator(); iter.hasNext();) {
                    Object obj = iter.next();
                    if (obj instanceof Activity) {
                        if (hasDestination) {
                            assignSimDataToActivity(domain,
                                    (Activity) obj,
                                    ccmd);
                        }
                        // irrespective, add the gateway sim data
                        assignSplitSimDataToActivity(domain,
                                (Activity) obj,
                                ccmd);
                    }
                }
                originalCommand = ccmd.unwrap();
            }
        }
        return originalCommand;
    }

    /**
     * Assign simulation info to the activity.
     * 
     * @param domain
     *            The editing domain.
     * @param activity
     *            The activity to assign data to.
     * @param cmd
     *            The command to assign the data.
     */
    public static void assignSimDataToActivity(EditingDomain domain,
            Activity activity, CompoundCommand cmd) {

        Event event = activity.getEvent();
        Implementation impl = activity.getImplementation();
        if (event instanceof StartEvent) {
            ExtendedAttribute ea =
                    XpdlSearchUtil.findExtendedAttribute(activity,
                            SIM_START_EVENT_DATA);
            if (ea == null) {
                // start event
                setSimDataForStartEvent(domain, activity, cmd);
            }
        } else if (impl instanceof Task || impl instanceof SubFlow) {

            LateExecuteCompoundCommand c = new LateExecuteCompoundCommand();

            ExtendedAttribute ea =
                    XpdlSearchUtil.findExtendedAttribute(activity,
                            SIM_ACTIVITY_DATA);
            if (ea != null) {
                c.append(RemoveCommand.create(domain, ea));
            }

            // task activity
            setTaskSimData(domain, activity, c);

            cmd.append(c);
        }
        
        return;
    }

    /**
     * TODO JA Refactor - move to SimulationXpdlUtils for reuse.
     * 
     * @param domain
     *            The editing domain.
     * @param activity
     *            The activity to assign data to.
     * @param cmd
     *            The command to assign the data.
     */
    private static void setTaskSimData(EditingDomain domain, Activity activity,
            CompoundCommand cmd) {
        ExtendedAttribute ea = Xpdl2Factory.eINSTANCE.createExtendedAttribute();
        ea.setName(SIM_ACTIVITY_DATA);

        // creating and attaching default distribution.
        SimulationFactory simFact = SimulationFactory.eINSTANCE;

        ActivitySimulationDataType activitySimData =
                simFact.createActivitySimulationDataType();
        SimulationRealDistributionType duration =
                simFact.createSimulationRealDistributionType();
        ConstantRealDistribution constDist =
                simFact.createConstantRealDistribution();
        constDist.setConstantValue(CONSTANT_DIST_VALUE_DEFAULT);
        duration.setBasicDistribution(constDist);
        activitySimData.setDuration(duration);
        activitySimData.setDisplayTimeUnit(DEFAULT_TIME_UNIT);
        ea.getMixed().add(SimulationPackage.eINSTANCE
                .getDocumentRoot_ActivitySimulationData(),
                activitySimData);

        // adding extended attribute.
        EReference extAttFeature =
                Xpdl2Package.eINSTANCE
                        .getExtendedAttributesContainer_ExtendedAttributes();
        Command command =
                AddCommand.create(domain, activity, extAttFeature, ea);
        cmd.append(command);
    }

    /**
     * Assign simulation info to the start event.
     * 
     * @param domain
     *            The editing domain.
     * @param activity
     *            The activity to assign data to.
     * @param cmd
     *            The command to assign the data.
     */
    private static void setSimDataForStartEvent(EditingDomain domain,
            Activity activity, CompoundCommand cmd) {
        ExtendedAttribute ea = Xpdl2Factory.eINSTANCE.createExtendedAttribute();
        ea.setName(SIM_START_EVENT_DATA);

        // creating and attaching default distribution.
        StartSimulationDataType startSimData =
                SimulationFactory.eINSTANCE.createStartSimulationDataType();

        ConstantRealDistribution constDist =
                SimulationFactory.eINSTANCE.createConstantRealDistribution();
        constDist.setConstantValue(CONSTANT_DIST_VALUE_DEFAULT);

        startSimData.setDuration(constDist);
        startSimData.setDisplayTimeUnit(DEFAULT_TIME_UNIT);
        startSimData.setNumberOfCases(NUMBER_OF_CASES_DEFAULT);
        ea.getMixed().add(SimulationPackage.eINSTANCE
                .getDocumentRoot_StartSimulationData(),
                startSimData);

        // adding extended attribute.
        Command command =
                AddCommand
                        .create(domain,
                                activity,
                                Xpdl2Package.eINSTANCE
                                        .getExtendedAttributesContainer_ExtendedAttributes(),
                                ea);
        cmd.append(command);
    }

    /**
     * Assign simulation info to the activity.
     * 
     * @param domain
     *            The editing domain.
     * @param activity
     *            The activity to assign data to.
     * @param cmd
     *            The command to assign the data.
     */
    public static void assignSplitSimDataToActivity(EditingDomain domain,
            Activity activity, CompoundCommand cmd) {

        if (SimulationXpdlUtils.isRouteActivity(activity)) {
            ExtendedAttribute ea =
                    XpdlSearchUtil.findExtendedAttribute(activity,
                            SPLIT_SIM_ACTIVITY_DATA);
            if (ea == null) {
                createSplitSimulationData(domain, activity, cmd);
            }
        }
    }

    /**
     * Create simulation data for a split activity..
     * 
     * @param domain
     *            The editing domain.
     * @param activity
     *            The activity to assign data to.
     * @param compoundCommand
     *            The command to assign the data.
     */
    private static void createSplitSimulationData(EditingDomain domain,
            Activity activity, CompoundCommand compoundCommand) {

        // creating extended attributes.
        ExtendedAttribute ea = Xpdl2Factory.eINSTANCE.createExtendedAttribute();
        ea.setName(SPLIT_SIM_ACTIVITY_DATA);

        // creating and attaching default distribution.
        SplitSimulationDataType splitSimData =
                SimulationFactory.eINSTANCE.createSplitSimulationDataType();
        splitSimData.setParameterDeterminedSplit(true);
        SplitParameterType splitParameter =
                SimulationFactory.eINSTANCE.createSplitParameterType();
        splitParameter.setParameterId(""); //$NON-NLS-1$
        splitSimData.setSplitParameter(splitParameter);
        ea.getMixed().add(SimulationPackage.eINSTANCE
                .getDocumentRoot_SplitSimulationData(),
                splitSimData);
        // adding extended attribute.
        Command cmd =
                AddCommand
                        .create(domain,
                                activity,
                                Xpdl2Package.eINSTANCE
                                        .getExtendedAttributesContainer_ExtendedAttributes(),
                                ea);
        compoundCommand.append(cmd);
    }

}
