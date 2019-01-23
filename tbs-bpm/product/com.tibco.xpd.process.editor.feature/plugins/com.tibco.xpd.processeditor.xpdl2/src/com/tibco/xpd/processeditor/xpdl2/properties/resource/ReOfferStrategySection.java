/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.resource;

import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.SashSection;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.ElementsFactory;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.ActivityResourcePatterns;
import com.tibco.xpd.xpdExtension.AllocationStrategy;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Re-offer strategy section for resources tab.
 * 
 * @author aallway
 * @since 23 May 2012
 */
public class ReOfferStrategySection extends
        AbstractFilteredTransactionalSection implements SashSection {

    private Button reofferOnCloseBtn;

    private Button reofferOnCancelBtn;

    private Composite btnContainer;

    private Label descriptionLabel;

    /**
     * Re-offer strategy section for resources tab.
     */
    public ReOfferStrategySection() {
        super(Xpdl2Package.eINSTANCE.getActivity());
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

        descriptionLabel =
                toolkit.createLabel(root,
                        Messages.ReOfferStrategySection_ReOfferStrategy_description,
                        SWT.WRAP);

        btnContainer = toolkit.createComposite(root);
        GridLayout gl = new GridLayout(3, false);
        btnContainer.setLayout(gl);

        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        btnContainer.setLayoutData(gd);

        reofferOnCloseBtn =
                toolkit.createButton(btnContainer,
                        Messages.ReOfferStrategySection_ReOfferOnClose_checkbox,
                        SWT.CHECK);
        reofferOnCloseBtn
                .setToolTipText(Messages.ReOfferStrategySection_ReofferOnClose_tooltip);
        gd = new GridData();
        reofferOnCloseBtn.setLayoutData(gd);
        manageControl(reofferOnCloseBtn);

        reofferOnCancelBtn =
                toolkit.createButton(btnContainer,
                        Messages.ReOfferStrategySection_ReOfferOnCancel_checkbox,
                        SWT.CHECK);
        reofferOnCancelBtn
                .setToolTipText(Messages.ReOfferStrategySection_ReofferOnCancel_tooltip);
        gd = new GridData();
        reofferOnCancelBtn.setLayoutData(gd);
        manageControl(reofferOnCancelBtn);

        Composite filler = toolkit.createComposite(btnContainer);
        gd = new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL);
        gd.heightHint = 2;
        filler.setLayoutData(gd);

        Point sz = btnContainer.computeSize(SWT.DEFAULT, SWT.DEFAULT);
        GridData labelLayout = new GridData(GridData.FILL_HORIZONTAL);
        labelLayout.widthHint = sz.x;
        descriptionLabel.setLayoutData(labelLayout);

        return root;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {
        if (getInput() instanceof Activity && reofferOnCancelBtn != null
                && !reofferOnCancelBtn.isDisposed()) {

            Activity activity = (Activity) getInput();

            boolean reofferOnClose = false;
            boolean reofferOnCancel = false;

            AllocationStrategy allocationStrategy =
                    getAllocationStrategy(activity);

            if (allocationStrategy != null) {
                reofferOnCancel = allocationStrategy.isReOfferOnCancel();
                reofferOnClose = allocationStrategy.isReOfferOnClose();
            }

            reofferOnCancelBtn.setSelection(reofferOnCancel);
            reofferOnCloseBtn.setSelection(reofferOnClose);
        }
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    protected Command doGetCommand(Object obj) {
        if (getInput() instanceof Activity) {
            Activity activity = (Activity) getInput();

            if (obj == reofferOnCancelBtn) {
                CompoundCommand cmd =
                        new CompoundCommand(
                                Messages.ReOfferStrategySection_SetReOfferStrategy_menu);

                AllocationStrategy allocationStrategy =
                        getOrCreateAllocationStrategy(activity,
                                getEditingDomain(),
                                cmd);
                cmd.append(SetCommand.create(getEditingDomain(),
                        allocationStrategy,
                        XpdExtensionPackage.eINSTANCE
                                .getAllocationStrategy_ReOfferOnCancel(),
                        reofferOnCancelBtn.getSelection()));
                return cmd;

            } else if (obj == reofferOnCloseBtn) {
                CompoundCommand cmd =
                        new CompoundCommand(
                                Messages.ReOfferStrategySection_SetReOfferStrategy_menu);

                AllocationStrategy allocationStrategy =
                        getOrCreateAllocationStrategy(activity,
                                getEditingDomain(),
                                cmd);

                cmd.append(SetCommand.create(getEditingDomain(),
                        allocationStrategy,
                        XpdExtensionPackage.eINSTANCE
                                .getAllocationStrategy_ReOfferOnClose(),
                        reofferOnCloseBtn.getSelection()));
                return cmd;
            }
        }

        return null;
    }

    /**
     * @param activity
     * @param editingDomain
     * @param cmd
     * @return The {@link AllocationStrategy} extension element (added to
     *         activity if required).
     */
    private AllocationStrategy getOrCreateAllocationStrategy(Activity activity,
            EditingDomain editingDomain, CompoundCommand cmd) {
        AllocationStrategy allocationStrategy = null;

        TaskType taskType = TaskObjectUtil.getTaskType(activity);

        ActivityResourcePatterns activityResourcePatterns =
                (ActivityResourcePatterns) Xpdl2ModelUtil
                        .getOtherElement(activity,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_ActivityResourcePatterns());

        if (activityResourcePatterns == null) {
            activityResourcePatterns =
                    ElementsFactory.createActivityResourcePatterns(taskType);

            allocationStrategy =
                    activityResourcePatterns.getAllocationStrategy();

            allocationStrategy.setReOfferOnCancel(reofferOnCancelBtn
                    .getSelection());
            allocationStrategy.setReOfferOnClose(reofferOnCloseBtn
                    .getSelection());

            cmd.append(Xpdl2ModelUtil.getSetOtherElementCommand(editingDomain,
                    activity,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_ActivityResourcePatterns(),
                    activityResourcePatterns));

        } else {
            allocationStrategy =
                    activityResourcePatterns.getAllocationStrategy();
            if (allocationStrategy == null) {
                allocationStrategy =
                        ElementsFactory.createAllocationStrategy(taskType);

                allocationStrategy.setReOfferOnCancel(reofferOnCancelBtn
                        .getSelection());
                allocationStrategy.setReOfferOnClose(reofferOnCloseBtn
                        .getSelection());

                cmd.append(SetCommand
                        .create(editingDomain,
                                activityResourcePatterns,
                                XpdExtensionPackage.eINSTANCE
                                        .getActivityResourcePatterns_AllocationStrategy(),
                                allocationStrategy));
            }
        }

        return allocationStrategy;
    }

    /**
     * @param activity
     * @return The {@link AllocationStrategy} extension element or
     *         <code>null</code> if not defined.
     */
    public AllocationStrategy getAllocationStrategy(Activity activity) {
        AllocationStrategy allocationStrategy = null;

        ActivityResourcePatterns arp =
                (ActivityResourcePatterns) Xpdl2ModelUtil
                        .getOtherElement(activity,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_ActivityResourcePatterns());

        if (arp != null) {
            allocationStrategy = arp.getAllocationStrategy();

        }
        return allocationStrategy;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection#select(java.lang.Object)
     * 
     * @param toTest
     * @return
     */
    @Override
    public boolean select(Object toTest) {
        if (toTest instanceof Activity) {
            if (TaskType.USER_LITERAL.equals(TaskObjectUtil
                    .getTaskType((Activity) toTest))) {
                return true;
            }
        }
        return false;
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

}
