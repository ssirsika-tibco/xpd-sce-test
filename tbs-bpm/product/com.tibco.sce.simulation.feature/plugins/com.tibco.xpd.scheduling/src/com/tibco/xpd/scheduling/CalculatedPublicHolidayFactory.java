package com.tibco.xpd.scheduling;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.Wrapper;


public class CalculatedPublicHolidayFactory {
    private String name;
    private String script;

    public CalculatedPublicHolidayFactory(String name, String script) {
        this.name = name;
        this.script = script;
    }

    public IPublicHoliday getPublicHoliday(int year) {
        IPublicHoliday holiday = null;
        try {
            Context cx = Context.enter();
            Scriptable scope = cx.initStandardObjects();
            scope.put("year", scope, new Integer(year)); //$NON-NLS-1$
            Object result = cx.evaluateString(scope, script, "<cmd>", 1, null); //$NON-NLS-1$
            if (result instanceof Wrapper) {
                result = ((Wrapper) result).unwrap();
                if (result instanceof Date) {
                    Date date = (Date) result;
                    GregorianCalendar calendar = new GregorianCalendar();
                    calendar.setTime(date);
                    calendar.add(Calendar.DATE, 1);
                    holiday = new SinglePublicHoliday(name, date, calendar.getTime());
                }
            } else if (result instanceof ScriptableObject) {
                ScriptableObject scriptable = (ScriptableObject) result;
                Object start = scriptable.get(0, scriptable);
                if (start != null && start instanceof Wrapper) {
                    start = ((Wrapper) start).unwrap();
                }
                Object end = scriptable.get(1, scriptable);
                if (end != null && end instanceof Wrapper) {
                    end = ((Wrapper) end).unwrap();
                }
                if (start instanceof Date && end instanceof Date) {
                    Date startDate = (Date) start;
                    Date endDate = (Date) end;
                    holiday = new SinglePublicHoliday(name, startDate, endDate);
                }
            }
        } finally {
            Context.exit();
        }
        return holiday;
    }
}
