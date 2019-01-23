/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections.internal.controls;

import java.text.ParseException;
import java.util.Date;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.DateUtil;
import com.tibco.xpd.resources.ui.components.calendar.DateTimePopup;
import com.tibco.xpd.resources.ui.components.calendar.DateType;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.properties.components.AbstractPickerControl;

/**
 * A date selection {@link Composite} that will allow user to select date, time
 * or both from a pop-up. A handler will have to be provided that will be
 * notified of change to the date/time. The {@link DateTimePopupType type} will
 * determine what pop-up is required.
 * 
 * @author njpatel
 * 
 */
public class DateControl extends AbstractPickerControl<Date> {

    /**
     * Handler for the selection in the {@link DateControl date controller}.
     * 
     * @author njpatel
     * 
     */
    public interface DateControlHandler {
        /**
         * Get the {@link Command} to set the date selected in the control.
         * 
         * @param ed
         *            editing domain
         * @param date
         *            selected date
         * @return <code>Command</code> to set the date.
         */
        Command getSetDateCommand(EditingDomain ed, Date date);

        /**
         * Get the {@link Command} to clear the date.
         * 
         * @param ed
         *            editing domain
         * @param date
         *            date currently set
         * @return <code>Command</code> to clear the date.
         */
        Command getClearDateCommand(EditingDomain ed, Date date);
    }

    /**
     * Type of control required.
     */
    private DateType type = DateType.DATE;

    private final DateControlHandler handler;

    /**
     * Date/time control that shows the selected date/time and two buttons - a
     * browse for the calendar pop-up and a Clear button. This constructor
     * assumes a Date selection is required.
     * 
     * @param parent
     *            parent container
     * @param style
     *            {@link SWT} style for the <code>Composite</code>
     * @param toolkit
     *            form toolkit
     * @param ed
     *            editing domain
     */
    public DateControl(Composite parent, int style, XpdFormToolkit toolkit,
            EditingDomain ed, DateControlHandler handler) {

        this(parent, style, toolkit, ed, DateType.DATE, handler);
        setHyperlinkActive(false);
    }

    /**
     * Date/time control that shows the selected date/time and two buttons - a
     * browse for the calendar pop-up and a Clear button.
     * 
     * @param parent
     *            parent container
     * @param style
     *            {@link SWT} style for the <code>Composite</code>
     * @param toolkit
     *            form toolkit
     * @param ed
     *            editing domain
     * @param type
     *            {@link DateTimePopupType type} of control required
     * @param handler
     */
    public DateControl(Composite parent, int style, XpdFormToolkit toolkit,
            EditingDomain ed, DateType type, DateControlHandler handler) {
        super(parent, style, toolkit, ed);
        Assert.isNotNull(handler, "No handler set for the date control."); //$NON-NLS-1$
        this.handler = handler;
        setType(type);

        setLabelProvider(new LabelProvider() {
            @Override
            public String getText(Object element) {
                if (element instanceof Date) {
                    return DateUtil.getLocalizedString((Date) element,
                            getType());
                }
                return getClearText();
            }
        });

        setBrowseTooltip(Messages.DateControl_selectDate_button_tooltip);
        setClearTooltip(Messages.DateControl_clear_button_tooltip);
    }

    /**
     * Set type of input required.
     * 
     * @param type
     *            {@link DateTimePopupType}
     */
    public void setType(DateType type) {
        this.type = type != null ? type : DateType.DATE;
    }

    /**
     * Get the date format
     * 
     * @return
     */
    private DateType getType() {
        return type;
    }

    /**
     * Set the date from the date string.
     * 
     * @param dateString
     *            ISO 8601 format date string
     */
    public void setValue(String dateString) {
        if (dateString != null && dateString.length() > 0) {
            try {
                setValue(DateUtil.parse(dateString, getType()));
            } catch (ParseException e) {
                // Invalid value
                setErrorText(Messages.DateControl_invalidValue_label);
            }
        } else {
            setValue((Date) null);
        }
    }

    @Override
    protected Date doBrowse(Control control) {
        Date dt = null;
        DateTimePopup popup = new DateTimePopup(control, type);
        popup.setCalendar(DateTimePopup.createCalendar(getValue()));
        if (popup.open() == DateTimePopup.OK) {
            dt = DateTimePopup.getDate(popup.getSelection());
        }
        return dt;
    }

    @Override
    protected Command getClearValueCommand(EditingDomain editingDomain,
            Date value) {
        return handler.getClearDateCommand(editingDomain, value);
    }

    @Override
    protected Command getSetValueCommand(EditingDomain editingDomain, Date value) {
        return handler.getSetDateCommand(editingDomain, value);
    }
}
