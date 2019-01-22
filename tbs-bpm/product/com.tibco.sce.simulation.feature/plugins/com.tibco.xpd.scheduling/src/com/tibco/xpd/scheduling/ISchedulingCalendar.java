package com.tibco.xpd.scheduling;

import java.util.Calendar;

public interface ISchedulingCalendar {
    /**
     * @param year The year to return public holidays for.
     * @return An array of public holidays.
     */
    IPublicHoliday[] getPublicHolidays(int year);
    /**
     * Finds the Public Holidays between the start and end dates (both
     * inclusive).
     * 
     * @param start
     *            The start of the period to search for Public Holidays
     *            (inclusive).
     * @param end
     *            The end of the period to search for Public Holidays
     *            (inclusive).
     */
    IPublicHoliday[] getPublicHolidays(Calendar start, Calendar end);

    /**
     * Finds the Working Days between the start and end dates (both inclusive).
     * 
     * @param start
     *            The start of the period to search for Public Holidays
     *            (inclusive).
     * @param end
     *            The end of the period to search for Public Holidays
     *            (inclusive).
     */
    IWorkingDay[] getWorkingDays(Calendar start, Calendar end);

    /**
     * Calcluates the number of working hours between two given points in time taking
     * into account working hours and public holidays.
     *
     * @param start The start date-time.
     * @param end The end date-time.
     * @return The number of working hours between the start and end.
     */
    float getWorkingHours(Calendar start, Calendar end);

    /**
     * Calcluates the number of working hours between two given points in time taking
     * into account working hours and public holidays.
     *
     * @param start The start date-time.
     * @param end The end date-time.
     * @param units The time units, e.g. Calendar.SECOND
     * @return The number of working hours between the start and end.
     */
    double getWorkingTime(Calendar start, Calendar end, int units);

    /**
     * Finds the next TimeSlot start for this participant after the time.
     * @param from The date from which to search.
     * @return The next start time after the from date.
     */
    Calendar getNextStartTime(Calendar from);

    /**
     * Finds the next TimeSlot finish for this participant after the time.
     * @param from The date from which to search.
     * @return The next finish time.
     */
    Calendar getNextFinishTime(Calendar from);
}
