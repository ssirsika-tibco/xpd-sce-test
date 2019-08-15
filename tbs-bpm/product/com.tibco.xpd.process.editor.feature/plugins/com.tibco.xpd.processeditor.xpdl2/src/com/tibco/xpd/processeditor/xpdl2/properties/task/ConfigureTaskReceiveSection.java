/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.task;

import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IPluginContribution;

import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.task.TaskGeneralSection.DelegatableShouldRefresh;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskReceive;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * LHS details section for receive tasks (ACE-2388)
 *
 * @author aallway
 * @since 15 Aug 2019
 */
public class ConfigureTaskReceiveSection extends
        AbstractFilteredTransactionalSection implements IPluginContribution,
        DelegatableShouldRefresh {

    private Composite root;

    /**
     * Check box for "Correlate Immediately", available on correlating activities.
     */
    private Group correlationGroup;

    protected Button correlateImmediately;

    public ConfigureTaskReceiveSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        root = toolkit.createComposite(parent);
        GridLayout rootGl = new GridLayout(1, false);
        rootGl.marginWidth = 0;
        root.setLayout(rootGl);

        /*
         * Sid ACE-2388 Add correlate immediately controls
         */
        correlationGroup =
                toolkit.createGroup(root, Messages.EventTriggerTypeIntermediateRequestSection_CorrelationGroup_label);
        GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.horizontalSpan = 2;

        correlationGroup.setLayoutData(gridData);
        correlationGroup.setLayout(new GridLayout(1, false));

        correlateImmediately = toolkit.createButton(correlationGroup,
                Messages.EventTriggerTypeIntermediateRequestSection_CorrelateImmediately_checkbox,
                SWT.CHECK);
        correlateImmediately.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        manageControl(correlateImmediately);

        return root;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        Activity act = getActivity();
        if (root.isDisposed() || act == null) {
            return null;
        }

        if (obj == correlateImmediately) {
            if (act.getImplementation() instanceof Task) {
                if (((Task) act.getImplementation()).getTaskReceive() != null) {
                    TaskReceive taskReceive = ((Task) act.getImplementation()).getTaskReceive();

                    /*
                     * Sid ACE-2388 handle correlate immediately setting.
                     */
                    boolean isCorrelateImmediately = correlateImmediately.getSelection();
                    CompoundCommand cmd = new CompoundCommand(isCorrelateImmediately
                            ? Messages.EventTriggerTypeIntermediateRequestSection_SetCorrelateImmediatley_menu
                            : Messages.EventTriggerTypeIntermediateRequestSection_UnsetCorrelateImmediatley_menu);

                    cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(getEditingDomain(),
                            taskReceive,
                            XpdExtensionPackage.eINSTANCE.getDocumentRoot_CorrelateImmediately(),
                            isCorrelateImmediately));

                    return cmd;
                }
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

        if (act.getImplementation() instanceof Task) {
            if (((Task) act.getImplementation()).getTaskReceive() != null) {
                TaskReceive taskReceive = ((Task) act.getImplementation()).getTaskReceive();

                /*
                 * Sid ACE-2388 handle correlate immediately setting.
                 */
                boolean isCorrelateImmediately = Xpdl2ModelUtil.getOtherAttributeAsBoolean(taskReceive,
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_CorrelateImmediately());

                correlateImmediately.setSelection(isCorrelateImmediately);

            }
        }

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
        return "taskreceive.config.section"; //$NON-NLS-1$
    }

    @Override
    public String getPluginId() {
        return Xpdl2ProcessEditorPlugin.ID;
    }


    @Override
    public boolean delegateShouldRefresh(List<Notification> notifications) {
        return shouldRefresh(notifications);
    }

}
