/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.internal;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.validation.ValidationActivator;

/**
 * Internal performance profiling class.
 * 
 * @author nwilson
 */
public final class ValidationProfiler {

    private static long start;

    private static long delta;

    private static Map<Class, ClassTime> times = new HashMap<Class, ClassTime>();

    private static Logger logger = ValidationActivator.getDefault().getLogger();

    private ValidationProfiler() {
    }

    public static void add(Class cls, long time) {
        ClassTime ct = times.get(cls);
        if (ct == null) {
            ct = new ClassTime(cls, time);
            times.put(cls, ct);
        } else {
            ct.add(time);
        }
    }

    public static void delta(String message) {
        long current = System.currentTimeMillis();

        logger.debug(message + ":" + (current - delta)); //$NON-NLS-1$
        delta = current;
    }

    public static void report() {
        long total = System.currentTimeMillis() - start;
        TreeSet<ClassTime> ts = new TreeSet<ClassTime>(times.values());
        for (ClassTime ct : ts) {
            logger.debug(ct.getClassName() + ":" + ct.getTime() + " ns"); //$NON-NLS-1$ //$NON-NLS-2$
        }
        logger.debug("Total:" + total + " ms"); //$NON-NLS-1$ //$NON-NLS-2$;
    }

    public static void reset() {
        times.clear();
        start = System.currentTimeMillis();
        delta = start;
    }

    static class ClassTime implements Comparable<ClassTime> {
        @Override
        public boolean equals(Object obj) {
            boolean equal = false;
            if (obj == this) {
                equal = true;
            } else {
                if (obj instanceof ClassTime) {
                    ClassTime other = (ClassTime) obj;
                    if (cls.equals(other.cls)) {
                        equal = true;
                    }
                }
            }
            return equal;
        }

        @Override
        public int hashCode() {
            return cls.getName().hashCode();
        }

        private Class cls;
        private long time;

        /**
         * @param cls2
         * @param time2
         */
        public ClassTime(Class cls, long time) {
            this.cls = cls;
            this.time = time;
        }

        /**
         * @param time
         */
        public void add(long time) {
            this.time += time;
        }

        public String getClassName() {
            return cls.getName();
        }

        public long getTime() {
            return time;
        }

        /**
         * @param o
         * @return
         * @see java.lang.Comparable#compareTo(java.lang.Object)
         */
        public int compareTo(ClassTime other) {
            return (int) (time - other.time);
        }
    }
}
