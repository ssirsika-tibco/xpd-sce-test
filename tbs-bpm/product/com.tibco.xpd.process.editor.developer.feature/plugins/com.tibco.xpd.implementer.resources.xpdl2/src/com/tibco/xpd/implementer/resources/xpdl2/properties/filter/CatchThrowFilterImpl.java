/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.properties.filter;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;

import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.CatchThrow;

/**
 * @author rsomayaj
 * 
 */
public class CatchThrowFilterImpl implements CatchThrowFilter {

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.filter.CatchThrowFilter#hasCatchThrowType(com.tibco.xpd.xpdl2.CatchThrow)
     * 
     * @param catchThrow
     * @return
     */
    public IFilter hasCatchThrowType(CatchThrow catchThrow) {
        return new CatchThrowFilter(catchThrow);
    }

    class CatchThrowFilter implements IFilter {

        private CatchThrow catchThrow;

        /**
         * @param catchThrow
         */
        public CatchThrowFilter(CatchThrow catchThrow) {
            this.catchThrow = catchThrow;
        }

        /**
         * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
         * 
         * @param toTest
         * @return
         */
        public boolean select(Object toTest) {
            boolean result = false;
            EObject eo = null;
            if (toTest instanceof EObject) {
                eo = (EObject) toTest;
            } else if (toTest instanceof IAdaptable) {
                eo = (EObject) ((IAdaptable) toTest).getAdapter(EObject.class);
            }

            if (eo != null && eo instanceof Activity) {
                Activity activity = (Activity) eo;

                if (EventFlowType.FLOW_INTERMEDIATE_LITERAL
                        .equals(EventObjectUtil.getFlowType(activity))) {

                    EventTriggerType triggerType =
                            EventObjectUtil.getEventTriggerType(activity);
                    if (CatchThrow.CATCH.equals(this.catchThrow)) {
                        switch (triggerType.getValue()) {
                        case EventTriggerType.EVENT_COMPENSATION_CATCH:
                        case EventTriggerType.EVENT_LINK_CATCH:
                        case EventTriggerType.EVENT_MESSAGE_CATCH:
                        case EventTriggerType.EVENT_SIGNAL_CATCH:
                        case EventTriggerType.EVENT_MULTIPLE_CATCH:
                            result = true;
                        }
                    } else if (CatchThrow.THROW.equals(this.catchThrow)) {
                        switch (triggerType.getValue()) {
                        case EventTriggerType.EVENT_COMPENSATION_THROW:
                        case EventTriggerType.EVENT_LINK_THROW:
                        case EventTriggerType.EVENT_MESSAGE_THROW:
                        case EventTriggerType.EVENT_SIGNAL_THROW:
                        case EventTriggerType.EVENT_MULTIPLE_THROW:
                            result = true;
                        }
                    }
                }

            }

            return result;
        }
    }
}
