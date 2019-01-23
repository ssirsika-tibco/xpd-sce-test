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

import com.tibco.xpd.processwidget.internal.Messages;

public final class ActivityMarkerType {

    public static final int MARKER_LOOP = 0;

    public static final int MARKER_MULTIPLE = 1;

    public static final int MARKER_AD_HOC = 2;

    public static final int MARKER_COMPENSATION = 3;

    public static final ActivityMarkerType MARKER_LOOP_LITERAL = new ActivityMarkerType(
            MARKER_LOOP);

    public static final ActivityMarkerType MARKER_MULTIPLE_LITERAL = new ActivityMarkerType(
            MARKER_MULTIPLE);

    public static final ActivityMarkerType MARKER_AD_HOC_LITERAL = new ActivityMarkerType(
            MARKER_AD_HOC);

    public static final ActivityMarkerType MARKER_COMPENSATION_LITERAL = new ActivityMarkerType(
            MARKER_COMPENSATION);

    public static final ActivityMarkerType[] types = new ActivityMarkerType[] {
            MARKER_LOOP_LITERAL, MARKER_MULTIPLE_LITERAL, MARKER_AD_HOC_LITERAL,
            MARKER_COMPENSATION_LITERAL };

    private final int type;

    private final String name;

    private ActivityMarkerType(int type) {
        this.type = type;
        switch (type) {
        case MARKER_LOOP:
            name = Messages.ActivityMarkerType_Loop_label;
            break;
        case MARKER_COMPENSATION:
            name = Messages.ActivityMarkerType_Compensation_label;
            break;
        case MARKER_AD_HOC:
            name = Messages.ActivityMarkerType_AdHoc_label;
            break;
        case MARKER_MULTIPLE:
            name = Messages.ActivityMarkerType_Multiple_label;
            break;
        default:
            throw new IllegalArgumentException();
        }
    }

    public int getValue() {
        return type;
    }

    public String toString() {
        return name;
    }

    public static ActivityMarkerType getType(int typeId) {
        return types[typeId];
    }
}