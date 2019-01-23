/*
 ** 
 **  MODULE:             $RCSfile: $ 
 **                      $Revision: $ 
 **                      $Date: $ 
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc., All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */

package com.tibco.xpd.processwidget.adapters;

import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.internal.Messages;

public final class EventFlowType {
    public static final int FLOW_START = 0;

    public static final int FLOW_INTERMEDIATE = 1;

    public static final int FLOW_END = 2;

    public static final EventFlowType FLOW_START_LITERAL =
            new EventFlowType(FLOW_START);

    public static final EventFlowType FLOW_INTERMEDIATE_LITERAL =
            new EventFlowType(FLOW_INTERMEDIATE);

    public static final EventFlowType FLOW_END_LITERAL =
            new EventFlowType(FLOW_END);

    private final int flow;

    private final String name;

    public EventFlowType(int flow) {
        this.flow = flow;
        switch (flow) {
        case FLOW_START:
            name = Messages.EventFlowType_StartEvent_label;
            break;
        case FLOW_INTERMEDIATE:
            name = Messages.EventFlowType_IntermediateEvent_label;
            break;
        case FLOW_END:
            name = Messages.EventFlowType_EndEvent_label;
            break;
        default:
            throw new IllegalArgumentException();
        }
    }

    public int getValue() {
        return flow;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * @return the ProcessWidgetColors.ID used as the default FILL Color for
     *         this task type.
     */
    public String getGetDefaultFillColorId() {
        String colorID = null;

        switch (getValue()) {
        case FLOW_START:
            colorID = ProcessWidgetColors.START_EVENT_FILL;
            break;
        case FLOW_INTERMEDIATE:
            colorID = ProcessWidgetColors.INTERMEDIATE_EVENT_FILL;
            break;
        case FLOW_END:
            colorID = ProcessWidgetColors.END_EVENT_FILL;
            break;
        default:
            throw new IllegalArgumentException();
        }

        return colorID;
    }

    /**
     * @return the ProcessWidgetColors.ID used as the default LINE Color for
     *         this task type.
     */
    public String getGetDefaultLineColorId() {
        String colorID = null;

        switch (getValue()) {
        case FLOW_START:
            colorID = ProcessWidgetColors.START_EVENT_LINE;
            break;
        case FLOW_INTERMEDIATE:
            colorID = ProcessWidgetColors.INTERMEDIATE_EVENT_LINE;
            break;
        case FLOW_END:
            colorID = ProcessWidgetColors.END_EVENT_LINE;
            break;
        default:
            throw new IllegalArgumentException();
        }

        return colorID;
    }

}