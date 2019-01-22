/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.actions;

import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;

import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.RCPImages;
import com.tibco.xpd.rcp.internal.IOpenResourceListener;
import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.rcp.internal.OverviewEditorInput;
import com.tibco.xpd.rcp.internal.overview.OverviewEditor;
import com.tibco.xpd.rcp.internal.resources.IRCPContainer;
import com.tibco.xpd.rcp.internal.resources.IRCPResource;
import com.tibco.xpd.rcp.internal.resources.ModelResource;
import com.tibco.xpd.rcp.internal.resources.RCPResourceChangeListener;
import com.tibco.xpd.rcp.internal.resources.RCPResourceManager;

/**
 * Action to show the Overview editor.
 * 
 * @author njpatel
 * 
 */
public class OverviewAction extends RegisterCommandAction implements
        IOpenResourceListener {
    public OverviewAction(IWorkbenchWindow window) {
        super(window, Messages.OverviewAction_title);
        setEnabled(false);
        setImageDescriptor(RCPActivator.getImageDescriptor(RCPImages.TIBCO16
                .getPath()));
        setDisabledImageDescriptor(RCPActivator
                .getImageDescriptor(RCPImages.TIBCO16.getDisabledPath()));
        RCPResourceManager.addOpenListener(this);

        // Register for key-binding
        registerCommand("com.tibco.xpd.rcp.command.overview"); //$NON-NLS-1$
    }

    @Override
    public void run() {
        IRCPContainer resource = RCPResourceManager.getResource();
        if (resource != null && resource.getProjectResources() != null
                && !resource.getProjectResources().isEmpty()) {
            try {
                IDE.openEditor(getWindow().getActivePage(),
                        new OverviewEditorInput(resource),
                        OverviewEditor.ID);
            } catch (PartInitException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public void opened(IRCPContainer resource) {
        // Don't show overview page for model resources
        setEnabled(!(resource instanceof ModelResource));

        resource.addChangeListener(new RCPResourceChangeListener() {

            @Override
            public void resourceChanged(IRCPResource resource,
                    RCPResourceChangeEvent event) {
                if (event.eventType == RCPResourceEventType.DISPOSED) {
                    setEnabled(false);
                }
            }

        });
    }
}