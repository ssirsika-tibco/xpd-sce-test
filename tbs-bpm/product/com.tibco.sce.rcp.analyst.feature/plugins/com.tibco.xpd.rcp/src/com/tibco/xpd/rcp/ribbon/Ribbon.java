/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.ribbon;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.services.IDisposable;

import com.hexapixel.widgets.ribbon.QuickAccessShellToolbar;
import com.hexapixel.widgets.ribbon.RibbonButton;
import com.hexapixel.widgets.ribbon.RibbonButtonGroup;
import com.hexapixel.widgets.ribbon.RibbonCanvas;
import com.hexapixel.widgets.ribbon.RibbonGroup;
import com.hexapixel.widgets.ribbon.RibbonTab;
import com.hexapixel.widgets.ribbon.RibbonTooltip;
import com.tibco.xpd.fragments.FragmentsActivator;
import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.RCPImages;
import com.tibco.xpd.rcp.internal.IOpenResourceListener;
import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.rcp.internal.RibbonExtHelper;
import com.tibco.xpd.rcp.internal.RibbonExtHelper.Tab;
import com.tibco.xpd.rcp.internal.RibbonExtHelper.TabGroup;
import com.tibco.xpd.rcp.internal.actions.AboutAction;
import com.tibco.xpd.rcp.internal.actions.ExitAction;
import com.tibco.xpd.rcp.internal.actions.NewApplicationAction;
import com.tibco.xpd.rcp.internal.actions.OpenFileAction;
import com.tibco.xpd.rcp.internal.actions.OpenFolderAction;
import com.tibco.xpd.rcp.internal.actions.OpenProjectFromSVNURLAction;
import com.tibco.xpd.rcp.internal.actions.RefreshWorkspaceAction;
import com.tibco.xpd.rcp.internal.actions.SaveAllAction;
import com.tibco.xpd.rcp.internal.actions.saveas.SaveAsExtFolderAction;
import com.tibco.xpd.rcp.internal.actions.saveas.SaveAsMaaAction;
import com.tibco.xpd.rcp.internal.resources.ExtFolderResource;
import com.tibco.xpd.rcp.internal.resources.IRCPContainer;
import com.tibco.xpd.rcp.internal.resources.IRCPResource;
import com.tibco.xpd.rcp.internal.resources.MAAResource;
import com.tibco.xpd.rcp.internal.resources.RCPResourceChangeListener;
import com.tibco.xpd.rcp.internal.resources.RCPResourceManager;
import com.tibco.xpd.rcp.ribbon.action.AbstractRibbonAction;
import com.tibco.xpd.rcp.ribbon.action.RibbonQuickAccessToolbarAction;
import com.tibco.xpd.ui.actions.ShowViewAction;

/**
 * The Reloaded Ribbon.
 * 
 * @author njpatel
 * 
 */
public final class Ribbon implements IDisposable {

    private static final ImageRegistry IMAGE_REGISTRY =
            RCPActivator.getDefault().getImageRegistry();

    private RibbonCanvas ribbonCanvas;

    private final Map<String, RibbonTab> tabs;

    private final Map<String, TabGroup> tabGroups;

    private final Map<String, RibbonGroup> groups;

    private final Map<String, RibbonButtonGroup> buttonGroups;

    private final Map<String, AbstractRibbonAction> actions;

    private final IWorkbenchWindow window;

    private final Map<ImageDescriptor, Image> images;

    public Ribbon(IWorkbenchWindow window) {
        this.window = window;
        tabs = new HashMap<String, RibbonTab>();
        tabGroups = new HashMap<String, TabGroup>();
        groups = new HashMap<String, RibbonGroup>();
        buttonGroups = new HashMap<String, RibbonButtonGroup>();
        actions = new HashMap<String, AbstractRibbonAction>();
        images = new HashMap<ImageDescriptor, Image>();
    }

    /**
     * Create a Ribbon on the given Composite.
     * 
     * @param parent
     * @return
     */
    public RibbonCanvas createRibbon(final Composite parent) {
        if (parent != null) {
            ribbonCanvas = new RibbonCanvas(parent);
            ribbonCanvas.setButtonImage(
                    IMAGE_REGISTRY.get(RCPImages.TIBCO32.getPath()));

            /*
             * Sid XPD-8302. Should never set the layout data for the child of a
             * parent we do not own and therefore set the layout for (e.g. we
             * don't know what layout has been set on the parent here
             */
            // ribbonCanvas.setLayoutData(
            // new GridData(SWT.FILL, SWT.FILL, true, false));

            init(ribbonCanvas);
            parent.addPaintListener(new PaintListener() {

                @Override
                public void paintControl(PaintEvent e) {
                    ribbonCanvas.redraw();
                }

            });

            return ribbonCanvas;
        }
        return null;
    }

    /**
     * Create all groups/actions in the ribbon.
     * 
     * @param ribbon
     */
    private void init(RibbonCanvas ribbon) {
        RibbonExtHelper helper = new RibbonExtHelper();
        initQuickAccessToolbar(ribbon);

        for (Tab tab : helper.getTabs()) {
            RibbonTab rTab = createTab(ribbon, tab.getId(), tab.getLabel());
            Collection<TabGroup> grs = helper.getGroups(tab.getId());
            if (grs != null) {
                for (TabGroup gr : grs) {
                    try {
                        AbstractRibbonGroup clazz = gr.getRibbonGroupClass();
                        clazz.create(window, rTab, gr.getLabel());
                    } catch (CoreException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Add a menu item to the given menu that will run the action given. The
     * text and image will be taken from the action.
     * 
     * @param menu
     * @param action
     */
    private void addMenuItem(Menu menu, final IAction action) {
        final MenuItem item = new MenuItem(menu, SWT.CASCADE);
        item.setText(action.getText());
        item.setImage(getImage(action.getImageDescriptor()));
        item.setEnabled(action.isEnabled());
        // Hook the menu item to the action
        item.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (action.isEnabled()) {
                    action.run();
                }
            }
        });
        // Update menu item if action enablement changes
        action.addPropertyChangeListener(new IPropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent event) {
                if (IAction.ENABLED.equals(event.getProperty())
                        && !item.isDisposed()) {
                    item.getDisplay().asyncExec(new Runnable() {

                        @Override
                        public void run() {
                            if (item != null && !item.isDisposed()) {
                                item.setEnabled(action.isEnabled());
                            }
                        }
                    });
                }
            }
        });
    }

    /**
     * Get an image for the given image descriptor. This will check the local
     * cache for the image and if not found create one and add it to the cache
     * so that it can be disposed later.
     * 
     * @param imgDescriptor
     * @return
     */
    private Image getImage(ImageDescriptor imgDescriptor) {
        Image img = null;
        if (imgDescriptor != null) {
            img = images.get(imgDescriptor);
            if (img == null) {
                img = imgDescriptor.createImage();
                images.put(imgDescriptor, img);
            }
        }
        return img;
    }

    /**
     * Initialize the quick-access toolbar.
     * 
     * @param ribbon
     */
    private void initQuickAccessToolbar(final RibbonCanvas ribbon) {

        QuickAccessShellToolbar toolbar = ribbon.getToolbar();

        // New maa application
        NewApplicationAction newAction = new NewApplicationAction(window);
        createAction(toolbar, newAction);

        // Open maa file
        OpenFileAction openFileAction = new OpenFileAction(window);
        createAction(toolbar, openFileAction);

        // Save all
        SaveAllAction saveAllAction = new SaveAllAction(window);
        createAction(toolbar, saveAllAction);

        OpenFolderAction openFolderAction = new OpenFolderAction(window);
        OpenProjectFromSVNURLAction openSVNFolderAction =
                new OpenProjectFromSVNURLAction(window);

        /*
         * Update the big button menu
         */
        Menu menu = ribbon.getBigButtonMenu();

        // Add the actions
        addMenuItem(menu, newAction);
        addMenuItem(menu, openFileAction);
        addMenuItem(menu, openFolderAction);
        addMenuItem(menu, openSVNFolderAction);
        addMenuItem(menu, saveAllAction);

        // Add save as option
        configureSaveAs(menu);

        // Add print option
        new MenuItem(menu, SWT.SEPARATOR);
        IAction printAction =
                RCPActivator.getGlobalAction(RibbonConsts.ACTION_PRINT.getId());
        printAction.setText(Messages.Ribbon_print_action);
        addMenuItem(menu, printAction);

        new MenuItem(menu, SWT.SEPARATOR);

        /*
         * Add refresh workspace action
         */
        addMenuItem(menu, new RefreshWorkspaceAction(window));

        /*
         * Add the show view's submenu
         */
        MenuItem item = new MenuItem(menu, SWT.CASCADE);
        item.setText(Messages.Ribbon_showView_menu);

        Menu viewMenu = new Menu(menu);
        addMenuItem(viewMenu,
                new ShowViewAction(IPageLayout.ID_PROP_SHEET,
                        Messages.Ribbon_view_properties_menu,
                        Messages.Ribbon_view_properties_menu_tooltip));
        addMenuItem(viewMenu,
                new ShowViewAction(IPageLayout.ID_PROBLEM_VIEW,
                        Messages.Ribbon_view_problem_menu,
                        Messages.Ribbon_view_problem_menu_tooltip));
        addMenuItem(viewMenu,
                new ShowViewAction(FragmentsActivator.VIEW_ID,
                        Messages.Ribbon_view_fragments_menu,
                        Messages.Ribbon_view_fragments_menu_tooltip));
        item.setMenu(viewMenu);

        // Add About dialog
        new MenuItem(menu, SWT.SEPARATOR);
        addMenuItem(menu, new AboutAction(window));

        /*
         * Add exit option to the big button menu
         */
        new MenuItem(menu, SWT.SEPARATOR);
        addMenuItem(menu, new ExitAction(window));

        // Enable the big button menu
        ribbon.addBigButtonListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                ribbon.showBigButtonMenu();
                ribbon.redraw();
            }
        });
    }

    /**
     * Configure the Save As option.
     * 
     * @param menu
     */
    private void configureSaveAs(final Menu parentMenu) {
        final MenuItem saveAsMenuItem = new MenuItem(parentMenu, SWT.CASCADE);
        saveAsMenuItem.setText("Save As");
        Menu saveAsMenu = new Menu(parentMenu);
        saveAsMenuItem.setMenu(saveAsMenu);
        saveAsMenuItem.setEnabled(false);

        /*
         * Update the save as options when a resource is opened. If an MAA
         * resource is opened then this will provide 2 options - save as MAA and
         * save as external folder. If External folder is opened then only save
         * as MAA option will be available.
         */
        RCPResourceManager.addOpenListener(
                new ResourceListener(parentMenu, saveAsMenuItem));

    }

    /**
     * Create a tab in the Ribbon.
     * 
     * @param ribbon
     * @param id
     * @param label
     * @return
     */
    private RibbonTab createTab(RibbonCanvas ribbon, String id, String label) {
        RibbonTab tab = null;
        if (id != null && label != null) {
            tab = new RibbonTab(ribbon.getRibbonTabFolder(), label);
            tabs.put(id, tab);
        }
        return tab;
    }

    /**
     * Create an action in the quick-access toolbar.
     * 
     * @param toolbar
     * @param action
     * @return
     */
    private AbstractRibbonAction createAction(QuickAccessShellToolbar toolbar,
            IAction action) {
        return createAction(action.getId(),
                toolbar,
                action,
                action.getText(),
                getImage(action.getImageDescriptor()),
                getImage(action.getDisabledImageDescriptor()));
    }

    /**
     * Create an action in the quick-access toolbar.
     * 
     * @param id
     * @param toolbar
     * @param action
     * @param label
     * @param activeImage
     * @param disabledImage
     * @return
     */
    private AbstractRibbonAction createAction(String id,
            QuickAccessShellToolbar toolbar, IAction action, String label,
            Image activeImage, Image disabledImage) {

        RibbonQuickAccessToolbarAction toolbarAction = null;

        if (id != null && toolbar != null && action != null && label != null
                && activeImage != null) {
            toolbarAction = new RibbonQuickAccessToolbarAction(toolbar, action,
                    label, activeImage, disabledImage,
                    RibbonButton.STYLE_NO_DEPRESS, true);
            toolbarAction.getButton().setToolTip(
                    new RibbonTooltip(label, action.getToolTipText()));
            actions.put(id, toolbarAction);
        }

        return toolbarAction;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.services.IDisposable#dispose()
     */
    @Override
    public void dispose() {
        for (Image img : images.values()) {
            img.dispose();
        }
        images.clear();

        if (ribbonCanvas != null) {
            for (AbstractRibbonAction action : actions.values()) {
                action.dispose();
            }
            actions.clear();

            for (RibbonButtonGroup grp : buttonGroups.values()) {
                grp.dispose();
            }
            buttonGroups.clear();

            for (RibbonGroup grp : groups.values()) {
                grp.dispose();
            }
            groups.clear();

            for (TabGroup grp : tabGroups.values()) {
                try {
                    grp.getRibbonGroupClass().dispose();
                } catch (CoreException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            for (RibbonTab tab : tabs.values()) {
                tab.dispose();
            }
            tabs.clear();

            ribbonCanvas.dispose();
        }
    }

    /**
     * Listens to changes in the resource. This will update the Save As options
     * accordingly.
     * 
     * @author njpatel
     */
    private class ResourceListener implements IOpenResourceListener {

        private final Menu parentMenu;

        private final MenuItem saveAsMenuItem;

        /**
         * 
         */
        public ResourceListener(Menu parentMenu, MenuItem saveAsMenuItem) {
            this.parentMenu = parentMenu;
            this.saveAsMenuItem = saveAsMenuItem;
        }

        @Override
        public void opened(IRCPContainer resource) {
            updateMenu(resource);
            resource.addChangeListener(new RCPResourceChangeListener() {

                @Override
                public void resourceChanged(final IRCPResource resource,
                        RCPResourceChangeEvent event) {
                    if (event.eventType == RCPResourceEventType.DIRTY
                            && !resource.isDirty()) {
                        /*
                         * Enable the Save As options when a new MAA has been
                         * saved.
                         */

                        /*
                         * Sid XPD-8302 we can get notification of resource
                         * changed on background thread (during close window we
                         * save the MAA file in WorkspaceModifyOperation) so we
                         * MUST make sure we do this on display thread.
                         */
                        Display.getDefault().asyncExec(new Runnable() {

                            @Override
                            public void run() {
                                updateMenu(resource);
                            }
                        });

                    }
                }
            });
        }

        /**
         * Update the Save As menu options.
         * 
         * @param resource
         */
        private void updateMenu(IRCPResource resource) {
            // Clear previous options
            /*
             * Sid XPD-8302 - belt-n-braces! we could be called during shutdown
             * of UI.
             */
            if (saveAsMenuItem.isDisposed()) {
                return;
            }

            Menu prevMenu = saveAsMenuItem.getMenu();
            if (prevMenu != null) {
                prevMenu.dispose();
            }

            Menu saveAsMenu = new Menu(parentMenu);

            if (resource.getPath() != null && (resource instanceof MAAResource
                    || resource instanceof ExtFolderResource)) {
                addMenuItem(saveAsMenu,
                        new SaveAsMaaAction(saveAsMenu.getShell()));
            }

            if (resource.getPath() != null && resource instanceof MAAResource) {
                addMenuItem(saveAsMenu,
                        new SaveAsExtFolderAction(saveAsMenu.getShell()));
            }

            saveAsMenuItem.setMenu(saveAsMenu);
            saveAsMenuItem.setEnabled(saveAsMenu.getItemCount() > 0);
        }

    }

}
