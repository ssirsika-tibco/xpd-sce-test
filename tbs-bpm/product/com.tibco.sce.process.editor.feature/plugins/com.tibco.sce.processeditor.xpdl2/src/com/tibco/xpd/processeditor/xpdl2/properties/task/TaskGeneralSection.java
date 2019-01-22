/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EventObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.ActivityManagerEvent;
import org.eclipse.ui.activities.IActivityManagerListener;
import org.eclipse.ui.activities.WorkbenchActivityHelper;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ProcessEditorConfigurationUtil;
import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ProcessEditorObjectType;
import com.tibco.xpd.analyst.resources.xpdl2.propertytesters.XpdlFileContentPropertyTester;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDestinationUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessFeaturesUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.properties.SashDividedNamedElementSection;
import com.tibco.xpd.processeditor.xpdl2.properties.adhoc.AdhocConfigurationSection;
import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.util.FixedValueFieldAssistHelper;
import com.tibco.xpd.processeditor.xpdl2.properties.util.FixedValueFieldAssistHelper.FixedValueFieldProposalProvider;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.ActivityMarkerType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.ui.properties.AbstractTransactionalSection;
import com.tibco.xpd.ui.properties.AbstractXpdSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.util.CapabilityUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.DecisionFlowUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Task general section
 * 
 * @author wzurek
 */
public class TaskGeneralSection extends SashDividedNamedElementSection {

    /** Local id of the task process developer specific details. */
    public static final String TASK_DEVELOPER_DETAILS = "taskDeveloperDetails"; //$NON-NLS-1$

    /**
     * Controls and data for Task Type setting.
     */
    public class TaskTypeSection {
        public List<TaskType> taskTypes;

        public Composite page = null;

        public AbstractXpdSection section = null;

        public TaskTypeSection(TaskType taskType, AbstractXpdSection section) {
            this.taskTypes = Collections.singletonList(taskType);
            this.section = section;
        }

        public TaskTypeSection(List<TaskType> taskTypes,
                AbstractXpdSection section) {
            this.taskTypes = taskTypes;
            this.section = section;
        }

    }

    /**
     * Activity marker related controls.
     */
    private Button[] markersBut;

    private boolean isValidGenSection = false;

    /**
     * Performer (participant) controls...
     */
    private ParticipantSelectionSection participantSelectionSection;

    /**
     * Task type combo
     */
    private CCombo activityTypeCombo;

    /*
     * Type of task specific controls are held in a page book (each type can
     * have its own page, we show correct one for currently selected task). This
     * one is for the right hand side of splitter.
     */
    private PageBook taskTypeRightSidePageBook;

    private List<TaskTypeSection> taskTypeRightSideSections = Collections
            .emptyList();

    private TaskTypeSection currTaskRightSideTypeSection = null;

    /*
     * Type of task specific controls are held in a page book (each type can
     * have its own page, we show correct one for currently selected task). this
     * one is for the left hand side
     */
    private PageBook taskTypeLeftSidePageBook;

    private List<TaskTypeSection> taskTypeLeftSideSections = Collections
            .emptyList();

    private TaskTypeSection currTaskLeftSideTypeSection = null;

    private final IActivityManagerListener activityListener;

    private ScrolledComposite detailsScrolledContainer;

    private Set activityMarkers;

    // The last set type of task (for comparison during refresh tabs.
    private TaskType lastSetTaskType = TaskType.NONE_LITERAL;

    private Composite parametersControl;

    private FormText solutionDesignForm;

    private CommandStackListener wizardModelChangeListener;

    //
    // Lane selection controls (for wizards only!)
    private List<LaneListEntry> availableLanes = Collections.emptyList();

    private DecoratedField laneSelectionControl = null;

    private ScrolledComposite leftSideScrolledContainer;

    /**
     * Container that holds the Adhoc Configuration Hyperlink which points to
     * the Adhoc configuration tab.
     */
    private Composite adhocHyperLinkContainer;

    /**
     * Code...
     * 
     */
    public TaskGeneralSection() {
        super(Xpdl2Package.eINSTANCE.getActivity(), Xpdl2ProcessEditorPlugin.ID
                + "TaskGeneralSection"); //$NON-NLS-1$

        setMinimumHeight(SWT.DEFAULT);

        activityListener = new IActivityManagerListener() {
            @Override
            public void activityManagerChanged(
                    ActivityManagerEvent activityManagerEvent) {
                if (activityManagerEvent.haveEnabledActivityIdsChanged()) {
                    refreshTabs();
                    doRefreshImplementationSection();
                }

            }
        };
    }

    /**
     * Create the list of right hand side sections for different task types.
     */
    protected List<TaskTypeSection> createTaskTypeLeftSideSections() {
        List<TaskTypeSection> ttLeftSideSections =
                new ArrayList<TaskTypeSection>();

        TaskTypeSection tt =
                new TaskTypeSection(TaskType.NONE_LITERAL,
                        new EmptyTaskTypeSection());
        ttLeftSideSections.add(tt);

        tt =
                new TaskTypeSection(TaskType.USER_LITERAL,
                        new EmptyTaskTypeSection());
        ttLeftSideSections.add(tt);

        tt =
                new TaskTypeSection(TaskType.MANUAL_LITERAL,
                        new EmptyTaskTypeSection());
        ttLeftSideSections.add(tt);

        tt =
                new TaskTypeSection(TaskType.SERVICE_LITERAL,
                        new EmptyTaskTypeSection());
        ttLeftSideSections.add(tt);

        tt =
                new TaskTypeSection(TaskType.SCRIPT_LITERAL,
                        new EmptyTaskTypeSection());
        ttLeftSideSections.add(tt);

        tt =
                new TaskTypeSection(TaskType.SEND_LITERAL,
                        new ConfigureReplyActivitySection());
        ttLeftSideSections.add(tt);

        tt =
                new TaskTypeSection(TaskType.RECEIVE_LITERAL,
                        new EmptyTaskTypeSection());
        ttLeftSideSections.add(tt);

        tt =
                new TaskTypeSection(TaskType.REFERENCE_LITERAL,
                        new EmptyTaskTypeSection());
        ttLeftSideSections.add(tt);

        tt =
                new TaskTypeSection(TaskType.SUBPROCESS_LITERAL,
                        new EmptyTaskTypeSection());
        ttLeftSideSections.add(tt);

        tt =
                new TaskTypeSection(TaskType.EMBEDDED_SUBPROCESS_LITERAL,
                        new EmptyTaskTypeSection());
        ttLeftSideSections.add(tt);

        /*
         * ABPM-911: Add section for event sub-process.
         */
        tt =
                new TaskTypeSection(TaskType.EVENT_SUBPROCESS_LITERAL,
                        new EmptyTaskTypeSection());
        ttLeftSideSections.add(tt);

        return ttLeftSideSections;
    }

    /**
     * Create the list of right hand side sections for different task types.
     */
    protected List<TaskTypeSection> createTaskTypeRightSideSections() {
        List<TaskTypeSection> ttRightSideSections =
                new ArrayList<TaskTypeSection>();

        if (!isWizard()) {
            TaskTypeSection tt =
                    new TaskTypeSection(TaskType.NONE_LITERAL,
                            new EmptyTaskTypeSection());
            ttRightSideSections.add(tt);

            tt =
                    new TaskTypeSection(TaskType.USER_LITERAL,
                            new TaskTypeUserSection());
            ttRightSideSections.add(tt);

            tt =
                    new TaskTypeSection(TaskType.MANUAL_LITERAL,
                            new EmptyTaskTypeSection());
            ttRightSideSections.add(tt);

            tt =
                    new TaskTypeSection(TaskType.SERVICE_LITERAL,
                            new TaskImplementationDetailsSection(
                                    TaskType.SERVICE_LITERAL));
            ttRightSideSections.add(tt);

            /*
             * tt = new TaskTypeSection(TaskType.SCRIPT_LITERAL, new
             * TaskTypeScriptSection()); taskTypeSections.add(tt);
             */

            tt =
                    new TaskTypeSection(TaskType.SCRIPT_LITERAL,
                            new TaskScriptSection());
            ttRightSideSections.add(tt);

            tt =
                    new TaskTypeSection(TaskType.SEND_LITERAL,
                            new TaskImplementationDetailsSection(
                                    TaskType.SEND_LITERAL));
            ttRightSideSections.add(tt);

            tt =
                    new TaskTypeSection(TaskType.RECEIVE_LITERAL,
                            new TaskImplementationDetailsSection(
                                    TaskType.RECEIVE_LITERAL));
            ttRightSideSections.add(tt);

            tt =
                    new TaskTypeSection(TaskType.REFERENCE_LITERAL,
                            new TaskTypeReferenceSection());
            ttRightSideSections.add(tt);
            tt =
                    new TaskTypeSection(TaskType.SUBPROCESS_LITERAL,
                            new TaskTypeIndependentSubProcSection());
            ttRightSideSections.add(tt);

            /*
             * ABPM-911: Use same RHS section for both Embbeded and Event
             * sub-processes. Otherwise we get all sports of nasty things going
             * on swapping sections (which look identical) and setting input on
             * them because of that - righ tin the middle of rpessing radio
             * button for task type.
             */
            List<TaskType> embTaskTypes = new ArrayList<TaskType>();
            embTaskTypes.add(TaskType.EMBEDDED_SUBPROCESS_LITERAL);
            embTaskTypes.add(TaskType.EVENT_SUBPROCESS_LITERAL);

            tt =
                    new TaskTypeSection(embTaskTypes,
                            new TaskTypeEmbeddedSubProcSection());
            ttRightSideSections.add(tt);

        }
        return ttRightSideSections;
    }

    @Override
    public void setInput(Collection<?> items) {
        // System.out.println("==> TaskGeneralSection.setInput() (Thread:
        // "+Thread.currentThread().getId()+")"); //$NON-NLS-1$ //$NON-NLS-2$
        super.setInput(items);

        if (isWizard()) {
            //
            // When in wizard mode, create and add a listener to the 'dummy'
            // editing domain created for the wizard to run in - this listener
            // can then do the section refresh.
            if (wizardModelChangeListener == null) {
                wizardModelChangeListener = new CommandStackListener() {
                    @Override
                    public void commandStackChanged(EventObject event) {
                        Display.getCurrent().syncExec(new Runnable() {

                            @Override
                            public void run() {
                                refresh();
                            }
                        });
                    }
                };
                getEditingDomain().getCommandStack()
                        .addCommandStackListener(wizardModelChangeListener);
            }
        }

        Activity activity = getActivity();
        TaskType taskType = null;

        if (activity != null) {
            taskType = TaskObjectUtil.getTaskType(activity);
            lastSetTaskType = taskType;
            activityMarkers = TaskObjectUtil.getMarkers(activity);
        }

        // Update input for task type sections
        setTaskTypeRightSidePageInput(taskType);

        setTaskTypeLeftSidePageInput(taskType);

        if (participantSelectionSection != null) {
            participantSelectionSection.setInput(items);
        }

        // System.out.println("<== TaskGeneralSection.setInput() (Thread:
        // "+Thread.currentThread().getId()+")"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    @Override
    public void dispose() {
        if (wizardModelChangeListener != null && getEditingDomain() != null) {
            getEditingDomain().getCommandStack()
                    .removeCommandStackListener(wizardModelChangeListener);
            wizardModelChangeListener = null;
        }

        for (TaskTypeSection tt : taskTypeRightSideSections) {
            tt.section.dispose();
        }

        for (TaskTypeSection tt : taskTypeLeftSideSections) {
            tt.section.dispose();
        }

        if (participantSelectionSection != null) {
            participantSelectionSection.dispose();
        }

        super.dispose();
    }

    @Override
    protected Composite doCreateDetailsSection(Composite parent,
            XpdFormToolkit toolkit) {
        taskTypeRightSideSections = createTaskTypeRightSideSections();

        // ScrolledComposite scrolledContainer =
        // toolkit.createScrolledComposite(
        detailsScrolledContainer =
                toolkit.createScrolledComposite(parent, SWT.V_SCROLL
                        | SWT.H_SCROLL);
        detailsScrolledContainer.setExpandHorizontal(true);
        detailsScrolledContainer.setExpandVertical(true);
        taskTypeRightSidePageBook =
                new PageBook(detailsScrolledContainer, SWT.NONE);
        toolkit.adapt(taskTypeRightSidePageBook, false, false);
        detailsScrolledContainer.setContent(taskTypeRightSidePageBook);

        createRightSideTaskTypePages(toolkit, taskTypeRightSidePageBook);

        if (taskTypeRightSideSections.isEmpty()) {
            setSashPercent(1.0f);
        }

        return detailsScrolledContainer;
    }

    @Override
    protected Composite doCreateGeneralSection(Composite parent,
            XpdFormToolkit toolkit) {
        taskTypeLeftSideSections = createTaskTypeLeftSideSections();

        int textWidthHint;
        textWidthHint = TEXT_WIDTH_HINT;
        leftSideScrolledContainer =
                toolkit.createScrolledComposite(parent, SWT.V_SCROLL
                        | SWT.H_SCROLL);

        leftSideScrolledContainer.setExpandHorizontal(true);
        leftSideScrolledContainer.setExpandVertical(true);

        Composite mainContainer =
                toolkit.createComposite(leftSideScrolledContainer);
        leftSideScrolledContainer.setContent(mainContainer);

        GridLayout mainContLayout = new GridLayout(1, false);
        mainContLayout.marginHeight = 0;
        mainContainer.setLayout(mainContLayout);

        //
        // In Wizard mode, allow the user to select the lane.
        Label laneLabel = null;
        if (isWizard()) {
            Composite laneSelectContainer =
                    toolkit.createComposite(mainContainer);
            laneSelectContainer.setLayoutData(new GridData(
                    GridData.FILL_HORIZONTAL));
            GridLayout laneSelContLayout = new GridLayout(2, false);
            laneSelContLayout.marginHeight = 0;
            laneSelectContainer.setLayout(laneSelContLayout);

            if (isTaskLibraryActivity()) {
                laneLabel =
                        toolkit.createLabel(laneSelectContainer,
                                Messages.TaskGeneralSection_TaskSet_label);
            } else {
                laneLabel =
                        toolkit.createLabel(laneSelectContainer,
                                Messages.TaskGeneralSection_Lane_label);
            }
            laneLabel.setLayoutData(new GridData());

            Control layoutControl =
                    createLaneSelectionControls(toolkit, laneSelectContainer);

            GridData lanSelGd = new GridData(GridData.FILL_HORIZONTAL);
            layoutControl.setLayoutData(lanSelGd);
        }

        Composite markersParticsAndTypeContainer =
                toolkit.createComposite(mainContainer);
        markersParticsAndTypeContainer.setLayoutData(new GridData(
                GridData.FILL_BOTH));

        final int containerLayoutColumns = 4;
        GridData gData;
        GridLayout mptContainerLayout =
                new GridLayout(containerLayoutColumns, false);
        mptContainerLayout.marginHeight = 0;
        markersParticsAndTypeContainer.setLayout(mptContainerLayout);

        //
        // Activity markers
        Label markerLabel =
                createLabel(markersParticsAndTypeContainer,
                        toolkit,
                        Messages.TaskGeneralSection_ACTIVITY_MARKERS_LABEL);
        markerLabel.setLayoutData(new GridData());
        Composite markers =
                createActivityMarkers(markersParticsAndTypeContainer, toolkit);
        gData = new GridData(GridData.FILL_HORIZONTAL);
        gData.horizontalSpan = containerLayoutColumns - 1;
        gData.widthHint = textWidthHint;
        markers.setLayoutData(gData);

        createAdhocConfigHyperLink(markersParticsAndTypeContainer, toolkit);
        //
        // Participant selection.
        Composite particComposite =
                toolkit.createComposite(markersParticsAndTypeContainer);
        gData = new GridData(GridData.FILL_HORIZONTAL);
        gData.horizontalSpan = containerLayoutColumns;
        particComposite.setLayoutData(gData);

        FillLayout fl = new FillLayout();
        fl.marginHeight = 0;
        fl.marginWidth = 0;
        particComposite.setLayout(fl);

        participantSelectionSection =
                new ParticipantSelectionSection(particComposite.getParent());
        if (getInput() != null) {
            participantSelectionSection.setInput(Collections
                    .singletonList(getInput()));
        }

        if (isWizard()) {
            participantSelectionSection
                    .createControls(particComposite, toolkit);
            participantSelectionSection
                    .setXpdSectionContainerProvider(getXpdSectionContainerProvider());
        } else {
            participantSelectionSection.createControls(particComposite,
                    getPropertySheetPage());
            participantSelectionSection
                    .setXpdSectionContainerProvider(getXpdSectionContainerProvider());
        }

        //
        // Activity types
        Label typeLabel =
                createLabel(markersParticsAndTypeContainer,
                        toolkit,
                        Messages.TaskGeneralSection_ACTIVITY_TYPE_LABEL);
        typeLabel.setLayoutData(new GridData());

        Composite pad = toolkit.createComposite(markersParticsAndTypeContainer);
        gData = new GridData(GridData.FILL_HORIZONTAL);
        gData.widthHint = textWidthHint;
        gData.horizontalSpan = containerLayoutColumns - 1;
        pad.setLayoutData(gData);

        GridLayout gl = new GridLayout(1, false);
        gl.marginWidth = 1;
        gl.marginHeight = 2;
        gl.marginLeft = 0;
        pad.setLayout(gl);

        activityTypeCombo = createActivityTypeCombo(pad, toolkit);
        activityTypeCombo.setData("name", "comboTaskActivityType");//$NON-NLS-1$//$NON-NLS-2$
        gData = new GridData(GridData.FILL_HORIZONTAL);
        activityTypeCombo.setLayoutData(gData);

        //
        // Create the task type specific page book for left hand side.
        //
        if (!isWizard()) {
            Composite filler =
                    toolkit.createComposite(markersParticsAndTypeContainer);
            gl = new GridLayout();
            gl.marginHeight = 1;
            filler.setLayout(gl);
            filler.setLayoutData(new GridData());

            taskTypeLeftSidePageBook =
                    new PageBook(markersParticsAndTypeContainer, SWT.NONE);
            toolkit.adapt(taskTypeLeftSidePageBook, false, false);

            GridData leftSidePageLayoutData = new GridData(GridData.FILL_BOTH);
            leftSidePageLayoutData.horizontalSpan = containerLayoutColumns - 1;
            leftSidePageLayoutData.minimumHeight = 10;
            taskTypeLeftSidePageBook.setLayoutData(leftSidePageLayoutData);

            createLeftSideTaskTypePages(toolkit, taskTypeLeftSidePageBook);
        }

        // Align the controls to right of labels.
        List<Control> labels = new ArrayList<Control>();
        Map<Control, Integer> adjustments = new HashMap<Control, Integer>();
        if (isWizard()) {
            labels.add(laneLabel);

            // Want to line the edit box of library task path with the edit box
            // part of the decorated local task content assist control so have
            // to allow 8 pixels for lightbulb.
            adjustments.put(laneLabel, 8);
        }

        labels.add(markerLabel);
        labels.add(participantSelectionSection.getParticLabel());
        labels.add(typeLabel);

        setSameGridDataWidth(labels, adjustments);

        //
        // Create the 'switch to solution designer mode' hyperlink.
        //
        if (!isWizard()) {
            solutionDesignForm =
                    toolkit.createFormText(markersParticsAndTypeContainer, true);

            /*
             * Must ensure we set SOME text on FormText before we do first
             * layout else subsequent ones will do nothing!
             */
            solutionDesignForm
                    .setText(Messages.AddSolutionDesignCapability_form,
                            true,
                            false);

            gData =
                    new GridData(GridData.FILL_HORIZONTAL
                            | GridData.HORIZONTAL_ALIGN_END);
            gData.horizontalSpan = containerLayoutColumns;
            solutionDesignForm.setLayoutData(gData);
            manageControl(solutionDesignForm);

            leftSideScrolledContainer.setMinSize(markersParticsAndTypeContainer
                    .computeSize(SWT.DEFAULT, SWT.DEFAULT));
        }

        return leftSideScrolledContainer;
    }

    /**
     * @param markersParticsAndTypeContainer
     * @param toolkit
     */
    private void createAdhocConfigHyperLink(
            Composite markersParticsAndTypeContainer, XpdFormToolkit toolkit) {

        // Inserting a blank label as we want the Ad-Hoc Properties Hyperlink in
        // the second column.
        Label dummyLabel =
                toolkit.createLabel(markersParticsAndTypeContainer, ""); //$NON-NLS-1$
        GridData labelData = new GridData();
        labelData.heightHint = 0;
        dummyLabel.setLayoutData(labelData);

        adhocHyperLinkContainer =
                toolkit.createComposite(markersParticsAndTypeContainer);
        adhocHyperLinkContainer.setLayout(new FillLayout());
        adhocHyperLinkContainer.setLayoutData(new GridData(GridData.BEGINNING,
                GridData.VERTICAL_ALIGN_BEGINNING, false, false));

        Hyperlink adhocConfigHyperLink =
                toolkit.createHyperlink(adhocHyperLinkContainer,
                        Messages.TaskGeneralSection_AdhocConfigPropertiesHyperLink_label,
                        SWT.NONE);

        adhocConfigHyperLink.addHyperlinkListener(new IHyperlinkListener() {

            @Override
            public void linkActivated(HyperlinkEvent e) {
                showPropertyTab(AdhocConfigurationSection.TAB_ID);
            }

            @Override
            public void linkEntered(HyperlinkEvent e) {
                // Do nothing
            }

            @Override
            public void linkExited(HyperlinkEvent e) {
                // Do nothing
            }

        });
    }

    /**
     * @param toolkit
     * @param container
     */
    private Control createLaneSelectionControls(XpdFormToolkit toolkit,
            Composite container) {
        FixedValueFieldProposalProvider proposalProvider =
                new FixedValueFieldAssistHelper.FixedValueFieldProposalProvider() {
                    @Override
                    public Object[] getProposals() {
                        if (availableLanes != null) {
                            return availableLanes.toArray();
                        }
                        return null;
                    }
                };

        FixedValueFieldAssistHelper laneHelper =
                new FixedValueFieldAssistHelper(toolkit, container,
                        proposalProvider, true);

        laneHelper
                .addValueChangedListener(new FixedValueFieldAssistHelper.FixedValueFieldChangedListener() {
                    @Override
                    public void fixedValueFieldChanged(Object newValue) {
                        Activity act = getActivity();
                        Process process = getProcess();

                        if (act != null && process != null) {

                            Object laneId = SetCommand.UNSET_VALUE;

                            if (newValue instanceof LaneListEntry) {
                                laneId =
                                        ((LaneListEntry) newValue).getLane()
                                                .getId();

                            }

                            CompoundCommand cmd = new CompoundCommand();

                            NodeGraphicsInfo ngi =
                                    Xpdl2ModelUtil
                                            .getOrCreateNodeGraphicsInfo(act,
                                                    getEditingDomain(),
                                                    cmd);

                            cmd.append(SetCommand.create(getEditingDomain(),
                                    ngi,
                                    Xpdl2Package.eINSTANCE
                                            .getNodeGraphicsInfo_LaneId(),
                                    laneId));
                            if (cmd.canExecute()) {
                                getEditingDomain().getCommandStack()
                                        .execute(cmd);
                            }
                        }

                    }
                });

        laneSelectionControl = laneHelper.getDecoratedField();

        laneSelectionControl.getLayoutControl()
                .setBackground(container.getBackground());

        return laneSelectionControl.getLayoutControl();
    }

    @Override
    protected Command doGetDetailsCommand(Object obj) {
        Command cmd = null;

        Activity activity = getActivity();
        Process process = getProcess();

        EditingDomain ed = getEditingDomain();

        if (activity != null && ed != null) {

            if (obj instanceof Button) {
                Object data = ((Button) obj).getData();

                if (data instanceof ActivityMarkerType) {
                    // Activity marker updated
                    ActivityMarkerType marker = (ActivityMarkerType) data;
                    Set markers =
                            new HashSet(TaskObjectUtil.getMarkers(activity));

                    if (((Button) obj).getSelection()) {
                        if (!markers.contains(marker)) {
                            // Make sure that Loop and Multiple instance loop
                            // are
                            // mutually exclusive.
                            markers.add(marker);

                            if (ActivityMarkerType.MARKER_LOOP_LITERAL
                                    .equals(marker)) {
                                if (markers
                                        .contains(ActivityMarkerType.MARKER_MULTIPLE_LITERAL)) {
                                    markers.remove(ActivityMarkerType.MARKER_MULTIPLE_LITERAL);
                                }
                            } else if (ActivityMarkerType.MARKER_MULTIPLE_LITERAL
                                    .equals(marker)) {
                                if (markers
                                        .contains(ActivityMarkerType.MARKER_LOOP_LITERAL)) {
                                    markers.remove(ActivityMarkerType.MARKER_LOOP_LITERAL);
                                }
                            }
                        }
                    } else {
                        markers.remove(marker);
                    }
                    if (!markers.equals(TaskObjectUtil.getMarkers(activity))) {
                        cmd =
                                TaskObjectUtil.getSetMarkersCommand(ed,
                                        activity,
                                        markers);
                    }
                    // if (markers == null || markers.isEmpty()) {
                    // refreshTabs();
                    // }
                }

            } else if (obj == activityTypeCombo) {
                TaskType newTaskType =
                        (TaskType) activityTypeCombo.getData(activityTypeCombo
                                .getText());

                if (newTaskType != null
                        && !newTaskType.equals(TaskObjectUtil
                                .getTaskType(activity))) {
                    cmd =
                            TaskObjectUtil.getSetTaskTypeCommandEx(ed,
                                    activity,
                                    newTaskType,
                                    getProcess(),
                                    true,
                                    true,
                                    true);
                }
            }
        }

        return cmd;
    }

    protected boolean shouldShowSolutionDesignForm() {
        // Don't show in RCP application
        if (ProcessFeaturesUtil.isProcessDeveloperFeatureInstalled()
                && !XpdResourcesPlugin.isRCP()) {
            Activity activity = getActivity();
            if (activity != null) {
                Implementation implementation = activity.getImplementation();
                if (implementation instanceof Task) {
                    Task task = (Task) implementation;
                    TaskType taskType = TaskObjectUtil.getTaskType(activity);
                    if (taskType != null) {
                        if (taskType.equals(TaskType.SERVICE_LITERAL)
                                || taskType.equals(TaskType.SCRIPT_LITERAL)
                                || taskType.equals(TaskType.SEND_LITERAL)
                                || taskType.equals(TaskType.RECEIVE_LITERAL)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 
     * @return <code>true</code> if Process has BPM destination enabled and the
     *         Solution design capability is enabled. <code>false</code>
     *         otherwise.
     */
    private boolean hasDeveloperCapabilityAndBpmDestinationSet() {

        return CapabilityUtil.isDeveloperActivityEnabled()
                && ProcessDestinationUtil
                        .isBPMDestinationSelected(getProcess());
    }

    /**
     * 
     */
    private void reportChildSize(Composite comp, int indent) {
        if (indent < 4) {
            for (int i = 0; i < indent; i++) {
                System.out.print("  |"); //$NON-NLS-1$
            }
            System.out
                    .println("==> Height " + comp.computeSize(SWT.DEFAULT, SWT.DEFAULT).y); //$NON-NLS-1$

            Control[] children = comp.getChildren();
            for (Control control : children) {
                if (control instanceof Composite) {
                    reportChildSize((Composite) control, indent + 1);
                }
            }

            // for (int i = 0; i < indent; i++) {
            //                System.out.print("  |"); //$NON-NLS-1$
            // }
            // System.out
            //                    .println("<== Height: " + comp.computeSize(SWT.DEFAULT, SWT.DEFAULT).y); //$NON-NLS-1$
        }
        return;
    }

    @SuppressWarnings("unused")
    @Override
    protected void doRefreshDetailsSection() {
        // System.out.println("==> TaskGeneralSection.doRefresh() (Thread:
        // "+Thread.currentThread().getId()+")"); //$NON-NLS-1$ //$NON-NLS-2$

        if (solutionDesignForm != null) {
            if (!CapabilityUtil.isDeveloperActivityEnabled()) {
                solutionDesignForm
                        .setText(Messages.AddSolutionDesignCapability_form,
                                true,
                                false);
            } else {
                solutionDesignForm
                        .setText(Messages.RemoveSolutionDesignCapability_form,
                                true,
                                false);
            }

            if (shouldShowSolutionDesignForm()) {
                solutionDesignForm.setVisible(true);
            } else {
                solutionDesignForm.setVisible(false);
            }
        }

        // If controls are disposed then unregister adapter
        if (activityTypeCombo.isDisposed()) {
            System.err
                    .println("ERROR: not unregistered Task property  page! (Thread: " + Thread.currentThread().getId() + ")"); //$NON-NLS-1$ //$NON-NLS-2$
            return;
        }

        TaskType currTaskType = lastSetTaskType;
        Activity activity = getActivity();
        Process process = getProcess();

        EditingDomain ed = getEditingDomain();

        boolean isPageFlow = false;
        boolean isServiceTask = false;

        if (activity != null && ed != null) {
            Set markers = TaskObjectUtil.getMarkers(activity);
            for (int i = 0; i < markersBut.length; i++) {
                if (markersBut[i] != null) {
                    Object btMarker = markersBut[i].getData();
                    boolean btVal = markers.contains(btMarker);

                    if (!btVal) {
                        Set tmpMarkers = new HashSet(markers);
                        tmpMarkers.add(btMarker);
                        Command cmd =
                                TaskObjectUtil.getSetMarkersCommand(ed,
                                        activity,
                                        tmpMarkers);
                        markersBut[i].setEnabled(cmd.canExecute());
                    } else {
                        HashSet tmpMarkers = new HashSet(markers);
                        tmpMarkers.remove(btMarker);
                        if (!tmpMarkers.isEmpty()) {
                            // empty set will result a unexecutable command so
                            // not calling the function
                            Command cmd =
                                    TaskObjectUtil.getSetMarkersCommand(ed,
                                            activity,
                                            tmpMarkers);
                            markersBut[i].setEnabled(cmd.canExecute());
                        } else {
                            markersBut[i].setEnabled(btVal);
                        }
                    }
                    markersBut[i].setSelection(btVal);

                    setAdhocLinkVisibility();

                    markersBut[i].setData("name", "buttonIsTask" + btMarker); //$NON-NLS-1$ //$NON-NLS-2$
                }
            }

            currTaskType = TaskObjectUtil.getTaskType(activity);

            /*
             * Refresh task type combo
             */
            refreshTaskTypeCombo(currTaskType, activity);

            if (isTaskLibraryActivity()) {
                // Remove unhandled task types.
                if (activityTypeCombo.indexOf(TaskType.RECEIVE_LITERAL
                        .toString()) >= 0) {
                    activityTypeCombo.remove(TaskType.RECEIVE_LITERAL
                            .toString());
                    activityTypeCombo.remove(TaskType.REFERENCE_LITERAL
                            .toString());
                    /*
                     * ABPM-911: No need to do this now.
                     */
                    // activityTypeCombo
                    // .remove(TaskType.EMBEDDED_SUBPROCESS_LITERAL
                    // .toString());
                    // activityTypeCombo.remove(TaskType.EVENT_SUBPROCESS_LITERAL
                    // .toString());
                }
            }

            //
            // Update the participant controls.
            participantSelectionSection.refresh();

            //
            // OTHER STUFF
            //

            if (ProcessInterfaceUtil.isEventImplemented(activity)) {
                nameLabel
                        .setText(Messages.TaskGeneralSection_ImplementsMethod_label);
            } else {
                nameLabel.setText(Messages.TaskGeneralSection_Name_label);
            }
            isPageFlow = Xpdl2ModelUtil.isPageflow(getProcess());
            Implementation impl = activity.getImplementation();
            if (impl instanceof Task) {
                Task task = (Task) impl;
                isServiceTask = task.getTaskService() != null;
            }
        }

        //
        // Update lane selection controls if they were created.
        if (laneSelectionControl != null) {
            boolean canFinish = true;
            String cantFinishMessage = ""; //$NON-NLS-1$

            String laneName = null;
            String actLaneId = null;

            NodeGraphicsInfo ngi = Xpdl2ModelUtil.getNodeGraphicsInfo(activity);
            if (ngi != null) {
                actLaneId = ngi.getLaneId();
            }

            availableLanes = new ArrayList<LaneListEntry>();

            for (Pool pool : Xpdl2ModelUtil.getProcessPools(process)) {
                for (Lane lane : pool.getLanes()) {
                    if (lane.getId().equals(actLaneId)) {
                        laneName = Xpdl2ModelUtil.getDisplayNameOrName(lane);
                    }

                    availableLanes.add(new LaneListEntry(lane));
                }
            }

            ((Text) laneSelectionControl.getControl())
                    .setText(laneName == null ? "" : laneName); //$NON-NLS-1$

            if (laneName == null || laneName.length() == 0) {
                canFinish = false;
                if (isTaskLibraryActivity()) {
                    cantFinishMessage =
                            Messages.TaskGeneralSection_MustSelectTaskSet_longdesc;
                } else {
                    cantFinishMessage =
                            Messages.TaskGeneralSection_MustSelectLane_longdesc;
                }
            }

            if (!canFinish) {
                setSubClassCanFinish(false, cantFinishMessage);
            } else {
                setSubClassCanFinish(true, ""); //$NON-NLS-1$
            }
        }

        // Refresh the details section.
        // System.out.println(" --> TaskGeneralSection: refreshDetails().
        // (Thread: "+Thread.currentThread().getId()+")"); //$NON-NLS-1$
        // //$NON-NLS-2$
        doRefreshImplementationSection();
        // System.out.println(" <-- TaskGeneralSection: refreshDetails().
        // (Thread: "+Thread.currentThread().getId()+")"); //$NON-NLS-1$
        // //$NON-NLS-2$

        if (taskTypeLeftSidePageBook != null) {
            Point sz = new Point(0, 0);
            if (currTaskLeftSideTypeSection != null
                    && currTaskLeftSideTypeSection.page != null) {
                sz =
                        currTaskLeftSideTypeSection.page
                                .computeSize(SWT.DEFAULT, SWT.DEFAULT);
            }

            GridData leftSidePageLayoutData = new GridData(GridData.FILL_BOTH);
            leftSidePageLayoutData.horizontalSpan = 4 - 1;
            leftSidePageLayoutData.heightHint = sz.y;
            taskTypeLeftSidePageBook.setLayoutData(leftSidePageLayoutData);

            if (false) {
                System.out.println("Children"); //$NON-NLS-1$
                reportChildSize((Composite) leftSideScrolledContainer.getContent(),
                        1);
                System.out.println("-------------------"); //$NON-NLS-1$
            }

            Point totSize =
                    leftSideScrolledContainer.getContent()
                            .computeSize(SWT.DEFAULT, SWT.DEFAULT);
            if (false) {
                System.out.println("Min Height: " + totSize.y); //$NON-NLS-1$
            }
            leftSideScrolledContainer.setMinSize(totSize);

            boolean refreshTabs = false;
            // On a change of task type, we must refresh the tabs.
            if (!lastSetTaskType.equals(currTaskType)) {
                lastSetTaskType = currTaskType;

                refreshTabs = true;

            } else {
                Set markers = TaskObjectUtil.getMarkers(activity);
                if (!activityMarkers.containsAll(markers)
                        || markers.size() != activityMarkers.size()) {
                    activityMarkers = markers;
                    refreshTabs = true;
                }
            }

            if (refreshTabs) {
                // System.out.println(" TaskGeneralSection: task type change -
                // refresh tabs. (Thread: "+Thread.currentThread().getId()+")");
                // //$NON-NLS-1$ //$NON-NLS-2$
                Display.getDefault().asyncExec(new Runnable() {
                    @Override
                    public void run() {
                        refreshTabs();
                    }
                });
            }

        }
        // System.out.println("<== TaskGeneralSection.doRefresh() (Thread:
        // "+Thread.currentThread().getId()+")"); //$NON-NLS-1$ //$NON-NLS-2$

    }

    /**
     * Sets the Adhoc hyperlink visibility. The hyperlink should only be visible
     * only if the Activity has Adhoc Config and the Developer capability is on
     * and the BPM destination is enabled.
     */
    private void setAdhocLinkVisibility() {
        Object otherElement =
                Xpdl2ModelUtil.getOtherElement(getActivity(),
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_AdHocTaskConfiguration());

        if ((otherElement != null)
                && hasDeveloperCapabilityAndBpmDestinationSet()) {

            if (!adhocHyperLinkContainer.isVisible()) {

                GridData gd =
                        (GridData) adhocHyperLinkContainer.getLayoutData();
                gd.heightHint = 20;
                adhocHyperLinkContainer.setVisible(true);

                // ((GridData) dummyLabel.getLayoutData()).heightHint = 20;
                // dummyLabel.setVisible(true);

                adhocHyperLinkContainer.getParent().layout(true);
            }

        } else {
            if (adhocHyperLinkContainer.isVisible()) {

                GridData gd =
                        (GridData) adhocHyperLinkContainer.getLayoutData();
                gd.heightHint = 1;
                adhocHyperLinkContainer.setVisible(false);

                // gd = (GridData) dummyLabel.getLayoutData();
                // gd.heightHint = 1;

                // dummyLabel.setVisible(false);
                adhocHyperLinkContainer.getParent().layout(true);

            }
        }
    }

    /**
     * Refresh the items in the task type combo list. This should include only
     * those types that have not been excluded by preferences PLUS the currently
     * selected task type.
     * 
     * @param currTaskType
     * @param activity
     */
    private void refreshTaskTypeCombo(TaskType currTaskType, Activity activity) {
        /*
         * Only add the types available according to object exclusions
         */
        Set<ProcessEditorObjectType> excludedObjectTypes =
                ProcessEditorConfigurationUtil.getExcludedObjectTypes(activity
                        .getProcess());

        Set<TaskType> includedTaskTypes = new HashSet<TaskType>();

        for (TaskTypeSection tts : taskTypeLeftSideSections) {

            for (TaskType taskType : tts.taskTypes) {
                ProcessEditorObjectType objectType =
                        taskType.getProcessEditorObjectType();

                if (!excludedObjectTypes.contains(objectType)) {
                    includedTaskTypes.add(taskType);
                }
            }
        }

        /* Always include the current task type. */
        includedTaskTypes.add(currTaskType);

        /* Check if there is a change in the list from current. */
        boolean typesChanged = false;

        if (activityTypeCombo.getItemCount() != includedTaskTypes.size()) {
            typesChanged = true;

        } else {
            for (int j = 0; j < activityTypeCombo.getItemCount(); j++) {
                String itemText = activityTypeCombo.getItem(j);
                Object itemTaskType = activityTypeCombo.getData(itemText);

                if (!includedTaskTypes.contains(itemTaskType)) {
                    typesChanged = true;
                    break;
                }
            }
        }

        if (typesChanged) {
            activityTypeCombo.removeAll();

            for (TaskTypeSection tts : taskTypeLeftSideSections) {
                for (TaskType taskType : tts.taskTypes) {
                    if (includedTaskTypes.contains(taskType)) {
                        activityTypeCombo.add(taskType.toString());
                        activityTypeCombo
                                .setData(taskType.toString(), taskType);
                    }
                }
            }
        }

        activityTypeCombo.setText(currTaskType.toString());
        activityTypeCombo.setSelection(new Point(0, 0));
    }

    /**
     * @param taskType
     */
    @Override
    protected void doRefreshImplementationSection() {
        Activity activity = getActivity();
        if (activity != null) {

            TaskType taskType = TaskObjectUtil.getTaskType(activity);

            // Find the task type section for the latest task type.
            TaskTypeSection newTaskTypeRightSideSection = null;

            for (TaskTypeSection taskTypeSect : taskTypeRightSideSections) {
                for (TaskType sectTaskType : taskTypeSect.taskTypes) {
                    if (sectTaskType.equals(taskType)) {
                        newTaskTypeRightSideSection = taskTypeSect;
                        break;
                    }
                }
            }

            boolean showDetails = false;
            if (newTaskTypeRightSideSection != null
                    && newTaskTypeRightSideSection.section instanceof IPluginContribution) {
                if (!(newTaskTypeRightSideSection.section instanceof EmptyTaskTypeSection)) {
                    showDetails =
                            showDetails((IPluginContribution) newTaskTypeRightSideSection.section);
                }
            }
            if (showDetails) {
                setSashPercentToLastUserSelected();
                if (taskTypeRightSidePageBook != null) {
                    // If it has changed then reset the inputs.
                    if (newTaskTypeRightSideSection != currTaskRightSideTypeSection) {
                        setTaskTypeRightSidePageInput(taskType);
                        if (newTaskTypeRightSideSection.section instanceof IFilter
                                && newTaskTypeRightSideSection.section instanceof AbstractTransactionalSection) {
                            IFilter filter =
                                    (IFilter) newTaskTypeRightSideSection.section;
                            AbstractTransactionalSection xpdSection =
                                    (AbstractTransactionalSection) newTaskTypeRightSideSection.section;
                            if (!filter.select(xpdSection.getInput())) {
                                setSashPercent(1.0f);
                                return;
                            }
                        }

                        if (newTaskTypeRightSideSection != null) {
                            taskTypeRightSidePageBook
                                    .showPage(newTaskTypeRightSideSection.page);
                        }

                        currTaskRightSideTypeSection =
                                newTaskTypeRightSideSection;
                    }

                    // Refresh whatever task type section is now current.
                    if (currTaskRightSideTypeSection != null) {
                        currTaskRightSideTypeSection.section.refresh();

                        if (detailsScrolledContainer != null
                                && !detailsScrolledContainer.isDisposed()) {
                            /*
                             * Recalculate the size of the details' section
                             * scrolled composite
                             */
                            detailsScrolledContainer
                                    .setMinSize(taskTypeRightSidePageBook
                                            .computeSize(SWT.DEFAULT,
                                                    SWT.DEFAULT));
                        }
                    }
                }
            } else {
                setSashPercent(1.0f);
            }

            //
            // Now do the left hand page book section specific to task type.
            //
            TaskTypeSection newTaskTypeLeftSideSection = null;

            for (TaskTypeSection taskTypeSect : taskTypeLeftSideSections) {
                for (TaskType sectTaskType : taskTypeSect.taskTypes) {
                    if (sectTaskType.equals(taskType)) {
                        newTaskTypeLeftSideSection = taskTypeSect;
                        break;
                    }
                }
            }

            if (taskTypeLeftSidePageBook != null) {
                // If it has changed then reset the inputs.
                if (newTaskTypeLeftSideSection != currTaskLeftSideTypeSection) {
                    setTaskTypeLeftSidePageInput(taskType);

                    if (newTaskTypeRightSideSection != null) {
                        taskTypeLeftSidePageBook
                                .showPage(newTaskTypeLeftSideSection.page);
                    }

                    currTaskLeftSideTypeSection = newTaskTypeLeftSideSection;
                }

                if (currTaskLeftSideTypeSection != null) {
                    if (currTaskLeftSideTypeSection.section instanceof ConfigureReplyActivitySection
                            && isTaskLibraryActivity()) {
                        //
                        // Do not show the REPLY activity configuration for
                        // Send Tasks in task libraries (which do not allow
                        // creation of the receive tasks that can be replied to
                        // in the first place)
                        currTaskLeftSideTypeSection.page.setVisible(false);

                    } else {
                        currTaskLeftSideTypeSection.page.setVisible(true);
                        currTaskLeftSideTypeSection.section.refresh();
                    }
                }
            }

        }
    }

    /**
     * Create the Activity Markers
     * 
     * @param root
     * @param toolkit
     * @return <code>Composite</code> containing the check controls
     */
    private Composite createActivityMarkers(Composite root,
            XpdFormToolkit toolkit) {

        Composite optionsComposite = toolkit.createComposite(root);
        RowLayout rowLayout = new RowLayout(SWT.HORIZONTAL);
        rowLayout.marginWidth = 0;
        rowLayout.marginLeft = 0;

        optionsComposite.setLayout(rowLayout);

        markersBut = new Button[3];

        // Loop
        markersBut[0] =
                createActivityMarkerOption(optionsComposite,
                        toolkit,
                        Messages.TaskGeneralSection_LOOP_MARKER,
                        ActivityMarkerType.MARKER_LOOP_LITERAL);

        // Multiple
        markersBut[1] =
                createActivityMarkerOption(optionsComposite,
                        toolkit,
                        Messages.TaskGeneralSection_MULTIPLE_MARKER,
                        ActivityMarkerType.MARKER_MULTIPLE_LITERAL);

        // Ad-Hoc
        markersBut[2] =
                createActivityMarkerOption(optionsComposite,
                        toolkit,
                        Messages.TaskGeneralSection_ADHOC_MARKER,
                        ActivityMarkerType.MARKER_AD_HOC_LITERAL);

        return optionsComposite;
    }

    /**
     * Create the given activity check control
     * 
     * @param parent
     * @param toolkit
     * @param label
     * @param markerType
     * @return <code>Button</code>
     */
    private Button createActivityMarkerOption(Composite parent,
            XpdFormToolkit toolkit, String label, ActivityMarkerType markerType) {
        Button btn = toolkit.createButton(parent, label, SWT.CHECK);
        btn.setData(markerType);
        manageControl(btn);
        return btn;
    }

    /**
     * Create the Activities type combo control
     * 
     * @param parent
     * @param toolkit
     * @return <code>CCombo</code>
     */
    private CCombo createActivityTypeCombo(Composite parent,
            XpdFormToolkit toolkit) {

        CCombo combo = toolkit.createCCombo(parent, SWT.NONE);
        combo.setData(TabbedPropertySheetWidgetFactory.KEY_DRAW_BORDER,
                TabbedPropertySheetWidgetFactory.TEXT_BORDER);

        for (TaskTypeSection tts : taskTypeLeftSideSections) {
            for (TaskType taskType : tts.taskTypes) {
                combo.add(taskType.toString());
                combo.setData(taskType.toString(), taskType);
            }
        }
        combo.setEditable(false);
        manageControl(combo);
        return combo;
    }

    /**
     * Create the pages for the implementation part of this properties page
     * 
     * @param toolkit
     * @param pageBook
     */
    private void createRightSideTaskTypePages(XpdFormToolkit toolkit,
            PageBook pageBook) {
        for (TaskTypeSection tt : taskTypeRightSideSections) {
            tt.page = toolkit.createComposite(pageBook);
            tt.page.setLayoutData(new GridData(GridData.FILL_BOTH));

            FillLayout fillLayout = new FillLayout();
            fillLayout.marginHeight = 0;
            fillLayout.marginWidth = 0;
            tt.page.setLayout(fillLayout);

            tt.section.createControls(tt.page, getPropertySheetPage());
        }
    }

    private void createLeftSideTaskTypePages(XpdFormToolkit toolkit,
            PageBook pageBook) {
        for (TaskTypeSection tt : taskTypeLeftSideSections) {
            tt.page = toolkit.createComposite(pageBook);
            tt.page.setLayoutData(new GridData(GridData.FILL_BOTH));

            FillLayout fillLayout = new FillLayout();
            fillLayout.marginHeight = 0;
            fillLayout.marginWidth = 0;
            tt.page.setLayout(fillLayout);

            if (isWizard()) {
                tt.section.createControls(tt.page, toolkit);
                tt.section
                        .setXpdSectionContainerProvider(getXpdSectionContainerProvider());
            } else {
                tt.section.createControls(tt.page, getPropertySheetPage());
                tt.section
                        .setXpdSectionContainerProvider(getXpdSectionContainerProvider());
            }
        }

    }

    /**
     * Set input for the selected activity type implementation section
     * 
     * @param taskType
     */
    private void setTaskTypeRightSidePageInput(TaskType taskType) {
        // Set the input for task type specific page
        // and unset all others (by giving them empty selection.
        for (TaskTypeSection tts : taskTypeRightSideSections) {
            if (tts.taskTypes.contains(taskType)) {
                tts.section.setInput(getPart(), getSelection());
            } else {
                tts.section.setInput(getPart(), StructuredSelection.EMPTY);
            }
        }
    }

    private void setTaskTypeLeftSidePageInput(TaskType taskType) {
        for (TaskTypeSection tts : taskTypeLeftSideSections) {
            if (tts.taskTypes.contains(taskType)) {
                tts.section.setInput(getPart(), getSelection());
            } else {
                tts.section.setInput(getPart(), StructuredSelection.EMPTY);
            }
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

    private Process getProcess() {
        if (isWizard()) {
            return Xpdl2ModelUtil.getProcess(getXpdSectionContainerProvider()
                    .getInputContainer());
        } else if (getActivity() != null) {
            return getActivity().getProcess();
        }
        return null;
    }

    private boolean showDetails(IPluginContribution pluginContribution) {
        if (WorkbenchActivityHelper.filterItem(pluginContribution)) {
            return false;
        } else {
            return true;
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

        if (participantSelectionSection != null) {
            participantSelectionSection.aboutToBeHidden();
        }

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

        if (participantSelectionSection != null) {
            participantSelectionSection.aboutToBeShown();
        }
        PlatformUI.getWorkbench().getActivitySupport().getActivityManager()
                .addActivityManagerListener(activityListener);
    }

    public boolean isWizard() {
        return getSectionContainerType() == ContainerType.WIZARD;
    }

    @Override
    public boolean select(Object toTest) {
        if (super.select(toTest)) {
            Activity act = (Activity) getBaseSelectObject(toTest);
            Process process = Xpdl2ModelUtil.getProcess(act);
            if (!DecisionFlowUtil.isDecisionFlow(process)) {
                //
                // We consider anything that isn't a Gateway(route) or Event to
                // be a
                // Task (i.e. we want to include indi and embedded subproc and
                // reference tasks.
                if ((act.getRoute() == null) && (act.getEvent() == null)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected boolean shouldRefresh(List<Notification> notifications) {

        if (currTaskLeftSideTypeSection != null
                && currTaskLeftSideTypeSection.section instanceof DelegatableShouldRefresh) {
            if (((DelegatableShouldRefresh) currTaskLeftSideTypeSection.section)
                    .delegateShouldRefresh(notifications)) {
                return true;
            }
        }

        if (currTaskRightSideTypeSection != null
                && currTaskRightSideTypeSection.section instanceof DelegatableShouldRefresh) {
            if (((DelegatableShouldRefresh) currTaskRightSideTypeSection.section)
                    .delegateShouldRefresh(notifications)) {
                return true;
            }
        }

        /*
         * Sid XPD-2958: Respond to changes in activity set that this activity
         * references.
         */
        Activity activity = getActivity();
        if (activity != null) {
            ActivitySet activitySet =
                    Xpdl2ModelUtil.getEmbeddedSubProcessActivitySet(activity);

            if (activitySet != null) {
                for (Notification notification : notifications) {
                    if (activitySet.equals(notification.getNotifier())) {
                        return true;
                    }
                }
            }

        }

        return super.shouldRefresh(notifications);
    }

    private boolean isTaskLibraryActivity() {
        if (getInputContainer() != null) {
            // May be in a wizard so have to check the input container because
            // the activity input won't be in a file yet
            if (XpdlFileContentPropertyTester
                    .isTasksFileContent(getInputContainer())) {
                return true;
            }
        } else {
            if (XpdlFileContentPropertyTester.isTasksFileContent(getInput())) {
                return true;
            }
        }
        return false;
    }

    /**
     * In order that sections included via pagebooks can contribute to the
     * TaskGeneralSection.shouldRefresh() method, they must implement this
     * method (because unfortunately, TaskGeneralSection cannot currentl
     * delegate directly as AbstractTransactionalSection.shouldRefresh() is
     * protected.
     * 
     * @author aallway
     * @since 3.2
     */
    public interface DelegatableShouldRefresh {
        public boolean delegateShouldRefresh(List<Notification> notifications);
    }

    /**
     * Content assist list entry for local task selection.
     */
    private class LaneListEntry {
        private Lane lane;

        public LaneListEntry(Lane lane) {
            this.lane = lane;
        }

        @Override
        public String toString() {
            return getName();
        }

        public String getName() {
            String name = Xpdl2ModelUtil.getDisplayNameOrName(lane);

            if (name == null || name.length() == 0) {
                if (isTaskLibraryActivity()) {
                    name = Messages.TaskGeneralSection_UnnamedTaskSet_label;
                } else {
                    name = Messages.TaskGeneralSection_UnnamedLane_label;
                }
            }
            return name;
        }

        public String getId() {
            return lane.getId();
        }

        public Lane getLane() {
            return lane;
        }
    }

}
