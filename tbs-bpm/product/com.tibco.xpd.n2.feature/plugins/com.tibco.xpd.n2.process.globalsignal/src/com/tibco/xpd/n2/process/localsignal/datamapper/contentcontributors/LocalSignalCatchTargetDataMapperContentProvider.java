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

    /**
     * Sid ACE-3025... When we switched to Data Mapper grammar for ACE for catch local signal on a user task boundary it
     * introduced the ability to expand the content of complex data fields on the right hand side of the mapper and
     * allow the user to map directly to child content.
     * 
     * If a user maps just a few of a target complex fields child attributes, then they might rightly be fooled into
     * thinking that ONLY the few mappings would be applied to the target object WITHINT HE ACTUAL WORK ITEM.
     * 
     * However, this is not the case, merging the new values into the target complex field (by doing the assignments
     * specified by the mappings) happens on the process side of things. Then the whole target complex field is resent
     * to the Work Item in its entirety so whatever other values are prevalent in the process at that point will
     * overwrite the current values in the work item.
     * 
     * The proposed solution to this is not to allow the user to expand the RHS complex type content at all. Thus the
     * user can only map directly to a whole complex type field and this may be more intuitive in leading the user to
     * believe that they will overwrite the whole field in the work item.
     * 
     * So we'll override the default content provider and prevent children from being returned.
     * 
     * @see com.tibco.xpd.process.datamapper.common.ProcessDataMapperConceptPathProvider#getChildren(java.lang.Object)
     *
     * @param parentElement
     * @return
     */
    @Override
    public Object[] getChildren(Object parentElement) {
        return null;
    }

    /**
     * @see com.tibco.xpd.process.datamapper.common.ProcessDataMapperConceptPathProvider#hasChildren(java.lang.Object)
     *
     * @param element
     * @return
     */
    @Override
    public boolean hasChildren(Object element) {
        return false;
    }

}
