/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.adhoc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.ActivityPickerControl;
import com.tibco.xpd.processeditor.xpdl2.properties.ActivityPickerControl.IActivityPickerListener;
import com.tibco.xpd.processeditor.xpdl2.properties.SashSection;
import com.tibco.xpd.processeditor.xpdl2.properties.script.BaseProcessScriptSection;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.script.ui.api.AbstractScriptEditorSection;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.ActivityRef;
import com.tibco.xpd.xpdExtension.AdHocExecutionTypeType;
import com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType;
import com.tibco.xpd.xpdExtension.EnablementType;
import com.tibco.xpd.xpdExtension.InitializerActivitiesType;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Creates the Adhoc Configuration Enablement collapsible Section.
 * 
 * @author kthombar
 * @since 30-Jul-2014
 */
public class AdhocConfigurationEnablementSection extends
        AbstractFilteredTransactionalSection implements SashSection {

    /**
     * the Activity selection section which provides picker to select activity.
     */
    private ActivityPickerControl initializerActivityPickerControl;

    private Label enablementLabel;

    private AdhocActivityEnablmentScriptSection adhocScriptSection;

    private Label scriptLabel;

    /**
     * @param eClass
     */
    public AdhocConfigurationEnablementSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());

    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.SashSection#shouldSashSectionRefresh(java.util.List)
     * 
     * @param notifications
     * @return
     */
    @Override
    public boolean shouldSashSectionRefresh(List<Notification> notifications) {

        return shouldRefresh(notifications);
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#setInput(org.eclipse.ui.IWorkbenchPart,
     *      org.eclipse.jface.viewers.ISelection)
     * 
     * @param part
     * @param selection
     */
    @Override
    public void setInput(IWorkbenchPart part, ISelection selection) {
        super.setInput(part, selection);

        if (adhocScriptSection != null) {
            adhocScriptSection.setInput(part, selection);
        }
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {

        if (adhocScriptSection != null) {
            adhocScriptSection.refresh();
        }

        AdHocTaskConfigurationType adhocConfigType =
                getAdhocTaskConfigurationType();

        if (adhocConfigType != null) {
            /* set the already selected activities. */
            initializerActivityPickerControl.setActivities(getProcess(),
                    getAlreadySelectedActivities(adhocConfigType));

            AdHocExecutionTypeType adHocExecutionType =
                    adhocConfigType.getAdHocExecutionType();
            /*
             * Change the Enablement Label base on the Adhoc Execution Type
             * (i.e. either Automatic or Manual)
             */
            if (AdHocExecutionTypeType.AUTOMATIC.equals(adHocExecutionType)) {

                enablementLabel
                        .setText(Messages.AdhocConfigurationEnablementSection_EnablementLabelAutomaticInvocation_desc);

                scriptLabel
                        .setText(Messages.AdhocConfigurationEnablementSection_AutomaticAdhocPreconditionSection_label);
            } else {

                enablementLabel
                        .setText(Messages.AdhocConfigurationEnablementSection_EnablementLabelManualInvocation_desc);

                scriptLabel
                        .setText(Messages.AdhocConfigurationEnablementSection_ManualAdhocPreconditionExpression_label);
            }
        }
    }

    /**
     * Gets already selected initializer activities by the Ad-Hoc Task.
     * 
     * @param adhocConfigType
     * @return
     */
    List<Activity> getAlreadySelectedActivities(
            AdHocTaskConfigurationType adhocConfigType) {

        List<Activity> alreadySelectedActivities = new ArrayList<Activity>();

        EnablementType enablement = adhocConfigType.getEnablement();

        if (enablement != null) {

            InitializerActivitiesType initializerActivitiesType =
                    enablement.getInitializerActivities();

            if (initializerActivitiesType != null) {

                EList<ActivityRef> activityRef =
                        initializerActivitiesType.getActivityRef();

                if (!activityRef.isEmpty()) {

                    for (ActivityRef eachRef : activityRef) {

                        Activity activityById =
                                Xpdl2ModelUtil.getActivityById(getProcess(),
                                        eachRef.getIdRef());

                        if (activityById != null) {
                            alreadySelectedActivities.add(activityById);
                        }
                    }
                }
            }
        }
        return alreadySelectedActivities;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doCreateControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param parent
     * @param toolkit
     * @return
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {

        Composite enablementComposite = toolkit.createComposite(parent);

        GridLayout layout = new GridLayout(1, false);
        layout.marginLeft = -6;
        enablementComposite.setLayout(layout);

        GridData gridData = new GridData(GridData.FILL_HORIZONTAL);

        enablementLabel =
                toolkit.createLabel(enablementComposite,
                        Messages.AdhocConfigurationEnablementSection_EnablementLabelAutomaticInvocation_desc,
                        SWT.WRAP);
        enablementLabel.setLayoutData(gridData);

        gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.verticalIndent = 10;
        gridData.horizontalIndent = 5;

        /* Initializer Activity Section */
        Label initializerActivityLabel =
                toolkit.createLabel(enablementComposite,
                        Messages.AdhocConfigurationEnablementSection_InitializerActivity_label,
                        SWT.WRAP);
        initializerActivityLabel.setLayoutData(gridData);
        initializerActivityLabel
                .setToolTipText(Messages.AdhocConfigurationEnablementSection_InitializerActivity_tooltip);

        Label initializerActivityInfoLabel =
                toolkit.createLabel(enablementComposite,
                        Messages.AdhocConfigurationEnablementSection_InitializerActivityInfoLabel_desc,
                        SWT.WRAP);
        gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.horizontalIndent = 15;
        initializerActivityInfoLabel.setLayoutData(gridData);

        initializerActivityPickerControl =
                new ActivityPickerControl(
                        enablementComposite,
                        toolkit,
                        Messages.AdhocConfigurationEnablementSection_InitilizerActivityPicker_title,
                        SWT.MULTI) {
                    @Override
                    protected IFilter createActivityFilter() {

                        return new IFilter() {
                            @Override
                            public boolean select(Object toTest) {
                                boolean ret = false;

                                if (toTest instanceof Activity) {
                                    Activity activity = (Activity) toTest;

                                    if (activity != getActivity()) {
                                        /*
                                         * an adhoc activity cannot be its own
                                         * initializer activity.
                                         */
                                        if (activity.getEvent() != null) {
                                            /*
                                             * If it's an event then it must not
                                             * be an event attached to task.
                                             */
                                            if (!Xpdl2ModelUtil
                                                    .isEventAttachedToTask(activity)) {
                                                ret = true;
                                            }
                                        } else {
                                            // is a task

                                            if (TaskObjectUtil
                                                    .getTaskTypeStrict(activity) != null) {
                                                ret = true;
                                            }
                                        }
                                    }
                                }
                                return ret;
                            }
                        };
                    }
                };

        gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.horizontalIndent = 15;
        initializerActivityPickerControl.setLayoutData(gridData);

        /* manage the selection and clearing of the activities.(listener) */
        manageSelectUnselectClearActivities();

        Label preconditionLabel =
                toolkit.createLabel(enablementComposite,
                        Messages.AdhocConfigurationEnablementSection_Precondition_label,
                        SWT.WRAP);
        gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.verticalIndent = 10;
        gridData.horizontalIndent = 5;
        preconditionLabel.setLayoutData(gridData);

        scriptLabel =
                toolkit.createLabel(enablementComposite,
                        Messages.AdhocConfigurationEnablementSection_AutomaticAdhocPreconditionSection_label,
                        SWT.WRAP);
        gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.horizontalIndent = 15;
        scriptLabel.setLayoutData(gridData);

        Composite createComposite =
                toolkit.createComposite(enablementComposite);
        GridData gdata = new GridData(GridData.FILL_BOTH);
        gdata.horizontalIndent = 10;
        createComposite.setLayoutData(gdata);

        createComposite.setLayout(new FillLayout());

        /* Precondition Script Section */
        adhocScriptSection = new AdhocActivityEnablmentScriptSection();
        adhocScriptSection.createControls(createComposite,
                getPropertySheetPage());

        return enablementComposite;
    }

    /**
     * Looks at the Activities selected from the picker and accordingly adds or
     * removes those activites from the model by firing a command. Additionaly
     * removes all the Activity ref details if the user selects the clear
     * button.
     */
    private void manageSelectUnselectClearActivities() {

        initializerActivityPickerControl
                .addActivitiesPickedListener(new IActivityPickerListener() {

                    @Override
                    public void activitiesPicked(
                            Collection<Activity> selectedActivities) {

                        EditingDomain editingDomain = getEditingDomain();

                        AdHocTaskConfigurationType adhocTaskConfig =
                                getAdhocTaskConfigurationType();

                        if (adhocTaskConfig != null) {

                            CompoundCommand ccmd = new CompoundCommand();

                            InitializerActivitiesType initializerActivitiesType =
                                    null;

                            EnablementType enablementType =
                                    adhocTaskConfig.getEnablement();

                            if (!selectedActivities.isEmpty()) {

                                List<Activity> alreadySelectedActivities =
                                        getAlreadySelectedActivities(adhocTaskConfig);

                                if (!alreadySelectedActivities
                                        .equals(selectedActivities)) {

                                    initializerActivitiesType =
                                            XpdExtensionFactory.eINSTANCE
                                                    .createInitializerActivitiesType();

                                    List<ActivityRef> activityRefs =
                                            new ArrayList<ActivityRef>();

                                    for (Activity act : selectedActivities) {

                                        ActivityRef createActivityRef =
                                                XpdExtensionFactory.eINSTANCE
                                                        .createActivityRef();

                                        createActivityRef.setIdRef(act.getId());

                                        activityRefs.add(createActivityRef);
                                    }

                                    initializerActivitiesType.getActivityRef()
                                            .addAll(activityRefs);

                                    if (enablementType == null) {
                                        enablementType =
                                                XpdExtensionFactory.eINSTANCE
                                                        .createEnablementType();
                                        enablementType
                                                .setInitializerActivities(initializerActivitiesType);
                                    } else {
                                        ccmd.append(SetCommand
                                                .create(editingDomain,
                                                        enablementType,
                                                        XpdExtensionPackage.eINSTANCE
                                                                .getEnablementType_InitializerActivities(),
                                                        initializerActivitiesType));
                                    }

                                    ccmd.setLabel(Messages.AdhocConfigurationEnablementSection_SetInitializerActivitiesCommand_desc);

                                    ccmd.append(SetCommand
                                            .create(editingDomain,
                                                    adhocTaskConfig,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getAdHocTaskConfigurationType_Enablement(),
                                                    enablementType));
                                }
                            } else {
                                /*
                                 * clear activities.
                                 */
                                enablementType =
                                        adhocTaskConfig.getEnablement();

                                if (enablementType != null
                                        && enablementType
                                                .getInitializerActivities() != null) {
                                    ccmd.setLabel(Messages.AdhocConfigurationEnablementSection_UnsetInitializerActivitiesCommand_desc);
                                    ccmd.append(SetCommand
                                            .create(editingDomain,
                                                    enablementType,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getEnablementType_InitializerActivities(),
                                                    SetCommand.UNSET_VALUE));
                                }
                            }

                            if (ccmd != null && ccmd.canExecute()) {
                                editingDomain.getCommandStack().execute(ccmd);
                            }
                        }
                    }
                });
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    protected Command doGetCommand(Object obj) {
        return null;
    }

    /**
     * 
     * @see com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection#select(java.lang.Object)
     * 
     * @param toTest
     * @return <code>true</code> always, let
     *         {@link AdhocConfigurationSection#select(Object)} do the
     *         filtering.
     */
    @Override
    public boolean select(Object toTest) {
        return true;
    }

    /**
     * 
     * @return the {@link AdHocTaskConfigurationType} extension element of the
     *         {@link Activity} for which this Section is visible. If the
     *         activity does not have {@link AdHocTaskConfigurationType} then
     *         return <code>null</code>
     */
    private AdHocTaskConfigurationType getAdhocTaskConfigurationType() {

        AdHocTaskConfigurationType adhocConfigType = null;

        Activity activity = getActivity();

        if (activity != null) {

            Object adhocConfig =
                    Xpdl2ModelUtil.getOtherElement(activity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_AdHocTaskConfiguration());

            if (adhocConfig instanceof AdHocTaskConfigurationType) {

                adhocConfigType = (AdHocTaskConfigurationType) adhocConfig;
            }
        }
        return adhocConfigType;
    }

    /**
     * 
     * @return the {@link Activity} for which this property section is shown
     *         else return <code>null</code>
     */
    private Activity getActivity() {
        Object o = getInput();
        if (o instanceof Activity) {
            return (Activity) o;
        }
        return null;
    }

    /**
     * 
     * @return the Parent {@link Process} of the {@link Activity} for which this
     *         property section is shown else return <code>null</code>
     */
    private Process getProcess() {
        Activity activity = getActivity();
        if (activity != null) {
            return activity.getProcess();
        }
        return null;
    }

    /**
     * Script section for Ad-Hoc Enablement Collapsible section. this is
     * incomplete.
     * 
     * @author kthombar
     * @since 12-Aug-2014
     */
    private class AdhocActivityEnablmentScriptSection extends
            BaseProcessScriptSection {

        /**
         * @param inputType
         */
        public AdhocActivityEnablmentScriptSection() {
            super(Xpdl2Package.eINSTANCE.getActivity());
        }

        /**
         * @see com.tibco.xpd.processeditor.xpdl2.properties.script.BaseProcessScriptSection#getCurrentSetScriptGrammarId()
         * 
         * @return
         */
        @Override
        public String getCurrentSetScriptGrammarId() {

            String grammarId = null;

            AdHocTaskConfigurationType adhocTaskConfigurationType =
                    getAdhocTaskConfigurationType();

            if (adhocTaskConfigurationType != null) {
                EnablementType enablement =
                        adhocTaskConfigurationType.getEnablement();

                if (enablement != null) {
                    Expression preconditionExpression =
                            enablement.getPreconditionExpression();

                    if (preconditionExpression != null) {
                        grammarId = preconditionExpression.getScriptGrammar();
                    }
                }
            }
            return grammarId;
        }

        /**
         * @see com.tibco.xpd.script.ui.internal.BaseScriptSection#getScriptContext()
         * 
         * @return
         */
        @Override
        public String getScriptContext() {
            return ProcessScriptContextConstants.ADHOC_PRECONDITION;
        }

        /**
         * 
         * @see com.tibco.xpd.script.ui.internal.BaseScriptSection#getSetScriptGrammarCommand(java.lang.String,
         *      com.tibco.xpd.script.ui.api.AbstractScriptEditorSection)
         * 
         * @param grammar
         * @param editorSection
         * @return
         */
        @SuppressWarnings("restriction")
        @Override
        protected Command getSetScriptGrammarCommand(String grammar,
                AbstractScriptEditorSection editorSection) {
            Command cmd = null;

            Activity act = getActivity();
            EditingDomain ed = getEditingDomain();

            if (act != null && ed != null && editorSection != null) {
                cmd = editorSection.getSetScriptGrammarCommand(ed, act);
            }
            return cmd;
        }
    }
}
