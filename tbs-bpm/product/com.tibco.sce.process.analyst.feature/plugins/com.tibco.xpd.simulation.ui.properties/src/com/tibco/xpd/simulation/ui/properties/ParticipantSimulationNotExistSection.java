/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.ui.properties;

import java.util.Iterator;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.simulation.ParticipantSimulationDataType;
import com.tibco.xpd.simulation.SimulationConstants;
import com.tibco.xpd.simulation.common.command.SimulationAddParticipantContribution;
import com.tibco.xpd.simulation.common.util.SimulationXpdlUtils;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantsContainer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * Participant simulation not exist section.
 */
public class ParticipantSimulationNotExistSection extends
        AbstractFilteredTransactionalSection {

    /** The minimum section height. */
    private static final int SECTION_HEIGHT = 30;

    private boolean shouldRefreshTabs;

    private final boolean neverRefreshTabs;

    /**
     * Constructor.
     */
    public ParticipantSimulationNotExistSection() {
        this(false);

    }

    public ParticipantSimulationNotExistSection(boolean embedded) {
        super(Xpdl2Package.eINSTANCE.getParticipant());
        setMinimumHeight(SECTION_HEIGHT);

        /* Tabs should not be refreshed if section is embedded. */
        neverRefreshTabs = embedded;
    }

    /**
     * @param parent
     *            The parent composite.
     * @param toolkit
     *            The UI toolkit.
     * @return The root section control.
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doCreateControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = toolkit.createComposite(parent);
        TableWrapLayout layout = new TableWrapLayout();
        root.setLayout(layout);
        // root.setLayoutData(new GridData(GridData.FILL_BOTH));

        FormText l = toolkit.createFormText(root, true);
        l.setText(PropertiesMessage
                .getString("participantSimulationNotExistForm"), true, false); //$NON-NLS-1$

        manageControl(l);

        return root;
    }

    /**
     * @param obj
     *            The object to get the command for.
     * @return The command.
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     */
    @Override
    public Command doGetCommand(Object obj) {
        Command cmd = null;
        if ("this".equals(obj)) { //$NON-NLS-1$
            cmd = getAddElementCommand();
        } else if ("all".equals(obj)) { //$NON-NLS-1$
            cmd = getAddAllElementsCommand();
        }
        if (cmd != null && cmd.canExecute()) {
            shouldRefreshTabs = true;
        }
        return cmd;
    }

    /**
     * @return Command to add simulation data to all participants.
     */
    private Command getAddAllElementsCommand() {
        Participant participant = (Participant) getInput();

        ParticipantsContainer cont =
                (ParticipantsContainer) participant.eContainer();

        EditingDomain ed = getEditingDomain(participant);

        CompoundCommand compoundCmd =
                new CompoundCommand(
                        PropertiesMessage
                                .getString("ParticipantSimulationNotExistSection.Add")); //$NON-NLS-1$
        for (Iterator iter = cont.getParticipants().iterator(); iter.hasNext();) {
            Participant prt = (Participant) iter.next();
            ParticipantSimulationDataType simData =
                    SimulationXpdlUtils.getParticipantSimulationData(prt);
            if (simData == null) {
                SimulationAddParticipantContribution
                        .assignSimDataToParticipant(ed, prt, compoundCmd);
            }
        }
        return compoundCmd;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     */
    @Override
    protected void doRefresh() {
        if (shouldRefreshTabs) {
            shouldRefreshTabs = false;
            if (!neverRefreshTabs) {
                refreshTabs();
            }
        }
    }

    /**
     * @return Command to add simulation data to the input participant.
     */
    protected Command getAddElementCommand() {
        Participant participant = (Participant) getInput();
        EditingDomain ed = getEditingDomain(participant);

        CompoundCommand compoundCmd =
                new CompoundCommand(
                        PropertiesMessage
                                .getString("ParticipantSimulationNotExistSection.Add")); //$NON-NLS-1$
        SimulationAddParticipantContribution.assignSimDataToParticipant(ed,
                participant,
                compoundCmd);
        return compoundCmd;
    }

    /**
     * @param object
     *            The input object.
     * @return true if the section should display.
     * @see com.tibco.xpd.ui.properties.AbstractEObjectSection#select(java.lang.Object)
     */
    @Override
    public boolean select(Object object) {
        EObject eo = getBaseSelectObject(object);
        if (eo instanceof Participant) {
            Participant participant = (Participant) eo;
            EObject pc = participant.eContainer();
            if (pc instanceof Process) {
                Process proc = (Process) pc;
                if (proc != null) {
                    if (DestinationUtil.isValidationDestinationEnabled(proc,
                            SimulationConstants.SIMULATION_DEST_ENV_1_2_ID)
                            && !simDataExist(participant)) {

                        return true;
                    }
                }
            } else if (pc instanceof Package) {
                boolean hasSimulation = false;
                Package p = (Package) pc;
                for (Iterator i = p.getProcesses().iterator(); i.hasNext();) {
                    Process wp = (Process) i.next();
                    if (DestinationUtil.isValidationDestinationEnabled(wp,
                            SimulationConstants.SIMULATION_DEST_ENV_1_2_ID)
                            && !simDataExist(participant)) {
                        hasSimulation = true;
                        break;
                    }
                }
                if (hasSimulation) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param eo
     *            The object to check.
     * @return true if the object has simulation data.
     */
    private boolean simDataExist(EObject eo) {
        ParticipantSimulationDataType simData =
                SimulationXpdlUtils
                        .getParticipantSimulationData((Participant) eo);
        return simData != null;
    }
}
