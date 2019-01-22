/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.mapper;

/**
 * @author nwilson
 */
public interface MapperItemListener {
    /**
     * @param src
     *            The object generating the event.
     * @param items
     *            The items selected.
     */
    void sourceItemsSelected(Object src, Object[] items);

    /**
     * @param src
     *            The object generating the event.
     * @param items
     *            The items selected.
     */
    void targetItemsSelected(Object src, Object[] items);
}
