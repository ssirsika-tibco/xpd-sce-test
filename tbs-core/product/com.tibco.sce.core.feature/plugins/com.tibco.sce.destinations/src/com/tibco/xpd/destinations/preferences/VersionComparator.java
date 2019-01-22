/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.destinations.preferences;

import java.util.Comparator;

/**
 * Class for comparing version numbers.
 * 
 * @author NWilson
 * 
 */
public class VersionComparator implements Comparator<String> {

    public int compare(String o1, String o2) {
        int result = 0;
        if (o1 != null && o2 != null) {
            ComponentVersionsLabelProvider label = new ComponentVersionsLabelProvider();
            o1 = label.getText(o1);
            o2 = label.getText(o2);
            int length = Math.min(o1.length(), o2.length());
            for (int i = 0; i < length; i++) {
                char c1 = o1.charAt(i);
                char c2= o2.charAt(i);
                if (Character.isDigit(c1)) {
                    if (Character.isDigit(c2)) {
                        result = c1 - c2;
                    } else {
                        result = 1;
                    }
                } else {
                    if (Character.isDigit(c2)) {
                        result = -1;
                    } else {
                        result = c1 - c2;
                    }
                }
                if (result != 0) {
                    break;
                }
            }
            if (result == 0) {
                result = o1.length() - o2.length();
            }
        }
        return result;
    }

}
