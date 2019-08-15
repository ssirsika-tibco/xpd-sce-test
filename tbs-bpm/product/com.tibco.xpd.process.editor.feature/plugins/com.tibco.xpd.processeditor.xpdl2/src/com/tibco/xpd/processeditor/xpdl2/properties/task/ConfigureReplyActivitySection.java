/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IPluginContribution;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.task.TaskGeneralSection.DelegatableShouldRefresh;
import com.tibco.xpd.processeditor.xpdl2.properties.util.FixedValueFieldAssistHelper;
import com.tibco.xpd.processeditor.xpdl2.properties.util.FixedValueFieldAssistHelper.FixedValueFieldChangedListener;
import com.tibco.xpd.processeditor.xpdl2.properties.util.FixedValueFieldAssistHelper.FixedValueFieldProposalProvider;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.edit.ui.Xpdl2UiPlugin;
import com.tibco.xpd.xpdl2.edit.util.RevealProcessDiagramEObject;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * TaskTypeNoneSection
 * 
 * @author aallway
 */
public class ConfigureReplyActivitySection extends
        AbstractFilteredTransactionalSection implements IPluginContribution,
        FixedValueFieldChangedListener, DelegatableShouldRefresh {

    private Composite root;

    private Composite radioContainer;

    private Button replyToUpstreamBtn;

    private Button sendOneWayMessageBtn;

    private Composite selectRequestActivityContainer;

    private CLabel selectRequestActivityLabel;

    private List<IncomingRequestActivityEntry> incomingRequestActivityList;

    private DecoratedField selectRequestActivity;

    private Button gotoBtn;

    private Color origLabelColour;

    private Color origTextColour;

    private Boolean wasReplyActOnLastRefresh = null;

    public ConfigureReplyActivitySection() {
        super(Xpdl2Package.eINSTANCE.getActivity());
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        root = toolkit.createComposite(parent);
        GridLayout rootGl = new GridLayout(1, false);

        rootGl.marginWidth = 0;
        root.setLayout(rootGl);

        createRadioButtonsSection(toolkit);

        createSelectRequestActivityControls(toolkit);

        return root;
    }

    /**
     * @param toolkit
     */
    private void createSelectRequestActivityControls(XpdFormToolkit toolkit) {
        selectRequestActivityContainer = toolkit.createComposite(root);
        selectRequestActivityContainer.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL));
        GridLayout replyGl = new GridLayout(3, false);
        replyGl.marginHeight = 0;
        replyGl.marginLeft = 8;
        selectRequestActivityContainer.setLayout(replyGl);

        selectRequestActivityLabel =
                toolkit.createCLabel(selectRequestActivityContainer,
                        Messages.ConfigureReplyActivitySection_IncomingMsgActivity_label);
        selectRequestActivityLabel.setLayoutData(new GridData());
        origLabelColour = selectRequestActivityLabel.getForeground();

        //
        // Add content assisted text control for referenced event entry.
        FixedValueFieldProposalProvider proposalProvider =
                new FixedValueFieldAssistHelper.FixedValueFieldProposalProvider() {
                    @Override
                    public Object[] getProposals() {
                        if (incomingRequestActivityList != null) {
                            return incomingRequestActivityList.toArray();
                        }
                        return null;
                    }
                };

        FixedValueFieldAssistHelper refTaskHelper =
                new FixedValueFieldAssistHelper(toolkit,
                        selectRequestActivityContainer, proposalProvider, true);

        refTaskHelper.addValueChangedListener(this);

        selectRequestActivity = refTaskHelper.getDecoratedField();
        selectRequestActivity
                .getControl()
                .setToolTipText(Messages.ConfigureReplyActivitySection_IncomingRequestActivityLabel_tooltip);
        origTextColour = selectRequestActivity.getControl().getForeground();

        selectRequestActivity.getLayoutControl().setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL));

        selectRequestActivity.getLayoutControl()
                .setBackground(selectRequestActivityContainer.getBackground());

        //
        // And the goto button
        gotoBtn =
                toolkit.createButton(selectRequestActivityContainer,
                        Messages.GotoButton_Label,
                        SWT.PUSH);
        gotoBtn.setLayoutData(new GridData());
        manageControl(gotoBtn);
        gotoBtn
                .setToolTipText(Messages.ConfigureReplyActivitySection_GotoRequestActivity_tooltip);
    }

    /**
     * @param toolkit
     */
    private void createRadioButtonsSection(XpdFormToolkit toolkit) {
        radioContainer = toolkit.createComposite(root);
        radioContainer.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        GridLayout radioGl = new GridLayout(1, false);
        radioGl.marginHeight = 0;
        radioGl.marginWidth = 0;
        radioContainer.setLayout(radioGl);

        sendOneWayMessageBtn =
                toolkit
                        .createButton(radioContainer,
                                Messages.ConfigureReplyActivitySection_SendOneWayMsg_button,
                                SWT.RADIO);
        sendOneWayMessageBtn.setLayoutData(new GridData());
        manageControl(sendOneWayMessageBtn);
        sendOneWayMessageBtn
                .setToolTipText(Messages.ConfigureReplyActivitySection_ConfigureAsOneWayMessage_tooltip);

        replyToUpstreamBtn =
                toolkit
                        .createButton(radioContainer,
                                Messages.ConfigureReplyActivitySection_ReplyToUpstream_button,
                                SWT.RADIO);
        replyToUpstreamBtn.setLayoutData(new GridData());
        manageControl(replyToUpstreamBtn);
        replyToUpstreamBtn
                .setToolTipText(Messages.ConfigureReplyActivitySection_COnfigureAsReplyActivity_tooltip);
    }

    @Override
    protected Command doGetCommand(Object obj) {
        Activity act = getActivity();
        if (root.isDisposed() || act == null) {
            return null;
        }

        if (obj == gotoBtn) {
            Activity requestActivity =
                    ReplyActivityUtil.getRequestActivityForReplyActivity(act);
            if (requestActivity != null) {
                RevealProcessDiagramEObject.revealEObject(getSite(),
                        requestActivity,
                        true);
            }

        } else if (obj == sendOneWayMessageBtn) {
            if (ReplyActivityUtil.isReplyActivity(act)) {
                return ReplyActivityUtil
                        .getUnsetRequestActivityForReplyActivityCommand(getEditingDomain(),
                                act);
            }

        } else if (obj == replyToUpstreamBtn) {
            if (!ReplyActivityUtil.isReplyActivity(act)) {
                return ReplyActivityUtil
                        .getSetRequestActivityForReplyActivityCommand(getEditingDomain(),
                                act,
                                ""); //$NON-NLS-1$
            }
        }

        return null;
    }

    @Override
    protected void doRefresh() {
        Activity act = getActivity();
        if (root.isDisposed() || act == null) {
            return;
        }

        boolean isValid = false;
        boolean gotoEnabled = false;
        String selectRequestTooltip =
                Messages.ConfigureReplyActivitySection_IncomingRequestActivityLabel_tooltip;


        boolean isReplyActivity = ReplyActivityUtil.isReplyActivity(act);
        if (isReplyActivity) {
            // 
            // Configured as a reply activity
            sendOneWayMessageBtn.setSelection(false);
            replyToUpstreamBtn.setSelection(true);

            // Disallow change request activity for implementing end event.
            if (ProcessInterfaceUtil.isEventImplemented(getActivity())) {
                // Don't enable for implementing event.
                setEnabled(root, false);
                // But do enable the reply button.
                setEnabled(gotoBtn, true);
            } else {
                setEnabled(root, true);
            }

            updateRequestActivitiesList();

            Activity requestActivity =
                    ReplyActivityUtil.getRequestActivityForReplyActivity(act);

            if (requestActivity != null) {
                gotoEnabled = true;

                String name =
                        Xpdl2ModelUtil.getDisplayNameOrName(requestActivity);

                if (name == null || name.length() == 0) {
                    if (requestActivity.getEvent() != null) {
                        name =
                                Messages.EventTriggerTypeLinkSection_UnnamedEvent_label;
                    } else {
                        name =
                                Messages.TaskTypeReferenceSection_UnnamedTask_label;
                    }
                }

                ((Text) selectRequestActivity.getControl()).setText(name);

                // Make sure the referenced request activity is still one
                // that can actually receive a request.
                if (ReplyActivityUtil
                        .isIncomingRequestActivity(requestActivity)) {
                    /*
                     * XPD-3704 ABPM-470 - changes for option'Reply Immediate
                     * With Process Id'
                     */
                    if (ReplyActivityUtil
                            .isStartMessageWithReplyImmediate(requestActivity)) {
                        selectRequestTooltip =
                                Messages.ConfigureReplyActivitySection_InvalidReplyToReplyImmediateStartEvent;

                    } else {
                        isValid = true;
                    }
                } else {
                    selectRequestTooltip =
                            Messages.ConfigureReplyActivitySection_RefActMustBeIncomingRequest_tooltip;
                }

            } else {
                String id =
                        ReplyActivityUtil
                                .getRequestActivityIdForReplyActivity(act);
                if (id == null || id.length() == 0) {
                    selectRequestTooltip =
                            Messages.ConfigureReplyActivitySection_YouMustSelectRequestActivity_tooltip;
                    ((Text) selectRequestActivity.getControl()).setText(""); //$NON-NLS-1$

                } else {
                    selectRequestTooltip =
                            Messages.ConfigureReplyActivitySection_CannotLocateRequestActivity_tooltip;
                    ((Text) selectRequestActivity.getControl())
                            .setText(Messages.ConfigureReplyActivitySection_UnableToLocateRueqest_label);

                }
            }

        } else {
            // 
            // Configure as a Send One Way Message
            isValid = true;

            sendOneWayMessageBtn.setSelection(true);
            replyToUpstreamBtn.setSelection(false);

            setEnabled(root, true, true);
            setEnabled(selectRequestActivityContainer, false, true);

            ((Text) selectRequestActivity.getControl()).setText(""); //$NON-NLS-1$
        }

        if (isValid) {
            if (selectRequestActivityLabel.getImage() != null) {
                selectRequestActivityLabel.setImage(null);
                forceLayout();
            }

            selectRequestActivity.getControl().setForeground(origTextColour);

        } else {
            if (selectRequestActivityLabel.getImage() == null) {
                selectRequestActivityLabel.setImage(Xpdl2UiPlugin.getDefault()
                        .getImageRegistry().get(Xpdl2UiPlugin.IMG_ERROR));
                forceLayout();
            }

            selectRequestActivity.getControl()
                    .setForeground(ColorConstants.lightGray);
        }

        gotoBtn.setEnabled(gotoEnabled);
        selectRequestActivityLabel.setToolTipText(selectRequestTooltip);

        if (wasReplyActOnLastRefresh != null
                && wasReplyActOnLastRefresh != isReplyActivity) {
            refreshTabs();
        }

        wasReplyActOnLastRefresh = isReplyActivity;

        /*
         * Sid ACE-2388 Reply activities are not currently supported, disable until they are (don't hide because we want
         * to user to see what used to be reply activity in migrated processes.
         */
        replyToUpstreamBtn.setEnabled(false);
        selectRequestActivityLabel.setForeground(ColorConstants.lightGray);
        selectRequestActivity.getControl().setForeground(ColorConstants.lightGray);
        selectRequestActivity.getControl().setEnabled(false);
        gotoBtn.setEnabled(false);
        return;
    }

    /**
     * Rebuild the content proposal list for the request activity selection
     * field.
     */
    private void updateRequestActivitiesList() {
        incomingRequestActivityList =
                new ArrayList<IncomingRequestActivityEntry>();

        Collection<Activity> reqActivities =
                ReplyActivityUtil.getAllIncomingRequestActivities(getActivity()
                        .getProcess());

        for (Activity reqAct : reqActivities) {
            /*
             * XPD-3704 ABPM-470 - changes for option'Reply Immediate With
             * Process Id'
             */
            if (!ReplyActivityUtil.isStartMessageWithReplyImmediate(reqAct)) {
                incomingRequestActivityList
                        .add(new IncomingRequestActivityEntry(reqAct));
            }
        }

        return;
    }

    /**
     * The value in the request activity selection contentn assist field has
     * changed
     */
    @Override
    public void fixedValueFieldChanged(Object newValue) {

        if (getActivity() != null) {
            if (newValue instanceof IncomingRequestActivityEntry) {
                IncomingRequestActivityEntry entry =
                        (IncomingRequestActivityEntry) newValue;

                String currentRequestActId =
                        ReplyActivityUtil
                                .getRequestActivityIdForReplyActivity(getActivity());
                if (!entry.getId().equals(currentRequestActId)) {
                    Command cmd =
                            ReplyActivityUtil
                                    .getSetRequestActivityForReplyActivityCommand(getEditingDomain(),
                                            getActivity(),
                                            entry.getId());
                    if (cmd != null && cmd.canExecute()) {
                        getEditingDomain().getCommandStack().execute(cmd);
                    } else {
                        refresh();
                    }
                }
            }
        }
        return;

    }

    private Activity getActivity() {
        EObject o = getInput();
        if (o instanceof Activity) {
            return (Activity) o;
        }
        return null;
    }

    @Override
    public String getLocalId() {
        return "analyst.noneSection"; //$NON-NLS-1$
    }

    @Override
    public String getPluginId() {
        return Xpdl2ProcessEditorPlugin.ID;
    }

    /**
     * Little class for the content assist of request activities.
     * 
     * @author aallway
     * @since 3.2
     */
    public static class IncomingRequestActivityEntry {
        private Activity requestActivity;

        public IncomingRequestActivityEntry(Activity eventAct) {
            this.requestActivity = eventAct;
        }

        @Override
        public String toString() {
            return getName();
        }

        public String getName() {
            String name = Xpdl2ModelUtil.getDisplayNameOrName(requestActivity);

            if (name == null || name.length() == 0) {
                if (requestActivity.getEvent() != null) {
                    name =
                            Messages.EventTriggerTypeLinkSection_UnnamedEvent_label;
                } else {
                    name = Messages.TaskTypeReferenceSection_UnnamedTask_label;
                }
            }
            return name;
        }

        public String getId() {
            return requestActivity.getId();
        }
    }

    @Override
    protected boolean shouldRefresh(List<Notification> notifications) {
        Activity replyActivity = getActivity();
        if (replyActivity != null) {
            // Get the id of the activity we're "supposed" to reply to (this
            // will work even if that activity has been deleted).
            String id =
                    ReplyActivityUtil
                            .getRequestActivityIdForReplyActivity(getActivity());
            if (id != null && id.length() > 0) {
                // Go thru notifications, if any have an ancestor that is the
                // activity that is the request activity for our reply activity
                // then we need to refresh).
                for (Notification notification : notifications) {
                    Object ancestor = notification.getNotifier();
                    Activity activityAncestor = null;

                    // Find activity ancestor for this notifier.
                    while (ancestor instanceof EObject) {
                        if (ancestor instanceof Activity) {
                            activityAncestor = (Activity) ancestor;
                            break;
                        }

                        ancestor = ((EObject) ancestor).eContainer();
                    }

                    // Is the activity the request activity we're linked to.
                    if (activityAncestor != null
                            && id.equals(activityAncestor.getId())) {
                        return true;
                    }
                }
            }
        }

        return super.shouldRefresh(notifications);
    }

    /**
     * @return the gotoBtn
     */
    public Button getGotoBtn() {
        return gotoBtn;
    }

    @Override
    public boolean delegateShouldRefresh(List<Notification> notifications) {
        return shouldRefresh(notifications);
    }

}
