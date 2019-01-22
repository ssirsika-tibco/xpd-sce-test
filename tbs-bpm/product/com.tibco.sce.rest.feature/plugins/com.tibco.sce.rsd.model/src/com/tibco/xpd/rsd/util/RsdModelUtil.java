/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rsd.util;

import com.tibco.xpd.rsd.Parameter;
import com.tibco.xpd.rsd.ParameterContainer;
import com.tibco.xpd.rsd.ParameterStyle;

/**
 * Utilities to interrogate RSD model.
 *
 * @author jarciuch
 * @since 2 Apr 2015
 */
public class RsdModelUtil {

    /**
     * Prevents instantiation.
     */
    private RsdModelUtil() {
    }

    public static Parameter findParameterByName(
            ParameterContainer paramContainer, String name, ParameterStyle style) {
        for (Parameter p : paramContainer.getParameters()) {
            if (name.equals(p.getName())) {
                if (style == null) {
                    return p;
                } else {
                    if (p.getStyle() == style) {
                        return p;
                    }
                }
            }
        }
        return null;
    }

    public static Parameter findParameterByName(
            ParameterContainer paramContainer, String name) {
        return findParameterByName(paramContainer, name, null);
    }
}
