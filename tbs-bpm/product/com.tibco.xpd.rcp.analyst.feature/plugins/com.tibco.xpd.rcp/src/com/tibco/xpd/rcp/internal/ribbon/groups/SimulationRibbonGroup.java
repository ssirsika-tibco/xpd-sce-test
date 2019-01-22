/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.ribbon.groups;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.hexapixel.widgets.ribbon.RibbonButton;
import com.hexapixel.widgets.ribbon.RibbonButtonGroup;
import com.hexapixel.widgets.ribbon.RibbonGroup;
import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.RCPImages;
import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.rcp.internal.actions.CompareSimulationAction;
import com.tibco.xpd.rcp.internal.actions.EnableSimulationAction;
import com.tibco.xpd.rcp.internal.actions.PrepareSimulationAction;
import com.tibco.xpd.rcp.internal.actions.RunSimulationAction;
import com.tibco.xpd.rcp.ribbon.AbstractRibbonGroup;
import com.tibco.xpd.rcp.ribbon.action.AbstractRibbonAction;

/**
 * Simulation ribbon group that will list the simulation options available
 * 
 * @author mtorres
 */
public class SimulationRibbonGroup extends AbstractRibbonGroup {

    private static final ImageRegistry IMAGE_REGISTRY = RCPActivator
            .getDefault().getImageRegistry();

    private AbstractRibbonAction enableSimulationAction;

    private AbstractRibbonAction prepareSimulationAction;

    private AbstractRibbonAction runSimulationAction;

    private AbstractRibbonAction compareSimulationAction;

    private RibbonGroup group;

    public SimulationRibbonGroup() {
    }

    @Override
    protected void createContent(RibbonGroup group) {
        this.group = group;

        RibbonButtonGroup leftSimulationButtonGroup = createButtonGroup(group);

        enableSimulationAction =
                createCheckboxAction(leftSimulationButtonGroup,
                        new EnableSimulationAction(getWindow()),
                        Messages.SimulationRibbonGroup_EnableSimulation,
                        RibbonButton.STYLE_TOGGLE,
                        true);

        enableSimulationAction
                .getAction()
                .addPropertyChangeListener(new EnableSimulationPropertyChangeListener());

        enableSimulationAction
                .getButton()
                .addSelectionListener(new EnableSimulationCheckboxSelectionListener());

        runSimulationAction =
                createAction(leftSimulationButtonGroup,
                        new RunSimulationAction(getWindow()),
                        Messages.SimulationRibbonGroup_RunSimulation,
                        IMAGE_REGISTRY.get(RCPImages.RUNSIMULATION.getPath()),
                        IMAGE_REGISTRY.get(RCPImages.RUNSIMULATION
                                .getDisabledPath()));

        RibbonButtonGroup rightSimulationButtonGroup = createButtonGroup(group);

        prepareSimulationAction =
                createAction(rightSimulationButtonGroup,
                        new PrepareSimulationAction(getWindow()),
                        Messages.SimulationRibbonGroup_PrepareSimulation,
                        IMAGE_REGISTRY.get(RCPImages.PREPARESIMULATION
                                .getPath()),
                        IMAGE_REGISTRY.get(RCPImages.PREPARESIMULATION
                                .getDisabledPath()));

        compareSimulationAction =
                createAction(rightSimulationButtonGroup,
                        new CompareSimulationAction(getWindow()),
                        Messages.SimulationRibbonGroup_CompareSimulation,
                        IMAGE_REGISTRY.get(RCPImages.COMPARESIMULATION
                                .getPath()),
                        IMAGE_REGISTRY.get(RCPImages.COMPARESIMULATION
                                .getDisabledPath()));

    }

    /**
     * @return the enableSimulationAction
     */
    public AbstractRibbonAction getEnableSimulationAction() {
        return enableSimulationAction;
    }

    /**
     * @return the prepareSimulationAction
     */
    public AbstractRibbonAction getPrepareSimulationAction() {
        return prepareSimulationAction;
    }

    /**
     * @return the runSimulationAction
     */
    public AbstractRibbonAction getRunSimulationAction() {
        return runSimulationAction;
    }

    /**
     * @return the compareSimulationAction
     */
    public AbstractRibbonAction getCompareSimulationAction() {
        return compareSimulationAction;
    }

    /**
     * Class that reacts to property change in EnableSimulationAction
     * 
     * @author mtorres
     * @since Oct 16, 2012
     */
    private final class EnableSimulationPropertyChangeListener implements
            IPropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent event) {
            // If the action has been enabled update the state of
            // the checkbox
            if (event.getSource() instanceof EnableSimulationAction) {
                EnableSimulationAction action =
                        (EnableSimulationAction) event.getSource();
                if (IAction.ENABLED.equals(event.getProperty())) {
                    if (!action.isEnabled()) {
                        getEnableSimulationAction().getButton()
                                .setSelected(false);
                        action.setChecked(false);
                    }
                } else if (action.isEnabled()
                        && IAction.CHECKED.equals(event.getProperty())) {
                    getEnableSimulationAction().getButton()
                            .setSelected(action.isChecked());
                    group.redraw();
                }
                updateSimulationButtons();
            }
        }
    }

    /**
     * Class that reacts to changes in enable simulation check box
     * 
     * @author mtorres
     * @since Oct 16, 2012
     */
    private final class EnableSimulationCheckboxSelectionListener extends
            SelectionAdapter {
        /**
         * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
         * 
         * @param e
         */
        @Override
        public void widgetSelected(SelectionEvent e) {
            updateSimulationButtons();
        }
    }

    private void updateSimulationButtons() {
        boolean selected = enableSimulationAction.getButton().isSelected();
        getPrepareSimulationAction().getButton().setEnabled(selected);
        getRunSimulationAction().getButton().setEnabled(selected);
        getCompareSimulationAction().getButton().setEnabled(selected);
        group.redraw();
    }

}
