/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.general;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.DigitTextVerifyListener;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.DeliveryMode;
import com.tibco.xpd.xpdExtension.WsBinding;
import com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

/**
 * Section for SOAP JMS Message Configuration.This section will allow user to
 * configure 'Request-Response Timeout', 'Request Expiration Timeout', 'Delivery
 * Mode' and 'Priority'.
 * 
 * @author aprasad
 */
public class WsSoapJmsMessageConfigurationSection extends
        AbstractFilteredTransactionalSection {

    /**
     * Enumeration for {@link DeliveryMode} values for the Message
     * Configuration.Contains additional BLANK for no selection.
     * 
     * @author aprasad
     * @since 13-Jan-2015
     */
    enum DELIVERY_MODE {

        PERSISTIENT(
                DeliveryMode.PERSISTENT,
                Messages.WsSoapJmsMessageConfigurationSection_PERSISTENT_MODE_LABEL), //
        NON_PERSISTENT(
                DeliveryMode.NON_PERSISTENT,
                Messages.WsSoapJmsMessageConfigurationSection_NON_PERSISTENT_MODE_LABEL), //
        NO_SELECTION(null, ""); // //$NON-NLS-1$

        private String deliveryModeLabel;

        private DeliveryMode deliveryMode;

        private DELIVERY_MODE(DeliveryMode deliveryMode,
                String deliveryModeLabel) {
            this.deliveryModeLabel = deliveryModeLabel;
            this.deliveryMode = deliveryMode;
        }

        public String getLabel() {
            return deliveryModeLabel;
        }

        static public DELIVERY_MODE getDeliveryModeForValue(DeliveryMode value) {

            for (DELIVERY_MODE deliveryModeEnum : DELIVERY_MODE.values()) {

                if (deliveryModeEnum.deliveryMode != null
                        && deliveryModeEnum.deliveryMode.equals(value)) {
                    return deliveryModeEnum;
                }
            }
            /* DEFAULTS to No Selection */
            return NO_SELECTION;
        }

        /**
         * @see java.lang.Enum#toString()
         * 
         * @return
         */
        @Override
        public String toString() {
            return deliveryModeLabel;
        }

    }

    /**
     * Enumeration for Priority values for the Message Configuration.
     * 
     * @author aprasad
     * @since 13-Jan-2015
     */
    enum PRIORITY {

        ZERO(Messages.WsSoapJmsMessageConfigurationSection_ZERO_PRIORITY, 0), ONE(
                "1", 1), //$NON-NLS-1$
        TWO("2", 2), //$NON-NLS-1$
        THREE("3", 3), //$NON-NLS-1$
        FOUR(Messages.WsSoapJmsMessageConfigurationSection_NORMAL_PRIORITY, 4), FIVE(
                "5", 5), //$NON-NLS-1$
        SIX("6", 6), //$NON-NLS-1$
        SEVEN("7", 7), //$NON-NLS-1$
        EIGHT("8", 8), //$NON-NLS-1$
        NINE(Messages.WsSoapJmsMessageConfigurationSection_HIGHEST_PRIORITY, 9), NO_SELECTION(
                "", -1); //$NON-NLS-1$

        /**
         * Priority Value integer, -1 identifies no selection.
         */
        private int priorityValue;

        private String displayString;

        private PRIORITY(String displayString, int priorityValue) {
            this.priorityValue = priorityValue;
            this.displayString = displayString;
        }

        public int getValue() {
            return priorityValue;
        }

        static public PRIORITY getPriorityForValue(int value) {

            for (PRIORITY priority : PRIORITY.values()) {

                if (priority.priorityValue == value) {
                    return priority;
                }
            }
            /* DEFAULTS to No Selection */
            return NO_SELECTION;
        }

        /**
         * @see java.lang.Enum#toString()
         * 
         * @return
         */
        @Override
        public String toString() {
            return displayString;
        }
    }

    protected static final GridDataFactory indentedGridData = GridDataFactory
            .swtDefaults().indent(5, 0);

    /**
     * Text Input for Request response Timeout.
     */
    private Text requestResponseTimeoutText;

    /**
     * Text Input for Message Expiration Timeout.
     */
    private Text requestExpirationTimeoutText;

    /**
     * Drop Down Combo for Delivery Mode.
     */
    private ComboViewer deliveryModeSelectionCombo;

    /**
     * Drop Down for Priority.
     */
    private ComboViewer prioritySelectionCombo;

    /**
     * Consturctor.
     * 
     * @param eClass
     */
    public WsSoapJmsMessageConfigurationSection(EClass eClass) {
        super(eClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {

        Composite parentContainer = toolkit.createComposite(parent);

        GridLayoutFactory.fillDefaults().equalWidth(true).margins(1, 0)
                .applyTo(parentContainer);
        /*
         * Did not check for type of Layout on parent, as it is already
         * implemented taken into consideration that parent has GridLayout set
         */
        GridDataFactory.fillDefaults().span(1, 1).grab(true, true)
                .applyTo(parentContainer);

        createMessageConfigurationControls(parentContainer, toolkit);
        return parentContainer;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#getMinimumHeight()
     * 
     * @return
     */
    @Override
    public int getMinimumHeight() {
        return 200;
    }

    protected void createMessageConfigurationControls(Composite parent,
            XpdFormToolkit toolkit) {

        /* Message Configuration Group */
        Group messageConfigGroup =
                toolkit.createGroup(parent,
                        Messages.WsSoapJmsMessageConfigurationSection_MessageConfig_label);

        GridDataFactory.fillDefaults().grab(true, true)
                .applyTo(messageConfigGroup);
        GridLayoutFactory.fillDefaults().numColumns(3).margins(1, 2)
                .applyTo(messageConfigGroup);

        /* Request response Timeout Label */
        indentedGridData
                .applyTo(toolkit
                        .createLabel(messageConfigGroup,
                                Messages.WsSoapJmsMessageConfigurationSection_RequestRespTimeout_label));
        /* Request response Timeout Text */
        requestResponseTimeoutText = toolkit.createText(messageConfigGroup, ""); //$NON-NLS-1$
        requestResponseTimeoutText
                .addVerifyListener(new DigitTextVerifyListener());
        requestResponseTimeoutText
                .setToolTipText(Messages.WsSoapJmsMessageConfigurationSection_RequestRespTimeoutTooltip);
        GridDataFactory.fillDefaults().grab(true, false)
                .applyTo(requestResponseTimeoutText);
        manageControlUpdateOnDeactivate(requestResponseTimeoutText);

        GridDataFactory
                .fillDefaults()
                .align(SWT.END, SWT.CENTER)
                .applyTo(toolkit.createLabel(messageConfigGroup,
                        Messages.WsSoapJmsMessageConfigurationSection_SECONDS_LABEL));

        /* Message Expiration Timeout Label */
        indentedGridData
                .applyTo(toolkit
                        .createLabel(messageConfigGroup,
                                Messages.WsSoapJmsMessageConfigurationSection_RequestExpTimeout_label));

        /* Message Expiration Timeout Text */
        requestExpirationTimeoutText =
                toolkit.createText(messageConfigGroup, ""); //$NON-NLS-1$
        requestExpirationTimeoutText
                .addVerifyListener(new DigitTextVerifyListener());
        requestExpirationTimeoutText
                .setToolTipText(Messages.WsSoapJmsMessageConfigurationSection_RequestExpTimeoutTooltip);

        GridDataFactory.fillDefaults().grab(true, false)
                .applyTo(requestExpirationTimeoutText);
        manageControlUpdateOnDeactivate(requestExpirationTimeoutText);

        GridDataFactory
                .fillDefaults()
                .align(SWT.END, SWT.CENTER)
                .applyTo(toolkit.createLabel(messageConfigGroup,
                        Messages.WsSoapJmsMessageConfigurationSection_SECONDS_LABEL));

        /* Delivery Mode Label */
        indentedGridData
                .applyTo(toolkit
                        .createLabel(messageConfigGroup,
                                Messages.WsSoapJmsMessageConfigurationSection_DeliveryMode_label));
        /* Delivery Mode Combo */
        CCombo deliveryMode =
                toolkit.createCCombo(messageConfigGroup, SWT.READ_ONLY
                        | SWT.BORDER);
        deliveryMode
                .setToolTipText(Messages.WsSoapJmsMessageConfigurationSection_DeliveryModeTooltip);

        deliveryModeSelectionCombo = new ComboViewer(deliveryMode);

        deliveryModeSelectionCombo
                .setContentProvider(new ArrayContentProvider());

        deliveryModeSelectionCombo.setLabelProvider(new LabelProvider());
        deliveryModeSelectionCombo.setInput(DELIVERY_MODE.values());

        GridDataFactory.swtDefaults().span(2, 1).applyTo(deliveryMode);
        manageControl(deliveryMode);

        /* Priority Label */
        indentedGridData.applyTo(toolkit.createLabel(messageConfigGroup,
                Messages.WsSoapJmsMessageConfigurationSection_Priority_label));

        /* Priority Combo */
        CCombo priority =
                toolkit.createCCombo(messageConfigGroup, SWT.READ_ONLY
                        | SWT.BORDER);

        priority.setToolTipText(Messages.WsSoapJmsMessageConfigurationSection_PriorityTooltip);
        prioritySelectionCombo = new ComboViewer(priority);
        prioritySelectionCombo.setContentProvider(new ArrayContentProvider());
        prioritySelectionCombo.setLabelProvider(new LabelProvider());
        prioritySelectionCombo
                .setInput(getPriorityForCombo(PRIORITY.NO_SELECTION));
        GridDataFactory.swtDefaults().applyTo(priority);
        manageControl(priority);

        messageConfigGroup.computeSize(SWT.DEFAULT, SWT.DEFAULT);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Command doGetCommand(Object obj) {

        final WsBinding binding = (WsBinding) getInput();

        if (binding != null && binding instanceof WsSoapJmsOutboundBinding) {

            final WsSoapJmsOutboundBinding soapJmsBinding =
                    (WsSoapJmsOutboundBinding) binding;

            /* Request Response Timeout */
            if (obj == requestResponseTimeoutText) {

                return getSetRequestResponseTimeoutCmd(obj, soapJmsBinding);
            }
            /* Request Expiration Timeout */
            if (obj == requestExpirationTimeoutText) {

                return getSetMessageExpirationCmd(obj, soapJmsBinding);

            }
            /* Delivery Mode */
            if (obj == deliveryModeSelectionCombo.getCCombo()) {

                return getSetDeliveryModeCmd(soapJmsBinding);
            }
            /* Priority */
            if (obj == prioritySelectionCombo.getCCombo()) {

                return getSetPriorityCmd(soapJmsBinding);
            }

        }
        return null;
    }

    /**
     * Checks if the new value of Priority is different from the old value, i.e
     * if the value has changed, then creates and returns appropriate Set
     * command.Returns null when no change.Returns command to unset the value
     * when the value is deleted, and an old value exists.
     * 
     * @param obj
     * @param soapJmsBinding
     * @return command to set/unset the value as appropriate, returns null when
     *         not appropriate, which is highly unlikely.
     */
    private Command getSetPriorityCmd(
            final WsSoapJmsOutboundBinding soapJmsBinding) {

        final int oldPriority = getPriority().priorityValue;

        IStructuredSelection s =
                (IStructuredSelection) prioritySelectionCombo.getSelection();

        if (s.isEmpty()) {
            return null;
        }

        Object firstElement = s.getFirstElement();

        if (firstElement instanceof PRIORITY) {

            final PRIORITY newPriority = (PRIORITY) firstElement;
            /* Set only if value changes */
            if (newPriority.priorityValue != oldPriority) {

                return new RecordingCommand(
                        (TransactionalEditingDomain) getEditingDomain()) {
                    @Override
                    protected void doExecute() {
                        if (PRIORITY.NO_SELECTION.equals(newPriority)) {
                            soapJmsBinding.unsetPriority();
                        } else {
                            /* Set */
                            soapJmsBinding.setPriority(newPriority.getValue());
                        }

                    }
                };
            }
        }

        return null;
    }

    /**
     * Checks if the new value of {@link DeliveryMode} is different from the old
     * value, i.e if the value has changed, then creates and returns appropriate
     * Set command.Returns null when no change.Returns command to unset the
     * value when the value is deleted, and an old value exists.
     * 
     * @param soapJmsBinding
     * @return
     */
    private Command getSetDeliveryModeCmd(
            final WsSoapJmsOutboundBinding soapJmsBinding) {

        final DeliveryMode oldDeliveryMode = getDeliveryMode();

        IStructuredSelection s =
                (IStructuredSelection) deliveryModeSelectionCombo
                        .getSelection();

        if (s.isEmpty()) {
            return null;
        }

        Object firstElement = s.getFirstElement();

        if (firstElement instanceof DELIVERY_MODE) {

            final DELIVERY_MODE newDeliveryMode = (DELIVERY_MODE) firstElement;

            /* Set only if value changes */
            if (isDeliveryModeChanged(newDeliveryMode.deliveryMode,
                    oldDeliveryMode)) {

                return new RecordingCommand(
                        (TransactionalEditingDomain) getEditingDomain()) {
                    @Override
                    protected void doExecute() {

                        if (DELIVERY_MODE.NO_SELECTION.equals(newDeliveryMode)) {
                            soapJmsBinding.unsetDeliveryMode();
                        } else {
                            /* Set the new value */
                            soapJmsBinding
                                    .setDeliveryMode(newDeliveryMode.deliveryMode);
                        }
                    }
                };
            }
        }

        return null;
    }

    /**
     * Checks if the new value of Message Expiration is different from the old
     * value, i.e if the value has changed, then creates and returns appropriate
     * Set command.Returns null when no change.Returns command to unset the
     * value when the value is deleted, and an old value exists.
     * 
     * @param obj
     * @param soapJmsBinding
     * @return command to set/unset the value as appropriate, returns null when
     *         not appropriate, which is highly unlikely.
     */
    private Command getSetMessageExpirationCmd(Object obj,
            final WsSoapJmsOutboundBinding soapJmsBinding) {

        final int oldMessageExpirationTimeout = getMessageExpirationTimeout();

        Text tc = (Text) obj;
        final String newText = tc.getText();

        /* When there is a valid value */
        if (newText != null && !newText.isEmpty()) {
            try {

                final int newMessageExpirationTimeout =
                        Integer.parseInt(newText);
                /* check if it has changed */
                if (newMessageExpirationTimeout != oldMessageExpirationTimeout) {

                    return new RecordingCommand(
                            (TransactionalEditingDomain) getEditingDomain()) {
                        @Override
                        protected void doExecute() {

                            soapJmsBinding
                                    .setMessageExpiration(newMessageExpirationTimeout);
                        }
                    };
                }
            } catch (NumberFormatException nfe) {

                Xpdl2ProcessEditorPlugin
                        .getDefault()
                        .getLogger()
                        .info(Messages.WsSoapJmsMessageConfigurationSection_INVALID_VALUE_MSG
                                + Messages.WsSoapJmsMessageConfigurationSection_RequestExpTimeout_label);
                /*
                 * Set to old value
                 */
                tc.setText(String.valueOf(oldMessageExpirationTimeout));
            }

        } else {
            /* for Invalid value, Unset if already set */
            if (soapJmsBinding.eIsSet(XpdExtensionPackage.eINSTANCE
                    .getWsSoapJmsOutboundBinding_MessageExpiration())) {

                return new RecordingCommand(
                        (TransactionalEditingDomain) getEditingDomain()) {
                    @Override
                    protected void doExecute() {

                        soapJmsBinding
                                .eUnset(XpdExtensionPackage.eINSTANCE
                                        .getWsSoapJmsOutboundBinding_MessageExpiration());
                    }
                };
            }

        }
        return null;
    }

    /**
     * Checks if the Request Response Timeout new value is different from the
     * old value, i.e if the value has changed, then creates and returns
     * appropriate Set command.Returns null when no change.Returns command to
     * unset the value when the value is deleted, and an old value exists.
     * 
     * @param obj
     * @param soapJmsBinding
     * @return command to set/unset the value as appropriate, returns null when
     *         not appropriate, which is highly unlikely.
     */
    private Command getSetRequestResponseTimeoutCmd(Object obj,
            final WsSoapJmsOutboundBinding soapJmsBinding) {
        final int oldRequestResponseTimeout = getRequestResponseTimeout();

        Text tc = (Text) obj;
        final String newText = tc.getText();

        /* When there is a valid value */
        if (newText != null && !newText.isEmpty()) {
            try {

                final int newRequestResponseTimeout = Integer.parseInt(newText);
                /* check if it has changed */
                if (newRequestResponseTimeout != oldRequestResponseTimeout) {

                    return new RecordingCommand(
                            (TransactionalEditingDomain) getEditingDomain()) {
                        @Override
                        protected void doExecute() {
                            soapJmsBinding
                                    .setInvocationTimeout(newRequestResponseTimeout);
                        }
                    };
                }
            } catch (NumberFormatException nfe) {

                Xpdl2ProcessEditorPlugin
                        .getDefault()
                        .getLogger()
                        .info(Messages.WsSoapJmsMessageConfigurationSection_INVALID_VALUE_MSG
                                + Messages.WsSoapJmsMessageConfigurationSection_RequestExpTimeout_label);

                /*
                 * Reset to old value
                 */
                tc.setText(String.valueOf(oldRequestResponseTimeout));
            }

        } else {
            /* for Invalid value, Unset if already set */
            if (soapJmsBinding.eIsSet(XpdExtensionPackage.eINSTANCE
                    .getWsSoapJmsOutboundBinding_InvocationTimeout())) {

                return new RecordingCommand(
                        (TransactionalEditingDomain) getEditingDomain()) {
                    @Override
                    protected void doExecute() {

                        soapJmsBinding
                                .eUnset(XpdExtensionPackage.eINSTANCE
                                        .getWsSoapJmsOutboundBinding_InvocationTimeout());
                    }
                };
            }

        }

        return null;
    }

    /**
     * Checks if the new {@link DeliveryMode} is different from old
     * {@link DeliveryMode}.
     * 
     * @param newDeliveryMode
     * @param oldDeliveryMode
     * @return true if the new {@link DeliveryMode} is different form the old
     *         {@link DeliveryMode}.
     */
    private boolean isDeliveryModeChanged(DeliveryMode newDeliveryMode,
            DeliveryMode oldDeliveryMode) {

        if (newDeliveryMode == null) {
            /* New is null then true when old was not null */
            if (oldDeliveryMode != null) {
                return true;
            }
            /* false when old was also null */
            return false;
        }
        /* When new is not null return comparison from old */
        return !newDeliveryMode.equals(oldDeliveryMode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doRefresh() {

        final String BLANK_STRING = ""; //$NON-NLS-1$
        int requestResponseTimeout = getRequestResponseTimeout();

        requestResponseTimeoutText
                .setText((requestResponseTimeout == -1) ? BLANK_STRING : String
                        .valueOf(requestResponseTimeout));

        int requestExpirationTimeout = getMessageExpirationTimeout();

        requestExpirationTimeoutText
                .setText((requestExpirationTimeout == -1) ? BLANK_STRING
                        : String.valueOf(requestExpirationTimeout));

        DELIVERY_MODE selDeliveryMode =
                DELIVERY_MODE.getDeliveryModeForValue(getDeliveryMode());
        deliveryModeSelectionCombo
                .setInput(getDeliveryModesForCombo(selDeliveryMode));
        deliveryModeSelectionCombo.setSelection(new StructuredSelection(
                selDeliveryMode), true);

        PRIORITY selPriority = getPriority();
        prioritySelectionCombo.setInput(getPriorityForCombo(selPriority));
        prioritySelectionCombo
                .setSelection(new StructuredSelection(selPriority), true);

    }

    /**
     * Returns Request Response Timeout for the input
     * {@link WsSoapJmsOutboundBinding}, returns -1 if the input is not of type
     * WsSoapJmsOutboundBinding.
     * 
     * @return Request Response Timeout value in int.
     */
    private int getRequestResponseTimeout() {

        EObject input = getInput();
        if (input instanceof WsSoapJmsOutboundBinding) {

            WsSoapJmsOutboundBinding jmsBinding =
                    (WsSoapJmsOutboundBinding) input;

            if (jmsBinding.eIsSet(XpdExtensionPackage.eINSTANCE
                    .getWsSoapJmsOutboundBinding_InvocationTimeout())) {
                return jmsBinding.getInvocationTimeout();
            }

        }
        return -1;
    }

    /**
     * Returns Request Response Timeout for the input
     * {@link WsSoapJmsOutboundBinding}, returns -1 if the input is not of type
     * WsSoapJmsOutboundBinding.
     * 
     * @return Returns Message Expiration Timeout in int
     */
    private int getMessageExpirationTimeout() {

        EObject input = getInput();

        if (input instanceof WsSoapJmsOutboundBinding) {

            WsSoapJmsOutboundBinding jmsBinding =
                    (WsSoapJmsOutboundBinding) input;

            if (jmsBinding.eIsSet(XpdExtensionPackage.eINSTANCE
                    .getWsSoapJmsOutboundBinding_MessageExpiration())) {

                return jmsBinding.getMessageExpiration();
            }
        }
        return -1;
    }

    /**
     * Returns {@link DeliveryMode} for the {@link WsSoapJmsOutboundBinding}
     * input of this section, returns null if the input is not an instance of
     * {@link WsSoapJmsOutboundBinding} .
     * 
     * @return {@link DeliveryMode} for the input
     *         {@link WsSoapJmsOutboundBinding}, returns null when input is not
     *         of type {@link WsSoapJmsOutboundBinding}
     */
    private DeliveryMode getDeliveryMode() {
        EObject input = getInput();

        if (input instanceof WsSoapJmsOutboundBinding) {

            WsSoapJmsOutboundBinding jmsBinding =
                    (WsSoapJmsOutboundBinding) input;

            return jmsBinding.getDeliveryMode();

        }
        return null;
    }

    /**
     * Returns Priority for the input {@link WsSoapJmsOutboundBinding}, returns
     * null if the input is not of type WsSoapJmsOutboundBinding.
     * 
     * @return Priority for the input {@link WsSoapJmsOutboundBinding}, returns
     *         0 when input is not of type {@link WsSoapJmsOutboundBinding}
     */
    private PRIORITY getPriority() {
        EObject input = getInput();
        if (input instanceof WsSoapJmsOutboundBinding) {

            WsSoapJmsOutboundBinding jmsBinding =
                    (WsSoapJmsOutboundBinding) input;

            if (jmsBinding.eIsSet(XpdExtensionPackage.eINSTANCE
                    .getWsSoapJmsOutboundBinding_Priority())) {

                return PRIORITY.getPriorityForValue(jmsBinding.getPriority());
            }
        }
        return PRIORITY.NO_SELECTION;
    }

    /**
     * Returns {@link PRIORITY} list for the combo display, excludes BLANK value
     * when the model has a valid value, to restrict user from resetting to
     * null.
     * 
     * @param selPriority
     * @return Array of {@link PRIORITY} to be displayed in the Priority Combo.
     */
    private PRIORITY[] getPriorityForCombo(PRIORITY selPriority) {

        List<PRIORITY> priorities = new LinkedList<>();

        priorities.addAll(Arrays.asList(PRIORITY.values()));

        if (!PRIORITY.NO_SELECTION.equals(selPriority)) {
            priorities.remove(PRIORITY.NO_SELECTION);
        }

        return priorities.toArray(new PRIORITY[priorities.size()]);
    }

    /**
     * Returns {@link DELIVERY_MODE} list for the combo display, excludes BLANK
     * value when the model has a valid value, to restrict user from resetting
     * to null.
     * 
     * @param selDeliveryMode
     * @return Array of {@link DELIVERY_MODE} to be displayed in the Delivery
     *         Mode Combo.
     */
    private DELIVERY_MODE[] getDeliveryModesForCombo(
            DELIVERY_MODE selDeliveryMode) {

        List<DELIVERY_MODE> modes = new LinkedList<>();

        modes.addAll(Arrays.asList(DELIVERY_MODE.values()));

        if (!DELIVERY_MODE.NO_SELECTION.equals(selDeliveryMode)) {
            modes.remove(DELIVERY_MODE.NO_SELECTION);
        }

        return modes.toArray(new DELIVERY_MODE[modes.size()]);
    }
}
