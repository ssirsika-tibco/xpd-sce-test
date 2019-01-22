package com.tibco.xpd.worklistfacade.resource.mapper;

import org.eclipse.jface.viewers.IFilter;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.xpdl2.Process;

/**
 * Section filter for Process data to Work List Facade mapping.
 * 
 * 
 * @author aprasad
 * @since 11-nov-2013
 */
public class ProcessDataToWIAttributeMappingSectionFilter implements
        IFilter {

    public ProcessDataToWIAttributeMappingSectionFilter() {

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
    @Override
    public boolean select(Object toTest) {
        if (toTest instanceof Process) {
            return com.tibco.xpd.n2.resources.util.N2Utils
                    .isN2DestinationEnabled((Process) toTest)
                    && !XpdResourcesPlugin.isRCP();
            // Do not Show this Section in RCP
        }
        return false;

    }
}
