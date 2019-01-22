package com.tibco.xpd.swtbot.performance.utils;

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withMnemonic;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;

import java.util.Arrays;

import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.WidgetResult;
import org.eclipse.swtbot.swt.finder.widgets.AbstractSWTBot;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.hamcrest.Matcher;

/**
 * Contains utilities find SWT Bot widgets if there is a problem doing this the
 * bot way.
 * 
 * @author jarciuch
 * 
 */
public class FindUtils {

    /**
     * Finds the sub-menus in a context menu.
     * 
     * @param submenus
     *            the texts on the context menu.
     * @return Requested context menu item.
     * @throws WidgetNotFoundException
     *             if the widget is not found.
     * 
     *             <pre>
     * BOT api doen't work (although it should).
     *   SWTBotMenu contextMenu = projTreeItem.contextMenu("Export");
     *   contextMenu.menu("Distributed Application Archive (DAA) Export")
     * </pre>
     */
    public static SWTBotMenu findContextMenu(final AbstractSWTBot bot,
            final String contextMenuString, final String... submenus) {

        final SWTBotMenu contextMenu = bot.contextMenu(contextMenuString);
        if (submenus.length == 0) {
            return contextMenu;
        }
        // find submenus of the context menu.
        final MenuItem menuItem =
                UIThreadRunnable.syncExec(new WidgetResult<MenuItem>() {
                    @Override
                    public MenuItem run() {
                        Menu menu = contextMenu.widget.getMenu();
                        MenuItem menuItem = null;
                        for (String text : submenus) {
                            Matcher<?> matcher =
                                    allOf(instanceOf(MenuItem.class),
                                            withMnemonic(text));
                            menuItem = findMenuItem(menu, matcher);
                            if (menuItem != null) {
                                menu = menuItem.getMenu();
                            } else {
                                break;
                            }
                        }

                        return menuItem;
                    }
                });
        if (menuItem != null) {
            return new SWTBotMenu(menuItem);
        }
        throw new WidgetNotFoundException("Could not find menu: "
                + contextMenuString + " > " + Arrays.asList(submenus));
    }

    private static MenuItem findMenuItem(final Menu menu,
            final Matcher<?> matcher) {
        if (menu != null) {
            MenuItem[] items = menu.getItems();
            for (final MenuItem menuItem : items) {
                if (matcher.matches(menuItem)) {
                    return menuItem;
                }
            }
        }
        return null;
    }
}