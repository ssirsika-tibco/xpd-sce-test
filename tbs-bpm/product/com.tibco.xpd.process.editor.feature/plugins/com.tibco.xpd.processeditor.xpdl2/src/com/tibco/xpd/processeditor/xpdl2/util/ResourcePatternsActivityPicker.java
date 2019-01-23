/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.analyst.resources.xpdl2.pickers.ActivityFilterPicker;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.IActivityPickerProxyItem;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.ActivityFilterPicker.ActivityPickerType;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;

/**
 * @author bharge
 * 
 */
public class ResourcePatternsActivityPicker {

    protected static Object[] pickActivities(final Object input,
            List<Activity> activityList) {
        Activity activity = null;
        if (input instanceof Activity) {
            activity = (Activity) input;
        }
        ActivityFilterPicker activityPicker =
                new ActivityFilterPicker(Display.getDefault().getActiveShell(),
                        ActivityPickerType.ACTIVITY, true);

        activityPicker.addFilter(new IFilter() {

            public boolean select(Object toTest) {
                if (toTest instanceof IActivityPickerProxyItem) {
                    Object obj = ((IActivityPickerProxyItem) toTest).getItem();
                    if (obj instanceof Activity) {
                        Activity newActivity = (Activity) obj;
                        Implementation impl = newActivity.getImplementation();
                        if (impl instanceof Task) {
                            Task task = (Task) impl;
                            if (null != task.getTaskManual()
                                    || null != task.getTaskUser()) {
                                if (input instanceof Activity) {
                                    // Activity act = (Activity) input;
                                    // if (!act.getId()
                                    // .equals(newActivity.getId())) {
                                    // return true;
                                    // }
                                    return true;
                                } else if (input instanceof Process) {
                                    return true;
                                }
                            }

                        }
                    }
                }
                return false;
            }
        });

        activityPicker.setTitle(Messages.ResourcePatternsActivityPicker_SelectTasks_title);

        Process process = null;
        if (input instanceof Activity) {
            activityPicker.setScope(activity);
            process = activity.getProcess();
            /**
             * this is in case of create new activity task group when empty
             * activity list is passed
             */
            if (activityList.size() == 0) {
                activityList.add(activity);
            }
        } else if (input instanceof Process) {
            process = (Process) input;
            activityPicker.setScope(process);
        }

        /**
         * to show the available tasks as the initial selection
         */
        activityPicker.setInitialElementSelections(activityList);
        if (activityPicker.open() == ActivityFilterPicker.OK) {
            return activityPicker.getResult();
        }

        return null;
    }

    public static Object[] pickActivities(final Object input) {
        List<Activity> activityList = new ArrayList<Activity>();
        Object[] pickActivities = pickActivities(input, activityList);
        return pickActivities;
    }

}
