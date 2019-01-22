/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.pickers;

/**
 * The semantics used by the resource db plugin. It is an abstraction over
 * possible different API's.
 * 
 * TODO: Understand why ResourceItemType is in the core if specific to BOM or,
 * Not Specific?
 * 
 * @author Miguel Torres
 * 
 */
public enum ProcessResourceItemType {

    PROCESS("PROCESS"), PROCESSINTERFACE("PROCESSINTERFACE"), SERVICEPROCESSINTERFACE("SERVICEPROCESSINTERFACE"), SERVICEPROCESS("SERVICEPROCESS"), PROCESSPACKAGE("PROCESSPACKAGE"), PAGEFLOW("PAGEFLOW"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$

    UNKNOWN("UNKNOWN");//$NON-NLS-1$

    String name;

    ProcessResourceItemType(String name) {
        this.name = name;
    }

    static public ProcessResourceItemType create(String name) {

        if ("PROCESS".equalsIgnoreCase(name)) {//$NON-NLS-1$

            return PROCESS;
        } else if ("PROCESSINTERFACE".equalsIgnoreCase(name)) {//$NON-NLS-1$

            return PROCESSINTERFACE;
        } else if ("SERVICEPROCESSINTERFACE".equalsIgnoreCase(name)) { //$NON-NLS-1$

            return SERVICEPROCESSINTERFACE;
        } else if ("SERVICEPROCESS".equalsIgnoreCase(name)) { //$NON-NLS-1$

            return SERVICEPROCESS;
        } else if ("PROCESSPACKAGE".equalsIgnoreCase(name)) {//$NON-NLS-1$

            return PROCESSPACKAGE;
        } else if ("PAGEFLOW".equalsIgnoreCase(name)) {//$NON-NLS-1$

            return PAGEFLOW;
        }
        return UNKNOWN;
    }
}
