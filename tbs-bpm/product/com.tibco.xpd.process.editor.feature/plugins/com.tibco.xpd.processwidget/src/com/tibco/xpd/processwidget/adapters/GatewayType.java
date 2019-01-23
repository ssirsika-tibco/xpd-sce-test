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

import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ProcessEditorObjectType;
import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.internal.Messages;

public final class GatewayType {
    public static final int EXLCUSIVE_DATA = 0;

    public static final int EXCLUSIVE_EVENT = 1;

    public static final int INCLUSIVE = 2;

    public static final int COMPLEX = 3;

    public static final int PARALLEL = 4;

    public static final GatewayType EXCLUSIVE_DATA_LITERAL = new GatewayType(
            EXLCUSIVE_DATA);

    public static final GatewayType EXLCUSIVE_EVENT_LITERAL = new GatewayType(
            EXCLUSIVE_EVENT);

    public static final GatewayType INCLUSIVE_LITERAL = new GatewayType(
            INCLUSIVE);

    public static final GatewayType COMPLEX_LITERAL = new GatewayType(COMPLEX);

    public static final GatewayType PARALLEL_LITERAL =
            new GatewayType(PARALLEL);

    public static final GatewayType[] types = new GatewayType[] {
            EXCLUSIVE_DATA_LITERAL, EXLCUSIVE_EVENT_LITERAL, INCLUSIVE_LITERAL,
            COMPLEX_LITERAL, PARALLEL_LITERAL };

    private final int type;

    private final String name;

    private GatewayType(int type) {
        this.type = type;
        switch (type) {
        case EXLCUSIVE_DATA:
            name = Messages.GatewayType_ExclusiveData_label;
            break;
        case EXCLUSIVE_EVENT:
            name = Messages.GatewayType_ExclusiveEvent_label;
            break;
        case INCLUSIVE:
            name = Messages.GatewayType_Inclusive_label;
            break;
        case COMPLEX:
            name = Messages.GatewayType_Complex_label;
            break;
        case PARALLEL:
            name = Messages.GatewayType_Parallel_label;
            break;
        default:
            throw new IllegalArgumentException();
        }
    }

    public int getValue() {
        return type;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Returns the ProcessWidgetColors.ID used as the default FILL Color for
     * this gateway type.
     * 
     * @return
     */
    public String getGetDefaultFillColorId() {
        String colorID = null;

        switch (getValue()) {
        case GatewayType.COMPLEX:
            colorID = ProcessWidgetColors.COMPLEX_GATEWAY_FILL;
            break;
        case GatewayType.INCLUSIVE:
            colorID = ProcessWidgetColors.INCLUSIVE_GATEWAY_FILL;
            break;
        case GatewayType.PARALLEL:
            colorID = ProcessWidgetColors.PARALLEL_GATEWAY_FILL;
            break;
        case GatewayType.EXCLUSIVE_EVENT:
            colorID = ProcessWidgetColors.EXCLUSIVE_EVENT_GATEWAY_FILL;
            break;
        case GatewayType.EXLCUSIVE_DATA:
        default:
            colorID = ProcessWidgetColors.EXCLUSIVE_DATA_GATEWAY_FILL;
            break;
        }

        return colorID;
    }

    /**
     * Returns the ProcessWidgetColors.ID used as the default FILL Color for
     * this task type.
     * 
     * @return
     */
    public String getGetDefaultLineColorId() {
        String colorID = null;

        switch (getValue()) {
        case GatewayType.COMPLEX:
            colorID = ProcessWidgetColors.COMPLEX_GATEWAY_LINE;
            break;
        case GatewayType.INCLUSIVE:
            colorID = ProcessWidgetColors.OR_GATEWAY_LINE;
            break;
        case GatewayType.PARALLEL:
            colorID = ProcessWidgetColors.AND_GATEWAY_LINE;
            break;
        case GatewayType.EXCLUSIVE_EVENT:
            colorID = ProcessWidgetColors.XOREVENT_GATEWAY_LINE;
            break;
        case GatewayType.EXLCUSIVE_DATA:
        default:
            colorID = ProcessWidgetColors.XORDATA_GATEWAY_LINE;
            break;
        }

        return colorID;
    }

    /**
     * @return process editor object type
     */
    public ProcessEditorObjectType getProcessEditorObjectType() {
        ProcessEditorObjectType objectType = null;

        switch (getValue()) {
        case GatewayType.COMPLEX:
            objectType = ProcessEditorObjectType.gateway_complex;
            break;
        case GatewayType.INCLUSIVE:
            objectType = ProcessEditorObjectType.gateway_inclusive;
            break;
        case GatewayType.PARALLEL:
            objectType = ProcessEditorObjectType.gateway_parallel;
            break;
        case GatewayType.EXCLUSIVE_EVENT:
            objectType = ProcessEditorObjectType.gateway_exclusive_event_based;
            break;
        case GatewayType.EXLCUSIVE_DATA:
            objectType = ProcessEditorObjectType.gateway_exclusive_data_based;
            break;
        }

        return objectType;
    }

}
