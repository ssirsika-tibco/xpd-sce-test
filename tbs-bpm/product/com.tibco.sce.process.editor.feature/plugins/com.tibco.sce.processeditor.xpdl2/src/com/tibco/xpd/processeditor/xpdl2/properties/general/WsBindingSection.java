/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.general;

import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.WsBinding;

/**
 * Base class for all web service's binding sections.
 * 
 * @author Jan Arciuchiewicz
 */
public abstract class WsBindingSection extends
        AbstractFilteredTransactionalSection {

    private Text nameText;

    protected static final GridDataFactory indentedGridData = GridDataFactory
            .swtDefaults().indent(5, 0);

    /**
     * @param eClass
     */
    public WsBindingSection(EClass eClass) {
        super(eClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        GridLayoutFactory.fillDefaults().numColumns(2).applyTo(parent);
        GridDataFactory
                .swtDefaults()
                .span(2, 1)
                .applyTo(toolkit.createLabel(parent,
                        Messages.WsBindingSection_BindingDetails_label));
        createNameControl(parent, toolkit);
        return parent;
    }

    protected void createNameControl(Composite parent, XpdFormToolkit toolkit) {
        indentedGridData.applyTo(toolkit.createLabel(parent,
                Messages.WsBindingSection_BindingName_label));
        nameText = toolkit.createText(parent, ""); //$NON-NLS-1$
        GridDataFactory.fillDefaults().grab(true, false).applyTo(nameText);
        manageControlUpdateOnDeactivate(nameText);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Command doGetCommand(Object obj) {
        final WsBinding binding = (WsBinding) getInput();
        if (obj == nameText && nameText != null) {
            if (binding != null) {
                Text tc = (Text) obj;
                final String text = tc.getText();
                if (!text.equals(binding.getName())) {
                    return new RecordingCommand(
                            (TransactionalEditingDomain) getEditingDomain()) {
                        @Override
                        protected void doExecute() {
                            binding.setName(text);
                        }
                    };
                }
            }
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractTransactionalSection#isInputRemoved(java.util.List)
     * 
     * @param notifications
     * @return
     */
    @Override
    protected boolean isInputRemoved(List<Notification> notifications) {
        /*
         * XPD-4454: We were experiencing problems when the transport was
         * deleted because the input to this binding section isn't necessarily
         * the main input for property tab. BUT
         * AbstractTransactionalSection.isInputRemoved() only checks for a
         * remove notification on the input that is set on the section.
         * 
         * If false is not returned here then AbstractTransactionalSection will
         * wipe out the selection for the entire property tab.
         */
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doRefresh() {
        refreshNemeControl();
    }

    protected void refreshNemeControl() {
        WsBinding binding = (WsBinding) getInput();
        if (binding != null && nameText != null) {
            nameText.setText(nullSafe(binding.getName()));
        }
    }

    protected String nullSafe(String s) {
        return s != null ? s : ""; //$NON-NLS-1$
    }

}
