/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.ui.properties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.xml.type.XMLTypeFactory;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ResourceSelectionDialog;
import org.eclipse.ui.forms.widgets.ScrolledPageBook;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;

import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.simulation.AbstractBasicDistribution;
import com.tibco.xpd.simulation.ConstantRealDistribution;
import com.tibco.xpd.simulation.ExponentialRealDistribution;
import com.tibco.xpd.simulation.ExternalEmpiricalDistribution;
import com.tibco.xpd.simulation.NormalRealDistribution;
import com.tibco.xpd.simulation.ParticipantSimulationDataType;
import com.tibco.xpd.simulation.RealDistributionCategory;
import com.tibco.xpd.simulation.SimulationConstants;
import com.tibco.xpd.simulation.SimulationFactory;
import com.tibco.xpd.simulation.SimulationPackage;
import com.tibco.xpd.simulation.StartSimulationDataType;
import com.tibco.xpd.simulation.TimeDisplayUnitType;
import com.tibco.xpd.simulation.UniformRealDistribution;
import com.tibco.xpd.simulation.common.util.DisplayTimeUnitConverter;
import com.tibco.xpd.simulation.common.util.SimulationXpdlUtils;
import com.tibco.xpd.simulation.ui.provider.SimulationDurationTypeLabelProvider;
import com.tibco.xpd.simulation.ui.provider.SimulationTimeUnitLabelProvider;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.TextFieldVerifier;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * Start event simulation data section.
 */
public class StartEventSimulationExistSection extends
        AbstractFilteredTransactionalSection implements TextFieldVerifier {

    /** Empirical data page column count. */
    private static final int EMPIRICAL_COLUMNS = 3;

    /** Minimum section height. */
    private static final int SECTION_HEIGHT = 160;

    /** The activity cost display format. */
    private static final String ACTIVITY_COST_FORMAT = "#0.####"; //$NON-NLS-1$

    /** Feature ID. */
    private static final String FEATURE = "FEATURE"; //$NON-NLS-1$

    /** Default case count. */
    private static final String NUMBER_OF_CASES_DEFAULT = "1"; //$NON-NLS-1$

    /** Constant distribution default value. */
    private static final String CONSTANT_DIST_VALUE_DEFAULT = "5.0"; //$NON-NLS-1$

    /** Uniform distribution default minimum value. */
    private static final String UNIFORM_DIST_MIN_VALUE_DEFAULT = "2.0"; //$NON-NLS-1$

    /** Uniform distribution default maximum value. */
    private static final String UNIFORM_DIST_MAX_VALUE_DEFAULT = "5.0"; //$NON-NLS-1$

    /** Normal distribution default mean value. */
    private static final String NORMAL_DIST_MEAN_DEFAULT = "5.0"; //$NON-NLS-1$

    /** Normal distribution default standard deviation. */
    private static final String NORMAL_DIST_STANDARD_DEVIATION_DEFAULT = "2.0"; //$NON-NLS-1$

    /** Exponential distribution default mean value. */
    private static final String EXPONENTIAL_DIST_MEAN_DEFAULT = "5.0"; //$NON-NLS-1$

    /** Real start data ID. */
    private static final String REAL_START_DATA_SCENARIO =
            "RealStartDateScenario"; //$NON-NLS-1$

    /** The page book. */
    private ScrolledPageBook book;

    /** Default time unit. */
    private final TimeDisplayUnitType xpdlTimeUnit =
            DisplayTimeUnitConverter.DEFAULT_TIME_UNIT;

    /** Duration combo. */
    private CCombo durationTypeCCombo;

    /** Time unit combo. */
    private CCombo timeUnitCCombo;

    /** Number of cases. */
    private Text numberOfCasesText;

    /** Interval group. */
    private Group intervalGroup;

    /** Constant distribution value. */
    private Text constantValueText;

    /** Uniform distribution minimum value. */
    private Text uniformMinValueText;

    /** Uniform distribution maximum value. */
    private Text uniformMaxValueText;

    /** Normal distribution mean value. */
    private Text normalMeanText;

    /** Normal distribution standard deviation. */
    private Text normalStandardDeviationText;

    /** Exponential distribution mean value. */
    private Text exponentialMeanText;

    /** Constant distribution cost label. */
    private Label constantCostLabel;

    /** Uniform distribution cost label. */
    private Label uniformCostLabel;

    /** Normal distribution cost label. */
    private Label normalCostLabel;

    /** Exponential distribution cost label. */
    private Label exponentialCostLabel;

    /** Empirical data reference text. */
    private Text empiricalReferenceText;

    /** Combo viewer for duration types - used for displaying globalised values */
    private ComboViewer durationTypeComboViewer;

    /** Combo viewer for time units - used for displaying globalised values */
    private ComboViewer timeUnitComboViewer;

    /**
     * Constructor.
     */
    public StartEventSimulationExistSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());
        setShouldUseExtraSpace(false);
        setMinimumHeight(SECTION_HEIGHT);
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
    public Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = toolkit.createComposite(parent);
        GridLayout gridLayout = new GridLayout(2, false);
        root.setLayout(gridLayout);

        toolkit.createLabel(root, PropertiesMessage
                .getString("StartEventSimulationExistSection.NumberOfCases")); //$NON-NLS-1$
        numberOfCasesText = toolkit.createText(root, NUMBER_OF_CASES_DEFAULT);
        numberOfCasesText.setData(FEATURE, SimulationPackage.eINSTANCE
                .getStartSimulationDataType_NumberOfCases());
        numberOfCasesText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        intervalGroup =
                toolkit
                        .createGroup(root,
                                PropertiesMessage
                                        .getString("StartEventSimulationExistSection.Interval")); //$NON-NLS-1$
        GridLayout intervalGroupLayout = new GridLayout();
        intervalGroupLayout.numColumns = 2;
        intervalGroupLayout.marginWidth = ITabbedPropertyConstants.HSPACE + 2;
        intervalGroupLayout.marginHeight = ITabbedPropertyConstants.VSPACE;
        intervalGroupLayout.verticalSpacing =
                ITabbedPropertyConstants.VMARGIN + 1;
        intervalGroupLayout.horizontalSpacing =
                ITabbedPropertyConstants.HMARGIN + 1;
        intervalGroup.setLayout(intervalGroupLayout);
        GridData intervalGroupGridData = new GridData();
        intervalGroupGridData.grabExcessHorizontalSpace = true;
        intervalGroupGridData.grabExcessVerticalSpace = true;
        intervalGroupGridData.horizontalAlignment = SWT.FILL;
        intervalGroupGridData.verticalAlignment = SWT.FILL;
        intervalGroupGridData.horizontalSpan = 2;
        intervalGroup.setLayoutData(intervalGroupGridData);
        toolkit.paintBordersFor(intervalGroup);

        toolkit
                .createLabel(intervalGroup,
                        PropertiesMessage
                                .getString("StartEventSimulationExistSection.Distribution"), SWT.NONE); //$NON-NLS-1$
        durationTypeCCombo =
                toolkit.createCCombo(intervalGroup, SWT.READ_ONLY | SWT.FLAT);

        List<RealDistributionCategory> distCategoriesList =
                new ArrayList<RealDistributionCategory>();
        Set<RealDistributionCategory> filteredCategories =
                new HashSet<RealDistributionCategory>(
                        Arrays
                                .asList(new RealDistributionCategory[] {
                                        RealDistributionCategory.PARAMETER_BASED_LITERAL,
                                        RealDistributionCategory.EMPIRICAL_LITERAL }));
        for (Iterator iter = RealDistributionCategory.VALUES.iterator(); iter
                .hasNext();) {
            RealDistributionCategory distCategory =
                    ((RealDistributionCategory) iter.next());
            if (!filteredCategories.contains(distCategory)) {
                distCategoriesList.add(distCategory);
            }
        }

        // set content and label provider for duration distribution combo as we
        // want to work with
        // objects and change text if we must for globalisation purposes
        ArrayContentProvider contentProvider = new ArrayContentProvider();
        durationTypeComboViewer = new ComboViewer(durationTypeCCombo);
        durationTypeComboViewer.setContentProvider(contentProvider);
        durationTypeComboViewer.setInput(distCategoriesList);
        durationTypeComboViewer
                .setLabelProvider(new SimulationDurationTypeLabelProvider());
        durationTypeCCombo
                .setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        durationTypeCCombo.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                CCombo combo = (CCombo) e.getSource();
                int selectedIndex = combo.getSelectionIndex();
                RealDistributionCategory selectedDist =
                        (RealDistributionCategory) durationTypeComboViewer
                                .getElementAt(selectedIndex);
                onDistributonSelected(selectedDist);
            }
        });

        toolkit
                .createLabel(intervalGroup,
                        PropertiesMessage
                                .getString("StartEventSimulationExistSection.TimeUnit"), SWT.NONE); //$NON-NLS-1$
        timeUnitCCombo =
                toolkit.createCCombo(intervalGroup, SWT.READ_ONLY | SWT.FLAT);
        timeUnitCCombo.setData(FEATURE, SimulationPackage.eINSTANCE
                .getStartSimulationDataType_DisplayTimeUnit());
        // set content and label provider for time unit combo as we want to work
        // with
        // objects and change text if we must for globalisation purposes
        contentProvider = new ArrayContentProvider();
        timeUnitComboViewer = new ComboViewer(timeUnitCCombo);
        timeUnitComboViewer.setContentProvider(contentProvider);
        timeUnitComboViewer.setInput(TimeDisplayUnitType.VALUES);
        timeUnitComboViewer
                .setLabelProvider(new SimulationTimeUnitLabelProvider());
        timeUnitCCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        timeUnitCCombo.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                CCombo combo = (CCombo) e.getSource();
                int selectedIndex = combo.getSelectionIndex();
                TimeDisplayUnitType selectedTimeUnit =
                        (TimeDisplayUnitType) timeUnitComboViewer
                                .getElementAt(selectedIndex);
                onTimeUnitSelected(selectedTimeUnit);
            }
        });

        book = toolkit.createPageBook(intervalGroup, SWT.NONE);

        GridLayout bookGridLayout = new GridLayout();
        book.setLayout(bookGridLayout);

        GridData bookGridData = new GridData();
        bookGridData.grabExcessHorizontalSpace = true;
        bookGridData.grabExcessVerticalSpace = true;
        bookGridData.horizontalAlignment = SWT.FILL;
        bookGridData.verticalAlignment = SWT.FILL;
        bookGridData.horizontalSpan = 2;
        book.setLayoutData(bookGridData);

        createConstantDistributionPage(toolkit);
        createUniformDistributionPage(toolkit);
        createNormalDistributionPage(toolkit);
        createExponentialDistributionPage(toolkit);
        createEmpiricalDistributionPage(toolkit);

        manageControl(numberOfCasesText);
        manageControl(constantValueText);
        manageControl(uniformMinValueText);
        manageControl(uniformMaxValueText);
        manageControl(normalMeanText);
        manageControl(normalStandardDeviationText);
        manageControl(exponentialMeanText);
        manageControl(empiricalReferenceText);
        return root;
    }

    /**
     * @param toolkit
     *            The UI toolkit.
     */
    private void createEmpiricalDistributionPage(XpdFormToolkit toolkit) {
        // empirical
        Composite empiricalPage = toolkit.createComposite(book.getContainer());
        empiricalPage.setLayout(new GridLayout(EMPIRICAL_COLUMNS, false));

        toolkit.createLabel(empiricalPage, PropertiesMessage
                .getString("StartEventSimulationExistSection.Reference")); //$NON-NLS-1$

        empiricalReferenceText = toolkit.createText(empiricalPage, ""); //$NON-NLS-1$
        empiricalReferenceText.setData(FEATURE, SimulationPackage.eINSTANCE
                .getExternalEmpiricalDistribution_Reference());
        empiricalReferenceText.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL));
        empiricalReferenceText.setEditable(false);

        Button b = toolkit.createButton(empiricalPage, "...", SWT.NONE); //$NON-NLS-1$
        b.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                browseForRealDataFile();
            }
        });

        book.registerPage(RealDistributionCategory.EMPIRICAL_LITERAL,
                empiricalPage);
    }

    /**
     * @param toolkit
     *            The UI toolkit.
     */
    private void createExponentialDistributionPage(XpdFormToolkit toolkit) {
        // exponential
        Composite exponentialPage =
                toolkit.createComposite(book.getContainer());
        exponentialPage.setLayout(new GridLayout(2, false));

        toolkit.createLabel(exponentialPage, PropertiesMessage
                .getString("StartEventSimulationExistSection.Mean")); //$NON-NLS-1$
        exponentialMeanText =
                toolkit.createText(exponentialPage,
                        EXPONENTIAL_DIST_MEAN_DEFAULT);
        exponentialMeanText.setData(FEATURE, SimulationPackage.eINSTANCE
                .getExponentialRealDistribution_Mean());
        exponentialMeanText
                .setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        GridData exponentialLablelGD = new GridData(GridData.FILL_HORIZONTAL);
        exponentialLablelGD.horizontalSpan = 2;
        exponentialCostLabel = toolkit.createLabel(exponentialPage, ""); //$NON-NLS-1$
        exponentialCostLabel.setLayoutData(exponentialLablelGD);

        book.registerPage(RealDistributionCategory.EXPONENTIAL_LITERAL,
                exponentialPage);
    }

    /**
     * @param toolkit
     *            The UI toolkit.
     */
    private void createNormalDistributionPage(XpdFormToolkit toolkit) {
        // normal
        Composite normalPage = toolkit.createComposite(book.getContainer());
        normalPage.setLayout(new GridLayout(2, false));

        toolkit.createLabel(normalPage, PropertiesMessage
                .getString("StartEventSimulationExistSection.Mean")); //$NON-NLS-1$
        normalMeanText =
                toolkit.createText(normalPage, NORMAL_DIST_MEAN_DEFAULT);
        normalMeanText.setData(FEATURE, SimulationPackage.eINSTANCE
                .getNormalRealDistribution_Mean());
        normalMeanText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        toolkit
                .createLabel(normalPage,
                        PropertiesMessage
                                .getString("StartEventSimulationExistSection.StandardDeviation")); //$NON-NLS-1$
        normalStandardDeviationText =
                toolkit.createText(normalPage,
                        NORMAL_DIST_STANDARD_DEVIATION_DEFAULT);
        normalStandardDeviationText.setData(FEATURE,
                SimulationPackage.eINSTANCE
                        .getNormalRealDistribution_StandardDeviation());
        normalStandardDeviationText.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL));

        GridData normalLablelGD = new GridData(GridData.FILL_HORIZONTAL);
        normalLablelGD.horizontalSpan = 2;
        normalCostLabel = toolkit.createLabel(normalPage, ""); //$NON-NLS-1$
        normalCostLabel.setLayoutData(normalLablelGD);

        book.registerPage(RealDistributionCategory.NORMAL_LITERAL, normalPage);
    }

    /**
     * @param toolkit
     *            The UI toolkit.
     */
    private void createUniformDistributionPage(XpdFormToolkit toolkit) {
        // uniform
        Composite uniformPage = toolkit.createComposite(book.getContainer());
        uniformPage.setLayout(new GridLayout(2, false));
        toolkit.createLabel(uniformPage, PropertiesMessage
                .getString("StartEventSimulationExistSection.ValueMin")); //$NON-NLS-1$
        uniformMinValueText =
                toolkit.createText(uniformPage, UNIFORM_DIST_MIN_VALUE_DEFAULT);
        uniformMinValueText.setData(FEATURE, SimulationPackage.eINSTANCE
                .getUniformRealDistribution_LowerBorder());
        uniformMinValueText
                .setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        toolkit.createLabel(uniformPage, PropertiesMessage
                .getString("StartEventSimulationExistSection.ValueMax")); //$NON-NLS-1$
        uniformMaxValueText =
                toolkit.createText(uniformPage, UNIFORM_DIST_MAX_VALUE_DEFAULT);
        uniformMaxValueText.setData(FEATURE, SimulationPackage.eINSTANCE
                .getUniformRealDistribution_UpperBorder());
        uniformMaxValueText
                .setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        GridData uniformLablelGD = new GridData(GridData.FILL_HORIZONTAL);
        uniformLablelGD.horizontalSpan = 2;
        uniformCostLabel = toolkit.createLabel(uniformPage, ""); //$NON-NLS-1$
        uniformCostLabel.setLayoutData(uniformLablelGD);

        book
                .registerPage(RealDistributionCategory.UNIFORM_LITERAL,
                        uniformPage);
    }

    /**
     * @param toolkit
     *            The UI toolkit.
     */
    private void createConstantDistributionPage(XpdFormToolkit toolkit) {
        GridLayout pageGridLayout;
        GridData pageGridData;
        // constant
        Composite constantPage = toolkit.createComposite(book.getContainer());
        pageGridLayout = new GridLayout(2, false);
        constantPage.setLayout(pageGridLayout);
        pageGridData = new GridData();
        pageGridData.grabExcessHorizontalSpace = true;
        pageGridData.grabExcessVerticalSpace = true;
        pageGridData.horizontalAlignment = SWT.FILL;
        pageGridData.verticalAlignment = SWT.FILL;
        constantPage.setLayoutData(pageGridData);
        toolkit.createLabel(constantPage, PropertiesMessage
                .getString("StartEventSimulationExistSection.Value")); //$NON-NLS-1$
        constantValueText =
                toolkit.createText(constantPage, CONSTANT_DIST_VALUE_DEFAULT);
        constantValueText.setData(FEATURE, SimulationPackage.eINSTANCE
                .getConstantRealDistribution_ConstantValue());
        constantValueText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        GridData constLablelGD = new GridData(GridData.FILL_HORIZONTAL);
        constLablelGD.horizontalSpan = 2;
        constantCostLabel = toolkit.createLabel(constantPage, ""); //$NON-NLS-1$
        constantCostLabel.setLayoutData(constLablelGD);

        book.registerPage(RealDistributionCategory.CONSTANT_LITERAL,
                constantPage);
    }

    /**
     * @param selectedTimeUnit
     *            The selected time unit.
     */
    private void onTimeUnitSelected(TimeDisplayUnitType selectedTimeUnit) {
        Activity activity = (Activity) getInput();
        EditingDomain ed = getEditingDomain(activity);
        StartSimulationDataType activitySimData =
                getStartSimulationData(activity);

        CompoundCommand compoundCmd =
                new CompoundCommand(
                        PropertiesMessage
                                .getString("StartEventSimulationExistSection.ChangeTimeUnit"), //$NON-NLS-1$
                        PropertiesMessage
                                .getString("StartEventSimulationExistSection.ChangeTimeUnit")); //$NON-NLS-1$
        Command cmd =
                SetCommand.create(ed, activitySimData, timeUnitCCombo
                        .getData(FEATURE), selectedTimeUnit);
        compoundCmd.append(cmd);
        ed.getCommandStack().execute(compoundCmd);
    }

    /**
     * @param selectedDist
     *            The selected distribution.
     */
    private void onDistributonSelected(RealDistributionCategory selectedDist) {
        // book.showPage(selectedDist);
        Activity activity = (Activity) getInput();
        EditingDomain ed = getEditingDomain(activity);
        StartSimulationDataType activitySimData =
                getStartSimulationData(activity);

        CompoundCommand compoundCmd =
                new CompoundCommand(
                        PropertiesMessage
                                .getString("StartEventSimulationExistSection.ChangeDuration"), //$NON-NLS-1$
                        PropertiesMessage
                                .getString("StartEventSimulationExistSection.ChangeDuration")); //$NON-NLS-1$

        Command cmd = null;

        // creating and attaching default distribution.
        if (selectedDist == RealDistributionCategory.CONSTANT_LITERAL) {
            ConstantRealDistribution constDist =
                    SimulationFactory.eINSTANCE
                            .createConstantRealDistribution();
            constDist.setConstantValue(getDefaultValue(constantValueText,
                    CONSTANT_DIST_VALUE_DEFAULT));
            cmd =
                    SetCommand.create(ed,
                            activitySimData,
                            SimulationPackage.eINSTANCE
                                    .getStartSimulationDataType_Duration(),
                            constDist);
            compoundCmd.append(cmd);
        } else if (selectedDist == RealDistributionCategory.UNIFORM_LITERAL) {
            UniformRealDistribution uniformDist =
                    SimulationFactory.eINSTANCE.createUniformRealDistribution();
            uniformDist.setLowerBorder(getDefaultValue(uniformMinValueText,
                    UNIFORM_DIST_MIN_VALUE_DEFAULT));
            uniformDist.setUpperBorder(getDefaultValue(uniformMaxValueText,
                    UNIFORM_DIST_MAX_VALUE_DEFAULT));
            cmd =
                    SetCommand.create(ed,
                            activitySimData,
                            SimulationPackage.eINSTANCE
                                    .getStartSimulationDataType_Duration(),
                            uniformDist);
            compoundCmd.append(cmd);
        } else if (selectedDist == RealDistributionCategory.NORMAL_LITERAL) {
            NormalRealDistribution normalDist =
                    SimulationFactory.eINSTANCE.createNormalRealDistribution();
            normalDist.setMean(getDefaultValue(normalMeanText,
                    NORMAL_DIST_MEAN_DEFAULT));
            normalDist
                    .setStandardDeviation(getDefaultValue(normalStandardDeviationText,
                            NORMAL_DIST_STANDARD_DEVIATION_DEFAULT));

            cmd =
                    SetCommand.create(ed,
                            activitySimData,
                            SimulationPackage.eINSTANCE
                                    .getStartSimulationDataType_Duration(),
                            normalDist);
            compoundCmd.append(cmd);
        } else if (selectedDist == RealDistributionCategory.EXPONENTIAL_LITERAL) {

            ExponentialRealDistribution exponentialDist =
                    SimulationFactory.eINSTANCE
                            .createExponentialRealDistribution();
            exponentialDist.setMean(getDefaultValue(exponentialMeanText,
                    EXPONENTIAL_DIST_MEAN_DEFAULT));

            cmd =
                    SetCommand.create(ed,
                            activitySimData,
                            SimulationPackage.eINSTANCE
                                    .getStartSimulationDataType_Duration(),
                            exponentialDist);
            compoundCmd.append(cmd);
        } else if (selectedDist == RealDistributionCategory.EMPIRICAL_LITERAL) {
            ExternalEmpiricalDistribution empiricalDist =
                    SimulationFactory.eINSTANCE
                            .createExternalEmpiricalDistribution();
            empiricalDist.setType(REAL_START_DATA_SCENARIO);
            empiricalDist.setReference(""); //$NON-NLS-1$            
            cmd =
                    SetCommand.create(ed,
                            activitySimData,
                            SimulationPackage.eINSTANCE
                                    .getStartSimulationDataType_Duration(),
                            empiricalDist);
            compoundCmd.append(cmd);
        }

        if (compoundCmd.getCommandList().size() > 0) {
            ed.getCommandStack().execute(compoundCmd);
        }
    }

    /**
     * @param text
     *            The text control.
     * @param defaultVal
     *            The default value.
     * @return The double value or the default if the text is invalid.
     */
    private double getDefaultValue(Text text, String defaultVal) {
        double defVal = 0.0D;
        try {
            defVal = Double.parseDouble(text.getText());
        } catch (Exception e) {
            try {
                defVal = Double.parseDouble(defaultVal);
            } catch (Exception ex) {
                return 0.0D;
            }
        }
        return defVal;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     */
    protected void doRefresh() {
        Object input = getInput();
        if (input != null) {
            Activity activity = (Activity) input;
            StartSimulationDataType actSimData =
                    getStartSimulationData(activity);

            int numberOfCases = (int) actSimData.getNumberOfCases();
            updateText(numberOfCasesText, "" + numberOfCases); //$NON-NLS-1$
            AbstractBasicDistribution duration = actSimData.getDuration();
            TimeDisplayUnitType currentTimeUnit =
                    actSimData.getDisplayTimeUnit();
            timeUnitComboViewer.setSelection(new StructuredSelection(
                    currentTimeUnit), true);

            EObject distribution = duration;

            boolean hasParticipantCosstAssigned = false;
            double participantCost = 0.0D;

            Performer performer = null;
            List<Performer> performers = activity.getPerformerList();

            if (performers != null && performers.size() > 0) {
                performer = performers.get(0);
            }

            Participant actParticipant = findParticipant(activity, performer);
            if (actParticipant != null) {
                ParticipantSimulationDataType partSimData =
                        getParticipantSimulationData(actParticipant);
                if (partSimData != null) {
                    hasParticipantCosstAssigned = true;
                    participantCost = partSimData.getTimeUnitCost().getCost();
                }
            }

            if (distribution instanceof ConstantRealDistribution) {
                ConstantRealDistribution constDist =
                        (ConstantRealDistribution) distribution;
                durationTypeComboViewer.setSelection(new StructuredSelection(
                        RealDistributionCategory.CONSTANT_LITERAL), true);
                book.showPage(RealDistributionCategory.CONSTANT_LITERAL);
                updateText(constantValueText, DisplayTimeUnitConverter
                        .convertToString(xpdlTimeUnit,
                                currentTimeUnit,
                                constDist.getConstantValue()));

                if (hasParticipantCosstAssigned) {
                    updateConstantCostLabel(participantCost, constDist);
                }
            }
            if (distribution instanceof UniformRealDistribution) {
                UniformRealDistribution uniformDist =
                        (UniformRealDistribution) distribution;
                durationTypeComboViewer.setSelection(new StructuredSelection(
                        RealDistributionCategory.UNIFORM_LITERAL), true);
                book.showPage(RealDistributionCategory.UNIFORM_LITERAL);
                updateText(uniformMinValueText, DisplayTimeUnitConverter
                        .convertToString(xpdlTimeUnit,
                                currentTimeUnit,
                                uniformDist.getLowerBorder()));
                updateText(uniformMaxValueText, DisplayTimeUnitConverter
                        .convertToString(xpdlTimeUnit,
                                currentTimeUnit,
                                uniformDist.getUpperBorder()));
                if (hasParticipantCosstAssigned) {
                    updateUniformCostLabel(participantCost, uniformDist);
                }
            }
            if (distribution instanceof NormalRealDistribution) {
                NormalRealDistribution normalDist =
                        (NormalRealDistribution) distribution;
                durationTypeComboViewer.setSelection(new StructuredSelection(
                        RealDistributionCategory.NORMAL_LITERAL), true);
                book.showPage(RealDistributionCategory.NORMAL_LITERAL);
                updateText(normalMeanText, DisplayTimeUnitConverter
                        .convertToString(xpdlTimeUnit,
                                currentTimeUnit,
                                normalDist.getMean()));
                updateText(normalStandardDeviationText,
                        DisplayTimeUnitConverter.convertToString(xpdlTimeUnit,
                                currentTimeUnit,
                                normalDist.getStandardDeviation()));
                if (hasParticipantCosstAssigned) {
                    updateNormalCostLabel(participantCost, normalDist);
                }
            }
            if (distribution instanceof ExponentialRealDistribution) {
                ExponentialRealDistribution exponentialDist =
                        (ExponentialRealDistribution) distribution;
                durationTypeComboViewer.setSelection(new StructuredSelection(
                        RealDistributionCategory.EXPONENTIAL_LITERAL), true);
                book.showPage(RealDistributionCategory.EXPONENTIAL_LITERAL);
                updateText(exponentialMeanText, DisplayTimeUnitConverter
                        .convertToString(xpdlTimeUnit,
                                currentTimeUnit,
                                exponentialDist.getMean()));
                if (hasParticipantCosstAssigned) {
                    updateExponentialCostLabel(participantCost, exponentialDist);
                }
            }
            if (distribution instanceof ExternalEmpiricalDistribution) {
                ExternalEmpiricalDistribution empiricalDist =
                        (ExternalEmpiricalDistribution) distribution;
                durationTypeComboViewer.setSelection(new StructuredSelection(
                        RealDistributionCategory.EMPIRICAL_LITERAL), true);
                book.showPage(RealDistributionCategory.EMPIRICAL_LITERAL);
                updateText(empiricalReferenceText, empiricalDist.getReference());
            }
        }
    }

    /**
     * @param object
     *            The object to get the command for.
     * @return The command.
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     */
    public Command doGetCommand(Object object) {
        Activity activity = (Activity) getInput();
        EditingDomain ed = getEditingDomain(activity);
        CompoundCommand cmd = new CompoundCommand();

        StartSimulationDataType actSimData = getStartSimulationData(activity);
        if (actSimData != null) {
            int selectedIndex = durationTypeCCombo.getSelectionIndex();
            RealDistributionCategory selectedDist =
                    (RealDistributionCategory) durationTypeComboViewer
                            .getElementAt(selectedIndex);
            TimeDisplayUnitType currentTimeUnit =
                    actSimData.getDisplayTimeUnit();

            boolean hasParticipantCosstAssigned = false;
            double participantCost = 0.0D;

            Performer performer = null;
            List<Performer> performers = activity.getPerformerList();

            if (performers != null && performers.size() > 0) {
                performer = performers.get(0);
            }

            Participant actParticipant = findParticipant(activity, performer);
            if (actParticipant != null) {
                ParticipantSimulationDataType partSimData =
                        getParticipantSimulationData(actParticipant);
                if (partSimData != null) {
                    hasParticipantCosstAssigned = true;
                    participantCost = partSimData.getTimeUnitCost().getCost();
                }
            }
            if (object.equals(numberOfCasesText)) {
                try {
                    Long numberOfCases = new Long(numberOfCasesText.getText());
                    cmd.append(SetCommand.create(ed,
                            actSimData,
                            numberOfCasesText.getData(FEATURE),
                            numberOfCases));
                } catch (NumberFormatException e) {
                    // Ignore.
                }
            }
            if (selectedDist == RealDistributionCategory.CONSTANT_LITERAL) {
                ConstantRealDistribution constDist =
                        (ConstantRealDistribution) actSimData.getDuration();
                if (object.equals(constantValueText)) {
                    Double valueDouble =
                            DisplayTimeUnitConverter
                                    .convertToDouble(currentTimeUnit,
                                            xpdlTimeUnit,
                                            constantValueText.getText());
                    Object feature =
                            SimulationPackage.eINSTANCE
                                    .getConstantRealDistribution_ConstantValue();
                    if (valueDouble == null) {
                        cmd.append(UnexecutableCommand.INSTANCE);
                    } else {
                        cmd.append(SetCommand.create(ed,
                                constDist,
                                feature,
                                valueDouble));
                    }
                    if (hasParticipantCosstAssigned) {
                        updateConstantCostLabel(participantCost, constDist);
                    }
                }
            } else if (selectedDist == RealDistributionCategory.UNIFORM_LITERAL) {
                UniformRealDistribution uniformDist =
                        (UniformRealDistribution) actSimData.getDuration();
                if (object.equals(uniformMinValueText)) {
                    Double minValue =
                            DisplayTimeUnitConverter
                                    .convertToDouble(currentTimeUnit,
                                            xpdlTimeUnit,
                                            uniformMinValueText.getText());
                    if (minValue == null) {
                        cmd.append(UnexecutableCommand.INSTANCE);
                    } else {
                        cmd
                                .append(SetCommand
                                        .create(ed,
                                                uniformDist,
                                                SimulationPackage.eINSTANCE
                                                        .getUniformRealDistribution_LowerBorder(),
                                                minValue));
                    }
                    if (hasParticipantCosstAssigned) {
                        updateUniformCostLabel(participantCost, uniformDist);
                    }
                } else if (object.equals(uniformMaxValueText)) {
                    Double maxValue =
                            DisplayTimeUnitConverter
                                    .convertToDouble(currentTimeUnit,
                                            xpdlTimeUnit,
                                            uniformMaxValueText.getText());
                    if (maxValue == null) {
                        cmd.append(UnexecutableCommand.INSTANCE);
                    } else {
                        cmd
                                .append(SetCommand
                                        .create(ed,
                                                uniformDist,
                                                SimulationPackage.eINSTANCE
                                                        .getUniformRealDistribution_UpperBorder(),
                                                maxValue));
                    }
                    if (hasParticipantCosstAssigned) {
                        updateUniformCostLabel(participantCost, uniformDist);
                    }
                }
            } else if (selectedDist == RealDistributionCategory.NORMAL_LITERAL) {
                NormalRealDistribution normalDist =
                        (NormalRealDistribution) actSimData.getDuration();
                if (object.equals(normalMeanText)) {
                    Double mean =
                            DisplayTimeUnitConverter
                                    .convertToDouble(currentTimeUnit,
                                            xpdlTimeUnit,
                                            normalMeanText.getText());
                    if (mean == null) {
                        cmd.append(UnexecutableCommand.INSTANCE);
                    } else {
                        cmd.append(SetCommand.create(ed,
                                normalDist,
                                SimulationPackage.eINSTANCE
                                        .getNormalRealDistribution_Mean(),
                                mean));
                    }
                    if (hasParticipantCosstAssigned) {
                        updateNormalCostLabel(participantCost, normalDist);
                    }
                } else if (object.equals(normalStandardDeviationText)) {
                    Object feature =
                            SimulationPackage.eINSTANCE
                                    .getNormalRealDistribution_StandardDeviation();
                    Double sd =
                            DisplayTimeUnitConverter
                                    .convertToDouble(currentTimeUnit,
                                            xpdlTimeUnit,
                                            normalStandardDeviationText
                                                    .getText());
                    if (sd == null) {
                        cmd.append(UnexecutableCommand.INSTANCE);
                    } else {
                        cmd.append(SetCommand.create(ed,
                                normalDist,
                                feature,
                                sd));
                    }
                    if (hasParticipantCosstAssigned) {
                        updateNormalCostLabel(participantCost, normalDist);
                    }
                }
            } else if (selectedDist == RealDistributionCategory.EXPONENTIAL_LITERAL) {
                ExponentialRealDistribution exponentialDist =
                        (ExponentialRealDistribution) actSimData.getDuration();
                if (object.equals(exponentialMeanText)) {
                    Double mean =
                            DisplayTimeUnitConverter
                                    .convertToDouble(currentTimeUnit,
                                            xpdlTimeUnit,
                                            exponentialMeanText.getText());
                    if (mean == null) {
                        cmd.append(UnexecutableCommand.INSTANCE);
                    } else {
                        cmd.append(SetCommand.create(ed,
                                exponentialDist,
                                SimulationPackage.eINSTANCE
                                        .getExponentialRealDistribution_Mean(),
                                mean));
                    }
                    if (hasParticipantCosstAssigned) {
                        updateExponentialCostLabel(participantCost,
                                exponentialDist);
                    }
                }
            } else if (selectedDist == RealDistributionCategory.EMPIRICAL_LITERAL) {
                ExternalEmpiricalDistribution empiricalDist =
                        (ExternalEmpiricalDistribution) actSimData
                                .getDuration();
                if (object.equals(empiricalReferenceText)) {
                    Object feature =
                            SimulationPackage.eINSTANCE
                                    .getExternalEmpiricalDistribution_Reference();
                    cmd.append(SetCommand.create(ed,
                            empiricalDist,
                            feature,
                            empiricalReferenceText.getText()));
                }
            }
        }
        return cmd;
    }

    /**
     * @param participantCost
     *            The participant cost.
     * @param constDist
     *            The Constant distribution.
     */
    private void updateConstantCostLabel(double participantCost,
            ConstantRealDistribution constDist) {
        double actCost = participantCost * constDist.getConstantValue();
        constantCostLabel.setText(String.format(PropertiesMessage
                .getString("StartEventSimulationExistSection.EstimatedCost"), //$NON-NLS-1$
                DisplayTimeUnitConverter.convertToString(xpdlTimeUnit,
                        xpdlTimeUnit,
                        actCost,
                        ACTIVITY_COST_FORMAT)));

    }

    /**
     * @param participantCost
     *            The participant cost.
     * @param uniformDist
     *            The uniform distribution.
     */
    private void updateUniformCostLabel(double participantCost,
            UniformRealDistribution uniformDist) {
        double actCost =
                participantCost
                        * (uniformDist.getLowerBorder() + (uniformDist
                                .getUpperBorder() - uniformDist
                                .getLowerBorder()) / 2);
        uniformCostLabel.setText(String.format(PropertiesMessage
                .getString("StartEventSimulationExistSection.EstimatedMean"), //$NON-NLS-1$
                DisplayTimeUnitConverter.convertToString(xpdlTimeUnit,
                        xpdlTimeUnit,
                        actCost,
                        ACTIVITY_COST_FORMAT)));
    }

    /**
     * @param participantCost
     *            The participant cost.
     * @param normalDist
     *            The normal distribution.
     */
    private void updateNormalCostLabel(double participantCost,
            NormalRealDistribution normalDist) {
        double actCost = participantCost * normalDist.getMean();
        normalCostLabel.setText(String.format(PropertiesMessage
                .getString("StartEventSimulationExistSection.EstimatedMean"), //$NON-NLS-1$
                DisplayTimeUnitConverter.convertToString(xpdlTimeUnit,
                        xpdlTimeUnit,
                        actCost,
                        ACTIVITY_COST_FORMAT)));
    }

    /**
     * @param participantCost
     *            The participant cost.
     * @param exponentialDist
     *            The exponential distribution.
     */
    private void updateExponentialCostLabel(double participantCost,
            ExponentialRealDistribution exponentialDist) {
        double actCost = participantCost * exponentialDist.getMean();
        exponentialCostLabel.setText(String.format(PropertiesMessage
                .getString("StartEventSimulationExistSection.EstimatedMean"), //$NON-NLS-1$
                DisplayTimeUnitConverter.convertToString(xpdlTimeUnit,
                        xpdlTimeUnit,
                        actCost,
                        ACTIVITY_COST_FORMAT)));
    }

    /**
     * Gets AstivitySimulationData for Activityif tihs data exists otherwise
     * returns null.
     * 
     * @param activity
     *            xpdl activity.
     * @return AstivitySimulationData for Activity if tihs data exists otherwise
     *         returns null.
     */
    private StartSimulationDataType getStartSimulationData(Activity activity) {
        for (Iterator iter = activity.getExtendedAttributes().iterator(); iter
                .hasNext();) {
            ExtendedAttribute ea = (ExtendedAttribute) iter.next();
            if ("StartSimulationData".equals(ea.getName())) { //$NON-NLS-1$
                // found extended attribute
                EList simProcessList =
                        (EList) ea.getMixed().get(SimulationPackage.eINSTANCE
                                .getDocumentRoot_StartSimulationData(),
                                true);
                // there can only be one of these
                if (simProcessList.size() == 1 && simProcessList.get(0) != null) {
                    return (StartSimulationDataType) simProcessList.get(0);
                }
                return null;
            }
        }
        return null;
    }

    /**
     * @param e
     *            The text modification event.
     * @see com.tibco.xpd.ui.properties.TextFieldVerifier#verifyText(org.eclipse.swt.widgets.Event)
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
        StartSimulationDataType actSimData = getStartSimulationData(activity);
        if (actSimData == null) {
            e.doit = false;
            return;
        }
        int selectedIndex = durationTypeCCombo.getSelectionIndex();
        RealDistributionCategory selectedDist =
                (RealDistributionCategory) durationTypeComboViewer
                        .getElementAt(selectedIndex);
        TimeDisplayUnitType currentTimeUnit = actSimData.getDisplayTimeUnit();
        Double valueDouble =
                DisplayTimeUnitConverter.convertToDouble(currentTimeUnit,
                        xpdlTimeUnit,
                        t);
        if (textControl == numberOfCasesText) {
            e.doit =
                    canSetAttribute(actSimData, textControl.getData(FEATURE), t);
        } else if (selectedDist == RealDistributionCategory.CONSTANT_LITERAL) {
            ConstantRealDistribution constDist =
                    (ConstantRealDistribution) actSimData.getDuration();
            if (textControl == constantValueText) {
                if (valueDouble == null) {
                    e.doit = false;
                    return;
                }
                if (valueDouble.doubleValue() < 0.0) {
                    e.doit = false;
                    return;
                }
                t = valueDouble.toString();
                e.doit =
                        canSetAttribute(constDist,
                                textControl.getData(FEATURE),
                                t);
            }
        } else if (selectedDist == RealDistributionCategory.UNIFORM_LITERAL) {
            UniformRealDistribution uniformDist =
                    (UniformRealDistribution) actSimData.getDuration();
            if (textControl == uniformMinValueText) {
                if (valueDouble == null) {
                    e.doit = false;
                    return;
                }
                if (valueDouble.doubleValue() < 0.0) {
                    e.doit = false;
                    return;
                }
                t = valueDouble.toString();
                e.doit =
                        canSetAttribute(uniformDist, textControl
                                .getData(FEATURE), t);
            } else if (textControl == uniformMaxValueText) {
                if (valueDouble == null) {
                    e.doit = false;
                    return;
                }
                if (valueDouble.doubleValue() < 0.0) {
                    e.doit = false;
                    return;
                }
                t = valueDouble.toString();
                e.doit =
                        canSetAttribute(uniformDist, textControl
                                .getData(FEATURE), t);
            } else {
                throw new IllegalArgumentException();
            }
        } else if (selectedDist == RealDistributionCategory.NORMAL_LITERAL) {
            NormalRealDistribution normalDist =
                    (NormalRealDistribution) actSimData.getDuration();
            if (textControl == normalMeanText) {

                if (valueDouble == null) {
                    e.doit = false;
                    return;
                }
                if (valueDouble.doubleValue() < 0.0) {
                    e.doit = false;
                    return;
                }
                t = valueDouble.toString();
                e.doit =
                        canSetAttribute(normalDist, textControl
                                .getData(FEATURE), t);
            } else if (textControl == normalStandardDeviationText) {
                if (valueDouble == null) {
                    e.doit = false;
                    return;
                }
                if (valueDouble.doubleValue() < 0.0) {
                    e.doit = false;
                    return;
                }
                t = valueDouble.toString();
                e.doit =
                        canSetAttribute(normalDist, textControl
                                .getData(FEATURE), t);
            } else {
                throw new IllegalArgumentException();
            }
        } else if (selectedDist == RealDistributionCategory.EXPONENTIAL_LITERAL) {
            ExponentialRealDistribution exponentialDist =
                    (ExponentialRealDistribution) actSimData.getDuration();
            if (textControl == exponentialMeanText) {
                if (valueDouble == null) {
                    e.doit = false;
                    return;
                }
                if (valueDouble.doubleValue() < 0.0) {
                    e.doit = false;
                    return;
                }
                t = valueDouble.toString();
                e.doit =
                        canSetAttribute(exponentialDist, textControl
                                .getData(FEATURE), t);
            } else {
                throw new IllegalArgumentException();
            }
        } else if (selectedDist == RealDistributionCategory.EMPIRICAL_LITERAL) {
            // EmpiricalDistributionType uniformDist =
            // (EmpiricalDistributionType) getDistribution(actSimData
            // .getDuration());
            e.doit = true;
        }
    }

    /**
     * @param owner
     *            The owning object.
     * @param feature
     *            The feature ro check.
     * @param newValue
     *            The new value to check.
     * @return true if the feature of the object can be modified.
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

    /**
     * Search by participantId for participant from activity. First search in
     * the containing process and then in the package. If participant not found
     * then null is returned.
     * 
     * @param activity
     *            The activity.
     * @param performer
     *            The preformer.
     * @return participant form process or package or null if not found.
     */
    private Participant findParticipant(Activity activity, Performer performer) {
        if (performer == null) {
            return null;
        }
        String participantId = performer.getValue();
        if (participantId == null) {
            return null;
        }
        EObject container = activity;
        // search in process first then in package
        while ((container = container.eContainer()) != null) {
            if (container instanceof Process) {
                Process process = (Process) container;
                List participants = process.getParticipants();
                for (Iterator iter = participants.iterator(); iter.hasNext();) {
                    Participant participant = (Participant) iter.next();
                    if (participantId.equals(participant.getId())) {
                        return participant;
                    }
                }
            }
            if (container instanceof Package) {
                Package thePackage = (Package) container;
                List participants = thePackage.getParticipants();
                for (Iterator iter = participants.iterator(); iter.hasNext();) {
                    Participant participant = (Participant) iter.next();
                    if (participantId.equals(participant.getId())) {
                        return participant;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Gets ParticipantSimulationData for Participant if tihs data exists
     * otherwise returns null.
     * 
     * @param participant
     *            xpdl participant.
     * @return ParticipantSimulationData for Participant if tihs data exists
     *         otherwise returns null.
     */
    private static ParticipantSimulationDataType getParticipantSimulationData(
            Participant participant) {
        for (Iterator iter = participant.getExtendedAttributes().iterator(); iter
                .hasNext();) {
            ExtendedAttribute ea = (ExtendedAttribute) iter.next();
            if ("ParticipantSimulationData".equals(ea.getName())) { //$NON-NLS-1$
                // found extended attribute
                EStructuralFeature feature =
                        SimulationPackage.eINSTANCE
                                .getDocumentRoot_ParticipantSimulationData();
                EList simProcessList = (EList) ea.getMixed().get(feature, true);
                // there can only be one of these
                if (simProcessList.size() == 1 && simProcessList.get(0) != null) {
                    return (ParticipantSimulationDataType) simProcessList
                            .get(0);
                }
                return null;
            }
        }
        return null;
    }

    /**
     * @return An array of objects to monitor.
     * @see com.tibco.xpd.ui.properties.AbstractEObjectSection#
     *      getNotifierObjects()
     */
    public EObject[] getNotifierObjects() {
        Activity activity = (Activity) getInput();
        StartSimulationDataType actSimData = getStartSimulationData(activity);
        return new EObject[] { getExtendedAttribute(activity), actSimData };
    }

    /**
     * @param activity
     *            The activity to get the attribute for.
     * @return The StartSimulationData extended attribute.
     */
    private ExtendedAttribute getExtendedAttribute(Activity activity) {
        ExtendedAttribute extendedAttribute = null;
        for (Iterator iter = activity.getExtendedAttributes().iterator(); iter
                .hasNext();) {
            ExtendedAttribute ea = (ExtendedAttribute) iter.next();
            if ("StartSimulationData".equals(ea.getName())) { //$NON-NLS-1$
                // found extended attribute
                extendedAttribute = ea;
                break;
            }
        }
        return extendedAttribute;
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
                        && simDataExist(activity)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param act
     *            The activity to check.
     * @return true if it has simulation data.
     */
    private boolean simDataExist(EObject act) {
        StartSimulationDataType simData =
                SimulationXpdlUtils.getStartSimulationData((Activity) act);
        return simData != null;
    }

    /**
     * Browse for a real data file.
     */
    protected void browseForRealDataFile() {
        ResourceSelectionDialog resourceSelectionDialog =
                new ResourceSelectionDialog(
                        PropertiesPlugin.getShell(),
                        ResourcesPlugin.getWorkspace().getRoot(),
                        PropertiesMessage
                                .getString("StartEventSimulationExistSection.SelectFile")); //$NON-NLS-1$

        resourceSelectionDialog.open();
        Object[] result = resourceSelectionDialog.getResult();
        if (result != null) {
            IResource resource = (IResource) result[0];
            String path = resource.getFullPath().toPortableString();
            updateText(empiricalReferenceText,path);
        }
    }
}
