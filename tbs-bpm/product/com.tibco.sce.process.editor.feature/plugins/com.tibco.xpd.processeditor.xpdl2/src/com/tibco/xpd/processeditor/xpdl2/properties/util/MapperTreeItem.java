/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.util;

import java.util.Comparator;

/**
 * 
 *Data structure that manages the mapper tree items.
 * 
 * @author rsomayaj
 * @since 3.3 (16 Jun 2010)
 */
public abstract class MapperTreeItem implements Comparator<MapperTreeItem> {

    protected final Object mappingObject;

    public MapperTreeItem(Object mappingObject) {
        this.mappingObject = mappingObject;
    }

    /**
     * @return the mappingObject
     */
    public Object getMappingObject() {
        return mappingObject;
    }

    /**
     * @return
     */
    protected String getNormalizedLabel() {
        // Strip whitespaces

        // Truncate at 1st non-alphanumeric/underscore

        return null;
    }

    /**
     * @return
     */
    public abstract MapperTreeItem getParent();

    public abstract Object getType();

    /**
     * 
     * 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     * 
     * @param o1
     * @param o2
     * @return
     */
    @Override
    public int compare(MapperTreeItem mapperTreeItem1,
            MapperTreeItem mapperTreeItem2) {
        return 0;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        boolean equal = false;
        if (obj == this) {
            equal = true;
        } else if (obj instanceof MapperTreeItem) {
            String otherPath = ((MapperTreeItem) obj).getNormalizedLabel();
            if (getNormalizedLabel().equals(otherPath)) {
                equal = true;
            }
        }
        return equal;
    }
}
