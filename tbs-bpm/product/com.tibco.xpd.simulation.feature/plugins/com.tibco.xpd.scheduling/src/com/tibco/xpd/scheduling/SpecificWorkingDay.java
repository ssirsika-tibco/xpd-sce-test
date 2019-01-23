package com.tibco.xpd.scheduling;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class SpecificWorkingDay implements IWorkingDay {
    private TimeSlot[] timeSlots;

    public SpecificWorkingDay(TimeSlot[] timeSlots) {
        this.timeSlots = timeSlots;
    }

    public float getWorkingHours() {
        float hours = 0;
        for (int i = 0; i < timeSlots.length; i++) {
            hours += timeSlots[i].gethours();
        }
        return hours;
    }

    public String toString() {
        DateFormat date = new SimpleDateFormat("dd-MM-yyyy"); //$NON-NLS-1$
        DateFormat time = new SimpleDateFormat("HH:mm"); //$NON-NLS-1$
        StringBuffer buffer = new StringBuffer();
        buffer.append("SpecificWorkingDay"); //$NON-NLS-1$
        if (timeSlots.length > 0) {
            buffer.append("("); //$NON-NLS-1$
            buffer.append(date.format(timeSlots[0].getStart()));
            buffer.append(")"); //$NON-NLS-1$
            for (int i = 0; i < timeSlots.length; i++) {
                buffer.append("["); //$NON-NLS-1$
                buffer.append(time.format(timeSlots[i].getStart()));
                buffer.append("-"); //$NON-NLS-1$
                buffer.append(time.format(timeSlots[i].getEnd()));
                buffer.append("]"); //$NON-NLS-1$
            }
        }
        return buffer.toString();
    }
}
