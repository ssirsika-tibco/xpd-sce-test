package com.tibco.xpd.xpdl2.util.ocl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;

/**
 * Listener of the OclBasedNotifier.
 * 
 * @author wzurek
 */
public interface OclQueryListener {
    /**
     * Notify about the event in the path to the target of the OCL query.
     * 
     * @param base
     *            main notifier
     * @param target
     *            the object that issue the notification
     * @param notification
     *            oryginal notification
     */
    void oclNotifyChanged(EObject base, Object target,
            Notification notification);
}