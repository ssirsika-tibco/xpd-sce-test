/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;

/**
 * Simple way to add drop down toolbar buttons.
 * <p>
 * Simply sub-class this class and override createMenuManager() to return a
 * MenuManager containing your actions.
 * <p>
 * Then create an instance of your sub-class and add it to any troolbar manager
 * you like.
 * 
 * @author aallway
 * @since 3.2
 */
public abstract class DropDownToolbarButton extends Action implements IMenuCreator {

    private MenuManager dropDownMenu;

    public DropDownToolbarButton(String label, ImageDescriptor image) {
        super(label, image);
        setMenuCreator(this);
    }
    
    /**
     * Create the menu manager for your toolbar button drop down.
     * 
     * @return the menu manager for your drop down.
     */
    protected abstract MenuManager createMenuManager();


    private void createMenu() {
        if (dropDownMenu == null) {
            dropDownMenu = createMenuManager();
        }
        return;
    }

    public void dispose() {
        if (dropDownMenu != null) {
            dropDownMenu.dispose();
            dropDownMenu = null;
        }
        return;
    }

    public Menu getMenu(Control parent) {
        createMenu();
        return dropDownMenu.createContextMenu(parent);
    }

    public Menu getMenu(Menu parent) {
        createMenu();
        Menu menu = new Menu(parent);
        IContributionItem[] items = dropDownMenu.getItems();
        for (int i = 0; i < items.length; i++) {
            IContributionItem item = items[i];
            IContributionItem newItem = item;
            if (item instanceof ActionContributionItem) {
                newItem =
                        new ActionContributionItem(
                                ((ActionContributionItem) item).getAction());
            }
            newItem.fill(menu, -1);
        }
        return menu;
    }
    
}
