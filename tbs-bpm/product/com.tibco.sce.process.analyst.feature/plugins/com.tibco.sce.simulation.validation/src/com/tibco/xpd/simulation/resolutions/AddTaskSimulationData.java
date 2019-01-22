/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.simulation.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.simulation.ActivitySimulationDataType;
import com.tibco.xpd.simulation.ConstantRealDistribution;
import com.tibco.xpd.simulation.SimulationConstants;
import com.tibco.xpd.simulation.SimulationFactory;
import com.tibco.xpd.simulation.SimulationPackage;
import com.tibco.xpd.simulation.SimulationRealDistributionType;
import com.tibco.xpd.simulation.common.command.SimulationAddActivityContribution;
import com.tibco.xpd.simulation.common.util.SimulationXpdlUtils;
import com.tibco.xpd.simulation.validation.Messages;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * @author nwilson
 */
public class AddTaskSimulationData extends AbstractWorkingCopyResolution {

    /** Distribution value constant. */
    private static final double CONSTANT_DIST_VALUE = 5.0D;

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
        if (target instanceof Process) {
            String name = Messages.AddTaskSimulationData_AddData;
            CompoundCommand cc = new CompoundCommand(name);
            Process process = (Process) target;
            for (Object next : process.getActivities()) {
                if (next instanceof Activity) {
                    Activity activity = (Activity) next;
                    Command cmd = getActivityCommand(ed, activity);
                    if (cmd != null) {
                        cc.append(cmd);
                    }
                }
            }
            command = cc;
        }
        if (target instanceof Activity) {
            Activity activity = (Activity) target;
            command = getActivityCommand(ed, activity);
        }
        return command;
    }

    /**
     * @param ed
     *            The editing domain.
     * @param activity
     *            The activity to get the command for.
     * @return The command for the activity.
     */
    private Command getActivityCommand(EditingDomain ed, Activity activity) {
        CompoundCommand command = null;

        ActivitySimulationDataType simData =
                SimulationXpdlUtils.getActivitySimulationData(activity);

        if (simData == null) {
            command = new CompoundCommand();
            SimulationAddActivityContribution.assignSimDataToActivity(ed, activity, command);
        }

        return command;
    }

}
