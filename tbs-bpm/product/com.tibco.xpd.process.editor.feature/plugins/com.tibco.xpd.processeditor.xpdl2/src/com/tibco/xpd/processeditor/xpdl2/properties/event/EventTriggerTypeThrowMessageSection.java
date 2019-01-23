/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.event;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.task.ConfigureReplyActivitySection;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;

/**
 * Property section for Event with trigger type Message
 * 
 * @author aallway
 * 
 */
public class EventTriggerTypeThrowMessageSection extends
        AbstractFilteredTransactionalSection {

    private Text nameText;

    private Text toText;

    private Text fromText;

    private Text faultNameText;

    private ConfigureReplyActivitySection configureReplyActivitySection;

    private Composite replySectionContainer;

    public EventTriggerTypeThrowMessageSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());

        configureReplyActivitySection = new ConfigureReplyActivitySection();
    }

    @Override
    protected Composite doCreateControls(Composite parent,
            XpdFormToolkit toolkit) {

        GridData gData;

        Composite root = toolkit.createComposite(parent);
        root.setLayout(new GridLayout(2, false));

        replySectionContainer = toolkit.createComposite(root);
        replySectionContainer.setLayout(new FillLayout());
        gData = new GridData(GridData.FILL_HORIZONTAL);
        gData.horizontalSpan = 2;
        replySectionContainer.setLayoutData(gData);

        if (getSectionContainerType() == ContainerType.WIZARD) {
            configureReplyActivitySection.createControls(replySectionContainer,
                    toolkit);
        } else {
            configureReplyActivitySection.createControls(replySectionContainer,
                    getPropertySheetPage());
        }
        configureReplyActivitySection
                .setXpdSectionContainerProvider(getXpdSectionContainerProvider());

        toolkit.createLabel(root,
                Messages.EventTriggerTypeMessageSection_Name_label,
                SWT.NONE);
        nameText = toolkit.createText(root, "", SWT.NONE); //$NON-NLS-1$
        nameText.setData("name", "textEventMessageName"); //$NON-NLS-1$ //$NON-NLS-2$
        gData = new GridData(SWT.FILL, SWT.CENTER, true, false);
        gData.widthHint = LAYOUT_DATA_SHORT_WIDTH_HINT;
        nameText.setLayoutData(gData);
        manageControl(nameText);

        toolkit.createLabel(root,
                Messages.EventTriggerTypeMessageSection_To_label,
                SWT.NONE);
        toText = toolkit.createText(root, "", SWT.NONE); //$NON-NLS-1$
        toText.setData("name", "textEventMessageTo"); //$NON-NLS-1$ //$NON-NLS-2$
        gData = new GridData(SWT.FILL, SWT.CENTER, true, false);
        gData.widthHint = LAYOUT_DATA_SHORT_WIDTH_HINT;
        toText.setLayoutData(gData);
        manageControl(toText);

        toolkit.createLabel(root,
                Messages.EventTriggerTypeMessageSection_From_label,
                SWT.NONE);
        fromText = toolkit.createText(root, "", SWT.NONE); //$NON-NLS-1$
        fromText.setData("name", "textEventMessageFrom"); //$NON-NLS-1$ //$NON-NLS-2$
        gData = new GridData(SWT.FILL, SWT.CENTER, true, false);
        gData.widthHint = LAYOUT_DATA_SHORT_WIDTH_HINT;
        fromText.setLayoutData(gData);
        manageControl(fromText);

        toolkit.createLabel(root,
                Messages.EventTriggerTypeMessageSection_Fault_label,
                SWT.NONE);
        faultNameText = toolkit.createText(root, "", SWT.NONE); //$NON-NLS-1$
        faultNameText.setData("name", "textEventMessageFaultName"); //$NON-NLS-1$ //$NON-NLS-2$
        gData = new GridData(SWT.FILL, SWT.CENTER, true, false);
        gData.widthHint = LAYOUT_DATA_SHORT_WIDTH_HINT;
        faultNameText.setLayoutData(gData);
        manageControl(faultNameText);

        return root;
    }

    @Override
    public void setInput(IWorkbenchPart part, ISelection selection) {
        super.setInput(part, selection);
        
        configureReplyActivitySection.setInput(part, selection);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang
     * .Object)
     */
    @Override
    protected Command doGetCommand(Object obj) {
        CompoundCommand cmd = null;

        EditingDomain ed = getEditingDomain();
        Activity eventAct = getActivity();

        if (eventAct != null && ed != null) {

            Object trmObj = eventAct.getEvent().getEventTriggerTypeNode();
            if (trmObj instanceof TriggerResultMessage) {
                TriggerResultMessage trm = (TriggerResultMessage) trmObj;

                Message msg = trm.getMessage();
                if (msg != null) {
                    if (obj == nameText) {
                        cmd = new CompoundCommand();
                        cmd
                                .setLabel(Messages.EventTriggerTypeMessageSection_SetMsgName_menu);
                        cmd.append(SetCommand.create(ed,
                                msg,
                                Xpdl2Package.eINSTANCE.getNamedElement_Name(),
                                nameText.getText()));

                    } else if (obj == toText) {
                        cmd = new CompoundCommand();
                        cmd
                                .setLabel(Messages.EventTriggerTypeMessageSection_SetMsgTo_menu);
                        cmd.append(SetCommand.create(ed,
                                msg,
                                Xpdl2Package.eINSTANCE.getMessage_To(),
                                toText.getText()));
                    } else if (obj == fromText) {
                        cmd = new CompoundCommand();
                        cmd
                                .setLabel(Messages.EventTriggerTypeMessageSection_SetMsgFrom_menu);
                        cmd.append(SetCommand.create(ed,
                                msg,
                                Xpdl2Package.eINSTANCE.getMessage_From(),
                                fromText.getText()));

                    } else if (obj == faultNameText) {
                        cmd = new CompoundCommand();
                        cmd
                                .setLabel(Messages.EventTriggerTypeMessageSection_SetMsgFaultName_menu);
                        cmd.append(SetCommand.create(ed,
                                msg,
                                Xpdl2Package.eINSTANCE.getMessage_FaultName(),
                                faultNameText.getText()));

                    }

                }
            }

        }
        return cmd;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     */
    @Override
    protected void doRefresh() {

        if (nameText.isDisposed()) {
            return;
        }

        configureReplyActivitySection.refresh();

        if (getActivity() != null
                && ReplyActivityUtil.isReplyActivity(getActivity())) {

            nameText.setEnabled(false);
            toText.setEnabled(false);
            fromText.setEnabled(false);
            faultNameText.setEnabled(false);
        } else {
            nameText.setEnabled(true);
            toText.setEnabled(true);
            fromText.setEnabled(true);
            faultNameText.setEnabled(true);
        }

        Message msg = getMessage();

        if (msg != null) {

            updateText(nameText, msg.getName());
            updateText(toText, msg.getTo());
            updateText(fromText, msg.getFrom());
            updateText(faultNameText, msg.getFaultName());

        } else {
            updateText(nameText, null);
            updateText(toText, null);
            updateText(fromText, null);
            updateText(faultNameText, null);
        }

    }

    /**
     * Get The message element associated with input event activity.
     * 
     * @return
     */
    private Message getMessage() {
        Activity eventAct = getActivity();

        if (eventAct != null) {

            Object trmObj = eventAct.getEvent().getEventTriggerTypeNode();
            if (trmObj instanceof TriggerResultMessage) {
                TriggerResultMessage trm = (TriggerResultMessage) trmObj;

                Message msg = trm.getMessage();
                if (msg != null) {
                    return msg;
                }
            }
        }
        return null;
    }

    /**
     * Get the selected input object as an activity
     * 
     * @return activity for event or null on error.
     */
    private Activity getActivity() {
        Object o = getInput();
        if (o instanceof Activity) {
            Activity act = (Activity) o;

            return act;
        }
        return null;
    }

    @Override
    protected boolean shouldRefresh(List<Notification> notifications) {
        if (configureReplyActivitySection != null) {
            return super.shouldRefresh(notifications)
                    || configureReplyActivitySection
                            .delegateShouldRefresh(notifications);
        }
        return super.shouldRefresh(notifications);
    }

}
