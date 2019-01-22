package com.tibco.xpd.processeditor.xpdl2.properties.seqflow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.ActivityManagerEvent;
import org.eclipse.ui.activities.IActivityManagerListener;
import org.eclipse.ui.activities.WorkbenchActivityHelper;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.views.properties.tabbed.ISection;

import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ProcessEditorConfigurationUtil;
import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ProcessEditorObjectType;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessFeaturesUtil;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.properties.SashDividedNamedElementSection;
import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processwidget.adapters.SequenceFlowType;
import com.tibco.xpd.processwidget.commands.ChangeSeqFlowTypeCommand;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.util.CapabilityUtil;
import com.tibco.xpd.xpdl2.Condition;
import com.tibco.xpd.xpdl2.ConditionType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.DecisionFlowUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

public class SequenceFlowSection extends SashDividedNamedElementSection {

    private Set<Button> sequenceFlowButtons = new HashSet<Button>();

    private final IActivityManagerListener activityListener;

    private List<SequenceTypeSection> sequenceTypeSections;

    private ScrolledComposite detailsScrolledContainer;

    private SequenceTypeSection currSequenceTypeSection = null;

    private Transition transition = null;

    /*
     * Type of task specific controls are held in a page book (each type can
     * have its own page, we show correct one for currently selected task).
     */
    private PageBook flowTypeSpecificPageBook;

    private FormText solutionDesignForm;

    /**
     * Controls and data for Task Type setting.
     */
    public class SequenceTypeSection {
        public SequenceFlowType sequenceFlowType;

        public Composite page = null;

        public ISection section = null;

        public SequenceTypeSection(SequenceFlowType flowType, ISection section) {
            this.sequenceFlowType = flowType;
            this.section = section;
        }

        public SequenceFlowType getSequenceFlowType() {
            return sequenceFlowType;
        }
    }

    public SequenceFlowSection() {

        super(Xpdl2Package.eINSTANCE.getTransition(),
                Xpdl2ProcessEditorPlugin.ID + ".SequenceFlowSection"); //$NON-NLS-1$
        setMinimumHeight(SWT.DEFAULT);
        sequenceTypeSections = getSequenceTypeSections();

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

    protected List<SequenceTypeSection> getSequenceTypeSections() {
        List<SequenceTypeSection> sequenceTypeSections =
                new ArrayList<SequenceTypeSection>();

        SequenceTypeSection tt =
                new SequenceTypeSection(SequenceFlowType.UNCONTROLLED_LITERAL,
                        new DefaultSequenceFlowTypeSection());
        sequenceTypeSections.add(tt);

        tt =
                new SequenceTypeSection(SequenceFlowType.CONDITIONAL_LITERAL,
                        new SequenceFlowScriptSection());
        sequenceTypeSections.add(tt);

        tt =
                new SequenceTypeSection(SequenceFlowType.DEFAULT_LITERAL,
                        new DefaultSequenceFlowTypeSection());
        sequenceTypeSections.add(tt);

        return sequenceTypeSections;

    }

    @Override
    protected Composite doCreateDetailsSection(Composite parent,
            XpdFormToolkit toolkit) {
        detailsScrolledContainer =
                toolkit.createScrolledComposite(parent, SWT.V_SCROLL
                        | SWT.H_SCROLL);
        detailsScrolledContainer.setExpandHorizontal(true);
        detailsScrolledContainer.setExpandVertical(true);
        flowTypeSpecificPageBook =
                new PageBook(detailsScrolledContainer, SWT.NONE);
        toolkit.adapt(flowTypeSpecificPageBook, false, false);
        detailsScrolledContainer.setContent(flowTypeSpecificPageBook);
        createSequenceFlowTypePages(toolkit, flowTypeSpecificPageBook);

        return detailsScrolledContainer;
    }

    /**
     * Create the pages for the implementation part of this properties page
     * 
     * @param toolkit
     * @param pageBook
     */
    private void createSequenceFlowTypePages(XpdFormToolkit toolkit,
            PageBook pageBook) {
        for (SequenceTypeSection tt : sequenceTypeSections) {
            tt.page = toolkit.createComposite(pageBook);
            tt.page.setLayoutData(new GridData(GridData.FILL_BOTH));

            FillLayout fillLayout = new FillLayout();
            fillLayout.marginHeight = 0;
            fillLayout.marginWidth = 0;
            tt.page.setLayout(fillLayout);

            tt.section.createControls(tt.page, getPropertySheetPage());
        }
    }

    @Override
    protected Composite doCreateGeneralSection(Composite parent,
            XpdFormToolkit toolkit) {
        ScrolledForm form = toolkit.createScrolledForm(parent);

        form.setBackground(ColorConstants.white);

        Composite root = form.getBody();
        GridLayout gridLayout = new GridLayout(2, false);
        root.setLayout(gridLayout);

        Label typeLabel =
                toolkit.createLabel(root,
                        Messages.SequenceFlowSection_Type_label);

        GridData data = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
        data.verticalIndent = gridLayout.marginHeight;
        typeLabel.setLayoutData(data);

        Composite butt = toolkit.createComposite(root);
        data = new GridData(GridData.FILL_BOTH);
        butt.setLayoutData(data);
        butt.setLayout(new GridLayout(1, false));

        createTypeRadioButtons(butt, toolkit);

        solutionDesignForm = toolkit.createFormText(butt, true);
        solutionDesignForm.setLayoutData(data);
        manageControl(solutionDesignForm);

        toolkit.paintBordersFor(root);

        return form;

    }

    protected void createTypeRadioButtons(Composite butt, XpdFormToolkit toolkit) {
        List<SequenceTypeSection> sequenceTypeSections =
                getSequenceTypeSections();
        if (sequenceTypeSections != null && !sequenceTypeSections.isEmpty()) {
            Button tempButton = null;
            for (SequenceTypeSection sequenceTypeSection : sequenceTypeSections) {
                if (sequenceTypeSection != null
                        && sequenceTypeSection.getSequenceFlowType() != null) {
                    if (sequenceTypeSection.getSequenceFlowType()
                            .equals(SequenceFlowType.UNCONTROLLED_LITERAL)) {
                        tempButton =
                                createTypeRadio(butt,
                                        toolkit,
                                        SequenceFlowType.UNCONTROLLED_LITERAL,
                                        "IsSequenceFlowUncontrolled");//$NON-NLS-1$

                    } else if (sequenceTypeSection.getSequenceFlowType()
                            .equals(SequenceFlowType.CONDITIONAL_LITERAL)) {
                        tempButton =
                                createTypeRadio(butt,
                                        toolkit,
                                        SequenceFlowType.CONDITIONAL_LITERAL,
                                        "IsSequenceFlowConditional");//$NON-NLS-1$
                    } else if (sequenceTypeSection.getSequenceFlowType()
                            .equals(SequenceFlowType.DEFAULT_LITERAL)) {
                        tempButton =
                                createTypeRadio(butt,
                                        toolkit,
                                        SequenceFlowType.DEFAULT_LITERAL,
                                        "IsSequenceFlowDefault"); //$NON-NLS-1$
                    }
                }
            }
        }
    }

    @Override
    protected Command doGetDetailsCommand(Object obj) {
        Transition trans = getTransition();
        EditingDomain ed = getEditingDomain();

        if (trans != null && ed != null) {
            if (obj instanceof Button) {
                // Deal with the sequence flow type selection
                Button btn = (Button) obj;

                if (btn.getSelection()) {
                    Object data = btn.getData();

                    if (data instanceof SequenceFlowType) {
                        // Set the sequence flow type
                        return ChangeSeqFlowTypeCommand.create(ed,
                                getTransition(),
                                (SequenceFlowType) data);

                    }
                }
            }
        }
        return null;
    }

    /**
     * Create button for the sequence flow type
     * 
     * @param parent
     *            parent composite
     * @param toolkit
     *            form toolkit
     * @param type
     *            sequence flow type
     * @return created <code>Button</code>
     */
    protected Button createTypeRadio(Composite parent, XpdFormToolkit toolkit,
            SequenceFlowType type, String instrumentationName) {
        Button b =
                toolkit.createButton(parent,
                        type.toString(),
                        SWT.RADIO,
                        instrumentationName);
        b.setData(type);
        b.setData("name", "buttonIsSequenceFlow" + type); //$NON-NLS-1$ //$NON-NLS-2$
        b.setLayoutData(new GridData());

        manageControl(b);
        sequenceFlowButtons.add(b);
        return b;
    }

    protected boolean shouldShowSolutionDesignForm() {
        if (!XpdResourcesPlugin.isRCP()
                && ProcessFeaturesUtil.isProcessDeveloperFeatureInstalled()) {
            Transition transition = getTransition();
            if (transition != null) {
                if (transition.getCondition() != null) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected void doRefreshDetailsSection() {
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
        Transition trans = getTransition();
        if (trans != null) {
            SequenceFlowType ft = getFlowType();

            Set<ProcessEditorObjectType> excludedProcessObjectTypes =
                    ProcessEditorConfigurationUtil
                            .getExcludedProcessObjectTypes(getTransition()
                                    .getProcess());

            boolean layoutChange = false;

            // Update teh sequence flow type
            for (Button btn : sequenceFlowButtons) {
                if (btn.isDisposed()) {
                    return;
                }

                SequenceFlowType btnFlowType = (SequenceFlowType) btn.getData();
                boolean isCurrentSelection = btnFlowType.equals(ft);

                btn.setSelection(isCurrentSelection);

                /*
                 * Hide if excluded object type provided its not actual flow
                 * type on object.
                 */
                if (excludedProcessObjectTypes.contains(btnFlowType
                        .getEditorObjectType()) && !isCurrentSelection) {
                    if (btn.getVisible()) {
                        btn.setVisible(false);

                        GridData gd = new GridData();
                        gd.heightHint = 0;
                        btn.setLayoutData(gd);
                        layoutChange = true;
                    }
                } else {
                    if (!btn.getVisible()) {
                        btn.setVisible(true);

                        GridData gd = new GridData();
                        btn.setLayoutData(gd);
                        layoutChange = true;
                    }
                }
            }

            if (layoutChange) {
                sequenceFlowButtons.iterator().next().getParent().layout(true);
            }
        }
    }

    @Override
    protected void doRefreshImplementationSection() {
        Transition trans = getTransition();
        if (trans != null) {
            SequenceFlowType flowType = getFlowType();

            // Find the flow type section for the latest flow type.
            SequenceTypeSection newSequenceTypeSection = null;

            for (SequenceTypeSection flowTypeSect : sequenceTypeSections) {
                if (flowTypeSect.sequenceFlowType.equals(flowType)) {
                    newSequenceTypeSection = flowTypeSect;
                    break;
                }
            }
            boolean showDetails = false;
            if (newSequenceTypeSection.section instanceof IPluginContribution) {
                showDetails =
                        showDetails((IPluginContribution) newSequenceTypeSection.section);
            }
            if (showDetails) {
                setSashPercentToLastUserSelected();
                if (flowTypeSpecificPageBook != null) {
                    // If it has changed then reset the inputs.
                    if (newSequenceTypeSection != currSequenceTypeSection) {
                        setSequenceFlowTypePageInput(flowType);

                        if (newSequenceTypeSection != null) {
                            flowTypeSpecificPageBook
                                    .showPage(newSequenceTypeSection.page);
                        }

                        currSequenceTypeSection = newSequenceTypeSection;
                    }

                    // Refresh whatever task type section is now current.
                    if (currSequenceTypeSection != null) {
                        currSequenceTypeSection.section.refresh();

                        if (detailsScrolledContainer != null
                                && !detailsScrolledContainer.isDisposed()) {
                            /*
                             * Recalculate the size of the details' section
                             * scrolled composite
                             */
                            detailsScrolledContainer
                                    .setMinSize(flowTypeSpecificPageBook
                                            .computeSize(SWT.DEFAULT,
                                                    SWT.DEFAULT));
                        }
                    }
                }
            } else {
                setSashPercent(1.0f);
            }
        }
    }

    @Override
    public void setInput(Collection items) {
        super.setInput(items);

        Transition currentTransition = getTransition();
        if (transition != currentTransition) {
            /*
             * This indicates that we have switched from one transition to
             * another. We do this as we do not want currSequenceTypeSection to
             * be set to null when we are selecting the script type of a
             * conditional flow.
             */
            transition = currentTransition;
            /*
             * XPD-5478: Let doRefresh sort out initial assignment of sequence
             * flow tyep specific section. In that way, we ensure that the
             * lifecycle of currSequenceTypeSection is always consistent, when
             * selecting between different flows.
             */
            currSequenceTypeSection = null;

            /* Unset type specific section inputs. */
            setSequenceFlowTypePageInput(null);
        }
    }

    /**
     * Set input for the selected activity type implementation section
     * 
     * @param taskType
     */
    private void setSequenceFlowTypePageInput(SequenceFlowType flowType) {
        // Set the input for task type specific page
        // and unset all others (by giving them empty selection.
        System.out.println();
        for (SequenceTypeSection tts : sequenceTypeSections) {
            if (tts.sequenceFlowType.equals(flowType)) {
                tts.section.setInput(getPart(), getSelection());
            } else {
                tts.section.setInput(getPart(), StructuredSelection.EMPTY);
            }
        }
    }

    @Override
    public void dispose() {
        for (SequenceTypeSection tt : sequenceTypeSections) {
            tt.section.dispose();
        }
        super.dispose();
    }

    private SequenceFlowType getFlowType() {
        SequenceFlowType result = SequenceFlowType.UNCONTROLLED_LITERAL;

        Transition tr = getTransition();
        if (tr != null) {
            Condition cnd = tr.getCondition();
            if (cnd != null) {
                ConditionType tp = cnd.getType();
                if (tp != null) {
                    switch (tp.getValue()) {
                    case ConditionType.CONDITION:
                        result = SequenceFlowType.CONDITIONAL_LITERAL;
                        break;
                    case ConditionType.OTHERWISE:
                        result = SequenceFlowType.DEFAULT_LITERAL;
                        break;
                    }
                }
            }
        }
        return result;
    }

    private Transition getTransition() {
        Object o = getInput();
        if (o instanceof Transition) {
            return (Transition) o;
        }
        return null;
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

    private boolean showDetails(IPluginContribution pluginContribution) {
        if (WorkbenchActivityHelper.filterItem(pluginContribution)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean select(Object toTest) {
        if (super.select(toTest)) {
            EObject secFlow = getBaseSelectObject(toTest);
            Process process = Xpdl2ModelUtil.getProcess(secFlow);
            if (!DecisionFlowUtil.isDecisionFlow(process)) {
                return true;
            }
        }
        return false;
    }

}
