/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.simulation.ui.properties;

import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.part.PageBook;

import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.simulation.SimulationConstants;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Section to show participant simulation data.
 * 
 * @author kthombar
 * @since 3.7.0 (27-Jan-2014)
 */
public class ParticipantSimulationSection extends
        AbstractFilteredTransactionalSection {

    /** Composite for participantSimPageBook part (usually on the right) */
    private PageBook participantSimPageBook;

    /** Property section for the participant with simulation data. */
    private ParticipantSimulationExistSection participantSimSection =
            new ParticipantSimulationExistSection();

    /** Property section for the participant without simulation data. */
    private ParticipantSimulationNotExistSection participantNoSimSection =
            new ParticipantSimulationNotExistSection(/* embedded */true);

    /** Book page for participant with simulation data. */
    private Composite participantSimControl;

    /** Book page for participant without simulation data */
    private Control participantNoSimControl;

    public ParticipantSimulationSection() {
        super(Xpdl2Package.eINSTANCE.getParticipant());
    }

    /**
     * Switch appropriately between the Simulation enabled and Simulation
     * disabled sections.
     * 
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {
        EObject input = getInput();
        if (input instanceof Participant) {
            Participant participant = (Participant) input;
            if (ParticipantSimulationExistSection
                    .getParticipantSimulationData(participant) != null) {
                participantSimPageBook.showPage(participantSimControl);
                participantSimSection.setInput(getPart(),
                        new StructuredSelection(participant));
                participantSimSection.refresh();
            } else {
                participantSimPageBook.showPage(participantNoSimControl);
                participantNoSimSection.setInput(getPart(),
                        new StructuredSelection(participant));
                participantNoSimSection.refresh();
            }
        }
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doCreateControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param parent
     * @param toolkit
     * @return
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        participantSimPageBook = new PageBook(parent, SWT.NONE);

        participantSimControl = toolkit.createComposite(participantSimPageBook);
        GridDataFactory.fillDefaults().grab(true, true)
                .applyTo(participantSimControl);
        GridLayoutFactory.swtDefaults().applyTo(participantSimControl);
        GridDataFactory
                .swtDefaults()
                .applyTo(toolkit.createLabel(participantSimControl,
                        PropertiesMessage
                                .getString("ParticipantSimulationSection_ParticipantSimulationProperties_label"))); //$NON-NLS-1$
        participantSimSection.createControls(participantSimControl,
                getPropertySheetPage());
        GridDataFactory.fillDefaults().grab(true, true)
                .applyTo(participantSimSection.getControl());

        participantNoSimSection.createControls(participantSimPageBook,
                getPropertySheetPage());
        participantNoSimControl = participantNoSimSection.getControl();
        GridDataFactory.fillDefaults().grab(true, true)
                .applyTo(participantNoSimControl);

        participantSimPageBook.showPage(participantNoSimControl);

        return participantSimPageBook;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection#select(java.lang.Object)
     * 
     * @param toTest
     * @return
     */
    @Override
    public boolean select(Object toTest) {
        /*
         * Check if the object is a participant and simulation is enabled.
         */
        if (toTest instanceof Participant) {
            Package package1 = Xpdl2ModelUtil.getPackage((Participant) toTest);
            if (package1 != null) {
                if (DestinationUtil.isValidationDestinationEnabled(package1,
                        SimulationConstants.SIMULATION_DEST_ENV_1_2_ID)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    protected Command doGetCommand(Object obj) {
        /*
         * No need to fire command here. doRefresh does the work.
         */
        return null;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractTransactionalSection#shouldRefresh(java.util.List)
     * 
     * @param notifications
     * @return
     */
    @Override
    protected boolean shouldRefresh(List<Notification> notifications) {

        if (getInput() != null && notifications != null
                && !notifications.isEmpty()) {
            for (Notification notification : notifications) {
                if (!notification.isTouch()) {
                    Object notifier = notification.getNotifier();
                    if (notifier instanceof Participant) {
                        return true;
                    }
                }
            }
        }
        return super.shouldRefresh(notifications);
    }
}
