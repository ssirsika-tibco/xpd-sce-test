/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections.internal.controls;

import java.text.ParseException;
import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.xpd.om.resources.ui.properties.sections.internal.DateUtil;
import com.tibco.xpd.resources.ui.components.DialogCellWithClearEditor;
import com.tibco.xpd.resources.ui.components.calendar.DateTimePopup;
import com.tibco.xpd.resources.ui.components.calendar.DateType;

/**
 * Date/time cell editor that pops up a date/time picker in a table.
 * 
 * @author njpatel
 * 
 */
public class DateTimeCellEditor extends DialogCellWithClearEditor {

    private DateType type;

    private Date currentDate;

    /**
     * Cell editor to input date/time using a calendar pop-up.Using this
     * constructor will show a calendar pop-up for date selection.
     * 
     * @param parent
     *            parent control.
     */
    public DateTimeCellEditor(Composite parent) {
        this(parent, null);
    }

    /**
     * Cell editor to input date/time using a calendar pop-up.
     * 
     * @param parent
     * @param type
     */
    public DateTimeCellEditor(Composite parent, DateType type) {
        super(parent);
        setType(type);
    }

    /**
     * Set the type of pop-up required, date, time or date and time.
     * 
     * @param type
     *            type of pop-up. If <code>null</code> then DATE pop-up will be
     *            the default.
     */
    public void setType(DateType type) {
        this.type = type != null ? type : DateType.DATE;
    }

    /**
     * Get the currently set pop-up type.
     * 
     * @return
     */
    public DateType getType() {
        return type;
    }

    @Override
    protected void doSetValue(Object value) {
        if (value instanceof Date) {
            currentDate = (Date) value;
        } else {
            currentDate = null;
        }

        super.doSetValue(currentDate != null ? DateUtil
                .getLocalizedString(currentDate, getType()) : ""); //$NON-NLS-1$
    }

    @Override
    protected Object doGetValue() {
        return currentDate;
    }

    @Override
    protected Object openDialogBox(Control cellEditorWindow) {
        DateTimePopup popup = new DateTimePopup(cellEditorWindow, type);

        XMLGregorianCalendar calendar =
                DateTimePopup.createCalendar(currentDate != null ? currentDate
                        : new Date());
        popup.setCalendar(calendar);

        if (popup.open() == DateTimePopup.OK) {
            calendar = popup.getSelection();
            currentDate = DateTimePopup.getDate(calendar);
        }
        return currentDate;
    }

    /**
     * Get the ISO 8601 format string representing the given date.
     * 
     * @param date
     * @return ISO 8601 format string, or <code>null</code> if the date is
     *         <code>null</code>.
     */
    public String format(Date date) {
        if (date != null) {
            return DateUtil.format(date, type);
        }
        return null;
    }

    /**
     * Parse the given ISO 8601 format date string to create a {@link Date}.
     * 
     * @param text
     * @return {@link Date} or <code>null</code> if text is <code>null</code>.
     * @throws ParseException
     */
    public Date parse(String text) throws ParseException {
        if (text != null) {
            return DateUtil.parse(text, type);
        }
        return null;
    }
}
