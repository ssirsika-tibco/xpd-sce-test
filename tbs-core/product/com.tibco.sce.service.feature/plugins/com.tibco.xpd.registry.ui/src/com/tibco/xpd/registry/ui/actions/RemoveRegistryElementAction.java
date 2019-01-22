/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.registry.ui.actions;

import java.util.Collection;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.IWorkbenchActionDefinitionIds;

import com.tibco.xpd.registry.ui.Activator;
import com.tibco.xpd.registry.ui.internal.Messages;

/**
 * General element removal action "Registries" view.
 * <p>
 * <i>Created: 27 Jun 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class RemoveRegistryElementAction extends Action implements
        ISelectionChangedListener {

    /**
     * The id of this action.
     */
    public static final String ID = Activator.PLUGIN_ID
            + ".RemoveRegistryElementAction";//$NON-NLS-1$

    private final Collection<RemoveElementAction> subactions;

    private RemoveElementAction currentSubaction;

    /**
     * @param shell
     *            The shell to associate the wizard with.
     * @param provider
     *            The selection provider.
     */
    public RemoveRegistryElementAction(
            Collection<RemoveElementAction> subactions) {
        super();
        setId(ID);

        setText(Messages.RemoveRegistryElementAction_label);
        setToolTipText(Messages.RemoveRegistryElementAction_tooltip);

        ISharedImages images = PlatformUI.getWorkbench().getSharedImages();
        setDisabledImageDescriptor(images
                .getImageDescriptor(ISharedImages.IMG_TOOL_DELETE_DISABLED));
        setImageDescriptor(images
                .getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
        setActionDefinitionId(IWorkbenchActionDefinitionIds.DELETE);
        setEnabled(false);
        this.subactions = subactions;
    }

    /**
     * @see org.eclipse.jface.action.Action#run()
     */
    @Override
    public void run() {
        Assert.isNotNull(currentSubaction,
                "The run() method should not be invoked " + //$NON-NLS-1$
                        "before currentSubaction is set."); //$NON-NLS-1$
        currentSubaction.run();
    }

    /*
     * Enables this action and select the current sub action depending on
     * selection.
     * 
     * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
     */
    public void selectionChanged(SelectionChangedEvent event) {
        currentSubaction = null;
        boolean shouldBeEnabled = false;
        ISelection selection = event.getSelection();
        for (RemoveElementAction action : subactions) {
            if (action.canRemoveSelection(selection)) {
                if (currentSubaction == null) {
                    shouldBeEnabled = true;
                    currentSubaction = action;
                } else {
                    shouldBeEnabled = false;
                    break;
                }
            }
        }
        setEnabled(shouldBeEnabled);
    }
}
