/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments.internal.actions;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.actions.ActionFactory;

import com.tibco.xpd.fragments.FragmentsActivator;
import com.tibco.xpd.fragments.IFragment;
import com.tibco.xpd.fragments.internal.FragmentsExtensionHelper.FragmentsProvider;
import com.tibco.xpd.fragments.internal.impl.FragmentImpl;

/**
 * Copy fragment element action in the fragments view.
 * 
 * @author njpatel
 * 
 */
public class CopyActionDelegate extends FragmentActionDelegate {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.xpd.fragments.internal.actions.FragmentActionDelegate#init(
	 * org.eclipse.ui.IViewPart)
	 */
	public void init(IViewPart view) {
		IAction action = getAction();

		if (action != null) {
			view.getViewSite().getActionBars().setGlobalActionHandler(
					ActionFactory.COPY.getId(), action);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IActionDelegate2#runWithEvent(org.eclipse.jface.action
	 * .IAction, org.eclipse.swt.widgets.Event)
	 */
	public void runWithEvent(IAction action, Event event) {
		IStructuredSelection selection = getSelection();

		if (!selection.isEmpty()
				&& selection.getFirstElement() instanceof FragmentImpl) {
			FragmentImpl frag = (FragmentImpl) selection.getFirstElement();
			FragmentsProvider provider = frag.getProvider();
			if (provider != null) {
				try {
					provider.getProviderClass().copyToClipboard(frag);
				} catch (CoreException e) {
					FragmentsActivator.getDefault().getLogger().error(e);
				}
			}
		}
	}

	@Override
	protected boolean isEnabled(Object sel) {
		return sel instanceof IFragment;
	}
}
