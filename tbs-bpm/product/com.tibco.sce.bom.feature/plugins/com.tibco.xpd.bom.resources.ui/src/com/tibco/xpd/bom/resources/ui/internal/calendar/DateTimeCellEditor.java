/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.resources.ui.internal.calendar;

import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.xpd.bom.resources.ui.Activator;
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
    private XMLGregorianCalendar currentDate;

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
        if (value instanceof XMLGregorianCalendar) {
            currentDate = (XMLGregorianCalendar) value;
        } else {
            currentDate = null;
        }

        super.doSetValue(currentDate != null ? BOMDateUtil.getLocalizedString(
                currentDate, getType()) : ""); //$NON-NLS-1$
    }

    @Override
    protected Object doGetValue() {
        return currentDate;
    }

    @Override
    protected Object openDialogBox(Control cellEditorWindow) {
        DateTimePopup popup = new DateTimePopup(cellEditorWindow, type);
        XMLGregorianCalendar dt = currentDate;
        if (dt == null) {
            try {
                dt = DatatypeFactory.newInstance().newXMLGregorianCalendar(
                        (GregorianCalendar) GregorianCalendar.getInstance());
            } catch (DatatypeConfigurationException e) {
                Activator.getDefault().getLogger().error(e);
            }
        }
        popup.setCalendar(dt);

        if (popup.open() == DateTimePopup.OK) {
            dt = popup.getSelection();
            currentDate = dt;
        } else {
            dt = null;
        }

        return dt;
    }
}
