/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments.internal.actions;

import java.util.Iterator;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate2;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

import com.tibco.xpd.fragments.IContainedFragmentElement;
import com.tibco.xpd.fragments.IFragmentCategory;
import com.tibco.xpd.fragments.internal.FragmentsViewPart;

/**
 * Base class for actions in the fragments view.
 * 
 * @author njpatel
 * 
 */
public abstract class FragmentActionDelegate implements IViewActionDelegate,
		IActionDelegate2 {

	private IAction action;

	private IStructuredSelection selection;

	private FragmentsViewPart viewPart;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action
	 * .IAction, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		boolean enabled = false;

		if (!selection.isEmpty() && selection instanceof IStructuredSelection) {
			this.selection = (IStructuredSelection) selection;
			enabled = true;
			for (Iterator<?> iter = this.selection.iterator(); iter.hasNext();) {
				if (!(enabled = isEnabled(iter.next()))) {
					break;
				}
			}
		}
		action.setEnabled(enabled);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IViewActionDelegate#init(org.eclipse.ui.IViewPart)
	 */
	public void init(IViewPart view) {
		if (view instanceof FragmentsViewPart) {
			viewPart = (FragmentsViewPart) view;
		}
	}

	/**
	 * Get the fragments view part.
	 * 
	 * @return
	 */
	protected FragmentsViewPart getViewPart() {
		return viewPart;
	}

	/**
	 * Get the current selection.
	 * 
	 * @return
	 */
	protected IStructuredSelection getSelection() {
		return selection;
	}

	/**
	 * Check if the action should be enabled for the given object in the
	 * selection.
	 * 
	 * @param sel
	 *            object in the selection
	 * @return <code>true</code> if this action should be available,
	 *         <code>false</code> otherwise.
	 */
	protected boolean isEnabled(Object sel) {
		boolean enabled = false;
		IFragmentCategory cat = null;

		if (sel instanceof IFragmentCategory) {
			cat = (IFragmentCategory) sel;
		} else if (sel instanceof IContainedFragmentElement) {
			cat = ((IContainedFragmentElement) sel).getParent();
		}

		if (cat != null) {
			enabled = !cat.isSystem();
		}

		return enabled;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IActionDelegate2#init(org.eclipse.jface.action.IAction)
	 */
	public void init(IAction action) {
		this.action = action;
	}

	/**
	 * Get the action.
	 * 
	 * @return <code>IAction</code>.
	 */
	protected IAction getAction() {
		return action;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IActionDelegate2#dispose()
	 */
	public void dispose() {
		// Nothing to do, subclasses may override
	}

	public final void run(IAction action) {
		// Do nothing - runWithEvent will be called instead
	}
}
