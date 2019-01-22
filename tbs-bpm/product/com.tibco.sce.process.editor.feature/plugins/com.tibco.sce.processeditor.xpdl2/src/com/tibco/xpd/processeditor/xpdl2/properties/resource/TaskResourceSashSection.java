/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.processeditor.xpdl2.properties.SashSection;
import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.task.ParticipantSelectionSection;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.SashDividedTransactionalSection;
import com.tibco.xpd.ui.properties.TextFieldVerifier;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.ActivityResourcePatterns;
import com.tibco.xpd.xpdExtension.WorkItemPriority;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author nwilson
 */
public class TaskResourceSashSection extends
        AbstractFilteredTransactionalSection implements SashSection,
        TextFieldVerifier {

    private Text initialPriorityText;

    private ParticipantSelectionSection participantSelectionSection;

    /**
     * @param class1
     */
    public TaskResourceSashSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());
    }

    @Override
    public void setInput(Collection<?> items) {
        super.setInput(items);

        if (participantSelectionSection != null) {
            participantSelectionSection.setInput(items);
        }
    }

    @Override
    public void dispose() {
        if (participantSelectionSection != null) {
            participantSelectionSection.dispose();
        }
        super.dispose();
    }

    @Override
    public void aboutToBeShown() {
        super.aboutToBeShown();
        if (participantSelectionSection != null) {
            participantSelectionSection.aboutToBeShown();
        }
    }

    @Override
    public void aboutToBeHidden() {
        if (participantSelectionSection != null) {
            participantSelectionSection.aboutToBeHidden();
        }
        super.aboutToBeHidden();
    }

    /**
     * @param toTest
     * @return
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     */
    public boolean select(Object toTest) {
        boolean ok = false;
        if (toTest instanceof Activity) {
            Activity activity = (Activity) toTest;
            Implementation impl = activity.getImplementation();
            if (impl instanceof Task) {
                Task task = (Task) impl;
                if (task.getTaskUser() != null || task.getTaskManual() != null) {
                    ok = true;
                }
            }
        }
        return ok;
    }

    /**
     * @param parent
     * @param toolkit
     * @return
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doCreateControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        int textWidthHint;
        textWidthHint = SashDividedTransactionalSection.TEXT_WIDTH_HINT;

        Composite container = toolkit.createComposite(parent);

        final int cols = 4;
        GridData gData;
        container.setLayout(new GridLayout(cols, false));

        // 
        // Participant selection.
        Composite particComposite = toolkit.createComposite(container);
        gData = new GridData(GridData.FILL_HORIZONTAL);
        gData.horizontalSpan = cols;
        particComposite.setLayoutData(gData);

        FillLayout fl = new FillLayout();
        fl.marginHeight = 0;
        fl.marginWidth = 0;
        particComposite.setLayout(fl);

        participantSelectionSection =
                new ParticipantSelectionSection(particComposite.getParent());
        if (getInput() != null) {
            participantSelectionSection.setInput(Collections.singletonList(getInput()));
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

        // MR 38600 - begin
        Label initialPriorityLable =
                toolkit.createLabel(container,
                        Messages.TaskGeneralSection_INITIALPRIORITY_LABEL,
                        SWT.WRAP);
        initialPriorityText =
                toolkit.createText(container, "", "textInitialPriority"); //$NON-NLS-1$//$NON-NLS-2$

        gData = new GridData(GridData.FILL_HORIZONTAL);
        gData.widthHint = textWidthHint;
        gData.horizontalSpan = cols - 1;
        initialPriorityText.setLayoutData(gData);

        manageControl(initialPriorityText);

        List<Control> labels = new ArrayList<Control>();

        labels.add(participantSelectionSection.getParticLabel());
        labels.add(initialPriorityLable);
        setSameGridDataWidth(labels, null);

        // MR 38600 - end
        return container;
    }

    /**
     * @param obj
     * @return
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     */
    @Override
    protected Command doGetCommand(Object obj) {
        Command command = null;
        CompoundCommand cmd = new CompoundCommand();
        EditingDomain ed = getEditingDomain();
        Object input = getInput();
        if (input instanceof Activity) {
            Activity activity = (Activity) input;

            // MR 38600 - begin
            if (obj == initialPriorityText) {
                ActivityResourcePatterns activityResourcePatterns =
                        (ActivityResourcePatterns) Xpdl2ModelUtil
                                .getOtherElement(activity,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_ActivityResourcePatterns());
                WorkItemPriority workItemPriority =
                        XpdExtensionFactory.eINSTANCE.createWorkItemPriority();
                /**
                 * creating ActivityResourcePatterns if it does not already
                 * exist in the model
                 * */
                if (null == activityResourcePatterns) {
                    activityResourcePatterns =
                            XpdExtensionFactory.eINSTANCE
                                    .createActivityResourcePatterns();
                    activityResourcePatterns
                            .setWorkItemPriority(workItemPriority);
                    workItemPriority.setInitialPriority(initialPriorityText
                            .getText());
                    cmd
                            .append(Xpdl2ModelUtil
                                    .getSetOtherElementCommand(ed,
                                            activity,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ActivityResourcePatterns(),
                                            activityResourcePatterns));
                } else {
                    /**
                     * ActivityResourcePatterns already exists in the model, and
                     * WorkItemPriority also exist in the model. So we set the
                     * initial priority value to the existing WorkItemPriority
                     * */
                    if (null != activityResourcePatterns.getWorkItemPriority()) {
                        workItemPriority =
                                activityResourcePatterns.getWorkItemPriority();
                        /**
                         * if the initial priority is made empty, then don't
                         * update the model and roll back to the previous value
                         * */
                        if (initialPriorityText.getText().length() > 0) {
                            cmd
                                    .append(SetCommand
                                            .create(ed,
                                                    workItemPriority,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getWorkItemPriority_InitialPriority(),
                                                    initialPriorityText
                                                            .getText()));
                        }
                    } else {
                        /**
                         * ActivityResourcePatterns already exists in the model,
                         * but WorkItemPriority does not exist. Hence we set the
                         * WorkItemPriority to the ActivityResourcePatterns
                         * */
                        workItemPriority.setInitialPriority(initialPriorityText
                                .getText());
                        cmd
                                .append(SetCommand
                                        .create(ed,
                                                activityResourcePatterns,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getActivityResourcePatterns_WorkItemPriority(),
                                                workItemPriority));
                    }
                }
                command = cmd;
            }
            // MR 38600 - end
        }
        return command;
    }

    /**
     * 
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     */
    @Override
    protected void doRefresh() {
        Object input = getInput();
        if (input instanceof Activity) {
            Activity activity = (Activity) input;
            EObject[] performers =
                    TaskObjectUtil.getActivityPerformers(activity);

            participantSelectionSection.refresh();

            // MR 38600 - begin
            ActivityResourcePatterns activityResourcePatterns =
                    (ActivityResourcePatterns) Xpdl2ModelUtil
                            .getOtherElement(activity,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_ActivityResourcePatterns());
            //updateText(initialPriorityText, ""); //$NON-NLS-1$
            if (null != activityResourcePatterns) {
                WorkItemPriority workItemPriority =
                        activityResourcePatterns.getWorkItemPriority();
                if (null != workItemPriority
                        && null != workItemPriority.getInitialPriority()) {
                    updateText(initialPriorityText, workItemPriority
                            .getInitialPriority());
                }
            }
            // MR 38600 - end
        }
    }

    public boolean shouldSashSectionRefresh(List<Notification> notifications) {
        return shouldRefresh(notifications);
    }

    public void verifyText(Event event) {
        Text textControl = ((Text) event.widget);
        if (textControl == initialPriorityText) {
            String t =
                    textControl.getText(0, event.start - 1)
                            + event.text
                            + textControl.getText(event.end, textControl
                                    .getCharCount() - 1);
            if ("".equals(t) || "-".equals(t)) { //$NON-NLS-1$ //$NON-NLS-2$
                event.doit = true;
                return;
            }

            try {
                Short.valueOf(t);
                event.doit = true;
                return;
            } catch (NumberFormatException nfe) {
                event.doit = false;
                return;
            }
        }
    }

    public boolean isWizard() {
        return getSectionContainerType() == ContainerType.WIZARD;
    }

}
