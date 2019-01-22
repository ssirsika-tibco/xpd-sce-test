/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.process.datamapper.common;

import java.util.ArrayList;
import java.util.List;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceData;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceDataUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * Concept path item provider for Process data for all associated interface data
 * in scope of the given activity.
 * 
 * @author Ali
 * @since 19 Jan 2015
 * 
 *        Sid XPD-7839 - This class almost identical to
 *        {@link ProcessDataMapperConceptPathProvider} except for the
 *        determination of the available top level elements, therefore chaned to
 *        sub-class else (liek for initial commit of XPD-7839, we won't get
 *        benefits of fixes to identical functionality)
 */
public class ActivityInterfaceDataMapperConceptPathProvider extends
        ProcessDataMapperConceptPathProvider {

    /**
     * 
     * @see com.tibco.xpd.n2.pe.datamapper.ProcessDataMapperConceptPathProvider#getInScopeProcessData(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    protected List<ProcessRelevantData> getInScopeProcessData(Activity activity) {
        List<ProcessRelevantData> data = new ArrayList<ProcessRelevantData>();

        for (ActivityInterfaceData activityInterfaceData : ActivityInterfaceDataUtil
                .getActivityInterfaceData(activity)) {
            data.add(activityInterfaceData.getData());
        }
        return data;
    }

}
