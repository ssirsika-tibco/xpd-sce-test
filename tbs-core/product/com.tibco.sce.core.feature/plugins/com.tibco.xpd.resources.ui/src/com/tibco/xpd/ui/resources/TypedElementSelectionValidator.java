/*
 * Copyright (c) TIBCO Software Inc. 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.resources;

import java.util.Collection;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.util.Assert;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;


/**
 * Implementation of a <code>ISelectionValidator</code> to validate the type
 * of an element. Empty selections are not accepted.
 * 
 * Source of this class is based on TypedElementSelectionValidator class from 
 * Eclipse JDT plugin.
 */
public class TypedElementSelectionValidator implements
        ISelectionStatusValidator {

    private IStatus errorStatus = new StatusInfo(IStatus.ERROR, ""); //$NON-NLS-1$

    private IStatus okStatus = new StatusInfo();

    private Class[] acceptedTypes;

    private boolean allowMultipleSelection;

    private Collection rejectedElements;

    /**
     * @param acceptedTypes
     *            The types accepted by the validator
     * @param allowMultipleSelection
     *            If set to <code>true</code>, the validator allows multiple
     *            selection.
     */
    public TypedElementSelectionValidator(Class[] acceptedTypes,
            boolean allowMultipleSelection) {
        this(acceptedTypes, allowMultipleSelection, null);
    }

    /**
     * @param acceptedTypes
     *            The types accepted by the validator
     * @param allowMultipleSelection
     *            If set to <code>true</code>, the validator allows multiple
     *            selection.
     * @param rejectedElements
     *            A list of elements that are not accepted
     */
    public TypedElementSelectionValidator(Class[] acceptedTypes,
            boolean allowMultipleSelection, Collection rejectedElements) {
        Assert.isNotNull(acceptedTypes);
        this.acceptedTypes = acceptedTypes;
        this.allowMultipleSelection = allowMultipleSelection;
        this.rejectedElements = rejectedElements;
    }

    /*
     * @see org.eclipse.ui.dialogs.ISelectionValidator#isValid(java.lang.Object)
     */
    public IStatus validate(Object[] elements) {
        if (isValid(elements)) {
            return okStatus;
        }
        return errorStatus;
    }

    private boolean isOfAcceptedType(Object o) {
        for (int i = 0; i < acceptedTypes.length; i++) {
            if (acceptedTypes[i].isInstance(o)) {
                return true;
            }
        }
        return false;
    }

    private boolean isRejectedElement(Object elem) {
        return (rejectedElements != null) && rejectedElements.contains(elem);
    }

    private boolean isValid(Object[] selection) {
        if (selection.length == 0) {
            return false;
        }

        if (!allowMultipleSelection && selection.length != 1) {
            return false;
        }

        for (int i = 0; i < selection.length; i++) {
            Object o = selection[i];
            if (!isOfAcceptedType(o) || isRejectedElement(o)) {
                return false;
            }
        }
        return true;
    }
}