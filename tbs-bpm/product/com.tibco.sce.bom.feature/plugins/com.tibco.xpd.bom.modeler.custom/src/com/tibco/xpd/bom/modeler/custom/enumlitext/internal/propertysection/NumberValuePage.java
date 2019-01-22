/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.enumlitext.internal.propertysection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.uml2.uml.EnumerationLiteral;

import com.tibco.xpd.bom.modeler.custom.enumlitext.DomainValue;
import com.tibco.xpd.bom.modeler.custom.enumlitext.RangeValue;
import com.tibco.xpd.bom.modeler.custom.enumlitext.SingleValue;
import com.tibco.xpd.bom.modeler.custom.enumlitext.internal.commands.SetRangeInclusiveCommand;
import com.tibco.xpd.bom.modeler.custom.enumlitext.internal.commands.SetRangeValueCommand;
import com.tibco.xpd.bom.modeler.custom.enumlitext.internal.commands.SetRangeValueCommand.ValueType;
import com.tibco.xpd.bom.modeler.custom.enumlitext.util.EnumLitValueUtil;
import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Enumeration Literal section page to set a number value.
 * 
 * @author njpatel
 */
/* public */class NumberValuePage extends ValuePage {

    private Button singleBtn;

    private Text singleValueTxt;

    private Button rangeBtn;

    private RangeSection rangeValue;

    private Label singleValueLbl;

    public NumberValuePage(XpdFormToolkit toolkit, EditingDomain ed) {
        super(toolkit, ed);
    }

    @Override
    protected Composite doCreateControl(Composite parent) {
        Composite root = getToolkit().createComposite(parent);
        GridLayout layout = new GridLayout(3, false);
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        root.setLayout(layout);

        /*
         * Single value entry
         */
        singleBtn =
                getToolkit().createButton(root,
                        Messages.NumberValuePage_single_button,
                        SWT.RADIO);
        GridData data = new GridData(SWT.FILL, SWT.CENTER, false, true);
        data.widthHint = 85;
        singleBtn.setLayoutData(data);
        singleBtn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                enableRangeValue(false);
                enableSingleValue(true);
            }
        });

        singleValueLbl =
                createLabel(root, Messages.NumberValuePage_value_label);

        singleValueTxt = createNumberText(root, ""); //$NON-NLS-1$
        manageControl(singleValueTxt);

        rangeBtn =
                getToolkit().createButton(root,
                        Messages.NumberValuePage_range_label,
                        SWT.RADIO);
        data = new GridData();
        data.widthHint = 85;
        rangeBtn.setLayoutData(data);
        rangeBtn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                enableSingleValue(false);
                enableRangeValue(true);
            }
        });

        rangeValue = new RangeSection(root, SWT.NONE);
        data = new GridData(SWT.FILL, SWT.FILL, true, true);
        data.horizontalSpan = 2;
        rangeValue.setLayoutData(data);

        return root;
    }

    @Override
    protected void doRefresh(EnumerationLiteral input) {

        DomainValue value = EnumLitValueUtil.getDomainValue(input);

        if (value instanceof SingleValue) {
            enableSingleValue(true);
            enableRangeValue(false);

            singleValueTxt.setText(value.getValue());
            rangeValue.clear();

        } else if (value instanceof RangeValue) {
            enableSingleValue(false);
            enableRangeValue(true);
            rangeValue.refresh((RangeValue) value);
            singleValueTxt.setText(""); //$NON-NLS-1$
        }

    }

    @Override
    protected Command doGetCommand(Widget widget, EnumerationLiteral input) {
        Command cmd = null;

        if (widget == singleValueTxt) {
            String newValue = singleValueTxt.getText().trim();

            // Check if the value has changed as if it hasn't then no point
            // running command
            boolean hasChanged = true;
            DomainValue domainValue = EnumLitValueUtil.getDomainValue(input);
            if (domainValue instanceof SingleValue) {
                hasChanged = !newValue.equals(domainValue.getValue());
            }

            if (hasChanged) {
                cmd =
                        EnumLitValueUtil
                                .getSetSingleValueCommand((TransactionalEditingDomain) getEditingDomain(),
                                        input,
                                        newValue);
            }
        } else {
            cmd = rangeValue.getCommand(widget, input);
        }

        return cmd;
    }

    /**
     * Enable the single value controls.
     * 
     * @param enable
     */
    private void enableSingleValue(boolean enable) {
        singleValueLbl.setEnabled(enable);
        singleValueTxt.setEnabled(enable);
        singleBtn.setSelection(enable);
    }

    /**
     * Enable the range value controls.
     * 
     * @param enable
     */
    private void enableRangeValue(boolean enable) {
        rangeBtn.setSelection(enable);
        rangeValue.setEnabled(enable);
    }

    @Override
    public void setFocus() {
        getControl().setFocus();
    }

    /**
     * Create a label with a fixed width hint.
     * 
     * @param parent
     * @param text
     * @return
     */
    private Label createLabel(Composite parent, String text) {
        Label lbl = getToolkit().createLabel(parent, text);
        GridData data = new GridData();
        data.widthHint = 75;
        lbl.setLayoutData(data);

        return lbl;
    }

    /**
     * Create a text controls with a fixed width hint.
     * 
     * @param parent
     * @param text
     * @return
     */
    private Text createNumberText(Composite parent, String text) {
        Text txt = getToolkit().createText(parent, text);
        GridData data = new GridData();
        data.widthHint = 255;
        txt.setLayoutData(data);

        return txt;
    }

    /**
     * Range value section control.
     */
    private class RangeSection extends Composite {

        private Label lowerLbl;

        private Text lowerValue;

        private Button lowerInclusiveBtn;

        private Label upperLbl;

        private Text upperValue;

        private Button upperInclusiveBtn;

        public RangeSection(Composite parent, int style) {
            super(parent, style);
            getToolkit().adapt(this);
            createControl();
        }

        /**
         * Clear all controls in this section.
         */
        public void clear() {
            lowerValue.setText(""); //$NON-NLS-1$
            upperValue.setText("");//$NON-NLS-1$
            lowerInclusiveBtn.setSelection(false);
            upperInclusiveBtn.setSelection(false);
        }

        public Command getCommand(Widget widget, EnumerationLiteral input) {
            Command cmd = null;
            if (widget == lowerInclusiveBtn) {
                cmd =
                        new SetRangeInclusiveCommand(
                                (TransactionalEditingDomain) getEditingDomain(),
                                input, lowerInclusiveBtn.getSelection(),
                                ValueType.LOWER);
            } else if (widget == upperInclusiveBtn) {
                cmd =
                        new SetRangeInclusiveCommand(
                                (TransactionalEditingDomain) getEditingDomain(),
                                input, upperInclusiveBtn.getSelection(),
                                ValueType.UPPER);
            } else if (widget == lowerValue || widget == upperValue) {
                DomainValue domainValue =
                        EnumLitValueUtil.getDomainValue(input);

                if (widget == lowerValue) {
                    String newValue = lowerValue.getText().trim();
                    // Only provide command if value has changed
                    boolean hasChanged = true;
                    if (domainValue instanceof RangeValue) {
                        hasChanged =
                                !newValue.equals(((RangeValue) domainValue)
                                        .getLower());
                    }

                    if (hasChanged) {
                        cmd =
                                new SetRangeValueCommand(
                                        (TransactionalEditingDomain) getEditingDomain(),
                                        input, newValue, ValueType.LOWER);
                    }
                } else if (widget == upperValue) {
                    String newValue = upperValue.getText().trim();

                    // Only provide command if value has changed
                    boolean hasChanged = true;
                    if (domainValue instanceof RangeValue) {
                        hasChanged =
                                !newValue.equals(((RangeValue) domainValue)
                                        .getUpper());
                    }

                    if (hasChanged) {
                        cmd =
                                new SetRangeValueCommand(
                                        (TransactionalEditingDomain) getEditingDomain(),
                                        input, newValue, ValueType.UPPER);
                    }
                }
            }
            return cmd;
        }

        /**
         * Refresh this range control with the given value.
         * 
         * @param value
         */
        public void refresh(RangeValue value) {
            lowerValue
                    .setText(value.getLower() != null ? value.getLower() : ""); //$NON-NLS-1$
            upperValue
                    .setText(value.getUpper() != null ? value.getUpper() : ""); //$NON-NLS-1$

            lowerInclusiveBtn.setSelection(value.isLowerInclusive());
            upperInclusiveBtn.setSelection(value.isUpperInclusive());
        }

        @Override
        public void setEnabled(boolean enabled) {
            lowerLbl.setEnabled(enabled);
            lowerValue.setEnabled(enabled);
            lowerInclusiveBtn.setEnabled(enabled);
            upperLbl.setEnabled(enabled);
            upperValue.setEnabled(enabled);
            upperInclusiveBtn.setEnabled(enabled);
            super.setEnabled(enabled);
        }

        private void createControl() {
            GridLayout layout = new GridLayout(3, false);
            layout.marginWidth = 0;
            layout.marginHeight = 0;
            setLayout(layout);

            lowerLbl = createLabel(this, Messages.NumberValuePage_lower_label);
            lowerValue = createNumberText(this, ""); //$NON-NLS-1$
            manageControl(lowerValue);

            lowerInclusiveBtn =
                    getToolkit().createButton(this,
                            Messages.NumberValuePage_inclusive_button,
                            SWT.CHECK);
            manageControl(lowerInclusiveBtn);

            upperLbl = createLabel(this, Messages.NumberValuePage_upper_label);
            upperValue = createNumberText(this, ""); //$NON-NLS-1$
            manageControl(upperValue);

            upperInclusiveBtn =
                    getToolkit().createButton(this,
                            Messages.NumberValuePage_inclusive_button,
                            SWT.CHECK);
            manageControl(upperInclusiveBtn);
        }

    }

}
