package com.tibco.xpd.processeditor.xpdl2.properties.task;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.ActivityManagerEvent;
import org.eclipse.ui.activities.IActivityManagerListener;
import org.eclipse.ui.activities.WorkbenchActivityHelper;
import org.eclipse.ui.forms.widgets.ScrolledPageBook;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.views.properties.tabbed.ISection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.SashDividedFilteredTransactionalSection;
import com.tibco.xpd.processeditor.xpdl2.properties.script.MultiInstanceScriptSection;
import com.tibco.xpd.processeditor.xpdl2.properties.script.StdLoopExpressionSection;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.ActivityMarkerType;
import com.tibco.xpd.ui.properties.TextFieldVerifier;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.LoopMultiInstance;
import com.tibco.xpd.xpdl2.LoopStandard;
import com.tibco.xpd.xpdl2.MIFlowConditionType;
import com.tibco.xpd.xpdl2.MIOrderingType;
import com.tibco.xpd.xpdl2.TestTimeType;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

public class LoopControlSection extends SashDividedFilteredTransactionalSection
        implements TextFieldVerifier {

    /**
     * MI Flow Condition combo
     */
    private CCombo miFlowConditionCombo;

    /**
     * MI Ordering combo
     */
    private CCombo miOrderingCombo;

    /**
     * Standard Loop test time combo
     */
    private CCombo stdTestTimeCombo;

    /**
     * Std Max loop count
     */
    private Text stdLoopMaxCountText;

    private static final String INTEGER_PATTERN_STRING = "\\d*"; //$NON-NLS-1$

    private Pattern integerPattern;

    /** The section page book. */
    private ScrolledPageBook loopTypeBook;

    /** Last activity marker */
    private int lastActivityMarker;

    /** page book height hint. */
    private static final int PAGE_BOOK_HEIGHT = 80;

    private static final Integer NO_MARKER_KEY = 100;

    private static final Integer STD_LOOP_KEY = 101;

    private static final Integer MULTI_INSTANCE_KEY = 102;

    List<LoopMarkerDetailsSection> markerDetailSectionList = null;

    private LoopMarkerDetailsSection currentMarkerTypeSection = null;

    private final IActivityManagerListener activityListener;

    private ScrolledComposite detailsScrolledContainer;

    private static class MIOrderingTypeLabel {
        String label;

        MIFlowConditionType type;

        public MIOrderingTypeLabel(String label, MIFlowConditionType type) {
            this.label = label;
            this.type = type;
        }
    }

    private MIOrderingTypeLabel[] miOrderingTypes = new MIOrderingTypeLabel[] {
            new MIOrderingTypeLabel(
                    Messages.LoopControlSection_FlowCondition_All_label,
                    MIFlowConditionType.ALL_LITERAL),
            new MIOrderingTypeLabel(
                    Messages.LoopControlSection_FlowCondition_One_label,
                    MIFlowConditionType.ONE_LITERAL),
            new MIOrderingTypeLabel(
                    Messages.LoopControlSection_FlowCondition_None_label,
                    MIFlowConditionType.NONE_LITERAL),
            new MIOrderingTypeLabel(
                    Messages.LoopControlSection_FlowCondition_Complex_label,
                    MIFlowConditionType.COMPLEX_LITERAL)

    };

    /*
     * Marker specific controls are held in a page book (each marker type can
     * have its own page, we show correct one for currently selected task).
     */
    private PageBook markerTypePageBook;

    public LoopControlSection() {
        super(Xpdl2Package.eINSTANCE.getActivity(), Xpdl2ProcessEditorPlugin.ID
                + "LoopControlSection"); //$NON-NLS-1$
        integerPattern = Pattern.compile(INTEGER_PATTERN_STRING);
        setMinimumHeight(SWT.DEFAULT);
        markerDetailSectionList = new ArrayList<LoopMarkerDetailsSection>();
        LoopMarkerDetailsSection loopTypeSection =
                new LoopMarkerDetailsSection(LoopControlSection.STD_LOOP_KEY,
                        new StdLoopExpressionSection());
        markerDetailSectionList.add(loopTypeSection);
        loopTypeSection =
                new LoopMarkerDetailsSection(
                        LoopControlSection.MULTI_INSTANCE_KEY,
                        new MultiInstanceScriptSection());
        markerDetailSectionList.add(loopTypeSection);
        activityListener = new IActivityManagerListener() {
            @Override
            public void activityManagerChanged(
                    ActivityManagerEvent activityManagerEvent) {
                if (activityManagerEvent.haveEnabledActivityIdsChanged()) {
                    refreshDetails();
                }

            }
        };
    }

    /**
     * As we need to pass on the input to the right hand section, we need to
     * override this method.
     */
    @Override
    public void setInput(Collection<?> items) {
        super.setInput(items);
        Activity activity = getActivity();
        int activityMarker = LoopControlSection.NO_MARKER_KEY;
        if (activity != null) {
            Set<ActivityMarkerType> markerSet =
                    TaskObjectUtil.getMarkers(activity);
            if (markerSet != null) {
                for (ActivityMarkerType marker : markerSet) {
                    if (ActivityMarkerType.MARKER_LOOP_LITERAL.equals(marker)) {
                        activityMarker = LoopControlSection.STD_LOOP_KEY;
                        break;
                    } else if (ActivityMarkerType.MARKER_MULTIPLE_LITERAL
                            .equals(marker)) {
                        activityMarker = LoopControlSection.MULTI_INSTANCE_KEY;
                        break;
                    }
                }
            }
            lastActivityMarker = activityMarker;
        }
        // Update input for details section of activity markers.
        setDetailsPageInput(lastActivityMarker);
    }

    /**
     * Pass on the input to the details page.
     * 
     * @param currentActivityMarker
     */
    private void setDetailsPageInput(int currentActivityMarker) {
        for (LoopMarkerDetailsSection loopMarkerSection : markerDetailSectionList) {
            if (loopMarkerSection.markerType == currentActivityMarker) {
                loopMarkerSection.section.setInput(getPart(), getSelection());
            } else {
                loopMarkerSection.section.setInput(getPart(),
                        StructuredSelection.EMPTY);
            }
        }
    }

    @Override
    protected Composite createGeneralSection(Composite parent,
            XpdFormToolkit toolkit) {
        ScrolledComposite scrolledContainer =
                toolkit.createScrolledComposite(parent, SWT.V_SCROLL
                        | SWT.H_SCROLL);
        scrolledContainer.setExpandHorizontal(true);
        scrolledContainer.setExpandVertical(true);
        Composite container = toolkit.createComposite(scrolledContainer);
        scrolledContainer.setContent(container);
        final int cols = 2;
        container.setLayout(new GridLayout(cols, false));
        // page book for marker types
        createPageBook(container, toolkit, cols);
        return scrolledContainer;
    }

    private void createPageBook(Composite root, XpdFormToolkit toolkit, int cols) {

        loopTypeBook = toolkit.createPageBook(root, SWT.NONE);
        GridLayout bookGridLayout = new GridLayout();
        loopTypeBook.setLayout(bookGridLayout);
        GridData bookGridData = new GridData();
        bookGridData.grabExcessHorizontalSpace = true;
        bookGridData.grabExcessVerticalSpace = true;
        bookGridData.minimumHeight = PAGE_BOOK_HEIGHT;
        bookGridData.horizontalAlignment = SWT.FILL;
        bookGridData.verticalAlignment = SWT.FILL;
        bookGridData.horizontalSpan = 2;
        loopTypeBook.setLayoutData(bookGridData);
        createStandardLoopPage(toolkit, cols);
        createMultiInstancePage(toolkit, cols);
    }

    /**
     * @param toolkit
     *            The UI toolkit
     */
    private void createMultiInstancePage(XpdFormToolkit toolkit, int cols) {
        // constant
        Composite container = loopTypeBook.getContainer();
        Composite multiInstancePage = toolkit.createComposite(container);
        multiInstancePage.setLayout(new GridLayout(2, false));
        // MI_Ordering
        createLabel(multiInstancePage,
                toolkit,
                com.tibco.xpd.processeditor.xpdl2.internal.Messages.LoopControlSection_InstancesOrdering);
        miOrderingCombo = createMIOrderingCombo(multiInstancePage, toolkit);
        GridData gData = new GridData(GridData.FILL_HORIZONTAL);
        gData.widthHint = TEXT_WIDTH_HINT;
        gData.horizontalSpan = cols - 1;
        miOrderingCombo.setLayoutData(gData);
        // Flow Condition
        createLabel(multiInstancePage,
                toolkit,
                com.tibco.xpd.processeditor.xpdl2.internal.Messages.LoopControlSection_FlowCondition);
        miFlowConditionCombo =
                createFlowConditionCombo(multiInstancePage, toolkit);
        gData = new GridData(GridData.FILL_HORIZONTAL);
        gData.widthHint = TEXT_WIDTH_HINT;
        gData.horizontalSpan = cols - 1;
        miFlowConditionCombo.setLayoutData(gData);
        // for String array data field
        loopTypeBook.registerPage(LoopControlSection.MULTI_INSTANCE_KEY,
                multiInstancePage);
    }

    /**
     * @param toolkit
     *            The UI toolkit
     */
    private void createStandardLoopPage(XpdFormToolkit toolkit, int cols) {
        // constant
        Composite container = loopTypeBook.getContainer();
        Composite stdLoopPage = toolkit.createComposite(container);
        stdLoopPage.setLayout(new GridLayout(2, false));
        // Test time Combo
        createLabel(stdLoopPage,
                toolkit,
                com.tibco.xpd.processeditor.xpdl2.internal.Messages.LoopControlSection_TestTime);
        stdTestTimeCombo = createLoopTestTimeCombo(stdLoopPage, toolkit);
        GridData gData = new GridData(GridData.FILL_HORIZONTAL);
        gData.widthHint = TEXT_WIDTH_HINT;
        gData.horizontalSpan = cols - 1;
        stdTestTimeCombo.setLayoutData(gData);
        // Loop Maximum
        createLabel(stdLoopPage,
                toolkit,
                com.tibco.xpd.processeditor.xpdl2.internal.Messages.LoopControlSection_MaximumLoopCount);
        stdLoopMaxCountText = toolkit.createText(stdLoopPage, ""); //$NON-NLS-1$
        gData = new GridData(GridData.FILL_HORIZONTAL);
        gData.widthHint = TEXT_WIDTH_HINT;
        gData.horizontalSpan = cols - 1;
        stdLoopMaxCountText.setLayoutData(gData);
        manageControl(stdLoopMaxCountText);
        loopTypeBook.registerPage(LoopControlSection.STD_LOOP_KEY, stdLoopPage);
    }

    /**
     * 
     * @param parent
     * @param toolkit
     */
    @Override
    protected Composite createDetailsSection(Composite parent,
            XpdFormToolkit toolkit) {
        detailsScrolledContainer =
                toolkit.createScrolledComposite(parent, SWT.V_SCROLL
                        | SWT.H_SCROLL);
        detailsScrolledContainer.setExpandHorizontal(true);
        detailsScrolledContainer.setExpandVertical(true);
        markerTypePageBook = new PageBook(detailsScrolledContainer, SWT.NONE);
        toolkit.adapt(markerTypePageBook, false, false);
        detailsScrolledContainer.setContent(markerTypePageBook);
        createMarkerTypePages(toolkit, markerTypePageBook);
        return detailsScrolledContainer;
    }

    /**
     * Create the pages for the implementation part of this properties page
     * 
     * @param toolkit
     * @param pageBook
     */
    private void createMarkerTypePages(XpdFormToolkit toolkit, PageBook pageBook) {
        for (LoopMarkerDetailsSection loopMarkerSection : markerDetailSectionList) {
            loopMarkerSection.page = toolkit.createComposite(pageBook);
            loopMarkerSection.page.setLayoutData(new GridData(
                    GridData.FILL_BOTH));
            FillLayout fillLayout = new FillLayout();
            fillLayout.marginHeight = 0;
            fillLayout.marginWidth = 0;
            loopMarkerSection.page.setLayout(fillLayout);
            loopMarkerSection.section.createControls(loopMarkerSection.page,
                    getPropertySheetPage());
        }
    }

    /**
     * Create the Activities type combo control
     * 
     * @param parent
     * @param toolkit
     * @return <code>CCombo</code>
     */
    @SuppressWarnings("unchecked")
    private CCombo createLoopTestTimeCombo(Composite parent,
            XpdFormToolkit toolkit) {
        CCombo combo = toolkit.createCCombo(parent, SWT.NONE);
        combo.setData(TabbedPropertySheetWidgetFactory.KEY_DRAW_BORDER,
                TabbedPropertySheetWidgetFactory.TEXT_BORDER);

        EList<EEnumLiteral> literals =
                Xpdl2Package.Literals.TEST_TIME_TYPE.getELiterals();
        for (EEnumLiteral enumLiteral : literals) {
            String name = Xpdl2ModelUtil.getEnumLocalisedName(enumLiteral);
            combo.add(name);
            combo.setData(name, enumLiteral);
        }
        combo.setEditable(false);
        manageControl(combo);
        return combo;
    }

    /**
     * Create the Activities type combo control
     * 
     * @param parent
     * @param toolkit
     * @return <code>CCombo</code>
     */
    @SuppressWarnings("unchecked")
    private CCombo createMIOrderingCombo(Composite parent,
            XpdFormToolkit toolkit) {

        CCombo combo = toolkit.createCCombo(parent, SWT.NONE);
        combo.setData(TabbedPropertySheetWidgetFactory.KEY_DRAW_BORDER,
                TabbedPropertySheetWidgetFactory.TEXT_BORDER);
        EList<EEnumLiteral> literals =
                Xpdl2Package.Literals.MI_ORDERING_TYPE.getELiterals();
        for (EEnumLiteral enumLiteral : literals) {
            String name = Xpdl2ModelUtil.getEnumLocalisedName(enumLiteral);
            combo.add(name);
            combo.setData(name, enumLiteral);
        }
        combo.setEditable(false);
        manageControl(combo);
        return combo;
    }

    /**
     * Create the Activities type combo control
     * 
     * @param parent
     * @param toolkit
     * @return <code>CCombo</code>
     */
    @SuppressWarnings("unchecked")
    private CCombo createFlowConditionCombo(Composite parent,
            XpdFormToolkit toolkit) {

        CCombo combo = toolkit.createCCombo(parent, SWT.NONE);

        for (MIOrderingTypeLabel miType : miOrderingTypes) {
            combo.add(miType.label);
            combo.setData(miType.label, miType.type);
        }

        combo.setEditable(false);
        manageControl(combo);
        return combo;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        AbstractCommand cmd = null;
        Activity activity = getActivity();
        EditingDomain ed = getEditingDomain();

        if (activity == null || ed == null) {
            return cmd;
        }
        if (obj == miFlowConditionCombo) {
            MIFlowConditionType newFlowConditionType =
                    (MIFlowConditionType) miFlowConditionCombo
                            .getData(miFlowConditionCombo.getText());
            MIFlowConditionType existingflowCondition =
                    TaskObjectUtil.getMIFlowCondition(activity);
            if (newFlowConditionType != null
                    && newFlowConditionType.getValue() != existingflowCondition
                            .getValue()) {
                // setting value of the flow condition
                LoopMultiInstance loopMultiInstance =
                        TaskObjectUtil.getLoopMultiInstance(activity);
                cmd =
                        (AbstractCommand) SetCommand
                                .create(ed,
                                        loopMultiInstance,
                                        Xpdl2Package.eINSTANCE
                                                .getLoopMultiInstance_MIFlowCondition(),
                                        newFlowConditionType);
                cmd.setLabel(com.tibco.xpd.processeditor.xpdl2.internal.Messages.LoopControlSection_SetFlowConditionCommand);
                return cmd;
            }
        } else if (obj == miOrderingCombo) {
            EEnumLiteral newOrderingType =
                    (EEnumLiteral) miOrderingCombo.getData(miOrderingCombo
                            .getText());
            MIOrderingType existingOrdering =
                    TaskObjectUtil.getMIOrdering(activity);
            if (newOrderingType != null
                    && newOrderingType.getValue() != existingOrdering
                            .getValue()) {
                // setting value of the flow condition
                LoopMultiInstance loopMultiInstance =
                        TaskObjectUtil.getLoopMultiInstance(activity);
                cmd =
                        (AbstractCommand) SetCommand.create(ed,
                                loopMultiInstance,
                                Xpdl2Package.eINSTANCE
                                        .getLoopMultiInstance_MIOrdering(),
                                MIOrderingType.get(newOrderingType.getValue()));
                cmd.setLabel(com.tibco.xpd.processeditor.xpdl2.internal.Messages.LoopControlSection_SetInstancesCommand);
                return cmd;
            }
        } else if (obj == stdTestTimeCombo) {
            EEnumLiteral newTestType =
                    (EEnumLiteral) stdTestTimeCombo.getData(stdTestTimeCombo
                            .getText());
            TestTimeType stdTestTime = TaskObjectUtil.getStdTestTime(activity);
            if (stdTestTime != null
                    && newTestType.getValue() != stdTestTime.getValue()) {
                // setting value of the flow condition
                LoopStandard standardLoop =
                        TaskObjectUtil.getStandardLoop(activity);
                cmd =
                        (AbstractCommand) SetCommand.create(ed,
                                standardLoop,
                                Xpdl2Package.eINSTANCE
                                        .getLoopStandard_TestTime(),
                                TestTimeType.get(newTestType.getValue()));
                cmd.setLabel(com.tibco.xpd.processeditor.xpdl2.internal.Messages.LoopControlSection_SetExpdEvaluationTime);
                return cmd;
            }
        } else if (obj == stdLoopMaxCountText) {
            String newLoopMaximum = stdLoopMaxCountText.getText();
            BigInteger newBigInteger = null;
            try {
                newBigInteger = new BigInteger(newLoopMaximum);
            } catch (NumberFormatException nfe) {
            }
            BigInteger stdLoopMaximum =
                    TaskObjectUtil.getStdLoopMaximum(activity);
            if (newBigInteger != null && !newBigInteger.equals(stdLoopMaximum)) {
                LoopStandard standardLoop =
                        TaskObjectUtil.getStandardLoop(activity);
                cmd =
                        (AbstractCommand) SetCommand.create(ed,
                                standardLoop,
                                Xpdl2Package.eINSTANCE
                                        .getLoopStandard_LoopMaximum(),
                                new BigInteger(newLoopMaximum));
                cmd.setLabel(com.tibco.xpd.processeditor.xpdl2.internal.Messages.LoopControlSection_SetMaxLoopInstances);
                return cmd;
            }
        }
        return null;
    }

    @Override
    protected void doRefresh() {
        super.doRefresh();
        refreshDetails();
    }

    /**
     * @param taskType
     */
    private void refreshDetails() {
        Activity activity = getActivity();
        EditingDomain ed = getEditingDomain();
        if (activity == null || ed == null) {
            return;
        }
        int activityMarker = LoopControlSection.NO_MARKER_KEY;
        Set<ActivityMarkerType> markerSet = TaskObjectUtil.getMarkers(activity);
        if (markerSet != null) {
            for (ActivityMarkerType marker : markerSet) {
                if (ActivityMarkerType.MARKER_LOOP_LITERAL.equals(marker)) {
                    activityMarker = LoopControlSection.STD_LOOP_KEY;
                    break;
                } else if (ActivityMarkerType.MARKER_MULTIPLE_LITERAL
                        .equals(marker)) {
                    activityMarker = LoopControlSection.MULTI_INSTANCE_KEY;
                    break;
                }
            }
        }
        // updating the details details on the general section
        if (activityMarker == LoopControlSection.STD_LOOP_KEY) {
            LoopStandard loopStandard = activity.getLoop().getLoopStandard();
            TestTimeType testTime = loopStandard.getTestTime();
            if (testTime != null) {
                stdTestTimeCombo.select(testTime.getValue());
                stdTestTimeCombo.setSelection(new Point(0, 0));
            }
            BigInteger loopMaximum = loopStandard.getLoopMaximum();
            if (loopMaximum != null) {
                updateText(stdLoopMaxCountText, loopMaximum.toString());
            } else {
                updateText(stdLoopMaxCountText, ""); //$NON-NLS-1$
            }
        } else if (activityMarker == LoopControlSection.MULTI_INSTANCE_KEY) {
            LoopMultiInstance loopMultiInstance =
                    activity.getLoop().getLoopMultiInstance();
            MIFlowConditionType flowCondition =
                    loopMultiInstance.getMIFlowCondition();
            if (flowCondition != null) {
                int idx = -1;
                for (int i = 0; i < miOrderingTypes.length; i++) {
                    MIOrderingTypeLabel miType = miOrderingTypes[i];

                    if (flowCondition.equals(miType.type)) {
                        idx = i;
                        break;
                    }
                }
                miFlowConditionCombo.select(idx);
                miFlowConditionCombo.setSelection(new Point(0, 0));
            }
            MIOrderingType ordering = loopMultiInstance.getMIOrdering();
            if (ordering != null) {
                miOrderingCombo.select(ordering.getValue());
            }
        } else {
            return;
        }
        loopTypeBook.showPage(activityMarker);
        LoopMarkerDetailsSection sectionToShow = null;
        for (LoopMarkerDetailsSection markerSection : markerDetailSectionList) {
            if (markerSection.markerType == activityMarker) {
                sectionToShow = markerSection;
                break;
            }
        }
        boolean showDetails = false;
        if (sectionToShow != null
                && sectionToShow.section instanceof IPluginContribution) {
            showDetails =
                    showDetails((IPluginContribution) sectionToShow.section);
        }
        showDetails = true;
        if (showDetails) {
            setSashPercentToLastUserSelected();
            if (markerTypePageBook != null) {
                // If it has changed then reset the inputs.
                if (sectionToShow != currentMarkerTypeSection) {
                    setDetailsPageInput(sectionToShow.markerType);

                    if (sectionToShow != null) {
                        markerTypePageBook.showPage(sectionToShow.page);
                    }

                    currentMarkerTypeSection = sectionToShow;
                }

                // Refresh whatever task type section is now current.
                if (currentMarkerTypeSection != null) {
                    currentMarkerTypeSection.section.refresh();

                    if (detailsScrolledContainer != null
                            && !detailsScrolledContainer.isDisposed()) {
                        /*
                         * Recalculate the size of the details' section scrolled
                         * composite
                         */
                        detailsScrolledContainer.setMinSize(markerTypePageBook
                                .computeSize(SWT.DEFAULT, SWT.DEFAULT));
                    }
                }
            }

        }

    }

    private boolean showDetails(IPluginContribution pluginContribution) {
        if (WorkbenchActivityHelper.filterItem(pluginContribution)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Get xpdl2 Activity from the input
     * 
     * @return <code>Activity</code> if input is valid, <b>null</b> otherwise.
     */
    private Activity getActivity() {
        Object o = getInput();
        if (o instanceof Activity) {
            return (Activity) o;
        }
        return null;
    }

    @Override
    public boolean select(Object toTest) {
        if (super.select(toTest)) {
            Activity activity = (Activity) getBaseSelectObject(toTest);
            if (activity == null) {
                return false;
            }
            Set<ActivityMarkerType> markers =
                    TaskObjectUtil.getMarkers(activity);
            if (markers != null && !markers.isEmpty()) {
                /*
                 * Enable the Loops tab only if the selected markers is either
                 * an Single instance or a Multi instance marker.
                 */
                if (markers.contains(ActivityMarkerType.MARKER_LOOP_LITERAL)
                        || markers
                                .contains(ActivityMarkerType.MARKER_MULTIPLE_LITERAL)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Controls and data for Task Type setting.
     */
    private class LoopMarkerDetailsSection {
        public int markerType;

        public Composite page = null;

        public ISection section = null;

        LoopMarkerDetailsSection(int markerType, ISection section) {
            this.markerType = markerType;
            this.section = section;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.properties.general.SashDividedSection#
     * aboutToBeHidden()
     */
    @Override
    public void aboutToBeHidden() {
        PlatformUI.getWorkbench().getActivitySupport().getActivityManager()
                .removeActivityManagerListener(activityListener);
        super.aboutToBeHidden();
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.properties.general.SashDividedSection#
     * aboutToBeShown()
     */
    @Override
    public void aboutToBeShown() {
        super.aboutToBeShown();
        PlatformUI.getWorkbench().getActivitySupport().getActivityManager()
                .addActivityManagerListener(activityListener);
    }

    @Override
    public void verifyText(Event event) {
        Text textControl = ((Text) event.widget);
        String t =
                textControl.getText(0, event.start - 1)
                        + event.text
                        + textControl.getText(event.end,
                                textControl.getCharCount() - 1);
        if ("".equals(t)) { //$NON-NLS-1$
            event.doit = true;
            return;
        }
        EObject sectionInput = getInput();
        if (!(sectionInput instanceof Activity)) {
            return;
        }
        Activity activity = (Activity) sectionInput;
        LoopStandard standardLoopScript =
                ProcessScriptUtil.getLoopStandard(activity);

        if (standardLoopScript == null) {
            return;
        }

        if (textControl == stdLoopMaxCountText) {
            if (!integerPattern.matcher(t).matches()) {
                event.doit = false;
            }
            return;
        }
    }

}
