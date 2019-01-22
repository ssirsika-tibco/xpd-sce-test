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

package com.tibco.xpd.xpdl2.util.ocl;

import org.eclipse.emf.common.notify.Notification;

/**
 * @author wzurek
 */
public final class NotificationsConstants {

    private static final int OFFSET = 342;

    // Some simple visual aspaect has changed.
    public static final int REFRESH_VISUAL = 1 + OFFSET
            + Notification.EVENT_TYPE_COUNT;

    // Some aspect of one element referenced from another has changed
    // (such as connection source/target change, Activity/Artifact 
    //  put into lane / activity set).
    public static final int REFRESH_REFERENCED_ELEMENT = 2 + OFFSET
            + Notification.EVENT_TYPE_COUNT;

    private NotificationsConstants() {
    }
}
