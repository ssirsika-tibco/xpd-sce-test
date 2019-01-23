/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.ribbon.groups;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import com.hexapixel.widgets.ribbon.RibbonButton;
import com.hexapixel.widgets.ribbon.RibbonGroup;
import com.hexapixel.widgets.ribbon.RibbonGroupSeparator;
import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.RCPImages;
import com.tibco.xpd.rcp.internal.IOpenResourceListener;
import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.rcp.internal.models.BOMModelProvider;
import com.tibco.xpd.rcp.internal.models.OMModelProvider;
import com.tibco.xpd.rcp.internal.models.ProcessModelProvider;
import com.tibco.xpd.rcp.internal.models.actions.ModelAction;
import com.tibco.xpd.rcp.internal.models.actions.NewBOMAction;
import com.tibco.xpd.rcp.internal.models.actions.NewOrganizationAction;
import com.tibco.xpd.rcp.internal.models.actions.NewOrganizationModelAction;
import com.tibco.xpd.rcp.internal.models.actions.NewProcessAction;
import com.tibco.xpd.rcp.internal.models.actions.NewProcessPackageAction;
import com.tibco.xpd.rcp.internal.models.actions.OpenEditorForFileAction;
import com.tibco.xpd.rcp.internal.models.actions.OpenOrganizationAction;
import com.tibco.xpd.rcp.internal.models.actions.OpenProcessAction;
import com.tibco.xpd.rcp.internal.models.actions.OpenProcessInterfaceAction;
import com.tibco.xpd.rcp.internal.models.actions.SeparatorAction;
import com.tibco.xpd.rcp.internal.resources.IRCPContainer;
import com.tibco.xpd.rcp.internal.resources.IRCPResource;
import com.tibco.xpd.rcp.internal.resources.ModelResource;
import com.tibco.xpd.rcp.internal.resources.ProjectResource;
import com.tibco.xpd.rcp.internal.resources.RCPResourceChangeListener;
import com.tibco.xpd.rcp.internal.resources.RCPResourceManager;
import com.tibco.xpd.rcp.ribbon.AbstractRibbonGroup;

/**
 * Model ribbon group that will list all available models and allow the user to
 * create new models.
 * 
 * @author njpatel
 */
public class ModelRibbonGroup extends AbstractRibbonGroup {

    private static final ImageRegistry IMAGE_REGISTRY = RCPActivator
            .getDefault().getImageRegistry();

    private RibbonButton btnProcess;

    private RibbonButton btnBom;

    private RibbonButton btnOm;

    private final OpenListener listener;

    private RibbonGroup group;

    private ProcessModelProvider processModelProvider;

    private BOMModelProvider bomModelProvider;

    private OMModelProvider omModelProvider;

    public ModelRibbonGroup() {
        // instance = this;
        listener = new OpenListener();
        RCPResourceManager.addOpenListener(listener);
    }

    @Override
    public void dispose() {
        RCPResourceManager.removeOpenListener(listener);
        if (processModelProvider != null) {
            processModelProvider.dispose();
        }

        if (bomModelProvider != null) {
            bomModelProvider.dispose();
        }

        if (omModelProvider != null) {
            omModelProvider.dispose();
        }

        super.dispose();
    }

    @Override
    protected void createContent(RibbonGroup group) {
        this.group = group;
        processModelProvider = new ProcessModelProvider(getWindow());
        btnProcess =
                new RibbonButton(group, IMAGE_REGISTRY.get(RCPImages.PROCESS
                        .getPath()), IMAGE_REGISTRY.get(RCPImages.PROCESS
                        .getDisabledPath()),
                        Messages.ModelRibbonGroup_processPackage_button,
                        RibbonButton.STYLE_ARROW_DOWN);
        btnProcess.setEnabled(false);
        btnProcess
                .addSelectionListener(new ButtonSelectionListener(btnProcess));

        new RibbonGroupSeparator(group);

        bomModelProvider = new BOMModelProvider(getWindow());
        btnBom =
                new RibbonButton(group, IMAGE_REGISTRY.get(RCPImages.BOM
                        .getPath()), IMAGE_REGISTRY.get(RCPImages.BOM
                        .getDisabledPath()),
                        Messages.ModelRibbonGroup_bom_button,
                        RibbonButton.STYLE_ARROW_DOWN);
        btnBom.setEnabled(false);
        btnBom.addSelectionListener(new ButtonSelectionListener(btnBom));

        new RibbonGroupSeparator(group);

        omModelProvider = new OMModelProvider(getWindow());
        btnOm =
                new RibbonButton(group, IMAGE_REGISTRY.get(RCPImages.OM
                        .getPath()), IMAGE_REGISTRY.get(RCPImages.OM
                        .getDisabledPath()),
                        Messages.ModelRibbonGroup_om_button,
                        RibbonButton.STYLE_ARROW_DOWN);
        btnOm.setEnabled(false);
        btnOm.addSelectionListener(new ButtonSelectionListener(btnOm));
    }

    /**
     * Create a menu item for the given action.
     * 
     * @param menu
     * @param action
     * @return
     */
    private MenuItem createMenuItem(Menu menu, final ModelAction action,
            int style) {
        MenuItem item = null;

        if (action instanceof SeparatorAction) {
            // Add a separator
            item = new MenuItem(menu, SWT.SEPARATOR);
        } else {
            item = new MenuItem(menu, style);
            item.setText(action.getLabel());
            if (action.getImage() != null) {
                item.setImage(action.getImage());
            }

            item.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent e) {
                    action.run();
                }
            });
        }

        return item;
    }

    /**
     * Update the model group menus with the models available in the project
     * being opened.
     * 
     * @param resource
     */
    private void opened(IRCPContainer resource) {
        if (resource != null) {
            final Collection<ProjectResource> projectResources =
                    resource.getProjectResources();
            final boolean canAdd = !(resource instanceof ModelResource);
            getWindow().getShell().getDisplay().asyncExec(new Runnable() {
                @Override
                public void run() {
                    // Update the drop-down menus of each button in this ribbon
                    // group
                    updatePackages(projectResources, canAdd);
                    updateBOMs(projectResources, canAdd);
                    updateOMs(projectResources, canAdd);
                    group.redraw();
                }
            });
        }
    }

    /**
     * Update the package button menu.
     * 
     * @param projectResources
     * @param canAdd
     *            <code>true</code> if new models can be added
     */
    private void updatePackages(Collection<ProjectResource> projectResources,
            boolean canAdd) {
        Menu topLevelMenu = btnProcess.getMenu();
        clearMenu(topLevelMenu);

        btnProcess.setEnabled(false);

        if (projectResources != null && !projectResources.isEmpty()
                && btnProcess != null && processModelProvider != null) {
            Map<ProjectResource, List<ModelAction>> projectActions =
                    new TreeMap<ProjectResource, List<ModelAction>>();
            for (ProjectResource projectResource : projectResources) {
                List<ModelAction> actions =
                        processModelProvider.getActions(projectResource
                                .getProject());

                if (!actions.isEmpty()) {
                    projectActions.put(projectResource, actions);
                }
            }

            for (Entry<ProjectResource, List<ModelAction>> entry : projectActions
                    .entrySet()) {
                IProject project = entry.getKey().getProject();
                List<ModelAction> actions = entry.getValue();

                // Project Menu Level
                MenuItem projectLevelMenu =
                        new MenuItem(topLevelMenu, SWT.CASCADE);
                projectLevelMenu.setText(project.getName());
                projectLevelMenu.setImage(IMAGE_REGISTRY.get(RCPImages.PROJECT
                        .getPath()));
                // Package Level Menu
                Menu packageLevelMenu = new Menu(topLevelMenu);
                projectLevelMenu.setMenu(packageLevelMenu);

                // Process Level Menu
                Menu processLevelMenu = null;

                // boolean addSeparator = actions.size() > 1;
                for (ModelAction action : actions) {
                    if (action instanceof OpenEditorForFileAction) {
                        MenuItem packageMenuItem =
                                createMenuItem(packageLevelMenu,
                                        action,
                                        SWT.CASCADE);
                        // Set the new process Level Menu
                        processLevelMenu = new Menu(topLevelMenu);
                        packageMenuItem.setMenu(processLevelMenu);
                        createMenuItem(processLevelMenu, action, SWT.PUSH);

                    } else if (action instanceof NewProcessPackageAction) {
                        if (packageLevelMenu != null) {
                            MenuItem item =
                                    createMenuItem(packageLevelMenu,
                                            action,
                                            SWT.PUSH);
                            // Disable New Package option if not editing an MAA
                            // file.
                            if (!canAdd && action.isNewFileAction()) {
                                item.setEnabled(false);
                            }
                            // Add separator
                            new MenuItem(packageLevelMenu, SWT.SEPARATOR);
                        }
                    } else if (action instanceof NewProcessAction) {
                        if (processLevelMenu != null) {
                            createMenuItem(processLevelMenu, action, SWT.PUSH);
                            // Add separator
                            new MenuItem(processLevelMenu, SWT.SEPARATOR);
                        }
                    } else if (action instanceof OpenProcessAction
                            || action instanceof OpenProcessInterfaceAction) {
                        if (processLevelMenu != null) {
                            createMenuItem(processLevelMenu, action, SWT.PUSH);
                        }
                    }

                }
                btnProcess.setEnabled(true);

            }

        }
    }

    /**
     * Update the BOM button menu.
     * 
     * @param projectResources
     * @param canAdd
     *            <code>true</code> if an MAA is being edited
     */
    private void updateBOMs(Collection<ProjectResource> projectResources,
            boolean canAdd) {
        Menu menu = btnBom.getMenu();
        clearMenu(menu);

        btnBom.setEnabled(false);

        if (projectResources != null && !projectResources.isEmpty()
                && btnBom != null && bomModelProvider != null) {
            Map<ProjectResource, List<ModelAction>> projectActions =
                    new TreeMap<ProjectResource, List<ModelAction>>();
            for (ProjectResource projectResource : projectResources) {
                List<ModelAction> actions =
                        bomModelProvider.getActions(projectResource
                                .getProject());

                if (!actions.isEmpty()) {
                    projectActions.put(projectResource, actions);
                }
            }

            boolean addProjectMenu = projectActions.size() > 1;
            for (Entry<ProjectResource, List<ModelAction>> entry : projectActions
                    .entrySet()) {
                IProject project = entry.getKey().getProject();
                List<ModelAction> actions = entry.getValue();
                Menu subMenu = menu;

                if (addProjectMenu) {
                    MenuItem prMenu = new MenuItem(menu, SWT.CASCADE);
                    prMenu.setText(project.getName());
                    prMenu.setImage(IMAGE_REGISTRY.get(RCPImages.PROJECT
                            .getPath()));
                    subMenu = new Menu(menu);
                    prMenu.setMenu(subMenu);
                }

                for (ModelAction action : actions) {
                    MenuItem item = createMenuItem(subMenu, action, SWT.PUSH);
                    // Disable New BOM option if not editing an MAA file.
                    if (!canAdd && action.isNewFileAction()) {
                        item.setEnabled(false);
                    }
                    if (action instanceof NewBOMAction) {
                        // Add separator
                        new MenuItem(subMenu, SWT.SEPARATOR);
                    }
                }
                btnBom.setEnabled(true);

            }
        }
    }

    /**
     * Update the OM button menu.
     * 
     * @param projectResources
     * @param canAdd
     *            <code>true</code> if an MAA is being edited
     */
    private void updateOMs(Collection<ProjectResource> projectResources,
            boolean canAdd) {
        Menu topLevelMenu = btnOm.getMenu();
        clearMenu(topLevelMenu);

        btnOm.setEnabled(false);

        if (projectResources != null && !projectResources.isEmpty()
                && btnOm != null && omModelProvider != null) {

            Map<ProjectResource, List<ModelAction>> projectActions =
                    new TreeMap<ProjectResource, List<ModelAction>>();
            for (ProjectResource projectResource : projectResources) {
                List<ModelAction> actions =
                        omModelProvider
                                .getActions(projectResource.getProject());

                if (!actions.isEmpty()) {
                    projectActions.put(projectResource, actions);
                }
            }

            for (Entry<ProjectResource, List<ModelAction>> entry : projectActions
                    .entrySet()) {
                IProject project = entry.getKey().getProject();
                List<ModelAction> actions = entry.getValue();
                // Project Menu Level
                MenuItem projectLevelMenu =
                        new MenuItem(topLevelMenu, SWT.CASCADE);
                projectLevelMenu.setText(project.getName());
                projectLevelMenu.setImage(IMAGE_REGISTRY.get(RCPImages.PROJECT
                        .getPath()));

                // OM Model Menu Level
                Menu omModelLevelMenu = new Menu(topLevelMenu);
                projectLevelMenu.setMenu(omModelLevelMenu);

                // Organisation Level Menu
                Menu organisationLevelMenu = null;

                for (ModelAction action : actions) {

                    if (action instanceof OpenEditorForFileAction) {
                        MenuItem omModelMenuItem =
                                createMenuItem(omModelLevelMenu,
                                        action,
                                        SWT.CASCADE);
                        // Set the new organisation Level Menu
                        organisationLevelMenu = new Menu(topLevelMenu);
                        omModelMenuItem.setMenu(organisationLevelMenu);
                        createMenuItem(organisationLevelMenu, action, SWT.PUSH);

                    } else if (action instanceof NewOrganizationModelAction) {
                        if (omModelLevelMenu != null) {
                            MenuItem item =
                                    createMenuItem(omModelLevelMenu,
                                            action,
                                            SWT.PUSH);
                            // Disable New om model option if not editing an MAA
                            // file.
                            if (!canAdd && action.isNewFileAction()) {
                                item.setEnabled(false);
                            }
                            // Add separator
                            new MenuItem(omModelLevelMenu, SWT.SEPARATOR);
                        }
                    } else if (action instanceof NewOrganizationAction) {
                        if (organisationLevelMenu != null) {
                            createMenuItem(organisationLevelMenu,
                                    action,
                                    SWT.PUSH);
                            // Add separator
                            new MenuItem(organisationLevelMenu, SWT.SEPARATOR);
                        }
                    } else if (action instanceof OpenOrganizationAction) {
                        if (organisationLevelMenu != null) {
                            createMenuItem(organisationLevelMenu,
                                    action,
                                    SWT.PUSH);
                        }
                    }
                }
                btnOm.setEnabled(true);

            }
        }
    }

    /**
     * Clear all menu items from the given menu.
     * 
     * @param menu
     */
    private void clearMenu(Menu menu) {
        for (MenuItem item : menu.getItems()) {
            item.dispose();
            item = null;
        }
    }

    /**
     * Open new project listener which will update the drop-down menus of the
     * model group.
     * 
     * @author njpatel
     * 
     */
    private class OpenListener implements IOpenResourceListener,
            RCPResourceChangeListener {

        @Override
        public void opened(IRCPContainer resource) {
            ModelRibbonGroup.this.opened(resource);
            resource.addChangeListener(this);
        }

        @Override
        public void resourceChanged(final IRCPResource resource,
                final RCPResourceChangeEvent event) {
            final boolean canAdd = !(resource instanceof ModelResource);

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    /*
                     * if the current resource has been disposed then clear all
                     * menus
                     */
                    if (event.eventType == RCPResourceEventType.DISPOSED) {
                        updatePackages(null, canAdd);
                        updateBOMs(null, canAdd);
                        updateOMs(null, canAdd);
                        if (group != null && !group.isDisposed()) {
                            group.redraw();
                        }
                    } else {
                        // Update process packages
                        if (processModelProvider != null
                                && processModelProvider
                                        .isAffectingEvent(event.eventType,
                                                event.eventObj)
                                && resource instanceof IRCPContainer) {
                            updatePackages(((IRCPContainer) resource).getProjectResources(),
                                    canAdd);
                        }

                        // Update BOMs
                        if (bomModelProvider != null
                                && bomModelProvider
                                        .isAffectingEvent(event.eventType,
                                                event.eventObj)
                                && resource instanceof IRCPContainer) {
                            updateBOMs(((IRCPContainer) resource).getProjectResources(),
                                    canAdd);
                        }

                        // Update OMs
                        if (omModelProvider != null
                                && omModelProvider
                                        .isAffectingEvent(event.eventType,
                                                event.eventObj)
                                && resource instanceof IRCPContainer) {
                            updateOMs(((IRCPContainer) resource).getProjectResources(),
                                    canAdd);
                        }

                        if (group != null && !group.isDisposed()) {
                            group.redraw();
                        }
                    }
                }
            };
            getWindow().getShell().getDisplay().asyncExec(runnable);
        }

    }

    /**
     * Model buttons selection listener - this will open the drop-down menu of
     * the selected model (bom, process or om).
     * 
     * @author njpatel
     * 
     */
    private class ButtonSelectionListener extends SelectionAdapter {

        private final RibbonButton button;

        public ButtonSelectionListener(RibbonButton button) {
            this.button = button;
        }

        @Override
        public void widgetSelected(SelectionEvent e) {
            button.showMenu();
        }

    }

}
