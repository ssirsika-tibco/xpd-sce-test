/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.simulation.ui.properties;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.ui.provider.TransactionalAdapterFactoryContentProvider;
import org.eclipse.emf.transaction.ui.provider.TransactionalAdapterFactoryLabelProvider;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.part.PageBook;

import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.SashDividedFilteredTransactionalSection;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.simulation.SimulationConstants;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantsContainer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * Section provided for Studio for Analysts in order to allow manipulation of
 * participant simulation data.
 * 
 * @author sajain, jarciuch
 * @since Oct 9, 2013
 */
public class PackageSimulationSection extends
        SashDividedFilteredTransactionalSection {

    /** Composite for general part (usually on the left). */
    private Composite general;

    /** Composite for details part (usually on the right) */
    private PageBook details;

    /** Table to show package and process participants */
    private TableViewer participants;

    /** Property section for the participant with simulation data. */
    private ParticipantSimulationExistSection participantSimSection =
            new ParticipantSimulationExistSection();

    /** Property section for the participant without simulation data. */
    private ParticipantSimulationNotExistSection participantNoSimSection =
            new ParticipantSimulationNotExistSection(/* embedded */true);

    /** Book page for empty selection. */
    private Composite noParticipantControl;

    /** Book page for participant with simulation data. */
    private Composite participantSimControl;

    /** Book page for participant without simulation data */
    private Control participantNoSimControl;

    public PackageSimulationSection() {
        super(Xpdl2Package.eINSTANCE.getPackage(), ""); //$NON-NLS-1$
    }

    @Override
    protected Composite createDetailsSection(Composite parent,
            XpdFormToolkit toolkit) {
        details = new PageBook(parent, SWT.NONE);

        /* Create book pages. */
        noParticipantControl = toolkit.createComposite(details);
        GridDataFactory.fillDefaults().grab(true, true)
                .applyTo(noParticipantControl);
        GridLayoutFactory.swtDefaults().applyTo(noParticipantControl);
        GridDataFactory
                .fillDefaults()
                .grab(true, true)
                .indent(5, 5)
                .applyTo(toolkit.createLabel(noParticipantControl,
                        PropertiesMessage
                                .getString("PackageSimulationSection.NoParticipants_message"))); //$NON-NLS-1$

        participantSimControl = toolkit.createComposite(details);
        GridDataFactory.fillDefaults().grab(true, true)
                .applyTo(participantSimControl);
        GridLayoutFactory.swtDefaults().applyTo(participantSimControl);
        GridDataFactory
                .swtDefaults()
                .applyTo(toolkit.createLabel(participantSimControl,
                        PropertiesMessage
                                .getString("PackageSimulationSection.ParticipantSimulationProperties_label"))); //$NON-NLS-1$
        participantSimSection.createControls(participantSimControl,
                getPropertySheetPage());
        GridDataFactory.fillDefaults().grab(true, true)
                .applyTo(participantSimSection.getControl());

        participantNoSimSection.createControls(details, getPropertySheetPage());
        participantNoSimControl = participantNoSimSection.getControl();
        GridDataFactory.fillDefaults().grab(true, true)
                .applyTo(participantNoSimControl);

        details.showPage(noParticipantControl);
        return details;
    }

    @Override
    protected Composite createGeneralSection(Composite parent,
            XpdFormToolkit toolkit) {
        general = toolkit.createComposite(parent);

        GridLayoutFactory.swtDefaults().numColumns(1).applyTo(general);

        Label participantsLabel =
                toolkit.createLabel(general,
                        PropertiesMessage
                                .getString("ParticipantSimulationSection_Participants_Label")); //$NON-NLS-1$
        participantsLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER,
                false, false, 1, 1));

        Table participantTable =
                toolkit.createTable(general,
                        SWT.SINGLE | SWT.FULL_SELECTION,
                        "tableInstrumentationName");//$NON-NLS-1$
        participantTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
                true));
        participants = new TableViewer(participantTable);
        participants
                .setContentProvider(new TransactionalAdapterFactoryContentProvider(
                        XpdResourcesPlugin.getDefault().getEditingDomain(),
                        XpdResourcesPlugin.getDefault().getAdapterFactory()) {
                    /**
                     * Returns participants i.e. participants in the specified
                     * process/package.
                     * 
                     * @see org.eclipse.jface.viewers.ITreeContentProvider#getElements(java.lang.Object)
                     * 
                     * @param inputElement
                     * @return
                     */
                    @Override
                    public Object[] getElements(Object inputElement) {

                        if (inputElement instanceof Package) {
                            List<Participant> simParticipants =
                                    new ArrayList<Participant>();

                            /*
                             * Get participant from the package and processes
                             * with enabled simulation.
                             */
                            if (inputElement instanceof Package) {
                                Package pkg = (Package) inputElement;
                                simParticipants.addAll(pkg.getParticipants());
                                EList<Process> processes = pkg.getProcesses();
                                for (Process p : processes) {
                                    if (DestinationUtil
                                            .isValidationDestinationEnabled(p,
                                                    SimulationConstants.SIMULATION_DEST_ENV_1_2_ID)) {
                                        simParticipants.addAll(p
                                                .getParticipants());
                                    }
                                }
                            }

                            if (simParticipants.isEmpty()) {
                                /*
                                 * If there are no participants then display
                                 * message that no participants are available.
                                 */
                                String[] str = new String[1];
                                str[0] =
                                        PropertiesMessage
                                                .getString("ParticipantContentProvider_NoParticipantPresent_label");//$NON-NLS-1$
                                return str;

                            }
                            Participant participantArray[] =
                                    simParticipants
                                            .toArray(new Participant[simParticipants
                                                    .size()]);
                            return participantArray;
                        }
                        return new Object[0];

                    }
                });
        participants
                .setLabelProvider(new TransactionalAdapterFactoryLabelProvider(
                        XpdResourcesPlugin.getDefault().getEditingDomain(),
                        XpdResourcesPlugin.getDefault().getAdapterFactory()));
        participants
                .addSelectionChangedListener(new ISelectionChangedListener() {
                    @Override
                    public void selectionChanged(SelectionChangedEvent event) {
                        IStructuredSelection selection =
                                (IStructuredSelection) event.getSelection();
                        if (selection.size() > 0
                                && selection.getFirstElement() instanceof Participant) {
                            Participant participant =
                                    (Participant) selection.getFirstElement();
                            if (ParticipantSimulationExistSection
                                    .getParticipantSimulationData(participant) != null) {
                                details.showPage(participantSimControl);
                            } else {
                                details.showPage(participantNoSimControl);
                            }
                            participantSimSection.setInput(getPart(),
                                    new StructuredSelection(participant));
                            participantSimSection.refresh();

                            participantNoSimSection.setInput(getPart(),
                                    new StructuredSelection(participant));
                            participantNoSimSection.refresh();
                        } else {
                            details.showPage(noParticipantControl);
                            participantSimSection.setInput(null);
                            participantNoSimSection.setInput(null);
                        }
                    }
                });

        return general;
    }

    @Override
    protected Command doGetCommand(Object src) {
        /*
         * No command is needed at this level as model-view updates are managed
         * by details sections.
         */
        return null;
    }

    @Override
    protected boolean shouldRefresh(List<Notification> notifications) {
        /*
         * We also should refresh for participant's notification, not only for
         * base object (xpdl package) as we need to switch pages when
         * participant's simulation data is added.
         */
        if (notifications != null) {
            for (Notification n : notifications) {
                if (n.getNotifier() instanceof Participant) {
                    return true;
                }
            }
        }
        return super.shouldRefresh(notifications);
    }

    @Override
    protected void doRefresh() {
        Object input = getInput();
        if (input != null) {
            if (input instanceof ParticipantsContainer) {
                ParticipantsContainer container = (ParticipantsContainer) input;
                participants.setInput(container);
            }
            ISelection sel = participants.getSelection();
            if (!sel.isEmpty()) {
                /*
                 * Refresh current selection - this is needed as pagebook pages
                 * might need to be changed.
                 */
                participants.setSelection(sel);
            } else {
                // Select first element if possible.
                Object first = participants.getElementAt(0);
                if (first != null) {
                    participants.setSelection(new StructuredSelection(first));
                }
            }
        } else {
            participants.setInput(null);
        }
    }

    @Override
    public boolean select(Object object) {
        /*
         * Show tab only if one of the projects in the package have simulation
         * destination enabled.
         */
        EObject eo = getBaseSelectObject(object);
        if (eo instanceof Package) {
            Package p = (Package) eo;
            if (DestinationUtil.isValidationDestinationEnabled(p,
                    SimulationConstants.SIMULATION_DEST_ENV_1_2_ID)) {
                return true;
            }
        }
        return false;
    }
}
