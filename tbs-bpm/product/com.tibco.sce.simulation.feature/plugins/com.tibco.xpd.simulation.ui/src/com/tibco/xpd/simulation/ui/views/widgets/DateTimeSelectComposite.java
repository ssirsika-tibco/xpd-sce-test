/**
 * 
 */
package com.tibco.xpd.simulation.ui.views.widgets;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.gface.date.DateSelectedEvent;
import com.gface.date.DateSelectionListener;

public class DateTimeSelectComposite extends Composite {

    private DatePickerPopup popup;

    private List dateSelectionListener;

    private Date selectedDate;

    private TimeTextListener timeTextEventListener;

    private Text timeTextField;

    private Button popupButton;

    /**
     * TODO comment me!
     * 
     * @param parent
     * @param style
     * @param builder
     *            TODO
     */
    public DateTimeSelectComposite(Composite parent,
            final DateFormat dateFormat, Date initialDate) {
        super(parent, SWT.BORDER);
        GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
        parent.setLayoutData(layoutData);
        GridLayout layout = new GridLayout();
        layout.numColumns = 3;
        this.setLayout(layout);

        dateSelectionListener = new ArrayList();

        String timePattern = "HH:mm:ss"; // be careful with that !!!!!!!!! //$NON-NLS-1$
                                         // internals in listener
                                         // are looking for index of ':'
                                         // character
        DateFormat timeFormat = new SimpleDateFormat(timePattern);
        timeFormat.setLenient(false);

        final Label dateLabel = new Label(this, SWT.NONE);
        selectedDate = initialDate;
        String blankDate = dateFormat.format(initialDate);
        dateLabel.setText(blankDate);

        popup = new DatePickerPopup();
        popupButton = popup.createButton(this);
        popup.addDateSelectionListener(new DateSelectionListener() {

            @Override
            public void dateSelected(DateSelectedEvent e) {
                selectedDate = e.date;
                String formattedDate = dateFormat.format(selectedDate);
                dateLabel.setText(formattedDate);
                notifyListeners();
            }
        });

        timeTextField = new Text(this, SWT.BORDER);
        String blankTime = timeFormat.format(initialDate);
        timeTextField.setText(blankTime);
        timeTextEventListener =
                new TimeTextListener(timeTextField, timeFormat, blankTime, this);
        timeTextField.addListener(SWT.Verify, timeTextEventListener);
    }

    void notifyListeners() {
        Date selectedTime = timeTextEventListener.getSelectedTime();
        if (selectedTime == null) {
            selectedTime = GregorianCalendar.getInstance().getTime();
        }

        int year = selectedDate.getYear();
        int month = selectedDate.getMonth();
        int day = selectedDate.getDate();
        int hours = selectedTime.getHours();
        int minutes = selectedTime.getMinutes();
        int seconds = selectedTime.getSeconds();

        Date date = new Date(year, month, day, hours, minutes, seconds);

        DateSelectedEvent evt = new DateSelectedEvent(date);
        for (Iterator iter = dateSelectionListener.iterator(); iter.hasNext();) {
            DateSelectionListener listener =
                    (DateSelectionListener) iter.next();
            listener.dateSelected(evt);
        }
    }

    public void addDateSelectionListener(DateSelectionListener listener) {
        dateSelectionListener.add(listener);
    }

    /**
     * TODO comment me!
     * 
     * @see org.eclipse.swt.widgets.Control#setEnabled(boolean)
     */
    @Override
    public void setEnabled(final boolean enabled) {
        Display display;
        display = this.getDisplay();
        display.syncExec(new Runnable() {
            @Override
            public void run() {
                DateTimeSelectComposite.super.setEnabled(enabled);
                timeTextField.setEnabled(enabled);
                popupButton.setEnabled(enabled);
            }
        });
    }

}