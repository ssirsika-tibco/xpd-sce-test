/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.indexing;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.indexer.IndexResourceMatcher;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;

/**
 * 
 * @author mtorres
 *
 */
public class Xpdl2IndexResourceMatcher implements IndexResourceMatcher {

    public Xpdl2IndexResourceMatcher() {        
    }

    public boolean accept(WorkingCopy wc) {
		if (wc != null && !wc.isInvalidFile()) {
			if (wc instanceof Xpdl2WorkingCopyImpl) {
				return true;
			}
		}
		return false;
	}

}
