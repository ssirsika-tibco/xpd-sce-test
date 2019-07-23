/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.process.localsignal.datamapper.contentcontributors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceData;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceDataUtil;
import com.tibco.xpd.process.datamapper.common.ProcessDataMapperConceptPathProvider;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * Content provider implementation for Local Signal CATCH Data Mapper (target).
 *
 * @author sajain
 * @since Jul 17, 2019
 */
public class LocalSignalCatchTargetDataMapperContentProvider extends ProcessDataMapperConceptPathProvider {

    /**
     * Content provider implementation for Local Signal CATCH Data Mapper
     * (target).
     */
    public LocalSignalCatchTargetDataMapperContentProvider() {
    }

    /**
     * @see com.tibco.xpd.n2.process.globalsignal.mapping.CatchGlobalSignalMapperTargetContentProvider#getInScopeProcessData(com.tibco.xpd.xpdl2.Activity)
     *
     * @param activity
     * @return
     */
    @Override
    protected List<ProcessRelevantData> getInScopeProcessData(Activity activity) {
        List<ProcessRelevantData> inScopeProcessData = new ArrayList<ProcessRelevantData>();

        if (null != activity) {

            /*
             * Get the task this event is attached to and re-direct content
             * provider to that task. That way the user will be able to see
             * local data for the task.
             * 
             * If the event is not attached to any task, then we'll have to use
             * that of the signal itself.
             */
            Activity contextActivity = EventObjectUtil.getTaskAttachedTo(activity);

            boolean isAttached;
            if (contextActivity == null) {
                contextActivity = activity;
                isAttached = false;
            } else {
                isAttached = true;
            }

            Collection<ActivityInterfaceData> activityInterfaceData =
                    ActivityInterfaceDataUtil.getActivityInterfaceData(contextActivity);

            for (ActivityInterfaceData interfaceData : activityInterfaceData) {
                /*
                 * For attached to task only show IN or INOUT mode (can't resend
                 * OUT only data as we didn't send it in the first place!)
                 * 
                 * If not attached then allow everything.
                 */
                if (!isAttached || ModeType.IN_LITERAL.equals(interfaceData.getMode())
                        || ModeType.INOUT_LITERAL.equals(interfaceData.getMode())) {
                    inScopeProcessData.add(interfaceData.getData());
            }
        }
        }

        return inScopeProcessData;
    }

}
