/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.resources.internal.wc;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;

import com.tibco.xpd.resources.WorkingCopy;

/**
 * Adapter for the eResource that works as WorkingCopy provider and
 * EditingDomain provider. Working copies are installing this adapter on
 * eResourcess.
 * 
 * @author wzurek
 */
public class WorkingCopyProviderAdapter implements Adapter,
        IEditingDomainProvider {

    /**
     * 
     */
    private final WorkingCopy edp;

    /**
     * 
     */
    private Notifier target;

    /**
     * @param workingCopy
     */
    public WorkingCopyProviderAdapter(WorkingCopy workingCopy) {
        edp = workingCopy;
    }

    public boolean isAdapterForType(Object type) {
        return type == WorkingCopy.class
                || type == IEditingDomainProvider.class;
    }

    public WorkingCopy getWorkingCopy() {
        return edp;
    }

    public EditingDomain getEditingDomain() {
        return edp.getEditingDomain();
    }

    public Notifier getTarget() {
        return target;
    }

    public void notifyChanged(Notification notification) {
        // nothing to do
    }

    public void setTarget(Notifier newTarget) {
        this.target = newTarget;
    }
}
