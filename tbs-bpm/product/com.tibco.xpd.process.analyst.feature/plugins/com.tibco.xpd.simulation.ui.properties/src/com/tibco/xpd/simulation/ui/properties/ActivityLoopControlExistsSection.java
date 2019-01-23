/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.ui.properties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.xml.type.XMLTypeFactory;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.ScrolledPageBook;

import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.simulation.ActivitySimulationDataType;
import com.tibco.xpd.simulation.LoopControlStrategy;
import com.tibco.xpd.simulation.LoopControlType;
import com.tibco.xpd.simulation.MaxElapseTimeStrategyType;
import com.tibco.xpd.simulation.MaxLoopCountStrategyType;
import com.tibco.xpd.simulation.NormalDistributionStrategyType;
import com.tibco.xpd.simulation.SimulationConstants;
import com.tibco.xpd.simulation.SimulationFactory;
import com.tibco.xpd.simulation.SimulationPackage;
import com.tibco.xpd.simulation.TimeDisplayUnitType;
import com.tibco.xpd.simulation.common.util.DisplayTimeUnitConverter;
import com.tibco.xpd.simulation.common.util.SimulationXpdlUtils;
import com.tibco.xpd.simulation.provider.SimulationExtensionsEditPlugin;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.TextFieldVerifier;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.JoinSplitType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Route;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Simulation section.
 */
public class ActivityLoopControlExistsSection extends
        AbstractFilteredTransactionalSection implements TextFieldVerifier {

    /** The form text. */
    private static final String FORM_TEXT_ID = "activityLoopControlExistForm"; //$NON-NLS-1$

    /** The minimum height for this section. */
    private static final int MINIMUM_SECTION_HEIGHT = 160;

    /** Strategy combo. */
    private CCombo strategyCCombo;

    /** Page book. */
    private ScrolledPageBook book;

    /** Edit plugin instance. */
    private static SimulationExtensionsEditPlugin plugIn =
            SimulationExtensionsEditPlugin.INSTANCE;

    /** Default time unit. */
    private static final TimeDisplayUnitType DEFAULT_TIME_UNIT =
            TimeDisplayUnitType.MINUTE_LITERAL;

    /** max loop count strategy. */
    private Text maxLoopCountText;

    /** max loop decision combo. */
    private CCombo maxLoopDecisionActCombo;

    /** max loop destination combo. */
    private CCombo maxLoopToActCombo;

    /** Decision activity list. */
    private List decisionActivityList;

    /** Destination activity list. */
    private List toActivityList;

    /** Uniform loop count strategy. */
    private Text normalDistMeanText;

    /** Standard deviation. */
    private Text normalDistStdDeviation;

    /** Normal distribution destination combo. */
    private CCombo normalDistToActCombo;

    /** Normal distribution decision combo. */
    private CCombo normalDistDecisionActCombo;

    /** Elapsed Time Strategy. */
    private final TimeDisplayUnitType xpdlTimeUnit =
            DisplayTimeUnitConverter.DEFAULT_TIME_UNIT;

    /** Time unit combo. */
    private CCombo timeUnitCCombo;

    /** Max elapsed time. */
    private Text maxElapseTimeText;

    /** Max elapsed time destination. */
    private CCombo maxElapsedTimeToActCombo;

    /** Max elapsed time decision. */
    private CCombo maxElapsedTimeDecisionActCombo;

    /**
     * Constructor.
     */
    public ActivityLoopControlExistsSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());
        setMinimumHeight(MINIMUM_SECTION_HEIGHT);
        setShouldUseExtraSpace(true);
    }

    /**
     * @param parent
     *            The parent composite.
     * @param toolkit
     *            The toolkit.
     * @return The root control.
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doCreateControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {

        Composite baseComposite = toolkit.createComposite(parent);
        GridLayout baseGridLayout = new GridLayout();
        baseComposite.setLayout(baseGridLayout);

        Composite tableRoot = toolkit.createComposite(baseComposite);

        GridLayout layout = new GridLayout();
        tableRoot.setLayout(layout);

        FormText text = toolkit.createFormText(tableRoot, true);
        text.setText(PropertiesMessage.getString(FORM_TEXT_ID), true, false);

        GridData baseGridData = new GridData();
        baseGridData.horizontalAlignment = SWT.RIGHT;
        baseGridData.verticalAlignment = SWT.FILL;
        text.setLayoutData(baseGridData);
        manageControl(text);

        Composite root = toolkit.createComposite(baseComposite);
        GridLayout gridLayout = new GridLayout(2, false);
        root.setLayout(gridLayout);

        GridData rootGridData = new GridData(GridData.FILL_BOTH);
        root.setLayoutData(rootGridData);

        toolkit
                .createLabel(root,
                        PropertiesMessage
                                .getString("ActivityLoopControlExistsSection.Strategy"), SWT.NONE); //$NON-NLS-1$
        strategyCCombo = toolkit.createCCombo(root, SWT.READ_ONLY | SWT.FLAT);
        String[] strategyNames = new String[LoopControlStrategy.VALUES.size()];
        int i = 0;
        for (Iterator iter = LoopControlStrategy.VALUES.iterator(); iter
                .hasNext(); i++) {
            LoopControlStrategy controlStrategy =
                    ((LoopControlStrategy) iter.next());
            /*
             * strategyNames[i] = PropertiesMessage.getString(controlStrategy
             * .getName());
             */
            strategyNames[i] = plugIn.getString(controlStrategy.getName());
        }
        strategyCCombo.setItems(strategyNames);
        strategyCCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        strategyCCombo.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                int selectionIndex = strategyCCombo.getSelectionIndex();
                onStrategySelected(selectionIndex);
            }
        });
        // create a book
        book = toolkit.createPageBook(root, SWT.NONE);
        GridLayout bookGridLayout = new GridLayout();
        book.setLayout(bookGridLayout);

        GridData bookGridData = new GridData();
        bookGridData.grabExcessHorizontalSpace = true;
        bookGridData.grabExcessVerticalSpace = true;
        bookGridData.horizontalAlignment = SWT.FILL;
        bookGridData.verticalAlignment = SWT.FILL;
        bookGridData.horizontalSpan = 2;
        book.setLayoutData(bookGridData);

        // for Max Loop Count Strategy
        doCreateMaxLoopCountControls(toolkit);
        // *****for Uniform Distribution Strategy*****//
        doCreateNormalDistControls(toolkit);
        // *** Elapsed Time Strategy ***//
        doCreateMaxElapsedTimeControls(toolkit);
        return baseComposite;
    }

    /**
     * @param toolkit
     *            The ui toolkit.
     */
    private void doCreateMaxLoopCountControls(XpdFormToolkit toolkit) {
        Composite maxLoopPage = toolkit.createComposite(book.getContainer());
        maxLoopPage.setLayout(new GridLayout(2, false));
        toolkit.createLabel(maxLoopPage, PropertiesMessage
                .getString("ActivityLoopControlExistsSection.MaxLoopCount")); //$NON-NLS-1$
        maxLoopCountText = toolkit.createText(maxLoopPage, null);
        maxLoopCountText.setData(XpdFormToolkit.FEATURE_DATA,
                SimulationPackage.eINSTANCE
                        .getMaxLoopCountStrategyType_MaxLoopCount());
        maxLoopCountText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        manageControlUpdateOnDeactivate(maxLoopCountText);

        // Decision Activity
        toolkit
                .createLabel(maxLoopPage,
                        PropertiesMessage
                                .getString("ActivityLoopControlExistsSection.Decision"), SWT.NONE); //$NON-NLS-1$
        maxLoopDecisionActCombo =
                toolkit.createCCombo(maxLoopPage, SWT.READ_ONLY | SWT.FLAT);
        maxLoopDecisionActCombo.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL));
        maxLoopDecisionActCombo.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                int decisionActivityIndex =
                        maxLoopDecisionActCombo.getSelectionIndex();
                onDecisionActivityChanged(decisionActivityIndex);
            }
        });

        // to Activity
        toolkit
                .createLabel(maxLoopPage,
                        PropertiesMessage
                                .getString("ActivityLoopControlExistsSection.ToActivity"), SWT.NONE); //$NON-NLS-1$
        maxLoopToActCombo =
                toolkit.createCCombo(maxLoopPage, SWT.READ_ONLY | SWT.FLAT);
        maxLoopToActCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        maxLoopToActCombo.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                int toActivityIndex = maxLoopToActCombo.getSelectionIndex();
                onToActivityChanged(toActivityIndex);
            }
        });
        book.registerPage(LoopControlStrategy.MAX_LOOP_COUNT_LITERAL,
                maxLoopPage);

    }

    /**
     * @param toolkit
     *            The ui toolkit.
     */
    private void doCreateNormalDistControls(XpdFormToolkit toolkit) {
        Composite uniformDistPage =
                toolkit.createComposite(book.getContainer());
        uniformDistPage.setLayout(new GridLayout(2, false));
        // Min Loop Count Text
        toolkit.createLabel(uniformDistPage, PropertiesMessage
                .getString("ActivityLoopControlExistsSection.Mean")); //$NON-NLS-1$
        normalDistMeanText = toolkit.createText(uniformDistPage, null);
        normalDistMeanText.setData(XpdFormToolkit.FEATURE_DATA,
                SimulationPackage.eINSTANCE
                        .getNormalDistributionStrategyType_Mean());
        normalDistMeanText
                .setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        manageControlUpdateOnDeactivate(normalDistMeanText);
        // Max Loop Count Text
        toolkit
                .createLabel(uniformDistPage,
                        PropertiesMessage
                                .getString("ActivityLoopControlExistsSection.StandardDeviation")); //$NON-NLS-1$
        normalDistStdDeviation = toolkit.createText(uniformDistPage, null);
        normalDistStdDeviation.setData(XpdFormToolkit.FEATURE_DATA,
                SimulationPackage.eINSTANCE
                        .getNormalDistributionStrategyType_StandardDeviation());
        normalDistStdDeviation.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL));
        manageControlUpdateOnDeactivate(normalDistStdDeviation);
        // Decision Activity
        toolkit
                .createLabel(uniformDistPage,
                        PropertiesMessage
                                .getString("ActivityLoopControlExistsSection.Decision"), SWT.NONE); //$NON-NLS-1$
        normalDistDecisionActCombo =
                toolkit.createCCombo(uniformDistPage, SWT.READ_ONLY | SWT.FLAT);
        normalDistDecisionActCombo.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL));

        normalDistDecisionActCombo.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                int decisionActivityIndex =
                        normalDistDecisionActCombo.getSelectionIndex();
                onDecisionActivityChanged(decisionActivityIndex);
            }
        });

        // to Activity
        toolkit
                .createLabel(uniformDistPage,
                        PropertiesMessage
                                .getString("ActivityLoopControlExistsSection.ToActivity"), SWT.NONE); //$NON-NLS-1$
        normalDistToActCombo =
                toolkit.createCCombo(uniformDistPage, SWT.READ_ONLY | SWT.FLAT);
        normalDistToActCombo.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL));

        normalDistToActCombo.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                int toActivityIndex = normalDistToActCombo.getSelectionIndex();
                onToActivityChanged(toActivityIndex);
            }
        });
        book.registerPage(LoopControlStrategy.NORMAL_DISTRIBUTION_LITERAL,
                uniformDistPage);
    }

    /**
     * @param toolkit
     *            The ui toolkit.
     */
    private void doCreateMaxElapsedTimeControls(XpdFormToolkit toolkit) {
        Composite maxElapsedTimePage =
                toolkit.createComposite(book.getContainer());
        maxElapsedTimePage.setLayout(new GridLayout(2, false));
        toolkit
                .createLabel(maxElapsedTimePage,
                        PropertiesMessage
                                .getString("ActivityLoopControlExistsSection.TimeUnit"), SWT.NONE); //$NON-NLS-1$
        timeUnitCCombo =
                toolkit.createCCombo(maxElapsedTimePage, SWT.READ_ONLY
                        | SWT.FLAT);
        timeUnitCCombo.setData(XpdFormToolkit.FEATURE_DATA,
                SimulationPackage.eINSTANCE
                        .getMaxElapseTimeStrategyType_DisplayTimeUnit());
        String[] timeUnits = new String[TimeDisplayUnitType.VALUES.size()];
        int j = 0;
        for (Iterator iter = TimeDisplayUnitType.VALUES.iterator(); iter
                .hasNext(); j++) {
            timeUnits[j] = ((TimeDisplayUnitType) iter.next()).getName();
        }
        timeUnitCCombo.setItems(timeUnits);
        timeUnitCCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        timeUnitCCombo.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                TimeDisplayUnitType selectedTimeUnit =
                        TimeDisplayUnitType.get(timeUnitCCombo.getText());
                onTimeUnitSelected(selectedTimeUnit);
            }
        });
        toolkit.createLabel(maxElapsedTimePage, PropertiesMessage
                .getString("ActivityLoopControlExistsSection.Value")); //$NON-NLS-1$
        maxElapseTimeText = toolkit.createText(maxElapsedTimePage, null);
        maxElapseTimeText.setData(XpdFormToolkit.FEATURE_DATA,
                SimulationPackage.eINSTANCE
                        .getMaxElapseTimeStrategyType_MaxElapseTime());
        maxElapseTimeText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        manageControlUpdateOnDeactivate(maxElapseTimeText);
        // Decision Activity
        toolkit
                .createLabel(maxElapsedTimePage,
                        PropertiesMessage
                                .getString("ActivityLoopControlExistsSection.Decision"), SWT.NONE); //$NON-NLS-1$
        maxElapsedTimeDecisionActCombo =
                toolkit.createCCombo(maxElapsedTimePage, SWT.READ_ONLY
                        | SWT.FLAT);
        maxElapsedTimeDecisionActCombo.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL));
        maxElapsedTimeDecisionActCombo
                .addSelectionListener(new SelectionAdapter() {
                    @Override
                    public void widgetSelected(SelectionEvent e) {
                        int decisionActivityIndex =
                                maxElapsedTimeDecisionActCombo
                                        .getSelectionIndex();
                        onDecisionActivityChanged(decisionActivityIndex);
                    }
                });

        // to Activity
        toolkit
                .createLabel(maxElapsedTimePage,
                        PropertiesMessage
                                .getString("ActivityLoopControlExistsSection.ToActivity"), SWT.NONE); //$NON-NLS-1$
        maxElapsedTimeToActCombo =
                toolkit.createCCombo(maxElapsedTimePage, SWT.READ_ONLY
                        | SWT.FLAT);
        maxElapsedTimeToActCombo.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL));
        maxElapsedTimeToActCombo.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                int toActivityIndex =
                        maxElapsedTimeToActCombo.getSelectionIndex();
                onToActivityChanged(toActivityIndex);
            }
        });
        book.registerPage(LoopControlStrategy.MAX_ELAPSE_TIME_LITERAL,
                maxElapsedTimePage);
    }

    /**
     * @param selectedTimeUnit
     *            The time unit selected.
     */
    private void onTimeUnitSelected(TimeDisplayUnitType selectedTimeUnit) {
        Activity activity = (Activity) getInput();
        EditingDomain ed = getEditingDomain(activity);
        ActivitySimulationDataType activitySimData =
                getActivitySimulationData(activity);
        LoopControlType loopControl = activitySimData.getLoopControl();
        MaxElapseTimeStrategyType maxElapseTimeStrategy =
                loopControl.getMaxElapseTimeStrategy();
        CompoundCommand compoundCmd =
                new CompoundCommand(
                        PropertiesMessage
                                .getString("ActivityLoopControlExistsSection.ChangeTimeUnit"), //$NON-NLS-1$
                        PropertiesMessage
                                .getString("ActivityLoopControlExistsSection.ChangeTimeUnit")); //$NON-NLS-1$
        Command cmd =
                SetCommand
                        .create(ed,
                                maxElapseTimeStrategy,
                                timeUnitCCombo
                                        .getData(XpdFormToolkit.FEATURE_DATA),
                                selectedTimeUnit);
        compoundCmd.append(cmd);
        ed.getCommandStack().execute(compoundCmd);
        refresh();
    }

    /**
     * @param toActivityIndex
     *            The activity selected.
     */
    private void onToActivityChanged(int toActivityIndex) {
        Activity activity = (Activity) getInput();
        EditingDomain ed = getEditingDomain(activity);
        ActivitySimulationDataType activitySimData =
                getActivitySimulationData(activity);
        LoopControlType loopControlType = activitySimData.getLoopControl();
        Activity toActivity = (Activity) toActivityList.get(toActivityIndex);
        MaxLoopCountStrategyType maxLoopCountStrategy =
                loopControlType.getMaxLoopCountStrategy();
        Object parentObject = null;
        if (maxLoopCountStrategy != null) {
            parentObject = maxLoopCountStrategy;
        }
        NormalDistributionStrategyType uniformDistributionStrategy =
                loopControlType.getNormalDistributionStrategy();
        if (uniformDistributionStrategy != null) {
            parentObject = uniformDistributionStrategy;
        }
        MaxElapseTimeStrategyType maxElapseTimeStrategy =
                loopControlType.getMaxElapseTimeStrategy();
        if (maxElapseTimeStrategy != null) {
            parentObject = maxElapseTimeStrategy;
        }

        if (parentObject != null) {
            Command cmd = null;
            cmd =
                    SetCommand.create(ed,
                            parentObject,
                            SimulationPackage.eINSTANCE
                                    .getLoopControlTransitionType_ToActivity(),
                            toActivity.getId());
            CompoundCommand compoundCmd =
                    new CompoundCommand(
                            PropertiesMessage
                                    .getString("ActivityLoopControlExistsSection.SetToActivity"), PropertiesMessage.getString("ActivityLoopControlExistsSection.SetLoopActivity")); //$NON-NLS-1$ //$NON-NLS-2$
            compoundCmd.append(cmd);
            ed.getCommandStack().execute(compoundCmd);
        }
    }

    /**
     * @param decisionActivityIndex
     *            The decision activity selected.
     */
    private void onDecisionActivityChanged(int decisionActivityIndex) {
        Activity activity = (Activity) getInput();
        EditingDomain ed = getEditingDomain(activity);
        ActivitySimulationDataType activitySimData =
                getActivitySimulationData(activity);
        LoopControlType loopControlType = activitySimData.getLoopControl();
        Activity activityWithName =
                (Activity) decisionActivityList.get(decisionActivityIndex);
        Object parentObject = null;
        MaxLoopCountStrategyType maxLoopCountStrategy =
                loopControlType.getMaxLoopCountStrategy();
        if (maxLoopCountStrategy != null) {
            parentObject = maxLoopCountStrategy;
        }
        NormalDistributionStrategyType normalDistributionStrategy =
                loopControlType.getNormalDistributionStrategy();
        if (normalDistributionStrategy != null) {
            parentObject = normalDistributionStrategy;
        }
        MaxElapseTimeStrategyType maxElapseTimeStrategy =
                loopControlType.getMaxElapseTimeStrategy();
        if (maxElapseTimeStrategy != null) {
            parentObject = maxElapseTimeStrategy;
        }
        if (parentObject != null) {
            EStructuralFeature daFeature =
                    SimulationPackage.eINSTANCE
                            .getLoopControlTransitionType_DecisionActivity();
            Command cmd = null;
            cmd =
                    SetCommand.create(ed,
                            parentObject,
                            daFeature,
                            activityWithName.getId());
            CompoundCommand compoundCmd =
                    new CompoundCommand(
                            PropertiesMessage
                                    .getString("ActivityLoopControlExistsSection.SetDecision"), //$NON-NLS-1$
                            PropertiesMessage
                                    .getString("ActivityLoopControlExistsSection.SetLoopDecision")); //$NON-NLS-1$
            compoundCmd.append(cmd);
            ed.getCommandStack().execute(compoundCmd);
            doRefresh();
        }
    }

    /**
     * Returns a list of Actvities which follow the passed activity.
     * 
     * @param fromActivity
     *            The source activity.
     * @return a list, each element an instance of Actvity
     */
    private List getToActivities(Activity fromActivity) {
        if (fromActivity == null) {
            return Collections.EMPTY_LIST;
        }
        Process process = (Process) fromActivity.eContainer();
        EList transitions = process.getTransitions();
        List tranList = getTransitions(transitions, fromActivity);
        ArrayList<Activity> toActivityList = new ArrayList<Activity>();
        for (Iterator iter = tranList.iterator(); iter.hasNext();) {
            Transition tran = (Transition) iter.next();
            String toActivityId = tran.getTo();
            Activity toActivity =
                    SimulationXpdlUtils.getActivity(process, toActivityId);
            toActivityList.add(toActivity);

        }
        return toActivityList;
    }

    /**
     * This method will return all the Activities which have more than 1
     * outgoing transitions from the activity.
     * 
     * @return a list which contains Activity as elements
     */
    private List getDecisionActivities() {
        Activity activity = (Activity) getInput();
        if (activity == null) {
            return Collections.EMPTY_LIST;
        }
        Process process = (Process) activity.eContainer();
        EList transitions = process.getTransitions();
        EList activitiyList = process.getActivities();
        ArrayList<Activity> toReturn = new ArrayList<Activity>();
        for (Iterator iter = activitiyList.iterator(); iter.hasNext();) {
            Activity eachActivity = (Activity) iter.next();
            List tranList = getTransitions(transitions, eachActivity);
            if (tranList.size() > 1) {
                Route route = eachActivity.getRoute();
                if (route != null
                        && JoinSplitType.EXCLUSIVE_LITERAL
                                .equals(Xpdl2ModelUtil
                                        .safeGetGatewayType(eachActivity))) {
                    toReturn.add(eachActivity);
                }
            }
        }
        return toReturn;
    }

    /**
     * This method will be called when the user changes value of the UI elements
     * in the property page.
     * 
     * @param uiObject
     *            The object to get the command for.
     * @return The command.
     */
    @Override
    public Command doGetCommand(Object uiObject) {
        Activity activity = (Activity) getInput();
        ActivitySimulationDataType actSimData =
                getActivitySimulationData(activity);
        if (actSimData == null) {
            return null;
        }
        LoopControlType loopControl = actSimData.getLoopControl();
        if (loopControl == null) {
            return null;
        }
        if ("this".equals(uiObject)) { //$NON-NLS-1$
            return createRemoveStrategyCmd();
        }
        int selectionIndex = strategyCCombo.getSelectionIndex();
        LoopControlStrategy loopStrategy =
                LoopControlStrategy.get(selectionIndex);
        if (loopStrategy == LoopControlStrategy.MAX_LOOP_COUNT_LITERAL) {
            MaxLoopCountStrategyType maxLoopCountStrategy =
                    loopControl.getMaxLoopCountStrategy();
            EStructuralFeature feat =
                    (EStructuralFeature) ((Widget) uiObject)
                            .getData(XpdFormToolkit.FEATURE_DATA);
            if (feat == SimulationPackage.eINSTANCE
                    .getMaxLoopCountStrategyType_MaxLoopCount()) {
                String loopCountValue = ((Text) uiObject).getText();
                Command cmd;
                try {
                    Integer loopCountValueInt = Integer.valueOf(loopCountValue);
                    cmd =
                            SetCommand.create(getEditingDomain(),
                                    maxLoopCountStrategy,
                                    feat,
                                    loopCountValueInt);
                    return cmd;
                } catch (NumberFormatException e) {
                    doRefresh();
                    return null;
                }
            }
        } else if (loopStrategy == LoopControlStrategy.NORMAL_DISTRIBUTION_LITERAL) {
            NormalDistributionStrategyType normalDistributionStrategy =
                    loopControl.getNormalDistributionStrategy();
            EStructuralFeature feat =
                    (EStructuralFeature) ((Widget) uiObject)
                            .getData(XpdFormToolkit.FEATURE_DATA);
            try {
                if (feat == SimulationPackage.eINSTANCE
                        .getNormalDistributionStrategyType_Mean()) {
                    String meanValue = ((Text) uiObject).getText();
                    Command cmd =
                            SetCommand.create(getEditingDomain(),
                                    normalDistributionStrategy,
                                    feat,
                                    Double.valueOf(meanValue));
                    return cmd;
                } else if (feat == SimulationPackage.eINSTANCE
                        .getNormalDistributionStrategyType_StandardDeviation()) {
                    String stdDeviationValue = ((Text) uiObject).getText();
                    Command cmd =
                            SetCommand.create(getEditingDomain(),
                                    normalDistributionStrategy,
                                    feat,
                                    Double.valueOf(stdDeviationValue));
                    return cmd;
                }
            } catch (NumberFormatException e) {
                doRefresh();
                return null;
            }
        } else if (loopStrategy == LoopControlStrategy.MAX_ELAPSE_TIME_LITERAL) {
            MaxElapseTimeStrategyType maxElapseTimeStrategy =
                    loopControl.getMaxElapseTimeStrategy();
            EStructuralFeature feat =
                    (EStructuralFeature) ((Widget) uiObject)
                            .getData(XpdFormToolkit.FEATURE_DATA);
            if (feat == SimulationPackage.eINSTANCE
                    .getMaxElapseTimeStrategyType_MaxElapseTime()) {
                TimeDisplayUnitType currentTimeUnit =
                        maxElapseTimeStrategy.getDisplayTimeUnit();
                String maxElapseTimeValue = ((Text) uiObject).getText();
                try {
                    Double doubleMaxElapseTime =
                            DisplayTimeUnitConverter
                                    .convertToDouble(currentTimeUnit,
                                            xpdlTimeUnit,
                                            Double.valueOf(maxElapseTimeValue)
                                                    .toString());
                    Command cmd =
                            SetCommand.create(getEditingDomain(),
                                    maxElapseTimeStrategy,
                                    feat,
                                    doubleMaxElapseTime);
                    return cmd;
                } catch (NumberFormatException e) {
                    doRefresh();
                    return null;
                }
            }
        }
        return null;
    }

    /**
     * This method is called when the input to the property page changes and it
     * should update values of UI components with the new input.
     */
    @Override
    protected void doRefresh() {
        Activity activity = (Activity) getInput();
        if (activity == null) {
            return;
        }
        ActivitySimulationDataType actSimData =
                getActivitySimulationData(activity);
        if (actSimData == null) {
            return;
        }
        LoopControlType loopControl = actSimData.getLoopControl();
        if (loopControl == null) {
            return;
        }
        Process process = (Process) activity.eContainer();
        MaxLoopCountStrategyType maxLoopCountStrategy =
                loopControl.getMaxLoopCountStrategy();
        if (maxLoopCountStrategy != null) {
            updateMaxLoopUIControls(maxLoopCountStrategy, process);
            return;
        }
        NormalDistributionStrategyType normalDistributionStrategy =
                loopControl.getNormalDistributionStrategy();
        if (normalDistributionStrategy != null) {
            updateNormalDistUIControls(normalDistributionStrategy, process);
            return;
        }
        MaxElapseTimeStrategyType maxElapseTimeStrategy =
                loopControl.getMaxElapseTimeStrategy();
        if (maxElapseTimeStrategy != null) {
            updateMaxElapsedTimeUIControls(maxElapseTimeStrategy, process);
            return;
        }

    }

    /**
     * @param normalDistributionStrategy
     *            strategy.
     * @param process
     *            The containing process.
     */
    private void updateNormalDistUIControls(
            NormalDistributionStrategyType normalDistributionStrategy,
            Process process) {
        book.showPage(LoopControlStrategy.NORMAL_DISTRIBUTION_LITERAL);
        updateCCombo(strategyCCombo, plugIn
                .getString(LoopControlStrategy.NORMAL_DISTRIBUTION_LITERAL
                        .getName()));
        double meanValue = normalDistributionStrategy.getMean();
        updateText(normalDistMeanText, Double.toString(meanValue));
        double stdDeviation = normalDistributionStrategy.getStandardDeviation();
        updateText(normalDistStdDeviation, Double.toString(stdDeviation));
        decisionActivityList = getDecisionActivities();
        String[] decisionActivityNames = getActivityNames(decisionActivityList);
        normalDistDecisionActCombo.setItems(decisionActivityNames);
        String fromActivityId =
                normalDistributionStrategy.getDecisionActivity();
        Activity fromActivity =
                SimulationXpdlUtils.getActivity(process, fromActivityId);

        toActivityList = new ArrayList();
        if (fromActivity != null) {
            updateCCombo(normalDistDecisionActCombo, fromActivity.getName());
            toActivityList = getToActivities(fromActivity);
            normalDistToActCombo.setEnabled(true);
        } else {
            normalDistToActCombo.setEnabled(false);
        }
        String[] toActivityNames = getActivityNames(toActivityList);
        normalDistToActCombo.setItems(toActivityNames);
        String toActivityId = normalDistributionStrategy.getToActivity();
        if (toActivityId != null) {
            Activity toActivityWithId =
                    SimulationXpdlUtils.getActivity(process, toActivityId);
            updateCCombo(normalDistToActCombo, toActivityWithId.getName());
        }
    }

    /**
     * @param maxElapseTimeStrategy
     *            strategy.
     * @param process
     *            The containing process.
     */
    private void updateMaxElapsedTimeUIControls(
            MaxElapseTimeStrategyType maxElapseTimeStrategy, Process process) {
        book.showPage(LoopControlStrategy.MAX_ELAPSE_TIME_LITERAL);
        updateCCombo(strategyCCombo, plugIn
                .getString(LoopControlStrategy.MAX_ELAPSE_TIME_LITERAL
                        .getName()));

        TimeDisplayUnitType displayTimeUnit =
                maxElapseTimeStrategy.getDisplayTimeUnit();
        updateCCombo(timeUnitCCombo, displayTimeUnit.getName());

        double maxElapseTime = maxElapseTimeStrategy.getMaxElapseTime();
        String strMaxElapseTimeValue =
                DisplayTimeUnitConverter.convertToString(xpdlTimeUnit,
                        displayTimeUnit,
                        maxElapseTime);
        updateText(maxElapseTimeText, strMaxElapseTimeValue);

        decisionActivityList = getDecisionActivities();
        String[] decisionActivityNames = getActivityNames(decisionActivityList);
        maxElapsedTimeDecisionActCombo.setItems(decisionActivityNames);
        String fromActivityId = maxElapseTimeStrategy.getDecisionActivity();
        Activity fromActivity =
                SimulationXpdlUtils.getActivity(process, fromActivityId);
        toActivityList = new ArrayList();
        if (fromActivity != null) {
            updateCCombo(maxElapsedTimeDecisionActCombo, fromActivity.getName());
            toActivityList = getToActivities(fromActivity);
            maxElapsedTimeToActCombo.setEnabled(true);
        } else {
            maxElapsedTimeToActCombo.setEnabled(false);
        }
        String[] toActivityNames = getActivityNames(toActivityList);
        maxElapsedTimeToActCombo.setItems(toActivityNames);
        String toActivityId = maxElapseTimeStrategy.getToActivity();
        if (toActivityId != null) {
            Activity toActivityWithId =
                    SimulationXpdlUtils.getActivity(process, toActivityId);
            updateCCombo(maxElapsedTimeToActCombo, toActivityWithId.getName());
        }
    }

    /**
     * This method updates the ui controls with the values of the input.
     * 
     * @param maxLoopCountStrategy
     *            strategy.
     * @param process
     *            The containing process.
     */
    private void updateMaxLoopUIControls(
            MaxLoopCountStrategyType maxLoopCountStrategy, Process process) {
        book.showPage(LoopControlStrategy.MAX_LOOP_COUNT_LITERAL);
        updateCCombo(strategyCCombo,
                plugIn.getString(LoopControlStrategy.MAX_LOOP_COUNT_LITERAL
                        .getName()));
        int maxLoopCount = maxLoopCountStrategy.getMaxLoopCount();
        updateText(maxLoopCountText, Integer.toString(maxLoopCount));
        decisionActivityList = getDecisionActivities();
        String[] decisionActivityNames = getActivityNames(decisionActivityList);
        maxLoopDecisionActCombo.setItems(decisionActivityNames);
        String fromActivityId = maxLoopCountStrategy.getDecisionActivity();
        Activity fromActivity =
                SimulationXpdlUtils.getActivity(process, fromActivityId);
        toActivityList = new ArrayList();
        if (fromActivity != null) {
            updateCCombo(maxLoopDecisionActCombo, fromActivity.getName());
            toActivityList = getToActivities(fromActivity);
            maxLoopToActCombo.setEnabled(true);
        } else {
            maxLoopToActCombo.setEnabled(false);
        }
        String[] toActivityNames = getActivityNames(toActivityList);
        maxLoopToActCombo.setItems(toActivityNames);
        String toActivityId = maxLoopCountStrategy.getToActivity();
        if (toActivityId != null) {
            Activity toActivityWithId =
                    SimulationXpdlUtils.getActivity(process, toActivityId);
            updateCCombo(maxLoopToActCombo, toActivityWithId.getName());
        }

    }

    /**
     * Returns an array of activity names.
     * 
     * @param activityList
     *            The list of activities.
     * @return The array of activity names.
     */
    private String[] getActivityNames(List activityList) {
        String[] activityNames = new String[activityList.size()];
        int index = 0;
        for (Iterator iter = activityList.iterator(); iter.hasNext(); index++) {
            Activity element = (Activity) iter.next();
            String activityName = element.getName();
            if (activityName == null) {
                activityName = ""; //$NON-NLS-1$
            }
            activityNames[index] = activityName;
        }
        return activityNames;
    }

    /**
     * @param selectionIndex
     *            The selection index.
     */
    private void onStrategySelected(int selectionIndex) {
        Activity activity = (Activity) getInput();
        EditingDomain ed = getEditingDomain(activity);
        ActivitySimulationDataType activitySimData =
                getActivitySimulationData(activity);
        LoopControlType loopControlType = activitySimData.getLoopControl();
        if (loopControlType == null) {
            return;
        }
        CompoundCommand compoundCmd =
                new CompoundCommand(
                        PropertiesMessage
                                .getString("ActivityLoopControlExistsSection.CreateLoop"), //$NON-NLS-1$
                        PropertiesMessage
                                .getString("ActivityLoopControlExistsSection.CreateLoopDetail")); //$NON-NLS-1$
        clearLoopControlStrategy(ed, compoundCmd, loopControlType);
        Command setCommand = null;
        LoopControlStrategy strategy = LoopControlStrategy.get(selectionIndex);
        if (strategy == LoopControlStrategy.MAX_LOOP_COUNT_LITERAL) {
            book.showPage(LoopControlStrategy.MAX_LOOP_COUNT_LITERAL);
            MaxLoopCountStrategyType maxLoopCountStrategy =
                    SimulationFactory.eINSTANCE
                            .createMaxLoopCountStrategyType();
            setCommand =
                    SetCommand.create(ed,
                            loopControlType,
                            SimulationPackage.eINSTANCE
                                    .getLoopControlType_MaxLoopCountStrategy(),
                            maxLoopCountStrategy);
            compoundCmd.append(setCommand);
        } else if (strategy == LoopControlStrategy.NORMAL_DISTRIBUTION_LITERAL) {
            book.showPage(LoopControlStrategy.NORMAL_DISTRIBUTION_LITERAL);
            NormalDistributionStrategyType normalDistStrategy =
                    SimulationFactory.eINSTANCE
                            .createNormalDistributionStrategyType();
            EStructuralFeature ndsFeature =
                    SimulationPackage.eINSTANCE
                            .getLoopControlType_NormalDistributionStrategy();
            setCommand =
                    SetCommand.create(ed,
                            loopControlType,
                            ndsFeature,
                            normalDistStrategy);
            compoundCmd.append(setCommand);
        } else if (strategy == LoopControlStrategy.MAX_ELAPSE_TIME_LITERAL) {
            book.showPage(LoopControlStrategy.MAX_ELAPSE_TIME_LITERAL);
            MaxElapseTimeStrategyType maxElapsedTimetype =
                    SimulationFactory.eINSTANCE
                            .createMaxElapseTimeStrategyType();
            maxElapsedTimetype.setDisplayTimeUnit(DEFAULT_TIME_UNIT);
            setCommand =
                    SetCommand
                            .create(ed,
                                    loopControlType,
                                    SimulationPackage.eINSTANCE
                                            .getLoopControlType_MaxElapseTimeStrategy(),
                                    maxElapsedTimetype);
            compoundCmd.append(setCommand);
        }
        if (compoundCmd.getCommandList().size() > 0) {
            ed.getCommandStack().execute(compoundCmd);
            doRefresh();
        }
    }

    /**
     * This method clears the existing set strategies on loopControl.
     * 
     * @param ed
     *            The editing comain.
     * @param compoundCmd
     *            The command to add to.
     * @param loopControlType
     *            The loop control type to clear.
     */
    private void clearLoopControlStrategy(EditingDomain ed,
            CompoundCommand compoundCmd, LoopControlType loopControlType) {
        MaxLoopCountStrategyType maxLoopCountStrategy =
                loopControlType.getMaxLoopCountStrategy();
        if (maxLoopCountStrategy != null) {
            Command cmd =
                    SetCommand.create(ed,
                            loopControlType,
                            SimulationPackage.eINSTANCE
                                    .getLoopControlType_MaxLoopCountStrategy(),
                            null);
            compoundCmd.append(cmd);
        }
        NormalDistributionStrategyType normalDistributionStrategy =
                loopControlType.getNormalDistributionStrategy();
        if (normalDistributionStrategy != null) {
            EStructuralFeature ndsFeature =
                    SimulationPackage.eINSTANCE
                            .getLoopControlType_NormalDistributionStrategy();
            Command cmd =
                    SetCommand.create(ed, loopControlType, ndsFeature, null);
            compoundCmd.append(cmd);
        }
        MaxElapseTimeStrategyType maxElapseTimeStrategy =
                loopControlType.getMaxElapseTimeStrategy();
        if (maxElapseTimeStrategy != null) {
            Command cmd =
                    SetCommand
                            .create(ed,
                                    loopControlType,
                                    SimulationPackage.eINSTANCE
                                            .getLoopControlType_MaxElapseTimeStrategy(),
                                    null);
            compoundCmd.append(cmd);
        }

    }

    /**
     * @param object
     *            The input object.
     * @return true if this section should display.
     * @see com.tibco.xpd.ui.properties.AbstractEObjectSection#select(java.lang.Object)
     */
    @Override
    public boolean select(Object object) {
        EObject eo = getBaseSelectObject(object);
        if (eo instanceof Activity) {
            Activity activity = (Activity) eo;
            Process proc = activity.getProcess();
            if (proc != null) {
                if (DestinationUtil.isValidationDestinationEnabled(proc,
                        SimulationConstants.SIMULATION_DEST_ENV_1_2_ID)
                        && loopControlStrategyExist(activity)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param act
     *            The activity.
     * @return true if the activity has a loop control strategy.
     */
    private boolean loopControlStrategyExist(EObject act) {
        ActivitySimulationDataType simData =
                SimulationXpdlUtils.getActivitySimulationData((Activity) act);
        if (simData == null) {
            return false;
        }
        LoopControlType loopControl = simData.getLoopControl();
        return loopControl != null;
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
        ActivitySimulationDataType activitySimulationData =
                SimulationXpdlUtils.getActivitySimulationData(activity);
        return activitySimulationData;

    }

    /**
     * @return The remove strategy command.
     */
    protected Command createRemoveStrategyCmd() {
        Activity activity = (Activity) getInput();
        CompoundCommand compoundCmd =
                new CompoundCommand(PropertiesMessage
                        .getString("ActivityLoopControlExistsSection.Remove")); //$NON-NLS-1$
        ActivitySimulationDataType activitySimData =
                getActivitySimulationData(activity);
        LoopControlType loopControlType = activitySimData.getLoopControl();
        if (loopControlType == null) {
            return compoundCmd;
        }
        Command command =
                SetCommand.create(getEditingDomain(),
                        activitySimData,
                        SimulationPackage.eINSTANCE
                                .getActivitySimulationDataType_LoopControl(),
                        null);
        compoundCmd.append(command);
        return compoundCmd;
    }

    /**
     * Return a list with Transition objects for this activity.
     * 
     * @param transitions
     *            A list of transitions.
     * @param activity
     *            The activity to get transitions for.
     * @return The tranisition list filtered for the given activity.
     */
    private List getTransitions(EList transitions, Activity activity) {
        List<Transition> toReturn = new ArrayList<Transition>();
        String actId = activity.getId();
        for (Iterator i = transitions.iterator(); i.hasNext();) {
            Transition transition = (Transition) i.next();
            String fromId = transition.getFrom();
            if (fromId.equals(actId)) {
                toReturn.add(transition);
            }
        }
        return toReturn;
    }

    @Override
    protected boolean shouldRefresh(List<Notification> notifications) {
        for (Notification n : notifications) {
            if (n.getNotifier() instanceof ActivitySimulationDataType) {
                if (n.getEventType() == Notification.SET) {
                    int id = n.getFeatureID(ActivitySimulationDataType.class);
                    if (id == SimulationPackage.ACTIVITY_SIMULATION_DATA_TYPE__LOOP_CONTROL) {
                        if (n.getNewValue() == null) {
                            refreshTabs();
                            return false;
                        }
                    }
                }
            }
        }
        return super.shouldRefresh(notifications);
    }

    /**
     * This is the hook where in we can validate values enetered by the user.
     * 
     * @param e
     *            Verification event.
     */
    public void verifyText(Event e) {
        Text textControl = ((Text) e.widget);
        String t =
                textControl.getText(0, e.start - 1)
                        + e.text
                        + textControl.getText(e.end,
                                textControl.getCharCount() - 1);

        if ("".equals(t)) { //$NON-NLS-1$
            e.doit = true;
            return;
        }
        Activity activity = (Activity) getInput();
        ActivitySimulationDataType simData =
                SimulationXpdlUtils.getActivitySimulationData(activity);
        LoopControlType loopControl = simData.getLoopControl();
        MaxLoopCountStrategyType maxLoopCountStrategy =
                loopControl.getMaxLoopCountStrategy();
        if (maxLoopCountStrategy != null) {
            if (textControl == maxLoopCountText) {
                e.doit =
                        canSetAttribute(maxLoopCountStrategy, textControl
                                .getData(XpdFormToolkit.FEATURE_DATA), t);
                if (e.doit) {
                    e.doit = !isNegativeValue(t);
                }
                return;
            }
        }
        NormalDistributionStrategyType normalDistributionStrategy =
                loopControl.getNormalDistributionStrategy();
        if (normalDistributionStrategy != null) {
            e.doit =
                    canSetAttribute(normalDistributionStrategy, textControl
                            .getData(XpdFormToolkit.FEATURE_DATA), t);
            if (e.doit) {
                e.doit = !isNegativeDoubleValue(t);
            }
            return;
        }
        MaxElapseTimeStrategyType maxElapseTimeStrategy =
                loopControl.getMaxElapseTimeStrategy();
        if (maxElapseTimeStrategy != null) {
            e.doit =
                    canSetAttribute(maxElapseTimeStrategy, textControl
                            .getData(XpdFormToolkit.FEATURE_DATA), t);
            if (e.doit) {
                e.doit = !isNegativeDoubleValue(t);
            }
            return;
        }
    }

    /**
     * @param str
     *            The string to check.
     * @return true if it represents a negative integer.
     */
    private boolean isNegativeValue(String str) {
        int intValue = Integer.parseInt(str);
        if (intValue < 0) {
            return true;
        }
        return false;
    }

    /**
     * @param str
     *            The string to check.
     * @return true if it represents a negative double.
     */
    private boolean isNegativeDoubleValue(String str) {
        double dValue = Double.parseDouble(str);
        if (dValue < 0) {
            return true;
        }
        return false;
    }

    /**
     * @param owner
     *            The owning object.
     * @param feature
     *            The feature to check.
     * @param newValue
     *            The value to which the feature will be set.
     * @return true if the value can be set.
     */
    public boolean canSetAttribute(EObject owner, Object feature,
            String newValue) {
        if (owner != null && feature != null) {
            EAttribute ref = (EAttribute) feature;
            EClassifier type = ref.getEType();
            if (type instanceof EDataType) {
                Object qw = null;
                try {
                    qw =
                            XMLTypeFactory.eINSTANCE
                                    .createFromString((EDataType) type,
                                            newValue);
                } catch (RuntimeException e) {
                    return false;
                }
                EditingDomain ed = getEditingDomain(owner);
                return SetCommand.create(ed, owner, feature, qw).canExecute();
            }
            return false;
        } else {
            return false;
        }
    }

}
