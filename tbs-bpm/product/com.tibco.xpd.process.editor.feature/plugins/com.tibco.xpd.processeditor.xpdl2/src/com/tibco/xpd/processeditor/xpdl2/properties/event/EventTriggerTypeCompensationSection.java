/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.event;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.ISection;

import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.util.FixedValueFieldAssistHelper;
import com.tibco.xpd.processeditor.xpdl2.properties.util.FixedValueFieldAssistHelper.FixedValueFieldChangedListener;
import com.tibco.xpd.processeditor.xpdl2.properties.util.FixedValueFieldAssistHelper.FixedValueFieldProposalProvider;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.TriggerResultCompensation;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author bharge
 * 
 */
public class EventTriggerTypeCompensationSection extends
        AbstractFilteredTransactionalSection implements ISection,
        FixedValueFieldChangedListener {

    private String instrumentationPrefixName;

    private class CompensatedTasksEntry {
        private Activity activity;

        public CompensatedTasksEntry(Activity activity) {
            this.activity = activity;
        }

        @Override
        public String toString() {
            return getName();
        }

        public String getName() {
            if (null != activity) {
                return Xpdl2ModelUtil.getDisplayName(activity);
            }
            return null;
        }

        public String getId() {
            return activity.getId();
        }
    }

    private Set<CompensatedTasksEntry> compensatedTasksEntryList;

    private DecoratedField taskToCompensate;

    private Composite compensationComposite;

    public EventTriggerTypeCompensationSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());
    }

    /**
     * @param class1
     */
    public EventTriggerTypeCompensationSection(String intrumentationPrefixName) {
        super(Xpdl2Package.eINSTANCE.getActivity());
        this.instrumentationPrefixName = intrumentationPrefixName;
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
        Composite root = toolkit.createComposite(parent);
        root.setLayout(new GridLayout(1, false));

        toolkit.createLabel(root,
                Messages.EventTriggerTypeCompensationSection_label,
                SWT.NONE);

        compensationComposite = toolkit.createComposite(root);
        compensationComposite.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL));
        GridLayout compensatedControlCompositeLayout = new GridLayout(1, true);
        compensationComposite.setLayout(compensatedControlCompositeLayout);

        /**
         * Add content assisted text control for referenced event entry.
         * */
        FixedValueFieldProposalProvider proposalProvider =
                new FixedValueFieldAssistHelper.FixedValueFieldProposalProvider() {
                    public Object[] getProposals() {
                        if (compensatedTasksEntryList != null) {
                            return compensatedTasksEntryList.toArray();
                        }
                        return null;
                    }
                };

        FixedValueFieldAssistHelper refTaskHelper =
                new FixedValueFieldAssistHelper(toolkit, compensationComposite,
                        proposalProvider, true);

        refTaskHelper.addValueChangedListener(this);

        taskToCompensate = refTaskHelper.getDecoratedField();
        taskToCompensate.getControl()
                .setData(XpdFormToolkit.INSTRUMENTATION_DATA_NAME,
                        "CompensatedTaskName"); //$NON-NLS-1$
        taskToCompensate.getControl().setData("name", "textEventErrorCode"); //$NON-NLS-1$ //$NON-NLS-2$
        taskToCompensate.getLayoutControl()
                .setData(XpdFormToolkit.INSTRUMENTATION_DATA_NAME,
                        "assistTaskName"); //$NON-NLS-1$

        taskToCompensate.getLayoutControl().setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL));

        taskToCompensate.getLayoutControl().setBackground(compensationComposite
                .getBackground());

        return root;
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
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {
        String textValue = ""; //$NON-NLS-1$

        /**
         * If controls have been disposed then unregister adapter
         * */
        if (taskToCompensate == null
                || taskToCompensate.getControl().isDisposed()) {
            return;
        }

        Activity activity = getActivity();

        if (activity != null) {

            Collection<Activity> allActivitiesInProc =
                    Xpdl2ModelUtil
                            .getAllActivitiesInProc(activity.getProcess());

            compensatedTasksEntryList =
                    new HashSet<CompensatedTasksEntry>(allActivitiesInProc
                            .size());
            for (Activity activity2 : allActivitiesInProc) {
                /**
                 * only tasks of type None, Reusable Sub-Process, Embedded
                 * Sub-Process, Reference tasks and Service Tasks are eligible
                 * as compensation task(s)
                 * */
                boolean isValid = validateTaskForCompensation(activity2);
                if (isValid) {
                    compensatedTasksEntryList.add(new CompensatedTasksEntry(
                            activity2));
                }
            }

            Event event = activity.getEvent();
            TriggerResultCompensation resultCompensation = null;
            if (event instanceof EndEvent) {
                resultCompensation =
                        ((EndEvent) event).getTriggerResultCompensation();
            } else if (event instanceof IntermediateEvent) {
                resultCompensation =
                        ((IntermediateEvent) event)
                                .getTriggerResultCompensation();
            }
            if (null != resultCompensation
                    && null != resultCompensation.getActivityId()
                    && resultCompensation.getActivityId().length() > 0) {
                textValue =
                        getActivityNameToBeCompensated(resultCompensation
                                .getActivityId(), allActivitiesInProc);
            }
        }
        if (null == textValue) {
            textValue = ""; //$NON-NLS-1$
        }
        if (null != taskToCompensate
                && !taskToCompensate.getControl().isDisposed()) {
            ((Text) taskToCompensate.getControl()).setText(textValue);
        }

        return;
    }

    private String getActivityNameToBeCompensated(String activityId,
            Collection<Activity> allActivitiesInProc) {
        for (Activity activity : allActivitiesInProc) {
            if (activity.getId().equals(activityId)) {
                return Xpdl2ModelUtil.getDisplayName(activity);
            }
        }
        return null;
    }

    /**
     * only tasks of type None, Reusable Sub-Process, Embedded Sub-Process,
     * Reference tasks and Service Tasks are eligible as compensation task(s)
     * */
    private boolean validateTaskForCompensation(Activity activity) {
        /*
         * SID XPD_665. The original design spec stated that all task types
         * should be selectable for compensation INCLUDING task type none,
         * embedded, resusable and reference). Not excluding everything else.
         * 
         * (https://emea-swi-svn.emea.tibco
         * .com/svn/projects/1976/Docs/Requirements
         * /N2%20Patterns%20In%20Studio/N2%20Patterns%20Studio%
         * 20Eclipse%20Project/Business%20Assets/BPMN%20Compensation%20In%20Studio.doc)
         */

        /* As long as it's actually a task or embedded sub-proc then it's ok. */
        if (TaskObjectUtil.getTaskTypeStrict(activity) != null) {
            return true;
        }

        return false;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.util.FixedValueFieldAssistHelper.FixedValueFieldChangedListener#fixedValueFieldChanged(java.lang.Object)
     * 
     * @param newValue
     */
    public void fixedValueFieldChanged(Object newValue) {
        CompoundCommand cmd = new CompoundCommand();
        Activity activity = getActivity();
        EditingDomain ed = getEditingDomain();
        CompensatedTasksEntry compensatedTasksEntry = null;
        String currentId = null;
        String newId = null;
        TriggerResultCompensation resultCompensation = null;

        if (ed != null && activity != null) {

            if (newValue instanceof CompensatedTasksEntry) {
                compensatedTasksEntry = (CompensatedTasksEntry) newValue;
            }
            if (null != compensatedTasksEntry) {
                newId = compensatedTasksEntry.getId();
            }
            Event event = activity.getEvent();
            if (event instanceof EndEvent) {
                resultCompensation =
                        ((EndEvent) event).getTriggerResultCompensation();
            } else if (event instanceof IntermediateEvent) {
                resultCompensation =
                        ((IntermediateEvent) event)
                                .getTriggerResultCompensation();
            }
            if (null != resultCompensation) {
                currentId = resultCompensation.getActivityId();

                if (null == currentId) {
                    currentId = ""; //$NON-NLS-1$
                }
                if (null == newId) {
                    newId = ""; //$NON-NLS-1$
                }
                if (!newId.equals(currentId)) {
                    cmd.setLabel(Messages.SetCompensationEventName_label);
                    cmd.append(SetCommand.create(ed,
                            resultCompensation,
                            Xpdl2Package.eINSTANCE
                                    .getTriggerResultCompensation_ActivityId(),
                            newId));
                }
                if (cmd != null && cmd.canExecute()) {
                    ed.getCommandStack().execute(cmd);
                }
            }
        }
    }

    /**
     * Get the selected input object as an activity
     * 
     * @return activity for event or null on error.
     */
    public Activity getActivity() {
        Object o = getInput();
        if (o instanceof Activity) {
            Activity act = (Activity) o;

            return act;
        }
        return null;
    }

}
