package com.tibco.xpd.scheduling;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.scheduling.calendar.CalculatedHolidayType;
import com.tibco.xpd.scheduling.calendar.Calendar;
import com.tibco.xpd.scheduling.calendar.PublicHolidays;
import com.tibco.xpd.scheduling.calendar.SingleHolidayType;
import com.tibco.xpd.scheduling.calendar.TimeSlot;
import com.tibco.xpd.scheduling.calendar.WorkingSchedule;
import com.tibco.xpd.scheduling.calendar.WorkingDay;
import com.tibco.xpd.scheduling.calendar.WorkingTime;

public class SchedulingCalendar implements ISchedulingCalendar {
    /** The base calendar including a default working schedule. */
    private Calendar calendar;
    /** The current working schedule, separated out from Calendar so that the default can be overriden. */
    private WorkingSchedule workingSchedule;
    /** Map of day of week Integer to TimeSlot[]. */
    private HashMap baseWorkingDays;
    /** Time format for parsing dates. */
    private DateFormat dateFormat;
    /** Time format for parsing time slots. */
    private DateFormat timeFormat;

    public SchedulingCalendar(Calendar calendar) {
        this.calendar = calendar;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd"); //$NON-NLS-1$
        timeFormat = new SimpleDateFormat("HH:mm:ss"); //$NON-NLS-1$
        workingSchedule = calendar.getDefaultWorkingSchedule();
        baseWorkingDays = new HashMap();
        WorkingTime defaultWorkingTime = workingSchedule.getDefaultWorkingTime();
        com.tibco.xpd.scheduling.TimeSlot[] defaultTimeSlots = getTimeSlots(defaultWorkingTime);
        EList workingDayList = workingSchedule.getWorkingDay();
        for (Iterator i = workingDayList.iterator(); i.hasNext();) {
            WorkingDay workingDay = (WorkingDay) i.next();
            WorkingTime workingTime = workingDay.getWorkingTime();
            com.tibco.xpd.scheduling.TimeSlot[] timeSlots;
            if (workingTime == null) {
                timeSlots = defaultTimeSlots;
            } else {
                timeSlots = getTimeSlots(workingTime);
            }
            baseWorkingDays.put(new Integer(workingDay.getDayOfWeek()), timeSlots);
        }
    }
    
    public IPublicHoliday[] getPublicHolidays(int year) {
        IPublicHoliday[] holidays;
        ArrayList holidayList = new ArrayList();
        PublicHolidays publicHolidays = calendar.getPublicHolidays();
        if (publicHolidays != null) {
            EList singleHolidayList = publicHolidays.getSingleHoliday();
            for (Iterator i = singleHolidayList.iterator(); i.hasNext();) {
                Object next = i.next();
                if (next instanceof SingleHolidayType) {
                    SingleHolidayType holiday = (SingleHolidayType) next;
                    String startString = holiday.getStartDate().toString();
                    String endString = holiday.getEndDate().toString();
                    try {
                        Date start = dateFormat.parse(startString);
                        Date end = dateFormat.parse(endString);
                        holidayList.add(new SinglePublicHoliday(holiday.getName(), start, end));
                    } catch (ParseException e) {
                        // Bad date format
                    }
                }
            }
            EList calculatedHolidayList = publicHolidays.getCalculatedHoliday();
            for (Iterator i = calculatedHolidayList.iterator(); i.hasNext();) {
                Object next = i.next();
                if (next instanceof CalculatedHolidayType) {
                    CalculatedHolidayType holiday = (CalculatedHolidayType) next;
                    CalculatedPublicHolidayFactory factory = new CalculatedPublicHolidayFactory(holiday.getName(), holiday.getScript());
                    IPublicHoliday publicHoliday = factory.getPublicHoliday(year);
                    if (publicHoliday != null) {
                        holidayList.add(publicHoliday);
                    }
                }
            }
        }
        holidays = new IPublicHoliday[holidayList.size()];
        holidayList.toArray(holidays);
        return holidays;
    }

    public IPublicHoliday[] getPublicHolidays(java.util.Calendar start, java.util.Calendar end) {
        IPublicHoliday[] holidays = null;
        int startYear = start.get(java.util.Calendar.YEAR);
        int endYear = end.get(java.util.Calendar.YEAR);
        if (startYear <= endYear) {
            HashSet holidaySet = new HashSet();
            for (int i = startYear; i <= endYear; i++) {
                IPublicHoliday[] yearIHolidays = getPublicHolidays(i);
                for (int j = 0; j < yearIHolidays.length; j++) {
                    holidaySet.add(yearIHolidays[j]);
                }
            }
            holidays = new IPublicHoliday[holidaySet.size()];
            holidaySet.toArray(holidays);
        }
        return holidays;
    }

    public IWorkingDay[] getWorkingDays(java.util.Calendar start, java.util.Calendar end) {
        int startYear = start.get(java.util.Calendar.YEAR);
        int startDay = start.get(java.util.Calendar.DAY_OF_YEAR);
        int endYear = end.get(java.util.Calendar.YEAR);
        int endDay = end.get(java.util.Calendar.DAY_OF_YEAR);
        java.util.Calendar current = (java.util.Calendar) start.clone();
        IPublicHoliday[] holidays = getPublicHolidays(start, end);
        ArrayList workingDays = new ArrayList();
        
        int currentYear = startYear;
        int currentDay = startDay;
        while ((currentYear < endYear) || (currentYear <= endYear && currentDay <= endDay)) {
            com.tibco.xpd.scheduling.TimeSlot[] timeSlots = getTimeSlotsForDay(current);
            if (timeSlots != null) {
                // Subtract public holidays
                timeSlots = subtract(timeSlots, holidays);
                if (currentYear == startYear && currentDay == startDay) {
                    timeSlots = removeBefore(timeSlots, start.getTime());
                }
                if (currentYear == endYear && currentDay == endDay) {
                    timeSlots = removeAfter(timeSlots, end.getTime());
                }
                if (timeSlots.length != 0) {
                    IWorkingDay workingDay = new SpecificWorkingDay(timeSlots);
                    workingDays.add(workingDay);
                }
            }
            current.add(java.util.Calendar.DATE, 1);
            currentYear = current.get(java.util.Calendar.YEAR);
            currentDay = current.get(java.util.Calendar.DAY_OF_YEAR);
        }
        
        IWorkingDay[] workingDayArray = new IWorkingDay[workingDays.size()];
        workingDays.toArray(workingDayArray);
        return workingDayArray;
    }


    public float getWorkingHours(java.util.Calendar start, java.util.Calendar end) {
        float hours = 0;
        IWorkingDay[] workingDays = getWorkingDays(start, end);
        for (int i = 0; i < workingDays.length; i++) {
            IWorkingDay workingDay = workingDays[i];
            hours += workingDay.getWorkingHours();
        }
        return hours;
    }

    public double getWorkingTime(java.util.Calendar start, java.util.Calendar end, int units) {
        float hours = getWorkingHours(start, end);
        double time = hours;
        if (units == java.util.Calendar.MINUTE) {
            time = hours * 60;
        } else if (units == java.util.Calendar.SECOND) {
            time = hours * 3600;
        } else if (units == java.util.Calendar.MILLISECOND) {
            time = hours * 3600000;
        }
        return time;
    }

    public java.util.Calendar getNextStartTime(java.util.Calendar from) {
        java.util.Calendar start = null;
        long startTime = from.getTimeInMillis();
        int holidayYear = from.get(java.util.Calendar.YEAR);
        IPublicHoliday[] holidays = getPublicHolidays(holidayYear);
        java.util.Calendar current = (java.util.Calendar) from.clone();
        while (start == null) {
            com.tibco.xpd.scheduling.TimeSlot[] timeSlots = getTimeSlotsForDay(current);
            if (timeSlots != null) {
                timeSlots = subtract(timeSlots, holidays);
                for (int i = 0; i < timeSlots.length; i++) {
                    long timeSlotStart = timeSlots[i].getStart().getTime();
                    if (timeSlotStart > startTime) {
                        start = new GregorianCalendar();
                        start.setTimeInMillis(timeSlotStart);
                        break;
                    }
                }
            }
            current.add(java.util.Calendar.DAY_OF_MONTH, 1);
            if (current.get(java.util.Calendar.YEAR) != holidayYear) {
                holidayYear = current.get(java.util.Calendar.YEAR);
                holidays = getPublicHolidays(holidayYear);
            }
        }
        return start;
    }

    public java.util.Calendar getNextFinishTime(java.util.Calendar from) {
        java.util.Calendar end = null;
        long endTime = from.getTimeInMillis();
        int holidayYear = from.get(java.util.Calendar.YEAR);
        IPublicHoliday[] holidays = getPublicHolidays(holidayYear);
        java.util.Calendar current = (java.util.Calendar) from.clone();
        while (end == null) {
            com.tibco.xpd.scheduling.TimeSlot[] timeSlots = getTimeSlotsForDay(current);
            if (timeSlots != null) {
                timeSlots = subtract(timeSlots, holidays);
                for (int i = 0; i < timeSlots.length; i++) {
                    long timeSlotEnd = timeSlots[i].getEnd().getTime();
                    if (timeSlotEnd > endTime) {
                        end = new GregorianCalendar();
                        end.setTimeInMillis(timeSlotEnd);
                        break;
                    }
                }
            }
            current.add(java.util.Calendar.DAY_OF_MONTH, 1);
            if (current.get(java.util.Calendar.YEAR) != holidayYear) {
                holidayYear = current.get(java.util.Calendar.YEAR);
                holidays = getPublicHolidays(holidayYear);
            }
        }
        return end;
    }

    private com.tibco.xpd.scheduling.TimeSlot[] removeBefore(com.tibco.xpd.scheduling.TimeSlot[] timeSlots, Date time) {
        ArrayList modified = new ArrayList();
        for (int i = 0; i < timeSlots.length; i++) {
            com.tibco.xpd.scheduling.TimeSlot modifiedTimeSlot = removeBefore(timeSlots[i], time);
            if (modifiedTimeSlot != null) {
                modified.add(modifiedTimeSlot);
            }
        }
        timeSlots = new com.tibco.xpd.scheduling.TimeSlot[modified.size()];
        modified.toArray(timeSlots);
        return timeSlots;
    }

    private com.tibco.xpd.scheduling.TimeSlot removeBefore(com.tibco.xpd.scheduling.TimeSlot slot, Date time) {
        long t = time.getTime();
        if (t > slot.getStart().getTime()) {
            if (t < slot.getEnd().getTime()) {
                slot = new com.tibco.xpd.scheduling.TimeSlot(time, slot.getEnd());
            } else {
                slot = null;
            }
        }
        return slot;
    }

    private com.tibco.xpd.scheduling.TimeSlot[] removeAfter(com.tibco.xpd.scheduling.TimeSlot[] timeSlots, Date time) {
        ArrayList modified = new ArrayList();
        for (int i = 0; i < timeSlots.length; i++) {
            com.tibco.xpd.scheduling.TimeSlot modifiedTimeSlot = removeAfter(timeSlots[i], time);
            if (modifiedTimeSlot != null) {
                modified.add(modifiedTimeSlot);
            }
        }
        timeSlots = new com.tibco.xpd.scheduling.TimeSlot[modified.size()];
        modified.toArray(timeSlots);
        return timeSlots;
    }

    private com.tibco.xpd.scheduling.TimeSlot removeAfter(com.tibco.xpd.scheduling.TimeSlot slot, Date time) {
        long t = time.getTime();
        if (t < slot.getEnd().getTime()) {
            if (t > slot.getStart().getTime()) {
                slot = new com.tibco.xpd.scheduling.TimeSlot(slot.getStart(), time);
            } else {
                slot = null;
            }
        }
        return slot;
    }

    private com.tibco.xpd.scheduling.TimeSlot[] subtract(com.tibco.xpd.scheduling.TimeSlot[] timeSlots, ITimePeriod[] toSubtract) {
        ArrayList modified = new ArrayList();
        for (int i = 0; i < toSubtract.length; i++) {
            for (int j = 0; j < timeSlots.length; j++) {
                com.tibco.xpd.scheduling.TimeSlot[] modifiedTimeSlots = subtract(timeSlots[j], toSubtract[i]);
                for (int k = 0; k < modifiedTimeSlots.length; k++) {
                    modified.add(modifiedTimeSlots[k]);
                }
            }
            timeSlots = new com.tibco.xpd.scheduling.TimeSlot[modified.size()];
            modified.toArray(timeSlots);
            modified = new ArrayList();
        }
        return timeSlots;
    }

    private com.tibco.xpd.scheduling.TimeSlot[] subtract(com.tibco.xpd.scheduling.TimeSlot slot, ITimePeriod period) {
        com.tibco.xpd.scheduling.TimeSlot[] modified = new com.tibco.xpd.scheduling.TimeSlot[] {slot};
        long slotStart = slot.getStart().getTime();
        long slotEnd = slot.getEnd().getTime();
        long periodStart = period.getStart().getTime();
        long periodEnd = period.getEnd().getTime();
        if (periodStart < slotEnd && periodEnd > slotStart) {
            if (periodStart <= slotStart) {
                if (periodEnd >= slotEnd) {
                    modified = new com.tibco.xpd.scheduling.TimeSlot[0];
                } else {
                    modified[0] = new com.tibco.xpd.scheduling.TimeSlot(period.getEnd(), slot.getEnd());
                }
            } else {
                if (periodEnd >= slotEnd) {
                    modified[0] = new com.tibco.xpd.scheduling.TimeSlot(slot.getStart(), period.getStart());
                } else {
                    modified = new com.tibco.xpd.scheduling.TimeSlot[2];
                    modified[0] = new com.tibco.xpd.scheduling.TimeSlot(slot.getStart(), period.getStart());
                    modified[1] = new com.tibco.xpd.scheduling.TimeSlot(period.getEnd(), slot.getEnd());
                }
            }
        }
        return modified;
    }

    private com.tibco.xpd.scheduling.TimeSlot[] getTimeSlotsForDay(java.util.Calendar current) {
        com.tibco.xpd.scheduling.TimeSlot[] timeSlots = null;
        int dayOfWeek = current.get(java.util.Calendar.DAY_OF_WEEK);
        com.tibco.xpd.scheduling.TimeSlot[] baseSlots = (com.tibco.xpd.scheduling.TimeSlot[]) baseWorkingDays.get(new Integer(dayOfWeek));
        if (baseSlots != null) {
            java.util.Calendar start = (java.util.Calendar) current.clone();
            java.util.Calendar end = (java.util.Calendar) current.clone();
            int year = current.get(java.util.Calendar.YEAR);;
            int day = current.get(java.util.Calendar.DAY_OF_YEAR);;
            timeSlots = new com.tibco.xpd.scheduling.TimeSlot[baseSlots.length];
            for (int i = 0; i < baseSlots.length; i++) {
                start.setTime(baseSlots[i].getStart());
                start.set(java.util.Calendar.YEAR, year);
                start.set(java.util.Calendar.DAY_OF_YEAR, day);
                end.setTime(baseSlots[i].getEnd());
                end.set(java.util.Calendar.YEAR, year);
                end.set(java.util.Calendar.DAY_OF_YEAR, day);
                timeSlots[i] = new com.tibco.xpd.scheduling.TimeSlot(start.getTime(), end.getTime());
            }
        }
        return timeSlots;
    }

    private com.tibco.xpd.scheduling.TimeSlot[] getTimeSlots(WorkingTime workingTime) {
        EList timeSlotList = workingTime.getTimeSlot();
        ArrayList timeSlots = new ArrayList();
        for (Iterator i = timeSlotList.iterator(); i.hasNext();) {
            TimeSlot timeSlot = (TimeSlot) i.next();
            String startTimeString = timeSlot.getStartTime().toString();
            String endTimeString = timeSlot.getEndTime().toString();
            try {
                Date start = timeFormat.parse(startTimeString);
                Date end = timeFormat.parse(endTimeString);
                if (start != null && end != null) {
                    timeSlots.add(new com.tibco.xpd.scheduling.TimeSlot(start, end));
                }
            } catch(ParseException e) {
               // Bad time format. 
            }
        }
        com.tibco.xpd.scheduling.TimeSlot[] timeSlotArray
                = new com.tibco.xpd.scheduling.TimeSlot[timeSlots.size()];
        timeSlots.toArray(timeSlotArray);
        return timeSlotArray;
    }
}
