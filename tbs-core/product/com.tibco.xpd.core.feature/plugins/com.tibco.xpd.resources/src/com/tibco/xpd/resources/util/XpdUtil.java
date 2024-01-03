/*
 * Copyright (c) 2004-2023. Cloud Software Group, Inc. All Rights Reserved.
 */

package com.tibco.xpd.resources.util;

/**
 * General utilities
 * 
 * SCF-569 Added for support of removed Eclipse utils etc
 * 
 * @author aallway
 *
 */
public class XpdUtil {

    /**
     * A safe "equals" implementation that allows either or both objects to be assigned or null.
     * 
     * @param object1
     * @param object2
     * @return true if the objects are equal.
     */
    public static boolean safeEquals(Object object1, Object object2) {
        if (object1 == null) {
            if (object2 == null) {
                return true;
            } else {
                return false;
            }
        } else if (object2 == null) {
            if (object1 == null) {
                return true;
            } else {
                return false;
            }
        } else {
            return object1.equals(object2);
        }
    }

}
