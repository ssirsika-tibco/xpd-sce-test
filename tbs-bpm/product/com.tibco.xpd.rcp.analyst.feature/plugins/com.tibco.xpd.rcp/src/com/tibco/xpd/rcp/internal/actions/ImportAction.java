/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IWorkbenchWindow;

import com.hexapixel.widgets.ribbon.AbstractRibbonGroupItem;
import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.RCPImages;
import com.tibco.xpd.rcp.internal.IOpenResourceListener;
import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.rcp.internal.actions.imports.NimbusImportAction;
import com.tibco.xpd.rcp.internal.actions.imports.OtherImportAction;
import com.tibco.xpd.rcp.internal.resources.IRCPContainer;
import com.tibco.xpd.rcp.internal.resources.IRCPResource;
import com.tibco.xpd.rcp.internal.resources.ModelResource;
import com.tibco.xpd.rcp.internal.resources.RCPResourceChangeListener;
import com.tibco.xpd.rcp.internal.resources.RCPResourceManager;

/**
 * The import action to import a resource into the project.
 * 
 * @author njpatel
 * 
 */
public class ImportAction extends Action implements IRibbonGroupItemAction {

    private final IWorkbenchWindow window;

    private AbstractRibbonGroupItem ribbonItem;

    public ImportAction(IWorkbenchWindow workbenchWindow) {
        super(Messages.ImportAction_title);
        this.window = workbenchWindow;
        setId("import"); //$NON-NLS-1$
        setToolTipText(Messages.ImportAction_tooltip);
        setImageDescriptor(RCPActivator.getImageDescriptor(RCPImages.IMPORT
                .getPath()));
        setDisabledImageDescriptor(RCPActivator
                .getImageDescriptor(RCPImages.IMPORT.getDisabledPath()));
        setEnabled(false);
        RCPResourceManager.addOpenListener(new IOpenResourceListener() {
            @Override
            public void opened(IRCPContainer resource) {
                if (resource instanceof ModelResource) {
                    setEnabled(false);
                } else {
                    setEnabled(true);

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
        });
    }

    @Override
    public void run() {
        if (ribbonItem != null) {
            ribbonItem.showMenu();
        }
    }

    /**
     * @see com.tibco.xpd.rcp.internal.actions.IRibbonGroupItemAction#setRibbonGroupItem(AbstractRibbonGroupItem)
     * 
     * @param button
     */
    @Override
    public void setRibbonGroupItem(AbstractRibbonGroupItem ribbonItem) {
        this.ribbonItem = ribbonItem;

        Menu menu = ribbonItem.getMenu();

        addMenuItems(menu);
    }

    /**
     * @param menu
     */
    private void addMenuItems(Menu menu) {
        /*
         * Add Nimbus import
         */
        createMenuItem(menu, new NimbusImportAction(window), SWT.NONE);
        createMenuItem(menu, new OtherImportAction(window), SWT.NONE);
    }

    private MenuItem createMenuItem(Menu menu, final IAction action, int style) {
        final MenuItem item = new MenuItem(menu, style);
        item.setText(action.getText());
        item.setEnabled(action.isEnabled());
        Image img = getImage(action);
        if (img != null) {
            item.setImage(img);
        }

        action.addPropertyChangeListener(new IPropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent event) {
                if (IAction.ENABLED.equals(event.getProperty())) {
                    item.setEnabled(action.isEnabled());
                }

            }
        });

        item.addSelectionListener(new SelectionAdapter() {
            /**
             * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
             * 
             * @param e
             */
            @Override
            public void widgetSelected(SelectionEvent e) {
                // Run the action
                action.run();
            }
        });
        return item;
    }

    /**
     * Get the action's image.
     * 
     * @param action
     * @return
     */
    private Image getImage(IAction action) {
        Image img = null;
        ImageDescriptor descriptor = action.getImageDescriptor();
        if (descriptor != null) {
            img =
                    RCPActivator.getDefault().getImageRegistry()
                            .get(action.getId());

            if (img == null) {
                // Create and register the image
                img = descriptor.createImage();
                if (img != null) {
                    RCPActivator.getDefault().getImageRegistry()
                            .put(action.getId(), img);
                }
            }
        }

        return img;
    }

}
