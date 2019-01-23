/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.ui.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowPulldownDelegate2;
import org.eclipse.ui.actions.ActionDelegate;
import org.eclipse.ui.activities.ICategory;
import org.eclipse.ui.activities.NotDefinedException;

import com.tibco.xpd.ui.internal.actions.ActivitiesUtil;

/**
 * This class provides an action which enables/disables Activity Capabilities by
 * adding a drop down box to the workbench Toolbar.
 * 
 * @author rassisi
 * 
 */
public class ActivateCapabilityAction extends ActionDelegate implements
        IWorkbenchWindowPulldownDelegate2 {

    /**
     * The menu created by this action
     */
    private Menu fMenu;

    /**
     * if no activity categories are defined the toolbar action will be
     * disabled.
     */
    private boolean enable;

    /**
     * The action used to render this delegate.
     */
    private IAction fAction;

    /**
     * Constructor;
     */
    public ActivateCapabilityAction() {
        enable = false;
    }

    @Override
    public void runWithEvent(IAction action, Event event) {
        super.runWithEvent(action, event);
        if (event.widget instanceof ToolItem) {
            ToolItem toolItem = (ToolItem) event.widget;
            if (!enable) {
                // TODO:
                // toolItem.setEnabled(enable);
                // if (fAction != null) {
                // fAction.setEnabled(false);
                // }
                return;
            }

            Control control = toolItem.getParent();
            fMenu = getMenu(control);
        }
        if (fMenu != null) {
            fMenu.setVisible(true);
        }
    }

    /**
     * @param action
     */
    private void setAction(IAction action) {
        fAction = action;
    }

    /**
     * @param menu
     * @param action
     * @param label
     */
    private void addToMenu(Menu menu, IAction action, String label) {
        action.setText(label);
        ActionContributionItem item = new ActionContributionItem(action) {
            @Override
            public void fill(ToolBar parent, int index) {
                super.fill(parent, index);
            }

            @Override
            public void fill(Menu parent, int index) {
                super.fill(parent, index);
            }
        };
        item.fill(menu, -1);
    }

    /**
     * @param action
     */
    private void initialize(IAction action) {
        setAction(action);
        action.setEnabled(true);
    }

    @Override
    public void dispose() {
        setMenu(null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IWorkbenchWindowPulldownDelegate#getMenu(org.eclipse.swt.widgets.Control)
     */
    public Menu getMenu(Control parent) {
        setMenu(new Menu(parent));
        fillMenu(fMenu);
        return fMenu;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IWorkbenchWindowPulldownDelegate2#getMenu(org.eclipse.swt.widgets.Menu)
     */
    public Menu getMenu(Menu parent) {
        setMenu(new Menu(parent));
        fillMenu(fMenu);
        return fMenu;
    }

    /**
     * @param menu
     */
    private void setMenu(Menu menu) {
        if (fMenu != null) {
            fMenu.dispose();
        }
        fMenu = menu;
    }

    /**
     * @param menu
     */
    @SuppressWarnings("unchecked")
    private void fillMenu(Menu menu) {
        ArrayList<ICategory> categories = new ArrayList<ICategory>(
                ActivitiesUtil.getCategories());
        Collections.sort(categories, new Comparator<ICategory>() {
            public int compare(ICategory c1, ICategory c2) {

                String c1Name = c1.getId();
                try {
                    c1Name = c1.getName();
                } catch (NotDefinedException e) {
                    // ignore
                }

                String c2Name = c2.getId();
                try {
                    c2Name = c2.getName();
                } catch (NotDefinedException e) {
                    // ignore
                }

                return c1Name.compareTo(c2Name);
            }
        });
        for (Object category : categories) {
            if (addActivityAction(menu, (ICategory) category)) {
                enable = true;
            }
        }
    }

    /**
     * @param menu
     * @param activityId
     * @param enable
     * @param iconPath
     * @param label
     */
    private boolean addActivityAction(Menu menu, final ICategory category) {
        final Action activateCapability;
        try {
            activateCapability = new Action(category.getName()) {
                @SuppressWarnings("unchecked")
                @Override
                public void runWithEvent(Event event) {
                    super.runWithEvent(event);
                    boolean tickIt = !ActivitiesUtil
                            .isCategoryEnabled(category);
                    ActivitiesUtil.setCategoryEnabled(category.getId(), tickIt);
                    boolean activated = ActivitiesUtil
                            .isCategoryEnabled(category);
                    setChecked(activated);
                }
            };
        } catch (NotDefinedException e1) {
            // do nothing
            return false;
        }

        boolean activated = ActivitiesUtil.isCategoryEnabled(category);
        if (category.isDefined() && !ActivitiesUtil.isCategoryEmpty(category)) {
            activateCapability.setChecked(activated);
            try {
                addToMenu(menu, activateCapability, category.getName());
                return true;
            } catch (NotDefinedException e) {
                // cannot happen
                return false;
            }
        }
        return false;
    }

    @Override
    public void run(IAction action) {
        // do nothing - this is just a menu
    }

    /**
     * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
     *      org.eclipse.jface.viewers.ISelection)
     */
    @Override
    public void selectionChanged(IAction action, ISelection selection) {
        if (fAction == null) {
            initialize(action);
        }
    }

    /**
     * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#init(org.eclipse.ui.IWorkbenchWindow)
     */
    public void init(IWorkbenchWindow window) {
    }

}
