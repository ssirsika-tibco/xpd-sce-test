/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.validation.provider;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

/**
 * A validation item is passed to an {@link IScopeProvider} to tell the scope
 * provider what objects have changed. This extends <code>IValidationItem</code>
 * to provide a collection of {@link Notification} s that caused the validation
 * to run.
 * 
 * @see IValidationItem
 * 
 * @author njpatel
 * 
 * @since 3.1
 */
public interface IValidationItem2 extends IValidationItem {

    /**
     * Get the collection of <code>Notification</code>s that caused the
     * validation to run.
     * 
     * @return collection of <code>Notification</code>s, <code>null</code> or
     *         empty if none set.
     */
    Collection<Notification> getNotifications();

}
