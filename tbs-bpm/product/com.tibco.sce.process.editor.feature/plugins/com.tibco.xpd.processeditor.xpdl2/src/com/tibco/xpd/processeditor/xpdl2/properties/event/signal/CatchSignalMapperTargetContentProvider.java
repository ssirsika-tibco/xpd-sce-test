/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.event.signal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceData;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceDataUtil;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.ActivityDataFieldItemProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPathComparator;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ModeType;

/**
 * Target content provider for catch signal event mappings.
 * <p>
 * The context for the target data is the Task that the event is attached to NOT
 * the event activity itself i.e. it is the data associated with the task
 * (including, possibly, any data local to the task)
 * <p>
 * If the event is not attached to task then the input event activity's data
 * associations are used.
 * 
 * @author aallway
 * @since 30 Apr 2012
 */
public class CatchSignalMapperTargetContentProvider extends
        ActivityDataFieldItemProvider {

    /**
     * @param direction
     */
    public CatchSignalMapperTargetContentProvider() {
        super(MappingDirection.OUT);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.ActivityDataFieldItemProvider#getElements(java.lang.Object)
     * 
     * @param inputElement
     * @return
     */
    @Override
    public Object[] getElements(Object inputElement) {
        List<ConceptPath> elements = new ArrayList<ConceptPath>();

        if (inputElement instanceof Activity) {
            /*
             * Get task attached to and re-direct content provider to that task
             * - that way the user will be able to see local data for the task.
             * 
             * If not attached to task then have to use that of the signal
             * itself.
             */
            Activity contextActivity =
                    EventObjectUtil.getTaskAttachedTo((Activity) inputElement);

            boolean isAttached;
            if (contextActivity == null) {
                contextActivity = (Activity) inputElement;
                isAttached = false;
            } else {
                isAttached = true;
            }

            Collection<ActivityInterfaceData> activityInterfaceData =
                    ActivityInterfaceDataUtil
                            .getActivityInterfaceData(contextActivity);

            for (ActivityInterfaceData interfaceData : activityInterfaceData) {
                /*
                 * For attached to task only show IN or INOUT mode (can't resend
                 * OUT only data as we didn't sen it in the first place!)
                 * 
                 * If not attached then allow everything.
                 */
                if (!isAttached
                        || ModeType.IN_LITERAL.equals(interfaceData.getMode())
                        || ModeType.INOUT_LITERAL.equals(interfaceData
                                .getMode())) {
                    elements.add(ConceptUtil.getConceptPath(interfaceData
                            .getData()));
                }
            }

            Collections.sort(elements, new ConceptPathComparator());
        }

        return elements.toArray();
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.ActivityDataFieldItemProvider#hasChildren(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public boolean hasChildren(Object element) {
        /*
         * Prevent mapping to complex object child content for updating work
         * item data.
         */
        return false;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.ActivityDataFieldItemProvider#getChildren(java.lang.Object)
     * 
     * @param parentElement
     * @return
     */
    @Override
    public Object[] getChildren(Object parentElement) {
        /*
         * Prevent mapping to complex object child content for updating work
         * item data.
         */
        return new Object[0];
    }

}
