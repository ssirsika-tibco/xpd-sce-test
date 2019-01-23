package com.tibco.n2.ut.resources.dynamicparticipant.mapper;

import org.eclipse.jface.viewers.IFilter;

import com.tibco.xpd.xpdl2.Process;

/**
 * Section filter for Process data to Dynamic Identifier mapping.
 * 
 * 
 * @author kthombar
 * @since 13-Oct-2013
 */
public class ProcessDataToDynamicOrgIdentifierMappingSectionFilter implements
        IFilter {

    public ProcessDataToDynamicOrgIdentifierMappingSectionFilter() {

    }

    /**
     * We do not intend to show this section to non BPM clients(i.e. iProcess),
     * hence, return true when N2 Destination is enabled.
     * 
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     * 
     * @param toTest
     * @return
     */
    public boolean select(Object toTest) {
        if (toTest instanceof Process) {
            return com.tibco.xpd.n2.resources.util.N2Utils
                    .isN2DestinationEnabled((Process) toTest);
        }
        return false;

    }
}
