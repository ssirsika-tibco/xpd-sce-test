/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.enumlitext.internal.propertysection;

import java.util.Date;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.uml2.uml.EnumerationLiteral;

import com.tibco.xpd.bom.modeler.custom.Activator;
import com.tibco.xpd.bom.modeler.custom.enumlitext.DomainValue;
import com.tibco.xpd.bom.modeler.custom.enumlitext.RangeValue;
import com.tibco.xpd.bom.modeler.custom.enumlitext.SingleValue;
import com.tibco.xpd.bom.modeler.custom.enumlitext.internal.commands.SetRangeInclusiveCommand;
import com.tibco.xpd.bom.modeler.custom.enumlitext.internal.commands.SetRangeValueCommand;
import com.tibco.xpd.bom.modeler.custom.enumlitext.internal.commands.SetRangeValueCommand.ValueType;
import com.tibco.xpd.bom.modeler.custom.enumlitext.internal.commands.SetSingleValueCommand;
import com.tibco.xpd.bom.modeler.custom.enumlitext.util.EnumLitValueUtil;
import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.resources.ui.internal.calendar.BOMDateUtil;
import com.tibco.xpd.resources.ui.components.calendar.DateTimePopup;
import com.tibco.xpd.resources.ui.components.calendar.DateType;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.properties.components.AbstractPickerControl;

/**
 * Enumeration Literal section page to set a DateTime value.
 * 
 * @author njpatel
 */
@SuppressWarnings("restriction")
/* public */class DateTimeValuePage extends ValuePage {

    private Button singleBtn;

    private SingleValueCtrl singleValueCtrl;

    private Button rangeBtn;

    private RangeSection rangeValue;

    private Label singleDateLbl;

    private DateType dateType;

    public DateTimeValuePage(XpdFormToolkit toolkit, EditingDomain ed) {
        super(toolkit, ed);
    }

    public void setDateType(DateType type) {
        dateType = type;

        /* Update the single value label to show an appropriate text */
        if (singleDateLbl != null && !singleDateLbl.isDisposed()) {

            if (dateType == DateType.TIME) {

                singleDateLbl.setText(Messages.DateTimeValuePage_time_label);
            } else {

                singleDateLbl.setText(Messages.DateTimeValuePage_date_label);
            }
            /*
             * reset the TextMinimumWidth of picker controls according to date
             * type selected
             */
            int ourMinWidth = 0;

            if (DateType.DATE == dateType) {

                ourMinWidth =
                        singleValueCtrl.calculateMinimumTextWidth("11/11/1111"); //$NON-NLS-1$
            } else if (DateType.TIME == dateType) {

                ourMinWidth =
                        singleValueCtrl.calculateMinimumTextWidth("11:11"); //$NON-NLS-1$
            } else if (DateType.DATETIME == dateType) {

                ourMinWidth =
                        singleValueCtrl
                                .calculateMinimumTextWidth("11/11/1111 11:11"); //$NON-NLS-1$
            } else if (DateType.DATETIMETZ == dateType) {

                ourMinWidth =
                        singleValueCtrl
                                .calculateMinimumTextWidth("11/11/1111 11:11 (XXXXXXXXXX)"); //$NON-NLS-1$
            }

            /*
             * if the minimum width calculated here is greater than the minimum
             * text width in the base class only then we re-set the value
             */
            if (ourMinWidth > singleValueCtrl.getMinimumTextWidth()) {

                singleValueCtrl.setMinimumTextWidth(ourMinWidth);
            }
            if (ourMinWidth > rangeValue.lowerValue.getMinimumTextWidth()) {

                rangeValue.lowerValue.setMinimumTextWidth(ourMinWidth);
            }
            if (ourMinWidth > rangeValue.upperValue.getMinimumTextWidth()) {

                rangeValue.upperValue.setMinimumTextWidth(ourMinWidth);
            }

        }

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
                        Messages.DateTimeValuePage_single_button,
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

        manageControl(singleBtn);

        singleDateLbl =
                createLabel(root, Messages.DateTimeValuePage_date_label);

        singleValueCtrl =
                new SingleValueCtrl(root, SWT.NONE, getToolkit(),
                        getEditingDomain());

        /*
         * Range value entry
         */
        rangeBtn =
                getToolkit().createButton(root,
                        Messages.DateTimeValuePage_range_label,
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

        manageControl(rangeBtn);

        rangeValue = new RangeSection(root, SWT.NONE);
        data = new GridData(SWT.FILL, SWT.FILL, true, true);
        data.horizontalSpan = 2;
        rangeValue.setLayoutData(data);

        return root;
    }

    @Override
    protected Command doGetCommand(final Widget widget,
            final EnumerationLiteral input) {
        Command cmd = null;

        /*
         * Single, lower and upper range controls will set values using their
         * own commands. If this widget is not the single or range button then
         * it may be on of the buttons in the range control.
         */
        if (widget != singleBtn && widget != rangeBtn) {
            cmd = rangeValue.getCommand(widget, input);
        }

        return cmd;
    }

    @Override
    protected void doRefresh(EnumerationLiteral input) {
        DomainValue domainValue = EnumLitValueUtil.getDomainValue(input);
        if (domainValue instanceof SingleValue) {
            enableSingleValue(true);
            enableRangeValue(false);

            String value = domainValue.getValue();
            try {
                singleValueCtrl.setValue(getDate(value));
            } catch (IllegalArgumentException e) {
                /*
                 * Invalid date value set so just show an empty value. No point
                 * recording this error as there should be an error marker on
                 * the literal.
                 */
                singleValueCtrl.setValue(null);
            }
            rangeValue.clear();
        } else if (domainValue instanceof RangeValue) {
            enableSingleValue(false);
            enableRangeValue(true);

            rangeValue.refresh((RangeValue) domainValue);
            singleValueCtrl.setValue(null);
        } else {
            enableSingleValue(true);
            enableRangeValue(false);
            // Clear all controls
            rangeValue.clear();
            singleValueCtrl.setValue(null);
        }
    }

    /**
     * Parse the given ISO 8601 date/time string into a calendar.
     * 
     * @param isoDateStr
     * @return
     * @throws DatatypeConfigurationException
     */
    private XMLGregorianCalendar getDate(String isoDateStr) {
        XMLGregorianCalendar calendar = null;

        if (isoDateStr != null && !isoDateStr.isEmpty()) {
            try {
                calendar = BOMDateUtil.parse(isoDateStr, dateType);
            } catch (DatatypeConfigurationException e) {
                Activator.getDefault().getLogger()
                        .error(e, "Invalid date string: '%s'" + isoDateStr); //$NON-NLS-1$ 
            }
        }

        return calendar;
    }

    /**
     * Enable the single value controls.
     * 
     * @param enable
     */
    private void enableSingleValue(boolean enable) {
        if (singleDateLbl != null && !singleDateLbl.isDisposed()) {
            singleBtn.setSelection(enable);
            singleDateLbl.setEnabled(enable);
            singleValueCtrl.setEnabled(enable);
        }
    }

    /**
     * Enable the range value controls.
     * 
     * @param enable
     */
    private void enableRangeValue(boolean enable) {
        if (rangeValue != null && !rangeValue.isDisposed()) {
            rangeBtn.setSelection(enable);
            rangeValue.setEnabled(enable);
        }
    }

    @Override
    public void setFocus() {
        getControl().setFocus();
    }

    private Label createLabel(Composite parent, String text) {
        Label lbl = getToolkit().createLabel(parent, text);
        GridData data = new GridData();
        data.widthHint = 75;
        lbl.setLayoutData(data);

        return lbl;
    }

    /**
     * Section for the range controls.
     */
    private class RangeSection extends Composite {

        private Label lowerLbl;

        private RangeLowerCtrl lowerValue;

        private Button lowerInclusiveBtn;

        private Label upperLbl;

        private RangeUpperCtrl upperValue;

        private Button upperInclusiveBtn;

        public RangeSection(Composite parent, int style) {
            super(parent, style);
            getToolkit().adapt(this);
            createControl();
        }

        /**
         * Update the range controls with the given value.
         * 
         * @param range
         */
        public void refresh(RangeValue range) {
            if (lowerValue != null && !lowerValue.isDisposed()) {
                lowerValue.setValue(getDate(range.getLower()));
                upperValue.setValue(getDate(range.getUpper()));
                lowerInclusiveBtn.setSelection(range.isLowerInclusive());
                upperInclusiveBtn.setSelection(range.isUpperInclusive());
            }
        }

        /**
         * Clear all the controls
         */
        public void clear() {
            if (lowerValue != null && !lowerValue.isDisposed()) {
                lowerValue.setValue(null);
                upperValue.setValue(null);
                lowerInclusiveBtn.setSelection(false);
                upperInclusiveBtn.setSelection(false);
            }
        }

        /**
         * Get the command as a result of the selection of the given widget.
         * 
         * @param widget
         * @param input
         * @return
         */
        public Command getCommand(Widget widget, EnumerationLiteral input) {
            Command cmd = null;

            if (widget == lowerInclusiveBtn) {
                cmd =
                        new SetRangeInclusiveCommand(
                                (TransactionalEditingDomain) getEditingDomain(),
                                input, lowerInclusiveBtn.getSelection(),
                                ValueType.LOWER);
            } else {
                cmd =
                        new SetRangeInclusiveCommand(
                                (TransactionalEditingDomain) getEditingDomain(),
                                input, upperInclusiveBtn.getSelection(),
                                ValueType.UPPER);
            }
            return cmd;
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

            lowerLbl =
                    createLabel(this, Messages.DateTimeValuePage_lower_label);
            lowerValue =
                    new RangeLowerCtrl(this, SWT.NONE, getToolkit(),
                            getEditingDomain());

            lowerInclusiveBtn =
                    getToolkit().createButton(this,
                            Messages.DateTimeValuePage_inclusive_button,
                            SWT.CHECK);
            manageControl(lowerInclusiveBtn);

            upperLbl =
                    createLabel(this, Messages.DateTimeValuePage_upper_label);
            upperValue =
                    new RangeUpperCtrl(this, SWT.NONE, getToolkit(),
                            getEditingDomain());

            upperInclusiveBtn =
                    getToolkit().createButton(this,
                            Messages.DateTimeValuePage_inclusive_button,
                            SWT.CHECK);
            manageControl(upperInclusiveBtn);
        }

    }

    /**
     * Date/Time picker.
     */
    private abstract class DateTimePicker extends
            AbstractPickerControl<XMLGregorianCalendar> {

        public DateTimePicker(Composite parent, int style,
                XpdFormToolkit toolkit, EditingDomain editingDomain) {
            super(parent, style, toolkit, editingDomain);
            setErrorText(getClearText());
            setLabelProvider(new LabelProvider() {
                @Override
                public Image getImage(Object element) {
                    return null;
                }

                @Override
                public String getText(Object element) {
                    if (element instanceof XMLGregorianCalendar) {
                        return BOMDateUtil
                                .getLocalizedString((XMLGregorianCalendar) element,
                                        dateType);
                    }
                    return super.getText(element);
                }
            });
            setHyperlinkActive(false);
        }

        /**
         * Get the input EnumerationLiteral.
         * 
         * @return
         */
        protected EnumerationLiteral getLiteral() {
            return (EnumerationLiteral) (getInput() instanceof EnumerationLiteral ? getInput()
                    : null);
        }

        @Override
        protected XMLGregorianCalendar doBrowse(Control control) {
            XMLGregorianCalendar dt = null;
            DateTimePopup popup = new DateTimePopup(control, dateType);

            XMLGregorianCalendar value = getValue();
            if (value == null) {
                value = DateTimePopup.createCalendar(new Date());
            }
            popup.setCalendar(value);
            if (popup.open() == DateTimePopup.OK) {
                dt = popup.getSelection();
            }
            return dt;
        }
    }

    /**
     * Date Time control for the single value.
     */
    private class SingleValueCtrl extends DateTimePicker {

        public SingleValueCtrl(Composite parent, int style,
                XpdFormToolkit toolkit, EditingDomain editingDomain) {
            super(parent, style, toolkit, editingDomain);

        }

        @Override
        protected Command getSetValueCommand(EditingDomain editingDomain,
                final XMLGregorianCalendar value) {
            final EnumerationLiteral literal = getLiteral();
            if (literal != null) {
                String strValue =
                        value != null ? BOMDateUtil.getISO8601String(value,
                                dateType) : ""; //$NON-NLS-1$

                // Check if the value has changed - if not then no point running
                // command
                boolean hasChanged = true;
                DomainValue domainValue =
                        EnumLitValueUtil.getDomainValue(literal);
                if (domainValue instanceof SingleValue) {
                    String currValue = domainValue.getValue();
                    hasChanged = !strValue.equals(currValue);
                }

                if (hasChanged) {
                    return new SetSingleValueCommand(
                            (TransactionalEditingDomain) editingDomain,
                            literal, strValue);
                }
            }
            return null;
        }

        @Override
        protected Command getClearValueCommand(EditingDomain editingDomain,
                XMLGregorianCalendar value) {
            final EnumerationLiteral literal = getLiteral();
            if (literal != null) {
                return new SetSingleValueCommand(
                        (TransactionalEditingDomain) editingDomain, literal, ""); //$NON-NLS-1$
            }
            return null;
        }
    }

    /**
     * Date Time picker for the Range lower value.
     */
    private class RangeLowerCtrl extends DateTimePicker {

        public RangeLowerCtrl(Composite parent, int style,
                XpdFormToolkit toolkit, EditingDomain editingDomain) {
            super(parent, style, toolkit, editingDomain);
        }

        @Override
        protected Command getSetValueCommand(EditingDomain editingDomain,
                final XMLGregorianCalendar value) {
            EnumerationLiteral literal = getLiteral();
            if (literal != null) {
                String newValue =
                        value != null ? BOMDateUtil.getISO8601String(value,
                                dateType) : ""; //$NON-NLS-1$

                // Check if the value has changed as if it hasn't then no point
                // running command
                boolean hasChanged = true;
                DomainValue domainValue =
                        EnumLitValueUtil.getDomainValue(literal);
                if (domainValue instanceof RangeValue) {
                    String currValue = ((RangeValue) domainValue).getLower();
                    hasChanged = !newValue.equals(currValue);
                }

                if (hasChanged) {
                    return new SetRangeValueCommand(
                            (TransactionalEditingDomain) editingDomain,
                            literal, newValue, ValueType.LOWER);
                }
            }
            return null;
        }

        @Override
        protected Command getClearValueCommand(EditingDomain editingDomain,
                XMLGregorianCalendar value) {
            final EnumerationLiteral literal = getLiteral();
            if (literal != null) {
                return new SetRangeValueCommand(
                        (TransactionalEditingDomain) editingDomain, literal,
                        "", ValueType.LOWER); //$NON-NLS-1$
            }
            return null;
        }

    }

    /**
     * Date Time picker for the Range upper value.
     */
    private class RangeUpperCtrl extends DateTimePicker {

        public RangeUpperCtrl(Composite parent, int style,
                XpdFormToolkit toolkit, EditingDomain editingDomain) {
            super(parent, style, toolkit, editingDomain);
        }

        @Override
        protected Command getSetValueCommand(EditingDomain editingDomain,
                final XMLGregorianCalendar value) {
            final EnumerationLiteral literal = getLiteral();
            if (literal != null) {
                String newValue =
                        value != null ? BOMDateUtil.getISO8601String(value,
                                dateType) : ""; //$NON-NLS-1$

                // Check if the value has changed as if it hasn't then no point
                // running command
                boolean hasChanged = true;
                DomainValue domainValue =
                        EnumLitValueUtil.getDomainValue(literal);
                if (domainValue instanceof RangeValue) {
                    String currValue = ((RangeValue) domainValue).getUpper();
                    hasChanged = !newValue.equals(currValue);
                }

                if (hasChanged) {
                    return new SetRangeValueCommand(
                            (TransactionalEditingDomain) editingDomain,
                            literal, newValue, ValueType.UPPER);
                }
            }
            return null;
        }

        @Override
        protected Command getClearValueCommand(EditingDomain editingDomain,
                XMLGregorianCalendar value) {
            final EnumerationLiteral literal = getLiteral();
            if (literal != null) {
                return new SetRangeValueCommand(
                        (TransactionalEditingDomain) editingDomain, literal,
                        "", ValueType.UPPER); //$NON-NLS-1$
            }
            return null;
        }

    }

}
