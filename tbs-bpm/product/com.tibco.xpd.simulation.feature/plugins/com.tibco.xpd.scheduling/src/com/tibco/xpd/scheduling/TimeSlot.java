package com.tibco.xpd.scheduling;

import java.util.Date;

public class TimeSlot implements ITimePeriod {
    private Date start;
    private Date end;
    
    public TimeSlot(Date start, Date end) {
        this.start = start;
        this.end = end;
    }
    
    public Date getEnd() {
        return end;
    }
    public Date getStart() {
        return start;
    }

    public float gethours() {
        // TODO Auto-generated method stub
        return (end.getTime() - start.getTime()) / 3600000f;
    }
}
