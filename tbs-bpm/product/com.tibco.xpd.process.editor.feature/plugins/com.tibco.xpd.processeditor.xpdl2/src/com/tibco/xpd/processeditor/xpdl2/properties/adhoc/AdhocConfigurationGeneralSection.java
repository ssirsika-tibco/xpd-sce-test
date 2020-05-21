/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.adhoc;

import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.SashSection;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.AdHocExecutionTypeType;
import com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Sash selection that shows the Adhoc Task General Collapsible section.
 * 
 * @author kthombar
 * @since 30-Jul-2014
 */
public class AdhocConfigurationGeneralSection extends
        AbstractFilteredTransactionalSection implements SashSection {

    private Button allowMultipleInvocationsCheckBox;

    private Button automatic;

    private Button manual;

    private Button suspendMainFlow;

    /**
     * 
     */
    public AdhocConfigurationGeneralSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());

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
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {

        EObject input = getInput();

        if (input instanceof Activity) {

            Activity activity = (Activity) input;

            Object adhocConfig =
                    Xpdl2ModelUtil.getOtherElement(activity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_AdHocTaskConfiguration());

            if (adhocConfig instanceof AdHocTaskConfigurationType) {

                AdHocTaskConfigurationType adhocConfigType =
                        (AdHocTaskConfigurationType) adhocConfig;

                suspendMainFlow.setSelection(adhocConfigType
                        .isSuspendMainFlow());

                AdHocExecutionTypeType adHocExecutionType =
                        adhocConfigType.getAdHocExecutionType();

                if (AdHocExecutionTypeType.AUTOMATIC.equals(adHocExecutionType)) {

                    automatic.setSelection(true);
                    manual.setSelection(false);

                } else if (AdHocExecutionTypeType.MANUAL
                        .equals(adHocExecutionType)) {

                    manual.setSelection(true);
                    automatic.setSelection(false);

                    allowMultipleInvocationsCheckBox
                            .setSelection(adhocConfigType
                                    .isAllowMultipleInvocations());
                }
            }
        }

        if (automatic.getSelection()) {
            allowMultipleInvocationsCheckBox.setEnabled(false);
            allowMultipleInvocationsCheckBox.setSelection(false);
        } else {
            allowMultipleInvocationsCheckBox.setEnabled(true);
        }

        /* Sid ACE-3680 Disallow Manual Ad-Hoc in ACE for now. */
        manual.setEnabled(false);
        allowMultipleInvocationsCheckBox.setEnabled(false);

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

        Composite composite = toolkit.createComposite(parent);

        GridLayout layout = new GridLayout(1, false);
        layout.marginLeft = -10;
        composite.setLayout(layout);

        Composite suspendMainProcComposite = toolkit.createComposite(composite);
        suspendMainProcComposite.setLayout(new GridLayout(1, false));
        // suspend main process check-box.
        suspendMainFlow =
                toolkit.createButton(suspendMainProcComposite,
                        Messages.AdhocConfigurationGeneralSection_SuspendMainFlowCheckBox_label,
                        SWT.CHECK);
        suspendMainFlow
                .setToolTipText(Messages.AdhocConfigurationGeneralSection_SuspendMainFlowCheckBox_tooltip);

        GridData labelLayout = new GridData(GridData.FILL_HORIZONTAL);
        suspendMainFlow.setLayoutData(labelLayout);
        manageControl(suspendMainFlow);

        Composite invocationComposite = toolkit.createComposite(composite);

        invocationComposite.setLayout(new GridLayout(2, true));

        GridData invocationCompositeLayoutData =
                new GridData(GridData.FILL_HORIZONTAL);
        invocationComposite.setLayoutData(invocationCompositeLayoutData);
        // automatic button
        automatic =
                toolkit.createButton(invocationComposite,
                        Messages.AdhocConfigurationGeneralSection_AutomaticInvocationRadioButton_label,
                        SWT.RADIO,
                        "invocationType"); //$NON-NLS-1$
        automatic
                .setToolTipText(Messages.AdhocConfigurationGeneralSection_AutomaticInvocationRadioButton_tooltip);

        manageControl(automatic);
        // manual button
        manual =
                toolkit.createButton(invocationComposite,
                        Messages.AdhocConfigurationGeneralSection_ManualnvocationRadioButton_label,
                        SWT.RADIO,
                        "invocationType"); //$NON-NLS-1$
        manual.setToolTipText(Messages.AdhocConfigurationGeneralSection_ManualnvocationRadioButton_tooltip);

        manageControl(manual);


        // a dummy label to fill the grid
        toolkit.createLabel(invocationComposite, ""); //$NON-NLS-1$
        // allow multiple invocatio button
        allowMultipleInvocationsCheckBox =
                toolkit.createButton(invocationComposite,
                        Messages.AdhocConfigurationGeneralSection_AllowMultipleInvocationCheckBox_label,
                        SWT.CHECK);
        allowMultipleInvocationsCheckBox
                .setToolTipText(Messages.AdhocConfigurationGeneralSection_AllowMultipleInvocationCheckBox_tooltip);

        manageControl(allowMultipleInvocationsCheckBox);

        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalIndent = 16;
        allowMultipleInvocationsCheckBox.setLayoutData(gd);

        return composite;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    protected Command doGetCommand(Object obj) {

        CompoundCommand cmd = new CompoundCommand();

        EObject input = getInput();

        if (input instanceof Activity) {

            Activity activity = (Activity) input;

            EditingDomain editingDomain = getEditingDomain();

            Object adhocConfig =
                    Xpdl2ModelUtil.getOtherElement(activity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_AdHocTaskConfiguration());

            if (adhocConfig instanceof AdHocTaskConfigurationType) {

                AdHocTaskConfigurationType adhocConfigType =
                        (AdHocTaskConfigurationType) adhocConfig;

                if (obj == suspendMainFlow) {

                    cmd.setLabel(Messages.AdhocConfigurationGeneralSection_SuspendMainProcessCommand_desc);
                    cmd.append(SetCommand
                            .create(editingDomain,
                                    adhocConfigType,
                                    XpdExtensionPackage.eINSTANCE
                                            .getAdHocTaskConfigurationType_SuspendMainFlow(),
                                    ((Button) obj).getSelection()));

                } else if (obj == automatic) {

                    cmd.setLabel(Messages.AdhocConfigurationGeneralSection_SetAutomaticAdhocTypeCommand_desc);
                    cmd.append(SetCommand
                            .create(editingDomain,
                                    adhocConfigType,
                                    XpdExtensionPackage.eINSTANCE
                                            .getAdHocTaskConfigurationType_AdHocExecutionType(),
                                    AdHocExecutionTypeType.AUTOMATIC));

                    if (adhocConfigType.isSetAllowMultipleInvocations()) {

                        cmd.append(SetCommand
                                .create(editingDomain,
                                        adhocConfigType,
                                        XpdExtensionPackage.eINSTANCE
                                                .getAdHocTaskConfigurationType_AllowMultipleInvocations(),
                                        SetCommand.UNSET_VALUE));
                    }
                } else if (obj == manual) {

                    cmd.setLabel(Messages.AdhocConfigurationGeneralSection_SetManualAdhocType_Command_desc);
                    cmd.append(SetCommand
                            .create(editingDomain,
                                    adhocConfigType,
                                    XpdExtensionPackage.eINSTANCE
                                            .getAdHocTaskConfigurationType_AdHocExecutionType(),
                                    AdHocExecutionTypeType.MANUAL));
                } else if (obj == allowMultipleInvocationsCheckBox) {

                    cmd.setLabel(Messages.AdhocConfigurationGeneralSection_SetMultipleManualAhdocInvocationCommand_desc);
                    cmd.append(SetCommand
                            .create(editingDomain,
                                    adhocConfigType,
                                    XpdExtensionPackage.eINSTANCE
                                            .getAdHocTaskConfigurationType_AllowMultipleInvocations(),
                                    ((Button) obj).getSelection()));
                }
            }
        }
        return cmd;
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
