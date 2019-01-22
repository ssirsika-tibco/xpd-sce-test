/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.resources.indexer;

import com.tibco.xpd.resources.WorkingCopy;

/**
 * Implemented by indexer providers to dynamically check for which resources (
 * {@link WorkingCopy WorkingCopies}) to index.
 * 
 * @author njpatel
 * 
 * @since 3.2
 * 
 */
public interface IndexResourceMatcher {

    /**
     * Check if this {@link WorkingCopy} should be indexed.
     * 
     * @param wc
     *            <code>WorkingCopy</code>
     * @return <code>true</code> if this <code>WorkingCopy</code> should be
     *         indexer, <code>false</code> otherwise.
     */
    boolean accept(WorkingCopy wc);
}
