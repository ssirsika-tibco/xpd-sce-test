/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.script;

import java.util.Comparator;

import com.tibco.xpd.xpdExtension.ScriptInformation;

/**
 * @author nwilson
 */
public class ScriptInformationComparator implements
        Comparator<ScriptInformation> {

    /**
     * @param o1
     * @param o2
     * @return
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(ScriptInformation s1, ScriptInformation s2) {
        if (s1.eContainer() == null) {
            return -1;
        }
        if (s2.eContainer() == null) {
            return 1;
        }
        String n1 = s1.getName();
        String n2 = s2.getName();
        return n1.compareTo(n2);
    }

}
