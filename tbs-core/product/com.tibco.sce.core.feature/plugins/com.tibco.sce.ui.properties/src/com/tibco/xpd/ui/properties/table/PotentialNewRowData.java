/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.ui.properties.table;

/**
 * Mock cell data representing "..." action. This is the data added to end of
 * real table rows for potential creation of new row.
 * 
 * @author mmaciuki
 */
final class PotentialNewRowData {

    /**
     * Singleton instance of this class
     */
    public static final PotentialNewRowData INSTANCE = new PotentialNewRowData();

    /**
     * Default constructor. Set to private so it cannot be instantiated from
     * outside this class.
     */
    private PotentialNewRowData() {
    }
}
