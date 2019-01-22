/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.ui.properties;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.ScrolledPageBook;

import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.simulation.AbstractBasicDistribution;
import com.tibco.xpd.simulation.ActivitySimulationDataType;
import com.tibco.xpd.simulation.ConstantRealDistribution;
import com.tibco.xpd.simulation.EnumBasedExpressionType;
import com.tibco.xpd.simulation.ExponentialRealDistribution;
import com.tibco.xpd.simulation.ExternalEmpiricalDistribution;
import com.tibco.xpd.simulation.NormalRealDistribution;
import com.tibco.xpd.simulation.ParameterBasedDistribution;
import com.tibco.xpd.simulation.ParameterDependentDistribution;
import com.tibco.xpd.simulation.ParticipantSimulationDataType;
import com.tibco.xpd.simulation.RealDistributionCategory;
import com.tibco.xpd.simulation.SimulationConstants;
import com.tibco.xpd.simulation.SimulationFactory;
import com.tibco.xpd.simulation.SimulationPackage;
import com.tibco.xpd.simulation.SimulationRealDistributionType;
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
 * Activity simulation data exists section.
 */
public class ActivitySimulationExistSection extends
        AbstractFilteredTransactionalSection implements TextFieldVerifier {

    /** Section minimum height. */
    private static final int SECTION_HEIGHT = 160;

    /** SLA page book height hint. */
    private static final int SLA_BOOK_HEIGHT = 80;

    /** Page book height hint. */
    private static final int PAGE_BOOK_HEIGHT = 60;

    /** Table height hint. */
    private static final int TABLE_HEIGHT = 60;

    /** Width of small columns. */
    private static final int SMALL_COLUMN_WIDTH = 100;

    /** Width of large columns. */
    private static final int LARGE_COLUMN_WIDTH = 130;

    /** Activity cost display format. */
    private static final String ACTIVITY_COST_FORMAT = "#0.####"; //$NON-NLS-1$

    /** Feature ID. */
    private static final String FEATURE = "FEATURE"; //$NON-NLS-1$

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

    /** The section page book. */
    private ScrolledPageBook book;

    /** Time display unit. */
    private final TimeDisplayUnitType xpdlTimeUnit =
            DisplayTimeUnitConverter.DEFAULT_TIME_UNIT;

    /** Duration type combo. */
    private CCombo durationTypeCCombo;

    /** Time unit combo. */
    private CCombo timeUnitCCombo;

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

    /** SLA maximum delay. */
    private Text slaMaximumDelayText;

    /** Enum values table viewer. */
    private TableViewer enumValuesTableViewer;

    /** Combo viewer for duration types - used for displaying globalised values */
    private ComboViewer durationTypeComboViewer;

    /** Combo viewer for time units - used for displaying globalised values */
    private ComboViewer timeUnitComboViewer;

    /**
     * Constructor.
     */
    public ActivitySimulationExistSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());
        setMinimumHeight(SECTION_HEIGHT);
    }

    /**
     * @param parent
     *            The parent composite.
     * @param toolkit
     *            The UI toolkit.
     * @return The control.
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doCreateControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     */
    public Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = toolkit.createComposite(parent);
        GridLayout gridLayout = new GridLayout(2, false);
        root.setLayout(gridLayout);
        toolkit
                .createLabel(root,
                        PropertiesMessage
                                .getString("ActivitySimulationExistSection.Duration"), SWT.NONE); //$NON-NLS-1$
        durationTypeCCombo =
                toolkit.createCCombo(root, SWT.READ_ONLY | SWT.FLAT);

        List<RealDistributionCategory> distCategoriesList =
                new ArrayList<RealDistributionCategory>();
        Set<RealDistributionCategory> filteredCategories =
                new HashSet<RealDistributionCategory>();
        filteredCategories.add(RealDistributionCategory.EMPIRICAL_LITERAL);
        filteredCategories
                .add(RealDistributionCategory.PARAMETER_BASED_LITERAL);
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
                .createLabel(root,
                        PropertiesMessage
                                .getString("ActivitySimulationExistSection.TimeUnit"), SWT.NONE); //$NON-NLS-1$
        timeUnitCCombo = toolkit.createCCombo(root, SWT.READ_ONLY | SWT.FLAT);
        timeUnitCCombo.setData(FEATURE, SimulationPackage.eINSTANCE
                .getActivitySimulationDataType_DisplayTimeUnit());

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

        // SLA
        toolkit.createLabel(root, PropertiesMessage
                .getString("ActivitySimulationExistSection.MaxSLA"), SWT.NONE); //$NON-NLS-1$
        slaMaximumDelayText =
                toolkit.createText(root, null, SimulationPackage.eINSTANCE
                        .getActivitySimulationDataType_SlaMaximumDelay());
        slaMaximumDelayText
                .setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        book = toolkit.createPageBook(root, SWT.NONE);

        GridLayout bookGridLayout = new GridLayout();
        book.setLayout(bookGridLayout);

        GridData bookGridData = new GridData();
        bookGridData.grabExcessHorizontalSpace = true;
        bookGridData.grabExcessVerticalSpace = true;
        bookGridData.minimumHeight = SLA_BOOK_HEIGHT;
        bookGridData.horizontalAlignment = SWT.FILL;
        bookGridData.verticalAlignment = SWT.FILL;
        bookGridData.horizontalSpan = 2;
        book.setLayoutData(bookGridData);

        createConstantDistributionPage(toolkit);
        createUniformDistributionPage(toolkit);
        createNormalDistributionPage(toolkit);
        createExponentialDistributionPage(toolkit);
        createParameterDistributionPage(toolkit);
        createEmpiricalDistributionPage(toolkit);

        manageControl(constantValueText);
        manageControl(uniformMinValueText);
        manageControl(uniformMaxValueText);
        manageControl(normalMeanText);
        manageControl(normalStandardDeviationText);
        manageControl(exponentialMeanText);
        manageControl(slaMaximumDelayText);

        // root.pack();
        return root;
    }

    /**
     * @param toolkit
     *            The UI toolkit
     */
    private void createEmpiricalDistributionPage(XpdFormToolkit toolkit) {
        // empirical
        Composite empiricalPage = toolkit.createComposite(book.getContainer());
        empiricalPage.setLayout(new GridLayout(2, false));
        toolkit.createLabel(empiricalPage, PropertiesMessage
                .getString("ActivitySimulationExistSection.Empirical") //$NON-NLS-1$
                + PropertiesMessage
                        .getString("ActivitySimulationExistSection.Select")); //$NON-NLS-1$
        book.registerPage(RealDistributionCategory.EMPIRICAL_LITERAL,
                empiricalPage);
    }

    /**
     * @param toolkit
     *            The UI toolkit
     */
    private void createConstantDistributionPage(XpdFormToolkit toolkit) {
        // constant
        Composite constantPage = toolkit.createComposite(book.getContainer());
        constantPage.setLayout(new GridLayout(2, false));
        toolkit.createLabel(constantPage, PropertiesMessage
                .getString("ActivitySimulationExistSection.Value")); //$NON-NLS-1$
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
     * @param toolkit
     *            The UI toolkit
     */
    private void createUniformDistributionPage(XpdFormToolkit toolkit) {
        // uniform
        Composite uniformPage = toolkit.createComposite(book.getContainer());
        uniformPage.setLayout(new GridLayout(2, false));
        toolkit.createLabel(uniformPage, PropertiesMessage
                .getString("ActivitySimulationExistSection.ValueMin")); //$NON-NLS-1$
        uniformMinValueText =
                toolkit.createText(uniformPage, UNIFORM_DIST_MIN_VALUE_DEFAULT);
        uniformMinValueText.setData(FEATURE, SimulationPackage.eINSTANCE
                .getUniformRealDistribution_LowerBorder());
        uniformMinValueText
                .setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        toolkit.createLabel(uniformPage, PropertiesMessage
                .getString("ActivitySimulationExistSection.ValueMax")); //$NON-NLS-1$
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
    private void createNormalDistributionPage(XpdFormToolkit toolkit) {
        // normal
        Composite normalPage = toolkit.createComposite(book.getContainer());
        normalPage.setLayout(new GridLayout(2, false));

        toolkit.createLabel(normalPage, PropertiesMessage
                .getString("ActivitySimulationExistSection.Mean")); //$NON-NLS-1$
        normalMeanText =
                toolkit.createText(normalPage, NORMAL_DIST_MEAN_DEFAULT);
        normalMeanText.setData(FEATURE, SimulationPackage.eINSTANCE
                .getNormalRealDistribution_Mean());
        normalMeanText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        toolkit.createLabel(normalPage, PropertiesMessage
                .getString("ActivitySimulationExistSection.StandardDeviation")); //$NON-NLS-1$
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
    private void createExponentialDistributionPage(XpdFormToolkit toolkit) {
        // exponential
        Composite exponentialPage =
                toolkit.createComposite(book.getContainer());
        exponentialPage.setLayout(new GridLayout(2, false));

        toolkit.createLabel(exponentialPage, PropertiesMessage
                .getString("ActivitySimulationExistSection.Mean")); //$NON-NLS-1$
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
    private void createParameterDistributionPage(XpdFormToolkit toolkit) {
        // parameter based
        Composite parameterBasedPage =
                toolkit.createComposite(book.getContainer());
        parameterBasedPage.setLayout(new GridLayout(1, false));
        GridData gridDataForPBComposite = new GridData();
        gridDataForPBComposite.minimumHeight = PAGE_BOOK_HEIGHT;
        gridDataForPBComposite.heightHint = PAGE_BOOK_HEIGHT;
        parameterBasedPage.setLayoutData(gridDataForPBComposite);
        Table enumValuesTable =
                toolkit
                        .createTable(parameterBasedPage, SWT.BORDER
                                | SWT.SINGLE);
        GridData tabGridData = new GridData(GridData.FILL_BOTH);
        tabGridData.minimumHeight = TABLE_HEIGHT;
        tabGridData.heightHint = TABLE_HEIGHT;
        tabGridData.grabExcessHorizontalSpace = true;
        enumValuesTable.setLayoutData(tabGridData);
        enumValuesTable.setHeaderVisible(true);
        enumValuesTable.setLinesVisible(true);

        TableColumn parameterColumn =
                new TableColumn(enumValuesTable, SWT.NONE);
        parameterColumn.setText(PropertiesMessage
                .getString("ActivitySimulationExistSection.ParameterColumn")); //$NON-NLS-1$
        parameterColumn.setWidth(LARGE_COLUMN_WIDTH);

        TableColumn enumValueColumn =
                new TableColumn(enumValuesTable, SWT.NONE);
        enumValueColumn.setText(PropertiesMessage
                .getString("ActivitySimulationExistSection.ValueColumn")); //$NON-NLS-1$
        enumValueColumn.setWidth(SMALL_COLUMN_WIDTH);

        TableColumn meanTimeColumn = new TableColumn(enumValuesTable, SWT.NONE);
        meanTimeColumn.setText(PropertiesMessage
                .getString("ActivitySimulationExistSection.MeanTimeColumn")); //$NON-NLS-1$
        meanTimeColumn.setWidth(SMALL_COLUMN_WIDTH);

        TableColumn processParametersColumn =
                new TableColumn(enumValuesTable, SWT.NONE);
        processParametersColumn
                .setText(PropertiesMessage
                        .getString("ActivitySimulationExistSection.StandardDeviationColumn")); //$NON-NLS-1$
        processParametersColumn.setWidth(LARGE_COLUMN_WIDTH);

        enumValuesTableViewer = new TableViewer(enumValuesTable);
        enumValuesTableViewer
                .setContentProvider(new EnumValuesTableContentProvider());
        enumValuesTableViewer
                .setLabelProvider(new EnumValuesTableLabelProvider());

        book.registerPage(RealDistributionCategory.PARAMETER_BASED_LITERAL,
                parameterBasedPage);
    }

    /**
     * @param selectedTimeUnit
     *            The selected time unit.
     */
    private void onTimeUnitSelected(TimeDisplayUnitType selectedTimeUnit) {
        Activity activity = (Activity) getInput();
        EditingDomain ed = getEditingDomain(activity);
        ActivitySimulationDataType activitySimData =
                getActivitySimulationData(activity);

        CompoundCommand compoundCmd =
                new CompoundCommand(
                        PropertiesMessage
                                .getString("ActivitySimulationExistSection.ChangeTime"), //$NON-NLS-1$
                        PropertiesMessage
                                .getString("ActivitySimulationExistSection.ChangeTime")); //$NON-NLS-1$
        Command cmd =
                SetCommand.create(ed, activitySimData, timeUnitCCombo
                        .getData(FEATURE), selectedTimeUnit);
        compoundCmd.append(cmd);
        ed.getCommandStack().execute(compoundCmd);
        refresh();
    }

    /**
     * @param selectedDist
     *            The distribution selected.
     */
    private void onDistributonSelected(RealDistributionCategory selectedDist) {

        // book.showPage(selectedDist);
        Activity activity = (Activity) getInput();
        EditingDomain ed = getEditingDomain(activity);
        ActivitySimulationDataType activitySimData =
                getActivitySimulationData(activity);

        CompoundCommand compoundCmd =
                new CompoundCommand(
                        PropertiesMessage
                                .getString("ActivitySimulationExistSection.ChangeDuration"), //$NON-NLS-1$
                        PropertiesMessage
                                .getString("ActivitySimulationExistSection.ChangeDuration")); //$NON-NLS-1$

        Command cmd = null;

        // creating and attaching default distribution.
        if (selectedDist == RealDistributionCategory.CONSTANT_LITERAL) {
            SimulationRealDistributionType duration =
                    SimulationFactory.eINSTANCE
                            .createSimulationRealDistributionType();
            ConstantRealDistribution constDist =
                    SimulationFactory.eINSTANCE
                            .createConstantRealDistribution();
            constDist.setConstantValue(getDefaultValue(constantValueText,
                    CONSTANT_DIST_VALUE_DEFAULT));
            duration.setBasicDistribution(constDist);
            cmd =
                    SetCommand.create(ed,
                            activitySimData,
                            SimulationPackage.eINSTANCE
                                    .getActivitySimulationDataType_Duration(),
                            duration);
            compoundCmd.append(cmd);
        } else if (selectedDist == RealDistributionCategory.UNIFORM_LITERAL) {
            SimulationRealDistributionType duration =
                    SimulationFactory.eINSTANCE
                            .createSimulationRealDistributionType();
            UniformRealDistribution uniformDist =
                    SimulationFactory.eINSTANCE.createUniformRealDistribution();
            uniformDist.setLowerBorder(getDefaultValue(uniformMinValueText,
                    UNIFORM_DIST_MIN_VALUE_DEFAULT));
            uniformDist.setUpperBorder(getDefaultValue(uniformMaxValueText,
                    UNIFORM_DIST_MAX_VALUE_DEFAULT));
            duration.setBasicDistribution(uniformDist);

            cmd =
                    SetCommand.create(ed,
                            activitySimData,
                            SimulationPackage.eINSTANCE
                                    .getActivitySimulationDataType_Duration(),
                            duration);
            compoundCmd.append(cmd);
        } else if (selectedDist == RealDistributionCategory.NORMAL_LITERAL) {
            SimulationRealDistributionType duration =
                    SimulationFactory.eINSTANCE
                            .createSimulationRealDistributionType();
            NormalRealDistribution normalDist =
                    SimulationFactory.eINSTANCE.createNormalRealDistribution();
            normalDist.setMean(getDefaultValue(normalMeanText,
                    NORMAL_DIST_MEAN_DEFAULT));
            normalDist
                    .setStandardDeviation(getDefaultValue(normalStandardDeviationText,
                            NORMAL_DIST_STANDARD_DEVIATION_DEFAULT));
            duration.setBasicDistribution(normalDist);

            cmd =
                    SetCommand.create(ed,
                            activitySimData,
                            SimulationPackage.eINSTANCE
                                    .getActivitySimulationDataType_Duration(),
                            duration);
            compoundCmd.append(cmd);
        } else if (selectedDist == RealDistributionCategory.EXPONENTIAL_LITERAL) {
            SimulationRealDistributionType duration =
                    SimulationFactory.eINSTANCE
                            .createSimulationRealDistributionType();
            ExponentialRealDistribution exponentialDist =
                    SimulationFactory.eINSTANCE
                            .createExponentialRealDistribution();
            exponentialDist.setMean(getDefaultValue(exponentialMeanText,
                    EXPONENTIAL_DIST_MEAN_DEFAULT));
            duration.setBasicDistribution(exponentialDist);

            cmd =
                    SetCommand.create(ed,
                            activitySimData,
                            SimulationPackage.eINSTANCE
                                    .getActivitySimulationDataType_Duration(),
                            duration);
            compoundCmd.append(cmd);
        } else if (selectedDist == RealDistributionCategory.EMPIRICAL_LITERAL) {
            SimulationRealDistributionType duration =
                    SimulationFactory.eINSTANCE
                            .createSimulationRealDistributionType();
            ExternalEmpiricalDistribution exponentialDist =
                    SimulationFactory.eINSTANCE
                            .createExternalEmpiricalDistribution();
            duration.setBasicDistribution(exponentialDist);
            cmd =
                    SetCommand.create(ed,
                            activitySimData,
                            SimulationPackage.eINSTANCE
                                    .getActivitySimulationDataType_Duration(),
                            duration);
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
     * @return The text as a double, or the default if the text is invalid.
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
        Activity activity = (Activity) getInput();
        if (activity != null) {
            ActivitySimulationDataType actSimData =
                    getActivitySimulationData(activity);

            if (actSimData != null) {
                SimulationRealDistributionType duration =
                        actSimData.getDuration();
                TimeDisplayUnitType currentTimeUnit =
                        actSimData.getDisplayTimeUnit();
                timeUnitComboViewer.setSelection(new StructuredSelection(
                        currentTimeUnit), true);
                EObject distribution = duration.getBasicDistribution();
                if (distribution == null) {
                    distribution = duration.getParameterBasedDistribution();
                }

                boolean hasParticipantCosstAssigned = false;
                double participantCost = 0.0D;

                Performer performer = null;
                EList performers = activity.getPerformerList();

                if (performers != null && performers.size() > 0) {
                    performer = (Performer) performers.get(0);
                }

                Participant actParticipant =
                        findParticipant(activity, performer);
                if (actParticipant != null) {
                    ParticipantSimulationDataType partSimData =
                            getParticipantSimulationData(actParticipant);
                    if (partSimData != null) {
                        hasParticipantCosstAssigned = true;
                        participantCost =
                                partSimData.getTimeUnitCost().getCost();
                    }
                }
                if (distribution instanceof ConstantRealDistribution) {
                    ConstantRealDistribution constDist =
                            (ConstantRealDistribution) distribution;
                    durationTypeComboViewer
                            .setSelection(new StructuredSelection(
                                    RealDistributionCategory.CONSTANT_LITERAL),
                                    true);
                    book.showPage(RealDistributionCategory.CONSTANT_LITERAL);
                    updateText(constantValueText, DisplayTimeUnitConverter
                            .convertToString(xpdlTimeUnit,
                                    currentTimeUnit,
                                    constDist.getConstantValue()));

                    if (hasParticipantCosstAssigned) {
                        updateConstantCostLabel(participantCost, constDist
                                .getConstantValue());
                    }
                }
                if (distribution instanceof UniformRealDistribution) {
                    UniformRealDistribution uniformDist =
                            (UniformRealDistribution) distribution;
                    durationTypeComboViewer
                            .setSelection(new StructuredSelection(
                                    RealDistributionCategory.UNIFORM_LITERAL),
                                    true);
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
                        updateUniformCostLabel(participantCost, uniformDist
                                .getLowerBorder(), uniformDist.getUpperBorder());
                    }
                }
                if (distribution instanceof NormalRealDistribution) {
                    NormalRealDistribution normalDist =
                            (NormalRealDistribution) distribution;
                    durationTypeComboViewer
                            .setSelection(new StructuredSelection(
                                    RealDistributionCategory.NORMAL_LITERAL),
                                    true);
                    book.showPage(RealDistributionCategory.NORMAL_LITERAL);
                    updateText(normalMeanText, DisplayTimeUnitConverter
                            .convertToString(xpdlTimeUnit,
                                    currentTimeUnit,
                                    normalDist.getMean()));
                    updateText(normalStandardDeviationText,
                            DisplayTimeUnitConverter
                                    .convertToString(xpdlTimeUnit,
                                            currentTimeUnit,
                                            normalDist.getStandardDeviation()));
                    if (hasParticipantCosstAssigned) {
                        updateNormalCostLabel(participantCost, normalDist
                                .getMean());
                    }
                }
                if (distribution instanceof ExponentialRealDistribution) {
                    ExponentialRealDistribution exponentialDist =
                            (ExponentialRealDistribution) distribution;
                    durationTypeComboViewer
                            .setSelection(new StructuredSelection(
                                    RealDistributionCategory.EXPONENTIAL_LITERAL),
                                    true);
                    book.showPage(RealDistributionCategory.EXPONENTIAL_LITERAL);
                    updateText(exponentialMeanText, DisplayTimeUnitConverter
                            .convertToString(xpdlTimeUnit,
                                    currentTimeUnit,
                                    exponentialDist.getMean()));
                    if (hasParticipantCosstAssigned) {
                        updateExponentialCostLabel(participantCost,
                                exponentialDist.getMean());
                    }
                }
                if (distribution instanceof ExternalEmpiricalDistribution) {
                    durationTypeComboViewer
                            .setSelection(new StructuredSelection(
                                    RealDistributionCategory.EMPIRICAL_LITERAL),
                                    true);
                    book.showPage(RealDistributionCategory.EMPIRICAL_LITERAL);
                }
                if (distribution instanceof ParameterBasedDistribution) {
                    durationTypeCCombo
                            .setText(PropertiesMessage
                                    .getString("ActivitySimulationExistSection.ParameterBased_value")); //$NON-NLS-1$
                    book
                            .showPage(RealDistributionCategory.PARAMETER_BASED_LITERAL);
                    enumValuesTableViewer.setInput(distribution);
                    enumValuesTableViewer.refresh();
                }

                Double maxDelay = actSimData.getSlaMaximumDelay();
                updateText(slaMaximumDelayText,
                        maxDelay == null ? "" : maxDelay //$NON-NLS-1$
                                .toString());
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

        ActivitySimulationDataType actSimData =
                getActivitySimulationData(activity);
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
            EList performers = activity.getPerformerList();

            if (performers != null && performers.size() > 0) {
                performer = (Performer) performers.get(0);
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
            if (object.equals(slaMaximumDelayText)) {
                Double maxDelay = null;
                try {
                    String text = slaMaximumDelayText.getText();
                    if (text != null && text.length() != 0) {
                        maxDelay = new Double(text);
                    }
                } catch (NumberFormatException e) {
                    // Ignore
                }
                cmd.append(SetCommand.create(ed,
                        actSimData,
                        slaMaximumDelayText.getData(FEATURE),
                        maxDelay));
            }
            if (selectedDist == RealDistributionCategory.CONSTANT_LITERAL) {
                ConstantRealDistribution constDist =
                        (ConstantRealDistribution) actSimData.getDuration()
                                .getBasicDistribution();
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
                        updateConstantCostLabel(participantCost,
                                valueDouble == null ? 0 : valueDouble
                                        .doubleValue());
                    }
                }
            } else if (selectedDist == RealDistributionCategory.UNIFORM_LITERAL) {
                UniformRealDistribution uniformDist =
                        (UniformRealDistribution) actSimData.getDuration()
                                .getBasicDistribution();
                Double uniformMinValue =
                        DisplayTimeUnitConverter
                                .convertToDouble(currentTimeUnit,
                                        xpdlTimeUnit,
                                        uniformMinValueText.getText());
                Double uniformMaxValue =
                        DisplayTimeUnitConverter
                                .convertToDouble(currentTimeUnit,
                                        xpdlTimeUnit,
                                        uniformMaxValueText.getText());
                if (object.equals(uniformMinValueText)) {
                    if (uniformMinValue == null) {
                        cmd.append(UnexecutableCommand.INSTANCE);
                    } else {
                        cmd
                                .append(SetCommand
                                        .create(ed,
                                                uniformDist,
                                                SimulationPackage.eINSTANCE
                                                        .getUniformRealDistribution_LowerBorder(),
                                                uniformMinValue));
                    }
                    if (hasParticipantCosstAssigned) {
                        updateUniformCostLabel(participantCost,
                                uniformMinValue == null ? 0 : uniformMinValue
                                        .doubleValue(),
                                uniformMaxValue == null ? 0 : uniformMaxValue
                                        .doubleValue());
                    }
                } else if (object.equals(uniformMaxValueText)) {
                    if (uniformMaxValue == null) {
                        cmd.append(UnexecutableCommand.INSTANCE);
                    } else {
                        cmd
                                .append(SetCommand
                                        .create(ed,
                                                uniformDist,
                                                SimulationPackage.eINSTANCE
                                                        .getUniformRealDistribution_UpperBorder(),
                                                uniformMaxValue));
                    }
                    if (hasParticipantCosstAssigned) {
                        updateUniformCostLabel(participantCost,
                                uniformMinValue == null ? 0 : uniformMinValue
                                        .doubleValue(),
                                uniformMaxValue == null ? 0 : uniformMaxValue
                                        .doubleValue());
                    }
                }
            } else if (selectedDist == RealDistributionCategory.NORMAL_LITERAL) {
                NormalRealDistribution normalDist =
                        (NormalRealDistribution) actSimData.getDuration()
                                .getBasicDistribution();
                Double mean =
                        DisplayTimeUnitConverter
                                .convertToDouble(currentTimeUnit,
                                        xpdlTimeUnit,
                                        normalMeanText.getText());
                if (object.equals(normalMeanText)) {
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
                        updateNormalCostLabel(participantCost, mean == null ? 0
                                : mean.doubleValue());
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
                        updateNormalCostLabel(participantCost, mean == null ? 0
                                : mean.doubleValue());
                    }
                }
            } else if (selectedDist == RealDistributionCategory.EXPONENTIAL_LITERAL) {
                ExponentialRealDistribution exponentialDist =
                        (ExponentialRealDistribution) actSimData.getDuration()
                                .getBasicDistribution();
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
                                mean == null ? 0 : mean.doubleValue());
                    }
                }
            }
        }
        return cmd;
    }

    /**
     * @param participantCost
     *            The participant cost.
     * @param constValue
     *            The cost per time unit.
     */
    private void updateConstantCostLabel(double participantCost,
            double constValue) {
        double actCost = participantCost * constValue;
        constantCostLabel.setText(String.format(PropertiesMessage
                .getString("ActivitySimulationExistSection.EstimatedCost"), //$NON-NLS-1$
                DisplayTimeUnitConverter.convertToString(xpdlTimeUnit,
                        xpdlTimeUnit,
                        actCost,
                        ACTIVITY_COST_FORMAT)));

    }

    /**
     * @param participantCost
     *            Participant cost.
     * @param lowerBorder
     *            Lower cost.
     * @param upperBorder
     *            Upper cost.
     */
    private void updateUniformCostLabel(double participantCost,
            double lowerBorder, double upperBorder) {
        double actCost =
                participantCost
                        * (lowerBorder + (upperBorder - lowerBorder) / 2);
        uniformCostLabel.setText(String.format(PropertiesMessage
                .getString("ActivitySimulationExistSection.EstimatedMean"), //$NON-NLS-1$
                DisplayTimeUnitConverter.convertToString(xpdlTimeUnit,
                        xpdlTimeUnit,
                        actCost,
                        ACTIVITY_COST_FORMAT)));
    }

    /**
     * @param participantCost
     *            Participant cost.
     * @param mean
     *            Mean cost.
     */
    private void updateNormalCostLabel(double participantCost, double mean) {
        double actCost = participantCost * mean;
        normalCostLabel.setText(String.format(PropertiesMessage
                .getString("ActivitySimulationExistSection.EstimatedMean"), //$NON-NLS-1$
                DisplayTimeUnitConverter.convertToString(xpdlTimeUnit,
                        xpdlTimeUnit,
                        actCost,
                        ACTIVITY_COST_FORMAT)));
    }

    /**
     * @param participantCost
     *            Participant cost.
     * @param mean
     *            Mean cost.
     */
    private void updateExponentialCostLabel(double participantCost, double mean) {
        double actCost = participantCost * mean;
        exponentialCostLabel.setText(String.format(PropertiesMessage
                .getString("ActivitySimulationExistSection.EstimatedMean"), //$NON-NLS-1$
                DisplayTimeUnitConverter.convertToString(xpdlTimeUnit,
                        xpdlTimeUnit,
                        actCost,
                        ACTIVITY_COST_FORMAT)));
    }

    /**
     * Gets AstivitySimulationData for Activity if this data exists otherwise
     * returns null.
     * 
     * @param activity
     *            xpdl activity.
     * @return AstivitySimulationData for Activity if this data exists otherwise
     *         returns null.
     */
    private ActivitySimulationDataType getActivitySimulationData(
            Activity activity) {
        for (Iterator iter = activity.getExtendedAttributes().iterator(); iter
                .hasNext();) {
            ExtendedAttribute ea = (ExtendedAttribute) iter.next();
            if ("ActivitySimulationData".equals(ea.getName())) { //$NON-NLS-1$
                // found extended attribute
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
     * @param e
     *            The text modify event.
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
        ActivitySimulationDataType actSimData =
                getActivitySimulationData(activity);
        if (actSimData == null) {
            e.doit = false;
            return;
        }
        if (textControl == slaMaximumDelayText) {
            try {
                new Double(t);
            } catch (NumberFormatException ex) {
                e.doit = false;
            }
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

        if (selectedDist == RealDistributionCategory.CONSTANT_LITERAL) {
            ConstantRealDistribution constDist =
                    (ConstantRealDistribution) actSimData.getDuration()
                            .getBasicDistribution();
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
                    (UniformRealDistribution) actSimData.getDuration()
                            .getBasicDistribution();
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
                    (NormalRealDistribution) actSimData.getDuration()
                            .getBasicDistribution();
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
                    (ExponentialRealDistribution) actSimData.getDuration()
                            .getBasicDistribution();
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
     *            The EObject.
     * @param feature
     *            The feature to check.
     * @param newValue
     *            The new value to test.
     * @return true if the feature can be modified on the given EObject.
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
     *            The participant.
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
     * @param object
     *            The input object.
     * @return true if the property section should show.
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
     *            The activity.
     * @return true if the activity has simulation data.
     */
    private boolean simDataExist(EObject act) {
        ActivitySimulationDataType simData =
                SimulationXpdlUtils.getActivitySimulationData((Activity) act);
        return simData != null;
    }

    /**
     * Enum values content provider.
     */
    private class EnumValuesTableContentProvider implements
            IStructuredContentProvider {
        /**
         * @param inputElement
         *            The input element.
         * @return An array of top level elements.
         * @see org.eclipse.jface.viewers.IStructuredContentProvider#
         *      getElements(java.lang.Object)
         */
        public Object[] getElements(Object inputElement) {
            if (inputElement instanceof ParameterBasedDistribution) {
                return ((ParameterBasedDistribution) inputElement)
                        .getParameterDependentDistributions().toArray();
            }
            return new ParameterDependentDistribution[] {};
        }

        /**
         * @param viewer
         *            The viewer.
         * @param oldInput
         *            The old input.
         * @param newInput
         *            The new input.
         * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
         *      java.lang.Object, java.lang.Object)
         */
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
            // nothing to do
        }

        /**
         * @see org.eclipse.jface.viewers.IContentProvider#dispose()
         */
        public void dispose() {
            // nothing to do
        }
    }

    /**
     * Enum values label provider.
     */
    private class EnumValuesTableLabelProvider extends LabelProvider implements
            ITableLabelProvider {

        /** Third column index. */
        private static final int COLUMN_3 = 3;

        /**
         * @param element
         *            The element to get the text for.
         * @param columnIndex
         *            The column index.
         * @return The text.
         * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object,
         *      int)
         */
        public String getColumnText(Object element, int columnIndex) {
            Activity activity = (Activity) getInput();
            ActivitySimulationDataType actSimData =
                    getActivitySimulationData(activity);
            TimeDisplayUnitType currentTimeUnit;
            if (actSimData != null) {
                currentTimeUnit = actSimData.getDisplayTimeUnit();
            } else {
                currentTimeUnit = xpdlTimeUnit;
            }

            ParameterDependentDistribution distData =
                    ((ParameterDependentDistribution) element);
            EnumBasedExpressionType expr = null;
            expr = distData.getExpression().getEnumBasedExpression();
            boolean defaultExpresion =
                    expr == null
                            && distData.getExpression().getDefault() != null;
            AbstractBasicDistribution dist = distData.getBasicDistribution();
            switch (columnIndex) {
            case 0:
                if (expr != null) {
                    return expr.getParamName().toString();
                } else if (defaultExpresion) {
                    return ""; //$NON-NLS-1$
                }
                return PropertiesMessage
                        .getString("ActivitySimulationExistSection.UnknownExpression"); //$NON-NLS-1$
            case 1:
                if (expr != null) {
                    return expr.getEnumValue().toString();
                }
                if (defaultExpresion) {
                    return PropertiesMessage
                            .getString("ActivitySimulationExistSection.Default"); //$NON-NLS-1$
                }
                return PropertiesMessage
                        .getString("ActivitySimulationExistSection.UnknownExpression"); //$NON-NLS-1$
            case 2:
                if (dist instanceof NormalRealDistribution) {
                    return DisplayTimeUnitConverter
                            .convertToString(xpdlTimeUnit,
                                    currentTimeUnit,
                                    ((NormalRealDistribution) dist).getMean(),
                                    DisplayTimeUnitConverter.DEFAULT_LOCALE,
                                    DisplayTimeUnitConverter.DEFAULT_SHORT_FORMAT);
                }
                return PropertiesMessage
                        .getString("ActivitySimulationExistSection.UnsupportedDistribution"); //$NON-NLS-1$
            case COLUMN_3:
                if (dist instanceof NormalRealDistribution) {
                    return DisplayTimeUnitConverter
                            .convertToString(xpdlTimeUnit,
                                    currentTimeUnit,
                                    ((NormalRealDistribution) dist)
                                            .getStandardDeviation(),
                                    DisplayTimeUnitConverter.DEFAULT_LOCALE,
                                    DisplayTimeUnitConverter.DEFAULT_SHORT_FORMAT);
                }
                return PropertiesMessage
                        .getString("ActivitySimulationExistSection.UnsupportedDistribution"); //$NON-NLS-1$
            default:
                break;
            }
            return ""; //$NON-NLS-1$
        }

        /**
         * @param element
         *            The element to get an image for.
         * @param columnIndex
         *            The column index.
         * @return null.
         * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object,
         *      int)
         */
        public Image getColumnImage(Object element, int columnIndex) {
            return null;
        }

    }

}
