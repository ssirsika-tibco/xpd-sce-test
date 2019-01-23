package com.tibco.xpd.analyst.resources.xpdl2.indexing;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.indexer.IndexResourceMatcher;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;


public class ProcessToBOMIndexResourceMatcher implements IndexResourceMatcher {

    public ProcessToBOMIndexResourceMatcher() {        
    }

    public boolean accept(WorkingCopy wc) {
        if (wc != null && !wc.isInvalidFile()) {
            if (wc instanceof Xpdl2WorkingCopyImpl
                    || wc instanceof BOMWorkingCopy) {
                return true;
            }
        }
        return false;
    }

}
