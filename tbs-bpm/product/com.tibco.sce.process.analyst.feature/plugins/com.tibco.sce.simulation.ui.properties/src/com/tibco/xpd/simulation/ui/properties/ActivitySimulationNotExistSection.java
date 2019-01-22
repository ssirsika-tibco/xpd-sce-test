/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.ui.properties;

import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.simulation.SimulationConstants;
import com.tibco.xpd.simulation.common.command.SimulationAddActivityContribution;
import com.tibco.xpd.simulation.common.util.SimulationXpdlUtils;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * Activity simulation data not exist section.
 */
public class ActivitySimulationNotExistSection extends
        AbstractFilteredTransactionalSection {

    /** Minimum section height. */
    private static final int SECTION_HEIGHT = 100;

    /**
     * Constructor.
     */
    public ActivitySimulationNotExistSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());
        setMinimumHeight(SECTION_HEIGHT);
        setShouldUseExtraSpace(true);
    }

    /**
     * @param object
     *            The input object.
     * @return true if the section should show.
     * @see com.tibco.xpd.ui.properties.AbstractEObjectSection#select(java.lang.Object)
     */
    public boolean select(Object object) {
        EObject eo = getBaseSelectObject(object);
        if (eo instanceof Activity) {
            Activity activity = (Activity) eo;
            Process proc = activity.getProcess();
            if (proc != null) {
                if (DestinationUtil.isValidationDestinationEnabled(proc,
                        SimulationConstants.SIMULATION_DEST_ENV_1_2_ID)
                        && !simDataExist(activity)
                        && simDataShouldExist(activity)) {

                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param act
     *            The activity.
     * @return true if the activity should have simulation data.
     */
    private boolean simDataShouldExist(Activity act) {
        Implementation impl = act.getImplementation();
        if (impl instanceof Task || impl instanceof SubFlow) {
            // task activity
            return true;
        }
        Event event = act.getEvent();
        if (event instanceof StartEvent) {
            return true;
        }
        return false;
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
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {

        Composite root = toolkit.createComposite(parent);
        TableWrapLayout layout = new TableWrapLayout();
        root.setLayout(layout);
        // root.setLayoutData(new GridData(GridData.FILL_BOTH));

        FormText l = toolkit.createFormText(root, true);
        l.setText(
                PropertiesMessage.getString("activitySimulationNotExistForm"), //$NON-NLS-1$
                true, false);

        manageControl(l);

        return root;
    }

    /**
     * @param act
     *            The activity.
     * @return true if the activity has simulation data.
     */
    private boolean simDataExist(EObject act) {
        Object simData;
        simData = SimulationXpdlUtils.getActivitySimulationData((Activity) act);
        if (simData == null) {
            simData = SimulationXpdlUtils
                    .getStartSimulationData((Activity) act);
        }
        return simData != null;
    }

    @Override
    protected boolean shouldRefresh(List<Notification> notifications) {
        Object in = getInput();
        if (in instanceof EObject) {
            if (simDataExist((EObject) in)) {
                refreshTabs();
                return false;
            }
        }
        return super.shouldRefresh(notifications);
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     */
    protected void doRefresh() {
    }

    /**
     * @param obj
     *            The object to get the command for.
     * @return The command.
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     */
    public Command doGetCommand(Object obj) {
        Object href = obj;
        if ("this".equals(href)) { //$NON-NLS-1$
            return createAddElementCmd();
        } else if ("all".equals(href)) { //$NON-NLS-1$
            return createAddAllElementsCmd();
        }
        return UnexecutableCommand.INSTANCE;
    }

    /**
     * @return The command to add simulation data to the input activity.
     */
    protected Command createAddElementCmd() {
        Activity activity = (Activity) getInput();
        CompoundCommand compoundCmd = new CompoundCommand(PropertiesMessage
                .getString("ActivitySimulationNotExistSection.Add")); //$NON-NLS-1$
        SimulationAddActivityContribution.assignSimDataToActivity(
                getEditingDomain(), activity, compoundCmd);
        return compoundCmd;
    }

    /**
     * @return A command to add simulation data to all activities in the
     *         process.
     */
    private Command createAddAllElementsCmd() {
        Activity activity = (Activity) getInput();
        CompoundCommand compoundCmd = new CompoundCommand(PropertiesMessage
                .getString("ActivitySimulationNotExistSection.Add")); //$NON-NLS-1$
        Process process = activity.getProcess();
        List activities = process.getActivities();
        for (Object next : activities) {
            Activity current = (Activity) next;
            if (!simDataExist(current) && simDataShouldExist(current)) {
                SimulationAddActivityContribution.assignSimDataToActivity(
                        getEditingDomain(), current, compoundCmd);
            }
        }
        return compoundCmd;
    }

}
