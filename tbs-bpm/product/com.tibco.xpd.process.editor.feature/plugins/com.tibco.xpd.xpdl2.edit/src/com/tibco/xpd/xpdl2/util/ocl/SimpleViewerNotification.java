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

import org.eclipse.emf.common.notify.impl.NotificationImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ViewerNotification;


/**
 * Custom notification for EObject thats tell the listeners to refresh themself.
 * It is used outside (in addition to) normal EMF notifications.
 * 
 * @author wzurek
 */
public class SimpleViewerNotification extends ViewerNotification {

    public SimpleViewerNotification(EObject notifier, boolean contentRefresh,
            boolean labelUpdate) {
        super(
                new NotificationImpl(NotificationsConstants.REFRESH_VISUAL, 0,
                        0), notifier, contentRefresh, labelUpdate);
        this.notifier = notifier;
    }
}
