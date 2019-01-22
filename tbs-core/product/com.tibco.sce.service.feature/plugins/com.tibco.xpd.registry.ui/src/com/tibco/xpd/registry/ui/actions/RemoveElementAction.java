/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.registry.ui.actions;

import java.util.Iterator;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

/**
 * Actions of this type are intend to remove elements form selection.
 * <p>
 * <i>Created: 28 Jun 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public abstract class RemoveElementAction extends Action {

    /**
     * Returns the type of elements this action can remove.
     * 
     * @return class representing the type of the elements which can be deleted
     *         by the action.
     */
    protected abstract Class<?> getElementType();

    /**
     * Verifies if current selection contains only elements which can be removed
     * by this action.
     * 
     * @param selection
     *            the selection to test.
     * @return true if selection can be removed by action.
     */
    @SuppressWarnings("unchecked") //$NON-NLS-1$
    protected boolean canRemoveSelection(ISelection selection) {
        if (selection instanceof IStructuredSelection) {
            if (selection.isEmpty()) {
                return false;
            }
            IStructuredSelection structuredSelection = (IStructuredSelection) selection;
            for (Iterator iterator = structuredSelection.iterator(); iterator
                    .hasNext();) {
                Object element = iterator.next();
                if (!getElementType().isAssignableFrom(element.getClass())) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
