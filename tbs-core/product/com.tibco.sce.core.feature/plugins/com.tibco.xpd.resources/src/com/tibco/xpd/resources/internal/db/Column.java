/*
 * Copyright (c) TIBCO Software Inc. 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.resources.internal.db;

/**
 * Indexer table column.
 * 
 * @author njpatel
 */
public class Column {

    private String name;

    private String length;

    /*
     * SCF-173: Added option to set indexes in the database for a given column.
     * This will improve performance for columns that are searched on
     * frequently.
     */
    private boolean isIndexed;

    /**
     * Indexer table column.
     * 
     * @param name
     * @param length
     */
    public Column(String name, String length) {
        this(name, length, false);
    }

    /**
     * Indexer table column.
     * 
     * @param name
     * @param length
     * @param isIndexed
     *            if <code>true</code> then this column will be set as an index
     *            for the table (for performance any column that is searched on
     *            heavily will benefit from being indexed - primary keys should
     *            not be set as index as they automatically are).
     */
    public Column(String name, String length, boolean isIndexed) {
        this.name = name;
        this.length = length;
        this.isIndexed = isIndexed;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the length
     */
    public String getLength() {
        return length;
    }

    /**
     * Check if this column should be indexed.
     * 
     * @return the isIndexed
     */
    public boolean isIndexed() {
        return isIndexed;
    }

    /**
     * @see java.lang.Object#toString()
     * 
     * @return
     */
    @Override
    public String toString() {
        if (isIndexed) {
            return String.format("%1$s [size: %2$s][indexed]", name, length); //$NON-NLS-1$
        }
        return String.format("%1$s [size: %2$s]", name, length); //$NON-NLS-1$
    }
}
