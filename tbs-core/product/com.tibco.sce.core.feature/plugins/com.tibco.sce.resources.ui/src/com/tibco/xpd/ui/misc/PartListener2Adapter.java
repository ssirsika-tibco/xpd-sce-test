/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.ui.misc;

import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPartReference;

/**
 * Adapter class for {@link IPartListener2} to facilitate creation of its
 * instances.
 * <p>
 * All method in this implementation are empty.
 * </p>
 *
 * @author jarciuch
 * @since 23 Feb 2015
 */
public class PartListener2Adapter implements IPartListener2 {

    /**
     * {@inheritDoc}
     */
    @Override
    public void partActivated(IWorkbenchPartReference partRef) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void partBroughtToTop(IWorkbenchPartReference partRef) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void partClosed(IWorkbenchPartReference partRef) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void partDeactivated(IWorkbenchPartReference partRef) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void partOpened(IWorkbenchPartReference partRef) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void partHidden(IWorkbenchPartReference partRef) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void partVisible(IWorkbenchPartReference partRef) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void partInputChanged(IWorkbenchPartReference partRef) {
    }
}
