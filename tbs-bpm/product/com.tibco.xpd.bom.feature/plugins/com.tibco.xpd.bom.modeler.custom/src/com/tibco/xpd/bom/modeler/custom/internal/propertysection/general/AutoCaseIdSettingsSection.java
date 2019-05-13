/*
 * Copyright (c) TIBCO Software Inc. 2004, 2019. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.internal.propertysection.general;

import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.util.UMLUtil;

import com.tibco.xpd.bom.globaldata.api.AutoCaseIdProperties;
import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.modeler.custom.internal.propertysection.AbstractGeneralSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Auto case identifier settings section for {@link Property} has
 * AutoCaseIdentifier stereotype applied.
 * 
 * @author jarciuch
 * 
 */
public class AutoCaseIdSettingsSection extends AbstractGeneralSection {

    /** Limit on number of allowed chars in prefix or suffix. */
    private static int MAX_PREFIX_SUFFIX_LENGTH = 32;

    private Text minDigitsText;

    private Text prefixText;

    private Text suffixText;

    private static final AutoCaseIdProperties AUTO_CID_PROPS =
            new AutoCaseIdProperties();

    /**
     * @see com.tibco.xpd.bom.modeler.custom.internal.propertysection.AbstractGeneralSection#shouldDisplay(org.eclipse.emf.ecore.EObject)
     */
    @Override
    protected boolean shouldDisplay(EObject eo) {
        // Only display for AutoCaseIdentifiers.
        return eo instanceof Property
                && BOMGlobalDataUtils.isAutoCID((Property) eo);
    }

    /**
     * @see com.tibco.xpd.bom.modeler.custom.internal.propertysection.AbstractGeneralSection#doCreateControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     */
    @Override
    protected Control doCreateControls(Composite parent,
            XpdFormToolkit toolkit) {
        Composite root = (Composite) super.doCreateControls(parent, toolkit);

        Label minDigitsLabel = createLabel(root,
                toolkit,
                Messages.AutoCaseIdSettingsSection_minDigits_label);
        minDigitsLabel.setToolTipText(
                Messages.AutoCaseIdSettingsSection_minDigits_tooltip);
        minDigitsText = toolkit.createText(root, ""); //$NON-NLS-1$
        minDigitsText.setTextLimit(2);
        minDigitsText.setToolTipText(minDigitsLabel.getToolTipText());
        setLayoutData(minDigitsText);
        manageControlUpdateOnDeactivate(minDigitsText);
        // Add a Verify Listener that will check to ensure that only
        // numbers are added to the Text Box
        minDigitsText.addVerifyListener(new VerifyListener() {
            @Override
            public void verifyText(VerifyEvent e) {
                if (Character.isDigit(e.character) || e.keyCode == SWT.DEL
                        || e.keyCode == SWT.BS || e.character == 0) {
                    e.doit = true;
                } else {
                    e.doit = false;
                }
            }
        });

        Label prefixLabel = createLabel(root,
                toolkit,
                Messages.AutoCaseIdSettingsSection_prefix_label);
        prefixLabel.setToolTipText(
                Messages.AutoCaseIdSettingsSection_prefix_tooltip);
        prefixText = toolkit.createText(root, ""); //$NON-NLS-1$
        prefixText.setTextLimit(MAX_PREFIX_SUFFIX_LENGTH);
        prefixText.setToolTipText(prefixLabel.getToolTipText());
        setLayoutData(prefixText);
        manageControlUpdateOnDeactivate(prefixText);

        Label suffixLabel = createLabel(root,
                toolkit,
                Messages.AutoCaseIdSettingsSection_suffix_label);
        suffixLabel.setToolTipText(
                Messages.AutoCaseIdSettingsSection_suffix_tooltip);
        suffixText = toolkit.createText(root, ""); //$NON-NLS-1$
        suffixText.setTextLimit(MAX_PREFIX_SUFFIX_LENGTH);
        suffixText.setToolTipText(suffixLabel.getToolTipText());
        setLayoutData(suffixText);
        manageControlUpdateOnDeactivate(suffixText);

        return root;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     */
    @Override
    protected Command doGetCommand(Object obj) {
        final Property prop = getSemanticInput();
        if (prop == null) {
            return null;
        }
        if (obj == minDigitsText) {
            Integer newMinDigits = minDigitsText.getText().trim().isEmpty() ? 0
                    : Integer.valueOf(minDigitsText.getText().trim());
            Integer currentMinDigits = AUTO_CID_PROPS.getMinDigits(prop);
            // Only need to run the command if the value has changed
            if (!newMinDigits.equals(currentMinDigits)) {
                return new RecordingCommand(
                        (TransactionalEditingDomain) getEditingDomain()) {
                    @Override
                    protected void doExecute() {
                        AUTO_CID_PROPS.setMinDigits(prop,
                                minDigitsText.getText());
                    }
                };
            }
        } else if (obj == prefixText) {
            String newPrefix = prefixText.getText();
            String currentPrefix = AUTO_CID_PROPS.getPrefix(prop);
            if (!newPrefix.equals(currentPrefix)) {
                return new RecordingCommand(
                        (TransactionalEditingDomain) getEditingDomain()) {
                    @Override
                    protected void doExecute() {
                        AUTO_CID_PROPS.setPrefix(prop, newPrefix);
                    }
                };
            }
        } else if (obj == suffixText) {
            String newSuffix = suffixText.getText();
            String currentSuffix = AUTO_CID_PROPS.getPrefix(prop);
            if (!newSuffix.equals(currentSuffix)) {
                return new RecordingCommand(
                        (TransactionalEditingDomain) getEditingDomain()) {
                    @Override
                    protected void doExecute() {
                        AUTO_CID_PROPS.setSuffix(prop, newSuffix);
                    }
                };
            }
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     */
    @Override
    protected void doRefresh() {
        final Property prop = getSemanticInput();
        if (prop == null) {
            return;
        }
        if (minDigitsText != null && !minDigitsText.isDisposed()) {
            if (BOMGlobalDataUtils.isAutoCID(prop)) {
                updateText(minDigitsText,
                        AUTO_CID_PROPS.getMinDigits(prop).toString());
                updateText(prefixText, AUTO_CID_PROPS.getPrefix(prop));
                updateText(suffixText, AUTO_CID_PROPS.getSuffix(prop));
            }
        }
    }

    /**
     * Returns the UML property for the input or null.
     */
    private Property getSemanticInput() {
        EObject input = getInput();
        if ((input instanceof Property)) {
            return (Property) input;
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.bom.modeler.custom.internal.propertysection.AbstractGeneralSection#shouldRefresh(java.util.List)
     */
    @Override
    protected boolean shouldRefresh(List<Notification> notifications) {
        if (super.shouldRefresh(notifications)) {
            return true;
        }
        if (notifications != null) {
            for (Notification notification : notifications) {
                if (notification != null && !notification.isTouch()) {
                    Object notifier = notification.getNotifier();
                    if (notifier instanceof EObject) {
                        EObject eo = (EObject) notifier;
                        Element base = UMLUtil.getBaseElement(eo);
                        if (base instanceof Property) {
                            Property baseProperty = (Property) base;
                            if (BOMGlobalDataUtils.isAutoCID(baseProperty)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
