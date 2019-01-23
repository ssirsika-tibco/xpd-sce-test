/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.ui.properties;

import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.simulation.ActivitySimulationDataType;
import com.tibco.xpd.simulation.LoopControlType;
import com.tibco.xpd.simulation.SimulationConstants;
import com.tibco.xpd.simulation.SimulationFactory;
import com.tibco.xpd.simulation.SimulationPackage;
import com.tibco.xpd.simulation.common.util.SimulationXpdlUtils;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.validation.xpdl2.tools.Loop;
import com.tibco.xpd.validation.xpdl2.tools.LoopAnalyser;
import com.tibco.xpd.validation.xpdl2.tools.TransitionTool;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.ProcessFlowAnalyser;

/**
 * Simulation section.
 */
public class ActivityLoopControlNotExistsSection extends
        AbstractFilteredTransactionalSection {

    /** The form text for this section. */
    private static final String FORM_TEXT_ID =
            "activityLoopControlNotExistForm"; //$NON-NLS-1$

    /** The minimum height for this section. */
    private static final int MINIMUM_HEIGHT = 100;

    /**
     * Constructor.
     */
    public ActivityLoopControlNotExistsSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());
        setMinimumHeight(MINIMUM_HEIGHT);
        setShouldUseExtraSpace(true);
    }

    /**
     * @param object
     *            The input object.
     * @return true if this section should display.
     */
    public boolean select(Object object) {
        EObject eo = getBaseSelectObject(object);
        if (eo instanceof Activity) {
            Activity activity = (Activity) eo;

            Process proc = activity.getProcess();
            if (proc != null) {
                if (DestinationUtil.isValidationDestinationEnabled(proc,
                        SimulationConstants.SIMULATION_DEST_ENV_1_2_ID)
                        && simDataShouldExist(activity)
                        && !loopControlStrategyExist(activity)) {

                    // MR 41085 - Performance issues selecting tasks when
                    // simulation destination set on for a complex processes.
                    boolean activityInLoop = false;

                    ProcessFlowAnalyser analyser =
                            new ProcessFlowAnalyser(true, activity
                                    .getFlowContainer());
                    activityInLoop = analyser.isInLoop(activity.getId());

                    if (activityInLoop) {
                        return true;
                    }
                    // MR 41085 - END
                }
            }
        }
        return false;
    }

    /**
     * @param act
     *            The activity.
     * @return true if it should have simulation data.
     */
    private boolean simDataShouldExist(Activity act) {
        Implementation impl = act.getImplementation();
        if (impl != null) {
            if (impl instanceof Task) {
                // task activity
                return true;
            }
            if (impl instanceof SubFlow) {
                // sub flow
                return true;
            }
        }
        return false;
    }

    /**
     * @param parent
     *            The parent composite.
     * @param toolkit
     *            The ui toolkit.
     * @return The root control.
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doCreateControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     */
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {

        Composite root = toolkit.createComposite(parent);
        TableWrapLayout layout = new TableWrapLayout();
        root.setLayout(layout);

        FormText text = toolkit.createFormText(root, true);
        text.setText(PropertiesMessage.getString(FORM_TEXT_ID), true, false);

        manageControl(text);
        return root;
    }

    /**
     * @param eObject
     *            The activity to check.
     * @return true if a loop control strategy exists for that section.
     */
    private boolean loopControlStrategyExist(EObject eObject) {
        ActivitySimulationDataType simData =
                SimulationXpdlUtils
                        .getActivitySimulationData((Activity) eObject);
        if (simData == null) {
            return false;
        }
        LoopControlType loopControl = simData.getLoopControl();
        return loopControl != null;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     */
    protected void doRefresh() {
    }

    @Override
    protected boolean shouldRefresh(List<Notification> notifications) {
        for (Notification n : notifications) {
            if (n.getNotifier() instanceof ActivitySimulationDataType) {
                if (n.getEventType() == Notification.SET) {
                    int id = n.getFeatureID(ActivitySimulationDataType.class);
                    if (id == SimulationPackage.ACTIVITY_SIMULATION_DATA_TYPE__LOOP_CONTROL) {
                        refreshTabs();
                        return false;
                    }
                }
            }
        }
        return super.shouldRefresh(notifications);
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
        }
        return UnexecutableCommand.INSTANCE;
    }

    /**
     * @return The add element command.
     */
    protected Command createAddElementCmd() {
        Activity activity = (Activity) getInput();
        CompoundCommand compoundCmd =
                new CompoundCommand(PropertiesMessage
                        .getString("ActivityLoopControlNotExistsSection_Add")); //$NON-NLS-1$
        ActivitySimulationDataType activitySimData =
                getActivitySimulationData(activity);
        LoopControlType loopControlType =
                SimulationFactory.eINSTANCE.createLoopControlType();
        Command command =
                SetCommand.create(getEditingDomain(),
                        activitySimData,
                        SimulationPackage.eINSTANCE
                                .getActivitySimulationDataType_LoopControl(),
                        loopControlType);
        compoundCmd.append(command);
        return compoundCmd;
    }

    /**
     * Gets AstivitySimulationData for Activity if tihs data exists otherwise
     * returns null.
     * 
     * @param activity
     *            xpdl activity.
     * @return AstivitySimulationData for Activity if tihs data exists otherwise
     *         returns null.
     */
    private ActivitySimulationDataType getActivitySimulationData(
            Activity activity) {
        for (Iterator iter = activity.getExtendedAttributes().iterator(); iter
                .hasNext();) {
            ExtendedAttribute ea = (ExtendedAttribute) iter.next();
            if ("ActivitySimulationData".equals(ea.getName())) { //$NON-NLS-1$
                EList simProcessList =
                        (EList) ea.getMixed().get(SimulationPackage.eINSTANCE
                                .getDocumentRoot_ActivitySimulationData(),
                                true);
                // there can only be one of these
                if (simProcessList.size() == 1 && simProcessList.get(0) != null) {
                    return (ActivitySimulationDataType) simProcessList.get(0);
                }
                return null;
            }
        }
        return null;
    }

    /**
     * @return An array of objects to monitor for changes.
     * @see com.tibco.xpd.ui.properties.AbstractEObjectSection#getNotifierObjects()
     */
    public EObject[] getNotifierObjects() {
        Activity activity = (Activity) getInput();
        ActivitySimulationDataType actSimData =
                getActivitySimulationData(activity);
        return new EObject[] { actSimData };
    }

}
