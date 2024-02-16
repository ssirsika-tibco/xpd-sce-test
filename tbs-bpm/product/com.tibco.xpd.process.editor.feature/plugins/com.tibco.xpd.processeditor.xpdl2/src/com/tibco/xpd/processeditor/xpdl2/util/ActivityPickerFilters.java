/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.util;

import org.eclipse.jface.viewers.IFilter;

import com.tibco.xpd.processeditor.xpdl2.properties.ActivityPickerControl;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Activity picker filters.
 * <p>
 * Wrapper class for various filters that can be used for activity picking for
 * various scenarios.
 * <p>
 * This clas used to also contain a class for launchiong picker etc but that has
 * been refactored out into a standard control+picker class
 * {@link ActivityPickerControl}
 * 
 * @author aallway
 * @since 3.6
 */
public class ActivityPickerFilters {

    /**
     * Filter for activities that are valid to be used as event handler
     * initialisers.
     * <p>
     * Replaces previous static method call
     * isValidEventHandlerInititialiserActivity()
     * 
     * 
     * @author aallway
     * @since 11 Mar 2013
     */
    public static class ValidEventHandlerInitialiserActivityFilter implements
            IFilter {

        /**
         * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
         * 
         * @param toTest
         * @return
         */
        @Override
        public boolean select(Object toTest) {
            boolean ret = false;

            if (toTest instanceof Activity) {
                Activity activity = (Activity) toTest;

                /*
                 * Sid XPD-3926: Misinterpretation of the statement in the
                 * Studio proposal for ABPM-223 that read...
                 * 
                 * "The user can select any task, embedded-sub-process or event
                 * activity (except those attached to task boundary, or events
                 * that are themselves event handlers). This includes activities
                 * on event handler outgoing flows. ***It does not include
                 * activities in re-usable sub-processes***"
                 * 
                 * The latter meant that you could choose activities in a
                 * reusable sub-process that is invoked from the process of the
                 * event handler. It didn't mean
                 * "a process that appears to be a sub-process because it implements a process interface"
                 * 
                 * So the picker will only show activities in this process not
                 * invoked sub-processes so no need to check explicitly (removed
                 * filter based on whether parent process implement interface)
                 */

                if (activity.getEvent() != null) {
                    /*
                     * If it's an event then it mustn't be an event handler or
                     * an event attached to task.
                     */
					/*
					 * ACE-6836 Re-enable Correlation Data and Hence Event initialisers for Incoming Request Event
					 * handlers and Event Sub-processes
					 */
					if (!Xpdl2ModelUtil.isEventHandlerActivity(activity)
							&& !Xpdl2ModelUtil.isEventSubProcessStartEvent(activity)
                            && !Xpdl2ModelUtil.isEventAttachedToTask(activity)) {
                        ret = true;
                    }

                } else {
                    // is a task
                    /*
                     * Sid XPD-3926. Changed is a task test because there are
                     * other task types other than implementation = task and
                     * embedded sub-proc.
                     */
                    if (TaskObjectUtil.getTaskTypeStrict(activity) != null) {
                        ret = true;
                    }
                }
            }

            return ret;
        }

    }

    /**
     * Filter for activities that are timer events attached to the given task.
     * 
     * @author aallway
     * @since 11 Mar 2013
     */
    public static class ValidTaskBoundaryTimerEventFilter implements IFilter {

        private Activity taskActivity;

        /**
         * @param taskActivity
         *            Ther task activity that the timer event must be attached
         *            to to be filtered-in.
         */
        public ValidTaskBoundaryTimerEventFilter(Activity taskActivity) {
            super();
            this.taskActivity = taskActivity;
        }

        /**
         * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
         * 
         * @param toTest
         * @return
         */
        @Override
        public boolean select(Object toTest) {
            if (toTest instanceof Activity) {
                Activity activity = (Activity) toTest;

                if (EventTriggerType.EVENT_TIMER_LITERAL.equals(EventObjectUtil
                        .getEventTriggerType(activity))) {

                    Activity taskAttachedTo =
                            EventObjectUtil.getTaskAttachedTo(activity);

                    if (taskAttachedTo != null
                            && taskAttachedTo.equals(taskActivity)) {
                        return true;
                    }
                }
            }
            return false;
        }

    }

}
