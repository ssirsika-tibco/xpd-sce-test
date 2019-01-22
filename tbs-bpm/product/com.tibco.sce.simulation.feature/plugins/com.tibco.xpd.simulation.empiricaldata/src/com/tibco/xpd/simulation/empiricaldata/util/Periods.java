/* 
 ** 
 **  MODULE:             $RCSfile: Periods.java $ 
 **                      $Revision: 1.0 $ 
 **                      $Date: 2006-03-26 $ 
 ** 
 **  DESCRIPTION:           
 **                                              
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2006 TIBCO Software Inc, All Rights Reserved.
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */
package com.tibco.xpd.simulation.empiricaldata.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

import com.tibco.xpd.simulation.empiricaldata.DayOfMonthPeriodType;
import com.tibco.xpd.simulation.empiricaldata.DayOfWeekPeriodType;
import com.tibco.xpd.simulation.empiricaldata.HourOfDayPeriodType;
import com.tibco.xpd.simulation.empiricaldata.MonthOfYearPeriodType;
import com.tibco.xpd.simulation.empiricaldata.PeriodType;

/**
 * Utility class for managing periods.
 * 
 * @author jarciuch
 */
public class Periods {

    private static TimePeriod[] periods = new TimePeriodImpl[] {
            new TimePeriodImpl(
                    PeriodType.MONTH_OF_YEAR_LITERAL, MonthOfYearPeriodType.class, 30), 
            new TimePeriodImpl(
                    PeriodType.DAY_OF_MONTH_LITERAL, DayOfMonthPeriodType.class, 20), 
            new TimePeriodImpl(PeriodType.DAY_OF_WEEK_LITERAL, DayOfWeekPeriodType.class, 20), 
            new TimePeriodImpl(PeriodType.HOUR_OF_DAY_LITERAL, HourOfDayPeriodType.class, 10), 
    };

    /**
     * The constructor. Private to prevent instantiation.
     */
    private Periods() {
    }

    public interface TimePeriod {
        String getPeriodName();
        
        PeriodType getPeriodType();

        Class getPeriodEnum();
        
        TimePeriod[] getSubperiods();

        Enumerator[] getValues();
    }

    private static class TimePeriodImpl implements TimePeriod {
        private final Class periodEnum;

        private final int priority;

        private final PeriodType periodType;

        public TimePeriodImpl(PeriodType periodType, Class periodEnum, int priority) {
            this.periodType = periodType;
            this.periodEnum = periodEnum;
            this.priority = priority;
        }

        public String getPeriodName() {
            return periodType.getName();
        }

        public Class getPeriodEnum() {
            return periodEnum;
        }

        public TimePeriod[] getSubperiods() {
            List subperiods = new ArrayList();
            for (int i = 0; i < periods.length; i++) {
                TimePeriodImpl timePeriod = (TimePeriodImpl) periods[i];
                if (timePeriod.priority < priority) {
                    subperiods.add(timePeriod);
                }
            }
            return (TimePeriod[]) subperiods.toArray(new TimePeriod[subperiods.size()]);
        }

        public Enumerator[] getValues() {
            try {
                Field f = periodEnum.getField("VALUES"); //$NON-NLS-1$
                Object obj = (Object) f.get(null);
                if (obj instanceof List) {
                    List list = ((List) obj);
                    return (Enumerator[]) list.toArray(new Enumerator[list
                            .size()]);
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return new Enumerator[] {};
        }

        public String toString() {
            return getPeriodName();
        }

        public PeriodType getPeriodType() {
            return periodType;
        }
        
        

    }

    public static TimePeriod[] getPeriods() {
        return periods;
    }

    public static String[] getPeriodNames(){
        String[] periodNames = new String[periods.length];
        for (int i = 0; i < periods.length; i++) {
            periodNames[i] = periods[i].getPeriodName();
        }
        return periodNames;
    }
    
    public static Periods.TimePeriod getPeriod(String name) {
        for (int i = 0; i < periods.length; i++) {
            if (periods[i].getPeriodName().equals(name)){
                return periods[i];
            }
        }
        throw new IllegalArgumentException();
    }
    
    public static List getSubperiods(List periods) {
        if (periods == null || periods.isEmpty()) {
            return Arrays.asList(Periods.periods);
        }
        TimePeriodImpl lowestPriorityPeriod = null;
        for (Iterator iter = periods.iterator(); iter.hasNext();) {
            TimePeriodImpl p = (TimePeriodImpl) iter.next();
            if (lowestPriorityPeriod == null || lowestPriorityPeriod.priority > p.priority) {
                lowestPriorityPeriod = p;
            }
        }
        return Arrays.asList(lowestPriorityPeriod.getSubperiods());
    }
}
