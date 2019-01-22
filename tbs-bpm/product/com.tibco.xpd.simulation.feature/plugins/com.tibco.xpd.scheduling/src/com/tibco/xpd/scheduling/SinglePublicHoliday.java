package com.tibco.xpd.scheduling;

import java.util.Date;

public class SinglePublicHoliday implements IPublicHoliday {
    private String name;
    private Date startDate;
    private Date endDate;
    
    public SinglePublicHoliday(String name, Date startDate, Date endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public Date getStart() {
        return startDate;
    }

    public Date getEnd() {
        return endDate;
    }

    public String toString() {
        return name + " (" + startDate.toString() + " - " + endDate.toString() + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    public boolean equals(Object obj) {
        boolean equal = false;
        if (obj instanceof SinglePublicHoliday) {
            SinglePublicHoliday other = (SinglePublicHoliday) obj;
            if (isEqual(name, other.name)
                    && isEqual(startDate, other.getStart())
                    && isEqual(endDate, other.getEnd())) {
                equal = true;
            }
        }
        return equal;
    }

    private boolean isEqual(Object o1, Object o2) {
        return ((o1 == null && o2 == null) || (o1 != null && o1.equals(o2)));
    }

    public int hashCode() {
        return startDate.hashCode() + endDate.hashCode();
    }
}
