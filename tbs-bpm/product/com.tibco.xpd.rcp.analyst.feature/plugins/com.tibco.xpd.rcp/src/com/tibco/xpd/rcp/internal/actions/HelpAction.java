/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.actions;

import java.util.List;

import org.eclipse.core.expressions.EvaluationContext;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.menus.CommandContributionItem;

import com.hexapixel.widgets.ribbon.AbstractRibbonGroupItem;
import com.tibco.xpd.core.help.internal.DefineHelpCommands;
import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.RCPImages;
import com.tibco.xpd.rcp.internal.Messages;

/**
 * The import action to import a resource into the project.
 * 
 * @author njpatel
 * 
 */
public class HelpAction extends Action implements IRibbonGroupItemAction {

    private final IWorkbenchWindow window;

    private AbstractRibbonGroupItem ribbonItem;

    public HelpAction(IWorkbenchWindow workbenchWindow) {
        super(Messages.HelpAction_helpAction_label);
        this.window = workbenchWindow;
        setId("helpContent"); //$NON-NLS-1$
        setToolTipText(Messages.HelpAction_helpAction_tooltip);
        setImageDescriptor(RCPActivator.getImageDescriptor(RCPImages.HELP
                .getPath()));
        setDisabledImageDescriptor(RCPActivator
                .getImageDescriptor(RCPImages.HELP.getDisabledPath()));
        setEnabled(true);
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
        DefineHelpCommands dhc = new DefineHelpCommands();
        dhc.createCommands(window);
        List<CommandContributionItem> cis =
                dhc.getProductHelpContributionItems(window);

        addMenuItems(menu, cis);
        new MenuItem(menu, SWT.SEPARATOR);
        createMenuItem(menu,
                dhc.getHelpDownloadLinkContributionItem(window,
                        ShowPreferencesAction.getDisplayedPreferencePagesIds()),
                SWT.NONE);
    }

    /**
     * @param menu
     */
    private void addMenuItems(Menu menu, List<CommandContributionItem> ccis) {
        for (CommandContributionItem commandContributionItem : ccis) {
            createMenuItem(menu, commandContributionItem, SWT.NONE);
        }
    }

    private MenuItem createMenuItem(Menu menu,
            final CommandContributionItem contirbItem, int style) {
        final MenuItem item = new MenuItem(menu, style);
        item.setText(contirbItem.getData().label);
        item.setEnabled(contirbItem.isEnabled());
        Image img = getImage(contirbItem);
        if (img != null) {
            item.setImage(img);
        }

        item.addSelectionListener(new SelectionAdapter() {
            /**
             * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
             * 
             * @param e
             */
            @Override
            public void widgetSelected(SelectionEvent e) {
                // Run the action
                try {
                    EvaluationContext ctx = new EvaluationContext(null, window);
                    ctx.addVariable(ISources.ACTIVE_WORKBENCH_WINDOW_NAME,
                            window);
                    contirbItem.getCommand().executeWithChecks(item, ctx);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        return item;
    }

    /**
     * Get the action's image.
     * 
     * @param contirbItem
     * @return
     */
    private Image getImage(CommandContributionItem contirbItem) {
        Image img = null;
        ImageDescriptor descriptor = contirbItem.getData().icon;
        if (descriptor != null) {
            img =
                    RCPActivator.getDefault().getImageRegistry()
                            .get(contirbItem.getId());

            if (img == null) {
                // Create and register the image
                img = descriptor.createImage();
                if (img != null) {
                    RCPActivator.getDefault().getImageRegistry()
                            .put(contirbItem.getId(), img);
                }
            }
        }

        return img;
    }

}
