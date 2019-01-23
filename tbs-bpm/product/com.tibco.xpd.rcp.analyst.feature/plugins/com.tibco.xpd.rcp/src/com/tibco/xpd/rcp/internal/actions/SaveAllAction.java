/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.actions;

import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.rcp.internal.IOpenResourceListener;
import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.rcp.internal.resources.IRCPContainer;
import com.tibco.xpd.rcp.internal.resources.IRCPResource;
import com.tibco.xpd.rcp.internal.resources.RCPResourceChangeListener;
import com.tibco.xpd.rcp.internal.resources.RCPResourceManager;

/**
 * Save all action.
 * 
 * @author njpatel
 */
public class SaveAllAction extends RegisterCommandAction implements
        IOpenResourceListener {

    private IRCPContainer resource;

    private final ResourceListener listener;

    public SaveAllAction(IWorkbenchWindow window) {
        super(window, Messages.SaveAllAction_title);
        setId("saveAll"); //$NON-NLS-1$
        setToolTipText(Messages.SaveAllAction_tooltip);
        setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
                .getImageDescriptor(ISharedImages.IMG_ETOOL_SAVE_EDIT));
        setDisabledImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
                .getImageDescriptor(ISharedImages.IMG_ETOOL_SAVE_EDIT_DISABLED));
        setEnabled(false);
        listener = new ResourceListener();
        RCPResourceManager.addOpenListener(this);

        // Register for key-binding
        registerCommand("com.tibco.xpd.rcp.command.saveAll"); //$NON-NLS-1$
    }

    @Override
    public void run() {
        if (resource != null) {
            final Shell shell = getWindow().getShell();
            BusyIndicator.showWhile(shell.getDisplay(), new Runnable() {
                @Override
                public void run() {
                    try {
                        ResourcesPlugin.getWorkspace()
                                .run(new IWorkspaceRunnable() {
                                    @Override
                                    public void run(IProgressMonitor monitor)
                                            throws CoreException {
                                        resource.save(new NullProgressMonitor());
                                    }
                                },
                                        null);
                    } catch (CoreException e1) {
                        ErrorDialog
                                .openError(shell,
                                        Messages.SaveAllAction_errorDialog_title,
                                        Messages.SaveAllAction_problemSaving_error_message,
                                        e1.getStatus());
                    }
                }
            });
        }
    }

    @Override
    public void opened(IRCPContainer resource) {
        if (resource != null) {
            this.resource = resource;

            if (resource.isDirty()) {
                setEnabled(true);
            }

            resource.addChangeListener(listener);
        }
    }

    private class ResourceListener implements RCPResourceChangeListener {

        @Override
        public void resourceChanged(IRCPResource resource,
                RCPResourceChangeEvent event) {
            if (event.eventType == RCPResourceEventType.DISPOSED) {
                SaveAllAction.this.resource = null;
            }
            // If dirty, changed or disposed then update action state
            getWindow().getShell().getDisplay().asyncExec(new Runnable() {
                @Override
                public void run() {
                    updateState();
                }
            });
        }
    }

    /**
     * Update the state of this action. If the resource being edited is dirty,
     * or any open editors are dirty then this will enable this action.
     */
    private void updateState() {
        setEnabled(resource != null && resource.isDirty());
    }
}
