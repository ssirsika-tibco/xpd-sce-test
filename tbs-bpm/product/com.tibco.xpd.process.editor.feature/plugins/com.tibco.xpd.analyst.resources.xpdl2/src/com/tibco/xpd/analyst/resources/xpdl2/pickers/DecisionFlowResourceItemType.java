/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.pickers;

/**
 * The semantics used by the resource db plugin. It is an abstraction over
 * possible different API's.
 * 
 * 
 * @author Miguel Torres
 * 
 */ 
public enum DecisionFlowResourceItemType {

    DECISIONFLOW("DECISIONFLOW"),//$NON-NLS-1$
    DECISIONFLOW_PACKAGE("DECISIONFLOW_PACKAGE"),//$NON-NLS-1$

    UNKNOWN("UNKNOWN");//$NON-NLS-1$

    String name;

    DecisionFlowResourceItemType(String name) {
        this.name = name;
    }

    static public DecisionFlowResourceItemType create(String name) {
        if (name.equalsIgnoreCase("DECISIONFLOW")) {//$NON-NLS-1$
            return DECISIONFLOW;
        } else if (name.equalsIgnoreCase("DECISIONFLOW_PACKAGE")) {//$NON-NLS-1$
            return DECISIONFLOW_PACKAGE;
        }
        return UNKNOWN;
    }
}
